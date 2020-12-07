package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.AirbagStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.AirbagStatus}
 */
public class AirbagStatusTests extends TestCase {

    private AirbagStatus msg;

    @Override
    public void setUp() {
        msg = new AirbagStatus();
        assertNotNull(TestValues.NOT_NULL, msg);

        msg.setDriverAirbagDeployed(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setDriverCurtainAirbagDeployed(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setDriverKneeAirbagDeployed(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setDriverSideAirbagDeployed(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerAirbagDeployed(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerCurtainAirbagDeployed(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerKneeAirbagDeployed(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerSideAirbagDeployed(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);

    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        VehicleDataEventStatus airbagStatus = msg.getDriverAirbagDeployed();
        VehicleDataEventStatus curtainStatus = msg.getDriverCurtainAirbagDeployed();
        VehicleDataEventStatus kneeStatus = msg.getDriverKneeAirbagDeployed();
        VehicleDataEventStatus sideStatus = msg.getDriverSideAirbagDeployed();
        VehicleDataEventStatus passengerStatus = msg.getPassengerAirbagDeployed();
        VehicleDataEventStatus passengerCurtainStatus = msg.getPassengerCurtainAirbagDeployed();
        VehicleDataEventStatus passengerKneeStatus = msg.getPassengerKneeAirbagDeployed();
        VehicleDataEventStatus passengerSideStatus = msg.getPassengerSideAirbagDeployed();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, airbagStatus);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, curtainStatus);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, kneeStatus);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, sideStatus);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, passengerStatus);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, passengerCurtainStatus);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, passengerKneeStatus);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, passengerSideStatus);

        // Invalid/Null Tests
        AirbagStatus msg = new AirbagStatus();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getDriverAirbagDeployed());
        assertNull(TestValues.NULL, msg.getDriverSideAirbagDeployed());
        assertNull(TestValues.NULL, msg.getDriverCurtainAirbagDeployed());
        assertNull(TestValues.NULL, msg.getPassengerAirbagDeployed());
        assertNull(TestValues.NULL, msg.getPassengerCurtainAirbagDeployed());
        assertNull(TestValues.NULL, msg.getDriverKneeAirbagDeployed());
        assertNull(TestValues.NULL, msg.getPassengerSideAirbagDeployed());
        assertNull(TestValues.NULL, msg.getPassengerKneeAirbagDeployed());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(AirbagStatus.KEY_DRIVER_AIRBAG_DEPLOYED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_DRIVER_KNEE_AIRBAG_DEPLOYED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_DRIVER_SIDE_AIRBAG_DEPLOYED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_AIRBAG_DEPLOYED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);

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