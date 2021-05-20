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

import androidx.annotation.NonNull;

import com.livio.taskmaster.Queue;
import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

abstract class BaseVoiceCommandManager extends BaseSubManager {
    private static final String TAG = "BaseVoiceCommandManager";
    List<VoiceCommand> voiceCommands, currentVoiceCommands, originalVoiceCommands;

    int lastVoiceCommandId;
    private static final int voiceCommandIdMin = 1900000000;

    HMILevel currentHMILevel;
    OnRPCNotificationListener hmiListener;
    OnRPCNotificationListener commandListener;

    Queue transactionQueue;
    VoiceCommandUpdateOperation updateOperation;


    // CONSTRUCTORS

    BaseVoiceCommandManager(@NonNull ISdl internalInterface) {
        super(internalInterface);

        this.transactionQueue = newTransactionQueue();

        currentHMILevel =  null;
        addListeners();
        lastVoiceCommandId = voiceCommandIdMin;
    }

    @Override
    public void start(CompletionListener listener) {
        transitionToState(READY);
        super.start(listener);
    }

    @Override
    public void dispose() {

        lastVoiceCommandId = voiceCommandIdMin;
        voiceCommands = null;
        currentVoiceCommands = null;

        currentHMILevel = null;

        if (transactionQueue != null) {
            transactionQueue.close();
        }
        transactionQueue = null;

        updateOperation = null;

        // remove listeners
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_COMMAND, commandListener);

        super.dispose();
    }

    private Queue newTransactionQueue() {
        Queue queue = internalInterface.getTaskmaster().createQueue("VoiceCommandManager", 4, false);
        queue.pause();
        return queue;
    }

    // If the HMI level is NONE since we want to delay sending RPCs until we're in non-NONE
    private void updateTransactionQueueSuspended() {
        if (HMILevel.HMI_NONE.equals(currentHMILevel)) {
            DebugTool.logInfo(TAG, "Suspending the transaction queue. Current HMI level is NONE");
            transactionQueue.pause();
        } else {
            DebugTool.logInfo(TAG, "Starting the transaction queue");
            transactionQueue.resume();
        }
    }

    // SETTERS

    public void setVoiceCommands(List<VoiceCommand> voiceCommands) {

        // we actually need voice commands to set.
        if (voiceCommands == null || voiceCommands.equals(this.originalVoiceCommands)) {
            DebugTool.logInfo(TAG, "Voice commands list was null or matches the current voice commands");
            return;
        }

        if (!isVoiceCommandsUnique(voiceCommands)) {
            DebugTool.logError(TAG, "Not all voice command strings are unique across all voice commands. Voice commands will not be set.");
            return;
        }

        List<VoiceCommand> validatedVoiceCommands = removeEmptyVoiceCommands(voiceCommands);

        if (validatedVoiceCommands.size() == 0) {
            DebugTool.logError(TAG, "New voice commands are invalid, skipping...");
            return;
        }

        this.originalVoiceCommands = new ArrayList<>(voiceCommands);
        this.voiceCommands = validatedVoiceCommands;
        updateIdsOnVoiceCommands(voiceCommands);

        cleanTransactionQueue();
        updateOperation = new VoiceCommandUpdateOperation(internalInterface, currentVoiceCommands, this.voiceCommands, new VoiceCommandUpdateOperation.VoiceCommandChangesListener() {
            @Override
            public void updateVoiceCommands(List<VoiceCommand> newCurrentVoiceCommands, HashMap<RPCRequest, String> errorObject) {
                DebugTool.logInfo(TAG, "The updated list of VoiceCommands: " + newCurrentVoiceCommands);
                if (!errorObject.isEmpty()) {
                    DebugTool.logError(TAG, "The failed Add and Delete Commands: " + errorObject);
                }
                currentVoiceCommands = newCurrentVoiceCommands;
                updatePendingOperations(newCurrentVoiceCommands);
                updateOperation = null;
            }
        });
        transactionQueue.add(updateOperation, false);
    }

    public List<VoiceCommand> getVoiceCommands() {
        return voiceCommands;
    }

    private void cleanTransactionQueue() {
        if (transactionQueue != null) {
            transactionQueue.clear();
        }

        if (updateOperation != null) {
            updateOperation.cancelTask();
            updateOperation = null;
        }
    }

    private void updatePendingOperations(List<VoiceCommand> newCurrentVoiceCommands) {
        for (Task operation : transactionQueue.getTasksAsList()) {
            if (operation.getState() == Task.IN_PROGRESS) {
                continue;
            }
            VoiceCommandUpdateOperation vcOperation = (VoiceCommandUpdateOperation) operation;
            vcOperation.oldVoiceCommands = newCurrentVoiceCommands;
        }
    }

    // HELPERS

    private void updateIdsOnVoiceCommands(List<VoiceCommand> voiceCommands) {
        for (VoiceCommand command : voiceCommands) {
            command.setCommandId(++lastVoiceCommandId);
        }
    }

    List<VoiceCommand> removeEmptyVoiceCommands(List<VoiceCommand> voiceCommands) {
        List<VoiceCommand> validatedVoiceCommands = new ArrayList<>();
        for (VoiceCommand voiceCommand : voiceCommands) {
            if (voiceCommand == null) {
                continue;
            }
            List<String> voiceCommandStrings = new ArrayList<>();
            for (String voiceCommandString : voiceCommand.getVoiceCommands()) {
                if (voiceCommandString == null) {
                    continue;
                }
                String trimmedString = voiceCommandString.trim();
                if (trimmedString.length() > 0) {
                    voiceCommandStrings.add(trimmedString);
                }
            }
            if (voiceCommandStrings.size() > 0) {
                voiceCommand.setVoiceCommands(voiceCommandStrings);
                validatedVoiceCommands.add(voiceCommand);
            }
        }
        return validatedVoiceCommands;
    }

    // LISTENERS

    private void addListeners() {

        // HMI UPDATES
        hmiListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                    return;
                }
                currentHMILevel = onHMIStatus.getHmiLevel();
                updateTransactionQueueSuspended();
            }
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

        // COMMANDS
        commandListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnCommand onCommand = (OnCommand) notification;
                if (voiceCommands != null && voiceCommands.size() > 0) {
                    for (VoiceCommand command : voiceCommands) {
                        if (onCommand.getCmdID() == command.getCommandId()) {
                            if (command.getVoiceCommandSelectionListener() != null) {
                                command.getVoiceCommandSelectionListener().onVoiceCommandSelected();
                                break;
                            }
                        }
                    }
                }
            }
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_COMMAND, commandListener);
    }

    /**
     * Boolean method that checks to see if all VoiceCommands in a given list are unique
     *
     * @param voiceCommands - list of VoiceCommands
     * @return - true if VoiceCommands are unique, false if not
     */
    private boolean isVoiceCommandsUnique(List<VoiceCommand> voiceCommands) {
        HashSet<String> voiceCommandHashSet = new HashSet<>();
        int voiceCommandCount = 0;

        for (VoiceCommand voiceCommand : voiceCommands) {
            voiceCommandHashSet.addAll(voiceCommand.getVoiceCommands());
            voiceCommandCount += voiceCommand.getVoiceCommands().size();
        }
        return (voiceCommandHashSet.size() == voiceCommandCount);
    }
}
