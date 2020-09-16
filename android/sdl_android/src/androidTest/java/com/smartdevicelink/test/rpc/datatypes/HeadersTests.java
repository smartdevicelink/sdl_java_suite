package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.Headers;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.Headers}
 */
public class HeadersTests extends TestCase{

    private Headers msg;

    @Override
    public void setUp(){
        msg = new Headers();

        msg.setCharset(TestValues.GENERAL_STRING);
        msg.setConnectTimeout(TestValues.GENERAL_INT);
        msg.setContentLength(TestValues.GENERAL_INT);
        msg.setContentType(TestValues.GENERAL_STRING);
        msg.setDoInput(TestValues.GENERAL_BOOLEAN);
        msg.setDoOutput(TestValues.GENERAL_BOOLEAN);
        msg.setInstanceFollowRedirects(TestValues.GENERAL_BOOLEAN);
        msg.setReadTimeout(TestValues.GENERAL_INT);
        msg.setRequestMethod(TestValues.GENERAL_STRING);
        msg.setUseCaches(TestValues.GENERAL_BOOLEAN);
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
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, charset);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, contentType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, requestMode);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, connectTimeout);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, readTimeout);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, contentLength);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, doOutput);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, doInput);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, useCache);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, instanceFollow);
        
        // Invalid/Null Tests
        Headers msg = new Headers();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getCharset());
        assertNull(TestValues.NULL, msg.getConnectTimeout());
        assertNull(TestValues.NULL, msg.getContentLength());
        assertNull(TestValues.NULL, msg.getContentType());
        assertNull(TestValues.NULL, msg.getDoInput());
        assertNull(TestValues.NULL, msg.getDoOutput());
        assertNull(TestValues.NULL, msg.getInstanceFollowRedirects());
        assertNull(TestValues.NULL, msg.getReadTimeout());
        assertNull(TestValues.NULL, msg.getRequestMethod());
        assertNull(TestValues.NULL, msg.getUseCaches());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Headers.KEY_CHARSET, TestValues.GENERAL_STRING);
            reference.put(Headers.KEY_CONTENT_TYPE, TestValues.GENERAL_STRING);
            reference.put(Headers.KEY_REQUEST_METHOD, TestValues.GENERAL_STRING);
            reference.put(Headers.KEY_CONNECT_TIMEOUT, TestValues.GENERAL_INT);
            reference.put(Headers.KEY_READ_TIMEOUT, TestValues.GENERAL_INT);
            reference.put(Headers.KEY_CONTENT_LENGTH, TestValues.GENERAL_INT);
            reference.put(Headers.KEY_DO_OUTPUT, TestValues.GENERAL_BOOLEAN);
            reference.put(Headers.KEY_DO_INPUT, TestValues.GENERAL_BOOLEAN);
            reference.put(Headers.KEY_USE_CACHES, TestValues.GENERAL_BOOLEAN);
            reference.put(Headers.KEY_INSTANCE_FOLLOW_REDIRECTS, TestValues.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}