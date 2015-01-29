package com.smartdevicelink.test.rpc.responses;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;

public class GetDTCsResponseTests extends BaseRpcTests{

    private static final List<String> DTC_LIST = Arrays.asList(new String[] { "0x1FED: Tire Pressure Low",
            "0x84F2: Passenger Window Open Circuit Fault" });

    @Override
    protected RPCMessage createMessage(){
        GetDTCsResponse msg = new GetDTCsResponse();

        msg.setDtc(DTC_LIST);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_DTCS;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetDTCsResponse.KEY_DTC, JsonUtils.createJsonArray(DTC_LIST));
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testDtcList(){
        List<String> cmdId = ( (GetDTCsResponse) msg ).getDtc();

        assertNotSame("DTC list wasn't defensive copied.", DTC_LIST, cmdId);
        assertEquals("DTC list size didn't match expected size.", DTC_LIST.size(), cmdId.size());

        for(int i = 0; i < DTC_LIST.size(); i++){
            assertEquals("DTC value at index " + i + " didn't match expected value.", DTC_LIST.get(i), cmdId.get(i));
        }
    }

    public void testNull(){
        GetDTCsResponse msg = new GetDTCsResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("DTC list wasn't set, but getter method returned an object.", msg.getDtc());
    }
}
