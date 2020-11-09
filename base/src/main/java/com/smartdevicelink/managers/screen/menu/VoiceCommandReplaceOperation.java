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
import java.util.List;

public class VoiceCommandReplaceOperation extends Task {
    private static final String TAG = "VoiceCommandReplaceOperation";
    private final WeakReference<ISdl> internalInterface;
    private final List<VoiceCommand> deleteVoiceCommands;
    private final List<VoiceCommand> addVoiceCommands;
    private List<DeleteCommand> deleteCommands;
    private List<AddCommand> addCommands;
    private CompletionListener completionListener;
    private VoiceCommandChangesListener voiceCommandListener;
    private boolean isRetry = false;
    private int currentDeleteCommand;
    private int currentAddCommand;
    private List<DeleteCommand> failedDeleteCommands;
    private List<AddCommand> failedAddCommands;
    private List<AddCommand> successfulAddCommands;

    interface VoiceCommandChangesListener {
        void onDeleteCommandsFailed(List<DeleteCommand> deleteCommands);
        void onAddCommandsFailed(List<AddCommand> addCommands);
        void updatedVoiceCommands(List<AddCommand> voiceCommands);
    }

    VoiceCommandReplaceOperation(ISdl internalInterface, List<VoiceCommand> deleteVoiceCommands, List<VoiceCommand> addVoiceCommands, CompletionListener completionListener, VoiceCommandChangesListener voiceCommandListener) {
        super("VoiceCommandReplaceOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.deleteVoiceCommands = deleteVoiceCommands;
        this.addVoiceCommands = addVoiceCommands;
        this.completionListener = completionListener;
        this.voiceCommandListener = voiceCommandListener;
        this.failedAddCommands = new ArrayList<>();
        this.failedDeleteCommands = new ArrayList<>();
        this.successfulAddCommands = new ArrayList<>();
    }

    @Override
    public void onExecute() {
        start();
    }

    private void start() {
        sendCommandRPCS();
    }

    private void sendCommandRPCS() {
        if (getState() == Task.CANCELED) {
            return;
        }

        sendDeleteCurrentVoiceCommands(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (!success) {
                    voiceCommandListener.onDeleteCommandsFailed(failedDeleteCommands);
                }
                // we don't care about errors from deleting, send new add commands
                sendCurrentVoiceCommands(new CompletionListener() {
                    @Override
                    public void onComplete(boolean success2) {
                        if (!success2) {
                            DebugTool.logError(TAG, "Error sending voice commands");
                            onError();
                            if(!isRetry) {
                                isRetry = true;
                                sendCommandRPCS();
                            } else {
                                voiceCommandListener.onAddCommandsFailed(failedAddCommands);
                                completionListener.onComplete(false);
                            }
                        } else {
                            DebugTool.logInfo(TAG, "Successfully send voice commands");
                            onFinished();
                            voiceCommandListener.updatedVoiceCommands(successfulAddCommands);
                            completionListener.onComplete(true);
                        }
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
                currentDeleteCommand = deleteCommands.size() - remainingRequests - 1;
            }

            @Override
            public void onFinished() {
                DebugTool.logInfo(TAG, "Successfully deleted old voice commands");
                if (listener != null) {
                    if (failedDeleteCommands.isEmpty()) {
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
                    failedDeleteCommands.add(deleteCommands.get(currentDeleteCommand));
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
                currentAddCommand = addCommands.size() - remainingRequests - 1;
            }

            @Override
            public void onFinished() {
                DebugTool.logInfo(TAG, "Sending Voice Commands Complete");
                if (listener != null) {
                    if (failedAddCommands.isEmpty()) {
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
                    failedAddCommands.add(addCommands.get(currentAddCommand));
                } else {
                    successfulAddCommands.add(addCommands.get(currentDeleteCommand));
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
}
