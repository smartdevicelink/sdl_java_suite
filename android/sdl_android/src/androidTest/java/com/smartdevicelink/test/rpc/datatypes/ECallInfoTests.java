package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.ECallInfo;
import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.ECallInfo}
 */
public class ECallInfoTests extends TestCase {

    private ECallInfo msg;

    @Override
    public void setUp() {
        msg = new ECallInfo();

        msg.setAuxECallNotificationStatus(TestValues.GENERAL_VEHICLEDATANOTIFICATIONSTATUS);
        msg.setECallConfirmationStatus(TestValues.GENERAL_ECALLCONFIRMATIONSTATUS);
        msg.setECallNotificationStatus(TestValues.GENERAL_VEHICLEDATANOTIFICATIONSTATUS);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        VehicleDataNotificationStatus auxEcall = msg.getAuxECallNotificationStatus();
        VehicleDataNotificationStatus ecallNotify = msg.getECallNotificationStatus();
        ECallConfirmationStatus ecallConfirm = msg.getECallConfirmationStatus();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHICLEDATANOTIFICATIONSTATUS, auxEcall);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHICLEDATANOTIFICATIONSTATUS, ecallNotify);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_ECALLCONFIRMATIONSTATUS, ecallConfirm);

        // Invalid/Null Tests
        ECallInfo msg = new ECallInfo();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getECallConfirmationStatus());
        assertNull(TestValues.NULL, msg.getECallNotificationStatus());
        assertNull(TestValues.NULL, msg.getAuxECallNotificationStatus());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(ECallInfo.KEY_AUX_E_CALL_NOTIFICATION_STATUS, TestValues.GENERAL_VEHICLEDATANOTIFICATIONSTATUS);
            reference.put(ECallInfo.KEY_E_CALL_NOTIFICATION_STATUS, TestValues.GENERAL_VEHICLEDATANOTIFICATIONSTATUS);
            reference.put(ECallInfo.KEY_E_CALL_CONFIRMATION_STATUS, TestValues.GENERAL_ECALLCONFIRMATIONSTATUS);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}