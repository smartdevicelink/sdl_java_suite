package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;

public class VrCapabilitiesTests extends TestCase {

	public void testValidEnums () {	
		String example = "Text";
		VrCapabilities enumText = VrCapabilities.valueForString(example);
		
		assertNotNull("Text returned null", enumText);
	}
	
	//TODO: use this test?
	public void testInvalidEnum () {
		String example = "tExT";
		try {
			VrCapabilities.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			VrCapabilities.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
	public void testListEnum() {
 		List<VrCapabilities> enumValueList = Arrays.asList(VrCapabilities.values());

		List<VrCapabilities> enumTestList = new ArrayList<VrCapabilities>();
		enumTestList.add(VrCapabilities.Text);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
