package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.DTC;
import com.smartdevicelink.test.utils.JsonUtils;

public class DTCTests extends TestCase{

    private static final String ECU_ID = "511";
    private static final String STATUS = "0x00";

    private DTC                 msg;

    @Override
    public void setUp(){
        msg = new DTC();

        msg.setIdentifier(ECU_ID);
        msg.setStatusByte(STATUS);
    }

    public void testIdentifier(){
        String copy = msg.getIdentifier();
        assertEquals("Input value didn't match expected value.", ECU_ID, copy);
    }

    public void testStatusByte(){
        String copy = msg.getStatusByte();
        assertEquals("Input value didn't match expected value.", STATUS, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DTC.KEY_IDENTIFIER, ECU_ID);
            reference.put(DTC.KEY_STATUS_BYTE, STATUS);

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
        DTC msg = new DTC();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Identifier wasn't set, but getter method returned an object.", msg.getIdentifier());
        assertNull("Status wasn't set, but getter method returned an object.", msg.getStatusByte());
    }
}
