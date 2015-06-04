package com.smartdevicelink.rpc.responses;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Alert Maneuver Response is sent, when AlertManeuver has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class AlertManeuverResponse extends RpcResponse{

    /**
     * Constructs a new AlertManeuverResponse object
     */
    public AlertManeuverResponse() {
        super(FunctionId.ALERT_MANEUVER.toString());
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
