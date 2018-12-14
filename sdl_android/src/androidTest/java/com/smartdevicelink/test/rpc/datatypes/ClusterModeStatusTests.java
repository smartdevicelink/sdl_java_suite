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
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ClusterModeStatus}
 */
public class ClusterModeStatusTests extends TestCase{

    private ClusterModeStatus                         msg;

    @Override
    public void setUp(){
        msg = new ClusterModeStatus();

        msg.setPowerModeActive(Test.GENERAL_BOOLEAN);
        msg.setCarModeStatus(Test.GENERAL_CARMODESTATUS);
        msg.setPowerModeQualificationStatus(Test.GENERAL_POWERMODEQUALIFICATIONSTATUS);
        msg.setPowerModeStatus(Test.GENERAL_POWERMODESTATUS);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        boolean powerMode = msg.getPowerModeActive();
        PowerModeQualificationStatus qualification = msg.getPowerModeQualificationStatus();
        PowerModeStatus status = msg.getPowerModeStatus();
        CarModeStatus carStatus = msg.getCarModeStatus();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, powerMode);
        assertEquals(Test.MATCH, Test.GENERAL_POWERMODEQUALIFICATIONSTATUS, qualification);
        assertEquals(Test.MATCH, Test.GENERAL_POWERMODESTATUS, status);
        assertEquals(Test.MATCH, Test.GENERAL_CARMODESTATUS, carStatus);
        
        // Invalid/Null Tests
        ClusterModeStatus msg = new ClusterModeStatus();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getPowerModeActive());
        assertNull(Test.NULL, msg.getPowerModeStatus());
        assertNull(Test.NULL, msg.getPowerModeQualificationStatus());
        assertNull(Test.NULL, msg.getCarModeStatus());
    }
    
    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ClusterModeStatus.KEY_POWER_MODE_ACTIVE, Test.GENERAL_BOOLEAN);
            reference.put(ClusterModeStatus.KEY_POWER_MODE_STATUS, Test.GENERAL_POWERMODESTATUS);
            reference.put(ClusterModeStatus.KEY_POWER_MODE_QUALIFICATION_STATUS, Test.GENERAL_POWERMODEQUALIFICATIONSTATUS);
            reference.put(ClusterModeStatus.KEY_CAR_MODE_STATUS, Test.GENERAL_CARMODESTATUS);

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