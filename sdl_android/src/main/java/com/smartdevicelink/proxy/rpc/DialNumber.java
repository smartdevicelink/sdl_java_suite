package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Dials a phone number and switches to phone application.
 *
 * @since SmartDeviceLink 4.0
 */
public class DialNumber extends RPCRequest {
	public static final String KEY_NUMBER = "number";


	public DialNumber(){
        super(FunctionID.DIAL_NUMBER.toString());
	}
	
	public DialNumber(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Sets a number to dial
	 * 
	 * @param number
	 *             a phone number is a string, which can be up to 40 chars.
	 *            <p>
	 *            <b>Notes: </b>Maxlength=40</p>
	 *             All characters shall be stripped from string except digits 0-9 and * # , ; +
	 */
    public void setNumber(String number) {
        if (number != null) {
        	number = number.replaceAll("[^0-9*#,;+]", ""); //This will sanitize the input
            parameters.put(KEY_NUMBER, number);
        } else {
        	parameters.remove(KEY_NUMBER);
        }
    }

	/**
	 * Gets a number to dial
	 * 
	 * @return String - a String value representing a number to dial
	 */
    public String getNumber() {
        return (String) parameters.get(KEY_NUMBER);
    }
}
