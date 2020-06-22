package com.smartdevicelink.managers.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.SubscribeButton;
import com.smartdevicelink.proxy.rpc.UnsubscribeButton;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

class SubscribeButtonManager extends BaseSubManager {
    String TAG = "SubscribeButtonManager";
    private final HashMap<ButtonName, CopyOnWriteArrayList<OnButtonListener>> onButtonListeners;

    SubscribeButtonManager(@NonNull ISdl internalInterface){
        super(internalInterface);
        setRpcNotificaionListeners();
        onButtonListeners = new HashMap<>();


    }

    @Override
    public void start(CompletionListener listener) {
        transitionToState(READY);
        super.start(listener);
    }

    public void addButtonListener(final ButtonName buttonName, final OnButtonListener listener){

        if(onButtonListeners.get(buttonName) == null){
            onButtonListeners.put(buttonName, new CopyOnWriteArrayList<OnButtonListener>());
            subscribeButtonRequest(buttonName,listener);
        }
        if(onButtonListeners.get(buttonName).contains(listener)){
            Log.d(TAG, "Subscribe button with name " + buttonName +  " is already subscribed");
            return;
        }
        onButtonListeners.get(buttonName).add(listener);

    }

    private void subscribeButtonRequest(ButtonName buttonName, final OnButtonListener listener){
        SubscribeButton subscribeButtonRequest = new SubscribeButton();

        subscribeButtonRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                //TODO remove testing logs
               // Log.i(TAG, "onResponse: got it" + response.getInfo());
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info){
                listener.onError(info);
            }

        });
        subscribeButtonRequest.setButtonName(buttonName);
        internalInterface.sendRPC(subscribeButtonRequest);
    }

    private void setRpcNotificaionListeners() {
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnButtonPress onButtonPressNotification = (OnButtonPress) notification;

                CopyOnWriteArrayList<OnButtonListener> listeners = onButtonListeners.get(onButtonPressNotification.getButtonName());

                if (listeners != null && listeners.size() > 0) {
                    for (OnButtonListener listener : listeners) {
                        listener.onPress(onButtonPressNotification.getButtonName(), onButtonPressNotification);
                       //TODO remove testing logs
                        // Log.i(TAG, "onNotified: 1 " + onButtonPressNotification.getButtonName() + " " + onButtonPressNotification.getFunctionName());
                    }
                }
            }
        });

        internalInterface.addOnRPCNotificationListener(FunctionID.ON_BUTTON_EVENT, new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnButtonEvent onButtonEvent = (OnButtonEvent) notification;

                CopyOnWriteArrayList<OnButtonListener> listeners = onButtonListeners.get(onButtonEvent.getButtonName());

                if (listeners != null && listeners.size() > 0) {
                    for (OnButtonListener listener : listeners) {
                        listener.onEvent(onButtonEvent.getButtonName(), onButtonEvent);
                        //TODO remove testing logs
                        //Log.i(TAG, "onNotified: 2 " + onButtonEvent.getButtonName() + " " + onButtonEvent.getButtonEventMode());
                    }
                }
            }
        });
    }

    void removeButtonListener(final ButtonName buttonName, final OnButtonListener listener){

        if(onButtonListeners.get(buttonName) == null){
            return;
        }
        if(!onButtonListeners.get(buttonName).contains(listener)){
            return;
        }
        onButtonListeners.get(buttonName).remove(listener);

        if(onButtonListeners.get(buttonName).size() > 0){
            return;
        }

        UnsubscribeButton unsubscribeButtonRequest = new UnsubscribeButton();
        unsubscribeButtonRequest.setButtonName(buttonName);
        unsubscribeButtonRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Log.d(TAG, "Successfully unsubscribed to subscribe button named " + buttonName);
            }
            @Override
            public void onError(int correlationId, Result resultCode, String info){
                Log.e(TAG, "Attempt to unsubscribe to subscribe button named " + buttonName );
                listener.onError(info);
            }
        });

        internalInterface.sendRPC(unsubscribeButtonRequest);
    }
}
