package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.VentilationMode;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.enums.VentilationMode}
 */
public class VentilationModeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "UPPER";
		VentilationMode enumUpper = VentilationMode.valueForString(example);
		example = "LOWER";
		VentilationMode enumLower = VentilationMode.valueForString(example);
		example = "BOTH";
		VentilationMode enumBoth = VentilationMode.valueForString(example);
		example = "NONE";
		VentilationMode enumNone = VentilationMode.valueForString(example);

		assertNotNull("UPPER returned null", enumUpper);
		assertNotNull("LOWER returned null", enumLower);
		assertNotNull("BOTH returned null", enumBoth);
		assertNotNull("NONE returned null", enumNone);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "uPPER";
		try {
			VentilationMode temp = VentilationMode.valueForString(example);
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
			VentilationMode temp = VentilationMode.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of VentilationMode.
	 */
	public void testListEnum() {
 		List<VentilationMode> enumValueList = Arrays.asList(VentilationMode.values());

		List<VentilationMode> enumTestList = new ArrayList<VentilationMode>();
		enumTestList.add(VentilationMode.UPPER);
		enumTestList.add(VentilationMode.LOWER);
		enumTestList.add(VentilationMode.BOTH);
		enumTestList.add(VentilationMode.NONE);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}