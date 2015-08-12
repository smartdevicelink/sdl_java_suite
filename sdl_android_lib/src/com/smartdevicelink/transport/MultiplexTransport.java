package com.smartdevicelink.transport;

import android.content.Context;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;

import com.smartdevicelink.SdlConnection.SdlConnection;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;

public class MultiplexTransport extends SdlTransport{
	private final static String TAG = "Multiplex Transport";
	private String sComment = "I'm_a_little_teapot";
	
	TransportBrokerThread brokerThread;
	
	public MultiplexTransport(MultiplexTransportConfig transportConfig, final ITransportListener transportListener){
		super(transportListener);
		brokerThread = new TransportBrokerThread(transportConfig.context, transportConfig.appId);
		brokerThread.start();

	}

	public void forceHardwareConnectEvent(TransportType type){
		brokerThread.onHardwareConnected(type);

	}
	
	public void requestNewSession(){
		if(brokerThread!=null){
			brokerThread.requestNewSession();
		}
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
	protected boolean sendBytesOverTransport(byte[] msgBytes, int offset,
			int length) {
		
		brokerThread.sendPacket(msgBytes,offset,length);
		return true; //Sure why not.
	}

	@Override
	public void openConnection() throws SdlException {
		Log.d(TAG, "Open connection");
		brokerThread.startConnection();
		
	}

	@Override
	public void disconnect() {
		Log.d(TAG, "Close connection");
		brokerThread.cancel();
		brokerThread = null;
		handleTransportDisconnected(TransportType.MULTIPLEX.name());
		
	}
	
	

	/**
	 * This thread will handle the broker transaction with the router service.
	 *
	 */
	private class TransportBrokerThread extends Thread{
		private boolean connected = false; //This helps clear up double on hardware connects
		TransportBroker broker;

		public TransportBrokerThread(Context context, String appId){
			initTransportBroker(context, appId);
		}

		public void startConnection(){
			connected = false;
			broker.start();
		}

		public void cancel(){
			broker.stop();
			broker = null;
			connected = false;
			this.interrupt();

		}

		public void onHardwareConnected(TransportType type){
			broker.onHardwareConnected(type);
		}

		public void sendPacket(byte[] msgBytes,int offset,int length){
			broker.sendPacketToRouterService(msgBytes,offset,length);
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
			Looper.loop();
		}

		private void initTransportBroker(final Context context, final String appId){

			broker = new TransportBroker(context, appId){

				@Override
				public boolean onHardwareConnected(TransportType type) {
					if(super.onHardwareConnected(type)){
						Log.d(TAG, "On transport connected...");
						if(!connected){
							connected = true;
							Log.d(TAG, "Handling transport connected");
							handleTransportConnected();
						}else{Log.d(TAG, "Already connected");}
						return true;
					}else{
						this.start();
					}
					return false;
				}

				@Override
				public void onHardwareDisconnected(TransportType type) {
					super.onHardwareDisconnected(type);
					if(connected){
						Log.d(TAG, "Handling disconnect");
						connected = false;
						SdlConnection.enableLegacyMode(isLegacyModeEnabled(), TransportType.BLUETOOTH);
						if(isLegacyModeEnabled()){
							Log.d(TAG, "Handle transport disconnect, legacy mode enabled");
							this.stop();
							handleTransportDisconnected("");
						}else{
							Log.d(TAG, "Handle transport Error");
							handleTransportError("",null); //This seems wrong, but it works
						}
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
