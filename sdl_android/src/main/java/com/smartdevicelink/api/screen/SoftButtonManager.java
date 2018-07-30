package com.smartdevicelink.api.screen;

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
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <strong>SoftButtonManager</strong> <br>
 * Note: This class must be accessed through the SdlManager->ScreenManager. Do not instantiate it by itself. <br>
 */
class SoftButtonManager extends BaseSubManager {

    private static final String TAG = "SoftButtonManager";
    private FileManager fileManager;
	private DisplayCapabilities displayCapabilities;
	private SoftButtonCapabilities softButtonCapabilities;
	private List<SoftButtonObject> softButtonObjects;
	private HMILevel currentHMILevel;
    private Show inProgressShowRPC;
    private CompletionListener inProgressListener, queuedUpdateListener;
    private boolean hasQueuedUpdate, batchUpdates, waitingOnHMILevelUpdateToSetButtons;
    //private OnRPCResponseListener onRegisterAppInterfaceListener, onSetDisplayLayoutListener;
    private OnSystemCapabilityListener onSoftButtonCapabilitiesListener, onDisplayCapabilitiesListener;
    private OnRPCNotificationListener onHMIStatusListener;

	/**
	 HAX: This is necessary due to a Ford Sync 3 bug that doesn't like Show requests without a main field being set (it will accept them, but with a GENERIC_ERROR, and 10-15 seconds late...)
	 */
    private String currentMainField1;


    SoftButtonManager(ISdl internalInterface, FileManager fileManager) {
	    super(internalInterface);
        transitionToState(BaseSubManager.SETTING_UP);
        this.fileManager = fileManager;
		this.softButtonObjects = new ArrayList<>();
		this.currentHMILevel = HMILevel.HMI_NONE;  // Assume NONE until we get something else
        this.waitingOnHMILevelUpdateToSetButtons = false;


        // Add OnSoftButtonCapabilitiesListener to keep softButtonCapabilities updated
        onSoftButtonCapabilitiesListener = new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                List<SoftButtonCapabilities> softButtonCapabilitiesList = (List<SoftButtonCapabilities>)capability;
                if (softButtonCapabilitiesList != null && !softButtonCapabilitiesList.isEmpty()){
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
                displayCapabilities = (DisplayCapabilities)capability;
            }

            @Override
            public void onError(String info) {
                Log.w(TAG, "DISPLAY Capability cannot be retrieved:");
                displayCapabilities = null;
            }
        };
        this.internalInterface.addOnSystemCapabilityListener(SystemCapabilityType.DISPLAY, onDisplayCapabilitiesListener);


        // Add OnHMIStatusListener to keep currentHMILevel updated
        this.onHMIStatusListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnHMIStatus onHMIStatus = (OnHMIStatus)notification;

                transitionToState(READY);

                HMILevel oldHmiLevel = currentHMILevel;
                currentHMILevel = onHMIStatus.getHmiLevel();


                // Auto-send an updated show if we were in NONE and now we are not
                if (oldHmiLevel == HMILevel.HMI_NONE && currentHMILevel != HMILevel.HMI_NONE){
                    if (waitingOnHMILevelUpdateToSetButtons){
                        setSoftButtonObjects(softButtonObjects);
                    } else {
                        update(false, null);
                    }
                }
            }
        };
        this.internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);
    }

    protected List<SoftButtonObject> getSoftButtonObjects() {
        return softButtonObjects;
    }

    protected SoftButtonObject getSoftButtonObjectNamed(String name) {
        for (SoftButtonObject softButtonObject : softButtonObjects){
            if (softButtonObject.getName().equals(name)){
                return softButtonObject;
            }
        }
        return null;
    }

    // Upload images to head unit
	protected void setSoftButtonObjects(List<SoftButtonObject> softButtonObjects){
        if (hasTwoSoftButtonObjectsOfSameName(softButtonObjects)){
            this.softButtonObjects = new ArrayList<>();
            Log.e(TAG, "Attempted to set soft button objects, but two buttons had the same name");
            return;
        }

        // Set ids and managers for soft button objects
        for (int i = 0; i < softButtonObjects.size(); i++){
            softButtonObjects.get(i).setButtonId(i * 100);
            softButtonObjects.get(i).setSoftButtonManager(this);
        }
        this.softButtonObjects = softButtonObjects;


        if (currentHMILevel == null || currentHMILevel == HMILevel.HMI_NONE){
	        waitingOnHMILevelUpdateToSetButtons = true;
	        return;
        }


        // End any in-progress update
        inProgressShowRPC = null;
	    if (inProgressListener != null){
	        inProgressListener.onComplete(false);
	        inProgressListener = null;
        }


        // End any queued update
        hasQueuedUpdate = false;
	    if (queuedUpdateListener != null){
	        queuedUpdateListener.onComplete(false);
	        queuedUpdateListener = null;
        }


	    // Upload all soft button images to the head unit, the initial state images first, then the other states.
        List<SdlArtwork> initialStatesToBeUploaded = new ArrayList<>();
        List<SdlArtwork> otherStatesToBeUploaded = new ArrayList<>();
        if(displayCapabilities == null || displayCapabilities.getGraphicSupported()){

            // Prepare initial states images for upload
            for (SoftButtonObject softButtonObject : softButtonObjects){
                SoftButtonState initialState = null;
                if (softButtonObject != null){
                    initialState = softButtonObject.getCurrentState();
                }
                if (initialState != null && initialState.getArtwork() != null && !fileManager.hasUploadedFile(initialState.getArtwork())){
                    initialStatesToBeUploaded.add(softButtonObject.getCurrentState().getArtwork());
                }
            }

            // Prepare other states images for upload
            for (SoftButtonObject softButtonObject : softButtonObjects){
                SoftButtonState initialState = null;
                if (softButtonObject != null){
                    initialState = softButtonObject.getCurrentState();
                }
                if (initialState != null && softButtonObject.getStates() != null){
                    for (SoftButtonState softButtonState : softButtonObject.getStates()) {
                        if (softButtonState == null || softButtonState.getName() == null || softButtonState.getName().equals(initialState.getName())) {
                            continue;
                        }
                        if (softButtonState.getArtwork() != null && !fileManager.hasUploadedFile(initialState.getArtwork())) {
                            otherStatesToBeUploaded.add(softButtonState.getArtwork());
                        }
                    }
                }
            }
        }


        // Upload initial state images
        if (initialStatesToBeUploaded.size() > 0){
            Log.i(TAG, "Uploading soft button initial state artworks");
            fileManager.uploadArtworks(initialStatesToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null && errors.size() > 0){
                        Log.e(TAG, "Error uploading soft button artworks");
                    }
                    Log.i(TAG, "Soft button initial artworks uploaded");
                    update(false, null);
                }
            });
        }


        // Upload other state images
        if (otherStatesToBeUploaded.size() > 0){
            Log.i(TAG, "Uploading soft button other state artworks");
            fileManager.uploadArtworks(otherStatesToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null && errors.size() > 0){
                        Log.e(TAG, "Error uploading soft button artworks");
                    }
                    Log.i(TAG, "Soft button other state artworks uploaded");
                    update(false, null);
                }
            });
        }

        update(false, null);
    }

    // Send RPCs
    protected void update(boolean checkBatchUpdates, CompletionListener listener){
        if (batchUpdates){
            return;
        }

        // Don't send if we're in HMI NONE
        if (currentHMILevel == null || currentHMILevel == HMILevel.HMI_NONE){
            return;
        }

        Log.i(TAG, "Updating soft buttons");


        // Check if we have update already in progress
        if (inProgressShowRPC != null){
            Log.i(TAG, "In progress update exists, queueing update");
            // If we already have a pending update, we're going to tell the old listener that it was superseded by a new update and then return
            if (queuedUpdateListener != null){
                Log.i(TAG, "Queued update already exists, superseding previous queued update");
                queuedUpdateListener.onComplete(false);
                queuedUpdateListener = null;
            }

            // Note: the queued update will be started after the in-progress one finishes
            if (listener != null){
                queuedUpdateListener = listener;
            }
            hasQueuedUpdate = true;
            return;
        }


        // Send Show RPC with soft buttons representing the current state for the soft button objects
        inProgressListener = listener;
        inProgressShowRPC = new Show();
        if (currentMainField1 == null){
            inProgressShowRPC.setMainField1("");
        } else {
            inProgressShowRPC.setMainField1(currentMainField1);
        }
        if (softButtonObjects == null){
            Log.i(TAG, "Soft button objects are null, sending an empty array");
            inProgressShowRPC.setSoftButtons(new ArrayList<SoftButton>());
        } else if ( (currentStateHasImages() && !allCurrentStateImagesAreUploaded()) && (softButtonCapabilities == null || !softButtonCapabilities.getImageSupported()) ){
            // The images don't yet exist on the head unit, or we cannot use images, send a text update if possible, otherwise, don't send anything yet
            List<SoftButton> textOnlyButtons = createTextButtonsForCurrentState();
            if (textOnlyButtons != null){
                Log.i(TAG, "Soft button images unavailable, sending text buttons");
                inProgressShowRPC.setSoftButtons(textOnlyButtons);

            } else {
                Log.i(TAG, "Soft button images unavailable, text buttons unavailable");
                inProgressShowRPC = null;
                return;
            }

        } else {
            Log.i(TAG, "Sending soft buttons with images");
            inProgressShowRPC.setSoftButtons(softButtonsForCurrentState());
        }

        inProgressShowRPC.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Log.i(TAG, "Soft button update completed");

                inProgressShowRPC = null;
                if (inProgressListener != null){
                    inProgressListener.onComplete(true);
                    inProgressListener = null;
                }


                if (hasQueuedUpdate){
                    Log.i(TAG, "Queued update exists, sending another update");
                    update(true, queuedUpdateListener);
                    queuedUpdateListener = null;
                    hasQueuedUpdate = false;
                }
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                super.onError(correlationId, resultCode, info);

                Log.e(TAG, "Soft button update error");

                inProgressShowRPC = null;
                if (inProgressListener != null){
                    inProgressListener.onComplete(false);
                    inProgressListener = null;
                }


                if (hasQueuedUpdate){
                    Log.i(TAG, "Queued update exists, sending another update");
                    update(true, queuedUpdateListener);
                    queuedUpdateListener = null;
                    hasQueuedUpdate = false;
                }
            }
        });
        internalInterface.sendRPCRequest(inProgressShowRPC);
    }

    private boolean hasTwoSoftButtonObjectsOfSameName(List<SoftButtonObject> softButtonObjects){
        for (int i = 0; i < softButtonObjects.size(); i++){
            String buttonName = softButtonObjects.get(i).getName();
            for (int j = (i + 1); j < softButtonObjects.size(); j++){
                if (softButtonObjects.get(j).getName().equals(buttonName)){
                    return true;
                }
            }
        }
        return false;
    }

    protected void setCurrentMainField1(String currentMainField1) {
        this.currentMainField1 = currentMainField1;
    }

    protected void setBatchUpdates(boolean batchUpdates) {
        this.batchUpdates = batchUpdates;
    }

    @Override
    public void dispose() {
        super.dispose();

        transitionToState(SHUTDOWN);

        // Remove listeners
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);
        internalInterface.removeOnSystemCapabilityListener(SystemCapabilityType.SOFTBUTTON, onSoftButtonCapabilitiesListener);
        internalInterface.removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAY, onDisplayCapabilitiesListener);
    }

    private boolean currentStateHasImages() {
        for (SoftButtonObject softButtonObject : this.softButtonObjects) {
            if (softButtonObject.getCurrentState() != null && softButtonObject.getCurrentState().getArtwork() != null){
                return true;
            }
        }
		return false;
	}

	private boolean allCurrentStateImagesAreUploaded() {
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            SdlArtwork artwork = softButtonObject.getCurrentState().getArtwork();
            if (artwork != null && !fileManager.hasUploadedFile(artwork)){
                return false;
            }
        }
		return true;
	}

    /**
     Returns text soft buttons representing the initial states of the button objects, or null if _any_ of the buttons' current states are image only buttons.
     @return The text soft buttons
     */
	private List<SoftButton> createTextButtonsForCurrentState() {
	    List<SoftButton> textButtons = new ArrayList<>();
	    for (SoftButtonObject softButtonObject : softButtonObjects){
	        SoftButton softButton = softButtonObject.getCurrentStateSoftButton();
	        if (softButton.getText() == null){
	            return null;
            }
            softButton.setImage(null);
	        softButton.setType(SoftButtonType.SBT_TEXT);
	        textButtons.add(softButton);
        }
		return textButtons;
	}

	private List<SoftButton> softButtonsForCurrentState() {
	    List<SoftButton> softButtons = new ArrayList<>();
	    for(SoftButtonObject softButtonObject : softButtonObjects){
	        softButtons.add(softButtonObject.getCurrentStateSoftButton());
        }
	    return softButtons;
	}

}
