package com.smartdevicelink.managers.screen;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.MetadataTags;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.TemplateConfiguration;
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

/**
 * Created by Julian Kast on 8/23/20.
 */
class TextAndGraphicUpdateOperation extends Task {

    private static final String TAG = "TextAndGraphicUpdateOperation";
    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    WindowCapability defaultMainWindowCapability;
    private TextsAndGraphicsState currentScreenData;
    private final TextsAndGraphicsState updatedState;
    private final TextAndGraphicManager.CurrentScreenDataUpdatedListener currentScreenDataUpdateListener;
    private final CompletionListener listener;
    private Show fullShow;

    TextAndGraphicUpdateOperation(ISdl internalInterface, FileManager fileManager, WindowCapability currentCapabilities,
                                       TextsAndGraphicsState currentScreenData, TextsAndGraphicsState newState, CompletionListener listener, TextAndGraphicManager.CurrentScreenDataUpdatedListener currentScreenDataUpdateListener) {
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

    private void start() {
        if (getState() == Task.CANCELED) {
            finishOperation(false);
            return;
        }

        fullShow = new Show();
        fullShow.setAlignment(updatedState.getTextAlignment());
        fullShow = assembleShowText(fullShow);
        fullShow = assembleShowImages(fullShow);
        fullShow = assembleLayout(fullShow);


        if (showRPCSupportsTemplateConfiguration()) {
            updateGraphicsAndShow(fullShow);
        } else {
            if (shouldUpdateTemplateConfig()) {
                sendSetDisplayLayoutWithTemplateConfiguration(updatedState.getTemplateConfiguration(), new CompletionListener() {
                    @Override
                    public void onComplete(boolean success) {
                        if (getState() == Task.CANCELED || !success) {
                            // Task was canceled or SetDisplayLayout was not a success
                            finishOperation(false);
                            return;
                        }
                        updateGraphicsAndShow(fullShow);
                    }
                });
            } else {
                // just send the show
                updateGraphicsAndShow(fullShow);
            }
        }
    }

    void updateGraphicsAndShow(Show show) {
        if (!shouldUpdatePrimaryImage() && !shouldUpdateSecondaryImage()) {
            DebugTool.logInfo(TAG, "No images to send, sending text");
            // If there are no images to update, just send the text
            sendShow(extractTextAndLayoutFromShow(show), new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    finishOperation(success);
                }
            });

        } else if (!sdlArtworkNeedsUpload(updatedState.getPrimaryGraphic()) && !sdlArtworkNeedsUpload(updatedState.getSecondaryGraphic())) {
            DebugTool.logInfo(TAG, "Images already uploaded, sending full update");
            // The files to be updated are already uploaded, send the full show immediately
            sendShow(show, new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    finishOperation(success);
                }
            });
        } else {
            DebugTool.logInfo(TAG, "Images need to be uploaded, sending text and uploading images");

            sendShow(extractTextAndLayoutFromShow(show), new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    if (getState() == Task.CANCELED || !success) {
                        // Task was canceled or update to text / layout was not a success
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

    private void sendShow(final Show show, final CompletionListener listener) {
        show.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()) {
                    DebugTool.logInfo(TAG, "Text and Graphic update completed");
                    updateCurrentScreenDataFromShow(show);
                } else {
                    DebugTool.logInfo(TAG, "Text and Graphic Show failed");
                    currentScreenDataUpdateListener.onError();
                }
                listener.onComplete(response.getSuccess());

            }
        });
        if(internalInterface.get() != null){
            internalInterface.get().sendRPC(show);
        } else {
            DebugTool.logInfo(TAG, "ISdl is null Text and Graphic update failed");
            currentScreenDataUpdateListener.onError();
            finishOperation(false);
            return;
        }

    }

    private void sendSetDisplayLayoutWithTemplateConfiguration(TemplateConfiguration configuration, final CompletionListener listener){
       final SetDisplayLayout setLayout = new SetDisplayLayout().setDisplayLayout(configuration.getTemplate()).setDayColorScheme(configuration.getDayColorScheme()).setNightColorScheme(configuration.getNightColorScheme());
        setLayout.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(response.getSuccess()){
                    updateCurrentScreenDataFromSetDisplayLayout(setLayout);
                }
                else {
                    DebugTool.logInfo(TAG, "Text and Graphic SetDisplayLayout failed");
                    currentScreenDataUpdateListener.onError();
                }
                listener.onComplete(response.getSuccess());
            }
        });
        if(internalInterface.get() != null){
            internalInterface.get().sendRPC(setLayout);
        } else {
            DebugTool.logInfo(TAG, "ISdl is null Text and Graphic update failed");
            currentScreenDataUpdateListener.onError();
            finishOperation(false);
            return;
        }
    }


    private void uploadImagesAndSendWhenDone(final CompletionListener listener) {
        uploadImages(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                Show showWithGraphics = createImageOnlyShowWithPrimaryArtwork(updatedState.getPrimaryGraphic(), updatedState.getSecondaryGraphic());
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
                    listener.onComplete(false);
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

        int numberOfLines = defaultMainWindowCapability == null || shouldUpdateTemplateConfig() ? 4 : ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(defaultMainWindowCapability);

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
            if (updatedState.getTextField1Type() != null) {
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

        // set mainField1
        show.setMainField1(tempString.toString());

        // new stringBuilder object
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

    Show extractTextAndLayoutFromShow(Show show) {
        Show newShow = new Show();
        newShow.setMainField1(show.getMainField1());
        newShow.setMainField2(show.getMainField2());
        newShow.setMainField3(show.getMainField3());
        newShow.setMainField4(show.getMainField4());
        newShow.setTemplateTitle(show.getTemplateTitle());
        newShow.setMetadataTags(show.getMetadataTags());
        newShow.setAlignment(show.getAlignment());

        if (showRPCSupportsTemplateConfiguration()) {
            newShow.setTemplateConfiguration(show.getTemplateConfiguration());
        }
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

    Show assembleLayout(Show show) {
        if (!showRPCSupportsTemplateConfiguration() || !shouldUpdateTemplateConfig()) {
            return show;
        }
        show.setTemplateConfiguration(updatedState.getTemplateConfiguration());
        return show;
    }

    private void updateCurrentScreenDataFromSetDisplayLayout(SetDisplayLayout setDisplayLayout) {
        currentScreenData.setTemplateConfiguration(new TemplateConfiguration().setTemplate(setDisplayLayout.getDisplayLayout()).setDayColorScheme(setDisplayLayout.getDayColorScheme()).setNightColorScheme(setDisplayLayout.getNightColorScheme()));
        if(currentScreenDataUpdateListener != null){
            currentScreenDataUpdateListener.onUpdate(currentScreenData);
        }
    }

    private void updateCurrentScreenDataFromShow(Show show) {
        if (show == null) {
            DebugTool.logError(TAG, "can not updateCurrentScreenDataFromShow from null show");
            return;
        }
        if (show.getMainField1() != null) {
            currentScreenData.setTextField1(updatedState.getTextField1());
        }
        if (show.getMainField2() != null) {
            currentScreenData.setTextField2(updatedState.getTextField2());
        }
        if (show.getMainField3() != null) {
            currentScreenData.setTextField3(updatedState.getTextField3());
        }
        if (show.getMainField4() != null) {
            currentScreenData.setTextField4(updatedState.getTextField4());
        }
        if (show.getTemplateTitle() != null) {
            currentScreenData.setTitle(updatedState.getTitle());
        }
        if (show.getMediaTrack() != null) {
            currentScreenData.setMediaTrackTextField(updatedState.getMediaTrackTextField());
        }

        if(show.getMetadataTags() != null){
            currentScreenData.setTextField1Type(updatedState.getTextField1Type());
            currentScreenData.setTextField2Type(updatedState.getTextField2Type());
            currentScreenData.setTextField3Type(updatedState.getTextField3Type());
            currentScreenData.setTextField4Type(updatedState.getTextField4Type());
        }
        if (show.getAlignment() != null) {
            currentScreenData.setTextAlignment(updatedState.getTextAlignment());
        }
        if (show.getGraphic() != null) {
            currentScreenData.setPrimaryGraphic(updatedState.getPrimaryGraphic());
        }
        if (show.getSecondaryGraphic() != null) {
            currentScreenData.setSecondaryGraphic(updatedState.getSecondaryGraphic());
        }
        if (currentScreenDataUpdateListener != null) {
            currentScreenDataUpdateListener.onUpdate(currentScreenData);
        }
    }

    // Helpers

    private boolean showRPCSupportsTemplateConfiguration() {
        if (internalInterface.get() == null || internalInterface.get().getSdlMsgVersion() == null) {
            return false;
        }
        return internalInterface.get().getSdlMsgVersion().getMajorVersion() >= 6;
    }

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
        boolean templateSupportsPrimaryArtwork = templateSupportsImageField(ImageFieldName.graphic) || shouldUpdateTemplateConfig();
        String currentScreenDataPrimaryGraphicName = (currentScreenData != null && currentScreenData.getPrimaryGraphic() != null) ? currentScreenData.getPrimaryGraphic().getName() : null;
        String primaryGraphicName = updatedState.getPrimaryGraphic() != null ? updatedState.getPrimaryGraphic().getName() : null;

        boolean graphicMatchesExisting = CompareUtils.areStringsEqual(currentScreenDataPrimaryGraphicName, primaryGraphicName, true, true);

        return templateSupportsPrimaryArtwork && !graphicMatchesExisting;
    }

    /**
     * Check to see if secondaryGraphic should be updated
     *
     * @return true if secondaryGraphic should be updated, false if not
     */
    private boolean shouldUpdateSecondaryImage() {
        boolean templateSupportsSecondaryArtwork = templateSupportsImageField(ImageFieldName.secondaryGraphic) || shouldUpdateTemplateConfig();
        String currentScreenDataSecondaryGraphicName = (currentScreenData != null && currentScreenData.getSecondaryGraphic() != null) ? currentScreenData.getSecondaryGraphic().getName() : null;
        String secondaryGraphicName = updatedState.getSecondaryGraphic() != null ? updatedState.getSecondaryGraphic().getName() : null;

        boolean graphicMatchesExisting = CompareUtils.areStringsEqual(currentScreenDataSecondaryGraphicName, secondaryGraphicName, true, true);

        // Cannot detect if there is a secondary image below v5.0, so we'll just try to detect if the primary image is allowed and allow the secondary image if it is.
        if (internalInterface.get() != null && internalInterface.get().getSdlMsgVersion().getMajorVersion() >= 5) {
            return templateSupportsSecondaryArtwork && !graphicMatchesExisting;
        } else {
            return templateSupportsImageField(ImageFieldName.graphic) && !graphicMatchesExisting;
        }
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
        return templateSupportsTextField(TextFieldName.mediaTrack) || shouldUpdateTemplateConfig();
    }

    /**
     * Check to see if title should be updated
     *
     * @return true if title should be updated, false if not
     */
    private boolean shouldUpdateTitleField() {
        return templateSupportsTextField(TextFieldName.templateTitle) || shouldUpdateTemplateConfig();
    }

    private Boolean shouldUpdateTemplateConfig() {
        if(updatedState.getTemplateConfiguration() == null) {
            return false;
        } else if(currentScreenData.getTemplateConfiguration() == null) {
            return true;
        }
        return !updatedState.getTemplateConfiguration().getStore().equals(currentScreenData.getTemplateConfiguration().getStore());
    }

    /**
     * Check to see if field should be updated
     *
     * @return true if field should be updated, false if not
     */
    private boolean templateSupportsTextField(TextFieldName name) {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(defaultMainWindowCapability, name);
    }

    TextsAndGraphicsState getCurrentScreenData() {
        return currentScreenData;
    }

    void setCurrentScreenData(TextsAndGraphicsState currentScreenData) {
        this.currentScreenData = currentScreenData;
    }

    private void finishOperation(boolean success) {
        DebugTool.logInfo(TAG, "Finishing text and graphic update operation");
        if(listener != null){
            listener.onComplete(success);
        }
        onFinished();
    }
}
