package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import java.util.Hashtable;

public class GetInteriorVehicleDataResponse extends RPCResponse {
	public static final String KEY_MODULE_DATA = "moduleData";
	public static final String KEY_IS_SUBSCRIBED = "isSubscribed";

    public GetInteriorVehicleDataResponse() {
        super(FunctionID.GET_INTERIOR_VEHICLE_DATA.toString());
    }
    public GetInteriorVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	public ModuleData getModuleData() {
		return (ModuleData) getObject(ModuleData.class, KEY_MODULE_DATA);
	}

	public void setModuleData(ModuleData moduleData) {
		setParameters(KEY_MODULE_DATA, moduleData);
	}

	public void setIsSubscribed(Boolean isSubscribed) {
		setParameters(KEY_IS_SUBSCRIBED, isSubscribed);
	}
	public Boolean getIsSubscribed() {
		return getBoolean(KEY_IS_SUBSCRIBED);
	}
}
