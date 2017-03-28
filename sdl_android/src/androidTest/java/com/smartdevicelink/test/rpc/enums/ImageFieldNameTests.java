package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.ImageFieldName}
 */
public class ImageFieldNameTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "softButtonImage";
		ImageFieldName enumSoftButtonImage = ImageFieldName.valueForString(example);
		example = "choiceImage";
		ImageFieldName enumChoiceImage = ImageFieldName.valueForString(example);
		example = "choiceSecondaryImage";
		ImageFieldName enumSecondaryImage = ImageFieldName.valueForString(example);
		example = "vrHelpItem";
		ImageFieldName enumVrHelpItem = ImageFieldName.valueForString(example);
		example = "turnIcon";
		ImageFieldName enumTurnIcon = ImageFieldName.valueForString(example);
		example = "menuIcon";
		ImageFieldName enumMenuIcon = ImageFieldName.valueForString(example);
		example = "cmdIcon";
		ImageFieldName enumCmdIcon = ImageFieldName.valueForString(example);
		example = "appIcon";
		ImageFieldName enumAppIcon = ImageFieldName.valueForString(example);
		example = "graphic";
		ImageFieldName enumGraphicIcon = ImageFieldName.valueForString(example);
		example = "showConstantTBTIcon";
		ImageFieldName enumShowConstantTbtIcon = ImageFieldName.valueForString(example);
		example = "showConstantTBTNextTurnIcon";
		ImageFieldName enumShowConstantTbtNextTurnIcon = ImageFieldName.valueForString(example);
		example = "locationImage";
		ImageFieldName enumLocationImage = ImageFieldName.valueForString(example);
				
		assertNotNull("softButtonImage returned null", enumSoftButtonImage);
		assertNotNull("choiceImage returned null", enumChoiceImage);
		assertNotNull("choiceSecondaryImage returned null", enumSecondaryImage);
		assertNotNull("vrHelpItem returned null", enumVrHelpItem);
		assertNotNull("turnIcon returned null", enumTurnIcon);
		assertNotNull("menuIcon returned null", enumMenuIcon);
		assertNotNull("cmdIcon returned null", enumCmdIcon);
		assertNotNull("appIcon returned null", enumAppIcon);
		assertNotNull("graphic returned null", enumGraphicIcon);
		assertNotNull("showConstantTBTIcon returned null", enumShowConstantTbtIcon);
		assertNotNull("showConstantTBTNextTurnIcon returned null", enumShowConstantTbtNextTurnIcon);
		assertNotNull("location image returned null", enumLocationImage);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "sofTbUtTOnImagE";
		try {
		    ImageFieldName temp = ImageFieldName.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    ImageFieldName temp = ImageFieldName.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	/**
	 * Verifies the possible enum values of ImageFieldName.
	 */
	public void testListEnum() {
 		List<ImageFieldName> enumValueList = Arrays.asList(ImageFieldName.values());

		List<ImageFieldName> enumTestList = new ArrayList<ImageFieldName>();
		enumTestList.add(ImageFieldName.softButtonImage);
		enumTestList.add(ImageFieldName.choiceImage);
		enumTestList.add(ImageFieldName.choiceSecondaryImage);
		enumTestList.add(ImageFieldName.vrHelpItem);
		enumTestList.add(ImageFieldName.turnIcon);
		enumTestList.add(ImageFieldName.menuIcon);		
		enumTestList.add(ImageFieldName.cmdIcon);
		enumTestList.add(ImageFieldName.appIcon);	
		enumTestList.add(ImageFieldName.graphic);
		enumTestList.add(ImageFieldName.showConstantTBTIcon);	
		enumTestList.add(ImageFieldName.showConstantTBTNextTurnIcon);	
		enumTestList.add(ImageFieldName.locationImage);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}