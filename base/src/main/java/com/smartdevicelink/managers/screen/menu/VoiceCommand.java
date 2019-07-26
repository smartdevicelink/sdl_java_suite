/*
 * Copyright (c) 2019 Livio, Inc.
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

package com.smartdevicelink.managers.screen.menu;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class VoiceCommand {

	/**
	 * The strings the user can say to activate this voice command
	 */
	private List<String> voiceCommands;

	/**
	 * The listener that will be called when the command is activated
	 */
	private VoiceCommandSelectionListener voiceCommandSelectionListener;

	/**
	 * Used Internally to identify the command
	 */
	private int commandId;

	// CONSTRUCTOR(S)

	/**
	 * Constructor that sets all parameters for this class
	 * <strong>NOTE: While it is possible to pass in null for the listener, It is the easiest way to know when it was triggered.</strong>
	 * @param voiceCommands The strings the user can say to activate this voice command
	 * @param voiceCommandSelectionListener The listener that will be called when the command is activated
	 */
	public VoiceCommand(@NonNull List<String> voiceCommands, @Nullable VoiceCommandSelectionListener voiceCommandSelectionListener){
		setVoiceCommands(voiceCommands);
		setVoiceCommandSelectionListener(voiceCommandSelectionListener);
	}

	// SETTERS / GETTERS

	/**
	 * The strings the user can say to activate this voice command
	 * @param voiceCommands - the list of commands to send to the head unit
	 */
	public void setVoiceCommands(@NonNull List<String> voiceCommands) {
		this.voiceCommands = voiceCommands;
	}

	/**
	 * The strings the user can say to activate this voice command
	 * @return the List of voice commands
	 */
	public List<String> getVoiceCommands() {
		return voiceCommands;
	}

	/**
	 * The listener that will be called when the command is activated
	 * @param voiceCommandSelectionListener - the listener for this object
	 */
	public void setVoiceCommandSelectionListener(VoiceCommandSelectionListener voiceCommandSelectionListener) {
		this.voiceCommandSelectionListener = voiceCommandSelectionListener;
	}

	/**
	 * The listener that will be called when the command is activated
	 * @return voiceCommandSelectionListener - the listener for this object
	 */
	public VoiceCommandSelectionListener getVoiceCommandSelectionListener() {
		return voiceCommandSelectionListener;
	}

	/**
	 * set the command' ID
	 * <strong>NOTE: PLEASE DO NOT SET. This is used internally</strong>
	 * @param commandId the id to identify the command
	 */
	void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	/**
	 * the id used to identify the command
	 * @return the id
	 */
	int getCommandId() {
		return commandId;
	}
}
