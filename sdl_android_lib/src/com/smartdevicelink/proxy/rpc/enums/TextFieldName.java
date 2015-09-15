package com.smartdevicelink.proxy.rpc.enums;

public enum TextFieldName {
    mainField1,
    mainField2,
    mainField3,
    mainField4,
    statusBar,
    mediaClock,
    mediaTrack,
    alertText1,
    alertText2,
    alertText3,
    scrollableMessageBody,
    initialInteractionText,
    navigationText1,
    navigationText2,
    ETA,
    totalDistance,
    audioPassThruDisplayText1,
    audioPassThruDisplayText2,
    sliderHeader,
    sliderFooter,
    menuName,
    secondaryText,
    tertiaryText,
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

    public static TextFieldName valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
