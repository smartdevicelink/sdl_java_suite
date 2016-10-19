package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.FuelCutoffStatus}
 */
public class FuelCutoffStatusTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "TERMINATE_FUEL";
		FuelCutoffStatus enumTerminateFuel = FuelCutoffStatus.valueForString(example);
		example = "NORMAL_OPERATION";
		FuelCutoffStatus enumNormalOperation = FuelCutoffStatus.valueForString(example);
		example = "FAULT";
		FuelCutoffStatus enumFault = FuelCutoffStatus.valueForString(example);
		
		assertNotNull("TERMINATE_FUEL returned null", enumTerminateFuel);
		assertNotNull("NORMAL_OPERATION returned null", enumNormalOperation);
		assertNotNull("FAULT returned null", enumFault);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "tErmINAte_FueL";
		try {
		    FuelCutoffStatus temp = FuelCutoffStatus.valueForString(example);
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
		    FuelCutoffStatus temp = FuelCutoffStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}		

	/**
	 * Verifies the possible enum values of FuelCutoffStatus.
	 */
	public void testListEnum() {
 		List<FuelCutoffStatus> enumValueList = Arrays.asList(FuelCutoffStatus.values());

		List<FuelCutoffStatus> enumTestList = new ArrayList<FuelCutoffStatus>();
		enumTestList.add(FuelCutoffStatus.TERMINATE_FUEL);
		enumTestList.add(FuelCutoffStatus.NORMAL_OPERATION);
		enumTestList.add(FuelCutoffStatus.FAULT);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}