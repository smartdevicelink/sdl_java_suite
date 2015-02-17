package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Used to delete a file resident on the SDL module in the app's local cache.
 * Not supported on first generation SDL vehicles
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 * @see PutFile
 * @see ListFiles
 */
public class DeleteFile extends RPCRequest {
	public static final String KEY_SDL_FILE_NAME = "syncFileName";

	private String sdlFileName;
	
	/**
	 * Constructs a new DeleteFile object
	 */
    public DeleteFile() {
        super(FunctionID.DELETE_FILE);
    }

    /**
     * Creates a DeleteFile object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public DeleteFile(JSONObject jsonObject) {
        super(SdlCommand.DELETE_FILE, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.sdlFileName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_SDL_FILE_NAME);
            break;
        }
    }

	/**
	 * Sets a file reference name
	 * 
	 * @param sdlFileName
	 *            a String value representing a file reference name
	 */
    public void setSdlFileName(String sdlFileName) {
        this.sdlFileName = sdlFileName;
    }

	/**
	 * Gets a file reference name
	 * 
	 * @return String -a String value representing a file reference name
	 */
    public String getSdlFileName() {
        return this.sdlFileName;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_SDL_FILE_NAME, this.sdlFileName);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sdlFileName == null) ? 0 : sdlFileName.hashCode());
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
		DeleteFile other = (DeleteFile) obj;
		if (sdlFileName == null) {
			if (other.sdlFileName != null) { 
				return false;
			}
		} 
		else if (!sdlFileName.equals(other.sdlFileName)) { 
			return false;
		}
		return true;
	}
}
