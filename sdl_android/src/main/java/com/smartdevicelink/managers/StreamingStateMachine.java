package com.smartdevicelink.managers;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StreamingStateMachine {
	@IntDef({NONE, READY, STARTED, STOPPED, ERROR})
	@Retention(RetentionPolicy.SOURCE)
	public @interface StreamingState {}
	public static final int NONE = 0x00, READY = 0x30, STARTED = 0x60, STOPPED = 0x90, ERROR = 0xC0;

	private @StreamingState int state = NONE;
	private final Object STATE_LOCK = new Object();

	public StreamingStateMachine(){}

	public void transitionToState(int state) {
		if(state != NONE && state != READY && state != STARTED
				&& state != STOPPED && state != ERROR) {
			return;
		}
		synchronized (STATE_LOCK) {
			if(isValidTransition(this.state, state)){
				this.state = state;
			}
		}
	}

	public @StreamingState int getState() {
		synchronized (STATE_LOCK) {
			return state;
		}
	}

	private boolean isValidTransition(int prev_state, int next_state){
		if(prev_state == next_state){
			return false;
		}
		switch (prev_state){
			case NONE:
				if((next_state == READY) || (next_state == ERROR)){
					return true;
				}
				break;
			case READY:
				if((next_state == STARTED) || (next_state == ERROR)){
					return true;
				}
				break;
			case STARTED:
				if((next_state == STOPPED) || (next_state == ERROR)){
					return true;
				}
				break;
			case STOPPED:
				if((next_state == STARTED) || (next_state == NONE)){
					return true;
				}
				break;
			case ERROR:
				if(next_state == NONE){
					return true;
				}
				break;
			default:
				break;
		}
		return false;
	}
}