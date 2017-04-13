package com.smartdevicelink.api.menu;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

class SdlSyncCommand {

    private SelectListener mListener;
    private boolean isVisible;
    private boolean isRegistered;

    public SdlSyncCommand(@NonNull SelectListener listener) {
        mListener = listener;
    }


    void update(int id, SdlContext sdlContext, AddCommand command) {
        if(isVisible) sendReplaceCommand(id, sdlContext, command);
        if(!isRegistered) registerSelectListener(id, sdlContext);
        sdlContext.sendRpc(command);
        isVisible = true;
    }

    void remove(int id, SdlContext sdlContext) {
        if(isVisible) sendDeleteCommand(id, sdlContext, null);
        if(isRegistered) unregisterSelectListener(id, sdlContext);
    }

    void registerSelectListener(int id, SdlContext sdlContext) {
        if(!isRegistered) {
            sdlContext.registerMenuCallback(id, mListener);
            isRegistered = true;
        }
    }

    void unregisterSelectListener(int id, SdlContext sdlContext) {
        if(isRegistered) {
            sdlContext.unregisterMenuCallback(id);
            isRegistered = false;
        }
    }

    void imagePrepared(int id, SdlContext context, AddCommand command){
        if(isVisible) update(id, context, command);
    }

    private void sendDeleteCommand(int id, SdlContext sdlContext, OnRPCResponseListener listener) {
        DeleteCommand dc = new DeleteCommand();
        dc.setCmdID(id);
        dc.setOnRPCResponseListener(listener);
        sdlContext.sendRpc(dc);
        isVisible = false;
    }

    private void sendReplaceCommand(int id, final SdlContext sdlContext, final AddCommand command){
        sendDeleteCommand(id, sdlContext, new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(response != null && response.getSuccess()){
                    sdlContext.sendRpc(command);
                    isVisible = true;
                }
            }
        });
    }

}
