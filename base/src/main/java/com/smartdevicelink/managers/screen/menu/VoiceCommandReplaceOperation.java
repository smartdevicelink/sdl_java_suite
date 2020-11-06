package com.smartdevicelink.managers.screen.menu;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.List;

public class VoiceCommandReplaceOperation extends Task {
    private static final String TAG = "VoiceCommandReplaceOperation";
    private final WeakReference<ISdl> internalInterface;
    private final List<DeleteCommand> deleteVoiceCommands;
    private final List<AddCommand> addVoiceCommands;
    private CompletionListener completionListener;


    VoiceCommandReplaceOperation(ISdl internalInterface, List<DeleteCommand> deleteVoiceCommands, List<AddCommand> addVoiceCommands, CompletionListener completionListener) {
        super("VoiceCommandReplaceOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.deleteVoiceCommands = deleteVoiceCommands;
        this.addVoiceCommands = addVoiceCommands;
        this.completionListener = completionListener;
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
                            completionListener.onComplete(false);
                        } else {
                            DebugTool.logInfo(TAG, "Successfully send voice commands");
                            onFinished();
                            completionListener.onComplete(true);
                        }
                    }
                });
            }
        });
    }

    private void sendDeleteCurrentVoiceCommands(final CompletionListener listener) {

        if (deleteVoiceCommands == null || deleteVoiceCommands.size() == 0) {
            if (listener != null) {
                listener.onComplete(true);
            }
            return;
        }

        internalInterface.get().sendRPCs(deleteVoiceCommands, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {

            }

            @Override
            public void onFinished() {
                DebugTool.logInfo(TAG, "Successfully deleted old voice commands");
                if (listener != null) {
                    listener.onComplete(true);
                }
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
            }
        });

    }

    // SEND NEW MENU ITEMS

    private void sendCurrentVoiceCommands(final CompletionListener listener) {

        if (addVoiceCommands == null || addVoiceCommands.size() == 0) {
            if (listener != null) {
                listener.onComplete(true); // no voice commands to send doesnt mean that its an error
            }
            return;
        }

        internalInterface.get().sendRPCs(addVoiceCommands, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {

            }

            @Override
            public void onFinished() {
                DebugTool.logInfo(TAG, "Sending Voice Commands Complete");
                if (listener != null) {
                    listener.onComplete(true);
                }
//                oldVoiceCommands = voiceCommands;
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
            }
        });
    }

}
