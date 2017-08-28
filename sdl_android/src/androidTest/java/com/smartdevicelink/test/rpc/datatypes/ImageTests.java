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
        msg.setTemplateImage(Test.GENERAL_BOOLEAN);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        ImageType imageType = msg.getImageType();
        String value = msg.getValue();
        Boolean isTemplate = msg.isTemplateImage();

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
        assertNull(Test.NULL, msg.isTemplateImage());
    }

    public void testJsonWithOutTemplateKey(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Image.KEY_IMAGE_TYPE, Test.GENERAL_IMAGETYPE);
            reference.put(Image.KEY_VALUE, Test.GENERAL_STRING);
            reference.put(Image.KEY_IS_TEMPLATE_IMAGE, Test.GENERAL_BOOLEAN);

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

    public void testJsonWithTemplateKey(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Image.KEY_IMAGE_TYPE, Test.GENERAL_IMAGETYPE);
            reference.put(Image.KEY_VALUE, Test.GENERAL_STRING);
            reference.put(Image.KEY_IS_TEMPLATE_IMAGE, Test.GENERAL_BOOLEAN);

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

    public void testImageObjectCreation(){
        Image img = new Image(Test.GENERAL_STRING,Test.GENERAL_IMAGETYPE,Test.GENERAL_BOOLEAN);
        assertNotNull(img);

        Image img1 = null;
        Image img2 = null;
        Image img3 = null;
        Image img4 = null;

        try {
            img1 = new Image(null, Test.GENERAL_IMAGETYPE, Test.GENERAL_BOOLEAN);
        }catch (IllegalArgumentException e){
            assertNull(img1);
        }

        try {
            img2 = new Image(Test.GENERAL_STRING, null, Test.GENERAL_BOOLEAN);
        }catch (IllegalArgumentException e){
            assertNull(img2);
        }

        try {
            img3 = new Image(Test.GENERAL_STRING, Test.GENERAL_IMAGETYPE, null);
        }catch (IllegalArgumentException e){
            assertNotNull(img3);
        }

        try {
            img4 = new Image(null, null, Test.GENERAL_BOOLEAN);
        }catch (IllegalArgumentException e){
            assertNull(img4);
        }
    }
}