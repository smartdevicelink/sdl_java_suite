package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Used to set an alternate display layout. If not sent, default screen for
 * given platform will be shown
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 */
public class SetDisplayLayout extends RPCRequest {
	public static final String KEY_DISPLAY_LAYOUT = "displayLayout";
	
	private String displayLayout;
	
	/**
	 * Constructs a new SetDisplayLayout object
	 */
    public SetDisplayLayout() {
        super(FunctionID.SET_DISPLAY_LAYOUT);
    }
    
    /**
     * Creates a SetDisplayLayout object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SetDisplayLayout(JSONObject jsonObject){
        super(SdlCommand.SET_DISPLAY_LAYOUT, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.displayLayout = JsonUtils.readStringFromJsonObject(jsonObject, KEY_DISPLAY_LAYOUT);
            break;
        }
    }

	/**
	 * Sets a display layout. Predefined or dynamically created screen layout.
	 * Currently only predefined screen layouts are defined. Predefined layouts
	 * include: "ONSCREEN_PRESETS" Custom screen containing app-defined onscreen
	 * presets. Currently defined for GEN2
	 * 
	 * @param displayLayout
	 *            a String value representing a diaply layout
	 */
    public void setDisplayLayout(String displayLayout) {
        this.displayLayout = displayLayout;
    }

	/**
	 * Gets a display layout.
	 */
    public String getDisplayLayout() {
    	return this.displayLayout;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_DISPLAY_LAYOUT, this.displayLayout);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((displayLayout == null) ? 0 : displayLayout.hashCode());
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
		SetDisplayLayout other = (SetDisplayLayout) obj;
		if (displayLayout == null) {
			if (other.displayLayout != null) { 
				return false;
			}
		} else if (!displayLayout.equals(other.displayLayout)) { 
			return false;
		}
		return true;
	}
}
