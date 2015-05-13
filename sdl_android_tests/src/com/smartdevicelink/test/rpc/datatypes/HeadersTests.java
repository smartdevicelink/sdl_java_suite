package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.Headers;
import com.smartdevicelink.test.JsonUtils;

public class HeadersTests extends TestCase{

    private static final String  CONTENT_TYPE              = "text/html";
    private static final String  REQUEST_METHOD            = "GET";
    private static final String  CHARSET                   = "ISO-8859-4";
    private static final int     CONNECTION_TIMEOUT        = 200;
    private static final int     READ_TIMEOUT              = 100;
    private static final int     CONTENT_LENGTH            = 5029;
    private static final boolean DO_OUTPUT                 = true;
    private static final boolean DO_INPUT                  = false;
    private static final boolean USES_CACHES               = false;
    private static final boolean INSTANCE_FOLLOW_REDIRECTS = true;

    private Headers              msg;

    @Override
    public void setUp(){
        msg = new Headers();

        msg.setCharset(CHARSET);
        msg.setConnectTimeout(CONNECTION_TIMEOUT);
        msg.setContentLength(CONTENT_LENGTH);
        msg.setContentType(CONTENT_TYPE);
        msg.setDoInput(DO_INPUT);
        msg.setDoOutput(DO_OUTPUT);
        msg.setInstanceFollowRedirects(INSTANCE_FOLLOW_REDIRECTS);
        msg.setReadTimeout(READ_TIMEOUT);
        msg.setRequestMethod(REQUEST_METHOD);
        msg.setUseCaches(USES_CACHES);
    }

    public void testCharset(){
        String copy = msg.getCharset();
        assertEquals("Input value didn't match expected value.", CHARSET, copy);
    }

    public void testContentType(){
        String copy = msg.getContentType();
        assertEquals("Input value didn't match expected value.", CONTENT_TYPE, copy);
    }

    public void testRequestMethod(){
        String copy = msg.getRequestMethod();
        assertEquals("Input value didn't match expected value.", REQUEST_METHOD, copy);
    }

    public void testConnectTimeout(){
        int copy = msg.getConnectTimeout();
        assertEquals("Input value didn't match expected value.", CONNECTION_TIMEOUT, copy);
    }

    public void testReadTimeout(){
        int copy = msg.getReadTimeout();
        assertEquals("Input value didn't match expected value.", READ_TIMEOUT, copy);
    }

    public void testContentLength(){
        int copy = msg.getContentLength();
        assertEquals("Input value didn't match expected value.", CONTENT_LENGTH, copy);
    }

    public void testDoOutput(){
        boolean copy = msg.getDoOutput();
        assertEquals("Input value didn't match expected value.", DO_OUTPUT, copy);
    }

    public void testDoInput(){
        boolean copy = msg.getDoInput();
        assertEquals("Input value didn't match expected value.", DO_INPUT, copy);
    }

    public void testUseCache(){
        boolean copy = msg.getUseCaches();
        assertEquals("Input value didn't match expected value.", USES_CACHES, copy);
    }

    public void testInstanceFollowRedirects(){
        boolean copy = msg.getInstanceFollowRedirects();
        assertEquals("Input value didn't match expected value.", INSTANCE_FOLLOW_REDIRECTS, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Headers.KEY_CHARSET, CHARSET);
            reference.put(Headers.KEY_CONTENT_TYPE, CONTENT_TYPE);
            reference.put(Headers.KEY_REQUEST_METHOD, REQUEST_METHOD);
            reference.put(Headers.KEY_CONNECT_TIMEOUT, CONNECTION_TIMEOUT);
            reference.put(Headers.KEY_READ_TIMEOUT, READ_TIMEOUT);
            reference.put(Headers.KEY_CONTENT_LENGTH, CONTENT_LENGTH);
            reference.put(Headers.KEY_DO_OUTPUT, DO_OUTPUT);
            reference.put(Headers.KEY_DO_INPUT, DO_INPUT);
            reference.put(Headers.KEY_USE_CACHES, USES_CACHES);
            reference.put(Headers.KEY_INSTANCE_FOLLOW_REDIRECTS, INSTANCE_FOLLOW_REDIRECTS);

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
                        JsonUtils.readObjectFromJsonObject(reference, key),
                        JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        Headers msg = new Headers();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Charset wasn't set, but getter method returned an object.", msg.getCharset());
        assertNull("Connection timeout wasn't set, but getter method returned an object.", msg.getConnectTimeout());
        assertNull("Content length wasn't set, but getter method returned an object.", msg.getContentLength());
        assertNull("Content type wasn't set, but getter method returned an object.", msg.getContentType());
        assertNull("Do input wasn't set, but getter method returned an object.", msg.getDoInput());
        assertNull("Do output wasn't set, but getter method returned an object.", msg.getDoOutput());
        assertNull("Follow wasn't set, but getter method returned an object.", msg.getInstanceFollowRedirects());
        assertNull("Read timeout wasn't set, but getter method returned an object.", msg.getReadTimeout());
        assertNull("Request method wasn't set, but getter method returned an object.", msg.getRequestMethod());
        assertNull("Uses caches wasn't set, but getter method returned an object.", msg.getUseCaches());
    }
}
