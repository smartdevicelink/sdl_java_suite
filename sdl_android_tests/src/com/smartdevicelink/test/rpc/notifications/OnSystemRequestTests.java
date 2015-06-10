package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Headers;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnSystemRequest}
 */
public class OnSystemRequestTests extends BaseRpcTests{
    
    @Override
    protected RPCMessage createMessage(){
        OnSystemRequest msg = new OnSystemRequest();

        msg.setFileType(Test.GENERAL_FILETYPE);
        msg.setLength(Test.GENERAL_LONG);
        msg.setOffset(Test.GENERAL_LONG);
        msg.setRequestType(Test.GENERAL_REQUESTTYPE);
        msg.setTimeout(Test.GENERAL_INT);
        msg.setUrl(Test.GENERAL_STRING);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_SYSTEM_REQUEST.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnSystemRequest.KEY_FILE_TYPE, Test.GENERAL_FILETYPE);
            result.put(OnSystemRequest.KEY_LENGTH, Test.GENERAL_LONG);
            result.put(OnSystemRequest.KEY_TIMEOUT, Test.GENERAL_INT);
            result.put(OnSystemRequest.KEY_OFFSET, Test.GENERAL_LONG);
            result.put(OnSystemRequest.KEY_URL, Test.GENERAL_STRING);
            result.put(OnSystemRequest.KEY_REQUEST_TYPE, Test.GENERAL_REQUESTTYPE);
        } catch(JSONException e) {
        	fail(Test.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {       	
    	// Test Values
        FileType fileType = ( (OnSystemRequest) msg ).getFileType();
        Long length = ( (OnSystemRequest) msg ).getLength();
        int timeout = ( (OnSystemRequest) msg ).getTimeout();
        Long offset = ( (OnSystemRequest) msg ).getOffset();
        String url = ( (OnSystemRequest) msg ).getUrl();
        RequestType requestType = ( (OnSystemRequest) msg ).getRequestType();
        
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_FILETYPE, fileType);
        assertEquals(Test.MATCH, Test.GENERAL_LONG, length);
        assertEquals(Test.MATCH, Test.GENERAL_INT, timeout);
        assertEquals(Test.MATCH, Test.GENERAL_LONG, offset);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, url);
        assertEquals(Test.MATCH, Test.GENERAL_REQUESTTYPE, requestType);
        
        // Test Body
        OnSystemRequest osr = (OnSystemRequest) msg;
        String body = osr.getBody();
        assertNull(Test.NULL, body);
        
        String testBody = "123ABC";        
        osr.setBody(testBody);
        
        String readBody = osr.getBody();
        assertEquals(Test.MATCH, testBody, readBody);
    
        // Test Headers     
        Headers headers = osr.getHeader();
        assertNull(Test.NULL, headers);
        
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
        assertTrue(Test.TRUE, Validator.validateHeaders(testHeaders, readHeaders));
    
        // Invalid/Null Tests
        OnSystemRequest msg = new OnSystemRequest();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getFileType());
        assertNull(Test.NULL, msg.getLength());
        assertNull(Test.NULL, msg.getOffset());
        assertNull(Test.NULL, msg.getTimeout());
        assertNull(Test.NULL, msg.getUrl());
        assertNull(Test.NULL, msg.getRequestType());
    }
}