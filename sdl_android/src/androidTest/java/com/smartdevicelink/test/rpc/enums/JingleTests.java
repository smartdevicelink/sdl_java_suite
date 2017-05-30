package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.Jingle;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.Jingle}
 */
public class JingleTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {
		
		String example = mContext.getString(R.string.positive_jingle_caps);
		Jingle enumPositiveJingle = Jingle.valueForString(example);
		example = mContext.getString(R.string.negative_jingle_caps);
		Jingle enumNegativeJingle = Jingle.valueForString(example);
		example = mContext.getString(R.string.initial_jingle_caps);
		Jingle enumInitialJingle = Jingle.valueForString(example);
		example = mContext.getString(R.string.listen_jingle_caps);
		Jingle enumListenJingle = Jingle.valueForString(example);
		example = mContext.getString(R.string.help_jingle_caps);
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
		String example = mContext.getString(R.string.invalid_enum);
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