package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.DeviceInfo;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.DeviceInfo}
 */
public class DeviceInfoTests extends TestCase{

    private DeviceInfo          msg;

    @Override
    public void setUp(){
        msg = new DeviceInfo();

        msg.setCarrier(TestValues.GENERAL_STRING);
        msg.setFirmwareRev(TestValues.GENERAL_STRING);
        msg.setHardware(TestValues.GENERAL_STRING);
        msg.setMaxNumberRFCOMMPorts(TestValues.GENERAL_INT);
        msg.setOs(TestValues.GENERAL_STRING);
        msg.setOsVersion(TestValues.GENERAL_STRING);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        String carrier = msg.getCarrier();
        String hardware = msg.getHardware();
        String firmwareRev = msg.getFirmwareRev();
        String os = msg.getOs();
        String osVer = msg.getOsVersion();
        int maxNum = msg.getMaxNumberRFCOMMPorts();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, maxNum);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, osVer);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, os);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, firmwareRev);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, hardware);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, carrier);
        
        // Invalid/Null Tests
        DeviceInfo msg = new DeviceInfo();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getCarrier());
        assertNull(TestValues.NULL, msg.getHardware());
        assertNull(TestValues.NULL, msg.getFirmwareRev());
        assertNull(TestValues.NULL, msg.getOs());
        assertNull(TestValues.NULL, msg.getOsVersion());
        assertNull(TestValues.NULL, msg.getMaxNumberRFCOMMPorts());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DeviceInfo.KEY_CARRIER, TestValues.GENERAL_STRING);
            reference.put(DeviceInfo.KEY_HARDWARE, TestValues.GENERAL_STRING);
            reference.put(DeviceInfo.KEY_FIRMWARE_REV, TestValues.GENERAL_STRING);
            reference.put(DeviceInfo.KEY_OS, TestValues.GENERAL_STRING);
            reference.put(DeviceInfo.KEY_OS_VERSION, TestValues.GENERAL_STRING);
            reference.put(DeviceInfo.KEY_MAX_NUMBER_RFCOMM_PORTS, TestValues.GENERAL_INT);

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