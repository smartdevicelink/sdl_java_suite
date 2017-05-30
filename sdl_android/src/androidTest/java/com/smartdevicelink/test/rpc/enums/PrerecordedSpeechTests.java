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
		String example = mContext.getString(R.string.help_jingle_caps);
		PrerecordedSpeech enumHelpJingle = PrerecordedSpeech.valueForString(example);
		example = mContext.getString(R.string.initial_jingle_caps);
		PrerecordedSpeech enumInitialJingle = PrerecordedSpeech.valueForString(example);
		example = mContext.getString(R.string.listen_jingle_caps);
		PrerecordedSpeech enumListenJingle = PrerecordedSpeech.valueForString(example);
		example = mContext.getString(R.string.positive_jingle_caps);
		PrerecordedSpeech enumPositiveJingle = PrerecordedSpeech.valueForString(example);
		example = mContext.getString(R.string.negative_jingle_caps);
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
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (IllegalArgumentException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    PrerecordedSpeech temp = PrerecordedSpeech.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
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

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}