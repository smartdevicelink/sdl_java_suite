package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.util.DebugTool;

import java.util.Hashtable;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OnSystemRequest extends RPCNotification {
	public static final String URL = "URL";
	public static final String Timeout = "Timeout";
	public static final String headers = "headers";
	public static final String body = "body";
	public static final String fileType = "fileType";
	public static final String requestType = "requestType";
	public static final String data = "data";
	public static final String timeout = "timeout";
	public static final String offset = "offset";
	public static final String length = "length";

	
	Hashtable<String, Object> httpreqparams = null;
	JSONObject myJSONObj = null;
	public static final String url = "url";
	
    public OnSystemRequest() {
        super("OnSystemRequest");
    }

    public OnSystemRequest(Hashtable hash) {
        super(hash);
        
        //testing
        //String sJson = "{\"HTTPRequest\":{\"headers\":{\"ContentType\":\"application/json\",\"ConnectTimeout\":60,\"DoOutput\":true,\"DoInput\":true,\"UseCaches\":false,\"RequestMethod\":\"POST\",\"ReadTimeout\":60,\"InstanceFollowRedirects\":false,\"charset\":\"utf-8\",\"Content-Length\":10743},\"body\":\"{\\\"data\\\":[\\\"HQcYAAAp+Ul19L\\\"]}\"}}";
		try {			
			byte[] bulkData = (byte[]) hash.get(RPCNotification.bulkData);
			
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
            store.put(RPCNotification.bulkData, aptData);
        } else {
        	store.remove(RPCNotification.bulkData);
        }
    }
    public byte[] getBinData() {
        return (byte[]) store.get(RPCNotification.bulkData);
    }
    
    
    public Vector<String> getLegacyData() {
    	if (parameters.get(OnSystemRequest.data) instanceof Vector<?>) {
    		Vector<?> list = (Vector<?>)parameters.get(OnSystemRequest.data);
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
            parameters.put(OnSystemRequest.body, body);
        } else {
            parameters.remove(OnSystemRequest.body);
        }
    }
    
    
    public void setHeaders(Headers header) {
        if (header != null) {
        	httpreqparams.put(OnSystemRequest.headers, header);
        } else {
        	httpreqparams.remove(OnSystemRequest.headers);
        }
    }
 
    public Headers getHeader() {
    	if (httpreqparams == null) return null;
    	
    	Object obj = httpreqparams.get(OnSystemRequest.headers);
    	if (obj == null) return null;
        if (obj instanceof Headers) {
            return (Headers) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Headers((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnSystemRequest.headers, e);
            }
        }
        return null;
    }
    
    
    
    public RequestType getRequestType() {
        Object obj = parameters.get(OnSystemRequest.requestType);
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
                        		OnSystemRequest.requestType, e);
            }
            return theCode;
        }
        return null;
    }

    public void setRequestType(RequestType requestType) {
        if (requestType != null) {
            parameters.put(OnSystemRequest.requestType, requestType);
        } else {
            parameters.remove(OnSystemRequest.requestType);
        }
    }

    /*public Vector<String> getUrl() {
        if (parameters.get(Names.url) instanceof Vector<?>) {
            Vector<?> list = (Vector<?>) parameters.get(Names.url);
            if (list != null && list.size() > 0) {
                Object obj = list.get(0);
                if (obj instanceof String) {
                    return (Vector<String>) list;
                }
            }
        }
        return null;
    }*/

    public String getUrl() {
        Object o = parameters.get(OnSystemRequest.url);        
        if (o == null)
        {
        	//try again for gen 1.1
        	o = parameters.get(OnSystemRequest.URL);	
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
            parameters.put(OnSystemRequest.url, url);
        } else {
            parameters.remove(OnSystemRequest.url);
        }
    }
    
   /* public void setUrl(Vector<String> url) {
        if (url != null) {
            parameters.put(Names.url, url);
        } else {
            parameters.remove(Names.url);
        }
    }*/

    public FileType getFileType() {
        Object obj = parameters.get(OnSystemRequest.fileType);
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
                        		OnSystemRequest.fileType, e);
            }
            return theCode;
        }
        return null;
    }

    public void setFileType(FileType fileType) {
        if (fileType != null) {
            parameters.put(OnSystemRequest.fileType, fileType);
        } else {
            parameters.remove(OnSystemRequest.fileType);
        }
    }

    public Integer getOffset() {
        final Object o = parameters.get(OnSystemRequest.offset);
        
        if (o == null) return null;
        
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return null;
    }

    public void setOffset(Integer offset) {
        if (offset != null) {
            parameters.put(OnSystemRequest.offset, offset);
        } else {
            parameters.remove(OnSystemRequest.offset);
        }
    }
    
    public Integer getTimeout() {
        Object o = parameters.get(OnSystemRequest.timeout);
        
        if (o == null){
        	 o = parameters.get(OnSystemRequest.Timeout);
        	 if (o == null) return null;
        }
        
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return null;
    }

    public void setTimeout(Integer timeout) {
        if (timeout != null) {
            parameters.put(OnSystemRequest.timeout, timeout);
        } else {
            parameters.remove(OnSystemRequest.timeout);
        }
    }    

    public Integer getLength() {
        final Object o = parameters.get(OnSystemRequest.length);
        if (o == null) return null;
        		
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return null;
    }

    public void setLength(Integer length) {
        if (length != null) {
            parameters.put(OnSystemRequest.length, length);
        } else {
            parameters.remove(OnSystemRequest.length);
        }
    }
}
