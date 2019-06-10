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
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class PresentKeyboardOperation implements Runnable {

	private WeakReference<ISdl> internalInterface;
	private KeyboardListener keyboardListener;
	private KeyboardProperties originalKeyboardProperties, keyboardProperties, customConfig;
	private boolean updatedKeyboardProperties;
	private String initialText;
	private OnRPCNotificationListener keyboardRPCListener;

	public PresentKeyboardOperation(ISdl internalInterface, KeyboardProperties originalKeyboardProperties, String initialText, KeyboardProperties customConfig, KeyboardListener keyboardListener){
		this.internalInterface = new WeakReference<>(internalInterface);
		this.keyboardListener = keyboardListener;
		this.originalKeyboardProperties = originalKeyboardProperties;
		this.keyboardProperties = originalKeyboardProperties;
		this.customConfig = customConfig;
		this.initialText = initialText;
	}

	@Override
	public void run() {
		addListeners();
		start();
	}

	private void start(){
		DebugTool.logInfo("Choice Operation: Executing present keyboard operation");
		if (keyboardListener != null){
			keyboardProperties = customConfig;
			updatedKeyboardProperties = true;
		}

		updateKeyboardProperties(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				presentKeyboard();
			}
		});
	}

	// SENDING REQUESTS

	private void presentKeyboard(){

		if (internalInterface.get() != null){

			PerformInteraction pi = getPerformInteraction();
			pi.setOnRPCResponseListener(new OnRPCResponseListener() {
				@Override
				public void onResponse(int correlationId, RPCResponse response) {
					finishOperation();
				}
			});

			internalInterface.get().sendRPC(pi);

		}else{
			DebugTool.logError("Internal Interface null in present keyboard operation - choice");
		}

	}

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
					DebugTool.logError("Error Setting keyboard properties in present choice set operation");
				}

				updatedKeyboardProperties = true;

				if (listener != null){
					listener.onComplete(true);
				}
			}
		});

		if (internalInterface.get() != null){
			internalInterface.get().sendRPC(setGlobalProperties);
		} else {
			DebugTool.logError("Internal interface null - present keyboard op - choice");
		}
	}

	private void finishOperation() {

		if (updatedKeyboardProperties) {
			// We need to reset the keyboard properties
			SetGlobalProperties setGlobalProperties = new SetGlobalProperties();
			setGlobalProperties.setKeyboardProperties(originalKeyboardProperties);
			setGlobalProperties.setOnRPCResponseListener(new OnRPCResponseListener() {
				@Override
				public void onResponse(int correlationId, RPCResponse response) {
					updatedKeyboardProperties = false;
					DebugTool.logInfo("Successfully reset choice keyboard properties to original config");
				}
			});

			if (internalInterface.get() != null) {
				internalInterface.get().sendRPC(setGlobalProperties);
				internalInterface.get().removeOnRPCNotificationListener(FunctionID.ON_KEYBOARD_INPUT, keyboardRPCListener);
			} else {
				DebugTool.logError("Internal Interface null when finishing choice keyboard reset");
			}
		}
	}

	// GETTERS

	private PerformInteraction getPerformInteraction() {
		PerformInteraction pi = new PerformInteraction();
		pi.setInitialText(initialText);
		pi.setInteractionMode(InteractionMode.MANUAL_ONLY);
		pi.setInteractionChoiceSetIDList(Collections.<Integer>emptyList());
		pi.setInteractionLayout(LayoutMode.KEYBOARD);
		return pi;
	}

	// LISTENERS

	private void addListeners(){

		keyboardRPCListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {

				if (Thread.interrupted()){
					DebugTool.logWarning("Choice Operation - keyboard: Thread has been interrupted. Cleaning up");
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
							updatedKeyboardProperties = true;
						}
					});

					keyboardListener.updateCharacterSetWithInput(onKeyboard.getData(), new KeyboardCharacterSetCompletionListener() {
						@Override
						public void onUpdatedCharacterSet(List<String> updatedCharacterSet) {
							keyboardProperties.setLimitedCharacterList(updatedCharacterSet);
							updateKeyboardProperties(null);
							updatedKeyboardProperties = true;
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
			DebugTool.logError("Present Choice Keyboard Listener Not Added");
		}
	}
}