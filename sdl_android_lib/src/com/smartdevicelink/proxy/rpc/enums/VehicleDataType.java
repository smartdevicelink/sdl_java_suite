package com.smartdevicelink.proxy.rpc.enums;

/**
 * Defines the vehicle data types that can be published and subscribed to
 * 
 */
public enum VehicleDataType {
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
    VEHICLEDATA_MYKEY;

    public static VehicleDataType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
