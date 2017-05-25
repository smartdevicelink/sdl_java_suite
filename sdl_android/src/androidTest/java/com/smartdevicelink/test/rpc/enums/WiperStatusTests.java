package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.WiperStatis}
 */
public class WiperStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.off_caps);
		WiperStatus enumOff = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.auto_off_caps);
		WiperStatus enumAutoOff = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.off_moving_caps);
		WiperStatus enumOffMoving = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.man_int_off_caps);
		WiperStatus enumManIntOff = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.man_int_on_caps);
		WiperStatus enumManIntOn = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.man_low_caps);
		WiperStatus enumManLow = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.man_high_caps);
		WiperStatus enumManHigh = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.man_flick_caps);
		WiperStatus enumManFlick = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.wash_caps);
		WiperStatus enumWash = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.auto_low_caps);
		WiperStatus enumAutoLow = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.auto_high_caps);
		WiperStatus enumAutoHigh = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.courtesy_wipe_caps);
		WiperStatus enumCourtesyWipe = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.auto_adjust_caps);
		WiperStatus enumAutoAdjust = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.stalled_caps);
		WiperStatus enumStalled = WiperStatus.valueForString(example);
		example = mContext.getString(R.string.no_data_exists_caps);
		WiperStatus enumNoDataExists = WiperStatus.valueForString(example);
		
		assertNotNull("OFF returned null", enumOff);
		assertNotNull("AUTO_OFF returned null", enumAutoOff);
		assertNotNull("OFF_MOVING returned null", enumOffMoving);
		assertNotNull("MAN_INT_OFF returned null", enumManIntOff);
		assertNotNull("MAN_INT_ON returned null", enumManIntOn);
		assertNotNull("MAN_LOW returned null", enumManLow);
		assertNotNull("MAN_HIGH returned null", enumManHigh);
		assertNotNull("MAN_FLICK returned null", enumManFlick);
		assertNotNull("WASH returned null", enumWash);
		assertNotNull("AUTO_LOW returned null", enumAutoLow);
		assertNotNull("AUTO_HIGH returned null", enumAutoHigh);
		assertNotNull("COURTESYWIPE returned null", enumCourtesyWipe);
		assertNotNull("AUTO_ADJUST returned null", enumAutoAdjust);
		assertNotNull("STALLED returned null", enumStalled);
		assertNotNull("NO_DATA_EXISTS returned null", enumNoDataExists);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    WiperStatus temp = WiperStatus.valueForString(example);
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
		    WiperStatus temp = WiperStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	/**
	 * Verifies the possible enum values of WiperStatus.
	 */
	public void testListEnum() {
 		List<WiperStatus> enumValueList = Arrays.asList(WiperStatus.values());

		List<WiperStatus> enumTestList = new ArrayList<WiperStatus>();
		enumTestList.add(WiperStatus.OFF);
		enumTestList.add(WiperStatus.AUTO_OFF);
		enumTestList.add(WiperStatus.OFF_MOVING);
		enumTestList.add(WiperStatus.MAN_INT_OFF);
		enumTestList.add(WiperStatus.MAN_INT_ON);
		enumTestList.add(WiperStatus.MAN_LOW);		
		enumTestList.add(WiperStatus.MAN_HIGH);
		enumTestList.add(WiperStatus.MAN_FLICK);	
		enumTestList.add(WiperStatus.WASH);
		enumTestList.add(WiperStatus.AUTO_LOW);	
		enumTestList.add(WiperStatus.AUTO_HIGH);	
		enumTestList.add(WiperStatus.COURTESYWIPE);
		enumTestList.add(WiperStatus.AUTO_ADJUST);	
		enumTestList.add(WiperStatus.STALLED);
		enumTestList.add(WiperStatus.NO_DATA_EXISTS);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}