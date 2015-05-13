package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.MyKey;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;
import com.smartdevicelink.test.JsonUtils;

public class MyKeyTests extends TestCase{

    private static final VehicleDataStatus E_911_OVERRIDE = VehicleDataStatus.ON;

    private MyKey                          msg;

    @Override
    public void setUp(){
        msg = new MyKey();

        msg.setE911Override(E_911_OVERRIDE);
    }

    public void testE911Override(){
        VehicleDataStatus copy = msg.getE911Override();
        assertEquals("Input value didn't match expected value.", E_911_OVERRIDE, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(MyKey.KEY_E_911_OVERRIDE, E_911_OVERRIDE);

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
        MyKey msg = new MyKey();
        assertNotNull("Null object creation failed.", msg);

        assertNull("E 911 override wasn't set, but getter method returned an object.", msg.getE911Override());
    }
}
