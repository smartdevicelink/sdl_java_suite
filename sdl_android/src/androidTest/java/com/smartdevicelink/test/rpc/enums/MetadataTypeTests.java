package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.MetadataType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.MetadataTags}
 */

public class MetadataTypeTests extends TestCase{

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {
		String example = "MEDIA_TITLE";
		MetadataType enumMediaTitle = MetadataType.valueForString(example);
		example = "MEDIA_ARTIST";
		MetadataType enumMediaArtist = MetadataType.valueForString(example);
		example = "MEDIA_ALBUM";
		MetadataType enumMediaAlbum = MetadataType.valueForString(example);
		example = "MEDIA_YEAR";
		MetadataType enumMediaYear = MetadataType.valueForString(example);
		example = "MEDIA_GENRE";
		MetadataType enumMediaGenre = MetadataType.valueForString(example);
		example = "MEDIA_STATION";
		MetadataType enumMediaStation = MetadataType.valueForString(example);
		example = "RATING";
		MetadataType enumRating = MetadataType.valueForString(example);
		example = "CURRENT_TEMPERATURE";
		MetadataType enumCurrentTemperature = MetadataType.valueForString(example);
		example = "MAXIMUM_TEMPERATURE";
		MetadataType enumMaximumTemperature = MetadataType.valueForString(example);
		example = "MINIMUM_TEMPERATURE";
		MetadataType enumMinimumTemperature = MetadataType.valueForString(example);
		example = "WEATHER_TERM";
		MetadataType enumWeatherTerm = MetadataType.valueForString(example);
		example = "HUMIDITY";
		MetadataType enumHumidity = MetadataType.valueForString(example);


		assertNotNull("MEDIA_TITLE returned null", enumMediaTitle);
		assertNotNull("MEDIA_ARTIST returned null", enumMediaArtist);
		assertNotNull("MEDIA_ALBUM returned null", enumMediaAlbum);
		assertNotNull("MEDIA_YEAR returned null", enumMediaYear);
		assertNotNull("MEDIA_GENRE returned null", enumMediaGenre);
		assertNotNull("MEDIA_STATION returned null", enumMediaStation);
		assertNotNull("RATING returned null", enumRating);
		assertNotNull("CURRENT_TEMPERATURE returned null", enumCurrentTemperature);
		assertNotNull("MAXIMUM_TEMPERATURE returned null", enumMaximumTemperature);
		assertNotNull("MINIMUM_TEMPERATURE returned null", enumMinimumTemperature);
		assertNotNull("WEATHER_TERM returned null", enumWeatherTerm);
		assertNotNull("HUMIDITY returned null", enumHumidity);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "MEDIA_TITLEZ";
		try {
			MetadataType temp = MetadataType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
			MetadataType temp = MetadataType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of MetadataType.
	 */
	public void testListEnum() {
		List<MetadataType> enumValueList = Arrays.asList(MetadataType.values());

		List<MetadataType> enumTestList = new ArrayList<MetadataType>();
		enumTestList.add(MetadataType.MEDIA_TITLE);
		enumTestList.add(MetadataType.MEDIA_ARTIST);
		enumTestList.add(MetadataType.MEDIA_ALBUM);
		enumTestList.add(MetadataType.MEDIA_YEAR);
		enumTestList.add(MetadataType.MEDIA_GENRE);
		enumTestList.add(MetadataType.MEDIA_STATION);
		enumTestList.add(MetadataType.RATING);
		enumTestList.add(MetadataType.CURRENT_TEMPERATURE);
		enumTestList.add(MetadataType.MAXIMUM_TEMPERATURE);
		enumTestList.add(MetadataType.MINIMUM_TEMPERATURE);
		enumTestList.add(MetadataType.WEATHER_TERM);
		enumTestList.add(MetadataType.HUMIDITY);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}
