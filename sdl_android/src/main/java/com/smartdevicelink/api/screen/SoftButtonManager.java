package com.smartdevicelink.api.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.api.CompletionListener;
import com.smartdevicelink.api.FileManager;
import com.smartdevicelink.api.MultipleFileCompletionListener;
import com.smartdevicelink.api.datatypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <strong>SoftButtonManager</strong> <br>
 * SoftButtonManager gives the developer the ability to control how soft buttons are displayed on the head unit.<br>
 * Note: This class must be accessed through the SdlManager->ScreenManager. Do not instantiate it by itself.<br>
 */
class SoftButtonManager extends BaseSubManager {

    private static final String TAG = "SoftButtonManager";
    private FileManager fileManager;
    private DisplayCapabilities displayCapabilities;
    private SoftButtonCapabilities softButtonCapabilities;
    private CopyOnWriteArrayList<SoftButtonObject> softButtonObjects;
    private HMILevel currentHMILevel;
    private Show inProgressShowRPC;
    private CompletionListener inProgressListener, queuedUpdateListener, cachedListener;
    private boolean hasQueuedUpdate, batchUpdates, waitingOnHMILevelUpdateToSetButtons;
    private final OnSystemCapabilityListener onSoftButtonCapabilitiesListener, onDisplayCapabilitiesListener;
    private final OnRPCNotificationListener onHMIStatusListener, onButtonPressListener, onButtonEventListener;

    /**
     * HAX: This is necessary due to a Ford Sync 3 bug that doesn't like Show requests without a main field being set (it will accept them, but with a GENERIC_ERROR, and 10-15 seconds late...)
     */
    private String currentMainField1;


    /**
     * Creates a new instance of the SoftButtonManager
     * @param internalInterface
     * @param fileManager
     */
    SoftButtonManager(ISdl internalInterface, FileManager fileManager) {
        super(internalInterface);
        transitionToState(BaseSubManager.SETTING_UP);
        this.fileManager = fileManager;
        this.softButtonObjects = new CopyOnWriteArrayList<>();
        this.currentHMILevel = HMILevel.HMI_NONE;  // Assume NONE until we get something else
        this.waitingOnHMILevelUpdateToSetButtons = false;


        // Add OnSoftButtonCapabilitiesListener to keep softButtonCapabilities updated
        onSoftButtonCapabilitiesListener = new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                List<SoftButtonCapabilities> softButtonCapabilitiesList = (List<SoftButtonCapabilities>) capability;
                if (softButtonCapabilitiesList != null && !softButtonCapabilitiesList.isEmpty()) {
                    softButtonCapabilities = softButtonCapabilitiesList.get(0);
                } else {
                    softButtonCapabilities = null;
                }
            }

            @Override
            public void onError(String info) {
                Log.w(TAG, "SoftButton Capability cannot be retrieved:");
                softButtonCapabilities = null;
            }
        };
        this.internalInterface.addOnSystemCapabilityListener(SystemCapabilityType.SOFTBUTTON, onSoftButtonCapabilitiesListener);


        // Add OnDisplayCapabilitiesListener to keep displayCapabilities updated
        onDisplayCapabilitiesListener = new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                displayCapabilities = (DisplayCapabilities) capability;
            }

            @Override
            public void onError(String info) {
                Log.w(TAG, "Display Capability cannot be retrieved:");
                displayCapabilities = null;
            }
        };
        this.internalInterface.addOnSystemCapabilityListener(SystemCapabilityType.DISPLAY, onDisplayCapabilitiesListener);


        // Add OnHMIStatusListener to keep currentHMILevel updated
        this.onHMIStatusListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {

                OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
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

        // Transition to ready state
        transitionToState(READY);
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
     * @param softButtonObjects the list of the SoftButtonObject values that should be displayed on the head unit
     */
    protected void setSoftButtonObjects(@NonNull CopyOnWriteArrayList<SoftButtonObject> softButtonObjects) {
        if (hasTwoSoftButtonObjectsOfSameName(softButtonObjects)) {
            this.softButtonObjects = new CopyOnWriteArrayList<>();
            Log.e(TAG, "Attempted to set soft button objects, but two buttons had the same name");
            return;
        }

        // Set ids and managers for soft button objects
        for (int i = 0; i < softButtonObjects.size(); i++) {
            softButtonObjects.get(i).setButtonId(i * 100);
            softButtonObjects.get(i).setSoftButtonManager(this);
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
        if (softButtonImagesSupported()) {
            for (SoftButtonObject softButtonObject : softButtonObjects) {
                SoftButtonState initialState = null;
                if (softButtonObject != null) {
                    initialState = softButtonObject.getCurrentState();
                }
                if (initialState != null && softButtonObject.getStates() != null) {
                    for (SoftButtonState softButtonState : softButtonObject.getStates()) {
                        if (softButtonState != null && softButtonState.getName() != null && softButtonState.getArtwork() != null && !fileManager.hasUploadedFile(softButtonState.getArtwork())) {
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
        if (initialStatesToBeUploaded.size() > 0) {
            Log.v(TAG, "Uploading soft button initial state artworks");
            fileManager.uploadArtworks(initialStatesToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null && errors.size() > 0) {
                        Log.e(TAG, "Error uploading soft button artworks");
                    }
                    Log.d(TAG, "Soft button initial artworks uploaded");
                    update(cachedListener);
                }
            });
        }


        // Upload other state images
        if (otherStatesToBeUploaded.size() > 0) {
            Log.v(TAG, "Uploading soft button other state artworks");
            fileManager.uploadArtworks(otherStatesToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null && errors.size() > 0) {
                        Log.e(TAG, "Error uploading soft button artworks");
                    }
                    Log.d(TAG, "Soft button other state artworks uploaded");
                    // In case our soft button states have changed in the meantime
                    update(cachedListener);
                }
            });
        }

        // This is necessary because there may be no images needed to be uploaded
        update(cachedListener);
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

        Log.v(TAG, "Updating soft buttons");

        cachedListener = null;


        // Check if we have update already in progress
        if (inProgressShowRPC != null) {
            Log.d(TAG, "In progress update exists, queueing update");
            // If we already have a pending update, we're going to tell the old listener that it was superseded by a new update and then return
            if (queuedUpdateListener != null) {
                Log.d(TAG, "Queued update already exists, superseding previous queued update");
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
            Log.d(TAG, "Soft button objects are null, sending an empty array");
            inProgressShowRPC.setSoftButtons(new ArrayList<SoftButton>());
            Log.i(TAG, "update: ");
        } else if ((currentStateHasImages() && !allCurrentStateImagesAreUploaded()) || !softButtonImagesSupported()) {
            // The images don't yet exist on the head unit, or we cannot use images, send a text update if possible, otherwise, don't send anything yet
            List<SoftButton> textOnlySoftButtons = createTextSoftButtonsForCurrentState();
            if (textOnlySoftButtons != null) {
                Log.d(TAG, "Soft button images unavailable, sending text buttons");
                inProgressShowRPC.setSoftButtons(textOnlySoftButtons);

            } else {
                Log.d(TAG, "Soft button images unavailable, text buttons unavailable");
                inProgressShowRPC = null;
                return;
            }

        } else {
            Log.d(TAG, "Sending soft buttons with images");
            inProgressShowRPC.setSoftButtons(createSoftButtonsForCurrentState());
        }

        inProgressShowRPC.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Log.i(TAG, "Soft button update completed");

                inProgressShowRPC = null;
                CompletionListener currentListener;
                if (inProgressListener != null) {
                    currentListener = inProgressListener;
                    inProgressListener = null;
                    currentListener.onComplete(true);
                }


                if (hasQueuedUpdate) {
                    Log.d(TAG, "Queued update exists, sending another update");
                    currentListener = queuedUpdateListener;
                    queuedUpdateListener = null;
                    hasQueuedUpdate = false;
                    update(currentListener);
                }
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                super.onError(correlationId, resultCode, info);

                Log.e(TAG, "Soft button update error");

                inProgressShowRPC = null;
                CompletionListener currentListener;
                if (inProgressListener != null) {
                    currentListener = inProgressListener;
                    inProgressListener = null;
                    currentListener.onComplete(false);
                }


                if (hasQueuedUpdate) {
                    Log.d(TAG, "Queued update exists, sending another update");
                    currentListener = queuedUpdateListener;
                    queuedUpdateListener = null;
                    hasQueuedUpdate = false;
                    update(currentListener);
                }
            }
        });
        internalInterface.sendRPCRequest(inProgressShowRPC);
    }

    private boolean softButtonImagesSupported(){
        boolean graphicSupported = false;
        if (displayCapabilities == null || displayCapabilities.getGraphicSupported()){
            graphicSupported = true;
        }
        boolean imageSupported = false;
        if (softButtonCapabilities == null || softButtonCapabilities.getImageSupported()){
            imageSupported = true;
        }
        return graphicSupported && imageSupported;
    }

    /**
     * Check if two SoftButtonObject have the same name
     * @param softButtonObjects
     * @return a boolean value
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
     * Get the TextField1
     * @return currentMainField1
     */
    protected String getCurrentMainField1() {
        if (currentMainField1 == null){
            return "";
        }
        return currentMainField1;
    }

    /**
     * Set the TextField1
     * @param currentMainField1
     */
    protected void setCurrentMainField1(String currentMainField1) {
        this.currentMainField1 = currentMainField1;
    }

    /**
     * Set the batchUpdates flag that represents whether the manager should wait until commit() is called to send the updated show RPC
     * @param batchUpdates
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

        transitionToState(SHUTDOWN);

        // Remove listeners
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, onButtonPressListener);
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_BUTTON_EVENT, onButtonEventListener);
        internalInterface.removeOnSystemCapabilityListener(SystemCapabilityType.SOFTBUTTON, onSoftButtonCapabilitiesListener);
        internalInterface.removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAY, onDisplayCapabilitiesListener);
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
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            SoftButtonState currentState = softButtonObject.getCurrentState();
            if (currentState != null && currentState.getArtwork() != null && !fileManager.hasUploadedFile(currentState.getArtwork())) {
                return false;
            }
        }
        return true;
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
