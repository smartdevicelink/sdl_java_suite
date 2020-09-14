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
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PresentKeyboardOperation extends Task {
	private static final String TAG = "PresentKeyboardOperation";
	private final WeakReference<ISdl> internalInterface;
	private final KeyboardListener keyboardListener;
	private final KeyboardProperties originalKeyboardProperties;
	private KeyboardProperties keyboardProperties;
	private final KeyboardProperties customConfig;
	private boolean updatedKeyboardProperties;
	private final String initialText;
	private OnRPCNotificationListener keyboardRPCListener;
	private final Integer cancelID;
	final SdlMsgVersion sdlMsgVersion;

	PresentKeyboardOperation(ISdl internalInterface, KeyboardProperties originalKeyboardProperties, String initialText, KeyboardProperties customConfig, KeyboardListener keyboardListener, Integer cancelID){
		super("PresentKeyboardOperation");
		this.internalInterface = new WeakReference<>(internalInterface);
		this.keyboardListener = keyboardListener;
		this.originalKeyboardProperties = originalKeyboardProperties;
		this.keyboardProperties = originalKeyboardProperties;
		this.customConfig = customConfig;
		this.initialText = initialText;
		this.cancelID = cancelID;
		this.sdlMsgVersion = internalInterface.getSdlMsgVersion();
	}

	@Override
	public void onExecute() {
		DebugTool.logInfo(TAG, "Keyboard Operation: Executing present keyboard operation");
		addListeners();
		start();
	}

	private void start(){
		DebugTool.logInfo(TAG, "Choice Operation: Executing present keyboard operation");

		if (getState() == Task.CANCELED) {
			finishOperation();
			return;
		}

		if (keyboardListener != null){
			keyboardProperties = customConfig;
			updatedKeyboardProperties = true;
		}

		updateKeyboardProperties(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				if (getState() == Task.CANCELED) {
					finishOperation();
					return;
				}

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
					if (!response.getSuccess()) {
						DebugTool.logError(TAG, "There was an error presenting the keyboard. Finishing operation - choice set manager - . Error: " + response.getInfo() + " resultCode: " + response.getResultCode());
					}
					finishOperation();
				}
			});

			internalInterface.get().sendRPC(pi);

		}else{
			DebugTool.logError(TAG, "Internal Interface null in present keyboard operation - choice");
		}
	}

	/**
	 *  Cancels the keyboard-only interface if it is currently showing. If the keyboard has not yet been sent to Core, it will not be sent.
	 *
	 *  This will only dismiss an already presented keyboard if connected to head units running SDL 6.0+.
	 */
	void dismissKeyboard() {
		if ((getState() == Task.FINISHED)) {
			DebugTool.logInfo(TAG, "This operation has already finished so it can not be canceled.");
			return;
		} else if (getState() == Task.CANCELED) {
			DebugTool.logInfo(TAG, "This operation has already been canceled. It will be finished at some point during the operation.");
			return;
		} else if (getState() == Task.IN_PROGRESS) {
			if (sdlMsgVersion.getMajorVersion() < 6){
				DebugTool.logWarning(TAG, "Canceling a keyboard is not supported on this head unit");
				return;
			}

			DebugTool.logInfo(TAG, "Canceling the presented keyboard.");

			CancelInteraction cancelInteraction = new CancelInteraction(FunctionID.PERFORM_INTERACTION.getId(), cancelID);
			cancelInteraction.setOnRPCResponseListener(new OnRPCResponseListener() {
				@Override
				public void onResponse(int correlationId, RPCResponse response) {
					DebugTool.logInfo(TAG, "Canceled the presented keyboard " + ((response.getResultCode() == Result.SUCCESS) ? "successfully" : "unsuccessfully"));
				}
			});
			if (internalInterface.get() != null){
				internalInterface.get().sendRPC(cancelInteraction);
			} else {
				DebugTool.logError(TAG, "Internal interface null - could not send cancel interaction for keyboard.");
			}
		} else {
			DebugTool.logInfo(TAG, "Canceling a keyboard that has not yet been sent to Core.");
			this.cancelTask();
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
					if (listener != null){
						listener.onComplete(false);
					}
					DebugTool.logError(TAG, "Error Setting keyboard properties in present keyboard operation - choice manager");
					return;
				}

				updatedKeyboardProperties = true;

				if (listener != null){
					listener.onComplete(true);
				}
				DebugTool.logInfo(TAG, "Success Setting keyboard properties in present keyboard operation - choice manager");
			}
		});

		if (internalInterface.get() != null){
			internalInterface.get().sendRPC(setGlobalProperties);
		} else {
			DebugTool.logError(TAG, "Internal interface null - present keyboard op - choice");
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
					if (response.getSuccess()) {
						updatedKeyboardProperties = false;
						DebugTool.logInfo(TAG, "Successfully reset choice keyboard properties to original config");
					} else {
						DebugTool.logError(TAG, "Failed to reset choice keyboard properties to original config " + response.getResultCode() + ", " + response.getInfo());
					}
					PresentKeyboardOperation.super.onFinished();
				}
			});

			if (internalInterface.get() != null) {
				internalInterface.get().sendRPC(setGlobalProperties);
				internalInterface.get().removeOnRPCNotificationListener(FunctionID.ON_KEYBOARD_INPUT, keyboardRPCListener);
			} else {
				DebugTool.logError(TAG, "Internal Interface null when finishing choice keyboard reset");
			}
		} else {
			PresentKeyboardOperation.super.onFinished();
		}
	}

	// GETTERS

	PerformInteraction getPerformInteraction() {
		PerformInteraction pi = new PerformInteraction();
		pi.setInitialText(initialText);
		pi.setInteractionMode(InteractionMode.MANUAL_ONLY);
		pi.setInteractionChoiceSetIDList(Collections.<Integer>emptyList());
		pi.setInteractionLayout(LayoutMode.KEYBOARD);
		pi.setCancelID(cancelID);
		return pi;
	}

	public Integer getCancelID() {
		return cancelID;
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
					DebugTool.logError(TAG, "Received Keyboard Input But Listener is null");
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
			DebugTool.logError(TAG, "Present Keyboard Listener Not Added - choice manager");
		}
	}
}