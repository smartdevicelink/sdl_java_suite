package com.smartdevicelink.proxy.rpc.enums;

/**
 * Defines the metadata types that can be applied to text fields
 *
 */
public enum MetadataType {

	/**
	 * The data in this field contains the title of the currently playing audio track.
	 */
	MEDIA_TITLE,

	/**
	 * The data in this field contains the artist or creator of the currently playing audio track.
	 */
	MEDIA_ARTIST,

	/**
	 * The data in this field contains the album title of the currently playing audio track.
	 */
	MEDIA_ALBUM,

	/**
	 * The data in this field contains the creation year of the currently playing audio track.
	 */
	MEDIA_YEAR,

	/**
	 * The data in this field contains the genre of the currently playing audio track.
	 */
	MEDIA_GENRE,

	/**
	 * The data in this field contains the name of the current source for the media.
	 */
	MEDIA_STATION,

	/**
	 * The data in this field is a rating.
	 */
	RATING,

	/**
	 * The data in this field is the current temperature.
	 */
	CURRENT_TEMPERATURE,

	/**
	 * The data in this field is the maximum temperature for the day.
	 */
	MAXIMUM_TEMPERATURE,

	/**
	 * The data in this field is the minimum temperature for the day.
	 */
	MINIMUM_TEMPERATURE,

	/**
	 * The data in this field describes the current weather (ex. cloudy, clear, etc.).
	 */
	WEATHER_TERM,

	/**
	 * The data in this field describes the current humidity value.
	 */
	HUMIDITY;

	/**
	 * Convert String to VehicleDataType
	 * @param value String
	 * @return VehicleDataType
	 */
	public static MetadataType valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
	}
}
