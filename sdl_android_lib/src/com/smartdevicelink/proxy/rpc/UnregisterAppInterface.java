package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Terminates an application's interface registration. This causes SDL&reg; to
 * dispose of all resources associated with the application's interface
 * registration (e.g. Command Menu items, Choice Sets, button subscriptions,
 * etc.)
 * <p>
 * After the UnregisterAppInterface operation is performed, no other operations
 * can be performed until a new app interface registration is established by
 * calling <i>{@linkplain RegisterAppInterface}</i>
 * <p>
 * <b>HMILevel can be FULL, LIMITED, BACKGROUND or NONE</b>
 * </p>
 * 
 * @see RegisterAppInterface
 * @see OnAppInterfaceUnregistered
 */
public class UnregisterAppInterface extends RPCRequest {
	/**
	 * Constructs a new UnregisterAppInterface object
	 */
    public UnregisterAppInterface() {
        super(FunctionID.UNREGISTER_APP_INTERFACE.toString());
    }
	/**
	 * Constructs a new UnregisterAppInterface object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
    public UnregisterAppInterface(Hashtable<String, Object> hash) {
        super(hash);
    }
}