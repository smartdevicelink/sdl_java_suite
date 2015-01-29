package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetDTCs;
import com.smartdevicelink.test.BaseRpcTests;

public class GetDTCsTests extends BaseRpcTests{

    private static final int ECU_NAME = 1589;
    private static final int DTC_MASK = 0;

    @Override
    protected RPCMessage createMessage(){
        GetDTCs msg = new GetDTCs();

        msg.setEcuName(ECU_NAME);
        msg.setDtcMask(DTC_MASK);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_DTCS;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetDTCs.KEY_ECU_NAME, ECU_NAME);
            result.put(GetDTCs.KEY_DTC_MASK, DTC_MASK);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testEcuName(){
        int cmdId = ( (GetDTCs) msg ).getEcuName();
        assertEquals("ECU name didn't match input ECU name.", ECU_NAME, cmdId);
    }

    public void testDtcMask(){
        int cmdId = ( (GetDTCs) msg ).getDtcMask();
        assertEquals("DTC mask didn't match input DTC mask.", DTC_MASK, cmdId);
    }

    public void testNull(){
        GetDTCs msg = new GetDTCs();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("ECU name wasn't set, but getter method returned an object.", msg.getEcuName());
        assertNull("DTC mask wasn't set, but getter method returned an object.", msg.getDtcMask());
    }
}
