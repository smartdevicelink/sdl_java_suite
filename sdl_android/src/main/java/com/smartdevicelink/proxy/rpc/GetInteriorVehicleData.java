package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import java.util.Hashtable;

/**
 * Read the current status value of specified remote control module (type). In addition,
 * When subscribe=true, subscribes for specific remote control module data items;
 * When subscribe=false, un-subscribes for specific remote control module data items.
 * Once subscribed, the application will be notified by the onInteriorVehicleData notification
 * whenever new data is available for the module.
 */
public class GetInteriorVehicleData extends RPCRequest {
	public static final String KEY_MODULE_TYPE = "moduleType";
    public static final String KEY_SUBSCRIBE = "subscribe";

    /**
     * Constructs a new GetInteriorVehicleData object
     */
    public GetInteriorVehicleData() {
        super(FunctionID.GET_INTERIOR_VEHICLE_DATA.toString());
    }

    /**
     * <p>Constructs a new GetInteriorVehicleData object indicated by the
     * Hashtable parameter</p>
     *
     *
     * @param hash
     * The Hashtable to use
     */
    public GetInteriorVehicleData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new GetInteriorVehicleData object
     */
    public GetInteriorVehicleData(@NonNull ModuleType moduleType) {
        this();
        setModuleType(moduleType);
    }

    /**
     * Gets the ModuleType
     *
     * @return ModuleType - The type of a RC module to retrieve module data from the vehicle.
     * In the future, this should be the Identification of a module.
     */
    public ModuleType getModuleType() {
        return (ModuleType) getObject(ModuleType.class, KEY_MODULE_TYPE);
    }

    /**
     * Sets a ModuleType
     *
     * @param moduleType
     * The type of a RC module to retrieve module data from the vehicle.
     * In the future, this should be the Identification of a module.
     */
    public void setModuleType(@NonNull ModuleType moduleType) {
        setParameters(KEY_MODULE_TYPE, moduleType);
    }

    /**
     * Sets subscribe parameter
     *
     * @param subscribe
     * If subscribe is true, the head unit will register onInteriorVehicleData notifications for the requested moduelType.
     * If subscribe is false, the head unit will unregister onInteriorVehicleData notifications for the requested moduelType.
     */
    public void setSubscribe(Boolean subscribe) {
        setParameters(KEY_SUBSCRIBE, subscribe);
    }

    /**
     * Gets subscribe parameter
     *
     * @return Boolean - If subscribe is true, the head unit will register onInteriorVehicleData notifications for the requested moduelType.
     * If subscribe is false, the head unit will unregister onInteriorVehicleData notifications for the requested moduelType.
     */
    public Boolean getSubscribe() {
        return getBoolean(KEY_SUBSCRIBE);
    }
}
