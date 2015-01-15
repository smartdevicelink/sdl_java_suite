package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.util.DebugTool;
/**
 * An asynchronous request from the system for specific data from the device or the cloud or response to a request from the device or cloud<br>Binary data can be included in hybrid part of message for some requests (such as Authentication request responses)
 * <p>
 * <p><b>Parameter List</b>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>requestType</td>
 * 			<td>RequestType</td>
 * 			<td>The type of system request.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.3.2 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>url</td>
 * 			<td>String</td>
 * 			<td>Optional URL for HTTP requests.<br>If blank, the binary data shall be forwarded to the app.<br>If not blank, the binary data shall be forwarded to the url with a provided timeout in seconds.</td>
 *                 <td>N</td>
 *                 <td>maxlength: 1000 <br>minsize:1<br> maxsize: 100</td>
 * 			<td>SmartDeviceLink 2.3.2 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>timeout</td>
 * 			<td>Integer</td>
 * 			<td>Optional timeout for HTTP requests<br>Required if a URL is provided</td>
 *                 <td>N</td>
 *                 <td>minvalue:0<br> maxvalue: 2000000000</td>
 * 			<td>SmartDeviceLink </td>
 * 		</tr>
 * 		<tr>
 * 			<td>fileType</td>
 * 			<td>FileType</td>
 * 			<td>Optional file type (meant for HTTP file requests).</td>
 *                 <td>N</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.3.2 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>offset</td>
 * 			<td>Integer</td>
 * 			<td>Optional offset in bytes for resuming partial data chunks</td>
 *                 <td>N</td>
 *                 <td>minvalue:0<br> maxvalue:100000000000</td>
 * 			<td>SmartDeviceLink 2.3.2 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>length</td>
 * 			<td>Integer</td>
 * 			<td>Optional length in bytes for resuming partial data chunks</td>
 *                 <td>N</td>
 *                 <td>minvalue: 0<br> maxvalue:100000000000</td>
 * 			<td>SmartDeviceLink 2.3.2 </td>
 * 		</tr>
 *  </table>	      	
 *
 */
public class OnSystemRequest extends RPCNotification {
	public static final String KEY_URL_V1 = "URL";
    public static final String KEY_URL = "url";
	public static final String KEY_TIMEOUT_V1 = "Timeout";
    public static final String KEY_TIMEOUT = "timeout";
	public static final String KEY_HEADERS = "headers";
	public static final String KEY_BODY = "body";
	public static final String KEY_FILE_TYPE = "fileType";
	public static final String KEY_REQUEST_TYPE = "requestType";
	public static final String KEY_DATA = "data";
	public static final String KEY_OFFSET = "offset";
	public static final String KEY_LENGTH = "length";

	
	Hashtable<String, Object> httpreqparams = null;
	JSONObject myJSONObj = null;
	/** Constructs a new OnSystemsRequest object
	 * 	
	 */

    public OnSystemRequest() {
        super(FunctionID.ON_SYSTEM_REQUEST);
    }

    @SuppressWarnings("unchecked")
    public OnSystemRequest(Hashtable<String, Object> hash) {
        super(hash);
        
        //testing
        //String sJson = "{\"HTTPRequest\":{\"headers\":{\"ContentType\":\"application/json\",\"ConnectTimeout\":60,\"DoOutput\":true,\"DoInput\":true,\"UseCaches\":false,\"RequestMethod\":\"POST\",\"ReadTimeout\":60,\"InstanceFollowRedirects\":false,\"charset\":\"utf-8\",\"Content-Length\":10743},\"body\":\"{\\\"data\\\":[\\\"HQcYAAAp+Ul19L\\\"]}\"}}";
		try {			
			byte[] bulkData = (byte[]) hash.get(RPCStruct.KEY_BULK_DATA);
			
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
            store.put(RPCStruct.KEY_BULK_DATA, aptData);
        } else {
        	store.remove(RPCStruct.KEY_BULK_DATA);
        }
    }
    public byte[] getBinData() {
        return (byte[]) store.get(RPCStruct.KEY_BULK_DATA);
    }
    
    
    @SuppressWarnings("unchecked")
    public List<String> getLegacyData() {
    	if (parameters.get(KEY_DATA) instanceof List<?>) {
    		List<?> list = (List<?>)parameters.get(KEY_DATA);
    		if (list != null && list.size()>0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
        			return (List<String>)list;
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
            parameters.put(KEY_BODY, body);
        } else {
            parameters.remove(KEY_BODY);
        }
    }
    
    
    public void setHeaders(Headers header) {
        if (header != null) {
        	httpreqparams.put(KEY_HEADERS, header);
        } else {
        	httpreqparams.remove(KEY_HEADERS);
        }
    }
 
    @SuppressWarnings("unchecked")
    public Headers getHeader() {
    	if (httpreqparams == null) return null;
    	
    	Object obj = httpreqparams.get(KEY_HEADERS);
    	if (obj == null) return null;
        if (obj instanceof Headers) {
            return (Headers) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Headers((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_HEADERS, e);
            }
        }
        return null;
    }
    
    
    
    public RequestType getRequestType() {
        Object obj = parameters.get(KEY_REQUEST_TYPE);
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
                        		KEY_REQUEST_TYPE, e);
            }
            return theCode;
        }
        return null;
    }

    public void setRequestType(RequestType requestType) {
        if (requestType != null) {
            parameters.put(KEY_REQUEST_TYPE, requestType);
        } else {
            parameters.remove(KEY_REQUEST_TYPE);
        }
    }

    public String getUrl() {
        Object o = parameters.get(KEY_URL);        
        if (o == null)
        {
        	//try again for gen 1.1
        	o = parameters.get(KEY_URL_V1);	
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
            parameters.put(KEY_URL, url);
        } else {
            parameters.remove(KEY_URL);
        }
    }

    public FileType getFileType() {
        Object obj = parameters.get(KEY_FILE_TYPE);
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
                        		KEY_FILE_TYPE, e);
            }
            return theCode;
        }
        return null;
    }

    public void setFileType(FileType fileType) {
        if (fileType != null) {
            parameters.put(KEY_FILE_TYPE, fileType);
        } else {
            parameters.remove(KEY_FILE_TYPE);
        }
    }

    public Integer getOffset() {
        final Object o = parameters.get(KEY_OFFSET);
        
        if (o == null) return null;
        
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return null;
    }

    public void setOffset(Integer offset) {
        if (offset != null) {
            parameters.put(KEY_OFFSET, offset);
        } else {
            parameters.remove(KEY_OFFSET);
        }
    }
    
    public Integer getTimeout() {
        Object o = parameters.get(KEY_TIMEOUT);
        
        if (o == null){
        	 o = parameters.get(KEY_TIMEOUT_V1);
        	 if (o == null) return null;
        }
        
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return null;
    }

    public void setTimeout(Integer timeout) {
        if (timeout != null) {
            parameters.put(KEY_TIMEOUT, timeout);
        } else {
            parameters.remove(KEY_TIMEOUT);
        }
    }    

    public Integer getLength() {
        final Object o = parameters.get(KEY_LENGTH);
        if (o == null) return null;
        		
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return null;
    }

    public void setLength(Integer length) {
        if (length != null) {
            parameters.put(KEY_LENGTH, length);
        } else {
            parameters.remove(KEY_LENGTH);
        }
    }
}
