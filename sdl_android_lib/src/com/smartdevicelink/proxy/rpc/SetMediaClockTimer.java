package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.UpdateMode;
import com.smartdevicelink.util.DebugTool;
/**
 * Sets the media clock/timer value and the update method (e.g.count-up,
 * count-down, etc.)
 * <p>
 * Function Group: Base <p>
 * <b>HMILevel needs to be FULL, LIMITIED or BACKGROUND</b>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 */
public class SetMediaClockTimer extends RPCRequest {
	/**
	 * Constructs a new SetMediaClockTimer object
	 */
    public SetMediaClockTimer() {
        super("SetMediaClockTimer");
    }
	/**
	 * Constructs a new SetMediaClockTimer object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
    public SetMediaClockTimer(Hashtable hash) {
        super(hash);
    }
	/**
	 * Gets the Start Time which media clock timer is set
	 * 
	 * @return StartTime -a StartTime object specifying hour, minute, second
	 *         values
	 */    
    public StartTime getStartTime() {
        Object obj = parameters.get(Names.startTime);
        if (obj instanceof StartTime) {
        	return (StartTime)obj;
        } else if (obj instanceof Hashtable) {
        	return new StartTime((Hashtable)obj);
        }
        return null;
    }
	/**
	 * Sets a Start Time with specifying hour, minute, second values
	 * 
	 * @param startTime
	 *            a startTime object with specifying hour, minute, second values
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>If "updateMode" is COUNTUP or COUNTDOWN, this parameter
	 *            must be provided</li>
	 *            <li>Will be ignored for PAUSE/RESUME and CLEAR</li>
	 *            </ul>
	 */    
    public void setStartTime( StartTime startTime ) {
        if (startTime != null) {
            parameters.put(Names.startTime, startTime );
        }
    }
    
    public StartTime getEndTime() {
        Object obj = parameters.get(Names.endTime);
        if (obj instanceof StartTime) {
        	return (StartTime)obj;
        } else if (obj instanceof Hashtable) {
        	return new StartTime((Hashtable)obj);
        }
        return null;
    }
    
    public void setEndTime( StartTime endTime ) {
        if (endTime != null) {
            parameters.put(Names.endTime, endTime );
        }
    }
    
	/**
	 * Gets the media clock/timer update mode (COUNTUP/COUNTDOWN/PAUSE/RESUME)
	 * 
	 * @return UpdateMode -a Enumeration value (COUNTUP/COUNTDOWN/PAUSE/RESUME)
	 */    
    public UpdateMode getUpdateMode() {
        Object obj = parameters.get(Names.updateMode);
        if (obj instanceof UpdateMode) {
            return (UpdateMode) obj;
        } else if (obj instanceof String) {
            UpdateMode theCode = null;
            try {
                theCode = UpdateMode.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.updateMode, e);
            }
            return theCode;
        }
        return null;
    }
	/**
	 * Sets the media clock/timer update mode (COUNTUP/COUNTDOWN/PAUSE/RESUME)
	 * 
	 * @param updateMode
	 *            a Enumeration value (COUNTUP/COUNTDOWN/PAUSE/RESUME)
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>When updateMode is PAUSE, RESUME or CLEAR, the start time value
	 *            is ignored</li>
	 *            <li>When updateMode is RESUME, the timer resumes counting from
	 *            the timer's value when it was paused</li>
	 *            </ul>
	 */    
    public void setUpdateMode( UpdateMode updateMode ) {
        if (updateMode != null) {
            parameters.put(Names.updateMode, updateMode );
        }
    }
}
