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

public class MetadataTypeTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "mediaTitle";
        MetadataType enumMediaTitle = MetadataType.valueForString(example);
        example = "mediaArtist";
        MetadataType enumMediaArtist = MetadataType.valueForString(example);
        example = "mediaAlbum";
        MetadataType enumMediaAlbum = MetadataType.valueForString(example);
        example = "mediaYear";
        MetadataType enumMediaYear = MetadataType.valueForString(example);
        example = "mediaGenre";
        MetadataType enumMediaGenre = MetadataType.valueForString(example);
        example = "mediaStation";
        MetadataType enumMediaStation = MetadataType.valueForString(example);
        example = "rating";
        MetadataType enumRating = MetadataType.valueForString(example);
        example = "currentTemperature";
        MetadataType enumCurrentTemperature = MetadataType.valueForString(example);
        example = "maximumTemperature";
        MetadataType enumMaximumTemperature = MetadataType.valueForString(example);
        example = "minimumTemperature";
        MetadataType enumMinimumTemperature = MetadataType.valueForString(example);
        example = "weatherTerm";
        MetadataType enumWeatherTerm = MetadataType.valueForString(example);
        example = "humidity";
        MetadataType enumHumidity = MetadataType.valueForString(example);


        assertNotNull("mediaTitle returned null", enumMediaTitle);
        assertNotNull("mediaArtist returned null", enumMediaArtist);
        assertNotNull("mediaAlbum returned null", enumMediaAlbum);
        assertNotNull("mediaYear returned null", enumMediaYear);
        assertNotNull("mediaGenre returned null", enumMediaGenre);
        assertNotNull("mediaStation returned null", enumMediaStation);
        assertNotNull("rating returned null", enumRating);
        assertNotNull("currentTemperature returned null", enumCurrentTemperature);
        assertNotNull("maximumTemperature returned null", enumMaximumTemperature);
        assertNotNull("minimumTemperature returned null", enumMinimumTemperature);
        assertNotNull("weatherTerm returned null", enumWeatherTerm);
        assertNotNull("humidity returned null", enumHumidity);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "MEDIA_TITLEZ";
        try {
            MetadataType temp = MetadataType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    /**
     * Verifies that a null assignment is invalid.
     */
    public void testNullEnum() {
        String example = null;
        try {
            MetadataType temp = MetadataType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
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
