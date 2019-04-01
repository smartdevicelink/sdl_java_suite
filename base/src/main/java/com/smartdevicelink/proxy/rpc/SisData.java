package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class SisData extends RPCStruct {
	public static final String KEY_STATION_SHORT_NAME = "stationShortName";
	public static final String KEY_STATION_ID_NUMBER = "stationIDNumber";
	public static final String KEY_STATION_LONG_NAME = "stationLongName";
	public static final String KEY_STATION_LOCATION = "stationLocation";
	public static final String KEY_STATION_MESSAGE = "stationMessage";

	/**
	 * Constructs a new SisData object
	 */
	public SisData() {
	}

	/**
	 * <p>Constructs a new SisData object indicated by the Hashtable parameter
	 * </p>
	 *
	 * @param hash The Hashtable to use
	 */
	public SisData(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Sets the stationShortName portion of the SisData class
	 *
	 * @param stationShortName Identifies the 4-alpha-character station call sign plus an optional (-FM) extension.
	 */
	public void setStationShortName(String stationShortName) {
		setValue(KEY_STATION_SHORT_NAME, stationShortName);
	}

	/**
	 * Gets the stationShortName portion of the SisData class
	 *
	 * @return String - Identifies the 4-alpha-character station call sign plus an optional (-FM) extension.
	 */
	public String getStationShortName() {
		return getString(KEY_STATION_SHORT_NAME);
	}

	/**
	 * Sets the stationIDNumber portion of the SisData class
	 *
	 * @param stationIDNumber
	 */
	public void setStationIDNumber(StationIDNumber stationIDNumber) {
		setValue(KEY_STATION_ID_NUMBER, stationIDNumber);
	}

	/**
	 * Gets the stationIDNumber portion of the SisData class
	 *
	 * @return StationIDNumber.
	 */
	@SuppressWarnings("unchecked")
	public StationIDNumber getStationIDNumber() {
		return (StationIDNumber) getObject(StationIDNumber.class, KEY_STATION_ID_NUMBER);
	}

	/**
	 * Sets the stationLongName portion of the SisData class
	 *
	 * @param stationLongName Identifies the station call sign or other identifying information in the long format.
	 */
	public void setStationLongName(String stationLongName) {
		setValue(KEY_STATION_LONG_NAME, stationLongName);
	}

	/**
	 * Gets the stationLongName portion of the SisData class
	 *
	 * @return String - Identifies the station call sign or other identifying information in the long format.
	 */
	public String getStationLongName() {
		return getString(KEY_STATION_LONG_NAME);
	}

	/**
	 * Sets the stationLocation portion of the SisData class
	 *
	 * @param stationLocation Provides the 3-dimensional geographic station location.
	 */
	public void setStationLocation(GPSData stationLocation) {
		setValue(KEY_STATION_LOCATION, stationLocation);
	}

	/**
	 * Gets the stationLocation portion of the SisData class
	 *
	 * @return GPSData - Provides the 3-dimensional geographic station location.
	 */
	@SuppressWarnings("unchecked")
	public GPSData getStationLocation() {
		return (GPSData) getObject(GPSData.class, KEY_STATION_LOCATION);
	}

	/**
	 * Sets the stationMessage portion of the SisData class
	 *
	 * @param stationMessage May be used to convey textual information of general interest to the consumer such as weather forecasts or public service announcements.
	 *                       Includes a high priority delivery feature to convey emergencies that may be in the listening area.
	 */
	public void setStationMessage(String stationMessage) {
		setValue(KEY_STATION_MESSAGE, stationMessage);
	}

	/**
	 * Gets the stationMessage portion of the SisData class
	 *
	 * @return String - May be used to convey textual information of general interest to the consumer such as weather forecasts or public service announcements.
	 * Includes a high priority delivery feature to convey emergencies that may be in the listening area.
	 */
	public String getStationMessage() {
		return getString(KEY_STATION_MESSAGE);
	}
}
