/*
 * Copyright (c)  2019 Livio, Inc.
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
 *
 * Created by brettywhite on 6/12/19 1:52 PM
 *
 */

package com.smartdevicelink.managers.screen.choiceset;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.test.TestValues;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class PreloadChoicesOperationTests {

    private PreloadChoicesOperation preloadChoicesOperation;
    private PreloadChoicesOperation preloadChoicesOperationNullCapability;
    private PreloadChoicesOperation preloadChoicesOperationEmptyCapability;


    @Before
    public void setUp() throws Exception {

        ChoiceCell cell1 = new ChoiceCell("cell 1");
        ChoiceCell cell2 = new ChoiceCell("cell 2", null, TestValues.GENERAL_ARTWORK);
        LinkedHashSet<ChoiceCell> cellsToPreload = new LinkedHashSet<>();
        cellsToPreload.add(cell1);
        cellsToPreload.add(cell2);

        ImageField imageField = new ImageField(ImageFieldName.choiceImage, Arrays.asList(FileType.GRAPHIC_PNG, FileType.GRAPHIC_JPEG));
        ImageField imageField2 = new ImageField();
        imageField2.setName(ImageFieldName.choiceSecondaryImage);
        TextField textField = new TextField(TextFieldName.menuName, CharacterSet.CID1SET, 2, 2);

        TextField textField2 = new TextField();
        TextField textField3 = new TextField();

        textField2.setName(TextFieldName.secondaryText);
        textField3.setName(TextFieldName.tertiaryText);


        WindowCapability windowCapability = new WindowCapability();
        windowCapability.setImageFields(Arrays.asList(imageField, imageField2));
        windowCapability.setImageTypeSupported(Arrays.asList(ImageType.STATIC, ImageType.DYNAMIC));
        windowCapability.setTextFields(Arrays.asList(textField, textField2, textField3));

        ISdl internalInterface = mock(ISdl.class);
        FileManager fileManager = mock(FileManager.class);

        // We still want the mock fileManager to use the real implementation for fileNeedsUpload()
        when(fileManager.fileNeedsUpload(any(SdlFile.class))).thenCallRealMethod();

        preloadChoicesOperation = new PreloadChoicesOperation(internalInterface, fileManager, null, windowCapability, true, cellsToPreload, null);
    }

    /**
     * Sets up PreloadChoicesOperation with WindowCapability being null
     */
    public void setUpNullWindowCapability() {

        ChoiceCell cell1 = new ChoiceCell("cell 1");
        ChoiceCell cell2 = new ChoiceCell("cell 2", null, TestValues.GENERAL_ARTWORK);
        LinkedHashSet<ChoiceCell> cellsToPreload = new LinkedHashSet<>();
        cellsToPreload.add(cell1);
        cellsToPreload.add(cell2);

        ISdl internalInterface = mock(ISdl.class);
        FileManager fileManager = mock(FileManager.class);
        preloadChoicesOperationNullCapability = new PreloadChoicesOperation(internalInterface, fileManager, null, null, true, cellsToPreload, null);
    }

    /**
     * Sets up PreloadChoicesOperation with an Capability not being set
     * certain imageFields and TextFields
     */
    public void setUpEmptyWindowCapability() {

        ChoiceCell cell1 = new ChoiceCell("cell 1");
        ChoiceCell cell2 = new ChoiceCell("cell 2", null, TestValues.GENERAL_ARTWORK);
        LinkedHashSet<ChoiceCell> cellsToPreload = new LinkedHashSet<>();
        cellsToPreload.add(cell1);
        cellsToPreload.add(cell2);

        ImageField imageField = new ImageField();
        imageField.setName(ImageFieldName.alertIcon);

        TextField textField = new TextField();
        textField.setName(TextFieldName.mainField1);

        WindowCapability windowCapability = new WindowCapability();
        windowCapability.setImageFields(Collections.singletonList(imageField));
        windowCapability.setTextFields(Collections.singletonList(textField));

        ISdl internalInterface = mock(ISdl.class);
        FileManager fileManager = mock(FileManager.class);
        preloadChoicesOperationEmptyCapability = new PreloadChoicesOperation(internalInterface, fileManager, null, windowCapability, true, cellsToPreload, null);
    }

    @Test
    public void testArtworksToUpload() {
        List<SdlArtwork> artworksToUpload = preloadChoicesOperation.artworksToUpload();
        assertNotNull(artworksToUpload);
        assertEquals(artworksToUpload.size(), 1);
    }

    /**
     * Testing shouldSend method's with varying WindowCapability set.
     */
    @Test
    public void testShouldSendText() {

        setUpNullWindowCapability();
        assertTrue(preloadChoicesOperationNullCapability.shouldSendChoicePrimaryImage());
        assertTrue(preloadChoicesOperationNullCapability.shouldSendChoiceSecondaryImage());
        assertTrue(preloadChoicesOperationNullCapability.shouldSendChoiceSecondaryText());
        assertTrue(preloadChoicesOperationNullCapability.shouldSendChoiceTertiaryText());
        assertTrue(preloadChoicesOperationNullCapability.shouldSendChoiceText());


        assertTrue(preloadChoicesOperation.shouldSendChoicePrimaryImage());
        assertTrue(preloadChoicesOperation.shouldSendChoiceSecondaryImage());
        assertTrue(preloadChoicesOperation.shouldSendChoiceSecondaryText());
        assertTrue(preloadChoicesOperation.shouldSendChoiceTertiaryText());
        assertTrue(preloadChoicesOperation.shouldSendChoiceText());

        setUpEmptyWindowCapability();
        assertFalse(preloadChoicesOperationEmptyCapability.shouldSendChoicePrimaryImage());
        assertFalse(preloadChoicesOperationEmptyCapability.shouldSendChoiceSecondaryImage());
        assertFalse(preloadChoicesOperationEmptyCapability.shouldSendChoiceSecondaryText());
        assertFalse(preloadChoicesOperationEmptyCapability.shouldSendChoiceTertiaryText());
        assertFalse(preloadChoicesOperationEmptyCapability.shouldSendChoiceText());
    }

}
