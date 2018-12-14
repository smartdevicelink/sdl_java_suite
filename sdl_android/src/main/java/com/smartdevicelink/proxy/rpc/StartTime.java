package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Describes the hour, minute and second values used to set the media clock.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>hours</td>
 * 			<td>Integer</td>
 * 			<td>The hour. Minvalue="0", maxvalue="59"
 *					<p><b>Note:</b></p>Some display types only support a max value of 19. If out of range, it will be rejected.
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
  * 		<tr>
 * 			<td>minutes</td>
 * 			<td>Integer</td>
 * 			<td>The minute. Minvalue="0", maxvalue="59".</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>seconds</td>
 * 			<td>Integer</td>
 * 			<td>The second. Minvalue="0", maxvalue="59".</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * </table>
 * @since SmartDeviceLink 1.0
 */
public class StartTime extends RPCStruct {
	public static final String KEY_MINUTES = "minutes";
	public static final String KEY_SECONDS = "seconds";
	public static final String KEY_HOURS = "hours";

	/**
	 * Constructs a newly allocated StartTime object
	 */
	public StartTime() { }
    /**
     * Constructs a newly allocated StartTime object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public StartTime(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a newly allocated StartTime object
	 * @param hours The hour
	 * @param minutes The minute
	 * @param seconds The second
	 */
	public StartTime(@NonNull Integer hours, @NonNull Integer minutes, @NonNull Integer seconds){
		this();
		setHours(hours);
		setMinutes(minutes);
		setSeconds(seconds);
	}
    /**
     * Get the hour. Minvalue="0", maxvalue="59"
 *					<p><b>Note:</b></p>Some display types only support a max value of 19. If out of range, it will be rejected.
     * @return hours Minvalue="0", maxvalue="59"
     */    
    public Integer getHours() {
        return getInteger( KEY_HOURS );
    }
    /**
     * Set the hour. Minvalue="0", maxvalue="59"
 *					<p><b>Note:</b></p>Some display types only support a max value of 19. If out of range, it will be rejected.
     * @param hours min: 0; max: 59
     */    
    public void setHours(@NonNull Integer hours ) {
        setValue(KEY_HOURS, hours);
    }
    /**
     * Get the minute. Minvalue="0", maxvalue="59".
     * @return minutes Minvalue="0", maxvalue="59"
     */    
    public Integer getMinutes() {
        return getInteger( KEY_MINUTES );
    }
    /**
     * Set the minute. Minvalue="0", maxvalue="59".
     * @param minutes min: 0; max: 59
     */    
    public void setMinutes( @NonNull Integer minutes ) {
        setValue(KEY_MINUTES, minutes);
    }
    /**
     * Get the second. Minvalue="0", maxvalue="59".
     * @return seconds. Minvalue="0", maxvalue="59".
     */    
    public Integer getSeconds() {
        return getInteger( KEY_SECONDS );
    }
    /**
     * Set the second. Minvalue="0", maxvalue="59".
     * @param seconds min: 0 max: 59
     */    
    public void setSeconds( @NonNull Integer seconds ) {
        setValue(KEY_SECONDS, seconds);
    }
}
