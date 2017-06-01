package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.KeypressMode}
 */
public class KeypressModeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.single_keypress_caps);
		KeypressMode enumSingleKeypress = KeypressMode.valueForString(example);
		example = mContext.getString(R.string.queue_keypress_caps);
		KeypressMode enumQueueKeypresses = KeypressMode.valueForString(example);
		example = mContext.getString(R.string.resend_current_entry_caps);
		KeypressMode enumResendCurrentEntry = KeypressMode.valueForString(example);
		
		assertNotNull("SINGLE_KEYPRESS returned null", enumSingleKeypress);
		assertNotNull("QUEUE_KEYPRESSES returned null", enumQueueKeypresses);
		assertNotNull("RESEND_CURRENT_ENTRY returned null", enumResendCurrentEntry);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    KeypressMode temp = KeypressMode.valueForString(example);
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
		    KeypressMode temp = KeypressMode.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of KeypressMode.
	 */
	public void testListEnum() {
 		List<KeypressMode> enumValueList = Arrays.asList(KeypressMode.values());

		List<KeypressMode> enumTestList = new ArrayList<KeypressMode>();
		enumTestList.add(KeypressMode.SINGLE_KEYPRESS);
		enumTestList.add(KeypressMode.QUEUE_KEYPRESSES);
		enumTestList.add(KeypressMode.RESEND_CURRENT_ENTRY);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}