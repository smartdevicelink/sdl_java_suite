package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.MyKey;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.MyKey}
 */
public class MyKeyTests extends TestCase{

    private MyKey msg;

    @Override
    public void setUp(){
        msg = new MyKey();
        msg.setE911Override(Test.GENERAL_VEHICLEDATASTATUS);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        VehicleDataStatus override = msg.getE911Override();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_VEHICLEDATASTATUS, override);
        
        // Invalid/Null Tests
        MyKey msg = new MyKey();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getE911Override());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(MyKey.KEY_E_911_OVERRIDE, Test.GENERAL_VEHICLEDATASTATUS);

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