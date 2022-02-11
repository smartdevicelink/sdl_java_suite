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

import androidx.annotation.NonNull;

import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.managers.permission.PermissionManager;
import com.smartdevicelink.managers.screen.ScreenManager;
import com.smartdevicelink.protocol.ISdlServiceListener;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCRequestListener;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.util.List;

/**
 * <strong>SDLManager</strong> <br>
 * <p>
 * This is the main point of contact between an application and SDL <br>
 * <p>
 * It is broken down to these areas: <br>
 * <p>
 * 1. SDLManagerBuilder <br>
 * 2. ISdl Interface along with its overridden methods - This can be passed into attached managers <br>
 * 3. Sending Requests <br>
 * 4. Helper methods
 */
public class SdlManager extends BaseSdlManager {

    /**
     * Starts up a SdlManager, and calls provided callback called once all BaseSubManagers are done setting up
     */
    @Override
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                dispose();
            }
        });

        DebugTool.logInfo(TAG, "start");
        if (lifecycleManager == null) {
            if (transport != null && (transport.getTransportType().equals(TransportType.WEB_SOCKET_SERVER) || transport.getTransportType().equals(TransportType.CUSTOM))) {
                super.start();
                lifecycleManager.start();
            } else {
                throw new RuntimeException("No transport provided");
            }
        }
    }

    @Override
    protected void initialize() {
        // Instantiate sub managers
        this.permissionManager = new PermissionManager(_internalInterface);
        this.fileManager = new FileManager(_internalInterface, fileManagerConfig);
        this.screenManager = new ScreenManager(_internalInterface, this.fileManager);

        // Start sub managers
        this.permissionManager.start(subManagerListener);
        this.fileManager.start(subManagerListener);
        this.screenManager.start(subManagerListener);
    }

    @Override
    void checkState() {
        if (permissionManager != null && fileManager != null && screenManager != null) {
            if (permissionManager.getState() == BaseSubManager.READY && fileManager.getState() == BaseSubManager.READY && screenManager.getState() == BaseSubManager.READY) {
                DebugTool.logInfo(TAG, "Starting sdl manager, all sub managers are in ready state");
                transitionToState(BaseSubManager.READY);
                handleQueuedNotifications();
                notifyDevListener(null);
                onReady();
            } else if (permissionManager.getState() == BaseSubManager.ERROR && fileManager.getState() == BaseSubManager.ERROR && screenManager.getState() == BaseSubManager.ERROR) {
                String info = "ERROR starting sdl manager, all sub managers are in error state";
                DebugTool.logError(TAG, info);
                transitionToState(BaseSubManager.ERROR);
                notifyDevListener(info);
            } else if (permissionManager.getState() == BaseSubManager.SETTING_UP || fileManager.getState() == BaseSubManager.SETTING_UP || screenManager.getState() == BaseSubManager.SETTING_UP) {
                DebugTool.logInfo(TAG, "SETTING UP sdl manager, some sub managers are still setting up");
                transitionToState(BaseSubManager.SETTING_UP);
                // No need to notify developer here!
            } else {
                DebugTool.logWarning(TAG, "LIMITED starting sdl manager, some sub managers are in error or limited state and the others finished setting up");
                transitionToState(BaseSubManager.LIMITED);
                handleQueuedNotifications();
                notifyDevListener(null);
                onReady();
            }
        } else {
            // We should never be here, but somehow one of the sub-sub managers is null
            String info = "ERROR one of the sdl sub managers is null";
            DebugTool.logError(TAG, info);
            transitionToState(BaseSubManager.ERROR);
            notifyDevListener(info);
        }
    }

    private void notifyDevListener(String info) {
        if (managerListener != null) {
            if (getState() == BaseSubManager.ERROR) {
                managerListener.onError(this, info, null);
            } else {
                managerListener.onStart(this);
            }
        }
    }

    @Override
    void retryChangeRegistration() {
        // Do nothing
    }

    @Override
    public void dispose() {
        if (this.permissionManager != null) {
            this.permissionManager.dispose();
        }

        if (this.fileManager != null) {
            this.fileManager.dispose();
        }

        if (this.screenManager != null) {
            this.screenManager.dispose();
        }

        if (this.lifecycleManager != null) {
            this.lifecycleManager.stop();
        }

        if (managerListener != null) {
            managerListener.onDestroy(this);
            managerListener = null;
        }

        transitionToState(BaseSubManager.SHUTDOWN);
    }

    // BUILDER
    public static class Builder extends BaseSdlManager.Builder {
        /**
         * Builder for the SdlManager. Parameters in the constructor are required.
         *
         * @param appId    the app's ID
         * @param appName  the app's name
         * @param listener a SdlManagerListener object
         */
        public Builder(@NonNull final String appId, @NonNull final String appName, @NonNull final SdlManagerListener listener) {
            super(appId, appName, listener);
        }
    }

    private ISdl _internalInterface = new ISdl() {
        @Override
        public void start() {
            lifecycleManager.getInternalInterface(SdlManager.this).start();
        }

        @Override
        public void stop() {
            lifecycleManager.getInternalInterface(SdlManager.this).start();
        }

        @Override
        public boolean isConnected() {
            return lifecycleManager.getInternalInterface(SdlManager.this).isConnected();
        }

        @Override
        public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {
            lifecycleManager.getInternalInterface(SdlManager.this).addServiceListener(serviceType, sdlServiceListener);
        }

        @Override
        public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {
            lifecycleManager.getInternalInterface(SdlManager.this).removeServiceListener(serviceType, sdlServiceListener);
        }

        @Override
        public void startVideoService(VideoStreamingParameters parameters, boolean encrypted, boolean withPendingRestart) {
            lifecycleManager.getInternalInterface(SdlManager.this).startVideoService(parameters, encrypted, withPendingRestart);
        }

        @Override
        public void startAudioService(boolean encrypted) {
            lifecycleManager.getInternalInterface(SdlManager.this).startAudioService(encrypted);
        }

        @Override
        public void sendRPC(RPCMessage message) {
            lifecycleManager.getInternalInterface(SdlManager.this).sendRPC(message);
        }

        @Override
        public void sendRPCs(List<? extends RPCMessage> rpcs, OnMultipleRequestListener listener) {
            lifecycleManager.getInternalInterface(SdlManager.this).sendRPCs(rpcs, listener);
        }

        @Override
        public void sendSequentialRPCs(List<? extends RPCMessage> rpcs, OnMultipleRequestListener listener) {
            lifecycleManager.getInternalInterface(SdlManager.this).sendSequentialRPCs(rpcs, listener);
        }

        @Override
        public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
            lifecycleManager.getInternalInterface(SdlManager.this).addOnRPCNotificationListener(notificationId, listener);
        }

        @Override
        public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
            return lifecycleManager.getInternalInterface(SdlManager.this).removeOnRPCNotificationListener(notificationId, listener);
        }

        @Override
        public void addOnRPCRequestListener(FunctionID functionID, OnRPCRequestListener listener) {
            lifecycleManager.getInternalInterface(SdlManager.this).addOnRPCRequestListener(functionID, listener);
        }

        @Override
        public boolean removeOnRPCRequestListener(FunctionID functionID, OnRPCRequestListener listener) {
            return lifecycleManager.getInternalInterface(SdlManager.this).removeOnRPCRequestListener(functionID, listener);
        }

        @Override
        public void addOnRPCListener(FunctionID responseId, OnRPCListener listener) {
            lifecycleManager.getInternalInterface(SdlManager.this).addOnRPCListener(responseId, listener);
        }

        @Override
        public boolean removeOnRPCListener(FunctionID responseId, OnRPCListener listener) {
            return lifecycleManager.getInternalInterface(SdlManager.this).removeOnRPCListener(responseId, listener);
        }

        @Override
        public RegisterAppInterfaceResponse getRegisterAppInterfaceResponse() {
            return lifecycleManager.getInternalInterface(SdlManager.this).getRegisterAppInterfaceResponse();
        }

        @Override
        public boolean isTransportForServiceAvailable(SessionType serviceType) {
            return lifecycleManager.getInternalInterface(SdlManager.this).isTransportForServiceAvailable(serviceType);
        }

        @NonNull
        @Override
        public SdlMsgVersion getSdlMsgVersion() {
            return lifecycleManager.getInternalInterface(SdlManager.this).getSdlMsgVersion();
        }

        @NonNull
        @Override
        public Version getProtocolVersion() {
            return lifecycleManager.getInternalInterface(SdlManager.this).getProtocolVersion();
        }

        @Override
        public long getMtu(SessionType serviceType) {
            return lifecycleManager.getInternalInterface(SdlManager.this).getMtu(serviceType);
        }

        @Override
        public void startRPCEncryption() {
            lifecycleManager.getInternalInterface(SdlManager.this).startRPCEncryption();
        }

        @Override
        public Taskmaster getTaskmaster() {
            return lifecycleManager.getInternalInterface(SdlManager.this).getTaskmaster();
        }

        @Override
        public SystemCapabilityManager getSystemCapabilityManager() {
            return lifecycleManager.getInternalInterface(SdlManager.this).getSystemCapabilityManager();
        }

        @Override
        public PermissionManager getPermissionManager() {
            return permissionManager;
        }
    };
}
