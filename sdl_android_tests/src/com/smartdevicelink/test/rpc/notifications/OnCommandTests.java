package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.test.BaseRpcTests;

public class OnCommandTests extends BaseRpcTests{

    private static final int           ID             = 1545681;
    private static final TriggerSource TRIGGER_SOURCE = TriggerSource.TS_MENU;

    @Override
    protected RPCMessage createMessage(){
        OnCommand msg = new OnCommand();

        msg.setCmdID(ID);
        msg.setTriggerSource(TRIGGER_SOURCE);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_COMMAND;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnCommand.KEY_CMD_ID, ID);
            result.put(OnCommand.KEY_TRIGGER_SOURCE, TRIGGER_SOURCE);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testCmdID(){
        int cmdId = ( (OnCommand) msg ).getCmdID();
        assertEquals("Command ID didn't match input command ID.", ID, cmdId);
    }

    public void testTriggerSource(){
        TriggerSource cmdId = ( (OnCommand) msg ).getTriggerSource();
        assertEquals("Trigger source didn't match input trigger source.", TRIGGER_SOURCE, cmdId);
    }

    public void testNull(){
        OnCommand msg = new OnCommand();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Trigger source wasn't set, but getter method returned an object.", msg.getTriggerSource());
        assertNull("Command ID wasn't set, but getter method returned an object.", msg.getCmdID());
    }
}
