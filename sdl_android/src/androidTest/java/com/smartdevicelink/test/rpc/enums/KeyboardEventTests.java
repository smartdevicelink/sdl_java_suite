package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.KeyboardEvent}
 */
public class KeyboardEventTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "KEYPRESS";
		KeyboardEvent enumKeypress = KeyboardEvent.valueForString(example);
		example = "ENTRY_SUBMITTED";
		KeyboardEvent enumEntrySubmitted = KeyboardEvent.valueForString(example);
		example = "ENTRY_CANCELLED";
		KeyboardEvent enumEntryCancelled = KeyboardEvent.valueForString(example);
		example = "ENTRY_ABORTED";
		KeyboardEvent enumEntryAborted = KeyboardEvent.valueForString(example);
		example = "ENTRY_VOICE";
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
		String example = "keyPreSS";
		try {
		    KeyboardEvent temp = KeyboardEvent.valueForString(example);
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
		    KeyboardEvent temp = KeyboardEvent.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}