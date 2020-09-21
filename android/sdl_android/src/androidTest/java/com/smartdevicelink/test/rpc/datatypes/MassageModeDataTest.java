package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.MassageModeData;
import com.smartdevicelink.proxy.rpc.enums.MassageMode;
import com.smartdevicelink.proxy.rpc.enums.MassageZone;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.MassageModeData}
 */
public class MassageModeDataTest extends TestCase {

    private MassageModeData msg;

    @Override
    public void setUp() {
        msg = new MassageModeData();

        msg.setMassageZone(TestValues.GENERAL_MASSAGEZONE);
        msg.setMassageMode(TestValues.GENERAL_MASSAGEMODE);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        MassageZone massageZone = msg.getMassageZone();
        MassageMode massageMode = msg.getMassageMode();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MASSAGEZONE, massageZone);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MASSAGEMODE, massageMode);


        // Invalid/Null Tests
        MassageModeData msg = new MassageModeData();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getMassageMode());
        assertNull(TestValues.NULL, msg.getMassageZone());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(MassageModeData.KEY_MASSAGE_MODE, TestValues.GENERAL_MASSAGEMODE);
            reference.put(MassageModeData.KEY_MASSAGE_ZONE, TestValues.GENERAL_MASSAGEZONE);

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