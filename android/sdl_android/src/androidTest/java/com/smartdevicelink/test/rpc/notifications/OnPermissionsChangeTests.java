package com.smartdevicelink.test.rpc.notifications;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnPermissionsChange}
 */
public class OnPermissionsChangeTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnPermissionsChange msg = new OnPermissionsChange();

        msg.setPermissionItem(Test.GENERAL_PERMISSIONITEM_LIST);
        
        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_PERMISSIONS_CHANGE.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnPermissionsChange.KEY_PERMISSION_ITEM, Test.JSON_PERMISSIONITEMS);
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {       	
    	// Test Values
        List<PermissionItem> data = ( (OnPermissionsChange) msg ).getPermissionItem();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_PERMISSIONITEM_LIST.size(), data.size());
        for(int i=0; i<data.size(); i++){
            assertTrue(Test.TRUE, Validator.validatePermissionItem(Test.GENERAL_PERMISSIONITEM_LIST.get(i), data.get(i)));
        }
    
        // Invalid/Null Tests
        OnPermissionsChange msg = new OnPermissionsChange();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getPermissionItem());
    }
}