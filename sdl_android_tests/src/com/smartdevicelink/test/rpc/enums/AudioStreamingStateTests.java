package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.AudioStreamingState;

public class AudioStreamingStateTests extends TestCase {

	public void testValidEnums () {	
		String example = "AUDIBLE";
		AudioStreamingState enumAudible = AudioStreamingState.valueForString(example);
		example = "ATTENUATED";
		AudioStreamingState enumAttentuated = AudioStreamingState.valueForString(example);
		example = "NOT_AUDIBLE";
		AudioStreamingState enumNotAudible = AudioStreamingState.valueForString(example);

		
		assertNotNull("AUDIBLE returned null", enumAudible);
		assertNotNull("ATTENUATED returned null", enumAttentuated);
		assertNotNull("NOT_AUDIBLE returned null", enumNotAudible);
	}
	
	public void testInvalidEnum () {
		String example = "aUDibLE";
		try {
			AudioStreamingState.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			AudioStreamingState.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<AudioStreamingState> enumValueList = Arrays.asList(AudioStreamingState.values());

		List<AudioStreamingState> enumTestList = new ArrayList<AudioStreamingState>();
		enumTestList.add(AudioStreamingState.AUDIBLE);
		enumTestList.add(AudioStreamingState.ATTENUATED);
		enumTestList.add(AudioStreamingState.NOT_AUDIBLE);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
