package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.BeltStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.test.utils.JsonUtils;

public class BeltStatusTests extends TestCase{

    private static final VehicleDataEventStatus LEFT_REAR_INFLATABLE_BELTED  = VehicleDataEventStatus.YES;
    private static final VehicleDataEventStatus RIGHT_REAR_INFLATABLE_BELTED = VehicleDataEventStatus.YES;
    private static final VehicleDataEventStatus PASSENGER_CHILD_DETECTED     = VehicleDataEventStatus.NO;
    private static final VehicleDataEventStatus DRIVER_STATUS                = VehicleDataEventStatus.YES;
    private static final VehicleDataEventStatus PASSENGER_STATUS             = VehicleDataEventStatus.YES;
    private static final VehicleDataEventStatus ROW2_RIGHT_STATUS            = VehicleDataEventStatus.FAULT;
    private static final VehicleDataEventStatus ROW1_MIDDLE_STATUS           = VehicleDataEventStatus.FAULT;
    private static final VehicleDataEventStatus OTHER_STATUS                 = VehicleDataEventStatus.NO;

    private static final String                 STATUS_ASSERT_FAILED_MSG     = "Input status did not match expected status.";

    private BeltStatus                          msg;

    @Override
    protected void setUp() throws Exception{
        msg = new BeltStatus();
        msg.setLeftRearInflatableBelted(LEFT_REAR_INFLATABLE_BELTED);
        msg.setPassengerChildDetected(PASSENGER_CHILD_DETECTED);
        msg.setRightRearInflatableBelted(RIGHT_REAR_INFLATABLE_BELTED);

        msg.setDriverBuckleBelted(DRIVER_STATUS);
        msg.setPassengerBuckleBelted(PASSENGER_STATUS);
        msg.setRightRow2BuckleBelted(ROW2_RIGHT_STATUS);
        msg.setDriverBeltDeployed(DRIVER_STATUS);
        msg.setPassengerBeltDeployed(PASSENGER_STATUS);
        
        //TODO: closest set method found for row 2 belt deployed
        msg.setMiddleRow1BuckleBelted(ROW1_MIDDLE_STATUS);
        msg.setMiddleRow1BeltDeployed(ROW1_MIDDLE_STATUS);

        msg.setLeftRow2BuckleBelted(OTHER_STATUS);
        msg.setMiddleRow2BuckleBelted(OTHER_STATUS);
        msg.setMiddleRow3BuckleBelted(OTHER_STATUS);
        msg.setLeftRow3BuckleBelted(OTHER_STATUS);
        msg.setRightRow3BuckleBelted(OTHER_STATUS);
    }

    public void testCreation(){
        assertNotNull("Object creation failed.", msg);
    }

    public void testLeftRearInflatableBelted(){
        VehicleDataEventStatus copy = msg.getLeftRearInflatableBelted();
        assertEquals(STATUS_ASSERT_FAILED_MSG, LEFT_REAR_INFLATABLE_BELTED, copy);
    }

    public void testRightRearInflatableBelted(){
        VehicleDataEventStatus copy = msg.getRightRearInflatableBelted();
        assertEquals(STATUS_ASSERT_FAILED_MSG, RIGHT_REAR_INFLATABLE_BELTED, copy);
    }

    public void testPassengerChildDetected(){
        VehicleDataEventStatus copy = msg.getPassengerChildDetected();
        assertEquals(STATUS_ASSERT_FAILED_MSG, PASSENGER_CHILD_DETECTED, copy);
    }

    public void testZoneBeltBuckleStatus(){
    	VehicleDataEventStatus copy = msg.getDriverBuckleBelted();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, DRIVER_STATUS, copy);
    	copy = msg.getPassengerBuckleBelted();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, PASSENGER_STATUS, copy);
    	copy = msg.getRightRow2BuckleBelted();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, ROW2_RIGHT_STATUS, copy);
    	copy = msg.getMiddleRow1BuckleBelted();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, ROW1_MIDDLE_STATUS, copy);
    	copy = msg.getLeftRow2BuckleBelted();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, OTHER_STATUS, copy);
    	copy = msg.getMiddleRow2BuckleBelted();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, OTHER_STATUS, copy);
    	copy = msg.getMiddleRow3BuckleBelted();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, OTHER_STATUS, copy);
    	copy = msg.getLeftRow3BuckleBelted();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, OTHER_STATUS, copy);
    	copy = msg.getRightRow3BuckleBelted();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, OTHER_STATUS, copy);
    }

    public void testZoneBeltDeployStatus(){
    	VehicleDataEventStatus copy = msg.getDriverBeltDeployed();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, DRIVER_STATUS, copy);
    	copy = msg.getPassengerBeltDeployed();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, PASSENGER_STATUS, copy);
    	copy = msg.getMiddleRow1BeltDeployed();
    	assertEquals(STATUS_ASSERT_FAILED_MSG, ROW1_MIDDLE_STATUS, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(BeltStatus.KEY_PASSENGER_CHILD_DETECTED, PASSENGER_CHILD_DETECTED);
            reference.put(BeltStatus.KEY_REAR_INFLATABLE_BELTED, LEFT_REAR_INFLATABLE_BELTED);
            reference.put(BeltStatus.KEY_RIGHT_REAR_INFLATABLE_BELTED, RIGHT_REAR_INFLATABLE_BELTED);
            
            reference.put(BeltStatus.KEY_DRIVER_BELT_DEPLOYED, DRIVER_STATUS);
            reference.put(BeltStatus.KEY_DRIVER_BUCKLE_BELTED, DRIVER_STATUS);
            reference.put(BeltStatus.KEY_PASSENGER_BELT_DEPLOYED, PASSENGER_STATUS);
            reference.put(BeltStatus.KEY_PASSENGER_BUCKLE_BELTED, PASSENGER_STATUS);
            reference.put(BeltStatus.KEY_LEFT_ROW_2_BUCKLE_BELTED, OTHER_STATUS);
            reference.put(BeltStatus.KEY_RIGHT_ROW_2_BUCKLE_BELTED, ROW2_RIGHT_STATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_2_BUCKLE_BELTED, OTHER_STATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_3_BUCKLE_BELTED, OTHER_STATUS);
            reference.put(BeltStatus.KEY_LEFT_ROW_3_BUCKLE_BELTED, OTHER_STATUS);
            reference.put(BeltStatus.KEY_RIGHT_ROW_3_BUCKLE_BELTED, OTHER_STATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_1_BELT_DEPLOYED, ROW1_MIDDLE_STATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_1_BUCKLE_BELTED, ROW1_MIDDLE_STATUS);

            JSONObject underTest = msg.serializeJSON();
            
            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals("JSON value didn't match reference for key \"" + key + "\".",
                        JsonUtils.readObjectFromJsonObject(reference, key),
                        JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        BeltStatus msg = new BeltStatus();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Left-rear inflatable belted wasn't set, but getter method returned an object.",
                msg.getLeftRearInflatableBelted());
        assertNull("Right-rear inflatable belted wasn't set, but getter method returned an object.",
                msg.getRightRearInflatableBelted());
        assertNull("Passenger child detected wasn't set, but getter method returned an object.",
                msg.getPassengerChildDetected());
        assertNull("Driver belt buckle status wasn't set, but getter method returned an object.",
                msg.getDriverBuckleBelted());
        assertNull("Passenger belt buckle status wasn't set, but getter method returned an object.",
                msg.getPassengerBuckleBelted());
        assertNull("Right row 2 belt buckle status wasn't set, but getter method returned an object.",
                msg.getRightRow2BuckleBelted());
        assertNull("Middle row 1 belt buckle status wasn't set, but getter method returned an object.",
                msg.getMiddleRow1BuckleBelted());
        assertNull("Left row 2 belt buckle status wasn't set, but getter method returned an object.",
                msg.getLeftRow2BuckleBelted());
        assertNull("Middle row 2 belt buckle status wasn't set, but getter method returned an object.",
                msg.getMiddleRow2BuckleBelted());
        assertNull("Middle row 3 belt buckle status wasn't set, but getter method returned an object.",
                msg.getMiddleRow3BuckleBelted());
        assertNull("Left row 3 belt buckle status wasn't set, but getter method returned an object.",
                msg.getLeftRow3BuckleBelted());
        assertNull("Right row 3 belt buckle status wasn't set, but getter method returned an object.",
                msg.getRightRow3BuckleBelted());
        assertNull("Passenger belt buckle deployed wasn't set, but getter method returned an object.",
                msg.getPassengerBeltDeployed());
        assertNull("Middle row 1 belt buckle deployed wasn't set, but getter method returned an object.",
                msg.getMiddleRow1BeltDeployed());
    }
    
    //TODO: remove this method?
    /*
    public void testCopy(){
        BeltStatus copy = new BeltStatus(msg);
        
        assertNotSame("Object was not copied.", copy, msg);
        
        String error = "Object data was not copied correctly.";
        for(SeatZone zone : EnumSet.allOf(SeatZone.class)){
            assertEquals(error, copy.getZoneBeltBuckleStatus(zone), msg.getZoneBeltBuckleStatus(zone));
            assertEquals(error, copy.getZoneBeltDeployedStatus(zone), msg.getZoneBeltDeployedStatus(zone));
        }
        
        assertEquals(error, copy.getLeftRearInflatableBelted(), msg.getLeftRearInflatableBelted());
        assertEquals(error, copy.getRightRearInflatableBelted(), msg.getRightRearInflatableBelted());
        assertEquals(error, copy.getPassengerChildDetected(), msg.getPassengerChildDetected());
    }
    */
}
