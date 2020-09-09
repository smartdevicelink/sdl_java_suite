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
 * Speaks a phrase over the vehicle audio system using SDL's TTS
 * (text-to-speech) engine. The provided text to be spoken can be simply a text
 * phrase, or it can consist of phoneme specifications to direct SDL's TTS
 * engine to speak a "speech-sculpted" phrase
 * <p></p>
 * Receipt of the Response indicates the completion of the Speak operation,
 * regardless of how the Speak operation may have completed (i.e. successfully,
 * interrupted, terminated, etc.)
 * <p></p>
 * Requesting a new Speak operation while the application has another Speak
 * operation already in progress (i.e. no corresponding Response for that
 * in-progress Speak operation has been received yet) will terminate the
 * in-progress Speak operation (causing its corresponding Response to be sent by
 * SDL) and begin the requested Speak operation
 * <p></p>
 * Requesting a new Speak operation while the application has an <i>
 * {@linkplain Alert}</i> operation already in progress (i.e. no corresponding
 * Response for that in-progress <i>{@linkplain Alert}</i> operation has been
 * received yet) will result in the Speak operation request being rejected
 * (indicated in the Response to the Request)
 * <p></p>
 * Requesting a new <i>{@linkplain Alert}</i> operation while the application
 * has a Speak operation already in progress (i.e. no corresponding Response for
 * that in-progress Speak operation has been received yet) will terminate the
 * in-progress Speak operation (causing its corresponding Response to be sent by
 * SDL) and begin the requested <i>{@linkplain Alert}</i> operation
 * <p></p>
 * Requesting a new Speak operation while the application has a <i>
 * {@linkplain PerformInteraction}</i> operation already in progress (i.e. no
 * corresponding Response for that in-progress <i>
 * {@linkplain PerformInteraction}</i> operation has been received yet) will
 * result in the Speak operation request being rejected (indicated in the
 * Response to the Request)
 * <p></p>
 * Requesting a <i> {@linkplain PerformInteraction}</i> operation while the
 * application has a Speak operation already in progress (i.e. no corresponding
 * Response for that in-progress Speak operation has been received yet) will
 * terminate the in-progress Speak operation (causing its corresponding Response
 * to be sent by SDL) and begin the requested <i>
 * {@linkplain PerformInteraction}</i> operation
 * <p></p>
 * 
 * <p><b>HMI Status Requirements:</b></p>
 * <p>HMILevel: FULL, Limited</p>
 *<p> AudioStreamingState: Any</p>
 * <p>SystemContext: MAIN, MENU, VR</p>
 * 
 * <p><b>Notes: </b></p>
 * <ul>
 * <li>When <i>{@linkplain Alert}</i> is issued with MENU in effect, <i>
 * {@linkplain Alert}</i> is queued and "played" when MENU interaction is
 * completed (i.e. SystemContext reverts to MAIN). When <i>{@linkplain Alert}
 * </i> is issued with VR in effect, <i>{@linkplain Alert}</i> is queued and
 * "played" when VR interaction is completed (i.e. SystemContext reverts to
 * MAIN)</li>
 * <li>When both <i>{@linkplain Alert}</i> and Speak are queued during MENU or
 * VR, they are "played" back in the order in which they were queued, with all
 * existing rules for "collisions" still in effect</li>
 * </ul>
 * 
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th> Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>ttsChunks</td>
 * 			<td>String</td>
 * 			<td>An array of 1-100 TTSChunk structs which, taken together, specify the phrase to be spoken.</td>
 * 			<td>Y</td>
 * 			<td><p>The array must have 1-100 elements. </p><p>The total length of the phrase composed from the ttsChunks provided must be less than 500 characters or the request will be rejected. </p>Each chunk can be no more than 500 characters.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 * <p><b>Response</b> </p>
 * <p>This Response notifies the application of the completion, interruption, or failure of a Speak Request.</p>
 * 
 * <p><b>Non-default Result Codes:</b> </p>
 * 
 *	<p> SUCCESS </p>
 *<p>	 INVALID_DATA</p>
 *	<p> OUT_OF_MEMORY </p>
 *	<p>APPLICATION_NOT_REGISTERED </p>
 *	<p>TOO_MANY_PENDING_REQUESTS </p>
 *<p>	GENERIC_ERROR </p>
 *	<p>REJECTED  </p>
 *	<p>DISALLOWED </p>
 *	<p>ABORTED </p>
 * 
 * <p><b>Additional Notes:</b></p><ul>Total character limit depends on platform. Chunks are limited to 500 characters; however you can have multiple TTS chunks. On Gen 1.1 there is a total character limit of 500 characters across all chunks. This could vary according to the VCA. 
 * 
 * @since SmartDeviceLink 1.0
 * @see Alert
 */
public class Speak extends RPCRequest {
	public static final String KEY_TTS_CHUNKS = "ttsChunks";

	/**
	 * Constructs a new Speak object
	 */    
	public Speak() {
        super(FunctionID.SPEAK.toString());
    }
	/**
	 * Constructs a new Speak object indicated by the Hashtable parameter
	 * <p></p>
	 * 
	 * @param hash The Hashtable to use
	 */	
    public Speak(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new Speak object
	 * @param ttsChunks An array of 1-100 TTSChunk structs which, taken together, specify the phrase to be spoken.
	 */
	public Speak(@NonNull List<TTSChunk> ttsChunks){
		this();
		setTtsChunks(ttsChunks);
	}
	/**
	 * Gets a List<TTSChunk> representing an array of 1-100 TTSChunk structs
	 * which, taken together, specify the phrase to be spoken
	 * 
	 * @return List<TTSChunk> -an Array of 1-100 TTSChunk specify the phrase to be spoken
	 */    
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTtsChunks() {
		return (List<TTSChunk>) getObject(TTSChunk.class, KEY_TTS_CHUNKS);
    }
	/**
	 * Sets a List<TTSChunk> representing an array of 1-100 TTSChunk structs
	 * which, taken together, specify the phrase to be spoken
	 *
	 * @param ttsChunks
	 *            a List<TTSChunk> value representing an array of 1-100 TTSChunk structs
	 * which specify the phrase to be spoken
	 *            <p></p>
	 *            <ul>
	 *            <li>The array must have 1-100 elements</li>
	 *            <li>The total length of the phrase composed from the ttsChunks
	 *            provided must be less than 500 characters or the request will
	 *            be rejected</li>
	 *            <li>Each chunk can be no more than 500 characters</li>
	 *            </ul>
	 */
    public Speak setTtsChunks(@NonNull List<TTSChunk> ttsChunks) {
        setParameters(KEY_TTS_CHUNKS, ttsChunks);
        return this;
    }
}
