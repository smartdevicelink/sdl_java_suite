package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;

/**
 * <p>Notifies the application of the current driver distraction state (whether driver distraction rules are in effect, or 
 * not).</p>
 *
 * <p></p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel: 
 * <ul><li>Can be sent with FULL, LIMITED or BACKGROUND</li></ul>
 * AudioStreamingState: 
 * <ul><li>Any</li></ul>
 * SystemContext: 
 * <ul><li>Any</li></ul>
 * </ul>
 * <p></p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>state</td>
 * <td>{@linkplain DriverDistractionState}</td>
 * <td>Current driver distraction state (i.e. whether driver distraction rules are in effect, or not). </td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table> 
 * @since SmartDeviceLink 1.0
 */
public class OnDriverDistraction  extends RPCNotification {
	public static final String KEY_STATE = "state";
	/**
	*Constructs a newly allocated OnDriverDistraction object
	*/ 
	public OnDriverDistraction() {
        super(FunctionID.ON_DRIVER_DISTRACTION.toString());
    }
	/**
     *<p>Constructs a newly allocated OnDriverDistraction object indicated by the Hashtable parameter</p>
     *@param hash The Hashtable to use
     */	
    public OnDriverDistraction(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * <p>Called to get the current driver distraction state(i.e. whether driver distraction rules are in effect, or not)</p>
     * @return {@linkplain DriverDistractionState} the Current driver distraction state.
     */    
    public DriverDistractionState getState() {
        Object obj = parameters.get(KEY_STATE);
        if (obj instanceof DriverDistractionState) {
        	return (DriverDistractionState)obj;
        } else if(obj instanceof String) {
        	return DriverDistractionState.valueForString((String) obj);
        }    	
    	return null;
    }
    /**
     * <p>Called to set the driver distraction state(i.e. whether driver distraction rules are in effect, or not)</p>
     * @param state the current driver distraction state
     */    
    public void setState( DriverDistractionState state ) {
        if (state != null) {
            parameters.put(KEY_STATE, state );
        } else {
            parameters.remove(KEY_STATE);
        }
    }  
}
