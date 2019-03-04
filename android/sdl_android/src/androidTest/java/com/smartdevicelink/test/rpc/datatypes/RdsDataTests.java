package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.RdsData;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.RdsData}
 */
public class RdsDataTests extends TestCase{
	
    private RdsData msg;

    @Override
    public void setUp(){
        msg = new RdsData();

        msg.setProgramService(Test.GENERAL_STRING);
        msg.setRadioText(Test.GENERAL_STRING);
        msg.setClockText(Test.GENERAL_STRING);
        msg.setProgramIdentification(Test.GENERAL_STRING);
        msg.setRegion(Test.GENERAL_STRING);
        msg.setTrafficProgram(Test.GENERAL_BOOLEAN);
        msg.setTrafficAnnouncement(Test.GENERAL_BOOLEAN);
        msg.setProgramType(Test.GENERAL_INT);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
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
        assertEquals(Test.MATCH, Test.GENERAL_STRING, ps);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, rt);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, ct);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, pi);
        assertEquals(Test.MATCH, Test.GENERAL_INT, pty);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, tp);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, ta);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, reg);

        // Invalid/Null Tests
        RdsData msg = new RdsData();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getProgramService());
        assertNull(Test.NULL, msg.getRadioText());
        assertNull(Test.NULL, msg.getClockText());
        assertNull(Test.NULL, msg.getProgramIdentification());
        assertNull(Test.NULL, msg.getRegion());
        assertNull(Test.NULL, msg.getTrafficProgram());
        assertNull(Test.NULL, msg.getTrafficAnnouncement());
        assertNull(Test.NULL, msg.getProgramType());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(RdsData.KEY_PS, Test.GENERAL_STRING);
            reference.put(RdsData.KEY_RT, Test.GENERAL_STRING);
            reference.put(RdsData.KEY_CT, Test.GENERAL_STRING);
            reference.put(RdsData.KEY_PI, Test.GENERAL_STRING);
            reference.put(RdsData.KEY_PTY, Test.GENERAL_INT);
            reference.put(RdsData.KEY_TP, Test.GENERAL_BOOLEAN);
            reference.put(RdsData.KEY_TA, Test.GENERAL_BOOLEAN);
            reference.put(RdsData.KEY_REG, Test.GENERAL_STRING);

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