package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnSystemCapabilityUpdated;
import com.smartdevicelink.proxy.rpc.SystemCapability;
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
 * {@link com.smartdevicelink.proxy.rpc.OnSystemCapabilityUpdated}
 */
public class OnSystemCapabilityUpdatedTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        OnSystemCapabilityUpdated msg = new OnSystemCapabilityUpdated();

        msg.setSystemCapability(TestValues.GENERAL_SYSTEMCAPABILITY);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.ON_SYSTEM_CAPABILITY_UPDATED.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(OnSystemCapabilityUpdated.KEY_SYSTEM_CAPABILITY, TestValues.GENERAL_SYSTEMCAPABILITY.serializeJSON());
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
        SystemCapability cmdId = ((OnSystemCapabilityUpdated) msg).getSystemCapability();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SYSTEMCAPABILITY, cmdId);

        // Invalid/Null Tests
        OnSystemCapabilityUpdated msg = new OnSystemCapabilityUpdated();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getSystemCapability());

        // test constructor with param
        msg = new OnSystemCapabilityUpdated(TestValues.GENERAL_SYSTEMCAPABILITY);
        SystemCapability systemCapability = msg.getSystemCapability();
        assertEquals(systemCapability, TestValues.GENERAL_SYSTEMCAPABILITY);
    }
}
