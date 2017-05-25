package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.PrerecordedSpeech}
 */
public class PrerecordedSpeechTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.help_jungle_caps);
		PrerecordedSpeech enumHelpJingle = PrerecordedSpeech.valueForString(example);
		example = mContext.getString(R.string.initial_jingle_caps);
		PrerecordedSpeech enumInitialJingle = PrerecordedSpeech.valueForString(example);
		example = mContext.getString(R.string.listen_jingle_caps);
		PrerecordedSpeech enumListenJingle = PrerecordedSpeech.valueForString(example);
		example = mContext.getString(R.string.positive_jingle_caps);
		PrerecordedSpeech enumPositiveJingle = PrerecordedSpeech.valueForString(example);
		example = mContext.getString(R.string.neg_jingle_caps);
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
		String example = mContext.getString(R.string.invalid_enum);
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