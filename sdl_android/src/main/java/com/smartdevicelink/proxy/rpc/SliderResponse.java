package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

/**
 * Slider Response is sent, when Slider has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SliderResponse extends RPCResponse {
	public static final String KEY_SLIDER_POSITION = "sliderPosition";

	/**
	 * Constructs a new SliderResponse object
	 */
    public SliderResponse() {
        super(FunctionID.SLIDER.toString());
    }

	/**
	 * Constructs a new SliderResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SliderResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new SliderResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public SliderResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}

    /**
     * Sets an Initial position of slider control
     * @param sliderPosition
     */
    public void setSliderPosition(Integer sliderPosition) {
		setParameters(KEY_SLIDER_POSITION, sliderPosition);
    }
    /**
     * Gets an Initial position of slider control
     * @return Integer
     */
    public Integer getSliderPosition() {
    	return getInteger(KEY_SLIDER_POSITION);
    }
}
