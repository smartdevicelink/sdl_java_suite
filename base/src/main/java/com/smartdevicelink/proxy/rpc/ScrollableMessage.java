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
 * Creates a full screen overlay containing a large block of formatted text that
 * can be scrolled with up to 8 SoftButtons defined
 *
 * If connecting to SDL Core v.6.0+, the scrollable message can be canceled programmatically using the `cancelID`. On older versions of SDL Core, the scrollable message will persist until the user has interacted with the scrollable message or the specified timeout has elapsed.
 * 
 * <p>Function Group: ScrollableMessage</p>
 * 
 * <p><b>HMILevel needs to be FULL</b></p>
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>scrollableMessageBody</td>
 * 			<td>String</td>
 * 			<td>Body of text that can include newlines and tabs.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDevice Link 1.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>timeout</td>
 * 			<td>Integer</td>
 * 			<td>App defined timeout.  Indicates how long of a timeout from the last action (i.e. scrolling message resets timeout).</td>
 *                 <td>N</td>
 *                 <td>minValue=1000; maxValue=65535; defValue=30000</td>
 * 			<td>SmartDevice Link 1.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>softButtons</td>
 * 			<td>SoftButton</td>
 * 			<td>App defined SoftButtons. If omitted on supported displays, only the system defined "Close" SoftButton will be displayed.</td>
 *                 <td>N</td>
 *                 <td>minsize=0; maxsize=8</td>
 * 			<td>SmartDevice Link 1.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>cancelID</td>
 * 			<td>Integer</td>
 * 			<td>An ID for this specific ScrollableMessage to allow cancellation through the `CancelInteraction` RPC.</td>
 *          <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 6.0</td>
 * 		</tr>
 *  </table>
 *  <p> <b>Response</b></p>
 *<b>Non-default Result Codes:</b>
 *	<p>SUCCESS</p>
 *	<p>INVALID_DATA </p>
 *	<p>OUT_OF_MEMORY</p>
 *	<p>CHAR_LIMIT_EXCEEDED</p>
 *	<p>TOO_MANY_PENDING_REQUESTS</p>
 *	<p>APPLICATION_NOT_REGISTERED</p>
 *	<p>GENERIC_ERROR </p>
 *	<p>DISALLOWED</p>
 *	<p>UNSUPPORTED_RESOURCE</p>          
 *	<p>REJECTED </p>
 *	<p>ABORTED</p>
 *
 *  @see com.smartdevicelink.proxy.rpc.SoftButton
 */
public class ScrollableMessage extends RPCRequest {
	public static final String KEY_SCROLLABLE_MESSAGE_BODY = "scrollableMessageBody";
	public static final String KEY_TIMEOUT = "timeout";
	public static final String KEY_SOFT_BUTTONS = "softButtons";
	public static final String KEY_CANCEL_ID = "cancelID";

	/**
	 * Constructs a new ScrollableMessage object
	 */
    public ScrollableMessage() {
        super(FunctionID.SCROLLABLE_MESSAGE.toString());
    }

	/**
	 * Constructs a new ScrollableMessage object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash The Hashtable to use
	 */
    public ScrollableMessage(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new ScrollableMessage object
	 * @param scrollableMessageBody a String value representing the Body of text that can include newlines and tabs <br>
	 * <b>Notes: </b>Maxlength=500
	 */
	public ScrollableMessage(@NonNull String scrollableMessageBody) {
		this();
		setScrollableMessageBody(scrollableMessageBody);
	}

	/**
	 * Sets a Body of text that can include newlines and tabs
	 *
	 * @param scrollableMessageBody
	 *            a String value representing the Body of text that can include
	 *            newlines and tabs
	 *            <p></p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public ScrollableMessage setScrollableMessageBody(@NonNull String scrollableMessageBody) {
        setParameters(KEY_SCROLLABLE_MESSAGE_BODY, scrollableMessageBody);
        return this;
    }

	/**
	 * Gets a Body of text that can include newlines and tabs
	 * 
	 * @return String -a String value
	 */
    public String getScrollableMessageBody() {
        return getString(KEY_SCROLLABLE_MESSAGE_BODY);
    }

	/**
	 * Sets an App defined timeout. Indicates how long of a timeout from the
	 * last action
	 *
	 * @param timeout
	 *            an Integer value representing an App defined timeout
	 *            <p></p>
	 *            <b>Notes</b>:Minval=0; Maxval=65535;Default=30000
	 */
    public ScrollableMessage setTimeout( Integer timeout) {
        setParameters(KEY_TIMEOUT, timeout);
        return this;
    }

	/**
	 * Gets an App defined timeout
	 * 
	 * @return Integer -an Integer value representing an App defined timeout
	 */
    public Integer getTimeout() {
        return getInteger(KEY_TIMEOUT);
    }

	/**
	 * Sets App defined SoftButtons.If omitted on supported displays, only the
	 * system defined "Close" SoftButton will be displayed
	 *
	 * @param softButtons
	 *            a List<SoftButton> value representing App defined
	 *            SoftButtons
	 *            <p></p>
	 *            <b>Notes: </b>Minsize=0, Maxsize=8
	 */
    public ScrollableMessage setSoftButtons( List<SoftButton> softButtons) {
        setParameters(KEY_SOFT_BUTTONS, softButtons);
        return this;
    }

	/**
	 * Gets App defined soft button
	 * @return List -List<SoftButton> value
	 */
    @SuppressWarnings("unchecked")
    public List<SoftButton> getSoftButtons() {
		return (List<SoftButton>) getObject(SoftButton.class, KEY_SOFT_BUTTONS);
    }

	/**
	 * Gets an Integer value representing the cancel ID
	 *
	 * @return Integer - An Integer value representing the ID for this specific scrollable message to allow cancellation through the `CancelInteraction` RPC.
	 *
	 * @since SmartDeviceLink 6.0
	 */
	public Integer getCancelID() {
		return getInteger(KEY_CANCEL_ID);
	}

	/**
	 * Sets the cancel ID
	 *
	 * @param cancelID An Integer ID for this specific scrollable message to allow cancellation through the `CancelInteraction` RPC.
	 *
	 * @since SmartDeviceLink 6.0
	 */
	public ScrollableMessage setCancelID( Integer cancelID) {
        setParameters(KEY_CANCEL_ID, cancelID);
        return this;
    }
}
