/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
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
package com.smartdevicelink.streaming;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.util.DebugTool;

public class StreamPacketizer extends AbstractPacketizer implements IVideoStreamListener, IAudioStreamListener, Runnable{

	public final static String TAG = "StreamPacketizer";

	private Thread t = null;


	private final static int TLS_MAX_RECORD_SIZE = 16384;
	private final static int TLS_RECORD_HEADER_SIZE = 5;
	private final static int TLS_RECORD_MES_AUTH_CDE_SIZE = 32;
	private final static int TLS_MAX_RECORD_PADDING_SIZE = 256;


	private final static int BUFF_READ_SIZE = TLS_MAX_RECORD_SIZE - TLS_RECORD_HEADER_SIZE - TLS_RECORD_MES_AUTH_CDE_SIZE - TLS_MAX_RECORD_PADDING_SIZE;

	// Approximate size of data that mOutputQueue can hold in bytes.
	// By adding a buffer, we accept underlying transport being stuck for a short time. By setting
	// a limit of the buffer size, we avoid buffer overflows when underlying transport is too slow.
	private static final int MAX_QUEUE_SIZE = 256 * 1024;

    private Object mPauseLock;
    private boolean mPaused;
    private boolean isServiceProtected = false;
	private BlockingQueue<ByteBufferWithListener> mOutputQueue;

	public StreamPacketizer(IStreamListener streamListener, InputStream is, SessionType sType, byte rpcSessionID, SdlSession session) throws IOException {
		super(streamListener, is, sType, rpcSessionID, session);
        mPauseLock = new Object();
        mPaused = false;
        isServiceProtected = _session.isServiceProtected(_serviceType);
		if (bufferSize == 0) {
			// fail safe
			bufferSize = BUFF_READ_SIZE;
		}
		if(isServiceProtected){ //If our service is encrypted we can only use 1024 as the max buffer size. 
			bufferSize = BUFF_READ_SIZE;
			buffer = new byte[bufferSize];
		}
		mOutputQueue = new LinkedBlockingQueue<ByteBufferWithListener>(MAX_QUEUE_SIZE / bufferSize);
	}

	public void start() throws IOException {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	public void stop() {

		if (t != null)
		{
			t.interrupt();
			t = null;
		}

	}

	public void run() {
		int length;
		try 
		{
			while (t != null && !t.isInterrupted()) 
			{
				synchronized(mPauseLock)
				{
					while (mPaused)
                    {
						try
                        {
							mPauseLock.wait();
                        }
                        catch (InterruptedException e) {
							DebugTool.logError(TAG, "Streaming thread has been interrupted", e);
						}
                    }
                }

				if (is != null) { // using InputStream interface
					length = is.read(buffer, 0, bufferSize);

					if (length >= 0) {
						ProtocolMessage pm = new ProtocolMessage();
						pm.setSessionID(_rpcSessionID);
						pm.setSessionType(_serviceType);
						pm.setFunctionID(0);
						pm.setCorrID(0);
						pm.setData(buffer, length);
						pm.setPayloadProtected(isServiceProtected);

						if (t != null && !t.isInterrupted()) {
							_streamListener.sendStreamPacket(pm);
						}
					}
				} else { // using sendFrame interface
					ByteBufferWithListener byteBufferWithListener;
					ByteBuffer frame;
					CompletionListener completionListener;
					try {
						byteBufferWithListener = mOutputQueue.take();
						frame = byteBufferWithListener.byteBuffer;
						completionListener = byteBufferWithListener.completionListener;
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}

					while (frame.hasRemaining()) {
						int len = frame.remaining() > bufferSize ? bufferSize : frame.remaining();

						ProtocolMessage pm = new ProtocolMessage();
						pm.setSessionID(_rpcSessionID);
						pm.setSessionType(_serviceType);
						pm.setFunctionID(0);
						pm.setCorrID(0);
						pm.setData(frame.array(), frame.arrayOffset() + frame.position(), len);
						pm.setPayloadProtected(isServiceProtected);

						if (t != null && !t.isInterrupted()) {
							_streamListener.sendStreamPacket(pm);
						}

						frame.position(frame.position() + len);
					}

					if (!frame.hasRemaining() && completionListener != null){
						completionListener.onComplete(true);
					}
				}
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(_session != null) {
				_session.endService(_serviceType,_rpcSessionID);
			}


		}
	}

    @Override
	public void pause() {
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    @Override
    public void resume() {
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }

	/**
	 * Called by the app.
	 *
	 * @see IVideoStreamListener#sendFrame(byte[], int, int, long)
	 */
	@Override
	public void sendFrame(byte[] data, int offset, int length, long presentationTimeUs)
			throws ArrayIndexOutOfBoundsException {
		sendArrayData(data, offset, length);
	}

	/**
	 * Called by the app.
	 *
	 * @see IVideoStreamListener#sendFrame(ByteBuffer, long)
	 */
	@Override
	public void sendFrame(ByteBuffer data, long presentationTimeUs) {
		sendByteBufferData(data, null);
	}

	/**
	 * Called by the app.
	 *
	 * @see IAudioStreamListener#sendAudio(byte[], int, int, long)
	 */
	@Override
	public void sendAudio(byte[] data, int offset, int length, long presentationTimeUs)
			throws ArrayIndexOutOfBoundsException {
		sendArrayData(data, offset, length);
	}

	/**
	 * Called by the app.
	 *
	 * @see IAudioStreamListener#sendAudio(ByteBuffer, long)
	 */
	@Deprecated
	@Override
	public void sendAudio(ByteBuffer data, long presentationTimeUs) {
		sendByteBufferData(data, null);
	}

	@Override
	public void sendAudio(ByteBuffer data, long presentationTimeUs, CompletionListener completionListener) {
		sendByteBufferData(data, completionListener);
	}

	private void sendArrayData(byte[] data, int offset, int length)
			throws ArrayIndexOutOfBoundsException {
		if (offset < 0 || offset > data.length || length <= 0 || offset + length > data.length) {
			throw new ArrayIndexOutOfBoundsException();
		}

		// StreamPacketizer does not need to split a video frame into NAL units
		ByteBuffer buffer = ByteBuffer.allocate(length);
		buffer.put(data, offset, length);
		buffer.flip();

		try {
			mOutputQueue.put(new ByteBufferWithListener(buffer, null));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void sendByteBufferData(ByteBuffer data, CompletionListener completionListener) {
		if (data == null || data.remaining() == 0) {
			return;
		}

		// copy the whole buffer, so that even if the app modifies original ByteBuffer after
		// sendFrame() or sendAudio() call, our buffer will stay intact
		ByteBuffer buffer = ByteBuffer.allocate(data.remaining());
		buffer.put(data);
		buffer.flip();

		try {
			mOutputQueue.put(new ByteBufferWithListener(buffer, completionListener));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}


	private class ByteBufferWithListener{
		ByteBuffer byteBuffer;
		CompletionListener completionListener;
		ByteBufferWithListener (ByteBuffer byteBuffer, CompletionListener completionListener){
			this.byteBuffer = byteBuffer;
			this.completionListener = completionListener;
		}
	}
}
