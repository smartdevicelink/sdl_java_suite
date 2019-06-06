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

package com.smartdevicelink.managers.screen.choiceset.operations;

import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.screen.choiceset.ChoiceCell;
import com.smartdevicelink.managers.screen.choiceset.ChoiceSet;
import com.smartdevicelink.managers.screen.choiceset.KeyboardAutocompleteCompletionListener;
import com.smartdevicelink.managers.screen.choiceset.KeyboardCharacterSetCompletionListener;
import com.smartdevicelink.managers.screen.choiceset.KeyboardListener;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PresentChoiceSetOperation implements Runnable {

	private WeakReference<ISdl> internalInterface;
	private WeakReference<KeyboardListener> keyboardListener;
	private ChoiceSet choiceSet;
	private InteractionMode presentationMode;
	private KeyboardProperties originalKeyboardProperties, keyboardProperties;
	private ChoiceCell selectedCell;
	private TriggerSource selectedTriggerSource;
	private Integer selectedCellRow;
	private boolean updatedKeyboardProperties;
	private OnRPCNotificationListener keyboardRPCListener;
	private CompletionListener completionListener;

	public PresentChoiceSetOperation(ISdl internalInterface, ChoiceSet choiceSet, InteractionMode mode,
									 KeyboardProperties originalKeyboardProperties, KeyboardListener keyboardListener, CompletionListener completionListener){
		this.internalInterface = new WeakReference<>(internalInterface);
		this.keyboardListener = new WeakReference<>(keyboardListener);
		this.choiceSet = choiceSet;
		this.presentationMode = mode;
		this.originalKeyboardProperties = originalKeyboardProperties;
		this.keyboardProperties = originalKeyboardProperties;
		this.selectedCellRow = null;
		this.completionListener = completionListener;
	}

	@Override
	public void run() {
		addListeners();
		start();
	}

	private void start(){

		// Check if we're using a keyboard (searchable) choice set and setup keyboard properties if we need to
		if (keyboardListener != null && choiceSet.getCustomKeyboardConfiguration() != null){
			keyboardProperties = choiceSet.getCustomKeyboardConfiguration();
		}

		updateKeyboardProperties(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				presentChoiceSet();
			}
		});

	}


	// SENDING REQUESTS

	private void updateKeyboardProperties(CompletionListener listener){


	}

	private void presentChoiceSet() {



	}

	private void finishOperation() {

		if (keyboardProperties == null){
			completionListener.onComplete(true);
		}

		// We need to reset the keyboard properties
		SetGlobalProperties setGlobalProperties = new SetGlobalProperties();
		setGlobalProperties.setKeyboardProperties(originalKeyboardProperties);
		setGlobalProperties.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if (response.getSuccess()){
					completionListener.onComplete(true);
				}else{
					DebugTool.logError("Error resetting keyboard properties");
					completionListener.onComplete(false);
				}
			}
		});

		if (internalInterface.get() != null){
			internalInterface.get().sendRPC(setGlobalProperties);
		} else {
			DebugTool.logError("Internal Interface null when finishing choice keyboard reset");
		}
	}

	// GETTERS

	private PerformInteraction getPerformInteraction() {

		PerformInteraction pi = new PerformInteraction(choiceSet.getTitle(), presentationMode, getChoiceIds());
		pi.setInitialPrompt(choiceSet.getInitialPrompt());
		pi.setHelpPrompt(choiceSet.getHelpPrompt());
		pi.setTimeoutPrompt(choiceSet.getTimeoutPrompt());
		pi.setVrHelp(choiceSet.getVrHelpList());
		pi.setTimeout(choiceSet.getTimeout() * 1000);
		pi.setInteractionLayout(getLayoutMode());
		return pi;
	}

	private LayoutMode getLayoutMode() {

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

	private void setSelectedCellWithId(Integer cellId){
		List<ChoiceCell> cells = choiceSet.getChoices();
		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i).getChoiceId() == cellId){
				selectedCell = cells.get(i);
				selectedCellRow = i;
				return;
			}
		}
	}

	// LISTENERS

	private void addListeners(){

		keyboardRPCListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {

				if (keyboardListener == null){
					DebugTool.logError("Received Keyboard Input But Listener is null");
					return;
				}

				OnKeyboardInput onKeyboard = (OnKeyboardInput) notification;
				keyboardListener.get().onKeyboardDidSendEvent(onKeyboard.getEvent(), onKeyboard.getData());

				if (onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_VOICE) || onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_SUBMITTED)){
					// Submit Voice or Text
					keyboardListener.get().onUserDidSubmitInput(onKeyboard.getData(), onKeyboard.getEvent());
				} else if (onKeyboard.getEvent().equals(KeyboardEvent.KEYPRESS)){
					// Notify of Keypress
					keyboardListener.get().updateAutocompleteWithInput(onKeyboard.getData(), new KeyboardAutocompleteCompletionListener() {
						@Override
						public void onUpdatedAutoCompleteText(String updatedAutoCompleteText) {
							keyboardProperties.setAutoCompleteText(updatedAutoCompleteText);
							updateKeyboardProperties(null);
						}
					});

					keyboardListener.get().updateCharacterSetWithInput(onKeyboard.getData(), new KeyboardCharacterSetCompletionListener() {
						@Override
						public void onUpdatedCharacterSet(List<String> updatedCharacterSet) {
							keyboardProperties.setLimitedCharacterList(updatedCharacterSet);
							updateKeyboardProperties(null);
						}
					});
				} else if (onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_ABORTED) || onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_CANCELLED)){
					// Notify of abort / Cancellation
					keyboardListener.get().onKeyboardDidAbortWithReason(onKeyboard.getEvent());
				}

			}
		};

		if (internalInterface.get() != null) {
			internalInterface.get().addOnRPCNotificationListener(FunctionID.ON_KEYBOARD_INPUT, keyboardRPCListener);
		} else {
			DebugTool.logError("Present Choice Keyboard Listener Not Added");
		}


	}
}