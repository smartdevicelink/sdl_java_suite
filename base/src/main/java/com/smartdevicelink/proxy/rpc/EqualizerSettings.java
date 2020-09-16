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

import java.util.Hashtable;

/**
 * Defines the each Equalizer channel settings.
 */
public class EqualizerSettings extends RPCStruct {
	public static final String KEY_CHANNEL_ID = "channelId";
	public static final String KEY_CHANNEL_NAME = "channelName";
	public static final String KEY_CHANNEL_SETTING = "channelSetting";

	/**
	 * Constructs a newly allocated EqualizerSettings object
	 */
	public EqualizerSettings() {
	}

	/**
	 * Constructs a newly allocated EqualizerSettings object indicated by the Hashtable parameter
	 *
	 * @param hash The Hashtable to use
	 */
	public EqualizerSettings(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a newly allocated EqualizerSettings object
	 *
	 * @param channelId      Min: 0  Max: 100
	 * @param channelSetting Min: 0  Max: 100
	 */
	public EqualizerSettings(@NonNull Integer channelId, @NonNull Integer channelSetting) {
		this();
		setChannelId(channelId);
		setChannelSetting(channelSetting);
	}

	/**
	 * Sets the channelId portion of the EqualizerSettings class
	 *
	 * @param channelId ID that represents the channel these settings should be applied
	 */
	public EqualizerSettings setChannelId(@NonNull Integer channelId) {
        setValue(KEY_CHANNEL_ID, channelId);
        return this;
    }

	/**
	 * Gets the channelId portion of the EqualizerSettings class
	 *
	 * @return Integer
	 */
	public Integer getChannelId() {
		return getInteger(KEY_CHANNEL_ID);
	}

	/**
	 * Sets the channelName portion of the EqualizerSettings class
	 *
	 * @param channelName Read-only channel / frequency name (e.i. "Treble, MidRange, Bass" or "125 Hz").
	 */
	public EqualizerSettings setChannelName( String channelName) {
        setValue(KEY_CHANNEL_NAME, channelName);
        return this;
    }

	/**
	 * Gets the channelName portion of the EqualizerSettings class
	 *
	 * @return String - Read-only channel / frequency name (e.i. "Treble, MidRange, Bass" or "125 Hz").
	 */
	public String getChannelName() {
		return getString(KEY_CHANNEL_NAME);
	}

	/**
	 * Sets the channelSetting portion of the EqualizerSettings class
	 *
	 * @param channelSetting Reflects the setting, from 0%-100%.
	 */
	public EqualizerSettings setChannelSetting(@NonNull Integer channelSetting) {
        setValue(KEY_CHANNEL_SETTING, channelSetting);
        return this;
    }

	/**
	 * Gets the channelSetting portion of the EqualizerSettings class
	 *
	 * @return Integer - Reflects the setting, from 0%-100%.
	 */
	public Integer getChannelSetting() {
		return getInteger(KEY_CHANNEL_SETTING);
	}
}
