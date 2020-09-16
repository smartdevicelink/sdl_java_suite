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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;
import java.util.List;

/**
 * Updates the list of next maneuvers, which can be requested by the user pressing the soft button "Turns" on the
 * Navigation base screen. Three soft buttons are predefined by the system: Up, Down, Close
 * 
 * <p>Function Group: Navigation</p>
 * 
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 * 
 * 
 * @since SmartDeviceLink 2.0
 * @see ShowConstantTbt
 */
public class UpdateTurnList extends RPCRequest{
    public static final String KEY_TURN_LIST = "turnList";
    public static final String KEY_SOFT_BUTTONS = "softButtons";

    /**
     * Constructs a new UpdateTurnList object
     */
    public UpdateTurnList() {
        super(FunctionID.UPDATE_TURN_LIST.toString());
    }
    
    /**
    * <p>
    * Constructs a new UpdateTurnList object indicated by the Hashtable
    * parameter
    * </p>
    * 
    * @param hash
    *            The Hashtable to use
    */
    public UpdateTurnList(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    /**
     * Sets a list of turns to be shown to the user
     *
     * @param turnList
     *            a List<Turn> value representing a list of turns to be shown to the user
     *            <p>
     *            <b>Notes: </b>Minsize=1; Maxsize=100</p>
     */
    public UpdateTurnList setTurnList( List<Turn> turnList) {
        setParameters(KEY_TURN_LIST, turnList);
        return this;
    }
    
    /**
     * Gets a list of turns to be shown to the user
     * 
     * @return List<Turn> -a List value representing a list of turns
     */
    @SuppressWarnings("unchecked")
    public List<Turn> getTurnList(){
        return (List<Turn>) getObject(Turn.class, KEY_TURN_LIST);
    }

    /**
     * Gets the SoftButton List object
     * 
     * @return List<SoftButton> -a List<SoftButton> representing the List object
     * @since SmartDeviceLink 2.0
     */
    @SuppressWarnings("unchecked")
    public List<SoftButton> getSoftButtons(){
        return (List<SoftButton>) getObject(SoftButton.class, KEY_SOFT_BUTTONS);
    }

    /**
     * Sets the SoftButtons
     *
     * @param softButtons
     *            a List<SoftButton> value
     *            <p>
     *            <b>Notes: </b></p>
     *            <ul>
     *            <li>If omitted on supported displays, the alert will not have any SoftButton</li>
     *            <li>ArrayMin: 0</li>
     *            <li>ArrayMax: 1</li>
     *            </ul>
     * @since SmartDeviceLink 2.0
     */

    public UpdateTurnList setSoftButtons( List<SoftButton> softButtons) {
        setParameters(KEY_SOFT_BUTTONS, softButtons);
        return this;
    }

}
