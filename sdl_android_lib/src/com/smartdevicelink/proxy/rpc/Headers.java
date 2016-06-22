package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
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
        if (contenttype != null) {
            store.put(KEY_CONTENT_TYPE, contenttype);
        } else {
        	store.remove(KEY_CONTENT_TYPE);
        }
    }
    
    public String getContentType() {
        return (String) store.get(KEY_CONTENT_TYPE);
    }     
    
    public void setConnectTimeout(Integer connectiontimeout) {
        if (connectiontimeout != null) {
            store.put(KEY_CONNECT_TIMEOUT, connectiontimeout);
        } else {
        	store.remove(KEY_CONNECT_TIMEOUT);
        }
    }
    
    public Integer getConnectTimeout() {
        return (Integer) store.get(KEY_CONNECT_TIMEOUT);
    }
    
    public void setDoOutput(Boolean dooutput) {
        if (dooutput != null) {
            store.put(KEY_DO_OUTPUT, dooutput);
        } else {
        	store.remove(KEY_DO_OUTPUT);
        }
    }
    
    public Boolean getDoOutput() {
        return (Boolean) store.get(KEY_DO_OUTPUT);
    }    
    
    public void setDoInput(Boolean dooutput) {
        if (dooutput != null) {
            store.put(KEY_DO_INPUT, dooutput);
        } else {
        	store.remove(KEY_DO_INPUT);
        }
    }
    
    public Boolean getDoInput() {
        return (Boolean) store.get(KEY_DO_INPUT);
    }       

    public void setUseCaches(Boolean usescaches) {
        if (usescaches != null) {
            store.put(KEY_USE_CACHES, usescaches);
        } else {
        	store.remove(KEY_USE_CACHES);
        }
    }
    
    public Boolean getUseCaches() {
        return (Boolean) store.get(KEY_USE_CACHES);
    }      
    
    public void setRequestMethod(String requestmethod) {
        if (requestmethod != null) {
            store.put(KEY_REQUEST_METHOD, requestmethod);
        } else {
        	store.remove(KEY_REQUEST_METHOD);
        }
    }
    
    public String getRequestMethod() {
        return (String) store.get(KEY_REQUEST_METHOD);
    }   
    
    
    public void setReadTimeout(Integer readtimeout) {
        if (readtimeout != null) {
            store.put(KEY_READ_TIMEOUT, readtimeout);
        } else {
        	store.remove(KEY_READ_TIMEOUT);
        }
    }
    
    public Integer getReadTimeout() {
        return (Integer) store.get(KEY_READ_TIMEOUT);
    }
    
    public void setInstanceFollowRedirects(Boolean instancefollowredirects) {
        if (instancefollowredirects != null) {
            store.put(KEY_INSTANCE_FOLLOW_REDIRECTS, instancefollowredirects);
        } else {
        	store.remove(KEY_INSTANCE_FOLLOW_REDIRECTS);
        }
    }
    
    public Boolean getInstanceFollowRedirects() {
        return (Boolean) store.get(KEY_INSTANCE_FOLLOW_REDIRECTS);
    }
    
    public void setCharset(String charset) {
        if (charset != null) {
            store.put(KEY_CHARSET, charset);
        } else {
        	store.remove(KEY_CHARSET);
        }
    }
    
    public String getCharset() {
        return (String) store.get(KEY_CHARSET);
    }

    public void setContentLength(Integer contentlength) {
        if (contentlength != null) {
            store.put(KEY_CONTENT_LENGTH, contentlength);
        } else {
        	store.remove(KEY_CONTENT_LENGTH);
        }
    }
    
    public Integer getContentLength() {
        return (Integer) store.get(KEY_CONTENT_LENGTH);
    }    
}
