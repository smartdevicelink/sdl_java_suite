package com.smartdevicelink.proxy.rpc.enums;

/**
 * Indicates the format of the time displayed on the connected SDL unit.Format
 * description follows the following nomenclature:<p> Sp = Space</p> <p>| = or </p><p>c =
 * character</p>
 * 
 * @since SmartDeviceLink 1.0
 */
public enum MediaClockFormat {
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>maxHours = 19</li>
	 * <li>maxMinutes = 59</li>
	 * <li>maxSeconds = 59</li>
	 * </ul>
	 * 
	 * @since SmartDeviceLink 1.0
	 * 
	 */
	CLOCK1,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>maxHours = 59</li>
	 * <li>maxMinutes = 59</li>
	 * <li>maxSeconds = 59</li>
	 * </ul>
	 * 
	 * @since SmartDeviceLink 1.0
	 * 
	 */    
	CLOCK2,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>maxHours = 9</li>
	 * <li>maxMinutes = 59</li>
	 * <li>maxSeconds = 59</li>
	 * </ul>
	 * 
	 * @since SmartDeviceLink 2.0
	 * 
	 */
    CLOCK3,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>5 characters possible</li>
	 * <li>Format: 1|sp c :|sp c c</li>
	 * <li>1|sp : digit "1" or space</li>
	 * <li>c : character out of following character set: sp|0-9|[letters, see
	 * TypeII column in XLS.</li>
	 * <li>:|sp : colon or space</li>
	 * <li>used for Type II headunit</li>
	 * </ul>
	 * 
	 * @since SmartDeviceLink 1.0
	 */    
    CLOCKTEXT1,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>5 characters possible</li>
	 * <li>Format: 1|sp c :|sp c c</li>
	 * <li>1|sp : digit "1" or space</li>
	 * <li>c : character out of following character set: sp|0-9|[letters, see
	 * CID column in XLS.</li>
	 * <li>:|sp : colon or space</li>
	 * <li>used for CID headunit</li>
	 * </ul>
	 * difference between CLOCKTEXT1 and CLOCKTEXT2 is the supported character
	 * set
	 * 
	 * @since SmartDeviceLink 1.0
	 */    
    CLOCKTEXT2,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>6 chars possible</li>
	 * <li>Format: 1|sp c c :|sp c c</li>
	 * <li>1|sp : digit "1" or space</li>
	 * <li>c : character out of following character set: sp|0-9|[letters, see
	 * Type 5 column in XLS].</li>
	 * <li>:|sp : colon or space</li>
	 * <li>used for Type V headunit</li>
	 * </ul>
	 * difference between CLOCKTEXT1 and CLOCKTEXT2 is the supported character
	 * set
	 * 
	 * 
	 * @since SmartDeviceLink 1.0
	 */    
    CLOCKTEXT3,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>6 chars possible</li>
	 * <li>Format:      c   :|sp   c   c   :   c   c</li>
	 * <li>:|sp : colon or space</li>
	 * <li>c    : character out of following character set: sp|0-9|[letters]</li>
	 * <li>used for MFD3/4/5 headunits</li>
	 * </ul>
	 * 
	 * 
	 * @since SmartDeviceLink 2.0
	 */
    CLOCKTEXT4;

    public static MediaClockFormat valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
