package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.MassageCushionFirmness;
import com.smartdevicelink.proxy.rpc.MassageModeData;
import com.smartdevicelink.proxy.rpc.SeatControlData;
import com.smartdevicelink.proxy.rpc.SeatMemoryAction;
import com.smartdevicelink.proxy.rpc.enums.SupportedSeat;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.SeatControlData}
 */
public class SeatControlDataTest extends TestCase {

	private SeatControlData msg;

	@Override
	public void setUp() {
		msg = new SeatControlData();
		msg.setId(Test.GENERAL_SUPPORTEDSEAT);
		msg.setHeatingEnabled(Test.GENERAL_BOOLEAN);
		msg.setCoolingEnabled(Test.GENERAL_BOOLEAN);
		msg.setHeatingLevel(Test.GENERAL_INT);
		msg.setCoolingLevel(Test.GENERAL_INT);
		msg.setHorizontalPosition(Test.GENERAL_INT);
		msg.setVerticalPosition(Test.GENERAL_INT);
		msg.setFrontVerticalPosition(Test.GENERAL_INT);
		msg.setBackVerticalPosition(Test.GENERAL_INT);
		msg.setBackTiltAngle(Test.GENERAL_INT);
		msg.setHeadSupportVerticalPosition(Test.GENERAL_INT);
		msg.setHeadSupportHorizontalPosition(Test.GENERAL_INT);
		msg.setMassageEnabled(Test.GENERAL_BOOLEAN);
		msg.setMassageMode(Test.GENERAL_MASSAGEMODEDATA_LIST);
		msg.setMassageCushionFirmness(Test.GENERAL_MASSAGECUSHIONFIRMNESS_LIST);
		msg.setMemory(Test.GENERAL_SEATMEMORYACTION);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues() {
		// Test Values
		SupportedSeat id = msg.getId();
		Boolean heatingEnabled = msg.getHeatingEnabled();
		Boolean coolingEnabled = msg.getCoolingEnabled();
		Integer heatingLevel = msg.getHeatingLevel();
		Integer coolingLevel = msg.getCoolingLevel();
		Integer horizontalPosition = msg.getHorizontalPosition();
		Integer verticalPosition = msg.getVerticalPosition();
		Integer frontVerticalPosition = msg.getFrontVerticalPosition();
		Integer backVerticalPosition = msg.getBackVerticalPosition();
		Integer backTiltAngle = msg.getBackTiltAngle();
		Integer headSupportHorizontalPosition = msg.getHeadSupportHorizontalPosition();
		Integer headSupportVerticalPosition = msg.getHeadSupportVerticalPosition();
		Boolean massageEnabled = msg.getMassageEnabled();

		List<MassageModeData> massageMode = msg.getMassageMode();
		List<MassageCushionFirmness> massageCushionFirmness = msg.getMassageCushionFirmness();
		SeatMemoryAction memory = msg.getMemory();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_SUPPORTEDSEAT, id);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, heatingEnabled);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, coolingEnabled);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, heatingLevel);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, coolingLevel);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, horizontalPosition);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, verticalPosition);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, frontVerticalPosition);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, backVerticalPosition);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, backTiltAngle);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, headSupportHorizontalPosition);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, headSupportVerticalPosition);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, massageEnabled);

		assertTrue(Test.TRUE, Validator.validateMassageModeDataList(Test.GENERAL_MASSAGEMODEDATA_LIST, massageMode));
		assertTrue(Test.TRUE, Validator.validateMassageCushionFirmnessList(Test.GENERAL_MASSAGECUSHIONFIRMNESS_LIST, massageCushionFirmness));

		assertTrue(Test.TRUE, Validator.validateSeatMemoryAction(Test.GENERAL_SEATMEMORYACTION, memory));

		// Invalid/Null Tests
		SeatControlData msg = new SeatControlData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getId());
		assertNull(Test.NULL, msg.getHeatingEnabled());
		assertNull(Test.NULL, msg.getCoolingEnabled());
		assertNull(Test.NULL, msg.getHeatingLevel());
		assertNull(Test.NULL, msg.getCoolingLevel());
		assertNull(Test.NULL, msg.getHorizontalPosition());
		assertNull(Test.NULL, msg.getVerticalPosition());
		assertNull(Test.NULL, msg.getFrontVerticalPosition());
		assertNull(Test.NULL, msg.getBackVerticalPosition());
		assertNull(Test.NULL, msg.getBackTiltAngle());
		assertNull(Test.NULL, msg.getHeadSupportHorizontalPosition());
		assertNull(Test.NULL, msg.getHeadSupportVerticalPosition());
		assertNull(Test.NULL, msg.getMassageEnabled());
		assertNull(Test.NULL, msg.getMassageMode());
		assertNull(Test.NULL, msg.getMassageCushionFirmness());
		assertNull(Test.NULL, msg.getMemory());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SeatControlData.KEY_ID, Test.GENERAL_SUPPORTEDSEAT);
			reference.put(SeatControlData.KEY_HEATING_ENABLED, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlData.KEY_COOLING_ENABLED, Test.GENERAL_BOOLEAN);
			reference.put(SeatControlData.KEY_HEATING_LEVEL, Test.GENERAL_INT);
			reference.put(SeatControlData.KEY_COOLING_LEVEL, Test.GENERAL_INT);
			reference.put(SeatControlData.KEY_HORIZONTAL_POSITION, Test.GENERAL_INT);
			reference.put(SeatControlData.KEY_VERTICAL_POSITION, Test.GENERAL_INT);
			reference.put(SeatControlData.KEY_FRONT_VERTICAL_POSITION, Test.GENERAL_INT);
			reference.put(SeatControlData.KEY_BACK_VERTICAL_POSITION, Test.GENERAL_INT);
			reference.put(SeatControlData.KEY_BACK_TILT_ANGLE, Test.GENERAL_INT);
			reference.put(SeatControlData.KEY_HEAD_SUPPORT_HORIZONTAL_POSITION, Test.GENERAL_INT);
			reference.put(SeatControlData.KEY_HEAD_SUPPORT_VERTICAL_POSITION, Test.GENERAL_INT);
			reference.put(SeatControlData.KEY_MASSAGE_ENABLED, Test.GENERAL_BOOLEAN);

			reference.put(SeatControlData.KEY_MASSAGE_MODE, Test.GENERAL_MASSAGEMODEDATA_LIST);
			reference.put(SeatControlData.KEY_MASSAGE_CUSHION_FIRMNESS, Test.GENERAL_MASSAGECUSHIONFIRMNESS_LIST);

			reference.put(SeatControlData.KEY_MEMORY, Test.GENERAL_SEATMEMORYACTION);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(SeatControlData.KEY_MASSAGE_MODE)) {
					List<MassageModeData> mmdReference = (List<MassageModeData>) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONArray mmdArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					int i = 0;
					for (MassageModeData mmd : mmdReference) {
						assertTrue(Validator.validateMassageModeData(mmd, new MassageModeData(JsonRPCMarshaller.deserializeJSONObject(mmdArray.getJSONObject(i++)))));
					}
				} else if (key.equals(SeatControlData.KEY_MASSAGE_CUSHION_FIRMNESS)) {
					List<MassageCushionFirmness> mcfReference = (List<MassageCushionFirmness>) JsonUtils.readObjectFromJsonObject(reference, key);
					JSONArray mcfArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					int i = 0;
					for (MassageCushionFirmness mcf : mcfReference) {
						assertTrue(Validator.validateMassageCushionFirmness(mcf, new MassageCushionFirmness(JsonRPCMarshaller.deserializeJSONObject(mcfArray.getJSONObject(i++)))));
					}
				} else if (key.equals(SeatControlData.KEY_MEMORY)) {
					SeatMemoryAction mReference = (SeatMemoryAction) JsonUtils.readObjectFromJsonObject(reference, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(JsonUtils.readJsonObjectFromJsonObject(underTest, key));
					assertTrue(Validator.validateSeatMemoryAction(mReference, new SeatMemoryAction(hashTest)));
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}