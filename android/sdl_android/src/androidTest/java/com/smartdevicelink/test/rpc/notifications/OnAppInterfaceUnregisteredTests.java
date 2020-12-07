package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnAppInterfaceUnregistered;
import com.smartdevicelink.proxy.rpc.enums.AppInterfaceUnregisteredReason;
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
 * {@link com.smartdevicelink.proxy.rpc.OnAppInterfaceUnregistered}
 */
public class OnAppInterfaceUnregisteredTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        OnAppInterfaceUnregistered msg = new OnAppInterfaceUnregistered();

        msg.setReason(TestValues.GENERAL_APPINTERFACEUNREGISTEREDREASON);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.ON_APP_INTERFACE_UNREGISTERED.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(OnAppInterfaceUnregistered.KEY_REASON, TestValues.GENERAL_APPINTERFACEUNREGISTEREDREASON);
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    @Test
    public void testRpcValues() {
        // Test Values
        AppInterfaceUnregisteredReason reason = ((OnAppInterfaceUnregistered) msg).getReason();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_APPINTERFACEUNREGISTEREDREASON, reason);

        // Invalid/Null tests
        OnAppInterfaceUnregistered msg = new OnAppInterfaceUnregistered();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getReason());
    }
}