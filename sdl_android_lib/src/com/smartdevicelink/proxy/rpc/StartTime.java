package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

/**
 * Describes the hour, minute and second values used to set the media clock.
 * <p><b> Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>hours</td>
 * 			<td>Int16</td>
 * 			<td>The hour. Minvalue="0", maxvalue="59"
 *					<p><b>Note:</b>Some display types only support a max value of 19. If out of range, it will be rejected.
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
  * 		<tr>
 * 			<td>minutes</td>
 * 			<td>Int16</td>
 * 			<td>The minute. Minvalue="0", maxvalue="59".</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>seconds</td>
 * 			<td>Int16</td>
 * 			<td>The second. Minvalue="0", maxvalue="59".</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * </table>
 * @since SmartDeviceLink 1.0
 */
public class StartTime extends RPCObject {
	public static final String KEY_MINUTES = "minutes";
	public static final String KEY_SECONDS = "seconds";
	public static final String KEY_HOURS = "hours";

	private Integer hours, minutes, seconds;
	
	/**
	 * Constructs a newly allocated StartTime object
	 */
	public StartTime() { }
	
    /**
     * Creates a StartTime object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public StartTime(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.hours = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_HOURS);
            this.minutes = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_MINUTES);
            this.seconds = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_SECONDS);
            break;
        }
    }
    
    /**
     * Get the hour. Minvalue="0", maxvalue="59"
 *					<p><b>Note:</b>Some display types only support a max value of 19. If out of range, it will be rejected.
     * @return hours Minvalue="0", maxvalue="59"
     */    
    public Integer getHours() {
        return this.hours;
    }
    
    /**
     * Set the hour. Minvalue="0", maxvalue="59"
 *					<p><b>Note:</b>Some display types only support a max value of 19. If out of range, it will be rejected.
     * @param hours min: 0; max: 59
     */    
    public void setHours( Integer hours ) {
        this.hours = hours;
    }
    
    /**
     * Get the minute. Minvalue="0", maxvalue="59".
     * @return minutes Minvalue="0", maxvalue="59"
     */    
    public Integer getMinutes() {
        return this.minutes;
    }
    
    /**
     * Set the minute. Minvalue="0", maxvalue="59".
     * @param minutes min: 0; max: 59
     */    
    public void setMinutes( Integer minutes ) {
        this.minutes = minutes;
    }
    
    /**
     * Get the second. Minvalue="0", maxvalue="59".
     * @return seconds. Minvalue="0", maxvalue="59".
     */    
    public Integer getSeconds() {
        return this.seconds;
    }
    
    /**
     * Set the second. Minvalue="0", maxvalue="59".
     * @param seconds min: 0 max: 59
     */    
    public void setSeconds( Integer seconds ) {
        this.seconds = seconds;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_HOURS, this.hours);
            JsonUtils.addToJsonObject(result, KEY_MINUTES, this.minutes);
            JsonUtils.addToJsonObject(result, KEY_SECONDS, this.seconds);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hours == null) ? 0 : hours.hashCode());
		result = prime * result + ((minutes == null) ? 0 : minutes.hashCode());
		result = prime * result + ((seconds == null) ? 0 : seconds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		StartTime other = (StartTime) obj;
		if (hours == null) {
			if (other.hours != null) { 
				return false;
			}
		} 
		else if (!hours.equals(other.hours)) { 
			return false;
		}
		if (minutes == null) {
			if (other.minutes != null) { 
				return false;
			}
		} 
		else if (!minutes.equals(other.minutes)) { 
			return false;
		}
		if (seconds == null) {
			if (other.seconds != null) { 
				return false;
			}
		} 
		else if (!seconds.equals(other.seconds)) { 
			return false;
		}
		return true;
	}
}
