package com.smartdevicelink.managers.screen;

import android.content.Context;
import android.net.Uri;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.MetadataTags;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TextAndGraphicUpdateOperationTest {

    private TextAndGraphicUpdateOperation textAndGraphicUpdateOperation;
    private TextAndGraphicUpdateOperation textAndGraphicUpdateOperationNullCapability;
    private TextAndGraphicUpdateOperation textAndGraphicUpdateOperationEmptyCapability;
    private String textField1, textField2, textField3, textField4, mediaTrackField, title;
    private MetadataType textField1Type, textField2Type, textField3Type, textField4Type;
    private SdlArtwork testArtwork1, testArtwork2, testArtwork3, testArtwork4;
    private TextAlignment textAlignment;
    private WindowCapability defaultMainWindowCapability;
    private Show currentScreenData;
    private CompletionListener listener;
    ISdl internalInterface;
    FileManager fileManager;

    private Answer<Void> onShowSuccess = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) {
            Object[] args = invocation.getArguments();
            RPCRequest message = (RPCRequest) args[0];
            if (message instanceof Show) {
                int correlationId = message.getCorrelationID();
                ShowResponse showResponse = new ShowResponse();
                showResponse.setSuccess(true);
                message.getOnRPCResponseListener().onResponse(correlationId, showResponse);
            }
            return null;
        }
    };

    private Answer<Void> onShowSuccessCanceled = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) {
            Object[] args = invocation.getArguments();
            RPCRequest message = (RPCRequest) args[0];
            if (message instanceof Show) {
                int correlationId = message.getCorrelationID();
                textAndGraphicUpdateOperation.cancelTask();
                ShowResponse showResponse = new ShowResponse();
                showResponse.setSuccess(true);
                message.getOnRPCResponseListener().onResponse(correlationId, showResponse);
            }
            return null;
        }
    };


    private Answer<Void> onImageUploadSuccessTaskCanceled = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
            Object[] args = invocation.getArguments();
            MultipleFileCompletionListener listener = (MultipleFileCompletionListener) args[1];
            textAndGraphicUpdateOperation.cancelTask();
            listener.onComplete(null);
            return null;
        }
    };


    @Before
    public void setUp() throws Exception {
        Context mTestContext = getInstrumentation().getContext();
        // mock things
        internalInterface = mock(ISdl.class);
        fileManager = mock(FileManager.class);
        setUpCompletionListener();
        textField1 = "It is";
        textField2 = "Wednesday";
        textField3 = "My";
        textField4 = "Dudes";
        mediaTrackField = "dudes";
        title = "dudes";

        textField1Type = MetadataType.MEDIA_TITLE;
        textField2Type = MetadataType.MEDIA_TITLE;
        textField3Type = MetadataType.MEDIA_TITLE;
        textField4Type = MetadataType.MEDIA_TITLE;


        textAlignment = TextAlignment.CENTERED;

        testArtwork1 = new SdlArtwork();
        testArtwork1.setName("testFile1");
        Uri uri1 = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
        testArtwork1.setUri(uri1);
        testArtwork1.setType(FileType.GRAPHIC_PNG);

        testArtwork2 = new SdlArtwork();
        testArtwork2.setName("testFile2");
        Uri uri2 = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
        testArtwork2.setUri(uri2);
        testArtwork2.setType(FileType.GRAPHIC_PNG);

        testArtwork3 = new SdlArtwork();
        testArtwork3.setName("testFile3");
        Uri uri3 = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
        testArtwork3.setUri(uri3);
        testArtwork3.setType(FileType.GRAPHIC_PNG);

        testArtwork4 = new SdlArtwork();
        testArtwork4.setName("testFile4");
        Uri uri4 = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
        testArtwork4.setUri(uri4);
        testArtwork4.setType(FileType.GRAPHIC_PNG);

        currentScreenData = new Show();
        currentScreenData.setMainField1("Old");
        currentScreenData.setMainField2("Text");
        currentScreenData.setMainField3("Not");
        currentScreenData.setMainField4("Important");

        currentScreenData.setGraphic(testArtwork1.getImageRPC());
        currentScreenData.setSecondaryGraphic(testArtwork2.getImageRPC());

        defaultMainWindowCapability = getWindowCapability(4);

        TextsAndGraphicsState textsAndGraphicsState = new TextsAndGraphicsState(textField1, textField2, textField3, textField4,
                mediaTrackField, title, testArtwork3, testArtwork4, textAlignment, textField1Type, textField2Type, textField3Type, textField4Type);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);
    }


    private void setUpCompletionListener() {
        listener = new CompletionListener() {
            @Override
            public void onComplete(boolean success) {

            }
        };

    }

    private WindowCapability getWindowCapability(int numberOfMainFields) {

        TextField mainField1 = new TextField();
        mainField1.setName(TextFieldName.mainField1);
        TextField mainField2 = new TextField();
        mainField2.setName(TextFieldName.mainField2);
        TextField mainField3 = new TextField();
        mainField3.setName(TextFieldName.mainField3);
        TextField mainField4 = new TextField();
        mainField4.setName(TextFieldName.mainField4);

        List<TextField> textFieldList = new ArrayList<>();

        textFieldList.add(mainField1);
        textFieldList.add(mainField2);
        textFieldList.add(mainField3);
        textFieldList.add(mainField4);

        List<TextField> returnList = new ArrayList<>();

        if (numberOfMainFields > 0) {
            for (int i = 0; i < numberOfMainFields; i++) {
                returnList.add(textFieldList.get(i));
            }
        }

        WindowCapability windowCapability = new WindowCapability();
        windowCapability.setTextFields(returnList);

        ImageField imageField = new ImageField();
        imageField.setName(ImageFieldName.graphic);
        ImageField imageField2 = new ImageField();
        imageField2.setName(ImageFieldName.secondaryGraphic);
        List<ImageField> imageFieldList = new ArrayList<>();
        imageFieldList.add(imageField);
        imageFieldList.add(imageField2);
        windowCapability.setImageFields(imageFieldList);

        windowCapability.setImageFields(imageFieldList);

        return windowCapability;
    }

    /**
     * Used to simulate WindowCapability having no capabilities set
     *
     * @return windowCapability that has no capabilities set
     */
    private WindowCapability getNullVarWindowCapability() {

        WindowCapability windowCapability = new WindowCapability();
        return windowCapability;
    }

    @Test
    public void testUpload() {
        doAnswer(onShowSuccess).when(internalInterface).sendRPC(any(Show.class));

        // Test Images need to be uploaded, sending text and uploading images
        textAndGraphicUpdateOperation.onExecute();
        assertEquals(textAndGraphicUpdateOperation.getCurrentScreenData().getMainField1(), textField1);
        assertEquals(textAndGraphicUpdateOperation.getCurrentScreenData().getMainField2(), textField2);
        assertEquals(textAndGraphicUpdateOperation.getCurrentScreenData().getMainField3(), textField3);
        assertEquals(textAndGraphicUpdateOperation.getCurrentScreenData().getMainField4(), textField4);
        assertEquals(textAndGraphicUpdateOperation.getCurrentScreenData().getAlignment(), textAlignment);

        // Test The files to be updated are already uploaded, send the full show immediately
        String textField11 = "It's not";
        when(fileManager.hasUploadedFile(any(SdlFile.class))).thenReturn(true);
        TextsAndGraphicsState textsAndGraphicsState = new TextsAndGraphicsState(textField11, textField2, textField3, textField4,
                mediaTrackField, title, testArtwork3, testArtwork4, textAlignment, textField1Type, textField2Type, textField3Type, textField4Type);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);
        textAndGraphicUpdateOperation.onExecute();
        assertEquals(textAndGraphicUpdateOperation.getCurrentScreenData().getMainField1(), textField11);

        //Test: If there are no images to update, just send the text
        TextsAndGraphicsState textsAndGraphicsStateNullImages = new TextsAndGraphicsState(textField1, textField2, textField3, textField4,
                mediaTrackField, title, null, null, textAlignment, textField1Type, textField2Type, textField3Type, textField4Type);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsStateNullImages, listener);
        textAndGraphicUpdateOperation.onExecute();
        assertEquals(textAndGraphicUpdateOperation.getCurrentScreenData().getMainField1(), textField1);

        // Verifies that uploadArtworks gets called only with the fist textAndGraphicsUpdateOperation.onExecute call
        verify(fileManager, times(1)).uploadArtworks(any(List.class), any(MultipleFileCompletionListener.class));

    }

    @Test
    public void testCanceledRightAway() {
        textAndGraphicUpdateOperation.cancelTask();
        textAndGraphicUpdateOperation.onExecute();
        assertEquals(textAndGraphicUpdateOperation.getCurrentScreenData().getMainField1(), "Old");
    }

    @Test
    public void testTaskCanceledAfterImageUpload() {
        doAnswer(onShowSuccess).when(internalInterface).sendRPC(any(Show.class));
        doAnswer(onImageUploadSuccessTaskCanceled).when(fileManager).uploadArtworks(any(List.class), any(MultipleFileCompletionListener.class));

        // Test Canceled after Image upload
        textAndGraphicUpdateOperation.onExecute();
        verify(internalInterface, times(1)).sendRPC(any(Show.class));
        assertEquals(textAndGraphicUpdateOperation.getCurrentScreenData().getMainField1(), textField1);

    }

    public void testTaskCanceledAfterTextSent() {
        doAnswer(onShowSuccessCanceled).when(internalInterface).sendRPC(any(Show.class));
        textAndGraphicUpdateOperation.onExecute();
        verify(fileManager, times(0)).uploadArtworks(any(List.class), any(MultipleFileCompletionListener.class));

    }

    /**
     * Test getting number of lines available to be set based off of windowCapability
     */
    @Test
    public void testGetMainLines() {

        // We want to test that the looping works. By default, it will return 4 if display cap is null
        textAndGraphicUpdateOperation.defaultMainWindowCapability = getNullVarWindowCapability();

        // Null test
        assertEquals(0, ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(textAndGraphicUpdateOperation.defaultMainWindowCapability));

        // The tests.java class has an example of this, but we must build it to do what
        // we need it to do. Build display cap w/ 3 main fields and test that it returns 3
        textAndGraphicUpdateOperation.defaultMainWindowCapability = getWindowCapability(3);
        assertEquals(ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(textAndGraphicUpdateOperation.defaultMainWindowCapability), 3);
    }


    @Test
    public void testAssemble1Line() {

        Show inputShow = new Show();

        TextsAndGraphicsState textsAndGraphicsState = new TextsAndGraphicsState(textField1, null, null, null,
                mediaTrackField, title, testArtwork3, testArtwork4, textAlignment, MetadataType.HUMIDITY, null, null, null);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, getWindowCapability(1), currentScreenData, textsAndGraphicsState, listener);

        Show assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");

        // test tags (just 1)
        MetadataTags tags = assembledShow.getMetadataTags();
        List<MetadataType> tagsList = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        assertEquals(tags.getMainField1(), tagsList);

        textsAndGraphicsState.setTextField2(textField2);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, getWindowCapability(1), currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is - Wednesday");

        textsAndGraphicsState.setTextField3(textField3);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, getWindowCapability(1), currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is - Wednesday - My");

        textsAndGraphicsState.setTextField4(textField4);
        textsAndGraphicsState.setTextField4Type(MetadataType.CURRENT_TEMPERATURE);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, getWindowCapability(1), currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is - Wednesday - My - Dudes");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList.add(MetadataType.CURRENT_TEMPERATURE);
        assertEquals(tags.getMainField1(), tagsList);

        // For some obscurity, lets try setting just fields 2 and 4 for a 1 line display
        textsAndGraphicsState.setTextField1(null);
        textsAndGraphicsState.setTextField3(null);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, getWindowCapability(1), currentScreenData, textsAndGraphicsState, listener);


        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "Wednesday - Dudes");
    }

    @Test
    public void testAssemble2Lines() {

        Show inputShow = new Show();
        defaultMainWindowCapability = getWindowCapability(2);

        // Force it to return display with support for only 2 lines of text
        TextsAndGraphicsState textsAndGraphicsState = new TextsAndGraphicsState(textField1, null, null, null,
                mediaTrackField, title, testArtwork3, testArtwork4, textAlignment, MetadataType.HUMIDITY, null, null, null);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);

        Show assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");

        // test tags
        MetadataTags tags = assembledShow.getMetadataTags();
        List<MetadataType> tagsList = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        assertEquals(tags.getMainField1(), tagsList);

        textsAndGraphicsState.setTextField2(textField2);
        textsAndGraphicsState.setTextField2Type(MetadataType.CURRENT_TEMPERATURE);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "Wednesday");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        List<MetadataType> tagsList2 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);

        textsAndGraphicsState.setTextField3(textField3);
        textsAndGraphicsState.setTextField3Type(MetadataType.MEDIA_ALBUM);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, getWindowCapability(2), currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is - Wednesday");
        assertEquals(assembledShow.getMainField2(), "My");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList2 = new ArrayList<>();
        tagsList.add(MetadataType.CURRENT_TEMPERATURE);
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.MEDIA_ALBUM);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);

        textsAndGraphicsState.setTextField4(textField4);
        textsAndGraphicsState.setTextField4Type(MetadataType.MEDIA_STATION);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, getWindowCapability(2), currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is - Wednesday");
        assertEquals(assembledShow.getMainField2(), "My - Dudes");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList2 = new ArrayList<>();
        tagsList.add(MetadataType.CURRENT_TEMPERATURE);
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.MEDIA_STATION);
        tagsList2.add(MetadataType.MEDIA_ALBUM);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);

        // For some obscurity, lets try setting just fields 2 and 4 for a 2 line display
        textsAndGraphicsState.setTextField1(null);
        textsAndGraphicsState.setTextField3(null);
        textsAndGraphicsState.setTextField1Type(null);
        textsAndGraphicsState.setTextField3Type(null);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, getWindowCapability(2), currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "Wednesday");
        assertEquals(assembledShow.getMainField2(), "Dudes");

        // And 3 fields without setting 1
        textsAndGraphicsState.setTextField3(textField3);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, getWindowCapability(2), currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "Wednesday");
        assertEquals(assembledShow.getMainField2(), "My - Dudes");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList2 = new ArrayList<>();
        tagsList.add(MetadataType.CURRENT_TEMPERATURE);
        tagsList2.add(MetadataType.MEDIA_STATION);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);
    }

    @Test
    public void testAssemble3Lines() {

        Show inputShow = new Show();

        // Force it to return display with support for only 3 lines of text
        defaultMainWindowCapability = getWindowCapability(3);
        TextsAndGraphicsState textsAndGraphicsState = new TextsAndGraphicsState(textField1, null, null, null,
                mediaTrackField, title, testArtwork3, testArtwork4, textAlignment, MetadataType.HUMIDITY, null, null, null);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);

        Show assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "");
        assertEquals(assembledShow.getMainField3(), "");

        // test tags
        MetadataTags tags = assembledShow.getMetadataTags();
        List<MetadataType> tagsList = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        assertEquals(tags.getMainField1(), tagsList);

        textsAndGraphicsState.setTextField2(textField2);
        textsAndGraphicsState.setTextField2Type(MetadataType.CURRENT_TEMPERATURE);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "Wednesday");
        assertEquals(assembledShow.getMainField3(), "");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        List<MetadataType> tagsList2 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);

        textsAndGraphicsState.setTextField3(textField3);
        textsAndGraphicsState.setTextField3Type(MetadataType.MEDIA_ALBUM);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "Wednesday");
        assertEquals(assembledShow.getMainField3(), "My");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList2 = new ArrayList<>();
        List<MetadataType> tagsList3 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
        tagsList3.add(MetadataType.MEDIA_ALBUM);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);
        assertEquals(tags.getMainField3(), tagsList3);

        textsAndGraphicsState.setTextField4(textField4);
        textsAndGraphicsState.setTextField4Type(MetadataType.MEDIA_STATION);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "Wednesday");
        assertEquals(assembledShow.getMainField3(), "My - Dudes");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList2 = new ArrayList<>();
        tagsList3 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
        tagsList3.add(MetadataType.MEDIA_ALBUM);
        tagsList3.add(MetadataType.MEDIA_STATION);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);
        assertEquals(tags.getMainField3(), tagsList3);

        // Someone might not want to set the fields in order? We should handle that
        textsAndGraphicsState.setTextField1(null);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        try {
            System.out.println(assembledShow.serializeJSON().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assertEquals(assembledShow.getMainField2(), "Wednesday");
        assertEquals(assembledShow.getMainField3(), "My - Dudes");
    }

    @Test
    public void testAssemble4Lines() {

        Show inputShow = new Show();

        defaultMainWindowCapability = getWindowCapability(4);
        TextField tx1 = new TextField();
        TextField tx2 = new TextField();
        TextField tx3 = new TextField();
        TextField tx4 = new TextField();
        TextField tx5 = new TextField();
        TextField tx6 = new TextField();

        tx1.setName(TextFieldName.mainField1);
        tx2.setName(TextFieldName.mainField2);
        tx3.setName(TextFieldName.mainField3);
        tx4.setName(TextFieldName.mainField4);
        tx5.setName(TextFieldName.mediaTrack);
        tx6.setName(TextFieldName.templateTitle);

        List<TextField> textFieldNames = Arrays.asList(tx1, tx2, tx3, tx4, tx5, tx6);
        defaultMainWindowCapability.setTextFields(textFieldNames);

        TextsAndGraphicsState textsAndGraphicsState = new TextsAndGraphicsState(textField1, null, null, null,
                mediaTrackField, title, testArtwork3, testArtwork4, textAlignment, MetadataType.HUMIDITY, null, null, null);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);
        textsAndGraphicsState.setMediaTrackTextField("HI");
        textsAndGraphicsState.setTitle("bye");

        // Force it to return display with support for only 4 lines of text
        Show assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);

        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "");
        assertEquals(assembledShow.getMainField3(), "");
        assertEquals(assembledShow.getMainField4(), "");
        assertEquals(assembledShow.getMediaTrack(), "HI");
        assertEquals(assembledShow.getTemplateTitle(), "bye");

        // test tags
        MetadataTags tags = assembledShow.getMetadataTags();
        List<MetadataType> tagsList = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        assertEquals(tags.getMainField1(), tagsList);

        textsAndGraphicsState.setTextField2("Wednesday");
        textsAndGraphicsState.setTextField2Type(MetadataType.CURRENT_TEMPERATURE);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);


        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "Wednesday");
        assertEquals(assembledShow.getMainField3(), "");
        assertEquals(assembledShow.getMainField4(), "");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        List<MetadataType> tagsList2 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);

        textsAndGraphicsState.setTextField3("My");
        textsAndGraphicsState.setTextField3Type(MetadataType.MEDIA_ALBUM);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "Wednesday");
        assertEquals(assembledShow.getMainField3(), "My");
        assertEquals(assembledShow.getMainField4(), "");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList2 = new ArrayList<>();
        List<MetadataType> tagsList3 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
        tagsList3.add(MetadataType.MEDIA_ALBUM);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);
        assertEquals(tags.getMainField3(), tagsList3);

        textsAndGraphicsState.setTextField4("Dudes");
        textsAndGraphicsState.setTextField4Type(MetadataType.MEDIA_STATION);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "Wednesday");
        assertEquals(assembledShow.getMainField3(), "My");
        assertEquals(assembledShow.getMainField4(), "Dudes");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList2 = new ArrayList<>();
        tagsList3 = new ArrayList<>();
        List<MetadataType> tagsList4 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
        tagsList3.add(MetadataType.MEDIA_ALBUM);
        tagsList4.add(MetadataType.MEDIA_STATION);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);
        assertEquals(tags.getMainField3(), tagsList3);
        assertEquals(tags.getMainField4(), tagsList4);

        // try just setting line 1 and 4
        textsAndGraphicsState.setTextField2(null);
        textsAndGraphicsState.setTextField3(null);
        textsAndGraphicsState.setTextField2Type(null);
        textsAndGraphicsState.setTextField3Type(null);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "");
        assertEquals(assembledShow.getMainField3(), "");
        assertEquals(assembledShow.getMainField4(), "Dudes");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList4 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList4.add(MetadataType.MEDIA_STATION);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField4(), tagsList4);
    }

    /**
     * Testing if WindowCapability is null, TextFields should still update.
     */
    @Test
    public void testAssemble4LinesNullWindowCapability() {

        Show inputShow = new Show();

        TextsAndGraphicsState textsAndGraphicsState = new TextsAndGraphicsState(textField1, null, null, null,
                mediaTrackField, title, testArtwork3, testArtwork4, textAlignment, MetadataType.HUMIDITY, null, null, null);

        textsAndGraphicsState.setMediaTrackTextField("HI");
        textsAndGraphicsState.setTitle("bye");

        textsAndGraphicsState.setTextField1("It is");
        textsAndGraphicsState.setTextField1Type(MetadataType.HUMIDITY);

        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, null, currentScreenData, textsAndGraphicsState, listener);

        Show assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);

        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "");
        assertEquals(assembledShow.getMainField3(), "");
        assertEquals(assembledShow.getMainField4(), "");
        assertEquals(assembledShow.getMediaTrack(), "HI");
        assertEquals(assembledShow.getTemplateTitle(), "bye");

        // test tags
        MetadataTags tags = assembledShow.getMetadataTags();
        List<MetadataType> tagsList = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        assertEquals(tags.getMainField1(), tagsList);

        textsAndGraphicsState.setTextField2("Wednesday");
        textsAndGraphicsState.setTextField2Type(MetadataType.CURRENT_TEMPERATURE);

        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, null, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "Wednesday");
        assertEquals(assembledShow.getMainField3(), "");
        assertEquals(assembledShow.getMainField4(), "");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        List<MetadataType> tagsList2 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);

        textsAndGraphicsState.setTextField3("My");
        textsAndGraphicsState.setTextField3Type(MetadataType.MEDIA_ALBUM);

        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, null, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "Wednesday");
        assertEquals(assembledShow.getMainField3(), "My");
        assertEquals(assembledShow.getMainField4(), "");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList2 = new ArrayList<>();
        List<MetadataType> tagsList3 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
        tagsList3.add(MetadataType.MEDIA_ALBUM);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);
        assertEquals(tags.getMainField3(), tagsList3);

        textsAndGraphicsState.setTextField4("Dudes");
        textsAndGraphicsState.setTextField4Type(MetadataType.MEDIA_STATION);

        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, null, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "Wednesday");
        assertEquals(assembledShow.getMainField3(), "My");
        assertEquals(assembledShow.getMainField4(), "Dudes");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList2 = new ArrayList<>();
        tagsList3 = new ArrayList<>();
        List<MetadataType> tagsList4 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
        tagsList3.add(MetadataType.MEDIA_ALBUM);
        tagsList4.add(MetadataType.MEDIA_STATION);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField2(), tagsList2);
        assertEquals(tags.getMainField3(), tagsList3);
        assertEquals(tags.getMainField4(), tagsList4);

        // try just setting line 1 and 4
        textsAndGraphicsState.setTextField2(null);
        textsAndGraphicsState.setTextField3(null);
        textsAndGraphicsState.setTextField2Type(null);
        textsAndGraphicsState.setTextField3Type(null);

        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, null, currentScreenData, textsAndGraphicsState, listener);

        assembledShow = textAndGraphicUpdateOperation.assembleShowText(inputShow);
        assertEquals(assembledShow.getMainField1(), "It is");
        assertEquals(assembledShow.getMainField2(), "");
        assertEquals(assembledShow.getMainField3(), "");
        assertEquals(assembledShow.getMainField4(), "Dudes");

        // test tags
        tags = assembledShow.getMetadataTags();
        tagsList = new ArrayList<>();
        tagsList4 = new ArrayList<>();
        tagsList.add(MetadataType.HUMIDITY);
        tagsList4.add(MetadataType.MEDIA_STATION);
        assertEquals(tags.getMainField1(), tagsList);
        assertEquals(tags.getMainField4(), tagsList4);
    }

    @Test
    public void testExtractTextFromShow() {
        Show mainShow = new Show();
        mainShow.setMainField1("test");
        mainShow.setMainField3("Sauce");
        mainShow.setMainField4("");

        Show newShow = textAndGraphicUpdateOperation.extractTextFromShow(mainShow);

        assertEquals(newShow.getMainField1(), "test");
        assertEquals(newShow.getMainField3(), "Sauce");
        assertEquals(newShow.getMainField4(), "");
        assertNull(newShow.getMainField2());
    }

    @Test
    public void testCreateImageOnlyShowWithPrimaryArtwork() {
        // Test null
        Show testShow = textAndGraphicUpdateOperation.createImageOnlyShowWithPrimaryArtwork(null, null);
        assertNull(testShow);

        // Test when artwork hasn't been uploaded
        when(fileManager.hasUploadedFile(any(SdlFile.class))).thenReturn(false);
        TextsAndGraphicsState textsAndGraphicsState = new TextsAndGraphicsState(textField1, textField2, textField3, textField4,
                mediaTrackField, title, testArtwork3, testArtwork4, textAlignment, textField1Type, textField2Type, textField3Type, textField4Type);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);
        testShow = textAndGraphicUpdateOperation.createImageOnlyShowWithPrimaryArtwork(testArtwork1, testArtwork2);
        assertNull(testShow);

        // Test when artwork has been uploaded
        when(fileManager.hasUploadedFile(any(SdlFile.class))).thenReturn(true);
        textsAndGraphicsState = new TextsAndGraphicsState(textField1, textField2, textField3, textField4,
                mediaTrackField, title, testArtwork3, testArtwork4, textAlignment, textField1Type, textField2Type, textField3Type, textField4Type);
        textAndGraphicUpdateOperation = new TextAndGraphicUpdateOperation(internalInterface, fileManager, defaultMainWindowCapability, currentScreenData, textsAndGraphicsState, listener);
        testShow = textAndGraphicUpdateOperation.createImageOnlyShowWithPrimaryArtwork(testArtwork1, testArtwork2);
        assertEquals(testShow.getGraphic(), testArtwork1.getImageRPC());
        assertEquals(testShow.getSecondaryGraphic(), testArtwork2.getImageRPC());
    }

}
