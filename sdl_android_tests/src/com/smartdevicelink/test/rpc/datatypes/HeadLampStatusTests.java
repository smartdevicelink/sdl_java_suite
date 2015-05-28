package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.HeadLampStatus;
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.HeadLampStatus}
 */
public class HeadLampStatusTests extends TestCase{
	
    private HeadLampStatus msg;

    @Override
    public void setUp(){
        msg = new HeadLampStatus();

        msg.setAmbientLightStatus(Test.GENERAL_AMBIENTLIGHTSTATUS);
        msg.setLowBeamsOn(Test.GENERAL_BOOLEAN);
        msg.setHighBeamsOn(Test.GENERAL_BOOLEAN);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        boolean lowBeams = msg.getLowBeamsOn();
        boolean highBeams = msg.getHighBeamsOn();
        AmbientLightStatus ambientLights = msg.getAmbientLightStatus();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_AMBIENTLIGHTSTATUS, ambientLights);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, highBeams);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, lowBeams);
                
        // Invalid/Null Tests
        HeadLampStatus msg = new HeadLampStatus();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getLowBeamsOn());
        assertNull(Test.NULL, msg.getHighBeamsOn());
        assertNull(Test.NULL, msg.getAmbientLightStatus());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(HeadLampStatus.KEY_HIGH_BEAMS_ON, Test.GENERAL_BOOLEAN);
            reference.put(HeadLampStatus.KEY_LOW_BEAMS_ON, Test.GENERAL_BOOLEAN);
            reference.put(HeadLampStatus.KEY_AMBIENT_LIGHT_SENSOR_STATUS, Test.GENERAL_AMBIENTLIGHTSTATUS);

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