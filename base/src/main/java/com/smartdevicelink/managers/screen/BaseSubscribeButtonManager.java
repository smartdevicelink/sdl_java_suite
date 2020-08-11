package com.smartdevicelink.managers.screen;

import androidx.annotation.NonNull;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
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
import com.smartdevicelink.util.DebugTool;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <strong>SubscribeButtonManager</strong> <br>
 * <p>
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 */
abstract class BaseSubscribeButtonManager extends BaseSubManager {

    private static final String TAG = "SubscribeButtonManager";
    HashMap<ButtonName, CopyOnWriteArrayList<OnButtonListener>> onButtonListeners;
    private OnRPCNotificationListener onButtonPressListener;
    private OnRPCNotificationListener onButtonEventListener;

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

    @Override
    public void dispose() {
        super.dispose();
        if (onButtonListeners != null) {
            onButtonListeners.clear();
        }
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, onButtonPressListener);
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_BUTTON_EVENT, onButtonEventListener);
    }

    /***
     * Checks to see if Button is already subscribed and adds listener to hashmap.
     * If button is not already subscribed to, it call method:
     * subscribeButtonRequest to send RPC request
     * @param buttonName - Is the button that the developer wants to subscribe to
     * @param listener - Is the listener that was sent by developer
     */
    void addButtonListener(ButtonName buttonName, OnButtonListener listener) {
        if (listener == null) {
            DebugTool.logError(TAG, "OnButtonListener cannot be null");
            return;
        }
        if (buttonName == null) {
            listener.onError("ButtonName cannot be null");
            return;
        }

        if (onButtonListeners.get(buttonName) == null) {
            subscribeButtonRequest(buttonName, listener);
            return;
        }

        if (onButtonListeners.get(buttonName).contains(listener)) {
            DebugTool.logWarning(TAG, "Already subscribed to button named: " + buttonName);
            return;
        }
        onButtonListeners.get(buttonName).add(listener);
    }

    /**
     * Unsubscribe form button and/or listener sent by developer
     *
     * @param buttonName Is the button that the developer wants to unsubscribe from
     * @param listener   - the listener that was sent by developer
     */
    void removeButtonListener(final ButtonName buttonName, final OnButtonListener listener) {
        if (listener == null) {
            DebugTool.logError(TAG, "OnButtonListener cannot be null: ");
            return;
        }

        if (buttonName == null) {
            listener.onError("ButtonName cannot be null");
            return;
        }

        if (onButtonListeners.get(buttonName) == null || !onButtonListeners.get(buttonName).contains(listener)) {
            listener.onError("Attempting to unsubscribe to the " + buttonName + " button failed because it is not currently subscribed");
            return;
        }

        if (onButtonListeners.get(buttonName).size() > 1) {
            onButtonListeners.get(buttonName).remove(listener);
            return;
        }
        unsubscribeButtonRequest(buttonName, listener);
    }

    /**
     * Send the UnsubscribeButton RPC
     *
     * @param buttonName - ButtonName - name of button
     * @param listener - OnButtonListener - listener to get notified
     */
    private void unsubscribeButtonRequest(final ButtonName buttonName, final OnButtonListener listener) {
        UnsubscribeButton unsubscribeButtonRequest = new UnsubscribeButton(buttonName);
        unsubscribeButtonRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                onButtonListeners.remove(buttonName);
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                listener.onError("Attempt to unsubscribe to button named " + buttonName + " Failed. ResultCode: " + resultCode + " info: " + info);
            }
        });
        internalInterface.sendRPC(unsubscribeButtonRequest);
    }

    /**
     * Send the SubscribeButton RPC
     *
     * @param buttonName - ButtonName - name of button
     * @param listener   - OnButtonListener - listener to get notified
     */
    private void subscribeButtonRequest(final ButtonName buttonName, final OnButtonListener listener) {
        SubscribeButton subscribeButtonRequest = new SubscribeButton(buttonName);
        subscribeButtonRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                onButtonListeners.put(buttonName, new CopyOnWriteArrayList<OnButtonListener>());
                onButtonListeners.get(buttonName).add(listener);
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                listener.onError("Attempt to subscribe to button named " + buttonName + " Failed . ResultCode: " + resultCode + " info: " + info);
            }
        });
        internalInterface.sendRPC(subscribeButtonRequest);
    }

    /**
     * Sets up RpcNotificationListeners for button presses and events, is setup when manager is created
     */
    private void setRpcNotificationListeners() {
        onButtonPressListener = new OnRPCNotificationListener() {
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
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, onButtonPressListener);

        onButtonEventListener = new OnRPCNotificationListener() {
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
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_BUTTON_EVENT, onButtonEventListener);
    }
}
