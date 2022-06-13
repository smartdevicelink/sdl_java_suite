package com.smartdevicelink.managers.screen;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Bilal Alsharifi on 6/15/20.
 */
class SoftButtonReplaceOperation extends Task {
    private static final String TAG = "SoftButtonReplaceOperation";
    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    private final SoftButtonCapabilities softButtonCapabilities;
    private final CopyOnWriteArrayList<SoftButtonObject> softButtonObjects;
    private String currentMainField1;
    private Boolean isGraphicSupported;

    SoftButtonReplaceOperation(ISdl internalInterface, FileManager fileManager, SoftButtonCapabilities softButtonCapabilities, CopyOnWriteArrayList<SoftButtonObject> softButtonObjects, String currentMainField1, Boolean isGraphicSupported) {
        super("SoftButtonReplaceOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.softButtonCapabilities = softButtonCapabilities;
        this.softButtonObjects = softButtonObjects;
        this.currentMainField1 = currentMainField1;
        this.isGraphicSupported = isGraphicSupported;
    }

    @Override
    public void onExecute() {
        start();
    }

    private void start() {
        if (getState() == Task.CANCELED) {
            return;
        }

        // Check the state of our images
        if (!supportsSoftButtonImages()) {
            // We don't support images at all
            DebugTool.logWarning(TAG, "Soft button images are not supported. Attempting to send text-only soft buttons. If any button does not contain text, no buttons will be sent.");

            // Send text buttons if all the soft buttons have text
            sendCurrentStateTextOnlySoftButtons(new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    if (!success) {
                        DebugTool.logError(TAG, "Head unit does not support images and some of the soft buttons do not have text, so none of the buttons will be sent.");
                    }
                    onFinished();
                }
            });
        } else if (!supportsDynamicSoftButtonImages()) {
            DebugTool.logInfo(TAG, "Soft button images are not supported. Attempting to send text and static image only soft buttons. If any button does not contain text and/or a static image, no buttons will be sent.");
            sendCurrentStateStaticImageOnlySoftButtons(new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    if (!success) {
                        DebugTool.logError(TAG, "Buttons will not be sent because the module does not support dynamic images and some of the buttons do not have text or static images");
                    }
                    onFinished();
                }
            });

        } else if (currentStateHasImages() && !allCurrentStateImagesAreUploaded()) {
            // If there are images that aren't uploaded
            // Send text buttons if all the soft buttons have text
            sendCurrentStateTextOnlySoftButtons(null);

            // Upload initial images
            uploadInitialStateImages(new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    // Send initial soft buttons w/ images
                    sendCurrentStateSoftButtons(new CompletionListener() {
                        @Override
                        public void onComplete(boolean success) {
                            // Upload other images
                            uploadOtherStateImages(new CompletionListener() {
                                @Override
                                public void onComplete(boolean success) {
                                    DebugTool.logInfo(TAG, "Finished sending other images for soft buttons");
                                    onFinished();
                                }
                            });
                        }
                    });
                }
            });
        } else {
            // All the images are already uploaded. Send initial soft buttons w/ images.
            sendCurrentStateSoftButtons(new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    DebugTool.logInfo(TAG, "Finished sending soft buttons with images");
                    // Upload other images
                    uploadOtherStateImages(new CompletionListener() {
                        @Override
                        public void onComplete(boolean success) {
                            if (success) {
                                DebugTool.logInfo(TAG, "Finished sending other images for soft buttons");
                            }
                            onFinished();
                        }
                    });
                }
            });
        }
    }

    private void uploadInitialStateImages(final CompletionListener completionListener) {
        // Upload all soft button images, the initial state images first, then the other states. We need to send updates when the initial state is ready.
        List<SdlArtwork> initialStatesToBeUploaded = new ArrayList<>();
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            SoftButtonState softButtonState = softButtonObject.getCurrentState();
            if (softButtonState != null && fileManager.get() != null && fileManager.get().fileNeedsUpload(softButtonState.getArtwork()) && supportsSoftButtonImages()) {
                initialStatesToBeUploaded.add(softButtonState.getArtwork());
            }
        }

        if (initialStatesToBeUploaded.isEmpty()) {
            DebugTool.logInfo(TAG, "No initial state artworks to upload");
            if (completionListener != null) {
                completionListener.onComplete(false);
            }
            return;
        }
        if (!supportsDynamicSoftButtonImages()) {
            DebugTool.logInfo(TAG, "Head unit does not support dynamic images, skipping upload");
            if (completionListener != null) {
                completionListener.onComplete(false);
            }
            return;
        }

        DebugTool.logInfo(TAG, "Uploading soft button initial artworks");
        if (fileManager.get() != null) {
            fileManager.get().uploadArtworks(initialStatesToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null) {
                        DebugTool.logError(TAG, "Error uploading soft button artworks: " + errors.keySet());
                    } else {
                        DebugTool.logInfo(TAG, "Soft button initial state artworks uploaded");
                    }

                    if (getState() == Task.CANCELED) {
                        onFinished();
                        if (completionListener != null) {
                            completionListener.onComplete(false);
                        }
                        return;
                    }
                    if (completionListener != null) {
                        completionListener.onComplete(true);
                    }
                }
            });
        }
    }

    private void uploadOtherStateImages(final CompletionListener completionListener) {
        // Upload all soft button images, the initial state images first, then the other states. We need to send updates when the initial state is ready.
        List<SdlArtwork> otherStatesToBeUploaded = new ArrayList<>();
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            for (SoftButtonState softButtonState : softButtonObject.getStates()) {
                if (softButtonState.getName().equals(softButtonObject.getCurrentState().getName())) {
                    continue;
                }
                if (fileManager.get() != null && fileManager.get().fileNeedsUpload(softButtonState.getArtwork()) && supportsSoftButtonImages()) {
                    otherStatesToBeUploaded.add(softButtonState.getArtwork());
                }
            }
        }

        if (otherStatesToBeUploaded.isEmpty()) {
            DebugTool.logInfo(TAG, "No other state artworks to upload");
            if (completionListener != null) {
                completionListener.onComplete(false);
            }
            return;
        }

        DebugTool.logInfo(TAG, "Uploading soft button other state artworks");
        if (fileManager.get() != null) {
            fileManager.get().uploadArtworks(otherStatesToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null) {
                        DebugTool.logError(TAG, "Error uploading soft button artworks: " + errors.keySet());
                    } else {
                        DebugTool.logInfo(TAG, "Soft button other state artworks uploaded");
                    }

                    if (getState() == Task.CANCELED) {
                        onFinished();
                        if (completionListener != null) {
                            completionListener.onComplete(false);
                        }
                        return;
                    }

                    if (completionListener != null) {
                        completionListener.onComplete(true);
                    }
                }
            });
        }
    }

    private void sendCurrentStateSoftButtons(final CompletionListener completionListener) {
        if (getState() == Task.CANCELED) {
            onFinished();
        }

        DebugTool.logInfo(TAG, "Preparing to send full soft buttons");
        List<SoftButton> softButtons = new ArrayList<>();
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            softButtons.add(softButtonObject.getCurrentStateSoftButton());
        }

        Show show = new Show();
        show.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()) {
                    DebugTool.logInfo(TAG, "Finished sending text only soft buttons");
                } else {
                    DebugTool.logWarning(TAG, "Failed to update soft buttons with text buttons");
                }
                if (completionListener != null) {
                    completionListener.onComplete(response.getSuccess());
                }
            }
        });
        show.setMainField1(currentMainField1);
        show.setSoftButtons(softButtons);
        if (internalInterface.get() != null) {
            internalInterface.get().sendRPC(show);
        }
    }

    // Send soft buttons for the current state that only contain text and static images only, if possible.
    private void sendCurrentStateStaticImageOnlySoftButtons(final CompletionListener completionListener) {
        if (getState() == Task.CANCELED) {
            onFinished();
        }

        DebugTool.logInfo(TAG, "Preparing to send text and static image only soft buttons");
        List<SoftButton> textButtons = new ArrayList<>();
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            SoftButton softButton = softButtonObject.getCurrentStateSoftButton();
            if (softButton.getText() == null && softButton.getImage() != null && softButton.getImage().getImageType() == ImageType.DYNAMIC) {
                DebugTool.logWarning(TAG, "Attempted to create text and static image only buttons, but some buttons don't support text and have dynamic images, so no soft buttons will be sent.");
                if (completionListener != null) {
                    completionListener.onComplete(false);
                }
                return;
            }


            if (softButton.getImage() != null && softButton.getImage().getImageType() == ImageType.DYNAMIC) {
                // We should create a new softButtonObject rather than modifying the original one
                SoftButton textAndStaticImageOnlySoftButton = new SoftButton(SoftButtonType.SBT_TEXT, softButton.getSoftButtonID());
                textAndStaticImageOnlySoftButton.setText(softButton.getText());
                textAndStaticImageOnlySoftButton.setSystemAction(softButton.getSystemAction());
                textAndStaticImageOnlySoftButton.setIsHighlighted(softButton.getIsHighlighted());
                textAndStaticImageOnlySoftButton.setImage(softButton.getImage());
                textButtons.add(textAndStaticImageOnlySoftButton);
            } else {
                textButtons.add(softButton);
            }
        }

        Show show = new Show();
        show.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()) {
                    DebugTool.logInfo(TAG, "Finished sending text and static image only soft buttons");
                } else {
                    DebugTool.logWarning(TAG, "Failed to update soft buttons with text and static image only buttons");
                }
                if (completionListener != null) {
                    completionListener.onComplete(response.getSuccess());
                }
            }
        });
        show.setMainField1(currentMainField1);
        show.setSoftButtons(textButtons);
        if (internalInterface.get() != null) {
            internalInterface.get().sendRPC(show);
        }
    }


    // Returns text soft buttons representing the current states of the button objects, or returns if _any_ of the buttons' current states are image only buttons.
    private void sendCurrentStateTextOnlySoftButtons(final CompletionListener completionListener) {
        if (getState() == Task.CANCELED) {
            onFinished();
        }

        DebugTool.logInfo(TAG, "Preparing to send text-only soft buttons");
        List<SoftButton> textButtons = new ArrayList<>();
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            SoftButton softButton = softButtonObject.getCurrentStateSoftButton();
            if (softButton.getText() == null) {
                DebugTool.logWarning(TAG, "Attempted to create text buttons, but some buttons don't support text, so no text-only soft buttons will be sent");
                if (completionListener != null) {
                    completionListener.onComplete(false);
                }
                return;
            }

            // We should create a new softButtonObject rather than modifying the original one
            SoftButton textOnlySoftButton = new SoftButton(SoftButtonType.SBT_TEXT, softButton.getSoftButtonID());
            textOnlySoftButton.setText(softButton.getText());
            textOnlySoftButton.setSystemAction(softButton.getSystemAction());
            textOnlySoftButton.setIsHighlighted(softButton.getIsHighlighted());
            textButtons.add(textOnlySoftButton);
        }

        Show show = new Show();
        show.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()) {
                    DebugTool.logInfo(TAG, "Finished sending text only soft buttons");
                } else {
                    DebugTool.logWarning(TAG, "Failed to update soft buttons with text buttons");
                }
                if (completionListener != null) {
                    completionListener.onComplete(response.getSuccess());
                }
            }
        });
        show.setMainField1(currentMainField1);
        show.setSoftButtons(textButtons);
        if (internalInterface.get() != null) {
            internalInterface.get().sendRPC(show);
        }
    }

    private boolean currentStateHasImages() {
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            if (softButtonObject.getCurrentState().getArtwork() != null) {
                return true;
            }
        }
        return false;
    }

    private boolean allCurrentStateImagesAreUploaded() {
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            SdlArtwork artwork = softButtonObject.getCurrentState().getArtwork();
            if (fileManager.get() != null && fileManager.get().fileNeedsUpload(artwork) && supportsSoftButtonImages()) {
                return false;
            }
        }
        return true;
    }

    private boolean supportsDynamicSoftButtonImages() {
        return softButtonCapabilities != null && Boolean.TRUE.equals(isGraphicSupported) && Boolean.TRUE.equals(softButtonCapabilities.getImageSupported());
    }

    private boolean supportsSoftButtonImages() {
        return softButtonCapabilities != null && Boolean.TRUE.equals(softButtonCapabilities.getImageSupported());
    }

    void setCurrentMainField1(String currentMainField1) {
        this.currentMainField1 = currentMainField1;
    }
}
