package com.smartdevicelink.managers.screen;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.AlertCompletionListener;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.AlertResponse;
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
        this.defaultMainWindowCapability = currentCapabilities;
        this.speechCapabilities = speechCapabilities;
        this.alertView = alertView;
        this.fileManager = new WeakReference<>(fileManager);
        this.listener = listener;
        this.alertView.canceledListener = new AlertCanceledListener() {
            @Override
            public void onAlertCanceled() {
                cancelAlert();
            }
        };
        this.cancelId = cancelId;
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
        checkForImagesAndUpload(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                uploadAudioFiles(new CompletionListener() {
                    @Override
                    public void onComplete(boolean success) {
                        presentAlert();
                    }
                });
            }
        });
    }


    private void uploadAudioFiles(final CompletionListener listener) {
        if (!supportsAlertAudioFile()) {
            DebugTool.logInfo(TAG, "Module does not support audio files for alerts");
            listener.onComplete(false);
            return;
        }

        if (alertView.getAudio().getAudioFiles().size() == 0) {
            DebugTool.logInfo(TAG, "No audio files to upload for alert");
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
                        DebugTool.logError(TAG, "AlertManager Audio files failed to upload with error: " + errors.toString());
                        listener.onComplete(false);
                    } else {
                        listener.onComplete(true);
                    }
                }
            });
        }
    }

    private void checkForImagesAndUpload(CompletionListener listener) {
        List<SdlArtwork> artworksToBeUploaded = new ArrayList<>();

        if (supportsAlertIcon() && artworkNeedsUploaded(alertView.getIcon())) {
            artworksToBeUploaded.add(alertView.getIcon());
        }

        for (SoftButtonObject object : alertView.getSoftButtons()) {
            if (supportsSoftButtonImages() && artworkNeedsUploaded(object.getCurrentState().getArtwork())) {
                artworksToBeUploaded.add(object.getCurrentState().getArtwork());
            }
        }
        uploadImages(artworksToBeUploaded, listener);
    }

    private void uploadImages(List<SdlArtwork> images, final CompletionListener listener) {
        if (images.size() == 0) {
            DebugTool.logInfo(TAG, " No Images to upload for alert");
            listener.onComplete(true);
            return;
        }
        DebugTool.logInfo(TAG, "Uploading images for alert");

        if (fileManager.get() != null) {
            fileManager.get().uploadArtworks(images, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (getState() == Task.CANCELED) {
                        finishOperation(false, null);
                        return;
                    }
                    if (errors != null) {
                        DebugTool.logError(TAG, "AlertManager artwork failed to upload with error: " + errors.toString());
                        listener.onComplete(false);
                    } else {
                        listener.onComplete(true);
                    }
                }
            });
        }
    }

    private void presentAlert() {
        if (getState() == Task.CANCELED) {
            finishOperation(false, null);
            return;
        }

        Alert alert = createAlert();

        alert.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                finishOperation(response.getSuccess(), ((AlertResponse) response).getTryAgainTime());
            }
        });
        internalInterface.get().sendRPC(alert);

    }

    // Private Getters / Setters

    private Alert createAlert() {
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
            List<TTSChunk> ttsChunks = new ArrayList<>();

            if (supportsAlertAudioFile() && alertAudioData.getAudioFiles() != null && alertAudioData.getAudioFiles().size() > 0) {
                for (int i = 0; i < alertAudioData.getAudioFiles().size(); i++) {
                    ttsChunks.add(new TTSChunk(alertAudioData.getAudioFiles().get(i).getName(), SpeechCapabilities.FILE));
                }
            }

            if (alertAudioData.getPrompts().size() > 0) {
                ttsChunks.addAll(alertAudioData.getPrompts());
            }
            alert.setTtsChunks(ttsChunks);
        }
        return alert;
    }

    private Alert assembleAlertText(Alert alert){
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

    private Alert assembleOneLineAlertText(Alert alert, List<String> alertFields){
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

    private Alert assembleTwoLineAlertText(Alert alert){
        if (alertView.getText() != null && alertView.getText().length() > 0) {
            alert.setAlertText1(alertView.getText());
        }
        if (alertView.getSecondaryText() != null && alertView.getSecondaryText().length() > 0) {
            if ((alertView.getTertiaryText() == null || !(alertView.getTertiaryText() .length() > 0))) {
                // TertiaryText does not exist
                alert.setAlertText2(alertView.getSecondaryText());
            } else {
                // Text 3 exists, put secondaryText and TertiaryText in AlertText2
                alert.setAlertText2(alertView.getSecondaryText() + " - " + alertView.getTertiaryText());
            }
        }
        return alert;
    }

    private Alert assembleThreeLineAlertText(Alert alert){
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

    private boolean artworkNeedsUploaded(SdlArtwork artwork) {
        if (fileManager.get() != null) {
            return artwork != null && !fileManager.get().hasUploadedFile(artwork) && !artwork.isStaticIcon();
        }
        return false;
    }

    private boolean supportsSoftButtonImages() {
        if (defaultMainWindowCapability != null) {
            SoftButtonCapabilities softButtonCapabilities = defaultMainWindowCapability.getSoftButtonCapabilities().get(0);
            return softButtonCapabilities.getImageSupported().booleanValue();
        }
        return true;

    }

    private boolean supportsAlertAudioFile() {
        return (internalInterface.get() != null && internalInterface.get().getSdlMsgVersion().getMajorVersion() >= 5 && speechCapabilities != null && speechCapabilities.contains(SpeechCapabilities.FILE));
    }

    private boolean supportsAlertIcon() {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(defaultMainWindowCapability, ImageFieldName.alertIcon);
    }

    private void cancelAlert() {

    }

    private void finishOperation(boolean success, Integer tryAgainTime) {
        if (listener != null) {
            listener.onComplete(success, tryAgainTime);
        }
    }
}
