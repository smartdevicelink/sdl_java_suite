package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Headers;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.OnSystemRequest}
 */
public class OnSystemRequestTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        OnSystemRequest msg = new OnSystemRequest();

        msg.setFileType(TestValues.GENERAL_FILETYPE);
        msg.setLength(TestValues.GENERAL_LONG);
        msg.setOffset(TestValues.GENERAL_LONG);
        msg.setRequestType(TestValues.GENERAL_REQUESTTYPE);
        msg.setRequestSubType(TestValues.GENERAL_STRING);
        msg.setTimeout(TestValues.GENERAL_INT);
        msg.setUrl(TestValues.GENERAL_STRING);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.ON_SYSTEM_REQUEST.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(OnSystemRequest.KEY_FILE_TYPE, TestValues.GENERAL_FILETYPE);
            result.put(OnSystemRequest.KEY_LENGTH, TestValues.GENERAL_LONG);
            result.put(OnSystemRequest.KEY_TIMEOUT, TestValues.GENERAL_INT);
            result.put(OnSystemRequest.KEY_OFFSET, TestValues.GENERAL_LONG);
            result.put(OnSystemRequest.KEY_URL, TestValues.GENERAL_STRING);
            result.put(OnSystemRequest.KEY_REQUEST_TYPE, TestValues.GENERAL_REQUESTTYPE);
            result.put(OnSystemRequest.KEY_REQUEST_SUB_TYPE, TestValues.GENERAL_STRING);
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
        FileType fileType = ((OnSystemRequest) msg).getFileType();
        Long length = ((OnSystemRequest) msg).getLength();
        int timeout = ((OnSystemRequest) msg).getTimeout();
        Long offset = ((OnSystemRequest) msg).getOffset();
        String url = ((OnSystemRequest) msg).getUrl();
        RequestType requestType = ((OnSystemRequest) msg).getRequestType();
        String requestSubType = ((OnSystemRequest) msg).getRequestSubType();


        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_FILETYPE, fileType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LONG, length);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, timeout);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LONG, offset);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, url);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_REQUESTTYPE, requestType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, requestSubType);

        // Test Body
        OnSystemRequest osr = (OnSystemRequest) msg;
        String body = osr.getBody();
        assertNull(TestValues.NULL, body);

        String testBody = "123ABC";
        osr.setBody(testBody);

        String readBody = osr.getBody();
        assertEquals(TestValues.MATCH, testBody, readBody);

        // Test Headers     
        Headers headers = osr.getHeader();
        assertNull(TestValues.NULL, headers);

        Headers testHeaders = new Headers();
        testHeaders.setCharset("ASCII");
        testHeaders.setConnectTimeout(1000);
        testHeaders.setContentLength(1024);
        testHeaders.setContentType("application/json");
        testHeaders.setDoInput(false);
        testHeaders.setDoOutput(true);
        testHeaders.setInstanceFollowRedirects(true);
        testHeaders.setReadTimeout(800);
        testHeaders.setRequestMethod("POST");
        testHeaders.setUseCaches(false);
        osr.setHeaders(testHeaders);

        Headers readHeaders = osr.getHeader();
        assertTrue(TestValues.TRUE, Validator.validateHeaders(testHeaders, readHeaders));

        // Invalid/Null Tests
        OnSystemRequest msg = new OnSystemRequest();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getFileType());
        assertNull(TestValues.NULL, msg.getLength());
        assertNull(TestValues.NULL, msg.getOffset());
        assertNull(TestValues.NULL, msg.getTimeout());
        assertNull(TestValues.NULL, msg.getUrl());
        assertNull(TestValues.NULL, msg.getRequestType());
        assertNull(TestValues.NULL, msg.getRequestSubType());
    }

    @Test
    public void testUrlParam() {

        OnSystemRequest msg = new OnSystemRequest();

        StringBuilder longUrl = new StringBuilder("https://test.url");
        while (longUrl.length() < 10000) {
            longUrl.append("/test");
        }

        msg.setUrl(longUrl.toString());

        // test url length has not changed
        assertEquals(TestValues.MATCH, msg.getUrl().length(), longUrl.length());

        // test empty url
        msg.setUrl("");
        assertEquals(TestValues.MATCH, msg.getUrl(), "");

        msg.setUrl(longUrl.substring(0, 1000));
        assertEquals(TestValues.MATCH, msg.getUrl().length(), 1000);
    }
}