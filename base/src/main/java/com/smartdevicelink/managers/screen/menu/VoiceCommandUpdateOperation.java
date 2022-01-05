/*
 * Copyright (c) 2021 Livio, Inc.
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

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class VoiceCommandUpdateOperation extends Task {
    private static final String TAG = "VoiceCommandReplaceOperation";
    private final WeakReference<ISdl> internalInterface;
    List<VoiceCommand> oldVoiceCommands;
    private List<VoiceCommand> pendingVoiceCommands;
    private List<DeleteCommand> deleteVoiceCommands;
    private List<AddCommand> addCommandsToSend;
    VoiceCommandChangesListener voiceCommandListener;
    private List<VoiceCommand> currentVoiceCommands;
    private HashMap<RPCRequest, String> errorObject;

    interface VoiceCommandChangesListener {
        void updateVoiceCommands(List<VoiceCommand> newCurrentVoiceCommands, HashMap<RPCRequest, String> errorObject);
    }

    VoiceCommandUpdateOperation(ISdl internalInterface, List<VoiceCommand> oldVoiceCommands, List<VoiceCommand> pendingVoiceCommands, VoiceCommandChangesListener voiceCommandListener) {
        super("VoiceCommandReplaceOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.oldVoiceCommands = oldVoiceCommands;
        this.pendingVoiceCommands = pendingVoiceCommands;
        this.currentVoiceCommands = new ArrayList<>();
        if (oldVoiceCommands != null) {
            this.currentVoiceCommands.addAll(oldVoiceCommands);
        }
        this.voiceCommandListener = voiceCommandListener;
        this.errorObject = new HashMap<>();
    }

    @Override
    public void onExecute() {
        start();
    }

    private void start() {
        if (getState() == Task.CANCELED) {
            onFinished();
            return;
        }
        // Check if a voiceCommand has already been uploaded and update its VoiceCommandSelectionListener to
        // prevent calling the wrong listener in a case where a voice command was uploaded and then its voiceCommandSelectionListener was updated in another upload.
        if (pendingVoiceCommands != null && pendingVoiceCommands.size() > 0) {
            for (VoiceCommand voiceCommand : pendingVoiceCommands) {
                if (currentVoiceCommands.contains(voiceCommand)) {
                    currentVoiceCommands.get(currentVoiceCommands.indexOf(voiceCommand)).setVoiceCommandSelectionListener(voiceCommand.getVoiceCommandSelectionListener());
                }
            }
        }

        sendDeleteCurrentVoiceCommands(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (getState() == Task.CANCELED) {
                    onFinished();
                    return;
                }
                // we don't care about errors from deleting, send new add commands
                sendCurrentVoiceCommands(new CompletionListener() {
                    @Override
                    public void onComplete(boolean success2) {
                        if (!success2) {
                            DebugTool.logError(TAG, "Error sending voice commands");
                        }
                        onFinished();
                        if (voiceCommandListener != null) {
                            voiceCommandListener.updateVoiceCommands(currentVoiceCommands, errorObject);
                        }
                    }
                });
            }
        });
    }

    // Send DeleteCommandList

    private void sendDeleteCurrentVoiceCommands(final CompletionListener listener) {

        if (oldVoiceCommands == null || oldVoiceCommands.size() == 0) {
            if (listener != null) {
                listener.onComplete(true);
            }
            return;
        }

        List<VoiceCommand> voiceCommandsToDelete = voiceCommandsInListNotInSecondList(oldVoiceCommands, pendingVoiceCommands);

        if (voiceCommandsToDelete.size() == 0) {
            if (listener != null) {
                listener.onComplete(true);
            }
            return;
        }

        deleteVoiceCommands = deleteCommandsForVoiceCommands(voiceCommandsToDelete);

        internalInterface.get().sendRPCs(deleteVoiceCommands, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
            }

            @Override
            public void onFinished() {
                if (listener != null) {
                    if (errorObject.isEmpty()) {
                        DebugTool.logInfo(TAG, "Successfully deleted old voice commands");
                        listener.onComplete(true);
                    } else {
                        DebugTool.logInfo(TAG, "Unable to deleted some old voice commands");
                        listener.onComplete(false);
                    }
                }
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                DeleteCommand foundDeleteCommand = null;
                for (DeleteCommand deleteCommand : deleteVoiceCommands) {
                    if (correlationId == deleteCommand.getCorrelationID()) {
                        foundDeleteCommand = deleteCommand;
                        break;
                    }
                }

                if (!response.getSuccess()) {
                    errorObject.put(foundDeleteCommand, response.getInfo());
                } else {
                    if (foundDeleteCommand == null) {
                        return;
                    }
                    removeCurrentVoiceCommand(foundDeleteCommand.getCmdID());
                }
            }
        });

    }

    // Create DeleteCommand List

    List<DeleteCommand> deleteCommandsForVoiceCommands(List<VoiceCommand> voiceCommands) {
        List<DeleteCommand> deleteCommandList = new ArrayList<>();
        for (VoiceCommand command : voiceCommands) {
            DeleteCommand delete = new DeleteCommand(command.getCommandId());
            deleteCommandList.add(delete);
        }
        return deleteCommandList;
    }

    private void removeCurrentVoiceCommand(Integer commandId) {
        for (VoiceCommand voiceCommand : oldVoiceCommands) {
            if (commandId == voiceCommand.getCommandId()) {
                currentVoiceCommands.remove(voiceCommand);
                return;
            }
        }
    }

    // SEND NEW MENU ITEMS

    private void sendCurrentVoiceCommands(final CompletionListener listener) {

        List<VoiceCommand> voiceCommandsToAdd = voiceCommandsInListNotInSecondList(pendingVoiceCommands, oldVoiceCommands);

        if (voiceCommandsToAdd.size() == 0) {
            if (listener != null) {
                listener.onComplete(true); // no voice commands to send doesn't mean that its an error
            }
            return;
        }

        addCommandsToSend = addCommandsForVoiceCommands(voiceCommandsToAdd);

        internalInterface.get().sendRPCs(addCommandsToSend, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
            }

            @Override
            public void onFinished() {
                if (listener != null) {
                    if (errorObject.isEmpty()) {
                        DebugTool.logInfo(TAG, "Sending Voice Commands Complete");
                        listener.onComplete(true);
                    } else {
                        DebugTool.logInfo(TAG, "Sending Voice Commands Complete with errors");
                        listener.onComplete(false);
                    }
                }
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                AddCommand foundAddCommand = null;
                for (AddCommand addCommand : addCommandsToSend) {
                    if (correlationId == addCommand.getCorrelationID()) {
                        foundAddCommand = addCommand;
                        break;
                    }
                }
                if (!response.getSuccess()) {
                    errorObject.put(foundAddCommand, response.getInfo());
                } else {
                    if (foundAddCommand == null) {
                        return;
                    }
                    VoiceCommand foundVoiceCommand = pendingVoiceCommand(foundAddCommand.getCmdID());
                    if (foundVoiceCommand != null) {
                        currentVoiceCommands.add(foundVoiceCommand);
                    }
                }
            }
        });
    }

    List<VoiceCommand> voiceCommandsInListNotInSecondList(List<VoiceCommand> firstList, List<VoiceCommand> secondList) {
        if (secondList == null || secondList.size() == 0) {
            return firstList;
        }
        List<VoiceCommand> differencesList = new ArrayList<>(firstList);
        differencesList.removeAll(secondList);
        return differencesList;
    }

    public void setOldVoiceCommands(List<VoiceCommand> oldVoiceCommands) {
        this.oldVoiceCommands = oldVoiceCommands;
        this.currentVoiceCommands = new ArrayList<>(oldVoiceCommands);
    }

    // Create AddCommand List

    List<AddCommand> addCommandsForVoiceCommands(List<VoiceCommand> voiceCommands) {
        List<AddCommand> addCommandList = new ArrayList<>();
        for (VoiceCommand command : voiceCommands) {
            AddCommand addCommand = new AddCommand(command.getCommandId());
            addCommand.setVrCommands(command.getVoiceCommands());
            addCommandList.add(addCommand);
        }
        return addCommandList;
    }

    private VoiceCommand pendingVoiceCommand(Integer commandId) {
        for (VoiceCommand voiceCommand : pendingVoiceCommands) {
            if (commandId == voiceCommand.getCommandId()) {
                return voiceCommand;
            }
        }
        return null;
    }
}
