package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.AppServiceType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.AppServiceType}
 */
public class AppServiceTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {
		String example = "MEDIA";
		AppServiceType enumMedia = AppServiceType.valueForString(example);
		example = "WEATHER";
		AppServiceType enumWeather = AppServiceType.valueForString(example);
		example = "NAVIGATION";
		AppServiceType enumNavigation = AppServiceType.valueForString(example);

		assertNotNull("MEDIA returned null", enumMedia);
		assertNotNull("WEATHER returned null", enumWeather);
		assertNotNull("NAVIGATION returned null", enumNavigation);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "MedIas";
		try {
			AppServiceType temp = AppServiceType.valueForString(example);
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
			AppServiceType temp = AppServiceType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of AppServiceType.
	 */
	public void testListEnum() {
		List<AppServiceType> enumValueList = Arrays.asList(AppServiceType.values());

		List<AppServiceType> enumTestList = new ArrayList<>();
		enumTestList.add(AppServiceType.MEDIA);
		enumTestList.add(AppServiceType.NAVIGATION);
		enumTestList.add(AppServiceType.WEATHER);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}