package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnSeekMediaClockTimer;
import com.smartdevicelink.proxy.rpc.StartTime;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

import org.json.JSONException;
import org.json.JSONObject;


public class OnSeekMediaClockTimerTests extends BaseRpcTests {
    @Override
    protected RPCMessage createMessage(){
        OnSeekMediaClockTimer msg = new OnSeekMediaClockTimer();
        msg.setSeekTime(Test.GENERAL_STARTTIME);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_SEEK_MEDIA_CLOCK_TIMER.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();
        try {
            result.put(OnSeekMediaClockTimer.KEY_SEEK_TIME, Test.JSON_STARTTIME);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        StartTime seekTime = ((OnSeekMediaClockTimer) msg).getSeekTime();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STARTTIME, seekTime);

        // Invalid/Null Tests
        OnSeekMediaClockTimer msg = new OnSeekMediaClockTimer();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getSeekTime());
    }
}
