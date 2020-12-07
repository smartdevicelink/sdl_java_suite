package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;

import java.util.Hashtable;
import java.util.List;

public class GetInteriorVehicleDataConsent extends RPCRequest {
    public static final String KEY_MODULE_TYPE = "moduleType";
    public static final String KEY_MODULE_ID = "moduleIds";

    public GetInteriorVehicleDataConsent() {
        super(FunctionID.GET_INTERIOR_VEHICLE_DATA_CONSENT.toString());
    }

    public GetInteriorVehicleDataConsent(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * @param moduleType Sets the Module Type for this class
     * @param moduleIds  Sets the Module Ids for this class
     */
    public GetInteriorVehicleDataConsent(@NonNull ModuleType moduleType, @NonNull List<String> moduleIds) {
        this();
        setModuleType(moduleType);
        setModuleIds(moduleIds);
    }

    /**
     * Sets the Module Type for this class
     *
     * @param type the Module Type to be set
     */
    public GetInteriorVehicleDataConsent setModuleType(@NonNull ModuleType type) {
        setParameters(KEY_MODULE_TYPE, type);
        return this;
    }

    /**
     * Gets the Module Type of this class
     *
     * @return the Module Type of this class
     */
    public ModuleType getModuleType() {
        return (ModuleType) getObject(ModuleType.class, KEY_MODULE_TYPE);
    }

    /**
     * Sets the Module Ids for this class
     *
     * @param ids the ids to be set
     */
    public GetInteriorVehicleDataConsent setModuleIds(@NonNull List<String> ids) {
        setParameters(KEY_MODULE_ID, ids);
        return this;
    }

    /**
     * Gets the Module Ids of this class
     *
     * @return the Module Ids
     */
    @SuppressWarnings("unchecked")
    public List<String> getModuleIds() {
        return (List<String>) getObject(String.class, KEY_MODULE_ID);
    }
}
