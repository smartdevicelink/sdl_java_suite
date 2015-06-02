package com.smartdevicelink.proxy.rpc.enums;
/** Reflects the status of the Restraints Control Module fuel pump cutoff.<br> The fuel pump is cut off typically after the vehicle has had a collision.
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 *
 */
public enum FuelCutoffStatus {
	/** Fuel is cut off
	 */
	TERMINATE_FUEL,
	/** Fuel is not cut off
	 * 
	 */
	NORMAL_OPERATION,
	/** Status of the fuel pump cannot be determined
	 * 
	 */
    FAULT;
	/**
     * Convert String to FuelCutoffStatus
     * @param value String
     * @return FuelCuttoffStatus
     */
    public static FuelCutoffStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
