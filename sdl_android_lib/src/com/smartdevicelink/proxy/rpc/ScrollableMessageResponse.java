package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Scrollable Message Response is sent, when ScrollableMessage has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ScrollableMessageResponse extends RPCResponse {

	/**
	 * Constructs a new ScrollableMessageResponse object
	 */
    public ScrollableMessageResponse() {
        super("ScrollableMessage");
    }

	/**
	 * Constructs a new ScrollableMessageResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ScrollableMessageResponse(Hashtable hash) {
        super(hash);
    }
}