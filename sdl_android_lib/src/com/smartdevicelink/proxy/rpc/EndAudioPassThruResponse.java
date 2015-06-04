package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * End Audio Pass Thru Response is sent, when EndAudioPassThru has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class EndAudioPassThruResponse extends RpcResponse {

	/**
	 * Constructs a new EndAudioPassThruResponse object
	 */
    public EndAudioPassThruResponse() {
        super(FunctionId.END_AUDIO_PASS_THRU.toString());
    }
    public EndAudioPassThruResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}