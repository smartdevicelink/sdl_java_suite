package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


public class VideoStreamingCapabilityTests extends TestCase {

    private VideoStreamingCapability msg;

    @Override
    public void setUp() {
        msg = new VideoStreamingCapability();
        msg.setSupportedFormats(TestValues.GENERAL_VIDEOSTREAMINGFORMAT_LIST);
        msg.setPreferredResolution(TestValues.GENERAL_IMAGERESOLUTION);
        msg.setMaxBitrate(TestValues.GENERAL_INT);
        msg.setIsHapticSpatialDataSupported(TestValues.GENERAL_BOOLEAN);
        msg.setDiagonalScreenSize(TestValues.GENERAL_DOUBLE);
        msg.setPixelPerInch(TestValues.GENERAL_DOUBLE);
        msg.setScale(TestValues.GENERAL_DOUBLE);
        msg.setAdditionalVideoStreamingCapabilities(TestValues.GENERAL_ADDITIONAL_CAPABILITY_LIST);
        msg.setPreferredFPS(TestValues.GENERAL_INTEGER);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        List<VideoStreamingFormat> format = msg.getSupportedFormats();
        ImageResolution res = msg.getPreferredResolution();
        Integer maxBitrate = msg.getMaxBitrate();
        Boolean isHapticSpatialDataSupported = msg.getIsHapticSpatialDataSupported();
        Double diagonalScreenSize = msg.getDiagonalScreenSize();
        Double pixelPerInch = msg.getPixelPerInch();
        Double scale = msg.getScale();
        List<VideoStreamingCapability> additionalVideoStreamingCapabilities = msg.getAdditionalVideoStreamingCapabilities();
        Integer preferredFPS = msg.getPreferredFPS();

        // Valid Tests
        assertEquals(TestValues.MATCH, (List<VideoStreamingFormat>) TestValues.GENERAL_VIDEOSTREAMINGFORMAT_LIST, format);
        assertEquals(TestValues.MATCH, (ImageResolution) TestValues.GENERAL_IMAGERESOLUTION, res);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, maxBitrate);
        assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, isHapticSpatialDataSupported);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, diagonalScreenSize);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, pixelPerInch);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, scale);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_ADDITIONAL_CAPABILITY_LIST, additionalVideoStreamingCapabilities);

        // Invalid/Null Tests
        VideoStreamingCapability msg = new VideoStreamingCapability();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getMaxBitrate());
        assertNull(TestValues.NULL, msg.getPreferredResolution());
        assertNull(TestValues.NULL, msg.getSupportedFormats());
        assertNull(TestValues.NULL, msg.getIsHapticSpatialDataSupported());
        assertNull(TestValues.NULL, msg.getDiagonalScreenSize());
        assertNull(TestValues.NULL, msg.getPixelPerInch());
        assertNull(TestValues.NULL, msg.getScale());
        assertNull(TestValues.NULL, msg.getAdditionalVideoStreamingCapabilities());
        assertNull(TestValues.NULL, msg.getPreferredFPS());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();
        msg.setAdditionalVideoStreamingCapabilities(null);

        try {
            reference.put(VideoStreamingCapability.KEY_MAX_BITRATE, TestValues.GENERAL_INT);
            reference.put(VideoStreamingCapability.KEY_PREFERRED_RESOLUTION, TestValues.GENERAL_IMAGERESOLUTION);
            reference.put(VideoStreamingCapability.KEY_SUPPORTED_FORMATS, TestValues.GENERAL_VIDEOSTREAMINGFORMAT_LIST);
            reference.put(VideoStreamingCapability.KEY_HAPTIC_SPATIAL_DATA_SUPPORTED, TestValues.GENERAL_BOOLEAN);
            reference.put(VideoStreamingCapability.KEY_DIAGONAL_SCREEN_SIZE, TestValues.GENERAL_DOUBLE);
            reference.put(VideoStreamingCapability.KEY_PIXEL_PER_INCH, TestValues.GENERAL_DOUBLE);
            reference.put(VideoStreamingCapability.KEY_SCALE, TestValues.GENERAL_DOUBLE);
            reference.put(VideoStreamingCapability.KEY_PREFERRED_FPS, TestValues.GENERAL_INTEGER);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(VideoStreamingCapability.KEY_MAX_BITRATE) || key.equals(VideoStreamingCapability.KEY_HAPTIC_SPATIAL_DATA_SUPPORTED) || key.equals(VideoStreamingCapability.KEY_PREFERRED_FPS)) {
                    assertTrue(TestValues.TRUE, JsonUtils.readIntegerFromJsonObject(reference, key) == JsonUtils.readIntegerFromJsonObject(underTest, key));
                } else if (key.equals(VideoStreamingCapability.KEY_PREFERRED_RESOLUTION)) {
                    ImageResolution irReference = (ImageResolution) JsonUtils.readObjectFromJsonObject(reference, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(JsonUtils.readJsonObjectFromJsonObject(underTest, key));
                    assertTrue(Validator.validateImageResolution(irReference, new ImageResolution(hashTest)));
                } else if (key.equals(VideoStreamingCapability.KEY_SUPPORTED_FORMATS)) {
                    List<VideoStreamingFormat> vsfReference = (List<VideoStreamingFormat>) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONArray vsfArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    int i = 0;
                    for (VideoStreamingFormat vsf : vsfReference) {
                        assertTrue(Validator.validateSupportedFormats(vsf, new VideoStreamingFormat(JsonRPCMarshaller.deserializeJSONObject(vsfArray.getJSONObject(i++)))));
                    }
                } else if (key.equals(VideoStreamingCapability.KEY_DIAGONAL_SCREEN_SIZE) || key.equals(VideoStreamingCapability.KEY_PIXEL_PER_INCH) ||
                        key.equals(VideoStreamingCapability.KEY_SCALE)) {
                    assertEquals(JsonUtils.readDoubleFromJsonObject(reference, key), JsonUtils.readDoubleFromJsonObject(underTest, key), 0.0005);
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }

    @Test
    public void testFormatMethod() {
        List<VideoStreamingCapability> additionalCapabilities = msg.getAdditionalVideoStreamingCapabilities();
        msg.format(null, false);
        assertEquals(additionalCapabilities, msg.getAdditionalVideoStreamingCapabilities());
    }

    @Test
    public void testFormatWillRemoveSelf() {
        List<VideoStreamingCapability> additionalCapabilities = msg.getAdditionalVideoStreamingCapabilities();
        additionalCapabilities.add(msg);
        msg.format(null, false);
        assertEquals(additionalCapabilities.size(), msg.getAdditionalVideoStreamingCapabilities().size());
    }
}
