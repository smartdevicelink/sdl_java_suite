package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
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
        super(SdlCommand.SET_APP_ICON, jsonObject);
        jsonObject = getParameters(jsonObject);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
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
		SetAppIcon other = (SetAppIcon) obj;
		if (filename == null) {
			if (other.filename != null) { 
				return false;
			}
		} 
		else if (!filename.equals(other.filename)) { 
			return false;
		}
		return true;
	}
}
