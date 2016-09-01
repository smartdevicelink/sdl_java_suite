package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * Specifies the language to be used for TTS, VR, displayed messages/menus
 *
 * 
 * @since SmartDeviceLink 1.0
 *
 */
public enum Language {
	/**
	 * English - US
	 */

    EN_US("EN-US"),
    /**
     * Spanish - Mexico
     */

    ES_MX("ES-MX"),
    /**
     * French - Canada
     */

    FR_CA("FR-CA"),
    /**
     * German - Germany
     */

    DE_DE("DE-DE"),
    /**
     * Spanish - Spain
     */

    ES_ES("ES-ES"),
    /**
     * English - GB
     */

    EN_GB("EN-GB"),
    /**
     * Russian - Russia
     */

    RU_RU("RU-RU"),
    /**
     * Turkish - Turkey
     */

    TR_TR("TR-TR"),
    /**
     * Polish - Poland
     */

    PL_PL("PL-PL"),
    /**
     * French - France
     */

    FR_FR("FR-FR"),
    /**
     * Italian - Italy
     */

    IT_IT("IT-IT"),
    /**
     * Swedish - Sweden
     */

    SV_SE("SV-SE"),
    /**
     * Portuguese - Portugal
     */

    PT_PT("PT-PT"),
    /**
     * Dutch (Standard) - Netherlands
     */

    NL_NL("NL-NL"),
    /**
     * English - Australia
     */

    EN_AU("EN-AU"),
    /**
     * Mandarin - China
     */

    ZH_CN("ZH-CN"),
    /**
     * Mandarin - Taiwan
     */

    ZH_TW("ZH-TW"),
    /**
     * Japanese - Japan
     */

    JA_JP("JA-JP"),
    /**
     * Arabic - Saudi Arabia
     */

    AR_SA("AR-SA"),
    /**
     * Korean - South Korea
     */

    KO_KR("KO-KR"),
    /**
     * Portuguese - Brazil
     */

    PT_BR("PT-BR"),
    /**
     * Czech - Czech Republic
     */

    CS_CZ("CS-CZ"),
    /**
     * Danish - Denmark
     */

    DA_DK("DA-DK"),
    /**
     * Norwegian - Norway
     */

    NO_NO("NO-NO");

    private final String INTERNAL_NAME;
    
    private Language(String internalName) {
        this.INTERNAL_NAME = internalName;
    }
    /**
     * Returns a String representing a kind of Language
     */
    public String toString() {
        return this.INTERNAL_NAME;
    }
    
    /**
     * Returns a Language's name
     * @param value a String
     * @return Language -EN-US, ES-MX or FR-CA
     */
    public static Language valueForString(String value) {
        if(value == null){
            return null;
        }
        
    	for (Language anEnum : EnumSet.allOf(Language.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
