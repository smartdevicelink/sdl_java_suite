package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.SystemContext}
 */
public class SystemContextTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.main_caps);
		SystemContext enumMain = SystemContext.valueForString(example);
		example = mContext.getString(R.string.vr_session_caps);
		SystemContext enumVrSession = SystemContext.valueForString(example);
		example = mContext.getString(R.string.menu_caps);
		SystemContext enumMenu = SystemContext.valueForString(example);
		example = mContext.getString(R.string.hmi_obscured_caps);
		SystemContext enumHmiObscured = SystemContext.valueForString(example);
		example = mContext.getString(R.string.alert_caps);
		SystemContext enumAlert = SystemContext.valueForString(example);
		
		assertNotNull("MAIN returned null", enumMain);
		assertNotNull("VRSESSION returned null", enumVrSession);
		assertNotNull("MENU returned null", enumMenu);
		assertNotNull("HMI_OBSCURED returned null", enumHmiObscured);
		assertNotNull("ALERT returned null", enumAlert);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    SystemContext temp = SystemContext.valueForString(example);
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
		    SystemContext temp = SystemContext.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies the possible enum values of SystemContext.
	 */
	public void testListEnum() {
 		List<SystemContext> enumValueList = Arrays.asList(SystemContext.values());

		List<SystemContext> enumTestList = new ArrayList<SystemContext>();
		enumTestList.add(SystemContext.SYSCTXT_MAIN);
		enumTestList.add(SystemContext.SYSCTXT_VRSESSION);
		enumTestList.add(SystemContext.SYSCTXT_MENU);
		enumTestList.add(SystemContext.SYSCTXT_HMI_OBSCURED);
		enumTestList.add(SystemContext.SYSCTXT_ALERT);
		
		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}