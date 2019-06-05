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

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;
import java.util.List;

/**
 * Creates a Choice Set which can be used in subsequent <i>
 * {@linkplain PerformInteraction}</i> Operations.
 * 
 * <p>Function Group: Base </p>
 * 
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 * 
 * <p>AudioStreamingState : ANY</p>
 * 
 * <p>SystemContext: MAIN, MENU, VR</p>
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
 * 			<td>interactionChoiceSetID</td>
 * 			<td>Integer</td>
 * 			<td>A unique ID that identifies the Choice Set</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0; Max Value: 2000000000</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>choiceSet</td>
 * 			<td>Choice[]</td>
 * 			<td>Array of one or more elements.</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 1; Max Value: 100</td>
 * 			<td>SmartDeviceLink 1.0 </td>
 * 		</tr>
 *  </table>
 *  
 *   
 * <p> <b>Note:</b></p>Second Utterance issue with CreateInteractionChoiceSet RPC.  Before a perform interaction
 * is sent you MUST wait for the success from the CreateInteractionChoiceSet RPC.
 * If you do not wait the system may not recognize the first utterance from the user.
 * 
 * <p><b>Response</b></p>
 *
 * Indicates that the corresponding request either failed or succeeded. If the response returns with a SUCCESS result code, this means the Choice Set was created. 
 * 
 * <p><b>Non-default Result Codes:</b></p>
 * 	<p>SUCCESS</p>
 * 	<p>INVALID_DATA</p>
 * 	<p>OUT_OF_MEMORY</p>
 * 	<p>TOO_MANY_PENDING_REQUESTS</p>
 * 	<p>APPLICATION_NOT_REGISTERED</p>
 * 	<p>GENERIC_ERROR</p>
 * <p>	REJECTED</p> 
 * <p> INVALID_ID</p>
 * <p> DUPLICATE_NAME</p>
 *  <p>UNSUPPORTED_RESOURCE </p>    
 *  
 * 
 * @since SmartDeviceLink 1.0
 * @see DeleteInteractionChoiceSet
 * @see PerformInteraction
 */
public class CreateInteractionChoiceSet extends RPCRequest {
	public static final String KEY_CHOICE_SET = "choiceSet";
	public static final String KEY_INTERACTION_CHOICE_SET_ID = "interactionChoiceSetID";

	/**
	 * Constructs a new CreateInteractionChoiceSet object
	 */    
	public CreateInteractionChoiceSet() {
        super(FunctionID.CREATE_INTERACTION_CHOICE_SET.toString());
    }
	/**
	 * <p>Constructs a new CreateInteractionChoiceSet object indicated by the
	 * Hashtable parameter</p>
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */	
    public CreateInteractionChoiceSet(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Constructs a new CreateInteractionChoiceSet object
	 * @param interactionChoiceSetID: an Integer value representing the Choice Set ID
	 *            <b>Notes: </b>Min Value: 0; Max Value: 2000000000
	 * @param choiceSet: a List<Choice> representing the array of one or more elements
	 *            <b>Notes: </b>Min Value: 1; Max Value: 100
	 */
	public CreateInteractionChoiceSet(@NonNull Integer interactionChoiceSetID, @NonNull List<Choice> choiceSet) {
		this();
		setInteractionChoiceSetID(interactionChoiceSetID);
		setChoiceSet(choiceSet);
	}
	/**
	 * Gets the Choice Set unique ID
	 * 
	 * @return Integer -an Integer representing the Choice Set ID
	 */    
    public Integer getInteractionChoiceSetID() {
        return getInteger( KEY_INTERACTION_CHOICE_SET_ID );
    }
	/**
	 * Sets a unique ID that identifies the Choice Set
	 * 
	 * @param interactionChoiceSetID
	 *            an Integer value representing the Choice Set ID
	 *            
	 *            <b>Notes: </b>Min Value: 0; Max Value: 2000000000
	 */    
    public void setInteractionChoiceSetID( @NonNull Integer interactionChoiceSetID ) {
		setParameters(KEY_INTERACTION_CHOICE_SET_ID, interactionChoiceSetID);
    }
	/**
	 * Gets Choice Set Array of one or more elements
	 * 
	 * @return List<Choice> -a List<Choice> representing the array of one or
	 *         more elements
	 */   
    @SuppressWarnings("unchecked") 
    public List<Choice> getChoiceSet() {
		return (List<Choice>) getObject(Choice.class, KEY_CHOICE_SET);
    }
	/**
	 * Sets a Choice Set that is an Array of one or more elements
	 * 
	 * @param choiceSet
	 *            a List<Choice> representing the array of one or more
	 *            elements
	 *            
	 *            <b>Notes: </b>Min Value: 1; Max Value: 100
	 */    
    public void setChoiceSet( @NonNull List<Choice> choiceSet ) {
		setParameters(KEY_CHOICE_SET, choiceSet);
    }
}
