package com.smartdevicelink.managers;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.transport.utl.TransportRecord;

import java.util.List;

/**
 * <strong>BaseSubManager</strong> <br>
 *
 * Note: This class is extended by SubManagers <br>
 *
 * It is broken down to these areas: <br>
 *
 * 1. <br>
 */
public abstract class BaseSubManager {

	// states - if this gets more complicated we can move elsewhere
	private int state;
	private final Object STATE_LOCK = new Object();
	public static final int SETTING_UP = 0x00, READY = 0x30, LIMITED = 0x50, SHUTDOWN = 0x80, ERROR = 0xC0;
	protected final ISdl internalInterface;
	private CompletionListener completionListener;

	public BaseSubManager(@NonNull ISdl internalInterface){
		this.internalInterface = internalInterface;
		transitionToState(SETTING_UP);
	}

	/**
	 * Starts up a BaseSubManager, and calls provided callback once BaseSubManager is done setting up or failed setup.
	 * @param listener CompletionListener that is called once the BaseSubManager's state is READY, LIMITED, or ERROR
	 */
	public void start(CompletionListener listener){
		this.completionListener = listener;
		int state = getState();
		if((state == READY || state == LIMITED || state == ERROR) && completionListener != null){
			completionListener.onComplete(state == READY || state == LIMITED);
			completionListener = null;
		}
	}

	/**
	 * <p>Called when manager is being torn down</p>
	 */
	public void dispose(){
		transitionToState(SHUTDOWN);
	}

	protected void transitionToState(int state) {
		synchronized (STATE_LOCK) {
			this.state = state;
		}
		if((state == READY || state == LIMITED || state == ERROR) && completionListener != null ){
			completionListener.onComplete(state == READY || state == LIMITED);
			completionListener = null;
		}
	}

	public int getState() {
		synchronized (STATE_LOCK) {
			return state;
		}
	}

	//This allows the method to not be exposed to developers
	protected void handleTransportUpdated(List<TransportRecord> connectedTransports, boolean audioStreamTransportAvail, boolean videoStreamTransportAvail){
		this.onTransportUpdate(connectedTransports,audioStreamTransportAvail,videoStreamTransportAvail);
	}

	/**
	 * Transport status has been updated
	 * @param connectedTransports currently connected transports
	 * @param audioStreamTransportAvail if there is a transport that could be used to carry the
	 *                                     audio service
	 * @param videoStreamTransportAvail if there is a transport that could be used to carry the
	 *                                     video service
	 */
	protected void onTransportUpdate(List<TransportRecord> connectedTransports, boolean audioStreamTransportAvail, boolean videoStreamTransportAvail){}
}
