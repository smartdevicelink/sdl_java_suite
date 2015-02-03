package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.JsonUtils;

public class OnVehicleData extends RPCNotification {
	public static final String KEY_SPEED = "speed";
	public static final String KEY_RPM = "rpm";
	public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
	public static final String KEY_FUEL_LEVEL = "fuelLevel";
	public static final String KEY_VIN = "vin";
	public static final String KEY_PRNDL = "prndl";
	public static final String KEY_TIRE_PRESSURE = "tirePressure";
	public static final String KEY_ENGINE_TORQUE = "engineTorque";
	public static final String KEY_ODOMETER = "odometer";
	public static final String KEY_GPS = "gps";
	public static final String KEY_FUEL_LEVEL_STATE = "fuelLevel_State";
	public static final String KEY_INSTANT_FUEL_CONSUMPTION = "instantFuelConsumption";
	public static final String KEY_BELT_STATUS = "beltStatus";
	public static final String KEY_BODY_INFORMATION = "bodyInformation";
	public static final String KEY_DEVICE_STATUS = "deviceStatus";
	public static final String KEY_DRIVER_BRAKING = "driverBraking";
	public static final String KEY_WIPER_STATUS = "wiperStatus";
	public static final String KEY_HEAD_LAMP_STATUS = "headLampStatus";
	public static final String KEY_ACC_PEDAL_POSITION = "accPedalPosition";
	public static final String KEY_STEERING_WHEEL_ANGLE = "steeringWheelAngle";
	public static final String KEY_E_CALL_INFO = "eCallInfo";
	public static final String KEY_AIRBAG_STATUS = "airbagStatus";
	public static final String KEY_EMERGENCY_EVENT = "emergencyEvent";
	public static final String KEY_CLUSTER_MODE_STATUS = "clusterModeStatus";
	public static final String KEY_MY_KEY = "myKey";

    // TODO: refactor this class with new VehicleData design
    private GPSData gps;
    private TireStatus tirePressure;
    private BeltStatus beltStatus;
    private BodyInformation bodyInformation;
    private DeviceStatus deviceStatus;
    private HeadLampStatus headLampStatus;
    private ECallInfo eCallInfo;
    private AirbagStatus airbagStatus;
    private EmergencyEvent emergencyEvent;
    private MyKey myKey;
    private ClusterModeStatus clusterModeStatus;
    private Double speed, fuelLevel, instantFuelConsumption, externalTemperature, engineTorque, accPedalPosition,
        steeringWheelAngle;
    private Integer rpm, odometer;
    private String vin;
    private String wiperStatus; // represents WiperStatus enum
    private String fuelLevelState; // represents ComponentVolumeStatus enum
    private String prndl; // represents PRNDL enum
    private String driverBraking; // represents VehicleDataEventStatus enum

    public OnVehicleData() {
        super(FunctionID.ON_VEHICLE_DATA);
    }
    
    /**
     * Creates a GetVehicleDataResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnVehicleData(JSONObject jsonObject) {
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.speed = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_SPEED);
            this.externalTemperature = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_EXTERNAL_TEMPERATURE);
            this.fuelLevel = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_FUEL_LEVEL);
            this.engineTorque = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_ENGINE_TORQUE);
            this.instantFuelConsumption = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_INSTANT_FUEL_CONSUMPTION);
            this.accPedalPosition = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_ACC_PEDAL_POSITION);
            this.steeringWheelAngle = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_STEERING_WHEEL_ANGLE);
            
            this.rpm = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_RPM);
            this.odometer = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_ODOMETER);
            
            this.vin = JsonUtils.readStringFromJsonObject(jsonObject, KEY_VIN);
            this.prndl = JsonUtils.readStringFromJsonObject(jsonObject, KEY_PRNDL);
            this.fuelLevelState = JsonUtils.readStringFromJsonObject(jsonObject, KEY_FUEL_LEVEL_STATE);
            this.driverBraking = JsonUtils.readStringFromJsonObject(jsonObject, KEY_DRIVER_BRAKING);
            this.wiperStatus = JsonUtils.readStringFromJsonObject(jsonObject, KEY_WIPER_STATUS);
            
            JSONObject temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_TIRE_PRESSURE);
            if(temp != null){
                this.tirePressure = new TireStatus(temp);
            }
            
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_GPS);
            if(temp != null){
                this.gps = new GPSData(temp);
            }
            
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_BELT_STATUS);
            if(temp != null){
                this.beltStatus = new BeltStatus(temp);
            }
            
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_BODY_INFORMATION);
            if(temp != null){
                this.bodyInformation = new BodyInformation(temp);
            }
            
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_DEVICE_STATUS);
            if(temp != null){
                this.deviceStatus = new DeviceStatus(temp);
            }
            
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_HEAD_LAMP_STATUS);
            if(temp != null){
                this.headLampStatus = new HeadLampStatus(temp);
            }
            
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_E_CALL_INFO);
            if(temp != null){
                this.eCallInfo = new ECallInfo(temp);
            }
            
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_AIRBAG_STATUS);
            if(temp != null){
                this.airbagStatus = new AirbagStatus(temp);
            }
            
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_EMERGENCY_EVENT);
            if(temp != null){
                this.emergencyEvent = new EmergencyEvent(temp);
            }
            
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_MY_KEY);
            if(temp != null){
                this.myKey = new MyKey(temp);
            }
            
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_CLUSTER_MODE_STATUS);
            if(temp != null){
                this.clusterModeStatus = new ClusterModeStatus(temp);
            }
            
            break;
        }
    }
    
    public void setGps(GPSData gps) {
        this.gps = gps;
    }
    
    public GPSData getGps() {
        return this.gps;
    }
    
    public void setSpeed(Double speed) {
        this.speed = speed;
    }
    
    public Double getSpeed() {
        return this.speed;
    }
    
    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }
    
    public Integer getRpm() {
        return this.rpm;
    }
    
    public void setFuelLevel(Double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }
    
    public Double getFuelLevel() {
        return this.fuelLevel;
    }
    
    public void setFuelLevel_State(ComponentVolumeStatus fuelLevel_State) {
        this.fuelLevelState = (fuelLevel_State == null) ? null : fuelLevel_State.getJsonName(sdlVersion);
    }
    
    public ComponentVolumeStatus getFuelLevel_State() {
        return ComponentVolumeStatus.valueForJsonName(this.fuelLevelState, sdlVersion);
    }
    
    public void setInstantFuelConsumption(Double instantFuelConsumption) {
        this.instantFuelConsumption = instantFuelConsumption;
    }
    
    public Double getInstantFuelConsumption() {
        return this.instantFuelConsumption;
    }
    
    public void setExternalTemperature(Double externalTemperature) {
        this.externalTemperature = externalTemperature;
    }
    
    public Double getExternalTemperature() {
        return this.externalTemperature;
    }
    
    public void setVin(String vin) {
        this.vin = vin;
    }
    
    public String getVin() {
        return this.vin;
    }
    
    public void setPrndl(PRNDL prndl) {
        this.prndl = (prndl == null) ? null : prndl.getJsonName(sdlVersion);
    }
    
    public PRNDL getPrndl() {
        return PRNDL.valueForJsonName(this.prndl, sdlVersion);
    }
    
    public void setTirePressure(TireStatus tirePressure) {
        this.tirePressure = tirePressure;
    }
    
    public TireStatus getTirePressure() {
        return this.tirePressure;
    }
    
    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }
    
    public Integer getOdometer() {
        return this.odometer;
    }
    
    public void setBeltStatus(BeltStatus beltStatus) {
        this.beltStatus = beltStatus;
    }
    
    public BeltStatus getBeltStatus() {
        return this.beltStatus;
    }
    
    public void setBodyInformation(BodyInformation bodyInformation) {
        this.bodyInformation = bodyInformation;
    }
    
    public BodyInformation getBodyInformation() {
        return this.bodyInformation;
    }
    
    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
    
    public DeviceStatus getDeviceStatus() {
        return this.deviceStatus;
    }
    
    public void setDriverBraking(VehicleDataEventStatus driverBraking) {
        this.driverBraking = (driverBraking == null) ? null : driverBraking.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getDriverBraking() {
        return VehicleDataEventStatus.valueForJsonName(this.driverBraking, sdlVersion);
    }
    
    public void setWiperStatus(WiperStatus wiperStatus) {
        this.wiperStatus = (wiperStatus == null) ? null : wiperStatus.getJsonName(sdlVersion);
    }
    
    public WiperStatus getWiperStatus() {
        return WiperStatus.valueForJsonName(this.wiperStatus, sdlVersion);
    }
  
    public void setHeadLampStatus(HeadLampStatus headLampStatus) {
        this.headLampStatus = headLampStatus;
    }
    
    public HeadLampStatus getHeadLampStatus() {
        return this.headLampStatus;
    }
    
    public void setEngineTorque(Double engineTorque) {
        this.engineTorque = engineTorque;
    }
    
    public Double getEngineTorque() {
        return this.engineTorque;
    }

    public void setAccPedalPosition(Double accPedalPosition) {
        this.accPedalPosition = accPedalPosition;
    }
    
    public Double getAccPedalPosition() {
        return this.accPedalPosition;
    }
        
    public void setSteeringWheelAngle(Double steeringWheelAngle) {
        this.steeringWheelAngle = steeringWheelAngle;
    }
    
    public Double getSteeringWheelAngle() {
        return this.steeringWheelAngle;
    }    

    public void setECallInfo(ECallInfo eCallInfo) {
        this.eCallInfo = eCallInfo;
    }
    
    public ECallInfo getECallInfo() {
        return this.eCallInfo;
    }   
    
    public void setAirbagStatus(AirbagStatus airbagStatus) {
        this.airbagStatus = airbagStatus;
    }
    
    public AirbagStatus getAirbagStatus() {
        return this.airbagStatus;
    }   

    public void setEmergencyEvent(EmergencyEvent emergencyEvent) {
        this.emergencyEvent = emergencyEvent;
    }
    
    public EmergencyEvent getEmergencyEvent() {
        return this.emergencyEvent;
    }
    
    public void setClusterModeStatus(ClusterModeStatus clusterModeStatus) {
        this.clusterModeStatus = clusterModeStatus;
    }
    
    public ClusterModeStatus getClusterModeStatus() {
        return this.clusterModeStatus;
    }
    
    public void setMyKey(MyKey myKey) {
        this.myKey = myKey;
    }
    
    public MyKey getMyKey() {
        return this.myKey;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_SPEED, this.speed);
            JsonUtils.addToJsonObject(result, KEY_EXTERNAL_TEMPERATURE, this.externalTemperature);
            JsonUtils.addToJsonObject(result, KEY_FUEL_LEVEL, this.fuelLevel);
            JsonUtils.addToJsonObject(result, KEY_ENGINE_TORQUE, this.engineTorque);
            JsonUtils.addToJsonObject(result, KEY_INSTANT_FUEL_CONSUMPTION, this.instantFuelConsumption);
            JsonUtils.addToJsonObject(result, KEY_ACC_PEDAL_POSITION, this.accPedalPosition);
            JsonUtils.addToJsonObject(result, KEY_STEERING_WHEEL_ANGLE, this.steeringWheelAngle);
            JsonUtils.addToJsonObject(result, KEY_RPM, this.rpm);
            JsonUtils.addToJsonObject(result, KEY_ODOMETER, this.odometer);
            JsonUtils.addToJsonObject(result, KEY_VIN, this.vin);
            JsonUtils.addToJsonObject(result, KEY_PRNDL, this.prndl);
            JsonUtils.addToJsonObject(result, KEY_FUEL_LEVEL_STATE, this.fuelLevelState);
            JsonUtils.addToJsonObject(result, KEY_DRIVER_BRAKING, this.driverBraking);
            JsonUtils.addToJsonObject(result, KEY_WIPER_STATUS, this.wiperStatus);
            
            JsonUtils.addToJsonObject(result, KEY_TIRE_PRESSURE, 
                    (this.tirePressure == null) ? null : this.tirePressure.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_GPS, 
                    (this.gps == null) ? null : this.gps.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_BELT_STATUS, 
                    (this.beltStatus == null) ? null : this.beltStatus.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_BODY_INFORMATION, 
                    (this.bodyInformation == null) ? null : this.bodyInformation.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_DEVICE_STATUS, 
                    (this.deviceStatus == null) ? null : this.deviceStatus.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_HEAD_LAMP_STATUS, 
                    (this.headLampStatus == null) ? null : this.headLampStatus.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_E_CALL_INFO, 
                    (this.eCallInfo == null) ? null : this.eCallInfo.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_AIRBAG_STATUS, 
                    (this.airbagStatus == null) ? null : this.airbagStatus.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_EMERGENCY_EVENT, 
                    (this.emergencyEvent == null) ? null : this.emergencyEvent.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_MY_KEY, 
                    (this.myKey == null) ? null : this.myKey.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_CLUSTER_MODE_STATUS, 
                    (this.clusterModeStatus == null) ? null : this.clusterModeStatus.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }
}
