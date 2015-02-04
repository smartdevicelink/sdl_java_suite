package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.TirePressureTellTale;

public class TirePressureTellTaleTests extends TestCase {

	public void testValidEnums () {	
		String example = "OFF";
		TirePressureTellTale enumOff = TirePressureTellTale.valueForString(example);
		example = "ON";
		TirePressureTellTale enumOn = TirePressureTellTale.valueForString(example);
		example = "FLASH";
		TirePressureTellTale enumFlash = TirePressureTellTale.valueForString(example);
		
		assertNotNull("OFF returned null", enumOff);
		assertNotNull("ON returned null", enumOn);
		assertNotNull("FLASH returned null", enumFlash);
	}
	
	public void testInvalidEnum () {
		String example = "oFf";
		try {
			TirePressureTellTale.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			TirePressureTellTale.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
	public void testListEnum() {
 		List<TirePressureTellTale> enumValueList = Arrays.asList(TirePressureTellTale.values());

		List<TirePressureTellTale> enumTestList = new ArrayList<TirePressureTellTale>();
		enumTestList.add(TirePressureTellTale.OFF);
		enumTestList.add(TirePressureTellTale.ON);
		enumTestList.add(TirePressureTellTale.FLASH);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
