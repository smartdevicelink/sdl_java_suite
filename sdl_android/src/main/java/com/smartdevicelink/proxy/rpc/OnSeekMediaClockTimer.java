package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.Hashtable;

/**
 * Callback for the seek media clock timer notification. Notifies the application of
 * progress bar seek event on the media clock timer. System will automatically update
 * the media clock timer position based on the seek notification location.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>Req</th>
 * 			<th>Notes</th>
 * 			<th>SmartDeviceLink Ver Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>seekTime</td>
 * 			<td>StartTime</td>
 * 			<td>Describes the hour, minute and second values used for the current media clock timer.</td>
 * 			<td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 4.7</td>
 * 		</tr>
 * </table>
 */

public class OnSeekMediaClockTimer extends RPCNotification {
	public static final String KEY_SEEK_TIME = "seekTime";

	/**
	*Constructs a newly allocated OnSeekMediaClockTimer object
	*/
	public OnSeekMediaClockTimer() {
		super(FunctionID.ON_SEEK_MEDIA_CLOCK_TIMER.toString());
	}
	/**
     *<p>Constructs a newly allocated OnSeekMediaClockTimer object indicated by the Hashtable parameter</p>
     *@param hash The Hashtable to use
     */
	public OnSeekMediaClockTimer(Hashtable<String, Object> hash) {
		super(hash);
	}
	/**
	 * Gets the StartTime object representing the current media clock timer
	 *
	 * @return seekTime -a StartTime object specifying hour, minute, second values
	 */
	public StartTime getSeekTime() {
		return (StartTime) getObject(StartTime.class, KEY_SEEK_TIME);
	}
	/**
	 * Sets a seekTime with specifying hour, minute, second values
	 *
	 * @param seekTime -a StartTime object with specifying hour, minute, second values
	 */
	public void setSeekTime( StartTime seekTime ) {
		setParameters(KEY_SEEK_TIME, seekTime);
	}

}
