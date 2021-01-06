package com.smartdevicelink.proxy.rpc;


import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AppCapabilityType;

import java.util.Hashtable;

/**
 * @since SmartDeviceLink 7.1
 */
public class AppCapability extends RPCStruct {
    public static final String KEY_APP_CAPABILITY_TYPE = "appCapabilityType";
    public static final String KEY_VIDEO_STREAMING_CAPABILITY = "videoStreamingCapability";

    public AppCapability() {
    }

    /**
     * Constructs a new AppCapability object indicated by the Hashtable
     * parameter
     *
     * @param hash hashtable filled with params to create an instance of this RPC
     *             The hash table to use
     */
    public AppCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    public AppCapability(VideoStreamingCapability capability, AppCapabilityType appCapabilityType) {
        this();
        setVideoStreamingCapability(capability);
        setAppCapabilityType(appCapabilityType);
    }

    /**
     * Describes supported capabilities for video streaming
     * @return VideoStreamingCapability
     */
    public VideoStreamingCapability getVideoStreamingCapability() {
        return (VideoStreamingCapability) getObject(VideoStreamingCapability.class, KEY_VIDEO_STREAMING_CAPABILITY);
    }

    /**
     * @param capabilityType
     * Describes supported capabilities for video streaming
     */
    public void setVideoStreamingCapability(VideoStreamingCapability capabilityType) {
        setValue(KEY_VIDEO_STREAMING_CAPABILITY, capabilityType);
    }

    /**
     * Used as a descriptor of what data to expect in this struct.
     * The corresponding param to this enum should be included and the only other param included.
     * @return AppCapabilityType
     */
    public AppCapabilityType getAppCapabilityType() {
        return (AppCapabilityType) getObject(AppCapabilityType.class, KEY_APP_CAPABILITY_TYPE);
    }

    /**
     * @param capabilityType
     * Used as a descriptor of what data to expect in this struct.
     * The corresponding param to this enum should be included and the only other param included.
     */
    public void setAppCapabilityType(@NonNull AppCapabilityType capabilityType) {
        setValue(KEY_APP_CAPABILITY_TYPE, capabilityType);
    }
}
