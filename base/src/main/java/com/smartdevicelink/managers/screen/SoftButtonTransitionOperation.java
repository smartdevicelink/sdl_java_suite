package com.smartdevicelink.managers.screen;

import com.livio.taskmaster.Task;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Bilal Alsharifi on 6/15/20.
 */
class SoftButtonTransitionOperation extends Task {

    private final WeakReference<ISdl> internalInterface;
    private final CopyOnWriteArrayList<SoftButtonObject> softButtonObjects;
    private String currentMainField1;

    SoftButtonTransitionOperation(ISdl internalInterface, CopyOnWriteArrayList<SoftButtonObject> softButtonObjects, String currentMainField1) {
        super("SoftButtonReplaceOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.softButtonObjects = softButtonObjects;
        this.currentMainField1 = currentMainField1;
    }

    @Override
    public void onExecute() {
        start();
    }

    private void start() {
        if (getState() == Task.CANCELED) {
            return;
        }

        sendNewSoftButtons();
    }

    private void sendNewSoftButtons() {
        Show show = new Show();
        show.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (!response.getSuccess()) {
                    DebugTool.logWarning("Failed to transition soft button to new state");
                }
                onFinished();
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                super.onError(correlationId, resultCode, info);
                DebugTool.logWarning("Failed to transition soft button to new state. " + info);
                onFinished();
            }
        });
        show.setMainField1(currentMainField1);
        show.setSoftButtons(currentStateSoftButtonsForObjects(softButtonObjects));
        if (internalInterface.get() != null) {
            internalInterface.get().sendRPC(show);
        }
    }

    private List<SoftButton> currentStateSoftButtonsForObjects(List<SoftButtonObject> softButtonObjects) {
        List<SoftButton> softButtons = new ArrayList<>();
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            softButtons.add(softButtonObject.getCurrentStateSoftButton());
        }
        return softButtons;
    }

    void setCurrentMainField1(String currentMainField1) {
        this.currentMainField1 = currentMainField1;
    }
}
