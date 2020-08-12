package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleData;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.GetInteriorVehicleData}
 */
public class GetInteriorVehicleDataTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){
        GetInteriorVehicleData msg = new GetInteriorVehicleData();

        msg.setModuleType(TestValues.GENERAL_MODULETYPE);
        msg.setSubscribe(TestValues.GENERAL_BOOLEAN);
        msg.setModuleId(TestValues.GENERAL_STRING);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_INTERIOR_VEHICLE_DATA.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetInteriorVehicleData.KEY_MODULE_TYPE, TestValues.GENERAL_MODULETYPE);
            result.put(GetInteriorVehicleData.KEY_SUBSCRIBE, TestValues.GENERAL_BOOLEAN);
            result.put(GetInteriorVehicleData.KEY_MODULE_ID, TestValues.GENERAL_STRING);
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
        ModuleType testModuleType = ( (GetInteriorVehicleData) msg ).getModuleType();
        boolean testSubscribed = ( (GetInteriorVehicleData) msg ).getSubscribe();
        String testModuleId = ((GetInteriorVehicleData) msg).getModuleId();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULETYPE, testModuleType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, testSubscribed);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testModuleId);

        // Invalid/Null Tests
        GetInteriorVehicleData msg = new GetInteriorVehicleData();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getModuleType());
        assertNull(TestValues.NULL, msg.getSubscribe());
        assertNull(TestValues.NULL, msg.getModuleId());
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
            GetInteriorVehicleData cmd = new GetInteriorVehicleData(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetInteriorVehicleData.KEY_MODULE_TYPE).toString(), cmd.getModuleType().toString());
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetInteriorVehicleData.KEY_SUBSCRIBE), cmd.getSubscribe());
            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetInteriorVehicleData.KEY_MODULE_ID), cmd.getModuleId());
        }catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}