package com.smartdevicelink.managers.screen;

import android.content.Context;
import android.net.Uri;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.smartdevicelink.managers.AlertCompletionListener;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class PresentAlertOperationTest {

    private PresentAlertOperation presentAlertOperation;
    private TextAlignment textAlignment;
    private WindowCapability defaultMainWindowCapability;
    private AlertView alertView;
    private CompletionListener listener;
    private AlertAudioData alertAudioData;
    SdlArtwork testAlertArtwork;
    ISdl internalInterface;
    FileManager fileManager;
    SoftButtonState alertSoftButtonState;
    SoftButtonObject alertSoftButtonObject;

    @Before
    public void setUp() throws Exception {
        Context mTestContext = getInstrumentation().getContext();
        // mock things
        internalInterface = mock(ISdl.class);
        fileManager = mock(FileManager.class);

        alertAudioData = new AlertAudioData("Spoken Sting");
        alertAudioData.setPlayTone(true);

        testAlertArtwork = new SdlArtwork();
        testAlertArtwork.setName("testFile1");
        Uri uri1 = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
        testAlertArtwork.setUri(uri1);
        testAlertArtwork.setType(FileType.GRAPHIC_PNG);
        alertSoftButtonState = new SoftButtonState("state1", "State 1", null);
        SoftButtonObject.OnEventListener onEventListener = new SoftButtonObject.OnEventListener() {
            @Override
            public void onPress(SoftButtonObject softButtonObject, OnButtonPress onButtonPress) {

            }

            @Override
            public void onEvent(SoftButtonObject softButtonObject, OnButtonEvent onButtonEvent) {

            }
        };
        alertSoftButtonObject = new SoftButtonObject("Soft button 1", alertSoftButtonState, onEventListener);


        textAlignment = TextAlignment.CENTERED;
        AlertView.Builder builder = new AlertView.Builder();
        builder.setText("test");
        builder.setSecondaryText("secondaryText");
        builder.setTertiaryText("tertiaryText");
        builder.setAudio(alertAudioData);
        builder.setIcon(testAlertArtwork);
        builder.setDefaultTimeOut(10);
        builder.setTimeout(5);
        builder.setSoftButtons(Collections.singletonList(alertSoftButtonObject));
        builder.setShowWaitIndicator(true);
        alertView = builder.build();

        defaultMainWindowCapability = getWindowCapability(3);
        List<SpeechCapabilities> speechCapabilities = new ArrayList<SpeechCapabilities>();
        AlertCompletionListener alertCompletionListener = new AlertCompletionListener() {
            @Override
            public void onComplete(boolean success, Integer tryAgainTime) {

            }
        };
        presentAlertOperation = new PresentAlertOperation(internalInterface, alertView, defaultMainWindowCapability, speechCapabilities, fileManager, 1, alertCompletionListener);
    }

    private WindowCapability getWindowCapability(int numberOfAlertFields) {

        TextField alertText1 = new TextField();
        alertText1.setName(TextFieldName.alertText1);
        TextField alertText2 = new TextField();
        alertText2.setName(TextFieldName.alertText2);
        TextField alertText3 = new TextField();
        alertText3.setName(TextFieldName.alertText3);
        TextField mainField4 = new TextField();
        mainField4.setName(TextFieldName.mainField4);

        List<TextField> textFieldList = new ArrayList<>();

        textFieldList.add(alertText1);
        textFieldList.add(alertText2);
        textFieldList.add(alertText3);

        List<TextField> returnList = new ArrayList<>();

        if (numberOfAlertFields > 0) {
            for (int i = 0; i < numberOfAlertFields; i++) {
                returnList.add(textFieldList.get(i));
            }
        }

        WindowCapability windowCapability = new WindowCapability();
        windowCapability.setTextFields(returnList);

        ImageField imageField = new ImageField();
        imageField.setName(ImageFieldName.alertIcon);
        List<ImageField> imageFieldList = new ArrayList<>();
        imageFieldList.add(imageField);
        windowCapability.setImageFields(imageFieldList);

        windowCapability.setImageFields(imageFieldList);

        return windowCapability;
    }

    @Test
    public void testCreateAlert() {
        //PresentAlertOperation
    }
}
