package com.smartdevicelink.transport;

import android.os.Parcelable;
import android.util.Log;

import com.smartdevicelink.SdlConnection.SdlConnection;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.SdlPacket;

public class MultiplexTransport extends SdlTransport{
	private final static String TAG = "Multiplex Transport";

	private boolean connected = false; //This helps clear up double on hardware connects
	
	private String sComment = "I'm_a_little_teapot";
	
	TransportBroker broker;
	
	public MultiplexTransport(MultiplexTransportConfig transportConfig, final ITransportListener transportListener){
		super(transportListener);
		
		broker = new TransportBroker(transportConfig.context,
				transportConfig.appId){

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

	public void forceHardwareConnectEvent(TransportType type){
		broker.onHardwareConnected(type);

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
		
		broker.sendPacketToRouterService(msgBytes,offset,length);
		return true; //Sure why not.
	}

	@Override
	public void openConnection() throws SdlException {
		Log.d(TAG, "Open connection");
		connected = false; //TODO make sure this is cooooooooool
		broker.start(); //TODO idk if this is when to call this 
		
	}

	@Override
	public void disconnect() {
		Log.d(TAG, "Close connection");
		connected = false;
		broker.stop();
		broker = null;
		handleTransportDisconnected(TransportType.MULTIPLEX.name());
		
	}
	
	
}
