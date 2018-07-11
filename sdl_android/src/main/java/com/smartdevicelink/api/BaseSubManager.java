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
	public enum ManagerState {
		SETTING_UP,
		READY,
		SHUTDOWN,
		;
	}

	protected ISdl internalInterface;
	protected ManagerState state;

	public BaseSubManager(@NonNull ISdl internalInterface){
		this.internalInterface = internalInterface;
		this.state = ManagerState.SETTING_UP;
	}

	/**
	 * <p>Called when manager is being torn down</p>
	 */
	public void dispose(){
		this.state = ManagerState.SHUTDOWN;
	}

	protected void transitionToState(ManagerState state) {
		this.state = state;
	}

	protected ManagerState getState() {
		return state;
	}
}
