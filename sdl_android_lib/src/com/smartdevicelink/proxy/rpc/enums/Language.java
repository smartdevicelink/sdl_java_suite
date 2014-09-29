package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * Specifies the language to be used for TTS, VR, displayed messages/menus
 * <p>
 * 
 * @since SmartDeviceLink 1.0
 *
 */
public enum Language {
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
    NO_NO("NO-NO");

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
}
