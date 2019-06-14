package com.smartdevicelink.managers.screen;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.screen.menu.DynamicMenuUpdatesMode;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.test.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link ScreenManager}
 */
public class ScreenManagerTests extends AndroidTestCase2 {
	private ScreenManager screenManager;
	private SdlArtwork testArtwork;

	@Override
	public void setUp() throws Exception{
		super.setUp();

		ISdl internalInterface = mock(ISdl.class);
		FileManager fileManager = mock(FileManager.class);
		screenManager = new ScreenManager(internalInterface, fileManager);
		screenManager.start(null);
		testArtwork = new SdlArtwork("testFile", FileType.GRAPHIC_PNG, 1, false);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
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
		assertNull(screenManager.getMenu());
		assertNull(screenManager.getVoiceCommands());
		assertTrue(screenManager.getSoftButtonObjects().isEmpty());
		assertNull(screenManager.getSoftButtonObjectByName("test"));
		assertNull(screenManager.getSoftButtonObjectById(1));
		assertEquals(screenManager.getDynamicMenuUpdatesMode(), DynamicMenuUpdatesMode.ON_WITH_COMPAT_MODE);
		assertEquals(screenManager.getState(), BaseSubManager.READY);
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

	public void testSetPrimaryGraphicWithBlankImage() {
		screenManager.setPrimaryGraphic(null);
		assertNotNull(screenManager.getPrimaryGraphic());
		assertEquals(screenManager.getPrimaryGraphic().getName(), "blankArtwork");
	}

	public void testSetSecondaryGraphic() {
		screenManager.setSecondaryGraphic(testArtwork);
		assertEquals(screenManager.getSecondaryGraphic(), testArtwork);
	}

	public void testSetSecondaryGraphicWithBlankImage() {
		screenManager.setSecondaryGraphic(null);
		assertNotNull(screenManager.getSecondaryGraphic());
		assertEquals(screenManager.getSecondaryGraphic().getName(), "blankArtwork");
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

	public void testSetMenuManagerFields(){
		screenManager.setDynamicMenuUpdatesMode(DynamicMenuUpdatesMode.FORCE_ON);
		screenManager.setMenu(Test.GENERAL_MENUCELL_LIST);

		assertEquals(screenManager.getMenu(), Test.GENERAL_MENUCELL_LIST);
		assertEquals(screenManager.getDynamicMenuUpdatesMode(), DynamicMenuUpdatesMode.FORCE_ON);
	}

	public void testSetVoiceCommands(){
		screenManager.setVoiceCommands(Test.GENERAL_VOICE_COMMAND_LIST);
		assertEquals(screenManager.getVoiceCommands(), Test.GENERAL_VOICE_COMMAND_LIST);
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
