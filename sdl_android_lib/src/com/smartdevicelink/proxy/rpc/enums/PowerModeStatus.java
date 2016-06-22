package com.smartdevicelink.proxy.rpc.enums;
/** Reflects the status of the current power mode.
 * 
 * @since SmartDeviceLink 2.0
 *
 */
public enum PowerModeStatus {
	/** Key not inserted
	 * 
	 */
    KEY_OUT,
    /** Key is currently out
     * 
     */
    KEY_RECENTLY_OUT,
    
    KEY_APPROVED_0,
    POST_ACCESORY_0,
    /** Key is in accessory positon
     * 
     */

    ACCESORY_1,
    POST_IGNITION_1,
    
    /** Key is in position ignition on
     * 
     */
    IGNITION_ON_2,
    /** Key is in position running
     * 
     */

    RUNNING_2,
    /** Key is in crank position
     * 
     */

    CRANK_3;

    public static PowerModeStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
