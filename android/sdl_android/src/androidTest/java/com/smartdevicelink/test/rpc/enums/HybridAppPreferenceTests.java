package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.HybridAppPreference;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.HybridAppPreference}
 */
public class HybridAppPreferenceTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {
		String example = "MOBILE";
		HybridAppPreference enumMobile = HybridAppPreference.valueForString(example);
		example = "CLOUD";
		HybridAppPreference enumCloud = HybridAppPreference.valueForString(example);
		example = "BOTH";
		HybridAppPreference enumBoth = HybridAppPreference.valueForString(example);

		assertNotNull("MOBILE returned null", enumMobile);
		assertNotNull("CLOUD returned null", enumCloud);
		assertNotNull("BOTH returned null", enumBoth);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "deFaUlt";
		try {
			HybridAppPreference temp = HybridAppPreference.valueForString(example);
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
			HybridAppPreference temp = HybridAppPreference.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of HybridAppPreference.
	 */
	public void testListEnum() {
		List<HybridAppPreference> enumValueList = Arrays.asList(HybridAppPreference.values());

		List<HybridAppPreference> enumTestList = new ArrayList<>();
		enumTestList.add(HybridAppPreference.MOBILE);
		enumTestList.add(HybridAppPreference.CLOUD);
		enumTestList.add(HybridAppPreference.BOTH);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}