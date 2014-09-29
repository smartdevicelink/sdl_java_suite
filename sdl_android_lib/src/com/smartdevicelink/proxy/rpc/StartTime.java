package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;

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
public class StartTime extends RPCStruct {

	/**
	 * Constructs a newly allocated StartTime object
	 */
	public StartTime() { }
    /**
     * Constructs a newly allocated StartTime object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public StartTime(Hashtable hash) {
        super(hash);
    }
    /**
     * Get the hour. Minvalue="0", maxvalue="59"
 *					<p><b>Note:</b>Some display types only support a max value of 19. If out of range, it will be rejected.
     * @return hours Minvalue="0", maxvalue="59"
     */    
    public Integer getHours() {
        return (Integer) store.get( Names.hours );
    }
    /**
     * Set the hour. Minvalue="0", maxvalue="59"
 *					<p><b>Note:</b>Some display types only support a max value of 19. If out of range, it will be rejected.
     * @param hours min: 0; max: 59
     */    
    public void setHours( Integer hours ) {
        if (hours != null) {
            store.put(Names.hours, hours );
        }
    }
    /**
     * Get the minute. Minvalue="0", maxvalue="59".
     * @return minutes Minvalue="0", maxvalue="59"
     */    
    public Integer getMinutes() {
        return (Integer) store.get( Names.minutes );
    }
    /**
     * Set the minute. Minvalue="0", maxvalue="59".
     * @param minutes min: 0; max: 59
     */    
    public void setMinutes( Integer minutes ) {
        if (minutes != null) {
            store.put(Names.minutes, minutes );
        }
    }
    /**
     * Get the second. Minvalue="0", maxvalue="59".
     * @return seconds. Minvalue="0", maxvalue="59".
     */    
    public Integer getSeconds() {
        return (Integer) store.get( Names.seconds );
    }
    /**
     * Set the second. Minvalue="0", maxvalue="59".
     * @param seconds min: 0 max: 59
     */    
    public void setSeconds( Integer seconds ) {
        if (seconds != null) {
            store.put(Names.seconds, seconds );
        }
    }
}
