package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.List;

public class LightControlData extends RPCStruct{
    public static final String KEY_LIGHT_STATE= "lightState";

    /**
     * Constructs a new LightControlData object
     */
    public LightControlData() { }

    /**
     * <p>Constructs a new LightControlData object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash
     *            The Hashtable to use
     */
    public LightControlData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Gets the lightState portion of the LightControlData class
     *
     * @return List<LightState> - An array of LightNames and their current or desired status. Status of the LightNames that are not listed in the array shall remain unchanged.
     */
    @SuppressWarnings("unchecked")
    public List<LightState> getLightState() {
        return (List<LightState>) getObject(LightState.class, KEY_LIGHT_STATE);
    }

    /**
     * Sets the lightState portion of the LightControlData class
     *
     * @param lightState
     * An array of LightNames and their current or desired status. Status of the LightNames that are not listed in the array shall remain unchanged.
     */
    public void setLightState( List<LightState> lightState ) {
        setValue(KEY_LIGHT_STATE, lightState);
    }
}
