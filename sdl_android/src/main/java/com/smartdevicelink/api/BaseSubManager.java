package com.smartdevicelink.api;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.interfaces.ISdl;

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
	public static final int SETTING_UP = 0x00, READY = 0x30, SHUTDOWN = 0x60;
	protected final ISdl internalInterface;
	private CompletionListener completionListener;

	public BaseSubManager(@NonNull ISdl internalInterface){
		this.internalInterface = internalInterface;
		transitionToState(SETTING_UP);
	}

	/**
	 * Called to start
	 * @param listener
	 */
	public void start(CompletionListener listener){
		this.completionListener = listener;
		if(state == READY && completionListener != null){
			completionListener.onComplete(true);
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
		if(state == READY && completionListener != null){
			completionListener.onComplete(true);
			completionListener = null;
		}else if(state == SHUTDOWN && completionListener != null){
			completionListener.onComplete(false);
			completionListener = null;
		}
	}

	public int getState() {
		synchronized (STATE_LOCK) {
			return state;
		}
	}
}
