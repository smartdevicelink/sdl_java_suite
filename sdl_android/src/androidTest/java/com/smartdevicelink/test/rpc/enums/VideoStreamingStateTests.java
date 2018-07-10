package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.VideoStreamingState;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.enums.VideoStreamingState}
 */
public class VideoStreamingStateTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "STREAMABLE";
		VideoStreamingState enumStreamable = VideoStreamingState.valueForString(example);
		example = "NOT_STREAMABLE";
		VideoStreamingState enumNotStreamable = VideoStreamingState.valueForString(example);

		assertNotNull("STREAMABLE returned null", enumStreamable);
		assertNotNull("NOT_STREAMABLE returned null", enumNotStreamable);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "StrEAmaBlE";
		try {
		    VideoStreamingState temp = VideoStreamingState.valueForString(example);
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
		    VideoStreamingState temp = VideoStreamingState.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of VideoStreamingState.
	 */
	public void testListEnum() {
 		List<VideoStreamingState> enumValueList = Arrays.asList(VideoStreamingState.values());

		List<VideoStreamingState> enumTestList = new ArrayList<VideoStreamingState>();
		enumTestList.add(VideoStreamingState.STREAMABLE);
		enumTestList.add(VideoStreamingState.NOT_STREAMABLE);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}