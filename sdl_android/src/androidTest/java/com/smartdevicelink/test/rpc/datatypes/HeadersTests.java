package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.Headers;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.Headers}
 */
public class HeadersTests extends TestCase{

    private Headers msg;

    @Override
    public void setUp(){
        msg = new Headers();

        msg.setCharset(Test.GENERAL_STRING);
        msg.setConnectTimeout(Test.GENERAL_INT);
        msg.setContentLength(Test.GENERAL_INT);
        msg.setContentType(Test.GENERAL_STRING);
        msg.setDoInput(Test.GENERAL_BOOLEAN);
        msg.setDoOutput(Test.GENERAL_BOOLEAN);
        msg.setInstanceFollowRedirects(Test.GENERAL_BOOLEAN);
        msg.setReadTimeout(Test.GENERAL_INT);
        msg.setRequestMethod(Test.GENERAL_STRING);
        msg.setUseCaches(Test.GENERAL_BOOLEAN);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        String charset = msg.getCharset();
        String contentType = msg.getContentType();
        String requestMode = msg.getRequestMethod();
        int connectTimeout = msg.getConnectTimeout();
        int readTimeout = msg.getReadTimeout();
        int contentLength = msg.getContentLength();
        boolean doOutput = msg.getDoOutput();
        boolean doInput = msg.getDoInput();
        boolean useCache = msg.getUseCaches();
        boolean instanceFollow = msg.getInstanceFollowRedirects();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING, charset);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, contentType);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, requestMode);
        assertEquals(Test.MATCH, Test.GENERAL_INT, connectTimeout);
        assertEquals(Test.MATCH, Test.GENERAL_INT, readTimeout);
        assertEquals(Test.MATCH, Test.GENERAL_INT, contentLength);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, doOutput);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, doInput);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, useCache);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, instanceFollow);
        
        // Invalid/Null Tests
        Headers msg = new Headers();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getCharset());
        assertNull(Test.NULL, msg.getConnectTimeout());
        assertNull(Test.NULL, msg.getContentLength());
        assertNull(Test.NULL, msg.getContentType());
        assertNull(Test.NULL, msg.getDoInput());
        assertNull(Test.NULL, msg.getDoOutput());
        assertNull(Test.NULL, msg.getInstanceFollowRedirects());
        assertNull(Test.NULL, msg.getReadTimeout());
        assertNull(Test.NULL, msg.getRequestMethod());
        assertNull(Test.NULL, msg.getUseCaches());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Headers.KEY_CHARSET, Test.GENERAL_STRING);
            reference.put(Headers.KEY_CONTENT_TYPE, Test.GENERAL_STRING);
            reference.put(Headers.KEY_REQUEST_METHOD, Test.GENERAL_STRING);
            reference.put(Headers.KEY_CONNECT_TIMEOUT, Test.GENERAL_INT);
            reference.put(Headers.KEY_READ_TIMEOUT, Test.GENERAL_INT);
            reference.put(Headers.KEY_CONTENT_LENGTH, Test.GENERAL_INT);
            reference.put(Headers.KEY_DO_OUTPUT, Test.GENERAL_BOOLEAN);
            reference.put(Headers.KEY_DO_INPUT, Test.GENERAL_BOOLEAN);
            reference.put(Headers.KEY_USE_CACHES, Test.GENERAL_BOOLEAN);
            reference.put(Headers.KEY_INSTANCE_FOLLOW_REDIRECTS, Test.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}