package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.util.DebugTool;

import java.util.Hashtable;

public class OnInteriorVehicleData extends RPCNotification {
	public static final String KEY_MODULE_DATA = "moduleData";

	public OnInteriorVehicleData() {
        super(FunctionID.ON_INTERIOR_VEHICLE_DATA.toString());
    }

    public OnInteriorVehicleData(Hashtable<String, Object> hash) {
        super(hash);
    }

    public ModuleData getModuleData() {
        return (ModuleData) getObject(ModuleData.class, KEY_MODULE_DATA);
    }

    public void setModuleData(ModuleData moduleData) {
        setParameters(KEY_MODULE_DATA, moduleData);
    }
}
