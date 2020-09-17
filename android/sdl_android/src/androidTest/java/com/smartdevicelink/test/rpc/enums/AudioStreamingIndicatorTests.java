package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.AudioStreamingIndicator;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.AudioStreamingIndicator}
 */
public class AudioStreamingIndicatorTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "PLAY_PAUSE";
        AudioStreamingIndicator enumPlayPause = AudioStreamingIndicator.valueForString(example);
        example = "PLAY";
        AudioStreamingIndicator enumPlay = AudioStreamingIndicator.valueForString(example);
        example = "PAUSE";
        AudioStreamingIndicator enumPause = AudioStreamingIndicator.valueForString(example);
        example = "STOP";
        AudioStreamingIndicator enumStop = AudioStreamingIndicator.valueForString(example);


        assertNotNull("PLAY_PAUSE returned null", enumPlayPause);
        assertNotNull("PLAY returned null", enumPlay);
        assertNotNull("PAUSE returned null", enumPause);
        assertNotNull("STOP returned null", enumStop);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "pLayPauZe";
        try {
            AudioStreamingIndicator temp = AudioStreamingIndicator.valueForString(example);
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
            AudioStreamingIndicator temp = AudioStreamingIndicator.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of AudioStreamingIndicator.
     */
    public void testListEnum() {
        List<AudioStreamingIndicator> enumValueList = Arrays.asList(AudioStreamingIndicator.values());

        List<AudioStreamingIndicator> enumTestList = new ArrayList<>();
        enumTestList.add(AudioStreamingIndicator.PLAY_PAUSE);
        enumTestList.add(AudioStreamingIndicator.PLAY);
        enumTestList.add(AudioStreamingIndicator.PAUSE);
        enumTestList.add(AudioStreamingIndicator.STOP);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}