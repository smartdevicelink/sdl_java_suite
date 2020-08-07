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
package com.smartdevicelink.proxy.rpc;


import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.TurnSignal;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;
import java.util.List;

/**
 * Get Vehicle Data Response is sent, when GetVehicleData has been called.
 * 
 * @since SmartDeviceLink 2.0
 */
public class GetVehicleDataResponse extends RPCResponse {
	public static final String KEY_SPEED = "speed";
	public static final String KEY_RPM = "rpm";
	public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
	public static final String KEY_FUEL_LEVEL = "fuelLevel";
	public static final String KEY_VIN = "vin";
	public static final String KEY_PRNDL = "prndl";
	public static final String KEY_TIRE_PRESSURE = "tirePressure";
	public static final String KEY_ENGINE_TORQUE = "engineTorque";
	public static final String KEY_ENGINE_OIL_LIFE = "engineOilLife";
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
	public static final String KEY_FUEL_RANGE = "fuelRange";
	public static final String KEY_TURN_SIGNAL = "turnSignal";
	public static final String KEY_ELECTRONIC_PARK_BRAKE_STATUS = "electronicParkBrakeStatus";
    public static final String KEY_CLOUD_APP_VEHICLE_ID = "cloudAppVehicleID";
    public static final String KEY_WINDOW_STATUS = "windowStatus";
    public static final String KEY_HANDS_OFF_STEERING = "handsOffSteering";

	/** 
	 * Constructs a new GetVehicleDataResponse object
	 */

    public GetVehicleDataResponse() {
        super(FunctionID.GET_VEHICLE_DATA.toString());
    }
    public GetVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new GetVehicleDataResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public GetVehicleDataResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
    public void setGps(GPSData gps) {
        setParameters(KEY_GPS, gps);
    }
    @SuppressWarnings("unchecked")
    public GPSData getGps() {
        return (GPSData) getObject(GPSData.class, KEY_GPS);
    }
    public void setSpeed(Double speed) {
        setParameters(KEY_SPEED, speed);
    }
    public Double getSpeed() {
    	Object object = getParameters(KEY_SPEED);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    public void setRpm(Integer rpm) {
        setParameters(KEY_RPM, rpm);
    }
    public Integer getRpm() {
    	return getInteger(KEY_RPM);
    }
    public void setFuelLevel(Double fuelLevel) {
        setParameters(KEY_FUEL_LEVEL, fuelLevel);
    }
    public Double getFuelLevel() {
    	Object object = getParameters(KEY_FUEL_LEVEL);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    @Deprecated
    public void setFuelLevel_State(ComponentVolumeStatus fuelLevel_State) {
        setFuelLevelState(fuelLevel_State);
    }
    @Deprecated
    public ComponentVolumeStatus getFuelLevel_State() {
        return getFuelLevelState();
    }
    public void setFuelLevelState(ComponentVolumeStatus fuelLevelState) {
        setParameters(KEY_FUEL_LEVEL_STATE, fuelLevelState);
    }
    public ComponentVolumeStatus getFuelLevelState() {
        return (ComponentVolumeStatus) getObject(ComponentVolumeStatus.class, KEY_FUEL_LEVEL_STATE);
    }
    public void setInstantFuelConsumption(Double instantFuelConsumption) {
        setParameters(KEY_INSTANT_FUEL_CONSUMPTION, instantFuelConsumption);
    }
    public Double getInstantFuelConsumption() {
    	Object object = getParameters(KEY_INSTANT_FUEL_CONSUMPTION);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    public void setExternalTemperature(Double externalTemperature) {
        setParameters(KEY_EXTERNAL_TEMPERATURE, externalTemperature);
    }
    public Double getExternalTemperature() {
    	Object object = getParameters(KEY_EXTERNAL_TEMPERATURE);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    public void setVin(String vin) {
        setParameters(KEY_VIN, vin);
    }
    public String getVin() {
    	return getString(KEY_VIN);
    }
    public void setPrndl(PRNDL prndl) {
        setParameters(KEY_PRNDL, prndl);
    }
    public PRNDL getPrndl() {
        return (PRNDL) getObject(PRNDL.class, KEY_PRNDL);
    }
    public void setTirePressure(TireStatus tirePressure) {
        setParameters(KEY_TIRE_PRESSURE, tirePressure);
    }
    @SuppressWarnings("unchecked")
    public TireStatus getTirePressure() {
        return (TireStatus) getObject(TireStatus.class, KEY_TIRE_PRESSURE);
    }
    public void setOdometer(Integer odometer) {
        setParameters(KEY_ODOMETER, odometer);
    }
    public Integer getOdometer() {
    	return getInteger(KEY_ODOMETER);
    }
    public void setBeltStatus(BeltStatus beltStatus) {
        setParameters(KEY_BELT_STATUS, beltStatus);
    }
    @SuppressWarnings("unchecked")
    public BeltStatus getBeltStatus() {
        return (BeltStatus) getObject(BeltStatus.class, KEY_BELT_STATUS);
    }
    public void setBodyInformation(BodyInformation bodyInformation) {
        setParameters(KEY_BODY_INFORMATION, bodyInformation);
    }
    @SuppressWarnings("unchecked")
    public BodyInformation getBodyInformation() {
        return (BodyInformation) getObject(BodyInformation.class, KEY_BODY_INFORMATION);
    }
    public void setDeviceStatus(DeviceStatus deviceStatus) {
        setParameters(KEY_DEVICE_STATUS, deviceStatus);
    }
    @SuppressWarnings("unchecked")
    public DeviceStatus getDeviceStatus() {
        return (DeviceStatus) getObject(DeviceStatus.class, KEY_DEVICE_STATUS);
    }
    public void setDriverBraking(VehicleDataEventStatus driverBraking) {
        setParameters(KEY_DRIVER_BRAKING, driverBraking);
    }
    public VehicleDataEventStatus getDriverBraking() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_BRAKING);
    }
    public void setWiperStatus(WiperStatus wiperStatus) {
        setParameters(KEY_WIPER_STATUS, wiperStatus);
    }
    public WiperStatus getWiperStatus() {
        return (WiperStatus) getObject(WiperStatus.class, KEY_WIPER_STATUS);
    }
  
    public void setHeadLampStatus(HeadLampStatus headLampStatus) {
        setParameters(KEY_HEAD_LAMP_STATUS, headLampStatus);
    }
    @SuppressWarnings("unchecked")
    public HeadLampStatus getHeadLampStatus() {
        return (HeadLampStatus) getObject(HeadLampStatus.class, KEY_HEAD_LAMP_STATUS);
    }
    public void setEngineTorque(Double engineTorque) {
        setParameters(KEY_ENGINE_TORQUE, engineTorque);
    }
    public Double getEngineTorque() {
        Object object = getParameters(KEY_ENGINE_TORQUE);
        return SdlDataTypeConverter.objectToDouble(object);
    }
    public void setEngineOilLife(Float engineOilLife) {
        setParameters(KEY_ENGINE_OIL_LIFE, engineOilLife);
    }
    public Float getEngineOilLife() {
        Object object = getParameters(KEY_ENGINE_OIL_LIFE);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    public void setAccPedalPosition(Double accPedalPosition) {
        setParameters(KEY_ACC_PEDAL_POSITION, accPedalPosition);
    }
    public Double getAccPedalPosition() {
    	Object object = getParameters(KEY_ACC_PEDAL_POSITION);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
        
    public void setSteeringWheelAngle(Double steeringWheelAngle) {
        setParameters(KEY_STEERING_WHEEL_ANGLE, steeringWheelAngle);
    }
    public Double getSteeringWheelAngle() {
    	Object object = getParameters(KEY_STEERING_WHEEL_ANGLE);
    	return SdlDataTypeConverter.objectToDouble(object);
    }    

    public void setECallInfo(ECallInfo eCallInfo) {
        setParameters(KEY_E_CALL_INFO, eCallInfo);
    }
    @SuppressWarnings("unchecked")
    public ECallInfo getECallInfo() {
        return (ECallInfo) getObject(ECallInfo.class, KEY_E_CALL_INFO);
    }	
	
    public void setAirbagStatus(AirbagStatus airbagStatus) {
        setParameters(KEY_AIRBAG_STATUS, airbagStatus);
    }
    @SuppressWarnings("unchecked")
    public AirbagStatus getAirbagStatus() {
        return (AirbagStatus) getObject(AirbagStatus.class, KEY_AIRBAG_STATUS);
    }	

    public void setEmergencyEvent(EmergencyEvent emergencyEvent) {
        setParameters(KEY_EMERGENCY_EVENT, emergencyEvent);
    }
    @SuppressWarnings("unchecked")
    public EmergencyEvent getEmergencyEvent() {
        return (EmergencyEvent) getObject(EmergencyEvent.class, KEY_EMERGENCY_EVENT);
    }
    public void setClusterModeStatus(ClusterModeStatus clusterModeStatus) {
        setParameters(KEY_CLUSTER_MODE_STATUS, clusterModeStatus);
    }
    @SuppressWarnings("unchecked")
    public ClusterModeStatus getClusterModeStatus() {
        return (ClusterModeStatus) getObject(ClusterModeStatus.class, KEY_CLUSTER_MODE_STATUS);
    }
    public void setMyKey(MyKey myKey) {
        setParameters(KEY_MY_KEY, myKey);
    }
    @SuppressWarnings("unchecked")
    public MyKey getMyKey() {
        return (MyKey) getObject(MyKey.class, KEY_MY_KEY);
    }

    /**
     * Sets Fuel Range List. Fuel Range - The estimate range in KM the vehicle can travel based on fuel level and consumption.
     * @param fuelRange the range in KM left as well as fuel type
     *
     * @see com.smartdevicelink.proxy.rpc.FuelRange
     * @see com.smartdevicelink.proxy.rpc.enums.FuelType
     */
    public void setFuelRange(List<FuelRange> fuelRange) {
        setParameters(KEY_FUEL_RANGE, fuelRange);
    }

    /**
     * Gets Fuel Range List
     * @return List<FuelRange>
     *     Fuel Range - The estimate range in KM the vehicle can travel based on fuel level and consumption.
     */
    @SuppressWarnings("unchecked")
    public List<FuelRange> getFuelRange() {
        return (List<FuelRange>) getObject(FuelRange.class, KEY_FUEL_RANGE);
    }

    /**
     * Sets turnSignal
     * @param turnSignal status of the turn signals
     *
     * @see com.smartdevicelink.proxy.rpc.enums.TurnSignal
     */
    public void setTurnSignal(TurnSignal turnSignal) {
        setParameters(KEY_TURN_SIGNAL, turnSignal);
    }

    /**
     * Gets turnSignal
     * @return TurnSignal status of the turn signals
     *
     * @see com.smartdevicelink.proxy.rpc.enums.TurnSignal
     */
    @SuppressWarnings("unchecked")
    public TurnSignal getTurnSignal() {
        return (TurnSignal) getObject(TurnSignal.class, KEY_TURN_SIGNAL);
    }

    /**
     * Sets electronicParkBrakeStatus
     * @param electronicParkBrakeStatus status of the electronic park brake of the connected vehicle
     *
     * @see com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus
     */
    public void setElectronicParkBrakeStatus(ElectronicParkBrakeStatus electronicParkBrakeStatus){
        setParameters(KEY_ELECTRONIC_PARK_BRAKE_STATUS, electronicParkBrakeStatus);
    }

    /**
     * Gets electronicParkBrakeStatus
     * @return ElectronicParkBrakeStatus status of the electronic park brake of the connected vehicle
     *
     * @see com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus
     */
    public ElectronicParkBrakeStatus getElectronicParkBrakeStatus(){
        return (ElectronicParkBrakeStatus) getObject(ElectronicParkBrakeStatus.class, KEY_ELECTRONIC_PARK_BRAKE_STATUS);
    }

    /**
     * Sets a string value for the cloud app vehicle ID
     * @param cloudAppVehicleID a string value
     */
    public void setCloudAppVehicleID(String cloudAppVehicleID){
        setParameters(KEY_CLOUD_APP_VEHICLE_ID, cloudAppVehicleID);
    }

    /**
     * Gets a String value of the returned cloud app vehicle ID
     * @return a String value.
     */
    public String getCloudAppVehicleID(){
        return getString(KEY_CLOUD_APP_VEHICLE_ID);
    }


    /**
     * Sets a value for OEM Custom VehicleData.
     * @param vehicleDataName a String value
     * @param vehicleDataState a VehicleDataResult value
     */
    public void setOEMCustomVehicleData(String vehicleDataName, Object vehicleDataState){
        setParameters(vehicleDataName, vehicleDataState);
    }

    /**
     * Gets a VehicleData value for the vehicle data item.
     * @return a Object related to the vehicle data
     */
    public Object getOEMCustomVehicleData(String vehicleDataName){
        return getParameters(vehicleDataName);
    }

    /**
     * Sets the windowStatus.
     *
     * @param windowStatus See WindowStatus
     * @since SmartDeviceLink 7.0.0
     */
    public void setWindowStatus(List<WindowStatus> windowStatus) {
        setParameters(KEY_WINDOW_STATUS, windowStatus);
    }

    /**
     * Gets the windowStatus.
     *
     * @return List<WindowStatus> See WindowStatus
     * @since SmartDeviceLink 7.0.0
     */
    @SuppressWarnings("unchecked")
    public List<WindowStatus> getWindowStatus() {
        return (List<WindowStatus>) getObject(WindowStatus.class, KEY_WINDOW_STATUS);
    }

    /**
     * Sets the handsOffSteering.
     *
     * @param handsOffSteering To indicate whether driver hands are off the steering wheel
     * @since SmartDeviceLink 7.0.0
     */
    public void setHandsOffSteering(Boolean handsOffSteering) {
        setParameters(KEY_HANDS_OFF_STEERING, handsOffSteering);
    }

    /**
     * Gets the handsOffSteering.
     *
     * @return Boolean To indicate whether driver hands are off the steering wheel
     * @since SmartDeviceLink 7.0.0
     */
    public Boolean getHandsOffSteering() {
        return getBoolean(KEY_HANDS_OFF_STEERING);
    }
}
