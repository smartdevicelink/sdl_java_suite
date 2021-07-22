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

    private enum OperationType{
        PRELOAD,
        PRESENT
    }

    private final OperationType opType;
    private static final String TAG = "PreloadPresentChoicesOperation";
    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    private final WindowCapability defaultMainWindowCapability;
    private final String displayName;
    private final ArrayList<ChoiceCell> cellsToUpload;
    private final PreloadChoicesCompletionListener completionListener;
    private boolean isRunning;
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
    private final ChoiceSetSelectionListener choiceSetSelectionListener;
    Integer selectedCellRow;
    KeyboardListener keyboardListener;
    final SdlMsgVersion sdlMsgVersion;

    public PreloadPresentChoicesOperation(ISdl internalInterface, FileManager fileManager, String displayName, WindowCapability defaultWindowCapability,
                                          Boolean isVROptional, LinkedHashSet<ChoiceCell> cellsToPreload, PreloadChoicesCompletionListener listener, HashSet<ChoiceCell> loadedCells) {
        super("PreloadPresentChoiceOperation");
        this.opType = OperationType.PRELOAD;
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
        this.choiceSetSelectionListener = null;
        this.sdlMsgVersion = internalInterface.getSdlMsgVersion();
        this.loadedCells = loadedCells;
    }

    public PreloadPresentChoicesOperation(ISdl internalInterface, ChoiceSet choiceSet, InteractionMode mode,
                                          KeyboardProperties originalKeyboardProperties, KeyboardListener keyboardListener, ChoiceSetSelectionListener choiceSetSelectionListener, Integer cancelID, HashSet<ChoiceCell> loadedCells) {
        super("PreloadPresentChoiceOperation");
        this.opType = OperationType.PRESENT;
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
        this.choiceSetSelectionListener = choiceSetSelectionListener;
        this.sdlMsgVersion = internalInterface.getSdlMsgVersion();
        this.fileManager = null;
        this.displayName = null;
        this.defaultMainWindowCapability = null;
        this.isVROptional = true;
        this.cellsToUpload = null;
        this.completionListener = null;
        this.loadedCells = loadedCells;
    }

    @Override
    public void onExecute() {
        if (this.opType == OperationType.PRELOAD) {
            DebugTool.logInfo(TAG, "Choice Operation: Executing preload choices operation");
            updateCellsBasedOnLoadedChoices();
            preloadCellArtworks(new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    preloadCells();
                }
            });
        } else if(this.opType == OperationType.PRESENT) {
            DebugTool.logInfo(TAG, "Choice Operation: Executing present choice set operation");
            addListeners();
            start();
        }
    }

    //PRELOAD OPERATION METHODS
    private void preloadCellArtworks(@NonNull final CompletionListener listener) {
        isRunning = true;

        List<SdlArtwork> artworksToUpload = artworksToUpload();

        if (artworksToUpload.size() == 0) {
            DebugTool.logInfo(TAG, "Choice Preload: No Choice Artworks to upload");
            listener.onComplete(true);
            isRunning = false;
            return;
        }

        if (fileManager.get() != null) {
            fileManager.get().uploadArtworks(artworksToUpload, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null && errors.size() > 0) {
                        DebugTool.logError(TAG, "Error uploading choice cell Artworks: " + errors.toString());
                        listener.onComplete(false);
                        isRunning = false;
                    } else {
                        DebugTool.logInfo(TAG, "Choice Artworks Uploaded");
                        listener.onComplete(true);
                        isRunning = false;
                    }
                }
            });
        } else {
            DebugTool.logError(TAG, "File manager is null in choice preload operation");
            listener.onComplete(false);
            isRunning = false;
        }
    }

    private void preloadCells() {
        isRunning = true;
        List<CreateInteractionChoiceSet> choiceRPCs = new ArrayList<>(cellsToUpload.size());
        for (ChoiceCell cell : cellsToUpload) {
            CreateInteractionChoiceSet csCell = choiceFromCell(cell);
            if (csCell != null) {
                choiceRPCs.add(csCell);
            }
        }

        if (choiceRPCs.size() == 0) {
            DebugTool.logError(TAG, " All Choice cells to send are null, so the choice set will not be shown");
            completionListener.onComplete(true, loadedCells);
            isRunning = false;
            return;
        }

        if (internalInterface.get() != null) {
            internalInterface.get().sendRPCs(choiceRPCs, new OnMultipleRequestListener() {
                final HashSet<ChoiceCell> failedChoiceCells = new HashSet<>();

                @Override
                public void onUpdate(int remainingRequests) {

                }

                @Override
                public void onFinished() {
                    isRunning = false;
                    DebugTool.logInfo(TAG, "Finished pre loading choice cells");
                    completionListener.onComplete(!choiceError, loadedCells);
                    choiceError = false;
                    PreloadPresentChoicesOperation.super.onFinished();
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
            isRunning = false;
            completionListener.onComplete(false, loadedCells);
        }
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

    private ChoiceCell cellFromChoiceId(int choiceId) {
        for (ChoiceCell cell : this.cellsToUpload) {
            if (cell.getChoiceId() == choiceId) {
                return cell;
            }
        }
        return null;
    }

    //PRESENT OPERATION METHODS
    private void start() {
        if (getState() == Task.CANCELED) {
            finishOperation();
            return;
        }

        HashSet<ChoiceCell> choiceSetCells = new HashSet<>(this.choiceSet.getChoices());
        if (!choiceSetCells.containsAll(this.loadedCells)) {
            this.choiceSetSelectionListener.onError("Choices not available for presentation");
            finishOperation();
            return;
        }

        updateChoiceSetChoicesId();

        // Check if we're using a keyboard (searchable) choice set and setup keyboard properties if we need to
        if (keyboardListener != null && choiceSet.getCustomKeyboardConfiguration() != null) {
            keyboardProperties = choiceSet.getCustomKeyboardConfiguration();
            updatedKeyboardProperties = true;
        }

        updateKeyboardProperties(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (getState() == Task.CANCELED) {
                    finishOperation();
                    return;
                }
                presentChoiceSet();
            }
        });
    }

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

    void updateChoiceSetChoicesId() {
        for (ChoiceCell cell : this.choiceSet.getChoices()) {
            for (ChoiceCell loadedCell : this.loadedCells) {
                if (loadedCell.equals(cell)) {
                    cell.setChoiceId(loadedCell.getChoiceId());
                }
            }
        }
    }

    private void presentChoiceSet() {
        PerformInteraction pi = getPerformInteraction();
        pi.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (!response.getSuccess()) {
                    DebugTool.logError(TAG, "Presenting Choice set failed: " + response.getInfo());

                    if (choiceSetSelectionListener != null) {
                        choiceSetSelectionListener.onError(response.getInfo());
                    }
                    finishOperation();
                    return;
                }

                PerformInteractionResponse performInteractionResponse = (PerformInteractionResponse) response;
                setSelectedCellWithId(performInteractionResponse.getChoiceID());
                selectedTriggerSource = performInteractionResponse.getTriggerSource();

                if (choiceSetSelectionListener != null && selectedCell != null && selectedTriggerSource != null && selectedCellRow != null) {
                    choiceSetSelectionListener.onChoiceSelected(selectedCell, selectedTriggerSource, selectedCellRow);
                }

                finishOperation();
            }
        });
        if (internalInterface.get() != null) {
            internalInterface.get().sendRPC(pi);
        } else {
            DebugTool.logError(TAG, "Internal Interface null when presenting choice set in operation");
        }
    }

    PerformInteraction getPerformInteraction() {
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

    private void updateKeyboardProperties(final CompletionListener listener) {
        if (keyboardProperties == null) {
            if (listener != null) {
                listener.onComplete(false);
            }
            return;
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
            if (sdlMsgVersion.getMajorVersion() < 6) {
                DebugTool.logWarning(TAG, "Canceling a presented choice set is not supported on this head unit");
                return;
            }

            DebugTool.logInfo(TAG, "Canceling the presented choice set interaction.");

            CancelInteraction cancelInteraction = new CancelInteraction(FunctionID.PERFORM_INTERACTION.getId(), cancelID);
            cancelInteraction.setOnRPCResponseListener(new OnRPCResponseListener() {
                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    DebugTool.logInfo(TAG, "Canceled the presented choice set " + ((response.getResultCode() == Result.SUCCESS) ? "successfully" : "unsuccessfully"));
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

    void finishOperation() {
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
