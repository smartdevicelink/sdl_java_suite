package com.smartdevicelink.managers.screen;


import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.StaticIconName;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.test.Validator;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link SoftButtonManager}
 */
public class SoftButtonManagerTests extends AndroidTestCase2 {

    private SoftButtonManager softButtonManager;
    private boolean fileManagerUploadArtworksGotCalled;
    private boolean internalInterfaceSendRPCRequestGotCalled;
    private boolean softButtonMangerUpdateCompleted;
    private SoftButtonObject softButtonObject1, softButtonObject2;
    private SoftButtonState softButtonState1, softButtonState2, softButtonState3, softButtonState4;


    @Override
    public void setUp() throws Exception {
        super.setUp();

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


        // When fileManager.uploadArtworks() is called inside the SoftButtonManager, respond with
        // a fake onComplete() callback to let the SoftButtonManager continue working.
        FileManager fileManager = mock(FileManager.class);
        Answer<Void> onFileManagerUploadAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                fileManagerUploadArtworksGotCalled = true;
                Object[] args = invocation.getArguments();
                MultipleFileCompletionListener multipleFileCompletionListener = (MultipleFileCompletionListener) args[1];
                multipleFileCompletionListener.onComplete(null);
                return null;
            }
        };
        doAnswer(onFileManagerUploadAnswer).when(fileManager).uploadArtworks(any(List.class), any(MultipleFileCompletionListener.class));


        // Create softButtonManager
        softButtonManager = new SoftButtonManager(internalInterface, fileManager);


        // When internalInterface.sendRPCRequest() is called inside SoftButtonManager:
        // 1) respond with a fake onResponse() callback to let the SoftButtonManager continue working
        // 2) assert that the Show RPC values (ie: MainField1 & SoftButtons) that are created by the SoftButtonManager, match the ones that are provided by the developer
        Answer<Void> onSendShowRPCAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                internalInterfaceSendRPCRequestGotCalled = true;
                Object[] args = invocation.getArguments();
                Show show = (Show) args[0];

                show.getOnRPCResponseListener().onResponse(0, null);

                assertEquals(show.getMainField1(), softButtonManager.getCurrentMainField1());
                assertEquals(show.getSoftButtons().size(), softButtonManager.createSoftButtonsForCurrentState().size());

                return null;
            }
        };
        doAnswer(onSendShowRPCAnswer).when(internalInterface).sendRPCRequest(any(Show.class));


        // Create soft button objects
        softButtonState1 = new SoftButtonState("object1-state1", "o1s1", new SdlArtwork("image1", FileType.GRAPHIC_PNG, 1, true));
        softButtonState2 = new SoftButtonState("object1-state2", "o1s2", new SdlArtwork(StaticIconName.ALBUM));
        softButtonObject1 = new SoftButtonObject("object1", Arrays.asList(softButtonState1, softButtonState2), softButtonState1.getName(), null);
        softButtonState3 = new SoftButtonState("object2-state1", "o2s1", null);
        softButtonState4 = new SoftButtonState("object2-state2", "o2s2", new SdlArtwork("image3", FileType.GRAPHIC_PNG, 3, true));
        softButtonObject2 = new SoftButtonObject("object2", Arrays.asList(softButtonState3, softButtonState4), softButtonState3.getName(), null);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSoftButtonManagerUpdate() {
        // Reset the boolean variables
        fileManagerUploadArtworksGotCalled = false;
        internalInterfaceSendRPCRequestGotCalled = false;
        softButtonMangerUpdateCompleted = false;


        // Test batch update
        softButtonManager.setBatchUpdates(true);
        List<SoftButtonObject> softButtonObjects = Arrays.asList(softButtonObject1, softButtonObject2);
        softButtonManager.setSoftButtonObjects(Arrays.asList(softButtonObject1, softButtonObject2));
        softButtonManager.setBatchUpdates(false);
        softButtonManager.update(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                softButtonMangerUpdateCompleted = true;
            }
        });


        // Test single update, setCurrentMainField1, and transitionToNextState
        softButtonManager.setCurrentMainField1("It is Wednesday my dudes!");
        softButtonObject1.transitionToNextState();


        // Check that everything got called as expected
        assertTrue("FileManager.uploadArtworks() did not get called", fileManagerUploadArtworksGotCalled);
        assertTrue("InternalInterface.sendRPCRequest() did not get called", internalInterfaceSendRPCRequestGotCalled);
        assertTrue("SoftButtonManger update onComplete() did not get called", softButtonMangerUpdateCompleted);


        // Test getSoftButtonObjects
        assertEquals("Returned softButtonObjects value doesn't match the expected value", softButtonObjects, softButtonManager.getSoftButtonObjects());
    }

    public void testSoftButtonManagerGetSoftButtonObject() {
        softButtonManager.setSoftButtonObjects(Arrays.asList(softButtonObject1, softButtonObject2));


        // Test get by valid name
        assertEquals("Returned SoftButtonObject doesn't match the expected value", softButtonObject2, softButtonManager.getSoftButtonObjectByName("object2"));


        // Test get by invalid name
        assertNull("Returned SoftButtonObject doesn't match the expected value", softButtonManager.getSoftButtonObjectByName("object300"));


        // Test get by valid id
        assertEquals("Returned SoftButtonObject doesn't match the expected value", softButtonObject2, softButtonManager.getSoftButtonObjectById(100));


        // Test get by invalid id
        assertNull("Returned SoftButtonObject doesn't match the expected value", softButtonManager.getSoftButtonObjectById(500));
    }

    public void testSoftButtonState(){
        // Test SoftButtonState.getName()
        String nameExpectedValue = "object1-state1";
        assertEquals("Returned state name doesn't match the expected value", nameExpectedValue, softButtonState1.getName());


        // Test SoftButtonState.getArtwork()
        SdlArtwork artworkExpectedValue = new SdlArtwork("image1", FileType.GRAPHIC_PNG, 1, true);
        assertTrue("Returned SdlArtwork doesn't match the expected value", Validator.validateSdlFile(artworkExpectedValue, softButtonState1.getArtwork()));
        SdlArtwork artworkExpectedValue2 = new SdlArtwork(StaticIconName.ALBUM);
        assertTrue("Returned SdlArtwork doesn't match the expected value", Validator.validateSdlFile(artworkExpectedValue2, softButtonState2.getArtwork()));


        // Test SoftButtonState.getSoftButton()
        SoftButton softButtonExpectedValue = new SoftButton(SoftButtonType.SBT_BOTH, 0);
        softButtonExpectedValue.setText("o1s1");
        softButtonExpectedValue.setImage(new Image(artworkExpectedValue.getName(), ImageType.DYNAMIC));
        assertTrue("Returned SoftButton doesn't match the expected value", Validator.validateSoftButton(softButtonExpectedValue, softButtonState1.getSoftButton()));
    }

    public void testSoftButtonObject(){
        // Test SoftButtonObject.getName()
        assertEquals("Returned object name doesn't match the expected value", "object1", softButtonObject1.getName());


        // Test SoftButtonObject.getCurrentState()
        assertEquals("Returned current state doesn't match the expected value", softButtonState1, softButtonObject1.getCurrentState());


        // Test SoftButtonObject.getCurrentStateName()
        assertEquals("Returned current state name doesn't match the expected value", softButtonState1.getName(), softButtonObject1.getCurrentStateName());


        // Test SoftButtonObject.getButtonId()
        assertEquals("Returned button Id doesn't match the expected value", 0, softButtonObject1.getButtonId());


        // Test SoftButtonObject.getCurrentStateSoftButton()
        SoftButton softButtonExpectedValue = new SoftButton(SoftButtonType.SBT_TEXT, 0);
        softButtonExpectedValue.setText("o2s1");
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
}
