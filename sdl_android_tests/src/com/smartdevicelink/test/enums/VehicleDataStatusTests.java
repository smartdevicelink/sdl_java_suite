package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;

public class VehicleDataStatusTests extends TestCase {

	public void testValidEnums () {	
		String example = "NO_DATA_EXISTS";
		VehicleDataStatus enumNoDataExists = VehicleDataStatus.valueForString(example);
		example = "OFF";
		VehicleDataStatus enumOff = VehicleDataStatus.valueForString(example);
		example = "ON";
		VehicleDataStatus enumOn = VehicleDataStatus.valueForString(example);
		
		assertNotNull("NO_DATA_EXISTS returned null", enumNoDataExists);
		assertNotNull("OFF returned null", enumOff);
		assertNotNull("ON returned null", enumOn);
	}
	
	public void testInvalidEnum () {
		String example = "No_DatA_ExiSTs";
		try {
			VehicleDataStatus.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			VehicleDataStatus.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<VehicleDataStatus> enumValueList = Arrays.asList(VehicleDataStatus.values());

		List<VehicleDataStatus> enumTestList = new ArrayList<VehicleDataStatus>();
		enumTestList.add(VehicleDataStatus.NO_DATA_EXISTS);
		enumTestList.add(VehicleDataStatus.OFF);
		enumTestList.add(VehicleDataStatus.ON);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
