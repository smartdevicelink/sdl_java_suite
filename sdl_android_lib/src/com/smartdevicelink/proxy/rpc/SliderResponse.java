package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Slider Response is sent, when Slider has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SliderResponse extends RpcResponse {
	public static final String KEY_SLIDER_POSITION = "sliderPosition";

	/**
	 * Constructs a new SliderResponse object
	 */
    public SliderResponse() {
        super(FunctionId.SLIDER.toString());
    }

	/**
	 * Constructs a new SliderResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SliderResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Sets an Initial position of slider control
     * @param sliderPosition
     */
    public void setSliderPosition(Integer sliderPosition) {
    	if (sliderPosition != null) {
    		parameters.put(KEY_SLIDER_POSITION, sliderPosition);
    	} else {
    		parameters.remove(KEY_SLIDER_POSITION);
    	}
    }
    /**
     * Gets an Initial position of slider control
     * @return Integer
     */
    public Integer getSliderPosition() {
    	return (Integer) parameters.get(KEY_SLIDER_POSITION);
    }
}
