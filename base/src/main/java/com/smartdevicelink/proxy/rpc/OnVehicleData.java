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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.TurnSignal;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;
import java.util.List;

/**
 * Individual requested DID result and data.
 *
 *
 * <p>Callback for the periodic and non periodic vehicle data read function.</p>
 *
 * <p> <b>Note:</b></p>
 * <p>
 * Initially SDL sends SubscribeVehicleData for getting the periodic updates from HMI whenever each of subscribed data types changes. OnVehicleData is expected to bring such updated values to SDL
 *
 *
 *
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>Gps</td>
 * 			<td>Boolean</td>
 * 			<td>GPS data. See {@linkplain com.smartdevicelink.proxy.rpc.GPSData} for details</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>Speed</td>
 * 			<td>Float</td>
 * 			<td>The vehicle speed in kilometers per hour</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rpm</td>
 * 			<td>Integer</td>
 * 			<td>The number of revolutions per minute of the engine</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>fuelLevel</td>
 * 			<td>Boolean</td>
 * 			<td>The fuel level in the tank (percentage). This parameter is deprecated starting RPC Spec7.0, please see fuelRange.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 7.0.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>fuelLevel_State</td>
 * 			<td>Boolean</td>
 * 			<td>The fuel level state. This parameter is deprecated starting RPC Spec 7.0, please see fuelRange.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 7.0.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>fuelRange</td>
 * 			<td>Boolean</td>
 * 			<td>The fuel type, estimated range in KM, fuel level/capacity and fuel level state for the vehicle. See struct FuelRange for details.</td>
 * 			<td>N</td>
 * 			<td>{"array_min_size": 0, "array_max_size": 100}</td>
 * 			<td>SmartDeviceLink 5.0.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>instantFuelConsumption</td>
 * 			<td>Float</td>
 * 			<td>The instantaneous fuel consumption in micro litres</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>externalTemperature</td>
 * 			<td>Float</td>
 * 			<td>The external temperature in degrees celsius.</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>vin</td>
 * 			<td>String</td>
 * 			<td>Vehicle identification number.</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *      <tr>
 *          <td>gearStatus</td>
 *          <td>GearStatus</td>
 *          <td>See GearStatus</td>
 *          <td>N</td>
 *          <td>SmartDeviceLink 7.0.0</td>
 *      </tr>
 *      <tr>
 *          <td>gearStatus</td>
 *          <td>GearStatus</td>
 *          <td>See GearStatus</td>
 *          <td>N</td>
 *          <td>SmartDeviceLink 7.0.0</td>
 *      </tr>
 *      <tr>
 *          <td>prndl</td>
 *          <td>PRNDL</td>
 *          <td>See PRNDL. This parameter is deprecated and it is now covered in `gearStatus`</td>
 *          <td>N</td>
 *          <td>SmartDeviceLink 7.0.0</td>
 *      </tr>
 * 		<tr>
 * 			<td>tirePressure</td>
 * 			<td>TireStatus</td>
 * 			<td>Tire pressure status</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>odometer</td>
 * 			<td>Integer</td>
 * 			<td>Odometer in km</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>beltStatus</td>
 * 			<td>BeltStatus</td>
 * 			<td>The status of the seat belts.</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>bodyInformation</td>
 * 			<td>BodyInformation</td>
 * 			<td>The body information including power modes.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>deviceStatus</td>
 * 			<td>DeviceStatus</td>
 * 			<td>The connected mobile device status including signal and battery strength.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>eCallInfo</td>
 * 			<td>ECallInfo</td>
 * 			<td>Emergency Call notification and confirmation data.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>airbagStatus</td>
 * 			<td>AirBagStatus</td>
 * 			<td>The status of the air bags.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>emergencyEvent</td>
 * 			<td>EmergencyEvent</td>
 * 			<td>Information related to an emergency event (and if it occurred).</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>clusterModeStatus</td>
 * 			<td>ClusterModeStatus</td>
 * 			<td>The status modes of the instrument panel cluster.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>myKey</td>
 * 			<td>MyKey</td>
 * 			<td>Information related to the MyKey feature.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 * 		<tr>
 * 			<td>driverBraking</td>
 * 			<td>vehicleDataEventStatus</td>
 * 			<td>The status of the brake pedal.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>wiperStatus</td>
 * 			<td>WiperStatus</td>
 * 			<td>The status of the wipers</td>
 *                 <td>N</td>
 * 			<td> </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>headLampStatus</td>
 * 			<td>headLampStatus</td>
 * 			<td>Status of the head lamps</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>engineTorque</td>
 * 			<td>Float</td>
 * 			<td>Torque value for engine (in Nm) on non-diesel variants</td>
 *                 <td>N</td>
 * 			<td>minvalue:-1000; maxvalue:2000</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>engineOilLife</td>
 * 			<td>Float</td>
 * 			<td>The estimated percentage of remaining oil life of the engine</td>
 *                 <td>N</td>
 * 			<td>minvalue:0; maxvalue:100</td>
 * 			<td>SmartDeviceLink 5.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>accPedalPosition</td>
 * 			<td>Float</td>
 * 			<td>Accelerator pedal position (percentage depressed)</td>
 *                 <td>N</td>
 * 			<td>minvalue: 0; maxvalue:100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>steeringWheelAngle</td>
 * 			<td>Float</td>
 * 			<td>Current angle of the steering wheel (in deg)</td>
 *                 <td>N</td>
 * 			<td> minvalue: -2000; maxvalue:2000</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 	     <tr>
 * 			<td>cloudAppVehicleID</td>
 * 			<td>String</td>
 * 			<td>ID for the vehicle when connecting to cloud applications</td>
 * 				<td>N</td>
 * 				<td></td>
 * 			<td>SmartDeviceLink 5.1 </td>
 * 		</tr>
 *      <tr>
 *          <td>handsOffSteering</td>
 *          <td>Boolean</td>
 *          <td>To indicate whether driver hands are off the steering wheel</td>
 *          <td>N</td>
 *          <td>SmartDeviceLink 7.0.0</td>
 *      </tr>
 *      <tr>
 *          <td>windowStatus</td>
 *          <td>Boolean</td>
 *          <td>See WindowStatus</td>
 *          <td>N</td>
 *          <td>SmartDeviceLink 7.0.0</td>
 *      </tr>
 *      <tr>
 *          <td>stabilityControlsStatus</td>
 *          <td>StabilityControlsStatus</td>
 *          <td>See StabilityControlsStatus</td>
 *          <td>N</td>
 *          <td>SmartDeviceLink 7.0.0</td>
 *  </tr>
 *  </table>
 *
 * @see SubscribeVehicleData
 * @see UnsubscribeVehicleData
 * @since SmartDeviceLink 1.0
 */
public class OnVehicleData extends RPCNotification {
    public static final String KEY_SPEED = "speed";
    public static final String KEY_RPM = "rpm";
    public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
    public static final String KEY_VIN = "vin";
    public static final String KEY_PRNDL = "prndl";
    public static final String KEY_TIRE_PRESSURE = "tirePressure";
    public static final String KEY_ENGINE_TORQUE = "engineTorque";
    public static final String KEY_ENGINE_OIL_LIFE = "engineOilLife";
    public static final String KEY_ODOMETER = "odometer";
    public static final String KEY_GPS = "gps";
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
    public static final String KEY_WINDOW_STATUS = "windowStatus";
    public static final String KEY_GEAR_STATUS = "gearStatus";
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

    public static final String KEY_STABILITY_CONTROLS_STATUS = "stabilityControlsStatus";

    public OnVehicleData() {
        super(FunctionID.ON_VEHICLE_DATA.toString());
    }

    public OnVehicleData(Hashtable<String, Object> hash) {
        super(hash);
    }

    public OnVehicleData setGps(GPSData gps) {
        setParameters(KEY_GPS, gps);
        return this;
    }

    public GPSData getGps() {
        return (GPSData) getObject(GPSData.class, KEY_GPS);
    }

    public OnVehicleData setSpeed(Double speed) {
        setParameters(KEY_SPEED, speed);
        return this;
    }

    public Double getSpeed() {
        Object object = getParameters(KEY_SPEED);
        return SdlDataTypeConverter.objectToDouble(object);
    }

    public OnVehicleData setRpm(Integer rpm) {
        setParameters(KEY_RPM, rpm);
        return this;
    }

    public Integer getRpm() {
        return getInteger(KEY_RPM);
    }

    /**
     * Sets the fuelLevel.
     *
     * @param fuelLevel The fuel level in the tank (percentage). This parameter is deprecated starting RPC Spec
     *                  7.0, please see fuelRange.
     */
    @Deprecated
    public OnVehicleData setFuelLevel(Double fuelLevel) {
        setParameters(KEY_FUEL_LEVEL, fuelLevel);
        return this;
    }

    /**
     * Gets the fuelLevel.
     *
     * @return Float The fuel level in the tank (percentage). This parameter is deprecated starting RPC Spec
     * 7.0, please see fuelRange.
     */
    @Deprecated
    public Double getFuelLevel() {
        Object object = getParameters(KEY_FUEL_LEVEL);
        return SdlDataTypeConverter.objectToDouble(object);
    }

    @Deprecated
    public OnVehicleData setFuelLevelState(ComponentVolumeStatus fuelLevelState) {
        setParameters(KEY_FUEL_LEVEL_STATE, fuelLevelState);
        return this;
    }

    @Deprecated
    public ComponentVolumeStatus getFuelLevelState() {
        return (ComponentVolumeStatus) getObject(ComponentVolumeStatus.class, KEY_FUEL_LEVEL_STATE);
    }

    public OnVehicleData setInstantFuelConsumption(Double instantFuelConsumption) {
        setParameters(KEY_INSTANT_FUEL_CONSUMPTION, instantFuelConsumption);
        return this;
    }

    public Double getInstantFuelConsumption() {
        Object object = getParameters(KEY_INSTANT_FUEL_CONSUMPTION);
        return SdlDataTypeConverter.objectToDouble(object);
    }

    public OnVehicleData setExternalTemperature(Double externalTemperature) {
        setParameters(KEY_EXTERNAL_TEMPERATURE, externalTemperature);
        return this;
    }

    public Double getExternalTemperature() {
        Object object = getParameters(KEY_EXTERNAL_TEMPERATURE);
        return SdlDataTypeConverter.objectToDouble(object);
    }

    public OnVehicleData setVin(String vin) {
        setParameters(KEY_VIN, vin);
        return this;
    }

    public String getVin() {
        return getString(KEY_VIN);
    }

    /**
     * Sets the prndl.
     *
     * @param prndl See PRNDL. This parameter is deprecated since 7.0.0 and it is now covered in `gearStatus`
     * @deprecated in SmartDeviceLink 7.0.0
     */
    @Deprecated
    public OnVehicleData setPrndl(PRNDL prndl) {
        setParameters(KEY_PRNDL, prndl);
        return this;
    }

    /**
     * Gets the prndl.
     *
     * @return PRNDL See PRNDL. This parameter since 7.0.0 is deprecated and it is now covered in `gearStatus`
     * @deprecated in SmartDeviceLink 7.0.0
     */
    @Deprecated
    public PRNDL getPrndl() {
        return (PRNDL) getObject(PRNDL.class, KEY_PRNDL);
    }

    /**
     * Sets the tirePressure.
     *
     * @param tirePressure See TireStatus
     */
    public OnVehicleData setTirePressure(TireStatus tirePressure) {
        setParameters(KEY_TIRE_PRESSURE, tirePressure);
        return this;
    }

    public TireStatus getTirePressure() {
        return (TireStatus) getObject(TireStatus.class, KEY_TIRE_PRESSURE);
    }

    public OnVehicleData setOdometer(Integer odometer) {
        setParameters(KEY_ODOMETER, odometer);
        return this;
    }

    public Integer getOdometer() {
        return getInteger(KEY_ODOMETER);
    }

    public OnVehicleData setBeltStatus(BeltStatus beltStatus) {
        setParameters(KEY_BELT_STATUS, beltStatus);
        return this;
    }

    public BeltStatus getBeltStatus() {
        return (BeltStatus) getObject(BeltStatus.class, KEY_BELT_STATUS);
    }

    public OnVehicleData setBodyInformation(BodyInformation bodyInformation) {
        setParameters(KEY_BODY_INFORMATION, bodyInformation);
        return this;
    }

    public BodyInformation getBodyInformation() {
        return (BodyInformation) getObject(BodyInformation.class, KEY_BODY_INFORMATION);
    }

    public OnVehicleData setDeviceStatus(DeviceStatus deviceStatus) {
        setParameters(KEY_DEVICE_STATUS, deviceStatus);
        return this;
    }

    public DeviceStatus getDeviceStatus() {
        return (DeviceStatus) getObject(DeviceStatus.class, KEY_DEVICE_STATUS);
    }

    public OnVehicleData setDriverBraking(VehicleDataEventStatus driverBraking) {
        setParameters(KEY_DRIVER_BRAKING, driverBraking);
        return this;
    }

    public VehicleDataEventStatus getDriverBraking() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_BRAKING);
    }

    public OnVehicleData setWiperStatus(WiperStatus wiperStatus) {
        setParameters(KEY_WIPER_STATUS, wiperStatus);
        return this;
    }

    public WiperStatus getWiperStatus() {
        return (WiperStatus) getObject(WiperStatus.class, KEY_WIPER_STATUS);
    }

    public OnVehicleData setHeadLampStatus(HeadLampStatus headLampStatus) {
        setParameters(KEY_HEAD_LAMP_STATUS, headLampStatus);
        return this;
    }

    public HeadLampStatus getHeadLampStatus() {
        return (HeadLampStatus) getObject(HeadLampStatus.class, KEY_HEAD_LAMP_STATUS);
    }

    public OnVehicleData setEngineTorque(Double engineTorque) {
        setParameters(KEY_ENGINE_TORQUE, engineTorque);
        return this;
    }

    public Double getEngineTorque() {
        Object object = getParameters(KEY_ENGINE_TORQUE);
        return SdlDataTypeConverter.objectToDouble(object);
    }

    public OnVehicleData setEngineOilLife(Float engineOilLife) {
        setParameters(KEY_ENGINE_OIL_LIFE, engineOilLife);
        return this;
    }

    public Float getEngineOilLife() {
        Object object = getParameters(KEY_ENGINE_OIL_LIFE);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    public OnVehicleData setAccPedalPosition(Double accPedalPosition) {
        setParameters(KEY_ACC_PEDAL_POSITION, accPedalPosition);
        return this;
    }

    public Double getAccPedalPosition() {
        Object object = getParameters(KEY_ACC_PEDAL_POSITION);
        return SdlDataTypeConverter.objectToDouble(object);
    }

    public OnVehicleData setSteeringWheelAngle(Double steeringWheelAngle) {
        setParameters(KEY_STEERING_WHEEL_ANGLE, steeringWheelAngle);
        return this;
    }

    public Double getSteeringWheelAngle() {
        Object object = getParameters(KEY_STEERING_WHEEL_ANGLE);
        return SdlDataTypeConverter.objectToDouble(object);
    }

    public OnVehicleData setECallInfo(ECallInfo eCallInfo) {
        setParameters(KEY_E_CALL_INFO, eCallInfo);
        return this;
    }

    public ECallInfo getECallInfo() {
        return (ECallInfo) getObject(ECallInfo.class, KEY_E_CALL_INFO);
    }

    public OnVehicleData setAirbagStatus(AirbagStatus airbagStatus) {
        setParameters(KEY_AIRBAG_STATUS, airbagStatus);
        return this;
    }

    public AirbagStatus getAirbagStatus() {
        return (AirbagStatus) getObject(AirbagStatus.class, KEY_AIRBAG_STATUS);
    }

    public OnVehicleData setEmergencyEvent(EmergencyEvent emergencyEvent) {
        setParameters(KEY_EMERGENCY_EVENT, emergencyEvent);
        return this;
    }

    public EmergencyEvent getEmergencyEvent() {
        return (EmergencyEvent) getObject(EmergencyEvent.class, KEY_EMERGENCY_EVENT);
    }

    public OnVehicleData setClusterModeStatus(ClusterModeStatus clusterModeStatus) {
        setParameters(KEY_CLUSTER_MODE_STATUS, clusterModeStatus);
        return this;
    }

    public ClusterModeStatus getClusterModeStatus() {
        return (ClusterModeStatus) getObject(ClusterModeStatus.class, KEY_CLUSTER_MODE_STATUS);
    }

    public OnVehicleData setMyKey(MyKey myKey) {
        setParameters(KEY_MY_KEY, myKey);
        return this;
    }

    public MyKey getMyKey() {
        return (MyKey) getObject(MyKey.class, KEY_MY_KEY);
    }

    /**
     * Sets the fuelRange.
     *
     * @param fuelRange The fuel type, estimated range in KM, fuel level/capacity and fuel level state for the
     *                  vehicle. See struct FuelRange for details.
     *                  {"array_min_size": 0, "array_max_size": 100}
     * @since SmartDeviceLink 5.0.0
     */
    public OnVehicleData setFuelRange(List<FuelRange> fuelRange) {
        setParameters(KEY_FUEL_RANGE, fuelRange);
        return this;
    }

    /**
     * Gets the fuelRange.
     *
     * @return List<FuelRange> The fuel type, estimated range in KM, fuel level/capacity and fuel level state for the
     * vehicle. See struct FuelRange for details.
     * {"array_min_size": 0, "array_max_size": 100}
     * @since SmartDeviceLink 5.0.0
     */
    @SuppressWarnings("unchecked")
    public List<FuelRange> getFuelRange() {
        return (List<FuelRange>) getObject(FuelRange.class, KEY_FUEL_RANGE);
    }

    /**
     * Sets turnSignal
     *
     * @param turnSignal
     */
    public OnVehicleData setTurnSignal(TurnSignal turnSignal) {
        setParameters(KEY_TURN_SIGNAL, turnSignal);
        return this;
    }

    /**
     * Gets turnSignal
     *
     * @return TurnSignal
     */
    public TurnSignal getTurnSignal() {
        return (TurnSignal) getObject(TurnSignal.class, KEY_TURN_SIGNAL);
    }

    /**
     * Sets electronicParkBrakeStatus
     *
     * @param electronicParkBrakeStatus
     */
    public OnVehicleData setElectronicParkBrakeStatus(ElectronicParkBrakeStatus electronicParkBrakeStatus) {
        setParameters(KEY_ELECTRONIC_PARK_BRAKE_STATUS, electronicParkBrakeStatus);
        return this;
    }

    /**
     * Gets electronicParkBrakeStatus
     *
     * @return ElectronicParkBrakeStatus
     */
    public ElectronicParkBrakeStatus getElectronicParkBrakeStatus() {
        return (ElectronicParkBrakeStatus) getObject(ElectronicParkBrakeStatus.class, KEY_ELECTRONIC_PARK_BRAKE_STATUS);
    }

    /**
     * Sets a string value for the cloud app vehicle ID
     *
     * @param cloudAppVehicleID a string value
     */
    public OnVehicleData setCloudAppVehicleID(String cloudAppVehicleID) {
        setParameters(KEY_CLOUD_APP_VEHICLE_ID, cloudAppVehicleID);
        return this;
    }

    /**
     * Gets a String value of the returned cloud app vehicle ID
     *
     * @return a String value.
     */
    public String getCloudAppVehicleID() {
        return getString(KEY_CLOUD_APP_VEHICLE_ID);
    }

    /**
     * Sets a value for OEM Custom VehicleData.
     *
     * @param vehicleDataName  a String value
     * @param vehicleDataState a VehicleDataResult value
     */
    public OnVehicleData setOEMCustomVehicleData(String vehicleDataName, Object vehicleDataState) {
        setParameters(vehicleDataName, vehicleDataState);
        return this;
    }

    /**
     * Gets a VehicleData value for the vehicle data item.
     *
     * @return a Object related to the vehicle data
     */
    public Object getOEMCustomVehicleData(String vehicleDataName) {
        return getParameters(vehicleDataName);
    }

    /**
     * Sets the windowStatus.
     *
     * @param windowStatus See WindowStatus
     * @since SmartDeviceLink 7.0.0
     */
    public OnVehicleData setWindowStatus(List<WindowStatus> windowStatus) {
        setParameters(KEY_WINDOW_STATUS, windowStatus);
        return this;
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
     * Gets the stabilityControlsStatus.
     *
     * @return StabilityControlsStatus See StabilityControlsStatus
     * @since SmartDeviceLink 7.0.0
     */
    public StabilityControlsStatus getStabilityControlsStatus() {
        return (StabilityControlsStatus) getObject(StabilityControlsStatus.class, KEY_STABILITY_CONTROLS_STATUS);
    }

    /**
     * Sets the stabilityControlsStatus.
     *
     * @param stabilityControlsStatus See StabilityControlsStatus
     * @since SmartDeviceLink 7.0.0
     */
    public OnVehicleData setStabilityControlsStatus(StabilityControlsStatus stabilityControlsStatus) {
        setParameters(KEY_STABILITY_CONTROLS_STATUS, stabilityControlsStatus);
        return this;
    }

    /**
     * Sets the handsOffSteering.
     *
     * @param handsOffSteering To indicate whether driver hands are off the steering wheel
     * @since SmartDeviceLink 7.0.0
     */
    public OnVehicleData setHandsOffSteering(Boolean handsOffSteering) {
        setParameters(KEY_HANDS_OFF_STEERING, handsOffSteering);
        return this;
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

    /**
     * Sets the gearStatus.
     *
     * @param gearStatus See GearStatus
     * @since SmartDeviceLink 7.0.0
     */
    public OnVehicleData setGearStatus(GearStatus gearStatus) {
        setParameters(KEY_GEAR_STATUS, gearStatus);
        return this;
    }

    /**
     * Gets the gearStatus.
     *
     * @return GearStatus See GearStatus
     * @since SmartDeviceLink 7.0.0
     */
    public GearStatus getGearStatus() {
        return (GearStatus) getObject(GearStatus.class, KEY_GEAR_STATUS);
    }
}
