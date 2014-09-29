package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.util.DebugTool;

public class Headers extends RPCStruct {

    public Headers() { }
    
    public Headers(Hashtable hash) {
        super(hash);
    }	
	
    public void setContentType(String contenttype) {
        if (contenttype != null) {
            store.put(Names.ContentType, contenttype);
        } else {
        	store.remove(Names.ContentType);
        }
    }
    
    public String getContentType() {
        return (String) store.get(Names.ContentType);
    }     
    
    public void setConnectTimeout(Integer connectiontimeout) {
        if (connectiontimeout != null) {
            store.put(Names.ConnectTimeout, connectiontimeout);
        } else {
        	store.remove(Names.ConnectTimeout);
        }
    }
    
    public Integer getConnectTimeout() {
        return (Integer) store.get(Names.ConnectTimeout);
    }
    
    public void setDoOutput(Boolean dooutput) {
        if (dooutput != null) {
            store.put(Names.DoOutput, dooutput);
        } else {
        	store.remove(Names.DoOutput);
        }
    }
    
    public Boolean getDoOutput() {
        return (Boolean) store.get(Names.DoOutput);
    }    
    
    public void setDoInput(Boolean dooutput) {
        if (dooutput != null) {
            store.put(Names.DoInput, dooutput);
        } else {
        	store.remove(Names.DoInput);
        }
    }
    
    public Boolean getDoInput() {
        return (Boolean) store.get(Names.DoInput);
    }       

    public void setUseCaches(Boolean usescaches) {
        if (usescaches != null) {
            store.put(Names.UseCaches, usescaches);
        } else {
        	store.remove(Names.UseCaches);
        }
    }
    
    public Boolean getUseCaches() {
        return (Boolean) store.get(Names.UseCaches);
    }      
    
    public void setRequestMethod(String requestmethod) {
        if (requestmethod != null) {
            store.put(Names.RequestMethod, requestmethod);
        } else {
        	store.remove(Names.RequestMethod);
        }
    }
    
    public String getRequestMethod() {
        return (String) store.get(Names.RequestMethod);
    }   
    
    
    public void setReadTimeout(Integer readtimeout) {
        if (readtimeout != null) {
            store.put(Names.ReadTimeout, readtimeout);
        } else {
        	store.remove(Names.ReadTimeout);
        }
    }
    
    public Integer getReadTimeout() {
        return (Integer) store.get(Names.ReadTimeout);
    }
    
    public void setInstanceFollowRedirects(Boolean instancefollowredirects) {
        if (instancefollowredirects != null) {
            store.put(Names.InstanceFollowRedirects, instancefollowredirects);
        } else {
        	store.remove(Names.InstanceFollowRedirects);
        }
    }
    
    public Boolean getInstanceFollowRedirects() {
        return (Boolean) store.get(Names.InstanceFollowRedirects);
    }
    
    public void setCharset(String charset) {
        if (charset != null) {
            store.put(Names.charset, charset);
        } else {
        	store.remove(Names.charset);
        }
    }
    
    public String getCharset() {
        return (String) store.get(Names.charset);
    }

    public void setContentLength(Integer contentlength) {
        if (contentlength != null) {
            store.put(Names.ContentLength, contentlength);
        } else {
        	store.remove(Names.ContentLength);
        }
    }
    
    public Integer getContentLength() {
        return (Integer) store.get(Names.ContentLength);
    }    
}
