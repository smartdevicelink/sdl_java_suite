package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * This function allows a remote control type mobile application change the settings
 * of a specific remote control module.
 */
public class SetInteriorVehicleData extends RPCRequest {
	public static final String KEY_MODULE_DATA = "moduleData";

    /**
     * Constructs a new SetInteriorVehicleData object
     */
    public SetInteriorVehicleData() {
        super(FunctionID.SET_INTERIOR_VEHICLE_DATA.toString());
    }

    /**
     * <p>Constructs a new SetInteriorVehicleData object indicated by the
     * Hashtable parameter</p>
     *
     *
     * @param hash
     * The Hashtable to use
     */
    public SetInteriorVehicleData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the moduleData
     *
     * @param moduleData
     */
    public void setModuleData(ModuleData moduleData) {
        setParameters(KEY_MODULE_DATA, moduleData);
    }

    /**
     * Gets the moduleData
     *
     * @return ModuleData
     */
    public ModuleData getModuleData() {
        return (ModuleData) getObject(ModuleData.class, KEY_MODULE_DATA);
    }
}
