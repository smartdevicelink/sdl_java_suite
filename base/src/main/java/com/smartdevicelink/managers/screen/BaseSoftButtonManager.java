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
package com.smartdevicelink.managers.screen;

import androidx.annotation.NonNull;

import com.livio.taskmaster.Queue;
import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <strong>SoftButtonManager</strong> <br>
 * SoftButtonManager gives the developer the ability to control how soft buttons are displayed on the head unit.<br>
 * Note: This class must be accessed through the SdlManager->ScreenManager. Do not instantiate it by itself.<br>
 */
abstract class BaseSoftButtonManager extends BaseSubManager {
    private static final String TAG = "BaseSoftButtonManager";
    private final WeakReference<FileManager> fileManager;
    SoftButtonCapabilities softButtonCapabilities;
    boolean isGraphicSupported;
    private CopyOnWriteArrayList<SoftButtonObject> softButtonObjects;
    private HMILevel currentHMILevel;
    private DisplayCapabilities displayCapabilities;
    private final OnSystemCapabilityListener onDisplayCapabilityListener;
    private final OnRPCNotificationListener onHMIStatusListener, onButtonPressListener, onButtonEventListener;
    private Queue transactionQueue;
    private final List<Task> batchQueue;
    private boolean batchUpdates;
    private final SoftButtonObject.UpdateListener updateListener;

    /**
     * HAX: This is necessary due to a Ford Sync 3 bug that doesn't like Show requests without a main field being set (it will accept them, but with a GENERIC_ERROR, and 10-15 seconds late...)
     */
    private String currentMainField1;

    /**
     * Creates a new instance of the SoftButtonManager
     *
     * @param internalInterface an instance of the ISdl interface that can be used for common SDL operations (sendRpc, addRpcListener, etc)
     * @param fileManager       an instance of the FileManager so that button graphics can be sent
     */
    BaseSoftButtonManager(@NonNull final ISdl internalInterface, @NonNull final FileManager fileManager) {
        super(internalInterface);

        this.fileManager = new WeakReference<>(fileManager);
        this.softButtonObjects = new CopyOnWriteArrayList<>();
        this.currentHMILevel = null;
        this.transactionQueue = newTransactionQueue();
        this.batchQueue = new ArrayList<>();

        if (internalInterface.getSystemCapabilityManager() != null) {
            displayCapabilities = (DisplayCapabilities) this.internalInterface.getSystemCapabilityManager().getCapability(SystemCapabilityType.DISPLAY, null, false);
        }
        isGraphicSupported = (displayCapabilities != null && displayCapabilities.getGraphicSupported() != null) ? displayCapabilities.getGraphicSupported() : false;

        this.updateListener = new SoftButtonObject.UpdateListener() {
            @Override
            public void onUpdate() {
                transitionSoftButton();
            }
        };

        // Add OnHMIStatusListener to keep currentHMILevel updated
        this.onHMIStatusListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                    return;
                }
                currentHMILevel = onHMIStatus.getHmiLevel();
                updateTransactionQueueSuspended();
            }
        };
        this.internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);

        // Add OnSoftButtonCapabilitiesListener to keep softButtonCapabilities updated
        onDisplayCapabilityListener = new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                SoftButtonCapabilities oldSoftButtonCapabilities = softButtonCapabilities;

                // Extract and update the capabilities
                // instead of using the parameter it's more safe to use the convenience method
                List<DisplayCapability> capabilities = SystemCapabilityManager.convertToList(capability, DisplayCapability.class);
                if (capabilities == null || capabilities.size() == 0) {
                    softButtonCapabilities = null;
                } else {
                    DisplayCapability mainDisplay = capabilities.get(0);
                    for (WindowCapability windowCapability : mainDisplay.getWindowCapabilities()) {
                        int currentWindowID = windowCapability.getWindowID() != null ? windowCapability.getWindowID() : PredefinedWindows.DEFAULT_WINDOW.getValue();
                        if (currentWindowID == PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                            if (windowCapability.getSoftButtonCapabilities() != null && windowCapability.getSoftButtonCapabilities().size() > 0) {
                                softButtonCapabilities = windowCapability.getSoftButtonCapabilities().get(0);
                            } else {
                                softButtonCapabilities = null;
                            }
                            break;
                        }
                    }
                }

                // Update the queue's suspend state
                updateTransactionQueueSuspended();

                // Auto-send an updated Show if we have new capabilities
                if (softButtonObjects != null && !softButtonObjects.isEmpty() && softButtonCapabilities != null && !softButtonCapabilitiesEquals(oldSoftButtonCapabilities, softButtonCapabilities)) {
                    SoftButtonReplaceOperation operation = new SoftButtonReplaceOperation(internalInterface, fileManager, softButtonCapabilities, softButtonObjects, getCurrentMainField1(), isGraphicSupported);
                    transactionQueue.add(operation, false);
                }
            }

            @Override
            public void onError(String info) {
                DebugTool.logError(TAG, "Display Capability cannot be retrieved");
                softButtonCapabilities = null;

                // Update the queue's suspend state
                updateTransactionQueueSuspended();
            }
        };
        if (internalInterface.getSystemCapabilityManager() != null) {
            this.internalInterface.getSystemCapabilityManager().addOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplayCapabilityListener);
        }

        // Add OnButtonPressListener to notify SoftButtonObjects when there is a button press
        this.onButtonPressListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnButtonPress onButtonPress = (OnButtonPress) notification;
                if (onButtonPress != null && onButtonPress.getButtonName() == ButtonName.CUSTOM_BUTTON) {
                    Integer buttonId = onButtonPress.getCustomButtonID();
                    if (getSoftButtonObjects() != null) {
                        for (SoftButtonObject softButtonObject : getSoftButtonObjects()) {
                            if (softButtonObject.getButtonId() == buttonId && softButtonObject.getOnEventListener() != null) {
                                softButtonObject.getOnEventListener().onPress(getSoftButtonObjectById(buttonId), onButtonPress);
                                break;
                            }
                        }
                    }
                }
            }
        };
        this.internalInterface.addOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, onButtonPressListener);

        // Add OnButtonEventListener to notify SoftButtonObjects when there is a button event
        this.onButtonEventListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnButtonEvent onButtonEvent = (OnButtonEvent) notification;
                if (onButtonEvent != null && onButtonEvent.getButtonName() == ButtonName.CUSTOM_BUTTON) {
                    Integer buttonId = onButtonEvent.getCustomButtonID();
                    if (getSoftButtonObjects() != null) {
                        for (SoftButtonObject softButtonObject : getSoftButtonObjects()) {
                            if (softButtonObject.getButtonId() == buttonId && softButtonObject.getOnEventListener() != null) {
                                softButtonObject.getOnEventListener().onEvent(getSoftButtonObjectById(buttonId), onButtonEvent);
                                break;
                            }
                        }
                    }
                }
            }
        };
        this.internalInterface.addOnRPCNotificationListener(FunctionID.ON_BUTTON_EVENT, onButtonEventListener);
    }

    @Override
    public void start(CompletionListener listener) {
        transitionToState(READY);
        super.start(listener);
    }

    /**
     * Clean up everything after the manager is no longer needed
     */
    @Override
    public void dispose() {
        super.dispose();

        softButtonObjects = new CopyOnWriteArrayList<>();
        currentMainField1 = null;
        currentHMILevel = null;
        softButtonCapabilities = null;

        // Cancel the operations
        if (transactionQueue != null) {
            transactionQueue.close();
            transactionQueue = null;
        }

        // Remove listeners
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, onButtonPressListener);
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_BUTTON_EVENT, onButtonEventListener);
        if (internalInterface.getSystemCapabilityManager() != null) {
            internalInterface.getSystemCapabilityManager().removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplayCapabilityListener);
        }
    }

    private Queue newTransactionQueue() {
        Queue queue = internalInterface.getTaskmaster().createQueue("SoftButtonManager", 2, false);
        queue.pause();
        return queue;
    }

    // Suspend the queue if the soft button capabilities are null (we assume that soft buttons are not supported)
    // OR if the HMI level is NONE since we want to delay sending RPCs until we're in non-NONE
    private void updateTransactionQueueSuspended() {
        if (softButtonCapabilities == null || HMILevel.HMI_NONE.equals(currentHMILevel)) {
            DebugTool.logInfo(TAG, String.format("Suspending the transaction queue. Current HMI level is NONE: %b, soft button capabilities are null: %b", HMILevel.HMI_NONE.equals(currentHMILevel), softButtonCapabilities == null));
            transactionQueue.pause();
        } else {
            DebugTool.logInfo(TAG, "Starting the transaction queue");
            transactionQueue.resume();
        }
    }

    /**
     * Get the soft button objects list
     *
     * @return a List<SoftButtonObject>
     */
    protected List<SoftButtonObject> getSoftButtonObjects() {
        return softButtonObjects;
    }

    /**
     * Set softButtonObjects list and upload the images to the head unit
     *
     * @param list the list of the SoftButtonObject values that should be displayed on the head unit
     */
    protected void setSoftButtonObjects(@NonNull List<SoftButtonObject> list) {
        // Convert the List to CopyOnWriteArrayList
        CopyOnWriteArrayList<SoftButtonObject> softButtonObjects;
        if (list instanceof CopyOnWriteArrayList) {
            softButtonObjects = (CopyOnWriteArrayList<SoftButtonObject>) list;
        } else {
            softButtonObjects = new CopyOnWriteArrayList<>(list);
        }

        // Only update if something changed. This prevents, for example, an empty array being reset
        if (softButtonObjects.equals(this.softButtonObjects)) {
            DebugTool.logInfo(TAG, "New soft button objects are equivalent to existing soft button objects, skipping...");
            return;
        }

        // Check if two soft button objects have the same name
        if (hasTwoSoftButtonObjectsOfSameName(softButtonObjects)) {
            this.softButtonObjects = new CopyOnWriteArrayList<>();
            DebugTool.logError(TAG, "Attempted to set soft button objects, but two buttons had the same name");
            return;
        }

       if (!BaseScreenManager.checkAndAssignButtonIds(softButtonObjects, BaseScreenManager.ManagerLocation.SOFTBUTTON_MANAGER)) {
            DebugTool.logError(TAG, "Attempted to set soft button objects, but multiple buttons had the same id");
            return;
        }

        // Set updateListeners for soft button objects
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            softButtonObject.setUpdateListener(updateListener);
        }

        this.softButtonObjects = softButtonObjects;

        // We only need to pass the first softButtonCapabilities in the array due to the fact that all soft button capabilities are the same (i.e. there is no way to assign a softButtonCapabilities to a specific soft button).
        SoftButtonReplaceOperation operation = new SoftButtonReplaceOperation(internalInterface, fileManager.get(), softButtonCapabilities, softButtonObjects, getCurrentMainField1(), isGraphicSupported);

        if (batchUpdates) {
            batchQueue.clear();
            batchQueue.add(operation);
        } else {
            transactionQueue.clear();
            transactionQueue.add(operation, false);
        }
    }

    /**
     * Check if two SoftButtonObject have the same name
     *
     * @param softButtonObjects a list of SoftButton objects that will be iterated through
     * @return true if two buttons exist that are the same in the list, false if not
     */
    private boolean hasTwoSoftButtonObjectsOfSameName(List<SoftButtonObject> softButtonObjects) {
        for (int i = 0; i < softButtonObjects.size(); i++) {
            String buttonName = softButtonObjects.get(i).getName();
            for (int j = (i + 1); j < softButtonObjects.size(); j++) {
                if (softButtonObjects.get(j).getName().equals(buttonName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void transitionSoftButton() {
        SoftButtonTransitionOperation operation = new SoftButtonTransitionOperation(internalInterface, softButtonObjects, getCurrentMainField1());

        if (batchUpdates) {
            for (Task task : batchQueue) {
                if (task instanceof SoftButtonTransitionOperation) {
                    batchQueue.remove(task);
                }
            }
            batchQueue.add(operation);
        } else {
            transactionQueue.add(operation, false);
        }
    }

    /**
     * Get the SoftButtonObject that has the provided name
     *
     * @param name a String value that represents the name
     * @return a SoftButtonObject
     */
    protected SoftButtonObject getSoftButtonObjectByName(String name) {
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            if (softButtonObject.getName().equals(name)) {
                return softButtonObject;
            }
        }
        return null;
    }

    /**
     * Get the SoftButtonObject that has the provided buttonId
     *
     * @param buttonId a int value that represents the id of the button
     * @return a SoftButtonObject
     */
    protected SoftButtonObject getSoftButtonObjectById(int buttonId) {
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            if (softButtonObject.getButtonId() == buttonId) {
                return softButtonObject;
            }
        }
        return null;
    }

    /**
     * Set the batchUpdates flag that represents whether the manager should wait until commit() is called to send the updated show RPC
     *
     * @param batchUpdates Set true if the manager should batch updates together, or false if it should send them as soon as they happen
     */
    protected void setBatchUpdates(boolean batchUpdates) {
        this.batchUpdates = batchUpdates;

        if (!this.batchUpdates) {
            for (Task task : batchQueue) {
                transactionQueue.add(task, false);
            }
            batchQueue.clear();
        }
    }

    /**
     * Get the current String associated with MainField1
     *
     * @return the string that is currently used for MainField1
     */
    protected String getCurrentMainField1() {
        if (currentMainField1 == null) {
            return "";
        }
        return currentMainField1;
    }

    /**
     * Sets the String to be associated with MainField1
     *
     * @param currentMainField1 the String that will be set to TextField1 on the current template
     */
    protected void setCurrentMainField1(String currentMainField1) {
        this.currentMainField1 = currentMainField1;

        for (Task task : transactionQueue.getTasksAsList()) {
            if (task instanceof SoftButtonReplaceOperation) {
                SoftButtonReplaceOperation operation = (SoftButtonReplaceOperation) task;
                operation.setCurrentMainField1(getCurrentMainField1());
            } else if (task instanceof SoftButtonTransitionOperation) {
                SoftButtonTransitionOperation operation = (SoftButtonTransitionOperation) task;
                operation.setCurrentMainField1(getCurrentMainField1());
            }
        }
    }

    private boolean softButtonCapabilitiesEquals(SoftButtonCapabilities capabilities1, SoftButtonCapabilities capabilities2) {
        if (capabilities1 == capabilities2) {
            return true;
        } else if (capabilities1 == null || capabilities2 == null) {
            return false;
        } else if (capabilities1.getImageSupported() != capabilities2.getImageSupported()) {
            return false;
        } else if (capabilities1.getTextSupported() != capabilities2.getTextSupported()) {
            return false;
        }
        return true;
    }
}
