package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * End Audio Pass Thru Response is sent, when EndAudioPassThru has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class EndAudioPassThruResponse extends RPCResponse {

	/**
	 * Constructs a new EndAudioPassThruResponse object
	 */
    public EndAudioPassThruResponse() {
        super(FunctionID.END_AUDIO_PASS_THRU.toString());
    }
    public EndAudioPassThruResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}