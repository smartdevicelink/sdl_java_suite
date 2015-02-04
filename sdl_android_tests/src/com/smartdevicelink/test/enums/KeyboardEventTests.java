package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;

public class KeyboardEventTests extends TestCase {

	public void testValidEnums () {	
		String example = "KEYPRESS";
		KeyboardEvent enumKeypress = KeyboardEvent.valueForString(example);
		example = "ENTRY_SUBMITTED";
		KeyboardEvent enumEntrySubmitted = KeyboardEvent.valueForString(example);
		example = "ENTRY_CANCELLED";
		KeyboardEvent enumEntryCancelled = KeyboardEvent.valueForString(example);
		example = "ENTRY_ABORTED";
		KeyboardEvent enumEntryAborted = KeyboardEvent.valueForString(example);
		
		assertNotNull("KEYPRESS returned null", enumKeypress);
		assertNotNull("ENTRY_SUBMITTED returned null", enumEntrySubmitted);
		assertNotNull("ENTRY_CANCELLED returned null", enumEntryCancelled);
		assertNotNull("ENTRY_ABORTED returned null", enumEntryAborted);
	}
	
	public void testInvalidEnum () {
		String example = "keyPreSS";
		try {
			KeyboardEvent.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			KeyboardEvent.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
	public void testListEnum() {
 		List<KeyboardEvent> enumValueList = Arrays.asList(KeyboardEvent.values());

		List<KeyboardEvent> enumTestList = new ArrayList<KeyboardEvent>();
		enumTestList.add(KeyboardEvent.KEYPRESS);
		enumTestList.add(KeyboardEvent.ENTRY_SUBMITTED);
		enumTestList.add(KeyboardEvent.ENTRY_CANCELLED);
		enumTestList.add(KeyboardEvent.ENTRY_ABORTED);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
