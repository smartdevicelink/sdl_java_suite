package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.MediaType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.MediaType}
 */
public class MediaTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums() {
		String example = "MUSIC";
		MediaType enumMusic = MediaType.valueForString(example);
		example = "PODCAST";
		MediaType enumPodcast = MediaType.valueForString(example);
		example = "AUDIOBOOK";
		MediaType enumAudioBook = MediaType.valueForString(example);
		example = "OTHER";
		MediaType enumOther = MediaType.valueForString(example);

		assertNotNull("MUSIC returned null", enumMusic);
		assertNotNull("PODCAST returned null", enumPodcast);
		assertNotNull("AUDIOBOOK returned null", enumAudioBook);
		assertNotNull("OTHER returned null", enumOther);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum() {
		String example = "PudCAsT";
		try {
			MediaType temp = MediaType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (IllegalArgumentException exception) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum() {
		String example = null;
		try {
			MediaType temp = MediaType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of MediaType.
	 */
	public void testListEnum() {
		List<MediaType> enumValueList = Arrays.asList(MediaType.values());

		List<MediaType> enumTestList = new ArrayList<MediaType>();
		enumTestList.add(MediaType.MUSIC);
		enumTestList.add(MediaType.PODCAST);
		enumTestList.add(MediaType.AUDIOBOOK);
		enumTestList.add(MediaType.OTHER);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}