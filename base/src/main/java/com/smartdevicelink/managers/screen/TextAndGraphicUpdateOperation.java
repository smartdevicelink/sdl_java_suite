package com.smartdevicelink.managers.screen;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.MetadataTags;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.CompareUtils;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextAndGraphicUpdateOperation extends Task {

    private static final String TAG = "TextAndGraphicUpdateOperation";
    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    WindowCapability defaultMainWindowCapability;
    private Show currentScreenData, sentShow;
    private TextsAndGraphicsState updatedState;
    private CompletionListener listener;
    private TextAndGraphicManager.CurrentScreenDataUpdatedListener currentScreenDataUpdateListener;


    /**
     * @param internalInterface
     * @param fileManager
     * @param currentCapabilities
     * @param currentScreenData
     * @param newState
     * @param listener
     */
    public TextAndGraphicUpdateOperation(ISdl internalInterface, FileManager fileManager, WindowCapability currentCapabilities,
                                         Show currentScreenData, TextsAndGraphicsState newState, CompletionListener listener, TextAndGraphicManager.CurrentScreenDataUpdatedListener currentScreenDataUpdateListener) {
        super("TextAndGraphicUpdateOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.defaultMainWindowCapability = currentCapabilities;
        this.currentScreenData = currentScreenData;
        this.updatedState = newState;
        this.listener = listener;
        this.currentScreenDataUpdateListener = currentScreenDataUpdateListener;
    }

    @Override
    public void onExecute() {
        start();
    }

    void start() {
        if (getState() == Task.CANCELED) {
            finishOperation(false);
            return;
        }

        // Build a show with everything from `self.newState`, we'll pull things out later if we can.
        Show fullShow = new Show();
        fullShow.setAlignment(updatedState.getTextAlignment());
        //TODO IOS ask about tages, Dont they just get set when we assembleShowText
        //  fullShow.setMetadataTags(updatedState.getM);
        fullShow = assembleShowText(fullShow);
        fullShow = assembleShowImages(fullShow);

        if (!shouldUpdatePrimaryImage() && !shouldUpdateSecondaryImage()) {
            DebugTool.logInfo(TAG, "No images to send, sending text");
            // If there are no images to update, just send the text
            sendShow(extractTextFromShow(fullShow), new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    finishOperation(success);
                }
            });

        } else if (!sdlArtworkNeedsUpload(updatedState.getPrimaryGraphic()) && !sdlArtworkNeedsUpload(updatedState.getSecondaryGraphic())) {
            DebugTool.logInfo(TAG, "Images already uploaded, sending full update");
            // The files to be updated are already uploaded, send the full show immediately
            sendShow(fullShow, new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    finishOperation(success);
                }
            });
        } else {
            DebugTool.logInfo(TAG, "Images need to be uploaded, sending text and uploading images");

            sendShow(extractTextFromShow(fullShow), new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    if (getState() == Task.CANCELED) {
                        finishOperation(false);
                        return;
                    }
                    uploadImagesAndSendWhenDone(new CompletionListener() {
                        @Override
                        public void onComplete(boolean success) {
                            finishOperation(success);
                        }
                    });

                }
            });
        }
    }

    void sendShow(final Show show, final CompletionListener listener) {
        show.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                DebugTool.logInfo(TAG, "Text and Graphic update completed");
                if (response.getSuccess()) {
                    updateCurrentScreenDataFromShow(show);
                }
                listener.onComplete(response.getSuccess());

            }
        });
        internalInterface.get().sendRPC(show);
    }


    void uploadImagesAndSendWhenDone(final CompletionListener listener) {
        uploadImages(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                Show showWithGraphics = createImageOnlyShowWithPrimaryArtwork(updatedState.getPrimaryGraphic(), updatedState.getSecondaryGraphic());
                //TODO Ask about logic here, Should we do if(success) then create and sendShow with Graphic or return false
                if (showWithGraphics != null) {
                    DebugTool.logInfo(TAG, "Sending update with the successfully uploaded images");
                    sendShow(showWithGraphics, new CompletionListener() {
                        @Override
                        public void onComplete(boolean success) {
                            listener.onComplete(success);
                        }
                    });
                } else {
                    DebugTool.logWarning(TAG, "All images failed to upload. No graphics to show, skipping update.");
                    listener.onComplete(success);
                }
            }
        });
    }

    private void uploadImages(final CompletionListener listener) {

        List<SdlArtwork> artworksToUpload = new ArrayList<>();

        // add primary image
        if (shouldUpdatePrimaryImage() && !updatedState.getPrimaryGraphic().isStaticIcon()) {
            artworksToUpload.add(updatedState.getPrimaryGraphic());
        }

        // add secondary image
        if (shouldUpdateSecondaryImage() && !updatedState.getSecondaryGraphic().isStaticIcon()) {
            artworksToUpload.add(updatedState.getSecondaryGraphic());
        }

        if (artworksToUpload.size() == 0) {
            DebugTool.logInfo(TAG, "No artworks need an upload, sending them without upload instead");
            listener.onComplete(true);
        }

        // use file manager to upload art
        if (fileManager.get() != null) {
            fileManager.get().uploadArtworks(artworksToUpload, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (getState() == Task.CANCELED) {
                        finishOperation(false);
                        return;
                    }
                    if (errors != null) {
                        DebugTool.logError(TAG, "Text and graphic manager artwork failed to upload with error: " + errors.toString());
                        listener.onComplete(false);
                    } else {
                        listener.onComplete(true);
                    }
                }
            });
        }
    }

    private Show assembleShowImages(Show show) {

        if (shouldUpdatePrimaryImage()) {
            show.setGraphic(updatedState.getPrimaryGraphic().getImageRPC());
        }

        if (shouldUpdateSecondaryImage()) {
            show.setSecondaryGraphic(updatedState.getSecondaryGraphic().getImageRPC());
        }

        return show;
    }

    Show createImageOnlyShowWithPrimaryArtwork(SdlArtwork primaryArtwork, SdlArtwork secondaryArtwork) {
        Show newShow = new Show();
        newShow.setGraphic((primaryArtwork != null && !(sdlArtworkNeedsUpload(primaryArtwork))) ? primaryArtwork.getImageRPC() : null);
        newShow.setSecondaryGraphic((secondaryArtwork != null && !(sdlArtworkNeedsUpload(secondaryArtwork))) ? secondaryArtwork.getImageRPC() : null);
        if (newShow.getGraphic() == null && newShow.getSecondaryGraphic() == null) {
            DebugTool.logInfo(TAG, "No graphics to upload");
            return null;
        }
        return newShow;
    }

    Show assembleShowText(Show show) {

        show = setBlankTextFields(show);

        if (updatedState.getMediaTrackTextField() != null && shouldUpdateMediaTrackField()) {
            show.setMediaTrack(updatedState.getMediaTrackTextField());
        }

        if (updatedState.getTitle() != null && shouldUpdateTitleField()) {
            show.setTemplateTitle(updatedState.getTitle());
        }

        List<String> nonNullFields = findValidMainTextFields();
        if (nonNullFields.isEmpty()) {
            return show;
        }

        int numberOfLines = defaultMainWindowCapability != null ? ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(defaultMainWindowCapability) : 4;

        switch (numberOfLines) {
            case 1:
                show = assembleOneLineShowText(show, nonNullFields);
                break;
            case 2:
                show = assembleTwoLineShowText(show);
                break;
            case 3:
                show = assembleThreeLineShowText(show);
                break;
            case 4:
                show = assembleFourLineShowText(show);
                break;
        }

        return show;
    }

    private Show assembleOneLineShowText(Show show, List<String> showFields) {

        StringBuilder showString1 = new StringBuilder();
        for (int i = 0; i < showFields.size(); i++) {
            if (i > 0) {
                showString1.append(" - ").append(showFields.get(i));
            } else {
                showString1.append(showFields.get(i));
            }
        }
        show.setMainField1(showString1.toString());

        MetadataTags tags = new MetadataTags();
        tags.setMainField1(findNonNullMetadataFields());

        show.setMetadataTags(tags);

        return show;
    }

    private Show assembleTwoLineShowText(Show show) {

        StringBuilder tempString = new StringBuilder();
        MetadataTags tags = new MetadataTags();

        if (updatedState.getTextField1() != null && updatedState.getTextField1().length() > 0) {
            tempString.append(updatedState.getTextField1());
            if (updatedState.getTextField1() != null) {
                tags.setMainField1(updatedState.getTextField1Type());
            }
        }

        if (updatedState.getTextField2() != null && updatedState.getTextField2().length() > 0) {
            if ((updatedState.getTextField3() == null || !(updatedState.getTextField3().length() > 0)) && (updatedState.getTextField4() == null || !(updatedState.getTextField4().length() > 0))) {
                // text does not exist in slots 3 or 4, put text2 in slot 2
                show.setMainField2(updatedState.getTextField2());
                if (updatedState.getTextField2Type() != null) {
                    tags.setMainField2(updatedState.getTextField2Type());
                }
            } else if (updatedState.getTextField1() != null && updatedState.getTextField1().length() > 0) {
                // If text 1 exists, put it in slot 1 formatted
                tempString.append(" - ").append(updatedState.getTextField2());
                if (updatedState.getTextField2Type() != null) {
                    List<MetadataType> typeList = new ArrayList<>();
                    typeList.add(updatedState.getTextField2Type());
                    if (updatedState.getTextField1Type() != null) {
                        typeList.add(updatedState.getTextField1Type());
                    }
                    tags.setMainField1(typeList);
                }
            } else {
                // If text 1 does not exist, put it in slot 1 unformatted
                tempString.append(updatedState.getTextField2());
                if (updatedState.getTextField2Type() != null) {
                    tags.setMainField1(updatedState.getTextField2Type());
                }
            }
        }

        // set mainfield 1
        show.setMainField1(tempString.toString());

        // new stringbuilder object
        tempString = new StringBuilder();

        if (updatedState.getTextField3() != null && updatedState.getTextField3().length() > 0) {
            // If text 3 exists, put it in slot 2
            tempString.append(updatedState.getTextField3());
            if (updatedState.getTextField3Type() != null) {
                List<MetadataType> typeList = new ArrayList<>();
                typeList.add(updatedState.getTextField3Type());
                tags.setMainField2(typeList);
            }
        }

        if (updatedState.getTextField4() != null && updatedState.getTextField4().length() > 0) {
            if (updatedState.getTextField3() != null && updatedState.getTextField3().length() > 0) {
                // If text 3 exists, put it in slot 2 formatted
                tempString.append(" - ").append(updatedState.getTextField4());
                if (updatedState.getTextField4Type() != null) {
                    List<MetadataType> typeList = new ArrayList<>();
                    typeList.add(updatedState.getTextField4Type());
                    if (updatedState.getTextField3Type() != null) {
                        typeList.add(updatedState.getTextField3Type());
                    }
                    tags.setMainField2(typeList);
                }
            } else {
                // If text 3 does not exist, put it in slot 3 unformatted
                tempString.append(updatedState.getTextField4());
                if (updatedState.getTextField4Type() != null) {
                    tags.setMainField2(updatedState.getTextField4Type());
                }
            }
        }

        if (tempString.toString().length() > 0) {
            show.setMainField2(tempString.toString());
        }

        show.setMetadataTags(tags);
        return show;
    }

    private Show assembleThreeLineShowText(Show show) {

        MetadataTags tags = new MetadataTags();

        if (updatedState.getTextField1() != null && updatedState.getTextField1().length() > 0) {
            show.setMainField1(updatedState.getTextField1());
            if (updatedState.getTextField1Type() != null) {
                tags.setMainField1(updatedState.getTextField1Type());
            }
        }

        if (updatedState.getTextField2() != null && updatedState.getTextField2().length() > 0) {
            show.setMainField2(updatedState.getTextField2());
            if (updatedState.getTextField2Type() != null) {
                tags.setMainField2(updatedState.getTextField2Type());
            }
        }

        StringBuilder tempString = new StringBuilder();

        if (updatedState.getTextField3() != null && updatedState.getTextField3().length() > 0) {
            tempString.append(updatedState.getTextField3());
            if (updatedState.getTextField3Type() != null) {
                tags.setMainField3(updatedState.getTextField3Type());
            }
        }

        if (updatedState.getTextField4() != null && updatedState.getTextField4().length() > 0) {
            if (updatedState.getTextField3() != null && updatedState.getTextField3().length() > 0) {
                // If text 3 exists, put it in slot 3 formatted
                tempString.append(" - ").append(updatedState.getTextField4());
                if (updatedState.getTextField4Type() != null) {
                    List<MetadataType> tags4 = new ArrayList<>();
                    if (updatedState.getTextField3Type() != null) {
                        tags4.add(updatedState.getTextField3Type());
                    }
                    tags4.add(updatedState.getTextField4Type());
                    tags.setMainField3(tags4);
                }
            } else {
                // If text 3 does not exist, put it in slot 3 formatted
                tempString.append(updatedState.getTextField4());
                if (updatedState.getTextField4Type() != null) {
                    tags.setMainField3(updatedState.getTextField4Type());
                }
            }
        }

        show.setMainField3(tempString.toString());
        show.setMetadataTags(tags);
        return show;
    }

    private Show assembleFourLineShowText(Show show) {

        MetadataTags tags = new MetadataTags();

        if (updatedState.getTextField1() != null && updatedState.getTextField1().length() > 0) {
            show.setMainField1(updatedState.getTextField1());
            if (updatedState.getTextField1Type() != null) {
                tags.setMainField1(updatedState.getTextField1Type());
            }
        }

        if (updatedState.getTextField2() != null && updatedState.getTextField2().length() > 0) {
            show.setMainField2(updatedState.getTextField2());
            if (updatedState.getTextField2Type() != null) {
                tags.setMainField2(updatedState.getTextField2Type());
            }
        }

        if (updatedState.getTextField3() != null && updatedState.getTextField3().length() > 0) {
            show.setMainField3(updatedState.getTextField3());
            if (updatedState.getTextField3Type() != null) {
                tags.setMainField3(updatedState.getTextField3Type());
            }
        }

        if (updatedState.getTextField4() != null && updatedState.getTextField4().length() > 0) {
            show.setMainField4(updatedState.getTextField4());
            if (updatedState.getTextField4Type() != null) {
                tags.setMainField4(updatedState.getTextField4Type());
            }
        }

        show.setMetadataTags(tags);
        return show;
    }

    // Extraction

    Show extractTextFromShow(Show show) {

        Show newShow = new Show();
        newShow.setMainField1(show.getMainField1());
        newShow.setMainField2(show.getMainField2());
        newShow.setMainField3(show.getMainField3());
        newShow.setMainField4(show.getMainField4());
        newShow.setTemplateTitle(show.getTemplateTitle());
        newShow.setMetadataTags(show.getMetadataTags());
        newShow.setAlignment(show.getAlignment());

        return newShow;
    }

    private Show setBlankTextFields(Show newShow) {

        newShow.setMainField1("");
        newShow.setMainField2("");
        newShow.setMainField3("");
        newShow.setMainField4("");
        newShow.setMediaTrack("");
        newShow.setTemplateTitle("");

        return newShow;
    }

    //TODO IOS different by maybe same
    private void updateCurrentScreenDataFromShow(Show show) {

        if (show == null) {
            DebugTool.logError(TAG, "can not updateCurrentScreenDataFromShow from null show");
            return;
        }

        // If the items are null, they were not updated, so we can't just set it directly
        if (show.getMainField1() != null) {
            currentScreenData.setMainField1(show.getMainField1());
        }
        if (show.getMainField2() != null) {
            currentScreenData.setMainField2(show.getMainField2());
        }
        if (show.getMainField3() != null) {
            currentScreenData.setMainField3(show.getMainField3());
        }
        if (show.getMainField4() != null) {
            currentScreenData.setMainField4(show.getMainField4());
        }
        if (show.getTemplateTitle() != null) {
            currentScreenData.setTemplateTitle(show.getTemplateTitle());
        }
        if (show.getMediaTrack() != null) {
            currentScreenData.setMediaTrack(show.getMediaTrack());
        }
        if (show.getMetadataTags() != null) {
            currentScreenData.setMetadataTags(show.getMetadataTags());
        }
        if (show.getAlignment() != null) {
            currentScreenData.setAlignment(show.getAlignment());
        }
        if (show.getGraphic() != null) {
            currentScreenData.setGraphic(show.getGraphic());
        }
        if (show.getSecondaryGraphic() != null) {
            currentScreenData.setSecondaryGraphic(show.getSecondaryGraphic());
        }
        if (currentScreenDataUpdateListener != null) {
            currentScreenDataUpdateListener.onUpdate(show);
        }
    }

    // Helpers

    private List<String> findValidMainTextFields() {
        List<String> array = new ArrayList<>();

        if (updatedState.getTextField1() != null && updatedState.getTextField1().length() > 0) {
            array.add(updatedState.getTextField1());
        }

        if (updatedState.getTextField2() != null && updatedState.getTextField2().length() > 0) {
            array.add(updatedState.getTextField2());
        }

        if (updatedState.getTextField3() != null && updatedState.getTextField3().length() > 0) {
            array.add(updatedState.getTextField3());
        }

        if (updatedState.getTextField4() != null && updatedState.getTextField4().length() > 0) {
            array.add(updatedState.getTextField4());
        }

        return array;
    }


    private List<MetadataType> findNonNullMetadataFields() {
        List<MetadataType> array = new ArrayList<>();

        if (updatedState.getTextField1Type() != null) {
            array.add(updatedState.getTextField1Type());
        }

        if (updatedState.getTextField2Type() != null) {
            array.add(updatedState.getTextField2Type());
        }

        if (updatedState.getTextField3Type() != null) {
            array.add(updatedState.getTextField3Type());
        }

        if (updatedState.getTextField4Type() != null) {
            array.add(updatedState.getTextField4Type());
        }

        return array;
    }

    // abstract SdlArtwork getBlankArtwork();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean sdlArtworkNeedsUpload(SdlArtwork artwork) {
        if (fileManager.get() != null) {
            return artwork != null && !fileManager.get().hasUploadedFile(artwork) && !artwork.isStaticIcon();
        }
        return false;
    }

    /**
     * Check to see if primaryGraphic should be updated
     *
     * @return true if primaryGraphic should be updated, false if not
     */
    private boolean shouldUpdatePrimaryImage() {
        boolean templateSupportsPrimaryArtwork = templateSupportsImageField(ImageFieldName.graphic);

        String currentScreenDataPrimaryGraphicName = (currentScreenData != null && currentScreenData.getGraphic() != null) ? currentScreenData.getGraphic().getValue() : null;
        String primaryGraphicName = updatedState.getPrimaryGraphic() != null ? updatedState.getPrimaryGraphic().getName() : null;
        return templateSupportsPrimaryArtwork
                && !CompareUtils.areStringsEqual(currentScreenDataPrimaryGraphicName, primaryGraphicName, true, true)
                && updatedState.getPrimaryGraphic() != null;
    }

    /**
     * Check to see if secondaryGraphic should be updated
     *
     * @return true if secondaryGraphic should be updated, false if not
     */
    private boolean shouldUpdateSecondaryImage() {
        boolean templateSupportsSecondaryArtwork = (templateSupportsImageField(ImageFieldName.graphic) || templateSupportsImageField(ImageFieldName.secondaryGraphic));

        String currentScreenDataSecondaryGraphicName = (currentScreenData != null && currentScreenData.getSecondaryGraphic() != null) ? currentScreenData.getSecondaryGraphic().getValue() : null;
        String secondaryGraphicName = updatedState.getSecondaryGraphic() != null ? updatedState.getSecondaryGraphic().getName() : null;
        return templateSupportsSecondaryArtwork
                && !CompareUtils.areStringsEqual(currentScreenDataSecondaryGraphicName, secondaryGraphicName, true, true)
                && updatedState.getSecondaryGraphic() != null;
    }

    /**
     * Check to see if template supports the specified image field
     *
     * @return true if image field is supported, false if not
     */
    private boolean templateSupportsImageField(ImageFieldName name) {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(defaultMainWindowCapability, name);
    }

    /**
     * Check to see if mediaTrackTextField should be updated
     *
     * @return true if mediaTrackTextField should be updated, false if not
     */
    private boolean shouldUpdateMediaTrackField() {
        return templateSupportsTextField(TextFieldName.mediaTrack);
    }

    /**
     * Check to see if title should be updated
     *
     * @return true if title should be updated, false if not
     */
    private boolean shouldUpdateTitleField() {
        return templateSupportsTextField(TextFieldName.templateTitle);
    }

    /**
     * Check to see if field should be updated
     *
     * @return true if field should be updated, false if not
     */
    private boolean templateSupportsTextField(TextFieldName name) {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(defaultMainWindowCapability, name);
    }

    public Show getSentShow() {
        return sentShow;
    }

    public void setSentShow(Show sentShow) {
        this.sentShow = sentShow;
    }

    public Show getCurrentScreenData() {
        return currentScreenData;
    }

    public void setCurrentScreenData(Show currentScreenData) {
        this.currentScreenData = currentScreenData;
    }

    void finishOperation(boolean success) {
        DebugTool.logInfo(TAG, "Finishing text and graphic update operation");
        listener.onComplete(success);
        onFinished();
    }
}
