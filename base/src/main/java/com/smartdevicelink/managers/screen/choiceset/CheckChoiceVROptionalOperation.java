/*
 * Copyright (c)  2019 Livio, Inc.
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
 *
 * Created by brettywhite on 6/12/19 1:52 PM
 *
 */

package com.smartdevicelink.managers.screen.choiceset;

import com.livio.taskmaster.Task;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.Collections;

class CheckChoiceVROptionalOperation extends Task {

	private CheckChoiceVROptionalInterface checkChoiceVROptionalInterface;
	private WeakReference<ISdl> internalInterface;
	private boolean isVROptional;

	CheckChoiceVROptionalOperation(ISdl internalInterface, CheckChoiceVROptionalInterface checkChoiceVROptionalInterface){
		super("CheckChoiceVROptionalOperation");
		this.internalInterface = new WeakReference<>(internalInterface);
		this.checkChoiceVROptionalInterface = checkChoiceVROptionalInterface;
	}

	@Override
	public void onExecute() {
		DebugTool.logInfo(null, "Choice Operation: Executing check vr optional operation");
		sendTestChoiceNoVR();
	}

	/**
	 * As VR used to me mandatory, we first will sent a choice cell WITHOUT VR. If this succeeds, we are good,
	 * if not check again without.
	 */
	private void sendTestChoiceNoVR() {
		CreateInteractionChoiceSet cics = testCellWithVR(false);
		cics.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if (response.getSuccess()) {
					// The request was successful, now send the SDLPerformInteraction RPC
					DebugTool.logInfo(null, "Connected head unit supports choice cells without voice commands. " +
							"Cells without voice will be sent without voice from now on (no placeholder voice).");
					isVROptional = true;
					deleteTestChoiceSet();
				}else{
					DebugTool.logWarning("Head unit doesn't support choices with no VR.");
					sendTestChoiceWithVR();
				}
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info){
				DebugTool.logWarning("Head unit doesn't support choices with no VR. Error: " + info + " resultCode: " + resultCode);
				sendTestChoiceWithVR();
			}
		});

		if (internalInterface.get() != null) {
			internalInterface.get().sendRPC(cics);
		}
	}

	/**
	 * The initial request failed. Try again without VR, if this fails, return and put CSM in error state
	 */
	private void sendTestChoiceWithVR(){
		CreateInteractionChoiceSet cics = testCellWithVR(true);
		cics.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if (response.getSuccess()) {
					// The request was successful, now send the SDLPerformInteraction RPC
					DebugTool.logWarning("Connected head unit does not support choice cells without voice commands. " +
							"Cells without voice will be sent with placeholder voices from now on.");
					isVROptional = false;
					deleteTestChoiceSet();
				}else{
					DebugTool.logError("Connected head unit has rejected all choice cells, choice manager disabled. Error: " + response.getInfo());
					isVROptional = false;
					if (checkChoiceVROptionalInterface != null){
						checkChoiceVROptionalInterface.onError(response.getInfo());
					}

					CheckChoiceVROptionalOperation.super.onFinished();
				}
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info){
				DebugTool.logError("There was an error in the check choice vr optional operation. Send test choice with VR failed. Error: " + info + " resultCode: " + resultCode);
				isVROptional = false;
				if (checkChoiceVROptionalInterface != null){
					checkChoiceVROptionalInterface.onError(info);
				}

				CheckChoiceVROptionalOperation.super.onFinished();
			}
		});

		if (internalInterface.get() != null) {
			internalInterface.get().sendRPC(cics);
		}
	}

	private void deleteTestChoiceSet(){
		DeleteInteractionChoiceSet delete = createDeleteInteractionChoiceSet();
		delete.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if (response.getSuccess() != null){
					DebugTool.logInfo(null, "Delete choice test set: "+ response.getSuccess());
				}

				if (checkChoiceVROptionalInterface != null){
					checkChoiceVROptionalInterface.onCheckChoiceVROperationComplete(isVROptional);
				}

				CheckChoiceVROptionalOperation.super.onFinished();
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info){
				DebugTool.logError("There was an error presenting the keyboard. Finishing operation - choice set manager - . Error: " + info + " resultCode: " + resultCode);
				if (checkChoiceVROptionalInterface != null){
					checkChoiceVROptionalInterface.onError(info);
				}

				CheckChoiceVROptionalOperation.super.onFinished();
			}
		});
		if (internalInterface.get() != null){
			internalInterface.get().sendRPC(delete);
		}
	}

	DeleteInteractionChoiceSet createDeleteInteractionChoiceSet(){
		return new DeleteInteractionChoiceSet(0);
	}

	CreateInteractionChoiceSet testCellWithVR(boolean hasVR){
		Choice choice = new Choice(0, "Test Cell");
		choice.setVrCommands((hasVR ? Collections.singletonList("Test VR") : null));
		choice.setIgnoreAddingVRItems(true);
		return new CreateInteractionChoiceSet(0, Collections.singletonList(choice));
	}
}
