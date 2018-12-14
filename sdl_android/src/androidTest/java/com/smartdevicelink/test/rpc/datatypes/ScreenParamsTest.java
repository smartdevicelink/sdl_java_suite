package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.ScreenParams;
import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ScreenParams}
 */
public class ScreenParamsTest extends TestCase {
	    
	private ScreenParams msg;

	@Override
	public void setUp() {
		msg = new ScreenParams();
		
		msg.setImageResolution(Test.GENERAL_IMAGERESOLUTION);
		msg.setTouchEventAvailable(Test.GENERAL_TOUCHEVENTCAPABILITIES);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		ImageResolution imageRes = msg.getImageResolution();
		TouchEventCapabilities touchEvent = msg.getTouchEventAvailable();
		
		// Valid Tests
		assertTrue(Test.TRUE, Validator.validateImageResolution(Test.GENERAL_IMAGERESOLUTION, imageRes));
		assertTrue(Test.TRUE, Validator.validateTouchEventCapabilities(Test.GENERAL_TOUCHEVENTCAPABILITIES, touchEvent));
		
		// Invalid/Null Tests
		ScreenParams msg = new ScreenParams();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getImageResolution());
		assertNull(Test.NULL, msg.getTouchEventAvailable());
	}
    
	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(ScreenParams.KEY_RESOLUTION, Test.JSON_IMAGERESOLUTION);
			reference.put(ScreenParams.KEY_TOUCH_EVENT_AVAILABLE, Test.JSON_TOUCHEVENTCAPABILITIES);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(ScreenParams.KEY_TOUCH_EVENT_AVAILABLE)) {
					JSONObject touchEventObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject touchEventObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(Test.TRUE, Validator.validateTouchEventCapabilities(
						new TouchEventCapabilities(JsonRPCMarshaller.deserializeJSONObject(touchEventObjReference)),
						new TouchEventCapabilities(JsonRPCMarshaller.deserializeJSONObject(touchEventObjTest))));
				} else if (key.equals(ScreenParams.KEY_RESOLUTION)) {
					JSONObject resolutionObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject resolutionObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue(Test.TRUE, Validator.validateImageResolution(
						new ImageResolution(JsonRPCMarshaller.deserializeJSONObject(resolutionObjReference)),
						new ImageResolution(JsonRPCMarshaller.deserializeJSONObject(resolutionObjTest))));
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}