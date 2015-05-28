package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.DIDResult;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.DIDResult}
 */
public class DIDResultTests extends TestCase {

    private DIDResult msg;

    @Override
    public void setUp(){
        msg = new DIDResult();

        msg.setData(Test.GENERAL_STRING);
        msg.setResultCode(Test.GENERAL_VEHICLEDATARESULTCODE);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        String data = msg.getData();
        VehicleDataResultCode resultCode = msg.getResultCode();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING, data);
        assertEquals(Test.MATCH, Test.GENERAL_VEHICLEDATARESULTCODE, resultCode);
        
        // Invalid/Null Tests
        DIDResult msg = new DIDResult();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getData());
        assertNull(Test.NULL, msg.getResultCode());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DIDResult.KEY_DATA, Test.GENERAL_STRING);
            reference.put(DIDResult.KEY_RESULT_CODE, Test.GENERAL_VEHICLEDATARESULTCODE);

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