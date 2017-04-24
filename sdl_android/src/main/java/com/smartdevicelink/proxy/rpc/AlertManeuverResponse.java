package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Alert Maneuver Response is sent, when AlertManeuver has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class AlertManeuverResponse extends RPCResponse{

    /**
     * Constructs a new AlertManeuverResponse object
     */
    public AlertManeuverResponse() {
        super(FunctionID.ALERT_MANEUVER.toString());
    }
    
    /**
    * <p>
    * Constructs a new AlertManeuverResponse object indicated by the Hashtable
    * parameter
    * </p>
    * 
    * @param hash
    *            The Hashtable to use
    */
    public AlertManeuverResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

}
