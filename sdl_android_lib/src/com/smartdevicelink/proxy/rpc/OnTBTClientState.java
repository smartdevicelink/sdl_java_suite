package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TBTState;

/**
 * <p>Notifies the application of the current TBT client status on the module.</p>
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
 * <td>{@linkplain TBTState}</td>
 * <td>Current state of TBT client.</td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table>
 * @since SmartDeviceLink 1.0
 * 
 */
public class OnTBTClientState extends RPCNotification {
	public static final String KEY_STATE = "state";
	/**
	*Constructs a newly allocated OnTBTClientState object
	*/ 
    public OnTBTClientState() {
        super(FunctionID.ON_TBT_CLIENT_STATE.toString());
    }
    /**
     *<p>Constructs a newly allocated OnTBTClientState object indicated by the Hashtable parameter</p>
     *@param hash The Hashtable to use
     */    
    public OnTBTClientState(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * <p>Called to get the current state of TBT client</p>
     * @return {@linkplain TBTState} the current state of TBT client
     */    
    public TBTState getState() {
        Object obj = parameters.get(KEY_STATE);
        if (obj instanceof TBTState) {
        	return (TBTState)obj;
        } else if(obj instanceof String) {
        	return TBTState.valueForString((String) obj);
        }    	
    	return null;
    }
    /**
     * <p>Called to set the current state of TBT client</p>
     * @param state current state of TBT client
     */    
    public void setState( TBTState state ) {
        if (state != null) {
            parameters.put(KEY_STATE, state );
        } else {
        	parameters.remove(KEY_STATE);
        }
    }
} // end-class
