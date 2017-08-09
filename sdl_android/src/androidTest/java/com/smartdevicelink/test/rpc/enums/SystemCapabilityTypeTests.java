package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.SystemCapabilityType}
 */
public class SystemCapabilityTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "NAVIGATION";
		SystemCapabilityType enumNavigation = SystemCapabilityType.valueForString(example);
		example = "PHONE_CALL";
		SystemCapabilityType enumPhoneCall = SystemCapabilityType.valueForString(example);
		example = "VIDEO_STREAMING";
		SystemCapabilityType enumVideoStreaming = SystemCapabilityType.valueForString(example);
		example = "REMOTE_CONTROL";
		SystemCapabilityType enumRemoteControl = SystemCapabilityType.valueForString(example);

		assertNotNull("NAVIGATION returned null", enumNavigation);
		assertNotNull("PHONE_CALL returned null", enumPhoneCall);
		assertNotNull("VIDEO_STREAMING returned null", enumVideoStreaming);
		assertNotNull("REMOTE_CONTROL returned null", enumRemoteControl);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "nAVIGATION";
		try {
			SystemCapabilityType temp = SystemCapabilityType.valueForString(example);
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
			SystemCapabilityType temp = SystemCapabilityType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of SystemCapabilityType.
	 */
	public void testListEnum() {
 		List<SystemCapabilityType> enumValueList = Arrays.asList(SystemCapabilityType.values());

		List<SystemCapabilityType> enumTestList = new ArrayList<SystemCapabilityType>();
		enumTestList.add(SystemCapabilityType.NAVIGATION);
		enumTestList.add(SystemCapabilityType.PHONE_CALL);
		enumTestList.add(SystemCapabilityType.VIDEO_STREAMING);
		enumTestList.add(SystemCapabilityType.REMOTE_CONTROL);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}