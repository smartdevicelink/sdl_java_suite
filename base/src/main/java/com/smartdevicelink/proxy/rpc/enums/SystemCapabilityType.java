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
 *  	<tr>
 * 			<td>APP_SERVICES</td>
 * 			<td>AppServicesCapabilities</td>
 * 			<td>Returns APP_SERVICES</td>
 * 			<td align=center>N</td>
 * 			<td>Available Asynchronously, Call is synchronous <strong>after</strong> initial call</td>
 * 		</tr>
 * 	   <tr>
 * 	       <td>SEAT_LOCATION</td>
 * 	       <td>SeatLocationCapability</td>
 * 	       <td>Returns SEAT_LOCATION</td>
 * 	       <td align=center>N</td>
 * 	       <td>Available Asynchronously, Call is synchronous <strong>after</strong> initial call</strong></td>
 * 	   </tr>
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
    NAVIGATION (true),

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
    PHONE_CALL (true),

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
    VIDEO_STREAMING (true),

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
    REMOTE_CONTROL (true),

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
    HMI (false),

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
	 * 			<td> Available Synchronously <strong>after</strong> Register App Interface response received</td>
	 * 		</tr>
	 * 	</table>
	 */
	@Deprecated
	DISPLAY (false),


	/**
	 * @since 6.0
	 * Returns: List<DisplayCapability>
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>DISPLAYS</td>
	 * 			<td>List<DisplayCapability></td>
	 * 			<td>Returns a list of Display Capability</td>
	 * 			<td align=center>Y</td>
	 * 			<td> <strong>Since 6.0</strong> Contain the display related information and all windows related to that display.</td>
	 * 		</tr>
	 * 	</table>
	 */
	DISPLAYS (true),

	/**
	 * Available Synchronously after Register App Interface response <br>
	 * Returns: List<PrerecordedSpeech>
	 * <table border="1" rules="all">
	 * 		<tr>
	 * 			<th>Enum Name</th>
	 * 			<th>Return Type</th>
	 * 			<th>Description</th>
	 * 			<th>Requires Async?</th>
	 * 			<th>Notes</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>PRERECORDED_SPEECH</td>
	 * 			<td>prerecordedSpeechCapabilities</td>
	 * 			<td>Returns List<PrerecordedSpeech></td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Synchronously <strong>after</strong> Register App Interface response received</td>
	 * 		</tr>
	 * 	</table>
	 */
	PRERECORDED_SPEECH (false),

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
    AUDIO_PASSTHROUGH (false),

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
	PCM_STREAMING (false),

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
	@Deprecated
    BUTTON (false),

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
    HMI_ZONE (false),

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
	@Deprecated
    PRESET_BANK (false),

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
	@Deprecated
    SOFTBUTTON (false),

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
    SPEECH (false),
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
    VOICE_RECOGNITION (false),
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
	 * 			<td>APP_SERVICES</td>
	 * 			<td>AppServicesCapabilities</td>
	 * 			<td>Returns APP_SERVICES</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Asynchronously, Call is synchronous <strong>after</strong> initial call</td>
	 * 		</tr>
	 * 	</table>
	 */
	APP_SERVICES (true),

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
	 * 			<td>SEAT_LOCATION</td>
	 * 			<td>SeatLocationCapability</td>
	 * 			<td>Returns SEAT_LOCATION</td>
	 * 			<td align=center>N</td>
	 * 			<td>Available Asynchronously, Call is synchronous <strong>after</strong> initial call</td>
	 * 		</tr>
	 * 	</table>
	 */
	SEAT_LOCATION (true),

	;

    boolean IS_QUERYABLE;

    SystemCapabilityType(boolean isQueryable) {
        this.IS_QUERYABLE = isQueryable;
    }

    public boolean isQueryable() {
        return IS_QUERYABLE;
    }

    public static SystemCapabilityType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
