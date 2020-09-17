package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.EmergencyEvent;
import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.EmergencyEvent}
 */
public class EmergencyEventTests extends TestCase {

    private EmergencyEvent msg;

    @Override
    public void setUp() {
        msg = new EmergencyEvent();

        msg.setEmergencyEventType(TestValues.GENERAL_EMERGENCYEVENTTYPE);
        msg.setFuelCutoffStatus(TestValues.GENERAL_FUELCUTOFFSTATUS);
        msg.setMaximumChangeVelocity(TestValues.GENERAL_INT);
        msg.setMultipleEvents(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setRolloverEvent(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        EmergencyEventType type = msg.getEmergencyEventType();
        FuelCutoffStatus cutoffStatus = msg.getFuelCutoffStatus();
        VehicleDataEventStatus multipleEvents = msg.getMultipleEvents();
        int changeVelocity = msg.getMaximumChangeVelocity();
        VehicleDataEventStatus rollover = msg.getRolloverEvent();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_EMERGENCYEVENTTYPE, type);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_FUELCUTOFFSTATUS, cutoffStatus);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, changeVelocity);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, multipleEvents);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, rollover);

        // Invalid/Null Tests
        EmergencyEvent msg = new EmergencyEvent();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getEmergencyEventType());
        assertNull(TestValues.NULL, msg.getFuelCutoffStatus());
        assertNull(TestValues.NULL, msg.getMaximumChangeVelocity());
        assertNull(TestValues.NULL, msg.getMultipleEvents());
        assertNull(TestValues.NULL, msg.getRolloverEvent());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(EmergencyEvent.KEY_FUEL_CUTOFF_STATUS, TestValues.GENERAL_FUELCUTOFFSTATUS);
            reference.put(EmergencyEvent.KEY_MULTIPLE_EVENTS, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(EmergencyEvent.KEY_ROLLOVER_EVENT, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(EmergencyEvent.KEY_EMERGENCY_EVENT_TYPE, TestValues.GENERAL_EMERGENCYEVENTTYPE);
            reference.put(EmergencyEvent.KEY_MAXIMUM_CHANGE_VELOCITY, TestValues.GENERAL_INT);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}