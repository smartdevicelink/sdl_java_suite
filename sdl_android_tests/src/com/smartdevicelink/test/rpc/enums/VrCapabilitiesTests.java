package com.smartdevicelink.test.rpc.enums;

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
	
	public void testInvalidEnum () {
		String example = "tExTx";
		try {
		    VrCapabilities temp = VrCapabilities.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
		    VrCapabilities temp = VrCapabilities.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	public void testListEnum() {
 		List<VrCapabilities> enumValueList = Arrays.asList(VrCapabilities.values());

		List<VrCapabilities> enumTestList = new ArrayList<VrCapabilities>();
		enumTestList.add(VrCapabilities.TEXT);
		enumTestList.add(VrCapabilities.Text);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
