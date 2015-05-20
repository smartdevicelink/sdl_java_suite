package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.CompassDirection;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.CompassDirection}
 */
public class CompassDirectionTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "NORTH";
		CompassDirection enumNorth = CompassDirection.valueForString(example);
		example = "NORTHWEST";
		CompassDirection enumNorthWest = CompassDirection.valueForString(example);
		example = "WEST";
		CompassDirection enumWest = CompassDirection.valueForString(example);
		example = "SOUTHWEST";
		CompassDirection enumSouthWest = CompassDirection.valueForString(example);
		example = "SOUTH";
		CompassDirection enumSouth = CompassDirection.valueForString(example);
		example = "SOUTHEAST";
		CompassDirection enumSouthEast = CompassDirection.valueForString(example);
		example = "EAST";
		CompassDirection enumEast = CompassDirection.valueForString(example);
		example = "NORTHEAST";
		CompassDirection enumNorthEast = CompassDirection.valueForString(example);
		
		assertNotNull("NORTH returned null", enumNorth);
		assertNotNull("NORTHWEST returned null", enumNorthWest);
		assertNotNull("WEST returned null", enumWest);
		assertNotNull("SOUTHWEST returned null", enumSouthWest);
		assertNotNull("SOUTH returned null", enumSouth);
		assertNotNull("SOUTHEAST returned null", enumSouthEast);
		assertNotNull("EAST returned null", enumEast);
		assertNotNull("NORTHEAST returned null", enumNorthEast);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "noRTh";
		try {
		    CompassDirection temp = CompassDirection.valueForString(example);
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
		    CompassDirection temp = CompassDirection.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of CompassDirection.
	 */
	public void testListEnum() {
 		List<CompassDirection> enumValueList = Arrays.asList(CompassDirection.values());

		List<CompassDirection> enumTestList = new ArrayList<CompassDirection>();
		enumTestList.add(CompassDirection.NORTH);
		enumTestList.add(CompassDirection.NORTHWEST);
		enumTestList.add(CompassDirection.WEST);
		enumTestList.add(CompassDirection.SOUTHWEST);
		enumTestList.add(CompassDirection.SOUTH);
		enumTestList.add(CompassDirection.SOUTHEAST);		
		enumTestList.add(CompassDirection.EAST);
		enumTestList.add(CompassDirection.NORTHEAST);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}