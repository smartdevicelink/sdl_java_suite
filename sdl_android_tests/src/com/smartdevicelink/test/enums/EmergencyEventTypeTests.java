package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;

public class EmergencyEventTypeTests extends TestCase {

	public void testValidEnums () {	
		String example = "NO_EVENT";
		EmergencyEventType enumEventType = EmergencyEventType.valueForString(example);
		example = "FRONTAL";
		EmergencyEventType enumFrontal = EmergencyEventType.valueForString(example);
		example = "SIDE";
		EmergencyEventType enumSide = EmergencyEventType.valueForString(example);
		example = "REAR";
		EmergencyEventType enumRear = EmergencyEventType.valueForString(example);
		example = "ROLLOVER";
		EmergencyEventType enumRollover = EmergencyEventType.valueForString(example);
		example = "NOT_SUPPORTED";
		EmergencyEventType enumNotSupported = EmergencyEventType.valueForString(example);
		example = "FAULT";
		EmergencyEventType enumFault = EmergencyEventType.valueForString(example);
		
		assertNotNull("NO_EVENT returned null", enumEventType);
		assertNotNull("FRONTAL returned null", enumFrontal);
		assertNotNull("SIDE returned null", enumSide);
		assertNotNull("REAR returned null", enumRear);
		assertNotNull("ROLLOVER returned null", enumRollover);
		assertNotNull("NOT_SUPPORTED returned null", enumNotSupported);
		assertNotNull("FAULT returned null", enumFault);
	}
	
	public void testInvalidEnum () {
		String example = "nO_EvenT";
		try {
			EmergencyEventType.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			EmergencyEventType.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<EmergencyEventType> enumValueList = Arrays.asList(EmergencyEventType.values());

		List<EmergencyEventType> enumTestList = new ArrayList<EmergencyEventType>();
		enumTestList.add(EmergencyEventType.NO_EVENT);
		enumTestList.add(EmergencyEventType.FRONTAL);
		enumTestList.add(EmergencyEventType.SIDE);
		enumTestList.add(EmergencyEventType.REAR);
		enumTestList.add(EmergencyEventType.ROLLOVER);
		enumTestList.add(EmergencyEventType.NOT_SUPPORTED);		
		enumTestList.add(EmergencyEventType.FAULT);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
