package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Slider Response is sent, when Slider has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SliderResponse extends RPCResponse {
	public static final String sliderPosition = "sliderPosition";

	/**
	 * Constructs a new SliderResponse object
	 */
    public SliderResponse() {
        super("Slider");
    }

	/**
	 * Constructs a new SliderResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SliderResponse(Hashtable hash) {
        super(hash);
    }
    /**
     * Sets an Initial position of slider control
     * @param sliderPosition
     */
    public void setSliderPosition(Integer sliderPosition) {
    	if (sliderPosition != null) {
    		parameters.put(SliderResponse.sliderPosition, sliderPosition);
    	} else {
    		parameters.remove(SliderResponse.sliderPosition);
    	}
    }
    /**
     * Gets an Initial position of slider control
     * @return Integer
     */
    public Integer getSliderPosition() {
    	return (Integer) parameters.get(SliderResponse.sliderPosition);
    }
}
