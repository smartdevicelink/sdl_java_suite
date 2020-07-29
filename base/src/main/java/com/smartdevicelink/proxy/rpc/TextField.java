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
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;

import java.util.Hashtable;

/**
 * Struct defining the characteristics of a displayed field on the HMI.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>name</td>
 * 			<td>TextFieldName</td>
 * 			<td>Enumeration identifying the field.	</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>characterSet</td>
 * 			<td>CharacterSet</td>
 * 			<td>The set of characters that are supported by this text field. All text is sent in UTF-8 format, but not all systems may support all of the characters expressed by UTF-8. All systems will support at least ASCII, but they may support more, either the LATIN-1 character set, or the full UTF-8 character set.	</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>width</td>
 * 			<td>Integer</td>
 * 			<td>The number of characters in one row of this field.
 * 					<ul>
 *					<li>Minvalue="1"</li>
 *					<li>maxvalue="500"</li>
 *					</ul>
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rows</td>
 * 			<td>Integer</td>
 * 			<td>The number of rows for this text field.
 * 					<ul>
 *					<li>Minvalue="1"</li>
 *					<li>maxvalue="3"</li>
 *					</ul>
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 1.0
 * 
 * @see TextFieldName
 * @see Alert
 * @see Show
 * @see PerformInteraction
 * @see ScrollableMessage
 * @see PerformAudioPassThru
 * @see ShowConstantTbt
 * 
 */
public class TextField extends RPCStruct {
	public static final String KEY_WIDTH = "width";
	public static final String KEY_CHARACTER_SET = "characterSet";
	public static final String KEY_ROWS = "rows";
	public static final String KEY_NAME = "name";
	/**
	 * Constructs a newly allocated TextField object
	 */
    public TextField() { }
    /**
     * Constructs a newly allocated TextField object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */    
    public TextField(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Constructs a newly allocated TextField object
	 * @param name Enumeration identifying the field.
	 * @param characterSet The set of characters that are supported by this text field.
     * All text is sent in UTF-8 format, but not all systems may support all of the characters expressed by UTF-8.
     * All systems will support at least ASCII, but they may support more, either the LATIN-1 character set, or the full UTF-8 character set.
	 * @param width The number of characters in one row of this field.
	 * @param rows The number of rows for this text field.
	 */
	public TextField(@NonNull TextFieldName name, @NonNull CharacterSet characterSet, @NonNull Integer width, @NonNull Integer rows){
		this();
		setName(name);
		setCharacterSet(characterSet);
		setWidth(width);
		setRows(rows);
	}
    /**
     * Get the enumeration identifying the field.	
     * @return the name of TextField
     */    
    public TextFieldName getName() {
        return (TextFieldName) getObject(TextFieldName.class, KEY_NAME);
    }
    /**
     * Set the enumeration identifying the field.	
     * @param name the name of TextField
     */    
    public void setName(@NonNull TextFieldName name ) {
        setValue(KEY_NAME, name);
    }
    /**
     * Get the character set that is supported in this field.
     * @return the character set
     */    
    public CharacterSet getCharacterSet() {
        return (CharacterSet) getObject(CharacterSet.class, KEY_CHARACTER_SET);
    }
    /**
     * Set the character set that is supported in this field.
     * @param characterSet - The set of characters that are supported by this text field.
     * All text is sent in UTF-8 format, but not all systems may support all of the characters expressed by UTF-8.
     * All systems will support at least ASCII, but they may support more, either the LATIN-1 character set, or the full UTF-8 character set.
     */    
    public void setCharacterSet(@NonNull CharacterSet characterSet ) {
        setValue(KEY_CHARACTER_SET, characterSet);
    }
    /**
     * Get the number of characters in one row of this field.
     * 					<ul>
     *					<li>Minvalue="1"</li>
     *					<li>maxvalue="500"</li>
     *					</ul>
     * @return the number of characters in one row of this field
     */    
    public Integer getWidth() {
        return getInteger( KEY_WIDTH );
    }
    /**
     * Set the number of characters in one row of this field.
     * 					<ul>
     *					<li>Minvalue="1"</li>
     *					<li>maxvalue="500"</li>
     *					</ul>
     * @param width  the number of characters in one row of this field
     */    
    public void setWidth(@NonNull Integer width ) {
        setValue(KEY_WIDTH, width);
    }
    /**
     *Get the number of rows for this text field.
     * 					<ul>
     *					<li>Minvalue="1"</li>
     *					<li>maxvalue="3"</li>
     *					</ul>
     * @return  the number of rows for this text field
     */    
    public Integer getRows() {
        return getInteger( KEY_ROWS );
    }
    public void setRows(@NonNull Integer rows ) {
        setValue(KEY_ROWS, rows);
    }
}
