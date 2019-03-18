package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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
        msg.setIsTemplate(Test.GENERAL_BOOLEAN);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        ImageType imageType = msg.getImageType();
        String value = msg.getValue();
        Boolean isTemplate = msg.getIsTemplate();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_IMAGETYPE, imageType);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, value);
        assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, isTemplate);
        
        // Invalid/Null Tests
        Image msg = new Image();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getImageType());
        assertNull(Test.NULL, msg.getValue());
        assertNull(Test.NULL, msg.getBulkData());
        assertNull(Test.NULL, msg.getIsTemplate());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Image.KEY_IMAGE_TYPE, Test.GENERAL_IMAGETYPE);
            reference.put(Image.KEY_VALUE, Test.GENERAL_STRING);
            reference.put(Image.KEY_IS_TEMPLATE, Test.GENERAL_BOOLEAN);

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