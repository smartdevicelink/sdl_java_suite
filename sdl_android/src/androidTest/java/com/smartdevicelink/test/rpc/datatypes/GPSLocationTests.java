package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.GPSLocation;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class GPSLocationTests extends TestCase {

    private GPSLocation msg;

    @Override
    public void setUp(){
        msg = new GPSLocation();

        msg.setLatitudeDegrees(Test.GENERAL_FLOAT);
        msg.setLongitudeDegrees(Test.GENERAL_FLOAT);
        msg.setAltitudeMeters(Test.GENERAL_FLOAT);
    }

    /**
    * Tests the expected values of the RPC message.
    */
    public void testRpcValues () {
        // Test Values
        float floatLat = msg.getLatitudeDegrees();
        float floatLong = msg.getLongitudeDegrees();
        float altitudeMeters = msg.getAltitudeMeters();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_FLOAT, floatLat);
        assertEquals(Test.MATCH, Test.GENERAL_FLOAT, floatLong);
        assertEquals(Test.MATCH, Test.GENERAL_FLOAT, altitudeMeters);
        // Invalid/Null Tests
        GPSLocation msg = new GPSLocation();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getLatitudeDegrees());
        assertNull(Test.NULL, msg.getLongitudeDegrees());
        assertNull(Test.NULL, msg.getAltitudeMeters());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(GPSLocation.KEY_LATITUDE_DEGREES, (Float) Test.GENERAL_FLOAT);
            reference.put(GPSLocation.KEY_LONGITUDE_DEGREES, (Float) Test.GENERAL_FLOAT);
            reference.put(GPSLocation.KEY_ALTITUDE_METERS, (Float) Test.GENERAL_FLOAT);

            JSONObject underTest = msg.serializeJSON();

            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                Object a = JsonUtils.readObjectFromJsonObject(reference, key);
                Object b = JsonUtils.readObjectFromJsonObject(underTest, key);

                assertEquals(Test.MATCH, a, b);
            }
        } catch(JSONException e){
            fail(Test.JSON_FAIL);
        }
    }
}