package com.smartdevicelink.managers.video;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.managers.video.resolution.Resolution;
import com.smartdevicelink.managers.video.resolution.VideoStreamingRange;
import com.smartdevicelink.proxy.rpc.ImageResolution;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class VideoStreamingRangeTests extends TestCase {

    VideoStreamingRange testRange;
    Resolution minResolution;
    Resolution maxResolution;
    Double minimumDiagonal;
    Double minimumAspectRatio;
    Double maximumAspectRatio;

    @Before
    public void setUp() {
        minResolution = new Resolution(640, 480);
        maxResolution = new Resolution(1920, 1080);
        minimumDiagonal = 3.3;
        minimumAspectRatio = 1.0;
        maximumAspectRatio = 2.0;
        testRange = new VideoStreamingRange(minResolution, maxResolution, minimumDiagonal, minimumAspectRatio, maximumAspectRatio);
    }

    @Test
    public void testResolutionInRange() {
        ImageResolution inRangeResolution = new ImageResolution(800, 600);
        assertTrue(testRange.isImageResolutionInRange(inRangeResolution));
    }

    @Test
    public void testAspectRatioInRange() {
        Double inRangeAspectRatio = 1.5;
        assertTrue(testRange.isAspectRatioInRange(inRangeAspectRatio));
    }
}
