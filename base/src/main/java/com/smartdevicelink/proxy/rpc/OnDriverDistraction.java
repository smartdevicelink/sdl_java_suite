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
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;

import java.util.Hashtable;

/**
 * <p>Notifies the application of the current driver distraction state (whether driver distraction rules are in effect, or 
 * not).</p>
 *
 * <p></p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel: 
 * <ul><li>Can be sent with FULL, LIMITED or BACKGROUND</li></ul>
 * AudioStreamingState: 
 * <ul><li>Any</li></ul>
 * SystemContext: 
 * <ul><li>Any</li></ul>
 * </ul>
 * <p></p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>state</td>
 * <td>{@linkplain DriverDistractionState}</td>
 * <td>Current driver distraction state (i.e. whether driver distraction rules are in effect, or not). </td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table> 
 * @since SmartDeviceLink 1.0
 */
public class OnDriverDistraction  extends RPCNotification {
	public static final String KEY_STATE = "state";
    public static final String KEY_LOCKSCREEN_DISMISSIBLE = "lockScreenDismissalEnabled";
    public static final String KEY_LOCKSCREEN_DISMISSIBLE_MSG = "lockScreenDismissalWarning";
	/**
	*Constructs a newly allocated OnDriverDistraction object
	*/ 
	public OnDriverDistraction() {
        super(FunctionID.ON_DRIVER_DISTRACTION.toString());
    }
	/**
     *<p>Constructs a newly allocated OnDriverDistraction object indicated by the Hashtable parameter</p>
     *@param hash The Hashtable to use
     */	
    public OnDriverDistraction(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     *Constructs a newly allocated OnDriverDistraction object
     * @param state the current driver distraction state
     */
    public OnDriverDistraction(@NonNull DriverDistractionState state) {
        this();
        setState(state);
    }
    /**
     * <p>Called to get the current driver distraction state(i.e. whether driver distraction rules are in effect, or not)</p>
     * @return {@linkplain DriverDistractionState} the Current driver distraction state.
     */    
    public DriverDistractionState getState() {
        return (DriverDistractionState) getObject(DriverDistractionState.class, KEY_STATE);
    }
    /**
     * <p>Called to set the driver distraction state(i.e. whether driver distraction rules are in effect, or not)</p>
     * @param state the current driver distraction state
     */    
    public void setState( @NonNull DriverDistractionState state ) {
        setParameters(KEY_STATE, state);
    }

    /**
     * <p>Called to set dismissible state of Lockscreen</p>
     * @param isDismissible the Lockscreen's dismissibility
     */
    public void setLockscreenDismissibility(boolean isDismissible) {
        setParameters(KEY_LOCKSCREEN_DISMISSIBLE, isDismissible);
    }

    /**
     * <p>Called to get the dismissible state of Lockscreen</p>
     * @return true if the Lockscreen is dismissible, false otherwise
     */
    public Boolean getLockscreenDismissibility() {
        return (Boolean) getObject(Boolean.class, KEY_LOCKSCREEN_DISMISSIBLE);
    }

    /**
     * Called to set a warning message for the lockscreen
     * @param msg the message to be set
     */
    public void setLockscreenWarningMessage(String msg) {
        setParameters(KEY_LOCKSCREEN_DISMISSIBLE_MSG, msg);
    }

    /**
     * Called to get the lockscreen warning message
     * @return warning message
     */
    public String getLockscreenWarningMessage() {
        return (String) getObject(String.class, KEY_LOCKSCREEN_DISMISSIBLE_MSG);
    }
}
