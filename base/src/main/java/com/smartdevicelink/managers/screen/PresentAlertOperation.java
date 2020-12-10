/*
 * Copyright (c) 2020 Livio, Inc.
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

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.AlertCompletionListener;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.proxy.rpc.CancelInteraction;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Julian Kast on 12/10/20.
 */
public class PresentAlertOperation extends Task {
    private static final String TAG = "PresentAlertOperation";
    private AlertView alertView;
    private AlertCompletionListener listener;
    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    WindowCapability defaultMainWindowCapability;
    private int cancelId;
    private List<SpeechCapabilities> speechCapabilities;

    public PresentAlertOperation(ISdl internalInterface, AlertView alertView, WindowCapability currentCapabilities, List<SpeechCapabilities> speechCapabilities, FileManager fileManager, Integer cancelId, AlertCompletionListener listener) {
        super("PresentAlertOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.defaultMainWindowCapability = currentCapabilities;
        this.speechCapabilities = speechCapabilities;
        this.alertView = alertView.clone();
        this.listener = listener;
        this.cancelId = cancelId;

        this.alertView.canceledListener = new AlertCanceledListener() {
            @Override
            public void onAlertCanceled() {
                cancelAlert();
            }
        };
        alertView.canceledListener = this.alertView.canceledListener;
    }

    @Override
    public void onExecute() {
        DebugTool.logInfo(TAG, "Alert Operation: Executing present Alert operation");
        start();
    }

    private void start() {
        if (getState() == Task.CANCELED) {
            finishOperation(false, null);
            return;
        }
        if (!isValidAlertViewData(alertView)) {
            if (alertView.getAudio() != null && alertView.getAudio().getAudioFiles() != null && alertView.getAudio().getAudioFiles().size() > 0) {
                DebugTool.logError(TAG, "The module does not support the use of only audio file data in an alert. " +
                        "The alert has no data and can not be sent to the module. " +
                        "The use of audio file data in an alert is only supported on modules supporting RPC Spec v5.0 or newer");
            } else {
                DebugTool.logError(TAG, "The alert data is invalid." +
                        " At least either text, secondaryText or audio needs to be provided. " +
                        "Make sure to set at least the text, secondaryText or audio properties on the AlertView");
            }
            finishOperation(false, null);
            return;
        }
        final DispatchGroup uploadFilesTask = new DispatchGroup();

        uploadFilesTask.enter();
        uploadFilesTask.enter();

        uploadFilesTask.notify(new Runnable() {
            @Override
            public void run() {
                presentAlert();
            }
        });

        checkForImagesAndUpload(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                uploadFilesTask.leave();
            }
        });

        uploadAudioFiles(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                uploadFilesTask.leave();
            }
        });
    }


    /**
     * Uploads AudioFiles
     *
     * @param listener
     */
    private void uploadAudioFiles(final CompletionListener listener) {
        if (alertView.getAudio() == null) {
            DebugTool.logInfo(TAG, "No audio sent for alert");
            listener.onComplete(true);
            return;
        }
        if (!supportsAlertAudioFile()) {
            DebugTool.logError(TAG, "Module does not support audio files for alerts");
            listener.onComplete(true);
            return;
        }

        if (alertView.getAudio().getAudioFiles() == null || alertView.getAudio().getAudioFiles().size() == 0) {
            DebugTool.logInfo(TAG, "No audio files to upload for alert");
            listener.onComplete(true);
            return;
        }

        List<SdlFile> filesToBeUploaded = new ArrayList<>();
        for (SdlFile file : alertView.getAudio().getAudioFiles()) {
            if (!fileNeedsUpload(file)) {
                continue;
            }
            filesToBeUploaded.add(file);
        }

        if (filesToBeUploaded.size() == 0) {
            DebugTool.logInfo(TAG, "No audio files need to be uploaded for alert");
            listener.onComplete(true);
            return;
        }

        DebugTool.logInfo(TAG, "Uploading audio files for alert");

        if (fileManager.get() != null) {
            fileManager.get().uploadFiles(alertView.getAudio().getAudioFiles(), new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (getState() == Task.CANCELED) {
                        finishOperation(false, null);
                        return;
                    }
                    if (errors != null) {
                        DebugTool.logError(TAG, "Error uploading alert audio files:" + errors.toString());
                    } else {
                        DebugTool.logInfo(TAG, "All alert audio files uploaded");
                    }
                    listener.onComplete(true);
                }
            });
        }
    }

    /**
     * Check alertView  for an Icon and SoftButtonObject images and send them to upload method
     *
     * @param listener - CompletionListener used to signal that images in AlertView have uploaded if any
     */
    private void checkForImagesAndUpload(CompletionListener listener) {
        List<SdlArtwork> artworksToBeUploaded = new ArrayList<>();

        if (supportsAlertIcon() && artworkNeedsUploaded(alertView.getIcon())) {
            artworksToBeUploaded.add(alertView.getIcon());
        }

        if (alertView.getSoftButtons() != null) {
            for (SoftButtonObject object : alertView.getSoftButtons()) {
                if (supportsSoftButtonImages() && object.getCurrentState() != null && artworkNeedsUploaded(object.getCurrentState().getArtwork())) {
                    artworksToBeUploaded.add(object.getCurrentState().getArtwork());
                }
            }
        }

        uploadImages(artworksToBeUploaded, listener);
    }

    /**
     * Uploads images using fileManager
     *
     * @param images   - List of SdlArtwork - Images that need to be uploaded
     * @param listener - CompletionListener used to signal that images in AlertView have uploaded if any
     */
    private void uploadImages(List<SdlArtwork> images, final CompletionListener listener) {
        if (images.size() == 0) {
            DebugTool.logInfo(TAG, "No Images to upload for alert");
            listener.onComplete(true);
            return;
        }

        DebugTool.logInfo(TAG, "Uploading images for alert");

        if (fileManager.get() != null) {
            fileManager.get().uploadArtworks(images, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (getState() == Task.CANCELED) {
                        DebugTool.logInfo(TAG, "Operation canceled");
                        finishOperation(false, null);
                        return;
                    }

                    if (errors != null) {
                        DebugTool.logError(TAG, "AlertManager artwork failed to upload with error: " + errors.toString());
                        listener.onComplete(false);

                    } else {
                        DebugTool.logInfo(TAG, "All alert images uploaded");
                        listener.onComplete(true);
                    }
                }
            });
        }
    }

    /**
     * Sends the alert RPC to the module. The operation is finished once a response has been received from the module.
     */
    private void presentAlert() {
        Alert alert = createAlert();

        alert.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (getState() == Task.CANCELED) {
                    finishOperation(false, null);
                    return;
                }
                if (!response.getSuccess()) {
                    DebugTool.logError(TAG, response.getInfo());
                }
                finishOperation(response.getSuccess(), ((AlertResponse) response).getTryAgainTime());
            }
        });
        internalInterface.get().sendRPC(alert);
    }

    private void cancelAlert() {
        if (getState() == Task.FINISHED) {
            DebugTool.logInfo(TAG, "This operation has already finished so it can not be canceled");
            return;
        } else if (getState() == Task.CANCELED) {
            DebugTool.logInfo(TAG, "This operation has already been canceled. It will be finished at some point during the operation.");
            return;
        } else if (getState() == Task.IN_PROGRESS) {
            cancelInteraction();
        } else {
            DebugTool.logInfo(TAG, "Cancelling an alert that has not yet been sent to Core");
            this.cancelTask();
        }
    }

    void cancelInteraction() {
        if (internalInterface.get() != null && internalInterface.get().getSdlMsgVersion() != null && internalInterface.get().getSdlMsgVersion().getMajorVersion() < 6) {
            DebugTool.logError(TAG, "Canceling an alert is not supported on this module");
        }
        DebugTool.logInfo(TAG, "Canceling the presented alert");

        CancelInteraction cancelInteraction = new CancelInteraction(FunctionID.ALERT.getId(), cancelId);
        cancelInteraction.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (!response.getSuccess()) {
                    DebugTool.logInfo(TAG, "Error canceling the presented alert: " + response.getInfo());
                    onFinished();
                    return;
                }
                DebugTool.logInfo(TAG, "The presented alert was canceled successfully");
                onFinished();
            }
        });
        internalInterface.get().sendRPC(cancelInteraction);
    }

    // Private Getters / Setters

    Alert createAlert() {
        Alert alert = new Alert();
        alert = assembleAlertText(alert);
        alert.setDuration(alertView.getTimeout() * 1000);
        if (alertView.getIcon() != null && supportsAlertIcon()) {
            alert.setAlertIcon(alertView.getIcon().getImageRPC());
        }
        alert.setProgressIndicator(alertView.isShowWaitIndicator());
        alert.setCancelID(this.cancelId);
        if (alertView.getSoftButtons() != null) {
            List<SoftButton> softButtons = new ArrayList<>();
            for (SoftButtonObject button : alertView.getSoftButtons()) {
                softButtons.add(button.getCurrentStateSoftButton());
            }
            alert.setSoftButtons(softButtons);
        }

        if (alertView.getAudio() != null) {
            AlertAudioData alertAudioData = alertView.getAudio();
            alert.setPlayTone(alertAudioData.isPlayTone());
            List<TTSChunk> ttsChunks = getTTSChunksForAlert(alertAudioData);

            if (ttsChunks.size() > 0) {
                alert.setTtsChunks(ttsChunks);
            }
        }
        return alert;
    }

    List<TTSChunk> getTTSChunksForAlert(AlertAudioData alertAudioData) {
        List<TTSChunk> ttsChunks = new ArrayList<>();

        if (supportsAlertAudioFile() && alertAudioData.getAudioFiles() != null && alertAudioData.getAudioFiles().size() > 0) {
            for (int i = 0; i < alertAudioData.getAudioFiles().size(); i++) {
                ttsChunks.add(new TTSChunk(alertAudioData.getAudioFiles().get(i).getName(), SpeechCapabilities.FILE));
            }
        }

        if (alertAudioData.getPrompts() != null && alertAudioData.getPrompts().size() > 0) {
            ttsChunks.addAll(alertAudioData.getPrompts());
        }
        return ttsChunks;
    }

    // Text Helpers

    private Alert assembleAlertText(Alert alert) {
        List<String> nonNullFields = findValidMainTextFields();
        if (nonNullFields.isEmpty()) {
            return alert;
        }
        int numberOfLines = defaultMainWindowCapability == null ? 3 : ManagerUtility.WindowCapabilityUtility.getMaxNumberOfAlertFieldLines(defaultMainWindowCapability);
        switch (numberOfLines) {
            case 1:
                alert = assembleOneLineAlertText(alert, nonNullFields);
                break;
            case 2:
                alert = assembleTwoLineAlertText(alert);
                break;
            case 3:
                alert = assembleThreeLineAlertText(alert);
                break;
        }
        return alert;
    }

    private List<String> findValidMainTextFields() {
        List<String> array = new ArrayList<>();

        if (alertView.getText() != null && alertView.getText().length() > 0) {
            array.add(alertView.getText());
        }

        if (alertView.getSecondaryText() != null && alertView.getSecondaryText().length() > 0) {
            array.add(alertView.getSecondaryText());
        }

        if (alertView.getTertiaryText() != null && alertView.getTertiaryText().length() > 0) {
            array.add(alertView.getTertiaryText());
        }

        return array;
    }

    private Alert assembleOneLineAlertText(Alert alert, List<String> alertFields) {
        StringBuilder alertString1 = new StringBuilder();
        for (int i = 0; i < alertFields.size(); i++) {
            if (i > 0) {
                alertString1.append(" - ").append(alertFields.get(i));
            } else {
                alertString1.append(alertFields.get(i));
            }
        }
        alert.setAlertText1(alertString1.toString());
        return alert;
    }

    private Alert assembleTwoLineAlertText(Alert alert) {
        if (alertView.getText() != null && alertView.getText().length() > 0) {
            alert.setAlertText1(alertView.getText());
        }
        if (alertView.getSecondaryText() != null && alertView.getSecondaryText().length() > 0) {
            if ((alertView.getTertiaryText() == null || !(alertView.getTertiaryText().length() > 0))) {
                // TertiaryText does not exist
                alert.setAlertText2(alertView.getSecondaryText());
            } else {
                // Text 3 exists, put secondaryText and TertiaryText in AlertText2
                alert.setAlertText2(alertView.getSecondaryText() + " - " + alertView.getTertiaryText());
            }
        }
        return alert;
    }

    private Alert assembleThreeLineAlertText(Alert alert) {
        if (alertView.getText() != null && alertView.getText().length() > 0) {
            alert.setAlertText1(alertView.getText());
        }

        if (alertView.getSecondaryText() != null && alertView.getSecondaryText().length() > 0) {
            alert.setAlertText2(alertView.getSecondaryText());
        }

        if (alertView.getTertiaryText() != null && alertView.getTertiaryText().length() > 0) {
            alert.setAlertText3(alertView.getTertiaryText());
        }

        return alert;
    }

    // Helper methods

    /**
     * Checks if an SdlArtwork needs to be uploaded to the module. An artwork only needs to be uploaded if it is a
     * dynamic image and an artwork with the same name has not yet been uploaded to the module.
     * If the artwork has already been uploaded to the module but the `overwrite` property has been set to true,
     * then the artwork needs to be re-uploaded.
     *
     * @param artwork the SdlArtwork that needs to be checked
     * @return True if SdlArtwork needs to be uploaded; false if not.
     */
    private boolean artworkNeedsUploaded(SdlArtwork artwork) {
        if (artwork != null && !artwork.isStaticIcon()) {
            return artwork.getOverwrite() || !fileManager.get().hasUploadedFile(artwork);
        }
        return false;
    }

    /**
     * Checks if a file needs to be uploaded to the module. If the file has already been uploaded
     * to the module but the `overwrite` property has been set to true,
     * then the file needs to be re-uploaded.
     *
     * @param file the SdlFile that needs to be checked
     * @return True if SdlFile needs to be uploaded; false if not.
     */
    private boolean fileNeedsUpload(SdlFile file) {
        if (file != null) {
            return file.getOverwrite() || !fileManager.get().hasUploadedFile(file);
        }
        return false;
    }

    /**
     * Checks if the connected module or current template supports soft button images.
     *
     * @return True if soft button images are currently supported or windowCapability is null; false if not.
     */
    private boolean supportsSoftButtonImages() {
        SoftButtonCapabilities softButtonCapabilities = defaultMainWindowCapability.getSoftButtonCapabilities().get(0);
        return softButtonCapabilities.getImageSupported().booleanValue();
    }

    /**
     * Checks if the connected module supports audio files. Using an audio file in an alert will only work if connected to modules supporting RPC spec versions 5.0+.
     * If the module does not return a speechCapabilities, assume that the module supports playing an audio file.
     *
     * @return True if the module supports playing audio files in an alert; false if not.
     */
    private boolean supportsAlertAudioFile() {
        return (internalInterface.get() != null && internalInterface.get().getSdlMsgVersion() != null && internalInterface.get().getSdlMsgVersion().getMajorVersion() >= 5 && speechCapabilities != null && speechCapabilities.contains(SpeechCapabilities.FILE));
    }

    /**
     * Checks if the connected module or current template supports alert icons.
     *
     * @return True if alert icons are currently supported or if windowCapability is null; false if not.
     */
    private boolean supportsAlertIcon() {
        return ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(defaultMainWindowCapability, ImageFieldName.alertIcon);
    }

    private boolean isValidAlertViewData(AlertView alertView) {
        if (alertView.getText() != null && alertView.getText().length() > 0) {
            return true;
        }
        if (alertView.getSecondaryText() != null && alertView.getText().length() > 0) {
            return true;
        }
        if (alertView.getAudio() != null && getTTSChunksForAlert(alertView.getAudio()).size() > 0) {
            return true;
        }
        return false;
    }

    private void finishOperation(boolean success, Integer tryAgainTime) {
        DebugTool.logInfo(TAG, "Finishing present alert operation");
        if (listener != null) {
            listener.onComplete(success, tryAgainTime);
        }
        onFinished();
    }
}
