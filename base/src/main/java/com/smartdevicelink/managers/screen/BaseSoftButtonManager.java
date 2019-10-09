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

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SystemCapabilityManager;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <strong>SoftButtonManager</strong> <br>
 * SoftButtonManager gives the developer the ability to control how soft buttons are displayed on the head unit.<br>
 * Note: This class must be accessed through the SdlManager->ScreenManager. Do not instantiate it by itself.<br>
 */
abstract class BaseSoftButtonManager extends BaseSubManager {

    private static final String TAG = "SoftButtonManager";
    private WeakReference<FileManager> fileManager;
    WindowCapability defaultMainWindowCapability;
    private CopyOnWriteArrayList<SoftButtonObject> softButtonObjects;
    private HMILevel currentHMILevel;
    private Show inProgressShowRPC;
    private CompletionListener inProgressListener, queuedUpdateListener, cachedListener;
    private boolean hasQueuedUpdate, batchUpdates, waitingOnHMILevelUpdateToSetButtons;
    private final OnSystemCapabilityListener onDisplayCapabilityListener;
    private final OnRPCNotificationListener onHMIStatusListener, onButtonPressListener, onButtonEventListener;
    private final SoftButtonObject.UpdateListener updateListener;

    /**
     * HAX: This is necessary due to a Ford Sync 3 bug that doesn't like Show requests without a main field being set (it will accept them, but with a GENERIC_ERROR, and 10-15 seconds late...)
     */
    private String currentMainField1;


    /**
     * Creates a new instance of the SoftButtonManager
     * @param internalInterface an instance of the ISdl interface that can be used for common SDL operations (sendRpc, addRpcListener, etc)
     * @param fileManager an instance of the FileManager so that button graphics can be sent
     */
    BaseSoftButtonManager(@NonNull final ISdl internalInterface, @NonNull FileManager fileManager) {
        super(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.softButtonObjects = new CopyOnWriteArrayList<>();
        this.currentHMILevel = HMILevel.HMI_NONE;  // Assume NONE until we get something else
        this.waitingOnHMILevelUpdateToSetButtons = false;
        this.updateListener = new SoftButtonObject.UpdateListener() {
            @Override
            public void onUpdate() {
                update(null);
            }
        };


        // Add OnSoftButtonCapabilitiesListener to keep softButtonCapabilities updated
        onDisplayCapabilityListener = new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                // instead of using the parameter it's more safe to use the convenience method
                List<DisplayCapability> capabilities = SystemCapabilityManager.convertToList(capability, DisplayCapability.class);
                if (capabilities == null || capabilities.size() == 0) {
                    DebugTool.logError("SoftButton Manager - Capabilities sent here are null or empty");
                }else {
                    DisplayCapability display = capabilities.get(0);
                    for (WindowCapability windowCapability : display.getWindowCapabilities()) {
                        int currentWindowID = windowCapability.getWindowID() != null ? windowCapability.getWindowID() : PredefinedWindows.DEFAULT_WINDOW.getValue();
                        if (currentWindowID == PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                            defaultMainWindowCapability = windowCapability;
                        }
                    }
                }
            }

            @Override
            public void onError(String info) {
                DebugTool.logError("Display Capability cannot be retrieved");
                defaultMainWindowCapability = null;
            }
        };
        this.internalInterface.addOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplayCapabilityListener);

        // Add OnHMIStatusListener to keep currentHMILevel updated
        this.onHMIStatusListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnHMIStatus onHMIStatus = (OnHMIStatus)notification;
                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                    return;
                }
                HMILevel oldHmiLevel = currentHMILevel;
                currentHMILevel = onHMIStatus.getHmiLevel();


                // Auto-send an updated show if we were in NONE and now we are not
                if (oldHmiLevel == HMILevel.HMI_NONE && currentHMILevel != HMILevel.HMI_NONE) {
                    if (waitingOnHMILevelUpdateToSetButtons) {
                        setSoftButtonObjects(softButtonObjects);
                    } else {
                        update(cachedListener);
                    }
                }
            }
        };
        this.internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);


        // Add OnButtonPressListener to notify SoftButtonObjects when there is a button press
        this.onButtonPressListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnButtonPress onButtonPress = (OnButtonPress) notification;
                if (onButtonPress!= null && onButtonPress.getButtonName() == ButtonName.CUSTOM_BUTTON) {
                    Integer buttonId = onButtonPress.getCustomButtonName();
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
                if (onButtonEvent!= null && onButtonEvent.getButtonName() == ButtonName.CUSTOM_BUTTON) {
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
     * Get the SoftButtonObject that has the provided name
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
     * Get the soft button objects list
     * @return a List<SoftButtonObject>
     */
    protected List<SoftButtonObject> getSoftButtonObjects() {
        return softButtonObjects;
    }

    /**
     * Set softButtonObjects list and upload the images to the head unit
     * @param list the list of the SoftButtonObject values that should be displayed on the head unit
     */
    protected void setSoftButtonObjects(@NonNull List<SoftButtonObject> list) {
        // Convert the List to CopyOnWriteArrayList
        CopyOnWriteArrayList<SoftButtonObject> softButtonObjects;
        if(list instanceof CopyOnWriteArrayList){
            softButtonObjects = (CopyOnWriteArrayList<SoftButtonObject>) list;
        }else{
            softButtonObjects = new CopyOnWriteArrayList<>(list);
        }


        // Check if two soft button objects have the same name
        if (hasTwoSoftButtonObjectsOfSameName(softButtonObjects)) {
            this.softButtonObjects = new CopyOnWriteArrayList<>();
            Log.e(TAG, "Attempted to set soft button objects, but two buttons had the same name");
            return;
        }


        // Set updateListeners for soft button objects
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            softButtonObject.setUpdateListener(updateListener);
        }


        if (!checkAndAssignButtonIds(softButtonObjects)) {
            Log.e(TAG, "Attempted to set soft button objects, but multiple buttons had the same id");
            return;
        }


        this.softButtonObjects = softButtonObjects;


        if (currentHMILevel == null || currentHMILevel == HMILevel.HMI_NONE) {
            waitingOnHMILevelUpdateToSetButtons = true;
            return;
        }


        // End any in-progress update
        inProgressShowRPC = null;
        if (inProgressListener != null) {
            inProgressListener.onComplete(false);
            inProgressListener = null;
        }


        // End any queued update
        hasQueuedUpdate = false;
        if (queuedUpdateListener != null) {
            queuedUpdateListener.onComplete(false);
            queuedUpdateListener = null;
        }


        // Prepare soft button images to be uploaded to the head unit.
        // we will prepare a list for initial state images and another list for other state images
        // so we can upload the initial state images first, then the other states images.
        List<SdlArtwork> initialStatesToBeUploaded = new ArrayList<>();
        List<SdlArtwork> otherStatesToBeUploaded = new ArrayList<>();
        if (softButtonImagesSupported() && fileManager.get() != null) {
            for (SoftButtonObject softButtonObject : softButtonObjects) {
                SoftButtonState initialState = null;
                if (softButtonObject != null) {
                    initialState = softButtonObject.getCurrentState();
                }
                if (initialState != null && softButtonObject.getStates() != null) {
                    for (SoftButtonState softButtonState : softButtonObject.getStates()) {
                        if (softButtonState != null && softButtonState.getName() != null && sdlArtworkNeedsUpload(softButtonState.getArtwork())) {
                            if (softButtonState.getName().equals(initialState.getName())) {
                                initialStatesToBeUploaded.add(softButtonObject.getCurrentState().getArtwork());
                            } else{
                                otherStatesToBeUploaded.add(softButtonState.getArtwork());
                            }
                        }
                    }
                }
            }
        }


        // Upload initial state images
        if (initialStatesToBeUploaded.size() > 0 && fileManager.get() != null) {
            DebugTool.logInfo( "Uploading soft button initial state artworks");
            fileManager.get().uploadArtworks(initialStatesToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null && errors.size() > 0) {
                        Log.e(TAG, "Error uploading soft button artworks");
                    }
                    DebugTool.logInfo( "Soft button initial artworks uploaded");
                    update(cachedListener);
                }
            });
        }


        // Upload other state images
        if (otherStatesToBeUploaded.size() > 0 && fileManager.get() != null) {
            DebugTool.logInfo("Uploading soft button other state artworks");
            fileManager.get().uploadArtworks(otherStatesToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null && errors.size() > 0) {
                        Log.e(TAG, "Error uploading soft button artworks");
                    }
                    DebugTool.logInfo("Soft button other state artworks uploaded");
                    // In case our soft button states have changed in the meantime
                    update(cachedListener);
                }
            });
        }

        // This is necessary because there may be no images needed to be uploaded
        update(cachedListener);
    }

    /**
     * Check if there is a collision in the ids provided by the developer and assign ids to the SoftButtonObjects that do not have ids
     * @param softButtonObjects the list of the SoftButtonObject values that should be displayed on the head unit
     * @return boolean representing whether the ids are unique or not
     */
    boolean checkAndAssignButtonIds(List<SoftButtonObject> softButtonObjects) {
        // Check if multiple soft button objects have the same id
        HashSet<Integer> buttonIdsSetByDevHashSet = new HashSet<>();
        int currentSoftButtonId, numberOfButtonIdsSetByDev = 0, maxButtonIdsSetByDev = SoftButtonObject.SOFT_BUTTON_ID_MIN_VALUE;
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            currentSoftButtonId = softButtonObject.getButtonId();
            if (currentSoftButtonId != SoftButtonObject.SOFT_BUTTON_ID_NOT_SET_VALUE) {
                numberOfButtonIdsSetByDev++;
                if (currentSoftButtonId > maxButtonIdsSetByDev){
                    maxButtonIdsSetByDev = currentSoftButtonId;
                }
                buttonIdsSetByDevHashSet.add(softButtonObject.getButtonId());
            }
        }
        if (numberOfButtonIdsSetByDev != buttonIdsSetByDevHashSet.size()){
            return false;
        }


        // Set ids for soft button objects
        int generatedSoftButtonId = maxButtonIdsSetByDev;
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            // If the dev did not set the buttonId, the manager should set an id on the dev's behalf
            currentSoftButtonId = softButtonObject.getButtonId();
            if (currentSoftButtonId == SoftButtonObject.SOFT_BUTTON_ID_NOT_SET_VALUE){
                do {
                    if (generatedSoftButtonId >= SoftButtonObject.SOFT_BUTTON_ID_MAX_VALUE){
                        generatedSoftButtonId = SoftButtonObject.SOFT_BUTTON_ID_MIN_VALUE;
                    }
                    generatedSoftButtonId++;
                } while (buttonIdsSetByDevHashSet.contains(generatedSoftButtonId));
                softButtonObject.setButtonId(generatedSoftButtonId);
            }
        }
        return true;
    }

    /**
     * Update the SoftButtonManger by sending a new Show RPC to reflect the changes
     * @param listener a CompletionListener
     */
    protected void update(CompletionListener listener) {
        cachedListener = listener;

        if (batchUpdates) {
            return;
        }

        // Don't send if we're in HMI NONE
        if (currentHMILevel == null || currentHMILevel == HMILevel.HMI_NONE) {
            return;
        }

        DebugTool.logInfo("Updating soft buttons");

        cachedListener = null;


        // Check if we have update already in progress
        if (inProgressShowRPC != null) {
            DebugTool.logInfo("In progress update exists, queueing update");
            // If we already have a pending update, we're going to tell the old listener that it was superseded by a new update and then return
            if (queuedUpdateListener != null) {
                DebugTool.logInfo("Queued update already exists, superseding previous queued update");
                queuedUpdateListener.onComplete(false);
                queuedUpdateListener = null;
            }

            // Note: the queued update will be started after the in-progress one finishes
            if (listener != null) {
                queuedUpdateListener = listener;
            }
            hasQueuedUpdate = true;
            return;
        }


        // Send Show RPC with soft buttons representing the current state for the soft button objects
        inProgressListener = listener;
        inProgressShowRPC = new Show();
        inProgressShowRPC.setMainField1(getCurrentMainField1());
        if (softButtonObjects == null) {
            DebugTool.logInfo("Soft button objects are null, sending an empty array");
            inProgressShowRPC.setSoftButtons(new ArrayList<SoftButton>());
        } else if ((currentStateHasImages() && !allCurrentStateImagesAreUploaded()) || !softButtonImagesSupported()) {
            // The images don't yet exist on the head unit, or we cannot use images, send a text update if possible, otherwise, don't send anything yet
            List<SoftButton> textOnlySoftButtons = createTextSoftButtonsForCurrentState();
            if (textOnlySoftButtons != null) {
                DebugTool.logInfo( "Soft button images unavailable, sending text buttons");
                inProgressShowRPC.setSoftButtons(textOnlySoftButtons);

            } else {
                DebugTool.logInfo( "Soft button images unavailable, text buttons unavailable");
                inProgressShowRPC = null;
                return;
            }

        } else {
            DebugTool.logInfo( "Sending soft buttons with images");
            inProgressShowRPC.setSoftButtons(createSoftButtonsForCurrentState());
        }


        inProgressShowRPC.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                DebugTool.logInfo("Soft button update completed");
                handleResponse(true);
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                super.onError(correlationId, resultCode, info);

                Log.e(TAG, "Soft button update error. resultCode: " + resultCode + ". info: " + info);
                handleResponse(false);
            }

            private void handleResponse(boolean success){

                inProgressShowRPC = null;
                CompletionListener currentListener;
                if (inProgressListener != null) {
                    currentListener = inProgressListener;
                    inProgressListener = null;
                    currentListener.onComplete(success);
                }


                if (hasQueuedUpdate) {
                    DebugTool.logInfo("Queued update exists, sending another update");
                    currentListener = queuedUpdateListener;
                    queuedUpdateListener = null;
                    hasQueuedUpdate = false;
                    update(currentListener);
                }
            }
        });


        internalInterface.sendRPC(inProgressShowRPC);
    }

    private boolean softButtonImagesSupported() {
        return defaultMainWindowCapability == null
                || defaultMainWindowCapability.getSoftButtonCapabilities() == null
                || (!defaultMainWindowCapability.getSoftButtonCapabilities().isEmpty() && defaultMainWindowCapability.getSoftButtonCapabilities().get(0).getImageSupported());
    }

    /**
     * Check if two SoftButtonObject have the same name
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

    /**
     * Get the current String associated with MainField1
     * @return the string that is currently used for MainField1
     */
    protected String getCurrentMainField1() {
        if (currentMainField1 == null){
            return "";
        }
        return currentMainField1;
    }

    /**
     * Sets the String to be associated with MainField1
     * @param currentMainField1 the String that will be set to TextField1 on the current template
     */
    protected void setCurrentMainField1(String currentMainField1) {
        this.currentMainField1 = currentMainField1;
    }

    /**
     * Sets the batchUpdates flag that represents whether the manager should wait until commit() is called to send the updated show RPC
     * @param batchUpdates Set true if the manager should batch updates together, or false if it should send them as soon
     *                     as they happen
     */
    protected void setBatchUpdates(boolean batchUpdates) {
        this.batchUpdates = batchUpdates;
    }

    /**
     * Clean up everything after the manager is no longer needed
     */
    @Override
    public void dispose() {
        super.dispose();

        // Remove listeners
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, onButtonPressListener);
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_BUTTON_EVENT, onButtonEventListener);
        internalInterface.removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplayCapabilityListener);
    }

    /**
     * Check if the current state for any SoftButtonObject has images
     * @return a boolean value
     */
    private boolean currentStateHasImages() {
        for (SoftButtonObject softButtonObject : this.softButtonObjects) {
            if (softButtonObject.getCurrentState() != null && softButtonObject.getCurrentState().getArtwork() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the current state for any SoftButtonObject has images that are not uploaded yet
     * @return a boolean value
     */
    private boolean allCurrentStateImagesAreUploaded() {
        if (fileManager.get() != null) {
            for (SoftButtonObject softButtonObject : softButtonObjects) {
                SoftButtonState currentState = softButtonObject.getCurrentState();
                if (currentState != null && sdlArtworkNeedsUpload(currentState.getArtwork())) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean sdlArtworkNeedsUpload(SdlArtwork artwork){
        if (fileManager.get() != null) {
            return artwork != null && !fileManager.get().hasUploadedFile(artwork) && !artwork.isStaticIcon();
        }
        return false;
    }

    /**
     * Returns text soft buttons representing the initial states of the button objects, or null if _any_ of the buttons' current states are image only buttons.
     * @return The text soft buttons
     */
    private List<SoftButton> createTextSoftButtonsForCurrentState() {
        List<SoftButton> textButtons = new ArrayList<>();
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            SoftButton softButton = softButtonObject.getCurrentStateSoftButton();
            if (softButton.getText() == null) {
                return null;
            }
            // We should create a new softButtonObject rather than modifying the original one
            SoftButton textOnlySoftButton = new SoftButton(SoftButtonType.SBT_TEXT, softButton.getSoftButtonID());
            textOnlySoftButton.setText(softButton.getText());
            textButtons.add(textOnlySoftButton);
        }
        return textButtons;
    }

    /**
     * Returns a list of the SoftButton for the SoftButtonObjects' current state
     * @return a List<SoftButton>
     */
    protected List<SoftButton> createSoftButtonsForCurrentState() {
        List<SoftButton> softButtons = new ArrayList<>();
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            softButtons.add(softButtonObject.getCurrentStateSoftButton());
        }
        return softButtons;
    }
}
