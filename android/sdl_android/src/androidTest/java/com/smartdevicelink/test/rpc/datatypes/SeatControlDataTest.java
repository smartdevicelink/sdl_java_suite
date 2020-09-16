package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.MassageCushionFirmness;
import com.smartdevicelink.proxy.rpc.MassageModeData;
import com.smartdevicelink.proxy.rpc.SeatControlData;
import com.smartdevicelink.proxy.rpc.SeatMemoryAction;
import com.smartdevicelink.proxy.rpc.enums.SupportedSeat;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
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
 * {@link com.smartdevicelink.proxy.rpc.SeatControlData}
 */
public class SeatControlDataTest extends TestCase {

	private SeatControlData msg;

	@Override
	public void setUp() {
		msg = new SeatControlData();
		msg.setId(TestValues.GENERAL_SUPPORTEDSEAT);
		msg.setHeatingEnabled(TestValues.GENERAL_BOOLEAN);
		msg.setCoolingEnabled(TestValues.GENERAL_BOOLEAN);
		msg.setHeatingLevel(TestValues.GENERAL_INT);
		msg.setCoolingLevel(TestValues.GENERAL_INT);
		msg.setHorizontalPosition(TestValues.GENERAL_INT);
		msg.setVerticalPosition(TestValues.GENERAL_INT);
		msg.setFrontVerticalPosition(TestValues.GENERAL_INT);
		msg.setBackVerticalPosition(TestValues.GENERAL_INT);
		msg.setBackTiltAngle(TestValues.GENERAL_INT);
		msg.setHeadSupportVerticalPosition(TestValues.GENERAL_INT);
		msg.setHeadSupportHorizontalPosition(TestValues.GENERAL_INT);
		msg.setMassageEnabled(TestValues.GENERAL_BOOLEAN);
		msg.setMassageMode(TestValues.GENERAL_MASSAGEMODEDATA_LIST);
		msg.setMassageCushionFirmness(TestValues.GENERAL_MASSAGECUSHIONFIRMNESS_LIST);
		msg.setMemory(TestValues.GENERAL_SEATMEMORYACTION);
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
		assertEquals(TestValues.MATCH, TestValues.GENERAL_SUPPORTEDSEAT, id);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, heatingEnabled);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, coolingEnabled);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, heatingLevel);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, coolingLevel);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, horizontalPosition);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, verticalPosition);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, frontVerticalPosition);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, backVerticalPosition);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, backTiltAngle);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, headSupportHorizontalPosition);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, headSupportVerticalPosition);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, massageEnabled);

		assertTrue(TestValues.TRUE, Validator.validateMassageModeDataList(TestValues.GENERAL_MASSAGEMODEDATA_LIST, massageMode));
		assertTrue(TestValues.TRUE, Validator.validateMassageCushionFirmnessList(TestValues.GENERAL_MASSAGECUSHIONFIRMNESS_LIST, massageCushionFirmness));

		assertTrue(TestValues.TRUE, Validator.validateSeatMemoryAction(TestValues.GENERAL_SEATMEMORYACTION, memory));

		// Invalid/Null Tests
		SeatControlData msg = new SeatControlData();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getId());
		assertNull(TestValues.NULL, msg.getHeatingEnabled());
		assertNull(TestValues.NULL, msg.getCoolingEnabled());
		assertNull(TestValues.NULL, msg.getHeatingLevel());
		assertNull(TestValues.NULL, msg.getCoolingLevel());
		assertNull(TestValues.NULL, msg.getHorizontalPosition());
		assertNull(TestValues.NULL, msg.getVerticalPosition());
		assertNull(TestValues.NULL, msg.getFrontVerticalPosition());
		assertNull(TestValues.NULL, msg.getBackVerticalPosition());
		assertNull(TestValues.NULL, msg.getBackTiltAngle());
		assertNull(TestValues.NULL, msg.getHeadSupportHorizontalPosition());
		assertNull(TestValues.NULL, msg.getHeadSupportVerticalPosition());
		assertNull(TestValues.NULL, msg.getMassageEnabled());
		assertNull(TestValues.NULL, msg.getMassageMode());
		assertNull(TestValues.NULL, msg.getMassageCushionFirmness());
		assertNull(TestValues.NULL, msg.getMemory());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SeatControlData.KEY_ID, TestValues.GENERAL_SUPPORTEDSEAT);
			reference.put(SeatControlData.KEY_HEATING_ENABLED, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlData.KEY_COOLING_ENABLED, TestValues.GENERAL_BOOLEAN);
			reference.put(SeatControlData.KEY_HEATING_LEVEL, TestValues.GENERAL_INT);
			reference.put(SeatControlData.KEY_COOLING_LEVEL, TestValues.GENERAL_INT);
			reference.put(SeatControlData.KEY_HORIZONTAL_POSITION, TestValues.GENERAL_INT);
			reference.put(SeatControlData.KEY_VERTICAL_POSITION, TestValues.GENERAL_INT);
			reference.put(SeatControlData.KEY_FRONT_VERTICAL_POSITION, TestValues.GENERAL_INT);
			reference.put(SeatControlData.KEY_BACK_VERTICAL_POSITION, TestValues.GENERAL_INT);
			reference.put(SeatControlData.KEY_BACK_TILT_ANGLE, TestValues.GENERAL_INT);
			reference.put(SeatControlData.KEY_HEAD_SUPPORT_HORIZONTAL_POSITION, TestValues.GENERAL_INT);
			reference.put(SeatControlData.KEY_HEAD_SUPPORT_VERTICAL_POSITION, TestValues.GENERAL_INT);
			reference.put(SeatControlData.KEY_MASSAGE_ENABLED, TestValues.GENERAL_BOOLEAN);

			reference.put(SeatControlData.KEY_MASSAGE_MODE, TestValues.GENERAL_MASSAGEMODEDATA_LIST);
			reference.put(SeatControlData.KEY_MASSAGE_CUSHION_FIRMNESS, TestValues.GENERAL_MASSAGECUSHIONFIRMNESS_LIST);

			reference.put(SeatControlData.KEY_MEMORY, TestValues.GENERAL_SEATMEMORYACTION);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

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
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}