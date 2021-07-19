/*
 * Copyright (c)  2019 Livio, Inc.
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
 * Created by brettywhite on 6/12/19 1:52 PM
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
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

class PreloadChoicesOperation extends Task {
    private static final String TAG = "PreloadChoicesOperation";
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

    PreloadChoicesOperation(ISdl internalInterface, FileManager fileManager, String displayName, WindowCapability defaultMainWindowCapability,
                            Boolean isVROptional, LinkedHashSet<ChoiceCell> cellsToPreload, PreloadChoicesCompletionListener listener, HashSet<ChoiceCell> loadedCells) {
        super("PreloadChoicesOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.displayName = displayName;
        this.defaultMainWindowCapability = defaultMainWindowCapability;
        this.isVROptional = isVROptional;
        this.cellsToUpload = new ArrayList<>(cellsToPreload);
        this.completionListener = listener;
        this.loadedCells = loadedCells;
    }

    @Override
    public void onExecute() {
        DebugTool.logInfo(TAG, "Choice Operation: Executing preload choices operation");
        updateCellsBasedOnLoadedChoices();
        preloadCellArtworks(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                preloadCells();
            }
        });
    }

    void removeChoicesFromUpload(HashSet<ChoiceCell> choices) {
        if (isRunning) {
            return;
        }
        cellsToUpload.removeAll(choices);
    }

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
                @Override
                public void onUpdate(int remainingRequests) {

                }

                @Override
                public void onFinished() {
                    isRunning = false;
                    DebugTool.logInfo(TAG, "Finished pre loading choice cells");
                    completionListener.onComplete(!choiceError, loadedCells);
                    choiceError = false;
                    PreloadChoicesOperation.super.onFinished();
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

    private ChoiceCell cellFromChoiceId(int choiceId) {
        for (ChoiceCell cell : this.cellsToUpload) {
            if (cell.getChoiceId() == choiceId) {
                return cell;
            }
        }
        return null;
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

    // HELPERS
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

}