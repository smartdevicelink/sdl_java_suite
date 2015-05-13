package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.test.JsonUtils;

public class ButtonCapabilitiesTests extends TestCase{

    private static final boolean    SHORT_PRESS_AVAILABLE = true;
    private static final boolean    LONG_PRESS_AVAILABLE  = true;
    private static final boolean    UP_DOWN_AVAILABLE     = true;
    private static final ButtonName BUTTON_NAME           = ButtonName.OK;

    private ButtonCapabilities      msg;

    @Override
    public void setUp(){
        msg = new ButtonCapabilities();

        msg.setLongPressAvailable(LONG_PRESS_AVAILABLE);
        msg.setName(BUTTON_NAME);
        msg.setShortPressAvailable(SHORT_PRESS_AVAILABLE);
        msg.setUpDownAvailable(UP_DOWN_AVAILABLE);
    }

    public void testShortPressAvailable(){
        boolean copy = msg.getShortPressAvailable();
        assertEquals("Input value didn't match expected value.", SHORT_PRESS_AVAILABLE, copy);
    }

    public void testLongPressAvailable(){
        boolean copy = msg.getLongPressAvailable();
        assertEquals("Input value didn't match expected value.", LONG_PRESS_AVAILABLE, copy);
    }

    public void testUpDownAvailable(){
        boolean copy = msg.getUpDownAvailable();
        assertEquals("Input value didn't match expected value.", UP_DOWN_AVAILABLE, copy);
    }

    public void testButtonName(){
        ButtonName copy = msg.getName();
        assertEquals("Input value didn't match expected value.", BUTTON_NAME, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, SHORT_PRESS_AVAILABLE);
            reference.put(ButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, LONG_PRESS_AVAILABLE);
            reference.put(ButtonCapabilities.KEY_UP_DOWN_AVAILABLE, UP_DOWN_AVAILABLE);
            reference.put(ButtonCapabilities.KEY_NAME, BUTTON_NAME);

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
        ButtonCapabilities msg = new ButtonCapabilities();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Short press available wasn't set, but getter method returned an object.",
                msg.getShortPressAvailable());
        assertNull("Long press available wasn't set, but getter method returned an object.",
                msg.getLongPressAvailable());
        assertNull("Up/down available wasn't set, but getter method returned an object.", msg.getUpDownAvailable());
        assertNull("Button name wasn't set, but getter method returned an object.", msg.getName());
    }

    //TODO: remove testCopy()?
    /*
    public void testCopy(){
        ButtonCapabilities copy = new ButtonCapabilities(msg);

        assertNotSame("Object was not copied.", copy, msg);

        String error = "Object data was not copied correctly.";
        assertEquals(error, copy.getShortPressAvailable(), msg.getShortPressAvailable());
        assertEquals(error, copy.getLongPressAvailable(), msg.getLongPressAvailable());
        assertEquals(error, copy.getUpDownAvailable(), msg.getUpDownAvailable());
        assertEquals(error, copy.getName(), msg.getName());
    }
    */
}
