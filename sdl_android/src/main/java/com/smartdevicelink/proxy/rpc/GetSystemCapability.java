package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;

import java.util.Hashtable;

/**
 * Used to request the corresponding capability object for a given capability.
 */

public class GetSystemCapability extends RPCRequest {
    public static final String KEY_SYSTEM_CAPABILITY_TYPE = "systemCapabilityType";

    /**
     * Constructs a new GetSystemCapability object
     */
    public GetSystemCapability(){
        super(FunctionID.GET_SYSTEM_CAPABILITY.toString());
    }

    /**
     * <p>Constructs a new GetSystemCapability object indicated by the Hashtable parameter</p>
     *
     * @param hash
     *            The Hashtable to use
     */
    public GetSystemCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new GetSystemCapability object
     * @param systemCapabilityType SystemCapabilityType being requested
     */
    public GetSystemCapability(@NonNull SystemCapabilityType systemCapabilityType){
        this();
        setSystemCapabilityType(systemCapabilityType);
    }

    /**
     * Used to get the SystemCapabilityType being requested
     * @return the SystemCapabilityType being requested
     */
    public SystemCapabilityType getSystemCapabilityType(){
        return (SystemCapabilityType) getObject(SystemCapabilityType.class, KEY_SYSTEM_CAPABILITY_TYPE);
    }

    /**
     * Used to set the SystemCapabilityType being requested
     * @param value SystemCapabilityType being requested
     */
    public void setSystemCapabilityType(@NonNull SystemCapabilityType value){
        setParameters(KEY_SYSTEM_CAPABILITY_TYPE, value);
    }
}
