/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
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
package com.smartdevicelink.managers;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StreamingStateMachine {
	@IntDef({NONE, READY, STARTED, STOPPED, ERROR, PAUSED})
	@Retention(RetentionPolicy.SOURCE)
	public @interface StreamingState {}
	public static final int NONE = 0x00, READY = 0x30, STARTED = 0x60, STOPPED = 0x90, ERROR = 0xC0, PAUSED = 0xF0;

	private @StreamingState int state = NONE;
	private final Object STATE_LOCK = new Object();

	public StreamingStateMachine(){}

	public void transitionToState(int state) {
		if(state != NONE && state != READY && state != STARTED
				&& state != PAUSED && state != STOPPED && state != ERROR) {
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
				if((next_state == STOPPED) || (next_state == ERROR) || (next_state == PAUSED)){
					return true;
				}
				break;
			case PAUSED:
				if((next_state == STARTED) || (next_state == STOPPED) || (next_state == ERROR)){
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