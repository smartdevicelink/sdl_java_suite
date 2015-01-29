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

public class OnLockScreenStatusTests extends BaseRpcTests{

    private static final boolean DRIVER_DISTRACTED = false;
    private static final LockScreenStatus SHOW_LOCK_SCREEN  = LockScreenStatus.REQUIRED;
    private static final boolean USER_SELECTED     = true;
    private static final HMILevel  HMI_LEVEL         = HMILevel.HMI_BACKGROUND;

    @Override
    protected RPCMessage createMessage(){
        OnLockScreenStatus msg = new OnLockScreenStatus();

        msg.setDriverDistractionStatus(DRIVER_DISTRACTED);
        msg.setHMILevel(HMI_LEVEL);
        msg.setShowLockScreen(SHOW_LOCK_SCREEN);
        msg.setUserSelected(USER_SELECTED);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_LOCK_SCREEN_STATUS;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnLockScreenStatus.KEY_DRIVER_DISTRACTION, Boolean.valueOf(DRIVER_DISTRACTED)
                    .toString());
            //TODO: is OnHMIStatus correct class for this variable?
            result.put(OnHMIStatus.KEY_HMI_LEVEL, HMI_LEVEL);
            result.put(OnLockScreenStatus.KEY_SHOW_LOCK_SCREEN, SHOW_LOCK_SCREEN);
            result.put(OnLockScreenStatus.KEY_USER_SELECTED, USER_SELECTED);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testDriverDistractionStatus(){
        Boolean data = ( (OnLockScreenStatus) msg ).getDriverDistractionStatus();
        assertEquals("Data didn't match input data.", Boolean.valueOf(DRIVER_DISTRACTED), data);
    }

    public void testHMILevel(){
    	HMILevel data = ( (OnLockScreenStatus) msg ).getHMILevel();
        assertEquals("Data didn't match input data.", HMI_LEVEL, data);
    }

    public void testShowLockScreen(){
        LockScreenStatus data = ( (OnLockScreenStatus) msg ).getShowLockScreen();
        assertEquals("Data didn't match input data.", SHOW_LOCK_SCREEN, data);
    }

    public void testUserSelected(){
        boolean data = ( (OnLockScreenStatus) msg ).getUserSelected();
        assertEquals("Data didn't match input data.", USER_SELECTED, data);
    }

    public void testNull(){
        OnLockScreenStatus msg = new OnLockScreenStatus();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Driver distraction status wasn't set, but getter method returned an object.",
                msg.getDriverDistractionStatus());
        assertNull("HMI level wasn't set, but getter method returned an object.", msg.getHMILevel());
        assertNull("Show lock screen wasn't set, but getter method returned an object.", msg.getShowLockScreen());
        assertNull("User selected wasn't set, but getter method returned an object.", msg.getUserSelected());
    }
}
