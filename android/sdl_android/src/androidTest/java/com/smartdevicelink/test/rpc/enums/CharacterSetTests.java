package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.CharacterSet;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.CharacterSet}
 */
public class CharacterSetTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "TYPE2SET";
		CharacterSet enumType2Set = CharacterSet.valueForString(example);
		example = "TYPE5SET";
		CharacterSet enumType5Set = CharacterSet.valueForString(example);
		example = "CID1SET";
		CharacterSet enumCid1Set = CharacterSet.valueForString(example);
		example = "CID2SET";
		CharacterSet enumCid2Set = CharacterSet.valueForString(example);
		
		assertNotNull("TYPE2SET returned null", enumType2Set);
		assertNotNull("TYPE5SET returned null", enumType5Set);
		assertNotNull("CID1SET returned null", enumCid1Set);
		assertNotNull("CID2SET returned null", enumCid2Set);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "tyPe2SeT";
		try {
		    CharacterSet temp = CharacterSet.valueForString(example);
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
		    CharacterSet temp = CharacterSet.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of CharacterSet.
	 */
	public void testListEnum() {
 		List<CharacterSet> enumValueList = Arrays.asList(CharacterSet.values());

		List<CharacterSet> enumTestList = new ArrayList<CharacterSet>();
		enumTestList.add(CharacterSet.TYPE2SET);
		enumTestList.add(CharacterSet.TYPE5SET);
		enumTestList.add(CharacterSet.CID1SET);
		enumTestList.add(CharacterSet.CID2SET);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}