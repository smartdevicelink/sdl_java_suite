package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetWayPoints;
import com.smartdevicelink.proxy.rpc.enums.WayPointType;
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
import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Created by austinkirk on 6/6/17.
 */

public class GetWayPointsTests extends BaseRpcTests {
    @Override
    protected RPCMessage createMessage(){
        GetWayPoints msg = new GetWayPoints();

        msg.setWayPointType(WayPointType.DESTINATION);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_WAY_POINTS.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetWayPoints.KEY_WAY_POINT_TYPE, WayPointType.DESTINATION);
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
        WayPointType testType      = ( (GetWayPoints) msg ).getWayPointType();

        // Valid Tests
        assertEquals(TestValues.MATCH, WayPointType.DESTINATION, testType);

        // Invalid/Null Tests
        GetWayPoints msg = new GetWayPoints();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getWayPointType());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
        JSONObject commandJson = JsonFileReader.readId(getContext(), getCommandType(), getMessageType());
        assertNotNull(TestValues.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            GetWayPoints cmd = new GetWayPoints(hash);
            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(TestValues.MATCH, WayPointType.valueForString(JsonUtils.readStringFromJsonObject(parameters, GetWayPoints.KEY_WAY_POINT_TYPE)), cmd.getWayPointType());
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
