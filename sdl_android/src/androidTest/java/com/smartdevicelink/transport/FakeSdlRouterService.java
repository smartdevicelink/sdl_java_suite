package com.smartdevicelink.transport;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;

/**
 * Created by mat on 7/14/17.
 */

public class FakeSdlRouterService extends Service {

	// Handler that receives messages from the thread
	final Messenger routerMessenger = new Messenger(new RouterHandler(this));
	private ExecutorService packetExecuter = null;

	/**
	 * Handler of incoming messages from clients.
	 */
	class RouterHandler extends Handler {
		final WeakReference<FakeSdlRouterService> provider;

		public RouterHandler(FakeSdlRouterService provider){
			this.provider = new WeakReference<FakeSdlRouterService>(provider);
		}

		@Override
		public void handleMessage(Message msg) {
			if(this.provider.get() == null){
				return;
			}
//			final Bundle receivedBundle = msg.getData();
//			Bundle returnBundle;
//			final FakeSdlRouterService service = this.provider.get();
//
//			switch (msg.what) {
//				case TransportConstants.ROUTER_REQUEST_BT_CLIENT_CONNECT:
//					//**************** We don't break here so we can let the app register as well
//				case TransportConstants.ROUTER_REGISTER_CLIENT: //msg.arg1 is appId
//					//pingClients();
//					Message message = Message.obtain();
//					message.what = TransportConstants.ROUTER_REGISTER_CLIENT_RESPONSE;
//					message.arg1 = TransportConstants.REGISTRATION_RESPONSE_SUCESS;
//					String appId = receivedBundle.getString(TransportConstants.APP_ID_EXTRA_STRING);
//					if(appId == null){
//						appId = "" + receivedBundle.getLong(TransportConstants.APP_ID_EXTRA, -1);
//					}
//					if(appId == null || appId.length()<=0 || msg.replyTo == null){
//						if(msg.replyTo!=null){
//							message.arg1 = TransportConstants.REGISTRATION_RESPONSE_DENIED_APP_ID_NOT_INCLUDED;
//							try {
//								msg.replyTo.send(message);
//							} catch (RemoteException e) {
//								e.printStackTrace();
//							}
//						}
//						break;
//					}
//					break;
//				default:
//					super.handleMessage(msg);
//			}
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return this.routerMessenger.getBinder();
	}

	@Override
	public void onDestroy() {

	}
}



