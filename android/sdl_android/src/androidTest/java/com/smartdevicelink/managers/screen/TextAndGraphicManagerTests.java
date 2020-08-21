package com.smartdevicelink.managers.screen;

import android.content.Context;
import android.net.Uri;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.MetadataTags;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.managers.screen.TextAndGraphicManager}
 */
@RunWith(AndroidJUnit4.class)
public class TextAndGraphicManagerTests {

	// SETUP / HELPERS
	private TextAndGraphicManager textAndGraphicManager;
	private SdlArtwork testArtwork;
	Taskmaster taskmaster;

	@Before
	public void setUp() throws Exception{
		Context mTestContext = getInstrumentation().getContext();

		// mock things
		ISdl internalInterface = mock(ISdl.class);
		FileManager fileManager = mock(FileManager.class);
		SoftButtonManager softButtonManager = mock(SoftButtonManager.class);
		taskmaster = new Taskmaster.Builder().build();
		when(internalInterface.getTaskmaster()).thenReturn(taskmaster);

		testArtwork = new SdlArtwork();
		testArtwork.setName("testFile");
		Uri uri = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
		testArtwork.setUri(uri);
		testArtwork.setType(FileType.GRAPHIC_PNG);

		textAndGraphicManager = new TextAndGraphicManager(internalInterface, fileManager);
	}


	private WindowCapability getWindowCapability(int numberOfMainFields){

		TextField mainField1 = new TextField();
		mainField1.setName(TextFieldName.mainField1);
		TextField mainField2 = new TextField();
		mainField2.setName(TextFieldName.mainField2);
		TextField mainField3 = new TextField();
		mainField3.setName(TextFieldName.mainField3);
		TextField mainField4 = new TextField();
		mainField4.setName(TextFieldName.mainField4);

		List<TextField> textFieldList = new ArrayList<>();

		textFieldList.add(mainField1);
		textFieldList.add(mainField2);
		textFieldList.add(mainField3);
		textFieldList.add(mainField4);

		List<TextField> returnList = new ArrayList<>();

		if (numberOfMainFields > 0){
			for (int i = 0; i < numberOfMainFields; i++) {
				returnList.add(textFieldList.get(i));
			}
		}

		WindowCapability windowCapability = new WindowCapability();
		windowCapability.setTextFields(returnList);

		return windowCapability;
	}

	/**
	 * Used to simulate WindowCapability having no capabilities set
	 * @return windowCapability that has no capabilities set
	 */
	private WindowCapability getNullVarWindowCapability() {

		WindowCapability windowCapability = new WindowCapability();
		return windowCapability;
	}

	@Test
	public void testInstantiation(){

		assertNull(textAndGraphicManager.getTextField1());
		assertNull(textAndGraphicManager.getTextField2());
		assertNull(textAndGraphicManager.getTextField3());
		assertNull(textAndGraphicManager.getTextField4());
		assertNull(textAndGraphicManager.getTitle());
		assertNull(textAndGraphicManager.getMediaTrackTextField());
		assertNull(textAndGraphicManager.getPrimaryGraphic());
		assertNull(textAndGraphicManager.getSecondaryGraphic());
		assertEquals(textAndGraphicManager.getTextAlignment(), TextAlignment.CENTERED);
		assertNull(textAndGraphicManager.getTextField1Type());
		assertNull(textAndGraphicManager.getTextField2Type());
		assertNull(textAndGraphicManager.getTextField3Type());
		assertNull(textAndGraphicManager.getTextField4Type());
		assertNotNull(textAndGraphicManager.currentScreenData);
		assertNull(textAndGraphicManager.defaultMainWindowCapability);
		assertEquals(textAndGraphicManager.currentHMILevel, HMILevel.HMI_NONE);
		assertFalse(textAndGraphicManager.isDirty);
		assertEquals(textAndGraphicManager.getState(), BaseSubManager.SETTING_UP);
		assertNotNull(textAndGraphicManager.getBlankArtwork());
	}

	/**
	 * Test getting number of lines available to be set based off of windowCapability
	 */
	@Test
	public void testGetMainLines(){

		// We want to test that the looping works. By default, it will return 4 if display cap is null
		textAndGraphicManager.defaultMainWindowCapability = getNullVarWindowCapability();

		// Null test
		assertEquals(0, ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(textAndGraphicManager.defaultMainWindowCapability));

		// The tests.java class has an example of this, but we must build it to do what
		// we need it to do. Build display cap w/ 3 main fields and test that it returns 3
		textAndGraphicManager.defaultMainWindowCapability = getWindowCapability(3);
		assertEquals(ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(textAndGraphicManager.defaultMainWindowCapability), 3);
	}

	@Test
	public void testMediaTrackTextField() {

		String songTitle = "Wild For The Night";
		textAndGraphicManager.setMediaTrackTextField(songTitle);
		assertEquals(textAndGraphicManager.getMediaTrackTextField(), songTitle);
	}

	@Test
	public void testTemplateTitle() {

		String title = "template title";
		textAndGraphicManager.setTitle(title);
		assertEquals(textAndGraphicManager.getTitle(), title);
	}

	@Test
	public void testAlignment() {

		textAndGraphicManager.setTextAlignment(TextAlignment.LEFT_ALIGNED);
		assertEquals(textAndGraphicManager.getTextAlignment(), TextAlignment.LEFT_ALIGNED);
	}

	// TEST IMAGES

	@Test
	public void testSetPrimaryGraphic() {
		textAndGraphicManager.setPrimaryGraphic(testArtwork);
		assertEquals(textAndGraphicManager.getPrimaryGraphic(), testArtwork);
	}

	@Test
	public void testSetSecondaryGraphic() {
		textAndGraphicManager.setSecondaryGraphic(testArtwork);
		assertEquals(textAndGraphicManager.getSecondaryGraphic(), testArtwork);
	}

	// TEST DISPOSE

	@Test
	public void testDispose() {
		textAndGraphicManager.dispose();

		assertNull(textAndGraphicManager.getTextField1());
		assertNull(textAndGraphicManager.getTextField2());
		assertNull(textAndGraphicManager.getTextField3());
		assertNull(textAndGraphicManager.getTextField4());
		assertNull(textAndGraphicManager.getMediaTrackTextField());
		assertNull(textAndGraphicManager.getPrimaryGraphic());
		assertNull(textAndGraphicManager.getSecondaryGraphic());
		assertNull(textAndGraphicManager.getTextAlignment());
		assertNull(textAndGraphicManager.getTextField1Type());
		assertNull(textAndGraphicManager.getTextField2Type());
		assertNull(textAndGraphicManager.getTextField3Type());
		assertNull(textAndGraphicManager.getTextField4Type());
		assertNull(textAndGraphicManager.getTitle());
		assertNotNull(textAndGraphicManager.getBlankArtwork());
		assertNull(textAndGraphicManager.currentScreenData);
		assertNull(textAndGraphicManager.defaultMainWindowCapability);
		assertFalse(textAndGraphicManager.isDirty);
		assertEquals(textAndGraphicManager.getState(), BaseSubManager.SHUTDOWN);
	}
}
