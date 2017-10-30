package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;

import static android.provider.Contacts.SettingsColumns.KEY;
import static com.smartdevicelink.proxy.constants.Names.multipleEvents;
import static com.smartdevicelink.proxy.rpc.TireStatus.KEY_INNER_RIGHT_REAR;

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
        setValue(KEY_EMERGENCY_EVENT_TYPE, emergencyEventType);
    }
    public EmergencyEventType getEmergencyEventType() {
        return (EmergencyEventType) getObject(EmergencyEventType.class, KEY_EMERGENCY_EVENT_TYPE);
    }
    public void setFuelCutoffStatus(FuelCutoffStatus fuelCutoffStatus) {
        setValue(KEY_FUEL_CUTOFF_STATUS, fuelCutoffStatus);
    }
    public FuelCutoffStatus getFuelCutoffStatus() {
        return (FuelCutoffStatus) getObject(FuelCutoffStatus.class, KEY_FUEL_CUTOFF_STATUS);
    }
    public void setRolloverEvent(VehicleDataEventStatus rolloverEvent) {
        setValue(KEY_ROLLOVER_EVENT, rolloverEvent);
    }
    public VehicleDataEventStatus getRolloverEvent() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_ROLLOVER_EVENT);
    }
    public void setMaximumChangeVelocity(Integer maximumChangeVelocity) {
        setValue(KEY_MAXIMUM_CHANGE_VELOCITY, maximumChangeVelocity);
    }
    public Integer getMaximumChangeVelocity() {
    	return getInteger(KEY_MAXIMUM_CHANGE_VELOCITY);
    }
    public void setMultipleEvents(VehicleDataEventStatus multipleEvents) {
        setValue(KEY_MULTIPLE_EVENTS, multipleEvents);
    }
    public VehicleDataEventStatus getMultipleEvents() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_MULTIPLE_EVENTS);
    }
}
