package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.util.DebugTool;

public class EmergencyEvent extends RPCStruct {
    public static final String emergencyEventType = "emergencyEventType";
    public static final String fuelCutoffStatus = "fuelCutoffStatus";
    public static final String rolloverEvent = "rolloverEvent";
    public static final String maximumChangeVelocity = "maximumChangeVelocity";
    public static final String multipleEvents = "multipleEvents";

    public EmergencyEvent() { }
    public EmergencyEvent(Hashtable hash) {
        super(hash);
    }

    public void setEmergencyEventType(EmergencyEventType emergencyEventType) {
        if (emergencyEventType != null) {
            store.put(EmergencyEvent.emergencyEventType, emergencyEventType);
        } else {
        	store.remove(EmergencyEvent.emergencyEventType);
        }
    }
    public EmergencyEventType getEmergencyEventType() {
        Object obj = store.get(EmergencyEvent.emergencyEventType);
        if (obj instanceof EmergencyEventType) {
            return (EmergencyEventType) obj;
        } else if (obj instanceof String) {
        	EmergencyEventType theCode = null;
            try {
                theCode = EmergencyEventType.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + EmergencyEvent.emergencyEventType, e);
            }
            return theCode;
        }
        return null;
    }
    public void setFuelCutoffStatus(FuelCutoffStatus fuelCutoffStatus) {
        if (fuelCutoffStatus != null) {
            store.put(EmergencyEvent.fuelCutoffStatus, fuelCutoffStatus);
        } else {
        	store.remove(EmergencyEvent.fuelCutoffStatus);
        }
    }
    public FuelCutoffStatus getFuelCutoffStatus() {
        Object obj = store.get(EmergencyEvent.fuelCutoffStatus);
        if (obj instanceof FuelCutoffStatus) {
            return (FuelCutoffStatus) obj;
        } else if (obj instanceof String) {
        	FuelCutoffStatus theCode = null;
            try {
                theCode = FuelCutoffStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + EmergencyEvent.fuelCutoffStatus, e);
            }
            return theCode;
        }
        return null;
    }
    public void setRolloverEvent(VehicleDataEventStatus rolloverEvent) {
        if (rolloverEvent != null) {
            store.put(EmergencyEvent.rolloverEvent, rolloverEvent);
        } else {
        	store.remove(EmergencyEvent.rolloverEvent);
        }
    }
    public VehicleDataEventStatus getRolloverEvent() {
        Object obj = store.get(EmergencyEvent.rolloverEvent);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + EmergencyEvent.rolloverEvent, e);
            }
            return theCode;
        }
        return null;
    }
    public void setMaximumChangeVelocity(Integer maximumChangeVelocity) {
        if (maximumChangeVelocity != null) {
            store.put(EmergencyEvent.maximumChangeVelocity, maximumChangeVelocity);
        } else {
        	store.remove(EmergencyEvent.maximumChangeVelocity);
        }
    }
    public Integer getMaximumChangeVelocity() {
    	return (Integer) store.get(EmergencyEvent.maximumChangeVelocity);
    }
    public void setMultipleEvents(VehicleDataEventStatus multipleEvents) {
        if (multipleEvents != null) {
            store.put(EmergencyEvent.multipleEvents, multipleEvents);
        } else {
        	store.remove(EmergencyEvent.multipleEvents);
        }
    }
    public VehicleDataEventStatus getMultipleEvents() {
        Object obj = store.get(EmergencyEvent.multipleEvents);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + EmergencyEvent.multipleEvents, e);
            }
            return theCode;
        }
        return null;
    }
}
