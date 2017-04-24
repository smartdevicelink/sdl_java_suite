package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
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
        if (driverAirbagDeployed != null) {
            store.put(KEY_DRIVER_AIRBAG_DEPLOYED, driverAirbagDeployed);
        } else {
            store.remove(KEY_DRIVER_AIRBAG_DEPLOYED);
        }
    }
    public VehicleDataEventStatus getDriverAirbagDeployed() {
        Object obj = store.get(KEY_DRIVER_AIRBAG_DEPLOYED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setDriverSideAirbagDeployed(VehicleDataEventStatus driverSideAirbagDeployed) {
        if (driverSideAirbagDeployed != null) {
            store.put(KEY_DRIVER_SIDE_AIRBAG_DEPLOYED, driverSideAirbagDeployed);
        } else {
            store.remove(KEY_DRIVER_SIDE_AIRBAG_DEPLOYED);
        }
    }
    public VehicleDataEventStatus getDriverSideAirbagDeployed() {
        Object obj = store.get(KEY_DRIVER_SIDE_AIRBAG_DEPLOYED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setDriverCurtainAirbagDeployed(VehicleDataEventStatus driverCurtainAirbagDeployed) {
        if (driverCurtainAirbagDeployed != null) {
            store.put(KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED, driverCurtainAirbagDeployed);
        } else {
            store.remove(KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED);
        }
    }
    public VehicleDataEventStatus getDriverCurtainAirbagDeployed() {
        Object obj = store.get(KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setPassengerAirbagDeployed(VehicleDataEventStatus passengerAirbagDeployed) {
        if (passengerAirbagDeployed != null) {
            store.put(KEY_PASSENGER_AIRBAG_DEPLOYED, passengerAirbagDeployed);
        } else {
            store.remove(KEY_PASSENGER_AIRBAG_DEPLOYED);
        }
    }
    public VehicleDataEventStatus getPassengerAirbagDeployed() {
        Object obj = store.get(KEY_PASSENGER_AIRBAG_DEPLOYED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setPassengerCurtainAirbagDeployed(VehicleDataEventStatus passengerCurtainAirbagDeployed) {
        if (passengerCurtainAirbagDeployed != null) {
            store.put(KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED, passengerCurtainAirbagDeployed);
        } else {
            store.remove(KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED);
        }
    }
    public VehicleDataEventStatus getPassengerCurtainAirbagDeployed() {
        Object obj = store.get(KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setDriverKneeAirbagDeployed(VehicleDataEventStatus driverKneeAirbagDeployed) {
        if (driverKneeAirbagDeployed != null) {
            store.put(KEY_DRIVER_KNEE_AIRBAG_DEPLOYED, driverKneeAirbagDeployed);
        } else {
            store.remove(KEY_DRIVER_KNEE_AIRBAG_DEPLOYED);
        }
    }
    public VehicleDataEventStatus getDriverKneeAirbagDeployed() {
        Object obj = store.get(KEY_DRIVER_KNEE_AIRBAG_DEPLOYED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setPassengerSideAirbagDeployed(VehicleDataEventStatus passengerSideAirbagDeployed) {
        if (passengerSideAirbagDeployed != null) {
            store.put(KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED, passengerSideAirbagDeployed);
        } else {
            store.remove(KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED);
        }
    }
    public VehicleDataEventStatus getPassengerSideAirbagDeployed() {
        Object obj = store.get(KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setPassengerKneeAirbagDeployed(VehicleDataEventStatus passengerKneeAirbagDeployed) {
        if (passengerKneeAirbagDeployed != null) {
            store.put(KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED, passengerKneeAirbagDeployed);
        } else {
            store.remove(KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED);
        }
    }
    public VehicleDataEventStatus getPassengerKneeAirbagDeployed() {
        Object obj = store.get(KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
}
