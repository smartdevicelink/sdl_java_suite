/*
 * Copyright (c) 2020 Livio, Inc.
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

package com.smartdevicelink.managers.screen;

import androidx.annotation.NonNull;

import com.livio.taskmaster.Queue;
import com.smartdevicelink.managers.AlertCompletionListener;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.managers.permission.OnPermissionChangeListener;
import com.smartdevicelink.managers.permission.PermissionElement;
import com.smartdevicelink.managers.permission.PermissionStatus;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;


abstract class BaseAlertManager extends BaseSubManager {

    private static final String TAG = "BaseAlertManager";
    Queue transactionQueue;
    WindowCapability defaultMainWindowCapability;
    private OnSystemCapabilityListener onSpeechCapabilityListener, onDisplaysCapabilityListener;
    List<SpeechCapabilities> speechCapabilities;
    private UUID permissionListener;
    boolean currentAlertPermissionStatus = false;
    private final WeakReference<FileManager> fileManager;
    int nextCancelId;
    final int alertCancelIdMin = 20000;

    public BaseAlertManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {
        super(internalInterface);
        this.transactionQueue = newTransactionQueue();
        this.fileManager = new WeakReference<>(fileManager);
        nextCancelId = alertCancelIdMin;
        addListeners();
    }

    @Override
    public void start(CompletionListener listener) {
        transitionToState(READY);
        super.start(listener);
    }

    @Override
    public void dispose() {
        defaultMainWindowCapability = null;
        speechCapabilities = null;
        currentAlertPermissionStatus = false;

        if (transactionQueue != null) {
            transactionQueue.close();
            transactionQueue = null;
        }

        // remove listeners
        if (internalInterface.getSystemCapabilityManager() != null) {
            internalInterface.getSystemCapabilityManager().removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplaysCapabilityListener);
            internalInterface.getSystemCapabilityManager().removeOnSystemCapabilityListener(SystemCapabilityType.SPEECH, onSpeechCapabilityListener);
        }
        if (internalInterface.getPermissionManager() != null) {
            internalInterface.getPermissionManager().removeListener(permissionListener);
        }
        super.dispose();
    }

    /**
     * Creates a PresentAlertOperation and adds it to the transactionQueue
     *
     * @param alert    - AlertView object that contains alert information
     * @param listener - AlertCompletionListener that will notify the sender when Alert has completed
     */
    public void presentAlert(AlertView alert, AlertCompletionListener listener) {
        if (getState() == ERROR) {
            DebugTool.logWarning(TAG, "Alert Manager In Error State");
            return;
        }

        // Check for softButtons and assign them ID's, Behavior mimic SoftButtonManager,
        // as in if invalid ID's are set, Alert will not show up.
        // It's best if ID's are not set custom and allow the screenManager to set them.
        if (alert.getSoftButtons() != null) {
            if (!BaseScreenManager.checkAndAssignButtonIds(alert.getSoftButtons(), BaseScreenManager.ManagerLocation.ALERT_MANAGER)) {
                DebugTool.logError(TAG, "Attempted to set soft button objects for Alert, but multiple buttons had the same id.");
                return;
            }
        }
        PresentAlertOperation operation = new PresentAlertOperation(internalInterface, alert, defaultMainWindowCapability, speechCapabilities, fileManager.get(), nextCancelId++, listener);
        transactionQueue.add(operation, false);

    }

    private Queue newTransactionQueue() {
        Queue queue = internalInterface.getTaskmaster().createQueue("AlertManager", 4, false);
        queue.pause();
        return queue;
    }

    // Suspend the queue if the WindowCapabilities are null
    // OR if the HMI level is NONE since we want to delay sending RPCs until we're in non-NONE
    private void updateTransactionQueueSuspended() {
        if (!currentAlertPermissionStatus || defaultMainWindowCapability == null) {
            DebugTool.logInfo(TAG, String.format("Suspending the transaction queue. Current permission status is false: %b, window capabilities are null: %b", currentAlertPermissionStatus, defaultMainWindowCapability == null));
            transactionQueue.pause();
        } else {
            DebugTool.logInfo(TAG, "Starting the transaction queue");
            transactionQueue.resume();
        }
    }


    private void addListeners() {
        // Retrieves SpeechCapabilities of the system.
        onSpeechCapabilityListener = new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                speechCapabilities = (List<SpeechCapabilities>) capability;
            }

            @Override
            public void onError(String info) {

            }
        };
        if (internalInterface.getSystemCapabilityManager() != null) {
            this.internalInterface.getSystemCapabilityManager().getCapability(SystemCapabilityType.SPEECH, onSpeechCapabilityListener, false);
        }
        // Retrieves WindowCapability of the system, if WindowCapability are null, queue pauses
        onDisplaysCapabilityListener = new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                // instead of using the parameter it's more safe to use the convenience method
                List<DisplayCapability> capabilities = SystemCapabilityManager.convertToList(capability, DisplayCapability.class);
                if (capabilities == null || capabilities.size() == 0) {
                    defaultMainWindowCapability = null;
                } else {
                    DisplayCapability display = capabilities.get(0);
                    for (WindowCapability windowCapability : display.getWindowCapabilities()) {
                        int currentWindowID = windowCapability.getWindowID() != null ? windowCapability.getWindowID() : PredefinedWindows.DEFAULT_WINDOW.getValue();
                        if (currentWindowID != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                            continue;
                        }
                        defaultMainWindowCapability = windowCapability;
                        break;
                    }
                }
                // Update the queue's suspend state
                updateTransactionQueueSuspended();
            }

            @Override
            public void onError(String info) {
                DebugTool.logError(TAG, "Display Capability cannot be retrieved");
                defaultMainWindowCapability = null;
                updateTransactionQueueSuspended();
            }
        };
        if (internalInterface.getSystemCapabilityManager() != null) {
            this.internalInterface.getSystemCapabilityManager().addOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplaysCapabilityListener);
        }

        // Listener listening if RPC is allowed by system at any given point, will pause the queue if not allowed.
        PermissionElement alertPermissionElement = new PermissionElement(FunctionID.ALERT, null);
        permissionListener = internalInterface.getPermissionManager().addListener(Collections.singletonList(alertPermissionElement), internalInterface.getPermissionManager().PERMISSION_GROUP_TYPE_ANY, new OnPermissionChangeListener() {
            @Override
            public void onPermissionsChange(@NonNull Map<FunctionID, PermissionStatus> allowedPermissions, int permissionGroupStatus) {
                if (allowedPermissions.get(FunctionID.ALERT) != null) {
                    currentAlertPermissionStatus = allowedPermissions.get(FunctionID.ALERT).getIsRPCAllowed();
                } else {
                    currentAlertPermissionStatus = false;
                }
                updateTransactionQueueSuspended();
            }
        });
    }
}
