package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.util.JsonUtils;
import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonParameters;

public class AirbagStatus implements JsonParameters {
    public static final String KEY_DRIVER_AIRBAG_DEPLOYED = "driverAirbagDeployed";
    public static final String KEY_DRIVER_SIDE_AIRBAG_DEPLOYED = "driverSideAirbagDeployed";
    public static final String KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED = "driverCurtainAirbagDeployed";
    public static final String KEY_DRIVER_KNEE_AIRBAG_DEPLOYED = "driverKneeAirbagDeployed";
    public static final String KEY_PASSENGER_AIRBAG_DEPLOYED = "passengerAirbagDeployed";
    public static final String KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED = "passengerSideAirbagDeployed";
    public static final String KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED = "passengerCurtainAirbagDeployed";
    public static final String KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED = "passengerKneeAirbagDeployed";

    private int sdlVersion = -1; // TODO: default to "SDL_VERSION_INVALID" or something pre-defined
    
    // TODO: refactor these strings into a HashMap<String, String> object and implement AirbagZone enum for different
    //       airbags.  This will allow us to loop and utilize a single API instead of having multiple setter/getters
    //       for the same type of data.  Similar to VehicleData re-design.
    private String driverAirbagDeployed, driverSideAirbagDeployed, driverCurtainAirbagDeployed,
        driverKneeAirbagDeployed, passengerAirbagDeployed, passengerSideAirbagDeployed, passengerCurtainAirbagDeployed,
        passengerKneeAirbagDeployed;
    
    public AirbagStatus() { }
    
    public AirbagStatus(JSONObject json, int sdlVersion){
        this.sdlVersion = sdlVersion;
        switch(sdlVersion){
        default:
            this.driverAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_DRIVER_AIRBAG_DEPLOYED);
            this.driverSideAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_DRIVER_SIDE_AIRBAG_DEPLOYED);
            this.driverCurtainAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED);
            this.driverKneeAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_DRIVER_KNEE_AIRBAG_DEPLOYED);
            this.passengerAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_PASSENGER_AIRBAG_DEPLOYED);
            this.passengerSideAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED);
            this.passengerCurtainAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED);
            this.passengerKneeAirbagDeployed = JsonUtils.readStringFromJsonObject(json, KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED);
            break;
        }
    }

    public void setDriverAirbagDeployed(VehicleDataEventStatus driverAirbagDeployed) {
        this.driverAirbagDeployed = driverAirbagDeployed.getJsonName(this.sdlVersion);
    }
    
    public VehicleDataEventStatus getDriverAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.driverAirbagDeployed, this.sdlVersion);
    }
    
    public void setDriverSideAirbagDeployed(VehicleDataEventStatus driverSideAirbagDeployed) {
        this.driverSideAirbagDeployed = driverSideAirbagDeployed.getJsonName(this.sdlVersion);
    }
    
    public VehicleDataEventStatus getDriverSideAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.driverAirbagDeployed, this.sdlVersion);
    }
    
    public void setDriverCurtainAirbagDeployed(VehicleDataEventStatus driverCurtainAirbagDeployed) {
        this.driverCurtainAirbagDeployed = driverCurtainAirbagDeployed.getJsonName(this.sdlVersion);
    }
    
    public VehicleDataEventStatus getDriverCurtainAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.driverCurtainAirbagDeployed, this.sdlVersion);
    }
    
    public void setPassengerAirbagDeployed(VehicleDataEventStatus passengerAirbagDeployed) {
        this.passengerAirbagDeployed = passengerAirbagDeployed.getJsonName(this.sdlVersion);
    }
    
    public VehicleDataEventStatus getPassengerAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.passengerAirbagDeployed, this.sdlVersion);
    }
    
    public void setPassengerCurtainAirbagDeployed(VehicleDataEventStatus passengerCurtainAirbagDeployed) {
        this.passengerCurtainAirbagDeployed = passengerCurtainAirbagDeployed.getJsonName(this.sdlVersion);
    }
    
    public VehicleDataEventStatus getPassengerCurtainAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.passengerCurtainAirbagDeployed, this.sdlVersion);
    }
    
    public void setDriverKneeAirbagDeployed(VehicleDataEventStatus driverKneeAirbagDeployed) {
        this.driverKneeAirbagDeployed = driverKneeAirbagDeployed.getJsonName(this.sdlVersion);
    }
    
    public VehicleDataEventStatus getDriverKneeAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.driverKneeAirbagDeployed, this.sdlVersion);
    }
    
    public void setPassengerSideAirbagDeployed(VehicleDataEventStatus passengerSideAirbagDeployed) {
        this.passengerSideAirbagDeployed = passengerSideAirbagDeployed.getJsonName(this.sdlVersion);
    }
    
    public VehicleDataEventStatus getPassengerSideAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.passengerSideAirbagDeployed, this.sdlVersion);
    }
    
    public void setPassengerKneeAirbagDeployed(VehicleDataEventStatus passengerKneeAirbagDeployed) {
        this.passengerKneeAirbagDeployed = passengerKneeAirbagDeployed.getJsonName(this.sdlVersion);
    }
    
    public VehicleDataEventStatus getPassengerKneeAirbagDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.passengerKneeAirbagDeployed, this.sdlVersion);
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = new JSONObject();
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_DRIVER_AIRBAG_DEPLOYED, this.driverAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_DRIVER_SIDE_AIRBAG_DEPLOYED, this.driverSideAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED, this.driverCurtainAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_DRIVER_KNEE_AIRBAG_DEPLOYED, this.driverKneeAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_PASSENGER_AIRBAG_DEPLOYED, this.passengerAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED, this.passengerSideAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED, this.passengerCurtainAirbagDeployed);
            JsonUtils.addToJsonObject(result, KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED, this.passengerKneeAirbagDeployed);
            break;
        }
        
        return result;
    }
}
