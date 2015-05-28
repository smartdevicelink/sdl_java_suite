package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;


/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ImageResolution}
 */
public class ImageResolutionTests extends TestCase{

    private ImageResolution msg;

    @Override
    public void setUp(){
        msg = new ImageResolution();

        msg.setResolutionHeight(Test.GENERAL_INT);
        msg.setResolutionWidth(Test.GENERAL_INT);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        int height = msg.getResolutionHeight();
        int width = msg.getResolutionWidth();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, height);
        assertEquals(Test.MATCH, Test.GENERAL_INT, width);
        
        // Invalid/Null Tests
        ImageResolution msg = new ImageResolution();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getResolutionHeight());
        assertNull(Test.NULL, msg.getResolutionWidth());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ImageResolution.KEY_RESOLUTION_HEIGHT, Test.GENERAL_INT);
            reference.put(ImageResolution.KEY_RESOLUTION_WIDTH, Test.GENERAL_INT);

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