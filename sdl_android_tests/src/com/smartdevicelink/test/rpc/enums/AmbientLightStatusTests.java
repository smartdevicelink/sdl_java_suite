package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;

public class AmbientLightStatusTests extends TestCase {

	public void testValidEnums () {	
		String example = "NIGHT";
		AmbientLightStatus enumNight = AmbientLightStatus.valueForString(example);
		example = "TWILIGHT_1";
		AmbientLightStatus enumTwilight1 = AmbientLightStatus.valueForString(example);
		example = "TWILIGHT_2";
		AmbientLightStatus enumTwilight2 = AmbientLightStatus.valueForString(example);
		example = "TWILIGHT_3";
		AmbientLightStatus enumTwilight3 = AmbientLightStatus.valueForString(example);
		example = "TWILIGHT_4";
		AmbientLightStatus enumTwilight4 = AmbientLightStatus.valueForString(example);
		example = "DAY";
		AmbientLightStatus enumDay = AmbientLightStatus.valueForString(example);
		example = "UNKNOWN";
		AmbientLightStatus enumUnknown = AmbientLightStatus.valueForString(example);
		example = "INVALID";
		AmbientLightStatus enumInvalid = AmbientLightStatus.valueForString(example);
				
		assertNotNull("NIGHT returned null", enumNight);
		assertNotNull("TWILIGHT_1 returned null", enumTwilight1);
		assertNotNull("TWILIGHT_2 returned null", enumTwilight2);
		assertNotNull("TWILIGHT_3 returned null", enumTwilight3);
		assertNotNull("TWILIGHT_4 returned null", enumTwilight4);
		assertNotNull("DAY returned null", enumDay);
		assertNotNull("UNKNOWN returned null", enumUnknown);
		assertNotNull("INVALID returned null", enumInvalid);
	}
	
	public void testInvalidEnum () {
		String example = "niGHt";
		try {
			AmbientLightStatus.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			AmbientLightStatus.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<AmbientLightStatus> enumValueList = Arrays.asList(AmbientLightStatus.values());
 		
		List<AmbientLightStatus> enumTestList = new ArrayList<AmbientLightStatus>();
		enumTestList.add(AmbientLightStatus.NIGHT);
		enumTestList.add(AmbientLightStatus.TWILIGHT_1);
		enumTestList.add(AmbientLightStatus.TWILIGHT_2);
		enumTestList.add(AmbientLightStatus.TWILIGHT_3);
		enumTestList.add(AmbientLightStatus.TWILIGHT_4);
		enumTestList.add(AmbientLightStatus.DAY);		
		enumTestList.add(AmbientLightStatus.UNKNOWN);		
		enumTestList.add(AmbientLightStatus.INVALID);		

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}
