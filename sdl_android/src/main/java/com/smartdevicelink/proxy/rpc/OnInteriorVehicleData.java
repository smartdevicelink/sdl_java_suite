package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.Hashtable;

public class OnInteriorVehicleData extends RPCNotification {
	public static final String KEY_MODULE_DATA = "moduleData";

    /**
     * Constructs a new OnInteriorVehicleData object
     */
	public OnInteriorVehicleData() {
        super(FunctionID.ON_INTERIOR_VEHICLE_DATA.toString());
    }

    /**
     * <p>Constructs a new OnInteriorVehicleData object indicated by the
     * Hashtable parameter</p>
     *
     *
     * @param hash
     * The Hashtable to use
     */
    public OnInteriorVehicleData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new OnInteriorVehicleData object
     * @param moduleData
     */
    public OnInteriorVehicleData(@NonNull ModuleData moduleData) {
        this();
        setModuleData(moduleData);
    }

    /**
     * Gets the moduleData
     *
     * @return ModuleData
     */
    public ModuleData getModuleData() {
        return (ModuleData) getObject(ModuleData.class, KEY_MODULE_DATA);
    }

    /**
     * Sets the moduleData
     *
     * @param moduleData
     */
    public void setModuleData(@NonNull ModuleData moduleData) {
        setParameters(KEY_MODULE_DATA, moduleData);
    }
}
