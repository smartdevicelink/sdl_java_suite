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
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;

import java.util.Hashtable;

/**
 * This is called when a command was selected via VR after pressing the PTT button, or selected from the menu after 
 * pressing the MENU button. <p>
 * <b>Note: </b>Sequence of OnHMIStatus and OnCommand notifications for user-initiated interactions is indeterminate.
 * <p></p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel: 
 * <ul><li>FULL,LIMITED</li></ul>
 * AudioStreamingState: 
 * <ul><li>Any</li></ul>
 * SystemContext: 
 * <ul><li>Any</li></ul>
 * </ul>
 * <p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>Notes</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>cmdID</td>
 * <td>Integer</td>
 * <td>The cmdID of the command the user selected. This is the cmdID value provided by the application in the AddCommand operation that created the command.</td>
 * <td></td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * <tr>
 * <td>triggerSource</td>
 * <td>{@linkplain TriggerSource}</td>
 * <td>Indicates whether command was selected via VR or via a menu selection (using the OK button).</td>
 * <td></td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table>
 * </p>
 * @since SmartDeviceLink 1.0
 * @see AddCommand
 * @see DeleteCommand
 * @see DeleteSubMenu
 */
public class OnCommand extends RPCNotification {
	public static final String KEY_CMD_ID = "cmdID";
	public static final String KEY_TRIGGER_SOURCE = "triggerSource";
	/**
	*Constructs a newly allocated OnCommand object
	*/    
    public OnCommand() {
        super(FunctionID.ON_COMMAND.toString());
    }
    /**
    *<p>Constructs a newly allocated OnCommand object indicated by the Hashtable parameter</p>
    *@param hash The Hashtable to use
    */    
    public OnCommand(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     *Constructs a newly allocated OnCommand object
     * @param cmdID an integer object representing a Command ID
     * @param triggerSource a TriggerSource object
     */
    public OnCommand(@NonNull Integer cmdID, @NonNull TriggerSource triggerSource) {
        this();
        setCmdID(cmdID);
        setTriggerSource(triggerSource);
    }
    /**
     * <p>Returns an <i>Integer</i> object representing the Command ID</p>
     * @return Integer an integer representation of this object
     */    
    public Integer getCmdID() {
        return getInteger( KEY_CMD_ID );
    }
    /**
     * <p>Sets a Command ID</p>
     * @param cmdID an integer object representing a Command ID
     */
    public OnCommand setCmdID(@NonNull Integer cmdID) {
        setParameters(KEY_CMD_ID, cmdID);
        return this;
    }
    /**
     * <p>Returns a <I>TriggerSource</I> object which will be shown in the HMI</p>    
     * @return TriggerSource a TriggerSource object
     */    
    public TriggerSource getTriggerSource() {
        return (TriggerSource) getObject(TriggerSource.class, KEY_TRIGGER_SOURCE);
    }
    /**
     * <p>Sets TriggerSource</p>
     * <p>Indicates whether command was selected via VR or via a menu selection (using the OK button).</p>
     * @param triggerSource a TriggerSource object
     */
    public OnCommand setTriggerSource(@NonNull TriggerSource triggerSource) {
        setParameters(KEY_TRIGGER_SOURCE, triggerSource);
        return this;
    }
}
