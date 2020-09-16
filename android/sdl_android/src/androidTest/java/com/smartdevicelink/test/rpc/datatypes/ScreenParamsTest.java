package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.ScreenParams;
import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.ScreenParams}
 */
public class ScreenParamsTest extends TestCase {
	    
	private ScreenParams msg;

	@Override
	public void setUp() {
		msg = new ScreenParams();
		
		msg.setImageResolution(TestValues.GENERAL_IMAGERESOLUTION);
		msg.setTouchEventAvailable(TestValues.GENERAL_TOUCHEVENTCAPABILITIES);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		ImageResolution imageRes = msg.getImageResolution();
		TouchEventCapabilities touchEvent = msg.getTouchEventAvailable();
		
		// Valid Tests
		assertTrue(TestValues.TRUE, Validator.validateImageResolution(TestValues.GENERAL_IMAGERESOLUTION, imageRes));
		assertTrue(TestValues.TRUE, Validator.validateTouchEventCapabilities(TestValues.GENERAL_TOUCHEVENTCAPABILITIES, touchEvent));
		
		// Invalid/Null Tests
		ScreenParams msg = new ScreenParams();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getImageResolution());
		assertNull(TestValues.NULL, msg.getTouchEventAvailable());
	}
    
	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(ScreenParams.KEY_RESOLUTION, TestValues.JSON_IMAGERESOLUTION);
			reference.put(ScreenParams.KEY_TOUCH_EVENT_AVAILABLE, TestValues.JSON_TOUCHEVENTCAPABILITIES);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(ScreenParams.KEY_TOUCH_EVENT_AVAILABLE)) {
					JSONObject touchEventObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject touchEventObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateTouchEventCapabilities(
						new TouchEventCapabilities(JsonRPCMarshaller.deserializeJSONObject(touchEventObjReference)),
						new TouchEventCapabilities(JsonRPCMarshaller.deserializeJSONObject(touchEventObjTest))));
				} else if (key.equals(ScreenParams.KEY_RESOLUTION)) {
					JSONObject resolutionObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject resolutionObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(TestValues.TRUE, Validator.validateImageResolution(
						new ImageResolution(JsonRPCMarshaller.deserializeJSONObject(resolutionObjReference)),
						new ImageResolution(JsonRPCMarshaller.deserializeJSONObject(resolutionObjTest))));
				} else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}