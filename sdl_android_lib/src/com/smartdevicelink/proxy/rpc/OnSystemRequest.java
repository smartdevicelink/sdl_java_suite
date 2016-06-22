package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
/**
 * An asynchronous request from the system for specific data from the device or the cloud or response to a request from the device or cloud. Binary data can be included in hybrid part of message for some requests (such as Authentication request responses)
 * 
 * <p><b>Parameter List</b></p>
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
 * 			<td>Array of Strings</td>
 * 			<td>Optional URL for HTTP requests.If blank, the binary data shall be forwarded to the app.If not blank, the binary data shall be forwarded to the url with a provided timeout in seconds.</td>
 *                 <td>N</td>
 *                 <td>maxlength: 1000; minsize:1;  maxsize: 100</td>
 * 			<td>SmartDeviceLink 2.3.2 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>timeout</td>
 * 			<td>Integer</td>
 * 			<td>Optional timeout for HTTP requests;Required if a URL is provided</td>
 *                 <td>N</td>
 *                 <td>minvalue:0; maxvalue: 2000000000</td>
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
 * 			<td>Float</td>
 * 			<td>Optional offset in bytes for resuming partial data chunks</td>
 *                 <td>N</td>
 *                 <td>minvalue:0; maxvalue:100000000000</td>
 * 			<td>SmartDeviceLink 2.3.2 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>length</td>
 * 			<td>Float</td>
 * 			<td>Optional length in bytes for resuming partial data chunks</td>
 *                 <td>N</td>
 *                 <td>minvalue: 0; maxvalue:100000000000</td>
 * 			<td>SmartDeviceLink 2.3.2 </td>
 * 		</tr>
 *  </table>	      	
 * @since SmartDeviceLink 2.3.2
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
	
	private String body;
	private Headers headers;	
	
	/** Constructs a new OnSystemsRequest object
	 * 	
	 */

    public OnSystemRequest() {
        super(FunctionID.ON_SYSTEM_REQUEST.toString());
    }

    public OnSystemRequest(Hashtable<String, Object> hash) {
        this(hash, (byte[]) hash.get(RPCStruct.KEY_BULK_DATA));
    }
    
    public OnSystemRequest(Hashtable<String, Object> hash, byte[] bulkData){
        super(hash);
        setBulkData(bulkData);
    }
    
    private void handleBulkData(byte[] bulkData){
        if(bulkData == null){
            return;
        }
        
        JSONObject httpJson;
        String tempBody = null;
        Headers tempHeaders = null;
        if(RequestType.PROPRIETARY.equals(this.getRequestType())){
        	try{
            	JSONObject bulkJson = new JSONObject(new String(bulkData));
           
            	httpJson = bulkJson.getJSONObject("HTTPRequest");
            	tempBody = getBody(httpJson);
            	tempHeaders = getHeaders(httpJson);
        	}catch(JSONException e){
            	Log.e("OnSystemRequest", "HTTPRequest in bulk data was malformed.");
            	e.printStackTrace();
        	}catch(NullPointerException e){
            	Log.e("OnSystemRequest", "Invalid HTTPRequest object in bulk data.");
            	e.printStackTrace();
        	}
        }else if(RequestType.HTTP.equals(this.getRequestType())){
        	tempHeaders = new Headers();
        	tempHeaders.setContentType("application/json");
        	tempHeaders.setConnectTimeout(7);
        	tempHeaders.setDoOutput(true);
        	tempHeaders.setDoInput(true);
        	tempHeaders.setUseCaches(false);
        	tempHeaders.setRequestMethod("POST");
        	tempHeaders.setReadTimeout(7);
        	tempHeaders.setInstanceFollowRedirects(false);
        	tempHeaders.setCharset("utf-8");
        	tempHeaders.setContentLength(bulkData.length); 
        }
        
        this.body = tempBody;
        this.headers = tempHeaders;
    }
    
    private String getBody(JSONObject httpJson){
        String result = null;
        
        try{
            result = httpJson.getString("body");
        }catch(JSONException e){
            Log.e("OnSystemRequest", "\"body\" key doesn't exist in bulk data.");
            e.printStackTrace();
        }
        
        return result;
    }
    
    private Headers getHeaders(JSONObject httpJson){
        Headers result = null;
        
        try{
            JSONObject httpHeadersJson = httpJson.getJSONObject("headers");
            Hashtable<String, Object> httpHeadersHash = JsonRPCMarshaller.deserializeJSONObject(httpHeadersJson);
            result = new Headers(httpHeadersHash);
        }catch(JSONException e){
            Log.e("OnSystemRequest", "\"headers\" key doesn't exist in bulk data.");
            e.printStackTrace();
        }
        
        return result;
    }
    
    @Deprecated
    public void setBinData(byte[] aptData) {
        setBulkData(aptData);
    }
    
    @Deprecated
    public byte[] getBinData() {
        return getBulkData();
    }
    
    @Override
    public void setBulkData(byte[] bulkData){
        super.setBulkData(bulkData);
        handleBulkData(bulkData);
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
        return this.body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public void setHeaders(Headers header) {
        this.headers = header;
    }

    public Headers getHeader() {
    	return this.headers;
    }
    
    public RequestType getRequestType() {
        Object obj = parameters.get(KEY_REQUEST_TYPE);
        if (obj == null) return null;
        if (obj instanceof RequestType) {
            return (RequestType) obj;
        } else if (obj instanceof String) {
            return RequestType.valueForString((String) obj);
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
            return FileType.valueForString((String) obj);
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

    /**
     * @deprecated as of SmartDeviceLink 4.0
     * @param offset
     */
    public void setOffset(Integer offset) {
    	if(offset == null){
    		setOffset((Long)null);
    	}else{
    		setOffset(offset.longValue());
    	}
    }
    
    public Long getOffset() {
        final Object o = parameters.get(KEY_OFFSET);
        
        if (o == null){
        	return null;
        }
        
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }else if(o instanceof Long){
        	return (Long) o;
        }
        return null;
    }

    public void setOffset(Long offset) {
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
    
    public Long getLength() {
        final Object o = parameters.get(KEY_LENGTH);
        if (o == null){
        	return null;
        }
        		
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }else if(o instanceof Long){
        	return (Long) o;
        }
        return null;
    }

    /**
     * @deprecated as of SmartDeviceLink 4.0
     * @param length
     */
    public void setLength(Integer length) {
    	if(length == null){
    		setLength((Long)null);
    	}else{
    		setLength(length.longValue());
    	}
    }
    
    public void setLength(Long length) {
        if (length != null) {
            parameters.put(KEY_LENGTH, length);
        } else {
            parameters.remove(KEY_LENGTH);
        }
    }
}
