package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.DIDResult;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.DIDResult}
 */
public class DIDResultTests extends TestCase {

    private DIDResult msg;

    @Override
    public void setUp(){
        msg = new DIDResult();

        msg.setData(TestValues.GENERAL_STRING);
        msg.setResultCode(TestValues.GENERAL_VEHICLEDATARESULTCODE);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        String data = msg.getData();
        VehicleDataResultCode resultCode = msg.getResultCode();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, data);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHICLEDATARESULTCODE, resultCode);
        
        // Invalid/Null Tests
        DIDResult msg = new DIDResult();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getData());
        assertNull(TestValues.NULL, msg.getResultCode());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DIDResult.KEY_DATA, TestValues.GENERAL_STRING);
            reference.put(DIDResult.KEY_RESULT_CODE, TestValues.GENERAL_VEHICLEDATARESULTCODE);

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