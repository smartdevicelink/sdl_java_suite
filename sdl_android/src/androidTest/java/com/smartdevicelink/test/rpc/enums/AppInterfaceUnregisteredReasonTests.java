package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.AppInterfaceUnregisteredReason;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.AppInterfaceUregisteredReason}
 */
public class AppInterfaceUnregisteredReasonTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.user_exit_caps);
		AppInterfaceUnregisteredReason enumUserExit = AppInterfaceUnregisteredReason.valueForString(example);
		example = mContext.getString(R.string.ignition_off_caps);
		AppInterfaceUnregisteredReason enumIgnitionOff = AppInterfaceUnregisteredReason.valueForString(example);
		example = mContext.getString(R.string.bluetooth_off_caps);
		AppInterfaceUnregisteredReason enumBluetoothOff = AppInterfaceUnregisteredReason.valueForString(example);
		example = mContext.getString(R.string.usb_disconnected_caps);
		AppInterfaceUnregisteredReason enumUsbDisconnected = AppInterfaceUnregisteredReason.valueForString(example);
		example = mContext.getString(R.string.request_while_in_none_hmi_level_caps);
		AppInterfaceUnregisteredReason enumRequestWhileInNoneHmiLevel = AppInterfaceUnregisteredReason.valueForString(example);
		example = mContext.getString(R.string.too_many_requests_caps);
		AppInterfaceUnregisteredReason enumTooManyRequests = AppInterfaceUnregisteredReason.valueForString(example);
		example = mContext.getString(R.string.driver_distraction_violation_caps);
		AppInterfaceUnregisteredReason enumDriverDistractionViolation = AppInterfaceUnregisteredReason.valueForString(example);
		example = mContext.getString(R.string.lang_change_caps);
		AppInterfaceUnregisteredReason enumLanguageChange = AppInterfaceUnregisteredReason.valueForString(example);
		example = mContext.getString(R.string.master_reset_caps);
		AppInterfaceUnregisteredReason enumMasterReset = AppInterfaceUnregisteredReason.valueForString(example);
		example = mContext.getString(R.string.factory_defaults_caps);
		AppInterfaceUnregisteredReason enumFactoryDefaults = AppInterfaceUnregisteredReason.valueForString(example);
		example = mContext.getString(R.string.app_unauth_caps);
		AppInterfaceUnregisteredReason enumAppAuthorized = AppInterfaceUnregisteredReason.valueForString(example);
		example = mContext.getString(R.string.protocol_violation_caps);
		AppInterfaceUnregisteredReason enumProtocolViolation = AppInterfaceUnregisteredReason.valueForString(example);
				
		assertNotNull("USER_EXIT returned null", enumUserExit);
		assertNotNull("IGNITION_OFF returned null", enumIgnitionOff);
		assertNotNull("BLUETOOTH_OFF returned null", enumBluetoothOff);
		assertNotNull("USB_DISCONNECTED returned null", enumUsbDisconnected);
		assertNotNull("REQUEST_WHILE_IN_NONE_HMI_LEVEL returned null", enumRequestWhileInNoneHmiLevel);
		assertNotNull("TOO_MANY_REQUESTS returned null", enumTooManyRequests);
		assertNotNull("DRIVER_DISTRACTION_VIOLATION returned null", enumDriverDistractionViolation);
		assertNotNull("LANGUAGE_CHANGE returned null", enumLanguageChange);
		assertNotNull("MASTER_RESET returned null", enumMasterReset);
		assertNotNull("FACTORY_DEFAULTS returned null", enumFactoryDefaults);
		assertNotNull("APP_UNAUTHORIZED returned null", enumAppAuthorized);
		assertNotNull("PROTOCOL_VIOLATION returned null", enumProtocolViolation);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    AppInterfaceUnregisteredReason temp = AppInterfaceUnregisteredReason.valueForString(example);
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
		    AppInterfaceUnregisteredReason temp = AppInterfaceUnregisteredReason.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of AppInterfaceUnregisteredReason.
	 */
	public void testListEnum() {
 		List<AppInterfaceUnregisteredReason> enumValueList = Arrays.asList(AppInterfaceUnregisteredReason.values());

		List<AppInterfaceUnregisteredReason> enumTestList = new ArrayList<AppInterfaceUnregisteredReason>();
		enumTestList.add(AppInterfaceUnregisteredReason.USER_EXIT);
		enumTestList.add(AppInterfaceUnregisteredReason.IGNITION_OFF);
		enumTestList.add(AppInterfaceUnregisteredReason.BLUETOOTH_OFF);
		enumTestList.add(AppInterfaceUnregisteredReason.USB_DISCONNECTED);
		enumTestList.add(AppInterfaceUnregisteredReason.REQUEST_WHILE_IN_NONE_HMI_LEVEL);
		enumTestList.add(AppInterfaceUnregisteredReason.TOO_MANY_REQUESTS);		
		enumTestList.add(AppInterfaceUnregisteredReason.DRIVER_DISTRACTION_VIOLATION);
		enumTestList.add(AppInterfaceUnregisteredReason.LANGUAGE_CHANGE);	
		enumTestList.add(AppInterfaceUnregisteredReason.MASTER_RESET);
		enumTestList.add(AppInterfaceUnregisteredReason.FACTORY_DEFAULTS);	
		enumTestList.add(AppInterfaceUnregisteredReason.APP_UNAUTHORIZED);
		enumTestList.add(AppInterfaceUnregisteredReason.PROTOCOL_VIOLATION);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}