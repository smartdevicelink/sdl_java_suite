package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
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
        super(FunctionID.SCROLLABLE_MESSAGE.toString());
    }

	/**
	 * Constructs a new ScrollableMessageResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ScrollableMessageResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}