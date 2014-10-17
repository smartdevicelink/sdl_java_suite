package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.util.DebugTool;

public class Headers extends RPCStruct {
	public static final String ContentType = "ContentType";
	public static final String ConnectTimeout = "ConnectTimeout";
	public static final String DoOutput = "DoOutput";
	public static final String DoInput  = "DoInput";
	public static final String UseCaches = "UseCaches";
	public static final String RequestMethod = "RequestMethod";
	public static final String ReadTimeout = "ReadTimeout";
	public static final String InstanceFollowRedirects = "InstanceFollowRedirects";
	public static final String charset = "charset";
	public static final String ContentLength = "Content-Length";

    public Headers() { }
    
    public Headers(Hashtable hash) {
        super(hash);
    }	
	
    public void setContentType(String contenttype) {
        if (contenttype != null) {
            store.put(Headers.ContentType, contenttype);
        } else {
        	store.remove(Headers.ContentType);
        }
    }
    
    public String getContentType() {
        return (String) store.get(Headers.ContentType);
    }     
    
    public void setConnectTimeout(Integer connectiontimeout) {
        if (connectiontimeout != null) {
            store.put(Headers.ConnectTimeout, connectiontimeout);
        } else {
        	store.remove(Headers.ConnectTimeout);
        }
    }
    
    public Integer getConnectTimeout() {
        return (Integer) store.get(Headers.ConnectTimeout);
    }
    
    public void setDoOutput(Boolean dooutput) {
        if (dooutput != null) {
            store.put(Headers.DoOutput, dooutput);
        } else {
        	store.remove(Headers.DoOutput);
        }
    }
    
    public Boolean getDoOutput() {
        return (Boolean) store.get(Headers.DoOutput);
    }    
    
    public void setDoInput(Boolean dooutput) {
        if (dooutput != null) {
            store.put(Headers.DoInput, dooutput);
        } else {
        	store.remove(Headers.DoInput);
        }
    }
    
    public Boolean getDoInput() {
        return (Boolean) store.get(Headers.DoInput);
    }       

    public void setUseCaches(Boolean usescaches) {
        if (usescaches != null) {
            store.put(Headers.UseCaches, usescaches);
        } else {
        	store.remove(Headers.UseCaches);
        }
    }
    
    public Boolean getUseCaches() {
        return (Boolean) store.get(Headers.UseCaches);
    }      
    
    public void setRequestMethod(String requestmethod) {
        if (requestmethod != null) {
            store.put(Headers.RequestMethod, requestmethod);
        } else {
        	store.remove(Headers.RequestMethod);
        }
    }
    
    public String getRequestMethod() {
        return (String) store.get(Headers.RequestMethod);
    }   
    
    
    public void setReadTimeout(Integer readtimeout) {
        if (readtimeout != null) {
            store.put(Headers.ReadTimeout, readtimeout);
        } else {
        	store.remove(Headers.ReadTimeout);
        }
    }
    
    public Integer getReadTimeout() {
        return (Integer) store.get(Headers.ReadTimeout);
    }
    
    public void setInstanceFollowRedirects(Boolean instancefollowredirects) {
        if (instancefollowredirects != null) {
            store.put(Headers.InstanceFollowRedirects, instancefollowredirects);
        } else {
        	store.remove(Headers.InstanceFollowRedirects);
        }
    }
    
    public Boolean getInstanceFollowRedirects() {
        return (Boolean) store.get(Headers.InstanceFollowRedirects);
    }
    
    public void setCharset(String charset) {
        if (charset != null) {
            store.put(Headers.charset, charset);
        } else {
        	store.remove(Headers.charset);
        }
    }
    
    public String getCharset() {
        return (String) store.get(Headers.charset);
    }

    public void setContentLength(Integer contentlength) {
        if (contentlength != null) {
            store.put(Headers.ContentLength, contentlength);
        } else {
        	store.remove(Headers.ContentLength);
        }
    }
    
    public Integer getContentLength() {
        return (Integer) store.get(Headers.ContentLength);
    }    
}
