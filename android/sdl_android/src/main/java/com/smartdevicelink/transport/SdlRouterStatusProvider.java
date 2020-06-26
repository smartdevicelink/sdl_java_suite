/*
 * Copyright (c) 2019, Livio, Inc.
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

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.smartdevicelink.util.AndroidTools;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;

import static com.smartdevicelink.transport.TransportConstants.FOREGROUND_EXTRA;

public class SdlRouterStatusProvider {

	private static final String TAG = "SdlRouterStateProvider";
		
	private Context context = null;
	private boolean isBound = false;
	ConnectedStatusCallback cb = null;
	Messenger routerServiceMessenger = null;
	private ComponentName routerService = null;
	private int flags = 0;

	final Messenger clientMessenger; 
	
	private ServiceConnection routerConnection= new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
			DebugTool.logInfo(TAG, "Bound to service " + className.toString());
			routerServiceMessenger = new Messenger(service);
			isBound = true;
			//So we just established our connection
			//Register with router service
			Message msg = Message.obtain();
			msg.what = TransportConstants.ROUTER_STATUS_CONNECTED_STATE_REQUEST;
			msg.arg1 = flags;
			msg.replyTo = clientMessenger;
			try {
				routerServiceMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
				if(cb!=null){
					cb.onConnectionStatusUpdate(false, routerService, context);
				}
			}			
		}

		public void onServiceDisconnected(ComponentName className) {
			DebugTool.logInfo(TAG, "UN-Bound from service " + className.getClassName());
			routerServiceMessenger = null;
			isBound = false;
		}
	};

	public SdlRouterStatusProvider(Context context, ComponentName service, ConnectedStatusCallback callback){
		if(context == null || service == null || callback == null){
			throw new IllegalStateException("Supplied params are not correct. Context == null? "+ (context==null) + " ComponentName == null? " + (service == null) + " ConnectedStatusListener == null? " + callback);
		}
		this.context = context;
		this.routerService = service;
		this.cb = callback;
		this.clientMessenger = new Messenger(new ClientHandler(this));

	}
	public void setFlags(int flags){
		this.flags = flags;
	}
	public void checkIsConnected(){
		if(!AndroidTools.isServiceExported(context,routerService) || !bindToService()){
			//We are unable to bind to service
			cb.onConnectionStatusUpdate(false, routerService, context);
			unBindFromService();
		}
	}
	
	public void cancel(){
		if(isBound){
			unBindFromService();
		}
	}

	private boolean bindToService(){
		if(isBound){
			return true;
		}
		if(clientMessenger == null){
			return false;
		}
		Intent bindingIntent = new Intent();
		bindingIntent.setClassName(this.routerService.getPackageName(), this.routerService.getClassName());//This sets an explicit intent
		//Quickly make sure it's just up and running
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
			context.startService(bindingIntent);
		}else {
			bindingIntent.putExtra(FOREGROUND_EXTRA, true);
			SdlBroadcastReceiver.setForegroundExceptionHandler(); //Prevent ANR in case the OS takes too long to start the service
			context.startForegroundService(bindingIntent);

		}
		bindingIntent.setAction( TransportConstants.BIND_REQUEST_TYPE_STATUS);
		return context.bindService(bindingIntent, routerConnection, Context.BIND_AUTO_CREATE);
	}

	private void unBindFromService(){
		try{
			if(context!=null && routerConnection!=null){
				context.unbindService(routerConnection);
			}else{
				DebugTool.logWarning("Unable to unbind from router service, context was null");
			}
			
		}catch(IllegalArgumentException e){
			//This is ok
		}
	}
	
	private void handleRouterStatusConnectedResponse(int connectedStatus){
		if(cb!=null){
			  cb.onConnectionStatusUpdate(connectedStatus == 1, routerService,context);
		  }
		  unBindFromService();
		  routerServiceMessenger =null;
	}
	
	static class ClientHandler extends Handler {
		final WeakReference<SdlRouterStatusProvider> provider;

		 public ClientHandler(SdlRouterStatusProvider provider){
			 super(Looper.getMainLooper());
			 this.provider = new WeakReference<SdlRouterStatusProvider>(provider);
		 }
		 
    	@Override
        public void handleMessage(Message msg) {
    		if(provider.get()==null){
    			return; 
    		}
    		  switch (msg.what) {
    		  case TransportConstants.ROUTER_STATUS_CONNECTED_STATE_RESPONSE:
    			  provider.get().handleRouterStatusConnectedResponse(msg.arg1);
    			  break;
    		  default:
    			  break;
    		  }
    	}
	};
	
	public interface ConnectedStatusCallback{
		public void onConnectionStatusUpdate(boolean connected, ComponentName service, Context context);
	}
	
}
