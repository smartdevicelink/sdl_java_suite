package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.test.utils.JsonUtils;

public class ImageResolutionTests extends TestCase{

    private static final int HEIGHT = 1920;
    private static final int WIDTH  = 1080;

    private ImageResolution  msg;

    @Override
    public void setUp(){
        msg = new ImageResolution();

        msg.setResolutionHeight(HEIGHT);
        msg.setResolutionWidth(WIDTH);
    }

    public void testHeight(){
        int copy = msg.getResolutionHeight();
        assertEquals("Input value didn't match expected value.", HEIGHT, copy);
    }

    public void testWidth(){
        int copy = msg.getResolutionWidth();
        assertEquals("Input value didn't match expected value.", WIDTH, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ImageResolution.KEY_RESOLUTION_HEIGHT, HEIGHT);
            reference.put(ImageResolution.KEY_RESOLUTION_WIDTH, WIDTH);

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
        ImageResolution msg = new ImageResolution();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Height wasn't set, but getter method returned an object.", msg.getResolutionHeight());
        assertNull("Width wasn't set, but getter method returned an object.", msg.getResolutionWidth());
    }
}
