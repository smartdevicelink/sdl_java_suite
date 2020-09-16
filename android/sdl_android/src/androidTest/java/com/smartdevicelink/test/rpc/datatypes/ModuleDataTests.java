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
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.ModuleData}
 */
public class ModuleDataTests extends TestCase {

	private ModuleData msg;

	@Override
	public void setUp() {
		msg = new ModuleData();
		msg.setModuleType(TestValues.GENERAL_MODULETYPE);
		msg.setRadioControlData(TestValues.GENERAL_RADIOCONTROLDATA);
		msg.setClimateControlData(TestValues.GENERAL_CLIMATECONTROLDATA);
		msg.setSeatControlData(TestValues.GENERAL_SEATCONTROLDATA);
		msg.setAudioControlData(TestValues.GENERAL_AUDIOCONTROLDATA);
		msg.setHmiSettingsControlData(TestValues.GENERAL_HMISETTINGSCONTROLDATA);
		msg.setLightControlData(TestValues.GENERAL_LIGHTCONTROLDATA);
		msg.setModuleId(TestValues.GENERAL_STRING);
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
		String moduleId = msg.getModuleId();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULETYPE, moduleType);
		assertTrue(TestValues.TRUE, Validator.validateRadioControlData(TestValues.GENERAL_RADIOCONTROLDATA, radioControlData));
		assertTrue(TestValues.TRUE, Validator.validateClimateControlData(TestValues.GENERAL_CLIMATECONTROLDATA, climateControlData));
		assertTrue(TestValues.TRUE, Validator.validateSeatControlData(TestValues.GENERAL_SEATCONTROLDATA, seatControlData));
		assertTrue(TestValues.TRUE, Validator.validateAudioControlData(TestValues.GENERAL_AUDIOCONTROLDATA, audioControlData));
		assertTrue(TestValues.TRUE, Validator.validateHMISettingsControlData(TestValues.GENERAL_HMISETTINGSCONTROLDATA, hmiSettingsControlData));
		assertTrue(TestValues.TRUE, Validator.validateLightControlData(TestValues.GENERAL_LIGHTCONTROLDATA, lightControlData));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, moduleId);

		// Invalid/Null Tests
		ModuleData msg = new ModuleData();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getModuleType());
		assertNull(TestValues.NULL, msg.getRadioControlData());
		assertNull(TestValues.NULL, msg.getClimateControlData());
		assertNull(TestValues.NULL, msg.getSeatControlData());
		assertNull(TestValues.NULL, msg.getAudioControlData());
		assertNull(TestValues.NULL, msg.getHmiSettingsControlData());
		assertNull(TestValues.NULL, msg.getLightControlData());
		assertNull(TestValues.NULL, msg.getModuleId());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(ModuleData.KEY_MODULE_TYPE, TestValues.GENERAL_MODULETYPE);
			reference.put(ModuleData.KEY_RADIO_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_RADIOCONTROLDATA.getStore()));
			reference.put(ModuleData.KEY_CLIMATE_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_CLIMATECONTROLDATA.getStore()));
			reference.put(ModuleData.KEY_SEAT_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_SEATCONTROLDATA.getStore()));
			reference.put(ModuleData.KEY_AUDIO_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_AUDIOCONTROLDATA.getStore()));
			reference.put(ModuleData.KEY_HMI_SETTINGS_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_HMISETTINGSCONTROLDATA.getStore()));
			reference.put(ModuleData.KEY_LIGHT_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_LIGHTCONTROLDATA.getStore()));
			reference.put(ModuleData.KEY_MODULE_ID, TestValues.GENERAL_STRING);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(ModuleData.KEY_RADIO_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateRadioControlData(new RadioControlData(hashReference), new RadioControlData(hashTest)));
				} else if (key.equals(ModuleData.KEY_CLIMATE_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateClimateControlData(new ClimateControlData(hashReference), new ClimateControlData(hashTest)));
				} else if (key.equals(ModuleData.KEY_SEAT_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateSeatControlData(new SeatControlData(hashReference), new SeatControlData(hashTest)));
				} else if (key.equals(ModuleData.KEY_AUDIO_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateAudioControlData(new AudioControlData(hashReference), new AudioControlData(hashTest)));
				} else if (key.equals(ModuleData.KEY_HMI_SETTINGS_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateHMISettingsControlData(new HMISettingsControlData(hashReference), new HMISettingsControlData(hashTest)));
				} else if (key.equals(ModuleData.KEY_LIGHT_CONTROL_DATA)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateLightControlData(new LightControlData(hashReference), new LightControlData(hashTest)));
				} else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}