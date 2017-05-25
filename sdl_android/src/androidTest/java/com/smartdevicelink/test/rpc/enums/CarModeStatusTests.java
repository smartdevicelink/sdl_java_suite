package com.smartdevicelink.test.rpc.enums;

import android.content.res.Resources;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.CarModeStatus;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.CarModeStatus}
 */
public class CarModeStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.normal_caps);
		CarModeStatus enumNormal = CarModeStatus.valueForString(example);
		example = mContext.getString(R.string.factory_caps);
		CarModeStatus enumFactory = CarModeStatus.valueForString(example);
		example = mContext.getString(R.string.transport_caps);
		CarModeStatus enumTransport = CarModeStatus.valueForString(example);
		example = mContext.getString(R.string.crash_caps);
		CarModeStatus enumCrash = CarModeStatus.valueForString(example);
		
		assertNotNull("NORMAL returned null", enumNormal);
		assertNotNull("FACTORY returned null", enumFactory);
		assertNotNull("TRANSPORT returned null", enumTransport);
		assertNotNull("CRASH returned null", enumCrash);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    CarModeStatus temp = CarModeStatus.valueForString(example);
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
		    CarModeStatus temp = CarModeStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of CarModeStatus.
	 */
	public void testListEnum() {
 		List<CarModeStatus> enumValueList = Arrays.asList(CarModeStatus.values());

		List<CarModeStatus> enumTestList = new ArrayList<CarModeStatus>();
		enumTestList.add(CarModeStatus.NORMAL);
		enumTestList.add(CarModeStatus.FACTORY);
		enumTestList.add(CarModeStatus.TRANSPORT);
		enumTestList.add(CarModeStatus.CRASH);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}