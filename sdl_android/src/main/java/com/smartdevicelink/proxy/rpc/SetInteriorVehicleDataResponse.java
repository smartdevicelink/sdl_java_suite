package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.util.DebugTool;

import java.util.Hashtable;

public class SetInteriorVehicleDataResponse extends RPCResponse {
	public static final String KEY_MODULE_DATA = "moduleData";

    public SetInteriorVehicleDataResponse() {
        super(FunctionID.SET_INTERIOR_VEHICLE_DATA.toString());
    }
    public SetInteriorVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	public ModuleData getModuleData() {
		return (ModuleData) getObject(ModuleData.class, KEY_MODULE_DATA);
	}

	public void setModuleData(ModuleData moduleData) {
		setParameters(KEY_MODULE_DATA, moduleData);
	}
}
