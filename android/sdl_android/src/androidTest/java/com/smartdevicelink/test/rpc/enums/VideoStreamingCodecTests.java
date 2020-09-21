package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoStreamingCodecTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "H264";
        VideoStreamingCodec enumH264 = VideoStreamingCodec.valueForString(example);
        example = "H265";
        VideoStreamingCodec enumH265 = VideoStreamingCodec.valueForString(example);
        example = "Theora";
        VideoStreamingCodec enumTheora = VideoStreamingCodec.valueForString(example);
        example = "VP8";
        VideoStreamingCodec enumVP8 = VideoStreamingCodec.valueForString(example);
        example = "VP9";
        VideoStreamingCodec enumVP9 = VideoStreamingCodec.valueForString(example);

        assertNotNull("H264 returned null", enumH264);
        assertNotNull("H265 returned null", enumH265);
        assertNotNull("Theora returned null", enumTheora);
        assertNotNull("VP8 returned null", enumVP8);
        assertNotNull("VP9 returned null", enumVP9);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "H234";
        try {
            VideoStreamingCodec temp = VideoStreamingCodec.valueForString(example);
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
            VideoStreamingCodec temp = VideoStreamingCodec.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }


    /**
     * Verifies the possible enum values of TouchType.
     */
    public void testListEnum() {
        List<VideoStreamingCodec> enumValueList = Arrays.asList(VideoStreamingCodec.values());

        List<VideoStreamingCodec> enumTestList = new ArrayList<VideoStreamingCodec>();
        enumTestList.add(VideoStreamingCodec.H264);
        enumTestList.add(VideoStreamingCodec.H265);
        enumTestList.add(VideoStreamingCodec.Theora);
        enumTestList.add(VideoStreamingCodec.VP8);
        enumTestList.add(VideoStreamingCodec.VP9);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}
