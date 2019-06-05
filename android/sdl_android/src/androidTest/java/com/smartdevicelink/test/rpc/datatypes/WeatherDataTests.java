package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DateTime;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.Temperature;
import com.smartdevicelink.proxy.rpc.WeatherData;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
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
	public void setUp(){

		msg = new WeatherData();
		msg.setCurrentTemperature(Test.GENERAL_TEMPERATURE);
		msg.setTemperatureHigh(Test.GENERAL_TEMPERATURE);
		msg.setTemperatureLow(Test.GENERAL_TEMPERATURE);
		msg.setApparentTemperature(Test.GENERAL_TEMPERATURE);
		msg.setApparentTemperatureHigh(Test.GENERAL_TEMPERATURE);
		msg.setApparentTemperatureLow(Test.GENERAL_TEMPERATURE);
		msg.setWeatherSummary(Test.GENERAL_STRING);
		msg.setTime(Test.GENERAL_DATETIME);
		msg.setHumidity(Test.GENERAL_FLOAT);
		msg.setCloudCover(Test.GENERAL_FLOAT);
		msg.setMoonPhase(Test.GENERAL_FLOAT);
		msg.setWindBearing(Test.GENERAL_INTEGER);
		msg.setWindGust(Test.GENERAL_FLOAT);
		msg.setWindSpeed(Test.GENERAL_FLOAT);
		msg.setNearestStormBearing(Test.GENERAL_INTEGER);
		msg.setNearestStormDistance(Test.GENERAL_INTEGER);
		msg.setPrecipAccumulation(Test.GENERAL_FLOAT);
		msg.setPrecipIntensity(Test.GENERAL_FLOAT);
		msg.setPrecipProbability(Test.GENERAL_FLOAT);
		msg.setPrecipType(Test.GENERAL_STRING);
		msg.setVisibility(Test.GENERAL_FLOAT);
		msg.setWeatherIcon(Test.GENERAL_IMAGE);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
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
		assertEquals(Test.MATCH, currentTemperature, Test.GENERAL_TEMPERATURE);
		assertEquals(Test.MATCH, temperatureHigh, Test.GENERAL_TEMPERATURE);
		assertEquals(Test.MATCH, temperatureLow, Test.GENERAL_TEMPERATURE);
		assertEquals(Test.MATCH, apparentTemperature, Test.GENERAL_TEMPERATURE);
		assertEquals(Test.MATCH, apparentTemperatureHigh, Test.GENERAL_TEMPERATURE);
		assertEquals(Test.MATCH, weatherSummary, Test.GENERAL_STRING);
		assertEquals(Test.MATCH, time, Test.GENERAL_DATETIME);
		assertEquals(Test.MATCH, humidity, Test.GENERAL_FLOAT);
		assertEquals(Test.MATCH, cloudCover, Test.GENERAL_FLOAT);
		assertEquals(Test.MATCH, moonPhase, Test.GENERAL_FLOAT);
		assertEquals(Test.MATCH, windBearing, Test.GENERAL_INTEGER);
		assertEquals(Test.MATCH, windGust, Test.GENERAL_FLOAT);
		assertEquals(Test.MATCH, windSpeed, Test.GENERAL_FLOAT);
		assertEquals(Test.MATCH, nearestStormBearing, Test.GENERAL_INTEGER);
		assertEquals(Test.MATCH, nearestStormDistance, Test.GENERAL_INTEGER);
		assertEquals(Test.MATCH, precipAccumulation, Test.GENERAL_FLOAT);
		assertEquals(Test.MATCH, precipIntensity, Test.GENERAL_FLOAT);
		assertEquals(Test.MATCH, precipProbability, Test.GENERAL_FLOAT);
		assertEquals(Test.MATCH, precipType, Test.GENERAL_STRING);
		assertEquals(Test.MATCH, visibility, Test.GENERAL_FLOAT);
		assertEquals(Test.MATCH, weatherIcon, Test.GENERAL_IMAGE);

		// Invalid/Null Tests
		WeatherData msg = new WeatherData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getCurrentTemperature());
		assertNull(Test.NULL, msg.getTemperatureHigh());
		assertNull(Test.NULL, msg.getTemperatureLow());
		assertNull(Test.NULL, msg.getApparentTemperature());
		assertNull(Test.NULL, msg.getApparentTemperatureHigh());
		assertNull(Test.NULL, msg.getApparentTemperatureLow());
		assertNull(Test.NULL, msg.getWeatherSummary());
		assertNull(Test.NULL, msg.getTime());
		assertNull(Test.NULL, msg.getHumidity());
		assertNull(Test.NULL, msg.getCloudCover());
		assertNull(Test.NULL, msg.getMoonPhase());
		assertNull(Test.NULL, msg.getWindBearing());
		assertNull(Test.NULL, msg.getWindGust());
		assertNull(Test.NULL, msg.getWindSpeed());
		assertNull(Test.NULL, msg.getNearestStormBearing());
		assertNull(Test.NULL, msg.getNearestStormDistance());
		assertNull(Test.NULL, msg.getPrecipAccumulation());
		assertNull(Test.NULL, msg.getPrecipIntensity());
		assertNull(Test.NULL, msg.getPrecipProbability());
		assertNull(Test.NULL, msg.getPrecipType());
		assertNull(Test.NULL, msg.getVisibility());
		assertNull(Test.NULL, msg.getWeatherIcon());
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(WeatherData.KEY_CURRENT_TEMPERATURE, Test.GENERAL_TEMPERATURE);
			reference.put(WeatherData.KEY_TEMPERATURE_HIGH, Test.GENERAL_TEMPERATURE);
			reference.put(WeatherData.KEY_TEMPERATURE_LOW, Test.GENERAL_TEMPERATURE);
			reference.put(WeatherData.KEY_APPARENT_TEMPERATURE, Test.GENERAL_TEMPERATURE);
			reference.put(WeatherData.KEY_APPARENT_TEMPERATURE_HIGH, Test.GENERAL_TEMPERATURE);
			reference.put(WeatherData.KEY_APPARENT_TEMPERATURE_LOW, Test.GENERAL_TEMPERATURE);
			reference.put(WeatherData.KEY_WEATHER_SUMMARY, Test.GENERAL_STRING);
			reference.put(WeatherData.KEY_TIME, Test.GENERAL_DATETIME);
			reference.put(WeatherData.KEY_HUMIDITY, Test.GENERAL_FLOAT);
			reference.put(WeatherData.KEY_CLOUD_COVER, Test.GENERAL_FLOAT);
			reference.put(WeatherData.KEY_MOON_PHASE, Test.GENERAL_FLOAT);
			reference.put(WeatherData.KEY_WIND_BEARING, Test.GENERAL_INTEGER);
			reference.put(WeatherData.KEY_WIND_GUST, Test.GENERAL_FLOAT);
			reference.put(WeatherData.KEY_WIND_SPEED, Test.GENERAL_FLOAT);
			reference.put(WeatherData.KEY_NEAREST_STORM_BEARING, Test.GENERAL_INTEGER);
			reference.put(WeatherData.KEY_NEAREST_STORM_DISTANCE, Test.GENERAL_INTEGER);
			reference.put(WeatherData.KEY_PRECIP_ACCUMULATION, Test.GENERAL_FLOAT);
			reference.put(WeatherData.KEY_PRECIP_INTENSITY, Test.GENERAL_FLOAT);
			reference.put(WeatherData.KEY_PRECIP_PROBABILITY, Test.GENERAL_FLOAT);
			reference.put(WeatherData.KEY_PRECIP_TYPE, Test.GENERAL_STRING);
			reference.put(WeatherData.KEY_VISIBILITY, Test.GENERAL_FLOAT);
			reference.put(WeatherData.KEY_WEATHER_ICON, Test.GENERAL_STRING);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()) {
				String key = (String) iterator.next();
				if (key.equals(WeatherData.KEY_CURRENT_TEMPERATURE) || key.equals(WeatherData.KEY_TEMPERATURE_HIGH)||
						key.equals(WeatherData.KEY_TEMPERATURE_LOW) || key.equals(WeatherData.KEY_APPARENT_TEMPERATURE)||
						key.equals(WeatherData.KEY_APPARENT_TEMPERATURE_HIGH) || key.equals(WeatherData.KEY_APPARENT_TEMPERATURE_LOW)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateTemperature(Test.GENERAL_TEMPERATURE, new Temperature(hashTest)));
				} else if (key.equals(WeatherData.KEY_TIME)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateDateTime(Test.GENERAL_DATETIME, new DateTime(hashTest)));
				} else if (key.equals(WeatherData.KEY_WEATHER_ICON)){
					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, new Image(hashTest)));
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}