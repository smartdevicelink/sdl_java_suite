package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.TextFieldName}
 */
public class TextFieldNameTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.main_field_one);
		TextFieldName enumMainField1 = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.main_field_two);
		TextFieldName enumMainField2 = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.main_field_three);
		TextFieldName enumMainField3 = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.main_field_four);
		TextFieldName enumMainField4 = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.status_bar);
		TextFieldName enumStatusBar = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.media_clock);
		TextFieldName enumMediaClock = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.media_track);
		TextFieldName enumMediaTrack = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.alert_text_one);
		TextFieldName enumAlertText1 = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.alert_text_two);
		TextFieldName enumAlertText2 = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.alert_text_three);
		TextFieldName enumAlertText3 = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.scrollable_message_body);
		TextFieldName enumScrollableMessageBody = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.initial_interaction_text);
		TextFieldName enumInitialInteractionText = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.navigation_text_one);
		TextFieldName enumNavigationText1 = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.navigation_text_two);
		TextFieldName enumNavigationText2 = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.eta_caps);
		TextFieldName enumEta = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.total_distance);
		TextFieldName enumTotalDistance = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.audio_pass_through_display_text_one);
		TextFieldName enumAudioPassThruDisplayText1 = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.audio_pass_through_display_text_two);
		TextFieldName enumAudioPassThruDisplayText2 = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.slider_header);
		TextFieldName enumSliderHeader = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.slider_footer);
		TextFieldName enumSliderFooter = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.menu_name);
		TextFieldName enumMenuName = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.secondary_text);
		TextFieldName enumSecondaryText = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.tertiary_text);
		TextFieldName enumTertiaryText = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.menu_title);
		TextFieldName enumMenuTitle = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.location_name);
		TextFieldName enumLocName = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.location_description);
		TextFieldName enumLocDesc = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.address_lines);
		TextFieldName enumAddLines = TextFieldName.valueForString(example);
		example = mContext.getString(R.string.phone_number);
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
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    TextFieldName temp = TextFieldName.valueForString(example);
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
		    TextFieldName temp = TextFieldName.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
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

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}