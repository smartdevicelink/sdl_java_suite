package com.smartdevicelink.managers.screen.choiceset;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.livio.taskmaster.Queue;
import com.livio.taskmaster.Task;
import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.CancelInteraction;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.util.Version;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class PreloadPresentChoicesOperationTests {

    private PreloadPresentChoicesOperation preloadChoicesOperation;
    private PreloadPresentChoicesOperation preloadChoicesOperationNullCapability;
    private PreloadPresentChoicesOperation preloadChoicesOperationEmptyCapability;

    private PreloadPresentChoicesOperation presentChoicesOperation;
    private ChoiceSet choiceSet;
    private ISdl internalInterface;
    private FileManager fileManager;
    private KeyboardListener keyboardListener;
    private ChoiceSetSelectionListener choiceSetSelectionListener;

    private Taskmaster taskmaster;
    private Queue queue;

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

        internalInterface = mock(ISdl.class);
        fileManager = mock(FileManager.class);

        // We still want the mock fileManager to use the real implementation for fileNeedsUpload()
        when(fileManager.fileNeedsUpload(any(SdlFile.class))).thenCallRealMethod();

        preloadChoicesOperation = new PreloadPresentChoicesOperation(internalInterface, fileManager, null, windowCapability, true, cellsToPreload, null, null);

        keyboardListener = mock(KeyboardListener.class);
        choiceSetSelectionListener = mock(ChoiceSetSelectionListener.class);

        ChoiceCell cell = new ChoiceCell("Cell1");
        cell.setChoiceId(0);
        choiceSet = new ChoiceSet("Test", Collections.singletonList(cell), choiceSetSelectionListener);

        taskmaster = new Taskmaster.Builder().build();
        queue = taskmaster.createQueue("test", 100, false);
        taskmaster.start();
    }

    private KeyboardProperties getKeyBoardProperties() {
        KeyboardProperties properties = new KeyboardProperties();
        properties.setLanguage(Language.EN_US);
        properties.setKeyboardLayout(KeyboardLayout.QWERTZ);
        properties.setKeypressMode(KeypressMode.RESEND_CURRENT_ENTRY);
        return properties;
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
        preloadChoicesOperationNullCapability = new PreloadPresentChoicesOperation(internalInterface, fileManager, null, null, true, cellsToPreload, null, null);
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
        preloadChoicesOperationEmptyCapability = new PreloadPresentChoicesOperation(internalInterface, fileManager, null, windowCapability, true, cellsToPreload, null, null);
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


    @Test
    public void testGetLayoutMode() {
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(7, 1));
        // First we will check knowing our keyboard listener is NOT NULL
        WindowCapability windowCapability = new WindowCapability();
        HashSet<ChoiceCell> loadedCells = new HashSet<>();
        presentChoicesOperation = new PreloadPresentChoicesOperation(internalInterface, fileManager, choiceSet, InteractionMode.MANUAL_ONLY, getKeyBoardProperties(), keyboardListener, TestValues.GENERAL_INTEGER, null, windowCapability, true, loadedCells, null);

        assertEquals(presentChoicesOperation.getLayoutMode(), LayoutMode.LIST_WITH_SEARCH);
        presentChoicesOperation.keyboardListener = null;
        assertEquals(presentChoicesOperation.getLayoutMode(), LayoutMode.LIST_ONLY);
    }

    @Test
    public void testGetPerformInteraction() {
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(7, 1));
        WindowCapability windowCapability = new WindowCapability();
        HashSet<ChoiceCell> loadedCells = new HashSet<>();
        presentChoicesOperation = new PreloadPresentChoicesOperation(internalInterface, fileManager, choiceSet, InteractionMode.MANUAL_ONLY, getKeyBoardProperties(), keyboardListener, TestValues.GENERAL_INTEGER, null, windowCapability, true, loadedCells, null);

        PerformInteraction pi = presentChoicesOperation.getPerformInteraction();
        assertEquals(pi.getInitialText(), "Test");
        assertNull(pi.getHelpPrompt());
        assertNull(pi.getTimeoutPrompt());
        assertNull(pi.getVrHelp());
        assertEquals(pi.getTimeout(), Integer.valueOf(10000));
        assertEquals(pi.getCancelID(), TestValues.GENERAL_INTEGER);
        assertEquals(presentChoicesOperation.getLayoutMode(), LayoutMode.LIST_WITH_SEARCH);
    }

    @Test
    public void testSetSelectedCellWithId() {
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(7, 1));
        WindowCapability windowCapability = new WindowCapability();
        HashSet<ChoiceCell> loadedCells = new HashSet<>();
        presentChoicesOperation = new PreloadPresentChoicesOperation(internalInterface, fileManager, choiceSet, InteractionMode.MANUAL_ONLY, getKeyBoardProperties(), keyboardListener, TestValues.GENERAL_INTEGER, null, windowCapability, true, loadedCells, null);

        assertNull(presentChoicesOperation.selectedCellRow);
        presentChoicesOperation.setSelectedCellWithId(0);
        assertEquals(presentChoicesOperation.selectedCellRow, Integer.valueOf(0));
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCancelingChoiceSetSuccessfullyIfThreadIsRunning() {
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
        WindowCapability windowCapability = new WindowCapability();
        HashSet<ChoiceCell> loadedCells = new HashSet<>();
        PreloadChoicesCompletionListener listener = new PreloadChoicesCompletionListener() {
            @Override
            public void onComplete(boolean success, HashSet<ChoiceCell> loadedChoiceCells) {
                choiceSet.cancel();
                sleep();

                verify(internalInterface, times(1)).sendRPC(any(CancelInteraction.class));
                verify(internalInterface, times(1)).sendRPC(any(PerformInteraction.class));

                assertEquals(Task.IN_PROGRESS, presentChoicesOperation.getState());
            }
        };
        presentChoicesOperation = new PreloadPresentChoicesOperation(internalInterface, fileManager, choiceSet, InteractionMode.MANUAL_ONLY, null, null, TestValues.GENERAL_INTEGER, null, windowCapability, true, loadedCells, listener);
        presentChoicesOperation.setLoadedCells(new HashSet<ChoiceCell>());
        queue.add(presentChoicesOperation, false);

        sleep();

        assertEquals(Task.IN_PROGRESS, presentChoicesOperation.getState());

        Answer<Void> cancelInteractionAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                CancelInteraction cancelInteraction = (CancelInteraction) args[0];

                assertEquals(cancelInteraction.getCancelID(), TestValues.GENERAL_INTEGER);
                assertEquals(cancelInteraction.getInteractionFunctionID().intValue(), FunctionID.PERFORM_INTERACTION.getId());

                RPCResponse response = new RPCResponse(FunctionID.CANCEL_INTERACTION.toString());
                response.setSuccess(true);
                cancelInteraction.getOnRPCResponseListener().onResponse(0, response);

                return null;
            }
        };
        doAnswer(cancelInteractionAnswer).when(internalInterface).sendRPC(any(CancelInteraction.class));
    }

    @Test
    public void testCancelingChoiceSetUnsuccessfullyIfThreadIsRunning() {
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
        WindowCapability windowCapability = new WindowCapability();
        HashSet<ChoiceCell> loadedCells = new HashSet<>();
        PreloadChoicesCompletionListener listener = new PreloadChoicesCompletionListener() {
            @Override
            public void onComplete(boolean success, HashSet<ChoiceCell> loadedChoiceCells) {
                choiceSet.cancel();
                sleep();

                verify(internalInterface, times(1)).sendRPC(any(CancelInteraction.class));
                verify(internalInterface, times(1)).sendRPC(any(PerformInteraction.class));

                assertEquals(Task.IN_PROGRESS, presentChoicesOperation.getState());
            }
        };
        presentChoicesOperation = new PreloadPresentChoicesOperation(internalInterface, fileManager, choiceSet, InteractionMode.MANUAL_ONLY, null, null, TestValues.GENERAL_INTEGER, null, windowCapability, true, loadedCells, listener);
        presentChoicesOperation.setLoadedCells(new HashSet<ChoiceCell>());
        queue.add(presentChoicesOperation, false);
        sleep();

        assertEquals(Task.IN_PROGRESS, presentChoicesOperation.getState());

        Answer<Void> cancelInteractionAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                CancelInteraction cancelInteraction = (CancelInteraction) args[0];

                assertEquals(cancelInteraction.getCancelID(), TestValues.GENERAL_INTEGER);
                assertEquals(cancelInteraction.getInteractionFunctionID().intValue(), FunctionID.PERFORM_INTERACTION.getId());

                RPCResponse response = new RPCResponse(FunctionID.CANCEL_INTERACTION.toString());
                response.setSuccess(false);
                cancelInteraction.getOnRPCResponseListener().onResponse(0, response);

                return null;
            }
        };
        doAnswer(cancelInteractionAnswer).when(internalInterface).sendRPC(any(CancelInteraction.class));
    }

    @Test
    public void testCancelingChoiceSetIfThreadHasFinished() {
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
        WindowCapability windowCapability = new WindowCapability();
        HashSet<ChoiceCell> loadedCells = new HashSet<>();
        presentChoicesOperation = new PreloadPresentChoicesOperation(internalInterface, fileManager, choiceSet, InteractionMode.MANUAL_ONLY, null, null, TestValues.GENERAL_INTEGER,null, windowCapability, true, loadedCells, null);
        presentChoicesOperation.finishOperation();

        assertEquals(Task.FINISHED, presentChoicesOperation.getState());

        choiceSet.cancel();
        verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));

        assertEquals(Task.FINISHED, presentChoicesOperation.getState());
    }

    @Test
    public void testCancelingChoiceSetIfThreadHasNotYetRun() {
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
        WindowCapability windowCapability = new WindowCapability();
        HashSet<ChoiceCell> loadedCells = new HashSet<>();
        presentChoicesOperation = new PreloadPresentChoicesOperation(internalInterface, fileManager, choiceSet, InteractionMode.MANUAL_ONLY, null, null, TestValues.GENERAL_INTEGER, null, windowCapability, true, loadedCells, null);

        assertEquals(Task.BLOCKED, presentChoicesOperation.getState());

        choiceSet.cancel();

        // Once the operation has started
        queue.add(presentChoicesOperation, false);
        sleep();

        assertEquals(Task.CANCELED, presentChoicesOperation.getState());

        // Make sure neither a `CancelInteraction` or `PerformInteraction` RPC is ever sent
        verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
        verify(internalInterface, never()).sendRPC(any(PerformInteraction.class));
    }

    @Test
    public void testCancelingChoiceSetIfHeadUnitDoesNotSupportFeature() {
        // Cancel Interaction is only supported on RPC specs v.6.0.0+
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(5, 3));
        WindowCapability windowCapability = new WindowCapability();
        HashSet<ChoiceCell> loadedCells = new HashSet<>();
        PreloadChoicesCompletionListener listener = new PreloadChoicesCompletionListener() {
            @Override
            public void onComplete(boolean success, HashSet<ChoiceCell> loadedChoiceCells) {
                choiceSet.cancel();
                sleep();

                assertEquals(Task.IN_PROGRESS, presentChoicesOperation.getState());

                verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
                verify(internalInterface, times(1)).sendRPC(any(PerformInteraction.class));
            }
        };
        presentChoicesOperation = new PreloadPresentChoicesOperation(internalInterface, fileManager, choiceSet, InteractionMode.MANUAL_ONLY, null, null, TestValues.GENERAL_INTEGER, null, windowCapability, true, loadedCells, listener);
        presentChoicesOperation.setLoadedCells(new HashSet<ChoiceCell>());
        queue.add(presentChoicesOperation, false);
    }

    @Test
    public void testCancelingChoiceSetIfHeadUnitDoesNotSupportFeatureButThreadIsNotRunning() {
        // Cancel Interaction is only supported on RPC specs v.6.0.0+
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(5, 3));
        WindowCapability windowCapability = new WindowCapability();
        HashSet<ChoiceCell> loadedCells = new HashSet<>();
        presentChoicesOperation = new PreloadPresentChoicesOperation(internalInterface, fileManager, choiceSet, InteractionMode.MANUAL_ONLY, null, null, TestValues.GENERAL_INTEGER, null, windowCapability, true, loadedCells, null);

        assertEquals(Task.BLOCKED, presentChoicesOperation.getState());

        choiceSet.cancel();

        verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));

        // Once the operation has started
        queue.add(presentChoicesOperation, false);
        sleep();

        assertEquals(Task.CANCELED, presentChoicesOperation.getState());

        // Make sure neither a `CancelInteraction` or `PerformInteraction` RPC is ever sent
        verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
        verify(internalInterface, never()).sendRPC(any(PerformInteraction.class));
    }
}
