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

package com.smartdevicelink.managers.lifecycle;

import android.support.annotation.NonNull;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.managers.ServiceEncryptionListener;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract class BaseEncryptionLifecycleManager {
    private static final String TAG = "BaseEncryptionLifecycleManager";
    private ISdl internalInterface;
    private ServiceEncryptionListener serviceEncryptionListener;
    private HMILevel currentHMILevel;
    private Set<String> encryptionRequiredRPCs = new HashSet<>();
    private boolean rpcSecuredServiceStarted;
    ISdlServiceListener securedServiceListener;

    BaseEncryptionLifecycleManager(@NonNull ISdl isdl, ServiceEncryptionListener listener) {
        internalInterface = isdl;
        serviceEncryptionListener = listener;
        rpcSecuredServiceStarted = false;

        OnRPCNotificationListener onHMIStatusListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
                currentHMILevel = onHMIStatus.getHmiLevel();
                checkStatusAndInitSecuredService();
            }
        };

        OnRPCNotificationListener onPermissionsChangeListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                List<PermissionItem> permissionItems = ((OnPermissionsChange) notification).getPermissionItem();
                Boolean requireEncryptionAppLevel = ((OnPermissionsChange) notification).getRequireEncryption();
                encryptionRequiredRPCs.clear();
                if (requireEncryptionAppLevel == null || requireEncryptionAppLevel) {
                    if (permissionItems != null && !permissionItems.isEmpty()) {
                        for (PermissionItem permissionItem : permissionItems) {
                            if (permissionItem != null && Boolean.TRUE.equals(permissionItem.getRequireEncryption())) {
                                String rpcName = permissionItem.getRpcName();
                                if (rpcName != null) {
                                    encryptionRequiredRPCs.add(rpcName);
                                }
                            }
                        }
                    }
                    checkStatusAndInitSecuredService();
                }
            }
        };

        securedServiceListener = new ISdlServiceListener() {
            @Override
            public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {
                if(SessionType.RPC.equals(type)){
                    rpcSecuredServiceStarted = isEncrypted;
                }
                if (serviceEncryptionListener != null) {
                    serviceEncryptionListener.onEncryptionServiceUpdated(type, isEncrypted, null);
                }
                DebugTool.logInfo(TAG, "onServiceStarted, session Type: " + type.getName() + ", isEncrypted: " + isEncrypted);
            }

            @Override
            public void onServiceEnded(SdlSession session, SessionType type) {
                if (SessionType.RPC.equals(type)) {
                    rpcSecuredServiceStarted = false;
                }
                if (serviceEncryptionListener != null) {
                    serviceEncryptionListener.onEncryptionServiceUpdated(type, false, null);
                }
                DebugTool.logInfo(TAG, "onServiceEnded, session Type: " + type.getName());
            }

            @Override
            public void onServiceError(SdlSession session, SessionType type, String reason) {
                if (SessionType.RPC.equals(type)) {
                    rpcSecuredServiceStarted = false;
                }
                if (serviceEncryptionListener != null) {
                    serviceEncryptionListener.onEncryptionServiceUpdated(type, false, "onServiceError: " + reason);
                }
                DebugTool.logError(TAG, "onServiceError, session Type: " + type.getName() + ", reason: " + reason);
            }
        };

        internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_PERMISSIONS_CHANGE, onPermissionsChangeListener);
        internalInterface.addServiceListener(SessionType.RPC, securedServiceListener);
    }

    /**
     * Gets the app level encryption requirement
     *
     * @return true if encryption is required for app level; false otherwise
     */
    private boolean getRequiresEncryption() {
        return !encryptionRequiredRPCs.isEmpty();
    }

    /**
     * Checks if an RPC requires encryption
     *
     * @param rpcName the rpc name to check
     * @return true if the given RPC requires encryption; false, otherwise
     */
    boolean getRPCRequiresEncryption(@NonNull FunctionID rpcName) {
        return encryptionRequiredRPCs.contains(rpcName.toString());
    }

    /**
     * Checks the current state and make the call back to initiate secured service flow
     */
    private void checkStatusAndInitSecuredService() {
        if ((currentHMILevel != null && currentHMILevel != HMILevel.HMI_NONE) && getRequiresEncryption() && !isEncryptionReady() ) {
            internalInterface.startRPCEncryption();
        }
    }

    /**
     * Check to see if a secured service is ready to use
     *
     * @return true if there is a secured service; false otherwise
     */
    boolean isEncryptionReady() {
        return rpcSecuredServiceStarted;
    }

    /**
     * Clean up everything after the manager is no longer needed
     */
    void dispose() {
        rpcSecuredServiceStarted = false;
        encryptionRequiredRPCs.clear();
        serviceEncryptionListener = null;
    }
}
