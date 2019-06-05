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

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

import java.util.Hashtable;

/**
 * <p>Specifies what is to be spoken. This can be simply a text phrase, which SDL will speak according to its own rules.
 *  It can also be phonemes from either the Microsoft SAPI phoneme set, or from the LHPLUS phoneme set. 
 *  It can also be a pre-recorded sound in WAV format (either developer-defined, or provided by the SDL platform).</p>
 *  
 *  <p>In SDL, words, and therefore sentences, can be built up from phonemes and are used to explicitly provide the proper pronounciation to the TTS engine.
 *   For example, to have SDL pronounce the word "read" as "red", rather than as when it is pronounced like "reed",
 *   the developer would use phonemes to express this desired pronounciation.</p>
 *  <p>For more information about phonemes, see <a href="http://en.wikipedia.org/wiki/Phoneme">http://en.wikipedia.org/wiki/Phoneme</a></p>.
 *  <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>text</td>
 * 			<td>String</td>
 * 			<td>Text to be spoken, or a phoneme specification, or the name of a pre-recorded sound. The contents of this field are indicated by the "type" field.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>type</td>
 * 			<td>SpeechCapabilities</td>
 * 			<td>Indicates the type of information in the "text" field (e.g. phrase to be spoken, phoneme specification, name of pre-recorded sound).	</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 1.0
 */
public class TTSChunk extends RPCStruct {
	public static final String KEY_TEXT = "text";
	public static final String KEY_TYPE = "type";
	/**
	 * Constructs a newly allocated TTSChunk object
	 */
    public TTSChunk() { }
    /**
     * Constructs a newly allocated TTSChunk object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */    
    public TTSChunk(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a newly allocated TTSChunk object
	 * @param text Text to be spoken, or a phoneme specification, or the name of a pre-recorded sound. The contents of this field are indicated by the "type" field.
	 * @param type Indicates the type of information in the "text" field (e.g. phrase to be spoken, phoneme specification, name of pre-recorded sound).
	 */
    public TTSChunk(@NonNull String text, @NonNull SpeechCapabilities type){
    	this();
    	setText(text);
    	setType(type);
	}
    /**
     * Get text to be spoken, or a phoneme specification, or the name of a pre-recorded sound. The contents of this field are indicated by the "type" field.
     * @return text to be spoken, or a phoneme specification, or the name of a pre-recorded sound
     */
    public String getText() {
        return getString( KEY_TEXT );
    }
    /**
     * Set the text to be spoken, or a phoneme specification, or the name of a pre-recorded sound. The contents of this field are indicated by the "type" field.
     * @param text to be spoken, or a phoneme specification, or the name of a pre-recorded sound.
     */    
    public void setText(@NonNull String text ) {
        setValue(KEY_TEXT, text);
    }
    /**
     * Get the type of information in the "text" field (e.g. phrase to be spoken, phoneme specification, name of pre-recorded sound).	
     * @return the type of information in the "text" field
     */    
    public SpeechCapabilities getType() {
        return (SpeechCapabilities) getObject(SpeechCapabilities.class, KEY_TYPE);
    }
    /**
     * Set the type of information in the "text" field (e.g. phrase to be spoken, phoneme specification, name of pre-recorded sound).	
     * @param type the type of information in the "text" field
     */    
    public void setType(@NonNull SpeechCapabilities type ) {
        setValue(KEY_TYPE, type);
    }
}
