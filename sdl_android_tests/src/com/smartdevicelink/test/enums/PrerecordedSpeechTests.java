package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;

public class PrerecordedSpeechTests extends TestCase {
	
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
	
	public void testInvalidEnum () {
		String example = "heLP_JingLE";
		try {
			PrerecordedSpeech.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			PrerecordedSpeech.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
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
