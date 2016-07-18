package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.WarningLightStatus;
import com.smartdevicelink.util.DebugTool;
/** <p>The status and pressure of the tires.</p>
 *   <p><b> Parameter List:</b></p>
 *   
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *          <th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>PressureTellTale</td>
 * 			<td>WarningLightStatus</td>
 * 			<td>Status of the Tire Pressure TellTale</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * <tr>
 * 			<td>LeftFront</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the left front tire.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * <tr>
 * 			<td>RightFront</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the right front tire.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * <tr>
 * 			<td>LeftRear</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the left rear tire.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>RightRear</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the right rear tire</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>InnerLeftRear</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the inner left rear tire.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>InnerRightRear</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the inner right rear tire.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 *  
 *  @since SmartDeviceLink 2.0
 *  
 * @see WarningLightStatus
 * @see SingleTireStatus
 * @see GetVehicleData 
 * @see OnVehicleData
 */

public class TireStatus extends RPCStruct {
	public static final String KEY_PRESSURE_TELL_TALE = "pressureTellTale";
	public static final String KEY_LEFT_FRONT = "leftFront";
	public static final String KEY_RIGHT_FRONT = "rightFront";
	public static final String KEY_LEFT_REAR = "leftRear";
	public static final String KEY_INNER_LEFT_REAR = "innerLeftRear";
	public static final String KEY_INNER_RIGHT_REAR = "innerRightRear";
	public static final String KEY_RIGHT_REAR = "rightRear";
	 /**
		 * <p>Constructs a new TireStatus object indicated by the Hashtable parameter</p>
		 * 
		 * 
		 * @param hash
		 * <p>
		 *            The Hashtable to use</p>
		 */

    public TireStatus() { }
    public TireStatus(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setPressureTellTale(WarningLightStatus pressureTellTale) {
    	if (pressureTellTale != null) {
    		store.put(KEY_PRESSURE_TELL_TALE, pressureTellTale);
    	} else {
    		store.remove(KEY_PRESSURE_TELL_TALE);
    	}
    }
    public WarningLightStatus getPressureTellTale() {
        Object obj = store.get(KEY_PRESSURE_TELL_TALE);
        if (obj instanceof WarningLightStatus) {
            return (WarningLightStatus) obj;
        } else if (obj instanceof String) {
        	return WarningLightStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setLeftFront(SingleTireStatus leftFront) {
    	if (leftFront != null) {
    		store.put(KEY_LEFT_FRONT, leftFront);
    	} else {
    		store.remove(KEY_LEFT_FRONT);
    	}
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getLeftFront() {
    	Object obj = store.get(KEY_LEFT_FRONT);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_LEFT_FRONT, e);
            }
        }
        return null;
    }
    public void setRightFront(SingleTireStatus rightFront) {
    	if (rightFront != null) {
    		store.put(KEY_RIGHT_FRONT, rightFront);
    	} else {
    		store.remove(KEY_RIGHT_FRONT);
    	}
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getRightFront() {
    	Object obj = store.get(KEY_RIGHT_FRONT);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_RIGHT_FRONT, e);
            }
        }
        return null;
    }
    public void setLeftRear(SingleTireStatus leftRear) {
    	if (leftRear != null) {
    		store.put(KEY_LEFT_REAR, leftRear);
    	} else {
    		store.remove(KEY_LEFT_REAR);
    	}
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getLeftRear() {
    	Object obj = store.get(KEY_LEFT_REAR);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_LEFT_REAR, e);
            }
        }
        return null;
    }
    public void setRightRear(SingleTireStatus rightRear) {
    	if (rightRear != null) {
    		store.put(KEY_RIGHT_REAR, rightRear);
    	} else {
    		store.remove(KEY_RIGHT_REAR);
    	}
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getRightRear() {
    	Object obj = store.get(KEY_RIGHT_REAR);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_RIGHT_REAR, e);
            }
        }
        return null;
    }
    public void setInnerLeftRear(SingleTireStatus innerLeftRear) {
    	if (innerLeftRear != null) {
    		store.put(KEY_INNER_LEFT_REAR, innerLeftRear);
    	} else {
    		store.remove(KEY_INNER_LEFT_REAR);
    	}
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getInnerLeftRear() {
    	Object obj = store.get(KEY_INNER_LEFT_REAR);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_INNER_LEFT_REAR, e);
            }
        }
        return null;
    }
    public void setInnerRightRear(SingleTireStatus innerRightRear) {
    	if (innerRightRear != null) {
    		store.put(KEY_INNER_RIGHT_REAR, innerRightRear);
    	} else {
    		store.remove(KEY_INNER_RIGHT_REAR);
    	}
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getInnerRightRear() {
    	Object obj = store.get(KEY_INNER_RIGHT_REAR);
        if (obj instanceof SingleTireStatus) {
            return (SingleTireStatus) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new SingleTireStatus((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_INNER_RIGHT_REAR, e);
            }
        }
        return null;
    }
}
