package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

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
    public static final String KEY_REMOTE_CONTROL_CAPABILITY = "remoteControlCapability";

    public SystemCapability(){}

    public SystemCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Create a systemCapability object
	 * @param systemCapabilityType The type
	 */
	public SystemCapability(@NonNull SystemCapabilityType systemCapabilityType){
		this();
		setSystemCapabilityType(systemCapabilityType);
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
    public void setSystemCapabilityType(@NonNull SystemCapabilityType value){
        setValue(KEY_SYSTEM_CAPABILITY_TYPE, value);
    }

    public RPCStruct getCapabilityForType(SystemCapabilityType type) {
	    if (type.equals(SystemCapabilityType.NAVIGATION)) {
		    return (RPCStruct) getObject(NavigationCapability.class, KEY_NAVIGATION_CAPABILITY);
	    } else if (type.equals(SystemCapabilityType.PHONE_CALL)) {
		    return (RPCStruct) getObject(PhoneCapability.class, KEY_PHONE_CAPABILITY);
	    } else if (type.equals(SystemCapabilityType.VIDEO_STREAMING)){
		    return (RPCStruct) getObject(VideoStreamingCapability.class, KEY_VIDEO_STREAMING_CAPABILITY);
        }else if(type.equals(SystemCapabilityType.REMOTE_CONTROL)){
            return (RPCStruct) getObject(RemoteControlCapabilities.class, KEY_REMOTE_CONTROL_CAPABILITY);
        }else{
            return null;
        }
    }

    public void setCapabilityForType(SystemCapabilityType type, RPCStruct capability){
        if(type.equals(SystemCapabilityType.NAVIGATION)){
            setValue(KEY_NAVIGATION_CAPABILITY, capability);
        }else if(type.equals(SystemCapabilityType.PHONE_CALL)){
            setValue(KEY_PHONE_CAPABILITY, capability);
        }else if(type.equals(SystemCapabilityType.VIDEO_STREAMING)){
	        setValue(KEY_VIDEO_STREAMING_CAPABILITY, capability);
        }else if(type.equals(SystemCapabilityType.REMOTE_CONTROL)){
            setValue(KEY_REMOTE_CONTROL_CAPABILITY, capability);
        }else{
	        return;
        }
    }
}
