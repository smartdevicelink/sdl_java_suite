package com.smartdevicelink.proxy.rpc;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.BulkData;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

public class OnSystemRequest extends RPCNotification implements BulkData{
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

	private List<String> legacyData;
	private String body, url;
	private String requestType; // represents RequestType enum
	private String fileType; // represents FileType enum
	private Integer offset, timeout, length;
	private Headers headers;
	
	private byte[] bulkData;
	
	private JSONObject bulkDataJson;
	
    public OnSystemRequest() {
        super(FunctionID.ON_SYSTEM_REQUEST);
    }
    
    /**
     * Creates a OnSystemRequest object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnSystemRequest(JSONObject jsonObject){
        this(jsonObject, null);
    }
    
    /**
     * Creates a OnSystemRequest object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     * @param bulkData The bulk data for this object
     */
    public OnSystemRequest(JSONObject jsonObject, byte[] bulkData){
        super(SdlCommand.ON_SYSTEM_REQUEST, jsonObject);
        jsonObject = getParameters(jsonObject);
        
        this.bulkData = bulkData;
        try{
            extractBulkData(bulkData);
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        
        switch(sdlVersion){
        // TODO: what versions use the "v1" JSON keys?
        case 1: // fall through
        case 2: 
            this.url = JsonUtils.readStringFromJsonObject(jsonObject, KEY_URL_V1);
            this.requestType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_REQUEST_TYPE);
            this.fileType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_FILE_TYPE);
            
            this.offset = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_OFFSET);
            this.timeout = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_TIMEOUT_V1);
            this.length = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_LENGTH);
            
            this.legacyData = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_DATA);
            break;
        default:
            this.url = JsonUtils.readStringFromJsonObject(jsonObject, KEY_URL);
            this.requestType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_REQUEST_TYPE);
            this.fileType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_FILE_TYPE);
            
            this.offset = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_OFFSET);
            this.timeout = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_TIMEOUT);
            this.length = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_LENGTH);
            
            this.legacyData = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_DATA);
            break;
        }
    }
    
    private void extractBulkData(byte[] bulkData) throws IllegalArgumentException{
        if(bulkData != null){
            this.bulkDataJson = JsonUtils.createJsonObject(bulkData);
            if(bulkDataJson == null){
                throw new IllegalArgumentException("Bulk data does not represent a valid JSON object.  getBody and getHeaders may not work as expected.");
            }
            JSONObject httpRequestJson = JsonUtils.readJsonObjectFromJsonObject(bulkDataJson, "HTTPRequest");
            if(httpRequestJson != null){
                this.body = JsonUtils.readStringFromJsonObject(httpRequestJson, "body");
                if(this.body == null){
                    // TODO: this seems like a hack.  are we to assume that if the body field is null that the 
                    //       body is actually just the entire JSON structure?
                    this.body = bulkDataJson.toString();
                }
                
                JSONObject headersJson = JsonUtils.readJsonObjectFromJsonObject(httpRequestJson, "headers");
                if(headersJson != null){
                    this.headers = new Headers(headersJson);
                }
            }
        }
    }
    
    @Deprecated
    public void setBinData(byte[] aptData) {
        setBulkData(aptData);
    }
    
    @Deprecated
    public byte[] getBinData() {
        return getBulkData();
    }
    
    public List<String> getLegacyData() {
    	return this.legacyData;
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
        return RequestType.valueForJsonName(this.requestType, sdlVersion);
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = (requestType == null) ? null : requestType.getJsonName(sdlVersion);
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FileType getFileType() {
        return FileType.valueForJsonName(this.fileType, sdlVersion);
    }

    public void setFileType(FileType fileType) {
        this.fileType = (fileType == null) ? null : fileType.getJsonName(sdlVersion);
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    
    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }    

    public Integer getLength() {
        return this.length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public byte[] getBulkData(){
        return this.bulkData;
    }

    @Override
    public void setBulkData(byte[] rawData){
        this.bulkData = rawData;
        try{
            extractBulkData(bulkData);
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        // TODO: what versions use the "v1" JSON keys?
        case 1: // fall through
        case 2:
            JsonUtils.addToJsonObject(result, KEY_URL_V1, this.url);
            JsonUtils.addToJsonObject(result, KEY_TIMEOUT_V1, this.timeout);
            JsonUtils.addToJsonObject(result, KEY_FILE_TYPE, this.fileType);
            JsonUtils.addToJsonObject(result, KEY_LENGTH, this.length);
            JsonUtils.addToJsonObject(result, KEY_OFFSET, this.offset);
            JsonUtils.addToJsonObject(result, KEY_REQUEST_TYPE, this.requestType);
            JsonUtils.addToJsonObject(result, KEY_DATA, (this.legacyData == null) ? null :
                JsonUtils.createJsonArray(this.legacyData));
            break;
        default:
            JsonUtils.addToJsonObject(result, KEY_URL, this.url);
            JsonUtils.addToJsonObject(result, KEY_TIMEOUT, this.timeout);
            JsonUtils.addToJsonObject(result, KEY_FILE_TYPE, this.fileType);
            JsonUtils.addToJsonObject(result, KEY_LENGTH, this.length);
            JsonUtils.addToJsonObject(result, KEY_OFFSET, this.offset);
            JsonUtils.addToJsonObject(result, KEY_REQUEST_TYPE, this.requestType);
            JsonUtils.addToJsonObject(result, KEY_DATA, (this.legacyData == null) ? null :
                JsonUtils.createJsonArray(this.legacyData));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + Arrays.hashCode(bulkData);
		result = prime * result + ((bulkDataJson == null) ? 0 : bulkDataJson.hashCode());
		result = prime * result + ((fileType == null) ? 0 : fileType.hashCode());
		result = prime * result + ((headers == null) ? 0 : headers.hashCode());
		result = prime * result + ((legacyData == null) ? 0 : legacyData.hashCode());
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result + ((offset == null) ? 0 : offset.hashCode());
		result = prime * result + ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result + ((timeout == null) ? 0 : timeout.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		OnSystemRequest other = (OnSystemRequest) obj;
		if (body == null) {
			if (other.body != null) { 
				return false;
			}
		}
		else if (!body.equals(other.body)) { 
			return false;
		}
		if (!Arrays.equals(bulkData, other.bulkData)) { 
			return false;
		}
		if (bulkDataJson == null) {
			if (other.bulkDataJson != null) { 
				return false;
			}
		}
		else if (!bulkDataJson.equals(other.bulkDataJson)) { 
			return false;
		}
		if (fileType == null) {
			if (other.fileType != null) { 
				return false;
			}
		} 
		else if (!fileType.equals(other.fileType)) { 
			return false;
		}
		if (headers == null) {
			if (other.headers != null) { 
				return false;
			}
		} 
		else if (!headers.equals(other.headers)) { 
			return false;
		}
		if (legacyData == null) {
			if (other.legacyData != null) { 
				return false;
			}
		} 
		else if (!legacyData.equals(other.legacyData)) { 
			return false;
		}
		if (length == null) {
			if (other.length != null) { 
				return false;
			}
		} 
		else if (!length.equals(other.length)) { 
			return false;
		}
		if (offset == null) {
			if (other.offset != null) { 
				return false;
			}
		} 
		else if (!offset.equals(other.offset)) { 
			return false;
		}
		if (requestType == null) {
			if (other.requestType != null) { 
				return false;
			}
		} 
		else if (!requestType.equals(other.requestType)) { 
			return false;
		}
		if (timeout == null) {
			if (other.timeout != null) { 
				return false;
			}
		}
		else if (!timeout.equals(other.timeout)) { 
			return false;
		}
		if (url == null) {
			if (other.url != null) { 
				return false;
			}
		} 
		else if (!url.equals(other.url)) { 
			return false;
		}
		return true;
	}
}
