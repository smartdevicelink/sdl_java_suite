package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.KeyboardEvent}
 */
public class KeyboardEventTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.keypress_caps);
		KeyboardEvent enumKeypress = KeyboardEvent.valueForString(example);
		example = mContext.getString(R.string.entry_submitted_caps);
		KeyboardEvent enumEntrySubmitted = KeyboardEvent.valueForString(example);
		example = mContext.getString(R.string.entry_cancelled_caps);
		KeyboardEvent enumEntryCancelled = KeyboardEvent.valueForString(example);
		example = mContext.getString(R.string.entry_aborted_caps);
		KeyboardEvent enumEntryAborted = KeyboardEvent.valueForString(example);
		example = mContext.getString(R.string.entry_voice_caps);
		KeyboardEvent enumEntryVoice = KeyboardEvent.valueForString(example);
		
		assertNotNull("KEYPRESS returned null", enumKeypress);
		assertNotNull("ENTRY_SUBMITTED returned null", enumEntrySubmitted);
		assertNotNull("ENTRY_CANCELLED returned null", enumEntryCancelled);
		assertNotNull("ENTRY_ABORTED returned null", enumEntryAborted);
		assertNotNull("ENTRY_VOICE returned null", enumEntryVoice);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    KeyboardEvent temp = KeyboardEvent.valueForString(example);
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
		    KeyboardEvent temp = KeyboardEvent.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of KeyboardEvent.
	 */
	public void testListEnum() {
 		List<KeyboardEvent> enumValueList = Arrays.asList(KeyboardEvent.values());

		List<KeyboardEvent> enumTestList = new ArrayList<KeyboardEvent>();
		enumTestList.add(KeyboardEvent.KEYPRESS);
		enumTestList.add(KeyboardEvent.ENTRY_SUBMITTED);
		enumTestList.add(KeyboardEvent.ENTRY_CANCELLED);
		enumTestList.add(KeyboardEvent.ENTRY_ABORTED);
		enumTestList.add(KeyboardEvent.ENTRY_VOICE);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}