package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetWayPointsResponse;
import com.smartdevicelink.proxy.rpc.LocationDetails;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by austinkirk on 6/6/17.
 */

public class GetWayPointsResponseTests extends BaseRpcTests {
    List<LocationDetails> waypoints = new ArrayList<LocationDetails>();

    @Override
    protected RPCMessage createMessage() {

        waypoints.add(TestValues.GENERAL_LOCATIONDETAILS);
        waypoints.add(TestValues.GENERAL_LOCATIONDETAILS);

        GetWayPointsResponse getWayPointsResponse = new GetWayPointsResponse();
        getWayPointsResponse.setWayPoints(waypoints);

        return getWayPointsResponse;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.GET_WAY_POINTS.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_LOCATIONDETAILS.getStore()));
            jsonArray.put(JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_LOCATIONDETAILS.getStore()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            result.put(GetWayPointsResponse.KEY_WAY_POINTS, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    @Test
    public void testRpcValues() {

        // Test Values
        List<LocationDetails> testWPs = ((GetWayPointsResponse) msg).getWayPoints();

        // Valid Tests
        assertEquals(TestValues.MATCH, waypoints, testWPs);

        // Invalid/Null Tests
        GetWayPointsResponse msg = new GetWayPointsResponse();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getWayPoints());
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
            GetWayPointsResponse cmd = new GetWayPointsResponse(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            JSONArray locArray = JsonUtils.readJsonArrayFromJsonObject(parameters, GetWayPointsResponse.KEY_WAY_POINTS);
            List<LocationDetails> locationList = new ArrayList<LocationDetails>();
            for (int index = 0; index < locArray.length(); index++) {
                LocationDetails det = new LocationDetails(JsonRPCMarshaller.deserializeJSONObject((JSONObject) locArray.get(index)));
                locationList.add(det);
            }
            List<LocationDetails> dets = cmd.getWayPoints();
            assertEquals(TestValues.MATCH, locationList.size(), dets.size());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
