package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.test.JsonUtils;

public class TouchCoordTest extends TestCase {

	private static final Integer X = 0;
	private static final Integer Y = 0;
	
	private TouchCoord msg;

	@Override
	public void setUp() {
		msg = new TouchCoord();
		
		msg.setX(X);
		msg.setY(Y);
	}

	public void testX () {
		Integer copy = msg.getX();
		
		assertEquals("Input value didn't match expected value.", X, copy);
	}
	
	public void testY () {
		Integer copy = msg.getY();
		
		assertEquals("Input value didn't match expected value.", Y, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TouchCoord.KEY_X, X);
			reference.put(TouchCoord.KEY_Y, Y);

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
		TouchCoord msg = new TouchCoord();
		assertNotNull("Null object creation failed.", msg);

		assertNull("X wasn't set, but getter method returned an object.", msg.getX());
		assertNull("Y wasn't set, but getter method returned an object.", msg.getY());
	}
}
