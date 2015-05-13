package com.smartdevicelink.test.rpc.datatypes;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.SingleTireStatus;
import com.smartdevicelink.proxy.rpc.TireStatus;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.WarningLightStatus;
import com.smartdevicelink.test.Validator;

public class TireStatusTest extends TestCase {

	//private static final Map<TireZone, SingleTireStatus> TIRE_STATUSES = new HashMap<TireZone, SingleTireStatus>();
	private static final WarningLightStatus TIRE_PRESSURE_TELL_TALE = WarningLightStatus.OFF;
	private static final ComponentVolumeStatus TIRE_PRESSURE_NORMAL = ComponentVolumeStatus.NORMAL;
	private static final ComponentVolumeStatus TIRE_PRESSURE_FAULT = ComponentVolumeStatus.FAULT;
	private static final ComponentVolumeStatus TIRE_PRESSURE_LOW = ComponentVolumeStatus.LOW;
	private static final ComponentVolumeStatus TIRE_PRESSURE_ALERT = ComponentVolumeStatus.ALERT;
	
	private TireStatus msg;

	@Override
	public void setUp() {
		msg = new TireStatus();
		msg.setPressureTellTale(TIRE_PRESSURE_TELL_TALE);
    	SingleTireStatus tireLeftFront = new SingleTireStatus();
    	tireLeftFront.setStatus(TIRE_PRESSURE_NORMAL);
    	msg.setLeftFront(tireLeftFront);
    	SingleTireStatus tireRightFront = new SingleTireStatus();
    	tireRightFront.setStatus(TIRE_PRESSURE_FAULT);
    	msg.setRightFront(tireRightFront);
    	SingleTireStatus tireLeftRear = new SingleTireStatus();
    	tireLeftRear.setStatus(TIRE_PRESSURE_LOW);
    	msg.setLeftRear(tireLeftRear);
    	SingleTireStatus tireRightRear = new SingleTireStatus();
    	tireRightRear.setStatus(TIRE_PRESSURE_NORMAL);
    	msg.setRightRear(tireRightRear);
    	SingleTireStatus tireInnerLeftRear = new SingleTireStatus();
    	tireInnerLeftRear.setStatus(TIRE_PRESSURE_LOW);
    	msg.setInnerLeftRear(tireInnerLeftRear);
    	SingleTireStatus tireInnerRightRear = new SingleTireStatus();
    	tireInnerRightRear.setStatus(TIRE_PRESSURE_ALERT);
    	msg.setInnerRightRear(tireInnerRightRear);
	}

	public void testPressureTellTale() {
		WarningLightStatus copy = msg.getPressureTellTale();
		assertEquals("Input value didn't match expected value.", TIRE_PRESSURE_TELL_TALE, copy);
	}
	
	public void testTireLeftFront() {
		SingleTireStatus copy = msg.getLeftFront();
		assertEquals("Tire status does not match expected value.", TIRE_PRESSURE_NORMAL, copy.getStatus());
	}
	
	public void testTireRightFront() {
		SingleTireStatus copy = msg.getRightFront();
		assertEquals("Tire status does not match expected value.", TIRE_PRESSURE_FAULT, copy.getStatus());
	}
	
	public void testTireLeftRear() {
		SingleTireStatus copy = msg.getLeftRear();
		assertEquals("Tire status does not match expected value.", TIRE_PRESSURE_LOW, copy.getStatus());
	}
	
	public void testTireRightRear() {
		SingleTireStatus copy = msg.getRightRear();
		assertEquals("Tire status does not match expected value.", TIRE_PRESSURE_NORMAL, copy.getStatus());
	}
	
	public void testTireInnerLeftRear() {
		SingleTireStatus copy = msg.getInnerLeftRear();
		assertEquals("Tire status does not match expected value.", TIRE_PRESSURE_LOW, copy.getStatus());
	}
	
	public void testTireInnerRightRear() {
		SingleTireStatus copy = msg.getInnerRightRear();
		assertEquals("Tire status does not match expected value.", TIRE_PRESSURE_ALERT, copy.getStatus());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();
		
		try {
			reference.put(TireStatus.KEY_PRESSURE_TELL_TALE, TIRE_PRESSURE_TELL_TALE);
			JSONObject tireLeftFront = new JSONObject();
			tireLeftFront.put(SingleTireStatus.KEY_STATUS, TIRE_PRESSURE_NORMAL);
			reference.put(TireStatus.KEY_LEFT_FRONT, tireLeftFront);
			JSONObject tireRightFront = new JSONObject();
			tireRightFront.put(SingleTireStatus.KEY_STATUS, TIRE_PRESSURE_FAULT);
			reference.put(TireStatus.KEY_RIGHT_FRONT, tireRightFront);
			JSONObject tireLeftRear = new JSONObject();
			tireLeftRear.put(SingleTireStatus.KEY_STATUS, TIRE_PRESSURE_LOW);
			reference.put(TireStatus.KEY_LEFT_REAR, tireLeftRear);
			JSONObject tireRightRear = new JSONObject();
			tireRightRear.put(SingleTireStatus.KEY_STATUS, TIRE_PRESSURE_NORMAL);
			reference.put(TireStatus.KEY_RIGHT_REAR, tireRightRear);
			JSONObject tireInnerLeftRear = new JSONObject();
			tireInnerLeftRear.put(SingleTireStatus.KEY_STATUS, TIRE_PRESSURE_LOW);
			reference.put(TireStatus.KEY_INNER_LEFT_REAR, tireInnerLeftRear);
			JSONObject tireInnerRightRear = new JSONObject();
			tireInnerRightRear.put(SingleTireStatus.KEY_STATUS, TIRE_PRESSURE_ALERT);
			reference.put(TireStatus.KEY_INNER_RIGHT_REAR, tireInnerRightRear);
			
			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.",
					reference.length(), underTest.length());
			
			//TODO: does this test what's needed?
			assertTrue("JSON didn't match expected JSON.",
					Validator.validateTireStatus(
							new TireStatus(JsonRPCMarshaller.deserializeJSONObject(reference)),
							new TireStatus(JsonRPCMarshaller.deserializeJSONObject(underTest))));
/*
			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(TireStatus.KEY_PRESSURE_TELL_TALE)) {
					assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
							JsonUtils.readObjectFromJsonObject(reference, key),
							JsonUtils.readObjectFromJsonObject(underTest, key));
				}
				else {
					System.out.println(JsonUtils.readJsonObjectFromJsonObject(reference, key));
					System.out.println(JsonUtils.readJsonObjectFromJsonObject(underTest, key));
					
					assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
							JsonUtils.readJsonObjectFromJsonObject(reference, key),
							JsonUtils.readJsonObjectFromJsonObject(underTest, key));
				}
			}
			*/
		} catch (JSONException e) {
			/* do nothing */
		}
	}

	public void testNull() {
		TireStatus msg = new TireStatus();
		assertNotNull("Null object creation failed.", msg);
	}
}
