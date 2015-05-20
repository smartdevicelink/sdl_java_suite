package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.Jingle;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.Jingle}
 */
public class JingleTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {
		
		String example = "POSITIVE_JINGLE";
		Jingle enumPositiveJingle = Jingle.valueForString(example);
		example = "NEGATIVE_JINGLE";
		Jingle enumNegativeJingle = Jingle.valueForString(example);
		example = "INITIAL_JINGLE";
		Jingle enumInitialJingle = Jingle.valueForString(example);
		example = "LISTEN_JINGLE";
		Jingle enumListenJingle = Jingle.valueForString(example);
		example = "HELP_JINGLE";
		Jingle enumHelpJingle = Jingle.valueForString(example);
		
		assertNotNull("POSITIVE_JINGLE returned null", enumPositiveJingle);
		assertNotNull("NEGATIVE_JINGLE returned null", enumNegativeJingle);
		assertNotNull("INITIAL_JINGLE returned null", enumInitialJingle);
		assertNotNull("LISTEN_JINGLE returned null", enumListenJingle);
		assertNotNull("HELP_JINGLE returned null", enumHelpJingle);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "posITive_JiGLE";
		try {
			Jingle temp = Jingle.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (IllegalArgumentException exception) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
			Jingle temp = Jingle.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (IllegalArgumentException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of Jingle.
	 */
	public void testListEnums () {
		List<Jingle> enumValueList = Arrays.asList(Jingle.values());
		List<Jingle> enumTestList = new ArrayList<Jingle>();
		
		enumTestList.add(Jingle.HELP);
		enumTestList.add(Jingle.INITIAL);
		enumTestList.add(Jingle.LISTEN);
		enumTestList.add(Jingle.NEGATIVE);
		enumTestList.add(Jingle.POSITIVE);
		
		assertTrue("Enum value list does not match neum class list.",
					enumValueList.containsAll(enumTestList) &&
					enumTestList.containsAll(enumValueList));
	}
}