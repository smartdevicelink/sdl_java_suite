package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class AudioControlCapabilities extends RPCStruct {
	public static final String KEY_MODULE_NAME = "moduleName";
	public static final String KEY_SOURCE_AVAILABLE = "sourceAvailable";
	public static final String KEY_KEEP_CONTEXT_AVAILABLE = "keepContextAvailable";
	public static final String KEY_VOLUME_AVAILABLE = "volumeAvailable";
	public static final String KEY_EQUALIZER_AVAILABLE = "equalizerAvailable";
	public static final String KEY_EQUALIZER_MAX_CHANNEL_ID = "equalizerMaxChannelId";

	/**
	 * Constructs a newly allocated AudioControlCapabilities object
	 */
	public AudioControlCapabilities() {
	}

	/**
	 * Constructs a newly allocated AudioControlCapabilities object indicated by the Hashtable parameter
	 *
	 * @param hash The Hashtable to use
	 */
	public AudioControlCapabilities(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a newly allocated AudioControlCapabilities object
	 *
	 * @param moduleName short friendly name of the light control module.
	 */
	public AudioControlCapabilities(@NonNull String moduleName) {
		this();
		setModuleName(moduleName);
	}

	/**
	 * Sets the moduleName portion of the AudioControlCapabilities class
	 *
	 * @param moduleName The short friendly name of the light control module. It should not be used to identify a module by mobile application.
	 */
	public void setModuleName(@NonNull String moduleName) {
		setValue(KEY_MODULE_NAME, moduleName);
	}

	/**
	 * Gets the moduleName portion of the AudioControlCapabilities class
	 *
	 * @return String - The short friendly name of the light control module. It should not be used to identify a module by mobile application.
	 */
	public String getModuleName() {
		return getString(KEY_MODULE_NAME);
	}

	/**
	 * Sets the keepContextAvailable portion of the AudioControlCapabilities class
	 *
	 * @param keepContextAvailable Availability of the keepContext parameter.
	 */
	public void setKeepContextAvailable(Boolean keepContextAvailable) {
		setValue(KEY_KEEP_CONTEXT_AVAILABLE, keepContextAvailable);
	}

	/**
	 * Gets the keepContextAvailable portion of the AudioControlCapabilities class
	 *
	 * @return Boolean - Availability of the keepContext parameter.
	 */
	public Boolean getKeepContextAvailable() {
		return getBoolean(KEY_KEEP_CONTEXT_AVAILABLE);
	}

	/**
	 * Sets the sourceAvailable portion of the AudioControlCapabilities class
	 *
	 * @param sourceAvailable Availability of the control of audio source.
	 */
	public void setSourceAvailable(Boolean sourceAvailable) {
		setValue(KEY_SOURCE_AVAILABLE, sourceAvailable);
	}

	/**
	 * Gets the sourceAvailable portion of the AudioControlCapabilities class
	 *
	 * @return Boolean - Availability of the control of audio source.
	 */
	public Boolean getSourceAvailable() {
		return getBoolean(KEY_SOURCE_AVAILABLE);
	}

	/**
	 * Sets the volumeAvailable portion of the AudioControlCapabilities class
	 *
	 * @param volumeAvailable Availability of the control of audio volume.
	 */
	public void setVolumeAvailable(Boolean volumeAvailable) {
		setValue(KEY_VOLUME_AVAILABLE, volumeAvailable);
	}

	/**
	 * Gets the volumeAvailable portion of the AudioControlCapabilities class
	 *
	 * @return Boolean - Availability of the control of audio volume.
	 */
	public Boolean getVolumeAvailable() {
		return getBoolean(KEY_VOLUME_AVAILABLE);
	}

	/**
	 * Sets the equalizerAvailable portion of the AudioControlCapabilities class
	 *
	 * @param equalizerAvailable Availability of the control of Equalizer Settings.
	 */
	public void setEqualizerAvailable(Boolean equalizerAvailable) {
		setValue(KEY_EQUALIZER_AVAILABLE, equalizerAvailable);
	}

	/**
	 * Gets the equalizerAvailable portion of the AudioControlCapabilities class
	 *
	 * @return Boolean - Availability of the control of Equalizer Settings.
	 */
	public Boolean getEqualizerAvailable() {
		return getBoolean(KEY_EQUALIZER_AVAILABLE);
	}

	/**
	 * Sets the equalizerMaxChannelId portion of the AudioControlCapabilities class
	 *
	 * @param equalizerMaxChannelId Must be included if equalizerAvailable=true, and assume all IDs starting from 1 to this value are valid.
	 */
	public void setEqualizerMaxChannelId(Integer equalizerMaxChannelId) {
		setValue(KEY_EQUALIZER_MAX_CHANNEL_ID, equalizerMaxChannelId);
	}

	/**
	 * Gets the equalizerMaxChannelId portion of the AudioControlCapabilities class
	 *
	 * @return Integer - Must be included if equalizerAvailable=true, and assume all IDs starting from 1 to this value are valid.
	 */
	public Integer getEqualizerMaxChannelId() {
		return getInteger(KEY_EQUALIZER_MAX_CHANNEL_ID);
	}

}
