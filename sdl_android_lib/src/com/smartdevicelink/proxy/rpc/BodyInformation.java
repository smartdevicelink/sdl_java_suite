package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;

public class BodyInformation extends RPCStruct {
    public static final String KEY_PARK_BRAKE_ACTIVE = "parkBrakeActive";
    public static final String KEY_IGNITION_STABLE_STATUS = "ignitionStableStatus";
    public static final String KEY_IGNITION_STATUS = "ignitionStatus";
    public static final String KEY_DRIVER_DOOR_AJAR = "driverDoorAjar";
    public static final String KEY_PASSENGER_DOOR_AJAR = "passengerDoorAjar";
    public static final String KEY_REAR_LEFT_DOOR_AJAR = "rearLeftDoorAjar";
    public static final String KEY_REAR_RIGHT_DOOR_AJAR = "rearRightDoorAjar";

    public BodyInformation() { }
    public BodyInformation(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setParkBrakeActive(Boolean parkBrakeActive) {
        if (parkBrakeActive != null) {
        	store.put(KEY_PARK_BRAKE_ACTIVE, parkBrakeActive);
        } else {
        	store.remove(KEY_PARK_BRAKE_ACTIVE);
        }
    }
    public Boolean getParkBrakeActive() {
        return (Boolean) store.get(KEY_PARK_BRAKE_ACTIVE);
    }
    public void setIgnitionStableStatus(IgnitionStableStatus ignitionStableStatus) {
        if (ignitionStableStatus != null) {
            store.put(KEY_IGNITION_STABLE_STATUS, ignitionStableStatus);
        } else {
        	store.remove(KEY_IGNITION_STABLE_STATUS);
        }
    }
    public IgnitionStableStatus getIgnitionStableStatus() {
        Object obj = store.get(KEY_IGNITION_STABLE_STATUS);
        if (obj instanceof IgnitionStableStatus) {
            return (IgnitionStableStatus) obj;
        } else if (obj instanceof String) {
        	return IgnitionStableStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setIgnitionStatus(IgnitionStatus ignitionStatus) {
        if (ignitionStatus != null) {
            store.put(KEY_IGNITION_STATUS, ignitionStatus);
        } else {
        	store.remove(KEY_IGNITION_STATUS);
        }
    }
    public IgnitionStatus getIgnitionStatus() {
        Object obj = store.get(KEY_IGNITION_STATUS);
        if (obj instanceof IgnitionStatus) {
            return (IgnitionStatus) obj;
        } else if (obj instanceof String) {
            return IgnitionStatus.valueForString((String) obj);
        }
        return null;
    }
    
    public void setDriverDoorAjar(Boolean driverDoorAjar) {
        if (driverDoorAjar != null) {
        	store.put(KEY_DRIVER_DOOR_AJAR, driverDoorAjar);
        } else {
        	store.remove(KEY_DRIVER_DOOR_AJAR);
        }
    }    
    public Boolean getDriverDoorAjar() {
        return (Boolean) store.get(KEY_DRIVER_DOOR_AJAR);
    }
    
    
    public void setPassengerDoorAjar(Boolean passengerDoorAjar) {
        if (passengerDoorAjar != null) {
        	store.put(KEY_PASSENGER_DOOR_AJAR, passengerDoorAjar);
        } else {
        	store.remove(KEY_PASSENGER_DOOR_AJAR);
        }
    }    
    public Boolean getPassengerDoorAjar() {
        return (Boolean) store.get(KEY_PASSENGER_DOOR_AJAR);
    }
    
    public void setRearLeftDoorAjar(Boolean rearLeftDoorAjar) {
        if (rearLeftDoorAjar != null) {
        	store.put(KEY_REAR_LEFT_DOOR_AJAR, rearLeftDoorAjar);
        } else {
        	store.remove(KEY_REAR_LEFT_DOOR_AJAR);
        }
    }    
    public Boolean getRearLeftDoorAjar() {
        return (Boolean) store.get(KEY_REAR_LEFT_DOOR_AJAR);
    }

    public void setRearRightDoorAjar(Boolean rearRightDoorAjar) {
        if (rearRightDoorAjar != null) {
        	store.put(KEY_REAR_RIGHT_DOOR_AJAR, rearRightDoorAjar);
        } else {
        	store.remove(KEY_REAR_RIGHT_DOOR_AJAR);
        }
    }    
    public Boolean getRearRightDoorAjar() {
        return (Boolean) store.get(KEY_REAR_RIGHT_DOOR_AJAR);
    }     
    
}
