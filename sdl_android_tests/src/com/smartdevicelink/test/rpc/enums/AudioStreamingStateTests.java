package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.AudioStreamingState;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.AudioStreaming}
 */
public class AudioStreamingStateTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
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

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "aUDibLE";
		try {
		    AudioStreamingState temp = AudioStreamingState.valueForString(example);
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
		    AudioStreamingState temp = AudioStreamingState.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}