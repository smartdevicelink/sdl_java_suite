package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

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
	 * @param channelId
	 */
	public void setChannelId(@NonNull Integer channelId) {
		setValue(KEY_CHANNEL_ID, channelId);
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
	 * @param channelName Read-only channel / frequency name (e.i. "Treble, Midrange, Bass" or "125 Hz").
	 */
	public void setChannelName(String channelName) {
		setValue(KEY_CHANNEL_NAME, channelName);
	}

	/**
	 * Gets the channelName portion of the EqualizerSettings class
	 *
	 * @return String - Read-only channel / frequency name (e.i. "Treble, Midrange, Bass" or "125 Hz").
	 */
	public String getChannelName() {
		return getString(KEY_CHANNEL_NAME);
	}

	/**
	 * Sets the channelSetting portion of the EqualizerSettings class
	 *
	 * @param channelSetting Reflects the setting, from 0%-100%.
	 */
	public void setChannelSetting(@NonNull Integer channelSetting) {
		setValue(KEY_CHANNEL_SETTING, channelSetting);
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
