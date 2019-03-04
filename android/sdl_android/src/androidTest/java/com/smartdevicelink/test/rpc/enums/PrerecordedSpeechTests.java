package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.PrerecordedSpeech}
 */
public class PrerecordedSpeechTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "HELP_JINGLE";
		PrerecordedSpeech enumHelpJingle = PrerecordedSpeech.valueForString(example);
		example = "INITIAL_JINGLE";
		PrerecordedSpeech enumInitialJingle = PrerecordedSpeech.valueForString(example);
		example = "LISTEN_JINGLE";
		PrerecordedSpeech enumListenJingle = PrerecordedSpeech.valueForString(example);
		example = "POSITIVE_JINGLE";
		PrerecordedSpeech enumPositiveJingle = PrerecordedSpeech.valueForString(example);
		example = "NEGATIVE_JINGLE";
		PrerecordedSpeech enumNegativeJingle = PrerecordedSpeech.valueForString(example);

		assertNotNull("HELP_JINGLE returned null", enumHelpJingle);
		assertNotNull("INITIAL_JINGLE returned null", enumInitialJingle);
		assertNotNull("LISTEN_JINGLE returned null", enumListenJingle);
		assertNotNull("POSITIVE_JINGLE returned null", enumPositiveJingle);
		assertNotNull("NEGATIVE_JINGLE returned null", enumNegativeJingle);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "heLP_JingLE";
		try {
		    PrerecordedSpeech temp = PrerecordedSpeech.valueForString(example);
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
		    PrerecordedSpeech temp = PrerecordedSpeech.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of PrerecordedSpeech.
	 */
	public void testListEnum() {
 		List<PrerecordedSpeech> enumValueList = Arrays.asList(PrerecordedSpeech.values());

		List<PrerecordedSpeech> enumTestList = new ArrayList<PrerecordedSpeech>();
		enumTestList.add(PrerecordedSpeech.HELP_JINGLE);
		enumTestList.add(PrerecordedSpeech.INITIAL_JINGLE);
		enumTestList.add(PrerecordedSpeech.LISTEN_JINGLE);
		enumTestList.add(PrerecordedSpeech.POSITIVE_JINGLE);
		enumTestList.add(PrerecordedSpeech.NEGATIVE_JINGLE);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}