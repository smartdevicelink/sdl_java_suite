package com.smartdevicelink.proxy.rpc.enums;

/**
 * <p>The SystemCapabilityType indicates which type of capability information exists in a SystemCapability struct.</p>
 *
 * <p><b>Enum List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Enum Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>Async?</th>
 * 			<th>Notes</th>
 * 		</tr>
 * 		<tr>
 * 			<td>NAVIGATION</td>
 * 			<td>NavigationCapability</td>
 * 			<td>Returns Navigation Capabilities</td>
 *          <td>Y</td>
 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
 * 		</tr>
 * 		<tr>
 * 			<td>PHONE_CALL</td>
 * 			<td>PhoneCapability</td>
 * 			<td>Returns Phone Capabilities</td>
 *          <td>Y</td>
 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
 * 		</tr>
 *      <tr>
 * 			<td>VIDEO_STREAMING</td>
 * 			<td>VideoStreamingCapability</td>
 * 			<td>Returns Video Streaming Capabilities</td>
 *          <td>Y</td>
 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
 * 		</tr>
 * 		<tr>
 * 			<td>REMOTE_CONTROL</td>
 * 			<td>RemoteControlCapabilities</td>
 * 			<td>Returns Remote Control Capabilities</td>
 *          <td>Y</td>
 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
 * 		</tr>
 * 		<tr>
 * 			<td>HMI</td>
 * 			<td>HMICapabilities</td>
 * 			<td>Returns HMI Capabilities</td>
 *          <td>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
 * 		</tr>
 * 		<tr>
 * 			<td>DISPLAY</td>
 * 			<td>DisplayCapabilities</td>
 * 			<td>Returns Display Capabilities</td>
 *          <td>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
 * 		</tr>
 * 		<tr>
 * 			<td>AUDIO_PASSTHROUGH</td>
 * 			<td>List<AudioPassThruCapabilities></td>
 * 			<td>Returns a List of AudioPassThruCapabilities</td>
 *          <td>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response. @SuppressWarnings("unchecked") may be needed when casting depending on implementation</td>
 * 		</tr>
 * 		<tr>
 * 			<td>BUTTON</td>
 * 			<td>List<ButtonCapabilities></td>
 * 			<td>Returns a List of ButtonCapabilities</td>
 *          <td>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response. @SuppressWarnings("unchecked") may be needed when casting depending on implementation</td>
 * 		</tr>
 * 		<tr>
 * 			<td>HMI_ZONE</td>
 * 			<td>HmiZoneCapabilities</td>
 * 			<td>Returns HmiZone Capabilities</td>
 *          <td>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
 * 		</tr>
 * 		<tr>
 * 			<td>PRESET_BANK</td>
 * 			<td>PresetBankCapabilities</td>
 * 			<td>Returns PresetBank Capabilities</td>
 *          <td>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
 * 		</tr>
 * 		<tr>
 * 			<td>SOFTBUTTON</td>
 * 			<td>List<SoftButtonCapabilities></td>
 * 			<td>Returns a List of SoftButtonCapabilities</td>
 *          <td>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response. @SuppressWarnings("unchecked") may be needed when casting depending on implementation</td>
 * 		</tr>
 * 		<tr>
 * 			<td>SPEECH</td>
 * 			<td>SpeechCapabilities</td>
 * 			<td>Returns Speech Capabilities</td>
 *          <td>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
 * 		</tr>
 *
 */

public enum SystemCapabilityType {

	/**
	 * Available Asynchronously, then synchronously after successful call <br>
	 * Returns: Object that can be cast to NavigationCapability
	 */
    NAVIGATION,

	/**
	 * Available Asynchronously, then synchronously after successful call <br>
	 * Returns: Object that can be cast to PhoneCapability
	 */
    PHONE_CALL,

	/**
	 * Available Asynchronously, then synchronously after successful call <br>
	 * Returns: Object that can be cast to VideoStreamingCapability
	 */
    VIDEO_STREAMING,

	/**
	 * Available Asynchronously, then synchronously after successful call <br>
	 * Returns: Object that can be cast to RemoteControlCapabilities
	 */
    REMOTE_CONTROL,

    /* These below aren’t actually part of the RPC spec. Only for Internal Proxy use */

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * Returns: Object that can be cast to HMICapabilities
	 */
    HMI,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * Returns: Object that can be cast to DisplayCapabilities
	 */
    DISPLAY,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * Returns: List<AudioPassThruCapabilities> <br>
	 * Note: @SuppressWarnings("unchecked") may be needed when casting depending on implementation
	 */
    AUDIO_PASSTHROUGH,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * Returns: List<ButtonCapabilities> <br>
	 * Note: @SuppressWarnings("unchecked") may be needed when casting depending on implementation
	 */
    BUTTON,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * Returns: Object that can be cast to HmiZoneCapabilities
	 */
    HMI_ZONE,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * Returns: Object that can be cast to PresetBankCapabilities
	 */
    PRESET_BANK,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * Returns: List<SoftButtonCapabilities> <br>
	 * Note: @SuppressWarnings("unchecked") may be needed when casting depending on implementation
	 */
    SOFTBUTTON,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * Returns: Object that can be cast to SpeechCapabilities
	 */
    SPEECH;

    public static SystemCapabilityType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
