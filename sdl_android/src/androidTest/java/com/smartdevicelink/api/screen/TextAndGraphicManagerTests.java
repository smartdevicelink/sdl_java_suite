package com.smartdevicelink.api.screen;

import android.content.Context;

import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.api.FileManager;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.test.utl.AndroidToolsTests;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by brettywhite on 7/31/18.
 */
public class TextAndGraphicManagerTests extends AndroidToolsTests{

	// SETUP / HELPERS
	private TextAndGraphicManager textAndGraphicManager;

	@Override
	public void setUp() throws Exception{
		super.setUp();
		Context mTestContext = this.getContext();
		// mock things
		ISdl internalInterface = mock(ISdl.class);
		FileManager fileManager = mock(FileManager.class);

		textAndGraphicManager = new TextAndGraphicManager(internalInterface, fileManager, mTestContext);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	private DisplayCapabilities getDisplayCapability(int numberOfMainFields){

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

		DisplayCapabilities displayCapabilities = new DisplayCapabilities();
		displayCapabilities.setTextFields(returnList);

		return displayCapabilities;
	}

	public void testInstantiation(){

		assertNull(textAndGraphicManager.getTextField1());
		assertNull(textAndGraphicManager.getTextField2());
		assertNull(textAndGraphicManager.getTextField3());
		assertNull(textAndGraphicManager.getTextField4());
		assertNull(textAndGraphicManager.getMediaTrackTextField());
		assertNull(textAndGraphicManager.getPrimaryGraphic());
		assertNull(textAndGraphicManager.getSecondaryGraphic());
		assertEquals(textAndGraphicManager.getTextAlignment(), TextAlignment.CENTERED);
		assertNull(textAndGraphicManager.getTextField1Type());
		assertNull(textAndGraphicManager.getTextField2Type());
		assertNull(textAndGraphicManager.getTextField3Type());
		assertNull(textAndGraphicManager.getTextField4Type());

		assertNotNull(textAndGraphicManager.currentScreenData);
		assertNull(textAndGraphicManager.inProgressUpdate);
		assertNull(textAndGraphicManager.queuedImageUpdate);
		assertEquals(textAndGraphicManager.hasQueuedUpdate, false);
		assertNull(textAndGraphicManager.displayCapabilities);
		assertEquals(textAndGraphicManager.currentHMILevel, HMILevel.HMI_NONE);
		assertEquals(textAndGraphicManager.isDirty, false);
		assertEquals(textAndGraphicManager.getState(), BaseSubManager.READY);
	}

	public void testGetMainLines(){

		// We want to test that the looping works. By default, it will return 4 if display cap is null

		// Null test
		assertEquals(textAndGraphicManager.getNumberOfLines(), 4);

		// The tests.java class has an example of this, but we must build it to do what
		// we need it to do. Build display cap w/ 3 main fields and test that it returns 3
		textAndGraphicManager.displayCapabilities = getDisplayCapability(3);
		assertEquals(textAndGraphicManager.getNumberOfLines(), 3);
	}

	public void testAssemble1Line(){

		Show inputShow = new Show();

		// Force it to return display with support for only 1 line of text
		textAndGraphicManager.displayCapabilities = getDisplayCapability(1);

		textAndGraphicManager.setTextField1("It is");

		Show assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");

		textAndGraphicManager.setTextField2("Wednesday");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is - Wednesday");

		textAndGraphicManager.setTextField3("My");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is - Wednesday - My");

		textAndGraphicManager.setTextField4("Dudes");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is - Wednesday - My - Dudes");

		// For some obscurity, lets try setting just fields 2 and 4 for a 1 line display
		textAndGraphicManager.setTextField1(null);
		textAndGraphicManager.setTextField3(null);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "Wednesday - Dudes");
	}

	public void testAssemble2Lines() {

		Show inputShow = new Show();

		// Force it to return display with support for only 2 lines of text
		textAndGraphicManager.displayCapabilities = getDisplayCapability(2);

		textAndGraphicManager.setTextField1("It is");

		Show assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");

		textAndGraphicManager.setTextField2("Wednesday");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");

		textAndGraphicManager.setTextField3("My");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is - Wednesday");
		assertEquals(assembledShow.getMainField2(), "My");

		textAndGraphicManager.setTextField4("Dudes");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is - Wednesday");
		assertEquals(assembledShow.getMainField2(), "My - Dudes");

		// For some obscurity, lets try setting just fields 2 and 4 for a 2 line display
		textAndGraphicManager.setTextField1(null);
		textAndGraphicManager.setTextField3(null);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "Wednesday");
		assertEquals(assembledShow.getMainField2(), "Dudes");

		// And 3 fields without setting 1
		textAndGraphicManager.setTextField3("My");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "Wednesday");
		assertEquals(assembledShow.getMainField2(), "My - Dudes");
	}

	public void testAssemble3Lines() {

		Show inputShow = new Show();

		// Force it to return display with support for only 3 lines of text
		textAndGraphicManager.displayCapabilities = getDisplayCapability(3);

		textAndGraphicManager.setTextField1("It is");

		Show assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "");
		assertEquals(assembledShow.getMainField3(), "");

		textAndGraphicManager.setTextField2("Wednesday");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "");

		textAndGraphicManager.setTextField3("My");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "My");

		textAndGraphicManager.setTextField4("Dudes");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "My - Dudes");

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

	public void testAssemble4Lines() {

		Show inputShow = new Show();

		// Force it to return display with support for only 4 lines of text
		textAndGraphicManager.displayCapabilities = getDisplayCapability(4);

		textAndGraphicManager.setTextField1("It is");
		textAndGraphicManager.setTextField1Type(MetadataType.HUMIDITY);

		Show assembledShow = textAndGraphicManager.assembleShowText(inputShow);

		try {
			System.out.println(assembledShow.serializeJSON().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "");
		assertEquals(assembledShow.getMainField3(), "");
		assertEquals(assembledShow.getMainField4(), "");

		textAndGraphicManager.setTextField2("Wednesday");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "");
		assertEquals(assembledShow.getMainField4(), "");

		textAndGraphicManager.setTextField3("My");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "My");
		assertEquals(assembledShow.getMainField4(), "");

		textAndGraphicManager.setTextField4("Dudes");

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "Wednesday");
		assertEquals(assembledShow.getMainField3(), "My");
		assertEquals(assembledShow.getMainField4(), "Dudes");

		// try just setting line 1 and 4
		textAndGraphicManager.setTextField2(null);
		textAndGraphicManager.setTextField3(null);

		assembledShow = textAndGraphicManager.assembleShowText(inputShow);
		assertEquals(assembledShow.getMainField1(), "It is");
		assertEquals(assembledShow.getMainField2(), "");
		assertEquals(assembledShow.getMainField3(), "");
		assertEquals(assembledShow.getMainField4(), "Dudes");
	}

	// add types to test above

	// TEST SETTERS

		// batching

		// not batching


	// TEST IMAGES

	// TEST DISPOSE
}
