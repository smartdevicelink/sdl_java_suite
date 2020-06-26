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
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.CancelInteraction;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

class PresentChoiceSetOperation extends Task {

	private WeakReference<ISdl> internalInterface;
	private ChoiceSet choiceSet;
	private Integer cancelID;
	private InteractionMode presentationMode;
	private KeyboardProperties originalKeyboardProperties, keyboardProperties;
	private ChoiceCell selectedCell;
	private TriggerSource selectedTriggerSource;
	private boolean updatedKeyboardProperties;
	private OnRPCNotificationListener keyboardRPCListener;
	private ChoiceSetSelectionListener choiceSetSelectionListener;
	Integer selectedCellRow;
	KeyboardListener keyboardListener;
	SdlMsgVersion sdlMsgVersion;

	PresentChoiceSetOperation(ISdl internalInterface, ChoiceSet choiceSet, InteractionMode mode,
									 KeyboardProperties originalKeyboardProperties, KeyboardListener keyboardListener, ChoiceSetSelectionListener choiceSetSelectionListener, Integer cancelID){
		super("PresentChoiceSetOperation");
		this.internalInterface = new WeakReference<>(internalInterface);
		this.keyboardListener = keyboardListener;
		this.choiceSet = choiceSet;
		this.choiceSet.canceledListener = new ChoiceSetCanceledListener() {
			@Override
			public void onChoiceSetCanceled() {
				cancelInteraction();
			}
		};
		this.presentationMode = mode;
		this.cancelID = cancelID;
		this.originalKeyboardProperties = originalKeyboardProperties;
		this.keyboardProperties = originalKeyboardProperties;
		this.selectedCellRow = null;
		this.choiceSetSelectionListener = choiceSetSelectionListener;
		this.sdlMsgVersion = internalInterface.getSdlMsgVersion();
	}

	@Override
	public void onExecute() {
		DebugTool.logInfo(null, "Choice Operation: Executing present choice set operation");
		addListeners();
		start();
	}

	private void start(){
		if (getState() == Task.CANCELED) {
			finishOperation();
			return;
		}

		// Check if we're using a keyboard (searchable) choice set and setup keyboard properties if we need to
		if (keyboardListener != null && choiceSet.getCustomKeyboardConfiguration() != null){
			keyboardProperties = choiceSet.getCustomKeyboardConfiguration();
			updatedKeyboardProperties = true;
		}

		updateKeyboardProperties(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				if (getState() == Task.CANCELED) {
					finishOperation();
					return;
				}
				presentChoiceSet();
			}
		});
	}

	// SENDING REQUESTS

	private void updateKeyboardProperties(final CompletionListener listener){
		if (keyboardProperties == null){
			if (listener != null){
				listener.onComplete(false);
			}
			return;
		}
		SetGlobalProperties setGlobalProperties = new SetGlobalProperties();
		setGlobalProperties.setKeyboardProperties(keyboardProperties);
		setGlobalProperties.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {

				if (!response.getSuccess()){
					if (listener != null){
						listener.onComplete(false);
					}
					DebugTool.logError("Error Setting keyboard properties in present choice set operation");
					return;
				}

				updatedKeyboardProperties = true;

				if (listener != null){
					listener.onComplete(true);
				}
				DebugTool.logInfo(null, "Success Setting keyboard properties in present choice set operation");
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {
				if (listener != null){
					listener.onComplete(false);
				}
				DebugTool.logError("Error Setting keyboard properties in present keyboard operation - choice manager - " + info);
			}
		});
		if (internalInterface.get() != null){
			internalInterface.get().sendRPC(setGlobalProperties);
		} else {
			DebugTool.logError("Internal interface null - present choice set op - choice");
		}
	}

	private void presentChoiceSet() {
		PerformInteraction pi = getPerformInteraction();
		pi.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if (!response.getSuccess()){
					DebugTool.logError("Presenting Choice set failed: "+ response.getInfo());

					if (choiceSetSelectionListener != null){
						choiceSetSelectionListener.onError(response.getInfo());
					}
					finishOperation();
				}

				PerformInteractionResponse performInteractionResponse = (PerformInteractionResponse) response;
				setSelectedCellWithId(performInteractionResponse.getChoiceID());
				selectedTriggerSource = performInteractionResponse.getTriggerSource();

				if (choiceSetSelectionListener != null && selectedCell != null && selectedTriggerSource != null && selectedCellRow != null){
					choiceSetSelectionListener.onChoiceSelected(selectedCell, selectedTriggerSource, selectedCellRow);
				}

				finishOperation();
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {
				DebugTool.logError("Presenting Choice set failed: " + resultCode + ", " + info);

				if (choiceSetSelectionListener != null){
					choiceSetSelectionListener.onError(resultCode + ", " + info);
				}
				finishOperation();
			}
		});
		if (internalInterface.get() != null){
			internalInterface.get().sendRPC(pi);
		}else {
			DebugTool.logError("Internal Interface null when presenting choice set in operation");
		}
	}

	void finishOperation() {
		if (updatedKeyboardProperties) {
			// We need to reset the keyboard properties
			SetGlobalProperties setGlobalProperties = new SetGlobalProperties();
			setGlobalProperties.setKeyboardProperties(originalKeyboardProperties);
			setGlobalProperties.setOnRPCResponseListener(new OnRPCResponseListener() {
				@Override
				public void onResponse(int correlationId, RPCResponse response) {
					updatedKeyboardProperties = false;
					DebugTool.logInfo(null, "Successfully reset choice keyboard properties to original config");
					PresentChoiceSetOperation.super.onFinished();
				}

				@Override
				public void onError(int correlationId, Result resultCode, String info) {
					DebugTool.logError("Failed to reset choice keyboard properties to original config " + resultCode + ", " + info);
					PresentChoiceSetOperation.super.onFinished();
				}
			});

			if (internalInterface.get() != null) {
				internalInterface.get().sendRPC(setGlobalProperties);
				internalInterface.get().removeOnRPCNotificationListener(FunctionID.ON_KEYBOARD_INPUT, keyboardRPCListener);
			} else {
				DebugTool.logError("Internal Interface null when finishing choice keyboard reset");
			}
		} else {
			PresentChoiceSetOperation.super.onFinished();
		}
	}

	/*
	* Cancels the choice set. If the choice set has not yet been sent to Core, it will not be sent. If the choice set is already presented on Core, the choice set will be dismissed using the `CancelInteraction` RPC.
	*/
	private void cancelInteraction() {
		if ((getState() == Task.FINISHED)) {
			DebugTool.logInfo(null, "This operation has already finished so it can not be canceled.");
			return;
		} else if (getState() == Task.CANCELED) {
			DebugTool.logInfo(null, "This operation has already been canceled. It will be finished at some point during the operation.");
			return;
		} else if ((getState() == Task.IN_PROGRESS)) {
			if (sdlMsgVersion.getMajorVersion() < 6){
				DebugTool.logWarning("Canceling a presented choice set is not supported on this head unit");
				return;
			}

			DebugTool.logInfo(null, "Canceling the presented choice set interaction.");

			CancelInteraction cancelInteraction = new CancelInteraction(FunctionID.PERFORM_INTERACTION.getId(), cancelID);
			cancelInteraction.setOnRPCResponseListener(new OnRPCResponseListener() {
				@Override
				public void onResponse(int correlationId, RPCResponse response) {
					DebugTool.logInfo(null, "Canceled the presented choice set " + ((response.getResultCode() == Result.SUCCESS) ? "successfully" : "unsuccessfully"));
				}

				@Override
				public void onError(int correlationId, Result resultCode, String info){
					DebugTool.logError("Error canceling the presented choice set " + resultCode + " " + info);
				}
			});

			if (internalInterface.get() != null){
				internalInterface.get().sendRPC(cancelInteraction);
			} else {
				DebugTool.logError("Internal interface null - could not send cancel interaction for choice set");
			}
		} else {
			DebugTool.logInfo(null, "Canceling a choice set that has not yet been sent to Core");
			this.cancelTask();
		}
	}

	// GETTERS

	PerformInteraction getPerformInteraction() {
		PerformInteraction pi = new PerformInteraction(choiceSet.getTitle(), presentationMode, getChoiceIds());
		pi.setInitialPrompt(choiceSet.getInitialPrompt());
		pi.setHelpPrompt(choiceSet.getHelpPrompt());
		pi.setTimeoutPrompt(choiceSet.getTimeoutPrompt());
		pi.setVrHelp(choiceSet.getVrHelpList());
		pi.setTimeout(choiceSet.getTimeout() * 1000);
		pi.setInteractionLayout(getLayoutMode());
		pi.setCancelID(cancelID);
		return pi;
	}

	LayoutMode getLayoutMode() {
		switch (choiceSet.getLayout()){
			case CHOICE_SET_LAYOUT_LIST:
				return keyboardListener != null ? LayoutMode.LIST_WITH_SEARCH : LayoutMode.LIST_ONLY;
			case CHOICE_SET_LAYOUT_TILES:
				return keyboardListener != null ? LayoutMode.ICON_WITH_SEARCH : LayoutMode.ICON_ONLY;
		}
		return LayoutMode.LIST_ONLY; // default
	}

	private List<Integer> getChoiceIds() {

		List<Integer> choiceIds = new ArrayList<>(choiceSet.getChoices().size());
		for (ChoiceCell cell : choiceSet.getChoices()){
			choiceIds.add(cell.getChoiceId());
		}
		return choiceIds;
	}

	// HELPERS

	void setSelectedCellWithId(Integer cellId){
		if (choiceSet.getChoices() != null && cellId != null) {
			List<ChoiceCell> cells = choiceSet.getChoices();
			for (int i = 0; i < cells.size(); i++) {
				if (cells.get(i).getChoiceId() == cellId) {
					selectedCell = cells.get(i);
					selectedCellRow = i;
					return;
				}
			}
		}
	}

	// LISTENERS

	private void addListeners(){

		keyboardRPCListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				if (getState() == Task.CANCELED) {
					finishOperation();
					return;
				}

				if (keyboardListener == null){
					DebugTool.logError("Received Keyboard Input But Listener is null");
					return;
				}

				OnKeyboardInput onKeyboard = (OnKeyboardInput) notification;
				keyboardListener.onKeyboardDidSendEvent(onKeyboard.getEvent(), onKeyboard.getData());

				if (onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_VOICE) || onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_SUBMITTED)){
					// Submit Voice or Text
					keyboardListener.onUserDidSubmitInput(onKeyboard.getData(), onKeyboard.getEvent());
				} else if (onKeyboard.getEvent().equals(KeyboardEvent.KEYPRESS)){
					// Notify of Keypress
					keyboardListener.updateAutocompleteWithInput(onKeyboard.getData(), new KeyboardAutocompleteCompletionListener() {
						@Override
						public void onUpdatedAutoCompleteText(String updatedAutoCompleteText) {
							keyboardProperties.setAutoCompleteText(updatedAutoCompleteText);
							updateKeyboardProperties(null);
						}

						@Override
						public void onUpdatedAutoCompleteList(List<String> updatedAutoCompleteList) {
							keyboardProperties.setAutoCompleteList(updatedAutoCompleteList != null ? updatedAutoCompleteList : new ArrayList<String>());
							keyboardProperties.setAutoCompleteText(updatedAutoCompleteList != null && !updatedAutoCompleteList.isEmpty() ? updatedAutoCompleteList.get(0) : null);
							updateKeyboardProperties(null);
						}
					});

					keyboardListener.updateCharacterSetWithInput(onKeyboard.getData(), new KeyboardCharacterSetCompletionListener() {
						@Override
						public void onUpdatedCharacterSet(List<String> updatedCharacterSet) {
							keyboardProperties.setLimitedCharacterList(updatedCharacterSet);
							updateKeyboardProperties(null);
						}
					});
				} else if (onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_ABORTED) || onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_CANCELLED)){
					// Notify of abort / Cancellation
					keyboardListener.onKeyboardDidAbortWithReason(onKeyboard.getEvent());
				}
			}
		};

		if (internalInterface.get() != null) {
			internalInterface.get().addOnRPCNotificationListener(FunctionID.ON_KEYBOARD_INPUT, keyboardRPCListener);
		} else {
			DebugTool.logError("Present Choice Set Keyboard Listener Not Added");
		}
	}
}