package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Non periodic vehicle data read request. This is an RPC to get diagnostics
 * data from certain vehicle modules. DIDs of a certain module might differ from
 * vehicle type to vehicle type
 * <p>
 * Function Group: ProprietaryData
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 */
public class ReadDID extends RPCRequest {
	public static final String KEY_ECU_NAME = "ecuName";
	public static final String KEY_DID_LOCATION = "didLocation";

	/**
	 * Constructs a new ReadDID object
	 */
    public ReadDID() {
        super(FunctionID.READ_DID.toString());
    }

	/**
	 * Constructs a new ReadDID object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ReadDID(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Sets an ID of the vehicle module
	 * 
	 * @param ecuName
	 *            an Integer value representing the ID of the vehicle module
	 *            <p>
	 *            <b>Notes: </b>Minvalue:0; Maxvalue:65535
	 */
    public void setEcuName(Integer ecuName) {
    	if (ecuName != null) {
    		parameters.put(KEY_ECU_NAME, ecuName);
    	} else {
    		parameters.remove(KEY_ECU_NAME);
    	}
    }

	/**
	 * Gets the ID of the vehicle module
	 * 
	 * @return Integer -an Integer value representing the ID of the vehicle
	 *         module
	 */
    public Integer getEcuName() {
    	return (Integer) parameters.get(KEY_ECU_NAME);
    }

	/**
	 * Sets raw data from vehicle data DID location(s)
	 * 
	 * @param didLocation
	 *            a List<Integer> value representing raw data from vehicle
	 *            data DID location(s)
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Minvalue:0; Maxvalue:65535</li>
	 *            <li>ArrayMin:0; ArrayMax:1000</li>
	 *            </ul>
	 */
    public void setDidLocation(List<Integer> didLocation) {
    	if (didLocation != null) {
    		parameters.put(KEY_DID_LOCATION, didLocation);
    	} else {
    		parameters.remove(KEY_DID_LOCATION);
    	}
    }

	/**
	 * Gets raw data from vehicle data DID location(s)
	 * 
	 * @return List<Integer> -a List<Integer> value representing raw data
	 *         from vehicle data DID location(s)
	 */
    @SuppressWarnings("unchecked")
    public List<Integer> getDidLocation() {
        if (parameters.get(KEY_DID_LOCATION) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_DID_LOCATION);
        	if (list != null && list.size() > 0) {
        		Object obj = list.get(0);
        		if (obj instanceof Integer) {
                	return (List<Integer>) list;
        		}
        	}
        }
        return null;
    }
}
