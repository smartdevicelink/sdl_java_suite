package com.smartdevicelink.proxy.rpc.enums;
/** Describes the carmode the vehicle is in.
 * @see SoftButtonCapabilities
 * @see ButtonCapabilities
 * @see OnButtonPress
 * @since SmartDeviceLink 2.0
 */

public enum CarModeStatus {
	/** Provides carmode NORMAL to each module.
	 */

	NORMAL,
	/** Provides carmode FACTORY to each module.
	 */

	FACTORY,
	/** Provides carmode TRANSPORT to each module.
	 */

	TRANSPORT,
	/** Provides carmode CRASH to each module.
	 */


	CRASH;
	/** Convert String to CarModeStatus
	 * @param value String
	 * @return CarModeStatus
	 */

    public static CarModeStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
