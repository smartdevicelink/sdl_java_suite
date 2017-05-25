package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

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
		    KeypressMode temp = KeypressMode.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}