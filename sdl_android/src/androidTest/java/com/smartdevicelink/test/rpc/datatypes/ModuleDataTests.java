package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AudioControlData;
import com.smartdevicelink.proxy.rpc.ClimateControlData;
import com.smartdevicelink.proxy.rpc.HMISettingsControlData;
import com.smartdevicelink.proxy.rpc.LightControlData;
import com.smartdevicelink.proxy.rpc.ModuleData;
import com.smartdevicelink.proxy.rpc.RadioControlData;
import com.smartdevicelink.proxy.rpc.SeatControlData;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.ModuleData}
 */
public class ModuleDataTests extends TestCase {

	private ModuleData msg;

	@Override
	public void setUp() {
		msg = new ModuleData();
		msg.setModuleType(Test.GENERAL_MODULETYPE);
		msg.setRadioControlData(Test.GENERAL_RADIOCONTROLDATA);
		msg.setClimateControlData(Test.GENERAL_CLIMATECONTROLDATA);
		msg.setSeatControlData(Test.GENERAL_SEATCONTROLDATA);
		msg.setAudioControlData(Test.GENERAL_AUDIOCONTROLDATA);
		msg.setHmiSettingsControlData(Test.GENERAL_HMISETTINGSCONTROLDATA);
		msg.setLightControlData(Test.GENERAL_LIGHTCONTROLDATA);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		ModuleType moduleType = msg.getModuleType();
		RadioControlData radioControlData = msg.getRadioControlData();
		ClimateControlData climateControlData = msg.getClimateControlData();
		SeatControlData seatControlData = msg.getSeatControlData();
		AudioControlData audioControlData = msg.getAudioControlData();
		HMISettingsControlData hmiSettingsControlData = msg.getHmiSettingsControlData();
		LightControlData lightControlData = msg.getLightControlData();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_MODULETYPE, moduleType);
		assertTrue(Test.TRUE, Validator.validateRadioControlData(Test.GENERAL_RADIOCONTROLDATA, radioControlData));
		assertTrue(Test.TRUE, Validator.validateClimateControlData(Test.GENERAL_CLIMATECONTROLDATA, climateControlData));
		assertTrue(Test.TRUE, Validator.validateSeatControlData(Test.GENERAL_SEATCONTROLDATA, seatControlData));
		assertTrue(Test.TRUE, Validator.validateAudioControlData(Test.GENERAL_AUDIOCONTROLDATA, audioControlData));
		assertTrue(Test.TRUE, Validator.validateHMISettingsControlData(Test.GENERAL_HMISETTINGSCONTROLDATA, hmiSettingsControlData));
		assertTrue(Test.TRUE, Validator.validateLightControlData(Test.GENERAL_LIGHTCONTROLDATA, lightControlData));

		// Invalid/Null Tests
		ModuleData msg = new ModuleData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getModuleType());
		assertNull(Test.NULL, msg.getRadioControlData());
		assertNull(Test.NULL, msg.getClimateControlData());
		assertNull(Test.NULL, msg.getSeatControlData());
		assertNull(Test.NULL, msg.getAudioControlData());
		assertNull(Test.NULL, msg.getHmiSettingsControlData());
		assertNull(Test.NULL, msg.getLightControlData());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(ModuleData.KEY_MODULE_TYPE, Test.GENERAL_MODULETYPE);
			reference.put(ModuleData.KEY_RADIO_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_RADIOCONTROLDATA.getStore()));
			reference.put(ModuleData.KEY_CLIMATE_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_CLIMATECONTROLDATA.getStore()));
			reference.put(ModuleData.KEY_SEAT_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_SEATCONTROLDATA.getStore()));
			reference.put(ModuleData.KEY_AUDIO_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_AUDIOCONTROLDATA.getStore()));
			reference.put(ModuleData.KEY_HMI_SETTINGS_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_HMISETTINGSCONTROLDATA.getStore()));
			reference.put(ModuleData.KEY_LIGHT_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_LIGHTCONTROLDATA.getStore()));

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(ModuleData.KEY_RADIO_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateRadioControlData(new RadioControlData(hashReference), new RadioControlData(hashTest)));
				} else if (key.equals(ModuleData.KEY_CLIMATE_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateClimateControlData(new ClimateControlData(hashReference), new ClimateControlData(hashTest)));
				} else if (key.equals(ModuleData.KEY_SEAT_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateSeatControlData(new SeatControlData(hashReference), new SeatControlData(hashTest)));
				} else if (key.equals(ModuleData.KEY_AUDIO_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateAudioControlData(new AudioControlData(hashReference), new AudioControlData(hashTest)));
				} else if (key.equals(ModuleData.KEY_HMI_SETTINGS_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateHMISettingsControlData(new HMISettingsControlData(hashReference), new HMISettingsControlData(hashTest)));
				} else if (key.equals(ModuleData.KEY_LIGHT_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateLightControlData(new LightControlData(hashReference), new LightControlData(hashTest)));
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}