package com.smartdevicelink.managers.screen;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.managers.screen.SoftButtonObject;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PresentAlertOperation extends Task {
    private static final String TAG = "PresentAlertOperation";
    private AlertView alertView;
    private CompletionListener listener;
    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    WindowCapability defaultMainWindowCapability;
    private int hasFinishedUploadTask;


    public PresentAlertOperation(ISdl internalInterface, AlertView alertView, WindowCapability currentCapabilities, FileManager fileManager, CompletionListener listener) {
        super("PresentAlertOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.defaultMainWindowCapability = currentCapabilities;
        this.alertView = alertView;
        this.fileManager = new WeakReference<>(fileManager);
        this.listener = listener;
        this.alertView.canceledListener = new AlertCanceledListener() {
            @Override
            public void onAlertCanceled() {
                cancelAlert();
            }
        };
        hasFinishedUploadTask = 0;
    }

    @Override
    public void onExecute() {
        DebugTool.logInfo(TAG, "Alert Operation: Executing present Alert operation");
        start();

    }

    private void start() {
        if (getState() == Task.CANCELED) {
            finishOperation(false);
            return;
        }
        uploadAudioFilesAndImages(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                presentAlert();
            }
        });

    }

    private void uploadAudioFilesAndImages(final CompletionListener listener) {
        checkForImagesAndUpload(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                hasFinishedUploadTask++;
                if (hasFinishedUploadTask == 2) {
                    listener.onComplete(success);
                }
            }
        });

        uploadAudioFiles(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                hasFinishedUploadTask++;
                if (hasFinishedUploadTask == 2) {
                    listener.onComplete(success);
                }
            }
        });
    }

    private void uploadAudioFiles(final CompletionListener listener) {
        if (!supportsAlertAudioFile()) {
            DebugTool.logInfo(TAG, "Module does not support audio files for alerts");
            listener.onComplete(false);
            return;
        }
        List<SdlFile> filesToBeUploaded = new ArrayList<>();

        for (AlertAudioData audioData : alertView.getAudio()) {
            if (audioData.getAudioFile() == null) {
                continue;
            }
            filesToBeUploaded.add(audioData.getAudioFile());
        }

        if (filesToBeUploaded.size() == 0) {
            DebugTool.logInfo(TAG, "No audio files to upload for alert");
            listener.onComplete(true);
            return;
        }

        DebugTool.logInfo(TAG, "Uploading audio files for alert");

        if (fileManager.get() != null) {
            fileManager.get().uploadFiles(filesToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (getState() == Task.CANCELED) {
                        finishOperation(false);
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
                        finishOperation(false);
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
            finishOperation(false);
            return;
        }

        Alert alert = createAlert();

        alert.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                finishOperation(response.getSuccess());
            }
        });
        internalInterface.get().sendRPC(alert);

    }

    // Private Getters / Setters

    private Alert createAlert() {
        Alert alert = new Alert();
        alert.setAlertText1(alertView.getText());
        alert.setAlertText2(alertView.getSecondaryText());
        alert.setAlertText3(alertView.getTertiaryText());
        alert.setDuration(alertView.getTimeout() * 1000);
        if(alertView.getIcon() != null){
            alert.setAlertIcon(alertView.getIcon().getImageRPC());
        }
        alert.setProgressIndicator(alertView.isShowWaitIndicator());
        //TODO FIGURE OUT WHAT TO DO HERE alert.setCancelID(alert.getCancelID());
        ManagerUtility.SoftButtonUtility.checkAndAssignButtonIds(alertView.getSoftButtons(), ManagerUtility.SoftButtonUtility.SoftButtonLocation.ALERT_MANAGER);

        List<SoftButton> softButtons = new ArrayList<>();
        for (SoftButtonObject button : alertView.getSoftButtons()) {
            softButtons.add(button.getCurrentStateSoftButton());
        }
        alert.setSoftButtons(softButtons);

        if(alertView.getAudio() != null){
            List<TTSChunk> ttsChunks = new ArrayList<>();
            boolean isPlayTone = false;
            for (AlertAudioData audioData : alertView.getAudio()) {
                if (audioData.isPlayTone()) {
                    isPlayTone = true;
                }
                if (audioData.getAudioFile() != null) {
                    //TODO
   /*             ttsChunks.add(new TTSChunk(audioData.getAudioFile().getName()), audioData.getAudioFile().)
                //ttsChunks.add(new TTSChunk(audioData.getAudioFile().getName()));*/
                }
                if (audioData.getPrompt() != null) {
                    ttsChunks.addAll(audioData.getPrompt());
                }
            }
            alert.setPlayTone(isPlayTone);
            alert.setTtsChunks(ttsChunks);
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
        if(defaultMainWindowCapability != null){
            SoftButtonCapabilities softButtonCapabilities = defaultMainWindowCapability.getSoftButtonCapabilities().get(0);
            return softButtonCapabilities.getImageSupported().booleanValue();
        }
        return true;

    }



    private boolean supportsAlertAudioFile() {
        return (internalInterface.get() != null && internalInterface.get().getSdlMsgVersion().getMajorVersion() >= 5);
    }

    private boolean supportsAlertIcon() {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(defaultMainWindowCapability, ImageFieldName.alertIcon);
    }

    private boolean supportsAlertTextField1() {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(defaultMainWindowCapability, TextFieldName.alertText1);
    }

    private boolean supportsAlertTextField2() {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(defaultMainWindowCapability, TextFieldName.alertText2);
    }

    private boolean supportsAlertTextField13() {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(defaultMainWindowCapability, TextFieldName.alertText3);
    }

    private void cancelAlert() {

    }

    private void finishOperation(boolean success) {
        if (listener != null) {
            listener.onComplete(success);
        }
    }
}
