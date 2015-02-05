package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;

public class HmiZoneCapabilitiesTests extends TestCase {

	public void testValidEnums () {	
		String example = "FRONT";
		HmiZoneCapabilities enumFront = HmiZoneCapabilities.valueForString(example);
		example = "BACK";
		HmiZoneCapabilities enumBack = HmiZoneCapabilities.valueForString(example);
		
		assertNotNull("FRONT returned null", enumFront);
		assertNotNull("BACK returned null", enumBack);
	}
	
	public void testInvalidEnum () {
		String example = "fROnT";
		try {
			HmiZoneCapabilities.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			HmiZoneCapabilities.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<HmiZoneCapabilities> enumValueList = Arrays.asList(HmiZoneCapabilities.values());

		List<HmiZoneCapabilities> enumTestList = new ArrayList<HmiZoneCapabilities>();
		enumTestList.add(HmiZoneCapabilities.FRONT);
		enumTestList.add(HmiZoneCapabilities.BACK);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
