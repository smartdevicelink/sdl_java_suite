package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoStreamingProtocolTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "RAW";
        VideoStreamingProtocol enumRAW = VideoStreamingProtocol.valueForString(example);
        example = "RTP";
        VideoStreamingProtocol enumRTP = VideoStreamingProtocol.valueForString(example);
        example = "RTSP";
        VideoStreamingProtocol enumRTSP = VideoStreamingProtocol.valueForString(example);
        example = "RTMP";
        VideoStreamingProtocol enumRTMP = VideoStreamingProtocol.valueForString(example);
        example = "WEBM";
        VideoStreamingProtocol enumWEBM = VideoStreamingProtocol.valueForString(example);

        assertNotNull("RAW returned null", enumRAW);
        assertNotNull("RTP returned null", enumRTP);
        assertNotNull("RTSP returned null", enumRTSP);
        assertNotNull("RTMP returned null", enumRTMP);
        assertNotNull("WEBM returned null", enumWEBM);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "RAAW";
        try {
            VideoStreamingProtocol temp = VideoStreamingProtocol.valueForString(example);
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
            VideoStreamingProtocol temp = VideoStreamingProtocol.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }


    /**
     * Verifies the possible enum values of TouchType.
     */
    public void testListEnum() {
        List<VideoStreamingProtocol> enumValueList = Arrays.asList(VideoStreamingProtocol.values());

        List<VideoStreamingProtocol> enumTestList = new ArrayList<VideoStreamingProtocol>();
        enumTestList.add(VideoStreamingProtocol.RAW);
        enumTestList.add(VideoStreamingProtocol.RTP);
        enumTestList.add(VideoStreamingProtocol.RTSP);
        enumTestList.add(VideoStreamingProtocol.RTMP);
        enumTestList.add(VideoStreamingProtocol.WEBM);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}

