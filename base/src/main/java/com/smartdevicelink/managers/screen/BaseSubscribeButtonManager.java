package com.smartdevicelink.managers.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.OnButtonListener;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
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

abstract class BaseSubscribeButtonManager extends BaseSubManager {

    private static final String TAG = "SubscribeButtonManager";
    private final HashMap<ButtonName, CopyOnWriteArrayList<OnButtonListener>> onButtonListeners;

    BaseSubscribeButtonManager(@NonNull ISdl internalInterface) {
        super(internalInterface);
        setRpcNotificationListeners();
        onButtonListeners = new HashMap<>();
    }

    @Override
    public void start(CompletionListener listener) {
        transitionToState(READY);
        super.start(listener);
    }

    void addButtonListener(ButtonName buttonName, OnButtonListener listener) {

        if (listener == null) {
            Log.e(TAG, "OnButtonListener cannot be null: ");
            return;
        }
        if (buttonName == null) {
            listener.onError("ButtonName cannot be null");
            Log.e(TAG, "ButtonName cannot be null");
            return;
        }

        if (onButtonListeners.get(buttonName) == null) {
            onButtonListeners.put(buttonName, new CopyOnWriteArrayList<OnButtonListener>());
            subscribeButtonRequest(buttonName, listener);
        }
        if (onButtonListeners.get(buttonName).contains(listener)) {
            Log.d(TAG, "Subscribe button with name " + buttonName + " is already subscribed");
            return;
        }
        onButtonListeners.get(buttonName).add(listener);
    }

    void removeButtonListener(final ButtonName buttonName, final OnButtonListener listener) {

        if (onButtonListeners.get(buttonName) == null) {
            return;
        }
        if (!onButtonListeners.get(buttonName).contains(listener)) {
            return;
        }
        onButtonListeners.get(buttonName).remove(listener);

        if (onButtonListeners.get(buttonName).size() > 0) {
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
            public void onError(int correlationId, Result resultCode, String info) {
                Log.e(TAG, "Attempt to unsubscribe to subscribe button named " + buttonName);
                listener.onError(info);
            }
        });

        internalInterface.sendRPC(unsubscribeButtonRequest);
    }

    private void subscribeButtonRequest(final ButtonName buttonName, final OnButtonListener listener) {
        SubscribeButton subscribeButtonRequest = new SubscribeButton();

        subscribeButtonRequest.setOnRPCResponseListener(new OnRPCResponseListener() {

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Log.d(TAG, "Successfully subscribed to button named " + buttonName);
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                Log.e(TAG, "Attempt to subscribe to subscribe button named " + buttonName + " " + info);
                listener.onError(info);
            }
        });

        subscribeButtonRequest.setButtonName(buttonName);
        internalInterface.sendRPC(subscribeButtonRequest);
    }

    private void setRpcNotificationListeners() {
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnButtonPress onButtonPressNotification = (OnButtonPress) notification;
                CopyOnWriteArrayList<OnButtonListener> listeners = onButtonListeners.get(onButtonPressNotification.getButtonName());

                if (listeners != null && listeners.size() > 0) {
                    for (OnButtonListener listener : listeners) {
                        listener.onPress(onButtonPressNotification.getButtonName(), onButtonPressNotification);
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
                    }
                }
            }
        });
    }
}
