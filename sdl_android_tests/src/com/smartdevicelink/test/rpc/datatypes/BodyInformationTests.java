package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.BodyInformation;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;
import com.smartdevicelink.test.utils.JsonUtils;

public class BodyInformationTests extends TestCase{

    private static final boolean              PARK_BRAKE_ACTIVE       = true;
    private static final IgnitionStatus       IGNITION_STATUS         = IgnitionStatus.RUN;
    private static final IgnitionStableStatus IGNITION_STABLE_STATUS  = IgnitionStableStatus.IGNITION_SWITCH_STABLE;
    private static final boolean              DRIVER_DOOR_AJAR_STATUS = true;
    private static final boolean              OTHER_DOOR_AJAR_STATUS  = false;
    private static final String               STATUS_ASSERT_FAILED_MSG     = "Input status did not match expected status.";
    
    private BodyInformation                   msg;

    @Override
    public void setUp(){
        msg = new BodyInformation();
        msg.setParkBrakeActive(PARK_BRAKE_ACTIVE);
        msg.setIgnitionStatus(IGNITION_STATUS);
        msg.setIgnitionStableStatus(IGNITION_STABLE_STATUS);

        msg.setDriverDoorAjar(DRIVER_DOOR_AJAR_STATUS);
        msg.setPassengerDoorAjar(OTHER_DOOR_AJAR_STATUS);
        msg.setRearLeftDoorAjar(OTHER_DOOR_AJAR_STATUS);
        msg.setRearRightDoorAjar(OTHER_DOOR_AJAR_STATUS);
    }

    public void testParkBrakeActive(){
        boolean copy = msg.getParkBrakeActive();
        assertEquals("Input value didn't match expected value.", PARK_BRAKE_ACTIVE, copy);
    }

    public void testIgnitionStatus(){
        IgnitionStatus copy = msg.getIgnitionStatus();
        assertEquals("Input status didn't match expected status.", IGNITION_STATUS, copy);
    }

    public void testIgnitionStableStatus(){
        IgnitionStableStatus copy = msg.getIgnitionStableStatus();
        assertEquals("Input status didn't match expected status.", IGNITION_STABLE_STATUS, copy);
    }

    public void testDoorAjar(){
    	assertEquals(STATUS_ASSERT_FAILED_MSG, DRIVER_DOOR_AJAR_STATUS, (boolean) msg.getDriverDoorAjar());
    	assertEquals(STATUS_ASSERT_FAILED_MSG, OTHER_DOOR_AJAR_STATUS, (boolean) msg.getPassengerDoorAjar());
    	assertEquals(STATUS_ASSERT_FAILED_MSG, OTHER_DOOR_AJAR_STATUS, (boolean) msg.getRearLeftDoorAjar());
    	assertEquals(STATUS_ASSERT_FAILED_MSG, OTHER_DOOR_AJAR_STATUS, (boolean) msg.getRearRightDoorAjar());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(BodyInformation.KEY_PARK_BRAKE_ACTIVE, PARK_BRAKE_ACTIVE);
            reference.put(BodyInformation.KEY_IGNITION_STATUS, IGNITION_STATUS);
            reference.put(BodyInformation.KEY_IGNITION_STABLE_STATUS, IGNITION_STABLE_STATUS);
            reference.put(BodyInformation.KEY_DRIVER_DOOR_AJAR, DRIVER_DOOR_AJAR_STATUS);
            reference.put(BodyInformation.KEY_PASSENGER_DOOR_AJAR, OTHER_DOOR_AJAR_STATUS);
            reference.put(BodyInformation.KEY_REAR_LEFT_DOOR_AJAR, OTHER_DOOR_AJAR_STATUS);
            reference.put(BodyInformation.KEY_REAR_RIGHT_DOOR_AJAR, OTHER_DOOR_AJAR_STATUS);

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
        BodyInformation msg = new BodyInformation();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Park brake active wasn't set, but getter method returned an object.", msg.getParkBrakeActive());
        assertNull("Ignition status wasn't set, but getter method returned an object.", msg.getIgnitionStatus());
        assertNull("Ignition stable status wasn't set, but getter method returned an object.", msg.getIgnitionStatus());

        assertNull("Driver door ajar wasn't set, but getter method returned an object.", msg.getDriverDoorAjar());
        assertNull("Passenger door ajar wasn't set, but getter method returned an object.", msg.getPassengerDoorAjar());
        assertNull("Rear left door ajar wasn't set, but getter method returned an object.", msg.getRearLeftDoorAjar());
        assertNull("Rear right door ajar wasn't set, but getter method returned an object.", msg.getRearRightDoorAjar());

    }
    //TODO: remove this method?
    /*
    public void testCopy(){
        BodyInformation copy = new BodyInformation(msg);
        
        assertNotSame("Object was not copied.", copy, msg);
        
        String error = "Object data was not copied correctly.";
        for(DoorZone zone : EnumSet.allOf(DoorZone.class)){
            assertEquals(error, copy.getDoorAjar(zone), msg.getDoorAjar(zone));
        }
        
        assertEquals(error, copy.getParkBrakeActive(), msg.getParkBrakeActive());
        assertEquals(error, copy.getIgnitionStatus(), msg.getIgnitionStatus());
        assertEquals(error, copy.getIgnitionStableStatus(), msg.getIgnitionStableStatus());
    }
    */
}
