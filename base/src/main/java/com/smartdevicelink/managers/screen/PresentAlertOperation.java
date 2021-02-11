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
 * Operation that handles uploading images and audio data needed by the alert and, once the data uploads are complete, sending the alert.
 * 
 * Created by Julian Kast on 12/10/20.
 */
public class PresentAlertOperation extends Task {
    private static final String TAG = "PresentAlertOperation";
    private AlertView alertView;
    private AlertCompletionListener listener;
    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    WindowCapability currentWindowCapability;
    private int cancelId;
    private List<SpeechCapabilities> speechCapabilities;
    static int SOFTBUTTON_COUNT = 4;

    public PresentAlertOperation(ISdl internalInterface, AlertView alertView, WindowCapability currentCapabilities, List<SpeechCapabilities> speechCapabilities, FileManager fileManager, Integer cancelId, AlertCompletionListener listener) {
        super("PresentAlertOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.currentWindowCapability = currentCapabilities;
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

        // Enter DispatchGroup twice for two tasks needing to be completed, One for uploading images and one for uploading audio files
        uploadFilesTask.enter();
        uploadFilesTask.enter();

        // DispatchGroup notify when all tasks are done
        uploadFilesTask.notify(new Runnable() {
            @Override
            public void run() {
                presentAlert();
            }
        });

        // DispatchGroup Task 1: uploading images
        uploadImages(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                uploadFilesTask.leave();
            }
        });

        // DispatchGroup Task 2: uploading audio files
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
        if (!supportsAlertAudioFile()) {
            DebugTool.logInfo(TAG, "Module does not support audio files for alerts, skipping upload of audio files");
            listener.onComplete(true);
            return;
        }

        if (alertView.getAudio() == null || alertView.getAudio().getAudioData() == null || alertView.getAudio().getAudioData().size() == 0) {
            DebugTool.logInfo(TAG, "No audio files need to be uploaded for alert");
            listener.onComplete(true);
            return;
        }

        List<SdlFile> filesToBeUploaded = new ArrayList<>();
        for (TTSChunk ttsChunk : alertView.getAudio().getAudioData()) {
            if(ttsChunk.getType() != SpeechCapabilities.FILE){
                continue;
            }
            SdlFile audioFile = alertView.getAudio().getAudioFiles().get(ttsChunk.getText());
            if (fileManager.get() == null || !fileManager.get().fileNeedsUpload(audioFile)) {
                continue;
            }
            filesToBeUploaded.add(audioFile);
        }

        if (filesToBeUploaded.size() == 0) {
            DebugTool.logInfo(TAG, "No audio files need to be uploaded for alert");
            listener.onComplete(true);
            return;
        }

        DebugTool.logInfo(TAG, "Uploading audio files for alert");

        if (fileManager.get() != null) {
            fileManager.get().uploadFiles(filesToBeUploaded, new MultipleFileCompletionListener() {
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
     * Upload the alert icon and soft button images.
     *
     * @param listener - CompletionListener called when all images have been uploaded.
     */
    private void uploadImages(final CompletionListener listener) {
        List<SdlArtwork> artworksToBeUploaded = new ArrayList<>();

        if (supportsAlertIcon() && fileManager.get() != null && fileManager.get().fileNeedsUpload(alertView.getIcon())) {
            artworksToBeUploaded.add(alertView.getIcon());
        }

        if (alertView.getSoftButtons() != null) {
            for (int i = 0; i < getSoftButtonCount(); i++) {
                SoftButtonObject object = alertView.getSoftButtons().get(i);
                if (supportsSoftButtonImages() && object.getCurrentState() != null && fileManager.get() != null && fileManager.get().fileNeedsUpload(object.getCurrentState().getArtwork())) {
                    artworksToBeUploaded.add(object.getCurrentState().getArtwork());
                }
            }
        }

        if (artworksToBeUploaded.size() == 0) {
            DebugTool.logInfo(TAG, "No Images to upload for alert");
            listener.onComplete(true);
            return;
        }

        DebugTool.logInfo(TAG, "Uploading images for alert");

        if (fileManager.get() != null) {
            fileManager.get().uploadArtworks(artworksToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (getState() == Task.CANCELED) {
                        DebugTool.logInfo(TAG, "Operation canceled");
                        finishOperation(false, null);
                        return;
                    }

                    if (errors != null) {
                        DebugTool.logError(TAG, "AlertManager artwork failed to upload with error: " + errors.toString());
                    } else {
                        DebugTool.logInfo(TAG, "All alert images uploaded");
                    }
                    listener.onComplete(true);
                }
            });
        }
    }

    /**
     * Sends the alert RPC to the module. The operation is finished once a response has been received from the module.
     */
    private void presentAlert() {
        Alert alert = alertRpc();

        alert.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (!response.getSuccess()) {
                    DebugTool.logError(TAG, "There was an error presenting the alert: " + response.getInfo());
                }  else {
                    DebugTool.logInfo(TAG, "Alert finished presenting");
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
            if (internalInterface.get() != null && internalInterface.get().getSdlMsgVersion() != null && internalInterface.get().getSdlMsgVersion().getMajorVersion() < 6) {
                DebugTool.logError(TAG, "Canceling an alert is not supported on this module");
                this.cancelTask();
                return;
            }
            cancelInteraction();
        } else {
            DebugTool.logInfo(TAG, "Cancelling an alert that has not yet been sent to Core");
            this.cancelTask();
        }
    }

    void cancelInteraction() {
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

    Alert alertRpc() {
        Alert alert = new Alert();
        alert = assembleAlertText(alert);
        alert.setDuration(alertView.getTimeout() * 1000);

        if (alertView.getIcon() != null && supportsAlertIcon() && !(fileManager.get().hasUploadedFile(alertView.getIcon()))) {
            alert.setAlertIcon(alertView.getIcon().getImageRPC());
        }

        alert.setProgressIndicator(alertView.isShowWaitIndicator());
        alert.setCancelID(this.cancelId);

        if (alertView.getSoftButtons() != null) {
            List<SoftButton> softButtons = new ArrayList<>();
            for (int i = 0; i < getSoftButtonCount(); i++) {
                softButtons.add(alertView.getSoftButtons().get(i).getCurrentStateSoftButton());
            }
            alert.setSoftButtons(softButtons);
        }

        if (alertView.getAudio() != null) {
            alert.setPlayTone(alertView.getAudio().isPlayTone());
            alert.setTtsChunks(getTTSChunksForAlert(alertView));
        }
        return alert;
    }

    /**
     * Checks if AudioFiles are supported by module and removes them form audioData list if they are not
     * @param alertView
     * @return List of ttsChunks
     */
    List<TTSChunk> getTTSChunksForAlert(AlertView alertView) {
        AlertAudioData alertAudioData = alertView.getAudio();
        List<TTSChunk> ttsChunks = new ArrayList<>();
            for (TTSChunk chunk : alertAudioData.getAudioData()) {
                if (chunk.getType() == SpeechCapabilities.FILE && !supportsAlertAudioFile()) {
                    continue;
                }
                ttsChunks.add(chunk);
            }
        return ttsChunks.size() > 0 ? ttsChunks : null;
    }
    // Text Helpers

    private Alert assembleAlertText(Alert alert) {
        List<String> nonNullFields = findNonNullTextFields();
        if (nonNullFields.isEmpty()) {
            return alert;
        }
        int numberOfLines = currentWindowCapability!= null ? ManagerUtility.WindowCapabilityUtility.getMaxNumberOfAlertFieldLines(currentWindowCapability) : 3;
        switch (numberOfLines) {
            case 1:
                alert = assembleOneLineAlertText(alert, nonNullFields);
                break;
            case 2:
                alert = assembleTwoLineAlertText(alert, nonNullFields);
                break;
            case 3:
                alert = assembleThreeLineAlertText(alert, nonNullFields);
                break;
        }
        return alert;
    }

    private List<String> findNonNullTextFields() {
        List<String> list = new ArrayList<>();

        if (alertView.getText() != null && alertView.getText().length() > 0) {
            list.add(alertView.getText());
        }

        if (alertView.getSecondaryText() != null && alertView.getSecondaryText().length() > 0) {
            list.add(alertView.getSecondaryText());
        }

        if (alertView.getTertiaryText() != null && alertView.getTertiaryText().length() > 0) {
            list.add(alertView.getTertiaryText());
        }

        return list;
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

    private Alert assembleTwoLineAlertText(Alert alert, List<String> alertFields) {
        if (alertFields.size() <= 2) {
            alert.setAlertText1(alertFields.size() > 0 ? alertFields.get(0) : null);
            alert.setAlertText2(alertFields.size() > 1 ? alertFields.get(1) : null);
        } else {
            alert.setAlertText1(alertFields.size() > 0 ? alertFields.get(0) : null);
            alert.setAlertText2(alertFields.get(1) + " - " + alertFields.get(2));
        }
        return alert;
    }

    private Alert assembleThreeLineAlertText(Alert alert, List<String> alertFields) {
        alert.setAlertText1(alertFields.size() > 0 ? alertFields.get(0) : null);
        alert.setAlertText2(alertFields.size() > 1 ? alertFields.get(1) : null);
        alert.setAlertText3(alertFields.size() > 2 ? alertFields.get(2) : null);
        return alert;
    }

    // Helper methods
    /**
     * Limits the number of SoftButtons that can be set in the AlertRPC to 4
     *
     * @return The maximum number of soft buttons that can be sent to the module
     */
    private int getSoftButtonCount() {
        return alertView.getSoftButtons().size() <= 4 ? alertView.getSoftButtons().size() : SOFTBUTTON_COUNT;
    }

    /**
     * Checks if the connected module or current template supports soft button images.
     *
     * @return True if soft button images are currently supported; false if not.
     */
    private boolean supportsSoftButtonImages() {
        SoftButtonCapabilities softButtonCapabilities = currentWindowCapability.getSoftButtonCapabilities().get(0);
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
     * @return True if alert icons are currently supported; false if not.
     */
    private boolean supportsAlertIcon() {
        return ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(currentWindowCapability, ImageFieldName.alertIcon);
    }

    /**
     * Checks the `AlertView` data to make sure it conforms to the RPC Spec, which says that at least either `alertText1`, `alertText2` or `TTSChunks` need to be provided.
     * @param alertView - Alert data that needs to be presented
     * @return true if AlertView data conforms to RPC Spec
     */
    private boolean isValidAlertViewData(AlertView alertView) {
        if (alertView.getText() != null && alertView.getText().length() > 0) {
            return true;
        }
        if (alertView.getSecondaryText() != null && alertView.getSecondaryText().length() > 0) {
            return true;
        }
        if (alertView.getAudio() != null && alertView.getAudio().getAudioData().size() > 0) {
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

    // Updates WindowCapability if the operation is pending the in the Alert Manager.
    void setWindowCapability(WindowCapability currentWindowCapability) {
        this.currentWindowCapability = currentWindowCapability;
    }
}
