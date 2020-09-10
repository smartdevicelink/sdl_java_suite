package com.smartdevicelink.test.streaming.video;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.TestValues;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;

@RunWith(AndroidJUnit4.class)
public class VideoStreamingParametersTest {

    private VideoStreamingParameters params;
    private VideoStreamingCapability capability;
    private ImageResolution preferredResolution;
    private final String vehicleMake = "SDL";

    @Before
    public void setUp() {
        params = new VideoStreamingParameters();
        capability = new VideoStreamingCapability();
    }

    @Test
    public void testUpdateNullScale() {
        preferredResolution = new ImageResolution(800, 354);

        capability.setScale(null);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability, vehicleMake);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(800, width);
        assertEquals(354, height);
    }

    @Test
    public void testUpdateScale_1_Resolution_800_354() {
        preferredResolution = new ImageResolution(800, 354);

        capability.setScale(1.0);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability, vehicleMake);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(800, width);
        assertEquals(354, height);
    }

    @Test
    public void testUpdateScale_1_25_Resolution_1280_569() {
        preferredResolution = new ImageResolution(1280, 569);

        capability.setScale(1.25);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability, vehicleMake);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(1024, width);
        assertEquals(456, height);
    }

    @Test
    public void testUpdateScale_1_5_Resolution_1280_569() {
        preferredResolution = new ImageResolution(1280, 569);

        capability.setScale(1.5);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability, vehicleMake);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(854, width);
        assertEquals(380, height);
    }

    @Test
    public void testUpdateScale_1_0_Ford_Resolution_800_354() {
        preferredResolution = new ImageResolution(800, 354);

        capability.setScale(1.0);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability, "Ford");

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(800, width);
        assertEquals(354, height);
    }

    @Test
    public void testUpdateScale_1_3_Lincoln_Resolution_600_900() {
        preferredResolution = new ImageResolution(600, 900);

        capability.setScale(1.0);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability, "Lincoln");

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(450, width);
        assertEquals(676, height);
    }

    @Test
    public void testUpdateScale_1_3_Ford_Resolution_900_600() {
        preferredResolution = new ImageResolution(900, 600);

        capability.setScale(1.0);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability, "Ford");

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(676, width);
        assertEquals(450, height);
    }

    @Test
    public void testUpdateScale_1_0_Toyota_Resolution_900_600() {
        preferredResolution = new ImageResolution(900, 600);

        capability.setScale(1.0);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability, "Toyota");

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(900, width);
        assertEquals(600, height);
    }

    @Test
    public void testUpdateCapabilityFormat(){
        VideoStreamingCapability capability = new VideoStreamingCapability();
        capability.setMaxBitrate(10000);
        capability.setPreferredResolution( new ImageResolution(800,600));
        capability.setIsHapticSpatialDataSupported(false);

        VideoStreamingFormat format = new VideoStreamingFormat(VideoStreamingProtocol.RAW, VideoStreamingCodec.H264);
        capability.setSupportedFormats(Collections.singletonList(format));

        VideoStreamingParameters params = new VideoStreamingParameters();
        params.setFormat(null);

        assertNull(params.getFormat());

        params.update(capability, vehicleMake);

        assertEquals(params.getFormat(), format);

        format = new VideoStreamingFormat(VideoStreamingProtocol.RTP, VideoStreamingCodec.H264);
        capability.setSupportedFormats(Collections.singletonList(format));
        params.update(capability, vehicleMake);
        assertEquals(params.getFormat(), format);

        format = new VideoStreamingFormat(VideoStreamingProtocol.RTP, VideoStreamingCodec.H265);
        capability.setSupportedFormats(Collections.singletonList(format));
        params.update(capability, vehicleMake);
        assertFalse(params.getFormat().equals(format));

        format = new VideoStreamingFormat(VideoStreamingProtocol.RAW, VideoStreamingCodec.VP8);
        capability.setSupportedFormats(Collections.singletonList(format));
        params.update(capability, vehicleMake);
        assertFalse(params.getFormat().equals(format));

    }

    @Test
    public void testAdditionalCapabilities() {
        capability.setAdditionalVideoStreamingCapabilities(TestValues.GENERAL_ADDITIONAL_CAPABILITY_LIST);
        assertEquals(TestValues.GENERAL_ADDITIONAL_CAPABILITY_LIST, capability.getAdditionalVideoStreamingCapabilities());

        capability.setAdditionalVideoStreamingCapabilities(null);
        assertNull(capability.getAdditionalVideoStreamingCapabilities());
    }
}
