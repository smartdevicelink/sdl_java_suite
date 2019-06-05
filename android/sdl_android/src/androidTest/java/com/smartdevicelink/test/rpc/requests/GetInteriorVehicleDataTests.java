package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleData;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.GetInteriorVehicleData}
 */
public class GetInteriorVehicleDataTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage(){
        GetInteriorVehicleData msg = new GetInteriorVehicleData();

        msg.setModuleType(Test.GENERAL_MODULETYPE);
        msg.setSubscribe(Test.GENERAL_BOOLEAN);

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
            result.put(GetInteriorVehicleData.KEY_MODULE_TYPE, Test.GENERAL_MODULETYPE);
            result.put(GetInteriorVehicleData.KEY_SUBSCRIBE, Test.GENERAL_BOOLEAN);
        }catch(JSONException e){
            fail(Test.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        ModuleType testModuleType = ( (GetInteriorVehicleData) msg ).getModuleType();
        boolean testSubscribed = ( (GetInteriorVehicleData) msg ).getSubscribe();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_MODULETYPE, testModuleType);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, testSubscribed);

        // Invalid/Null Tests
        GetInteriorVehicleData msg = new GetInteriorVehicleData();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getModuleType());
        assertNull(Test.NULL, msg.getSubscribe());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
        JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
        assertNotNull(Test.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            GetInteriorVehicleData cmd = new GetInteriorVehicleData(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(Test.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetInteriorVehicleData.KEY_MODULE_TYPE).toString(), cmd.getModuleType().toString());
            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetInteriorVehicleData.KEY_SUBSCRIBE), cmd.getSubscribe());
        }catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }
    }
}