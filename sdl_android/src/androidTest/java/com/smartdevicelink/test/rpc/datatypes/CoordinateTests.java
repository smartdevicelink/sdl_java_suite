package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.Coordinate;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by austinkirk on 6/6/17.
 */

public class CoordinateTests extends TestCase {

    private Coordinate msg;

    @Override
    public void setUp(){
        msg = new Coordinate();

        msg.setLatitudeDegrees(Test.GENERAL_FLOAT);
        msg.setLongitudeDegrees(Test.GENERAL_FLOAT);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        float floatLat = msg.getLatitudeDegrees();
        float floatLong = msg.getLongitudeDegrees();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_FLOAT, floatLat);
        assertEquals(Test.MATCH, Test.GENERAL_FLOAT, floatLong);
        // Invalid/Null Tests
        Coordinate msg = new Coordinate();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getLatitudeDegrees());
        assertNull(Test.NULL, msg.getLongitudeDegrees());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Coordinate.KEY_LATITUDE_DEGREES, (Float) Test.GENERAL_FLOAT);
            reference.put(Coordinate.KEY_LONGITUDE_DEGREES, (Float) Test.GENERAL_FLOAT);

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