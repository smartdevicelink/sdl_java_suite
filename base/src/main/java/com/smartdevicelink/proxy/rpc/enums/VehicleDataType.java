/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
     * Notifies ENGINEOILLIFEData may be subscribed
     */
    VEHICLEDATA_ENGINEOILLIFE,
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
    VEHICLEDATA_MYKEY,

    VEHICLEDATA_FUELRANGE,

    /**
     * Notifies TURNSIGNALData may be subscribed
     */
    VEHICLEDATA_TURNSIGNAL,

    /**
     * Notifies ELECTRONICPARKBRAKESTATUSData may be subscribed
     */
    VEHICLEDATA_ELECTRONICPARKBRAKESTATUS,

    /**
     * Notifies VEHICLEDATA_CLOUDAPPVEHICLEID may be subscribed
     */
    VEHICLEDATA_CLOUDAPPVEHICLEID,
    /**
     * Notifies VEHICLEDATA_OEM_CUSTOM_DATA may be subscribed
     *
     * @since SmartDeviceLink 6.0
     */
    VEHICLEDATA_OEM_CUSTOM_DATA,


    VEHICLEDATA_STABILITYCONTROLSSTATUS;
    ;

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
