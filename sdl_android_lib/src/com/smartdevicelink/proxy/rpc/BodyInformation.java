package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;
import com.smartdevicelink.util.JsonUtils;

public class BodyInformation extends RPCObject {
    public static final String KEY_PARK_BRAKE_ACTIVE = "parkBrakeActive";
    public static final String KEY_IGNITION_STABLE_STATUS = "ignitionStableStatus";
    public static final String KEY_IGNITION_STATUS = "ignitionStatus";
    public static final String KEY_DRIVER_DOOR_AJAR = "driverDoorAjar";
    public static final String KEY_PASSENGER_DOOR_AJAR = "passengerDoorAjar";
    public static final String KEY_REAR_LEFT_DOOR_AJAR = "rearLeftDoorAjar";
    public static final String KEY_REAR_RIGHT_DOOR_AJAR = "rearRightDoorAjar";

    private String ignitionStableStatus; // represents IgnitionStableStatus enum
    private String ignitionStatus; // represents IgnitionStatus enum
    private Boolean parkingBrakeActive, driverDoorAjar, passengerDoorAjar, 
                    rearLeftDoorAjar, rearRightDoorAjar;
    
    public BodyInformation() { }
    
    /**
     * Creates a BodyInformation object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     * @param sdlVersion The version of SDL represented in the JSON
     */
    public BodyInformation(JSONObject jsonObject, int sdlVersion) {
        switch(sdlVersion){
        default:
            this.ignitionStableStatus = JsonUtils.readStringFromJsonObject(jsonObject, KEY_IGNITION_STABLE_STATUS);
            this.ignitionStatus = JsonUtils.readStringFromJsonObject(jsonObject, KEY_IGNITION_STATUS);
            this.parkingBrakeActive = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_PARK_BRAKE_ACTIVE);
            this.driverDoorAjar = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_DRIVER_DOOR_AJAR);
            this.passengerDoorAjar = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_PASSENGER_DOOR_AJAR);
            this.rearLeftDoorAjar = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_REAR_LEFT_DOOR_AJAR);
            this.rearRightDoorAjar = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_REAR_RIGHT_DOOR_AJAR);
            break;
        }
    }

    public void setParkBrakeActive(Boolean parkBrakeActive) {
        this.parkingBrakeActive = parkBrakeActive;
    }
    
    public Boolean getParkBrakeActive() {
        return this.parkingBrakeActive;
    }
    
    public void setIgnitionStableStatus(IgnitionStableStatus ignitionStableStatus) {
        this.ignitionStableStatus = ignitionStableStatus.getJsonName(sdlVersion);
    }
    
    public IgnitionStableStatus getIgnitionStableStatus() {
        return IgnitionStableStatus.valueForJsonName(this.ignitionStableStatus, sdlVersion);
    }
    
    public void setIgnitionStatus(IgnitionStatus ignitionStatus) {
        this.ignitionStatus = ignitionStatus.getJsonName(sdlVersion);
    }
    
    public IgnitionStatus getIgnitionStatus() {
        return IgnitionStatus.valueForJsonName(this.ignitionStatus, sdlVersion);
    }
    
    public void setDriverDoorAjar(Boolean driverDoorAjar) {
        this.driverDoorAjar = driverDoorAjar;
    }
    
    public Boolean getDriverDoorAjar() {
        return this.driverDoorAjar;
    }
    
    public void setPassengerDoorAjar(Boolean passengerDoorAjar) {
        this.passengerDoorAjar = passengerDoorAjar;
    }
    
    public Boolean getPassengerDoorAjar() {
        return this.passengerDoorAjar;
    }
    
    public void setRearLeftDoorAjar(Boolean rearLeftDoorAjar) {
        this.rearLeftDoorAjar = rearLeftDoorAjar;
    }

    public Boolean getRearLeftDoorAjar() {
        return rearLeftDoorAjar;
    }

    public void setRearRightDoorAjar(Boolean rearRightDoorAjar) {
        this.rearRightDoorAjar = rearRightDoorAjar;
    }

    public Boolean getRearRightDoorAjar() {
        return rearRightDoorAjar;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_IGNITION_STABLE_STATUS, this.ignitionStableStatus);
            JsonUtils.addToJsonObject(result, KEY_IGNITION_STATUS, this.ignitionStatus);
            JsonUtils.addToJsonObject(result, KEY_PARK_BRAKE_ACTIVE, this.parkingBrakeActive);
            JsonUtils.addToJsonObject(result, KEY_DRIVER_DOOR_AJAR, this.driverDoorAjar);
            JsonUtils.addToJsonObject(result, KEY_PASSENGER_DOOR_AJAR, this.passengerDoorAjar);
            JsonUtils.addToJsonObject(result, KEY_REAR_LEFT_DOOR_AJAR, this.rearLeftDoorAjar);
            JsonUtils.addToJsonObject(result, KEY_REAR_RIGHT_DOOR_AJAR, this.rearRightDoorAjar);
            break;
        }
        
        return result;
    }
}
