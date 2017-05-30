package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.GlobalProperty}
 */
public class GlobalPropertyTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.helprompt_caps);
		GlobalProperty enumHelpPrompt = GlobalProperty.valueForString(example);
		example = mContext.getString(R.string.timeoutprompt_caps);
		GlobalProperty enumTimeoutPrompt = GlobalProperty.valueForString(example);
		example = mContext.getString(R.string.vrhelptitle_caps);
		GlobalProperty enumVrHelpTitle = GlobalProperty.valueForString(example);
		example = mContext.getString(R.string.vrhelpitems_caps);
		GlobalProperty enumVrHelpItems = GlobalProperty.valueForString(example);
		example = mContext.getString(R.string.menuname_caps);
		GlobalProperty enumMenuName = GlobalProperty.valueForString(example);
		example = mContext.getString(R.string.menuicons_caps);
		GlobalProperty enumMenuIcon = GlobalProperty.valueForString(example);
		example = mContext.getString(R.string.keyboardprops_caps);
		GlobalProperty enumKeyboardProperties = GlobalProperty.valueForString(example);
		
		assertNotNull("HELPPROMPT returned null", enumHelpPrompt);
		assertNotNull("TIMEOUTPROMPT returned null", enumTimeoutPrompt);
		assertNotNull("VRHELPTITLE returned null", enumVrHelpTitle);
		assertNotNull("VRHELPITEMS returned null", enumVrHelpItems);
		assertNotNull("MENUNAME returned null", enumMenuName);
		assertNotNull("MENUICON returned null", enumMenuIcon);
		assertNotNull("KEYBOARDPROPERTIES returned null", enumKeyboardProperties);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    GlobalProperty temp = GlobalProperty.valueForString(example);
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
		    GlobalProperty temp = GlobalProperty.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of GlobalProperty.
	 */
	public void testListEnum() {
 		List<GlobalProperty> enumValueList = Arrays.asList(GlobalProperty.values());

		List<GlobalProperty> enumTestList = new ArrayList<GlobalProperty>();
		enumTestList.add(GlobalProperty.HELPPROMPT);
		enumTestList.add(GlobalProperty.TIMEOUTPROMPT);
		enumTestList.add(GlobalProperty.VRHELPTITLE);
		enumTestList.add(GlobalProperty.VRHELPITEMS);
		enumTestList.add(GlobalProperty.MENUNAME);
		enumTestList.add(GlobalProperty.MENUICON);		
		enumTestList.add(GlobalProperty.KEYBOARDPROPERTIES);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}