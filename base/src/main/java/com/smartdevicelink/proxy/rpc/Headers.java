package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
/**
 * 
 * @since SmartDeviceLink 3.0
 *
 */

public class Headers extends RPCStruct {
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

    public Headers() { }
    /**
	* <p>
	* Constructs a new Headers object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/   

    public Headers(Hashtable<String, Object> hash) {
        super(hash);
    }	
	
    public void setContentType(String contenttype) {
        setValue(KEY_CONTENT_TYPE, contenttype);
    }
    
    public String getContentType() {
        return getString(KEY_CONTENT_TYPE);
    }     
    
    public void setConnectTimeout(Integer connectiontimeout) {
        setValue(KEY_CONNECT_TIMEOUT, connectiontimeout);
    }
    
    public Integer getConnectTimeout() {
        return getInteger(KEY_CONNECT_TIMEOUT);
    }
    
    public void setDoOutput(Boolean dooutput) {
        setValue(KEY_DO_OUTPUT, dooutput);
    }
    
    public Boolean getDoOutput() {
        return getBoolean(KEY_DO_OUTPUT);
    }    
    
    public void setDoInput(Boolean doinput) {
        setValue(KEY_DO_INPUT, doinput);
    }
    
    public Boolean getDoInput() {
        return getBoolean(KEY_DO_INPUT);
    }       

    public void setUseCaches(Boolean usescaches) {
        setValue(KEY_USE_CACHES, usescaches);
    }
    
    public Boolean getUseCaches() {
        return getBoolean(KEY_USE_CACHES);
    }      
    
    public void setRequestMethod(String requestmethod) {
        setValue(KEY_REQUEST_METHOD, requestmethod);
    }
    
    public String getRequestMethod() {
        return getString(KEY_REQUEST_METHOD);
    }   
    
    
    public void setReadTimeout(Integer readtimeout) {
        setValue(KEY_READ_TIMEOUT, readtimeout);
    }
    
    public Integer getReadTimeout() {
        return getInteger(KEY_READ_TIMEOUT);
    }
    
    public void setInstanceFollowRedirects(Boolean instancefollowredirects) {
        setValue(KEY_INSTANCE_FOLLOW_REDIRECTS, instancefollowredirects);
    }
    
    public Boolean getInstanceFollowRedirects() {
        return getBoolean(KEY_INSTANCE_FOLLOW_REDIRECTS);
    }
    
    public void setCharset(String charset) {
        setValue(KEY_CHARSET, charset);
    }
    
    public String getCharset() {
        return getString(KEY_CHARSET);
    }

    public void setContentLength(Integer contentlength) {
        setValue(KEY_CONTENT_LENGTH, contentlength);
    }
    
    public Integer getContentLength() {
        return getInteger(KEY_CONTENT_LENGTH);
    }    
}
