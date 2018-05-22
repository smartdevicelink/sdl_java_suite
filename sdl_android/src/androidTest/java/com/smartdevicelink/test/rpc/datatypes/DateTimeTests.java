package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.DateTime;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by austinkirk on 6/6/17.
 */

public class DateTimeTests extends TestCase {

    private DateTime msg;

    @Override
    public void setUp(){
        msg = new DateTime();

        msg.setYear(Test.GENERAL_INT);
        msg.setMonth(Test.GENERAL_INT);
        msg.setDay(Test.GENERAL_INT);
        msg.setHour(Test.GENERAL_INT);
        msg.setMinute(Test.GENERAL_INT);
        msg.setSecond(Test.GENERAL_INT);
        msg.setMilliSecond(Test.GENERAL_INT);
        msg.setTzHour(Test.GENERAL_INT);
        msg.setTzMinute(Test.GENERAL_INT);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        Integer year = msg.getYear();
        Integer month = msg.getMonth();
        Integer day = msg.getDay();
        Integer hour = msg.getHour();
        Integer min = msg.getMinute();
        Integer sec = msg.getSecond();
        Integer ms = msg.getMilliSecond();
        Integer tzHour = msg.getTzHour();
        Integer tzMin = msg.getTzMinute();

        // Valid Tests
        assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, year);
        assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, month);
        assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, day);
        assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, hour);
        assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, min);
        assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, sec);
        assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, ms);
        assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, tzHour);
        assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, tzMin);

        // Invalid/Null Tests
        DateTime msg = new DateTime();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getYear());
        assertNull(Test.NULL, msg.getMonth());
        assertNull(Test.NULL, msg.getDay());
        assertNull(Test.NULL, msg.getHour());
        assertNull(Test.NULL, msg.getMinute());
        assertNull(Test.NULL, msg.getSecond());
        assertNull(Test.NULL, msg.getMilliSecond());
        assertNull(Test.NULL, msg.getTzHour());
        assertNull(Test.NULL, msg.getTzMinute());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DateTime.KEY_YEAR, (Integer) Test.GENERAL_INT);
            reference.put(DateTime.KEY_MONTH, (Integer) Test.GENERAL_INT);
            reference.put(DateTime.KEY_DAY, (Integer) Test.GENERAL_INT);
            reference.put(DateTime.KEY_HOUR, (Integer) Test.GENERAL_INT);
            reference.put(DateTime.KEY_MINUTE, (Integer) Test.GENERAL_INT);
            reference.put(DateTime.KEY_SECOND, (Integer) Test.GENERAL_INT);
            reference.put(DateTime.KEY_MILLISECOND, (Integer) Test.GENERAL_INT);
            reference.put(DateTime.KEY_TZ_HOUR, (Integer) Test.GENERAL_INT);
            reference.put(DateTime.KEY_TZ_MINUTE, (Integer) Test.GENERAL_INT);

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