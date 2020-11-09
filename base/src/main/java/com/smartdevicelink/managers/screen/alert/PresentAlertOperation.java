package com.smartdevicelink.managers.screen.alert;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;

public class PresentAlertOperation extends Task {
    private static final String TAG = "PresentAlertOperation";
    private AlertView alertView;
    private CompletionListener listener;
    private final WeakReference<ISdl> internalInterface;


    public PresentAlertOperation(ISdl internalInterface, AlertView alertView, CompletionListener listener) {
        super("PresentAlertOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.alertView = alertView;
        this.listener = listener;
        this.alertView.canceledListener = new AlertCanceledListener() {
            @Override
            public void onAlertCanceled() {
                cancelAlert();
            }
        };
    }

    @Override
    public void onExecute() {
        DebugTool.logInfo(TAG, "Alert Operation: Executing present Alert operation");
        start();

    }

    private void start() {
        Alert alert = new Alert();
        alert.setAlertText1(alertView.getText());

        alert.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(response.getSuccess()){
                    DebugTool.logInfo(TAG, "Alert was shown successfully");
                } else {
                    DebugTool.logInfo(TAG, "Alert was not shown successfully");
                }
                finishOperation(response.getSuccess());
            }
        });
        internalInterface.get().sendRPC(alert);


    }
    private void cancelAlert() {

    }

    private void finishOperation(boolean success){
        if(listener != null){
            listener.onComplete(success);
        }
    }
}
