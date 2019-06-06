package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.OasisAddress;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by austinkirk on 6/7/17.
 */

public class OasisAddressTests extends TestCase {
    private OasisAddress msg;

    @Override
    public void setUp(){
        msg = Test.GENERAL_OASISADDRESS;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        String f1 = msg.getAdministrativeArea();
        String f2 = msg.getSubAdministrativeArea();
        String f3 = msg.getCountryCode();
        String f4 = msg.getCountryName();
        String f5 = msg.getLocality();
        String f6 = msg.getSubLocality();
        String f7 = msg.getPostalCode();
        String f8 = msg.getThoroughfare();
        String f9 = msg.getSubThoroughfare();


        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING, f1);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, f2);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, f3);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, f4);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, f5);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, f6);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, f7);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, f8);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, f9);

        // Invalid/Null Tests
        OasisAddress msg = new OasisAddress();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(msg.getAdministrativeArea());
        assertNull(msg.getSubAdministrativeArea());
        assertNull(msg.getCountryCode());
        assertNull(msg.getCountryName());
        assertNull(msg.getLocality());
        assertNull(msg.getSubLocality());
        assertNull(msg.getPostalCode());
        assertNull(msg.getThoroughfare());
        assertNull(msg.getSubThoroughfare());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(OasisAddress.KEY_ADMINISTRATIVE_AREA, Test.GENERAL_STRING);
            reference.put(OasisAddress.KEY_SUB_ADMINISTRATIVE_AREA, Test.GENERAL_STRING);
            reference.put(OasisAddress.KEY_COUNTRY_CODE, Test.GENERAL_STRING);
            reference.put(OasisAddress.KEY_COUNTRY_NAME, Test.GENERAL_STRING);
            reference.put(OasisAddress.KEY_LOCALITY, Test.GENERAL_STRING);
            reference.put(OasisAddress.KEY_SUB_LOCALITY, Test.GENERAL_STRING);
            reference.put(OasisAddress.KEY_POSTAL_CODE, Test.GENERAL_STRING);
            reference.put(OasisAddress.KEY_THOROUGH_FARE, Test.GENERAL_STRING);
            reference.put(OasisAddress.KEY_SUB_THOROUGH_FARE, Test.GENERAL_STRING);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertEquals(Test.MATCH, reference.get(key),
                        underTest.get(key));
            }

        } catch(JSONException e){
            fail(Test.JSON_FAIL);
        }
    }
}