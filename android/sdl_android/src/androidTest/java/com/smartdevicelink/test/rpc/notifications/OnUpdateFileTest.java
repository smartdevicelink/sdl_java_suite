package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnUpdateFile;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.OnUpdateFile}
 */
public class OnUpdateFileTest extends BaseRpcTests {
    @Override
    protected RPCMessage createMessage() {
        OnUpdateFile msg = new OnUpdateFile();

        msg.setFileName(TestValues.GENERAL_STRING);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.ON_UPDATE_FILE.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(OnUpdateFile.KEY_FILE_NAME, TestValues.GENERAL_STRING);
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
        String fileName = ((OnUpdateFile) msg).getFileName();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, fileName);

        // Invalid/Null Tests
        OnUpdateFile msg = new OnUpdateFile();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getFileName());
    }
}
