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

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.Parcelable;

import com.smartdevicelink.SdlConnection.SdlConnection;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;

import java.util.List;

@Deprecated
public class MultiplexTransport extends SdlTransport{
	private final static String TAG = "Multiplex Transport";
	private String sComment = "Multiplexing";
	
	TransportBrokerThread brokerThread;
	protected boolean isDisconnecting = false;
	MultiplexTransportConfig transportConfig;
	public MultiplexTransport(MultiplexTransportConfig transportConfig, final ITransportListener transportListener){
		super(transportListener);
		if(transportConfig == null){
			this.handleTransportError("Transport config was null", null);
			throw new IllegalArgumentException("Null transportConfig in MultiplexTransport constructor");
		}
		this.transportConfig = transportConfig;
		brokerThread = new TransportBrokerThread(transportConfig.context, transportConfig.appId, transportConfig.service);
		brokerThread.start();
		isDisconnecting = false;
		//brokerThread.initTransportBroker();
		//brokerThread.start();

	}
	
	public boolean isDisconnecting(){
		return this.isDisconnecting;
	}
	/**
	 * Returns the config that was used to create this transport
	 * @return
	 */
	public MultiplexTransportConfig getConfig(){
		return this.transportConfig;
	}
	
	public boolean requestNewSession(){
		if(brokerThread!=null){
			brokerThread.requestNewSession();
			return true;
		}
		return false;
	}
	
	public void removeSession(long sessionId){
		if(brokerThread!=null){
			brokerThread.removeSession(sessionId);
		}
	}
	
	/**
	 * Overridden abstract method which returns specific type of this transport.
	 * 
	 * @return Constant value - TransportType.BLUETOOTH.
	 * @see TransportType
	 */
	public TransportType getTransportType() {
		return TransportType.MULTIPLEX;
	}

	@Override
	public String getBroadcastComment() {
		return sComment;
	}

	@Override
	protected boolean sendBytesOverTransport(SdlPacket packet) {
		if(brokerThread!=null){
			brokerThread.sendPacket(packet);
			return true;
		}
		return false; //Sure why not.
	}

	@Override
	public void openConnection() throws SdlException {
		DebugTool.logInfo(TAG, "Open connection");
		if(brokerThread!=null){
			brokerThread.startConnection();
		}//else should log out
		
	}

	@Override
	public void disconnect() {
		if(isDisconnecting){
			return;
		}
		DebugTool.logInfo(TAG, "Close connection");
			this.isDisconnecting= true;
			if(brokerThread!= null){
				brokerThread.cancel();
				brokerThread = null;
			}
			handleTransportDisconnected(TransportType.MULTIPLEX.name());
			isDisconnecting = false;
		
	}
	
	

	@Override
	protected void handleTransportError(String message, Exception ex) {
		if(brokerThread!=null){
			brokerThread.cancel();
			//brokerThread.interrupt();
			brokerThread = null;
		}
		super.handleTransportError(message, ex);
	}

	
	public boolean isPendingConnected(){
		if(brokerThread!=null){
			return brokerThread.queueStart;
		}
		return false;
	}
	/**
	 * This thread will handle the broker transaction with the router service.
	 *
	 */
	protected class TransportBrokerThread extends Thread{
		boolean connected = false; //This helps clear up double on hardware connects
		TransportBroker broker;
		boolean queueStart = false;
		final Context context;
		final String appId;
		final ComponentName service;
		Looper threadLooper = null;
		/**
		 * Thread will automatically start to prepare its looper.
		 * @param context
		 * @param appId
		 */
		public TransportBrokerThread(Context context, String appId, ComponentName service){
			//this.start();
			super();
			this.context = context;
			this.appId = appId;
			this.service = service;
			//initTransportBroker(context, appId);
		}

		public void startConnection(){
			synchronized(this){
				connected = false;
				if(broker!=null){
					try{
						broker.start();
					}catch(Exception e){
						handleTransportError("Error starting transport", e);
					}
				}else{
					queueStart = true;
				}
			}
		}

		@SuppressLint("NewApi")
		public synchronized void cancel(){
				if(broker!=null){
					broker.stop();
					broker = null;
				}
				connected = false;
				if(threadLooper !=null){
					if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2){
						threadLooper.quitSafely();
					}else{
						threadLooper.quit();
					}
					threadLooper = null;
				}	
				//this.interrupt();

		}

		public void onHardwareConnected(TransportType type){
			if(broker!=null){
				broker.onHardwareConnected(type);
			}else{
				queueStart = true;
			}
		}

		public void sendPacket(SdlPacket packet){
			broker.sendPacketToRouterService(packet);
		}

		public void requestNewSession(){
			if(broker!=null){
				broker.requestNewSession();
			}
		}
		public void removeSession(long sessionId){
			if(broker!=null){
				broker.removeSession(sessionId);
			}
		}
		@Override
		public void run() {
			Looper.prepare();
			
			if(broker==null){
				synchronized(this){
					initTransportBroker();
					if(queueStart){
						try{
							broker.start();
						}catch(Exception e){
							handleTransportError("Error starting transport", e);
						}
					}
					this.notify();
				}
			}
			threadLooper = Looper.myLooper();
			Looper.loop();
			
		}
		
		public void initTransportBroker(){

			broker = new TransportBroker(context, appId, service){
				
				@Override
				public boolean onHardwareConnected(TransportType type) {
					if(super.onHardwareConnected(type)){
						DebugTool.logInfo(TAG, "On transport connected...");
						if(!connected){
							connected = true;
							handleTransportConnected();
						}//else{Log.d(TAG, "Already connected");}
						return true;
					}else{
						try{
							this.start();
						}catch(Exception e){
							handleTransportError("Error starting transport", e);
						}
					}
					return false;
				}

				@Override
				public void onHardwareDisconnected(TransportRecord transportRecord, List<TransportRecord> connected) {
					onHardwareDisconnected(TransportType.BLUETOOTH);
				}
				@Override
				public void onHardwareDisconnected(TransportType type) {
					super.onHardwareDisconnected(type);
					if(connected){
						DebugTool.logInfo(TAG, "Handling disconnect");
						connected = false;
						SdlConnection.enableLegacyMode(isLegacyModeEnabled(), TransportType.BLUETOOTH);
						if(isLegacyModeEnabled()){
							DebugTool.logInfo(TAG, "Handle transport disconnect, legacy mode enabled");
							this.stop();
							isDisconnecting = true;
							//handleTransportDisconnected("");
							handleTransportError("",null); //This seems wrong, but it works
						}else{
							DebugTool.logInfo(TAG, "Handle transport Error");
							isDisconnecting = true;
							handleTransportError("",null); //This seems wrong, but it works
						}
					}
				}

				@Override
				public void onLegacyModeEnabled() {
					super.onLegacyModeEnabled();
					SdlConnection.enableLegacyMode(isLegacyModeEnabled(), TransportType.BLUETOOTH);
					if(isLegacyModeEnabled()){
						DebugTool.logInfo(TAG, "Handle on legacy mode enabled");
						this.stop();
						isDisconnecting = true;
						//handleTransportDisconnected("");
						handleTransportError("",null); //This seems wrong, but it works
					}
				}

				@Override
				public void onPacketReceived(Parcelable packet) {
					if(packet!=null){
						SdlPacket sdlPacket = (SdlPacket)packet;
						handleReceivedPacket(sdlPacket);
					}
				}
			}; 
		}

	}
}
