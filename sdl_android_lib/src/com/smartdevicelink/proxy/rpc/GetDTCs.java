package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;

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

	/**
	 * Constructs a new GetDTCs object
	 */
    public GetDTCs() {
        super("GetDTCs");
    }

	/**
	 * Constructs a new GetDTCs object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public GetDTCs(Hashtable hash) {
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
    		parameters.put(Names.ecuName, ecuName);
    	} else {
    		parameters.remove(Names.ecuName);
    	}
    }

	/**
	 * Gets a name of the module to receive the DTC form
	 * 
	 * @return Integer -an Integer value representing a name of the module to
	 *         receive the DTC form
	 */
    public Integer getEcuName() {
    	return (Integer) parameters.get(Names.ecuName);
    }
    public void setDtcMask(Integer dtcMask) {
    	if (dtcMask != null) {
    		parameters.put(Names.dtcMask, dtcMask);
    	} else {
    		parameters.remove(Names.dtcMask);
    	}
    }
    public Integer getDtcMask() {
    	return (Integer) parameters.get(Names.dtcMask);
    }
}
