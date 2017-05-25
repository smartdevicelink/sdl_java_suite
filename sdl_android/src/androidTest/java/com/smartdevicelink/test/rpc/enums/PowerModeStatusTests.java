package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.PowerModeStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.PowerModeStatus}
 */
public class PowerModeStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.key_out_caps);
		PowerModeStatus enumKeyOut = PowerModeStatus.valueForString(example);
		example = mContext.getString(R.string.key_recently_out_caps);
		PowerModeStatus enumKeyRecentlyOut = PowerModeStatus.valueForString(example);
		example = mContext.getString(R.string.key_approved_zero_caps);
		PowerModeStatus enumKeyApproved0 = PowerModeStatus.valueForString(example);
		example = mContext.getString(R.string.post_accessory_zero_caps);
		PowerModeStatus enumPostAccessory0 = PowerModeStatus.valueForString(example);
		example = mContext.getString(R.string.accessory_one_caps);
		PowerModeStatus enumAccessory1 = PowerModeStatus.valueForString(example);
		example = mContext.getString(R.string.post_iginition_one_caps);
		PowerModeStatus enumPostIgnition1 = PowerModeStatus.valueForString(example);
		example = mContext.getString(R.string.iginition_on_two_caps);
		PowerModeStatus enumIgnitionOn2 = PowerModeStatus.valueForString(example);
		example = mContext.getString(R.string.running_two_caps);
		PowerModeStatus enumRunning2 = PowerModeStatus.valueForString(example);
		example = mContext.getString(R.string.crank_three_caps);
		PowerModeStatus enumCrank3 = PowerModeStatus.valueForString(example);
		
		assertNotNull("KEY_OUT returned null", enumKeyOut);
		assertNotNull("KEY_RECENTLY_OUT returned null", enumKeyRecentlyOut);
		assertNotNull("KEY_APPROVED_0 returned null", enumKeyApproved0);
		assertNotNull("POST_ACCESORY_0 returned null", enumPostAccessory0);
		assertNotNull("ACCESORY_1 returned null", enumAccessory1);
		assertNotNull("POST_IGNITION_1 returned null", enumPostIgnition1);
		assertNotNull("IGNITION_ON_2 returned null", enumIgnitionOn2);
		assertNotNull("RUNNING_2 returned null", enumRunning2);
		assertNotNull("CRANK_3 returned null", enumCrank3);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    PowerModeStatus temp = PowerModeStatus.valueForString(example);
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
		    PowerModeStatus temp = PowerModeStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of PowerModeStatus.
	 */
	public void testListEnum() {
 		List<PowerModeStatus> enumValueList = Arrays.asList(PowerModeStatus.values());

		List<PowerModeStatus> enumTestList = new ArrayList<PowerModeStatus>();
		enumTestList.add(PowerModeStatus.KEY_OUT);
		enumTestList.add(PowerModeStatus.KEY_RECENTLY_OUT);
		enumTestList.add(PowerModeStatus.KEY_APPROVED_0);
		enumTestList.add(PowerModeStatus.POST_ACCESORY_0);
		enumTestList.add(PowerModeStatus.ACCESORY_1);
		enumTestList.add(PowerModeStatus.POST_IGNITION_1);		
		enumTestList.add(PowerModeStatus.IGNITION_ON_2);
		enumTestList.add(PowerModeStatus.RUNNING_2);	
		enumTestList.add(PowerModeStatus.CRANK_3);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}