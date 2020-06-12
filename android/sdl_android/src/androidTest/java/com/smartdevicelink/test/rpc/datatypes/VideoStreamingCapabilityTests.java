package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


public class VideoStreamingCapabilityTests extends TestCase {

	private VideoStreamingCapability msg;

	@Override
	public void setUp() {
		msg = new VideoStreamingCapability();
		msg.setSupportedFormats(Test.GENERAL_VIDEOSTREAMINGFORMAT_LIST);
		msg.setPreferredResolution(Test.GENERAL_IMAGERESOLUTION);
		msg.setMaxBitrate(Test.GENERAL_INT);
		msg.setIsHapticSpatialDataSupported(Test.GENERAL_BOOLEAN);
		msg.setDiagonalScreenSize(Test.GENERAL_DOUBLE);
		msg.setPixelPerInch(Test.GENERAL_DOUBLE);
		msg.setScale(Test.GENERAL_DOUBLE);
		msg.setPreferredFPS(Test.GENERAL_INTEGER);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		List<VideoStreamingFormat> format = msg.getSupportedFormats();
		ImageResolution res = msg.getPreferredResolution();
		Integer maxBitrate = msg.getMaxBitrate();
		Boolean isHapticSpatialDataSupported = msg.getIsHapticSpatialDataSupported();
		Double diagonalScreenSize = msg.getDiagonalScreenSize();
		Double pixelPerInch = msg.getPixelPerInch();
		Double scale = msg.getScale();
		Integer preferredFPS = msg.getPreferredFPS();

		// Valid Tests
		assertEquals(Test.MATCH, (List<VideoStreamingFormat>) Test.GENERAL_VIDEOSTREAMINGFORMAT_LIST, format);
		assertEquals(Test.MATCH, (ImageResolution) Test.GENERAL_IMAGERESOLUTION, res);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, maxBitrate);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, isHapticSpatialDataSupported);
		assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, diagonalScreenSize);
		assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, pixelPerInch);
		assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, scale);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, preferredFPS);

		// Invalid/Null Tests
		VideoStreamingCapability msg = new VideoStreamingCapability();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getMaxBitrate());
		assertNull(Test.NULL, msg.getPreferredResolution());
		assertNull(Test.NULL, msg.getSupportedFormats());
		assertNull(Test.NULL, msg.getIsHapticSpatialDataSupported());
		assertNull(Test.NULL, msg.getDiagonalScreenSize());
		assertNull(Test.NULL, msg.getPixelPerInch());
		assertNull(Test.NULL, msg.getScale());
		assertNull(Test.NULL, msg.getPreferredFPS());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(VideoStreamingCapability.KEY_MAX_BITRATE, Test.GENERAL_INT);
			reference.put(VideoStreamingCapability.KEY_PREFERRED_RESOLUTION, Test.GENERAL_IMAGERESOLUTION);
			reference.put(VideoStreamingCapability.KEY_SUPPORTED_FORMATS, Test.GENERAL_VIDEOSTREAMINGFORMAT_LIST);
			reference.put(VideoStreamingCapability.KEY_HAPTIC_SPATIAL_DATA_SUPPORTED, Test.GENERAL_BOOLEAN);
			reference.put(VideoStreamingCapability.KEY_DIAGONAL_SCREEN_SIZE, Test.GENERAL_DOUBLE);
			reference.put(VideoStreamingCapability.KEY_PIXEL_PER_INCH, Test.GENERAL_DOUBLE);
			reference.put(VideoStreamingCapability.KEY_SCALE, Test.GENERAL_DOUBLE);
			reference.put(VideoStreamingCapability.KEY_PREFERRED_FPS, Test.GENERAL_INTEGER);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(VideoStreamingCapability.KEY_MAX_BITRATE) || key.equals(VideoStreamingCapability.KEY_HAPTIC_SPATIAL_DATA_SUPPORTED) || key.equals(VideoStreamingCapability.KEY_PREFERRED_FPS)) {
					assertTrue(Test.TRUE, JsonUtils.readIntegerFromJsonObject(reference, key) == JsonUtils.readIntegerFromJsonObject(underTest, key));
				} else if (key.equals(VideoStreamingCapability.KEY_PREFERRED_RESOLUTION)) {
					ImageResolution irReference = (ImageResolution) JsonUtils.readObjectFromJsonObject(reference, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(JsonUtils.readJsonObjectFromJsonObject(underTest, key));
					assertTrue(Validator.validateImageResolution(irReference, new ImageResolution(hashTest)));
				} else if (key.equals(VideoStreamingCapability.KEY_SUPPORTED_FORMATS)){
					List<VideoStreamingFormat> vsfReference = (List<VideoStreamingFormat>) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONArray vsfArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					int i = 0;
					for(VideoStreamingFormat vsf : vsfReference){
						assertTrue(Validator.validateSupportedFormats(vsf, new VideoStreamingFormat(JsonRPCMarshaller.deserializeJSONObject(vsfArray.getJSONObject(i++)))));
					}
				} else if (key.equals(VideoStreamingCapability.KEY_DIAGONAL_SCREEN_SIZE) || key.equals(VideoStreamingCapability.KEY_PIXEL_PER_INCH) ||
						key.equals(VideoStreamingCapability.KEY_SCALE)) {
					assertEquals(JsonUtils.readDoubleFromJsonObject(reference, key), JsonUtils.readDoubleFromJsonObject(underTest, key), 0.0005);
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}
