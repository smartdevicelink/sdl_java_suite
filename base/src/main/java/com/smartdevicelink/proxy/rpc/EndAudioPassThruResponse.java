package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

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
    /**
     * Constructs a new EndAudioPassThruResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public EndAudioPassThruResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}