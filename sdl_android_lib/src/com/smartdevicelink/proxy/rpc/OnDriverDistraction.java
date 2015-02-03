package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;
import com.smartdevicelink.util.JsonUtils;

/**
 * <p>Notifies the application of the current driver distraction state (whether driver distraction rules are in effect, or 
 * not).</p>
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
 * <td>{@linkplain DriverDistractionState}</td>
 * <td>Current driver distraction state (i.e. whether driver distraction rules are in effect, or not). </td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table> 
 * @since SmartDeviceLink 1.0
 */
public class OnDriverDistraction  extends RPCNotification {
	public static final String KEY_STATE = "state";
	
	private String state; // represents DriverDistractionState enum
	
	/**
	*Constructs a newly allocated OnDriverDistraction object
	*/ 
	public OnDriverDistraction() {
        super(FunctionID.ON_DRIVER_DISTRACTION);
    }
	
	/**
     * Creates an OnDriverDistraction object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */	
    public OnDriverDistraction(JSONObject jsonObject) {
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.state = JsonUtils.readStringFromJsonObject(jsonObject, KEY_STATE);
            break;
        }
    }
    
    /**
     * <p>Called to get the current driver distraction state(i.e. whether driver distraction rules are in effect, or not)</p>
     * @return {@linkplain DriverDistractionState} the Current driver distraction state.
     */    
    public DriverDistractionState getState() {
        return DriverDistractionState.valueForJsonName(this.state, sdlVersion);
    }
    
    /**
     * <p>Called to set the driver distraction state(i.e. whether driver distraction rules are in effect, or not)</p>
     * @param state the current driver distraction state
     */    
    public void setState( DriverDistractionState state ) {
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
