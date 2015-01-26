package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
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
        super(jsonObject);
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
}
