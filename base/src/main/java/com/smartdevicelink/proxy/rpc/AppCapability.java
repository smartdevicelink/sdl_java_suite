package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AppCapabilityType;

import java.util.Hashtable;

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

    public AppCapability(@NonNull VideoStreamingCapability capability, AppCapabilityType appCapabilityType) {
        this();
        setVideoStreamingCapability(capability);
        setAppCapabilityType(appCapabilityType);
    }

    public VideoStreamingCapability getVideoStreamingCapability() {
        return (VideoStreamingCapability) getObject(VideoStreamingCapability.class, KEY_VIDEO_STREAMING_CAPABILITY);
    }

    public void setVideoStreamingCapability(@NonNull VideoStreamingCapability capabilityType) {
        setValue(KEY_VIDEO_STREAMING_CAPABILITY, capabilityType);
    }

    public AppCapabilityType getAppCapabilityType() {
        return (AppCapabilityType) getObject(VideoStreamingCapability.class, KEY_APP_CAPABILITY_TYPE);
    }

    public void setAppCapabilityType(@NonNull AppCapabilityType capabilityType) {
        setValue(KEY_APP_CAPABILITY_TYPE, capabilityType);
    }
}
