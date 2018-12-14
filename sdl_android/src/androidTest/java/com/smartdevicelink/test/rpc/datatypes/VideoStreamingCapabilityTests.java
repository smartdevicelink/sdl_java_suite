package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
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

		// Valid Tests
		assertEquals(Test.MATCH, (List<VideoStreamingFormat>) Test.GENERAL_VIDEOSTREAMINGFORMAT_LIST, format);
		assertEquals(Test.MATCH, (ImageResolution) Test.GENERAL_IMAGERESOLUTION, res);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, maxBitrate);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, isHapticSpatialDataSupported);

		// Invalid/Null Tests
		VideoStreamingCapability msg = new VideoStreamingCapability();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getMaxBitrate());
		assertNull(Test.NULL, msg.getPreferredResolution());
		assertNull(Test.NULL, msg.getSupportedFormats());
		assertNull(Test.NULL, msg.getIsHapticSpatialDataSupported());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(VideoStreamingCapability.KEY_MAX_BITRATE, Test.GENERAL_INT);
			reference.put(VideoStreamingCapability.KEY_PREFERRED_RESOLUTION, Test.GENERAL_IMAGERESOLUTION);
			reference.put(VideoStreamingCapability.KEY_SUPPORTED_FORMATS, Test.GENERAL_VIDEOSTREAMINGFORMAT_LIST);
			reference.put(VideoStreamingCapability.KEY_HAPTIC_SPATIAL_DATA_SUPPORTED, Test.GENERAL_BOOLEAN);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(VideoStreamingCapability.KEY_MAX_BITRATE) || key.equals(VideoStreamingCapability.KEY_HAPTIC_SPATIAL_DATA_SUPPORTED)) {
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
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}
