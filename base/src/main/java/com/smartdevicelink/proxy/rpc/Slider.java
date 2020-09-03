/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;
import java.util.List;

/**
 * Creates a full screen or pop-up overlay (depending on platform) with a single user controlled slider.
 *
 * If connecting to SDL Core v.6.0+, the slider can be canceled programmatically using the `cancelID`. On older versions of SDL Core, the slider will persist until the user has interacted with the slider or the specified timeout has elapsed.
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
 * 		<tr>
 * 			<td>cancelID</td>
 * 			<td>Integer</td>
 * 			<td>An ID for this specific slider to allow cancellation through the `CancelInteraction` RPC.</td>
 *          <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 6.0</td>
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
	public static final String KEY_CANCEL_ID = "cancelID";

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
	 * Constructs a new Slider object \
	 * @param numTicks Number of selectable items on a horizontal axis.
	 * @param position Initial position of slider control (cannot exceed numTicks)
	 * @param sliderHeader Text header to display
	 */
	public Slider(@NonNull Integer numTicks, @NonNull Integer position, @NonNull String sliderHeader){
		this();
		setNumTicks(numTicks);
		setPosition(position);
		setSliderHeader(sliderHeader);
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
    public Slider setNumTicks(@NonNull Integer numTicks) {
        setParameters(KEY_NUM_TICKS, numTicks);
        return this;
    }

	/**
	 * Gets a number of selectable items on a horizontal axis
	 * 
	 * @return Integer -an Integer value representing a number of selectable
	 *         items on a horizontal axis
	 */
    public Integer getNumTicks() {
    	return getInteger(KEY_NUM_TICKS);
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
    public Slider setPosition(@NonNull Integer position) {
        setParameters(KEY_POSITION, position);
        return this;
    }

	/**
	 * Gets an Initial position of slider control
	 * 
	 * @return Integer -an Integer value representing an Initial position of
	 *         slider control
	 */
    public Integer getPosition() {
    	return getInteger(KEY_POSITION);
    }

	/**
	 * Sets a text header to display
	 *
	 * @param sliderHeader
	 *            a String value
	 *            <p></p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public Slider setSliderHeader(@NonNull String sliderHeader) {
        setParameters(KEY_SLIDER_HEADER, sliderHeader);
        return this;
    }

	/**
	 * Gets a text header to display
	 * 
	 * @return String -a String value representing a text header to display
	 */
    public String getSliderHeader() {
    	return getString(KEY_SLIDER_HEADER);
    }

	/**
	 * Sets a text footer to display
	 *
	 * @param sliderFooter
	 *            a List<String> value representing a text footer to display
	 *            <p></p>
	 *            <b>Notes: </b>Maxlength=500; Minvalue=1; Maxvalue=26
	 */
    public Slider setSliderFooter( List<String> sliderFooter) {
        setParameters(KEY_SLIDER_FOOTER, sliderFooter);
        return this;
    }

	/**
	 * Gets a text footer to display
	 * 
	 * @return String -a String value representing a text footer to display
	 */
    @SuppressWarnings("unchecked")
    public List<String> getSliderFooter() {
		return (List<String>) getObject(String.class, KEY_SLIDER_FOOTER);
    }

	/**
	 * Sets an App defined timeout
	 *
	 * @param timeout
	 *            an Integer value representing an App defined timeout
	 *            <p></p>
	 *            <b>Notes: </b>Minvalue=0; Maxvalue=65535; Defvalue=10000
	 */
    public Slider setTimeout( Integer timeout) {
        setParameters(KEY_TIMEOUT, timeout);
        return this;
    }

	/**
	 * Gets an App defined timeout
	 * @return Integer -an Integer value representing an App defined timeout
	 */
    public Integer getTimeout() {
    	return getInteger(KEY_TIMEOUT);
    }

	/**
	 * Gets an Integer value representing the cancel ID
	 *
	 * @return Integer - An Integer value representing the ID for this specific slider to allow cancellation through the `CancelInteraction` RPC.
	 *
	 * @since SmartDeviceLink 6.0
	 */
	public Integer getCancelID() {
		return getInteger(KEY_CANCEL_ID);
	}

	/**
	 * Sets the cancel ID
	 *
	 * @param cancelID An Integer ID for this specific slider to allow cancellation through the `CancelInteraction` RPC.
	 *
	 * @since SmartDeviceLink 6.0
	 */
	public Slider setCancelID( Integer cancelID) {
        setParameters(KEY_CANCEL_ID, cancelID);
        return this;
    }
}
