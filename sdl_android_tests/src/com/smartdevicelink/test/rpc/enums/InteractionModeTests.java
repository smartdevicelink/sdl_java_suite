package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.InteractionMode;

public class InteractionModeTests extends TestCase {

	public void testValidEnums () {	
		String example = "MANUAL_ONLY";
		InteractionMode enumManualOnly = InteractionMode.valueForString(example);
		example = "VR_ONLY";
		InteractionMode enumVrOnly = InteractionMode.valueForString(example);
		example = "BOTH";
		InteractionMode enumBoth = InteractionMode.valueForString(example);
		
		assertNotNull("MANUAL_ONLY returned null", enumManualOnly);
		assertNotNull("VR_ONLY returned null", enumVrOnly);
		assertNotNull("BOTH returned null", enumBoth);
	}
	
	public void testInvalidEnum () {
		String example = "maNuAL_OnlY";
		try {
		    InteractionMode temp = InteractionMode.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
		    InteractionMode temp = InteractionMode.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	public void testListEnum() {
 		List<InteractionMode> enumValueList = Arrays.asList(InteractionMode.values());

		List<InteractionMode> enumTestList = new ArrayList<InteractionMode>();
		enumTestList.add(InteractionMode.MANUAL_ONLY);
		enumTestList.add(InteractionMode.VR_ONLY);
		enumTestList.add(InteractionMode.BOTH);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
