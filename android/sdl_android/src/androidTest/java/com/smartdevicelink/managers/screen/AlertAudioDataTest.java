package com.smartdevicelink.managers.screen;

import android.content.Context;
import android.net.Uri;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.managers.screen.choiceset.ChoiceCell;
import com.smartdevicelink.managers.screen.choiceset.ChoiceSet;
import com.smartdevicelink.managers.screen.choiceset.ChoiceSetLayout;
import com.smartdevicelink.managers.screen.choiceset.ChoiceSetSelectionListener;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.TestValues;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class AlertAudioDataTest {
    SdlFile testAudio;

    @Before
    public void setUp() throws Exception {
        Context mTestContext = getInstrumentation().getContext();
        Uri uri1 = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/raw/test_audio_square_250hz_80amp_1s.mp3");
        testAudio = new SdlFile("TestAudioFile", FileType.AUDIO_MP3, uri1, false);
    }

    @Test
    public void testConstructors() {
        AlertAudioData alertAudioData = new AlertAudioData();
        alertAudioData.setPlayTone(true);
        assertTrue(alertAudioData.isPlayTone());

        AlertAudioData alertAudioData1 = new AlertAudioData("phoneticString", SpeechCapabilities.TEXT);
        assertEquals("phoneticString", alertAudioData1.getAudioData().get(0).getText());

        AlertAudioData alertAudioData2 = new AlertAudioData("spokenString");
        assertEquals("spokenString", alertAudioData2.getAudioData().get(0).getText());

        AlertAudioData alertAudioData3 = new AlertAudioData(testAudio);
        assertEquals(alertAudioData3.getAudioData().get(0).getText(), testAudio.getName());
    }

    @Test
    public void testAdd() {
        AlertAudioData alertAudioData1 = new AlertAudioData("phoneticString", SpeechCapabilities.TEXT);
        alertAudioData1.addAudioFiles(Collections.singletonList(testAudio));
        alertAudioData1.addPhoneticSpeechSynthesizerStrings(Collections.singletonList("addition"), SpeechCapabilities.TEXT);
        alertAudioData1.addSpeechSynthesizerStrings(Collections.singletonList("addition2"));
        alertAudioData1.addAudioFiles(Collections.singletonList(testAudio));
        assertEquals("phoneticString", alertAudioData1.getAudioData().get(0).getText());
        assertEquals(testAudio.getName(), alertAudioData1.getAudioData().get(1).getText());
        assertEquals("addition", alertAudioData1.getAudioData().get(2).getText());
        assertEquals("addition2", alertAudioData1.getAudioData().get(3).getText());


    }
}
