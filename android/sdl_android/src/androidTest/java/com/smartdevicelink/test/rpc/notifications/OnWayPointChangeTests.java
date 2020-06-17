package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetWayPointsResponse;
import com.smartdevicelink.proxy.rpc.LocationDetails;
import com.smartdevicelink.proxy.rpc.OnWayPointChange;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by austinkirk on 6/7/17.
 */

public class OnWayPointChangeTests extends BaseRpcTests {
    @Override
    protected RPCMessage createMessage(){
        OnWayPointChange msg = new OnWayPointChange();

        List<LocationDetails> list = new ArrayList<>();
        list.add(TestValues.GENERAL_LOCATIONDETAILS);
        list.add(TestValues.GENERAL_LOCATIONDETAILS);

        msg.setWayPoints(list);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_WAY_POINT_CHANGE.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
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
    public void testRpcValues () {
        // Test Values
        List<LocationDetails> list = ((OnWayPointChange) msg).getWayPoints();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LOCATIONDETAILS, list.get(0));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LOCATIONDETAILS, list.get(1));

        // Invalid/Null Tests
        OnWayPointChange msg = new OnWayPointChange();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getWayPoints());
    }
}
