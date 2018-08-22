package com.smartdevicelink.proxy.rpc.enums;

/**
 * Identifies the playback status of a media app
 *
 * @since SmartDeviceLink 4.6
 */
public enum AudioStreamingIndicator {
	/**
	 * Default playback indicator.
	 *
	 * @since SmartDeviceLink 4.6
	 */
	PLAY_PAUSE,

	/**
	 * Indicates that a button press of the Play/Pause button would start the playback.
	 *
	 * @since SmartDeviceLink 4.6
	 */
	PLAY,
	/**
	 * Indicates that a button press of the Play/Pause button would pause the current playback.
	 *
	 * @since SmartDeviceLink 4.6
	 */
	PAUSE,
	/**
	 * Indicates that a button press of the Play/Pause button would stop the current playback.
	 *
	 * @since SmartDeviceLink 4.6
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
