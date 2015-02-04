package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;

public class ComponentVolumeStatusTests extends TestCase {

	public void testValidEnums () {	
		String example = "UNKNOWN";
		ComponentVolumeStatus enumUnknown = ComponentVolumeStatus.valueForString(example);
		example = "NORMAL";
		ComponentVolumeStatus enumNormal = ComponentVolumeStatus.valueForString(example);
		example = "LOW";
		ComponentVolumeStatus enumLow = ComponentVolumeStatus.valueForString(example);
		example = "FAULT";
		ComponentVolumeStatus enumFault = ComponentVolumeStatus.valueForString(example);
		example = "ALERT";
		ComponentVolumeStatus enumAlert = ComponentVolumeStatus.valueForString(example);
		example = "NOT_SUPPORTED";
		ComponentVolumeStatus enumNotSupported = ComponentVolumeStatus.valueForString(example);
		
		assertNotNull("UNKNOWN returned null", enumUnknown);
		assertNotNull("NORMAL returned null", enumNormal);
		assertNotNull("LOW returned null", enumLow);
		assertNotNull("FAULT returned null", enumFault);
		assertNotNull("ALERT returned null", enumAlert);
		assertNotNull("NOT_SUPPORTED returned null", enumNotSupported);

	}
	
	public void testInvalidEnum () {
		String example = "unKNowN";
		try {
			ComponentVolumeStatus.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			ComponentVolumeStatus.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
	public void testListEnum() {
 		List<ComponentVolumeStatus> enumValueList = Arrays.asList(ComponentVolumeStatus.values());

		List<ComponentVolumeStatus> enumTestList = new ArrayList<ComponentVolumeStatus>();
		enumTestList.add(ComponentVolumeStatus.UNKNOWN);
		enumTestList.add(ComponentVolumeStatus.NORMAL);
		enumTestList.add(ComponentVolumeStatus.LOW);
		enumTestList.add(ComponentVolumeStatus.FAULT);
		enumTestList.add(ComponentVolumeStatus.ALERT);
		enumTestList.add(ComponentVolumeStatus.NOT_SUPPORTED);		

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
