package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.UpdateMode;
import com.smartdevicelink.util.JsonUtils;
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
	public static final String KEY_START_TIME = "startTime";
	public static final String KEY_END_TIME = "endTime";
	public static final String KEY_UPDATE_MODE = "updateMode";
	
	private StartTime startTime, endTime;
	private String updateMode; // represents UpdateMode enum
	
	/**
	 * Constructs a new SetMediaClockTimer object
	 */
    public SetMediaClockTimer() {
        super(FunctionID.SET_MEDIA_CLOCK_TIMER);
    }
    
    /**
     * Creates a SetMediaClockTimer object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SetMediaClockTimer(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.updateMode = JsonUtils.readStringFromJsonObject(jsonObject, KEY_UPDATE_MODE);

            JSONObject startTimeObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_START_TIME);
            if(startTimeObj != null){
                this.startTime = new StartTime(startTimeObj);
            }
            
            JSONObject endTimeObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_END_TIME);
            if(endTimeObj != null){
                this.startTime = new StartTime(endTimeObj);
            }
            break;
        }
    }

	/**
	 * Gets the Start Time which media clock timer is set
	 * 
	 * @return StartTime -a StartTime object specifying hour, minute, second
	 *         values
	 */
    public StartTime getStartTime() {
        return this.startTime;
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
        this.startTime = startTime;
    }
    
    public StartTime getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime( StartTime endTime ) {
        this.endTime = endTime;
    }
    
	/**
	 * Gets the media clock/timer update mode (COUNTUP/COUNTDOWN/PAUSE/RESUME)
	 * 
	 * @return UpdateMode -a Enumeration value (COUNTUP/COUNTDOWN/PAUSE/RESUME)
	 */    
    public UpdateMode getUpdateMode() {
        return UpdateMode.valueForJsonName(this.updateMode, sdlVersion);
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
        this.updateMode = updateMode.getJsonName(sdlVersion);
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_UPDATE_MODE, this.updateMode);
            JsonUtils.addToJsonObject(result, KEY_START_TIME, (this.startTime == null) ? null : 
                this.startTime.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_END_TIME, (this.endTime == null) ? null : 
                this.endTime.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }
}
