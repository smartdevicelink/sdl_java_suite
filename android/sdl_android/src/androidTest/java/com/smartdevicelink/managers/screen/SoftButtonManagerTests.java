package com.smartdevicelink.managers.screen;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.StaticIconName;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.test.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link SoftButtonManager}
 */
@RunWith(AndroidJUnit4.class)
public class SoftButtonManagerTests {

    private SoftButtonManager softButtonManager;
    private int fileManagerUploadArtworksListenerCalledCounter;
    private int internalInterfaceSendRPCListenerCalledCounter;
    private int softButtonObject1Id = 1000, softButtonObject2Id = 2000;
    private SoftButtonObject softButtonObject1, softButtonObject2;
    private SoftButtonState softButtonState1, softButtonState2, softButtonState3, softButtonState4;


    @Before
    public void setUp() throws Exception {

        // When internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, OnRPCNotificationListener) is called
        // inside SoftButtonManager, respond with a fake HMILevel.HMI_FULL response to let the SoftButtonManager continue working.
        ISdl internalInterface = mock(ISdl.class);
        Answer<Void> onHMIStatusAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                OnRPCNotificationListener onHMIStatusListener = (OnRPCNotificationListener) args[1];
                OnHMIStatus onHMIStatusFakeNotification = new OnHMIStatus();
                onHMIStatusFakeNotification.setHmiLevel(HMILevel.HMI_FULL);
                onHMIStatusListener.onNotified(onHMIStatusFakeNotification);
                return null;
            }
        };
        doAnswer(onHMIStatusAnswer).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCNotificationListener.class));


        // When internalInterface.getSystemCapabilityManager().addOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onSystemCapabilityListener) is called
        // inside SoftButtonManager, respond with a fake response to let the SoftButtonManager continue working.
        Answer<Void> onSystemCapabilityAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                OnSystemCapabilityListener onSystemCapabilityListener = (OnSystemCapabilityListener) args[1];
                SoftButtonCapabilities softButtonCapabilities = new SoftButtonCapabilities();
                softButtonCapabilities.setImageSupported(true);
                softButtonCapabilities.setTextSupported(true);
                WindowCapability windowCapability = new WindowCapability();
                windowCapability.setSoftButtonCapabilities(Collections.singletonList(softButtonCapabilities));
                DisplayCapability displayCapability = new DisplayCapability();
                displayCapability.setWindowCapabilities(Collections.singletonList(windowCapability));
                List<DisplayCapability> capabilities = Collections.singletonList(displayCapability);
                onSystemCapabilityListener.onCapabilityRetrieved(capabilities);
                return null;
            }
        };
        SystemCapabilityManager systemCapabilityManager = mock(SystemCapabilityManager.class);
        doAnswer(onSystemCapabilityAnswer).when(systemCapabilityManager).addOnSystemCapabilityListener(eq(SystemCapabilityType.DISPLAYS), any(OnSystemCapabilityListener.class));
        doReturn(systemCapabilityManager).when(internalInterface).getSystemCapabilityManager();

        // When fileManager.uploadArtworks() is called inside the SoftButtonManager, respond with
        // a fake onComplete() callback to let the SoftButtonManager continue working.
        FileManager fileManager = mock(FileManager.class);
        Answer<Void> onFileManagerUploadAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                fileManagerUploadArtworksListenerCalledCounter++;
                Object[] args = invocation.getArguments();
                MultipleFileCompletionListener multipleFileCompletionListener = (MultipleFileCompletionListener) args[1];
                multipleFileCompletionListener.onComplete(null);
                return null;
            }
        };
        doAnswer(onFileManagerUploadAnswer).when(fileManager).uploadArtworks(any(List.class), any(MultipleFileCompletionListener.class));


        // We still want the mock fileManager to use the real implementation for fileNeedsUpload()
        when(fileManager.fileNeedsUpload(any(SdlFile.class))).thenCallRealMethod();


        // Create softButtonManager
        Taskmaster taskmaster = new Taskmaster.Builder().build();
        taskmaster.start();
        when(internalInterface.getTaskmaster()).thenReturn(taskmaster);
        softButtonManager = new SoftButtonManager(internalInterface, fileManager);
        softButtonManager.isDynamicGraphicSupported = true;


        // When internalInterface.sendRPC() is called inside SoftButtonManager:
        // 1) respond with a fake onResponse() callback to let the SoftButtonManager continue working
        // 2) assert that the Show RPC values (ie: MainField1 & SoftButtons) that are created by the SoftButtonManager, match the ones that are provided by the developer
        Answer<Void> onSendShowRPCAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                internalInterfaceSendRPCListenerCalledCounter++;
                Object[] args = invocation.getArguments();
                Show show = (Show) args[0];

                show.getOnRPCResponseListener().onResponse(0, new ShowResponse(true, Result.SUCCESS));

                assertEquals(show.getMainField1(), softButtonManager.getCurrentMainField1());
                assertEquals(show.getSoftButtons().size(), softButtonManager.getSoftButtonObjects().size());

                return null;
            }
        };
        doAnswer(onSendShowRPCAnswer).when(internalInterface).sendRPC(any(Show.class));


        // Create soft button objects
        softButtonState1 = new SoftButtonState("object1-state1", "o1s1", new SdlArtwork("image1", FileType.GRAPHIC_PNG, 1, true));
        softButtonState2 = new SoftButtonState("object1-state2", "o1s2", new SdlArtwork(StaticIconName.ALBUM));
        softButtonObject1 = new SoftButtonObject("object1", Arrays.asList(softButtonState1, softButtonState2), softButtonState1.getName(), null);
        softButtonObject1.setButtonId(softButtonObject1Id);
        softButtonState3 = new SoftButtonState("object2-state1", "o2s1", null);
        softButtonState4 = new SoftButtonState("object2-state2", "o2s2", new SdlArtwork("image3", FileType.GRAPHIC_PNG, 3, true));
        softButtonObject2 = new SoftButtonObject("object2", Arrays.asList(softButtonState3, softButtonState4), softButtonState3.getName(), null);
        softButtonObject2.setButtonId(softButtonObject2Id);
    }


    private void sleep() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSoftButtonManagerUpdate() {
        // Reset the boolean variables
        fileManagerUploadArtworksListenerCalledCounter = 0;
        internalInterfaceSendRPCListenerCalledCounter = 0;


        // Test batch update
        softButtonManager.setBatchUpdates(true);
        List<SoftButtonObject> softButtonObjects = Arrays.asList(softButtonObject1, softButtonObject2);
        softButtonManager.setSoftButtonObjects(Arrays.asList(softButtonObject1, softButtonObject2));
        softButtonManager.setBatchUpdates(false);


        // Test single update, setCurrentMainField1, and transitionToNextState
        softButtonManager.setCurrentMainField1("It is Wednesday my dudes!");
        softButtonObject1.transitionToNextState();


        // Sleep to give time to Taskmaster to run the operations
        sleep();


        // Check that everything got called as expected
        assertEquals("FileManager.uploadArtworks() did not get called correctly", 1, fileManagerUploadArtworksListenerCalledCounter);
        assertEquals("InternalInterface.sendRPC() did not get called correctly", 2, internalInterfaceSendRPCListenerCalledCounter);


        // Test getSoftButtonObjects
        assertEquals("Returned softButtonObjects value doesn't match the expected value", softButtonObjects, softButtonManager.getSoftButtonObjects());
    }

    @Test
    public void testSoftButtonManagerGetSoftButtonObject() {
        softButtonManager.setSoftButtonObjects(Arrays.asList(softButtonObject1, softButtonObject2));


        // Test get by valid name
        assertEquals("Returned SoftButtonObject doesn't match the expected value", softButtonObject2, softButtonManager.getSoftButtonObjectByName("object2"));


        // Test get by invalid name
        assertNull("Returned SoftButtonObject doesn't match the expected value", softButtonManager.getSoftButtonObjectByName("object300"));


        // Test get by valid id
        assertEquals("Returned SoftButtonObject doesn't match the expected value", softButtonObject2, softButtonManager.getSoftButtonObjectById(softButtonObject2Id));


        // Test get by invalid id
        assertNull("Returned SoftButtonObject doesn't match the expected value", softButtonManager.getSoftButtonObjectById(5555));
    }

    @Test
    public void testSoftButtonState() {
        // Test SoftButtonState.getName()
        String nameExpectedValue = "object1-state1";
        assertEquals("Returned state name doesn't match the expected value", nameExpectedValue, softButtonState1.getName());


        // Test SoftButtonState.getArtwork()
        SdlArtwork artworkExpectedValue = new SdlArtwork("image1", FileType.GRAPHIC_PNG, 1, true);
        assertTrue("Returned SdlArtwork doesn't match the expected value", Validator.validateSdlFile(artworkExpectedValue, softButtonState1.getArtwork()));
        SdlArtwork artworkExpectedValue2 = new SdlArtwork(StaticIconName.ALBUM);
        assertTrue("Returned SdlArtwork doesn't match the expected value", Validator.validateSdlFile(artworkExpectedValue2, softButtonState2.getArtwork()));


        // Test SoftButtonState.getSoftButton()
        SoftButton softButtonExpectedValue = new SoftButton(SoftButtonType.SBT_BOTH, SoftButtonObject.SOFT_BUTTON_ID_NOT_SET_VALUE);
        softButtonExpectedValue.setText("o1s1");
        softButtonExpectedValue.setImage(new Image(artworkExpectedValue.getName(), ImageType.DYNAMIC));
        softButtonExpectedValue.setSystemAction(SystemAction.DEFAULT_ACTION);
        SoftButton actual = softButtonState1.getSoftButton();
        assertTrue("Returned SoftButton doesn't match the expected value", Validator.validateSoftButton(softButtonExpectedValue, actual));
    }

    @Test
    public void testSoftButtonObject() {
        // Test SoftButtonObject.getName()
        assertEquals("Returned object name doesn't match the expected value", "object1", softButtonObject1.getName());


        // Test SoftButtonObject.getCurrentState()
        assertEquals("Returned current state doesn't match the expected value", softButtonState1, softButtonObject1.getCurrentState());


        // Test SoftButtonObject.getCurrentStateName()
        assertEquals("Returned current state name doesn't match the expected value", softButtonState1.getName(), softButtonObject1.getCurrentStateName());


        // Test SoftButtonObject.getButtonId()
        assertEquals("Returned button Id doesn't match the expected value", softButtonObject1Id, softButtonObject1.getButtonId());


        // Test SoftButtonObject.getCurrentStateSoftButton()
        SoftButton softButtonExpectedValue = new SoftButton(SoftButtonType.SBT_TEXT, softButtonObject2Id);
        softButtonExpectedValue.setText("o2s1");
        softButtonExpectedValue.setSystemAction(SystemAction.DEFAULT_ACTION);
        assertTrue("Returned current state SoftButton doesn't match the expected value", Validator.validateSoftButton(softButtonExpectedValue, softButtonObject2.getCurrentStateSoftButton()));


        // Test SoftButtonObject.getStates()
        assertEquals("Returned object states doesn't match the expected value", Arrays.asList(softButtonState1, softButtonState2), softButtonObject1.getStates());


        // Test SoftButtonObject.transitionToNextState()
        assertEquals(softButtonState1, softButtonObject1.getCurrentState());
        softButtonObject1.transitionToNextState();
        assertEquals(softButtonState2, softButtonObject1.getCurrentState());


        // Test SoftButtonObject.transitionToStateByName() - transitioning to a none existing state
        boolean success = softButtonObject1.transitionToStateByName("none existing name");
        assertFalse(success);


        // Test SoftButtonObject.transitionToStateByName() - transitioning to an existing state
        success = softButtonObject1.transitionToStateByName("object1-state1");
        assertTrue(success);
        assertEquals(softButtonState1, softButtonObject1.getCurrentState());
    }

    /**
     * Test custom overridden softButtonObject equals method
     */
    @Test
    public void testSoftButtonObjectEquals() {
        SoftButtonObject softButtonObject1;
        SoftButtonObject softButtonObject2;

        SoftButtonObject.OnEventListener testOnEventList1 = new SoftButtonObject.OnEventListener() {
            @Override
            public void onPress(SoftButtonObject softButtonObject, OnButtonPress onButtonPress) {
            }

            @Override
            public void onEvent(SoftButtonObject softButtonObject, OnButtonEvent onButtonEvent) {
            }
        };

        SoftButtonObject.OnEventListener testOnEventList2 = new SoftButtonObject.OnEventListener() {
            @Override
            public void onPress(SoftButtonObject softButtonObject, OnButtonPress onButtonPress) {
            }

            @Override
            public void onEvent(SoftButtonObject softButtonObject, OnButtonEvent onButtonEvent) {
            }
        };

        // Case 1: object is null, assertFalse
        softButtonObject1 = new SoftButtonObject("test", softButtonState1, null);
        softButtonObject2 = null;
        assertNotEquals(softButtonObject1, softButtonObject2);

        // Case 2 SoftButtonObjects are the same, assertTrue
        assertEquals(softButtonObject1, softButtonObject1);

        // Case 3: object is not an instance of SoftButtonObject assertFalse
        SdlArtwork artwork = new SdlArtwork("image1", FileType.GRAPHIC_PNG, 1, true);
        assertNotEquals(softButtonObject1, artwork);

        // Case 4: SoftButtonObjectState List are not same size, assertFalse
        List<SoftButtonState> softButtonStateList = new ArrayList<>();
        List<SoftButtonState> softButtonStateList2 = new ArrayList<>();
        softButtonStateList.add(softButtonState1);
        softButtonStateList2.add(softButtonState1);
        softButtonStateList2.add(softButtonState2);
        softButtonObject1 = new SoftButtonObject("hi", softButtonStateList, softButtonStateList.get(0).getName(), null);
        softButtonObject2 = new SoftButtonObject("hi", softButtonStateList2, softButtonStateList2.get(0).getName(), null);
        assertNotEquals(softButtonObject1, softButtonObject2);

        // Case 5: SoftButtonStates are not the same, assertFalse
        softButtonObject1 = new SoftButtonObject("test", softButtonState1, null);
        softButtonObject2 = new SoftButtonObject("test", softButtonState2, null);
        assertNotEquals(softButtonObject1, softButtonObject2);

        // Case 6: SoftButtonObject names are not same, assertFalse
        softButtonObject1 = new SoftButtonObject("test", softButtonState1, null);
        softButtonObject2 = new SoftButtonObject("test23123", softButtonState1, null);
        assertNotEquals(softButtonObject1, softButtonObject2);

        // Case 7: SoftButtonObject currentStateName not same, assertFalse
        softButtonObject1 = new SoftButtonObject("hi", softButtonStateList2, softButtonStateList2.get(0).getName(), null);
        softButtonObject2 = new SoftButtonObject("hi", softButtonStateList2, softButtonStateList2.get(1).getName(), null);
        assertNotEquals(softButtonObject1, softButtonObject2);
    }

    /**
     * Test custom overridden softButtonState equals method
     */
    @Test
    public void testSoftButtonStateEquals() {
        assertNotEquals(softButtonState1, softButtonState2);
        SdlArtwork artwork1 = new SdlArtwork("image1", FileType.GRAPHIC_PNG, 1, true);
        SdlArtwork artwork2 = new SdlArtwork("image2", FileType.GRAPHIC_PNG, 1, true);

        // Case 1: object is null, assertFalse
        softButtonState1 = new SoftButtonState("object1-state1", "o1s1", artwork1);
        softButtonState2 = null;
        assertNotEquals(softButtonState1, softButtonState2);

        // Case 2 SoftButtonObjects are the same, assertTrue
        assertEquals(softButtonState1, softButtonState1);

        // Case 3: object is not an instance of SoftButtonState, assertFalse
        assertNotEquals(softButtonState1, artwork1);

        // Case 4: different artwork, assertFalse
        softButtonState2 = new SoftButtonState("object1-state1", "o1s1", artwork2);
        assertNotEquals(softButtonState1, softButtonState2);

        // Case 5: different name, assertFalse
        softButtonState2 = new SoftButtonState("object1-state1 different name", "o1s1", artwork1);
        assertNotEquals(softButtonState1, softButtonState2);

        // Case 6 they are equal, assertTrue
        softButtonState2 = new SoftButtonState("object1-state1", "o1s1", artwork1);
        assertEquals(softButtonState1, softButtonState2);
    }

    /**
     * Test constructing SoftButtonObject with an empty state list
     */
    @Test
    public void testConstructSoftButtonObjectWithEmptyStateList() {
        List<SoftButtonState> stateList = new ArrayList<>();
        SoftButtonObject softButtonObject = new SoftButtonObject("hello_there", stateList, "general_kenobi", null);
        assertNull(softButtonObject.getStates());
    }

    /**
     * Test constructing SoftButtonObject with an nonempty state list
     */
    @Test
    public void testConstructSoftButtonObjectWithNonEmptyStateList() {
        List<SoftButtonState> stateList = new ArrayList<>();
        SoftButtonState softButtonState = new SoftButtonState("general_kenobi", "General Kenobi", null);
        stateList.add(softButtonState);
        SoftButtonObject softButtonObject = new SoftButtonObject("hello_there", stateList, "general_kenobi", null);
        assertEquals(stateList, softButtonObject.getStates());
    }

    /**
     * Test constructing SoftButtonObject with an invalid initialStateName
     */
    @Test
    public void testConstructSoftButtonObjectWithInvalidInitialStateName() {
        List<SoftButtonState> stateList = new ArrayList<>();
        SoftButtonState softButtonState = new SoftButtonState("general_kenobi", "General Kenobi", null);
        stateList.add(softButtonState);
        SoftButtonObject softButtonObject = new SoftButtonObject("hello_there", stateList, "hello_there", null);
        assertNull(softButtonObject.getStates());
    }

    /**
     * Test assigning an empty state list to existing SoftButtonObject
     */
    @Test
    public void testAssignEmptyStateListToSoftButtonObject() {
        List<SoftButtonState> nonEmptyStateList = new ArrayList<>();
        List<SoftButtonState> emptyStateList = new ArrayList<>();
        SoftButtonState softButtonState = new SoftButtonState("general_kenobi", "General Kenobi", null);
        nonEmptyStateList.add(softButtonState);

        SoftButtonObject softButtonObject = new SoftButtonObject("hello_there", nonEmptyStateList, "general_kenobi", null);

        softButtonObject.setStates(emptyStateList);
        assertEquals(nonEmptyStateList, softButtonObject.getStates());
    }

    /**
     * Test assigning a state list with the current state to existing SoftButtonObject
     */
    @Test
    public void testAssignStateListWithCurrentStateToSoftButtonObject() {
        List<SoftButtonState> stateList1 = new ArrayList<>();
        SoftButtonState softButtonState1 = new SoftButtonState("hello_there", "Hello there", null);
        stateList1.add(softButtonState1);

        List<SoftButtonState> stateList2 = new ArrayList<>();
        SoftButtonState softButtonState2 = new SoftButtonState("general_kenobi", "General Kenobi", null);
        stateList2.add(softButtonState1);
        stateList2.add(softButtonState2);

        SoftButtonObject softButtonObject = new SoftButtonObject("general_kenobi", stateList1, "hello_there", null);

        softButtonObject.setStates(stateList2);

        assertEquals(stateList2, softButtonObject.getStates());
    }

    /**
     * Test assigning a state list without the current state to existing SoftButtonObject
     */
    @Test
    public void testAssignStateListWithoutCurrentStateToSoftButtonObject() {
        List<SoftButtonState> stateList1 = new ArrayList<>();
        SoftButtonState softButtonState1 = new SoftButtonState("hello_there", "Hello there", null);
        stateList1.add(softButtonState1);

        List<SoftButtonState> stateList2 = new ArrayList<>();
        SoftButtonState softButtonState2 = new SoftButtonState("general_kenobi", "General Kenobi", null);
        stateList2.add(softButtonState2);

        SoftButtonObject softButtonObject = new SoftButtonObject("general_kenobi", stateList1, "hello_there", null);

        softButtonObject.setStates(stateList2);

        assertEquals(stateList2, softButtonObject.getStates());
    }

    /**
     * Test assigning a state list with states that have the same name to existing SoftButtonObject
     */
    @Test
    public void testAssignSameNameStateListToSoftButtonObject() {
        List<SoftButtonState> stateListUnique = new ArrayList<>();
        SoftButtonState softButtonState1 = new SoftButtonState("hello_there", "Hello there", null);
        stateListUnique.add(softButtonState1);

        List<SoftButtonState> stateListDuplicateNames = new ArrayList<>();
        SoftButtonState softButtonState2 = new SoftButtonState("general_kenobi", "General Kenobi", null);
        stateListDuplicateNames.add(softButtonState2);
        SoftButtonState softButtonState3 = new SoftButtonState("general_kenobi", "General Kenobi Again", null);
        stateListDuplicateNames.add(softButtonState3);

        SoftButtonObject softButtonObject = new SoftButtonObject("general_kenobi", stateListUnique, "hello_there", null);

        softButtonObject.setStates(stateListDuplicateNames);

        assertEquals(stateListUnique, softButtonObject.getStates());
    }

    @Test
    public void testSoftButtonManagerGraphicNotSupported() {
        softButtonManager.isDynamicGraphicSupported = false;
        fileManagerUploadArtworksListenerCalledCounter = 0;
        internalInterfaceSendRPCListenerCalledCounter = 0;

        softButtonManager.setSoftButtonObjects(Arrays.asList(softButtonObject1, softButtonObject2));
        assertEquals("SoftButtonManager is uploading artwork, when graphic is not supported", 0, fileManagerUploadArtworksListenerCalledCounter);
    }

    @Test
    public void testSoftButtonManagerDynamicImageNotSupportedNoText() {
        softButtonManager.isDynamicGraphicSupported = false;
        fileManagerUploadArtworksListenerCalledCounter = 0;
        internalInterfaceSendRPCListenerCalledCounter = 0;

        SoftButtonState softButtonState = new SoftButtonState("testState", null, new SdlArtwork("image", FileType.GRAPHIC_PNG, 1, true));
        SoftButtonObject softButtonObject = new SoftButtonObject("obj1", softButtonState, null);

        softButtonManager.setSoftButtonObjects(Arrays.asList(softButtonObject));
        assertEquals("SoftButtonManager is uploading artwork, when graphic is not supported", 0, fileManagerUploadArtworksListenerCalledCounter);
    }
}
