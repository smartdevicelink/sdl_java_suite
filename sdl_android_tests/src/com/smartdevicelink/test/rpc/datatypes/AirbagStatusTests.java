package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.AirbagStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.test.JsonUtils;

public class AirbagStatusTests extends TestCase{

    private static final VehicleDataEventStatus DRIVER_STATUS            = VehicleDataEventStatus.YES;
    private static final VehicleDataEventStatus DRIVER_CURTAIN_STATUS    = VehicleDataEventStatus.NO;
    private static final VehicleDataEventStatus DRIVER_KNEE_STATUS       = VehicleDataEventStatus.YES;
    private static final VehicleDataEventStatus DRIVER_SIDE_STATUS       = VehicleDataEventStatus.NO;
    private static final VehicleDataEventStatus PASSENGER_STATUS         = VehicleDataEventStatus.YES;
    private static final VehicleDataEventStatus PASSENGER_CURTAIN_STATUS = VehicleDataEventStatus.YES;
    private static final VehicleDataEventStatus PASSENGER_KNEE_STATUS    = VehicleDataEventStatus.NO;
    private static final VehicleDataEventStatus PASSENGER_SIDE_STATUS    = VehicleDataEventStatus.YES;

    private AirbagStatus                        msg;

    @Override
    public void setUp(){
        msg = new AirbagStatus();
        
        msg.setDriverAirbagDeployed(DRIVER_STATUS);
        msg.setDriverCurtainAirbagDeployed(DRIVER_CURTAIN_STATUS);
        msg.setDriverKneeAirbagDeployed(DRIVER_KNEE_STATUS);
        msg.setDriverSideAirbagDeployed(DRIVER_SIDE_STATUS);
        msg.setPassengerAirbagDeployed(PASSENGER_STATUS);
        msg.setPassengerCurtainAirbagDeployed(PASSENGER_CURTAIN_STATUS);
        msg.setPassengerKneeAirbagDeployed(PASSENGER_KNEE_STATUS);
        msg.setPassengerSideAirbagDeployed(PASSENGER_SIDE_STATUS);
        
    }

    public void testCreation(){
        assertNotNull("Object creation failed.", msg);
    }

    public void testDriverAirbag(){
        VehicleDataEventStatus status = msg.getDriverAirbagDeployed();
        assertEquals("Read status didn't match expected status.", DRIVER_STATUS, status);
    }

    public void testDriverCurtainAirbag(){
        VehicleDataEventStatus status = msg.getDriverCurtainAirbagDeployed();
        assertEquals("Read status didn't match expected status.", DRIVER_CURTAIN_STATUS, status);
    }

    public void testDriverKneeAirbag(){
        VehicleDataEventStatus status = msg.getDriverKneeAirbagDeployed();
        assertEquals("Read status didn't match expected status.", DRIVER_KNEE_STATUS, status);
    }

    public void testDriverSideAirbag(){
        VehicleDataEventStatus status = msg.getDriverSideAirbagDeployed();
        assertEquals("Read status didn't match expected status.", DRIVER_SIDE_STATUS, status);
    }

    public void testPassengerAirbag(){
        VehicleDataEventStatus status = msg.getPassengerAirbagDeployed();
        assertEquals("Read status didn't match expected status.", PASSENGER_STATUS, status);
    }

    public void testPassengerCurtainAirbag(){
        VehicleDataEventStatus status = msg.getPassengerCurtainAirbagDeployed();
        assertEquals("Read status didn't match expected status.", PASSENGER_CURTAIN_STATUS, status);
    }

    public void testPassengerKneeAirbag(){
        VehicleDataEventStatus status = msg.getPassengerKneeAirbagDeployed();
        assertEquals("Read status didn't match expected status.", PASSENGER_KNEE_STATUS, status);
    }

    public void testPassengerSideAirbag(){
        VehicleDataEventStatus status = msg.getPassengerSideAirbagDeployed();
        assertEquals("Read status didn't match expected status.", PASSENGER_SIDE_STATUS, status);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(AirbagStatus.KEY_DRIVER_AIRBAG_DEPLOYED, DRIVER_STATUS);
            reference.put(AirbagStatus.KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED, DRIVER_CURTAIN_STATUS);
            reference.put(AirbagStatus.KEY_DRIVER_KNEE_AIRBAG_DEPLOYED, DRIVER_KNEE_STATUS);
            reference.put(AirbagStatus.KEY_DRIVER_SIDE_AIRBAG_DEPLOYED, DRIVER_SIDE_STATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_AIRBAG_DEPLOYED, PASSENGER_STATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED, PASSENGER_CURTAIN_STATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED, PASSENGER_KNEE_STATUS);
            reference.put(AirbagStatus.KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED, PASSENGER_SIDE_STATUS);

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals("JSON object didn't match reference for key \"" + key + "\".",
                        JsonUtils.readObjectFromJsonObject(reference, key),
                        JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        AirbagStatus msg = new AirbagStatus();
        assertNotNull("Null object creation failed.", msg);
        
        assertNull("Zone wasn't set for driver airbag but getter method returned an object.", msg.getDriverAirbagDeployed());
        assertNull("Zone wasn't set for driver side airbag but getter method returned an object.", msg.getDriverSideAirbagDeployed());
        assertNull("Zone wasn't set for driver curtain airbag but getter method returned an object.", msg.getDriverCurtainAirbagDeployed());
        assertNull("Zone wasn't set for passenger airbag but getter method returned an object.", msg.getPassengerAirbagDeployed());
        assertNull("Zone wasn't set for passenger curtain airbag but getter method returned an object.", msg.getPassengerCurtainAirbagDeployed());
        assertNull("Zone wasn't set for driver knee airbag but getter method returned an object.", msg.getDriverKneeAirbagDeployed());
        assertNull("Zone wasn't set for passenger side airbag but getter method returned an object.", msg.getPassengerSideAirbagDeployed());
        assertNull("Zone wasn't set for passenger knee airbag but getter method returned an object.", msg.getPassengerKneeAirbagDeployed());
    }
    //TODO: remove this method?
    /*
    public void testCopy(){
        AirbagStatus copy = new AirbagStatus(msg);

        assertNotSame("Object was not copied.", copy, msg);
        for(AirbagZone zone : EnumSet.allOf(AirbagZone.class)){
            assertEquals("Object data was not copied correctly.", copy.getAirbagStatus(zone), msg.getAirbagStatus(zone));
        }
    }
    */
}
