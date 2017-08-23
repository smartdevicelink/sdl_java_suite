package com.smartdevicelink.proxy.rpc.enums;

/**
 * Defines the metadata types that can be applied to text fields
 *
 */
public enum MetadataType {

	/**
	 * The data in this field contains the title of the currently playing audio track.
	 */
	mediaTitle,

	/**
	 * The data in this field contains the artist or creator of the currently playing audio track.
	 */
	mediaArtist,

	/**
	 * The data in this field contains the album title of the currently playing audio track.
	 */
	mediaAlbum,

	/**
	 * The data in this field contains the creation year of the currently playing audio track.
	 */
	mediaYear,

	/**
	 * The data in this field contains the genre of the currently playing audio track.
	 */
	mediaGenre,

	/**
	 * The data in this field contains the name of the current source for the media.
	 */
	mediaStation,

	/**
	 * The data in this field is a rating.
	 */
	rating,

	/**
	 * The data in this field is the current temperature.
	 */
	currentTemperature,

	/**
	 * The data in this field is the maximum temperature for the day.
	 */
	maximumTemperature,

	/**
	 * The data in this field is the minimum temperature for the day.
	 */
	minimumTemperature,

	/**
	 * The data in this field describes the current weather (ex. cloudy, clear, etc.).
	 */
	weatherTerm,

	/**
	 * The data in this field describes the current humidity value.
	 */
	humidity;

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
