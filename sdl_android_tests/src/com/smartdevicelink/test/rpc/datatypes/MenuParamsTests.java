package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.test.utils.JsonUtils;

public class MenuParamsTests extends TestCase{

    private static final String MENU_NAME = "Favorites Menu";
    private static final int    PARENT_ID = 5026;
    private static final int    POSITION  = 3;

    private MenuParams          msg;

    @Override
    public void setUp(){
        msg = new MenuParams();

        msg.setMenuName(MENU_NAME);
        msg.setParentID(PARENT_ID);
        msg.setPosition(POSITION);
    }

    public void testMenuName(){
        String copy = msg.getMenuName();
        assertEquals("Input value didn't match expected value.", MENU_NAME, copy);
    }

    public void testParentID(){
        int copy = msg.getParentID();
        assertEquals("Input value didn't match expected value.", PARENT_ID, copy);
    }

    public void testPosition(){
        int copy = msg.getPosition();
        assertEquals("Input value didn't match expected value.", POSITION, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(MenuParams.KEY_MENU_NAME, MENU_NAME);
            reference.put(MenuParams.KEY_PARENT_ID, PARENT_ID);
            reference.put(MenuParams.KEY_POSITION, POSITION);

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
        MenuParams msg = new MenuParams();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Menu name wasn't set, but getter method returned an object.", msg.getMenuName());
        assertNull("Parent ID wasn't set, but getter method returned an object.", msg.getParentID());
        assertNull("Position wasn't set, but getter method returned an object.", msg.getPosition());
    }
}
