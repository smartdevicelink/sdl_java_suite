package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class ImageTests extends TestCase{

    private static final ImageType IMAGE_TYPE = ImageType.DYNAMIC;
    private static final String    IMAGE_NAME = "image_name.png";
    private static final byte[]    IMAGE_DATA = new byte[] { 4, 7, 1, 8, 14, 10, 0, 0, 3 };

    private Image                  msg;

    @Override
    public void setUp(){
        msg = new Image();

        msg.setImageType(IMAGE_TYPE);
        msg.setValue(IMAGE_NAME);
        msg.setBulkData(IMAGE_DATA);
    }

    public void testImageType(){
        ImageType copy = msg.getImageType();
        assertEquals("Input value didn't match expected value.", IMAGE_TYPE, copy);
    }

    public void testValue(){
        String copy = msg.getValue();
        assertEquals("Input value didn't match expected value.", IMAGE_NAME, copy);
    }

    public void testBulkData(){
        byte[] copy = msg.getBulkData();
        assertEquals("Bulk data size didn't match expected size.", IMAGE_DATA.length, copy.length);
        assertTrue("Input value didn't match expected value.", Validator.validateBulkData(IMAGE_DATA, copy));
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Image.KEY_IMAGE_TYPE, IMAGE_TYPE);
            reference.put(Image.KEY_VALUE, IMAGE_NAME);

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
                        JsonUtils.readObjectFromJsonObject(reference, key),
                        JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        Image msg = new Image();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Image type wasn't set, but getter method returned an object.", msg.getImageType());
        assertNull("Value wasn't set, but getter method returned an object.", msg.getValue());
        assertNull("Bulk data wasn't set, but getter method returned an object.", msg.getBulkData());
    }
}
