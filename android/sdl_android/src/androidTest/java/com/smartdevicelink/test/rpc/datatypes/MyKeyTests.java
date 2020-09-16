package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.MyKey;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.MyKey}
 */
public class MyKeyTests extends TestCase{

    private MyKey msg;

    @Override
    public void setUp(){
        msg = new MyKey();
        msg.setE911Override(TestValues.GENERAL_VEHICLEDATASTATUS);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        VehicleDataStatus override = msg.getE911Override();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHICLEDATASTATUS, override);
        
        // Invalid/Null Tests
        MyKey msg = new MyKey();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getE911Override());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(MyKey.KEY_E_911_OVERRIDE, TestValues.GENERAL_VEHICLEDATASTATUS);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}