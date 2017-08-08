package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.TextFieldType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.TextFieldName}
 */

public class TextFieldTypeTests extends TestCase{

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {
		String example = "MEDIA_TITLE";
		TextFieldType enumMediaTitle = TextFieldType.valueForString(example);
		example = "MEDIA_ARTIST";
		TextFieldType enumMediaArtist = TextFieldType.valueForString(example);
		example = "MEDIA_ALBUM";
		TextFieldType enumMediaAlbum = TextFieldType.valueForString(example);
		example = "MEDIA_YEAR";
		TextFieldType enumMediaYear = TextFieldType.valueForString(example);
		example = "MEDIA_GENRE";
		TextFieldType enumMediaGenre = TextFieldType.valueForString(example);
		example = "MEDIA_STATION";
		TextFieldType enumMediaStation = TextFieldType.valueForString(example);
		example = "RATING";
		TextFieldType enumRating = TextFieldType.valueForString(example);
		example = "CURRENT_TEMPERATURE";
		TextFieldType enumCurrentTemperature = TextFieldType.valueForString(example);
		example = "MAXIMUM_TEMPERATURE";
		TextFieldType enumMaximumTemperature = TextFieldType.valueForString(example);
		example = "MINIMUM_TEMPERATURE";
		TextFieldType enumMinimumTemperature = TextFieldType.valueForString(example);
		example = "WEATHER_TERM";
		TextFieldType enumWeatherTerm = TextFieldType.valueForString(example);
		example = "HUMIDITY";
		TextFieldType enumHumidity = TextFieldType.valueForString(example);


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
			TextFieldType temp = TextFieldType.valueForString(example);
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
			TextFieldType temp = TextFieldType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of TextFieldType.
	 */
	public void testListEnum() {
		List<TextFieldType> enumValueList = Arrays.asList(TextFieldType.values());

		List<TextFieldType> enumTestList = new ArrayList<TextFieldType>();
		enumTestList.add(TextFieldType.MEDIA_TITLE);
		enumTestList.add(TextFieldType.MEDIA_ARTIST);
		enumTestList.add(TextFieldType.MEDIA_ALBUM);
		enumTestList.add(TextFieldType.MEDIA_YEAR);
		enumTestList.add(TextFieldType.MEDIA_GENRE);
		enumTestList.add(TextFieldType.MEDIA_STATION);
		enumTestList.add(TextFieldType.RATING);
		enumTestList.add(TextFieldType.CURRENT_TEMPERATURE);
		enumTestList.add(TextFieldType.MAXIMUM_TEMPERATURE);
		enumTestList.add(TextFieldType.MINIMUM_TEMPERATURE);
		enumTestList.add(TextFieldType.WEATHER_TERM);
		enumTestList.add(TextFieldType.HUMIDITY);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}
