package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Creates a full screen or pop-up overlay (depending on platform) with a single
 * user controlled slider
 * <p>
 * Function Group: Base
 * <p>
 * <b>HMILevel needs to be FULL</b>
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 * 
 */
public class Slider extends RPCRequest {

	public static final String KEY_NUM_TICKS = "numTicks";
	public static final String KEY_SLIDER_HEADER = "sliderHeader";
	public static final String KEY_SLIDER_FOOTER = "sliderFooter";
	public static final String KEY_POSITION = "position";
	public static final String KEY_TIMEOUT = "timeout";
	/**
	 * Constructs a new Slider object
	 */
    public Slider() {
        super(FunctionID.SLIDER.toString());
    }

	/**
	 * Constructs a new Slider object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public Slider(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Sets a number of selectable items on a horizontal axis
	 * 
	 * @param numTicks
	 *            an Integer value representing a number of selectable items on
	 *            a horizontal axis
	 *            <p>
	 *            <b>Notes: </b>Minvalue=2; Maxvalue=26
	 */
    public void setNumTicks(Integer numTicks) {
    	if (numTicks != null) {
    		parameters.put(KEY_NUM_TICKS, numTicks);
    	} else {
    		parameters.remove(KEY_NUM_TICKS);
    	}
    }

	/**
	 * Gets a number of selectable items on a horizontal axis
	 * 
	 * @return Integer -an Integer value representing a number of selectable
	 *         items on a horizontal axis
	 */
    public Integer getNumTicks() {
    	return (Integer) parameters.get(KEY_NUM_TICKS);
    }

	/**
	 * Sets an Initial position of slider control
	 * 
	 * @param position
	 *            an Integer value representing an Initial position of slider
	 *            control
	 *            <p>
	 *            <b>Notes: </b>Minvalue=1; Maxvalue=26
	 */
    public void setPosition(Integer position) {
    	if (position != null) {
    		parameters.put(KEY_POSITION, position);
    	} else {
    		parameters.remove(KEY_POSITION);
    	}
    }

	/**
	 * Gets an Initial position of slider control
	 * 
	 * @return Integer -an Integer value representing an Initial position of
	 *         slider control
	 */
    public Integer getPosition() {
    	return (Integer) parameters.get(KEY_POSITION);
    }

	/**
	 * Sets a text header to display
	 * 
	 * @param sliderHeader
	 *            a String value
	 *            <p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public void setSliderHeader(String sliderHeader) {
    	if (sliderHeader != null) {
    		parameters.put(KEY_SLIDER_HEADER, sliderHeader);
    	} else {
    		parameters.remove(KEY_SLIDER_HEADER);
    	}
    }

	/**
	 * Gets a text header to display
	 * 
	 * @return String -a String value representing a text header to display
	 */
    public String getSliderHeader() {
    	return (String) parameters.get(KEY_SLIDER_HEADER);
    }

	/**
	 * Sets a text footer to display
	 * 
	 * @param sliderFooter
	 *            a List<String> value representing a text footer to display
	 *            <p>
	 *            <b>Notes: </b>Maxlength=500; Minvalue=1; Maxvalue=26
	 */
    public void setSliderFooter(List<String> sliderFooter) {
    	if (sliderFooter != null) {
    		parameters.put(KEY_SLIDER_FOOTER, sliderFooter);
    	} else {
    		parameters.remove(KEY_SLIDER_FOOTER);
    	}
    }

	/**
	 * Gets a text footer to display
	 * 
	 * @return String -a String value representing a text footer to display
	 */
    @SuppressWarnings("unchecked")
    public List<String> getSliderFooter() {
        if (parameters.get(KEY_SLIDER_FOOTER) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_SLIDER_FOOTER);
        	if (list != null && list.size()>0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
        			return (List<String>) list;
        		}
        	}
        }
    	return null;
    }

	/**
	 * Sets an App defined timeout
	 * 
	 * @param timeout
	 *            an Integer value representing an App defined timeout
	 *            <p>
	 *            <b>Notes: </b>Minvalue=0; Maxvalue=65535; Defvalue=10000
	 */
    public void setTimeout(Integer timeout) {
    	if (timeout != null) {
    		parameters.put(KEY_TIMEOUT, timeout);
    	} else {
    		parameters.remove(KEY_TIMEOUT);
    	}
    }

	/**
	 * Gets an App defined timeout
	 * @return Integer -an Integer value representing an App defined timeout
	 */
    public Integer getTimeout() {
    	return (Integer) parameters.get(KEY_TIMEOUT);
    }
}
