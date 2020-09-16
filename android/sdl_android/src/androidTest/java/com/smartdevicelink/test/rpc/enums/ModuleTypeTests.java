package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.ModuleType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.ModuleType}
 */
public class ModuleTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums() {
		String example = "CLIMATE";
		ModuleType enumClimate = ModuleType.valueForString(example);
		example = "RADIO";
		ModuleType enumRadio = ModuleType.valueForString(example);
		example = "SEAT";
		ModuleType enumSeat = ModuleType.valueForString(example);
		example = "AUDIO";
		ModuleType enumAudio = ModuleType.valueForString(example);
		example = "LIGHT";
		ModuleType enumLight = ModuleType.valueForString(example);
		example = "HMI_SETTINGS";
		ModuleType enumHmiSettings = ModuleType.valueForString(example);

		assertNotNull("CLIMATE returned null", enumClimate);
		assertNotNull("RADIO returned null", enumRadio);
		assertNotNull("SEAT returned null", enumSeat);
		assertNotNull("AUDIO returned null", enumAudio);
		assertNotNull("LIGHT returned null", enumLight);
		assertNotNull("HMI_SETTINGS returned null", enumHmiSettings);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum() {
		String example = "cLIMATE";
		try {
			ModuleType temp = ModuleType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (IllegalArgumentException exception) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum() {
		String example = null;
		try {
			ModuleType temp = ModuleType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of ModuleType.
	 */
	public void testListEnum() {
		List<ModuleType> enumValueList = Arrays.asList(ModuleType.values());

		List<ModuleType> enumTestList = new ArrayList<ModuleType>();
		enumTestList.add(ModuleType.CLIMATE);
		enumTestList.add(ModuleType.RADIO);
		enumTestList.add(ModuleType.SEAT);
		enumTestList.add(ModuleType.AUDIO);
		enumTestList.add(ModuleType.LIGHT);
		enumTestList.add(ModuleType.HMI_SETTINGS);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}