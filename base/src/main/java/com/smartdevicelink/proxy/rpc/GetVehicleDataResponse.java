/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium Inc. nor the names of
 * its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
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
 * @since SmartDeviceLink 2.0.0
 */
public class GetVehicleDataResponse extends RPCResponse {
    public static final String KEY_GPS = "gps";
    public static final String KEY_SPEED = "speed";
    public static final String KEY_RPM = "rpm";
    /**
     * @deprecated
     */
    @Deprecated
    public static final String KEY_FUEL_LEVEL = "fuelLevel";
    /**
     * @deprecated
     */
    @Deprecated
    public static final String KEY_FUEL_LEVEL_STATE = "fuelLevel_State";
    public static final String KEY_INSTANT_FUEL_CONSUMPTION = "instantFuelConsumption";
    public static final String KEY_FUEL_RANGE = "fuelRange";
    public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
    public static final String KEY_TURN_SIGNAL = "turnSignal";
    public static final String KEY_VIN = "vin";
    public static final String KEY_PRNDL = "prndl";
    public static final String KEY_TIRE_PRESSURE = "tirePressure";
    public static final String KEY_ODOMETER = "odometer";
    public static final String KEY_BELT_STATUS = "beltStatus";
    public static final String KEY_BODY_INFORMATION = "bodyInformation";
    public static final String KEY_DEVICE_STATUS = "deviceStatus";
    public static final String KEY_DRIVER_BRAKING = "driverBraking";
    public static final String KEY_WIPER_STATUS = "wiperStatus";
    public static final String KEY_HEAD_LAMP_STATUS = "headLampStatus";
    public static final String KEY_ENGINE_TORQUE = "engineTorque";
    public static final String KEY_ACC_PEDAL_POSITION = "accPedalPosition";
    public static final String KEY_STEERING_WHEEL_ANGLE = "steeringWheelAngle";
    public static final String KEY_ENGINE_OIL_LIFE = "engineOilLife";
    public static final String KEY_ELECTRONIC_PARK_BRAKE_STATUS = "electronicParkBrakeStatus";
    public static final String KEY_CLOUD_APP_VEHICLE_ID = "cloudAppVehicleID";
    public static final String KEY_E_CALL_INFO = "eCallInfo";
    public static final String KEY_AIRBAG_STATUS = "airbagStatus";
    public static final String KEY_EMERGENCY_EVENT = "emergencyEvent";
    public static final String KEY_CLUSTER_MODE_STATUS = "clusterModeStatus";
    public static final String KEY_MY_KEY = "myKey";

    /**
     * Constructs a new GetVehicleDataResponse object
     */
    public GetVehicleDataResponse() {
        super(FunctionID.GET_VEHICLE_DATA.toString());
    }

    /**
     * Constructs a new GetVehicleDataResponse object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public GetVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new GetVehicleDataResponse object
     *
     * @param success whether the request is successfully processed
     * @param resultCode additional information about a response returning a failed outcome
     */
    public GetVehicleDataResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }

    /**
     * Sets the gps.
     *
     * @param gps See GPSData
     */
    public void setGps(GPSData gps) {
        setParameters(KEY_GPS, gps);
    }

    /**
     * Gets the gps.
     *
     * @return GPSData See GPSData
     */
    public GPSData getGps() {
        return (GPSData) getObject(GPSData.class, KEY_GPS);
    }

    /**
     * Sets the speed.
     *
     * @param speed The vehicle speed in kilometers per hour
     */
    public void setSpeed(Float speed) {
        setParameters(KEY_SPEED, speed);
    }

    /**
     * Gets the speed.
     *
     * @return Float The vehicle speed in kilometers per hour
     */
    public Float getSpeed() {
        Object object = getParameters(KEY_SPEED);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the rpm.
     *
     * @param rpm The number of revolutions per minute of the engine
     */
    public void setRpm(Integer rpm) {
        setParameters(KEY_RPM, rpm);
    }

    /**
     * Gets the rpm.
     *
     * @return Integer The number of revolutions per minute of the engine
     */
    public Integer getRpm() {
        return getInteger(KEY_RPM);
    }

    /**
     * Sets the fuelLevel.
     *
     * @param fuelLevel The fuel level in the tank (percentage). This parameter is deprecated starting RPC Spec
     * 7.0, please see fuelRange.
     * @since SmartDeviceLink 7.0.0
     */
    public void setFuelLevel(Float fuelLevel) {
        setParameters(KEY_FUEL_LEVEL, fuelLevel);
    }

    /**
     * Gets the fuelLevel.
     *
     * @return Float The fuel level in the tank (percentage). This parameter is deprecated starting RPC Spec
     * 7.0, please see fuelRange.
     * @since SmartDeviceLink 7.0.0
     */
    public Float getFuelLevel() {
        Object object = getParameters(KEY_FUEL_LEVEL);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the fuelLevel_State.
     *
     * @param fuelLevel_State The fuel level state. This parameter is deprecated starting RPC Spec 7.0, please see
     * fuelRange.
     * @since SmartDeviceLink 7.0.0
     */
    public void setFuelLevel_State(ComponentVolumeStatus fuelLevel_State) {
        setParameters(KEY_FUEL_LEVEL_STATE, fuelLevel_State);
    }

    /**
     * Gets the fuelLevel_State.
     *
     * @return ComponentVolumeStatus The fuel level state. This parameter is deprecated starting RPC Spec 7.0, please see
     * fuelRange.
     * @since SmartDeviceLink 7.0.0
     */
    public ComponentVolumeStatus getFuelLevel_State() {
        return (ComponentVolumeStatus) getObject(ComponentVolumeStatus.class, KEY_FUEL_LEVEL_STATE);
    }

    /**
     * Sets the instantFuelConsumption.
     *
     * @param instantFuelConsumption The instantaneous fuel consumption in microlitres
     */
    public void setInstantFuelConsumption(Float instantFuelConsumption) {
        setParameters(KEY_INSTANT_FUEL_CONSUMPTION, instantFuelConsumption);
    }

    /**
     * Gets the instantFuelConsumption.
     *
     * @return Float The instantaneous fuel consumption in microlitres
     */
    public Float getInstantFuelConsumption() {
        Object object = getParameters(KEY_INSTANT_FUEL_CONSUMPTION);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the fuelRange.
     *
     * @param fuelRange The fuel type, estimated range in KM, fuel level/capacity and fuel level state for the
     * vehicle. See struct FuelRange for details.
     * @since SmartDeviceLink 5.0.0
     */
    public void setFuelRange(List<FuelRange> fuelRange) {
        setParameters(KEY_FUEL_RANGE, fuelRange);
    }

    /**
     * Gets the fuelRange.
     *
     * @return List<FuelRange> The fuel type, estimated range in KM, fuel level/capacity and fuel level state for the
     * vehicle. See struct FuelRange for details.
     * @since SmartDeviceLink 5.0.0
     */
    @SuppressWarnings("unchecked")
    public List<FuelRange> getFuelRange() {
        return (List<FuelRange>) getObject(FuelRange.class, KEY_FUEL_RANGE);
    }

    /**
     * Sets the externalTemperature.
     *
     * @param externalTemperature The external temperature in degrees celsius
     */
    public void setExternalTemperature(Float externalTemperature) {
        setParameters(KEY_EXTERNAL_TEMPERATURE, externalTemperature);
    }

    /**
     * Gets the externalTemperature.
     *
     * @return Float The external temperature in degrees celsius
     */
    public Float getExternalTemperature() {
        Object object = getParameters(KEY_EXTERNAL_TEMPERATURE);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the turnSignal.
     *
     * @param turnSignal See TurnSignal
     * @since SmartDeviceLink 5.0.0
     */
    public void setTurnSignal(TurnSignal turnSignal) {
        setParameters(KEY_TURN_SIGNAL, turnSignal);
    }

    /**
     * Gets the turnSignal.
     *
     * @return TurnSignal See TurnSignal
     * @since SmartDeviceLink 5.0.0
     */
    public TurnSignal getTurnSignal() {
        return (TurnSignal) getObject(TurnSignal.class, KEY_TURN_SIGNAL);
    }

    /**
     * Sets the vin.
     *
     * @param vin Vehicle identification number
     */
    public void setVin(String vin) {
        setParameters(KEY_VIN, vin);
    }

    /**
     * Gets the vin.
     *
     * @return String Vehicle identification number
     */
    public String getVin() {
        return getString(KEY_VIN);
    }

    /**
     * Sets the prndl.
     *
     * @param prndl See PRNDL
     */
    public void setPrndl(PRNDL prndl) {
        setParameters(KEY_PRNDL, prndl);
    }

    /**
     * Gets the prndl.
     *
     * @return PRNDL See PRNDL
     */
    public PRNDL getPrndl() {
        return (PRNDL) getObject(PRNDL.class, KEY_PRNDL);
    }

    /**
     * Sets the tirePressure.
     *
     * @param tirePressure See TireStatus
     */
    public void setTirePressure(TireStatus tirePressure) {
        setParameters(KEY_TIRE_PRESSURE, tirePressure);
    }

    /**
     * Gets the tirePressure.
     *
     * @return TireStatus See TireStatus
     */
    public TireStatus getTirePressure() {
        return (TireStatus) getObject(TireStatus.class, KEY_TIRE_PRESSURE);
    }

    /**
     * Sets the odometer.
     *
     * @param odometer Odometer in km
     */
    public void setOdometer(Integer odometer) {
        setParameters(KEY_ODOMETER, odometer);
    }

    /**
     * Gets the odometer.
     *
     * @return Integer Odometer in km
     */
    public Integer getOdometer() {
        return getInteger(KEY_ODOMETER);
    }

    /**
     * Sets the beltStatus.
     *
     * @param beltStatus The status of the seat belts
     */
    public void setBeltStatus(BeltStatus beltStatus) {
        setParameters(KEY_BELT_STATUS, beltStatus);
    }

    /**
     * Gets the beltStatus.
     *
     * @return BeltStatus The status of the seat belts
     */
    public BeltStatus getBeltStatus() {
        return (BeltStatus) getObject(BeltStatus.class, KEY_BELT_STATUS);
    }

    /**
     * Sets the bodyInformation.
     *
     * @param bodyInformation The body information including power modes
     */
    public void setBodyInformation(BodyInformation bodyInformation) {
        setParameters(KEY_BODY_INFORMATION, bodyInformation);
    }

    /**
     * Gets the bodyInformation.
     *
     * @return BodyInformation The body information including power modes
     */
    public BodyInformation getBodyInformation() {
        return (BodyInformation) getObject(BodyInformation.class, KEY_BODY_INFORMATION);
    }

    /**
     * Sets the deviceStatus.
     *
     * @param deviceStatus The device status including signal and battery strength
     */
    public void setDeviceStatus(DeviceStatus deviceStatus) {
        setParameters(KEY_DEVICE_STATUS, deviceStatus);
    }

    /**
     * Gets the deviceStatus.
     *
     * @return DeviceStatus The device status including signal and battery strength
     */
    public DeviceStatus getDeviceStatus() {
        return (DeviceStatus) getObject(DeviceStatus.class, KEY_DEVICE_STATUS);
    }

    /**
     * Sets the driverBraking.
     *
     * @param driverBraking The status of the brake pedal
     */
    public void setDriverBraking(VehicleDataEventStatus driverBraking) {
        setParameters(KEY_DRIVER_BRAKING, driverBraking);
    }

    /**
     * Gets the driverBraking.
     *
     * @return VehicleDataEventStatus The status of the brake pedal
     */
    public VehicleDataEventStatus getDriverBraking() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_BRAKING);
    }

    /**
     * Sets the wiperStatus.
     *
     * @param wiperStatus The status of the wipers
     */
    public void setWiperStatus(WiperStatus wiperStatus) {
        setParameters(KEY_WIPER_STATUS, wiperStatus);
    }

    /**
     * Gets the wiperStatus.
     *
     * @return WiperStatus The status of the wipers
     */
    public WiperStatus getWiperStatus() {
        return (WiperStatus) getObject(WiperStatus.class, KEY_WIPER_STATUS);
    }

    /**
     * Sets the headLampStatus.
     *
     * @param headLampStatus Status of the head lamps
     */
    public void setHeadLampStatus(HeadLampStatus headLampStatus) {
        setParameters(KEY_HEAD_LAMP_STATUS, headLampStatus);
    }

    /**
     * Gets the headLampStatus.
     *
     * @return HeadLampStatus Status of the head lamps
     */
    public HeadLampStatus getHeadLampStatus() {
        return (HeadLampStatus) getObject(HeadLampStatus.class, KEY_HEAD_LAMP_STATUS);
    }

    /**
     * Sets the engineTorque.
     *
     * @param engineTorque Torque value for engine (in Nm) on non-diesel variants
     */
    public void setEngineTorque(Float engineTorque) {
        setParameters(KEY_ENGINE_TORQUE, engineTorque);
    }

    /**
     * Gets the engineTorque.
     *
     * @return Float Torque value for engine (in Nm) on non-diesel variants
     */
    public Float getEngineTorque() {
        Object object = getParameters(KEY_ENGINE_TORQUE);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the accPedalPosition.
     *
     * @param accPedalPosition Accelerator pedal position (percentage depressed)
     */
    public void setAccPedalPosition(Float accPedalPosition) {
        setParameters(KEY_ACC_PEDAL_POSITION, accPedalPosition);
    }

    /**
     * Gets the accPedalPosition.
     *
     * @return Float Accelerator pedal position (percentage depressed)
     */
    public Float getAccPedalPosition() {
        Object object = getParameters(KEY_ACC_PEDAL_POSITION);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the steeringWheelAngle.
     *
     * @param steeringWheelAngle Current angle of the steering wheel (in deg)
     */
    public void setSteeringWheelAngle(Float steeringWheelAngle) {
        setParameters(KEY_STEERING_WHEEL_ANGLE, steeringWheelAngle);
    }

    /**
     * Gets the steeringWheelAngle.
     *
     * @return Float Current angle of the steering wheel (in deg)
     */
    public Float getSteeringWheelAngle() {
        Object object = getParameters(KEY_STEERING_WHEEL_ANGLE);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the engineOilLife.
     *
     * @param engineOilLife The estimated percentage of remaining oil life of the engine.
     * @since SmartDeviceLink 5.0.0
     */
    public void setEngineOilLife(Float engineOilLife) {
        setParameters(KEY_ENGINE_OIL_LIFE, engineOilLife);
    }

    /**
     * Gets the engineOilLife.
     *
     * @return Float The estimated percentage of remaining oil life of the engine.
     * @since SmartDeviceLink 5.0.0
     */
    public Float getEngineOilLife() {
        Object object = getParameters(KEY_ENGINE_OIL_LIFE);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the electronicParkBrakeStatus.
     *
     * @param electronicParkBrakeStatus The status of the park brake as provided by Electric Park Brake (EPB) system.
     * @since SmartDeviceLink 5.0.0
     */
    public void setElectronicParkBrakeStatus(ElectronicParkBrakeStatus electronicParkBrakeStatus) {
        setParameters(KEY_ELECTRONIC_PARK_BRAKE_STATUS, electronicParkBrakeStatus);
    }

    /**
     * Gets the electronicParkBrakeStatus.
     *
     * @return ElectronicParkBrakeStatus The status of the park brake as provided by Electric Park Brake (EPB) system.
     * @since SmartDeviceLink 5.0.0
     */
    public ElectronicParkBrakeStatus getElectronicParkBrakeStatus() {
        return (ElectronicParkBrakeStatus) getObject(ElectronicParkBrakeStatus.class, KEY_ELECTRONIC_PARK_BRAKE_STATUS);
    }

    /**
     * Sets the cloudAppVehicleID.
     *
     * @param cloudAppVehicleID Parameter used by cloud apps to identify a head unit
     * @since SmartDeviceLink 5.1.0
     */
    public void setCloudAppVehicleID(String cloudAppVehicleID) {
        setParameters(KEY_CLOUD_APP_VEHICLE_ID, cloudAppVehicleID);
    }

    /**
     * Gets the cloudAppVehicleID.
     *
     * @return String Parameter used by cloud apps to identify a head unit
     * @since SmartDeviceLink 5.1.0
     */
    public String getCloudAppVehicleID() {
        return getString(KEY_CLOUD_APP_VEHICLE_ID);
    }

    /**
     * Sets the eCallInfo.
     *
     * @param eCallInfo Emergency Call notification and confirmation data
     */
    public void setECallInfo(ECallInfo eCallInfo) {
        setParameters(KEY_E_CALL_INFO, eCallInfo);
    }

    /**
     * Gets the eCallInfo.
     *
     * @return ECallInfo Emergency Call notification and confirmation data
     */
    public ECallInfo getECallInfo() {
        return (ECallInfo) getObject(ECallInfo.class, KEY_E_CALL_INFO);
    }

    /**
     * Sets the airbagStatus.
     *
     * @param airbagStatus The status of the air bags
     */
    public void setAirbagStatus(AirbagStatus airbagStatus) {
        setParameters(KEY_AIRBAG_STATUS, airbagStatus);
    }

    /**
     * Gets the airbagStatus.
     *
     * @return AirbagStatus The status of the air bags
     */
    public AirbagStatus getAirbagStatus() {
        return (AirbagStatus) getObject(AirbagStatus.class, KEY_AIRBAG_STATUS);
    }

    /**
     * Sets the emergencyEvent.
     *
     * @param emergencyEvent Information related to an emergency event (and if it occurred)
     */
    public void setEmergencyEvent(EmergencyEvent emergencyEvent) {
        setParameters(KEY_EMERGENCY_EVENT, emergencyEvent);
    }

    /**
     * Gets the emergencyEvent.
     *
     * @return EmergencyEvent Information related to an emergency event (and if it occurred)
     */
    public EmergencyEvent getEmergencyEvent() {
        return (EmergencyEvent) getObject(EmergencyEvent.class, KEY_EMERGENCY_EVENT);
    }

    /**
     * Sets the clusterModeStatus.
     *
     * @param clusterModeStatus The status modes of the cluster
     */
    public void setClusterModeStatus(ClusterModeStatus clusterModeStatus) {
        setParameters(KEY_CLUSTER_MODE_STATUS, clusterModeStatus);
    }

    /**
     * Gets the clusterModeStatus.
     *
     * @return ClusterModeStatus The status modes of the cluster
     */
    public ClusterModeStatus getClusterModeStatus() {
        return (ClusterModeStatus) getObject(ClusterModeStatus.class, KEY_CLUSTER_MODE_STATUS);
    }

    /**
     * Sets the myKey.
     *
     * @param myKey Information related to the MyKey feature
     */
    public void setMyKey(MyKey myKey) {
        setParameters(KEY_MY_KEY, myKey);
    }

    /**
     * Gets the myKey.
     *
     * @return MyKey Information related to the MyKey feature
     */
    public MyKey getMyKey() {
        return (MyKey) getObject(MyKey.class, KEY_MY_KEY);
    }
}
