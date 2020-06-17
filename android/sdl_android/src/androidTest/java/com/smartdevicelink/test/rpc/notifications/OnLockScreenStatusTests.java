package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnLockScreenStatus}
 */
public class OnLockScreenStatusTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnLockScreenStatus msg = new OnLockScreenStatus();

        msg.setDriverDistractionStatus(TestValues.GENERAL_BOOLEAN);
        msg.setHMILevel(TestValues.GENERAL_HMILEVEL);
        msg.setShowLockScreen(TestValues.GENERAL_LOCKSCREENSTATUS);
        msg.setUserSelected(TestValues.GENERAL_BOOLEAN);

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
            result.put(OnLockScreenStatus.KEY_DRIVER_DISTRACTION, TestValues.GENERAL_BOOLEAN);
            result.put(OnHMIStatus.KEY_HMI_LEVEL, TestValues.GENERAL_HMILEVEL);
            result.put(OnLockScreenStatus.KEY_SHOW_LOCK_SCREEN, TestValues.GENERAL_LOCKSCREENSTATUS);
            result.put(OnLockScreenStatus.KEY_USER_SELECTED, TestValues.GENERAL_BOOLEAN);
        }catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    @Test
    public void testRpcValues () {       	
    	// Test Values
        Boolean status = ( (OnLockScreenStatus) msg ).getDriverDistractionStatus();
        HMILevel hmiLevel = ( (OnLockScreenStatus) msg ).getHMILevel();
        LockScreenStatus lockScreen = ( (OnLockScreenStatus) msg ).getShowLockScreen();
        boolean userSelected = ( (OnLockScreenStatus) msg ).getUserSelected();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, status);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_HMILEVEL, hmiLevel);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LOCKSCREENSTATUS, lockScreen);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, userSelected);
    
        // Invalid/Null Tests
        OnLockScreenStatus msg = new OnLockScreenStatus();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getDriverDistractionStatus());
        assertNull(TestValues.NULL, msg.getHMILevel());
        assertNull(TestValues.NULL, msg.getShowLockScreen());
        assertNull(TestValues.NULL, msg.getUserSelected());
    }
}