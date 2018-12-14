package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.MenuParams}
 */
public class MenuParamsTests extends TestCase{

    private MenuParams msg;

    @Override
    public void setUp(){
        msg = new MenuParams();

        msg.setMenuName(Test.GENERAL_STRING);
        msg.setParentID(Test.GENERAL_INT);
        msg.setPosition(Test.GENERAL_INT);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        String menuName = msg.getMenuName();
        int parentId = msg.getParentID();
        int position = msg.getPosition();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING, menuName);
        assertEquals(Test.MATCH, Test.GENERAL_INT, parentId);
        assertEquals(Test.MATCH, Test.GENERAL_INT, position);
        
        // Invalid/Null Tests
        MenuParams msg = new MenuParams();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getMenuName());
        assertNull(Test.NULL, msg.getParentID());
        assertNull(Test.NULL, msg.getPosition());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(MenuParams.KEY_MENU_NAME, Test.GENERAL_STRING);
            reference.put(MenuParams.KEY_PARENT_ID, Test.GENERAL_INT);
            reference.put(MenuParams.KEY_POSITION, Test.GENERAL_INT);

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