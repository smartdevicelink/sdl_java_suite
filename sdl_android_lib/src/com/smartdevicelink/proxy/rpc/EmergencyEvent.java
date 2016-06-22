package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
/** Information related to an emergency event (and if it occurred).
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>emergencyEventType</td>
 * 			<td>EmergencyEventType</td>
 * 			<td></td>
 * 			<td>References signal "VedsEvntType_D_Ltchd". See{@linkplain EmergencyEventType}</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>fuelCutoffStatus</td>
 * 			<td>FuelCutoffStatus</td>
 * 			<td></td>
 * 			<td>References signal "RCM_FuelCutoff". See{@linkplain FuelCutoffStatus}</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rolloverEvent</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td></td>
 * 			<td>References signal "VedsEvntRoll_D_Ltchd". See{@linkplain VehicleDataEventStatus}</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>maximumChangeVelocity</td>
 * 			<td>Integer</td>
 * 			<td></td>
 * 			<td>References signal "VedsMaxDeltaV_D_Ltchd".</td>
 * 			<td>minvalue=0; maxvalue=255;<p> Additional reserved values:</p> <p>0x00 No event; 0xFE Not supported; 0xFF Fault</p> </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 *
 * 		<tr>
 * 			<td>multipleEvents</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td></td>
 * 			<td>References signal "VedsMultiEvnt_D_Ltchd". See{@linkplain VehicleDataEventStatus}</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * 
 * @see Image
 * @see SubscribeVehicleData
 * @since SmartDeviceLink 2.0
 *
 */

public class EmergencyEvent extends RPCStruct {
    public static final String KEY_EMERGENCY_EVENT_TYPE = "emergencyEventType";
    public static final String KEY_FUEL_CUTOFF_STATUS = "fuelCutoffStatus";
    public static final String KEY_ROLLOVER_EVENT = "rolloverEvent";
    public static final String KEY_MAXIMUM_CHANGE_VELOCITY = "maximumChangeVelocity";
    public static final String KEY_MULTIPLE_EVENTS = "multipleEvents";
	
	/** Constructs a new EmergencyEvent object indicated by the Hashtable
	 * parameter
	 * @param hash
	 * 
	 * <p>The hash table to use</p>
	 */

    public EmergencyEvent() { }
    public EmergencyEvent(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setEmergencyEventType(EmergencyEventType emergencyEventType) {
        if (emergencyEventType != null) {
            store.put(KEY_EMERGENCY_EVENT_TYPE, emergencyEventType);
        } else {
        	store.remove(KEY_EMERGENCY_EVENT_TYPE);
        }
    }
    public EmergencyEventType getEmergencyEventType() {
        Object obj = store.get(KEY_EMERGENCY_EVENT_TYPE);
        if (obj instanceof EmergencyEventType) {
            return (EmergencyEventType) obj;
        } else if (obj instanceof String) {
        	return EmergencyEventType.valueForString((String) obj);
        }
        return null;
    }
    public void setFuelCutoffStatus(FuelCutoffStatus fuelCutoffStatus) {
        if (fuelCutoffStatus != null) {
            store.put(KEY_FUEL_CUTOFF_STATUS, fuelCutoffStatus);
        } else {
        	store.remove(KEY_FUEL_CUTOFF_STATUS);
        }
    }
    public FuelCutoffStatus getFuelCutoffStatus() {
        Object obj = store.get(KEY_FUEL_CUTOFF_STATUS);
        if (obj instanceof FuelCutoffStatus) {
            return (FuelCutoffStatus) obj;
        } else if (obj instanceof String) {
        	return FuelCutoffStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setRolloverEvent(VehicleDataEventStatus rolloverEvent) {
        if (rolloverEvent != null) {
            store.put(KEY_ROLLOVER_EVENT, rolloverEvent);
        } else {
        	store.remove(KEY_ROLLOVER_EVENT);
        }
    }
    public VehicleDataEventStatus getRolloverEvent() {
        Object obj = store.get(KEY_ROLLOVER_EVENT);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setMaximumChangeVelocity(Integer maximumChangeVelocity) {
        if (maximumChangeVelocity != null) {
            store.put(KEY_MAXIMUM_CHANGE_VELOCITY, maximumChangeVelocity);
        } else {
        	store.remove(KEY_MAXIMUM_CHANGE_VELOCITY);
        }
    }
    public Integer getMaximumChangeVelocity() {
    	return (Integer) store.get(KEY_MAXIMUM_CHANGE_VELOCITY);
    }
    public void setMultipleEvents(VehicleDataEventStatus multipleEvents) {
        if (multipleEvents != null) {
            store.put(KEY_MULTIPLE_EVENTS, multipleEvents);
        } else {
        	store.remove(KEY_MULTIPLE_EVENTS);
        }
    }
    public VehicleDataEventStatus getMultipleEvents() {
        Object obj = store.get(KEY_MULTIPLE_EVENTS);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	return VehicleDataEventStatus.valueForString((String) obj);
        }
        return null;
    }
}
