package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.EmergencyEvent;
import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.test.JsonUtils;

public class EmergencyEventTests extends TestCase{

    // public EmergencyEvent(EmergencyEventType type, FuelCutoffStatus fuelCutoffStatus,
    // VehicleDataEventStatus rolloverEvent, VehicleDataEventStatus multipleEvents, Integer maxChangeVelocity){

    private static final EmergencyEventType     TYPE                = EmergencyEventType.NO_EVENT;
    private static final FuelCutoffStatus       FUEL_CUTOFF_STATUS  = FuelCutoffStatus.NORMAL_OPERATION;
    private static final VehicleDataEventStatus ROLLOVER_EVENT      = VehicleDataEventStatus.NO_EVENT;
    private static final VehicleDataEventStatus MULTIPLE_EVENTS     = VehicleDataEventStatus.NO;
    private static final int                    MAX_CHANGE_VELOCITY = 50;

    private EmergencyEvent                      msg;

    @Override
    public void setUp(){
        msg = new EmergencyEvent();

        msg.setEmergencyEventType(TYPE);
        msg.setFuelCutoffStatus(FUEL_CUTOFF_STATUS);
        msg.setMaximumChangeVelocity(MAX_CHANGE_VELOCITY);
        msg.setMultipleEvents(MULTIPLE_EVENTS);
        msg.setRolloverEvent(ROLLOVER_EVENT);
    }

    public void testEmergencyEventType(){
        EmergencyEventType copy = msg.getEmergencyEventType();
        assertEquals("Input value didn't match expected value.", TYPE, copy);
    }

    public void testFuelCutoffStatus(){
        FuelCutoffStatus copy = msg.getFuelCutoffStatus();
        assertEquals("Input value didn't match expected value.", FUEL_CUTOFF_STATUS, copy);
    }

    public void testMaximumChangeVelocity(){
        int copy = msg.getMaximumChangeVelocity();
        assertEquals("Input value didn't match expected value.", MAX_CHANGE_VELOCITY, copy);
    }

    public void testMultipleEvents(){
        VehicleDataEventStatus copy = msg.getMultipleEvents();
        assertEquals("Input value didn't match expected value.", MULTIPLE_EVENTS, copy);
    }

    public void testRolloverEvent(){
        VehicleDataEventStatus copy = msg.getRolloverEvent();
        assertEquals("Input value didn't match expected value.", ROLLOVER_EVENT, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(EmergencyEvent.KEY_FUEL_CUTOFF_STATUS, FUEL_CUTOFF_STATUS);
            reference.put(EmergencyEvent.KEY_MULTIPLE_EVENTS, MULTIPLE_EVENTS);
            reference.put(EmergencyEvent.KEY_ROLLOVER_EVENT, ROLLOVER_EVENT);
            reference.put(EmergencyEvent.KEY_EMERGENCY_EVENT_TYPE, TYPE);
            reference.put(EmergencyEvent.KEY_MAXIMUM_CHANGE_VELOCITY, MAX_CHANGE_VELOCITY);

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
                        JsonUtils.readObjectFromJsonObject(reference, key),
                        JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        EmergencyEvent msg = new EmergencyEvent();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Emergency event type wasn't set, but getter method returned an object.",
                msg.getEmergencyEventType());
        assertNull("Fuel cutoff status wasn't set, but getter method returned an object.", msg.getFuelCutoffStatus());
        assertNull("Maximum change velocity wasn't set, but getter method returned an object.",
                msg.getMaximumChangeVelocity());
        assertNull("Multiple events wasn't set, but getter method returned an object.", msg.getMultipleEvents());
        assertNull("Rollover event wasn't set, but getter method returned an object.", msg.getRolloverEvent());
    }
}
