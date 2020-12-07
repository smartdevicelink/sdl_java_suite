package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.CloudAppProperties;
import com.smartdevicelink.proxy.rpc.enums.HybridAppPreference;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.CloudAppProperties}
 */
public class CloudAppPropertiesTests extends TestCase {

    private CloudAppProperties msg;

    @Override
    public void setUp() {
        msg = new CloudAppProperties();

        msg.setNicknames(TestValues.GENERAL_STRING_LIST);
        msg.setAppID(TestValues.GENERAL_STRING);
        msg.setEnabled(TestValues.GENERAL_BOOLEAN);
        msg.setAuthToken(TestValues.GENERAL_STRING);
        msg.setCloudTransportType(TestValues.GENERAL_STRING);
        msg.setHybridAppPreference(TestValues.GENERAL_HYBRID_APP_PREFERENCE);
        msg.setEndpoint(TestValues.GENERAL_STRING);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        List<String> nicknames = msg.getNicknames();
        String appID = msg.getAppID();
        boolean enabled = msg.isEnabled();
        String authToken = msg.getAuthToken();
        String cloudTransportType = msg.getCloudTransportType();
        HybridAppPreference hybridAppPreference = msg.getHybridAppPreference();
        String endpoint = msg.getEndpoint();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST, nicknames);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, appID);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, enabled);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, authToken);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, cloudTransportType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_HYBRID_APP_PREFERENCE, hybridAppPreference);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, endpoint);

        // Invalid/Null Tests
        CloudAppProperties msg = new CloudAppProperties();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getNicknames());
        assertNull(TestValues.NULL, msg.getAppID());
        assertNull(TestValues.NULL, msg.isEnabled());
        assertNull(TestValues.NULL, msg.getAuthToken());
        assertNull(TestValues.NULL, msg.getCloudTransportType());
        assertNull(TestValues.NULL, msg.getHybridAppPreference());
        assertNull(TestValues.NULL, msg.getEndpoint());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(CloudAppProperties.KEY_NICKNAMES, TestValues.GENERAL_STRING_LIST);
            reference.put(CloudAppProperties.KEY_APP_ID, TestValues.GENERAL_STRING);
            reference.put(CloudAppProperties.KEY_ENABLED, TestValues.GENERAL_BOOLEAN);
            reference.put(CloudAppProperties.KEY_AUTH_TOKEN, TestValues.GENERAL_STRING);
            reference.put(CloudAppProperties.KEY_CLOUD_TRANSPORT_TYPE, TestValues.GENERAL_STRING);
            reference.put(CloudAppProperties.KEY_HYBRID_APP_PREFERENCE, TestValues.GENERAL_HYBRID_APP_PREFERENCE);
            reference.put(CloudAppProperties.KEY_ENDPOINT, TestValues.GENERAL_STRING);

            JSONObject underTest = msg.serializeJSON();

            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(CloudAppProperties.KEY_NICKNAMES)) {
                    Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key),
                            JsonUtils.readStringListFromJsonObject(underTest, key));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}