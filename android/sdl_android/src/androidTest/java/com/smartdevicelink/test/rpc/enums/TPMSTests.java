package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.TPMS;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.TPMS}
 */
public class TPMSTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {
		String example = "UNKNOWN";
		TPMS unknown = TPMS.valueForString(example);
		example = "SYSTEM_FAULT";
		TPMS systemFault = TPMS.valueForString(example);
		example = "SENSOR_FAULT";
		TPMS sensorFault = TPMS.valueForString(example);
		example = "LOW";
		TPMS low = TPMS.valueForString(example);
		example = "SYSTEM_ACTIVE";
		TPMS systemActive = TPMS.valueForString(example);
		example = "TRAIN";
		TPMS train = TPMS.valueForString(example);
		example = "TRAINING_COMPLETE";
		TPMS trainingComplete = TPMS.valueForString(example);
		example = "NOT_TRAINED";
		TPMS notTrained = TPMS.valueForString(example);

		assertNotNull("UNKNOWN returned null", unknown);
		assertNotNull("SYSTEM_FAULT returned null", systemFault);
		assertNotNull("SENSOR_FAULT returned null", sensorFault);
		assertNotNull("LOW returned null", low);
		assertNotNull("SYSTEM_ACTIVE returned null", systemActive);
		assertNotNull("TRAIN returned null", train);
		assertNotNull("TRAINING_COMPLETE returned null", trainingComplete);
		assertNotNull("NOT_TRAINED returned null", notTrained);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "IsHoUldBeNulL";
		try {
			TPMS temp = TPMS.valueForString(example);
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
			TPMS temp = TPMS.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of TPMS.
	 */
	public void testListEnum() {
		List<TPMS> enumValueList = Arrays.asList(TPMS.values());

		List<TPMS> enumTestList = new ArrayList<>();
		enumTestList.add(TPMS.UNKNOWN);
		enumTestList.add(TPMS.SYSTEM_FAULT);
		enumTestList.add(TPMS.SENSOR_FAULT);
		enumTestList.add(TPMS.LOW);
		enumTestList.add(TPMS.SYSTEM_ACTIVE);
		enumTestList.add(TPMS.TRAIN);
		enumTestList.add(TPMS.TRAINING_COMPLETE);
		enumTestList.add(TPMS.NOT_TRAINED);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}