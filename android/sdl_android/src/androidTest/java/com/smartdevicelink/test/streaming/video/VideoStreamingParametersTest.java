package com.smartdevicelink.test.streaming.video;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import java.util.Collections;


public class VideoStreamingParametersTest extends AndroidTestCase2 {

    private VideoStreamingParameters params;
    private VideoStreamingCapability capability;
    private ImageResolution preferredResolution;

    public void setUp() {
        params = new VideoStreamingParameters();
        capability = new VideoStreamingCapability();
    }

    public void testUpdateNullScale() {
        preferredResolution = new ImageResolution(800, 354);

        capability.setScale(null);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(800, width);
        assertEquals(354, height);
    }

    public void testUpdateScale_1_Resolution_800_354() {
        preferredResolution = new ImageResolution(800, 354);

        capability.setScale(1.0);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(800, width);
        assertEquals(354, height);
    }

    public void testUpdateScale_1_25_Resolution_1280_569() {
        preferredResolution = new ImageResolution(1280, 569);

        capability.setScale(1.25);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(1024, width);
        assertEquals(456, height);
    }

    public void testUpdateScale_1_5_Resolution_1280_569() {
        preferredResolution = new ImageResolution(1280, 569);

        capability.setScale(1.5);
        capability.setPreferredResolution(preferredResolution);

        params.update(capability);

        int width = params.getResolution().getResolutionWidth();
        int height = params.getResolution().getResolutionHeight();

        assertEquals(854, width);
        assertEquals(380, height);
    }

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

        params.update(capability);

        assertEquals(params.getFormat(), format);

        format = new VideoStreamingFormat(VideoStreamingProtocol.RTP, VideoStreamingCodec.H264);
        capability.setSupportedFormats(Collections.singletonList(format));
        params.update(capability);
        assertEquals(params.getFormat(), format);

        format = new VideoStreamingFormat(VideoStreamingProtocol.RTP, VideoStreamingCodec.H265);
        capability.setSupportedFormats(Collections.singletonList(format));
        params.update(capability);
        assertFalse(params.getFormat().equals(format));

        format = new VideoStreamingFormat(VideoStreamingProtocol.RAW, VideoStreamingCodec.VP8);
        capability.setSupportedFormats(Collections.singletonList(format));
        params.update(capability);
        assertFalse(params.getFormat().equals(format));

    }
}
