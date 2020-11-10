package com.smartdevicelink.managers.screen.menu;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteCommandResponse;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoiceCommandReplaceOperation extends Task {
    private static final String TAG = "VoiceCommandReplaceOperation";
    private final WeakReference<ISdl> internalInterface;
    List<VoiceCommand> deleteVoiceCommands;
    private List<VoiceCommand> addVoiceCommands;
    private List<DeleteCommand> deleteCommands;
    private List<AddCommand> addCommands;
    private VoiceCommandChangesListener voiceCommandListener;
    private List<VoiceCommand> updatedVoiceCommands;
    private HashMap<Integer, String> errorObject;

    interface VoiceCommandChangesListener {
        void updatedVoiceCommands(List<VoiceCommand> voiceCommands, HashMap<Integer, String> errorObject);
    }

    VoiceCommandReplaceOperation(ISdl internalInterface, List<VoiceCommand> deleteVoiceCommands, List<VoiceCommand> addVoiceCommands, VoiceCommandChangesListener voiceCommandListener) {
        super("VoiceCommandReplaceOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.deleteVoiceCommands = deleteVoiceCommands;
        this.addVoiceCommands = addVoiceCommands;
        this.voiceCommandListener = voiceCommandListener;
        this.updatedVoiceCommands = deleteVoiceCommands;
        this.errorObject = new HashMap<>();
    }

    @Override
    public void onExecute() {
        start();
    }

    private void start() {
        if (getState() == Task.CANCELED) {
            return;
        }

        sendDeleteCurrentVoiceCommands(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
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
                        voiceCommandListener.updatedVoiceCommands(updatedVoiceCommands, errorObject);
                    }
                });
            }
        });
    }

    // Send DeleteCommandList

    private void sendDeleteCurrentVoiceCommands(final CompletionListener listener) {

        if (deleteVoiceCommands == null || deleteVoiceCommands.isEmpty()) {
            if (listener != null) {
                listener.onComplete(true);
            }
            return;
        }

        deleteCommands = deleteCommandsForVoiceCommands(deleteVoiceCommands);

        internalInterface.get().sendRPCs(deleteCommands, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
            }

            @Override
            public void onFinished() {
                DebugTool.logInfo(TAG, "Successfully deleted old voice commands");
                if (listener != null) {
                    if (errorObject.isEmpty()) {
                        listener.onComplete(true);
                    } else {
                        listener.onComplete(false);
                    }
                }
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                DeleteCommandResponse deleteResponse = (DeleteCommandResponse) response;
                if (!deleteResponse.getSuccess()) {
                    errorObject.put(correlationId, response.getInfo());
                } else {
                    VoiceCommand foundCommand = getVoiceCommandFromDeleteCommandResponse(correlationId);
                    if (foundCommand != null) {
                        updatedVoiceCommands.remove(foundCommand);
                    }
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

    private VoiceCommand getVoiceCommandFromDeleteCommandResponse(int correlationId) {
        for (DeleteCommand deleteCommand : deleteCommands) {
            if (correlationId == deleteCommand.getCorrelationID()) {
                for (VoiceCommand voiceCommand : deleteVoiceCommands) {
                    if (deleteCommand.getCmdID() == voiceCommand.getCommandId()) {
                        return voiceCommand;
                    }
                }
            }
        }
        return null;
    }

    // SEND NEW MENU ITEMS

    private void sendCurrentVoiceCommands(final CompletionListener listener) {

        if (addVoiceCommands == null || addVoiceCommands.size() == 0) {
            if (listener != null) {
                listener.onComplete(true); // no voice commands to send doesnt mean that its an error
            }
            return;
        }

        addCommands = addCommandsForVoiceCommands(addVoiceCommands);

        internalInterface.get().sendRPCs(addCommands, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
            }

            @Override
            public void onFinished() {
                DebugTool.logInfo(TAG, "Sending Voice Commands Complete");
                if (listener != null) {
                    if (errorObject.isEmpty()) {
                        listener.onComplete(true);
                    } else {
                        listener.onComplete(false);
                    }
                }
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                AddCommandResponse addResponse = (AddCommandResponse) response;
                if (!addResponse.getSuccess()) {
                    errorObject.put(correlationId, response.getInfo());
                } else {
                    VoiceCommand foundCommand = getVoiceCommandFromAddCommandResponse(correlationId);
                    if (foundCommand != null) {
                        updatedVoiceCommands.add(foundCommand);
                    }
                }
            }
        });
    }

    // Create AddCommand List

    List<AddCommand> addCommandsForVoiceCommands(List<VoiceCommand> voiceCommands) {
        List<AddCommand> addCommandList = new ArrayList<>();
        for (VoiceCommand command : voiceCommands) {
            addCommandList.add(commandForVoiceCommand(command));
        }
        return addCommandList;
    }

    // Create AddCommand

    private AddCommand commandForVoiceCommand(VoiceCommand voiceCommand) {
        AddCommand command = new AddCommand(voiceCommand.getCommandId());
        command.setVrCommands(voiceCommand.getVoiceCommands());
        return command;
    }

    private VoiceCommand getVoiceCommandFromAddCommandResponse(int correlationId) {
        for (AddCommand addCommand : addCommands) {
            if (correlationId == addCommand.getCorrelationID()) {
                for (VoiceCommand voiceCommand : addVoiceCommands) {
                    if (addCommand.getCmdID() == voiceCommand.getCommandId()) {
                        return voiceCommand;
                    }
                }
            }
        }
        return null;
    }
}
