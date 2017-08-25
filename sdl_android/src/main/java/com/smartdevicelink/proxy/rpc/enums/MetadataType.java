package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * Defines the metadata types that can be applied to text fields
 *
 */
public enum MetadataType {

	/**
	 * The data in this field contains the title of the currently playing audio track.
	 */
	MEDIA_TITLE("mediaTitle"),

	/**
	 * The data in this field contains the artist or creator of the currently playing audio track.
	 */
	MEDIA_ARTIST("mediaArtist"),

	/**
	 * The data in this field contains the album title of the currently playing audio track.
	 */
	MEDIA_ALBUM("mediaAlbum"),

	/**
	 * The data in this field contains the creation year of the currently playing audio track.
	 */
	MEDIA_YEAR("mediaYear"),

	/**
	 * The data in this field contains the genre of the currently playing audio track.
	 */
	MEDIA_GENRE("mediaGenre"),

	/**
	 * The data in this field contains the name of the current source for the media.
	 */
	MEDIA_STATION("mediaStation"),

	/**
	 * The data in this field is a rating.
	 */
	RATING("rating"),

	/**
	 * The data in this field is the current temperature.
	 */
	CURRENT_TEMPERATURE("currentTemperature"),

	/**
	 * The data in this field is the maximum temperature for the day.
	 */
	MAXIMUM_TEMPERATURE("maximumTemperature"),

	/**
	 * The data in this field is the minimum temperature for the day.
	 */
	MINIMUM_TEMPERATURE("minimumTemperature"),

	/**
	 * The data in this field describes the current weather (ex. cloudy, clear, etc.).
	 */
	WEATHER_TERM("weatherTerm"),

	/**
	 * The data in this field describes the current humidity value.
	 */
	HUMIDITY("humidity"),


	;

	private final String internalName;

	private MetadataType(String internalName) {
		this.internalName = internalName;
	}

	public String toString() {
		return this.internalName;
	}

	public static MetadataType valueForString(String value) {
		if(value == null){
			return null;
		}

		for (MetadataType anEnum : EnumSet.allOf(MetadataType.class)) {
			if (anEnum.toString().equals(value)) {
				return anEnum;
			}
		}
		return null;
	}
}
