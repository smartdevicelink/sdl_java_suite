package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DateTime;
import com.smartdevicelink.proxy.rpc.WeatherAlert;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.WeatherAlert}
 */
public class WeatherAlertTests extends TestCase {

    private WeatherAlert msg;

    @Override
    public void setUp() {

        msg = new WeatherAlert();
        msg.setExpires(TestValues.GENERAL_DATETIME);
        msg.setTimeIssued(TestValues.GENERAL_DATETIME);
        msg.setRegions(TestValues.GENERAL_STRING_LIST);
        msg.setSeverity(TestValues.GENERAL_STRING);
        msg.setSummary(TestValues.GENERAL_STRING);
        msg.setTitle(TestValues.GENERAL_STRING);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        DateTime expires = msg.getExpires();
        DateTime issued = msg.getTimeIssued();
        List<String> regions = msg.getRegions();
        String severity = msg.getSeverity();
        String summary = msg.getSummary();
        String title = msg.getTitle();

        // Valid Tests
        assertEquals(TestValues.MATCH, expires, TestValues.GENERAL_DATETIME);
        assertEquals(TestValues.MATCH, issued, TestValues.GENERAL_DATETIME);
        assertEquals(TestValues.MATCH, regions, TestValues.GENERAL_STRING_LIST);
        assertEquals(TestValues.MATCH, severity, TestValues.GENERAL_STRING);
        assertEquals(TestValues.MATCH, summary, TestValues.GENERAL_STRING);
        assertEquals(TestValues.MATCH, title, TestValues.GENERAL_STRING);

        // Invalid/Null Tests
        WeatherAlert msg = new WeatherAlert();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getExpires());
        assertNull(TestValues.NULL, msg.getTimeIssued());
        assertNull(TestValues.NULL, msg.getRegions());
        assertNull(TestValues.NULL, msg.getSeverity());
        assertNull(TestValues.NULL, msg.getSummary());
        assertNull(TestValues.NULL, msg.getTitle());
    }

    public void testRequiredParamsConstructor() {
        msg = new WeatherAlert(TestValues.GENERAL_STRING_LIST);
        List<String> regions = msg.getRegions();
        assertEquals(TestValues.MATCH, regions, TestValues.GENERAL_STRING_LIST);
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(WeatherAlert.KEY_EXPIRES, TestValues.GENERAL_DATETIME);
            reference.put(WeatherAlert.KEY_TIME_ISSUED, TestValues.GENERAL_DATETIME);
            reference.put(WeatherAlert.KEY_REGIONS, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
            reference.put(WeatherAlert.KEY_SEVERITY, TestValues.GENERAL_STRING);
            reference.put(WeatherAlert.KEY_SUMMARY, TestValues.GENERAL_STRING);
            reference.put(WeatherAlert.KEY_TITLE, TestValues.GENERAL_STRING);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(WeatherAlert.KEY_EXPIRES) || key.equals(WeatherAlert.KEY_TIME_ISSUED)) {
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateDateTime(TestValues.GENERAL_DATETIME, new DateTime(hashTest)));
                } else if (key.equals(WeatherAlert.KEY_REGIONS)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());
                    assertTrue(TestValues.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}