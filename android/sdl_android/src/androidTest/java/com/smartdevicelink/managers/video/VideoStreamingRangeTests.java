package com.smartdevicelink.managers.video;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.managers.video.resolution.Resolution;
import com.smartdevicelink.managers.video.resolution.VideoStreamingRange;
import com.smartdevicelink.proxy.rpc.ImageResolution;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

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
    public void testResolutionOutRange() {
        ImageResolution outOfRangeResolution = new ImageResolution(600, 800);
        assertFalse(testRange.isImageResolutionInRange(outOfRangeResolution));
    }

    @Test
    public void testResolutionWithNullResolutionRange() {
        testRange.setMinSupportedResolution(null);
        testRange.setMaxSupportedResolution(null);
        Integer randomResolutionParameter = new Random().nextInt(3);
        ImageResolution randomResolution = new ImageResolution(randomResolutionParameter, randomResolutionParameter);
        assertTrue(testRange.isImageResolutionInRange(randomResolution));
    }

    @Test
    public void testResolutionWithDisabledResolutionRange() {
        ImageResolution resolution = new ImageResolution(0, 0);
        assertFalse(testRange.isImageResolutionInRange(resolution));
    }

    @Test
    public void testAspectRatioInRange() {
        Double inRangeAspectRatio = 1.5;
        assertTrue(testRange.isAspectRatioInRange(inRangeAspectRatio));
    }

    @Test
    public void testAspectRatioOutRange() {
        Double outOfRangeAspectRatio = 0.9;
        assertFalse(testRange.isAspectRatioInRange(outOfRangeAspectRatio));
    }

    @Test
    public void testAspectRatioWithNullAspectRatioRange() {
        testRange.setMaximumAspectRatio(null);
        testRange.setMinimumAspectRatio(null);
        Double randomAspectRatioParameter = new Random().nextDouble();
        assertTrue(testRange.isAspectRatioInRange(randomAspectRatioParameter));
    }

    @Test
    public void testAspectRatioWithDisabledAspectRatioRange() {
        Double aspectRatio = 0.;
        assertFalse(testRange.isAspectRatioInRange(aspectRatio));
    }
}
