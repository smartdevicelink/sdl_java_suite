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
     * Optional hone number of intended location / establishment (if applicable) for SendLocation.
     * 
     * @since SmartDeviceLink 4.0
     */
    phoneNumber,
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
