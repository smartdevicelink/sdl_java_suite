package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.AppInterfaceUnregisteredReason;
import com.smartdevicelink.util.DebugTool;

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
 * <p>
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
 * </p>
 * @since SmartDeviceLink 1.0
 * @see RegisterAppInterface
 */
public class OnAppInterfaceUnregistered extends RPCNotification {
	public static final String reason = "reason";
	/**
	*Constructs a newly allocated OnAppInterfaceUnregistered object
	*/ 
    public OnAppInterfaceUnregistered() {
        super("OnAppInterfaceUnregistered");
    }
    /**
    *<p>Constructs a newly allocated OnAppInterfaceUnregistered object indicated by the Hashtable parameter</p>
    *@param hash The Hashtable to use
    */    
    public OnAppInterfaceUnregistered(Hashtable hash) {
        super(hash);
    }
    /**
     * <p>Get the reason the registration was terminated</p>
     * @return {@linkplain AppInterfaceUnregisteredReason} the reason the application's interface registration was terminated
     */    
    public AppInterfaceUnregisteredReason getReason() {
        Object obj = parameters.get(OnAppInterfaceUnregistered.reason);
        if (obj instanceof AppInterfaceUnregisteredReason) {
            return (AppInterfaceUnregisteredReason) obj;
        } else if (obj instanceof String) {
            AppInterfaceUnregisteredReason theCode = null;
            try {
                theCode = AppInterfaceUnregisteredReason.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnAppInterfaceUnregistered.reason, e);
            }
            return theCode;
        }
        return null;
    }
    /**
     * <p>Set the reason application's interface was terminated</p>
     * @param reason The reason application's interface registration was terminated
     */    
    public void setReason( AppInterfaceUnregisteredReason reason ) {
        if (reason != null) {
            parameters.put(OnAppInterfaceUnregistered.reason, reason );
        } else {
            parameters.remove(OnAppInterfaceUnregistered.reason);
        }
    }
}
