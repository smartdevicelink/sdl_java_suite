package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.IgnitionStableStatus}
 */
public class IgnitionStableStatusTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "IGNITION_SWITCH_NOT_STABLE";
		IgnitionStableStatus enumIgnitionSwitchNotStable = IgnitionStableStatus.valueForString(example);
		example = "IGNITION_SWITCH_STABLE";
		IgnitionStableStatus enumIgnitionSwitchStable = IgnitionStableStatus.valueForString(example);
		example = "MISSING_FROM_TRANSMITTER";
		IgnitionStableStatus enumMissingFromTransmitter = IgnitionStableStatus.valueForString(example);
		
		assertNotNull("IGNITION_SWITCH_NOT_STABLE returned null", enumIgnitionSwitchNotStable);
		assertNotNull("IGNITION_SWITCH_STABLE returned null", enumIgnitionSwitchStable);
		assertNotNull("MISSING_FROM_TRANSMITTER returned null", enumMissingFromTransmitter);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "iGnitIoN_SwiTch_NoT_StablE";
		try {
		    IgnitionStableStatus temp = IgnitionStableStatus.valueForString(example);
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
		    IgnitionStableStatus temp = IgnitionStableStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of IgnitionStableStatus.
	 */
	public void testListEnum() {
 		List<IgnitionStableStatus> enumValueList = Arrays.asList(IgnitionStableStatus.values());

		List<IgnitionStableStatus> enumTestList = new ArrayList<IgnitionStableStatus>();
		enumTestList.add(IgnitionStableStatus.IGNITION_SWITCH_NOT_STABLE);
		enumTestList.add(IgnitionStableStatus.IGNITION_SWITCH_STABLE);
		enumTestList.add(IgnitionStableStatus.MISSING_FROM_TRANSMITTER);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}