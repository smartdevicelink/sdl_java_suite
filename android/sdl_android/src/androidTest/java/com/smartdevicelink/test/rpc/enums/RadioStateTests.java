package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.RadioState;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.enums.RadioState}
 */
public class RadioStateTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "ACQUIRING";
		RadioState enumAcquiring = RadioState.valueForString(example);
		example = "ACQUIRED";
		RadioState enumAcquired = RadioState.valueForString(example);
		example = "MULTICAST";
		RadioState enumMultiCast = RadioState.valueForString(example);
		example = "NOT_FOUND";
		RadioState enumNotFound = RadioState.valueForString(example);

		assertNotNull("ACQUIRING returned null", enumAcquiring);
		assertNotNull("ACQUIRED returned null", enumAcquired);
		assertNotNull("MULTICAST returned null", enumMultiCast);
		assertNotNull("NOT_FOUND returned null", enumNotFound);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "aCQUIRING";
		try {
			RadioState temp = RadioState.valueForString(example);
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
			RadioState temp = RadioState.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of RadioState.
	 */
	public void testListEnum() {
 		List<RadioState> enumValueList = Arrays.asList(RadioState.values());

		List<RadioState> enumTestList = new ArrayList<RadioState>();
		enumTestList.add(RadioState.ACQUIRING);
		enumTestList.add(RadioState.ACQUIRED);
		enumTestList.add(RadioState.MULTICAST);
		enumTestList.add(RadioState.NOT_FOUND);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}