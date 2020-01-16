/*
 * Copyright (c) 2019 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.managers;

import android.util.Log;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCRequestListener;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

abstract class BaseSdlManager {

    private static final String TAG = "BaseSubManager";

    final Object STATE_LOCK = new Object();
    int state = -1;

    static final int MAX_RETRY = 3;
    int changeRegistrationRetry = 0;
    String appId, appName, shortAppName;
    boolean isMediaApp;
    Language hmiLanguage;
    Language language;
    Vector<AppHMIType> hmiTypes;
    BaseTransportConfig transport;
    Vector<String> vrSynonyms;
    Vector<TTSChunk> ttsChunks;
    TemplateColorScheme dayColorScheme, nightColorScheme;
    Version minimumProtocolVersion;
    Version minimumRPCVersion;

    Queue<RPCNotification> queuedNotifications = null;
    OnRPCNotificationListener queuedNotificationListener = null;
    Map<FunctionID, OnRPCNotificationListener> onRPCNotificationListeners;

    // PROTECTED GETTERS
    protected String getAppName() { return appName; }

    protected String getAppId() { return appId; }

    protected String getShortAppName() { return shortAppName; }

    protected Version getMinimumProtocolVersion() { return minimumProtocolVersion; }

    protected Version getMinimumRPCVersion() { return minimumRPCVersion; }

    protected Language getHmiLanguage() { return hmiLanguage; }

    protected Language getLanguage() { return language; }

    protected TemplateColorScheme getDayColorScheme() { return dayColorScheme; }

    protected TemplateColorScheme getNightColorScheme() { return nightColorScheme; }

    protected Vector<AppHMIType> getAppTypes() { return hmiTypes; }

    protected Vector<String> getVrSynonyms() { return vrSynonyms; }

    protected Vector<TTSChunk> getTtsChunks() { return ttsChunks; }

    protected BaseTransportConfig getTransport() { return transport; }


    /**
     * Get the current state for the SdlManager
     * @return int value that represents the current state
     * @see BaseSubManager
     */
    public int getState() {
        synchronized (STATE_LOCK) {
            return state;
        }
    }

    protected void transitionToState(int state) {
        synchronized (STATE_LOCK) {
            this.state = state;
        }
    }

    void checkSdlManagerState(){
        if (getState() != BaseSubManager.READY && getState() != BaseSubManager.LIMITED){
            Log.e(TAG, "SdlManager is not ready for use, be sure to initialize with start() method, implement callback, and use SubManagers in the SdlManager's callback");
        }
    }

    void initNotificationQueue(){
        //Setup the notification queue
        if (onRPCNotificationListeners != null) {
            Set<FunctionID> functionIDSet = onRPCNotificationListeners.keySet();
            if (functionIDSet != null && !functionIDSet.isEmpty()) {
                queuedNotifications = new ConcurrentLinkedQueue<RPCNotification>();
                queuedNotificationListener = new OnRPCNotificationListener() {
                    @Override
                    public void onNotified(RPCNotification notification) {
                        queuedNotifications.add(notification);
                    }
                };
                for (FunctionID functionID : functionIDSet) {
                    addOnRPCNotificationListener(functionID, queuedNotificationListener);
                }
            }
        }
    }

    void handleQueuedNotifications(){
        //Handle queued notifications and add the listeners
        if (onRPCNotificationListeners != null) {
            Set<FunctionID> functionIDSet = onRPCNotificationListeners.keySet();
            if (queuedNotifications != null && queuedNotifications.size() > 0) {
                for (RPCNotification notification : queuedNotifications) {
                    try {
                        onRPCNotificationListeners.get(notification.getFunctionID()).onNotified(notification);
                    } catch (Exception e) {
                        DebugTool.logError("Error going through queued notifications", e);
                    }
                }
            }

            //Swap queued listener for developer's listeners
            if (functionIDSet != null && !functionIDSet.isEmpty()) {
                for (FunctionID functionID : functionIDSet) {
                    //Remove the old queue listener
                    removeOnRPCNotificationListener(functionID, queuedNotificationListener);
                    //Add the developer listener
                    addOnRPCNotificationListener(functionID, onRPCNotificationListeners.get(functionID));
                }
            }
            //Set variables to null that are no longer needed
            queuedNotifications = null;
            queuedNotificationListener = null;
            onRPCNotificationListeners = null;
        }
    }

    abstract void checkState();

    protected abstract void initialize();
    protected abstract void checkLifecycleConfiguration();

    //Public abstract API
    public abstract  void start();
    public abstract void dispose();
    public abstract void sendRPC(RPCMessage message);
    public abstract void sendSequentialRPCs(final List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener);
    public abstract void sendRPCs(List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener);
    public abstract RegisterAppInterfaceResponse getRegisterAppInterfaceResponse();
    public abstract OnHMIStatus getCurrentHMIStatus();
    public abstract String getAuthToken();

    public abstract void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener);

    /**
     * Remove an OnRPCNotificationListener
     * @param listener listener that was previously added
     */
    public abstract void removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener);

    /**
     * Add an OnRPCRequestListener
     * @param listener listener that will be called when a request is received
     */
    public abstract void addOnRPCRequestListener(FunctionID requestId, OnRPCRequestListener listener);

    /**
     * Remove an OnRPCRequestListener
     * @param listener listener that was previously added
     */
    public abstract void removeOnRPCRequestListener(FunctionID requestId, OnRPCRequestListener listener);

}
