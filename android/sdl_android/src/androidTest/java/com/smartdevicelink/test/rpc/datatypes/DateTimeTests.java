package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.DateTime;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

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

        msg.setYear(TestValues.GENERAL_INT);
        msg.setMonth(TestValues.GENERAL_INT);
        msg.setDay(TestValues.GENERAL_INT);
        msg.setHour(TestValues.GENERAL_INT);
        msg.setMinute(TestValues.GENERAL_INT);
        msg.setSecond(TestValues.GENERAL_INT);
        msg.setMilliSecond(TestValues.GENERAL_INT);
        msg.setTzHour(TestValues.GENERAL_INT);
        msg.setTzMinute(TestValues.GENERAL_INT);
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
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, year);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, month);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, day);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, hour);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, min);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, sec);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, ms);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, tzHour);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, tzMin);

        // Invalid/Null Tests
        DateTime msg = new DateTime();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getYear());
        assertNull(TestValues.NULL, msg.getMonth());
        assertNull(TestValues.NULL, msg.getDay());
        assertNull(TestValues.NULL, msg.getHour());
        assertNull(TestValues.NULL, msg.getMinute());
        assertNull(TestValues.NULL, msg.getSecond());
        assertNull(TestValues.NULL, msg.getMilliSecond());
        assertNull(TestValues.NULL, msg.getTzHour());
        assertNull(TestValues.NULL, msg.getTzMinute());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DateTime.KEY_YEAR, (Integer) TestValues.GENERAL_INT);
            reference.put(DateTime.KEY_MONTH, (Integer) TestValues.GENERAL_INT);
            reference.put(DateTime.KEY_DAY, (Integer) TestValues.GENERAL_INT);
            reference.put(DateTime.KEY_HOUR, (Integer) TestValues.GENERAL_INT);
            reference.put(DateTime.KEY_MINUTE, (Integer) TestValues.GENERAL_INT);
            reference.put(DateTime.KEY_SECOND, (Integer) TestValues.GENERAL_INT);
            reference.put(DateTime.KEY_MILLISECOND, (Integer) TestValues.GENERAL_INT);
            reference.put(DateTime.KEY_TZ_HOUR, (Integer) TestValues.GENERAL_INT);
            reference.put(DateTime.KEY_TZ_MINUTE, (Integer) TestValues.GENERAL_INT);

            JSONObject underTest = msg.serializeJSON();

            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                Object a = JsonUtils.readObjectFromJsonObject(reference, key);
                Object b = JsonUtils.readObjectFromJsonObject(underTest, key);

                assertEquals(TestValues.MATCH, a, b);
            }
        } catch(JSONException e){
            fail(TestValues.JSON_FAIL);
        }
    }
}