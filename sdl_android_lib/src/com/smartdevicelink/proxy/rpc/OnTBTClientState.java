package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TBTState;
import com.smartdevicelink.util.JsonUtils;

/**
 * <p>Notifies the application of the current TBT client status on the module.</p>
 *
 * <p></p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel: 
 * <ul><li>Can be sent with FULL, LIMITED or BACKGROUND</li></ul>
 * AudioStreamingState: 
 * <ul><li>Any</li></ul>
 * SystemContext: 
 * <ul><li>Any</li></ul>
 * </ul>
 * <p></p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>state</td>
 * <td>{@linkplain TBTState}</td>
 * <td>Current state of TBT client.</td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table>
 * @since SmartDeviceLink 1.0
 * 
 */
public class OnTBTClientState extends RPCNotification {
	public static final String KEY_STATE = "state";
	
	private String state; // represents TBTState enum
	
	/**
	*Constructs a newly allocated OnTBTClientState object
	*/ 
    public OnTBTClientState() {
        super(FunctionID.ON_TBT_CLIENT_STATE);
    }
    
    /**
     * Creates a OnTBTClientState object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnTBTClientState(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.state = JsonUtils.readStringFromJsonObject(jsonObject, KEY_STATE);
            break;
        }
    }
    
    /**
     * <p>Called to get the current state of TBT client</p>
     * @return {@linkplain TBTState} the current state of TBT client
     */    
    public TBTState getState() {
        return TBTState.valueForJsonName(this.state, sdlVersion);
    }
    
    /**
     * <p>Called to set the current state of TBT client</p>
     * @param state current state of TBT client
     */    
    public void setState( TBTState state ) {
        this.state = (state == null) ? null : state.getJsonName(sdlVersion);
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_STATE, this.state);
            break;
        }
        
        return result;
    }
}
