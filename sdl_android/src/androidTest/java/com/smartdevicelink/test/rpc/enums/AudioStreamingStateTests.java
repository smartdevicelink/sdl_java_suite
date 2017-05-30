package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.AudioStreamingState;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.AudioStreaming}
 */
public class AudioStreamingStateTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.audible_caps);
		AudioStreamingState enumAudible = AudioStreamingState.valueForString(example);
		example = mContext.getString(R.string.attenuated_caps);
		AudioStreamingState enumAttentuated = AudioStreamingState.valueForString(example);
		example = mContext.getString(R.string.not_audible_caps);
		AudioStreamingState enumNotAudible = AudioStreamingState.valueForString(example);

		
		assertNotNull("AUDIBLE returned null", enumAudible);
		assertNotNull("ATTENUATED returned null", enumAttentuated);
		assertNotNull("NOT_AUDIBLE returned null", enumNotAudible);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    AudioStreamingState temp = AudioStreamingState.valueForString(example);
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
		    AudioStreamingState temp = AudioStreamingState.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of AudioStreamingState.
	 */
	public void testListEnum() {
 		List<AudioStreamingState> enumValueList = Arrays.asList(AudioStreamingState.values());

		List<AudioStreamingState> enumTestList = new ArrayList<AudioStreamingState>();
		enumTestList.add(AudioStreamingState.AUDIBLE);
		enumTestList.add(AudioStreamingState.ATTENUATED);
		enumTestList.add(AudioStreamingState.NOT_AUDIBLE);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}