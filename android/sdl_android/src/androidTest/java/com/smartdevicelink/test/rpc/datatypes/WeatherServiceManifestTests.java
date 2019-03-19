package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.WeatherServiceManifest;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.WeatherServiceManifest}
 */
public class WeatherServiceManifestTests extends TestCase {

	private WeatherServiceManifest msg;

	@Override
	public void setUp(){
		msg = new WeatherServiceManifest();

		msg.setCurrentForecastSupported(Test.GENERAL_BOOLEAN);
		msg.setMaxHourlyForecastAmount(Test.GENERAL_INT);
		msg.setMaxMinutelyForecastAmount(Test.GENERAL_INT);
		msg.setMaxMultidayForecastAmount(Test.GENERAL_INT);
		msg.setWeatherForLocationSupported(Test.GENERAL_BOOLEAN);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		boolean currentForecastSupported = msg.getCurrentForecastSupported();
		boolean weatherForLocationSupported = msg.getWeatherForLocationSupported();
		Integer getMaxHourlyForecastAmt = msg.getMaxHourlyForecastAmount();
		Integer getMaxMinutelyForecastAmt = msg.getMaxMinutelyForecastAmount();
		Integer getMaxMultidayForecastAmt = msg.getMaxMultidayForecastAmount();

		// Valid Tests
		assertEquals(Test.GENERAL_BOOLEAN, currentForecastSupported);
		assertEquals(Test.GENERAL_BOOLEAN, weatherForLocationSupported);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, getMaxHourlyForecastAmt);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, getMaxMinutelyForecastAmt);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, getMaxMultidayForecastAmt);

		// Invalid/Null Tests
		WeatherServiceManifest msg = new WeatherServiceManifest();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getCurrentForecastSupported());
		assertNull(Test.NULL, msg.getWeatherForLocationSupported());
		assertNull(Test.NULL, msg.getMaxHourlyForecastAmount());
		assertNull(Test.NULL, msg.getMaxMinutelyForecastAmount());
		assertNull(Test.NULL, msg.getMaxMultidayForecastAmount());
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(WeatherServiceManifest.KEY_CURRENT_FORECAST_SUPPORTED, Test.GENERAL_BOOLEAN);
			reference.put(WeatherServiceManifest.KEY_WEATHER_FOR_LOCATION_SUPPORTED, Test.GENERAL_BOOLEAN);
			reference.put(WeatherServiceManifest.KEY_MAX_HOURLY_FORECAST_AMOUNT, Test.GENERAL_INTEGER);
			reference.put(WeatherServiceManifest.KEY_MAX_MINUTELY_FORECAST_AMOUNT, Test.GENERAL_INTEGER);
			reference.put(WeatherServiceManifest.KEY_MAX_MULTIDAY_FORECAST_AMOUNT, Test.GENERAL_INTEGER);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}