package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
/** The status of the seat belts. 
 *
 * <p><b>Parameter List</b></p>
 * 
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 		</tr>
 * 		<tr>
 * 			<td>driverBeltDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The driver seat belt is deployed.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerBeltDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The passenger seat belt is deployed.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerBuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The passenger seat belt is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>driverBuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The driver seat belt is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>leftRow2BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The left seat belt of the 2nd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerChildDetected</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The child passenger is detected.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rightRow2BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The right seat belt of the 2nd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>middleRow2BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The middle seat belt of the 2nd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>middleRow3BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The middle seat belt of the 3rd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>leftRow3BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The left seat belt of the 3rd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rightRow3BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The right seat belt of the 3rd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>leftRearInflatableBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The left rear inflatable is belted.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rightRearInflatableBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The right rear inflatable is belted.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>middleRow1BeltDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The seat belt of the middle row is deployed.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>middleRow1BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The seat belt of the middle row is buckled.</td>
 * 		</tr>
 *  </table>
* @since SmartDeviceLink 2.0
* @see VehicleDataEventStatus
* @see GetVehicleData 
* @see OnVehicleData
 * @see SubscribeVehicleData
 */

public class BeltStatus extends RPCStruct {
    public static final String KEY_DRIVER_BELT_DEPLOYED = "driverBeltDeployed";
    public static final String KEY_PASSENGER_BELT_DEPLOYED = "passengerBeltDeployed";
    public static final String KEY_PASSENGER_BUCKLE_BELTED = "passengerBuckleBelted";
    public static final String KEY_DRIVER_BUCKLE_BELTED = "driverBuckleBelted";
    public static final String KEY_LEFT_ROW_2_BUCKLE_BELTED = "leftRow2BuckleBelted";
    public static final String KEY_PASSENGER_CHILD_DETECTED = "passengerChildDetected";
    public static final String KEY_RIGHT_ROW_2_BUCKLE_BELTED = "rightRow2BuckleBelted";
    public static final String KEY_MIDDLE_ROW_2_BUCKLE_BELTED = "middleRow2BuckleBelted";
    public static final String KEY_MIDDLE_ROW_3_BUCKLE_BELTED = "middleRow3BuckleBelted";
    public static final String KEY_LEFT_ROW_3_BUCKLE_BELTED = "leftRow3BuckleBelted";
    public static final String KEY_RIGHT_ROW_3_BUCKLE_BELTED = "rightRow3BuckleBelted";
    public static final String KEY_REAR_INFLATABLE_BELTED = "rearInflatableBelted";
    public static final String KEY_RIGHT_REAR_INFLATABLE_BELTED = "rightRearInflatableBelted";
    public static final String KEY_MIDDLE_ROW_1_BELT_DEPLOYED = "middleRow1BeltDeployed";
    public static final String KEY_MIDDLE_ROW_1_BUCKLE_BELTED = "middleRow1BuckleBelted";
	/** Constructs a new BeltStatus object indicated by the Hashtable
	 * parameter
	 * @param hash
	 * The hash table to use
	 * 
	 *
	 */

    public BeltStatus() { }
    public BeltStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setDriverBeltDeployed(VehicleDataEventStatus driverBeltDeployed) {
        if (driverBeltDeployed != null) {
            store.put(KEY_DRIVER_BELT_DEPLOYED, driverBeltDeployed);
        } else {
        	store.remove(KEY_DRIVER_BELT_DEPLOYED);
        }
    }
    public VehicleDataEventStatus getDriverBeltDeployed() {
        Object obj = store.get(KEY_DRIVER_BELT_DEPLOYED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setPassengerBeltDeployed(VehicleDataEventStatus passengerBeltDeployed) {
        if (passengerBeltDeployed != null) {
            store.put(KEY_PASSENGER_BELT_DEPLOYED, passengerBeltDeployed);
        } else {
        	store.remove(KEY_PASSENGER_BELT_DEPLOYED);
        }
    }
    public VehicleDataEventStatus getPassengerBeltDeployed() {
        Object obj = store.get(KEY_PASSENGER_BELT_DEPLOYED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setPassengerBuckleBelted(VehicleDataEventStatus passengerBuckleBelted) {
        if (passengerBuckleBelted != null) {
            store.put(KEY_PASSENGER_BUCKLE_BELTED, passengerBuckleBelted);
        } else {
        	store.remove(KEY_PASSENGER_BUCKLE_BELTED);
        }
    }
    public VehicleDataEventStatus getPassengerBuckleBelted() {
        Object obj = store.get(KEY_PASSENGER_BUCKLE_BELTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setDriverBuckleBelted(VehicleDataEventStatus driverBuckleBelted) {
        if (driverBuckleBelted != null) {
            store.put(KEY_DRIVER_BUCKLE_BELTED, driverBuckleBelted);
        } else {
        	store.remove(KEY_DRIVER_BUCKLE_BELTED);
        }
    }
    public VehicleDataEventStatus getDriverBuckleBelted() {
        Object obj = store.get(KEY_DRIVER_BUCKLE_BELTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setLeftRow2BuckleBelted(VehicleDataEventStatus leftRow2BuckleBelted) {
        if (leftRow2BuckleBelted != null) {
            store.put(KEY_LEFT_ROW_2_BUCKLE_BELTED, leftRow2BuckleBelted);
        } else {
        	store.remove(KEY_LEFT_ROW_2_BUCKLE_BELTED);
        }
    }
    public VehicleDataEventStatus getLeftRow2BuckleBelted() {
        Object obj = store.get(KEY_LEFT_ROW_2_BUCKLE_BELTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setPassengerChildDetected(VehicleDataEventStatus passengerChildDetected) {
        if (passengerChildDetected != null) {
            store.put(KEY_PASSENGER_CHILD_DETECTED, passengerChildDetected);
        } else {
        	store.remove(KEY_PASSENGER_CHILD_DETECTED);
        }
    }
    public VehicleDataEventStatus getPassengerChildDetected() {
        Object obj = store.get(KEY_PASSENGER_CHILD_DETECTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setRightRow2BuckleBelted(VehicleDataEventStatus rightRow2BuckleBelted) {
        if (rightRow2BuckleBelted != null) {
            store.put(KEY_RIGHT_ROW_2_BUCKLE_BELTED, rightRow2BuckleBelted);
        } else {
        	store.remove(KEY_RIGHT_ROW_2_BUCKLE_BELTED);
        }
    }
    public VehicleDataEventStatus getRightRow2BuckleBelted() {
        Object obj = store.get(KEY_RIGHT_ROW_2_BUCKLE_BELTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setMiddleRow2BuckleBelted(VehicleDataEventStatus middleRow2BuckleBelted) {
        if (middleRow2BuckleBelted != null) {
            store.put(KEY_MIDDLE_ROW_2_BUCKLE_BELTED, middleRow2BuckleBelted);
        } else {
        	store.remove(KEY_MIDDLE_ROW_2_BUCKLE_BELTED);
        }
    }
    public VehicleDataEventStatus getMiddleRow2BuckleBelted() {
        Object obj = store.get(KEY_MIDDLE_ROW_2_BUCKLE_BELTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setMiddleRow3BuckleBelted(VehicleDataEventStatus middleRow3BuckleBelted) {
        if (middleRow3BuckleBelted != null) {
            store.put(KEY_MIDDLE_ROW_3_BUCKLE_BELTED, middleRow3BuckleBelted);
        } else {
        	store.remove(KEY_MIDDLE_ROW_3_BUCKLE_BELTED);
        }
    }
    public VehicleDataEventStatus getMiddleRow3BuckleBelted() {
        Object obj = store.get(KEY_MIDDLE_ROW_3_BUCKLE_BELTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setLeftRow3BuckleBelted(VehicleDataEventStatus leftRow3BuckleBelted) {
        if (leftRow3BuckleBelted != null) {
            store.put(KEY_LEFT_ROW_3_BUCKLE_BELTED, leftRow3BuckleBelted);
        } else {
        	store.remove(KEY_LEFT_ROW_3_BUCKLE_BELTED);
        }
    }
    public VehicleDataEventStatus getLeftRow3BuckleBelted() {
        Object obj = store.get(KEY_LEFT_ROW_3_BUCKLE_BELTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setRightRow3BuckleBelted(VehicleDataEventStatus rightRow3BuckleBelted) {
        if (rightRow3BuckleBelted != null) {
            store.put(KEY_RIGHT_ROW_3_BUCKLE_BELTED, rightRow3BuckleBelted);
        } else {
        	store.remove(KEY_RIGHT_ROW_3_BUCKLE_BELTED);
        }
    }
    public VehicleDataEventStatus getRightRow3BuckleBelted() {
        Object obj = store.get(KEY_RIGHT_ROW_3_BUCKLE_BELTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setLeftRearInflatableBelted(VehicleDataEventStatus rearInflatableBelted) {
        if (rearInflatableBelted != null) {
            store.put(KEY_REAR_INFLATABLE_BELTED, rearInflatableBelted);
        } else {
        	store.remove(KEY_REAR_INFLATABLE_BELTED);
        }
    }
    public VehicleDataEventStatus getLeftRearInflatableBelted() {
        Object obj = store.get(KEY_REAR_INFLATABLE_BELTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setRightRearInflatableBelted(VehicleDataEventStatus rightRearInflatableBelted) {
        if (rightRearInflatableBelted != null) {
            store.put(KEY_RIGHT_REAR_INFLATABLE_BELTED, rightRearInflatableBelted);
        } else {
        	store.remove(KEY_RIGHT_REAR_INFLATABLE_BELTED);
        }
    }
    public VehicleDataEventStatus getRightRearInflatableBelted() {
        Object obj = store.get(KEY_RIGHT_REAR_INFLATABLE_BELTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setMiddleRow1BeltDeployed(VehicleDataEventStatus middleRow1BeltDeployed) {
        if (middleRow1BeltDeployed != null) {
            store.put(KEY_MIDDLE_ROW_1_BELT_DEPLOYED, middleRow1BeltDeployed);
        } else {
        	store.remove(KEY_MIDDLE_ROW_1_BELT_DEPLOYED);
        }
    }
    public VehicleDataEventStatus getMiddleRow1BeltDeployed() {
        Object obj = store.get(KEY_MIDDLE_ROW_1_BELT_DEPLOYED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setMiddleRow1BuckleBelted(VehicleDataEventStatus middleRow1BuckleBelted) {
        if (middleRow1BuckleBelted != null) {
            store.put(KEY_MIDDLE_ROW_1_BUCKLE_BELTED, middleRow1BuckleBelted);
        } else {
        	store.remove(KEY_MIDDLE_ROW_1_BUCKLE_BELTED);
        }
    }
    public VehicleDataEventStatus getMiddleRow1BuckleBelted() {
        Object obj = store.get(KEY_MIDDLE_ROW_1_BUCKLE_BELTED);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
            return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
}
