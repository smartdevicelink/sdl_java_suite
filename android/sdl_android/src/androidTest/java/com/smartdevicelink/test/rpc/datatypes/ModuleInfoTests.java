package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Grid;
import com.smartdevicelink.proxy.rpc.ModuleInfo;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class ModuleInfoTests extends TestCase {
    private ModuleInfo msg;

    @Override
    public void setUp() {
        msg = new ModuleInfo();
        msg.setModuleId(TestValues.GENERAL_STRING);
        msg.setModuleLocation(TestValues.GENERAL_GRID);
        msg.setModuleServiceArea(TestValues.GENERAL_GRID);
        msg.setMultipleAccessAllowance(TestValues.GENERAL_BOOLEAN);
    }

    public void testRpcValues() {
        String id = msg.getModuleId();
        Grid loc = msg.getModuleLocation();
        Grid area = msg.getModuleServiceArea();
        boolean isAllowed = msg.getMultipleAccessAllowance();

        //valid tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, id);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_GRID, loc);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_GRID, area);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, isAllowed);

        //null test
        ModuleInfo msg = new ModuleInfo();
        assertNull(TestValues.NULL, msg.getModuleId());
        assertNull(TestValues.NULL, msg.getModuleLocation());
        assertNull(TestValues.NULL, msg.getModuleServiceArea());
        assertNull(TestValues.NULL, msg.getMultipleAccessAllowance());

        //test required params constructor
        ModuleInfo msg2 = new ModuleInfo(TestValues.GENERAL_STRING);
        assertEquals(TestValues.MATCH, msg2.getModuleId(), TestValues.GENERAL_STRING);
    }

    public void testJson() {
        JSONObject original = new JSONObject();
        try {
            original.put(ModuleInfo.KEY_MODULE_ID, TestValues.GENERAL_STRING);
            original.put(ModuleInfo.KEY_MODULE_LOCATION, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_GRID.getStore()));
            original.put(ModuleInfo.KEY_MODULE_SERVICE_AREA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_GRID.getStore()));
            original.put(ModuleInfo.KEY_MULTIPLE_ACCESS_ALLOWED, TestValues.GENERAL_BOOLEAN);

            JSONObject serialized = msg.serializeJSON();
            assertEquals(TestValues.MATCH, original.length(), serialized.length());

            Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(original);
            Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(serialized);
            assertTrue(TestValues.TRUE, Validator.validateModuleInfo(new ModuleInfo(h1), new ModuleInfo(h2)));
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
