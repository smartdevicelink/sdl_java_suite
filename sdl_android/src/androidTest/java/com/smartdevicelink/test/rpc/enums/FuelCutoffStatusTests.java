package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;

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
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (IllegalArgumentException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    FuelCutoffStatus temp = FuelCutoffStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
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

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}