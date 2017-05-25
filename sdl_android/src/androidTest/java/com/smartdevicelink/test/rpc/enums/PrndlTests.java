package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;


/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.Prndl}
 */
public class PrndlTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.park_caps);
		PRNDL enumPark = PRNDL.valueForString(example);
		example = mContext.getString(R.string.reverse_caps);
		PRNDL enumReverse = PRNDL.valueForString(example);
		example = mContext.getString(R.string.neutral_caps);
		PRNDL enumNeutral = PRNDL.valueForString(example);
		example = mContext.getString(R.string.drive_caps);
		PRNDL enumDrive = PRNDL.valueForString(example);
		example = mContext.getString(R.string.sport_caps);
		PRNDL enumSport = PRNDL.valueForString(example);
		example = mContext.getString(R.string.low_gear_caps);
		PRNDL enumLowGear = PRNDL.valueForString(example);
		example = mContext.getString(R.string.first_caps);
		PRNDL enumFirst = PRNDL.valueForString(example);
		example = mContext.getString(R.string.second_caps);
		PRNDL enumSecond = PRNDL.valueForString(example);
		example = mContext.getString(R.string.third_caps);
		PRNDL enumThird = PRNDL.valueForString(example);
		example = mContext.getString(R.string.fourth_caps);
		PRNDL enumFourth = PRNDL.valueForString(example);
		example = mContext.getString(R.string.fifth_caps);
		PRNDL enumFifth = PRNDL.valueForString(example);
		example = mContext.getString(R.string.sixth_caps);
		PRNDL enumSixth = PRNDL.valueForString(example);
		example = mContext.getString(R.string.seventh_caps);
		PRNDL enumSeventh = PRNDL.valueForString(example);
		example = mContext.getString(R.string.eighth_caps);
		PRNDL enumEighth = PRNDL.valueForString(example);
		example = mContext.getString(R.string.unknown_caps);
		PRNDL enumUnknown = PRNDL.valueForString(example);
		example = mContext.getString(R.string.fault_caps);
		PRNDL enumFault = PRNDL.valueForString(example);
		
		assertNotNull("PARK returned null", enumPark);
		assertNotNull("REVERSE returned null", enumReverse);
		assertNotNull("NEUTRAL returned null", enumNeutral);
		assertNotNull("DRIVE returned null", enumDrive);
		assertNotNull("SPORT returned null", enumSport);
		assertNotNull("LOWGEAR returned null", enumLowGear);
		assertNotNull("FIRST returned null", enumFirst);
		assertNotNull("SECOND returned null", enumSecond);
		assertNotNull("THIRD returned null", enumThird);
		assertNotNull("FOURTH returned null", enumFourth);
		assertNotNull("FIFTH returned null", enumFifth);
		assertNotNull("SIXTH returned null", enumSixth);
		assertNotNull("SEVENTH returned null", enumSeventh);
		assertNotNull("EIGHTH returned null", enumEighth);
		assertNotNull("UNKNOWN returned null", enumUnknown);
		assertNotNull("FAULT returned null", enumFault);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    PRNDL temp = PRNDL.valueForString(example);
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
		    PRNDL temp = PRNDL.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of PRNDL.
	 */
	public void testListEnum() {
 		List<PRNDL> enumValueList = Arrays.asList(PRNDL.values());

		List<PRNDL> enumTestList = new ArrayList<PRNDL>();
		enumTestList.add(PRNDL.PARK);
		enumTestList.add(PRNDL.REVERSE);
		enumTestList.add(PRNDL.NEUTRAL);
		enumTestList.add(PRNDL.DRIVE);
		enumTestList.add(PRNDL.SPORT);
		enumTestList.add(PRNDL.LOWGEAR);		
		enumTestList.add(PRNDL.FIRST);
		enumTestList.add(PRNDL.SECOND);	
		enumTestList.add(PRNDL.THIRD);
		enumTestList.add(PRNDL.FOURTH);	
		enumTestList.add(PRNDL.FIFTH);	
		enumTestList.add(PRNDL.SIXTH);
		enumTestList.add(PRNDL.SEVENTH);	
		enumTestList.add(PRNDL.EIGHTH);
		enumTestList.add(PRNDL.UNKNOWN);	
		enumTestList.add(PRNDL.FAULT);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}