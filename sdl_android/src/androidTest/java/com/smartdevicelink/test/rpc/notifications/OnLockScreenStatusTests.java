package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnLockScreenStatus}
 */
public class OnLockScreenStatusTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnLockScreenStatus msg = new OnLockScreenStatus();

        msg.setDriverDistractionStatus(Test.GENERAL_BOOLEAN);
        msg.setHMILevel(Test.GENERAL_HMILEVEL);
        msg.setShowLockScreen(Test.GENERAL_LOCKSCREENSTATUS);
        msg.setUserSelected(Test.GENERAL_BOOLEAN);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_LOCK_SCREEN_STATUS.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnLockScreenStatus.KEY_DRIVER_DISTRACTION, Test.GENERAL_BOOLEAN);
            result.put(OnHMIStatus.KEY_HMI_LEVEL, Test.GENERAL_HMILEVEL);
            result.put(OnLockScreenStatus.KEY_SHOW_LOCK_SCREEN, Test.GENERAL_LOCKSCREENSTATUS);
            result.put(OnLockScreenStatus.KEY_USER_SELECTED, Test.GENERAL_BOOLEAN);
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
        Boolean status = ( (OnLockScreenStatus) msg ).getDriverDistractionStatus();
        HMILevel hmiLevel = ( (OnLockScreenStatus) msg ).getHMILevel();
        LockScreenStatus lockScreen = ( (OnLockScreenStatus) msg ).getShowLockScreen();
        boolean userSelected = ( (OnLockScreenStatus) msg ).getUserSelected();
        
        // Valid Tests
        assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, status);
        assertEquals(Test.MATCH, Test.GENERAL_HMILEVEL, hmiLevel);
        assertEquals(Test.MATCH, Test.GENERAL_LOCKSCREENSTATUS, lockScreen);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, userSelected);
    
        // Invalid/Null Tests
        OnLockScreenStatus msg = new OnLockScreenStatus();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getDriverDistractionStatus());
        assertNull(Test.NULL, msg.getHMILevel());
        assertNull(Test.NULL, msg.getShowLockScreen());
        assertNull(Test.NULL, msg.getUserSelected());
    }
}