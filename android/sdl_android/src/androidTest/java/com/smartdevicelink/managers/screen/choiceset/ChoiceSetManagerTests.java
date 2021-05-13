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
 * Created by brettywhite on 6/11/19 9:58 AM
 *
 */

package com.smartdevicelink.managers.screen.choiceset;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.KeyboardCapabilities;
import com.smartdevicelink.proxy.rpc.KeyboardLayoutCapability;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.KeyboardInputMask;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.test.TestValues;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ChoiceSetManagerTests {

    private ChoiceSetManager csm;
    Taskmaster taskmaster;

    @Before
    public void setUp() throws Exception {

        ISdl internalInterface = mock(ISdl.class);
        FileManager fileManager = mock(FileManager.class);
        taskmaster = new Taskmaster.Builder().build();
        when(internalInterface.getTaskmaster()).thenReturn(taskmaster);
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(7, 0));
        csm = new ChoiceSetManager(internalInterface, fileManager);

        assertEquals(csm.getState(), BaseSubManager.SETTING_UP);
        assertEquals(csm.currentSystemContext, SystemContext.SYSCTXT_MAIN);
        assertEquals(csm.currentHMILevel, HMILevel.HMI_NONE);
        assertEquals(csm.choiceCellIdMin, 1);
        assertEquals(csm.nextChoiceId, 1);
        assertFalse(csm.isVROptional);
        assertNotNull(csm.fileManager);
        assertNotNull(csm.preloadedChoices);
        assertNotNull(csm.pendingPreloadChoices);
        assertNotNull(csm.transactionQueue);
        assertNotNull(csm.hmiListener);
        assertNotNull(csm.onDisplayCapabilityListener);
        assertNull(csm.pendingPresentOperation);
    }

    @After
    public void tearDown() throws Exception {

        csm.dispose();

        assertNull(csm.currentHMILevel);
        assertNull(csm.currentSystemContext);
        assertNull(csm.defaultMainWindowCapability);
        assertNull(csm.pendingPresentationSet);
        assertNull(csm.pendingPresentOperation);

        assertEquals(csm.transactionQueue.getTasksAsList().size(), 0);
        assertEquals(csm.nextChoiceId, 1);

        assertFalse(csm.isVROptional);

        assertEquals(csm.getState(), BaseSubManager.SHUTDOWN);

    }

    @Test
    public void testDefaultKeyboardConfiguration() {
        KeyboardProperties properties = csm.defaultKeyboardConfiguration();
        assertEquals(properties.getLanguage(), Language.EN_US);
        assertEquals(properties.getKeyboardLayout(), KeyboardLayout.QWERTY);
        assertEquals(properties.getKeypressMode(), KeypressMode.RESEND_CURRENT_ENTRY);
    }

    @Test
    public void testSetupChoiceSet() {

        ChoiceSetSelectionListener choiceSetSelectionListener = new ChoiceSetSelectionListener() {
            @Override
            public void onChoiceSelected(ChoiceCell choiceCell, TriggerSource triggerSource, int rowIndex) {
            }

            @Override
            public void onError(String error) {
            }
        };

        // Cannot send choice set with empty or null choice list
        ChoiceSet choiceSet1 = new ChoiceSet("test", Collections.<ChoiceCell>emptyList(), choiceSetSelectionListener);
        assertFalse(csm.setUpChoiceSet(choiceSet1));

        // Identical cells will not be allowed
        ChoiceCell cell1 = new ChoiceCell("test");
        ChoiceCell cell2 = new ChoiceCell("test");
        ChoiceSet choiceSet2 = new ChoiceSet("test", Arrays.asList(cell1, cell2), choiceSetSelectionListener);
        assertFalse(csm.setUpChoiceSet(choiceSet2));

        // cells that have duplicate text will be allowed if there is another property to make them unique because a unique name will be assigned and used
        ChoiceCell cell3 = new ChoiceCell("test");
        cell3.setSecondaryText("text 1");
        ChoiceCell cell4 = new ChoiceCell("test");
        cell4.setSecondaryText("text 2");
        ChoiceSet choiceSet3 = new ChoiceSet("test", Arrays.asList(cell3, cell4), choiceSetSelectionListener);
        assertTrue(csm.setUpChoiceSet(choiceSet3));

        // cells cannot mix and match VR / non-VR
        ChoiceCell cell5 = new ChoiceCell("test", Collections.singletonList("Test"), null);
        ChoiceCell cell6 = new ChoiceCell("test2");
        ChoiceSet choiceSet4 = new ChoiceSet("test", Arrays.asList(cell5, cell6), choiceSetSelectionListener);
        assertFalse(csm.setUpChoiceSet(choiceSet4));

        // VR Commands must be unique
        ChoiceCell cell7 = new ChoiceCell("test", Collections.singletonList("Test"), null);
        ChoiceCell cell8 = new ChoiceCell("test2", Collections.singletonList("Test"), null);
        ChoiceSet choiceSet5 = new ChoiceSet("test", Arrays.asList(cell7, cell8), choiceSetSelectionListener);
        assertFalse(csm.setUpChoiceSet(choiceSet5));

        // Passing Case
        ChoiceCell cell9 = new ChoiceCell("test", Collections.singletonList("Test"), null);
        ChoiceCell cell10 = new ChoiceCell("test2", Collections.singletonList("Test2"), null);
        ChoiceSet choiceSet6 = new ChoiceSet("test", Arrays.asList(cell9, cell10), choiceSetSelectionListener);
        assertTrue(csm.setUpChoiceSet(choiceSet6));
    }

    @Test
    public void testFindIfPresent() {

        ChoiceCell cell1 = new ChoiceCell("test");
        ChoiceCell cell2 = new ChoiceCell("test2");
        ChoiceCell cell3 = new ChoiceCell("test3");
        HashSet<ChoiceCell> cellSet = new HashSet<>();
        cellSet.add(cell1);
        cellSet.add(cell2);

        assertNotNull(csm.findIfPresent(cell1, cellSet));
        assertNull(csm.findIfPresent(cell3, cellSet));
    }

    @Test
    public void testUpdateIdsOnChoices() {

        ChoiceCell cell1 = new ChoiceCell("test");
        ChoiceCell cell2 = new ChoiceCell("test2");
        ChoiceCell cell3 = new ChoiceCell("test3");
        LinkedHashSet<ChoiceCell> cellSet = new LinkedHashSet<>();
        cellSet.add(cell1);
        cellSet.add(cell2);
        cellSet.add(cell3);
        // Cells are initially set to MAX_ID
        assertEquals(cell1.getChoiceId(), 2000000000);
        assertEquals(cell2.getChoiceId(), 2000000000);
        assertEquals(cell3.getChoiceId(), 2000000000);
        csm.updateIdsOnChoices(cellSet);
        // We are looking for unique IDs
        assertNotSame(cell1.getChoiceId(), 2000000000);
        assertNotSame(cell2.getChoiceId(), 2000000000);
        assertNotSame(cell3.getChoiceId(), 2000000000);
    }

    @Test
    public void testAddUniqueNamesToCells() {
        ChoiceCell cell1 = new ChoiceCell("McDonalds", "1 mile away", null, null, null, null);
        ChoiceCell cell2 = new ChoiceCell("McDonalds", "2 mile away", null, null, null, null);
        ChoiceCell cell3 = new ChoiceCell("Starbucks", "3 mile away", null, null, null, null);
        ChoiceCell cell4 = new ChoiceCell("McDonalds", "4 mile away", null, null, null, null);
        ChoiceCell cell5 = new ChoiceCell("Starbucks", "5 mile away", null, null, null, null);
        ChoiceCell cell6 = new ChoiceCell("Meijer", "6 mile away", null, null, null, null);
        LinkedHashSet<ChoiceCell> cellList = new LinkedHashSet<>();
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);
        cellList.add(cell4);
        cellList.add(cell5);
        cellList.add(cell6);

        csm.addUniqueNamesToCells(cellList);

        assertEquals(cell1.getUniqueText(), "McDonalds");
        assertEquals(cell2.getUniqueText(), "McDonalds (2)");
        assertEquals(cell3.getUniqueText(), "Starbucks");
        assertEquals(cell4.getUniqueText(), "McDonalds (3)");
        assertEquals(cell5.getUniqueText(), "Starbucks (2)");
        assertEquals(cell6.getUniqueText(), "Meijer");
    }

    @Test
    public void testChoicesToBeRemovedFromPendingWithArray() {

        ChoiceCell cell1 = new ChoiceCell("test");
        ChoiceCell cell2 = new ChoiceCell("test2");
        ChoiceCell cell3 = new ChoiceCell("test3");

        HashSet<ChoiceCell> pendingPreloadSet = new HashSet<>();
        pendingPreloadSet.add(cell1);
        pendingPreloadSet.add(cell2);
        pendingPreloadSet.add(cell3);

        csm.pendingPreloadChoices.clear();
        csm.pendingPreloadChoices = pendingPreloadSet;

        List<ChoiceCell> choices = new ArrayList<>();
        choices.add(cell2);

        HashSet<ChoiceCell> returnedChoices = csm.choicesToBeRemovedFromPendingWithArray(choices);

        assertEquals(returnedChoices.size(), 1);
        for (ChoiceCell cell : returnedChoices) {
            assertEquals(cell.getText(), "test2");
        }
    }

    @Test
    public void testChoicesToBeUploadedWithArray() {

        ChoiceCell cell1 = new ChoiceCell("test");
        ChoiceCell cell2 = new ChoiceCell("test2");
        ChoiceCell cell3 = new ChoiceCell("test3");

        HashSet<ChoiceCell> pendingDeleteSet = new HashSet<>();
        pendingDeleteSet.add(cell1);
        pendingDeleteSet.add(cell2);
        pendingDeleteSet.add(cell3);

        csm.preloadedChoices.clear();
        csm.preloadedChoices = pendingDeleteSet;

        List<ChoiceCell> choices = new ArrayList<>();
        choices.add(cell2);

        HashSet<ChoiceCell> returnedChoices = csm.choicesToBeDeletedWithArray(choices);

        assertEquals(returnedChoices.size(), 1);
        for (ChoiceCell cell : returnedChoices) {
            assertEquals(cell.getText(), "test2");
        }
    }

    @Test
    public void testPresentingKeyboardShouldReturnCancelIDIfKeyboardCanBeSent() {
        ISdl internalInterface = mock(ISdl.class);
        when(internalInterface.getTaskmaster()).thenReturn(taskmaster);
        FileManager fileManager = mock(FileManager.class);

        ChoiceSetManager newCSM = new ChoiceSetManager(internalInterface, fileManager);
        ChoiceSetManager partialMockCSM = spy(newCSM);
        when(partialMockCSM.getState()).thenReturn(BaseSubManager.READY);

        Integer cancelId = partialMockCSM.presentKeyboard("initial text", mock(KeyboardProperties.class), mock(KeyboardListener.class));
        assertNotNull(cancelId);
    }

    @Test
    public void testPresentingKeyboardShouldNotReturnCancelIDIfKeyboardCannotBeSent() {
        ISdl internalInterface = mock(ISdl.class);
        when(internalInterface.getTaskmaster()).thenReturn(taskmaster);
        FileManager fileManager = mock(FileManager.class);

        ChoiceSetManager newCSM = new ChoiceSetManager(internalInterface, fileManager);
        ChoiceSetManager partialMockCSM = spy(newCSM);
        when(partialMockCSM.getState()).thenReturn(BaseSubManager.ERROR);

        Integer cancelId = partialMockCSM.presentKeyboard("initial text", mock(KeyboardProperties.class), mock(KeyboardListener.class));
        assertNull(cancelId);
    }

    @Test
    public void testDefaultWindowCapabilityNotSet() throws NoSuchFieldException, IllegalAccessException {
        ISdl internalInterface = mock(ISdl.class);
        when(internalInterface.getTaskmaster()).thenReturn(taskmaster);
        FileManager fileManager = mock(FileManager.class);

        // Test direct set
        ChoiceSetManager newCSM = new ChoiceSetManager(internalInterface, fileManager);
        newCSM.setKeyboardConfiguration(newCSM.defaultKeyboardConfiguration());
        Field field = BaseChoiceSetManager.class.getDeclaredField("keyboardConfiguration");
        field.setAccessible(true);
        KeyboardProperties properties = (KeyboardProperties)field.get(newCSM);
        assertEquals(properties, csm.defaultKeyboardConfiguration());

        // Test presentKeyboard
        newCSM = new ChoiceSetManager(internalInterface, fileManager);
        newCSM.presentKeyboard("qwerty", newCSM.defaultKeyboardConfiguration(), null);
        field = BaseChoiceSetManager.class.getDeclaredField("keyboardConfiguration");
        field.setAccessible(true);
        properties = (KeyboardProperties)field.get(newCSM);
        assertEquals(properties, csm.defaultKeyboardConfiguration());
    }

    @Test
    public void testDefaultWindowCapabilityTooManyKeys() throws NoSuchFieldException, IllegalAccessException {
        ISdl internalInterface = mock(ISdl.class);
        when(internalInterface.getTaskmaster()).thenReturn(taskmaster);
        FileManager fileManager = mock(FileManager.class);

        ChoiceSetManager newCSM = new ChoiceSetManager(internalInterface, fileManager);
        WindowCapability smallKeysAmountCapability = new WindowCapability();
        KeyboardCapabilities capabilities = new KeyboardCapabilities();
        capabilities.setMaskInputCharactersSupported(true);
        KeyboardLayout layout = KeyboardLayout.QWERTY;
        capabilities.setSupportedKeyboards(Collections.singletonList(new KeyboardLayoutCapability(layout, 1)));
        smallKeysAmountCapability.setKeyboardCapabilities(capabilities);
        newCSM.defaultMainWindowCapability = smallKeysAmountCapability;

        KeyboardProperties setProperties = new KeyboardProperties();
        setProperties.setKeyboardLayout(layout);
        setProperties.setCustomKeys(Arrays.asList("1", "2"));

        newCSM.setKeyboardConfiguration(setProperties);
        Field field = BaseChoiceSetManager.class.getDeclaredField("keyboardConfiguration");
        field.setAccessible(true);

        KeyboardProperties getProperties = (KeyboardProperties)field.get(newCSM);

        assertEquals(getProperties.getCustomKeys().size(), 1);
    }

    @Test
    public void testCustomKeysNull() throws NoSuchFieldException, IllegalAccessException {
        ISdl internalInterface = mock(ISdl.class);
        when(internalInterface.getTaskmaster()).thenReturn(taskmaster);
        FileManager fileManager = mock(FileManager.class);

        ChoiceSetManager newCSM = new ChoiceSetManager(internalInterface, fileManager);
        WindowCapability smallKeysAmountCapability = new WindowCapability();
        KeyboardCapabilities capabilities = new KeyboardCapabilities();
        capabilities.setMaskInputCharactersSupported(true);
        KeyboardLayout layout = KeyboardLayout.QWERTY;
        capabilities.setSupportedKeyboards(Collections.singletonList(new KeyboardLayoutCapability(layout, 0)));
        smallKeysAmountCapability.setKeyboardCapabilities(capabilities);
        newCSM.defaultMainWindowCapability = smallKeysAmountCapability;

        KeyboardProperties setProperties = new KeyboardProperties();
        setProperties.setKeyboardLayout(layout);
        setProperties.setCustomKeys(new ArrayList<String>());

        newCSM.setKeyboardConfiguration(setProperties);
        Field field = BaseChoiceSetManager.class.getDeclaredField("keyboardConfiguration");
        field.setAccessible(true);

        KeyboardProperties getProperties = (KeyboardProperties)field.get(newCSM);

        assertNull(getProperties.getCustomKeys());
    }

    @Test
    public void testMaskInputCharactersNotSupported() throws NoSuchFieldException, IllegalAccessException {
        ISdl internalInterface = mock(ISdl.class);
        when(internalInterface.getTaskmaster()).thenReturn(taskmaster);
        FileManager fileManager = mock(FileManager.class);

        ChoiceSetManager newCSM = new ChoiceSetManager(internalInterface, fileManager);
        WindowCapability maskInputNotSupportedCapability = new WindowCapability();
        KeyboardCapabilities capabilities = new KeyboardCapabilities();

        capabilities.setMaskInputCharactersSupported(false);
        KeyboardLayout layout = KeyboardLayout.QWERTY;
        capabilities.setSupportedKeyboards(Collections.singletonList(new KeyboardLayoutCapability(layout, 0)));

        maskInputNotSupportedCapability.setKeyboardCapabilities(capabilities);

        newCSM.defaultMainWindowCapability = maskInputNotSupportedCapability;

        KeyboardProperties setProperties = new KeyboardProperties();
        setProperties.setKeyboardLayout(layout);
        setProperties.setMaskInputCharacters(KeyboardInputMask.DISABLE_INPUT_KEY_MASK);

        newCSM.setKeyboardConfiguration(setProperties);
        Field field = BaseChoiceSetManager.class.getDeclaredField("keyboardConfiguration");
        field.setAccessible(true);

        KeyboardProperties getProperties = (KeyboardProperties)field.get(newCSM);

        assertNull(getProperties.getMaskInputCharacters());
    }

    @Test
    public void testDismissingExecutingKeyboard() {
        Integer testCancelID = 42;
        PresentKeyboardOperation testKeyboardOp = mock(PresentKeyboardOperation.class);
        doReturn(testCancelID).when(testKeyboardOp).getCancelID();
        csm.currentlyPresentedKeyboardOperation = testKeyboardOp;
        csm.dismissKeyboard(testCancelID);
        verify(testKeyboardOp, times(1)).dismissKeyboard();
    }

    @Test
    public void testDismissingQueuedKeyboard() {
        Integer testCancelID = 42;

        // Currently executing operation
        PresentKeyboardOperation testKeyboardOp = mock(PresentKeyboardOperation.class);
        doReturn(96).when(testKeyboardOp).getCancelID();
        csm.currentlyPresentedKeyboardOperation = testKeyboardOp;

        // Queued operations
        PresentKeyboardOperation testKeyboardOp2 = mock(PresentKeyboardOperation.class);
        doReturn(testCancelID).when(testKeyboardOp2).getCancelID();
        csm.currentlyPresentedKeyboardOperation = testKeyboardOp2;

        // Queued operation should be canceled
        csm.dismissKeyboard(testCancelID);
        verify(testKeyboardOp, times(0)).dismissKeyboard();
        verify(testKeyboardOp2, times(1)).dismissKeyboard();
    }

    @Test
    public void testUniquenessForAvailableFields() {
        WindowCapability windowCapability = new WindowCapability();
        TextField secondaryText = new TextField();
        secondaryText.setName(TextFieldName.secondaryText);
        TextField tertiaryText = new TextField();
        tertiaryText.setName(TextFieldName.tertiaryText);

        List<TextField> textFields = new ArrayList<>();
        textFields.add(secondaryText);
        textFields.add(tertiaryText);

        windowCapability.setTextFields(textFields);

        ImageField choiceImage = new ImageField();
        choiceImage.setName(ImageFieldName.choiceImage);
        ImageField choiceSecondaryImage = new ImageField();
        choiceSecondaryImage.setName(ImageFieldName.choiceSecondaryImage);

        List<ImageField> imageFieldList = new ArrayList<>();
        imageFieldList.add(choiceImage);
        imageFieldList.add(choiceSecondaryImage);
        windowCapability.setImageFields(imageFieldList);

        csm.defaultMainWindowCapability = windowCapability;

        ChoiceCell cell1 = new ChoiceCell("Item 1", "null" , "tertiaryText", null, TestValues.GENERAL_ARTWORK, TestValues.GENERAL_ARTWORK );
        ChoiceCell cell2 = new ChoiceCell("Item 2", "null" , "tertiaryText", null, TestValues.GENERAL_ARTWORK ,TestValues.GENERAL_ARTWORK );
        List<ChoiceCell> choiceCellList = new ArrayList<>();
        choiceCellList.add(cell1);
        choiceCellList.add(cell2);
        ChoiceSet choiceSet = new ChoiceSet("choicSet" , choiceCellList, null);

        assertTrue(csm.setUpChoiceSet(choiceSet));

        cell2.setText("Item 1");
        assertFalse(csm.setUpChoiceSet(choiceSet));
        cell2.setSecondaryText("changed text");
        assertTrue(csm.setUpChoiceSet(choiceSet));
        textFields.remove(secondaryText);
        assertFalse(csm.setUpChoiceSet(choiceSet));
        cell2.setTertiaryText("changed text");
        assertTrue(csm.setUpChoiceSet(choiceSet));
        textFields.remove(tertiaryText);
        assertFalse(csm.setUpChoiceSet(choiceSet));
        cell2.setArtwork(null);
        assertTrue(csm.setUpChoiceSet(choiceSet));
        imageFieldList.remove(choiceImage);
        assertFalse(csm.setUpChoiceSet(choiceSet));
        cell2.setSecondaryArtwork(null);
        assertTrue(csm.setUpChoiceSet(choiceSet));
        imageFieldList.remove(choiceSecondaryImage);
        assertFalse(csm.setUpChoiceSet(choiceSet));



    }
}
