package com.smartdevicelink.managers.screen;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link ScreenManager}
 */
public class ScreenManagerTests extends AndroidTestCase2 {
	private ScreenManager screenManager;
	private FileManager fileManager;
	private SdlArtwork testArtwork;

	@Override
	public void setUp() throws Exception{
		super.setUp();

		fileManager = mock(FileManager.class);

		// When internalInterface.sendRPCRequest() is called, create a fake success response
		ISdl internalInterface = mock(ISdl.class);
		sendFakeSetDisplayLayoutResponse(internalInterface, true);

		screenManager = new ScreenManager(internalInterface, fileManager);
		screenManager.start(null);

		testArtwork = new SdlArtwork("testFile", FileType.GRAPHIC_PNG, 1, false);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	// When internalInterface.sendRPCRequest() is called, create a fake response
	private void sendFakeSetDisplayLayoutResponse(ISdl internalInterface, final boolean success){
		Answer<Void> answer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				RPCRequest request = (RPCRequest) args[0];
				RPCResponse response = new RPCResponse(FunctionID.SET_DISPLAY_LAYOUT.toString());
				response.setSuccess(success);
				request.getOnRPCResponseListener().onResponse(0, response);
				return null;
			}
		};
		doAnswer(answer).when(internalInterface).sendRPCRequest(any(RPCRequest.class));
	}

	public void testInstantiation(){
		assertNull(screenManager.getTextField1());
		assertNull(screenManager.getTextField2());
		assertNull(screenManager.getTextField3());
		assertNull(screenManager.getTextField4());
		assertNull(screenManager.getMediaTrackTextField());
		assertNull(screenManager.getPrimaryGraphic());
		assertNull(screenManager.getSecondaryGraphic());
		assertEquals(screenManager.getTextAlignment(), TextAlignment.CENTERED);
		assertNull(screenManager.getTextField1Type());
		assertNull(screenManager.getTextField2Type());
		assertNull(screenManager.getTextField3Type());
		assertNull(screenManager.getTextField4Type());
		assertTrue(screenManager.getSoftButtonObjects().isEmpty());
		assertNull(screenManager.getSoftButtonObjectByName("test"));
		assertNull(screenManager.getSoftButtonObjectById(1));
		assertEquals(screenManager.getState(), BaseSubManager.READY);
	}

	public void testManagerStates() {
		ISdl tempInternalInterface = mock(ISdl.class);
		ScreenManager tempScreenManager = new ScreenManager(tempInternalInterface, fileManager);

		// Case 1
		sendFakeSetDisplayLayoutResponse(tempInternalInterface, true);
		tempScreenManager.start(null);
		tempScreenManager.getSoftButtonManager().transitionToState(BaseSubManager.READY);
		tempScreenManager.getTextAndGraphicManager().transitionToState(BaseSubManager.READY);
		tempScreenManager.checkState();
		assertEquals(BaseSubManager.READY, tempScreenManager.getState());

		// Case 2
		sendFakeSetDisplayLayoutResponse(tempInternalInterface, true);
		tempScreenManager.start(null);
		tempScreenManager.getSoftButtonManager().transitionToState(BaseSubManager.ERROR);
		tempScreenManager.getTextAndGraphicManager().transitionToState(BaseSubManager.ERROR);
		tempScreenManager.checkState();
		assertEquals(BaseSubManager.ERROR, tempScreenManager.getState());

		// Case 3
		sendFakeSetDisplayLayoutResponse(tempInternalInterface, false);
		tempScreenManager.start(null);
		tempScreenManager.getSoftButtonManager().transitionToState(BaseSubManager.SETTING_UP);
		tempScreenManager.getTextAndGraphicManager().transitionToState(BaseSubManager.READY);
		tempScreenManager.checkState();
		assertEquals(BaseSubManager.SETTING_UP, tempScreenManager.getState());

		// Case 4
		sendFakeSetDisplayLayoutResponse(tempInternalInterface, true);
		tempScreenManager.start(null);
		tempScreenManager.getSoftButtonManager().transitionToState(BaseSubManager.READY);
		tempScreenManager.getTextAndGraphicManager().transitionToState(BaseSubManager.ERROR);
		tempScreenManager.checkState();
		assertEquals(BaseSubManager.LIMITED, tempScreenManager.getState());

		// Case 5
		sendFakeSetDisplayLayoutResponse(tempInternalInterface, false);
		tempScreenManager.start(null);
		tempScreenManager.getSoftButtonManager().transitionToState(BaseSubManager.READY);
		tempScreenManager.getTextAndGraphicManager().transitionToState(BaseSubManager.READY);
		tempScreenManager.checkState();
		assertEquals(BaseSubManager.LIMITED, tempScreenManager.getState());
	}
	
	public void testSetTextField() {
		screenManager.setTextField1("It is");
		screenManager.setTextField2("Wednesday");
		screenManager.setTextField3("My");
		screenManager.setTextField4("Dudes");
		assertEquals(screenManager.getTextField1(), "It is");
		assertEquals(screenManager.getTextField2(), "Wednesday");
		assertEquals(screenManager.getTextField3(), "My");
		assertEquals(screenManager.getTextField4(), "Dudes");
	}

	public void testMediaTrackTextFields() {
		String songTitle = "Wild For The Night";
		screenManager.setMediaTrackTextField(songTitle);
		assertEquals(screenManager.getMediaTrackTextField(), songTitle);
	}

	public void testSetPrimaryGraphic() {
		screenManager.setPrimaryGraphic(testArtwork);
		assertEquals(screenManager.getPrimaryGraphic(), testArtwork);
	}

	public void testSetSecondaryGraphic() {
		screenManager.setSecondaryGraphic(testArtwork);
		assertEquals(screenManager.getSecondaryGraphic(), testArtwork);
	}

	public void testAlignment() {
		screenManager.setTextAlignment(TextAlignment.LEFT_ALIGNED);
		assertEquals(screenManager.getTextAlignment(), TextAlignment.LEFT_ALIGNED);
	}

	public void testSetTextFieldTypes() {
		screenManager.setTextField1Type(MetadataType.MEDIA_TITLE);
		screenManager.setTextField2Type(MetadataType.MEDIA_ALBUM);
		screenManager.setTextField3Type(MetadataType.MEDIA_ARTIST);
		screenManager.setTextField4Type(MetadataType.MEDIA_GENRE);
		assertEquals(screenManager.getTextField1Type(), MetadataType.MEDIA_TITLE);
		assertEquals(screenManager.getTextField2Type(), MetadataType.MEDIA_ALBUM);
		assertEquals(screenManager.getTextField3Type(), MetadataType.MEDIA_ARTIST);
		assertEquals(screenManager.getTextField4Type(), MetadataType.MEDIA_GENRE);
	}

	public void testSetSoftButtonObjects(){
		// Create softButtonObject1
		SoftButtonState softButtonState1 = new SoftButtonState("object1-state1", "it is", testArtwork);
		SoftButtonState softButtonState2 = new SoftButtonState("object1-state2", "Wed", testArtwork);
		SoftButtonObject softButtonObject1 = new SoftButtonObject("object1", Arrays.asList(softButtonState1, softButtonState2), softButtonState1.getName(),null);

		// Create softButtonObject2
		SoftButtonState softButtonState3 = new SoftButtonState("object2-state1", "my", testArtwork);
		SoftButtonState softButtonState4 = new SoftButtonState("object2-state2", "dudes!", null);
		SoftButtonObject softButtonObject2 = new SoftButtonObject("object2", Arrays.asList(softButtonState3, softButtonState4), softButtonState3.getName(), null);

		List<SoftButtonObject> softButtonObjects = Arrays.asList(softButtonObject1, softButtonObject2);
		screenManager.setSoftButtonObjects(Arrays.asList(softButtonObject1, softButtonObject2));
		assertEquals(screenManager.getSoftButtonObjects(), softButtonObjects);
		assertEquals(screenManager.getSoftButtonObjectByName("object2"), softButtonObject2);
		assertEquals(screenManager.getSoftButtonObjectById(100), softButtonObject2);
	}

}
