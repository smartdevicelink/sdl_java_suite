package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * List Files Response is sent, when ListFiles has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ListFilesResponse extends RPCResponse {
	public static final String KEY_FILENAMES = "filenames";
	public static final String KEY_SPACE_AVAILABLE = "spaceAvailable";

	private List<String> filenames;
	private Integer spaceAvailable;
	
	/**
	 * Constructs a new ListFilesResponse object
	 */
    public ListFilesResponse() {
        super(FunctionID.LIST_FILES);
    }
    
    /**
     * Creates a ListFilesResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ListFilesResponse(JSONObject jsonObject) {
        super(SdlCommand.LIST_FILES, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.filenames = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_FILENAMES);
            this.spaceAvailable = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_SPACE_AVAILABLE);
            break;
        }
    }
    
    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }
    
    public List<String> getFilenames() {
        return this.filenames;
    }
    
    public void setSpaceAvailable(Integer spaceAvailable) {
        this.spaceAvailable = spaceAvailable;
    }
    
    public Integer getSpaceAvailable() {
        return this.spaceAvailable;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_SPACE_AVAILABLE, this.spaceAvailable);
            JsonUtils.addToJsonObject(result, KEY_FILENAMES, 
                    (this.filenames == null) ? null : JsonUtils.createJsonArray(this.filenames));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filenames == null) ? 0 : filenames.hashCode());
		result = prime * result + ((spaceAvailable == null) ? 0 : spaceAvailable.hashCode());
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
		ListFilesResponse other = (ListFilesResponse) obj;
		if (filenames == null) {
			if (other.filenames != null) { 
				return false;
			}
		} 
		else if (!filenames.equals(other.filenames)) { 
			return false;
		}
		if (spaceAvailable == null) {
			if (other.spaceAvailable != null) { 
				return false;
			}
		} 
		else if (!spaceAvailable.equals(other.spaceAvailable)) { 
			return false;
		}
		return true;
	}
}
