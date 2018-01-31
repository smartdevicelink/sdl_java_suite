package com.smartdevicelink.proxy.rpc.enums;

/**
 * <p>The SystemCapabilityType indicates which type of capability information exists in a SystemCapability struct.</p>
 *
 * <p><b>Enum List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Enum Name</th>
 * 			<th>Return Type</th>
 * 			<th>Description</th>
 * 			<th>Requires Async?</th>
 * 			<th>Notes</th>
 * 		</tr>
 * 		<tr>
 * 			<td>NAVIGATION</td>
 * 			<td>NavigationCapability</td>
 * 			<td>Returns Navigation Capabilities</td>
 * 			<td align=center>Y</td>
 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
 * 		</tr>
 * 		<tr>
 * 			<td>PHONE_CALL</td>
 * 			<td>PhoneCapability</td>
 * 			<td>Returns Phone Capabilities</td>
 * 			<td align=center>Y</td>
 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
 * 		</tr>
 * 		<tr>
 * 			<td>VIDEO_STREAMING</td>
 * 			<td>VideoStreamingCapability</td>
 * 			<td>Returns Video Streaming Capabilities</td>
 * 			<td align=center>Y</td>
 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
 * 		</tr>
 * 		<tr>
 * 			<td>REMOTE_CONTROL</td>
 * 			<td>RemoteControlCapabilities</td>
 * 			<td>Returns Remote Control Capabilities</td>
 * 			<td align=center>Y</td>
 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
 * 		</tr>
 * 		<tr>
 * 			<td>HMI</td>
 * 			<td>HMICapabilities</td>
 * 			<td>Returns HMI Capabilities</td>
 * 			<td align=center>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
 * 		</tr>
 * 		<tr>
 * 			<td>DISPLAY</td>
 * 			<td>DisplayCapabilities</td>
 * 			<td>Returns Display Capabilities</td>
 * 			<td align=center>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
 * 		</tr>
 * 		<tr>
 * 			<td>AUDIO_PASSTHROUGH</td>
 * 			<td>List<AudioPassThruCapabilities></td>
 * 			<td>Returns a List of AudioPassThruCapabilities</td>
 * 			<td align=center>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response. @SuppressWarnings("unchecked") may be needed when casting depending on implementation</td>
 * 		</tr>
 * 		<tr>
 * 			<td>PCM_STREAMING</td>
 * 			<td>AudioPassThruCapabilities</td>
 * 			<td>Returns an AudioPassThruCapabilities Object</td>
 * 			<td align=center>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response. @SuppressWarnings("unchecked") may be needed when casting depending on implementation</td>
 * 		</tr>
 * 		<tr>
 * 			<td>BUTTON</td>
 * 			<td>List<ButtonCapabilities></td>
 * 			<td>Returns a List of ButtonCapabilities</td>
 * 			<td align=center>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response. @SuppressWarnings("unchecked") may be needed when casting depending on implementation</td>
 * 		</tr>
 * 		<tr>
 * 			<td>HMI_ZONE</td>
 * 			<td>HmiZoneCapabilities</td>
 * 			<td>Returns HmiZone Capabilities</td>
 * 			<td align=center>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
 * 		</tr>
 * 		<tr>
 * 			<td>PRESET_BANK</td>
 * 			<td>PresetBankCapabilities</td>
 * 			<td>Returns PresetBank Capabilities</td>
 * 			<td align=center>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
 * 		</tr>
 * 		<tr>
 * 			<td>SOFTBUTTON</td>
 * 			<td>List<SoftButtonCapabilities></td>
 * 			<td>Returns a List of SoftButtonCapabilities</td>
 * 			<td align=center>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response. @SuppressWarnings("unchecked") may be needed when casting depending on implementation</td>
 * 		</tr>
 * 		<tr>
 * 			<td>SPEECH</td>
 * 			<td>SpeechCapabilities</td>
 * 			<td>Returns Speech Capabilities</td>
 * 			<td align=center>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
 * 		</tr>
 * 		<tr>
 * 			<td>VOICE_RECOGNITION</td>
 * 			<td>VrCapabilities</td>
 * 			<td>Returns VOICE_RECOGNITION</td>
 * 			<td align=center>N</td>
 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
 * 		</tr>
 * 	</table>
 *
 */

public enum SystemCapabilityType {

	/**
	 * <strong>Requires</strong> initial asynchronous call, then available synchronously after successful call. <br>
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>NAVIGATION</td>
	 * 			<td>NavigationCapability</td>
	 * 			<td>Returns Navigation Capabilities</td>
	 * 			<td align=center>Y</td>
	 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
	 * 		</tr>
	 * 	</table>
	 */
    NAVIGATION,

	/**
	 * <strong>Requires</strong> initial asynchronous call, then available synchronously after successful call. <br>
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>PHONE_CALL</td>
	 * 			<td>PhoneCapability</td>
	 * 			<td>Returns Phone Capabilities</td>
	 * 			<td align=center>Y</td>
	 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
	 * 		</tr>
	 * 	</table>
	 */
    PHONE_CALL,

	/**
	 * <strong>Requires</strong> initial asynchronous call, then available synchronously after successful call. <br>
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>VIDEO_STREAMING</td>
	 * 			<td>VideoStreamingCapability</td>
	 * 			<td>Returns Video Streaming Capabilities</td>
	 * 			<td align=center>Y</td>
	 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
	 * 		</tr>
	 * 	</table>
	 */
    VIDEO_STREAMING,

	/**
	 * <strong>Requires</strong> initial asynchronous call, then available synchronously after successful call. <br>
	 * 	<table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 			<td>REMOTE_CONTROL</td>
	 * 			<td>RemoteControlCapabilities</td>
	 * 			<td>Returns Remote Control Capabilities</td>
	 * 			<td align=center>Y</td>
	 * 			<td>Call is synchronous <strong>after</strong> initial call</td>
	 * 		</tr>
	 * 	</table>
	 *
	 */
    REMOTE_CONTROL,

    /* These below are not part of the RPC spec. Only for Internal Proxy use */

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * 	 <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 *		<tr>
	 * 			<td>HMI</td>
	 * 			<td>HMICapabilities</td>
	 * 			<td>Returns HMI Capabilities</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
	 * 		</tr>
	 * 	</table>
	 */
    HMI,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * Returns: DisplayCapabilities
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>DISPLAY</td>
	 * 			<td>DisplayCapabilities</td>
	 * 			<td>Returns Display Capabilities</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response received</td>
	 * 		</tr>
	 * 	</table>
	 */
    DISPLAY,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * <b>Note:</b> @SuppressWarnings("unchecked") may be needed when casting depending on implementation
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>AUDIO_PASSTHROUGH</td>
	 * 			<td>List<AudioPassThruCapabilities></td>
	 * 			<td>Returns a List of AudioPassThruCapabilities</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response. @SuppressWarnings("unchecked") may be needed when casting depending on implementation</td>
	 * 		</tr>
	 * 	</table>
	 */
    AUDIO_PASSTHROUGH,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * <b>Note:</b> @SuppressWarnings("unchecked") may be needed when casting depending on implementation
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>PCM_STREAMING</td>
	 * 			<td>AudioPassThruCapabilities</td>
	 * 			<td>Returns a AudioPassThruCapabilities Object</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response. @SuppressWarnings("unchecked") may be needed when casting depending on implementation</td>
	 * 		</tr>
	 * 	</table>
	 */
	PCM_STREAMING,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * <b>Note:</b> @SuppressWarnings("unchecked") may be needed when casting depending on implementation
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>BUTTON</td>
	 * 			<td>List<ButtonCapabilities></td>
	 * 			<td>Returns a List of ButtonCapabilities</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response. @SuppressWarnings("unchecked") may be needed when casting depending on implementation</td>
	 * 		</tr>
	 * 	</table>
	 */
    BUTTON,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>HMI_ZONE</td>
	 * 			<td>HmiZoneCapabilities</td>
	 * 			<td>Returns HmiZone Capabilities</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
	 * 		</tr>
	 * 	</table>
	 */
    HMI_ZONE,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>PRESET_BANK</td>
	 * 			<td>PresetBankCapabilities</td>
	 * 			<td>Returns PresetBank Capabilities</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
	 * 		</tr>
	 * 	</table>
	 */
    PRESET_BANK,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * Returns: List<SoftButtonCapabilities> <br>
	 * Note: @SuppressWarnings("unchecked") may be needed when casting depending on implementation
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>SOFTBUTTON</td>
	 * 			<td>List<SoftButtonCapabilities></td>
	 * 			<td>Returns a List of SoftButtonCapabilities</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response. @SuppressWarnings("unchecked") may be needed when casting depending on implementation</td>
	 * 		</tr>
	 * 	</table>
	 */
    SOFTBUTTON,

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>SPEECH</td>
	 * 			<td>SpeechCapabilities</td>
	 * 			<td>Returns Speech Capabilities</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
	 * 		</tr>
	 * 	</table>
	 */
    SPEECH,
	/**
	 * Available Synchronously after Register App Interface response <br>
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>VOICE_RECOGNITION</td>
	 * 			<td>VrCapabilities</td>
	 * 			<td>Returns VOICE_RECOGNITION</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response</td>
	 * 		</tr>
	 * 	</table>
	 */
    VOICE_RECOGNITION,

	;

    public static SystemCapabilityType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
