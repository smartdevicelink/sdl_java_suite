package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.AppInterfaceUnregisteredReason;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.AppInterfaceUregisteredReason}
 */
public class AppInterfaceUnregisteredReasonTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "USER_EXIT";
		AppInterfaceUnregisteredReason enumUserExit = AppInterfaceUnregisteredReason.valueForString(example);
		example = "IGNITION_OFF";
		AppInterfaceUnregisteredReason enumIgnitionOff = AppInterfaceUnregisteredReason.valueForString(example);
		example = "BLUETOOTH_OFF";
		AppInterfaceUnregisteredReason enumBluetoothOff = AppInterfaceUnregisteredReason.valueForString(example);
		example = "USB_DISCONNECTED";
		AppInterfaceUnregisteredReason enumUsbDisconnected = AppInterfaceUnregisteredReason.valueForString(example);
		example = "REQUEST_WHILE_IN_NONE_HMI_LEVEL";
		AppInterfaceUnregisteredReason enumRequestWhileInNoneHmiLevel = AppInterfaceUnregisteredReason.valueForString(example);
		example = "TOO_MANY_REQUESTS";
		AppInterfaceUnregisteredReason enumTooManyRequests = AppInterfaceUnregisteredReason.valueForString(example);
		example = "DRIVER_DISTRACTION_VIOLATION";
		AppInterfaceUnregisteredReason enumDriverDistractionViolation = AppInterfaceUnregisteredReason.valueForString(example);
		example = "LANGUAGE_CHANGE";
		AppInterfaceUnregisteredReason enumLanguageChange = AppInterfaceUnregisteredReason.valueForString(example);
		example = "MASTER_RESET";
		AppInterfaceUnregisteredReason enumMasterReset = AppInterfaceUnregisteredReason.valueForString(example);
		example = "FACTORY_DEFAULTS";
		AppInterfaceUnregisteredReason enumFactoryDefaults = AppInterfaceUnregisteredReason.valueForString(example);
		example = "APP_UNAUTHORIZED";
		AppInterfaceUnregisteredReason enumAppAuthorized = AppInterfaceUnregisteredReason.valueForString(example);
		example = "PROTOCOL_VIOLATION";
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
		String example = "uSer_ExiT";
		try {
		    AppInterfaceUnregisteredReason temp = AppInterfaceUnregisteredReason.valueForString(example);
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
		    AppInterfaceUnregisteredReason temp = AppInterfaceUnregisteredReason.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}