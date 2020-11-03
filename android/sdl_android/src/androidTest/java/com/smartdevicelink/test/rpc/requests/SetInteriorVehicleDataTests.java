package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ModuleData;
import com.smartdevicelink.proxy.rpc.SetInteriorVehicleData;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.SetInteriorVehicleData}
 */
public class SetInteriorVehicleDataTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        SetInteriorVehicleData msg = new SetInteriorVehicleData();

        msg.setModuleData(TestValues.GENERAL_MODULEDATA);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.SET_INTERIOR_VEHICLE_DATA.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(SetInteriorVehicleData.KEY_MODULE_DATA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_MODULEDATA.getStore()));
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    @Test
    public void testRpcValues() {
        // Test Values
        ModuleData testModuleData = ((SetInteriorVehicleData) msg).getModuleData();

        // Valid Tests
        assertTrue(TestValues.TRUE, Validator.validateModuleData(TestValues.GENERAL_MODULEDATA, testModuleData));

        // Invalid/Null Tests
        SetInteriorVehicleData msg = new SetInteriorVehicleData();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getModuleData());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor() {
        JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
        assertNotNull(TestValues.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            SetInteriorVehicleData cmd = new SetInteriorVehicleData(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            ModuleData referenceModuleData = new ModuleData(JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(parameters, SetInteriorVehicleData.KEY_MODULE_DATA)));

            assertTrue(TestValues.TRUE, Validator.validateModuleData(referenceModuleData, cmd.getModuleData()));
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}