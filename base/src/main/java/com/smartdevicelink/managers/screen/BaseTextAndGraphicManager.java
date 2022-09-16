/*
 * Copyright (c) 2019 - 2020 Livio, Inc.
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
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.TemplateConfiguration;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.smartdevicelink.proxy.rpc.enums.TextAlignment.CENTERED;

import android.util.Log;

/**
 * <strong>TextAndGraphicManager</strong> <br>
 * <p>
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 */
abstract class BaseTextAndGraphicManager extends BaseSubManager {

    private static final String TAG = "TextAndGraphicManager";

    boolean isDirty;
    TextAndGraphicState currentScreenData;
    HMILevel currentHMILevel;
    private final WeakReference<SoftButtonManager> softButtonManager;
    WindowCapability defaultMainWindowCapability;
    private boolean batchingUpdates;
    private final WeakReference<FileManager> fileManager;
    SdlArtwork blankArtwork;
    private OnRPCNotificationListener hmiListener;
    private OnSystemCapabilityListener onDisplaysCapabilityListener;
    private SdlArtwork primaryGraphic, secondaryGraphic;
    private TextAlignment textAlignment;
    private String textField1, textField2, textField3, textField4, mediaTrackTextField, title;
    private MetadataType textField1Type, textField2Type, textField3Type, textField4Type;
    private TemplateConfiguration templateConfiguration;
    TextAndGraphicUpdateOperation updateOperation;
    private CompletionListener currentOperationListener;
    Queue transactionQueue;

    //Constructors
    BaseTextAndGraphicManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager, @NonNull SoftButtonManager softButtonManager) {
        // set class vars
        super(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.softButtonManager = new WeakReference<>(softButtonManager);
        batchingUpdates = false;
        isDirty = false;
        textAlignment = CENTERED;
        currentHMILevel = HMILevel.HMI_NONE;
        currentScreenData = new TextAndGraphicState();
        this.transactionQueue = newTransactionQueue();
        addListeners();
    }

    @Override
    public void start(CompletionListener listener) {
        transitionToState(READY);
        super.start(listener);
    }

    @Override
    public void dispose() {
        textField1 = null;
        textField1Type = null;
        textField2 = null;
        textField2Type = null;
        textField3 = null;
        textField3Type = null;
        textField4 = null;
        textField4Type = null;
        mediaTrackTextField = null;
        title = null;
        textAlignment = null;
        primaryGraphic = null;
        secondaryGraphic = null;
        blankArtwork = null;
        defaultMainWindowCapability = null;
        currentScreenData = null;
        isDirty = false;
        updateOperation = null;

        // Cancel the operations
        if (transactionQueue != null) {
            transactionQueue.close();
            transactionQueue = null;
        }

        // remove listeners
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
        if (internalInterface.getSystemCapabilityManager() != null) {
            internalInterface.getSystemCapabilityManager().removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplaysCapabilityListener);
        }

        super.dispose();
    }

    private Queue newTransactionQueue() {
        Queue queue = internalInterface.getTaskmaster().createQueue("TextAndGraphicManager", 3, false);
        queue.pause();
        return queue;
    }

    // Suspend the queue if the WindowCapabilities are null
    // OR if the HMI level is NONE since we want to delay sending RPCs until we're in non-NONE
    private void updateTransactionQueueSuspended() {
        if (defaultMainWindowCapability == null || HMILevel.HMI_NONE.equals(currentHMILevel)) {
            DebugTool.logInfo(TAG, String.format("Suspending the transaction queue. Current HMI level is NONE: %b, window capabilities are null: %b", HMILevel.HMI_NONE.equals(currentHMILevel), defaultMainWindowCapability == null));
            transactionQueue.pause();
        } else {
            DebugTool.logInfo(TAG, "Starting the transaction queue");
            transactionQueue.resume();
        }
    }

    // Upload / Send
    protected void update(CompletionListener listener) {
        // check if is batch update
        if (batchingUpdates) {
            return;
        }
        if (isDirty) {
            isDirty = false;
            sdlUpdate(true, listener);
        } else if (listener != null) {
            listener.onComplete(true);
        }
    }

    private synchronized void sdlUpdate(Boolean supersedePreviousOperations, final CompletionListener listener) {

        currentOperationListener = listener;

        CurrentScreenDataUpdatedListener currentScreenDataUpdateListener = new CurrentScreenDataUpdatedListener() {
            @Override
            public void onUpdate(TextAndGraphicState newScreenData) {
                if (newScreenData != null) {
                    // Update our current screen data
                    currentScreenData = newScreenData;
                    updatePendingOperationsWithNewScreenData();
                }
            }

            @Override
            public void onError(TextAndGraphicState errorState) {
                // Invalidate data that's different from our current screen data
                resetFieldsToCurrentScreenData();
                // TODO NOT COVERED l201
                updatePendingOperationsWithFailedScreenState(errorState);
            }
        };

        updateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager.get(), defaultMainWindowCapability, currentScreenData, currentState(), currentOperationListener, currentScreenDataUpdateListener);
        transactionQueue.add(updateOperation, false);
    }

    void resetFieldsToCurrentScreenData() {
        textField1 = currentScreenData.getTextField1();
        textField2 = currentScreenData.getTextField2();
        textField3 = currentScreenData.getTextField3();
        textField4 = currentScreenData.getTextField4();
        mediaTrackTextField = currentScreenData.getMediaTrackTextField();
        title = currentScreenData.getTitle();
        textAlignment = currentScreenData.getTextAlignment();
        textField1Type = currentScreenData.getTextField1Type();
        textField2Type = currentScreenData.getTextField2Type();
        textField3Type = currentScreenData.getTextField3Type();
        textField4Type = currentScreenData.getTextField4Type();
        primaryGraphic = currentScreenData.getPrimaryGraphic();
        secondaryGraphic = currentScreenData.getSecondaryGraphic();
        templateConfiguration = currentScreenData.getTemplateConfiguration();
    }

    //Updates pending task with current screen data
    void updatePendingOperationsWithNewScreenData() {
        for (Task task : transactionQueue.getTasksAsList()) {
            if (!(task instanceof TextAndGraphicUpdateOperation)) {
                continue;
            }
            ((TextAndGraphicUpdateOperation) task).setCurrentScreenData(currentScreenData);
        }
        if (this.softButtonManager.get() != null && currentScreenData.getTextField1() != null) {
            this.softButtonManager.get().setCurrentMainField1(currentScreenData.getTextField1());
        }
    }

    void updatePendingOperationsWithFailedScreenState(TextAndGraphicState errorState) {
        for (Task task : transactionQueue.getTasksAsList()) {
            // TODO NOT COVERED l242-246
            if (!(task instanceof TextAndGraphicUpdateOperation)) {
                continue;
            }
            ((TextAndGraphicUpdateOperation) task).setCurrentScreenData(currentScreenData);
        }
        updateOperation.updateTargetStateWithErrorState(errorState);
    }

    interface CurrentScreenDataUpdatedListener {
        void onUpdate(TextAndGraphicState newState);

        void onError(TextAndGraphicState errorState);
    }


    private List<String> findNonNullTextFields() {
        List<String> array = new ArrayList<>();

        if (textField1 != null) {
            array.add(textField1);
        }

        if (textField2 != null) {
            array.add(textField2);
        }

        if (textField3 != null) {
            array.add(textField3);
        }

        if (textField4 != null) {
            array.add(textField4);
        }

        if (title != null) {
            array.add(title);
        }

        if (mediaTrackTextField != null) {
            array.add(mediaTrackTextField);
        }

        return array;
    }

    Boolean hasData() {
        boolean hasTextFields = (findNonNullTextFields().size() > 0);
        boolean hasImageFields = (primaryGraphic != null) || (secondaryGraphic != null);

        return hasTextFields || hasImageFields;
    }

    abstract SdlArtwork getBlankArtwork();


    // Convert to State
    TextAndGraphicState currentState() {
        return new TextAndGraphicState(textField1, textField2, textField3, textField4, mediaTrackTextField,
                title, primaryGraphic, secondaryGraphic, textAlignment, textField1Type, textField2Type, textField3Type, textField4Type, templateConfiguration);
    }

    // Getters / Setters
    void setTextAlignment(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
        // If we aren't batching, send the update immediately, if we are, set ourselves as dirty (so we know we should send an update after the batch ends)
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    TextAlignment getTextAlignment() {
        return textAlignment;
    }

    void setMediaTrackTextField(String mediaTrackTextField) {
        this.mediaTrackTextField = mediaTrackTextField;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    String getMediaTrackTextField() {
        return mediaTrackTextField;
    }

    void setTextField1(String textField1) {
        this.textField1 = textField1;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    String getTextField1() {
        return textField1;
    }

    void setTextField2(String textField2) {
        this.textField2 = textField2;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    String getTextField2() {
        return textField2;
    }

    void setTextField3(String textField3) {
        this.textField3 = textField3;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    String getTextField3() {
        return textField3;
    }

    void setTextField4(String textField4) {
        this.textField4 = textField4;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    String getTextField4() {
        return textField4;
    }

    void setTextField1Type(MetadataType textField1Type) {
        this.textField1Type = textField1Type;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    MetadataType getTextField1Type() {
        return textField1Type;
    }

    void setTextField2Type(MetadataType textField2Type) {
        this.textField2Type = textField2Type;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    MetadataType getTextField2Type() {
        return textField2Type;
    }

    void setTextField3Type(MetadataType textField3Type) {
        this.textField3Type = textField3Type;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    MetadataType getTextField3Type() {
        return textField3Type;
    }

    void setTextField4Type(MetadataType textField4Type) {
        this.textField4Type = textField4Type;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    MetadataType getTextField4Type() {
        return textField4Type;
    }

    void setTitle(String title) {
        this.title = title;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    String getTitle() {
        return title;
    }

    void setPrimaryGraphic(SdlArtwork primaryGraphic) {
        this.primaryGraphic = primaryGraphic;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    SdlArtwork getPrimaryGraphic() {
        return primaryGraphic;
    }

    void setSecondaryGraphic(SdlArtwork secondaryGraphic) {
        this.secondaryGraphic = secondaryGraphic;
        if (!batchingUpdates) {
            sdlUpdate(true, null);
        } else {
            isDirty = true;
        }
    }

    /**
     * Change the current layout to a new layout and optionally update the layout's night and day color schemes.
     * The values set for the text, graphics, buttons and template title persist between layout changes.
     * To update the text, graphics, buttons and template title at the same time as the template, batch all the updates between beginTransaction and commit.
     * If the layout update fails while batching, then the updated text, graphics, buttons or template title will also not be updated.
     *
     * @param templateConfiguration The new configuration of the template, including the layout and color scheme.
     * @param listener              A listener that will be called when the layout change finished.
     */
    void changeLayout(TemplateConfiguration templateConfiguration, CompletionListener listener) {
        setTemplateConfiguration(templateConfiguration);
        if (!batchingUpdates) {
            sdlUpdate(true, listener);
        } else {
            isDirty = true;
        }
    }

    TemplateConfiguration getTemplateConfiguration() {
        return templateConfiguration;
    }

    void setTemplateConfiguration(TemplateConfiguration templateConfiguration) {
        // Don't do the `isBatchingUpdates` like elsewhere because the call is already handled in `changeLayout(TemplateConfiguration templateConfiguration, CompletionListener listener) `
        this.templateConfiguration = templateConfiguration;
    }

    SdlArtwork getSecondaryGraphic() {
        return secondaryGraphic;
    }

    void setBatchUpdates(boolean batching) {
        this.batchingUpdates = batching;
    }

    private void addListeners() {
        // add listener
        hmiListener = new OnRPCNotificationListener() {
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
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

        onDisplaysCapabilityListener = new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                // instead of using the parameter it's more safe to use the convenience method
                List<DisplayCapability> capabilities = SystemCapabilityManager.convertToList(capability, DisplayCapability.class);
                if (capabilities == null || capabilities.size() == 0) {
                    DebugTool.logError(TAG, "TextAndGraphic Manager - Capabilities sent here are null or empty");
                    defaultMainWindowCapability = null;
                } else {
                    DisplayCapability display = capabilities.get(0);
                    for (WindowCapability windowCapability : display.getWindowCapabilities()) {
                        int currentWindowID = windowCapability.getWindowID() != null ? windowCapability.getWindowID() : PredefinedWindows.DEFAULT_WINDOW.getValue();
                        if (currentWindowID == PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                            // Check if the window capability is equal to the one we already have. If it is, abort.
                            if (defaultMainWindowCapability != null && defaultMainWindowCapability.getStore().equals(windowCapability.getStore())) {
                                return;
                            }
                            defaultMainWindowCapability = windowCapability;
                        }
                    }
                }
                // Update the queue's suspend state
                updateTransactionQueueSuspended();
                if (hasData()) {
                    // HAX: Capability updates cannot supersede earlier updates because of the case where a developer batched a `changeLayout` call w/ T&G changes on < 6.0 systems could cause this to come in before the operation completes. That would cause the operation to report a "failure" (because it was superseded by this call) when in fact the operation didn't fail at all and is just being adjusted.
                    sdlUpdate(false, null);
                }
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
    }
}
