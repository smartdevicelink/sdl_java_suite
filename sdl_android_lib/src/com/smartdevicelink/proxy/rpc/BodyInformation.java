package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;
import com.smartdevicelink.util.DebugTool;

public class BodyInformation extends RPCStruct {
    public static final String parkBrakeActive = "parkBrakeActive";
    public static final String ignitionStableStatus = "ignitionStableStatus";
    public static final String ignitionStatus = "ignitionStatus";
    public static final String driverDoorAjar = "driverDoorAjar";
    public static final String passengerDoorAjar = "passengerDoorAjar";
    public static final String rearLeftDoorAjar = "rearLeftDoorAjar";
    public static final String rearRightDoorAjar = "rearRightDoorAjar";

    public BodyInformation() { }
    public BodyInformation(Hashtable hash) {
        super(hash);
    }

    public void setParkBrakeActive(Boolean parkBrakeActive) {
        if (parkBrakeActive != null) {
        	store.put(BodyInformation.parkBrakeActive, parkBrakeActive);
        } else {
        	store.remove(BodyInformation.parkBrakeActive);
        }
    }
    public Boolean getParkBrakeActive() {
        return (Boolean) store.get(BodyInformation.parkBrakeActive);
    }
    public void setIgnitionStableStatus(IgnitionStableStatus ignitionStableStatus) {
        if (ignitionStableStatus != null) {
            store.put(BodyInformation.ignitionStableStatus, ignitionStableStatus);
        } else {
        	store.remove(BodyInformation.ignitionStableStatus);
        }
    }
    public IgnitionStableStatus getIgnitionStableStatus() {
        Object obj = store.get(BodyInformation.ignitionStableStatus);
        if (obj instanceof IgnitionStableStatus) {
            return (IgnitionStableStatus) obj;
        } else if (obj instanceof String) {
        	IgnitionStableStatus theCode = null;
            try {
                theCode = IgnitionStableStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BodyInformation.ignitionStableStatus, e);
            }
            return theCode;
        }
        return null;
    }
    public void setIgnitionStatus(IgnitionStatus ignitionStatus) {
        if (ignitionStatus != null) {
            store.put(BodyInformation.ignitionStatus, ignitionStatus);
        } else {
        	store.remove(BodyInformation.ignitionStatus);
        }
    }
    public IgnitionStatus getIgnitionStatus() {
        Object obj = store.get(BodyInformation.ignitionStatus);
        if (obj instanceof IgnitionStatus) {
            return (IgnitionStatus) obj;
        } else if (obj instanceof String) {
        	IgnitionStatus theCode = null;
            try {
                theCode = IgnitionStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BodyInformation.ignitionStatus, e);
            }
            return theCode;
        }
        return null;
    }
    
    public void setDriverDoorAjar(Boolean driverDoorAjar) {
        if (driverDoorAjar != null) {
        	store.put(BodyInformation.driverDoorAjar, driverDoorAjar);
        } else {
        	store.remove(BodyInformation.driverDoorAjar);
        }
    }    
    public Boolean getDriverDoorAjar() {
        return (Boolean) store.get(BodyInformation.driverDoorAjar);
    }
    
    
    public void setPassengerDoorAjar(Boolean passengerDoorAjar) {
        if (passengerDoorAjar != null) {
        	store.put(BodyInformation.passengerDoorAjar, passengerDoorAjar);
        } else {
        	store.remove(BodyInformation.passengerDoorAjar);
        }
    }    
    public Boolean getPassengerDoorAjar() {
        return (Boolean) store.get(BodyInformation.passengerDoorAjar);
    }
    
    public void setRearLeftDoorAjar(Boolean rearLeftDoorAjar) {
        if (rearLeftDoorAjar != null) {
        	store.put(BodyInformation.rearLeftDoorAjar, rearLeftDoorAjar);
        } else {
        	store.remove(BodyInformation.rearLeftDoorAjar);
        }
    }    
    public Boolean getRearLeftDoorAjar() {
        return (Boolean) store.get(BodyInformation.rearLeftDoorAjar);
    }

    public void setRearRightDoorAjar(Boolean rearRightDoorAjar) {
        if (rearRightDoorAjar != null) {
        	store.put(BodyInformation.rearRightDoorAjar, rearRightDoorAjar);
        } else {
        	store.remove(BodyInformation.rearRightDoorAjar);
        }
    }    
    public Boolean getRearRightDoorAjar() {
        return (Boolean) store.get(BodyInformation.rearRightDoorAjar);
    }     
    
}
