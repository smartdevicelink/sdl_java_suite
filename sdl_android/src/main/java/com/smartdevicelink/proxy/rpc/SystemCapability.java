package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;

import java.util.Hashtable;

/**
 * Struct that indicates the a SystemCapabilityType and houses different structs to describe particular capabilities
 */

public class SystemCapability extends RPCStruct {
    public static final String KEY_SYSTEM_CAPABILITY_TYPE = "systemCapabilityType";
    public static final String KEY_NAVIGATION_CAPABILITY = "navigationCapability";
    public static final String KEY_PHONE_CAPABILITY = "phoneCapability";
    public static final String KEY_VIDEO_STREAMING_CAPABILITY = "videoStreamingCapability";
    public static final String KEY_AUDIO_STREAMING_CAPABILITY = "audioStreamingCapability";

    public SystemCapability(){}

    public SystemCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     *
     * @return The SystemCapabilityType that indicates which type of data should be changed and identifies which data object exists in this struct. For example, if the SystemCapability Type is NAVIGATION then a "navigationCapability" should exist
     */
    public SystemCapabilityType getSystemCapabilityType(){
        return (SystemCapabilityType) getObject(SystemCapabilityType.class, KEY_SYSTEM_CAPABILITY_TYPE);
    }

    /**
     * @param value Set the SystemCapabilityType that indicates which type of data should be changed and identifies which data object exists in this struct.
     */
    public void setSystemCapabilityType(SystemCapabilityType value){
        setValue(KEY_SYSTEM_CAPABILITY_TYPE, value);
    }

    // TODO: Implement individual capability methods
}
