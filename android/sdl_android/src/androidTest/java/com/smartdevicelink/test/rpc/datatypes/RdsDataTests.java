package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.RdsData;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.RdsData}
 */
public class RdsDataTests extends TestCase {

    private RdsData msg;

    @Override
    public void setUp() {
        msg = new RdsData();

        msg.setProgramService(TestValues.GENERAL_STRING);
        msg.setRadioText(TestValues.GENERAL_STRING);
        msg.setClockText(TestValues.GENERAL_STRING);
        msg.setProgramIdentification(TestValues.GENERAL_STRING);
        msg.setRegion(TestValues.GENERAL_STRING);
        msg.setTrafficProgram(TestValues.GENERAL_BOOLEAN);
        msg.setTrafficAnnouncement(TestValues.GENERAL_BOOLEAN);
        msg.setProgramType(TestValues.GENERAL_INT);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        String ps = msg.getProgramService();
        String rt = msg.getRadioText();
        String ct = msg.getClockText();
        String pi = msg.getProgramIdentification();
        int pty = msg.getProgramType();
        boolean tp = msg.getTrafficProgram();
        boolean ta = msg.getTrafficAnnouncement();
        String reg = msg.getRegion();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, ps);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, rt);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, ct);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, pi);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, pty);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, tp);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, ta);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, reg);

        // Invalid/Null Tests
        RdsData msg = new RdsData();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getProgramService());
        assertNull(TestValues.NULL, msg.getRadioText());
        assertNull(TestValues.NULL, msg.getClockText());
        assertNull(TestValues.NULL, msg.getProgramIdentification());
        assertNull(TestValues.NULL, msg.getRegion());
        assertNull(TestValues.NULL, msg.getTrafficProgram());
        assertNull(TestValues.NULL, msg.getTrafficAnnouncement());
        assertNull(TestValues.NULL, msg.getProgramType());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(RdsData.KEY_PS, TestValues.GENERAL_STRING);
            reference.put(RdsData.KEY_RT, TestValues.GENERAL_STRING);
            reference.put(RdsData.KEY_CT, TestValues.GENERAL_STRING);
            reference.put(RdsData.KEY_PI, TestValues.GENERAL_STRING);
            reference.put(RdsData.KEY_PTY, TestValues.GENERAL_INT);
            reference.put(RdsData.KEY_TP, TestValues.GENERAL_BOOLEAN);
            reference.put(RdsData.KEY_TA, TestValues.GENERAL_BOOLEAN);
            reference.put(RdsData.KEY_REG, TestValues.GENERAL_STRING);

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