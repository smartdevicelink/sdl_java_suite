package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.AppInterfaceUnregisteredReason;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * <p>Notifies an application that its interface registration has been terminated. This means that all SDL resources 
 * associated with the application are discarded, including the Command Menu, Choice Sets, button subscriptions, etc.</p>
 * For more information about SDL resources related to an interface registration, see {@linkplain RegisterAppInterface}.
 * <p></p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel: 
 * <ul><li>Any</li></ul>
 * AudioStreamingState: 
 * <ul><li>Any</li></ul>
 * SystemContext: 
 * <ul><li>Any</li></ul>
 * </ul>
 * <p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>reason</td>
 * <td>{@linkplain AppInterfaceUnregisteredReason}</td>
 * <td>The reason the application's interface registration was terminated</td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table>
 * </p>
 * @since SmartDeviceLink 1.0
 * @see RegisterAppInterface
 */
public class OnAppInterfaceUnregistered extends RPCNotification {
	public static final String KEY_REASON = "reason";
	
	private String reason; // represents AppInterfaceUnregisteredReason enum
	
	/**
	*Constructs a newly allocated OnAppInterfaceUnregistered object
	*/ 
    public OnAppInterfaceUnregistered() {
        super(FunctionID.ON_APP_INTERFACE_UNREGISTERED);
    }
    
    /**
     * Creates an OnAppInterfaceUnregistered object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnAppInterfaceUnregistered(JSONObject jsonObject) {
        super(SdlCommand.ON_APP_INTERFACE_UNREGISTERED, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.reason = JsonUtils.readStringFromJsonObject(jsonObject, KEY_REASON);
            break;
        }
    }
    
    /**
     * <p>Get the reason the registration was terminated</p>
     * @return {@linkplain AppInterfaceUnregisteredReason} the reason the application's interface registration was terminated
     */    
    public AppInterfaceUnregisteredReason getReason() {
        return AppInterfaceUnregisteredReason.valueForJsonName(this.reason, sdlVersion);
    }
    
    /**
     * <p>Set the reason application's interface was terminated</p>
     * @param reason The reason application's interface registration was terminated
     */    
    public void setReason( AppInterfaceUnregisteredReason reason ) {
        this.reason = (reason == null) ? null : reason.getJsonName(sdlVersion);
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_REASON, this.reason);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
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
		OnAppInterfaceUnregistered other = (OnAppInterfaceUnregistered) obj;
		if (reason == null) {
			if (other.reason != null) { 
				return false;
			}
		} else if (!reason.equals(other.reason)) { 
			return false;
		}
		return true;
	}
}
