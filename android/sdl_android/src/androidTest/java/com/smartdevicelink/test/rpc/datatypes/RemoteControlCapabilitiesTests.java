package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AudioControlCapabilities;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.ClimateControlCapabilities;
import com.smartdevicelink.proxy.rpc.HMISettingsControlCapabilities;
import com.smartdevicelink.proxy.rpc.LightControlCapabilities;
import com.smartdevicelink.proxy.rpc.RadioControlCapabilities;
import com.smartdevicelink.proxy.rpc.RemoteControlCapabilities;
import com.smartdevicelink.proxy.rpc.SeatControlCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.RemoteControlCapabilities}
 */
public class RemoteControlCapabilitiesTests extends TestCase {

	private RemoteControlCapabilities msg;

	@Override
	public void setUp() {
		msg = new RemoteControlCapabilities();
		msg.setButtonCapabilities(Test.GENERAL_BUTTONCAPABILITIES_LIST);
		msg.setRadioControlCapabilities(Test.GENERAL_RADIOCONTROLCAPABILITIES_LIST);
		msg.setClimateControlCapabilities(Test.GENERAL_CLIMATECONTROLCAPABILITIES_LIST);
		msg.setSeatControlCapabilities(Test.GENERAL_SEATCONTROLCAPABILITIES_LIST);
		msg.setAudioControlCapabilities(Test.GENERAL_AUDIOCONTROLCAPABILITIES_LIST);
		msg.setHmiSettingsControlCapabilities(Test.GENERAL_HMISETTINGSCONTROLCAPABILITIES);
		msg.setLightControlCapabilities(Test.GENERAL_LIGHTCONTROLCAPABILITIES);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		List<ButtonCapabilities> buttonCapabilities = msg.getButtonCapabilities();
		List<RadioControlCapabilities> radioControlCapabilities = msg.getRadioControlCapabilities();
		List<ClimateControlCapabilities> climateControlCapabilities = msg.getClimateControlCapabilities();
		List<SeatControlCapabilities> seatControlCapabilities = msg.getSeatControlCapabilities();
		List<AudioControlCapabilities> audioControlCapabilities = msg.getAudioControlCapabilities();
		HMISettingsControlCapabilities hmiSettingsControlCapabilities = msg.getHmiSettingsControlCapabilities();
		LightControlCapabilities lightControlCapabilities = msg.getLightControlCapabilities();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_BUTTONCAPABILITIES_LIST.size(), buttonCapabilities.size());
		assertEquals(Test.MATCH, Test.GENERAL_RADIOCONTROLCAPABILITIES_LIST.size(), radioControlCapabilities.size());
		assertEquals(Test.MATCH, Test.GENERAL_CLIMATECONTROLCAPABILITIES_LIST.size(), climateControlCapabilities.size());
		assertEquals(Test.MATCH, Test.GENERAL_SEATCONTROLCAPABILITIES_LIST.size(), seatControlCapabilities.size());

		assertTrue(Test.TRUE, Validator.validateButtonCapabilities(Test.GENERAL_BUTTONCAPABILITIES_LIST, buttonCapabilities));
		assertTrue(Test.TRUE, Validator.validateRadioControlCapabilities(Test.GENERAL_RADIOCONTROLCAPABILITIES_LIST, radioControlCapabilities));
		assertTrue(Test.TRUE, Validator.validateClimateControlCapabilities(Test.GENERAL_CLIMATECONTROLCAPABILITIES_LIST, climateControlCapabilities));
		assertTrue(Test.TRUE, Validator.validateSeatControlCapabilitiesList(Test.GENERAL_SEATCONTROLCAPABILITIES_LIST, seatControlCapabilities));
		assertTrue(Test.TRUE, Validator.validateAudioControlCapabilitiesList(Test.GENERAL_AUDIOCONTROLCAPABILITIES_LIST, audioControlCapabilities));
		assertTrue(Test.TRUE, Validator.validateHMISettingsControlCapabilities(Test.GENERAL_HMISETTINGSCONTROLCAPABILITIES, hmiSettingsControlCapabilities));
		assertTrue(Test.TRUE, Validator.validateLightControlCapabilities(Test.GENERAL_LIGHTCONTROLCAPABILITIES, lightControlCapabilities));

		// Invalid/Null Tests
		RemoteControlCapabilities msg = new RemoteControlCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getButtonCapabilities());
		assertNull(Test.NULL, msg.getRadioControlCapabilities());
		assertNull(Test.NULL, msg.getClimateControlCapabilities());
		assertNull(Test.NULL, msg.getSeatControlCapabilities());
		assertNull(Test.NULL, msg.getAudioControlCapabilities());
		assertNull(Test.NULL, msg.getHmiSettingsControlCapabilities());
		assertNull(Test.NULL, msg.getLightControlCapabilities());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(RemoteControlCapabilities.KEY_BUTTON_CAPABILITIES, Test.JSON_BUTTONCAPABILITIES);
			reference.put(RemoteControlCapabilities.KEY_RADIO_CONTROL_CAPABILITIES, Test.JSON_RADIOCONTROLCAPABILITIES);
			reference.put(RemoteControlCapabilities.KEY_CLIMATE_CONTROL_CAPABILITIES, Test.JSON_CLIMATECONTROLCAPABILITIES);
			reference.put(RemoteControlCapabilities.KEY_SEAT_CONTROL_CAPABILITIES, Test.GENERAL_SEATCONTROLCAPABILITIES_LIST);
			reference.put(RemoteControlCapabilities.KEY_AUDIO_CONTROL_CAPABILITIES, Test.GENERAL_AUDIOCONTROLCAPABILITIES_LIST);
			reference.put(RemoteControlCapabilities.KEY_HMI_SETTINGS_CONTROL_CAPABILITIES, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_HMISETTINGSCONTROLCAPABILITIES.getStore()));
			reference.put(RemoteControlCapabilities.KEY_LIGHT_CONTROL_CAPABILITIES, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_LIGHTCONTROLCAPABILITIES.getStore()));

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(RemoteControlCapabilities.KEY_BUTTON_CAPABILITIES)) {
					JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
					JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

					List<ButtonCapabilities> referenceList = new ArrayList<ButtonCapabilities>();
					List<ButtonCapabilities> testList = new ArrayList<ButtonCapabilities>();
					for (int i = 0; i < referenceArray.length(); i++) {
						Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
						referenceList.add(new ButtonCapabilities(hashReference));
						Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
						testList.add(new ButtonCapabilities(hashTest));
					}
					assertTrue(Test.TRUE, Validator.validateButtonCapabilities(referenceList, testList));
				} else if (key.equals(RemoteControlCapabilities.KEY_RADIO_CONTROL_CAPABILITIES)) {
					JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
					JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

					List<RadioControlCapabilities> referenceList = new ArrayList<RadioControlCapabilities>();
					List<RadioControlCapabilities> testList = new ArrayList<RadioControlCapabilities>();
					for (int i = 0; i < referenceArray.length(); i++) {
						Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
						referenceList.add(new RadioControlCapabilities(hashReference));
						Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
						testList.add(new RadioControlCapabilities(hashTest));
					}
					assertTrue(Test.TRUE, Validator.validateRadioControlCapabilities(referenceList, testList));
				} else if (key.equals(RemoteControlCapabilities.KEY_CLIMATE_CONTROL_CAPABILITIES)) {
					JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
					JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

					List<ClimateControlCapabilities> referenceList = new ArrayList<ClimateControlCapabilities>();
					List<ClimateControlCapabilities> testList = new ArrayList<ClimateControlCapabilities>();
					for (int i = 0; i < referenceArray.length(); i++) {
						Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
						referenceList.add(new ClimateControlCapabilities(hashReference));
						Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
						testList.add(new ClimateControlCapabilities(hashTest));
					}
					assertTrue(Test.TRUE, Validator.validateClimateControlCapabilities(referenceList, testList));
				} else if (key.equals(RemoteControlCapabilities.KEY_SEAT_CONTROL_CAPABILITIES)) {
					List<SeatControlCapabilities> sccReference = (List<SeatControlCapabilities>) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONArray sccArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					int i = 0;
					for (SeatControlCapabilities scc : sccReference) {
						assertTrue(Validator.validateSeatControlCapabilities(scc, new SeatControlCapabilities(JsonRPCMarshaller.deserializeJSONObject(sccArray.getJSONObject(i++)))));
					}
				} else if (key.equals(RemoteControlCapabilities.KEY_AUDIO_CONTROL_CAPABILITIES)) {
					List<AudioControlCapabilities> accReference = (List<AudioControlCapabilities>) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONArray accArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					int i = 0;
					for (AudioControlCapabilities acc : accReference) {
						assertTrue(Validator.validateAudioControlCapabilities(acc, new AudioControlCapabilities(JsonRPCMarshaller.deserializeJSONObject(accArray.getJSONObject(i++)))));
					}
				} else if (key.equals(RemoteControlCapabilities.KEY_HMI_SETTINGS_CONTROL_CAPABILITIES)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateHMISettingsControlCapabilities(new HMISettingsControlCapabilities(hashReference), new HMISettingsControlCapabilities(hashTest)));
				} else if (key.equals(RemoteControlCapabilities.KEY_LIGHT_CONTROL_CAPABILITIES)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateLightControlCapabilities(new LightControlCapabilities(hashReference), new LightControlCapabilities(hashTest)));
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}