package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.Hashtable;

/**
 * since Smart Device Link 6.0
 * A notification to inform SDL Core that a specific app capability has changed.
 */

public class OnAppCapabilityUpdated extends RPCNotification {
    public static final String KEY_APP_CAPABILITY = "appCapability";

    /**
     * Constructs a newly allocated OnAppCapabilityUpdated object
     */
    public OnAppCapabilityUpdated() {
        super(FunctionID.ON_APP_CAPABILITY_UPDATED.toString());
    }

    /**
     *<p>Constructs a newly allocated OnAppCapabilityUpdated object indicated by the Hashtable parameter</p>
     *@param hash The Hashtable to use
     */
    public OnAppCapabilityUpdated(Hashtable<String, Object> hash) {
        super(hash);
    }

    public OnAppCapabilityUpdated(AppCapability capability) {
        this();
        setAppCapability(capability);
    }

    /**
     * @param capability
     * The app capability that has been updated
     */
    public void setAppCapability(AppCapability capability) {
        setParameters(KEY_APP_CAPABILITY, capability);
    }


    /**
     * @return AppCapability
     * The app capability that has been updated
     */
    public AppCapability getAppCapability() {
        return (AppCapability) getObject(AppCapability.class, KEY_APP_CAPABILITY);
    }
}
