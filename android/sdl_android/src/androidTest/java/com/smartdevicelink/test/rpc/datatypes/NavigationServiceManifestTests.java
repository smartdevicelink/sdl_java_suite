package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.NavigationServiceManifest;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.NavigationServiceManifest}
 */
public class NavigationServiceManifestTests extends TestCase {

	private NavigationServiceManifest msg;

	@Override
	public void setUp(){
		msg = new NavigationServiceManifest();

		msg.setAcceptsWayPoints(Test.GENERAL_BOOLEAN);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		boolean acceptsWayPoints = msg.getAcceptsWayPoints();

		// Valid Tests
		assertEquals(Test.GENERAL_BOOLEAN, acceptsWayPoints);

		// Invalid/Null Tests
		NavigationServiceManifest msg = new NavigationServiceManifest();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getAcceptsWayPoints());
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(NavigationServiceManifest.KEY_ACCEPTS_WAY_POINTS, Test.GENERAL_BOOLEAN);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}