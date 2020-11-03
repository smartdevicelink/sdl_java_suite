package com.smartdevicelink.test.rpc.datatypes;


import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.LocationDetails;
import com.smartdevicelink.proxy.rpc.WeatherAlert;
import com.smartdevicelink.proxy.rpc.WeatherData;
import com.smartdevicelink.proxy.rpc.WeatherServiceData;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
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
    public void setUp() {

        msg = new WeatherServiceData();
        msg.setLocation(TestValues.GENERAL_LOCATIONDETAILS);
        msg.setCurrentForecast(TestValues.GENERAL_WEATHERDATA);
        msg.setMinuteForecast(TestValues.GENERAL_WEATHERDATA_LIST);
        msg.setHourlyForecast(TestValues.GENERAL_WEATHERDATA_LIST);
        msg.setMultidayForecast(TestValues.GENERAL_WEATHERDATA_LIST);
        msg.setAlerts(TestValues.GENERAL_WEATHERALERT_LIST);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        LocationDetails location = msg.getLocation();
        WeatherData currentForecast = msg.getCurrentForecast();
        List<WeatherData> minuteForecast = msg.getMinuteForecast();
        List<WeatherData> hourlyForecast = msg.getHourlyForecast();
        List<WeatherData> multidayForecast = msg.getMultidayForecast();
        List<WeatherAlert> alerts = msg.getAlerts();

        // Valid Tests
        assertEquals(TestValues.GENERAL_LOCATIONDETAILS, location);
        assertEquals(TestValues.GENERAL_WEATHERDATA, currentForecast);
        assertEquals(TestValues.GENERAL_WEATHERDATA_LIST, minuteForecast);
        assertEquals(TestValues.GENERAL_WEATHERDATA_LIST, hourlyForecast);
        assertEquals(TestValues.GENERAL_WEATHERDATA_LIST, multidayForecast);
        assertEquals(TestValues.GENERAL_WEATHERALERT_LIST, alerts);

        // Invalid/Null Tests
        WeatherServiceData msg = new WeatherServiceData();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getLocation());
        assertNull(TestValues.NULL, msg.getCurrentForecast());
        assertNull(TestValues.NULL, msg.getMinuteForecast());
        assertNull(TestValues.NULL, msg.getHourlyForecast());
        assertNull(TestValues.NULL, msg.getMultidayForecast());
        assertNull(TestValues.NULL, msg.getAlerts());
    }

    public void testRequiredParamsConstructor() {
        msg = new WeatherServiceData(TestValues.GENERAL_LOCATIONDETAILS);
        LocationDetails location = msg.getLocation();
        assertEquals(TestValues.GENERAL_LOCATIONDETAILS, location);
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(WeatherServiceData.KEY_LOCATION, TestValues.GENERAL_LOCATIONDETAILS);
            reference.put(WeatherServiceData.KEY_CURRENT_FORECAST, TestValues.GENERAL_WEATHERDATA);
            reference.put(WeatherServiceData.KEY_MINUTE_FORECAST, TestValues.GENERAL_WEATHERDATA_LIST);
            reference.put(WeatherServiceData.KEY_HOURLY_FORECAST, TestValues.GENERAL_WEATHERDATA_LIST);
            reference.put(WeatherServiceData.KEY_MULTIDAY_FORECAST, TestValues.GENERAL_WEATHERDATA_LIST);
            reference.put(WeatherServiceData.KEY_ALERTS, TestValues.GENERAL_WEATHERALERT_LIST);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(WeatherServiceData.KEY_LOCATION)) {

                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateLocationDetails(TestValues.GENERAL_LOCATIONDETAILS, new LocationDetails(hashTest)));

                } else if (key.equals(WeatherServiceData.KEY_CURRENT_FORECAST)) {

                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateWeatherData(TestValues.GENERAL_WEATHERDATA, new WeatherData(hashTest)));

                } else if (key.equals(WeatherServiceData.KEY_MINUTE_FORECAST) || key.equals(WeatherServiceData.KEY_HOURLY_FORECAST)
                        || key.equals(WeatherServiceData.KEY_MULTIDAY_FORECAST)) {

                    JSONArray weatherDataArrayObjTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    List<WeatherData> weatherDataUnderTestList = new ArrayList<>();
                    for (int index = 0; index < weatherDataArrayObjTest.length(); index++) {
                        WeatherData weatherData1 = new WeatherData(JsonRPCMarshaller.deserializeJSONObject((JSONObject) weatherDataArrayObjTest.get(index)));
                        weatherDataUnderTestList.add(weatherData1);
                    }

                    assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
                            Validator.validateWeatherDataList(TestValues.GENERAL_WEATHERDATA_LIST, weatherDataUnderTestList));

                } else if (key.equals(WeatherServiceData.KEY_ALERTS)) {

                    JSONArray weatherAlertArrayObjTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    List<WeatherAlert> weatherAlertUnderTestList = new ArrayList<>();
                    for (int index = 0; index < weatherAlertArrayObjTest.length(); index++) {
                        WeatherAlert weatherAlert1 = new WeatherAlert(JsonRPCMarshaller.deserializeJSONObject((JSONObject) weatherAlertArrayObjTest.get(index)));
                        weatherAlertUnderTestList.add(weatherAlert1);
                    }

                    assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
                            Validator.validateWeatherAlertList(TestValues.GENERAL_WEATHERALERT_LIST, weatherAlertUnderTestList));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
