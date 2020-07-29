package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.WeatherServiceManifest;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

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

		msg.setCurrentForecastSupported(TestValues.GENERAL_BOOLEAN);
		msg.setMaxHourlyForecastAmount(TestValues.GENERAL_INT);
		msg.setMaxMinutelyForecastAmount(TestValues.GENERAL_INT);
		msg.setMaxMultidayForecastAmount(TestValues.GENERAL_INT);
		msg.setWeatherForLocationSupported(TestValues.GENERAL_BOOLEAN);
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
		assertEquals(TestValues.GENERAL_BOOLEAN, currentForecastSupported);
		assertEquals(TestValues.GENERAL_BOOLEAN, weatherForLocationSupported);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, getMaxHourlyForecastAmt);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, getMaxMinutelyForecastAmt);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, getMaxMultidayForecastAmt);

		// Invalid/Null Tests
		WeatherServiceManifest msg = new WeatherServiceManifest();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getCurrentForecastSupported());
		assertNull(TestValues.NULL, msg.getWeatherForLocationSupported());
		assertNull(TestValues.NULL, msg.getMaxHourlyForecastAmount());
		assertNull(TestValues.NULL, msg.getMaxMinutelyForecastAmount());
		assertNull(TestValues.NULL, msg.getMaxMultidayForecastAmount());
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(WeatherServiceManifest.KEY_CURRENT_FORECAST_SUPPORTED, TestValues.GENERAL_BOOLEAN);
			reference.put(WeatherServiceManifest.KEY_WEATHER_FOR_LOCATION_SUPPORTED, TestValues.GENERAL_BOOLEAN);
			reference.put(WeatherServiceManifest.KEY_MAX_HOURLY_FORECAST_AMOUNT, TestValues.GENERAL_INTEGER);
			reference.put(WeatherServiceManifest.KEY_MAX_MINUTELY_FORECAST_AMOUNT, TestValues.GENERAL_INTEGER);
			reference.put(WeatherServiceManifest.KEY_MAX_MULTIDAY_FORECAST_AMOUNT, TestValues.GENERAL_INTEGER);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch(JSONException e){
			fail(TestValues.JSON_FAIL);
		}
	}
}