/*
 * Copyright (c)  2021 Livio, Inc.
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
 * Created by rhenigan on 8/9/21 1:52 PM
 *
 */

package com.smartdevicelink.managers.screen.choiceset;

import androidx.annotation.NonNull;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.CancelInteraction;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

class PreloadPresentChoicesOperation extends Task {

    private static final String TAG = "PreloadPresentChoicesOperation";
    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    private final WindowCapability defaultMainWindowCapability;
    private final String displayName;
    private final ArrayList<ChoiceCell> cellsToUpload;
    private final BaseChoiceSetManager.ChoicesOperationCompletionListener completionListener;
    private final ChoiceSetSelectionListener selectionListener;
    private final boolean isVROptional;
    private boolean choiceError = false;
    private HashSet<ChoiceCell> loadedCells;
    private final ChoiceSet choiceSet;
    private static Integer choiceId = 0;
    private final Integer cancelID;
    private final InteractionMode presentationMode;
    private final KeyboardProperties originalKeyboardProperties;
    private KeyboardProperties keyboardProperties;
    private ChoiceCell selectedCell;
    private TriggerSource selectedTriggerSource;
    private boolean updatedKeyboardProperties;
    private OnRPCNotificationListener keyboardRPCListener;
    Integer selectedCellRow;
    KeyboardListener keyboardListener;
    final SdlMsgVersion sdlMsgVersion;
    private enum SDLPreloadPresentChoicesOperationState {
        NOT_STARTED,
        UPLOADING_IMAGES,
        UPLOADING_CHOICES,
        UPDATING_KEYBOARD_PROPERTIES,
        PRESENTING_CHOICES,
        CANCELLING_PRESENT_CHOICES,
        RESETTING_KEYBOARD_PROPERTIES,
        FINISHING
    }
    private SDLPreloadPresentChoicesOperationState currentState;

    PreloadPresentChoicesOperation(ISdl internalInterface, FileManager fileManager, String displayName, WindowCapability defaultWindowCapability,
                                          Boolean isVROptional, LinkedHashSet<ChoiceCell> cellsToPreload, HashSet<ChoiceCell> loadedCells, BaseChoiceSetManager.ChoicesOperationCompletionListener listener) {
        super("PreloadPresentChoiceOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.displayName = displayName;
        this.defaultMainWindowCapability = defaultWindowCapability;
        this.isVROptional = isVROptional;
        this.cellsToUpload = new ArrayList<>(cellsToPreload);
        this.completionListener = listener;
        this.keyboardListener = null;
        this.choiceSet = null;
        this.presentationMode = null;
        this.cancelID = null;
        this.originalKeyboardProperties = null;
        this.keyboardProperties = null;
        this.selectedCellRow = null;
        this.sdlMsgVersion = internalInterface.getSdlMsgVersion();
        this.loadedCells = loadedCells;
        this.currentState = SDLPreloadPresentChoicesOperationState.NOT_STARTED;
        this.selectionListener = null;
    }

    PreloadPresentChoicesOperation(ISdl internalInterface, FileManager fileManager, ChoiceSet choiceSet, InteractionMode mode,
                                   KeyboardProperties originalKeyboardProperties, KeyboardListener keyboardListener, Integer cancelID, String displayName, WindowCapability windowCapability,
                                   Boolean isVROptional, HashSet<ChoiceCell> loadedCells, BaseChoiceSetManager.ChoicesOperationCompletionListener preloadListener, ChoiceSetSelectionListener listener) {
        super("PreloadPresentChoiceOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.keyboardListener = keyboardListener;
        this.choiceSet = choiceSet;
        this.choiceSet.canceledListener = new ChoiceSetCanceledListener() {
            @Override
            public void onChoiceSetCanceled() {
                cancelInteraction();
            }
        };
        this.presentationMode = mode;
        this.cancelID = cancelID;
        this.originalKeyboardProperties = originalKeyboardProperties;
        this.keyboardProperties = originalKeyboardProperties;
        this.selectedCellRow = null;
        this.sdlMsgVersion = internalInterface.getSdlMsgVersion();
        this.fileManager = new WeakReference<>(fileManager);
        this.displayName = displayName;
        this.defaultMainWindowCapability = windowCapability;
        this.isVROptional = isVROptional;
        this.cellsToUpload = new ArrayList<>(choiceSet.getChoices());
        this.completionListener = preloadListener;
        this.selectionListener = listener;
        this.loadedCells = loadedCells;
        this.currentState = SDLPreloadPresentChoicesOperationState.NOT_STARTED;
    }

    @Override
    public void onExecute() {
        if (this.getState() == CANCELED) {
            return;
        }
        DebugTool.logInfo(TAG, "Choice Operation: Executing preload choices operation");
        // Enforce unique cells and remove cells that are already loaded
        this.cellsToUpload.removeAll(loadedCells);
        this.assignIdsToCells(this.cellsToUpload);
        makeCellsToUploadUnique(this.cellsToUpload);

        if (this.choiceSet != null) {
            updateChoiceSet(this.choiceSet, this.loadedCells, new HashSet<>(this.cellsToUpload));
        }
        // Start uploading cell artworks, then cells themselves, then determine if we want to present, then update keyboard properties if necessary, then present the choice set, then revert keyboard properties if necessary
        preloadCellArtworks(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                // If some artworks failed to upload, we are still going to try to load the cells
                if (getState()==CANCELED || !success) {
                    finishOperation(false);
                    return;
                }
                preloadCells(new CompletionListener() {
                    @Override
                    public void onComplete(boolean success) {
                        if (getState()==CANCELED || !success) {
                            finishOperation(false);
                            return;
                        }

                        if (choiceSet == null) {
                            finishOperation(false);
                            return;
                        }
                        DebugTool.logInfo(TAG, "Choice Operation: Executing present choice set operation");

                        updateKeyboardProperties(new CompletionListener() {
                            @Override
                            public void onComplete(boolean success) {
                                if (getState()==CANCELED || !success) {
                                    finishOperation(false);
                                    return;
                                }
                                presentChoiceSet(new CompletionListener() {
                                    @Override
                                    public void onComplete(boolean success) {
                                        resetKeyboardProperties(new CompletionListener() {
                                            @Override
                                            public void onComplete(boolean success) {
                                                finishOperation(success);
                                                return;
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    // Preload operation methods
    private void preloadCellArtworks(@NonNull final CompletionListener listener) {
        this.currentState = SDLPreloadPresentChoicesOperationState.UPLOADING_IMAGES;

        List<SdlArtwork> artworksToUpload = artworksToUpload();

        if (artworksToUpload.size() == 0) {
            DebugTool.logInfo(TAG, "Choice Preload: No Choice Artworks to upload");
            listener.onComplete(true);
            return;
        }

        if (fileManager.get() != null) {
            fileManager.get().uploadArtworks(artworksToUpload, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null && errors.size() > 0) {
                        DebugTool.logError(TAG, "Error uploading choice cell Artworks: " + errors.toString());
                        listener.onComplete(false);
                    } else {
                        DebugTool.logInfo(TAG, "Choice Artworks Uploaded");
                        listener.onComplete(true);
                    }
                }
            });
        } else {
            DebugTool.logError(TAG, "File manager is null in choice preload operation");
            listener.onComplete(false);
        }
    }

    private void preloadCells(@NonNull final CompletionListener listener) {
        this.currentState = SDLPreloadPresentChoicesOperationState.UPLOADING_CHOICES;

        final List<CreateInteractionChoiceSet> choiceRPCs = new ArrayList<>(cellsToUpload.size());
        for (ChoiceCell cell : cellsToUpload) {
            CreateInteractionChoiceSet csCell = choiceFromCell(cell);
            if (csCell != null) {
                choiceRPCs.add(csCell);
            }
        }

        if (choiceRPCs.size() == 0) {
            DebugTool.logError(TAG, " All Choice cells to send are null, so the choice set will not be shown");
            listener.onComplete(true);
            return;
        }

        if (internalInterface.get() != null) {
            internalInterface.get().sendRPCs(choiceRPCs, new OnMultipleRequestListener() {
                @Override
                public void onUpdate(int remainingRequests) {

                }

                @Override
                public void onFinished() {
                    DebugTool.logInfo(TAG, "Finished pre loading choice cells");
                    listener.onComplete(!choiceError);
                    choiceError = false;
                }

                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    if (!response.getSuccess()) {
                        DebugTool.logError(TAG, "There was an error uploading a choice cell: " + response.getInfo() + " resultCode: " + response.getResultCode());
                        choiceError = true;
                    } else {
                        for (CreateInteractionChoiceSet rpc : choiceRPCs) {
                            if (correlationId == rpc.getCorrelationID()) {
                                loadedCells.add(cellFromChoiceId(rpc.getChoiceSet().get(0).getChoiceID()));
                            }
                        }
                    }
                }
            });
        } else {
            DebugTool.logError(TAG, "Internal Interface null in preload choice operation");
            listener.onComplete(!choiceError);
        }
    }

    private void updateKeyboardProperties(final CompletionListener listener) {
        this.currentState = SDLPreloadPresentChoicesOperationState.UPDATING_KEYBOARD_PROPERTIES;
        if (keyboardListener == null) {
            if (listener != null) {
                listener.onComplete(true);
            }
            return;
        }

        addListeners();

        if (keyboardListener != null && choiceSet.getCustomKeyboardConfiguration() != null) {
            keyboardProperties = choiceSet.getCustomKeyboardConfiguration();
            updatedKeyboardProperties = true;
        }

        SetGlobalProperties setGlobalProperties = new SetGlobalProperties();
        setGlobalProperties.setKeyboardProperties(keyboardProperties);
        setGlobalProperties.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {

                if (!response.getSuccess()) {
                    if (listener != null) {
                        listener.onComplete(false);
                    }
                    DebugTool.logError(TAG, "Error Setting keyboard properties in present choice set operation");
                    return;
                }

                updatedKeyboardProperties = true;

                if (listener != null) {
                    listener.onComplete(true);
                }
                DebugTool.logInfo(TAG, "Success Setting keyboard properties in present choice set operation");
            }
        });
        if (internalInterface.get() != null) {
            internalInterface.get().sendRPC(setGlobalProperties);
        } else {
            DebugTool.logError(TAG, "Internal interface null - present choice set op - choice");
            listener.onComplete(false);
        }
    }

    void resetKeyboardProperties(final CompletionListener listener) {
        this.currentState = SDLPreloadPresentChoicesOperationState.RESETTING_KEYBOARD_PROPERTIES;
        if (this.keyboardListener == null || this.originalKeyboardProperties == null) {
            if(listener != null) {
                listener.onComplete(true);
                finishOperation(true);
                return;
            }
        }
        SetGlobalProperties setProperties = new SetGlobalProperties();
        setProperties.setKeyboardProperties(this.originalKeyboardProperties);
        setProperties.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()) {
                    updatedKeyboardProperties = false;
                    DebugTool.logInfo(TAG, "Successfully reset choice keyboard properties to original config");
                } else {
                    DebugTool.logError(TAG, "Failed to reset choice keyboard properties to original config " + response.getResultCode() + ", " + response.getInfo());
                }
                if (listener != null) {
                    listener.onComplete(response.getSuccess());
                    if (response.getSuccess()) {
                        finishOperation(true);
                    }
                }
            }
        });

        if (internalInterface.get() != null) {
            internalInterface.get().sendRPC(setProperties);
            internalInterface.get().removeOnRPCNotificationListener(FunctionID.ON_KEYBOARD_INPUT, keyboardRPCListener);
        } else {
            DebugTool.logError(TAG, "Internal Interface null when finishing choice keyboard reset");
            listener.onComplete(false);
        }
    }

    private void presentChoiceSet(final CompletionListener listener) {
        this.currentState = SDLPreloadPresentChoicesOperationState.PRESENTING_CHOICES;
        PerformInteraction pi = getPerformInteraction();
        pi.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (!response.getSuccess()) {
                    DebugTool.logError(TAG, "Presenting Choice set failed: " + response.getInfo());

                    if (selectionListener != null) {
                        selectionListener.onError(response.getInfo());
                    }
                    if (listener != null) {
                        listener.onComplete(false);
                    }
                    finishOperation(false);
                    return;
                }

                PerformInteractionResponse performInteractionResponse = (PerformInteractionResponse) response;
                setSelectedCellWithId(performInteractionResponse.getChoiceID());
                selectedTriggerSource = performInteractionResponse.getTriggerSource();

                if (selectionListener != null && selectedCell != null && selectedTriggerSource != null && selectedCellRow != null) {
                    selectionListener.onChoiceSelected(selectedCell, selectedTriggerSource, selectedCellRow);
                    if (listener != null) {
                        listener.onComplete(true);
                    }
                }
            }
        });
        if (internalInterface.get() != null) {
            internalInterface.get().sendRPC(pi);
        } else {
            DebugTool.logError(TAG, "Internal Interface null when presenting choice set in operation");
            if (selectionListener != null) {
                selectionListener.onError("Internal Interface null");
            }
            if (listener != null) {
                listener.onComplete(false);
            }
        }
    }

    private void cancelInteraction() {
        if ((getState() == Task.FINISHED)) {
            DebugTool.logInfo(TAG, "This operation has already finished so it can not be canceled.");
            return;
        } else if (getState() == Task.CANCELED) {
            DebugTool.logInfo(TAG, "This operation has already been canceled. It will be finished at some point during the operation.");
            return;
        } else if ((getState() == Task.IN_PROGRESS)) {
            if (this.currentState != SDLPreloadPresentChoicesOperationState.PRESENTING_CHOICES) {
                DebugTool.logInfo(TAG, "Canceling the operation before a present.");
                this.cancelTask();
                return;
            }else if (sdlMsgVersion.getMajorVersion() < 6) {
                DebugTool.logWarning(TAG, "Canceling a presented choice set is not supported on this head unit");
                this.cancelTask();
                return;
            }

            DebugTool.logInfo(TAG, "Canceling the presented choice set interaction.");

            CancelInteraction cancelInteraction = new CancelInteraction(FunctionID.PERFORM_INTERACTION.getId(), cancelID);
            cancelInteraction.setOnRPCResponseListener(new OnRPCResponseListener() {
                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    if (response.getSuccess()) {
                        DebugTool.logInfo(TAG, "Canceled the presented choice set " + ((response.getResultCode() == Result.SUCCESS) ? "successfully" : "unsuccessfully"));
                    } else {
                        DebugTool.logError(TAG, "Error canceling the presented choice set: " + correlationId + " with error: " + response.getInfo());
                    }
                }
            });

            if (internalInterface.get() != null) {
                internalInterface.get().sendRPC(cancelInteraction);
            } else {
                DebugTool.logError(TAG, "Internal interface null - could not send cancel interaction for choice set");
            }
        } else {
            DebugTool.logInfo(TAG, "Canceling a choice set that has not yet been sent to Core");
            this.cancelTask();
        }
    }

    // Present Helpers
    void setSelectedCellWithId(Integer cellId) {
        if (choiceSet.getChoices() != null && cellId != null) {
            List<ChoiceCell> cells = choiceSet.getChoices();
            for (int i = 0; i < cells.size(); i++) {
                if (cells.get(i).getChoiceId() == cellId) {
                    selectedCell = cells.get(i);
                    selectedCellRow = i;
                    return;
                }
            }
        }
    }

    PerformInteraction getPerformInteraction() {
        if (this.choiceSet == null) {
            return new PerformInteraction();
        }
        PerformInteraction pi = new PerformInteraction(choiceSet.getTitle(), presentationMode, getChoiceIds());
        pi.setInitialPrompt(choiceSet.getInitialPrompt());
        pi.setHelpPrompt(choiceSet.getHelpPrompt());
        pi.setTimeoutPrompt(choiceSet.getTimeoutPrompt());
        pi.setVrHelp(choiceSet.getVrHelpList());
        pi.setTimeout(choiceSet.getTimeout() * 1000);
        pi.setInteractionLayout(getLayoutMode());
        pi.setCancelID(cancelID);
        return pi;
    }

    private void assignIdsToCells(ArrayList<ChoiceCell> cells) {
        for (ChoiceCell cell : cells) {
            cell.setChoiceId(this.nextChoiceId());
        }
    }

    private void setNextChoiceId(int nextChoiceId) {
        choiceId = nextChoiceId;
    }

    private int nextChoiceId() {
        return ++choiceId;
    }

    // Choice Uniqueness
    void makeCellsToUploadUnique(ArrayList<ChoiceCell> cellsToUpload) {
        if (cellsToUpload.size() == 0) {
            return;
        }

        ArrayList<ChoiceCell> strippedCellsToUpload = (ArrayList<ChoiceCell>) cellsToUpload.clone();
        ArrayList<ChoiceCell> strippedLoadedCells = new ArrayList<>((HashSet<ChoiceCell>) loadedCells.clone());
        boolean supportsChoiceUniqueness = (sdlMsgVersion.getMajorVersion() >= 7 && sdlMsgVersion.getMinorVersion() >= 1);
        if (supportsChoiceUniqueness) {
            removeUnusedProperties(strippedCellsToUpload);
            removeUnusedProperties(strippedLoadedCells);
        }

        addUniqueNamesToCells(strippedCellsToUpload, strippedLoadedCells, supportsChoiceUniqueness);
        transferUniqueNamesFromCells(strippedCellsToUpload, cellsToUpload);
    }

    private void updateChoiceSet(ChoiceSet choiceSet, HashSet<ChoiceCell> loadedCells, HashSet<ChoiceCell> cellsToUpload) {
        ArrayList<ChoiceCell> choiceSetCells = new ArrayList<>();
        for (ChoiceCell cell : choiceSet.getChoices()) {
            if (loadedCells.contains(cell) || cellsToUpload.contains(cell)) {
                choiceSetCells.add(cell);
            }
        }
        this.choiceSet.setChoices((List<ChoiceCell>) choiceSetCells.clone());
    }

    private void transferUniqueNamesFromCells(ArrayList<ChoiceCell> fromCells, ArrayList<ChoiceCell> toCells) {
        for (int i = 0; i< fromCells.size(); i++) {
            toCells.get(i).setUniqueTextId(fromCells.get(i).getUniqueTextId());
        }
    }

    List<ChoiceCell> removeUnusedProperties(List<ChoiceCell> choiceCells) {
        List<ChoiceCell> strippedCellsClone = cloneChoiceCellList(choiceCells);
        // Clone Cells
        for (ChoiceCell cell : strippedCellsClone) {
            // Strip away fields that cannot be used to determine uniqueness visually including fields not supported by the HMI
            cell.setVoiceCommands(null);

            if (!shouldSendChoicePrimaryImage()) {
                cell.setArtwork(null);
            }
            if (!shouldSendChoiceSecondaryText()) {
                cell.setSecondaryText(null);
            }
            if (!shouldSendChoiceTertiaryText()) {
                cell.setTertiaryText(null);
            }
            if (!shouldSendChoiceSecondaryImage()) {
                cell.setSecondaryArtwork(null);
            }
        }
        return strippedCellsClone;
    }

    private List<ChoiceCell> cloneChoiceCellList(List<ChoiceCell> originalList) {
        if (originalList == null) {
            return null;
        }
        List<ChoiceCell> clone = new ArrayList<>();
        for (ChoiceCell choiceCell : originalList) {
            clone.add(choiceCell.clone());
        }
        return clone;
    }

    private boolean hasImageFieldOfName(ImageFieldName imageFieldName) {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(defaultMainWindowCapability, imageFieldName);
    }

    private boolean hasTextFieldOfName(TextFieldName textFieldName) {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(defaultMainWindowCapability, textFieldName);
    }

    /**
     * Checks if 2 or more cells have the same text/title. In case this condition is true, this function will handle the presented issue by adding "(count)".
     * E.g. Choices param contains 2 cells with text/title "Address" will be handled by updating the uniqueText/uniqueTitle of the second cell to "Address (2)".
     */
    void addUniqueNamesToCells(List<ChoiceCell> cellsToUpload, List<ChoiceCell> loadedCells, boolean supportsChoiceUniqueness) {
        HashMap<Object, ArrayList<Integer>> dictCounter = new HashMap<>();

        for (ChoiceCell loadedCell : loadedCells) {
            Object cellKey = supportsChoiceUniqueness ? loadedCell : loadedCell.getText();
            int cellUniqueId = loadedCell.getUniqueTextId();
            if (dictCounter.get(cellKey) == null) {
                ArrayList<Integer> uniqueIds = new ArrayList<>();
                uniqueIds.add(cellUniqueId);
                dictCounter.put(cellKey, uniqueIds);
            } else {
                dictCounter.get(cellKey).add(cellUniqueId);
            }
        }

        for (ChoiceCell cell : cellsToUpload) {
            Object cellKey = supportsChoiceUniqueness ? cell : cell.getText();
            if (dictCounter.get(cellKey) == null) {
                ArrayList<Integer> uniqueTextIds = new ArrayList<>();
                uniqueTextIds.add(cell.getUniqueTextId());
                dictCounter.put(cellKey, uniqueTextIds);
            } else {
                ArrayList<Integer> uniqueIds = dictCounter.get(cellKey);
                Integer lowestMissingUniqueId = uniqueIds.get(uniqueIds.size() - 1) + 1;
                for (int i = 1; i < dictCounter.get(cellKey).size() + 1; i++) {
                    if (i != dictCounter.get(cellKey).get(i -1)) {
                        lowestMissingUniqueId = i;
                        break;
                    }
                }

                cell.setUniqueTextId(lowestMissingUniqueId);
                dictCounter.get(cellKey).add(cell.getUniqueTextId() - 1, cell.getUniqueTextId());
            }
        }
    }

    // Finding Cells
    private ChoiceCell cellFromChoiceId(int choiceId) {
        for (ChoiceCell cell : this.cellsToUpload) {
            if (cell.getChoiceId() == choiceId) {
                return cell;
            }
        }
        return null;
    }

    // Assembling Choice RPCs
    private CreateInteractionChoiceSet choiceFromCell(ChoiceCell cell) {

        List<String> vrCommands;
        if (cell.getVoiceCommands() == null) {
            vrCommands = isVROptional ? null : Collections.singletonList(String.valueOf(cell.getChoiceId()));
        } else {
            vrCommands = cell.getVoiceCommands();
        }

        String menuName = shouldSendChoiceText() ? cell.getUniqueText() : null;

        if (menuName == null) {
            DebugTool.logError(TAG, "Could not convert Choice Cell to CreateInteractionChoiceSet. It will not be shown. Cell: " + cell.toString());
            return null;
        }

        String secondaryText = shouldSendChoiceSecondaryText() ? cell.getSecondaryText() : null;
        String tertiaryText = shouldSendChoiceTertiaryText() ? cell.getTertiaryText() : null;

        Image image = shouldSendChoicePrimaryImage() && cell.getArtwork() != null ? cell.getArtwork().getImageRPC() : null;
        Image secondaryImage = shouldSendChoiceSecondaryImage() && cell.getSecondaryArtwork() != null ? cell.getSecondaryArtwork().getImageRPC() : null;

        Choice choice = new Choice(cell.getChoiceId(), menuName);
        choice.setVrCommands(vrCommands);
        choice.setSecondaryText(secondaryText);
        choice.setTertiaryText(tertiaryText);
        choice.setIgnoreAddingVRItems(true);

        if (fileManager.get() != null) {
            if (image != null && (cell.getArtwork().isStaticIcon() || fileManager.get().hasUploadedFile(cell.getArtwork()))) {
                choice.setImage(image);
            }
            if (secondaryImage != null && (cell.getSecondaryArtwork().isStaticIcon() || fileManager.get().hasUploadedFile(cell.getSecondaryArtwork()))) {
                choice.setSecondaryImage(secondaryImage);
            }
        }

        return new CreateInteractionChoiceSet(choice.getChoiceID(), Collections.singletonList(choice));
    }

    boolean shouldSendChoiceText() {
        if (this.displayName != null && this.displayName.equals(DisplayType.GEN3_8_INCH.toString())) {
            return true;
        }
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(defaultMainWindowCapability, TextFieldName.menuName);
    }

    boolean shouldSendChoiceSecondaryText() {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(defaultMainWindowCapability, TextFieldName.secondaryText);
    }

    boolean shouldSendChoiceTertiaryText() {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(defaultMainWindowCapability, TextFieldName.tertiaryText);
    }

    boolean shouldSendChoicePrimaryImage() {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(defaultMainWindowCapability, ImageFieldName.choiceImage);
    }

    boolean shouldSendChoiceSecondaryImage() {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(defaultMainWindowCapability, ImageFieldName.choiceSecondaryImage);
    }

    // SDL Notifications
    private void addListeners() {

        keyboardRPCListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                if (getState() == Task.CANCELED) {
                    finishOperation(false);
                    return;
                }

                if (keyboardListener == null) {
                    DebugTool.logError(TAG, "Received Keyboard Input But Listener is null");
                    return;
                }

                OnKeyboardInput onKeyboard = (OnKeyboardInput) notification;
                keyboardListener.onKeyboardDidSendEvent(onKeyboard.getEvent(), onKeyboard.getData());

                if (onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_VOICE) || onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_SUBMITTED)) {
                    // Submit Voice or Text
                    keyboardListener.onUserDidSubmitInput(onKeyboard.getData(), onKeyboard.getEvent());
                } else if (onKeyboard.getEvent().equals(KeyboardEvent.KEYPRESS)) {
                    // Notify of Keypress
                    keyboardListener.updateAutocompleteWithInput(onKeyboard.getData(), new KeyboardAutocompleteCompletionListener() {
                        @Override
                        public void onUpdatedAutoCompleteList(List<String> updatedAutoCompleteList) {
                            keyboardProperties.setAutoCompleteList(updatedAutoCompleteList != null ? updatedAutoCompleteList : new ArrayList<String>());
                            keyboardProperties.setAutoCompleteText(updatedAutoCompleteList != null && !updatedAutoCompleteList.isEmpty() ? updatedAutoCompleteList.get(0) : null);
                            updateKeyboardProperties(null);
                        }
                    });

                    keyboardListener.updateCharacterSetWithInput(onKeyboard.getData(), new KeyboardCharacterSetCompletionListener() {
                        @Override
                        public void onUpdatedCharacterSet(List<String> updatedCharacterSet) {
                            keyboardProperties.setLimitedCharacterList(updatedCharacterSet);
                            updateKeyboardProperties(null);
                        }
                    });
                } else if (onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_ABORTED) || onKeyboard.getEvent().equals(KeyboardEvent.ENTRY_CANCELLED)) {
                    // Notify of abort / Cancellation
                    keyboardListener.onKeyboardDidAbortWithReason(onKeyboard.getEvent());
                } else if (onKeyboard.getEvent().equals(KeyboardEvent.INPUT_KEY_MASK_ENABLED) || onKeyboard.getEvent().equals(KeyboardEvent.INPUT_KEY_MASK_DISABLED)) {
                    keyboardListener.onKeyboardDidUpdateInputMask(onKeyboard.getEvent());
                }
            }
        };

        if (internalInterface.get() != null) {
            internalInterface.get().addOnRPCNotificationListener(FunctionID.ON_KEYBOARD_INPUT, keyboardRPCListener);
        } else {
            DebugTool.logError(TAG, "Present Choice Set Keyboard Listener Not Added");
        }
    }

    public void setLoadedCells(HashSet<ChoiceCell> loadedCells) {
        this.loadedCells = loadedCells;
    }

    List<SdlArtwork> artworksToUpload() {
        List<SdlArtwork> artworksToUpload = new ArrayList<>();
        for (ChoiceCell cell : cellsToUpload) {
            if (shouldSendChoicePrimaryImage() && fileManager.get() != null && fileManager.get().fileNeedsUpload(cell.getArtwork())) {
                artworksToUpload.add(cell.getArtwork());
            }
            if (shouldSendChoiceSecondaryImage() && fileManager.get() != null && fileManager.get().fileNeedsUpload(cell.getSecondaryArtwork())) {
                artworksToUpload.add(cell.getSecondaryArtwork());
            }
        }
        return artworksToUpload;
    }

    LayoutMode getLayoutMode() {
        switch (choiceSet.getLayout()) {
            case CHOICE_SET_LAYOUT_LIST:
                return keyboardListener != null ? LayoutMode.LIST_WITH_SEARCH : LayoutMode.LIST_ONLY;
            case CHOICE_SET_LAYOUT_TILES:
                return keyboardListener != null ? LayoutMode.ICON_WITH_SEARCH : LayoutMode.ICON_ONLY;
        }
        return LayoutMode.LIST_ONLY; // default
    }

    private List<Integer> getChoiceIds() {

        List<Integer> choiceIds = new ArrayList<>(choiceSet.getChoices().size());
        for (ChoiceCell cell : choiceSet.getChoices()) {
            choiceIds.add(cell.getChoiceId());
        }
        return choiceIds;
    }

    void finishOperation(boolean success) {
        this.currentState = SDLPreloadPresentChoicesOperationState.FINISHING;

        if (this.completionListener != null) {
            this.completionListener.onComplete(success, loadedCells);
        }

        if (this.choiceSet == null || this.choiceSet.getChoiceSetSelectionListener() == null) {
            DebugTool.logWarning(TAG, "");
        }


        if (updatedKeyboardProperties) {
            // We need to reset the keyboard properties
            SetGlobalProperties setGlobalProperties = new SetGlobalProperties();
            setGlobalProperties.setKeyboardProperties(originalKeyboardProperties);
            setGlobalProperties.setOnRPCResponseListener(new OnRPCResponseListener() {
                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    if (response.getSuccess()) {
                        updatedKeyboardProperties = false;
                        DebugTool.logInfo(TAG, "Successfully reset choice keyboard properties to original config");
                    } else {
                        DebugTool.logError(TAG, "Failed to reset choice keyboard properties to original config " + response.getResultCode() + ", " + response.getInfo());
                    }
                    onFinished();
                }
            });

            if (internalInterface.get() != null) {
                internalInterface.get().sendRPC(setGlobalProperties);
                internalInterface.get().removeOnRPCNotificationListener(FunctionID.ON_KEYBOARD_INPUT, keyboardRPCListener);
            } else {
                DebugTool.logError(TAG, "Internal Interface null when finishing choice keyboard reset");
            }
        } else {
            onFinished();
        }
    }
}
