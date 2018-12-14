package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.AmbientLightStatus}
 */
public class AmbientLightStatusTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		
		String example = "DAY";
		AmbientLightStatus enumDay = AmbientLightStatus.valueForString(example);
		example = "NIGHT";
		AmbientLightStatus enumNight = AmbientLightStatus.valueForString(example);
		example = "UNKNOWN";
		AmbientLightStatus enumUnknown = AmbientLightStatus.valueForString(example);
		example = "INVALID";
		AmbientLightStatus enumInvalid = AmbientLightStatus.valueForString(example);
		example = "TWILIGHT_1";
		AmbientLightStatus enumTwilight1 = AmbientLightStatus.valueForString(example);
		example = "TWILIGHT_2";
		AmbientLightStatus enumTwilight2 = AmbientLightStatus.valueForString(example);
		example = "TWILIGHT_3";
		AmbientLightStatus enumTwilight3 = AmbientLightStatus.valueForString(example);
		example = "TWILIGHT_4";
		AmbientLightStatus enumTwilight4 = AmbientLightStatus.valueForString(example);
			
		assertNotNull("DAY returned null", enumDay);
		assertNotNull("NIGHT returned null", enumNight);
		assertNotNull("UNKNOWN returned null", enumUnknown);
		assertNotNull("INVALID returned null", enumInvalid);
		assertNotNull("TWILIGHT_1 returned null", enumTwilight1);
		assertNotNull("TWILIGHT_2 returned null", enumTwilight2);
		assertNotNull("TWILIGHT_3 returned null", enumTwilight3);
		assertNotNull("TWILIGHT_4 returned null", enumTwilight4);		
	}	
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "niGHt";
		try {
		    AmbientLightStatus temp = AmbientLightStatus.valueForString(example);
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
		    AmbientLightStatus temp = AmbientLightStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}
	
	/**
	 * Verifies the possible enum values of AmbientLightStatus.
	 */
	public void testListEnum() {
 		List<AmbientLightStatus> enumValueList = Arrays.asList(AmbientLightStatus.values()); 		
		List<AmbientLightStatus> enumTestList = new ArrayList<AmbientLightStatus>();
		
		enumTestList.add(AmbientLightStatus.DAY);
		enumTestList.add(AmbientLightStatus.NIGHT);
		enumTestList.add(AmbientLightStatus.UNKNOWN);		
		enumTestList.add(AmbientLightStatus.INVALID);
		enumTestList.add(AmbientLightStatus.TWILIGHT_1);
		enumTestList.add(AmbientLightStatus.TWILIGHT_2);
		enumTestList.add(AmbientLightStatus.TWILIGHT_3);
		enumTestList.add(AmbientLightStatus.TWILIGHT_4);

		assertTrue("Enum value list does not match enum class list.", 
					enumValueList.containsAll(enumTestList) && 
					enumTestList.containsAll(enumValueList));
	}
}