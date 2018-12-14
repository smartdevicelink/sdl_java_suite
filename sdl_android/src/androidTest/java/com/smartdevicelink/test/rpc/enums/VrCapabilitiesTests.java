package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.VrCapabilities}
 */
public class VrCapabilitiesTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "Text";
		VrCapabilities enumText = VrCapabilities.valueForString(example);
		
		assertNotNull("Text returned null", enumText);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
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
	
	/**
	 * Verifies that a null assignment is invalid.
	 */
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
	
	/**
	 * Verifies the possible enum values of VrCapabilities.
	 */
	@SuppressWarnings("deprecation")
	public void testListEnum() {
 		List<VrCapabilities> enumValueList = Arrays.asList(VrCapabilities.values());

		List<VrCapabilities> enumTestList = new ArrayList<VrCapabilities>();
		enumTestList.add(VrCapabilities.TEXT);
		enumTestList.add(VrCapabilities.Text);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}