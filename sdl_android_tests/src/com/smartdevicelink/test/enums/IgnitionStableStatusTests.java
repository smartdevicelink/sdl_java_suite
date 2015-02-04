package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;

public class IgnitionStableStatusTests extends TestCase {

	public void testValidEnums () {	
		String example = "IGNITION_SWITCH_NOT_STABLE";
		IgnitionStableStatus enumIgnitionSwitchNotStable = IgnitionStableStatus.valueForString(example);
		example = "IGNITION_SWITCH_STABLE";
		IgnitionStableStatus enumIgnitionSwitchStable = IgnitionStableStatus.valueForString(example);
		example = "MISSING_FROM_TRANSMITTER";
		IgnitionStableStatus enumMissingFromTransmitter = IgnitionStableStatus.valueForString(example);
		
		assertNotNull("IGNITION_SWITCH_NOT_STABLE returned null", enumIgnitionSwitchNotStable);
		assertNotNull("IGNITION_SWITCH_STABLE returned null", enumIgnitionSwitchStable);
		assertNotNull("MISSING_FROM_TRANSMITTER returned null", enumMissingFromTransmitter);
	}
	
	public void testInvalidEnum () {
		String example = "iGnitIoN_SwiTch_NoT_StablE";
		try {
			IgnitionStableStatus.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			IgnitionStableStatus.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
	public void testListEnum() {
 		List<IgnitionStableStatus> enumValueList = Arrays.asList(IgnitionStableStatus.values());

		List<IgnitionStableStatus> enumTestList = new ArrayList<IgnitionStableStatus>();
		enumTestList.add(IgnitionStableStatus.IGNITION_SWITCH_NOT_STABLE);
		enumTestList.add(IgnitionStableStatus.IGNITION_SWITCH_STABLE);
		enumTestList.add(IgnitionStableStatus.MISSING_FROM_TRANSMITTER);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
