package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AppServiceManifest;
import com.smartdevicelink.proxy.rpc.AppServiceRecord;
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
 * {@link com.smartdevicelink.proxy.rpc.AppServiceRecord}
 */
public class AppServiceRecordTests extends TestCase {

    private AppServiceRecord msg;

    @Override
    public void setUp() {

        msg = new AppServiceRecord();
        msg.setServicePublished(TestValues.GENERAL_BOOLEAN);
        msg.setServiceActive(TestValues.GENERAL_BOOLEAN);
        msg.setServiceManifest(TestValues.GENERAL_APPSERVICEMANIFEST);
        msg.setServiceID(TestValues.GENERAL_STRING);

    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values

        boolean isServicePublished = msg.getServicePublished();
        boolean isServiceActive = msg.getServiceActive();
        AppServiceManifest serviceManifest = msg.getServiceManifest();
        String serviceID = msg.getServiceID();

        // Valid Tests
        assertEquals(TestValues.GENERAL_BOOLEAN, isServicePublished);
        assertEquals(TestValues.GENERAL_BOOLEAN, isServiceActive);
        assertEquals(TestValues.GENERAL_APPSERVICEMANIFEST, serviceManifest);
        assertEquals(TestValues.GENERAL_STRING, serviceID);

        // Invalid/Null Tests
        AppServiceRecord msg = new AppServiceRecord();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getServicePublished());
        assertNull(TestValues.NULL, msg.getServiceActive());
        assertNull(TestValues.NULL, msg.getServiceManifest());
        assertNull(TestValues.NULL, msg.getServiceID());
    }

    public void testRequiredParamsConstructor() {
        msg = new AppServiceRecord(TestValues.GENERAL_STRING, TestValues.GENERAL_APPSERVICEMANIFEST, TestValues.GENERAL_BOOLEAN, TestValues.GENERAL_BOOLEAN);

        boolean isServicePublished = msg.getServicePublished();
        boolean isServiceActive = msg.getServiceActive();
        AppServiceManifest serviceManifest = msg.getServiceManifest();
        String serviceID = msg.getServiceID();

        // Valid Tests
        assertEquals(TestValues.GENERAL_BOOLEAN, isServicePublished);
        assertEquals(TestValues.GENERAL_BOOLEAN, isServiceActive);
        assertEquals(TestValues.GENERAL_APPSERVICEMANIFEST, serviceManifest);
        assertEquals(TestValues.GENERAL_STRING, serviceID);
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(AppServiceRecord.KEY_SERVICE_ACTIVE, TestValues.GENERAL_BOOLEAN);
            reference.put(AppServiceRecord.KEY_SERVICE_PUBLISHED, TestValues.GENERAL_BOOLEAN);
            reference.put(AppServiceRecord.KEY_SERVICE_ID, TestValues.GENERAL_STRING);
            reference.put(AppServiceRecord.KEY_SERVICE_MANIFEST, TestValues.GENERAL_APPSERVICEMANIFEST);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(AppServiceRecord.KEY_SERVICE_MANIFEST)) {
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateAppServiceManifest(TestValues.GENERAL_APPSERVICEMANIFEST, new AppServiceManifest(hashTest)));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}