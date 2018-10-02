package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;

import java.util.Hashtable;
import java.util.List;

public class AudioControlData extends RPCStruct {
	public static final String KEY_SOURCE = "source";
	public static final String KEY_KEEP_CONTEXT = "keepContext";
	public static final String KEY_VOLUME = "volume";
	public static final String KEY_EQUALIZER_SETTINGS = "equalizerSettings";

	/**
	 * Constructs a newly allocated AudioControlData object
	 */
	public AudioControlData() {
	}

	/**
	 * Constructs a newly allocated AudioControlData object indicated by the Hashtable parameter
	 *
	 * @param hash The Hashtable to use
	 */
	public AudioControlData(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Sets the source portion of the AudioControlData class
	 *
	 * @param source In a getter response or a notification, it is the current primary audio source of the system.
	 *               In a setter request, it is the target audio source that the system shall switch to.
	 *               If the value is MOBILE_APP, the system shall switch to the mobile media app that issues the setter RPC.
	 */
	public void setSource(PrimaryAudioSource source) {
		setValue(KEY_SOURCE, source);
	}

	/**
	 * Gets the source portion of the AudioControlData class
	 *
	 * @return PrimaryAudioSource - In a getter response or a notification, it is the current primary audio source of the system.
	 * In a setter request, it is the target audio source that the system shall switch to.
	 * If the value is MOBILE_APP, the system shall switch to the mobile media app that issues the setter RPC.
	 */
	public PrimaryAudioSource getSource() {
		return (PrimaryAudioSource) getObject(PrimaryAudioSource.class, KEY_SOURCE);
	}

	/**
	 * Sets the keepContext portion of the AudioControlData class
	 *
	 * @param keepContext This parameter shall not be present in any getter responses or notifications.
	 *                    This parameter is optional in a setter request. The default value is false.
	 *                    If it is true, the system not only changes the audio source but also brings the default infotainment system UI associated with the audio source to foreground and set the application to background.
	 *                    If it is false, the system changes the audio source, but keeps the current application's context.
	 */
	public void setKeepContext(Boolean keepContext) {
		setValue(KEY_KEEP_CONTEXT, keepContext);
	}

	/**
	 * Gets the keepContext portion of the AudioControlData class
	 *
	 * @return Boolean - This parameter shall not be present in any getter responses or notifications.
	 * This parameter is optional in a setter request. The default value is false.
	 * If it is true, the system not only changes the audio source but also brings the default infotainment system UI associated with the audio source to foreground and set the application to background.
	 * If it is false, the system changes the audio source, but keeps the current application's context.
	 */
	public Boolean getKeepContext() {
		return getBoolean(KEY_KEEP_CONTEXT);
	}

	/**
	 * Sets the volume portion of the AudioControlData class
	 *
	 * @param volume Reflects the volume of audio, from 0%-100%.
	 */
	public void setVolume(Integer volume) {
		setValue(KEY_VOLUME, volume);
	}

	/**
	 * Gets the volume portion of the AudioControlData class
	 *
	 * @return Integer - Reflects the volume of audio, from 0%-100%.
	 */
	public Integer getVolume() {
		return getInteger(KEY_VOLUME);
	}

	/**
	 * Gets the equalizerSettings portion of the AudioControlData class
	 *
	 * @return List<EqualizerSettings> - Defines the list of supported channels (band) and their current/desired settings on HMI.
	 */
	@SuppressWarnings("unchecked")
	public List<EqualizerSettings> getEqualizerSettings() {
		return (List<EqualizerSettings>) getObject(EqualizerSettings.class, KEY_EQUALIZER_SETTINGS);
	}

	/**
	 * Sets the equalizerSettings portion of the AudioControlData class
	 *
	 * @param equalizerSettings Defines the list of supported channels (band) and their current/desired settings on HMI.
	 */
	public void setEqualizerSettings(List<EqualizerSettings> equalizerSettings) {
		setValue(KEY_EQUALIZER_SETTINGS, equalizerSettings);
	}

}
