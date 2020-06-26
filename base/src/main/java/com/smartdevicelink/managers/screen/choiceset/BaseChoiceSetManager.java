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
 *
 * Created by brettywhite on 2019-06-11.
 */

package com.smartdevicelink.managers.screen.choiceset;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.livio.taskmaster.Queue;
import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.SystemCapabilityManager;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;

/**
 * <strong>ChoiceSetManager</strong> <br>
 * <p>
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 */
abstract class BaseChoiceSetManager extends BaseSubManager {

    // additional state
    private static final int CHECKING_VOICE = 0xA0;
    private KeyboardProperties keyboardConfiguration;
    final WeakReference<FileManager> fileManager;

    OnRPCNotificationListener hmiListener;
    OnSystemCapabilityListener onDisplayCapabilityListener;
    HMILevel currentHMILevel;
    WindowCapability defaultMainWindowCapability;
    String displayName;
    SystemContext currentSystemContext;
    HashSet<ChoiceCell> preloadedChoices, pendingPreloadChoices;
    ChoiceSet pendingPresentationSet;

    // We will pass operations into this to be completed
    Queue transactionQueue;
    Task pendingPresentOperation;

    PresentKeyboardOperation currentlyPresentedKeyboardOperation;

    int nextChoiceId;
    int nextCancelId;
    final int choiceCellIdMin = 1;
    final int choiceCellCancelIdMin = 1;
    boolean isVROptional;

    BaseChoiceSetManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {
        super(internalInterface);

        // prepare operations queue
        transactionQueue = internalInterface.getTaskmaster().createQueue("ChoiceSetManagerQueue", 1, false);
        transactionQueue.pause(); // pause until HMI ready

        // capabilities
        currentSystemContext = SystemContext.SYSCTXT_MAIN;
        currentHMILevel = HMILevel.HMI_NONE;
        addListeners();

        // setting/instantiating class vars
        this.fileManager = new WeakReference<>(fileManager);
        preloadedChoices = new HashSet<>();
        pendingPreloadChoices = new HashSet<>();
        nextChoiceId = choiceCellIdMin;
        nextCancelId = choiceCellCancelIdMin;
        isVROptional = false;
        keyboardConfiguration = defaultKeyboardConfiguration();

        currentlyPresentedKeyboardOperation = null;
    }

    @Override
    public void start(CompletionListener listener){
        transitionToState(CHECKING_VOICE);
        checkVoiceOptional();
        super.start(listener);
    }

    @Override
    public void dispose(){

        // cancel the operations
        transactionQueue.close();

        currentHMILevel = null;
        currentSystemContext = null;
        defaultMainWindowCapability = null;

        pendingPresentationSet = null;
        pendingPresentOperation = null;
        isVROptional = false;
        nextChoiceId = choiceCellIdMin;
        nextCancelId = choiceCellCancelIdMin;

        // remove listeners
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
        internalInterface.removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplayCapabilityListener);

        super.dispose();
    }

    private void checkVoiceOptional(){

        CheckChoiceVROptionalOperation checkChoiceVR = new CheckChoiceVROptionalOperation(internalInterface, new CheckChoiceVROptionalInterface() {
            @Override
            public void onCheckChoiceVROperationComplete(boolean vrOptional) {
                isVROptional = vrOptional;
                transitionToState(READY);
                DebugTool.logInfo(null, "VR is optional: "+ isVROptional);
            }

            @Override
            public void onError(String error) {
                // At this point, there were errors trying to send a test PICS
                // If we reach this state, we cannot use the manager
                DebugTool.logError(error);
                transitionToState(ERROR);
                // checking VR will always be first in the queue.
                // If pre-load operations were added while this was in progress
                // clear it from the queue onError.
                transactionQueue.clear();
            }
        });
        transactionQueue.add(checkChoiceVR, false);
    }

    /**
     * Preload choices to improve performance while presenting a choice set at a later time
     * @param choices - a list of ChoiceCell objects that will be part of a choice set later
     * @param listener - a completion listener to inform when the operation is complete
     */
    public void preloadChoices(@NonNull List<ChoiceCell> choices, @Nullable final CompletionListener listener){

        if (getState() == ERROR){
            return;
        }

        final HashSet<ChoiceCell> choicesToUpload = new HashSet<>(choices);
        choicesToUpload.removeAll(preloadedChoices);
        choicesToUpload.removeAll(pendingPreloadChoices);

        if (choicesToUpload.size() == 0){
            if (listener != null){
                listener.onComplete(true);
            }
            return;
        }

        updateIdsOnChoices(choicesToUpload);

        // Add the preload cells to the pending preload choices
        pendingPreloadChoices.addAll(choicesToUpload);

        if (fileManager.get() != null) {
            PreloadChoicesOperation preloadChoicesOperation = new PreloadChoicesOperation(internalInterface, fileManager.get(), displayName, defaultMainWindowCapability, isVROptional, choicesToUpload, new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    if (success){
                        preloadedChoices.addAll(choicesToUpload);
                        pendingPreloadChoices.removeAll(choicesToUpload);
                        if (listener != null){
                            listener.onComplete(true);
                        }
                    }else {
                        DebugTool.logError("There was an error pre loading choice cells");
                        if (listener != null) {
                            listener.onComplete(false);
                        }
                    }
                }
            });

            transactionQueue.add(preloadChoicesOperation, false);
        } else {
            DebugTool.logError("File Manager was null in preload choice operation");
        }
    }

    /**
     * Deletes choices that were sent previously
     * @param choices - A list of ChoiceCell objects
     */
    public void deleteChoices(@NonNull List<ChoiceCell> choices){

        if (getState() == ERROR) {
            DebugTool.logWarning("Choice Manager In Error State");
            return;
        }

        // Find cells to be deleted that are already uploaded or are pending upload
        final HashSet<ChoiceCell> cellsToBeDeleted = choicesToBeDeletedWithArray(choices);
        HashSet<ChoiceCell> cellsToBeRemovedFromPending = choicesToBeRemovedFromPendingWithArray(choices);
        // If choices are deleted that are already uploaded or pending and are used by a pending presentation, cancel it and send an error
        HashSet<ChoiceCell> pendingPresentationChoices = new HashSet<>();
        if (pendingPresentationSet != null && pendingPresentationSet.getChoices() != null) {
            pendingPresentationChoices.addAll(pendingPresentationSet.getChoices());
        }

        if (pendingPresentOperation != null && pendingPresentOperation.getState() != Task.CANCELED && pendingPresentOperation.getState() != Task.FINISHED && (cellsToBeDeleted.retainAll(pendingPresentationChoices) || cellsToBeRemovedFromPending.retainAll(pendingPresentationChoices))){
            pendingPresentOperation.cancelTask();
            DebugTool.logWarning("Attempting to delete choice cells while there is a pending presentation operation. Pending presentation cancelled.");
            pendingPresentOperation = null;
        }

        // Remove cells from pending and delete choices
        pendingPresentationChoices.removeAll(cellsToBeRemovedFromPending);
        for (Task operation : transactionQueue.getTasksAsList()){
            if (!(operation instanceof PreloadChoicesOperation)){ continue; }
            ((PreloadChoicesOperation) operation).removeChoicesFromUpload(cellsToBeRemovedFromPending);
        }

        // Find Choices to delete
        if (cellsToBeDeleted.size() == 0){
            DebugTool.logInfo(null, "Cells to be deleted size == 0");
            return;
        }
        findIdsOnChoices(cellsToBeDeleted);

        DeleteChoicesOperation deleteChoicesOperation = new DeleteChoicesOperation(internalInterface, cellsToBeDeleted, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (!success){
                    DebugTool.logError("Failed to delete choices");
                    return;
                }
                preloadedChoices.removeAll(cellsToBeDeleted);
            }
        });
        transactionQueue.add(deleteChoicesOperation, false);
    }

    /**
     * Presents a choice set
     * @param choiceSet - The choice set to be presented. This can include Choice Cells that were preloaded or not
     * @param mode - The intended interaction mode
     * @param keyboardListener - A keyboard listener to capture user input
     */
    public void presentChoiceSet(@NonNull final ChoiceSet choiceSet, @Nullable final InteractionMode mode, @Nullable final KeyboardListener keyboardListener){

        if (getState() == ERROR) {
            DebugTool.logWarning("Choice Manager In Error State");
            return;
        }

        // Perform additional checks against the ChoiceSet
        if (!setUpChoiceSet(choiceSet)){ return; }

        if (this.pendingPresentationSet != null && pendingPresentOperation != null){
            pendingPresentOperation.cancelTask();
            DebugTool.logWarning("Presenting a choice set while one is currently presented. Cancelling previous and continuing");
        }

        this.pendingPresentationSet = choiceSet;
        preloadChoices(this.pendingPresentationSet.getChoices(), new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (!success){
                    choiceSet.getChoiceSetSelectionListener().onError("There was an error pre-loading choice set choices");
                }else{
                    sendPresentOperation(keyboardListener, mode);
                }
            }
        });
    }

    private void sendPresentOperation(KeyboardListener keyboardListener, InteractionMode mode){

        if (mode == null){
            mode = InteractionMode.MANUAL_ONLY;
        }

        findIdsOnChoiceSet(pendingPresentationSet);

        // Pass back the information to the developer
        ChoiceSetSelectionListener privateChoiceListener = new ChoiceSetSelectionListener() {
            @Override
            public void onChoiceSelected(ChoiceCell choiceCell, TriggerSource triggerSource, int rowIndex) {
                if (pendingPresentationSet.getChoiceSetSelectionListener() != null){
                    pendingPresentationSet.getChoiceSetSelectionListener().onChoiceSelected(choiceCell, triggerSource,rowIndex);
                }
            }

            @Override
            public void onError(String error) {
                if (pendingPresentationSet.getChoiceSetSelectionListener() != null){
                    pendingPresentationSet.getChoiceSetSelectionListener().onError(error);
                }
            }
        };

        PresentChoiceSetOperation presentOp;

        if (keyboardListener == null){
            // Non-searchable choice set
            DebugTool.logInfo(null, "Creating non-searchable choice set");
            presentOp = new PresentChoiceSetOperation(internalInterface, pendingPresentationSet, mode, null, null, privateChoiceListener, nextCancelId++);
        } else {
            // Searchable choice set
            DebugTool.logInfo(null, "Creating searchable choice set");
            presentOp = new PresentChoiceSetOperation(internalInterface, pendingPresentationSet, mode, keyboardConfiguration, keyboardListener, privateChoiceListener, nextCancelId++);
        }

        transactionQueue.add(presentOp, false);
        pendingPresentOperation = presentOp;
    }

    /**
     * Presents a keyboard on the Head unit to capture user input
     * @param initialText - The initial text that is used as a placeholder text. It might not work on some head units.
     * @param customKeyboardConfig - the custom keyboard configuration to be used when the keyboard is displayed
     * @param listener - A keyboard listener to capture user input
     * @return - A unique id that can be used to cancel this keyboard. If `null`, no keyboard was created.
     */
    public Integer presentKeyboard(@NonNull String initialText, @Nullable KeyboardProperties customKeyboardConfig, @NonNull KeyboardListener listener){
        if (initialText == null || initialText.length() == 0){
            DebugTool.logError("initialText cannot be an empty string.");
            return null;
        }

        if (getState() == ERROR) {
            DebugTool.logWarning("Choice Manager In Error State");
            return null;
        }

        if (pendingPresentationSet != null && pendingPresentOperation != null){
            pendingPresentOperation.cancelTask();
            pendingPresentationSet = null;
            DebugTool.logWarning("There is a current or pending choice set, cancelling and continuing.");
        }

        if (customKeyboardConfig == null){
            if (this.keyboardConfiguration != null){
                customKeyboardConfig = this.keyboardConfiguration;
            } else {
                customKeyboardConfig = defaultKeyboardConfiguration();
            }
        }

        // Present a keyboard with the choice set that we used to test VR's optional state
        DebugTool.logInfo(null, "Presenting Keyboard - Choice Set Manager");
        Integer keyboardCancelID = nextCancelId++;
        PresentKeyboardOperation keyboardOp = new PresentKeyboardOperation(internalInterface, keyboardConfiguration, initialText, customKeyboardConfig, listener, keyboardCancelID);
        currentlyPresentedKeyboardOperation = keyboardOp;
        transactionQueue.add(keyboardOp, false);
        pendingPresentOperation = keyboardOp;
        return keyboardCancelID;
    }

    /**
     *  Cancels the keyboard-only interface if it is currently showing. If the keyboard has not yet been sent to Core, it will not be sent.
     *
     *  This will only dismiss an already presented keyboard if connected to head units running SDL 6.0+.
     *
     * @param cancelID - The unique ID assigned to the keyboard, passed as the return value from `presentKeyboard`
     */
    public void dismissKeyboard(@NonNull Integer cancelID) {
        if (getState() == ERROR) {
            DebugTool.logWarning("Choice Manager In Error State");
            return;
        }

        // First, attempt to cancel the currently executing keyboard operation (Once an operation has started it is removed from the operationQueue)
        if (currentlyPresentedKeyboardOperation != null && currentlyPresentedKeyboardOperation.getCancelID().equals(cancelID)) {
            currentlyPresentedKeyboardOperation.dismissKeyboard();
            return;
        }

        // Next, attempt to cancel keyboard operations that have not yet started
        for (Task operation : transactionQueue.getTasksAsList()){
            if (!(operation instanceof PresentKeyboardOperation)){ continue; }

            PresentKeyboardOperation pendingOp = (PresentKeyboardOperation) operation;
            if (!(pendingOp.getCancelID().equals(cancelID))) { continue; }

            pendingOp.dismissKeyboard();
            break;
        }
    }

    /**
     * Set a custom keyboard configuration for this session. If set to null, it will reset to default keyboard configuration.
     * @param keyboardConfiguration - the custom keyboard configuration to be used when the keyboard is displayed
     */
    public void setKeyboardConfiguration(@Nullable KeyboardProperties keyboardConfiguration){
        if (keyboardConfiguration == null){
            this.keyboardConfiguration = defaultKeyboardConfiguration();
        } else{
            KeyboardProperties properties = new KeyboardProperties();
            properties.setLanguage((keyboardConfiguration.getLanguage() == null ? Language.EN_US : keyboardConfiguration.getLanguage()));
            properties.setKeyboardLayout((keyboardConfiguration.getKeyboardLayout() == null ? KeyboardLayout.QWERTZ : keyboardConfiguration.getKeyboardLayout()));
            properties.setKeypressMode((keyboardConfiguration.getKeypressMode() == null ? KeypressMode.RESEND_CURRENT_ENTRY : keyboardConfiguration.getKeypressMode()));
            properties.setLimitedCharacterList(keyboardConfiguration.getLimitedCharacterList());
            properties.setAutoCompleteText(keyboardConfiguration.getAutoCompleteText());
            this.keyboardConfiguration = properties;
        }
    }

    // GETTERS

    /**
     * @return A set of choice cells that have been preloaded to the head unit
     */
    public HashSet<ChoiceCell> getPreloadedChoices(){
        return this.preloadedChoices;
    }

    // CHOICE SET MANAGEMENT HELPERS

    HashSet<ChoiceCell> choicesToBeDeletedWithArray(List<ChoiceCell> choices){
        HashSet<ChoiceCell> choicesSet = new HashSet<>(choices);
        choicesSet.retainAll(this.preloadedChoices);
        return choicesSet;
    }

    HashSet<ChoiceCell> choicesToBeRemovedFromPendingWithArray(List<ChoiceCell> choices){
        HashSet<ChoiceCell> choicesSet = new HashSet<>(choices);
        choicesSet.retainAll(this.pendingPreloadChoices);
        return choicesSet;
    }

    void updateIdsOnChoices(HashSet<ChoiceCell> choices){
        for (ChoiceCell cell : choices){
            cell.setChoiceId(this.nextChoiceId);
            this.nextChoiceId++;
        }
    }

    private void findIdsOnChoiceSet(ChoiceSet choiceSet){
        findIdsOnChoices(new HashSet<>(choiceSet.getChoices()));
    }

    private void findIdsOnChoices(HashSet<ChoiceCell> choices){
        for (ChoiceCell cell : choices){
            ChoiceCell uploadCell = null;
            if (pendingPreloadChoices.contains(cell)){
                uploadCell = findIfPresent(cell, pendingPreloadChoices);
            }else if (preloadedChoices.contains(cell)){
                uploadCell = findIfPresent(cell, preloadedChoices);
            }
            if (uploadCell != null ){
                cell.setChoiceId(uploadCell.getChoiceId());
            }
        }
    }

    ChoiceCell findIfPresent(ChoiceCell cell, HashSet<ChoiceCell> set){
        if (set.contains(cell)) {
            for (ChoiceCell setCell : set) {
                if (setCell.equals(cell))
                    return setCell;
            }
        }
        return null;
    }

    // LISTENERS

    private void addListeners(){
        // DISPLAY/WINDOW CAPABILITIES - via SCM
        onDisplayCapabilityListener = new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                // instead of using the parameter it's more safe to use the convenience method
                List<DisplayCapability> capabilities = SystemCapabilityManager.convertToList(capability, DisplayCapability.class);
                if (capabilities == null || capabilities.size() == 0) {
                    DebugTool.logError("SoftButton Manager - Capabilities sent here are null or empty");
                }else {
                    DisplayCapability display = capabilities.get(0);
                    displayName = display.getDisplayName();
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
                DebugTool.logError("Unable to retrieve display capabilities. Many things will probably break. Info: "+ info);
                defaultMainWindowCapability = null;
            }
        };
        this.internalInterface.addOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplayCapabilityListener);

        // HMI UPDATES
        hmiListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnHMIStatus onHMIStatus = (OnHMIStatus)notification;
                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                    return;
                }
                HMILevel oldHMILevel = currentHMILevel;
                currentHMILevel = onHMIStatus.getHmiLevel();

                if (currentHMILevel == HMILevel.HMI_NONE){
                    transactionQueue.pause();
                }

                if (oldHMILevel == HMILevel.HMI_NONE && currentHMILevel != HMILevel.HMI_NONE){
                    transactionQueue.resume();
                }

                currentSystemContext = onHMIStatus.getSystemContext();

                if (currentSystemContext == SystemContext.SYSCTXT_HMI_OBSCURED || currentSystemContext == SystemContext.SYSCTXT_ALERT){
                    transactionQueue.pause();
                }

                if (currentSystemContext == SystemContext.SYSCTXT_MAIN && currentHMILevel != HMILevel.HMI_NONE){
                    transactionQueue.resume();
                }

            }
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
    }

    // ADDITIONAL HELPERS

    boolean setUpChoiceSet(ChoiceSet choiceSet) {

        List<ChoiceCell> choices = choiceSet.getChoices();

        // Choices are not optional here
        if (choices == null || choices.size() == 0) {
            DebugTool.logError("Cannot initiate a choice set with no choices");
            return false;
        }

        if (choiceSet.getTimeout() != null) {
            if (choiceSet.getTimeout() < 5 || choiceSet.getTimeout() > 100) {
                DebugTool.logWarning("Attempted to create a choice set with a " + choiceSet.getTimeout() + " second timeout; Only 5 - 100 seconds is valid. When using the choice set manager, setTimeout() uses seconds.");
                return false;
            }
        }

        HashSet<String> choiceTextSet = new HashSet<>();
        HashSet<String> uniqueVoiceCommands = new HashSet<>();
        int allVoiceCommandsCount = 0;
        int choiceCellWithVoiceCommandCount = 0;

        for (ChoiceCell cell : choices) {

            choiceTextSet.add(cell.getText());

            if (cell.getVoiceCommands() != null) {
                uniqueVoiceCommands.addAll(cell.getVoiceCommands());
                choiceCellWithVoiceCommandCount += 1;
                allVoiceCommandsCount += cell.getVoiceCommands().size();
            }
        }

        // Cell text MUST be unique
        if (choiceTextSet.size() < choices.size()) {
            DebugTool.logError("Attempted to create a choice set with duplicate cell text. Cell text must be unique. The choice set will not be set.");
            return false;
        }

        // All or none of the choices MUST have VR Commands
        if (choiceCellWithVoiceCommandCount > 0 && choiceCellWithVoiceCommandCount < choices.size()) {
            DebugTool.logError("If using voice recognition commands, all of the choice set cells must have unique VR commands. There are " + uniqueVoiceCommands.size() + " cells with unique voice commands and " + choices.size() + " total cells. The choice set will not be set.");
            return false;
        }

        // All VR Commands MUST be unique
        if (uniqueVoiceCommands.size() < allVoiceCommandsCount) {
            DebugTool.logError("If using voice recognition commands, all VR commands must be unique. There are " + uniqueVoiceCommands.size() + " unique VR commands and " + allVoiceCommandsCount + " VR commands. The choice set will not be set.");
            return false;
        }
        return true;
    }

    KeyboardProperties defaultKeyboardConfiguration(){
        KeyboardProperties defaultProperties = new KeyboardProperties();
        defaultProperties.setLanguage(Language.EN_US);
        defaultProperties.setKeyboardLayout(KeyboardLayout.QWERTY);
        defaultProperties.setKeypressMode(KeypressMode.RESEND_CURRENT_ENTRY);
        return defaultProperties;
    }
}
