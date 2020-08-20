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

/*	@Test
	public void testAssemble1Line(){

		Show inputShow = new Show();

		// Force it to return display with support for only 1 line of text
		textAndGraphicManager.defaultMainWindowCapability = getWindowCapability(1);

		textAndGraphicManager.setTextField1("It is");
		textAndGraphicManager.setTextField1Type(MetadataType.HUMIDITY);

		Show assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");

		// test tags (just 1)
		MetadataTags tags = assembledShow.getMetadataTags();
		List<MetadataType> tagsList = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		assertEquals(tags.getMainField1(), tagsList);

		textAndGraphicManager.setTextField2("Wednesday");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is - Wednesday");

		textAndGraphicManager.setTextField3("My");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is - Wednesday - My");

		textAndGraphicManager.setTextField4("Dudes");
		textAndGraphicManager.setTextField4Type(MetadataType.CURRENT_TEMPERATURE);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is - Wednesday - My - Dudes");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList.add(MetadataType.CURRENT_TEMPERATURE);
		assertEquals(tags.getMainField1(), tagsList);

		// For some obscurity, lets try setting just fields 2 and 4 for a 1 line display
		textAndGraphicManager.setTextField1(null);
		textAndGraphicManager.setTextField3(null);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "Wednesday - Dudes");
	}

	@Test
	public void testAssemble2Lines() {

		Show inputShow = new Show();

		// Force it to return display with support for only 2 lines of text
		textAndGraphicManager.defaultMainWindowCapability = getWindowCapability(2);

		textAndGraphicManager.setTextField1("It is");
		textAndGraphicManager.setTextField1Type(MetadataType.HUMIDITY);

		Show assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");

		// test tags
		MetadataTags tags = assembledShow.getMetadataTags();
		List<MetadataType> tagsList = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		assertEquals(tags.getMainField1(), tagsList);

		textAndGraphicManager.setTextField2("Wednesday");
		textAndGraphicManager.setTextField2Type(MetadataType.CURRENT_TEMPERATURE);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		List<MetadataType> tagsList2 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);

		textAndGraphicManager.setTextField3("My");
		textAndGraphicManager.setTextField3Type(MetadataType.MEDIA_ALBUM);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is - Wednesday");
		assertEquals(assembledShow.getMainField2(), "My");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList2 = new ArrayList<>();
		tagsList.add(MetadataType.CURRENT_TEMPERATURE);
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.MEDIA_ALBUM);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);

		textAndGraphicManager.setTextField4("Dudes");
		textAndGraphicManager.setTextField4Type(MetadataType.MEDIA_STATION);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is - Wednesday");
		assertEquals(assembledShow.getMainField2(), "My - Dudes");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList2 = new ArrayList<>();
		tagsList.add(MetadataType.CURRENT_TEMPERATURE);
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.MEDIA_STATION);
		tagsList2.add(MetadataType.MEDIA_ALBUM);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);

		// For some obscurity, lets try setting just fields 2 and 4 for a 2 line display
		textAndGraphicManager.setTextField1(null);
		textAndGraphicManager.setTextField3(null);
		textAndGraphicManager.setTextField1Type(null);
		textAndGraphicManager.setTextField3Type(null);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "Wednesday");
		assertEquals(assembledShow.getMainField2(), "Dudes");

		// And 3 fields without setting 1
		textAndGraphicManager.setTextField3("My");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "Wednesday");
		assertEquals(assembledShow.getMainField2(), "My - Dudes");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList2 = new ArrayList<>();
		tagsList.add(MetadataType.CURRENT_TEMPERATURE);
		tagsList2.add(MetadataType.MEDIA_STATION);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);
	}

	@Test
	public void testAssemble3Lines() {

		Show inputShow = new Show();

		// Force it to return display with support for only 3 lines of text
		textAndGraphicManager.defaultMainWindowCapability = getWindowCapability(3);

		textAndGraphicManager.setTextField1("It is");
		textAndGraphicManager.setTextField1Type(MetadataType.HUMIDITY);

		Show assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "");
		assertEquals(assembledShow.getMainField3(), "");

		// test tags
		MetadataTags tags = assembledShow.getMetadataTags();
		List<MetadataType> tagsList = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		assertEquals(tags.getMainField1(), tagsList);

		textAndGraphicManager.setTextField2("Wednesday");
		textAndGraphicManager.setTextField2Type(MetadataType.CURRENT_TEMPERATURE);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		List<MetadataType> tagsList2 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);

		textAndGraphicManager.setTextField3("My");
		textAndGraphicManager.setTextField3Type(MetadataType.MEDIA_ALBUM);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "My");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList2 = new ArrayList<>();
		List<MetadataType> tagsList3 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
		tagsList3.add(MetadataType.MEDIA_ALBUM);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);
		assertEquals(tags.getMainField3(), tagsList3);

		textAndGraphicManager.setTextField4("Dudes");
		textAndGraphicManager.setTextField4Type(MetadataType.MEDIA_STATION);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "My - Dudes");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList2 = new ArrayList<>();
		tagsList3 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
		tagsList3.add(MetadataType.MEDIA_ALBUM);
		tagsList3.add(MetadataType.MEDIA_STATION);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);
		assertEquals(tags.getMainField3(), tagsList3);

		// Someone might not want to set the fields in order? We should handle that
		textAndGraphicManager.setTextField1(null);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		try {
			System.out.println(assembledShow.serializeJSON().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "My - Dudes");
	}

	@Test
	public void testAssemble4Lines() {

		Show inputShow = new Show();

		textAndGraphicManager.defaultMainWindowCapability = getWindowCapability(4);
		TextField tx1 = new TextField();
		TextField tx2 = new TextField();
		TextField tx3 = new TextField();
		TextField tx4 = new TextField();
		TextField tx5 = new TextField();
		TextField tx6 = new TextField();

		tx1.setName(TextFieldName.mainField1);
		tx2.setName(TextFieldName.mainField2);
		tx3.setName(TextFieldName.mainField3);
		tx4.setName(TextFieldName.mainField4);
		tx5.setName(TextFieldName.mediaTrack);
		tx6.setName(TextFieldName.templateTitle);

		List<TextField> textFieldNames = Arrays.asList(tx1,tx2,tx3,tx4,tx5,tx6);
		textAndGraphicManager.defaultMainWindowCapability.setTextFields(textFieldNames);

		textAndGraphicManager.setMediaTrackTextField("HI");
		textAndGraphicManager.setTitle("bye");

		// Force it to return display with support for only 4 lines of text

		textAndGraphicManager.setTextField1("It is");
		textAndGraphicManager.setTextField1Type(MetadataType.HUMIDITY);

		Show assembledShow = textAndGraphicManager.assembleShowText(inputShow);

		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "");
		assertEquals(assembledShow.getMainField3(), "");
		assertEquals(assembledShow.getMainField4(), "");
		assertEquals(assembledShow.getMediaTrack(), "HI");
		assertEquals(assembledShow.getTemplateTitle(), "bye");

		// test tags
		MetadataTags tags = assembledShow.getMetadataTags();
		List<MetadataType> tagsList = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		assertEquals(tags.getMainField1(), tagsList);

		textAndGraphicManager.setTextField2("Wednesday");
		textAndGraphicManager.setTextField2Type(MetadataType.CURRENT_TEMPERATURE);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "");
		assertEquals(assembledShow.getMainField4(), "");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		List<MetadataType> tagsList2 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);

		textAndGraphicManager.setTextField3("My");
		textAndGraphicManager.setTextField3Type(MetadataType.MEDIA_ALBUM);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "My");
		assertEquals(assembledShow.getMainField4(), "");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList2 = new ArrayList<>();
		List<MetadataType> tagsList3 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
		tagsList3.add(MetadataType.MEDIA_ALBUM);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);
		assertEquals(tags.getMainField3(), tagsList3);

		textAndGraphicManager.setTextField4("Dudes");
		textAndGraphicManager.setTextField4Type(MetadataType.MEDIA_STATION);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "My");
		assertEquals(assembledShow.getMainField4(), "Dudes");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList2 = new ArrayList<>();
		tagsList3 = new ArrayList<>();
		List<MetadataType> tagsList4 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
		tagsList3.add(MetadataType.MEDIA_ALBUM);
		tagsList4.add(MetadataType.MEDIA_STATION);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);
		assertEquals(tags.getMainField3(), tagsList3);
		assertEquals(tags.getMainField4(), tagsList4);

		// try just setting line 1 and 4
		textAndGraphicManager.setTextField2(null);
		textAndGraphicManager.setTextField3(null);
		textAndGraphicManager.setTextField2Type(null);
		textAndGraphicManager.setTextField3Type(null);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "");
		assertEquals(assembledShow.getMainField3(), "");
		assertEquals(assembledShow.getMainField4(), "Dudes");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList4 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList4.add(MetadataType.MEDIA_STATION);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField4(), tagsList4);
	}

	*//**
	 * Testing if WindowCapability is null, TextFields should still update.
	 *//*
	@Test
	public void testAssemble4LinesNullWindowCapability() {

		Show inputShow = new Show();
		
		textAndGraphicManager.setMediaTrackTextField("HI");
		textAndGraphicManager.setTitle("bye");

		textAndGraphicManager.setTextField1("It is");
		textAndGraphicManager.setTextField1Type(MetadataType.HUMIDITY);

		Show assembledShow = textAndGraphicManager.assembleShowText(inputShow);

		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "");
		assertEquals(assembledShow.getMainField3(), "");
		assertEquals(assembledShow.getMainField4(), "");
		assertEquals(assembledShow.getMediaTrack(), "HI");
		assertEquals(assembledShow.getTemplateTitle(), "bye");

		// test tags
		MetadataTags tags = assembledShow.getMetadataTags();
		List<MetadataType> tagsList = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		assertEquals(tags.getMainField1(), tagsList);

		textAndGraphicManager.setTextField2("Wednesday");
		textAndGraphicManager.setTextField2Type(MetadataType.CURRENT_TEMPERATURE);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "");
		assertEquals(assembledShow.getMainField4(), "");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		List<MetadataType> tagsList2 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);

		textAndGraphicManager.setTextField3("My");
		textAndGraphicManager.setTextField3Type(MetadataType.MEDIA_ALBUM);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "My");
		assertEquals(assembledShow.getMainField4(), "");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList2 = new ArrayList<>();
		List<MetadataType> tagsList3 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
		tagsList3.add(MetadataType.MEDIA_ALBUM);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);
		assertEquals(tags.getMainField3(), tagsList3);

		textAndGraphicManager.setTextField4("Dudes");
		textAndGraphicManager.setTextField4Type(MetadataType.MEDIA_STATION);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "My");
		assertEquals(assembledShow.getMainField4(), "Dudes");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList2 = new ArrayList<>();
		tagsList3 = new ArrayList<>();
		List<MetadataType> tagsList4 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList2.add(MetadataType.CURRENT_TEMPERATURE);
		tagsList3.add(MetadataType.MEDIA_ALBUM);
		tagsList4.add(MetadataType.MEDIA_STATION);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField2(), tagsList2);
		assertEquals(tags.getMainField3(), tagsList3);
		assertEquals(tags.getMainField4(), tagsList4);

		// try just setting line 1 and 4
		textAndGraphicManager.setTextField2(null);
		textAndGraphicManager.setTextField3(null);
		textAndGraphicManager.setTextField2Type(null);
		textAndGraphicManager.setTextField3Type(null);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "");
		assertEquals(assembledShow.getMainField3(), "");
		assertEquals(assembledShow.getMainField4(), "Dudes");

		// test tags
		tags = assembledShow.getMetadataTags();
		tagsList = new ArrayList<>();
		tagsList4 = new ArrayList<>();
		tagsList.add(MetadataType.HUMIDITY);
		tagsList4.add(MetadataType.MEDIA_STATION);
		assertEquals(tags.getMainField1(), tagsList);
		assertEquals(tags.getMainField4(), tagsList4);
	}*/

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

/*	@Test
	public void testExtractTextFromShow(){

		Show mainShow = new Show();
		mainShow.setMainField1("test");
		mainShow.setMainField3("Sauce");
		mainShow.setMainField4("");

		Show newShow = textAndGraphicManager.extractTextFromShow(mainShow);

		assertEquals(newShow.getMainField1(), "test");
		assertEquals(newShow.getMainField3(), "Sauce");
		assertEquals(newShow.getMainField4(), "");
		assertNull(newShow.getMainField2());
	}*/

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
