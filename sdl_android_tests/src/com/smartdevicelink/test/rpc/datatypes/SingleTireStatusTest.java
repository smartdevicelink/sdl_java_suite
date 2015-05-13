package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.SingleTireStatus;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.test.JsonUtils;

public class SingleTireStatusTest extends TestCase {

	private static final ComponentVolumeStatus STATUS = ComponentVolumeStatus.NORMAL;
	
	private SingleTireStatus msg;

	@Override
	public void setUp() {
		msg = new SingleTireStatus();
		
		msg.setStatus(STATUS);
	}

	public void testStatus() {
		ComponentVolumeStatus copy = msg.getStatus();
		
		assertEquals("Input value didn't match expected value.", STATUS, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SingleTireStatus.KEY_STATUS, STATUS);

			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

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
		SingleTireStatus msg = new SingleTireStatus();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Status wasn't set, but getter method returned an object.", msg.getStatus());
	}
}
