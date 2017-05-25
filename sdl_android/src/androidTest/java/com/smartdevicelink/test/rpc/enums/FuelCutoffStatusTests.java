package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.FuelCutoffStatus}
 */
public class FuelCutoffStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.term_fuel_caps);
		FuelCutoffStatus enumTerminateFuel = FuelCutoffStatus.valueForString(example);
		example = mContext.getString(R.string.norm_op_caps);
		FuelCutoffStatus enumNormalOperation = FuelCutoffStatus.valueForString(example);
		example = mContext.getString(R.string.fault_caps);
		FuelCutoffStatus enumFault = FuelCutoffStatus.valueForString(example);
		
		assertNotNull("TERMINATE_FUEL returned null", enumTerminateFuel);
		assertNotNull("NORMAL_OPERATION returned null", enumNormalOperation);
		assertNotNull("FAULT returned null", enumFault);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
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