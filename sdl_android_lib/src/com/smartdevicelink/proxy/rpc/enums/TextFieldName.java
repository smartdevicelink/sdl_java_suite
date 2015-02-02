package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

public enum TextFieldName implements JsonName{
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
    
    ;

    public static TextFieldName valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static TextFieldName valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }

        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.name();
        }
    }
}
