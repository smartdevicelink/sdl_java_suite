package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.DeviceInfo;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.DeviceInfo}
 */
public class DeviceInfoTests extends TestCase{

    private DeviceInfo          msg;

    @Override
    public void setUp(){
        msg = new DeviceInfo();

        msg.setCarrier(Test.GENERAL_STRING);
        msg.setFirmwareRev(Test.GENERAL_STRING);
        msg.setHardware(Test.GENERAL_STRING);
        msg.setMaxNumberRFCOMMPorts(Test.GENERAL_INT);
        msg.setOs(Test.GENERAL_STRING);
        msg.setOsVersion(Test.GENERAL_STRING);
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
        assertEquals(Test.MATCH, Test.GENERAL_INT, maxNum);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, osVer);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, os);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, firmwareRev);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, hardware);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, carrier);
        
        // Invalid/Null Tests
        DeviceInfo msg = new DeviceInfo();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getCarrier());
        assertNull(Test.NULL, msg.getHardware());
        assertNull(Test.NULL, msg.getFirmwareRev());
        assertNull(Test.NULL, msg.getOs());
        assertNull(Test.NULL, msg.getOsVersion());
        assertNull(Test.NULL, msg.getMaxNumberRFCOMMPorts());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DeviceInfo.KEY_CARRIER, Test.GENERAL_STRING);
            reference.put(DeviceInfo.KEY_HARDWARE, Test.GENERAL_STRING);
            reference.put(DeviceInfo.KEY_FIRMWARE_REV, Test.GENERAL_STRING);
            reference.put(DeviceInfo.KEY_OS, Test.GENERAL_STRING);
            reference.put(DeviceInfo.KEY_OS_VERSION, Test.GENERAL_STRING);
            reference.put(DeviceInfo.KEY_MAX_NUMBER_RFCOMM_PORTS, Test.GENERAL_INT);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}