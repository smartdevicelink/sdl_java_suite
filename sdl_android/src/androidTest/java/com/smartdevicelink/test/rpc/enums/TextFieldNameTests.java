package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.TextFieldName}
 */
public class TextFieldNameTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "mainField1";
		TextFieldName enumMainField1 = TextFieldName.valueForString(example);
		example = "mainField2";
		TextFieldName enumMainField2 = TextFieldName.valueForString(example);
		example = "mainField3";
		TextFieldName enumMainField3 = TextFieldName.valueForString(example);
		example = "mainField4";
		TextFieldName enumMainField4 = TextFieldName.valueForString(example);
		example = "statusBar";
		TextFieldName enumStatusBar = TextFieldName.valueForString(example);
		example = "mediaClock";
		TextFieldName enumMediaClock = TextFieldName.valueForString(example);
		example = "mediaTrack";
		TextFieldName enumMediaTrack = TextFieldName.valueForString(example);
		example = "alertText1";
		TextFieldName enumAlertText1 = TextFieldName.valueForString(example);
		example = "alertText2";
		TextFieldName enumAlertText2 = TextFieldName.valueForString(example);
		example = "alertText3";
		TextFieldName enumAlertText3 = TextFieldName.valueForString(example);
		example = "scrollableMessageBody";
		TextFieldName enumScrollableMessageBody = TextFieldName.valueForString(example);
		example = "initialInteractionText";
		TextFieldName enumInitialInteractionText = TextFieldName.valueForString(example);
		example = "navigationText1";
		TextFieldName enumNavigationText1 = TextFieldName.valueForString(example);
		example = "navigationText2";
		TextFieldName enumNavigationText2 = TextFieldName.valueForString(example);
		example = "ETA";
		TextFieldName enumEta = TextFieldName.valueForString(example);
		example = "totalDistance";
		TextFieldName enumTotalDistance = TextFieldName.valueForString(example);
		example = "audioPassThruDisplayText1";
		TextFieldName enumAudioPassThruDisplayText1 = TextFieldName.valueForString(example);
		example = "audioPassThruDisplayText2";
		TextFieldName enumAudioPassThruDisplayText2 = TextFieldName.valueForString(example);
		example = "sliderHeader";
		TextFieldName enumSliderHeader = TextFieldName.valueForString(example);
		example = "sliderFooter";
		TextFieldName enumSliderFooter = TextFieldName.valueForString(example);
		example = "menuName";
		TextFieldName enumMenuName = TextFieldName.valueForString(example);
		example = "secondaryText";
		TextFieldName enumSecondaryText = TextFieldName.valueForString(example);
		example = "tertiaryText";
		TextFieldName enumTertiaryText = TextFieldName.valueForString(example);
		example = "menuTitle";
		TextFieldName enumMenuTitle = TextFieldName.valueForString(example);
		example = "locationName";
		TextFieldName enumLocName = TextFieldName.valueForString(example);
		example = "locationDescription";
		TextFieldName enumLocDesc = TextFieldName.valueForString(example);
		example = "addressLines";
		TextFieldName enumAddLines = TextFieldName.valueForString(example);
		example = "phoneNumber";
		TextFieldName enumPhone = TextFieldName.valueForString(example);
		
		assertNotNull("mainField1 returned null", enumMainField1);
		assertNotNull("mainField2 returned null", enumMainField2);
		assertNotNull("mainField3 returned null", enumMainField3);
		assertNotNull("mainField4 returned null", enumMainField4);
		assertNotNull("statusBar returned null",  enumStatusBar);
		assertNotNull("mediaClock returned null", enumMediaClock);
		assertNotNull("mediaTrack returned null", enumMediaTrack);
		assertNotNull("alertText1 returned null", enumAlertText1);
		assertNotNull("alertText2 returned null", enumAlertText2);
		assertNotNull("alertText3 returned null", enumAlertText3);
		assertNotNull("scrollableMessageBody returned null", enumScrollableMessageBody);
		assertNotNull("initialInteractionText returned null", enumInitialInteractionText);
		assertNotNull("navigationText1 returned null", enumNavigationText1);
		assertNotNull("navigationText2 returned null", enumNavigationText2);
		assertNotNull("ETA returned null", enumEta);
		assertNotNull("totalDistance returned null", enumTotalDistance);
		assertNotNull("audioPassThruDisplayText1 returned null", enumAudioPassThruDisplayText1);
		assertNotNull("audioPassThruDisplayText2 returned null", enumAudioPassThruDisplayText2);
		assertNotNull("sliderHeader returned null", enumSliderHeader);
		assertNotNull("sliderFooter returned null", enumSliderFooter);
		assertNotNull("menuName returned null", enumMenuName);
		assertNotNull("secondaryText returned null", enumSecondaryText);
		assertNotNull("tertiaryText returned null", enumTertiaryText);
		assertNotNull("menuTitle returned null", enumMenuTitle);
		
		assertNotNull(Test.NOT_NULL, enumLocName);
		assertNotNull(Test.NOT_NULL, enumLocDesc);
		assertNotNull(Test.NOT_NULL, enumAddLines);
		assertNotNull(Test.NOT_NULL, enumPhone);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "mAinField1";
		try {
		    TextFieldName temp = TextFieldName.valueForString(example);
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
		    TextFieldName temp = TextFieldName.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of TextFieldName.
	 */
	public void testListEnum() {
 		List<TextFieldName> enumValueList = Arrays.asList(TextFieldName.values());

		List<TextFieldName> enumTestList = new ArrayList<TextFieldName>();
		enumTestList.add(TextFieldName.mainField1);
		enumTestList.add(TextFieldName.mainField2);
		enumTestList.add(TextFieldName.mainField3);
		enumTestList.add(TextFieldName.mainField4);
		enumTestList.add(TextFieldName.statusBar);
		enumTestList.add(TextFieldName.mediaClock);		
		enumTestList.add(TextFieldName.mediaTrack);
		enumTestList.add(TextFieldName.alertText1);	
		enumTestList.add(TextFieldName.alertText2);
		enumTestList.add(TextFieldName.alertText3);	
		enumTestList.add(TextFieldName.scrollableMessageBody);	
		enumTestList.add(TextFieldName.initialInteractionText);
		enumTestList.add(TextFieldName.navigationText1);	
		enumTestList.add(TextFieldName.navigationText2);
		enumTestList.add(TextFieldName.ETA);	
		enumTestList.add(TextFieldName.totalDistance);	
		enumTestList.add(TextFieldName.audioPassThruDisplayText1);
		enumTestList.add(TextFieldName.audioPassThruDisplayText2);
		enumTestList.add(TextFieldName.sliderHeader);
		enumTestList.add(TextFieldName.sliderFooter);
		enumTestList.add(TextFieldName.menuName);
		enumTestList.add(TextFieldName.secondaryText);		
		enumTestList.add(TextFieldName.tertiaryText);
		enumTestList.add(TextFieldName.menuTitle);	
		
		enumTestList.add(TextFieldName.locationName);	
		enumTestList.add(TextFieldName.locationDescription);	
		enumTestList.add(TextFieldName.addressLines);	
		enumTestList.add(TextFieldName.phoneNumber);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}