package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.test.JsonUtils;

public class VehicleTypeTest extends TestCase {

	private static final String MAKE = "make";
	private static final String MODEL = "model";
	private static final String YEAR = "year";
	private static final String TRIM = "trim";
	
	private VehicleType msg;

	@Override
	public void setUp() {
		msg = new VehicleType();
		
		msg.setModel(MODEL);
		msg.setMake(MAKE);
		msg.setTrim(TRIM);
		msg.setModelYear(YEAR);
	}

	public void testModel() {
		String copy = msg.getModel();
		
		assertEquals("Input value didn't match expected value.", MODEL, copy);
	}
	
	public void testMake () {
		String copy = msg.getMake();
		
		assertEquals("Input value didn't match expected value.", MAKE, copy);
	}
	
	public void testTrim () {
		String copy = msg.getTrim();
		
		assertEquals("Input value didn't match expected value.", TRIM, copy);
	}
	
	public void testYear () {
		String copy = msg.getModelYear();
		
		assertEquals("Input value didn't match expected value.", YEAR, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(VehicleType.KEY_MODEL, MODEL);
			reference.put(VehicleType.KEY_MAKE, MAKE);
			reference.put(VehicleType.KEY_MODEL_YEAR, YEAR);
			reference.put(VehicleType.KEY_TRIM, TRIM);

			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.",
					reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(
						"JSON value didn't match expected value for key \""
								+ key + "\".",
						JsonUtils.readObjectFromJsonObject(reference, key),
						JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			/* do nothing */
		}
	}

	public void testNull() {
		VehicleType msg = new VehicleType();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Model wasn't set, but getter method returned an object.", msg.getModel());
		assertNull("Make wasn't set, but getter method returned an object.", msg.getMake());
		assertNull("Year wasn't set, but getter method returned an object.", msg.getModelYear());
		assertNull("Trim wasn't set, but getter method returned an object.", msg.getTrim());
	}
}
