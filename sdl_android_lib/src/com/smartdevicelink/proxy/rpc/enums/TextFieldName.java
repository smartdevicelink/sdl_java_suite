package com.smartdevicelink.proxy.rpc.enums;

public enum TextFieldName {
    MAIN_FIELD_1,
    MAIN_FIELD_2,
    MAIN_FIELD_3,
    MAIN_FIELD_4,
    STATUS_BAR,
    MEDIA_CLOCK,
    MEDIA_TRACK,
    ALERT_TEXT_1,
    ALERT_TEXT_2,
    ALERT_TEXT_3,
    SCROLLABLE_MESSAGE_BODY,
    INITIAL_INTERACTION_TEST,
    NAVIGATION_TEXT_1,
    NAVIGATION_TEXT_2,
    ETA,
    TOTAL_DISTANCE,
    AUDIO_PASS_THRU_DISPLAY_TEXT_1,
    AUDIO_PASS_THRU_DISPLAY_TEXT_2,
    SLIDER_HEADER,
    SLIDER_FOOTER,
    MENU_NAME,
    SECONDARY_TEXT,
    TERTIARY_TEXT,
    MENU_TITLE,
    /**
     * Optional name / title of intended location for SendLocation.
     * 
     * @since SmartDeviceLink 4.0
     */
    LOCATION_NAME,
    /**
     * Optional description of intended location / establishment (if applicable) for SendLocation
     * 
     * @since SmartDeviceLink 4.0
     */
    LOCATION_DESCRIPTION,
    /**
     * Optional location address (if applicable) for SendLocation.
     * 
     * @since SmartDeviceLink 4.0
     */
    ADDRESS_LINES,
    /**
     * Optional hone number of intended location / establishment (if applicable) for SendLocation.
     * 
     * @since SmartDeviceLink 4.0
     */
    PHONE_NUMBER,
    ;

    public static TextFieldName valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
