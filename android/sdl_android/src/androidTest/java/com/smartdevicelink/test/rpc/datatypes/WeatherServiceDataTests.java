package com.smartdevicelink.test.rpc.datatypes;


import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.LocationDetails;
import com.smartdevicelink.proxy.rpc.WeatherAlert;
import com.smartdevicelink.proxy.rpc.WeatherData;
import com.smartdevicelink.proxy.rpc.WeatherServiceData;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.WeatherServiceData}
 */
public class WeatherServiceDataTests extends TestCase {

	private WeatherServiceData msg;

	@Override
	public void setUp(){

		msg = new WeatherServiceData();
		msg.setLocation(Test.GENERAL_LOCATIONDETAILS);
		msg.setCurrentForecast(Test.GENERAL_WEATHERDATA);
		msg.setMinuteForecast(Test.GENERAL_WEATHERDATA_LIST);
		msg.setHourlyForecast(Test.GENERAL_WEATHERDATA_LIST);
		msg.setMultidayForecast(Test.GENERAL_WEATHERDATA_LIST);
		msg.setAlerts(Test.GENERAL_WEATHERALERT_LIST);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		LocationDetails location = msg.getLocation();
		WeatherData currentForecast = msg.getCurrentForecast();
		List<WeatherData> minuteForecast = msg.getMinuteForecast();
		List<WeatherData> hourlyForecast = msg.getHourlyForecast();
		List<WeatherData> multidayForecast = msg.getMultidayForecast();
		List<WeatherAlert> alerts = msg.getAlerts();

		// Valid Tests
		assertEquals(Test.GENERAL_LOCATIONDETAILS, location);
		assertEquals(Test.GENERAL_WEATHERDATA, currentForecast);
		assertEquals(Test.GENERAL_WEATHERDATA_LIST, minuteForecast);
		assertEquals(Test.GENERAL_WEATHERDATA_LIST, hourlyForecast);
		assertEquals(Test.GENERAL_WEATHERDATA_LIST, multidayForecast);
		assertEquals(Test.GENERAL_WEATHERALERT_LIST, alerts);

		// Invalid/Null Tests
		WeatherServiceData msg = new WeatherServiceData();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getLocation());
		assertNull(Test.NULL, msg.getCurrentForecast());
		assertNull(Test.NULL, msg.getMinuteForecast());
		assertNull(Test.NULL, msg.getHourlyForecast());
		assertNull(Test.NULL, msg.getMultidayForecast());
		assertNull(Test.NULL, msg.getAlerts());
	}

	public void testRequiredParamsConstructor(){
		msg = new WeatherServiceData(Test.GENERAL_LOCATIONDETAILS);
		LocationDetails location = msg.getLocation();
		assertEquals(Test.GENERAL_LOCATIONDETAILS, location);
	}

	public void testJson(){
		JSONObject reference = new JSONObject();

		try{
			reference.put(WeatherServiceData.KEY_LOCATION, Test.GENERAL_LOCATIONDETAILS);
			reference.put(WeatherServiceData.KEY_CURRENT_FORECAST, Test.GENERAL_WEATHERDATA);
			reference.put(WeatherServiceData.KEY_MINUTE_FORECAST, Test.GENERAL_WEATHERDATA_LIST);
			reference.put(WeatherServiceData.KEY_HOURLY_FORECAST, Test.GENERAL_WEATHERDATA_LIST);
			reference.put(WeatherServiceData.KEY_MULTIDAY_FORECAST, Test.GENERAL_WEATHERDATA_LIST);
			reference.put(WeatherServiceData.KEY_ALERTS, Test.GENERAL_WEATHERALERT_LIST);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();

				if(key.equals(WeatherServiceData.KEY_LOCATION)){

					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateLocationDetails( Test.GENERAL_LOCATIONDETAILS, new LocationDetails(hashTest)));

				} else if(key.equals(WeatherServiceData.KEY_CURRENT_FORECAST)){

					JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
					Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
					assertTrue(Test.TRUE, Validator.validateWeatherData( Test.GENERAL_WEATHERDATA, new WeatherData(hashTest)));

				} else if(key.equals(WeatherServiceData.KEY_MINUTE_FORECAST) || key.equals(WeatherServiceData.KEY_HOURLY_FORECAST)
						|| key.equals(WeatherServiceData.KEY_MULTIDAY_FORECAST)){

					JSONArray weatherDataArrayObjTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					List<WeatherData> weatherDataUnderTestList = new ArrayList<>();
					for (int index = 0; index < weatherDataArrayObjTest.length(); index++) {
						WeatherData weatherData1 = new WeatherData(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)weatherDataArrayObjTest.get(index) ));
						weatherDataUnderTestList.add(weatherData1);
					}

					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateWeatherDataList(Test.GENERAL_WEATHERDATA_LIST, weatherDataUnderTestList));

				} else if(key.equals(WeatherServiceData.KEY_ALERTS)){

					JSONArray weatherAlertArrayObjTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					List<WeatherAlert> weatherAlertUnderTestList = new ArrayList<>();
					for (int index = 0; index < weatherAlertArrayObjTest.length(); index++) {
						WeatherAlert weatherAlert1 = new WeatherAlert(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)weatherAlertArrayObjTest.get(index) ));
						weatherAlertUnderTestList.add(weatherAlert1);
					}

					assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
							Validator.validateWeatherAlertList(Test.GENERAL_WEATHERALERT_LIST, weatherAlertUnderTestList));
				}
			}
		} catch(JSONException e){
			fail(Test.JSON_FAIL);
		}
	}
}
