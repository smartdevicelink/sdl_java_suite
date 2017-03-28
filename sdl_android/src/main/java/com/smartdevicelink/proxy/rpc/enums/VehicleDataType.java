package com.smartdevicelink.proxy.rpc.enums;

/**
 * Defines the vehicle data types that can be published and subscribed to.
 * 
 */
public enum VehicleDataType {
	/**
	 * Notifies GPSData may be subscribed
	 */
	VEHICLEDATA_GPS,
	/**
	 * Notifies SPEED Data may be subscribed
	 */
    VEHICLEDATA_SPEED,
    /**
     * Notifies RPMData may be subscribed
     */
    VEHICLEDATA_RPM,
    /**
     * Notifies FUELLEVELData may be subscribed
     */
    VEHICLEDATA_FUELLEVEL,
    /**
     * Notifies FUELLEVEL_STATEData may be subscribed
     */
    VEHICLEDATA_FUELLEVEL_STATE,
/**
 * Notifies FUELCONSUMPTIONData may be subscribed
 */
    VEHICLEDATA_FUELCONSUMPTION,
    /**
     * Notifies EXTERNTEMPData may be subscribed
     */
    VEHICLEDATA_EXTERNTEMP,
    /**
     * Notifies VINData may be subscribed
     */
    VEHICLEDATA_VIN,
    /**
     * Notifies PRNDLData may be subscribed
     */
    VEHICLEDATA_PRNDL,
    /**
     * Notifies TIREPRESSUREData may be subscribed
     */
    VEHICLEDATA_TIREPRESSURE,
    /**
     * Notifies ODOMETERData may be subscribed
     */
    VEHICLEDATA_ODOMETER,   
    /**
     * Notifies BELTSTATUSData may be subscribed
     */
    VEHICLEDATA_BELTSTATUS,
    /**
     * Notifies BODYINFOData may be subscribed
     */
    VEHICLEDATA_BODYINFO,
    /**
     * Notifies DEVICESTATUSData may be subscribed
     */
    VEHICLEDATA_DEVICESTATUS,
    /**
     * Notifies BRAKINGData may be subscribed
     */
    VEHICLEDATA_BRAKING,
    /**
     * Notifies WIPERSTATUSData may be subscribed
     */
    VEHICLEDATA_WIPERSTATUS,
    /**
     * Notifies HEADLAMPSTATUSData may be subscribed
     */
    VEHICLEDATA_HEADLAMPSTATUS,
    /**
     * Notifies BATTVOLTAGEData may be subscribed
     */
    VEHICLEDATA_BATTVOLTAGE,
    /**
     * Notifies EGINETORQUEData may be subscribed
     */
    VEHICLEDATA_ENGINETORQUE,
    /**
     * Notifies ACCPEDALData may be subscribed
     */
    VEHICLEDATA_ACCPEDAL,
    /**
     * Notifies STEERINGWHEELData may be subscribed
     */
    VEHICLEDATA_STEERINGWHEEL,
    /**
     * Notifies ECALLINFOData may be subscribed
     */
    VEHICLEDATA_ECALLINFO,
    /**
     * Notifies AIRBAGSTATUSData may be subscribed
     */
    VEHICLEDATA_AIRBAGSTATUS,
    /**
     * Notifies EMERGENCYEVENTData may be subscribed
     */
    VEHICLEDATA_EMERGENCYEVENT,
    /**
     * Notifies CLUSTERMODESTATUSData may be subscribed
     */
    VEHICLEDATA_CLUSTERMODESTATUS,
    /**
     * Notifies MYKEYData may be subscribed
     */
    VEHICLEDATA_MYKEY;
	/**
     * Convert String to VehicleDataType
     * @param value String
     * @return VehicleDataType
     */

    public static VehicleDataType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
