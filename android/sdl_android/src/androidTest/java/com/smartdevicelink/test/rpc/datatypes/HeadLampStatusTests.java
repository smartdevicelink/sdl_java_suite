package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.HeadLampStatus;
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.HeadLampStatus}
 */
public class HeadLampStatusTests extends TestCase{
	
    private HeadLampStatus msg;

    @Override
    public void setUp(){
        msg = new HeadLampStatus();

        msg.setAmbientLightStatus(TestValues.GENERAL_AMBIENTLIGHTSTATUS);
        msg.setLowBeamsOn(TestValues.GENERAL_BOOLEAN);
        msg.setHighBeamsOn(TestValues.GENERAL_BOOLEAN);
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
        assertEquals(TestValues.MATCH, TestValues.GENERAL_AMBIENTLIGHTSTATUS, ambientLights);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, highBeams);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, lowBeams);
                
        // Invalid/Null Tests
        HeadLampStatus msg = new HeadLampStatus();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getLowBeamsOn());
        assertNull(TestValues.NULL, msg.getHighBeamsOn());
        assertNull(TestValues.NULL, msg.getAmbientLightStatus());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(HeadLampStatus.KEY_HIGH_BEAMS_ON, TestValues.GENERAL_BOOLEAN);
            reference.put(HeadLampStatus.KEY_LOW_BEAMS_ON, TestValues.GENERAL_BOOLEAN);
            reference.put(HeadLampStatus.KEY_AMBIENT_LIGHT_SENSOR_STATUS, TestValues.GENERAL_AMBIENTLIGHTSTATUS);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}