package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

public class DeviceInfo extends RPCObject {
    public static final String KEY_HARDWARE = "hardware";
    public static final String KEY_FIRMWARE_REV = "firmwareRev";
    public static final String KEY_OS = "os";
    public static final String KEY_OS_VERSION = "osVersion";
    public static final String KEY_CARRIER = "carrier";
    public static final String KEY_MAX_NUMBER_RFCOMM_PORTS = "maxNumberRFCOMMPorts";

    private String hardware, firmware, os, osVersion, carrier;
    private Integer maxNumberRfcommPorts;
    
	public DeviceInfo() { }

	/**
     * Creates an AddCommand object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
	public DeviceInfo(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.hardware = JsonUtils.readStringFromJsonObject(jsonObject, KEY_HARDWARE);
            this.firmware = JsonUtils.readStringFromJsonObject(jsonObject, KEY_FIRMWARE_REV);
            this.os = JsonUtils.readStringFromJsonObject(jsonObject, KEY_OS);
            this.osVersion = JsonUtils.readStringFromJsonObject(jsonObject, KEY_OS_VERSION);
            this.carrier = JsonUtils.readStringFromJsonObject(jsonObject, KEY_CARRIER);
            this.maxNumberRfcommPorts = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_MAX_NUMBER_RFCOMM_PORTS);
            break;
        }
    }
	
    public void setHardware(String hardware) {
         this.hardware = hardware;
    }

    public String getHardware() {
        return this.hardware;
    }
    
    public void setFirmwareRev(String firmwareRev) {
        this.firmware = firmwareRev;
    }

    public String getFirmwareRev() {
        return this.firmware;
    }
    
    public void setOs(String os) {
        this.os = os;
    }

    public String getOs() {
        return this.os;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
    
    public String getOsVersion() {
        return this.osVersion;
    }  
        
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
    
    public String getCarrier() {
       return this.carrier;
    } 
    
    public Integer getMaxNumberRFCOMMPorts() {
        return this.maxNumberRfcommPorts;
    }
    
    public void setMaxNumberRFCOMMPorts( Integer maxNumberRFCOMMPorts ) {
        this.maxNumberRfcommPorts = maxNumberRFCOMMPorts;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_CARRIER, this.carrier);
            JsonUtils.addToJsonObject(result, KEY_FIRMWARE_REV, this.firmware);
            JsonUtils.addToJsonObject(result, KEY_HARDWARE, this.hardware);
            JsonUtils.addToJsonObject(result, KEY_OS, this.os);
            JsonUtils.addToJsonObject(result, KEY_OS_VERSION, this.osVersion);
            JsonUtils.addToJsonObject(result, KEY_MAX_NUMBER_RFCOMM_PORTS, this.maxNumberRfcommPorts);
            break;
        }
        
        return result;
    }

}
