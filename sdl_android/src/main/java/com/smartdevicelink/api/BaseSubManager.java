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
	public int state, SETTING_UP = 0x00, READY = 0x30, SHUTDOWN = 0x60;

	protected final ISdl internalInterface;

	public BaseSubManager(@NonNull ISdl internalInterface){
		this.internalInterface = internalInterface;
		this.state = SETTING_UP;
	}

	/**
	 * <p>Called when manager is being torn down</p>
	 */
	public void dispose(){
		this.state = SHUTDOWN;
	}

	protected void transitionToState(int state) {
		this.state = state;
	}

	protected int getState() {
		return state;
	}
}
