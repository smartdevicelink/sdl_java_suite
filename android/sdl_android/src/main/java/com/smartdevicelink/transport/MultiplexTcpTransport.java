/*
 * Copyright (c) 2018 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.transport;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.WiFiSocketFactory;
import com.smartdevicelink.util.DebugTool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.smartdevicelink.util.DebugTool.logError;
import static com.smartdevicelink.util.NativeLogTool.logInfo;

public class MultiplexTcpTransport extends MultiplexBaseTransport {

	private static final String TAG = "MultiplexTcpTransport";

	private static final int READ_BUFFER_SIZE = 4096;
	private static final int RECONNECT_DELAY = 5000;
	private static final int RECONNECT_RETRY_COUNT = 30;

    private final String ipAddress;
	private final int port;
	private final boolean autoReconnect;
	private Socket mSocket = null;
	private InputStream mInputStream = null;
	private OutputStream mOutputStream = null;
	private MultiplexTcpTransport.TcpTransportThread mThread = null;
	private WriterThread writerThread;
	private final Context mContext;


	public MultiplexTcpTransport(int port, String ipAddress, boolean autoReconnect, Handler handler, Context context) {
		super(handler, TransportType.TCP);
		this.ipAddress = ipAddress;
		this.port = port;
        connectedDeviceAddress = ipAddress + ":" + port;
		this.autoReconnect = autoReconnect;
		mContext = context;
		setState(STATE_NONE);
	}

	public synchronized void start(){
		if(getState() == STATE_NONE) {
			synchronized (this) {
				setState(STATE_CONNECTING);
				logInfo("TCPTransport: openConnection request accepted. Starting transport thread");
				try {
					mThread = new MultiplexTcpTransport.TcpTransportThread();
					mThread.setDaemon(true);
					mThread.start();

					// Initialize the SiphonServer
					if (SiphonServer.getSiphonEnabledStatus()) {
						SiphonServer.init();
					}

				} catch (Exception e) {
					logError(TAG, "TCPTransport: Exception during transport thread starting", e);
				}
			}
		} else {
			logInfo("TCPTransport: openConnection request rejected. Another connection is not finished");
		}

		// Send the name of the connected device back to the UI Activity
		Message msg = handler.obtainMessage(SdlRouterService.MESSAGE_DEVICE_NAME);
		Bundle bundle = new Bundle();
		bundle.putString(DEVICE_NAME, connectedDeviceName);
		bundle.putString(DEVICE_ADDRESS, connectedDeviceAddress);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}


	@Override
	protected void stop(int state) {
		try {
			if(mThread != null) {
				mThread.halt();
				mThread.interrupt();
			}

			if (writerThread != null) {
				writerThread.cancel();
				writerThread = null;
			}

			if(mSocket != null){
				mSocket.close();
			}
			mSocket = null;
		} catch (IOException e) {
			logError(TAG,"TCPTransport.disconnect: Exception during disconnect: " + e.getMessage());
		}

		setState(state);
	}

	@Override
	public void write(byte[] out, int offset, int count) {
		// Create temporary object
		MultiplexTcpTransport.WriterThread r;
		// Synchronize a copy of the ConnectedThread
		synchronized (this) {
			if (mState != STATE_CONNECTED) return;
			r = writerThread;
			//r.write(out,offset,count);
		}
		// Perform the write unsynchronized
		r.write(out,offset,count);
	}

	/**
	 * Implementation of waiting required delay that cannot be interrupted
	 * @param timeMs Time in milliseconds of required delay
	 */
	private void waitFor(long timeMs) {
		long endTime = System.currentTimeMillis() +timeMs;
		while (System.currentTimeMillis() < endTime) {
			synchronized (this) {
				try {
					wait(endTime - System.currentTimeMillis());
				} catch (Exception e) {
					// Nothing To Do, simple wait
				}
			}
		}
	}

	private void startWriteThread() {
		if (writerThread == null) {
			writerThread =  new MultiplexTcpTransport.WriterThread();
			writerThread.start();
		}
	}

	/**
	 * Internal class that represents separate thread, that does actual work, related to connecting/reading/writing data
	 */
	private class TcpTransportThread extends Thread {
		final SdlPsm psm;
		public TcpTransportThread(){
			psm = new SdlPsm();
		}
		/**
		 * Represents current thread state - halted or not. This flag is used to change internal behavior depending
		 * on current state.
		 */
		private Boolean isHalted = false;

		/**
		 * Method that marks thread as halted.
		 */
		public void halt() {
			isHalted = true;
		}

		/**
		 * Tries to connect to the SmartDeviceLink core. Behavior depends autoReconnect configuration param:
		 *      a) If autoReconnect is false, then only one connect try will be performed.
		 *      b) If autoReconnect is true, then in case of connection error continuous reconnect will be performed
		 *          after short delay until connection will be established or retry count will be reached
		 *
		 * @return true if connection established and false otherwise
		 */
		private boolean connect() {
			boolean bConnected;
			int remainingRetry = RECONNECT_RETRY_COUNT;

			synchronized (MultiplexTcpTransport.this) {
				do {
					try {

						if ((null != mSocket) && (!mSocket.isClosed())) {
							logInfo("TCPTransport.connect: Socket is not closed. Trying to close it");
							mSocket.close();
						}

						logInfo(String.format("TCPTransport.connect: Socket is closed. Trying to connect to %s", getAddress()));
						mSocket = WiFiSocketFactory.createSocket(mContext);
						mSocket.connect(new InetSocketAddress(ipAddress, port));
						mOutputStream = mSocket.getOutputStream();
						mInputStream = mSocket.getInputStream();
						startWriteThread();
					} catch (IOException e) {
						logError(TAG, "TCPTransport.connect: Exception during connect stage: " + e.getMessage());
					}

					bConnected = (null != mSocket) && mSocket.isConnected();

					if(bConnected){
						logInfo("TCPTransport.connect: Socket connected");
						setState(STATE_CONNECTED);
					}else{
						if(autoReconnect){
							remainingRetry--;
							logInfo(String.format(Locale.US,"TCPTransport.connect: Socket not connected. AutoReconnect is ON. retryCount is: %d. Waiting for reconnect delay: %d"
									, remainingRetry, RECONNECT_DELAY));
							waitFor(RECONNECT_DELAY);
						} else {
							logInfo("TCPTransport.connect: Socket not connected. AutoReconnect is OFF");
						}
					}
				} while ((!bConnected) && (autoReconnect) && (remainingRetry > 0) && (!isHalted));

				return bConnected;
			}
		}

		/**
		 * Performs actual thread work
		 */
		@Override
		public void run() {
			logInfo("TCPTransport.run: transport thread created. Starting connect stage");
			psm.reset();
			while(!isHalted) {
				setState(STATE_CONNECTING);
				if(!connect()){
					if (isHalted) {
						logInfo("TCPTransport.run: Connection failed, but thread already halted");
					} else {
						MultiplexTcpTransport.this.stop(STATE_NONE);
					}
					break;
				}

				synchronized (MultiplexTcpTransport.this) {
					setState(STATE_CONNECTED);
				}

				byte input;
				byte[] buffer = new byte[READ_BUFFER_SIZE];
				int bytesRead;
				boolean stateProgress = false;
				while (!isHalted) {
					//logInfo("TCPTransport.run: Waiting for data...");
					try {
						//input = (byte) mInputStream.read();
						bytesRead = mInputStream.read(buffer);
					} catch (IOException e) {
						internalHandleStreamReadError();
						break;
					}

					if (bytesRead == -1) {
						// Javadoc says -1 indicates end of input stream.  In TCP case this means loss
						// of connection from HU (no exception is thrown when HU connection is lost).
						internalHandleStreamReadError();
						break;
					}

					synchronized (MultiplexTcpTransport.this) {
						if (mThread.isInterrupted()) {
							logInfo("TCPTransport.run: Got new data but thread is interrupted");
							break;
						}
					}
					for (int i = 0; i < bytesRead; i++) {
						//logInfo("TCPTransport.run: Got new data");
						// Send the response of what we received
						input = buffer[i];
						stateProgress = psm.handleByte(input);
						if (!stateProgress) {//We are trying to weed through the bad packet info until we get something

							//Log.w(TAG, "Packet State Machine did not move forward from state - "+ psm.getState()+". PSM being Reset.");
							psm.reset();
						}

						if (psm.getState() == SdlPsm.FINISHED_STATE)
						{
							synchronized (MultiplexTcpTransport.this) {
								DebugTool.logInfo(TAG, "Packet formed, sending off");
								SdlPacket packet = psm.getFormedPacket();
								packet.setTransportRecord(getTransportRecord());
								handler.obtainMessage(SdlRouterService.MESSAGE_READ, packet).sendToTarget();
							}
							//We put a trace statement in the message read so we can avoid all the extra bytes
							psm.reset();
						}
						//FIXME logInfo(String.format("TCPTransport.run: Received %d bytes", bytesRead));
					}
				}
			}

			logInfo("TCPTransport.run: Thread terminated");
			setState(STATE_NONE);
		}

		/**
		 * Internal handling of Tcp disconnection
		 */
		private void internalHandleTCPDisconnect() {
			if(isHalted){
				logInfo("TCPTransport.run: TCP disconnect received, but thread already halted");
			} else {
				logInfo("TCPTransport.run: TCP disconnect received");
				MultiplexTcpTransport.this.stop(STATE_NONE);
			}
		}

		/**
		 * Internal handling of reading data from input stream
		 */
		private void internalHandleStreamReadError() {
			if(isHalted){
				logError(TAG, "TCPTransport.run: Exception during reading data, but thread already halted");
			} else {
				logError(TAG, "TCPTransport.run: Exception during reading data");
				MultiplexTcpTransport.this.stop(STATE_NONE);
			}
		}
	}

	private class WriterThread extends Thread {
		private boolean isHalted = false;
		private final boolean mVerbose = false;
		final BlockingQueue<OutPacket> packetQueue = new LinkedBlockingQueue<>();

		@Override
		public void run() {
			while(!isHalted){
				try{
					OutPacket packet = packetQueue.take();
					if(packet == null){
						continue;
					}

					OutputStream out;
					synchronized (MultiplexTcpTransport.this) {
						out = mOutputStream;
					}

					if ((out != null) && (!isHalted)) {
						try {
							out.write(packet.bytes, packet.offset, packet.count);
							if (mVerbose) {
								logInfo("TCPTransport.sendBytesOverTransport: successfully sent data");
							}
						} catch (IOException e) {
							logError(TAG, "TCPTransport.sendBytesOverTransport: error during sending data: " + e.getMessage());
						}
					} else {
						if (isHalted) {
							logError(TAG, "TCPTransport: sendBytesOverTransport request accepted, thread is cancelled");
						} else {
							logError(TAG, "TCPTransport: sendBytesOverTransport request accepted, but output stream is null");
						}
					}

				}catch(InterruptedException e){
					break;
				}
			}
		}

		public void write(byte[] msgBytes, int offset, int count) {
			if ((msgBytes == null) || (msgBytes.length == 0)) {
				logInfo("TCPTransport.sendBytesOverTransport: nothing to send");
				return;
			}

			if (offset + count > msgBytes.length) {
				count = msgBytes.length - offset;
			}
			packetQueue.add(new OutPacket(msgBytes, offset, count));

		}

		public synchronized void cancel() {
			isHalted = true;
			if (mOutputStream != null) {
				synchronized (MultiplexTcpTransport.this) {
					try {
						mOutputStream.flush();
					} catch (IOException e) {
						logError(TAG, "TCPTransport flushing output stream failed: " + e.getMessage());
					}

					try {
						mOutputStream.close();
					} catch (IOException e) {
						logError(TAG, "TCPTransport closing output stream failed: " + e.getMessage());
					}
					mOutputStream = null;
				}
			}
		}
	}

	private final class OutPacket{
		final byte[] bytes;
		final int count;
		final int offset;

		OutPacket(byte[] bytes, int offset, int count){
			this.bytes = bytes;
			this.offset = offset;
			this.count = count;
		}
	}


}
