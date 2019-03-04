package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class StationIDNumber extends RPCStruct {
	public static final String KEY_COUNTRY_CODE = "countryCode";
	public static final String KEY_FCC_FACILITY_ID = "fccFacilityId";

	public StationIDNumber() {
	}

	public StationIDNumber(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Sets the countryCode portion of the StationIDNumber class
	 *
	 * @param countryCode Binary Representation of ITU Country Code. USA Code is 001.
	 */
	public void setCountryCode(Integer countryCode) {
		setValue(KEY_COUNTRY_CODE, countryCode);
	}

	/**
	 * Gets the countryCode portion of the StationIDNumber class
	 *
	 * @return Integer - Binary Representation of ITU Country Code. USA Code is 001.
	 */
	public Integer getCountryCode() {
		return getInteger(KEY_COUNTRY_CODE);
	}

	/**
	 * Sets the fccFacilityId portion of the StationIDNumber class
	 *
	 * @param fccFacilityId Binary representation  of unique facility ID assigned by the FCC; FCC controlled for U.S. territory.
	 */
	public void setFccFacilityId(Integer fccFacilityId) {
		setValue(KEY_FCC_FACILITY_ID, fccFacilityId);
	}

	/**
	 * Gets the fccFacilityId portion of the StationIDNumber class
	 *
	 * @return Integer - Binary representation  of unique facility ID assigned by the FCC; FCC controlled for U.S. territory.
	 */
	public Integer getFccFacilityId() {
		return getInteger(KEY_FCC_FACILITY_ID);
	}

}
