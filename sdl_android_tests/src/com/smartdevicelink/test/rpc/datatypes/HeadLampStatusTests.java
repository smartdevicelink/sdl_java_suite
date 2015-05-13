package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.HeadLampStatus;
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;
import com.smartdevicelink.test.JsonUtils;

public class HeadLampStatusTests extends TestCase{

    private static final boolean            LOW_BEAMS_ON         = false;
    private static final boolean            HIGH_BEAMS_ON        = true;
    private static final AmbientLightStatus AMBIENT_LIGHT_STATUS = AmbientLightStatus.NIGHT;

    private HeadLampStatus                  msg;

    @Override
    public void setUp(){
        msg = new HeadLampStatus();

        msg.setAmbientLightStatus(AMBIENT_LIGHT_STATUS);
        msg.setLowBeamsOn(LOW_BEAMS_ON);
        msg.setHighBeamsOn(HIGH_BEAMS_ON);
    }

    public void testLowBeamsOn(){
        boolean copy = msg.getLowBeamsOn();
        assertEquals("Input value didn't match expected value.", LOW_BEAMS_ON, copy);
    }

    public void testHighBeamsOn(){
        boolean copy = msg.getHighBeamsOn();
        assertEquals("Input value didn't match expected value.", HIGH_BEAMS_ON, copy);
    }

    public void testAmbientLightStatus(){
        AmbientLightStatus copy = msg.getAmbientLightStatus();
        assertEquals("Input value didn't match expected value.", AMBIENT_LIGHT_STATUS, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(HeadLampStatus.KEY_HIGH_BEAMS_ON, HIGH_BEAMS_ON);
            reference.put(HeadLampStatus.KEY_LOW_BEAMS_ON, LOW_BEAMS_ON);
            reference.put(HeadLampStatus.KEY_AMBIENT_LIGHT_SENSOR_STATUS, AMBIENT_LIGHT_STATUS);

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
        HeadLampStatus msg = new HeadLampStatus();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Low beams on wasn't set, but getter method returned an object.", msg.getLowBeamsOn());
        assertNull("High beams on wasn't set, but getter method returned an object.", msg.getHighBeamsOn());
        assertNull("Ambient light status wasn't set, but getter method returned an object.",
                msg.getAmbientLightStatus());
    }
}
