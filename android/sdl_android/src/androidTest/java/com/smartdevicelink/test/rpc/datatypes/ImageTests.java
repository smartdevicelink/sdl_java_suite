package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

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

        msg.setImageType(TestValues.GENERAL_IMAGETYPE);
        msg.setValue(TestValues.GENERAL_STRING);
        msg.setIsTemplate(TestValues.GENERAL_BOOLEAN);
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
        assertEquals(TestValues.MATCH, TestValues.GENERAL_IMAGETYPE, imageType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, value);
        assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, isTemplate);
        
        // Invalid/Null Tests
        Image msg = new Image();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getImageType());
        assertNull(TestValues.NULL, msg.getValue());
        assertNull(TestValues.NULL, msg.getBulkData());
        assertNull(TestValues.NULL, msg.getIsTemplate());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Image.KEY_IMAGE_TYPE, TestValues.GENERAL_IMAGETYPE);
            reference.put(Image.KEY_VALUE, TestValues.GENERAL_STRING);
            reference.put(Image.KEY_IS_TEMPLATE, TestValues.GENERAL_BOOLEAN);

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