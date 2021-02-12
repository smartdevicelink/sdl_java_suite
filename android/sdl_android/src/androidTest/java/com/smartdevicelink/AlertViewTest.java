package com.smartdevicelink;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.screen.AlertAudioData;
import com.smartdevicelink.managers.screen.AlertView;
import com.smartdevicelink.managers.screen.SoftButtonObject;
import com.smartdevicelink.managers.screen.SoftButtonState;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;

@RunWith(AndroidJUnit4.class)
public class AlertViewTest {

    @Test
    public void testAlertView() {
        SdlArtwork artwork1 = new SdlArtwork("test1", FileType.GRAPHIC_PNG, 1, true);
        SdlArtwork artwork2 = new SdlArtwork("test2", FileType.GRAPHIC_PNG, 2, true);

        SoftButtonState softButtonState1 = new SoftButtonState("object1-state1", "o1s1", new SdlArtwork("image1", FileType.GRAPHIC_PNG, 3, true));
        SoftButtonObject softButtonObject1 = new SoftButtonObject("object1", Arrays.asList(softButtonState1), softButtonState1.getName(), null);
        SoftButtonObject softButtonObject2 = new SoftButtonObject("object2", Arrays.asList(softButtonState1), softButtonState1.getName(), null);

        AlertAudioData alertAudioData = new AlertAudioData("hi");

        AlertView.Builder builder = new AlertView.Builder();
        builder.setText("Test");
        builder.setTertiaryText("Test");
        builder.setSecondaryText("Test");
        builder.setTimeout(1);
        builder.setIcon(artwork1);
        builder.setSoftButtons(Collections.singletonList(softButtonObject1));
        builder.setDefaultTimeOut(3);
        builder.setAudio(alertAudioData);
        AlertView alertView = builder.build();

        assertEquals(alertView.getText(), "Test");
        assertEquals(alertView.getSecondaryText(), "Test");
        assertEquals(alertView.getTertiaryText(), "Test");
        assertTrue(alertView.getAudio().getAudioData().size() > 0);
        assertEquals(alertView.getIcon().getName(), "test1");
        assertEquals(alertView.getSoftButtons().get(0).getName(), "object1");
        assertEquals(alertView.getDefaultTimeout(), 3);
        assertEquals(alertView.getTimeout().intValue(), 3);

        alertView.setText("Test2");
        alertView.setTertiaryText("Test2");
        alertView.setSecondaryText("Test2");
        alertView.setDefaultTimeout(6);
        alertView.setTimeout(6);
        alertView.setAudio(alertAudioData);
        alertView.setIcon(artwork2);
        alertView.setSoftButtons(Collections.singletonList(softButtonObject2));

        assertEquals(alertView.getText(), "Test2");
        assertEquals(alertView.getSecondaryText(), "Test2");
        assertEquals(alertView.getTertiaryText(), "Test2");
        assertTrue(alertView.getAudio().getAudioData().size() > 0);
        assertEquals(alertView.getIcon().getName(), "test2");
        assertEquals(alertView.getSoftButtons().get(0).getName(), "object2");
        assertEquals(alertView.getDefaultTimeout(), 6);
        assertEquals(alertView.getTimeout().intValue(), 6);
    }
}
