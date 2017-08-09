package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import java.util.Hashtable;

public class GetInteriorVehicleData extends RPCRequest {
	public static final String KEY_MODULE_TYPE = "moduleType";
    public static final String KEY_SUBSCRIBE = "subscribe";

    public GetInteriorVehicleData() {
        super(FunctionID.GET_INTERIOR_VEHICLE_DATA.toString());
    }

    public GetInteriorVehicleData(Hashtable<String, Object> hash) {
        super(hash);
    }

    public ModuleType getModuleType() {
        return (ModuleType) getObject(ModuleType.class, KEY_MODULE_TYPE);
    }

    public void setModuleType(ModuleType moduleType) {
        setParameters(KEY_MODULE_TYPE, moduleType);
    }

    public void setSubscribe(Boolean subscribe) {
        setParameters(KEY_SUBSCRIBE, subscribe);
    }
    public Boolean getSubscribe() {
        return getBoolean(KEY_SUBSCRIBE);
    }
}
