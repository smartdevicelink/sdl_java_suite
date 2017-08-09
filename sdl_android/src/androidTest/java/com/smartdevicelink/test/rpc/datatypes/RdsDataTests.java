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

        msg.setPs(Test.GENERAL_STRING);
        msg.setRt(Test.GENERAL_STRING);
        msg.setCt(Test.GENERAL_STRING);
        msg.setPi(Test.GENERAL_STRING);
        msg.setReg(Test.GENERAL_STRING);
        msg.setTp(Test.GENERAL_BOOLEAN);
        msg.setTa(Test.GENERAL_BOOLEAN);
        msg.setPty(Test.GENERAL_INT);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
        // Test Values
        String ps = msg.getPs();
        String rt = msg.getRt();
        String ct = msg.getCt();
        String pi = msg.getPi();
        int pty = msg.getPty();
        boolean tp = msg.getTp();
        boolean ta = msg.getTa();
        String reg = msg.getReg();

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

        assertNull(Test.NULL, msg.getPs());
        assertNull(Test.NULL, msg.getRt());
        assertNull(Test.NULL, msg.getCt());
        assertNull(Test.NULL, msg.getPi());
        assertNull(Test.NULL, msg.getReg());
        assertNull(Test.NULL, msg.getTp());
        assertNull(Test.NULL, msg.getTa());
        assertNull(Test.NULL, msg.getPty());
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