package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.AppInterfaceUnregisteredReason;

/**
 * <p>Notifies an application that its interface registration has been terminated. This means that all SDL resources 
 * associated with the application are discarded, including the Command Menu, Choice Sets, button subscriptions, etc.</p>
 * For more information about SDL resources related to an interface registration, see {@linkplain RegisterAppInterface}.
 * <p></p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel: 
 * <ul><li>Any</li></ul>
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
 * <td>reason</td>
 * <td>{@linkplain AppInterfaceUnregisteredReason}</td>
 * <td>The reason the application's interface registration was terminated</td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table>
 * @since SmartDeviceLink 1.0
 * @see RegisterAppInterface
 */
public class OnAppInterfaceUnregistered extends RPCNotification {
	public static final String KEY_REASON = "reason";
	/**
	*Constructs a newly allocated OnAppInterfaceUnregistered object
	*/ 
    public OnAppInterfaceUnregistered() {
        super(FunctionID.ON_APP_INTERFACE_UNREGISTERED.toString());
    }
    /**
    *<p>Constructs a newly allocated OnAppInterfaceUnregistered object indicated by the Hashtable parameter</p>
    *@param hash The Hashtable to use
    */    
    public OnAppInterfaceUnregistered(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * <p>Get the reason the registration was terminated</p>
     * @return {@linkplain AppInterfaceUnregisteredReason} the reason the application's interface registration was terminated
     */    
    public AppInterfaceUnregisteredReason getReason() {
        Object obj = parameters.get(KEY_REASON);
        if (obj instanceof AppInterfaceUnregisteredReason) {
            return (AppInterfaceUnregisteredReason) obj;
        } else if (obj instanceof String) {
            return AppInterfaceUnregisteredReason.valueForString((String) obj);
        }
        return null;
    }
    /**
     * <p>Set the reason application's interface was terminated</p>
     * @param reason The reason application's interface registration was terminated
     */    
    public void setReason( AppInterfaceUnregisteredReason reason ) {
        if (reason != null) {
            parameters.put(KEY_REASON, reason );
        } else {
            parameters.remove(KEY_REASON);
        }
    }
}
