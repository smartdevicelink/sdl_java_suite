package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.DeviceInfo;
import com.smartdevicelink.test.JsonUtils;

public class DeviceInfoTests extends TestCase{

    private static final String HARDWARE     = "aljg238";
    private static final String FIRMWARE_REV = "2.4.3";
    private static final String OS           = "Android";
    private static final String OS_VERSION   = "4.4.4";
    private static final String CARRIER      = "Verizon";
    private static final int    RFCOMM_PORTS = 3;

    private DeviceInfo          msg;

    @Override
    public void setUp(){
        msg = new DeviceInfo();

        msg.setCarrier(CARRIER);
        msg.setFirmwareRev(FIRMWARE_REV);
        msg.setHardware(HARDWARE);
        msg.setMaxNumberRFCOMMPorts(RFCOMM_PORTS);
        msg.setOs(OS);
        msg.setOsVersion(OS_VERSION);
    }

    public void testCarrier(){
        String copy = msg.getCarrier();
        assertEquals("Input value didn't match expected value.", CARRIER, copy);
    }

    public void testHardware(){
        String copy = msg.getHardware();
        assertEquals("Input value didn't match expected value.", HARDWARE, copy);
    }

    public void testFirmwareRev(){
        String copy = msg.getFirmwareRev();
        assertEquals("Input value didn't match expected value.", FIRMWARE_REV, copy);
    }

    public void testOs(){
        String copy = msg.getOs();
        assertEquals("Input value didn't match expected value.", OS, copy);
    }

    public void testOsVersion(){
        String copy = msg.getOsVersion();
        assertEquals("Input value didn't match expected value.", OS_VERSION, copy);
    }

    public void testMaxNumberRFCOMMPorts(){
        int copy = msg.getMaxNumberRFCOMMPorts();
        assertEquals("Input value didn't match expected value.", RFCOMM_PORTS, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DeviceInfo.KEY_CARRIER, CARRIER);
            reference.put(DeviceInfo.KEY_HARDWARE, HARDWARE);
            reference.put(DeviceInfo.KEY_FIRMWARE_REV, FIRMWARE_REV);
            reference.put(DeviceInfo.KEY_OS, OS);
            reference.put(DeviceInfo.KEY_OS_VERSION, OS_VERSION);
            reference.put(DeviceInfo.KEY_MAX_NUMBER_RFCOMM_PORTS, RFCOMM_PORTS);

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
                        JsonUtils.readObjectFromJsonObject(reference, key),
                        JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        DeviceInfo msg = new DeviceInfo();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Carrier wasn't set, but getter method returned an object.", msg.getCarrier());
        assertNull("Hardware wasn't set, but getter method returned an object.", msg.getHardware());
        assertNull("Firmware rev wasn't set, but getter method returned an object.", msg.getFirmwareRev());
        assertNull("OS wasn't set, but getter method returned an object.", msg.getOs());
        assertNull("OS version wasn't set, but getter method returned an object.", msg.getOsVersion());
        assertNull("Max number RFCOMM ports wasn't set, but getter method returned an object.",
                msg.getMaxNumberRFCOMMPorts());
    }
  //TODO: remove testCopy()?
    /*
    public void testCopy(){
        DeviceInfo copy = new DeviceInfo(msg);

        assertNotSame("Object was not copied.", copy, msg);

        String error = "Object data was not copied correctly.";
        assertEquals(error, copy.getCarrier(), msg.getCarrier());
        assertEquals(error, copy.getHardware(), msg.getHardware());
        assertEquals(error, copy.getFirmwareRev(), msg.getFirmwareRev());
        assertEquals(error, copy.getOs(), msg.getOs());
        assertEquals(error, copy.getOsVersion(), msg.getOsVersion());
        assertEquals(error, copy.getMaxNumberRFCOMMPorts(), msg.getMaxNumberRFCOMMPorts());
    }
    */
}
