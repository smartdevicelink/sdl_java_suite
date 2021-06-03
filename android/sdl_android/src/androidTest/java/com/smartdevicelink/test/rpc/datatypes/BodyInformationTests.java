package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.BodyInformation;
import com.smartdevicelink.proxy.rpc.DoorStatus;
import com.smartdevicelink.proxy.rpc.GateStatus;
import com.smartdevicelink.proxy.rpc.RoofStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

import static com.smartdevicelink.proxy.rpc.BodyInformation.KEY_DOOR_STATUSES;
import static com.smartdevicelink.proxy.rpc.BodyInformation.KEY_GATE_STATUSES;
import static com.smartdevicelink.proxy.rpc.BodyInformation.KEY_ROOF_STATUSES;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.BodyInformation}
 */
public class BodyInformationTests extends TestCase {

    private BodyInformation msg;

    @Override
    public void setUp() {
        msg = new BodyInformation();
        msg.setParkBrakeActive(TestValues.GENERAL_BOOLEAN);
        msg.setIgnitionStatus(TestValues.GENERAL_IGNITIONSTATUS);
        msg.setIgnitionStableStatus(TestValues.GENERAL_IGNITIONSTABLESTATUS);

        msg.setDriverDoorAjar(TestValues.GENERAL_BOOLEAN);
        msg.setPassengerDoorAjar(TestValues.GENERAL_BOOLEAN);
        msg.setRearLeftDoorAjar(TestValues.GENERAL_BOOLEAN);
        msg.setRearRightDoorAjar(TestValues.GENERAL_BOOLEAN);

        msg.setDoorStatuses(TestValues.GENERAL_DOOR_STATUS_LIST);
        msg.setGateStatuses(TestValues.GENERAL_GATE_STATUS_LIST);
        msg.setRoofStatuses(TestValues.GENERAL_ROOF_STATUS_LIST);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        boolean parkBrake = msg.getParkBrakeActive();
        IgnitionStatus ignitionStatus = msg.getIgnitionStatus();
        IgnitionStableStatus ignitionStable = msg.getIgnitionStableStatus();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, parkBrake);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_IGNITIONSTATUS, ignitionStatus);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_IGNITIONSTABLESTATUS, ignitionStable);

        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) msg.getDriverDoorAjar());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) msg.getPassengerDoorAjar());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) msg.getRearLeftDoorAjar());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) msg.getRearRightDoorAjar());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_GATE_STATUS_LIST, msg.getGateStatuses());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_ROOF_STATUS_LIST, msg.getRoofStatuses());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOOR_STATUS_LIST, msg.getDoorStatuses());

        // Invalid/Null Tests
        BodyInformation msg = new BodyInformation();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getParkBrakeActive());
        assertNull(TestValues.NULL, msg.getIgnitionStatus());
        assertNull(TestValues.NULL, msg.getIgnitionStatus());
        assertNull(TestValues.NULL, msg.getDriverDoorAjar());
        assertNull(TestValues.NULL, msg.getPassengerDoorAjar());
        assertNull(TestValues.NULL, msg.getRearLeftDoorAjar());
        assertNull(TestValues.NULL, msg.getRearRightDoorAjar());
        assertNull(TestValues.NULL, msg.getGateStatuses());
        assertNull(TestValues.NULL, msg.getRoofStatuses());
        assertNull(TestValues.NULL, msg.getDoorStatuses());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(BodyInformation.KEY_PARK_BRAKE_ACTIVE, TestValues.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_IGNITION_STATUS, TestValues.GENERAL_IGNITIONSTATUS);
            reference.put(BodyInformation.KEY_IGNITION_STABLE_STATUS, TestValues.GENERAL_IGNITIONSTABLESTATUS);
            reference.put(BodyInformation.KEY_DRIVER_DOOR_AJAR, TestValues.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_PASSENGER_DOOR_AJAR, TestValues.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_REAR_LEFT_DOOR_AJAR, TestValues.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_REAR_RIGHT_DOOR_AJAR, TestValues.GENERAL_BOOLEAN);
            reference.put(KEY_DOOR_STATUSES, TestValues.JSON_DOOR_STATUSES);
            reference.put(KEY_GATE_STATUSES, TestValues.JSON_GATE_STATUSES);
            reference.put(KEY_ROOF_STATUSES, TestValues.JSON_ROOF_STATUSES);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(KEY_DOOR_STATUSES)) {

                    JSONArray array1 = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray array2 = JsonUtils.readJsonArrayFromJsonObject(underTest, key);

                    for (int i = 0; i < array1.length(); i++) {
                        Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(array1.getJSONObject(i));
                        Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(array2.getJSONObject(i));
                        assertTrue(Validator.validateDoorStatus(new DoorStatus(h1), new DoorStatus(h2)));

                    }
                } else if (key.equals(KEY_GATE_STATUSES)) {

                    JSONArray array1 = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray array2 = JsonUtils.readJsonArrayFromJsonObject(underTest, key);

                    for (int i = 0; i < array1.length(); i++) {
                        Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(array1.getJSONObject(i));
                        Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(array2.getJSONObject(i));
                        assertTrue(Validator.validateGateStatus(new GateStatus(h1), new GateStatus(h2)));
                    }
                } else if (key.equals(KEY_ROOF_STATUSES)) {
                    JSONArray array1 = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray array2 = JsonUtils.readJsonArrayFromJsonObject(underTest, key);

                    for (int i = 0; i < array1.length(); i++) {
                        Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(array1.getJSONObject(i));
                        Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(array2.getJSONObject(i));
                        assertTrue(Validator.validateRoofStatus(new RoofStatus(h1), new RoofStatus(h2)));
                    }
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}