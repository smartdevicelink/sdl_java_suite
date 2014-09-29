package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;

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

	/**
	 * Constructs a new Slider object
	 */
    public Slider() {
        super("Slider");
    }

	/**
	 * Constructs a new Slider object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public Slider(Hashtable hash) {
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
    		parameters.put(Names.numTicks, numTicks);
    	} else {
    		parameters.remove(Names.numTicks);
    	}
    }

	/**
	 * Gets a number of selectable items on a horizontal axis
	 * 
	 * @return Integer -an Integer value representing a number of selectable
	 *         items on a horizontal axis
	 */
    public Integer getNumTicks() {
    	return (Integer) parameters.get(Names.numTicks);
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
    		parameters.put(Names.position, position);
    	} else {
    		parameters.remove(Names.position);
    	}
    }

	/**
	 * Gets an Initial position of slider control
	 * 
	 * @return Integer -an Integer value representing an Initial position of
	 *         slider control
	 */
    public Integer getPosition() {
    	return (Integer) parameters.get(Names.position);
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
    		parameters.put(Names.sliderHeader, sliderHeader);
    	} else {
    		parameters.remove(Names.sliderHeader);
    	}
    }

	/**
	 * Gets a text header to display
	 * 
	 * @return String -a String value representing a text header to display
	 */
    public String getSliderHeader() {
    	return (String) parameters.get(Names.sliderHeader);
    }

	/**
	 * Sets a text footer to display
	 * 
	 * @param sliderFooter
	 *            a Vector<String> value representing a text footer to display
	 *            <p>
	 *            <b>Notes: </b>Maxlength=500; Minvalue=1; Maxvalue=26
	 */
    public void setSliderFooter(Vector<String> sliderFooter) {
    	if (sliderFooter != null) {
    		parameters.put(Names.sliderFooter, sliderFooter);
    	} else {
    		parameters.remove(Names.sliderFooter);
    	}
    }

	/**
	 * Gets a text footer to display
	 * 
	 * @return String -a String value representing a text footer to display
	 */
    public Vector<String> getSliderFooter() {
        if (parameters.get(Names.sliderFooter) instanceof Vector<?>) {
        	Vector<?> list = (Vector<?>)parameters.get(Names.sliderFooter);
        	if (list != null && list.size()>0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
        			return (Vector<String>) list;
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
    		parameters.put(Names.timeout, timeout);
    	} else {
    		parameters.remove(Names.timeout);
    	}
    }

	/**
	 * Gets an App defined timeout
	 * @return Integer -an Integer value representing an App defined timeout
	 */
    public Integer getTimeout() {
    	return (Integer) parameters.get(Names.timeout);
    }
}
