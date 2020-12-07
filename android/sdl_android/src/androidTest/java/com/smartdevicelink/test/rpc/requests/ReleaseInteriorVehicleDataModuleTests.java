package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ReleaseInteriorVehicleDataModule;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

public class ReleaseInteriorVehicleDataModuleTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        ReleaseInteriorVehicleDataModule msg = new ReleaseInteriorVehicleDataModule();
        msg.setModuleType(TestValues.GENERAL_MODULETYPE);
        msg.setModuleId(TestValues.GENERAL_STRING);
        return msg;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();
        try {
            result.put(ReleaseInteriorVehicleDataModule.KEY_MODULE_TYPE, TestValues.GENERAL_MODULETYPE);
            result.put(ReleaseInteriorVehicleDataModule.KEY_MODULE_ID, TestValues.GENERAL_STRING);
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
        return result;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.RELEASE_INTERIOR_VEHICLE_MODULE.toString();
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_REQUEST;
    }

    @Test
    public void testRpcValues() {
        ModuleType type = ((ReleaseInteriorVehicleDataModule) msg).getModuleType();
        String id = ((ReleaseInteriorVehicleDataModule) msg).getModuleId();

        //valid tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULETYPE, type);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, id);

        //null tests
        ReleaseInteriorVehicleDataModule msg = new ReleaseInteriorVehicleDataModule();
        assertNull(TestValues.NULL, msg.getModuleType());
        assertNull(TestValues.NULL, msg.getModuleId());

        // required param tests
        ReleaseInteriorVehicleDataModule msg2 = new ReleaseInteriorVehicleDataModule(TestValues.GENERAL_MODULETYPE);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULETYPE, msg2.getModuleType());
    }

    @Test
    public void testJsonConstructor() {
        JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
        assertNotNull(TestValues.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            ReleaseInteriorVehicleDataModule cmd = new ReleaseInteriorVehicleDataModule(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, ReleaseInteriorVehicleDataModule.KEY_MODULE_TYPE).toString(), cmd.getModuleType().toString());
            assertEquals(TestValues.MATCH, JsonUtils.readStringListFromJsonObject(parameters, ReleaseInteriorVehicleDataModule.KEY_MODULE_ID), cmd.getModuleId());
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
