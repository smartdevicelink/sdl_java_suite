package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.util.DebugTool;

public class BeltStatus extends RPCStruct {
    public static final String driverBeltDeployed = "driverBeltDeployed";
    public static final String passengerBeltDeployed = "passengerBeltDeployed";
    public static final String passengerBuckleBelted = "passengerBuckleBelted";
    public static final String driverBuckleBelted = "driverBuckleBelted";
    public static final String leftRow2BuckleBelted = "leftRow2BuckleBelted";
    public static final String passengerChildDetected = "passengerChildDetected";
    public static final String rightRow2BuckleBelted = "rightRow2BuckleBelted";
    public static final String middleRow2BuckleBelted = "middleRow2BuckleBelted";
    public static final String middleRow3BuckleBelted = "middleRow3BuckleBelted";
    public static final String leftRow3BuckleBelted = "leftRow3BuckleBelted";
    public static final String rightRow3BuckleBelted = "rightRow3BuckleBelted";
    public static final String rearInflatableBelted = "rearInflatableBelted";
    public static final String rightRearInflatableBelted = "rightRearInflatableBelted";
    public static final String middleRow1BeltDeployed = "middleRow1BeltDeployed";
    public static final String middleRow1BuckleBelted = "middleRow1BuckleBelted";

    public BeltStatus() { }
    public BeltStatus(Hashtable hash) {
        super(hash);
    }

    public void setDriverBeltDeployed(VehicleDataEventStatus driverBeltDeployed) {
        if (driverBeltDeployed != null) {
            store.put(BeltStatus.driverBeltDeployed, driverBeltDeployed);
        } else {
        	store.remove(BeltStatus.driverBeltDeployed);
        }
    }
    public VehicleDataEventStatus getDriverBeltDeployed() {
        Object obj = store.get(BeltStatus.driverBeltDeployed);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.driverBeltDeployed, e);
            }
            return theCode;
        }
        return null;
    }
    public void setPassengerBeltDeployed(VehicleDataEventStatus passengerBeltDeployed) {
        if (passengerBeltDeployed != null) {
            store.put(BeltStatus.passengerBeltDeployed, passengerBeltDeployed);
        } else {
        	store.remove(BeltStatus.passengerBeltDeployed);
        }
    }
    public VehicleDataEventStatus getPassengerBeltDeployed() {
        Object obj = store.get(BeltStatus.passengerBeltDeployed);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.passengerBeltDeployed, e);
            }
            return theCode;
        }
        return null;
    }
    public void setPassengerBuckleBelted(VehicleDataEventStatus passengerBuckleBelted) {
        if (passengerBuckleBelted != null) {
            store.put(BeltStatus.passengerBuckleBelted, passengerBuckleBelted);
        } else {
        	store.remove(BeltStatus.passengerBuckleBelted);
        }
    }
    public VehicleDataEventStatus getPassengerBuckleBelted() {
        Object obj = store.get(BeltStatus.passengerBuckleBelted);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.passengerBuckleBelted, e);
            }
            return theCode;
        }
        return null;
    }
    public void setDriverBuckleBelted(VehicleDataEventStatus driverBuckleBelted) {
        if (driverBuckleBelted != null) {
            store.put(BeltStatus.driverBuckleBelted, driverBuckleBelted);
        } else {
        	store.remove(BeltStatus.driverBuckleBelted);
        }
    }
    public VehicleDataEventStatus getDriverBuckleBelted() {
        Object obj = store.get(BeltStatus.driverBuckleBelted);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.driverBuckleBelted, e);
            }
            return theCode;
        }
        return null;
    }
    public void setLeftRow2BuckleBelted(VehicleDataEventStatus leftRow2BuckleBelted) {
        if (leftRow2BuckleBelted != null) {
            store.put(BeltStatus.leftRow2BuckleBelted, leftRow2BuckleBelted);
        } else {
        	store.remove(BeltStatus.leftRow2BuckleBelted);
        }
    }
    public VehicleDataEventStatus getLeftRow2BuckleBelted() {
        Object obj = store.get(BeltStatus.leftRow2BuckleBelted);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.leftRow2BuckleBelted, e);
            }
            return theCode;
        }
        return null;
    }
    public void setPassengerChildDetected(VehicleDataEventStatus passengerChildDetected) {
        if (passengerChildDetected != null) {
            store.put(BeltStatus.passengerChildDetected, passengerChildDetected);
        } else {
        	store.remove(BeltStatus.passengerChildDetected);
        }
    }
    public VehicleDataEventStatus getPassengerChildDetected() {
        Object obj = store.get(BeltStatus.passengerChildDetected);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.passengerChildDetected, e);
            }
            return theCode;
        }
        return null;
    }
    public void setRightRow2BuckleBelted(VehicleDataEventStatus rightRow2BuckleBelted) {
        if (rightRow2BuckleBelted != null) {
            store.put(BeltStatus.rightRow2BuckleBelted, rightRow2BuckleBelted);
        } else {
        	store.remove(BeltStatus.rightRow2BuckleBelted);
        }
    }
    public VehicleDataEventStatus getRightRow2BuckleBelted() {
        Object obj = store.get(BeltStatus.rightRow2BuckleBelted);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.rightRow2BuckleBelted, e);
            }
            return theCode;
        }
        return null;
    }
    public void setMiddleRow2BuckleBelted(VehicleDataEventStatus middleRow2BuckleBelted) {
        if (middleRow2BuckleBelted != null) {
            store.put(BeltStatus.middleRow2BuckleBelted, middleRow2BuckleBelted);
        } else {
        	store.remove(BeltStatus.middleRow2BuckleBelted);
        }
    }
    public VehicleDataEventStatus getMiddleRow2BuckleBelted() {
        Object obj = store.get(BeltStatus.middleRow2BuckleBelted);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.middleRow2BuckleBelted, e);
            }
            return theCode;
        }
        return null;
    }
    public void setMiddleRow3BuckleBelted(VehicleDataEventStatus middleRow3BuckleBelted) {
        if (middleRow3BuckleBelted != null) {
            store.put(BeltStatus.middleRow3BuckleBelted, middleRow3BuckleBelted);
        } else {
        	store.remove(BeltStatus.middleRow3BuckleBelted);
        }
    }
    public VehicleDataEventStatus getMiddleRow3BuckleBelted() {
        Object obj = store.get(BeltStatus.middleRow3BuckleBelted);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.middleRow3BuckleBelted, e);
            }
            return theCode;
        }
        return null;
    }
    public void setLeftRow3BuckleBelted(VehicleDataEventStatus leftRow3BuckleBelted) {
        if (leftRow3BuckleBelted != null) {
            store.put(BeltStatus.leftRow3BuckleBelted, leftRow3BuckleBelted);
        } else {
        	store.remove(BeltStatus.leftRow3BuckleBelted);
        }
    }
    public VehicleDataEventStatus getLeftRow3BuckleBelted() {
        Object obj = store.get(BeltStatus.leftRow3BuckleBelted);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.leftRow3BuckleBelted, e);
            }
            return theCode;
        }
        return null;
    }
    public void setRightRow3BuckleBelted(VehicleDataEventStatus rightRow3BuckleBelted) {
        if (rightRow3BuckleBelted != null) {
            store.put(BeltStatus.rightRow3BuckleBelted, rightRow3BuckleBelted);
        } else {
        	store.remove(BeltStatus.rightRow3BuckleBelted);
        }
    }
    public VehicleDataEventStatus getRightRow3BuckleBelted() {
        Object obj = store.get(BeltStatus.rightRow3BuckleBelted);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.rightRow3BuckleBelted, e);
            }
            return theCode;
        }
        return null;
    }
    public void setLeftRearInflatableBelted(VehicleDataEventStatus rearInflatableBelted) {
        if (rearInflatableBelted != null) {
            store.put(BeltStatus.rearInflatableBelted, rearInflatableBelted);
        } else {
        	store.remove(BeltStatus.rearInflatableBelted);
        }
    }
    public VehicleDataEventStatus getLeftRearInflatableBelted() {
        Object obj = store.get(BeltStatus.rearInflatableBelted);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.rearInflatableBelted, e);
            }
            return theCode;
        }
        return null;
    }
    public void setRightRearInflatableBelted(VehicleDataEventStatus rightRearInflatableBelted) {
        if (rightRearInflatableBelted != null) {
            store.put(BeltStatus.rightRearInflatableBelted, rightRearInflatableBelted);
        } else {
        	store.remove(BeltStatus.rightRearInflatableBelted);
        }
    }
    public VehicleDataEventStatus getRightRearInflatableBelted() {
        Object obj = store.get(BeltStatus.rightRearInflatableBelted);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.rightRearInflatableBelted, e);
            }
            return theCode;
        }
        return null;
    }
    public void setMiddleRow1BeltDeployed(VehicleDataEventStatus middleRow1BeltDeployed) {
        if (middleRow1BeltDeployed != null) {
            store.put(BeltStatus.middleRow1BeltDeployed, middleRow1BeltDeployed);
        } else {
        	store.remove(BeltStatus.middleRow1BeltDeployed);
        }
    }
    public VehicleDataEventStatus getMiddleRow1BeltDeployed() {
        Object obj = store.get(BeltStatus.middleRow1BeltDeployed);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.middleRow1BeltDeployed, e);
            }
            return theCode;
        }
        return null;
    }
    public void setMiddleRow1BuckleBelted(VehicleDataEventStatus middleRow1BuckleBelted) {
        if (middleRow1BuckleBelted != null) {
            store.put(BeltStatus.middleRow1BuckleBelted, middleRow1BuckleBelted);
        } else {
        	store.remove(BeltStatus.middleRow1BuckleBelted);
        }
    }
    public VehicleDataEventStatus getMiddleRow1BuckleBelted() {
        Object obj = store.get(BeltStatus.middleRow1BuckleBelted);
        if (obj instanceof VehicleDataEventStatus) {
            return (VehicleDataEventStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataEventStatus theCode = null;
            try {
                theCode = VehicleDataEventStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + BeltStatus.middleRow1BuckleBelted, e);
            }
            return theCode;
        }
        return null;
    }
}
