package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnPermissionsChange}
 */
public class OnPermissionsChangeTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnPermissionsChange msg = new OnPermissionsChange();

        msg.setPermissionItem(TestValues.GENERAL_PERMISSIONITEM_LIST);
        msg.setRequireEncryption(TestValues.GENERAL_BOOLEAN);
        
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
            result.put(OnPermissionsChange.KEY_PERMISSION_ITEM, TestValues.JSON_PERMISSIONITEMS);
            result.put(OnPermissionsChange.KEY_REQUIRE_ENCRYPTION, TestValues.GENERAL_BOOLEAN);
        }catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {       	
    	// Test Values
        List<PermissionItem> data = ( (OnPermissionsChange) msg ).getPermissionItem();
        boolean isRequired = ((OnPermissionsChange)msg).getRequireEncryption();
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_PERMISSIONITEM_LIST.size(), data.size());
        for(int i=0; i<data.size(); i++){
            assertTrue(TestValues.TRUE, Validator.validatePermissionItem(TestValues.GENERAL_PERMISSIONITEM_LIST.get(i), data.get(i)));
        }
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, isRequired);
    
        // Invalid/Null Tests
        OnPermissionsChange msg = new OnPermissionsChange();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);
        assertNull(TestValues.NULL, msg.getRequireEncryption());

        assertNull(TestValues.NULL, msg.getPermissionItem());
    }
}