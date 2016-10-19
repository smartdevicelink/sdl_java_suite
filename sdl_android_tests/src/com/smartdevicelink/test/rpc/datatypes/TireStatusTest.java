package com.smartdevicelink.test.rpc.datatypes;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.SingleTireStatus;
import com.smartdevicelink.proxy.rpc.TireStatus;
import com.smartdevicelink.proxy.rpc.enums.WarningLightStatus;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.TireStatus}
 */
public class TireStatusTest extends TestCase {
	
	private TireStatus msg;

	@Override
	public void setUp() {
		msg = new TireStatus();
		msg.setPressureTellTale(Test.GENERAL_WARNINGLIGHTSTATUS);
    	SingleTireStatus tireLeftFront = new SingleTireStatus();
    	tireLeftFront.setStatus(Test.GENERAL_COMPONENTVOLUMESTATUS);
    	msg.setLeftFront(tireLeftFront);
    	SingleTireStatus tireRightFront = new SingleTireStatus();
    	tireRightFront.setStatus(Test.GENERAL_COMPONENTVOLUMESTATUS);
    	msg.setRightFront(tireRightFront);
    	SingleTireStatus tireLeftRear = new SingleTireStatus();
    	tireLeftRear.setStatus(Test.GENERAL_COMPONENTVOLUMESTATUS);
    	msg.setLeftRear(tireLeftRear);
    	SingleTireStatus tireRightRear = new SingleTireStatus();
    	tireRightRear.setStatus(Test.GENERAL_COMPONENTVOLUMESTATUS);
    	msg.setRightRear(tireRightRear);
    	SingleTireStatus tireInnerLeftRear = new SingleTireStatus();
    	tireInnerLeftRear.setStatus(Test.GENERAL_COMPONENTVOLUMESTATUS);
    	msg.setInnerLeftRear(tireInnerLeftRear);
    	SingleTireStatus tireInnerRightRear = new SingleTireStatus();
    	tireInnerRightRear.setStatus(Test.GENERAL_COMPONENTVOLUMESTATUS);
    	msg.setInnerRightRear(tireInnerRightRear);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		WarningLightStatus pressure = msg.getPressureTellTale();
		SingleTireStatus leftFront = msg.getLeftFront();
		SingleTireStatus rightFront = msg.getRightFront();
		SingleTireStatus leftRear = msg.getLeftRear();
		SingleTireStatus rightRear = msg.getRightRear();
		SingleTireStatus innerLeftRear = msg.getInnerLeftRear();
		SingleTireStatus innerRightRear = msg.getInnerRightRear();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_WARNINGLIGHTSTATUS, pressure);
		assertEquals(Test.MATCH, Test.GENERAL_COMPONENTVOLUMESTATUS, leftFront.getStatus());
		assertEquals(Test.MATCH, Test.GENERAL_COMPONENTVOLUMESTATUS, rightFront.getStatus());
		assertEquals(Test.MATCH, Test.GENERAL_COMPONENTVOLUMESTATUS, leftRear.getStatus());
		assertEquals(Test.MATCH, Test.GENERAL_COMPONENTVOLUMESTATUS, rightRear.getStatus());
		assertEquals(Test.MATCH, Test.GENERAL_COMPONENTVOLUMESTATUS, innerLeftRear.getStatus());
		assertEquals(Test.MATCH, Test.GENERAL_COMPONENTVOLUMESTATUS, innerRightRear.getStatus());
		
		// Invalid/Null Tests
		TireStatus msg = new TireStatus();
		assertNotNull(Test.NOT_NULL, msg);	
	}

	public void testJson() {
		JSONObject reference = new JSONObject();
		
		try {
			reference.put(TireStatus.KEY_PRESSURE_TELL_TALE, Test.GENERAL_WARNINGLIGHTSTATUS);
			JSONObject tireLeftFront = new JSONObject();
			tireLeftFront.put(SingleTireStatus.KEY_STATUS, Test.GENERAL_COMPONENTVOLUMESTATUS);
			reference.put(TireStatus.KEY_LEFT_FRONT, tireLeftFront);
			JSONObject tireRightFront = new JSONObject();
			tireRightFront.put(SingleTireStatus.KEY_STATUS, Test.GENERAL_COMPONENTVOLUMESTATUS);
			reference.put(TireStatus.KEY_RIGHT_FRONT, tireRightFront);
			JSONObject tireLeftRear = new JSONObject();
			tireLeftRear.put(SingleTireStatus.KEY_STATUS, Test.GENERAL_COMPONENTVOLUMESTATUS);
			reference.put(TireStatus.KEY_LEFT_REAR, tireLeftRear);
			JSONObject tireRightRear = new JSONObject();
			tireRightRear.put(SingleTireStatus.KEY_STATUS, Test.GENERAL_COMPONENTVOLUMESTATUS);
			reference.put(TireStatus.KEY_RIGHT_REAR, tireRightRear);
			JSONObject tireInnerLeftRear = new JSONObject();
			tireInnerLeftRear.put(SingleTireStatus.KEY_STATUS, Test.GENERAL_COMPONENTVOLUMESTATUS);
			reference.put(TireStatus.KEY_INNER_LEFT_REAR, tireInnerLeftRear);
			JSONObject tireInnerRightRear = new JSONObject();
			tireInnerRightRear.put(SingleTireStatus.KEY_STATUS, Test.GENERAL_COMPONENTVOLUMESTATUS);
			reference.put(TireStatus.KEY_INNER_RIGHT_REAR, tireInnerRightRear);
			
			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());
			
			assertTrue(Test.TRUE, Validator.validateTireStatus(
				new TireStatus(JsonRPCMarshaller.deserializeJSONObject(reference)),
				new TireStatus(JsonRPCMarshaller.deserializeJSONObject(underTest))));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}