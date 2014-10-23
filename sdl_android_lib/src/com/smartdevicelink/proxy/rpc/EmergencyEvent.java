package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.util.DebugTool;

public class EmergencyEvent extends RPCStruct {

    public EmergencyEvent() { }
    public EmergencyEvent(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setEmergencyEventType(EmergencyEventType emergencyEventType) {
        if (emergencyEventType != null) {
            store.put(Names.emergencyEventType, emergencyEventType);
        } else {
        	store.remove(Names.emergencyEventType);
        }
    }
    public EmergencyEventType getEmergencyEventType() {
        Object obj = store.get(Names.emergencyEventType);
        if (obj instanceof EmergencyEventType) {
            return (EmergencyEventType) obj;
        } else if (obj instanceof String) {
        	EmergencyEventType theCode = null;
            try {
                theCode = EmergencyEventType.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.emergencyEventType, e);
            }
            return theCode;
        }
        return null;
    }
    public void setFuelCutoffStatus(FuelCutoffStatus fuelCutoffStatus) {
        if (fuelCutoffStatus != null) {
            store.put(Names.fuelCutoffStatus, fuelCutoffStatus);
        } else {
        	store.remove(Names.fuelCutoffStatus);
        }
    }
    public FuelCutoffStatus getFuelCutoffStatus() {
        Object obj = store.get(Names.fuelCutoffStatus);
        if (obj instanceof FuelCutoffStatus) {
            return (FuelCutoffStatus) obj;
        } else if (obj instanceof String) {
        	FuelCutoffStatus theCode = null;
            try {
                theCode = FuelCutoffStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.fuelCutoffStatus, e);
            }
            return theCode;
        }
        return null;
    }
    public void setRolloverEvent(VehicleDataEventStatus rolloverEvent) {
        if (rolloverEvent != null) {
            store.put(Names.rolloverEvent, rolloverEvent);
        } else {
        	store.remove(Names.rolloverEvent);
        }
    }
    public VehicleDataEventStatus getRolloverEvent() {
        Object obj = store.get(Names.rolloverEvent);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.rolloverEvent, e);
            }
            return theCode;
        }
        return null;
    }
    public void setMaximumChangeVelocity(Integer maximumChangeVelocity) {
        if (maximumChangeVelocity != null) {
            store.put(Names.maximumChangeVelocity, maximumChangeVelocity);
        } else {
        	store.remove(Names.maximumChangeVelocity);
        }
    }
    public Integer getMaximumChangeVelocity() {
    	return (Integer) store.get(Names.maximumChangeVelocity);
    }
    public void setMultipleEvents(VehicleDataEventStatus multipleEvents) {
        if (multipleEvents != null) {
            store.put(Names.multipleEvents, multipleEvents);
        } else {
        	store.remove(Names.multipleEvents);
        }
    }
    public VehicleDataEventStatus getMultipleEvents() {
        Object obj = store.get(Names.multipleEvents);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.multipleEvents, e);
            }
            return theCode;
        }
        return null;
    }
}
