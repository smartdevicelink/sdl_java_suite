package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

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

	public DialNumber(@NonNull String number){
		this();
		setNumber(number);
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
    public void setNumber(@NonNull String number) {
        if (number != null) {
        	number = number.replaceAll("[^0-9*#,;+]", ""); //This will sanitize the input
        }
		setParameters(KEY_NUMBER, number);
    }

	/**
	 * Gets a number to dial
	 * 
	 * @return String - a String value representing a number to dial
	 */
    public String getNumber() {
        return getString(KEY_NUMBER);
    }
}
