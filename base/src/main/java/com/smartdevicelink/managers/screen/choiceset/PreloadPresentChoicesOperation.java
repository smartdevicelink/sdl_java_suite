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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class PreloadPresentChoicesOperation extends Task {

    private static final String TAG = "PreloadPresentChoicesOperation";
    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    private final WindowCapability defaultMainWindowCapability;
    private final String displayName;
    private final ArrayList<ChoiceCell> cellsToUpload;
    private final PreloadChoicesCompletionListener completionListener;
    private final ChoiceSetSelectionListener selectionListener;
    private final boolean isVROptional;
    private boolean choiceError = false;
    private HashSet<ChoiceCell> loadedCells;
    private final ChoiceSet choiceSet;
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

    public PreloadPresentChoicesOperation(ISdl internalInterface, FileManager fileManager, String displayName, WindowCapability defaultWindowCapability,
                                          Boolean isVROptional, LinkedHashSet<ChoiceCell> cellsToPreload, HashSet<ChoiceCell> loadedCells, PreloadChoicesCompletionListener listener) {
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

    public PreloadPresentChoicesOperation(ISdl internalInterface, FileManager fileManager, ChoiceSet choiceSet, InteractionMode mode,
                                          KeyboardProperties originalKeyboardProperties, KeyboardListener keyboardListener, Integer cancelID, String displayName, WindowCapability windowCapability,
                                          Boolean isVROptional, HashSet<ChoiceCell> loadedCells, PreloadChoicesCompletionListener preloadListener, ChoiceSetSelectionListener listener) {
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
        updateCellsBasedOnLoadedChoices();
        // Start uploading cell artworks, then cells themselves, then determine if we want to present, then update keyboard properties if necessary, then present the choice set, then revert keyboard properties if necessary
        preloadCellArtworks(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                // If some artworks failed to upload, we are still going to try to load the cells
                if (getState()==CANCELED || !success) {
                    finishOperation();
                    return;
                }
                preloadCells(new CompletionListener() {
                    @Override
                    public void onComplete(boolean success) {
                        if (getState()==CANCELED || !success) {
                            finishOperation();
                            return;
                        }

                        if (choiceSet == null) {
                            finishOperation();
                            return;
                        }
                        DebugTool.logInfo(TAG, "Choice Operation: Executing present choice set operation");

                        updateKeyboardProperties(new CompletionListener() {
                            @Override
                            public void onComplete(boolean success) {
                                if (getState()==CANCELED || !success) {
                                    finishOperation();
                                    return;
                                }
                                presentChoiceSet(new CompletionListener() {
                                    @Override
                                    public void onComplete(boolean success) {
                                        resetKeyboardProperties(new CompletionListener() {
                                            @Override
                                            public void onComplete(boolean success) {
                                                if (!success) {
                                                    finishOperation();
                                                    return;
                                                }
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

    //PRELOAD OPERATION METHODS
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

        List<CreateInteractionChoiceSet> choiceRPCs = new ArrayList<>(cellsToUpload.size());
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
                        loadedCells.add(cellFromChoiceId(correlationId));
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
                    finishOperation();
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

    //Present Helpers
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

    //Choice Uniqueness
    void updateCellsBasedOnLoadedChoices() {
        if (internalInterface.get().getProtocolVersion().getMajor() >= 7 && internalInterface.get().getProtocolVersion().getMinor() >= 1) {
            addUniqueNamesToCells(cellsToUpload);
        } else {
            ArrayList<ChoiceCell> strippedCellsCopy = (ArrayList<ChoiceCell>) removeUnusedProperties(cellsToUpload);
            addUniqueNamesBasedOnStrippedCells(strippedCellsCopy, cellsToUpload);
        }
        cellsToUpload.removeAll(loadedCells);
    }

    List<ChoiceCell> removeUnusedProperties(List<ChoiceCell> choiceCells) {
        List<ChoiceCell> strippedCellsClone = cloneChoiceCellList(choiceCells);
        //Clone Cells
        for (ChoiceCell cell : strippedCellsClone) {
            // Strip away fields that cannot be used to determine uniqueness visually including fields not supported by the HMI
            cell.setVoiceCommands(null);

            if (!hasImageFieldOfName(ImageFieldName.choiceImage)) {
                cell.setArtwork(null);
            }
            if (!hasTextFieldOfName(TextFieldName.secondaryText)) {
                cell.setSecondaryText(null);
            }
            if (!hasTextFieldOfName(TextFieldName.tertiaryText)) {
                cell.setTertiaryText(null);
            }
            if (!hasImageFieldOfName(ImageFieldName.choiceSecondaryImage)) {
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
     * @param choices The list of choiceCells to be uploaded.
     */
    void addUniqueNamesToCells(List<ChoiceCell> choices) {
        HashMap<String, Integer> dictCounter = new HashMap<>();

        for (ChoiceCell cell : choices) {
            String cellName = cell.getText();
            Integer counter = dictCounter.get(cellName);

            if (counter != null) {
                dictCounter.put(cellName, ++counter);
                cell.setUniqueText(cell.getText() + " (" + counter + ")");
            } else {
                dictCounter.put(cellName, 1);
            }
        }
    }

    void addUniqueNamesBasedOnStrippedCells(List<ChoiceCell> strippedCells, List<ChoiceCell> unstrippedCells) {
        if (strippedCells == null || unstrippedCells == null || strippedCells.size() != unstrippedCells.size()) {
            return;
        }
        // Tracks how many of each cell primary text there are so that we can append numbers to make each unique as necessary
        HashMap<ChoiceCell, Integer> dictCounter = new HashMap<>();
        for (int i = 0; i < strippedCells.size(); i++) {
            ChoiceCell cell = strippedCells.get(i);
            Integer counter = dictCounter.get(cell);
            if (counter != null) {
                counter++;
                dictCounter.put(cell, counter);
            } else {
                dictCounter.put(cell, 1);
            }

            counter = dictCounter.get(cell);

            if (counter > 1) {
                unstrippedCells.get(i).setUniqueText(unstrippedCells.get(i).getText() + " (" + counter + ")");
            }
        }
    }

    //Finding Cells
    private ChoiceCell cellFromChoiceId(int choiceId) {
        for (ChoiceCell cell : this.cellsToUpload) {
            if (cell.getChoiceId() == choiceId) {
                return cell;
            }
        }
        return null;
    }

    //Assembling Choice RPCs
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
        return templateSupportsTextField(TextFieldName.menuName);
    }

    boolean shouldSendChoiceSecondaryText() {
        return templateSupportsTextField(TextFieldName.secondaryText);
    }

    boolean shouldSendChoiceTertiaryText() {
        return templateSupportsTextField(TextFieldName.tertiaryText);
    }

    boolean shouldSendChoicePrimaryImage() {
        return templateSupportsImageField(ImageFieldName.choiceImage);
    }

    boolean shouldSendChoiceSecondaryImage() {
        return templateSupportsImageField(ImageFieldName.choiceSecondaryImage);
    }

    //SDL Notifications
    private void addListeners() {

        keyboardRPCListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                if (getState() == Task.CANCELED) {
                    finishOperation();
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

    boolean templateSupportsTextField(TextFieldName name) {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(defaultMainWindowCapability, name);
    }

    boolean templateSupportsImageField(ImageFieldName name) {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(defaultMainWindowCapability, name);
    }

    public void setLoadedCells(HashSet<ChoiceCell> loadedCells) {
        this.loadedCells = loadedCells;
    }

    public HashSet<ChoiceCell> getLoadedCells() {
        return this.loadedCells;
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

    void finishOperation() {
        this.currentState = SDLPreloadPresentChoicesOperationState.FINISHING;

        if (this.completionListener != null) {
            this.completionListener.onComplete(false, loadedCells);
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
                    PreloadPresentChoicesOperation.super.onFinished();
                }
            });

            if (internalInterface.get() != null) {
                internalInterface.get().sendRPC(setGlobalProperties);
                internalInterface.get().removeOnRPCNotificationListener(FunctionID.ON_KEYBOARD_INPUT, keyboardRPCListener);
            } else {
                DebugTool.logError(TAG, "Internal Interface null when finishing choice keyboard reset");
            }
        } else {
            PreloadPresentChoicesOperation.super.onFinished();
        }
    }
}
