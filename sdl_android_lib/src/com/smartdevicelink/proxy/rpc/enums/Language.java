package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Specifies the language to be used for TTS, VR, displayed messages/menus
 * <p>
 * 
 * @since SmartDeviceLink 1.0
 *
 */
public enum Language implements JsonName{
    EN_US("EN-US"),
    ES_MX("ES-MX"),
    FR_CA("FR-CA"),
    DE_DE("DE-DE"),
    ES_ES("ES-ES"),
    EN_GB("EN-GB"),
    RU_RU("RU-RU"),
    TR_TR("TR-TR"),
    PL_PL("PL-PL"),
    FR_FR("FR-FR"),
    IT_IT("IT-IT"),
    SV_SE("SV-SE"),
    PT_PT("PT-PT"),
    NL_NL("NL-NL"),
    EN_AU("EN-AU"),
    ZH_CN("ZH-CN"),
    ZH_TW("ZH-TW"),
    JA_JP("JA-JP"),
    AR_SA("AR-SA"),
    KO_KR("KO-KR"),
    PT_BR("PT-BR"),
    CS_CZ("CS-CZ"),
    DA_DK("DA-DK"),
    NO_NO("NO-NO"),
    
    ;

    String internalName;
    
    private Language(String internalName) {
        this.internalName = internalName;
    }
    /**
     * Returns a String representing a kind of Language
     */
    public String toString() {
        return this.internalName;
    }
    
    /**
     * Returns a Language's name
     * @param value a String
     * @return Language -EN-US, ES-MX or FR-CA
     */
    public static Language valueForString(String value) {       	
    	for (Language anEnum : EnumSet.allOf(Language.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
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
    public static Language valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }
        
        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }
}
