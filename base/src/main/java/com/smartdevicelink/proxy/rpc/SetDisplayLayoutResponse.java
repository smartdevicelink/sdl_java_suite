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
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;
import java.util.List;

/**
 * <Strong>If using Widgets with Core > 6.0, use {@link Show} to change widget layouts</Strong> <br>
 * Set Display Layout Response is sent, when SetDisplayLayout has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SetDisplayLayoutResponse extends RPCResponse {
	public static final String KEY_BUTTON_CAPABILITIES = "buttonCapabilities";
	public static final String KEY_DISPLAY_CAPABILITIES = "displayCapabilities";
    public static final String KEY_SOFT_BUTTON_CAPABILITIES = "softButtonCapabilities";
    public static final String KEY_PRESET_BANK_CAPABILITIES = "presetBankCapabilities";

	/**
	 * Constructs a new SetDisplayLayoutResponse object
	 */
    public SetDisplayLayoutResponse() {
        super(FunctionID.SET_DISPLAY_LAYOUT.toString());
    }

	/**
	 * Constructs a new SetDisplayLayoutResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetDisplayLayoutResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new SetDisplayLayoutResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public SetDisplayLayoutResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }

    @SuppressWarnings("unchecked")
    public DisplayCapabilities getDisplayCapabilities() {
        return (DisplayCapabilities) getObject(DisplayCapabilities.class, KEY_DISPLAY_CAPABILITIES);
    }

    public SetDisplayLayoutResponse setDisplayCapabilities( DisplayCapabilities displayCapabilities) {
        setParameters(KEY_DISPLAY_CAPABILITIES, displayCapabilities);
        return this;
    }

    @SuppressWarnings("unchecked")
    public List<ButtonCapabilities> getButtonCapabilities() {
        return (List<ButtonCapabilities>) getObject(ButtonCapabilities.class, KEY_BUTTON_CAPABILITIES);
    }

    public SetDisplayLayoutResponse setButtonCapabilities( List<ButtonCapabilities> buttonCapabilities) {
        setParameters(KEY_BUTTON_CAPABILITIES, buttonCapabilities);
        return this;
    }

    @SuppressWarnings("unchecked")
    public List<SoftButtonCapabilities> getSoftButtonCapabilities() {
        return (List<SoftButtonCapabilities>) getObject(SoftButtonCapabilities.class, KEY_SOFT_BUTTON_CAPABILITIES);
    }

    public SetDisplayLayoutResponse setSoftButtonCapabilities( List<SoftButtonCapabilities> softButtonCapabilities) {
        setParameters(KEY_SOFT_BUTTON_CAPABILITIES, softButtonCapabilities);
        return this;
    }

    @SuppressWarnings("unchecked")
    public PresetBankCapabilities getPresetBankCapabilities() {
        return (PresetBankCapabilities) getObject(PresetBankCapabilities.class, KEY_PRESET_BANK_CAPABILITIES);
    }

    public SetDisplayLayoutResponse setPresetBankCapabilities( PresetBankCapabilities presetBankCapabilities) {
        setParameters(KEY_PRESET_BANK_CAPABILITIES, presetBankCapabilities);
        return this;
    }
    
}
