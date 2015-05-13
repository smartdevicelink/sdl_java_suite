package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.ClusterModeStatus;
import com.smartdevicelink.proxy.rpc.enums.CarModeStatus;
import com.smartdevicelink.proxy.rpc.enums.PowerModeQualificationStatus;
import com.smartdevicelink.proxy.rpc.enums.PowerModeStatus;
import com.smartdevicelink.test.JsonUtils;

public class ClusterModeStatusTests extends TestCase{

    private static final boolean                      POWER_MODE_ACTIVE               = true;
    private static final PowerModeQualificationStatus POWER_MODE_QUALIFICATION_STATUS = PowerModeQualificationStatus.POWER_MODE_OK;
    private static final CarModeStatus                CAR_MODE_STATUS                 = CarModeStatus.NORMAL;
    private static final PowerModeStatus              POWER_MODE_STATUS               = PowerModeStatus.RUNNING_2;

    private ClusterModeStatus                         msg;

    @Override
    public void setUp(){
        msg = new ClusterModeStatus();

        msg.setPowerModeActive(POWER_MODE_ACTIVE);
        msg.setCarModeStatus(CAR_MODE_STATUS);
        msg.setPowerModeQualificationStatus(POWER_MODE_QUALIFICATION_STATUS);
        msg.setPowerModeStatus(POWER_MODE_STATUS);
    }

    public void testPowerModeActive(){
        boolean copy = msg.getPowerModeActive();
        assertEquals("Input value didn't match expected value.", POWER_MODE_ACTIVE, copy);
    }

    public void testPowerModeQualificationStatus(){
        PowerModeQualificationStatus copy = msg.getPowerModeQualificationStatus();
        assertEquals("Input value didn't match expected value.", POWER_MODE_QUALIFICATION_STATUS, copy);
    }

    public void testPowerModeStatus(){
        PowerModeStatus copy = msg.getPowerModeStatus();
        assertEquals("Input value didn't match expected value.", POWER_MODE_STATUS, copy);
    }

    public void testCarModeStatus(){
        CarModeStatus copy = msg.getCarModeStatus();
        assertEquals("Input value didn't match expected value.", CAR_MODE_STATUS, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ClusterModeStatus.KEY_POWER_MODE_ACTIVE, POWER_MODE_ACTIVE);
            reference.put(ClusterModeStatus.KEY_POWER_MODE_STATUS, POWER_MODE_STATUS);
            reference.put(ClusterModeStatus.KEY_POWER_MODE_QUALIFICATION_STATUS,
                    POWER_MODE_QUALIFICATION_STATUS);
            reference.put(ClusterModeStatus.KEY_CAR_MODE_STATUS, CAR_MODE_STATUS);

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
        ClusterModeStatus msg = new ClusterModeStatus();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Power mode active wasn't set, but getter method returned an object.", msg.getPowerModeActive());
        assertNull("Power mode status wasn't set, but getter method returned an object.", msg.getPowerModeStatus());
        assertNull("Power mode qualification status wasn't set, but getter method returned an object.",
                msg.getPowerModeQualificationStatus());
        assertNull("Car mode status wasn't set, but getter method returned an object.", msg.getCarModeStatus());
    }
  //TODO: remove testCopy()?
    /*
    public void testCopy(){
        ClusterModeStatus copy = new ClusterModeStatus(msg);
        
        assertNotSame("Object was not copied.", copy, msg);
        
        String error = "Object data was not copied correctly.";
        assertEquals(error, copy.getPowerModeActive(), msg.getPowerModeActive());
        assertEquals(error, copy.getPowerModeStatus(), msg.getPowerModeStatus());
        assertEquals(error, copy.getPowerModeQualificationStatus(), msg.getPowerModeQualificationStatus());
        assertEquals(error, copy.getCarModeStatus(), msg.getCarModeStatus());
    }
    */
}
