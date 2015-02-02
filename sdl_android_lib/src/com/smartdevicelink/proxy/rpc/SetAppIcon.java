package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.JsonUtils;

/**
 * Used to set existing local file on SDL as the app's icon. Not supported on
 * first generation SDL vehicles
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 */
public class SetAppIcon extends RPCRequest {
	public static final String KEY_SDL_FILE_NAME = "syncFileName";

	private String filename;
	
	/**
	 * Constructs a new SetAppIcon object
	 */
    public SetAppIcon() {
        super(FunctionID.SET_APP_ICON);
    }
    
    /**
     * Creates a SetAppIcon object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SetAppIcon(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.filename = JsonUtils.readStringFromJsonObject(jsonObject, KEY_SDL_FILE_NAME);
            break;
        }
    }

	/**
	 * Sets a file reference name
	 * 
	 * @param sdlFileName
	 *            a String value representing a file reference name
	 *            <p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public void setSdlFileName(String sdlFileName) {
        this.filename = sdlFileName;
    }

	/**
	 * Gets a file reference name
	 * @return String -a String value
	 */
    public String getSdlFileName() {
        return this.filename;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_SDL_FILE_NAME, this.filename);
            break;
        }
        
        return result;
    }
}
