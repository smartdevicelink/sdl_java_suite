package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.MenuParams}
 */
public class MenuParamsTests extends TestCase{

    private MenuParams msg;

    @Override
    public void setUp(){
        msg = new MenuParams();

        msg.setMenuName(TestValues.GENERAL_STRING);
        msg.setParentID(TestValues.GENERAL_INT);
        msg.setPosition(TestValues.GENERAL_INT);
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
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, menuName);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, parentId);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, position);
        
        // Invalid/Null Tests
        MenuParams msg = new MenuParams();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getMenuName());
        assertNull(TestValues.NULL, msg.getParentID());
        assertNull(TestValues.NULL, msg.getPosition());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(MenuParams.KEY_MENU_NAME, TestValues.GENERAL_STRING);
            reference.put(MenuParams.KEY_PARENT_ID, TestValues.GENERAL_INT);
            reference.put(MenuParams.KEY_POSITION, TestValues.GENERAL_INT);

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