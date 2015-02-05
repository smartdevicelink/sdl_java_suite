package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;

public class SdlDisconnectedReasonTests extends TestCase {
	
	public void testValidEnums () {	
		String example = "USER_EXIT";
		SdlDisconnectedReason enumUserExit = SdlDisconnectedReason.valueForString(example);
		example = "IGNITION_OFF";
		SdlDisconnectedReason enumIgnitionOff = SdlDisconnectedReason.valueForString(example);
		example = "BLUETOOTH_OFF";
		SdlDisconnectedReason enumBluetoothOff = SdlDisconnectedReason.valueForString(example);
		example = "USB_DISCONNECTED";
		SdlDisconnectedReason enumUsbDisconnected = SdlDisconnectedReason.valueForString(example);
		example = "REQUEST_WHILE_IN_NONE_HMI_LEVEL";
		SdlDisconnectedReason enumRequestWhileInNoneHmiLevel = SdlDisconnectedReason.valueForString(example);
		example = "TOO_MANY_REQUESTS";
		SdlDisconnectedReason enumTooManyRequests = SdlDisconnectedReason.valueForString(example);
		example = "DRIVER_DISTRACTION_VIOLATION";
		SdlDisconnectedReason enumDriverDistractionViolation = SdlDisconnectedReason.valueForString(example);
		example = "LANGUAGE_CHANGE";
		SdlDisconnectedReason enumLanuguageChange = SdlDisconnectedReason.valueForString(example);
		example = "MASTER_RESET";
		SdlDisconnectedReason enumMasterReset = SdlDisconnectedReason.valueForString(example);
		example = "FACTORY_DEFAULTS";
		SdlDisconnectedReason enumFactoryDefaults = SdlDisconnectedReason.valueForString(example);
		example = "TRANSPORT_ERROR";
		SdlDisconnectedReason enumTransportError = SdlDisconnectedReason.valueForString(example);
		example = "APPLICATION_REQUESTED_DISCONNECT";
		SdlDisconnectedReason enumApplicationRequestedDisconnect = SdlDisconnectedReason.valueForString(example);
		example = "DEFAULT";
		SdlDisconnectedReason enumDefault = SdlDisconnectedReason.valueForString(example);
		example = "TRANSPORT_DISCONNECT";
		SdlDisconnectedReason enumTransportDisconnect = SdlDisconnectedReason.valueForString(example);
		example = "HB_TIMEOUT";
		SdlDisconnectedReason enumHbTimeout = SdlDisconnectedReason.valueForString(example);
		example = "BLUETOOTH_DISABLED";
		SdlDisconnectedReason enumBluetoothDisabled = SdlDisconnectedReason.valueForString(example);
		example = "BLUETOOTH_ADAPTER_ERROR";
		SdlDisconnectedReason enumBluetoothAdapterError = SdlDisconnectedReason.valueForString(example);
		example = "SDL_REGISTRATION_ERROR";
		SdlDisconnectedReason enumSdlRegistrationError = SdlDisconnectedReason.valueForString(example);
		example = "APP_INTERFACE_UNREG";
		SdlDisconnectedReason enumAppInterfaceUnreg = SdlDisconnectedReason.valueForString(example);
		example = "GENERIC_ERROR";
		SdlDisconnectedReason enumGenericError = SdlDisconnectedReason.valueForString(example);
		
		assertNotNull("USER_EXIT returned null", enumUserExit);
		assertNotNull("IGNITION_OFF returned null", enumIgnitionOff);
		assertNotNull("BLUETOOTH_OFF returned null", enumBluetoothOff);
		assertNotNull("USB_DISCONNECTED returned null", enumUsbDisconnected);
		assertNotNull("REQUEST_WHILE_IN_NONE_HMI_LEVEL returned null", enumRequestWhileInNoneHmiLevel);
		assertNotNull("TOO_MANY_REQUESTS returned null", enumTooManyRequests);
		assertNotNull("DRIVER_DISTRACTION_VIOLATION returned null", enumDriverDistractionViolation);
		assertNotNull("LANGUAGE_CHANGE returned null", enumLanuguageChange);
		assertNotNull("MASTER_RESET returned null", enumMasterReset);
		assertNotNull("FACTORY_DEFAULTS returned null", enumFactoryDefaults);
		assertNotNull("TRANSPORT_ERROR returned null", enumTransportError);
		assertNotNull("APPLICATION_REQUESTED_DISCONNECT returned null", enumApplicationRequestedDisconnect);
		assertNotNull("DEFAULT returned null", enumDefault);
		assertNotNull("TRANSPORT_DISCONNECT returned null", enumTransportDisconnect);
		assertNotNull("HB_TIMEOUT returned null", enumHbTimeout);
		assertNotNull("BLUETOOTH_DISABLED returned null", enumBluetoothDisabled);
		assertNotNull("BLUETOOTH_ADAPTER_ERROR returned null", enumBluetoothAdapterError);
		assertNotNull("SDL_REGISTRATION_ERROR returned null", enumSdlRegistrationError);
		assertNotNull("APP_INTERFACE_UNREG returned null", enumAppInterfaceUnreg);
		assertNotNull("GENERIC_ERROR returned null", enumGenericError);
	}
	
	public void testInvalidEnum () {
		String example = "uSer_ExiT";
		try {
			SdlDisconnectedReason.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			SdlDisconnectedReason.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<SdlDisconnectedReason> enumValueList = Arrays.asList(SdlDisconnectedReason.values());

		List<SdlDisconnectedReason> enumTestList = new ArrayList<SdlDisconnectedReason>();
		enumTestList.add(SdlDisconnectedReason.USER_EXIT);
		enumTestList.add(SdlDisconnectedReason.IGNITION_OFF);
		enumTestList.add(SdlDisconnectedReason.BLUETOOTH_OFF);
		enumTestList.add(SdlDisconnectedReason.USB_DISCONNECTED);
		enumTestList.add(SdlDisconnectedReason.REQUEST_WHILE_IN_NONE_HMI_LEVEL);
		enumTestList.add(SdlDisconnectedReason.TOO_MANY_REQUESTS);		
		enumTestList.add(SdlDisconnectedReason.DRIVER_DISTRACTION_VIOLATION);
		enumTestList.add(SdlDisconnectedReason.LANGUAGE_CHANGE);	
		enumTestList.add(SdlDisconnectedReason.MASTER_RESET);
		enumTestList.add(SdlDisconnectedReason.FACTORY_DEFAULTS);	
		enumTestList.add(SdlDisconnectedReason.TRANSPORT_ERROR);	
		enumTestList.add(SdlDisconnectedReason.APPLICATION_REQUESTED_DISCONNECT);
		enumTestList.add(SdlDisconnectedReason.DEFAULT);	
		enumTestList.add(SdlDisconnectedReason.TRANSPORT_DISCONNECT);
		enumTestList.add(SdlDisconnectedReason.HB_TIMEOUT);	
		enumTestList.add(SdlDisconnectedReason.BLUETOOTH_DISABLED);	
		enumTestList.add(SdlDisconnectedReason.BLUETOOTH_ADAPTER_ERROR);
		enumTestList.add(SdlDisconnectedReason.SDL_REGISTRATION_ERROR);
		enumTestList.add(SdlDisconnectedReason.APP_INTERFACE_UNREG);
		enumTestList.add(SdlDisconnectedReason.GENERIC_ERROR);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
	//TODO: add test cases for convertAppInterfaceUnregisteredReason method
	
}
