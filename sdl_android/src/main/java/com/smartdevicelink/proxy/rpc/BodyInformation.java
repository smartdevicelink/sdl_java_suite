package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;

import java.util.Hashtable;

import static com.smartdevicelink.proxy.rpc.AirbagStatus.KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED;

/** The body information including power modes.
 * 
 * <p><b>Note:</b> The structure defines the information about the park brake and ignition.</p>
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>parkBrakeActive</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>Describes, if the parkbreak is active. The information about the park brake: - true, if active - false if not.</td>
 *  			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>ignitionStableStatus</td>
 * 			<td>IgnitionStableStatus</td>
 * 			<td>true</td>
 * 			<td>Describes, if the ignition situation is considered stableThe information about stability of the ignition switch. See {@linkplain IgnitionStableStatus}</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>ignitionStatus</td>
 * 			<td>IgnitionStatus</td>
 * 			<td>true</td>
 * 			<td>The information about ignition status. See {@linkplain  IgnitionStatus}</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>parkBrakeActive</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>The information about the park brake: - true, if active - false if not.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>driverDoorAjar</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>The information about the park brake: - true, if active - false if not.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerDoorAjar</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>The information about the park brake: - true, if active - false if not.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rearLeftDoorAjar</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>The information about the park brake: - true, if active - false if not.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rearRightDoorAjar</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>References signal "DrStatRr_B_Actl".</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 
 *  </table>
 * @since SmartDeviceLink 2.0
 * @see SubscribeVehicleData  
 * @see GetVehicleData
 * @see OnVehicleData 
 *
 */

public class BodyInformation extends RPCStruct {
    public static final String KEY_PARK_BRAKE_ACTIVE = "parkBrakeActive";
    public static final String KEY_IGNITION_STABLE_STATUS = "ignitionStableStatus";
    public static final String KEY_IGNITION_STATUS = "ignitionStatus";
    public static final String KEY_DRIVER_DOOR_AJAR = "driverDoorAjar";
    public static final String KEY_PASSENGER_DOOR_AJAR = "passengerDoorAjar";
    public static final String KEY_REAR_LEFT_DOOR_AJAR = "rearLeftDoorAjar";
    public static final String KEY_REAR_RIGHT_DOOR_AJAR = "rearRightDoorAjar";
	/** Constructs a new BodyInformation object indicated by the Hashtable
	 * parameter
	 * @param hash
	 * The hash table to use
	 * 
	 *
	 */

    public BodyInformation() { }
    public BodyInformation(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setParkBrakeActive(Boolean parkBrakeActive) {
        setValue(KEY_PARK_BRAKE_ACTIVE, parkBrakeActive);
    }
    public Boolean getParkBrakeActive() {
        return getBoolean(KEY_PARK_BRAKE_ACTIVE);
    }
    public void setIgnitionStableStatus(IgnitionStableStatus ignitionStableStatus) {
        setValue(KEY_IGNITION_STABLE_STATUS, ignitionStableStatus);
    }
    public IgnitionStableStatus getIgnitionStableStatus() {
        return (IgnitionStableStatus) getObject(IgnitionStableStatus.class, KEY_IGNITION_STABLE_STATUS);
    }
    public void setIgnitionStatus(IgnitionStatus ignitionStatus) {
        setValue(KEY_IGNITION_STATUS, ignitionStatus);
    }
    public IgnitionStatus getIgnitionStatus() {
        return (IgnitionStatus) getObject(IgnitionStatus.class, KEY_IGNITION_STATUS);
    }
    
    public void setDriverDoorAjar(Boolean driverDoorAjar) {
        setValue(KEY_DRIVER_DOOR_AJAR, driverDoorAjar);
    }    
    public Boolean getDriverDoorAjar() {
        return getBoolean(KEY_DRIVER_DOOR_AJAR);
    }
    
    
    public void setPassengerDoorAjar(Boolean passengerDoorAjar) {
        setValue(KEY_PASSENGER_DOOR_AJAR, passengerDoorAjar);
    }    
    public Boolean getPassengerDoorAjar() {
        return getBoolean(KEY_PASSENGER_DOOR_AJAR);
    }
    
    public void setRearLeftDoorAjar(Boolean rearLeftDoorAjar) {
        setValue(KEY_REAR_LEFT_DOOR_AJAR, rearLeftDoorAjar);
    }    
    public Boolean getRearLeftDoorAjar() {
        return getBoolean(KEY_REAR_LEFT_DOOR_AJAR);
    }

    public void setRearRightDoorAjar(Boolean rearRightDoorAjar) {
        setValue(KEY_REAR_RIGHT_DOOR_AJAR, rearRightDoorAjar);
    }    
    public Boolean getRearRightDoorAjar() {
        return getBoolean(KEY_REAR_RIGHT_DOOR_AJAR);
    }     
    
}
