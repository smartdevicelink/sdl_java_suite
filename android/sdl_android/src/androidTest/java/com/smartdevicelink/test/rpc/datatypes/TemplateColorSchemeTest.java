package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.RGBColor;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.TemplateColorScheme}
 */
public class TemplateColorSchemeTest extends TestCase {
	
	private TemplateColorScheme msg;

	@Override
	public void setUp() {
		msg = new TemplateColorScheme();
		msg.setPrimaryColor(Test.GENERAL_RGBCOLOR);
		msg.setSecondaryColor(Test.GENERAL_RGBCOLOR);
		msg.setBackgroundColor(Test.GENERAL_RGBCOLOR);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		RGBColor primaryColor = msg.getPrimaryColor();
		RGBColor secondaryColor = msg.getSecondaryColor();
		RGBColor backgroundColor = msg.getBackgroundColor();

		// Valid Tests
		assertTrue(Test.TRUE, Validator.validateRGBColor(Test.GENERAL_RGBCOLOR, primaryColor));
		assertTrue(Test.TRUE, Validator.validateRGBColor(Test.GENERAL_RGBCOLOR, secondaryColor));
		assertTrue(Test.TRUE, Validator.validateRGBColor(Test.GENERAL_RGBCOLOR, backgroundColor));

		// Invalid/Null Tests
		TemplateColorScheme msg = new TemplateColorScheme();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getPrimaryColor());
		assertNull(Test.NULL, msg.getSecondaryColor());
		assertNull(Test.NULL, msg.getBackgroundColor());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TemplateColorScheme.KEY_PRIMARY_COLOR, Test.JSON_RGBCOLOR);
			reference.put(TemplateColorScheme.KEY_SECONDARY_COLOR, Test.JSON_RGBCOLOR);
			reference.put(TemplateColorScheme.KEY_BACKGROUND_COLOR, Test.JSON_RGBCOLOR);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				JSONObject referenceColorObj = JsonUtils.readJsonObjectFromJsonObject(reference, key);
				RGBColor referenceColor = new RGBColor(JsonRPCMarshaller.deserializeJSONObject(referenceColorObj));
				JSONObject underTestColorObj = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
				RGBColor underTestColor = new RGBColor(JsonRPCMarshaller.deserializeJSONObject(underTestColorObj));
				assertTrue(Test.TRUE, Validator.validateRGBColor(referenceColor, underTestColor));
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}