package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.WarningLightStatus;
import com.smartdevicelink.util.DebugTool;

public class TireStatus extends RPCStruct {
	public static final String pressureTellTale = "pressureTellTale";
	public static final String leftFront = "leftFront";
	public static final String rightFront = "rightFront";
	public static final String leftRear = "leftRear";
	public static final String innerLeftRear = "innerLeftRear";
	public static final String innerRightRear = "innerRightRear";
	public static final String rightRear = "rightRear";

    public TireStatus() { }
    public TireStatus(Hashtable hash) {
        super(hash);
    }
    public void setPressureTellTale(WarningLightStatus pressureTellTale) {
    	if (pressureTellTale != null) {
    		store.put(TireStatus.pressureTellTale, pressureTellTale);
    	} else {
    		store.remove(TireStatus.pressureTellTale);
    	}
    }
    public WarningLightStatus getPressureTellTale() {
        Object obj = store.get(TireStatus.pressureTellTale);
        if (obj instanceof WarningLightStatus) {
            return (WarningLightStatus) obj;
        } else if (obj instanceof String) {
        	WarningLightStatus theCode = null;
            try {
                theCode = WarningLightStatus.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + TireStatus.pressureTellTale, e);
            }
            return theCode;
        }
        return null;
    }
    public void setLeftFront(SingleTireStatus leftFront) {
    	if (leftFront != null) {
    		store.put(TireStatus.leftFront, leftFront);
    	} else {
    		store.remove(TireStatus.leftFront);
    	}
    }
    public SingleTireStatus getLeftFront() {
    	Object obj = store.get(TireStatus.leftFront);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + TireStatus.leftFront, e);
            }
        }
        return null;
    }
    public void setRightFront(SingleTireStatus rightFront) {
    	if (rightFront != null) {
    		store.put(TireStatus.rightFront, rightFront);
    	} else {
    		store.remove(TireStatus.rightFront);
    	}
    }
    public SingleTireStatus getRightFront() {
    	Object obj = store.get(TireStatus.rightFront);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + TireStatus.rightFront, e);
            }
        }
        return null;
    }
    public void setLeftRear(SingleTireStatus leftRear) {
    	if (leftRear != null) {
    		store.put(TireStatus.leftRear, leftRear);
    	} else {
    		store.remove(TireStatus.leftRear);
    	}
    }
    public SingleTireStatus getLeftRear() {
    	Object obj = store.get(TireStatus.leftRear);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + TireStatus.leftRear, e);
            }
        }
        return null;
    }
    public void setRightRear(SingleTireStatus rightRear) {
    	if (rightRear != null) {
    		store.put(TireStatus.rightRear, rightRear);
    	} else {
    		store.remove(TireStatus.rightRear);
    	}
    }
    public SingleTireStatus getRightRear() {
    	Object obj = store.get(TireStatus.rightRear);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + TireStatus.rightRear, e);
            }
        }
        return null;
    }
    public void setInnerLeftRear(SingleTireStatus innerLeftRear) {
    	if (innerLeftRear != null) {
    		store.put(TireStatus.innerLeftRear, innerLeftRear);
    	} else {
    		store.remove(TireStatus.innerLeftRear);
    	}
    }
    public SingleTireStatus getInnerLeftRear() {
    	Object obj = store.get(TireStatus.innerLeftRear);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + TireStatus.innerLeftRear, e);
            }
        }
        return null;
    }
    public void setInnerRightRear(SingleTireStatus innerRightRear) {
    	if (innerRightRear != null) {
    		store.put(TireStatus.innerRightRear, innerRightRear);
    	} else {
    		store.remove(TireStatus.innerRightRear);
    	}
    }
    public SingleTireStatus getInnerRightRear() {
    	Object obj = store.get(TireStatus.innerRightRear);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + TireStatus.innerRightRear, e);
            }
        }
        return null;
    }
}
