package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Headers;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class OnSystemRequestTests extends BaseRpcTests{

    private static final int LENGTH = 10;
    private static final int TIMEOUT = 5000;
    private static final int OFFSET = 0;
    private static final String URL = "http://www.livioconnect.com";
    private static final FileType FILE_TYPE = FileType.BINARY;
    private static final RequestType REQUEST_TYPE = RequestType.HTTP;
    
    // TODO: read this from file somewhere
    private static final String HTTP_REQUEST_JSON = "{\"HTTPRequest\":{\"body\":\"123456789abcdef\","+
                                                    "\"headers\":{\"ContentType\":\"application/json\"" +
                                                    ",\"ConnectTimeout\":60,\"DoOutput\":true," +
                                                    "\"DoInput\":true,\"UseCaches\":false,\"RequestMethod\""+
                                                    ":\"POST\",\"ReadTimeout\":60,\"InstanceFollowRedirects\""+
                                                    ":false,\"charset\":\"utf-8\",\"Content-Length\":10743}}}";
    
    @Override
    protected RPCMessage createMessage(){
        OnSystemRequest msg = new OnSystemRequest();

        msg.setFileType(FILE_TYPE);
        msg.setLength(LENGTH);
        msg.setOffset(OFFSET);
        msg.setRequestType(REQUEST_TYPE);
        msg.setTimeout(TIMEOUT);
        msg.setUrl(URL);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_SYSTEM_REQUEST;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnSystemRequest.KEY_FILE_TYPE, FILE_TYPE);
            result.put(OnSystemRequest.KEY_LENGTH, LENGTH);
            result.put(OnSystemRequest.KEY_TIMEOUT, TIMEOUT);
            result.put(OnSystemRequest.KEY_OFFSET, OFFSET);
            result.put(OnSystemRequest.KEY_URL, URL);
            result.put(OnSystemRequest.KEY_REQUEST_TYPE, REQUEST_TYPE);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }
    
    public void testBulkData(){
        JSONObject bulkDataJson = JsonUtils.createJsonObject(HTTP_REQUEST_JSON.getBytes());
        JSONObject httpRequestJson = JsonUtils.readJsonObjectFromJsonObject(bulkDataJson, "HTTPRequest");
        msg.setBulkData(HTTP_REQUEST_JSON.getBytes());
        
        String referenceBody = JsonUtils.readStringFromJsonObject(httpRequestJson, "body");
        JSONObject referenceHeadersJson = JsonUtils.readJsonObjectFromJsonObject(httpRequestJson, "headers");
        Headers referenceHeaders = null;
        try{
            referenceHeaders = new Headers(JsonRPCMarshaller.deserializeJSONObject(referenceHeadersJson));
        }catch(JSONException e){
            e.printStackTrace();
        }
        
        OnSystemRequest underTestMsg = (OnSystemRequest) msg;
        String underTestBody = underTestMsg.getBody();
        Headers underTestHeaders = underTestMsg.getHeader();
        
        assertEquals("Value for \"body\" key didn't match input value.", referenceBody, underTestBody);
        assertTrue("Value for \"headers\" key didn't match input value.",
                Validator.validateHeaders(referenceHeaders, underTestHeaders));
        
        msg.setBulkData(null);
    }

    public void testFileType(){
        FileType data = ( (OnSystemRequest) msg ).getFileType();
        assertEquals("Data didn't match input data.", FILE_TYPE, data);
    }

    public void testLength(){
        int data = ( (OnSystemRequest) msg ).getLength();
        assertEquals("Data didn't match input data.", LENGTH, data);
    }

    public void testTimeout(){
        int data = ( (OnSystemRequest) msg ).getTimeout();
        assertEquals("Data didn't match input data.", TIMEOUT, data);
    }

    public void testOffset(){
        int data = ( (OnSystemRequest) msg ).getOffset();
        assertEquals("Data didn't match input data.", OFFSET, data);
    }

    public void testUrl(){
        String data = ( (OnSystemRequest) msg ).getUrl();
        assertEquals("Data didn't match input data.", URL, data);
    }

    public void testRequestType(){
        RequestType data = ( (OnSystemRequest) msg ).getRequestType();
        assertEquals("Data didn't match input data.", REQUEST_TYPE, data);
    }
    
    public void testBody(){
        OnSystemRequest osr = (OnSystemRequest) msg;
        String body = osr.getBody();
        assertNull("Item was not null.", body);
        
        String testBody = "123ABC";
        
        osr.setBody(testBody);
        
        String readBody = osr.getBody();
        assertEquals("Output value didn't match expected value.", testBody, readBody);
    }
    
    public void testHeaders(){
        OnSystemRequest osr = (OnSystemRequest) msg;
        
        Headers headers = osr.getHeader();
        assertNull("Item was not null.", headers);
        
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
        assertTrue("Output value didn't match expected value.", Validator.validateHeaders(testHeaders, readHeaders));
    }

    public void testNull(){
        OnSystemRequest msg = new OnSystemRequest();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("File type wasn't set, but getter method returned an object.", msg.getFileType());
        assertNull("Length wasn't set, but getter method returned an object.", msg.getLength());
        assertNull("Offset wasn't set, but getter method returned an object.", msg.getOffset());
        assertNull("Timeout wasn't set, but getter method returned an object.", msg.getTimeout());
        assertNull("URL wasn't set, but getter method returned an object.", msg.getUrl());
        assertNull("Request type wasn't set, but getter method returned an object.", msg.getRequestType());
    }
}
