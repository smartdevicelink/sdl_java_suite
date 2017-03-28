package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.AirbagStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.AirbagStatus}
 */
public class AirbagStatusTests extends TestCase{

    private AirbagStatus msg;

    @Override
    public void setUp(){
        msg = new AirbagStatus();
        assertNotNull(Test.NOT_NULL, msg);
        
        msg.setDriverAirbagDeployed(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setDriverCurtainAirbagDeployed(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setDriverKneeAirbagDeployed(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setDriverSideAirbagDeployed(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerAirbagDeployed(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerCurtainAirbagDeployed(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerKneeAirbagDeployed(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerSideAirbagDeployed(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        
    }
    
    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {       	
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
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, airbagStatus);
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, curtainStatus);
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, kneeStatus);
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, sideStatus);
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, passengerStatus);
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, passengerCurtainStatus);
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, passengerKneeStatus);
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, passengerSideStatus);
    
        // Invalid/Null Tests
        AirbagStatus msg = new AirbagStatus();
        assertNotNull(Test.NOT_NULL, msg);
        
        assertNull(Test.NULL, msg.getDriverAirbagDeployed());
        assertNull(Test.NULL, msg.getDriverSideAirbagDeployed());
        assertNull(Test.NULL, msg.getDriverCurtainAirbagDeployed());
        assertNull(Test.NULL, msg.getPassengerAirbagDeployed());
        assertNull(Test.NULL, msg.getPassengerCurtainAirbagDeployed());
        assertNull(Test.NULL, msg.getDriverKneeAirbagDeployed());
        assertNull(Test.NULL, msg.getPassengerSideAirbagDeployed());
        assertNull(Test.NULL, msg.getPassengerKneeAirbagDeployed());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(AirbagStatus.KEY_DRIVER_AIRBAG_DEPLOYED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_DRIVER_KNEE_AIRBAG_DEPLOYED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_DRIVER_SIDE_AIRBAG_DEPLOYED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_AIRBAG_DEPLOYED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}