package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.ImageFieldName}
 */
public class ImageFieldNameTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.soft_button_img_cap);
		ImageFieldName enumSoftButtonImage = ImageFieldName.valueForString(example);
		example = mContext.getString(R.string.choice_img);
		ImageFieldName enumChoiceImage = ImageFieldName.valueForString(example);
		example = mContext.getString(R.string.choice_second_img);
		ImageFieldName enumSecondaryImage = ImageFieldName.valueForString(example);
		example = mContext.getString(R.string.vrhelpitem);
		ImageFieldName enumVrHelpItem = ImageFieldName.valueForString(example);
		example = mContext.getString(R.string.turnicon);
		ImageFieldName enumTurnIcon = ImageFieldName.valueForString(example);
		example = mContext.getString(R.string.menuicon);
		ImageFieldName enumMenuIcon = ImageFieldName.valueForString(example);
		example = mContext.getString(R.string.cmdicon);
		ImageFieldName enumCmdIcon = ImageFieldName.valueForString(example);
		example = mContext.getString(R.string.appicon);
		ImageFieldName enumAppIcon = ImageFieldName.valueForString(example);
		example = mContext.getString(R.string.graphic);
		ImageFieldName enumGraphicIcon = ImageFieldName.valueForString(example);
		example = mContext.getString(R.string.showconstanttbticon);
		ImageFieldName enumShowConstantTbtIcon = ImageFieldName.valueForString(example);
		example = mContext.getString(R.string.showconstanttbtnextturnicon);
		ImageFieldName enumShowConstantTbtNextTurnIcon = ImageFieldName.valueForString(example);
		example = mContext.getString(R.string.location_img);
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
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    ImageFieldName temp = ImageFieldName.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (IllegalArgumentException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    ImageFieldName temp = ImageFieldName.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
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

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}