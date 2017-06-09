package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

/**
 * GetSystemCapabilityResponse is sent, when GetSystemCapability has been called
 */

public class GetSystemCapabilityResponse extends RPCResponse {
    public static final String KEY_SYSTEM_CAPABILITY = "systemCapability";

    /**
     * Constructs a new GetSystemCapability object
     */
    public GetSystemCapabilityResponse(){
        super(FunctionID.GET_SYSTEM_CAPABILITY.toString());
    }

    /**
     * <p>Constructs a new GetSystemCapability object indicated by the Hashtable parameter</p>
     *
     *
     * @param hash
     *            The Hashtable to use
     */
    public GetSystemCapabilityResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Get the SystemCapability object returned after a GetSystemCapability call
     * @return SystemCapability object
     */
    public SystemCapability getSystemCapability(){
        return (SystemCapability) getObject(SystemCapability.class, KEY_SYSTEM_CAPABILITY);
    }

    /**
     * Set a SystemCapability object in the response
     * @param value SystemCapability object
     */
    public void setSystemCapability(SystemCapability value){
        setParameters(KEY_SYSTEM_CAPABILITY, value);
    }
}
