package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.util.DebugTool;

public class AirbagStatus extends RPCStruct {
    public static final String driverAirbagDeployed = "driverAirbagDeployed";
    public static final String driverSideAirbagDeployed = "driverSideAirbagDeployed";
    public static final String driverCurtainAirbagDeployed = "driverCurtainAirbagDeployed";
    public static final String passengerAirbagDeployed = "passengerAirbagDeployed";
    public static final String passengerCurtainAirbagDeployed = "passengerCurtainAirbagDeployed";
    public static final String driverKneeAirbagDeployed = "driverKneeAirbagDeployed";
    public static final String passengerSideAirbagDeployed = "passengerSideAirbagDeployed";
    public static final String passengerKneeAirbagDeployed = "passengerKneeAirbagDeployed";

    public AirbagStatus() { }
    public AirbagStatus(Hashtable hash) {
        super(hash);
    }

    public void setDriverAirbagDeployed(VehicleDataEventStatus driverAirbagDeployed) {
        if (driverAirbagDeployed != null) {
            store.put(AirbagStatus.driverAirbagDeployed, driverAirbagDeployed);
        } else {
            store.remove(AirbagStatus.driverAirbagDeployed);
        }
    }
    public VehicleDataEventStatus getDriverAirbagDeployed() {
        Object obj = store.get(AirbagStatus.driverAirbagDeployed);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + AirbagStatus.driverAirbagDeployed, e);
            }
            return theCode;
        }
        return null;
    }
    public void setDriverSideAirbagDeployed(VehicleDataEventStatus driverSideAirbagDeployed) {
        if (driverSideAirbagDeployed != null) {
            store.put(AirbagStatus.driverSideAirbagDeployed, driverSideAirbagDeployed);
        } else {
            store.remove(AirbagStatus.driverSideAirbagDeployed);
        }
    }
    public VehicleDataEventStatus getDriverSideAirbagDeployed() {
        Object obj = store.get(AirbagStatus.driverSideAirbagDeployed);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + AirbagStatus.driverSideAirbagDeployed, e);
            }
            return theCode;
        }
        return null;
    }
    public void setDriverCurtainAirbagDeployed(VehicleDataEventStatus driverCurtainAirbagDeployed) {
        if (driverCurtainAirbagDeployed != null) {
            store.put(AirbagStatus.driverCurtainAirbagDeployed, driverCurtainAirbagDeployed);
        } else {
            store.remove(AirbagStatus.driverCurtainAirbagDeployed);
        }
    }
    public VehicleDataEventStatus getDriverCurtainAirbagDeployed() {
        Object obj = store.get(AirbagStatus.driverCurtainAirbagDeployed);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + AirbagStatus.driverCurtainAirbagDeployed, e);
            }
            return theCode;
        }
        return null;
    }
    public void setPassengerAirbagDeployed(VehicleDataEventStatus passengerAirbagDeployed) {
        if (passengerAirbagDeployed != null) {
            store.put(AirbagStatus.passengerAirbagDeployed, passengerAirbagDeployed);
        } else {
            store.remove(AirbagStatus.passengerAirbagDeployed);
        }
    }
    public VehicleDataEventStatus getPassengerAirbagDeployed() {
        Object obj = store.get(AirbagStatus.passengerAirbagDeployed);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + AirbagStatus.passengerAirbagDeployed, e);
            }
            return theCode;
        }
        return null;
    }
    public void setPassengerCurtainAirbagDeployed(VehicleDataEventStatus passengerCurtainAirbagDeployed) {
        if (passengerCurtainAirbagDeployed != null) {
            store.put(AirbagStatus.passengerCurtainAirbagDeployed, passengerCurtainAirbagDeployed);
        } else {
            store.remove(AirbagStatus.passengerCurtainAirbagDeployed);
        }
    }
    public VehicleDataEventStatus getPassengerCurtainAirbagDeployed() {
        Object obj = store.get(AirbagStatus.passengerCurtainAirbagDeployed);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + AirbagStatus.passengerCurtainAirbagDeployed, e);
            }
            return theCode;
        }
        return null;
    }
    public void setDriverKneeAirbagDeployed(VehicleDataEventStatus driverKneeAirbagDeployed) {
        if (driverKneeAirbagDeployed != null) {
            store.put(AirbagStatus.driverKneeAirbagDeployed, driverKneeAirbagDeployed);
        } else {
            store.remove(AirbagStatus.driverKneeAirbagDeployed);
        }
    }
    public VehicleDataEventStatus getDriverKneeAirbagDeployed() {
        Object obj = store.get(AirbagStatus.driverKneeAirbagDeployed);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + AirbagStatus.driverKneeAirbagDeployed, e);
            }
            return theCode;
        }
        return null;
    }
    public void setPassengerSideAirbagDeployed(VehicleDataEventStatus passengerSideAirbagDeployed) {
        if (passengerSideAirbagDeployed != null) {
            store.put(AirbagStatus.passengerSideAirbagDeployed, passengerSideAirbagDeployed);
        } else {
            store.remove(AirbagStatus.passengerSideAirbagDeployed);
        }
    }
    public VehicleDataEventStatus getPassengerSideAirbagDeployed() {
        Object obj = store.get(AirbagStatus.passengerSideAirbagDeployed);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + AirbagStatus.passengerSideAirbagDeployed, e);
            }
            return theCode;
        }
        return null;
    }
    public void setPassengerKneeAirbagDeployed(VehicleDataEventStatus passengerKneeAirbagDeployed) {
        if (passengerKneeAirbagDeployed != null) {
            store.put(AirbagStatus.passengerKneeAirbagDeployed, passengerKneeAirbagDeployed);
        } else {
            store.remove(AirbagStatus.passengerKneeAirbagDeployed);
        }
    }
    public VehicleDataEventStatus getPassengerKneeAirbagDeployed() {
        Object obj = store.get(AirbagStatus.passengerKneeAirbagDeployed);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + AirbagStatus.passengerKneeAirbagDeployed, e);
            }
            return theCode;
        }
        return null;
    }
}
