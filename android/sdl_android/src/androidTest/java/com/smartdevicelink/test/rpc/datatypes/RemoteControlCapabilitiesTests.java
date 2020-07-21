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
import com.smartdevicelink.test.TestValues;
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
		msg.setButtonCapabilities(TestValues.GENERAL_BUTTONCAPABILITIES_LIST);
		msg.setRadioControlCapabilities(TestValues.GENERAL_RADIOCONTROLCAPABILITIES_LIST);
		msg.setClimateControlCapabilities(TestValues.GENERAL_CLIMATECONTROLCAPABILITIES_LIST);
		msg.setSeatControlCapabilities(TestValues.GENERAL_SEATCONTROLCAPABILITIES_LIST);
		msg.setAudioControlCapabilities(TestValues.GENERAL_AUDIOCONTROLCAPABILITIES_LIST);
		msg.setHmiSettingsControlCapabilities(TestValues.GENERAL_HMISETTINGSCONTROLCAPABILITIES);
		msg.setLightControlCapabilities(TestValues.GENERAL_LIGHTCONTROLCAPABILITIES);
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
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BUTTONCAPABILITIES_LIST.size(), buttonCapabilities.size());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_RADIOCONTROLCAPABILITIES_LIST.size(), radioControlCapabilities.size());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_CLIMATECONTROLCAPABILITIES_LIST.size(), climateControlCapabilities.size());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_SEATCONTROLCAPABILITIES_LIST.size(), seatControlCapabilities.size());

		assertTrue(TestValues.TRUE, Validator.validateButtonCapabilities(TestValues.GENERAL_BUTTONCAPABILITIES_LIST, buttonCapabilities));
		assertTrue(TestValues.TRUE, Validator.validateRadioControlCapabilities(TestValues.GENERAL_RADIOCONTROLCAPABILITIES_LIST, radioControlCapabilities));
		assertTrue(TestValues.TRUE, Validator.validateClimateControlCapabilities(TestValues.GENERAL_CLIMATECONTROLCAPABILITIES_LIST, climateControlCapabilities));
		assertTrue(TestValues.TRUE, Validator.validateSeatControlCapabilitiesList(TestValues.GENERAL_SEATCONTROLCAPABILITIES_LIST, seatControlCapabilities));
		assertTrue(TestValues.TRUE, Validator.validateAudioControlCapabilitiesList(TestValues.GENERAL_AUDIOCONTROLCAPABILITIES_LIST, audioControlCapabilities));
		assertTrue(TestValues.TRUE, Validator.validateHMISettingsControlCapabilities(TestValues.GENERAL_HMISETTINGSCONTROLCAPABILITIES, hmiSettingsControlCapabilities));
		assertTrue(TestValues.TRUE, Validator.validateLightControlCapabilities(TestValues.GENERAL_LIGHTCONTROLCAPABILITIES, lightControlCapabilities));

		// Invalid/Null Tests
		RemoteControlCapabilities msg = new RemoteControlCapabilities();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getButtonCapabilities());
		assertNull(TestValues.NULL, msg.getRadioControlCapabilities());
		assertNull(TestValues.NULL, msg.getClimateControlCapabilities());
		assertNull(TestValues.NULL, msg.getSeatControlCapabilities());
		assertNull(TestValues.NULL, msg.getAudioControlCapabilities());
		assertNull(TestValues.NULL, msg.getHmiSettingsControlCapabilities());
		assertNull(TestValues.NULL, msg.getLightControlCapabilities());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(RemoteControlCapabilities.KEY_BUTTON_CAPABILITIES, TestValues.JSON_BUTTONCAPABILITIES);
			reference.put(RemoteControlCapabilities.KEY_RADIO_CONTROL_CAPABILITIES, TestValues.JSON_RADIOCONTROLCAPABILITIES);
			reference.put(RemoteControlCapabilities.KEY_CLIMATE_CONTROL_CAPABILITIES, TestValues.JSON_CLIMATECONTROLCAPABILITIES);
			reference.put(RemoteControlCapabilities.KEY_SEAT_CONTROL_CAPABILITIES, TestValues.GENERAL_SEATCONTROLCAPABILITIES_LIST);
			reference.put(RemoteControlCapabilities.KEY_AUDIO_CONTROL_CAPABILITIES, TestValues.GENERAL_AUDIOCONTROLCAPABILITIES_LIST);
			reference.put(RemoteControlCapabilities.KEY_HMI_SETTINGS_CONTROL_CAPABILITIES, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_HMISETTINGSCONTROLCAPABILITIES.getStore()));
			reference.put(RemoteControlCapabilities.KEY_LIGHT_CONTROL_CAPABILITIES, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_LIGHTCONTROLCAPABILITIES.getStore()));

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(RemoteControlCapabilities.KEY_BUTTON_CAPABILITIES)) {
					JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
					JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

					List<ButtonCapabilities> referenceList = new ArrayList<ButtonCapabilities>();
					List<ButtonCapabilities> testList = new ArrayList<ButtonCapabilities>();
					for (int i = 0; i < referenceArray.length(); i++) {
						Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
						referenceList.add(new ButtonCapabilities(hashReference));
						Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
						testList.add(new ButtonCapabilities(hashTest));
					}
					assertTrue(TestValues.TRUE, Validator.validateButtonCapabilities(referenceList, testList));
				} else if (key.equals(RemoteControlCapabilities.KEY_RADIO_CONTROL_CAPABILITIES)) {
					JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
					JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

					List<RadioControlCapabilities> referenceList = new ArrayList<RadioControlCapabilities>();
					List<RadioControlCapabilities> testList = new ArrayList<RadioControlCapabilities>();
					for (int i = 0; i < referenceArray.length(); i++) {
						Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
						referenceList.add(new RadioControlCapabilities(hashReference));
						Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
						testList.add(new RadioControlCapabilities(hashTest));
					}
					assertTrue(TestValues.TRUE, Validator.validateRadioControlCapabilities(referenceList, testList));
				} else if (key.equals(RemoteControlCapabilities.KEY_CLIMATE_CONTROL_CAPABILITIES)) {
					JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
					JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

					List<ClimateControlCapabilities> referenceList = new ArrayList<ClimateControlCapabilities>();
					List<ClimateControlCapabilities> testList = new ArrayList<ClimateControlCapabilities>();
					for (int i = 0; i < referenceArray.length(); i++) {
						Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
						referenceList.add(new ClimateControlCapabilities(hashReference));
						Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
						testList.add(new ClimateControlCapabilities(hashTest));
					}
					assertTrue(TestValues.TRUE, Validator.validateClimateControlCapabilities(referenceList, testList));
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
					assertTrue(TestValues.TRUE, Validator.validateHMISettingsControlCapabilities(new HMISettingsControlCapabilities(hashReference), new HMISettingsControlCapabilities(hashTest)));
				} else if (key.equals(RemoteControlCapabilities.KEY_LIGHT_CONTROL_CAPABILITIES)) {
					JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(TestValues.TRUE, Validator.validateLightControlCapabilities(new LightControlCapabilities(hashReference), new LightControlCapabilities(hashTest)));
				}
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}