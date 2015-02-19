package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Alert Maneuver Response is sent, when AlertManeuver has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class AlertManeuverResponse extends RPCRequest{

    /**
     * Constructs a new AlertManeuverResponse object
     */
    public AlertManeuverResponse() {
        super(FunctionID.ALERT_MANEUVER);
    }
    
    /**
     * Creates an AlertManeuverResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public AlertManeuverResponse(JSONObject json){
        super(SdlCommand.ALERT_MANEUVER, json);
    }
    
}
