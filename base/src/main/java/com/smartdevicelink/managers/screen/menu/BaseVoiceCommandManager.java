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

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.screen.menu.cells.VoiceCommand;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.List;

abstract class BaseVoiceCommandManager extends BaseSubManager {

	private List<VoiceCommand> voiceCommands;
	private List<VoiceCommand> oldVoiceCommands;

	private List<RPCRequest> inProgressUpdate;

	private int commandId;
	private int lastVoiceCommandId;
	private static final int voiceCommandIdMin = 1900000000;

	private boolean waitingOnHMIUpdate;
	private boolean hasQueuedUpdate;

	private HMILevel currentHMILevel;
	private OnRPCNotificationListener hmiListener;
	private OnRPCNotificationListener commandListener;

	// CONSTRUCTORS

	public BaseVoiceCommandManager(@NonNull ISdl internalInterface) {
		super(internalInterface);
		addListeners();

		lastVoiceCommandId = voiceCommandIdMin;
		voiceCommands = new ArrayList<>();
		oldVoiceCommands = new ArrayList<>();
	}

	// SETTERS

	public void setVoiceCommands(List<VoiceCommand> voiceCommands){

	}

	// UPDATING SYSTEM

		// update w/ listener

	// DELETING OLD MENU ITEMS

		// deleteCurrentVoiceCommands w/ listener

	// SEND NEW MENU ITEMS

		// send current voice commands w/ listener

	// DELETES

		// deleteCommandsForVoiceCommands

	// COMMANDS

		// addCommandsForVoiceCommands

	// HELPERS

		// updateIdsOnVoiceCommands

	public void stop(){

		lastVoiceCommandId = voiceCommandIdMin;
		voiceCommands = null;
		oldVoiceCommands = null;

		waitingOnHMIUpdate = false;
		currentHMILevel = null;
		inProgressUpdate = null;
		hasQueuedUpdate = false;

		// remove listeners
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_COMMAND, commandListener);

		super.dispose();
	}

	// LISTENERS

	private void addListeners(){
		
		// HMI UPDATES
		hmiListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				currentHMILevel = ((OnHMIStatus) notification).getHmiLevel();
				if (currentHMILevel == HMILevel.HMI_FULL){
					if (waitingOnHMIUpdate){
						DebugTool.logInfo( "Acquired HMI_FULL with pending update. Sending now");
						waitingOnHMIUpdate = false;
						// TODO: DO THINGS HERE
					}
				}
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

		// COMMANDS
		commandListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				OnCommand command = (OnCommand) notification;
				// TODO: STUFF HERE
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_COMMAND, commandListener);
	}
}
