package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.DTC;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.DTC}
 */
public class DTCTests extends TestCase{

    private DTC msg;

    @Override
    public void setUp(){
        msg = new DTC();

        msg.setIdentifier(Test.GENERAL_STRING);
        msg.setStatusByte(Test.GENERAL_STRING);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        String identifier = msg.getIdentifier();
        String statusByte = msg.getStatusByte();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING, identifier);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, statusByte);
        
        // Invalid/Null Tests
        DTC msg = new DTC();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getIdentifier());
        assertNull(Test.NULL, msg.getStatusByte());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DTC.KEY_IDENTIFIER, Test.GENERAL_STRING);
            reference.put(DTC.KEY_STATUS_BYTE, Test.GENERAL_STRING);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}