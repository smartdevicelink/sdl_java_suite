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

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.Version;

import java.util.Hashtable;

/**
 * <p>Contains information about a SoftButton's capabilities.</p>
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>shortPressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports a short press.
 *					Whenever the button is pressed short, onButtonPressed( SHORT) will be invoked.
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>longPressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports a LONG press.
 * 					Whenever the button is pressed long, onButtonPressed( LONG) will be invoked.
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>upDownAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports "button down" and "button up". Whenever the button is pressed, onButtonEvent( DOWN) will be invoked.
 *					Whenever the button is released, onButtonEvent( UP) will be invoked. * 			
 *			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>imageSupported</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports referencing a static or dynamic image.
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class SoftButtonCapabilities extends RPCStruct {
	public static final String KEY_IMAGE_SUPPORTED = "imageSupported";
	public static final String KEY_SHORT_PRESS_AVAILABLE = "shortPressAvailable";
	public static final String KEY_LONG_PRESS_AVAILABLE = "longPressAvailable";
	public static final String KEY_UP_DOWN_AVAILABLE = "upDownAvailable";
	public static final String KEY_TEXT_SUPPORTED = "textSupported";

	/**
	 * Constructs a newly allocated SoftButtonCapabilities object
	 */
    public SoftButtonCapabilities() { }
    
    /**
     * Constructs a newly allocated SoftButtonCapabilities object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public SoftButtonCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a newly allocated SoftButtonCapabilities object
	 * @param shortPressAvailable The button supports a short press.
	 * @param longPressAvailable The button supports a LONG press
	 * @param upDownAvailable The button supports "button down" and "button up".
	 * @param imageSupported The button supports referencing a static or dynamic image.
	 */
	public SoftButtonCapabilities(@NonNull Boolean shortPressAvailable, @NonNull Boolean longPressAvailable, @NonNull Boolean upDownAvailable, @NonNull Boolean imageSupported){
		this();
		setShortPressAvailable(shortPressAvailable);
		setLongPressAvailable(longPressAvailable);
		setUpDownAvailable(upDownAvailable);
		setImageSupported(imageSupported);
	}

    @Override
    public void format(Version rpcVersion, boolean formatParams) {
        super.format(rpcVersion, formatParams);
        if(!store.containsKey(KEY_IMAGE_SUPPORTED)){
            // At some point this was added to the RPC spec as mandatory but at least in v1.0.0
            // it was not included.
            store.put(KEY_IMAGE_SUPPORTED, Boolean.FALSE);
        }
    }

    /**
     * set the button supports a short press.
     * @param shortPressAvailable whether the button supports a short press.
     */
    public SoftButtonCapabilities setShortPressAvailable(@NonNull Boolean shortPressAvailable) {
        setValue(KEY_SHORT_PRESS_AVAILABLE, shortPressAvailable);
        return this;
    }
    
    /**
     * get whether the button supports a short press.
     * @return whether the button supports a short press
     */
    public Boolean getShortPressAvailable() {
        return getBoolean( KEY_SHORT_PRESS_AVAILABLE);
    }
    
    /**
     * set the button supports a LONG press.
     * @param longPressAvailable whether the button supports a long press
     */
    public SoftButtonCapabilities setLongPressAvailable(@NonNull Boolean longPressAvailable) {
        setValue(KEY_LONG_PRESS_AVAILABLE, longPressAvailable);
        return this;
    }
    
    /**
     * get whether  the button supports a LONG press.
     * @return whether  the button supports a LONG press
     */
    public Boolean getLongPressAvailable() {
        return getBoolean( KEY_LONG_PRESS_AVAILABLE);
    }
    
    /**
     * set the button supports "button down" and "button up".
     * @param upDownAvailable the button supports "button down" and "button up".
     */
    public SoftButtonCapabilities setUpDownAvailable(@NonNull Boolean upDownAvailable) {
        setValue(KEY_UP_DOWN_AVAILABLE, upDownAvailable);
        return this;
    }
    
    /**
     * get the button supports "button down" and "button up".
     * @return the button supports "button down" and "button up".
     */
    public Boolean getUpDownAvailable() {
        return getBoolean( KEY_UP_DOWN_AVAILABLE);
    }
    
    /**
     * set the button supports referencing a static or dynamic image.
     * @param imageSupported whether the button supports referencing a static or dynamic image.
     */
    public SoftButtonCapabilities setImageSupported(@NonNull Boolean imageSupported) {
        setValue(KEY_IMAGE_SUPPORTED, imageSupported);
        return this;
    }
    
    /**
     * get the button supports referencing a static or dynamic image.
     * @return the button supports referencing a static or dynamic image.
     */
    public Boolean getImageSupported() {
        return getBoolean( KEY_IMAGE_SUPPORTED);
    }

    /**
     * set the text support. If not included, the default value should be considered true that the button will support text.
     * @param textSupported whether the button supports the use of text or not.
     * @since 6.0
     */
    public SoftButtonCapabilities setTextSupported( Boolean textSupported) {
        setValue(KEY_TEXT_SUPPORTED, textSupported);
        return this;
    }

    /**
     * get the text support.
     * @return Boolean - the text is supported or not.
     */
    public Boolean getTextSupported() {
        return getBoolean( KEY_TEXT_SUPPORTED);
    }
}
