package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

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
	/**
	 * Constructs a new ScrollableMessageResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public ScrollableMessageResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}
}