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
import com.smartdevicelink.test.Validator;

public class ScreenParamsTest extends TestCase {

	private static final ImageResolution IMAGE_RESOLUTION 				 = new ImageResolution();
	private static final Integer 		 IMAGE_RESOLUTION_WIDTH 	 	 = 640;
	private static final Integer 		 IMAGE_RESOLUTION_HEIGHT 		 = 480;
	
	private static final TouchEventCapabilities TOUCH_EVENT_CAPABILITIES = new TouchEventCapabilities();
    private static final boolean         PRESS_AVAILABLE                 = true;
    private static final boolean         MULTI_TOUCH_AVAILABLE           = true;
    private static final boolean         DOUBLE_PRESS_AVAILABLE          = true;
	    
	private ScreenParams msg;

	@Override
	public void setUp() {
		createCustomObjects();
		
		msg = new ScreenParams();
		
		msg.setImageResolution(IMAGE_RESOLUTION);
		msg.setTouchEventAvailable(TOUCH_EVENT_CAPABILITIES);
	}
	
	public void createCustomObjects() {
		TOUCH_EVENT_CAPABILITIES.setPressAvailable(PRESS_AVAILABLE);
		TOUCH_EVENT_CAPABILITIES.setMultiTouchAvailable(MULTI_TOUCH_AVAILABLE);
		TOUCH_EVENT_CAPABILITIES.setDoublePressAvailable(DOUBLE_PRESS_AVAILABLE);
		IMAGE_RESOLUTION.setResolutionWidth(IMAGE_RESOLUTION_WIDTH);
		IMAGE_RESOLUTION.setResolutionHeight(IMAGE_RESOLUTION_HEIGHT);
	}

	public void testImageResolution () {
		ImageResolution copy = msg.getImageResolution();
		
		assertTrue("Input value didn't match expected value.", Validator.validateImageResolution(IMAGE_RESOLUTION, copy));
	}
	
	public void testTouchEventCapabilities () {
		TouchEventCapabilities copy = msg.getTouchEventAvailable();
		
		assertTrue("Input value didn't match expected value.", Validator.validateTouchEventCapabilities(TOUCH_EVENT_CAPABILITIES, copy));
	}
    
	public void testJson() {
		JSONObject reference = new JSONObject();
		JSONObject imageRes = new JSONObject();
		JSONObject touchEvent = new JSONObject();

		try {
			imageRes.put(ImageResolution.KEY_RESOLUTION_HEIGHT, IMAGE_RESOLUTION.getResolutionHeight());
			imageRes.put(ImageResolution.KEY_RESOLUTION_WIDTH, IMAGE_RESOLUTION.getResolutionWidth());
			
			touchEvent.put(TouchEventCapabilities.KEY_DOUBLE_PRESS_AVAILABLE, TOUCH_EVENT_CAPABILITIES.getDoublePressAvailable());
			touchEvent.put(TouchEventCapabilities.KEY_MULTI_TOUCH_AVAILABLE, TOUCH_EVENT_CAPABILITIES.getMultiTouchAvailable());
			touchEvent.put(TouchEventCapabilities.KEY_PRESS_AVAILABLE, TOUCH_EVENT_CAPABILITIES.getPressAvailable());
			
			reference.put(ScreenParams.KEY_RESOLUTION, imageRes);
			reference.put(ScreenParams.KEY_TOUCH_EVENT_AVAILABLE, touchEvent);

			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.",
					reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(ScreenParams.KEY_TOUCH_EVENT_AVAILABLE)) {
					JSONObject touchEventObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject touchEventObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateTouchEventCapabilities(
									new TouchEventCapabilities(JsonRPCMarshaller.deserializeJSONObject(touchEventObjReference)),
									new TouchEventCapabilities(JsonRPCMarshaller.deserializeJSONObject(touchEventObjTest))));
				} else if (key.equals(ScreenParams.KEY_RESOLUTION)) {
					JSONObject resolutionObjReference = JsonUtils.readJsonObjectFromJsonObject(reference, key);
					JSONObject resolutionObjTest = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
					
					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateImageResolution(
									new ImageResolution(JsonRPCMarshaller.deserializeJSONObject(resolutionObjReference)),
									new ImageResolution(JsonRPCMarshaller.deserializeJSONObject(resolutionObjTest))));
				} else {
					assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
							JsonUtils.readObjectFromJsonObject(reference, key),
							JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			/* do nothing */
		}
	}
	

	public void testNull() {
		ScreenParams msg = new ScreenParams();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Image resolution wasn't set, but getter method returned an object.", msg.getImageResolution());
		assertNull("Touch event available wasn't set, but getter method returned an object.", msg.getTouchEventAvailable());
	}
}