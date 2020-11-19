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
    private VoiceCommandChangesListener voiceCommandListener;
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
                            onError();
                        } else {
                            DebugTool.logInfo(TAG, "Successfully send voice commands");
                            onFinished();
                        }
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

        if (oldVoiceCommands == null || oldVoiceCommands.isEmpty()) {
            if (listener != null) {
                listener.onComplete(true);
            }
            return;
        }

        deleteVoiceCommands = deleteCommandsForVoiceCommands(oldVoiceCommands);

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
                    }
                }

                if (!response.getSuccess()) {
                    errorObject.put(foundDeleteCommand, response.getInfo());
                } else {
                    if (foundDeleteCommand == null) {
                        return;
                    }
                    removeCurrentVoiceCommandForCorrelatingDeleteCommand(foundDeleteCommand);
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

    private void removeCurrentVoiceCommandForCorrelatingDeleteCommand(DeleteCommand deleteCommand) {
        for (VoiceCommand voiceCommand : oldVoiceCommands) {
            if (deleteCommand.getCmdID() == voiceCommand.getCommandId()) {
                currentVoiceCommands.remove(voiceCommand);
                return;
            }
        }
    }

    // SEND NEW MENU ITEMS

    private void sendCurrentVoiceCommands(final CompletionListener listener) {

        if (pendingVoiceCommands == null || pendingVoiceCommands.size() == 0) {
            if (listener != null) {
                listener.onComplete(true); // no voice commands to send doesnt mean that its an error
            }
            return;
        }

        addCommandsToSend = addCommandsForVoiceCommands(pendingVoiceCommands);

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
                    }
                }
                if (!response.getSuccess()) {
                    errorObject.put(foundAddCommand, response.getInfo());
                } else {
                    if (foundAddCommand == null) {
                        return;
                    }
                    VoiceCommand foundVoiceCommand = pendingVoiceCommandForCorrelatingAddCommand(foundAddCommand);
                    if (foundVoiceCommand != null) {
                        currentVoiceCommands.add(foundVoiceCommand);
                    }
                }
            }
        });
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

    private VoiceCommand pendingVoiceCommandForCorrelatingAddCommand(AddCommand addCommand) {
        for (VoiceCommand voiceCommand : pendingVoiceCommands) {
            if (addCommand.getCmdID() == voiceCommand.getCommandId()) {
                return voiceCommand;
            }
        }
        return null;
    }
}
