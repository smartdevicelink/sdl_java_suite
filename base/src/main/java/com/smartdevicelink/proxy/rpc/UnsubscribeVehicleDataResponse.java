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
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

/**
 * Unsubscribe Vehicle Data Response is sent, when UnsubscribeVehicleData has been called.
 *
 * @since SmartDeviceLink 2.0
 */
public class UnsubscribeVehicleDataResponse extends RPCResponse {
	public static final String KEY_SPEED = "speed";
	public static final String KEY_RPM = "rpm";
	public static final String KEY_FUEL_LEVEL = "fuelLevel";
	public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
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
    public static final String KEY_HANDS_OFF_STEERING = "handsOffSteering";

	/**
	 * Constructs a new UnsubscribeVehicleDataResponse object
	 */
	public UnsubscribeVehicleDataResponse() {
		super(FunctionID.UNSUBSCRIBE_VEHICLE_DATA.toString());
	}

	/**
	 * Constructs a new UnsubscribeVehicleDataResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public UnsubscribeVehicleDataResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}

	/**
	 * Constructs a new UnsubscribeVehicleDataResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 *
	 * @param hash The Hashtable to use to build this RPC
	 *
	 */
    public UnsubscribeVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Sets Gps
     * @param gps a VehicleDataResult related to GPS
     */
    public void setGps(VehicleDataResult gps) {
        setParameters(KEY_GPS, gps);
    }
    /**
     * Gets Gps
     * @return a VehicleDataResult related to GPS
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getGps() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_GPS);
    }
    /**
     * Sets Speed
     * @param speed a VehicleDataResult related to speed
     */
    public void setSpeed(VehicleDataResult speed) {
        setParameters(KEY_SPEED, speed);
    }
    /**
     * Gets Speed
     * @return a VehicleDataResult related to speed
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getSpeed() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_SPEED);
    }
    /**
     * Sets rpm
     * @param rpm a VehicleDataResult related to RPM
     */
    public void setRpm(VehicleDataResult rpm) {
        setParameters(KEY_RPM, rpm);
    }
    /**
     * Gets rpm
     * @return a VehicleDataResult related to RPM
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getRpm() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_RPM);
    }
    /**
     * Sets Fuel Level
     * @param fuelLevel a VehicleDataResult related to Fuel Level
     */
    public void setFuelLevel(VehicleDataResult fuelLevel) {
        setParameters(KEY_FUEL_LEVEL, fuelLevel);
    }
    /**
     * Gets Fuel Level
     * @return a VehicleDataResult related to FuelLevel
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getFuelLevel() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_FUEL_LEVEL);
    }
    /**
     * Sets Fuel Level State
     * @param fuelLevel_State a VehicleDataResult related to FuelLevel State
     */
    @Deprecated
    public void setFuelLevel_State(VehicleDataResult fuelLevel_State) {
        setFuelLevel(fuelLevel_State);
    }
    /**
     * Gets Fuel Level State
     * @return a VehicleDataResult related to FuelLevel State
     */
    @Deprecated
    public VehicleDataResult getFuelLevel_State() {
        return getFuelLevelState();
    }
    /**
     * Sets Fuel Level State
     * @param fuelLevelState a VehicleDataResult related to FuelLevel State
     */
    public void setFuelLevelState(VehicleDataResult fuelLevelState) {
        setParameters(KEY_FUEL_LEVEL_STATE, fuelLevelState);
    }
    /**
     * Gets Fuel Level State
     * @return a VehicleDataResult related to FuelLevel State
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getFuelLevelState() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_FUEL_LEVEL_STATE);
    }
    /**
     * Sets Instant Fuel Consumption
     * @param instantFuelConsumption a VehicleDataResult related to instant fuel consumption
     */
    public void setInstantFuelConsumption(VehicleDataResult instantFuelConsumption) {
        setParameters(KEY_INSTANT_FUEL_CONSUMPTION, instantFuelConsumption);
    }
    /**
     * Gets Instant Fuel Consumption
     * @return a VehicleDataResult related to instant fuel consumption
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getInstantFuelConsumption() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_INSTANT_FUEL_CONSUMPTION);
    }
    /**
     * Sets External Temperature
     * @param externalTemperature a VehicleDataResult related to external temperature
     */
    public void setExternalTemperature(VehicleDataResult externalTemperature) {
        setParameters(KEY_EXTERNAL_TEMPERATURE, externalTemperature);
    }
    /**
     * Gets External Temperature
     * @return a VehicleDataResult related to external temperature
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getExternalTemperature() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_EXTERNAL_TEMPERATURE);
    }
    /**
     * Gets currently selected gear data
     * @param prndl a VehicleDataResult related to the PRNDL status (automatic transmission gear)
     */
    public void setPrndl(VehicleDataResult prndl) {
        setParameters(KEY_PRNDL, prndl);
    }
    /**
     * Gets currently selected gear data
     * @return a VehicleDataResult related to the PRNDL status (automatic transmission gear)
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getPrndl() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_PRNDL);
    }
    /**
     * Sets Tire Pressure
     * @param tirePressure a VehicleDataResult related to tire pressure
     */
    public void setTirePressure(VehicleDataResult tirePressure) {
        setParameters(KEY_TIRE_PRESSURE, tirePressure);
    }
    /**
     * Gets Tire Pressure
     * @return a VehicleDataResult related to tire pressure
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getTirePressure() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_TIRE_PRESSURE);
    }
    /**
     * Sets Odometer
     * @param odometer a VehicleDataResult related to the odometer
     */
    public void setOdometer(VehicleDataResult odometer) {
        setParameters(KEY_ODOMETER, odometer);
    }
    /**
     * Gets Odometer
     * @return a VehicleDataResult related to the odometer
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getOdometer() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_ODOMETER);
    }
    /**
     * Sets Belt Status
     * @param beltStatus a VehicleDataResult related to the seat belt status
     */
    public void setBeltStatus(VehicleDataResult beltStatus) {
        setParameters(KEY_BELT_STATUS, beltStatus);
    }
    /**
     * Gets Belt Status
     * @return a VehicleDataResult related to the seat belt status
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getBeltStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_BELT_STATUS);
    }
    /**
     * Sets Body Information
     * @param bodyInformation a VehicleDataResult related to the body info
     */
    public void setBodyInformation(VehicleDataResult bodyInformation) {
        setParameters(KEY_BODY_INFORMATION, bodyInformation);
    }
    /**
     * Gets Body Information
     * @return a VehicleDataResult related to the body info
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getBodyInformation() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_BODY_INFORMATION);
    }
    /**
     * Sets Device Status
     * @param deviceStatus a VehicleDataResult related to the device status of the connected device
     */
    public void setDeviceStatus(VehicleDataResult deviceStatus) {
        setParameters(KEY_DEVICE_STATUS, deviceStatus);
    }
    /**
     * Gets Device Status
     * @return a VehicleDataResult related to the device status of the connected device
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getDeviceStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_DEVICE_STATUS);
    }
    /**
     * Sets Driver Braking
     * @param driverBraking a VehicleDataResult related to the driver breaking status
     */
    public void setDriverBraking(VehicleDataResult driverBraking) {
        setParameters(KEY_DRIVER_BRAKING, driverBraking);
    }
    /**
     * Gets Driver Braking
     * @return a VehicleDataResult related to the driver breaking status
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getDriverBraking() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_DRIVER_BRAKING);
    }
    /**
     * Sets Wiper Status
     * @param wiperStatus a VehicleDataResult related to the wiper status
     */
    public void setWiperStatus(VehicleDataResult wiperStatus) {
        setParameters(KEY_WIPER_STATUS, wiperStatus);
    }
    /**
     * Gets Wiper Status
     * @return a VehicleDataResult related to the wiper status
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getWiperStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_WIPER_STATUS);
    }
    /**
     * Sets Head Lamp Status
     * @param headLampStatus a VehicleDataResult related to the headlamp status
     */
    public void setHeadLampStatus(VehicleDataResult headLampStatus) {
        setParameters(KEY_HEAD_LAMP_STATUS, headLampStatus);
    }
    /**
     * Gets Head Lamp Status
     * @return a VehicleDataResult related to the headlamp status
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getHeadLampStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_HEAD_LAMP_STATUS);
    }
    /**
     * Sets Engine Torque
     * @param engineTorque a VehicleDataResult related to the engine's torque
     */
    public void setEngineTorque(VehicleDataResult engineTorque) {
        setParameters(KEY_ENGINE_TORQUE, engineTorque);
    }
    /**
     * Gets Engine Torque
     * @return a VehicleDataResult related to the enginer's torque
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getEngineTorque() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_ENGINE_TORQUE);
    }
    /**
     * Sets Engine Oil Life
     * @param engineOilLife a VehicleDataResult related to the engine's oil life
     */
    public void setEngineOilLife(VehicleDataResult engineOilLife) {
        setParameters(KEY_ENGINE_OIL_LIFE, engineOilLife);
    }
    /**
     * Gets Engine Oil Life
     * @return a VehicleDataResult related to the engine's oil life
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getEngineOilLife() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_ENGINE_OIL_LIFE);
    }
    /**
     * Sets AccPedal Position
     * @param accPedalPosition a VehicleDataResult related to the accelerator pedal's position
     */
    public void setAccPedalPosition(VehicleDataResult accPedalPosition) {
        setParameters(KEY_ACC_PEDAL_POSITION, accPedalPosition);
    }
    /**
     * Gets AccPedal Position
     * @return a VehicleDataResult related to the accelerator pedal's position
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getAccPedalPosition() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_ACC_PEDAL_POSITION);
    }

    public void setSteeringWheelAngle(VehicleDataResult steeringWheelAngle) {
        setParameters(KEY_STEERING_WHEEL_ANGLE, steeringWheelAngle);
    }

    @SuppressWarnings("unchecked")
    public VehicleDataResult getSteeringWheelAngle() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_STEERING_WHEEL_ANGLE);
    }

    public void setECallInfo(VehicleDataResult eCallInfo) {
        setParameters(KEY_E_CALL_INFO, eCallInfo);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getECallInfo() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_E_CALL_INFO);
    }
    public void setAirbagStatus(VehicleDataResult airbagStatus) {
        setParameters(KEY_AIRBAG_STATUS, airbagStatus);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getAirbagStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_AIRBAG_STATUS);
    }
    public void setEmergencyEvent(VehicleDataResult emergencyEvent) {
        setParameters(KEY_EMERGENCY_EVENT, emergencyEvent);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getEmergencyEvent() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_EMERGENCY_EVENT);
    }
    public void setClusterModeStatus(VehicleDataResult clusterModeStatus) {
        setParameters(KEY_CLUSTER_MODE_STATUS, clusterModeStatus);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getClusterModeStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_CLUSTER_MODE_STATUS);
    }
    public void setMyKey(VehicleDataResult myKey) {
        setParameters(KEY_MY_KEY, myKey);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getMyKey() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_MY_KEY);
    }

    /**
     * Sets Fuel Range
     * @param fuelRange a VehicleDataResult related to the fuel range
     */
    public void setFuelRange(VehicleDataResult fuelRange) {
        setParameters(KEY_FUEL_RANGE, fuelRange);
    }

    /**
     * Gets Fuel Range
     * @return a VehicleDataResult related to the fuel range
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getFuelRange() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_FUEL_RANGE);
    }

    /**
     * Sets turnSignal
     * @param turnSignal a VehicleDataResult related to the turn signal status
     */
    public void setTurnSignal(VehicleDataResult turnSignal) {
        setParameters(KEY_TURN_SIGNAL, turnSignal);
    }

    /**
     * Gets turnSignal
     * @return a VehicleDataResult related to the turn signal status
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getTurnSignal() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_TURN_SIGNAL);
    }

    /**
     * Sets electronicParkBrakeStatus
     * @param electronicParkBrakeStatus a VehicleDataResult related to the electronic park brake status
     */
    public void setElectronicParkBrakeStatus(VehicleDataResult electronicParkBrakeStatus){
        setParameters(KEY_ELECTRONIC_PARK_BRAKE_STATUS, electronicParkBrakeStatus);
    }

    /**
     * Gets electronicParkBrakeStatus
     * @return a VehicleDataResult related to the electronic park brake status
     */
    public VehicleDataResult getElectronicParkBrakeStatus(){
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_ELECTRONIC_PARK_BRAKE_STATUS);
    }

    /**
     * Sets cloudAppVehicleID
     * @param cloudAppVehicleID a VehicleDataResult related to the cloud app vehicle ID
     */
    public void setCloudAppVehicleID(VehicleDataResult cloudAppVehicleID){
        setParameters(KEY_CLOUD_APP_VEHICLE_ID, cloudAppVehicleID);
    }

    /**
     * Gets a VehicleDataResult for the unsubscribe response of the CloudAppVehicleID vehicle data item.
     * @return a VehicleDataResult related to the cloud app vehicle ID
     */
    public VehicleDataResult getCloudAppVehicleID(){
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_CLOUD_APP_VEHICLE_ID);
    }

    /**
     * Sets a value for OEM Custom VehicleData.
     * @param vehicleDataName a String value
     * @param vehicleDataState a VehicleDataResult value
     */
    public void setOEMCustomVehicleData(String vehicleDataName, VehicleDataResult vehicleDataState){
        setParameters(vehicleDataName, vehicleDataState);
    }

    /**
     * Gets a VehicleDataResult for the vehicle data item.
     * @return a VehicleDataResult related to the vehicle data
     */
    public VehicleDataResult getOEMCustomVehicleData(String vehicleDataName){
        return (VehicleDataResult) getObject(VehicleDataResult.class, vehicleDataName);
    }

    /**
     * Sets the handsOffSteering.
     *
     * @param handsOffSteering To indicate whether driver hands are off the steering wheel
     * @since SmartDeviceLink 7.0.0
     */
    public void setHandsOffSteering(VehicleDataResult handsOffSteering) {
        setParameters(KEY_HANDS_OFF_STEERING, handsOffSteering);
    }

    /**
     * Gets the handsOffSteering.
     *
     * @return VehicleDataResult To indicate whether driver hands are off the steering wheel
     * @since SmartDeviceLink 7.0.0
     */
    public VehicleDataResult getHandsOffSteering() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_HANDS_OFF_STEERING);
    }
}
