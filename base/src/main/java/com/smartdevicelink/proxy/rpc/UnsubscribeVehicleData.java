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
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * This function is used to unsubscribe the notifications from the
 * subscribeVehicleData function
 * <p>Function Group: Location, VehicleInfo and DrivingChara</p>
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>gps</td>
 * 			<td>Boolean</td>
 * 			<td>GPS data. See {@linkplain com.smartdevicelink.proxy.rpc.GPSData} for details</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>speed</td>
 * 			<td>Boolean</td>
 * 			<td>The vehicle speed in kilometers per hour</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>rpm</td>
 * 			<td>Boolean</td>
 * 			<td>The number of revolutions per minute of the engine</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>fuelLevel</td>
 * 			<td>Boolean</td>
 * 			<td>The fuel level in the tank (percentage)</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>fuelLevel_State</td>
 * 			<td>Boolean</td>
 * 			<td>The fuel level state</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>instantFuelConsumption</td>
 * 			<td>Boolean</td>
 * 			<td>The instantaneous fuel consumption in microlitres</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>externalTemperature</td>
 * 			<td>Boolean</td>
 * 			<td>The external temperature in degrees celsius</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>prndl</td>
 * 			<td>Boolean</td>
 * 			<td>Currently selected gear.</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>tirePressure</td>
 * 			<td>Boolean</td>
 * 			<td>Tire pressure status</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>odometer</td>
 * 			<td>Boolean</td>
 * 			<td>Odometer in km</td>
 *                 <td>N</td>
 *                 <td>Max Length: 500</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>beltStatus</td>
 * 			<td>Boolean</td>
 * 			<td>The status of the seat belts</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>bodyInformation</td>
 * 			<td>Boolean</td>
 * 			<td>The body information including ignition status and internal temp</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>deviceStatus</td>
 * 			<td>Boolean</td>
 * 			<td>The device status including signal and battery strength</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>driverBraking</td>
 * 			<td>Boolean</td>
 * 			<td>The status of the brake pedal</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>wiperStatus</td>
 * 			<td>Boolean</td>
 * 			<td>The status of the wipers</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>headLampStatus</td>
 * 			<td>Boolean</td>
 * 			<td>Status of the head lamps</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>engineTorque</td>
 * 			<td>Boolean</td>
 * 			<td>Torque value for engine (in Nm) on non-diesel variants</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>engineOilLife</td>
 * 			<td>Boolean</td>
 * 			<td>The estimated percentage of remaining oil life of the engine</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 5.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>accPedalPosition</td>
 * 			<td>Boolean</td>
 * 			<td>Accelerator pedal position (percentage depressed)</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>steeringWheelAngle</td>
 * 			<td>Boolean</td>
 * 			<td>Current angle of the steering wheel (in deg)</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>eCallInfo</td>
 * 			<td>Boolean</td>
 * 			<td>Emergency Call notification and confirmation data.</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>airbagStatus</td>
 * 			<td>Boolean</td>
 * 			<td>The status of the air bags.</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>emergencyEvent</td>
 * 			<td>Boolean</td>
 * 			<td>Information related to an emergency event (and if it occurred).</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>clusterModeStatus</td>
 * 			<td>Boolean</td>
 * 			<td>The status modes of the instrument panel cluster.</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>myKey</td>
 * 			<td>Boolean</td>
 * 			<td>Information related to the MyKey feature.</td>
 *                 <td>N</td>
 *                 <td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>turnSignal</td>
 * 			<td>Boolean</td>
 * 			<td>@see TurnSignal</td>
 *				<td>N</td>
 *				<td>Subscribable</td>
 * 			<td>SmartDeviceLink 5.0 </td>
 * 		</tr>
 * 		<tr>
 * 	      	<td>stabilityControlsStatus</td>
 *        	<td>Boolean</td>
 *        	<td>See StabilityControlsStatus</td>
 *        	<td>N</td>
 *        	<td>SmartDeviceLink 7.0.0</td>
 *    	</tr>
 *  *
 *  </table>
 * <p><b> Response</b></p>
 * <p><b>Non-default Result Codes:</b></p>
 *	<p>SUCCESS</p>
 *	<p>WARNINGS</p>
 *	<p>INVALID_DATA</p>
 *	<p>OUT_OF_MEMORY</p>
 *	<p>TOO_MANY_PENDING_REQUESTS</p>
 *	<p>APPLICATION_NOT_REGISTERED</p>
 *	<p>GENERIC_ERROR</p>
 *	<p>IGNORED</p>
 *	<p>DISALLOWED</p>
 *
 * @since SmartDeviceLink 2.0
 * @see SubscribeVehicleData
 * @see GetVehicleData
 */
public class UnsubscribeVehicleData extends RPCRequest {
	public static final String KEY_SPEED = "speed";
	public static final String KEY_RPM = "rpm";
	public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
	public static final String KEY_FUEL_LEVEL = "fuelLevel";
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
    public static final String KEY_STABILITY_CONTROLS_STATUS = "stabilityControlsStatus";

	/**
	 * Constructs a new UnsubscribeVehicleData object
	 */
    public UnsubscribeVehicleData() {
        super(FunctionID.UNSUBSCRIBE_VEHICLE_DATA.toString());
    }

	/**
	 * Constructs a new UnsubscribeVehicleData object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnsubscribeVehicleData(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from Gps data
	 * 
	 * @param gps
	 *            a boolean value
	 */
    public void setGps(Boolean gps) {
		setParameters(KEY_GPS, gps);
    }

	/**
	 * Gets a boolean value. If true, means the Gps data has been unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Gps data has been
	 *         unsubscribed.
	 */
    public Boolean getGps() {
        return getBoolean(KEY_GPS);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from speed data
	 * 
	 * @param speed
	 *            a boolean value
	 */
    public void setSpeed(Boolean speed) {
		setParameters(KEY_SPEED, speed);
    }

	/**
	 * Gets a boolean value. If true, means the Speed data has been unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Speed data has been
	 *         unsubscribed.
	 */
    public Boolean getSpeed() {
        return getBoolean(KEY_SPEED);
    }

	/**
	 * Sets a boolean value. If true, unsubscribe data
	 * 
	 * @param rpm
	 *            a boolean value
	 */
    public void setRpm(Boolean rpm) {
		setParameters(KEY_RPM, rpm);
    }

	/**
	 * Gets a boolean value. If true, means the rpm data has been unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the rpm data has been
	 *         unsubscribed.
	 */
    public Boolean getRpm() {
        return getBoolean(KEY_RPM);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from FuelLevel data
	 * 
	 * @param fuelLevel
	 *            a boolean value
	 */
    public void setFuelLevel(Boolean fuelLevel) {
		setParameters(KEY_FUEL_LEVEL, fuelLevel);
    }

	/**
	 * Gets a boolean value. If true, means the FuelLevel data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the FuelLevel data has
	 *         been unsubscribed.
	 */
    public Boolean getFuelLevel() {
        return getBoolean(KEY_FUEL_LEVEL);
    }

    /**
     * Sets a boolean value. If true, unsubscribes from fuelLevel_State data
     * 
     * @param fuelLevel_State
     *            a boolean value
     */
    @Deprecated
    public void setFuelLevel_State(Boolean fuelLevel_State) {
        setFuelLevelState(fuelLevel_State);
    }

    /**
     * Gets a boolean value. If true, means the fuelLevel_State data has been
     * unsubscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the fuelLevel_State data
     *         has been unsubscribed.
     */
    @Deprecated
    public Boolean getFuelLevel_State() {
        return getFuelLevelState();
    }

    /**
     * Sets a boolean value. If true, unsubscribes from fuelLevelState data
     * 
     * @param fuelLevelState
     *            a boolean value
     */
    public void setFuelLevelState(Boolean fuelLevelState) {
		setParameters(KEY_FUEL_LEVEL_STATE, fuelLevelState);
    }

    /**
     * Gets a boolean value. If true, means the fuelLevel_State data has been
     * unsubscribed.
     * 
     * @return Boolean -a Boolean value. If true, means the fuelLevelState data
     *         has been unsubscribed.
     */
    public Boolean getFuelLevelState() {
        return getBoolean(KEY_FUEL_LEVEL_STATE);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from instantFuelConsumption data
	 * 
	 * @param instantFuelConsumption
	 *            a boolean value
	 */
    public void setInstantFuelConsumption(Boolean instantFuelConsumption) {
		setParameters(KEY_INSTANT_FUEL_CONSUMPTION, instantFuelConsumption);
    }

	/**
	 * Gets a boolean value. If true, means the getInstantFuelConsumption data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the getInstantFuelConsumption data
	 *         has been unsubscribed.
	 */
    public Boolean getInstantFuelConsumption() {
        return getBoolean(KEY_INSTANT_FUEL_CONSUMPTION);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from externalTemperature data
	 * 
	 * @param externalTemperature
	 *            a boolean value
	 */
    public void setExternalTemperature(Boolean externalTemperature) {
		setParameters(KEY_EXTERNAL_TEMPERATURE, externalTemperature);
    }

	/**
	 * Gets a boolean value. If true, means the externalTemperature data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the externalTemperature data
	 *         has been unsubscribed.
	 */
    public Boolean getExternalTemperature() {
        return getBoolean(KEY_EXTERNAL_TEMPERATURE);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes Currently selected gear data
	 * 
	 * @param prndl
	 *            a boolean value
	 */
    public void setPrndl(Boolean prndl) {
		setParameters(KEY_PRNDL, prndl);
    }

	/**
	 * Gets a boolean value. If true, means the Currently selected gear data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Currently selected gear data
	 *         has been unsubscribed.
	 */
    public Boolean getPrndl() {
        return getBoolean(KEY_PRNDL);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from tire pressure status data
	 * 
	 * @param tirePressure
	 *            a boolean value
	 */
    public void setTirePressure(Boolean tirePressure) {
		setParameters(KEY_TIRE_PRESSURE, tirePressure);
    }

	/**
	 * Gets a boolean value. If true, means the tire pressure status data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the tire pressure status data
	 *         has been unsubscribed.
	 */
    public Boolean getTirePressure() {
        return getBoolean(KEY_TIRE_PRESSURE);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from odometer data
	 * 
	 * @param odometer
	 *            a boolean value
	 */
    public void setOdometer(Boolean odometer) {
		setParameters(KEY_ODOMETER, odometer);
    }

	/**
	 * Gets a boolean value. If true, means the odometer data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the odometer data
	 *         has been unsubscribed.
	 */
    public Boolean getOdometer() {
        return getBoolean(KEY_ODOMETER);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from belt Status data
	 * 
	 * @param beltStatus
	 *            a boolean value
	 */
    public void setBeltStatus(Boolean beltStatus) {
		setParameters(KEY_BELT_STATUS, beltStatus);
    }

	/**
	 * Gets a boolean value. If true, means the belt Status data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the belt Status data
	 *         has been unsubscribed.
	 */
    public Boolean getBeltStatus() {
        return getBoolean(KEY_BELT_STATUS);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from body Information data
	 * 
	 * @param bodyInformation
	 *            a boolean value
	 */
    public void setBodyInformation(Boolean bodyInformation) {
		setParameters(KEY_BODY_INFORMATION, bodyInformation);
    }

	/**
	 * Gets a boolean value. If true, means the body Information data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the body Information data
	 *         has been unsubscribed.
	 */
    public Boolean getBodyInformation() {
        return getBoolean(KEY_BODY_INFORMATION);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from device Status data
	 * 
	 * @param deviceStatus
	 *            a boolean value
	 */
    public void setDeviceStatus(Boolean deviceStatus) {
		setParameters(KEY_DEVICE_STATUS, deviceStatus);
    }

	/**
	 * Gets a boolean value. If true, means the device Status data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the device Status data
	 *         has been unsubscribed.
	 */
    public Boolean getDeviceStatus() {
        return getBoolean(KEY_DEVICE_STATUS);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from driver Braking data
	 * 
	 * @param driverBraking
	 *            a boolean value
	 */
    public void setDriverBraking(Boolean driverBraking) {
		setParameters(KEY_DRIVER_BRAKING, driverBraking);
    }

	/**
	 * Gets a boolean value. If true, means the driver Braking data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the driver Braking data
	 *         has been unsubscribed.
	 */
    public Boolean getDriverBraking() {
        return getBoolean(KEY_DRIVER_BRAKING);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from wiper Status data
	 * 
	 * @param wiperStatus
	 *            a boolean value
	 */
    public void setWiperStatus(Boolean wiperStatus) {
		setParameters(KEY_WIPER_STATUS, wiperStatus);
    }

	/**
	 * Gets a boolean value. If true, means the wiper Status data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the wiper Status data
	 *         has been unsubscribed.
	 */
    public Boolean getWiperStatus() {
        return getBoolean(KEY_WIPER_STATUS);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from Head Lamp Status data
	 * 
	 * @param headLampStatus
	 *            a boolean value
	 */
    public void setHeadLampStatus(Boolean headLampStatus) {
		setParameters(KEY_HEAD_LAMP_STATUS, headLampStatus);
    }

	/**
	 * Gets a boolean value. If true, means the Head Lamp Status data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the Head Lamp Status data
	 *         has been unsubscribed.
	 */
    public Boolean getHeadLampStatus() {
        return getBoolean(KEY_HEAD_LAMP_STATUS);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from Engine Torque data
	 *
	 * @param engineTorque
	 *            a boolean value
	 */
	public void setEngineTorque(Boolean engineTorque) {
		setParameters(KEY_ENGINE_TORQUE, engineTorque);
	}

	/**
	 * Gets a boolean value. If true, means the Engine Torque data has been
	 * unsubscribed.
	 *
	 * @return Boolean -a Boolean value. If true, means the Engine Torque data
	 *         has been unsubscribed.
	 */
	public Boolean getEngineTorque() {
		return getBoolean(KEY_ENGINE_TORQUE);
	}

	/**
	 * Sets a boolean value. If true, unsubscribes from Engine Oil Life data
	 *
	 * @param engineOilLife
	 *            a boolean value
	 */
	public void setEngineOilLife(Boolean engineOilLife) {
		setParameters(KEY_ENGINE_OIL_LIFE, engineOilLife);
	}

	/**
	 * Gets a boolean value. If true, means the Engine Oil Life data has been
	 * unsubscribed.
	 *
	 * @return Boolean -a Boolean value. If true, means the Engine Oil Life data
	 *         has been unsubscribed.
	 */
	public Boolean getEngineOilLife() {
		return getBoolean(KEY_ENGINE_OIL_LIFE);
	}


	/**
	 * Sets a boolean value. If true, unsubscribes from accPedalPosition data
	 * 
	 * @param accPedalPosition
	 *            a boolean value
	 */
    public void setAccPedalPosition(Boolean accPedalPosition) {
		setParameters(KEY_ACC_PEDAL_POSITION, accPedalPosition);
    }

	/**
	 * Gets a boolean value. If true, means the accPedalPosition data has been
	 * unsubscribed.
	 * 
	 * @return Boolean -a Boolean value. If true, means the accPedalPosition data
	 *         has been unsubscribed.
	 */
    public Boolean getAccPedalPosition() {
        return getBoolean(KEY_ACC_PEDAL_POSITION);
    }

    public void setSteeringWheelAngle(Boolean steeringWheelAngle) {
		setParameters(KEY_STEERING_WHEEL_ANGLE, steeringWheelAngle);
    }

    public Boolean getSteeringWheelAngle() {
        return getBoolean(KEY_STEERING_WHEEL_ANGLE);
    }    
        
    public void setECallInfo(Boolean eCallInfo) {
		setParameters(KEY_E_CALL_INFO, eCallInfo);
    }
    public Boolean getECallInfo() {
        return getBoolean(KEY_E_CALL_INFO);
    }
    public void setAirbagStatus(Boolean airbagStatus) {
		setParameters(KEY_AIRBAG_STATUS, airbagStatus);
    }
    public Boolean getAirbagStatus() {
        return getBoolean(KEY_AIRBAG_STATUS);
    }
    public void setEmergencyEvent(Boolean emergencyEvent) {
		setParameters(KEY_EMERGENCY_EVENT, emergencyEvent);
    }
    public Boolean getEmergencyEvent() {
        return getBoolean(KEY_EMERGENCY_EVENT);
    }
    public void setClusterModeStatus(Boolean clusterModeStatus) {
		setParameters(KEY_CLUSTER_MODE_STATUS, clusterModeStatus);
    }
    public Boolean getClusterModeStatus() {
        return getBoolean(KEY_CLUSTER_MODE_STATUS);
    }
    public void setMyKey(Boolean myKey) {
		setParameters(KEY_MY_KEY, myKey);
    }
    public Boolean getMyKey() {
        return getBoolean(KEY_MY_KEY);
    }

	/**
	 * Sets a boolean value. If true, unsubscribes from fuelRange data
	 *
	 * @param fuelRange
	 *            a boolean value
	 */
	public void setFuelRange(Boolean fuelRange) {
		setParameters(KEY_FUEL_RANGE, fuelRange);
	}

	/**
	 * Gets a boolean value. If true, means the fuelRange data has been
	 * unsubscribed.
	 *
	 * @return Boolean -a Boolean value. If true, means the fuelRange data
	 *         has been unsubscribed.
	 */
	public Boolean getFuelRange() {
		return getBoolean(KEY_FUEL_RANGE);
	}

	/**
	 * Sets a boolean value. If true, unsubscribes from turnSignal data
	 * @param turnSignal a boolean value
	 */
	public void setTurnSignal(Boolean turnSignal) { setParameters(KEY_TURN_SIGNAL, turnSignal); }

	/**
	 * Gets a boolean value. If true, means the turnSignal data has been unsubscribed.
	 * @return a Boolean value.
	 */
	public Boolean getTurnSignal() { return getBoolean(KEY_TURN_SIGNAL); }

	/**
	 * Sets a boolean value. If true, unsubscribes from electronicParkBrakeStatus data
	 * @param electronicParkBrakeStatus a boolean value
	 */
	public void setElectronicParkBrakeStatus(Boolean electronicParkBrakeStatus) {
		setParameters(KEY_ELECTRONIC_PARK_BRAKE_STATUS, electronicParkBrakeStatus);
	}

	/**
	 * Gets a boolean value. If true, means the electronicParkBrakeStatus data has been subscribed.
	 * @return a Boolean value.
	 */
	public Boolean getElectronicParkBrakeStatus() {
		return getBoolean(KEY_ELECTRONIC_PARK_BRAKE_STATUS);
	}

	/**
	 * Sets a boolean value. If true, unsubscribes from cloudAppVehicleID data
	 * @param cloudAppVehicleID a boolean value. 
	 */
	public void setCloudAppVehicleID(boolean cloudAppVehicleID){
		setParameters(KEY_CLOUD_APP_VEHICLE_ID, cloudAppVehicleID);
	}

	/**
	 * Gets a boolean value. If true, means the cloudAppVehicleID data has been unsubscribed.
	 * @return a Boolean value.
	 */
	public Boolean getCloudAppVehicleID(){
		return getBoolean( KEY_CLOUD_APP_VEHICLE_ID);
	}

	/**
	 * Sets a boolean value for OEM Custom VehicleData.
	 * @param vehicleDataName a String value
	 * @param vehicleDataState a boolean value
	 */
	public void setOEMCustomVehicleData(String vehicleDataName, Boolean vehicleDataState){
		setParameters(vehicleDataName, vehicleDataState);
	}

	/**
	 * Gets a boolean value for OEM Custom VehicleData.
	 * @return a Boolean value.
	 */
	public Boolean getOEMCustomVehicleData(String vehicleDataName){
		return getBoolean(vehicleDataName);
	}

    /**
     * Sets the stabilityControlsStatus.
     *
     * @param stabilityControlsStatus See StabilityControlsStatus
     * @since SmartDeviceLink 7.0.0
     */
    public void setStabilityControlsStatus(Boolean stabilityControlsStatus) {
        setParameters(KEY_STABILITY_CONTROLS_STATUS, stabilityControlsStatus);
    }

    /**
     * Gets the stabilityControlsStatus.
     *
     * @return Boolean See StabilityControlsStatus
     * @since SmartDeviceLink 7.0.0
     */
    public Boolean getStabilityControlsStatus() {
        return getBoolean(KEY_STABILITY_CONTROLS_STATUS);
    }
}
