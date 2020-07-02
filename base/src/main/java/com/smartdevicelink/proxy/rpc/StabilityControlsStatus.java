package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;

import java.util.Hashtable;

public class StabilityControlsStatus extends RPCStruct {
    public static final String KEY_ESC_SYSTEM = "escSystem";
    public static final String KEY_TRAILER_SWAY_CONTROL = "trailerSwayControl";


    /**
     * Constructs a new StabilityControlsStatus object
     */
    public StabilityControlsStatus() {}

    /**
     * <p>Constructs a new StabilityControlsStatus object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash The Hashtable to use
     */
    public StabilityControlsStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    /***
     * sets EscSystem
     * @param status VehicleDataStatus
     */
    public void setEscSystem(VehicleDataStatus status){
        setValue(KEY_ESC_SYSTEM, status);
    }

    /***
     * @return VehicleDataStatus for escSystem
     */
    public VehicleDataStatus getEscSystem() {
        return (VehicleDataStatus) getObject(VehicleDataStatus.class, KEY_ESC_SYSTEM);
    }

    /***
     * sets TrailerSwayControl
     * @param status VehicleDataStatus
     */
    public void setTrailerSwayControl(VehicleDataStatus status){
        setValue(KEY_TRAILER_SWAY_CONTROL, status);
    }

    /***
     * @return VehicleDataStatus for trailerSwayControl
     */
    public VehicleDataStatus getTrailerSWayControl() {
        return (VehicleDataStatus) getObject(VehicleDataStatus.class, KEY_TRAILER_SWAY_CONTROL);
    }
}
