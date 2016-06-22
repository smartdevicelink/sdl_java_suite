package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * <p>Creates a full screen or pop-up overlay (depending on platform) with a single
 * user controlled slider.</p>
 *
 * <p>Function Group: Base</p>
 * 
 * <p><b>HMILevel needs to be FULL</b></p>
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>numTicks</td>
 * 			<td>Integer</td>
 * 			<td>Number of selectable items on a horizontal axis.</td>
 *                 <td>Y</td>
 * 			<td>Minvalue=2; Maxvalue=26</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 * 		<tr>
 * 			<td>position</td>
 * 			<td>Integer</td>
 * 			<td>Initial position of slider control (cannot exceed numTicks),</td>
 *                 <td>Y</td>
 * 			<td>Minvalue=1; Maxvalue=26</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>sliderHeader</td>
 * 			<td>String</td>
 * 			<td>Text header to display</td>
 *                 <td>N</td>
 * 			<td>Maxlength=500</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>sliderFooter</td>
 * 			<td>Integer</td>
 * 			<td><p>Text footer to display (meant to display min/max threshold descriptors).</p>For a static text footer, only one footer string shall be provided in the array. For a dynamic text footer, the number of footer text string in the array must match the numTicks value.For a dynamic text footer, text array string should correlate with potential slider position index.If omitted on supported displays, no footer text shall be displayed.</td>
 *                 <td>N</td>
 * 			<td>Maxlength=500; Minvalue=1; Maxvalue=26</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 * 		<tr>
 * 			<td>timeout</td>
 * 			<td>String</td>
 * 			<td>App defined timeout.  Indicates how long of a timeout from the last action (i.e. sliding control resets timeout). If omitted, the value is set to 10000.</td>
 *                 <td>N</td>
 * 			<td>Minvalue=0; Maxvalue=65535; Defvalue= 10000</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
*<p><b>Response </b></p>
*
*<p><b>Non-default Result Codes:</b></p>
*	<p> SAVED </p>
*	<p> INVALID_DATA</p>
*	<p>OUT_OF_MEMORY</p>
*	<p>TOO_MANY_PENDING_REQUESTS</p>
*	<p>APPLICATION_NOT_REGISTERED</p>
*	<p>GENERIC_ERROR</p>
*<p>	DISALLOWED</p>
*<p>	UNSUPPORTED_RESOURCE </p>    
*<p>	 REJECTED   </p>
*	<p>ABORTED </p>
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
	 * <p></p>
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
	 *            <p></p>
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
	 *            <p></p>
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
	 *            <p></p>
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
	 *            <p></p>
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
	 *            <p></p>
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
