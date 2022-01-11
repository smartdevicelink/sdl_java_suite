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
 * Non periodic vehicle diagnostic request.
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
 * 			<td>targetID</td>
 * 			<td>Integer</td>
 * 			<td>Name of target ECU.</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0; Max Value: 65535</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>messageLength</td>
 * 			<td>Integer</td>
 * 			<td>Length of message (in bytes).</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0; Max Value:65535</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>messageData</td>
 * 			<td>Integer</td>
 * 			<td>Array of bytes comprising CAN message.</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0; Max Value:255; Min Size:1; Max Size:65535</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 * <p><b>HMI must:</b> </p>
 *
 * <p>1.	Check the requested data using provided information of targetID (name of ECU),messageLength and messageData.</p>
 * <p> 2.	Respond with one of the appropriate result codes.And in case of SUCCESS return messageDataResult which is an array of bytes comprising CAN message result.</p>
 *
 * @since SmartDeviceLink 3.0
 */

public class DiagnosticMessage extends RPCRequest {
    public static final String KEY_TARGET_ID = "targetID";
    public static final String KEY_MESSAGE_LENGTH = "messageLength";
    public static final String KEY_MESSAGE_DATA = "messageData";

    /**
     * Constructs a new DiagnosticMessage object
     */
    public DiagnosticMessage() {
        super(FunctionID.DIAGNOSTIC_MESSAGE.toString());
    }

    /**
     * <p>
     * Constructs a new DiagnosticMessage object indicated by the Hashtable
     * parameter
     * </p>
     *
     * @param hash The Hashtable to use to create this RPC
     */
    public DiagnosticMessage(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new DiagnosticMessage object
     */
    public DiagnosticMessage(@NonNull Integer targetID, @NonNull Integer messageLength, @NonNull List<Integer> messageData) {
        this();
        setTargetID(targetID);
        setMessageLength(messageLength);
        setMessageData(messageData);
    }

    /**
     * Sets TargetID
     *
     * @param targetID the target for this Diagnostic Message
     */
    public DiagnosticMessage setTargetID(@NonNull Integer targetID) {
        setParameters(KEY_TARGET_ID, targetID);
        return this;
    }

    /**
     * <p>
     * Returns an <i>Integer</i> object representing the Target ID that you want to add
     * </p>
     *
     * @return Integer -an integer representation a Unique Target ID
     */
    public Integer getTargetID() {
        return getInteger(KEY_TARGET_ID);
    }

    public DiagnosticMessage setMessageLength(@NonNull Integer messageLength) {
        setParameters(KEY_MESSAGE_LENGTH, messageLength);
        return this;
    }

    public Integer getMessageLength() {
        return getInteger(KEY_MESSAGE_LENGTH);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getMessageData() {
        return (List<Integer>) getObject(Integer.class, KEY_MESSAGE_DATA);
    }

    public DiagnosticMessage setMessageData(@NonNull List<Integer> messageData) {
        setParameters(KEY_MESSAGE_DATA, messageData);
        return this;
    }
}
