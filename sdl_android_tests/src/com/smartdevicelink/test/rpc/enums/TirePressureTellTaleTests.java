package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.TirePressureTellTale;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.TirePressureTellTale}
 */
public class TirePressureTellTaleTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
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

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "oFf";
		try {
		    TirePressureTellTale temp = TirePressureTellTale.valueForString(example);
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
		    TirePressureTellTale temp = TirePressureTellTale.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	

	/**
	 * Verifies the possible enum values of TirePressureTellTale.
	 */
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