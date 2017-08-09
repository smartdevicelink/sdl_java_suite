package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

public class SetInteriorVehicleData extends RPCRequest {
	public static final String KEY_MODULE_DATA = "moduleData";

    public SetInteriorVehicleData() {
        super(FunctionID.SET_INTERIOR_VEHICLE_DATA.toString());
    }

    public SetInteriorVehicleData(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setModuleData(ModuleData moduleData) {
        setParameters(KEY_MODULE_DATA, moduleData);
    }

    public ModuleData getModuleData() {
        return (ModuleData) getObject(ModuleData.class, KEY_MODULE_DATA);
    }
}
