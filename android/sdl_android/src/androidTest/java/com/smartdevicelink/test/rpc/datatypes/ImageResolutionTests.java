package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ImageResolution}
 */
public class ImageResolutionTests extends TestCase{

    private ImageResolution msg;

    @Override
    public void setUp(){
        msg = new ImageResolution();

        msg.setResolutionHeight(TestValues.GENERAL_INT);
        msg.setResolutionWidth(TestValues.GENERAL_INT);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        int height = msg.getResolutionHeight();
        int width = msg.getResolutionWidth();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, height);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, width);
        
        // Invalid/Null Tests
        ImageResolution msg = new ImageResolution();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getResolutionHeight());
        assertNull(TestValues.NULL, msg.getResolutionWidth());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ImageResolution.KEY_RESOLUTION_HEIGHT, TestValues.GENERAL_INT);
            reference.put(ImageResolution.KEY_RESOLUTION_WIDTH, TestValues.GENERAL_INT);

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

    public void testSetResolutionWidth_Odd() {
        msg.setResolutionWidth(175);
        assertEquals(176, (int)msg.getResolutionWidth());
    }

    public void testSetResolutionHeight_Odd() {
        msg.setResolutionHeight(175);
        assertEquals(176, (int)msg.getResolutionHeight());
    }

    public void testSetResolutionWidth_Pair() {
        msg.setResolutionWidth(176);
        assertEquals(176, (int)msg.getResolutionWidth());

    }

    public void testSetResolutionHeight_Pair() {
        msg.setResolutionHeight(176);
        assertEquals(176, (int)msg.getResolutionHeight());

    }
}