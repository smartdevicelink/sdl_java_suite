package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.GearShiftAdviceStatus;

public class GearShiftAdviceStatusTests extends TestCase {

	public void testValidEnums () {	
		String example = "NO_INDICATION";
		GearShiftAdviceStatus enumNoIndication = GearShiftAdviceStatus.valueForString(example);
		example = "UPSHIFT_FUEL_ECONOMY";
		GearShiftAdviceStatus enumUpshiftFuelEconomy = GearShiftAdviceStatus.valueForString(example);
		example = "UPSHIFT_PERFORMANCE";
		GearShiftAdviceStatus enumUpshiftPerformance = GearShiftAdviceStatus.valueForString(example);
		example = "UPSHIFT_WARNING";
		GearShiftAdviceStatus enumUpshiftWarning = GearShiftAdviceStatus.valueForString(example);
		example = "DOWNSHIFT_RECOMMENDATION";
		GearShiftAdviceStatus enumDownshiftRecommendation = GearShiftAdviceStatus.valueForString(example);
		example = "SHIFT_TO_NEUTRAL";
		GearShiftAdviceStatus enumShiftToNeutral = GearShiftAdviceStatus.valueForString(example);
		
		assertNotNull("NO_INDICATION returned null", enumNoIndication);
		assertNotNull("UPSHIFT_FUEL_ECONOMY returned null", enumUpshiftFuelEconomy);
		assertNotNull("UPSHIFT_PERFORMANCE returned null", enumUpshiftPerformance);
		assertNotNull("UPSHIFT_WARNING returned null", enumUpshiftWarning);
		assertNotNull("DOWNSHIFT_RECOMMENDATION returned null", enumDownshiftRecommendation);
		assertNotNull("SHIFT_TO_NEUTRAL returned null", enumShiftToNeutral);
	}
	
	public void testInvalidEnum () {
		String example = "no_INdICaTIon";
		try {
			GearShiftAdviceStatus.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			GearShiftAdviceStatus.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<GearShiftAdviceStatus> enumValueList = Arrays.asList(GearShiftAdviceStatus.values());

		List<GearShiftAdviceStatus> enumTestList = new ArrayList<GearShiftAdviceStatus>();
		enumTestList.add(GearShiftAdviceStatus.NO_INDICATION);
		enumTestList.add(GearShiftAdviceStatus.UPSHIFT_FUEL_ECONOMY);
		enumTestList.add(GearShiftAdviceStatus.UPSHIFT_PERFORMANCE);
		enumTestList.add(GearShiftAdviceStatus.UPSHIFT_WARNING);
		enumTestList.add(GearShiftAdviceStatus.DOWNSHIFT_RECOMMENDATION);
		enumTestList.add(GearShiftAdviceStatus.SHIFT_TO_NEUTRAL);			

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
