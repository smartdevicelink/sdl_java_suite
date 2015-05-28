package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.Image}
 */
public class ImageTests extends TestCase{

    private Image msg;

    @Override
    public void setUp(){
        msg = new Image();

        msg.setImageType(Test.GENERAL_IMAGETYPE);
        msg.setValue(Test.GENERAL_STRING);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        ImageType imageType = msg.getImageType();
        String value = msg.getValue();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_IMAGETYPE, imageType);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, value);
        
        // Invalid/Null Tests
        Image msg = new Image();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getImageType());
        assertNull(Test.NULL, msg.getValue());
        assertNull(Test.NULL, msg.getBulkData());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Image.KEY_IMAGE_TYPE, Test.GENERAL_IMAGETYPE);
            reference.put(Image.KEY_VALUE, Test.GENERAL_STRING);

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