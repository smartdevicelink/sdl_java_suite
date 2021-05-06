package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AppCapability;
import com.smartdevicelink.proxy.rpc.OnAppCapabilityUpdated;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

public class OnAppCapabilityUpdatedTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){
        OnAppCapabilityUpdated msg = new OnAppCapabilityUpdated();
        msg.setAppCapability(TestValues.GENERAL_APPCAPABILITY);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_APP_CAPABILITY_UPDATED.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnAppCapabilityUpdated.KEY_APP_CAPABILITY, TestValues.GENERAL_APPCAPABILITY.serializeJSON());
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
        AppCapability appCapability = ( (OnAppCapabilityUpdated) msg ).getAppCapability();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_APPCAPABILITY, appCapability);

        // Invalid/Null Tests
        OnAppCapabilityUpdated msg = new OnAppCapabilityUpdated();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getAppCapability());

        // test constructor with param
        msg = new OnAppCapabilityUpdated(TestValues.GENERAL_APPCAPABILITY);
        AppCapability generalAppCapability = msg.getAppCapability();
        assertEquals(generalAppCapability, TestValues.GENERAL_APPCAPABILITY);
    }
}
