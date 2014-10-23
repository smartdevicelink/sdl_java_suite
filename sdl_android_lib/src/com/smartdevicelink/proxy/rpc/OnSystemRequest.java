package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.util.DebugTool;

import java.util.Hashtable;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

public class OnSystemRequest extends RPCNotification {
	
	Hashtable<String, Object> httpreqparams = null;
	JSONObject myJSONObj = null;
	
    public OnSystemRequest() {
        super("OnSystemRequest");
    }

    @SuppressWarnings("unchecked")
    public OnSystemRequest(Hashtable<String, Object> hash) {
        super(hash);
        
        //testing
        //String sJson = "{\"HTTPRequest\":{\"headers\":{\"ContentType\":\"application/json\",\"ConnectTimeout\":60,\"DoOutput\":true,\"DoInput\":true,\"UseCaches\":false,\"RequestMethod\":\"POST\",\"ReadTimeout\":60,\"InstanceFollowRedirects\":false,\"charset\":\"utf-8\",\"Content-Length\":10743},\"body\":\"{\\\"data\\\":[\\\"HQcYAAAp+Ul19L\\\"]}\"}}";
		try {			
			byte[] bulkData = (byte[]) hash.get(Names.bulkData);
			
			if (bulkData == null) return;
			
			String jsonString = new String(bulkData);

			myJSONObj = new JSONObject(jsonString);
			Hashtable<String, Object> temp = JsonRPCMarshaller.deserializeJSONObject(myJSONObj);			
			httpreqparams = (Hashtable<String, Object>) temp.get("HTTPRequest");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}            
    }
    
    public void setBinData(byte[] aptData) {
        if (aptData != null) {
            store.put(Names.bulkData, aptData);
        } else {
        	store.remove(Names.bulkData);
        }
    }
    public byte[] getBinData() {
        return (byte[]) store.get(Names.bulkData);
    }
    
    
    @SuppressWarnings("unchecked")
    public Vector<String> getLegacyData() {
    	if (parameters.get(Names.data) instanceof Vector<?>) {
    		Vector<?> list = (Vector<?>)parameters.get(Names.data);
    		if (list != null && list.size()>0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
        			return (Vector<String>)list;
        		}
    		}
    	}
        return null;
    }

    public String getBody(){            	
        
    	JSONObject jLayer1 = null;
        String sReturn = null;        
        try
        {
        	if (httpreqparams != null)
        	{
	        	jLayer1 = myJSONObj.getJSONObject("HTTPRequest");        	
	        	sReturn = jLayer1.getString("body");
	        	return sReturn;
        	}
        	else if (myJSONObj != null)
        	{
        		//jLayer1 =  new JSONObject();
        		//jLayer1.put("data", myJSONObj);
        		return myJSONObj.toString();
        	}
        	else
        	{
        		return null;
        	}
        	
        }
		catch (Exception e) 
		{
			return null;        	
        }
    }
    
    public void setBody(String body) {
        if (body != null) {
            parameters.put(Names.body, body);
        } else {
            parameters.remove(Names.body);
        }
    }
    
    
    public void setHeaders(Headers header) {
        if (header != null) {
        	httpreqparams.put(Names.headers, header);
        } else {
        	httpreqparams.remove(Names.headers);
        }
    }
 
    @SuppressWarnings("unchecked")
    public Headers getHeader() {
    	if (httpreqparams == null) return null;
    	
    	Object obj = httpreqparams.get(Names.headers);
    	if (obj == null) return null;
        if (obj instanceof Headers) {
            return (Headers) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Headers((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.headers, e);
            }
        }
        return null;
    }
    
    
    
    public RequestType getRequestType() {
        Object obj = parameters.get(Names.requestType);
        if (obj == null) return null;
        if (obj instanceof RequestType) {
            return (RequestType) obj;
        } else if (obj instanceof String) {
            RequestType theCode = null;
            try {
                theCode = RequestType.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError(
                        "Failed to parse " + getClass().getSimpleName() + "." +
                        		Names.requestType, e);
            }
            return theCode;
        }
        return null;
    }

    public void setRequestType(RequestType requestType) {
        if (requestType != null) {
            parameters.put(Names.requestType, requestType);
        } else {
            parameters.remove(Names.requestType);
        }
    }

    public String getUrl() {
        Object o = parameters.get(Names.url);        
        if (o == null)
        {
        	//try again for gen 1.1
        	o = parameters.get(Names.URL);	
        }
        if (o == null)
        	return null;
        
        if (o instanceof String) {
            return (String) o;
        }
        return null;
    }

    public void setUrl(String url) {
        if (url != null) {
            parameters.put(Names.url, url);
        } else {
            parameters.remove(Names.url);
        }
    }

    public FileType getFileType() {
        Object obj = parameters.get(Names.fileType);
        if (obj == null) return null;
        if (obj instanceof FileType) {
            return (FileType) obj;
        } else if (obj instanceof String) {
            FileType theCode = null;
            try {
                theCode = FileType.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError(
                        "Failed to parse " + getClass().getSimpleName() + "." +
                        		Names.fileType, e);
            }
            return theCode;
        }
        return null;
    }

    public void setFileType(FileType fileType) {
        if (fileType != null) {
            parameters.put(Names.fileType, fileType);
        } else {
            parameters.remove(Names.fileType);
        }
    }

    public Integer getOffset() {
        final Object o = parameters.get(Names.offset);
        
        if (o == null) return null;
        
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return null;
    }

    public void setOffset(Integer offset) {
        if (offset != null) {
            parameters.put(Names.offset, offset);
        } else {
            parameters.remove(Names.offset);
        }
    }
    
    public Integer getTimeout() {
        Object o = parameters.get(Names.timeout);
        
        if (o == null){
        	 o = parameters.get(Names.Timeout);
        	 if (o == null) return null;
        }
        
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return null;
    }

    public void setTimeout(Integer timeout) {
        if (timeout != null) {
            parameters.put(Names.timeout, timeout);
        } else {
            parameters.remove(Names.timeout);
        }
    }    

    public Integer getLength() {
        final Object o = parameters.get(Names.length);
        if (o == null) return null;
        		
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return null;
    }

    public void setLength(Integer length) {
        if (length != null) {
            parameters.put(Names.length, length);
        } else {
            parameters.remove(Names.length);
        }
    }
}