package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.constants.Names;

/**
 * Slider Response is sent, when Slider has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SliderResponse extends RPCResponse {

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
    		parameters.put(Names.sliderPosition, sliderPosition);
    	} else {
    		parameters.remove(Names.sliderPosition);
    	}
    }
    /**
     * Gets an Initial position of slider control
     * @return Integer
     */
    public Integer getSliderPosition() {
    	return (Integer) parameters.get(Names.sliderPosition);
    }
}