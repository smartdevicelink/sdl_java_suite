package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;
import com.smartdevicelink.util.DebugTool;

public class BodyInformation extends RPCStruct {

    public BodyInformation() { }
    public BodyInformation(Hashtable hash) {
        super(hash);
    }

    public void setParkBrakeActive(Boolean parkBrakeActive) {
        if (parkBrakeActive != null) {
        	store.put(Names.parkBrakeActive, parkBrakeActive);
        } else {
        	store.remove(Names.parkBrakeActive);
        }
    }
    public Boolean getParkBrakeActive() {
        return (Boolean) store.get(Names.parkBrakeActive);
    }
    public void setIgnitionStableStatus(IgnitionStableStatus ignitionStableStatus) {
        if (ignitionStableStatus != null) {
            store.put(Names.ignitionStableStatus, ignitionStableStatus);
        } else {
        	store.remove(Names.ignitionStableStatus);
        }
    }
    public IgnitionStableStatus getIgnitionStableStatus() {
        Object obj = store.get(Names.ignitionStableStatus);
        if (obj instanceof IgnitionStableStatus) {
            return (IgnitionStableStatus) obj;
        } else if (obj instanceof String) {
        	IgnitionStableStatus theCode = null;
            try {
                theCode = IgnitionStableStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.ignitionStableStatus, e);
            }
            return theCode;
        }
        return null;
    }
    public void setIgnitionStatus(IgnitionStatus ignitionStatus) {
        if (ignitionStatus != null) {
            store.put(Names.ignitionStatus, ignitionStatus);
        } else {
        	store.remove(Names.ignitionStatus);
        }
    }
    public IgnitionStatus getIgnitionStatus() {
        Object obj = store.get(Names.ignitionStatus);
        if (obj instanceof IgnitionStatus) {
            return (IgnitionStatus) obj;
        } else if (obj instanceof String) {
        	IgnitionStatus theCode = null;
            try {
                theCode = IgnitionStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.ignitionStatus, e);
            }
            return theCode;
        }
        return null;
    }
    
    public void setDriverDoorAjar(Boolean driverDoorAjar) {
        if (driverDoorAjar != null) {
        	store.put(Names.driverDoorAjar, driverDoorAjar);
        } else {
        	store.remove(Names.driverDoorAjar);
        }
    }    
    public Boolean getDriverDoorAjar() {
        return (Boolean) store.get(Names.driverDoorAjar);
    }
    
    
    public void setPassengerDoorAjar(Boolean passengerDoorAjar) {
        if (passengerDoorAjar != null) {
        	store.put(Names.passengerDoorAjar, passengerDoorAjar);
        } else {
        	store.remove(Names.passengerDoorAjar);
        }
    }    
    public Boolean getPassengerDoorAjar() {
        return (Boolean) store.get(Names.passengerDoorAjar);
    }
    
    public void setRearLeftDoorAjar(Boolean rearLeftDoorAjar) {
        if (rearLeftDoorAjar != null) {
        	store.put(Names.rearLeftDoorAjar, rearLeftDoorAjar);
        } else {
        	store.remove(Names.rearLeftDoorAjar);
        }
    }    
    public Boolean getRearLeftDoorAjar() {
        return (Boolean) store.get(Names.rearLeftDoorAjar);
    }

    public void setRearRightDoorAjar(Boolean rearRightDoorAjar) {
        if (rearRightDoorAjar != null) {
        	store.put(Names.rearRightDoorAjar, rearRightDoorAjar);
        } else {
        	store.remove(Names.rearRightDoorAjar);
        }
    }    
    public Boolean getRearRightDoorAjar() {
        return (Boolean) store.get(Names.rearRightDoorAjar);
    }     
    
}