package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * This RPC allows to request diagnostic module trouble codes from a certain
 * vehicle module
 * <p>
 * Function Group: ProprietaryData
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * <p>
 */
public class GetDTCs extends RPCRequest {
	public static final String KEY_DTC_MASK = "dtcMask";
	public static final String KEY_ECU_NAME = "ecuName";

	/**
	 * Constructs a new GetDTCs object
	 */
    public GetDTCs() {
        super(FunctionID.GET_DTCS.toString());
    }

	/**
	 * Constructs a new GetDTCs object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public GetDTCs(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Sets a name of the module to receive the DTC form
	 * 
	 * @param ecuName
	 *            an Integer value representing a name of the module to receive
	 *            the DTC form
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
	 * Gets a name of the module to receive the DTC form
	 * 
	 * @return Integer -an Integer value representing a name of the module to
	 *         receive the DTC form
	 */
    public Integer getEcuName() {
    	return (Integer) parameters.get(KEY_ECU_NAME);
    }
    public void setDtcMask(Integer dtcMask) {
    	if (dtcMask != null) {
    		parameters.put(KEY_DTC_MASK, dtcMask);
    	} else {
    		parameters.remove(KEY_DTC_MASK);
    	}
    }
    public Integer getDtcMask() {
    	return (Integer) parameters.get(KEY_DTC_MASK);
    }
}
