package com.smartdevicelink.proxy.rpc.enums;

/**
 * Identifies the playback status of a media app
 *
 * @since SmartDeviceLink 5.0
 */
public enum AudioStreamingIndicator {
	/**
	 * Default playback indicator.
	 *
	 * @since SmartDeviceLink 5.0
	 */
	PLAY_PAUSE,

	/**
	 * Indicates that a button press of the Play/Pause button would start the playback.
	 *
	 * @since SmartDeviceLink 5.0
	 */
	PLAY,
	/**
	 * Indicates that a button press of the Play/Pause button would pause the current playback.
	 *
	 * @since SmartDeviceLink 5.0
	 */
	PAUSE,
	/**
	 * Indicates that a button press of the Play/Pause button would stop the current playback.
	 *
	 * @since SmartDeviceLink 5.0
	 */
	STOP,
	;

	/**
	 * Convert String to AudioStreamingIndicator
	 * @param value String
	 * @return AudioStreamingIndicator
	 */
	public static AudioStreamingIndicator valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
	}
}
