package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.CompassDirection;

public class CompassDirectionTests extends TestCase {

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
	
	public void testInvalidEnum () {
		String example = "noRTh";
		try {
			CompassDirection.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			CompassDirection.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
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
