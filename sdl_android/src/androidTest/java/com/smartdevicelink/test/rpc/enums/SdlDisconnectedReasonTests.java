package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.AppInterfaceUnregisteredReason;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.SdlDisconnectedReason}
 */
public class SdlDisconnectedReasonTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.user_exit_caps);
		SdlDisconnectedReason enumUserExit = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.iginition_off_caps);
		SdlDisconnectedReason enumIgnitionOff = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.bluetooth_off_caps);
		SdlDisconnectedReason enumBluetoothOff = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.usb_disconnected_caps);
		SdlDisconnectedReason enumUsbDisconnected = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.request_while_in_none_hmi_level_caps);
		SdlDisconnectedReason enumRequestWhileInNoneHmiLevel = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.too_many_requests_caps);
		SdlDisconnectedReason enumTooManyRequests = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.driver_distraction_violation_caps);
		SdlDisconnectedReason enumDriverDistractionViolation = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.lang_change_caps);
		SdlDisconnectedReason enumLanuguageChange = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.master_reset_caps);
		SdlDisconnectedReason enumMasterReset = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.factory_defaults_caps);
		SdlDisconnectedReason enumFactoryDefaults = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.transport_error);
		SdlDisconnectedReason enumTransportError = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.app_req_disconnect_caps);
		SdlDisconnectedReason enumApplicationRequestedDisconnect = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.default_caps);
		SdlDisconnectedReason enumDefault = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.trNSPORT_DISCONNECT_CAPS);
		SdlDisconnectedReason enumTransportDisconnect = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.hb_timeout);
		SdlDisconnectedReason enumHbTimeout = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.bt_disabled_caps);
		SdlDisconnectedReason enumBluetoothDisabled = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.bt_adapter_error);
		SdlDisconnectedReason enumBluetoothAdapterError = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.sdl_reg_error_caps);
		SdlDisconnectedReason enumSdlRegistrationError = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.app_interface_unreg_caps);
		SdlDisconnectedReason enumAppInterfaceUnreg = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.generic_error_caps);
		SdlDisconnectedReason enumGenericError = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.legacy_bt_mode_enabled_caps);
		SdlDisconnectedReason enumLegacyMode = SdlDisconnectedReason.valueForString(example);
		example = mContext.getString(R.string.rpc_session_ended_caps);
		SdlDisconnectedReason enumRpcSessionEnded = SdlDisconnectedReason.valueForString(example);
		
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
		assertNotNull("LEGACY_BLUETOOTH_MODE_ENABLED returned null", enumLegacyMode);
		assertNotNull("RPC_SESSION_ENDED returned null", enumRpcSessionEnded);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    SdlDisconnectedReason temp = SdlDisconnectedReason.valueForString(example);
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
		    SdlDisconnectedReason temp = SdlDisconnectedReason.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}		

	/**
	 * Verifies the possible enum values of SdlDisconnectedReason.
	 */
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
		enumTestList.add(SdlDisconnectedReason.LEGACY_BLUETOOTH_MODE_ENABLED);
		enumTestList.add(SdlDisconnectedReason.RPC_SESSION_ENDED);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
	/**
	 * Verifies the valid returns of the conversion method,
	 * {@link com.smartdevicelink.rpc.enums.SdlDisconnectedReason#convertAppInterfaceunregisteredReason(AppInterfaceUnregisteredReason)}
	 */
	public void testConvertMethod () {		
		assertEquals(Test.MATCH, SdlDisconnectedReason.DEFAULT, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason.APP_UNAUTHORIZED));
		assertEquals(Test.MATCH, SdlDisconnectedReason.BLUETOOTH_OFF, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason.BLUETOOTH_OFF));
		assertEquals(Test.MATCH, SdlDisconnectedReason.DRIVER_DISTRACTION_VIOLATION, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason.DRIVER_DISTRACTION_VIOLATION));
		assertEquals(Test.MATCH, SdlDisconnectedReason.FACTORY_DEFAULTS, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason.FACTORY_DEFAULTS));
		assertEquals(Test.MATCH, SdlDisconnectedReason.IGNITION_OFF, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason.IGNITION_OFF));
		assertEquals(Test.MATCH, SdlDisconnectedReason.LANGUAGE_CHANGE, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason.LANGUAGE_CHANGE));
		assertEquals(Test.MATCH, SdlDisconnectedReason.MASTER_RESET, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason.MASTER_RESET));
		assertEquals(Test.MATCH, SdlDisconnectedReason.REQUEST_WHILE_IN_NONE_HMI_LEVEL, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason.REQUEST_WHILE_IN_NONE_HMI_LEVEL));
		assertEquals(Test.MATCH, SdlDisconnectedReason.TOO_MANY_REQUESTS, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason.TOO_MANY_REQUESTS));
		assertEquals(Test.MATCH, SdlDisconnectedReason.USB_DISCONNECTED, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason.USB_DISCONNECTED));
		assertEquals(Test.MATCH, SdlDisconnectedReason.USER_EXIT, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason.USER_EXIT));
		assertNull(Test.MATCH, SdlDisconnectedReason.convertAppInterfaceUnregisteredReason(null));
	}
	
}