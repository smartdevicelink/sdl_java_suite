package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.CarModeStatus;

public class CarModeStatusTests extends TestCase {

	public void testValidEnums () {	
		String example = "NORMAL";
		CarModeStatus enumNormal = CarModeStatus.valueForString(example);
		example = "FACTORY";
		CarModeStatus enumFactory = CarModeStatus.valueForString(example);
		example = "TRANSPORT";
		CarModeStatus enumTransport = CarModeStatus.valueForString(example);
		example = "CRASH";
		CarModeStatus enumCrash = CarModeStatus.valueForString(example);
		
		assertNotNull("NORMAL returned null", enumNormal);
		assertNotNull("FACTORY returned null", enumFactory);
		assertNotNull("TRANSPORT returned null", enumTransport);
		assertNotNull("CRASH returned null", enumCrash);
	}
	
	public void testInvalidEnum () {
		String example = "noRmaL";
		try {
			CarModeStatus.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			CarModeStatus.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
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
