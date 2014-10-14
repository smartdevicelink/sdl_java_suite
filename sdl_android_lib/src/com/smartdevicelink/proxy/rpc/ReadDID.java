package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

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
	public static final String ecuName = "ecuName";
	public static final String didLocation = "didLocation";

	/**
	 * Constructs a new ReadDID object
	 */
    public ReadDID() {
        super("ReadDID");
    }

	/**
	 * Constructs a new ReadDID object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ReadDID(Hashtable hash) {
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
    		parameters.put(ReadDID.ecuName, ecuName);
    	} else {
    		parameters.remove(ReadDID.ecuName);
    	}
    }

	/**
	 * Gets the ID of the vehicle module
	 * 
	 * @return Integer -an Integer value representing the ID of the vehicle
	 *         module
	 */
    public Integer getEcuName() {
    	return (Integer) parameters.get(ReadDID.ecuName);
    }

	/**
	 * Sets raw data from vehicle data DID location(s)
	 * 
	 * @param didLocation
	 *            a Vector<Integer> value representing raw data from vehicle
	 *            data DID location(s)
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Minvalue:0; Maxvalue:65535</li>
	 *            <li>ArrayMin:0; ArrayMax:1000</li>
	 *            </ul>
	 */
    public void setDidLocation(Vector<Integer> didLocation) {
    	if (didLocation != null) {
    		parameters.put(ReadDID.didLocation, didLocation);
    	} else {
    		parameters.remove(ReadDID.didLocation);
    	}
    }

	/**
	 * Gets raw data from vehicle data DID location(s)
	 * 
	 * @return Vector<Integer> -a Vector<Integer> value representing raw data
	 *         from vehicle data DID location(s)
	 */
    public Vector<Integer> getDidLocation() {
        if (parameters.get(ReadDID.didLocation) instanceof Vector<?>) {
        	Vector<?> list = (Vector<?>)parameters.get(ReadDID.didLocation);
        	if (list != null && list.size() > 0) {
        		Object obj = list.get(0);
        		if (obj instanceof Integer) {
                	return (Vector<Integer>) list;        			
        		}
        	}
        }
        return null;
    }
}
