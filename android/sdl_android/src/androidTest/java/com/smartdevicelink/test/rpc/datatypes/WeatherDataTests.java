package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DateTime;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.Temperature;
import com.smartdevicelink.proxy.rpc.WeatherData;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.WeatherData}
 */
public class WeatherDataTests extends TestCase {

    private WeatherData msg;

    @Override
    public void setUp() {

        msg = new WeatherData();
        msg.setCurrentTemperature(TestValues.GENERAL_TEMPERATURE);
        msg.setTemperatureHigh(TestValues.GENERAL_TEMPERATURE);
        msg.setTemperatureLow(TestValues.GENERAL_TEMPERATURE);
        msg.setApparentTemperature(TestValues.GENERAL_TEMPERATURE);
        msg.setApparentTemperatureHigh(TestValues.GENERAL_TEMPERATURE);
        msg.setApparentTemperatureLow(TestValues.GENERAL_TEMPERATURE);
        msg.setWeatherSummary(TestValues.GENERAL_STRING);
        msg.setTime(TestValues.GENERAL_DATETIME);
        msg.setHumidity(TestValues.GENERAL_FLOAT);
        msg.setCloudCover(TestValues.GENERAL_FLOAT);
        msg.setMoonPhase(TestValues.GENERAL_FLOAT);
        msg.setWindBearing(TestValues.GENERAL_INTEGER);
        msg.setWindGust(TestValues.GENERAL_FLOAT);
        msg.setWindSpeed(TestValues.GENERAL_FLOAT);
        msg.setNearestStormBearing(TestValues.GENERAL_INTEGER);
        msg.setNearestStormDistance(TestValues.GENERAL_INTEGER);
        msg.setPrecipAccumulation(TestValues.GENERAL_FLOAT);
        msg.setPrecipIntensity(TestValues.GENERAL_FLOAT);
        msg.setPrecipProbability(TestValues.GENERAL_FLOAT);
        msg.setPrecipType(TestValues.GENERAL_STRING);
        msg.setVisibility(TestValues.GENERAL_FLOAT);
        msg.setWeatherIcon(TestValues.GENERAL_IMAGE);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        Temperature currentTemperature = msg.getCurrentTemperature();
        Temperature temperatureHigh = msg.getTemperatureHigh();
        Temperature temperatureLow = msg.getTemperatureLow();
        Temperature apparentTemperature = msg.getApparentTemperature();
        Temperature apparentTemperatureHigh = msg.getApparentTemperatureHigh();
        String weatherSummary = msg.getWeatherSummary();
        DateTime time = msg.getTime();
        Float humidity = msg.getHumidity();
        Float cloudCover = msg.getCloudCover();
        Float moonPhase = msg.getMoonPhase();
        Integer windBearing = msg.getWindBearing();
        Float windGust = msg.getWindGust();
        Float windSpeed = msg.getWindSpeed();
        Integer nearestStormBearing = msg.getNearestStormBearing();
        Integer nearestStormDistance = msg.getNearestStormDistance();
        Float precipAccumulation = msg.getPrecipAccumulation();
        Float precipIntensity = msg.getPrecipIntensity();
        Float precipProbability = msg.getPrecipProbability();
        String precipType = msg.getPrecipType();
        Float visibility = msg.getVisibility();
        Image weatherIcon = msg.getWeatherIcon();

        // Valid Tests
        assertEquals(TestValues.MATCH, currentTemperature, TestValues.GENERAL_TEMPERATURE);
        assertEquals(TestValues.MATCH, temperatureHigh, TestValues.GENERAL_TEMPERATURE);
        assertEquals(TestValues.MATCH, temperatureLow, TestValues.GENERAL_TEMPERATURE);
        assertEquals(TestValues.MATCH, apparentTemperature, TestValues.GENERAL_TEMPERATURE);
        assertEquals(TestValues.MATCH, apparentTemperatureHigh, TestValues.GENERAL_TEMPERATURE);
        assertEquals(TestValues.MATCH, weatherSummary, TestValues.GENERAL_STRING);
        assertEquals(TestValues.MATCH, time, TestValues.GENERAL_DATETIME);
        assertEquals(TestValues.MATCH, humidity, TestValues.GENERAL_FLOAT);
        assertEquals(TestValues.MATCH, cloudCover, TestValues.GENERAL_FLOAT);
        assertEquals(TestValues.MATCH, moonPhase, TestValues.GENERAL_FLOAT);
        assertEquals(TestValues.MATCH, windBearing, TestValues.GENERAL_INTEGER);
        assertEquals(TestValues.MATCH, windGust, TestValues.GENERAL_FLOAT);
        assertEquals(TestValues.MATCH, windSpeed, TestValues.GENERAL_FLOAT);
        assertEquals(TestValues.MATCH, nearestStormBearing, TestValues.GENERAL_INTEGER);
        assertEquals(TestValues.MATCH, nearestStormDistance, TestValues.GENERAL_INTEGER);
        assertEquals(TestValues.MATCH, precipAccumulation, TestValues.GENERAL_FLOAT);
        assertEquals(TestValues.MATCH, precipIntensity, TestValues.GENERAL_FLOAT);
        assertEquals(TestValues.MATCH, precipProbability, TestValues.GENERAL_FLOAT);
        assertEquals(TestValues.MATCH, precipType, TestValues.GENERAL_STRING);
        assertEquals(TestValues.MATCH, visibility, TestValues.GENERAL_FLOAT);
        assertEquals(TestValues.MATCH, weatherIcon, TestValues.GENERAL_IMAGE);

        // Invalid/Null Tests
        WeatherData msg = new WeatherData();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getCurrentTemperature());
        assertNull(TestValues.NULL, msg.getTemperatureHigh());
        assertNull(TestValues.NULL, msg.getTemperatureLow());
        assertNull(TestValues.NULL, msg.getApparentTemperature());
        assertNull(TestValues.NULL, msg.getApparentTemperatureHigh());
        assertNull(TestValues.NULL, msg.getApparentTemperatureLow());
        assertNull(TestValues.NULL, msg.getWeatherSummary());
        assertNull(TestValues.NULL, msg.getTime());
        assertNull(TestValues.NULL, msg.getHumidity());
        assertNull(TestValues.NULL, msg.getCloudCover());
        assertNull(TestValues.NULL, msg.getMoonPhase());
        assertNull(TestValues.NULL, msg.getWindBearing());
        assertNull(TestValues.NULL, msg.getWindGust());
        assertNull(TestValues.NULL, msg.getWindSpeed());
        assertNull(TestValues.NULL, msg.getNearestStormBearing());
        assertNull(TestValues.NULL, msg.getNearestStormDistance());
        assertNull(TestValues.NULL, msg.getPrecipAccumulation());
        assertNull(TestValues.NULL, msg.getPrecipIntensity());
        assertNull(TestValues.NULL, msg.getPrecipProbability());
        assertNull(TestValues.NULL, msg.getPrecipType());
        assertNull(TestValues.NULL, msg.getVisibility());
        assertNull(TestValues.NULL, msg.getWeatherIcon());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(WeatherData.KEY_CURRENT_TEMPERATURE, TestValues.GENERAL_TEMPERATURE);
            reference.put(WeatherData.KEY_TEMPERATURE_HIGH, TestValues.GENERAL_TEMPERATURE);
            reference.put(WeatherData.KEY_TEMPERATURE_LOW, TestValues.GENERAL_TEMPERATURE);
            reference.put(WeatherData.KEY_APPARENT_TEMPERATURE, TestValues.GENERAL_TEMPERATURE);
            reference.put(WeatherData.KEY_APPARENT_TEMPERATURE_HIGH, TestValues.GENERAL_TEMPERATURE);
            reference.put(WeatherData.KEY_APPARENT_TEMPERATURE_LOW, TestValues.GENERAL_TEMPERATURE);
            reference.put(WeatherData.KEY_WEATHER_SUMMARY, TestValues.GENERAL_STRING);
            reference.put(WeatherData.KEY_TIME, TestValues.GENERAL_DATETIME);
            reference.put(WeatherData.KEY_HUMIDITY, TestValues.GENERAL_FLOAT);
            reference.put(WeatherData.KEY_CLOUD_COVER, TestValues.GENERAL_FLOAT);
            reference.put(WeatherData.KEY_MOON_PHASE, TestValues.GENERAL_FLOAT);
            reference.put(WeatherData.KEY_WIND_BEARING, TestValues.GENERAL_INTEGER);
            reference.put(WeatherData.KEY_WIND_GUST, TestValues.GENERAL_FLOAT);
            reference.put(WeatherData.KEY_WIND_SPEED, TestValues.GENERAL_FLOAT);
            reference.put(WeatherData.KEY_NEAREST_STORM_BEARING, TestValues.GENERAL_INTEGER);
            reference.put(WeatherData.KEY_NEAREST_STORM_DISTANCE, TestValues.GENERAL_INTEGER);
            reference.put(WeatherData.KEY_PRECIP_ACCUMULATION, TestValues.GENERAL_FLOAT);
            reference.put(WeatherData.KEY_PRECIP_INTENSITY, TestValues.GENERAL_FLOAT);
            reference.put(WeatherData.KEY_PRECIP_PROBABILITY, TestValues.GENERAL_FLOAT);
            reference.put(WeatherData.KEY_PRECIP_TYPE, TestValues.GENERAL_STRING);
            reference.put(WeatherData.KEY_VISIBILITY, TestValues.GENERAL_FLOAT);
            reference.put(WeatherData.KEY_WEATHER_ICON, TestValues.GENERAL_STRING);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(WeatherData.KEY_CURRENT_TEMPERATURE) || key.equals(WeatherData.KEY_TEMPERATURE_HIGH) ||
                        key.equals(WeatherData.KEY_TEMPERATURE_LOW) || key.equals(WeatherData.KEY_APPARENT_TEMPERATURE) ||
                        key.equals(WeatherData.KEY_APPARENT_TEMPERATURE_HIGH) || key.equals(WeatherData.KEY_APPARENT_TEMPERATURE_LOW)) {
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateTemperature(TestValues.GENERAL_TEMPERATURE, new Temperature(hashTest)));
                } else if (key.equals(WeatherData.KEY_TIME)) {
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateDateTime(TestValues.GENERAL_DATETIME, new DateTime(hashTest)));
                } else if (key.equals(WeatherData.KEY_WEATHER_ICON)) {
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, new Image(hashTest)));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}