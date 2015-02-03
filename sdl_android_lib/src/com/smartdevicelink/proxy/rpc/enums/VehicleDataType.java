package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Defines the vehicle data types that can be published and subscribed to
 * 
 */
public enum VehicleDataType implements JsonName{
	VEHICLEDATA_GPS,
    VEHICLEDATA_SPEED,
    VEHICLEDATA_RPM,
    VEHICLEDATA_FUELLEVEL,
    VEHICLEDATA_FUELLEVEL_STATE,
    VEHICLEDATA_FUELCONSUMPTION,
    VEHICLEDATA_EXTERNTEMP,
    VEHICLEDATA_VIN,
    VEHICLEDATA_PRNDL,
    VEHICLEDATA_TIREPRESSURE,
    VEHICLEDATA_ODOMETER,    
    VEHICLEDATA_BELTSTATUS,
    VEHICLEDATA_BODYINFO,
    VEHICLEDATA_DEVICESTATUS,
    VEHICLEDATA_BRAKING,
    VEHICLEDATA_WIPERSTATUS,
    VEHICLEDATA_HEADLAMPSTATUS,
    VEHICLEDATA_BATTVOLTAGE,
    VEHICLEDATA_ENGINETORQUE,
    VEHICLEDATA_ACCPEDAL,
    VEHICLEDATA_STEERINGWHEEL,
    VEHICLEDATA_ECALLINFO,
    VEHICLEDATA_AIRBAGSTATUS,
    VEHICLEDATA_EMERGENCYEVENT,
    VEHICLEDATA_CLUSTERMODESTATUS,
    VEHICLEDATA_MYKEY,
    
    ;

    public static VehicleDataType valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static VehicleDataType valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }

        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.name();
        }
    }
}
