package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

public enum DisplayType implements JsonName{
    CID("CID"),
    TYPE2("TYPE2"),
    TYPE5("TYPE5"),
    NGN("NGN"),
    GEN2_8_DMA("GEN2_8_DMA"),
    GEN2_6_DMA("GEN2_6_DMA"),
    MFD3("MFD3"),
    MFD4("MFD4"),
    MFD5("MFD5"),
    GEN3_8_INCH("GEN3_8-INCH"),
    
    ;

    final String internalName;

    private DisplayType(String internalName) {
        this.internalName = internalName;
    }

    public static DisplayType valueForString(String value) {
        for (DisplayType type : DisplayType.values()) {
            if (type.toString().equals(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown value " + value);
    }

    @Override
    public String toString() {
        return internalName;
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.internalName;
        }
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static DisplayType valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }
        
        switch(sdlVersion){
        default:
            try{
                return valueForString(name);
            } catch(IllegalArgumentException e){
                // TODO: this is a work-around for a bug where valueForString throws a runtime exception instead of returning null
                return null;
            }
        }
    }
}
