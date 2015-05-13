package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.VehicleDataResult;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;
import com.smartdevicelink.test.JsonUtils;

public class VehicleDataResultTest extends TestCase {

	private static final VehicleDataType TYPE = VehicleDataType.VEHICLEDATA_BRAKING;
	private static final VehicleDataResultCode RESULT = VehicleDataResultCode.SUCCESS;
	
	private VehicleDataResult msg;

	@Override
	public void setUp() {
		msg = new VehicleDataResult();
		
		msg.setDataType(TYPE);
		msg.setResultCode(RESULT);
	}

	public void testType() {
		VehicleDataType copy = msg.getDataType();
		
		assertEquals("Input value didn't match expected value.", TYPE, copy);
	}
	
	public void testResult () {
		VehicleDataResultCode copy = msg.getResultCode();
		
		assertEquals("Input value didn't match expected value.", RESULT, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(VehicleDataResult.KEY_RESULT_CODE, RESULT);
			reference.put(VehicleDataResult.KEY_DATA_TYPE, TYPE);

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
		VehicleDataResult msg = new VehicleDataResult();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Type wasn't set, but getter method returned an object.", msg.getDataType());
		assertNull("Result wasn't set, but getter method returned an object.", msg.getResultCode());
	}
}
