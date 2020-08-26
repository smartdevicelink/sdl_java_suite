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
/** Names of the text fields that can appear on the display. 
 * 
 * @since SmartDeviceLink 1.0
 *
 */

public enum TextFieldName {
	/** The first line of first set of main fields of the persistent display; applies to "Show"
	 * 
	 */

    mainField1,
	/** The second line of first set of main fields of the persistent display; applies to "Show"
     * 
     */

    mainField2,
    /** The first line of second set of main fields of persistent display; applies to "Show"
     * 
     */

    mainField3,
    /** The second line of second set of main fields of the persistent display; applies to "Show"
     * 
     */

    mainField4,
    /** The status bar on NGN; applies to "Show"
     * 
     */

    statusBar,
    /** Text value for MediaClock field; applies to "Show"
     * 
     */

    mediaClock,
    /** The track field of NGN and GEN1.1 MFD displays. This field is only available for media applications; applies to "Show"
     * 
     */

    mediaTrack,
    /** The first line of the alert text field; applies to "Alert"
     * 
     */

    alertText1,
    /** The second line of the alert text field; applies to "Alert"
     * 
     */

    alertText2,
    /** The third line of the alert text field; applies to "Alert"
     * 
     */

    alertText3,
    /** Long form body of text that can include newlines and tabs; applies to "ScrollableMessage"
     * 
     */

    scrollableMessageBody,
    /** First line suggestion for a user response (in the case of VR enabled interaction)
     * 
     */

    initialInteractionText,
    /**  First line of navigation text
     * 
     */

    navigationText1,
    /** Second line of navigation text
     * 
     */

    navigationText2,
    /** Estimated Time of Arrival time for navigation
     * 
     */

    ETA,
    /** Total distance to destination for navigation
     * 
     */

    totalDistance,
    /** First line of text for audio pass thru
     * 
     */

    audioPassThruDisplayText1,
    /** Second line of text for audio pass thru
     * 
     */

    audioPassThruDisplayText2,
    /** Header text for slider
     * 
     */

    sliderHeader,
    /** Footer text for slider
     * 
     */

    sliderFooter,
    /** Primary text for Choice
     * 
     */

    menuName,
    /** Secondary text for Choice
     * 
     */

    secondaryText,
    /** Tertiary text for Choice
     * 
     */

    tertiaryText,
	/** Optional text to label an app menu button (for certain touchscreen platforms).
	 * 
	 */
    menuTitle,
    /**
     * Optional name / title of intended location for SendLocation.
     * 
     * @since SmartDeviceLink 4.0
     */
    locationName,
    /**
     * Optional description of intended location / establishment (if applicable) for SendLocation
     * 
     * @since SmartDeviceLink 4.0
     */
    locationDescription,
    /**
     * Optional location address (if applicable) for SendLocation.
     * 
     * @since SmartDeviceLink 4.0
     */
    addressLines,
    /**
     * Optional phone number of intended location / establishment (if applicable) for SendLocation.
     * 
     * @since SmartDeviceLink 4.0
     */
    phoneNumber,
    /**
     * Optional title of the template that will be displayed
     *
     * @since SmartDeviceLink 6.0
     */
    templateTitle,
    /**
     * The first line of the subtle alert text field; applies to `SubtleAlert` `alertText1`
     *
     * @since SmartDeviceLink 7.0.0
     */
    subtleAlertText1,
    /**
     * The second line of the subtle alert text field; applies to `SubtleAlert` `alertText2`
     *
     * @since SmartDeviceLink 7.0.0
     */
    subtleAlertText2,
    /**
     * A text field in the soft button of a subtle alert; applies to `SubtleAlert` `softButtons`
     *
     * @since SmartDeviceLink 7.0.0
     */
    subtleAlertSoftButtonText;
    ;
	  /**
       * Convert String to TextFieldName
       * @param value String
       * @return TextFieldName
       */

    public static TextFieldName valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
