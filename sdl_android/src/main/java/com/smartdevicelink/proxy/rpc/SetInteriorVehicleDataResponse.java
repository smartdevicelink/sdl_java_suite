package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

public class SetInteriorVehicleDataResponse extends RPCResponse {
	public static final String KEY_MODULE_DATA = "moduleData";

	/**
	 * Constructs a new SetInteriorVehicleDataResponse object
	 */
    public SetInteriorVehicleDataResponse() {
        super(FunctionID.SET_INTERIOR_VEHICLE_DATA.toString());
    }

	/**
	 * <p>Constructs a new SetInteriorVehicleDataResponse object indicated by the
	 * Hashtable parameter</p>
	 *
	 *
	 * @param hash
	 * The Hashtable to use
	 */
    public SetInteriorVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
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
	public void setModuleData(ModuleData moduleData) {
		setParameters(KEY_MODULE_DATA, moduleData);
	}
}
