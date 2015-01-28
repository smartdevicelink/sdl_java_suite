package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

public class Headers extends RPCObject {
	public static final String KEY_CONTENT_TYPE = "ContentType";
	public static final String KEY_CONNECT_TIMEOUT = "ConnectTimeout";
	public static final String KEY_DO_OUTPUT = "DoOutput";
	public static final String KEY_DO_INPUT  = "DoInput";
	public static final String KEY_USE_CACHES = "UseCaches";
	public static final String KEY_REQUEST_METHOD = "RequestMethod";
	public static final String KEY_READ_TIMEOUT = "ReadTimeout";
	public static final String KEY_INSTANCE_FOLLOW_REDIRECTS = "InstanceFollowRedirects";
	public static final String KEY_CHARSET = "charset";
	public static final String KEY_CONTENT_LENGTH = "Content-Length";

	private String contentType, charset, requestMethod;
	private Integer connectTimeout, readTimeout, contentLength;
	private Boolean doOutput, doInput, useCaches, instanceFollowRedirects;
	
    public Headers() { }
    
    /**
     * Creates a Headers object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public Headers(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.contentType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_CONTENT_TYPE);
            this.charset = JsonUtils.readStringFromJsonObject(jsonObject, KEY_CHARSET);
            this.requestMethod = JsonUtils.readStringFromJsonObject(jsonObject, KEY_REQUEST_METHOD);
            this.connectTimeout = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_CONNECT_TIMEOUT);
            this.readTimeout = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_READ_TIMEOUT);
            this.contentLength = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_CONTENT_LENGTH);
            this.doOutput = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_DO_OUTPUT);
            this.doInput = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_DO_INPUT);
            this.useCaches = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_USE_CACHES);
            this.instanceFollowRedirects = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_INSTANCE_FOLLOW_REDIRECTS);
            break;
        }
    }	
	
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public String getContentType() {
        return this.contentType;
    }     
    
    public void setConnectTimeout(Integer connectionTimeout) {
        this.connectTimeout = connectionTimeout;
    }
    
    public Integer getConnectTimeout() {
        return this.connectTimeout;
    }
    
    public void setDoOutput(Boolean doOutput) {
        this.doOutput = doOutput;
    }
    
    public Boolean getDoOutput() {
        return this.doOutput;
    }    
    
    public void setDoInput(Boolean doInput) {
        this.doInput = doInput;
    }
    
    public Boolean getDoInput() {
        return this.doInput;
    }       

    public void setUseCaches(Boolean usesCaches) {
        this.useCaches = usesCaches;
    }
    
    public Boolean getUseCaches() {
        return this.useCaches;
    }      
    
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
    
    public String getRequestMethod() {
        return this.requestMethod;
    }   
    
    
    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }
    
    public Integer getReadTimeout() {
        return this.readTimeout;
    }
    
    public void setInstanceFollowRedirects(Boolean instanceFollowRedirects) {
        this.instanceFollowRedirects = instanceFollowRedirects;
    }
    
    public Boolean getInstanceFollowRedirects() {
        return this.instanceFollowRedirects;
    }
    
    public void setCharset(String charset) {
        this.charset = charset;
    }
    
    public String getCharset() {
        return this.charset;
    }

    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
    }
    
    public Integer getContentLength() {
        return this.contentLength;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_CHARSET, this.charset);
            JsonUtils.addToJsonObject(result, KEY_CONNECT_TIMEOUT, this.connectTimeout);
            JsonUtils.addToJsonObject(result, KEY_CONTENT_LENGTH, this.contentLength);
            JsonUtils.addToJsonObject(result, KEY_CONTENT_TYPE, this.contentType);
            JsonUtils.addToJsonObject(result, KEY_DO_INPUT, this.doInput);
            JsonUtils.addToJsonObject(result, KEY_DO_OUTPUT, this.doOutput);
            JsonUtils.addToJsonObject(result, KEY_INSTANCE_FOLLOW_REDIRECTS, this.instanceFollowRedirects);
            JsonUtils.addToJsonObject(result, KEY_READ_TIMEOUT, this.readTimeout);
            JsonUtils.addToJsonObject(result, KEY_REQUEST_METHOD, this.requestMethod);
            JsonUtils.addToJsonObject(result, KEY_USE_CACHES, this.useCaches);
            break;
        }
        
        return result;
    }
}
