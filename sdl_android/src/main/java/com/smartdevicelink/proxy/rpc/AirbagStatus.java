package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;

import static com.smartdevicelink.proxy.constants.Names.driverCurtainAirbagDeployed;
import static com.smartdevicelink.proxy.constants.Names.driverSideAirbagDeployed;

/**
 * <p>The status of the air bags.</p>
 *
 *
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Version.</th>
 * 		</tr>
 * 		<tr>
 * 			<td>driverAirbagDeployed </td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of driver airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>driverSideAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of driver side airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>driverCurtainAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of driver curtain airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of passenger airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerCurtainAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of passenger curtain airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>driverKneeAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of driver knee airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerSideAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of passenger side airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerKneeAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of passenger knee airbag</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *
 *  </table>
 *  
 *  <p><b>Response:</b></p>
 *  If a resultCode of "SUCCESS" is returned, the request was accepted by SDL. By the time the corresponding response is received, the Alert will have completed. 
 *  
 *  <p><b> Non-default Result Codes:</b></p>
 *  <p>REJECTED</p><p>	ABORTED</P>
 *  
 *  
 * @since SmartDeviceLink 1.0
 *  
 * @see TextFieldName
 * @see Image
 * @see SubscribeVehicleData
 */
public class AirbagStatus extends RPCStruct {
    public static final String KEY_DRIVER_AIRBAG_DEPLOYED = "driverAirbagDeployed";
    public static final String KEY_DRIVER_SIDE_AIRBAG_DEPLOYED = "driverSideAirbagDeployed";
    public static final String KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED = "driverCurtainAirbagDeployed";
    public static final String KEY_DRIVER_KNEE_AIRBAG_DEPLOYED = "driverKneeAirbagDeployed";
    public static final String KEY_PASSENGER_AIRBAG_DEPLOYED = "passengerAirbagDeployed";
    public static final String KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED = "passengerSideAirbagDeployed";
    public static final String KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED = "passengerCurtainAirbagDeployed";
    public static final String KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED = "passengerKneeAirbagDeployed";
	/** Constructs a new AirbagStatus object indicated by the Hashtable
	 * parameter
	 * @param hash
	 * The hash table to use
	 * 
	 *
	 */

    public AirbagStatus() { }
    public AirbagStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setDriverAirbagDeployed(VehicleDataEventStatus driverAirbagDeployed) {
        setValue(KEY_DRIVER_AIRBAG_DEPLOYED, driverAirbagDeployed);
    }
    public VehicleDataEventStatus getDriverAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_AIRBAG_DEPLOYED);
    }
    public void setDriverSideAirbagDeployed(VehicleDataEventStatus driverSideAirbagDeployed) {
        setValue(KEY_DRIVER_SIDE_AIRBAG_DEPLOYED, driverSideAirbagDeployed);
    }
    public VehicleDataEventStatus getDriverSideAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_SIDE_AIRBAG_DEPLOYED);
    }
    public void setDriverCurtainAirbagDeployed(VehicleDataEventStatus driverCurtainAirbagDeployed) {
        setValue(KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED, driverCurtainAirbagDeployed);
    }
    public VehicleDataEventStatus getDriverCurtainAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED);
    }
    public void setPassengerAirbagDeployed(VehicleDataEventStatus passengerAirbagDeployed) {
        setValue(KEY_PASSENGER_AIRBAG_DEPLOYED, passengerAirbagDeployed);
    }
    public VehicleDataEventStatus getPassengerAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_PASSENGER_AIRBAG_DEPLOYED);
    }
    public void setPassengerCurtainAirbagDeployed(VehicleDataEventStatus passengerCurtainAirbagDeployed) {
        setValue(KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED, passengerCurtainAirbagDeployed);
    }
    public VehicleDataEventStatus getPassengerCurtainAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED);
    }
    public void setDriverKneeAirbagDeployed(VehicleDataEventStatus driverKneeAirbagDeployed) {
        setValue(KEY_DRIVER_KNEE_AIRBAG_DEPLOYED, driverKneeAirbagDeployed);
    }
    public VehicleDataEventStatus getDriverKneeAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_KNEE_AIRBAG_DEPLOYED);
    }
    public void setPassengerSideAirbagDeployed(VehicleDataEventStatus passengerSideAirbagDeployed) {
        setValue(KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED, passengerSideAirbagDeployed);
    }
    public VehicleDataEventStatus getPassengerSideAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED);
    }
    public void setPassengerKneeAirbagDeployed(VehicleDataEventStatus passengerKneeAirbagDeployed) {
        setValue(KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED, passengerKneeAirbagDeployed);
    }
    public VehicleDataEventStatus getPassengerKneeAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED);
    }
}
