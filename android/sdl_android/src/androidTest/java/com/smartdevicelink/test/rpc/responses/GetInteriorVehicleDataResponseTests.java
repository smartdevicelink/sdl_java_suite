package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.ModuleData;
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
 * {@link com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataResponse}
 */
public class GetInteriorVehicleDataResponseTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){

        GetInteriorVehicleDataResponse msg = new GetInteriorVehicleDataResponse();

        msg.setIsSubscribed(TestValues.GENERAL_BOOLEAN);
        msg.setModuleData(TestValues.GENERAL_MODULEDATA);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_INTERIOR_VEHICLE_DATA.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetInteriorVehicleDataResponse.KEY_IS_SUBSCRIBED, TestValues.GENERAL_BOOLEAN);
            result.put(GetInteriorVehicleDataResponse.KEY_MODULE_DATA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_MODULEDATA.getStore()));
        }catch(JSONException e){
            fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    @Test
    public void testRpcValues () {
        // Test Values
        boolean testIsSubscribed = ( (GetInteriorVehicleDataResponse) msg ).getIsSubscribed();
        ModuleData testModuleData = ( (GetInteriorVehicleDataResponse) msg ).getModuleData();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, testIsSubscribed);
        assertTrue(TestValues.TRUE, Validator.validateModuleData(TestValues.GENERAL_MODULEDATA, testModuleData));

        // Invalid/Null Tests
        GetInteriorVehicleDataResponse msg = new GetInteriorVehicleDataResponse();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getIsSubscribed());
        assertNull(TestValues.NULL, msg.getModuleData());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
        JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
        assertNotNull(TestValues.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            GetInteriorVehicleDataResponse cmd = new GetInteriorVehicleDataResponse (hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            ModuleData testModuleData = new ModuleData(JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(parameters, GetInteriorVehicleDataResponse.KEY_MODULE_DATA)));
            ModuleData cmdModuleData = cmd.getModuleData();

            assertTrue(TestValues.TRUE, Validator.validateModuleData(testModuleData, cmdModuleData) );
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetInteriorVehicleDataResponse.KEY_IS_SUBSCRIBED), cmd.getIsSubscribed());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
