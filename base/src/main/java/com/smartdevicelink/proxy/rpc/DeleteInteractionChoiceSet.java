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

/**
 * Deletes an existing Choice Set identified by the parameter
 * interactionChoiceSetID. If the specified interactionChoiceSetID is currently
 * in use by an active <i> {@linkplain PerformInteraction}</i> this call to
 * delete the Choice Set will fail returning an IN_USE resultCode
 *
 * <p>Function Group: Base</p>
 *
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 *
 * <p>AudioStreamingState: Any</p>
 *
 * <p>SystemContext: MAIN, MENU, VR </p>
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *         <tr>
 *             <th>Name</th>
 *             <th>Type</th>
 *             <th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 *             <th>Version</th>
 *         </tr>
 *         <tr>
 *             <td>interactionChoiceSetID</td>
 *             <td>Integer</td>
 *             <td> A unique ID that identifies the Choice Set (specified in a previous call to CreateInteractionChoiceSet)</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0 ; Max Value: 2000000000</td>
 *             <td>SmartDeviceLink 1.0</td>
 *         </tr>
 *  </table>
 *
 * <p><b>Response </b></p>
 * <p>
 * If a resultCode of "SUCCESS" is returned, the requested choice set has been created and can now be referenced by the application using the value of interactionChoiceSetID provided by the application.
 *
 * <p><b>Non-default Result Codes:</b></p>
 *     <p>SUCCESS</p>
 *     <p>INVALID_DATA</p>
 *     <p>OUT_OF_MEMORY</p>
 *     <p>TOO_MANY_PENDING_REQUESTS</p>
 *     <p>APPLICATION_NOT_REGISTERED</p>
 *     <p>GENERIC_ERROR</p>
 *     <p>REJECTED</p>
 *   <p>INVALID_ID</p>
 *
 * @see CreateInteractionChoiceSet
 * @see PerformInteraction
 * @since SmartDeviceLink 1.0
 */
public class DeleteInteractionChoiceSet extends RPCRequest {
    public static final String KEY_INTERACTION_CHOICE_SET_ID = "interactionChoiceSetID";

    /**
     * Constructs a new DeleteInteractionChoiceSet object
     */
    public DeleteInteractionChoiceSet() {
        super(FunctionID.DELETE_INTERACTION_CHOICE_SET.toString());
    }

    /**
     * <p>Constructs a new DeleteInteractionChoiceSet object indicated by the
     * Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public DeleteInteractionChoiceSet(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new DeleteInteractionChoiceSet object
     *
     * @param interactionChoiceSetID a unique ID that identifies the Choice Set
     *                               <p><b>Notes: </b>Min Value: 0; Max Value: 2000000000 </p>
     */
    public DeleteInteractionChoiceSet(@NonNull Integer interactionChoiceSetID) {
        this();
        setInteractionChoiceSetID(interactionChoiceSetID);
    }

    /**
     * Gets a unique ID that identifies the Choice Set
     *
     * @return Integer -an Integer value representing the unique Choice Set ID
     */
    public Integer getInteractionChoiceSetID() {
        return getInteger(KEY_INTERACTION_CHOICE_SET_ID);
    }

    /**
     * Sets a unique ID that identifies the Choice Set
     *
     * @param interactionChoiceSetID a unique ID that identifies the Choice Set
     *                               <p><b>Notes: </b>Min Value: 0; Max Value: 2000000000 </p>
     */
    public DeleteInteractionChoiceSet setInteractionChoiceSetID(@NonNull Integer interactionChoiceSetID) {
        setParameters(KEY_INTERACTION_CHOICE_SET_ID, interactionChoiceSetID);
        return this;
    }
}
