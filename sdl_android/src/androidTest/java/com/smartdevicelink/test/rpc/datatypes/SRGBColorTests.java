package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.SRGBColor;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SRGBColor}
 */
public class SRGBColorTests extends TestCase{
	
    private SRGBColor msg;

    @Override
    public void setUp(){
        msg = new SRGBColor();

        msg.setRed(Test.GENERAL_INT);
        msg.setGreen(Test.GENERAL_INT);
        msg.setBlue(Test.GENERAL_INT);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
        // Test Values

        int red = msg.getRed();
        int green = msg.getGreen();
        int blue = msg.getBlue();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, red);
        assertEquals(Test.MATCH, Test.GENERAL_INT, green);
        assertEquals(Test.MATCH, Test.GENERAL_INT, blue);

        // Invalid/Null Tests
        SRGBColor msg = new SRGBColor();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getRed());
        assertNull(Test.NULL, msg.getGreen());
        assertNull(Test.NULL, msg.getBlue());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{

            reference.put(SRGBColor.KEY_RED, Test.GENERAL_INT);
            reference.put(SRGBColor.KEY_GREEN, Test.GENERAL_INT);
            reference.put(SRGBColor.KEY_BLUE, Test.GENERAL_INT);

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