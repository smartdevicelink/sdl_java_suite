package com.smartdevicelink.test.rpc.notifications;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnTouchEvent}
 */
public class OnTouchEventTests extends BaseRpcTests{

	@Override
    protected RPCMessage createMessage(){
        OnTouchEvent msg = new OnTouchEvent();
        msg.setType(Test.GENERAL_TOUCHTYPE);
        msg.setEvent(Test.GENERAL_TOUCHEVENT_LIST);

        return msg;
    }    

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_TOUCH_EVENT.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{            
            result.put(OnTouchEvent.KEY_TYPE,Test.GENERAL_TOUCHTYPE);
            result.put(OnTouchEvent.KEY_EVENT,  Test.JSON_TOUCHEVENTS);
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
        TouchType type = ( (OnTouchEvent) msg ).getType();
        List<TouchEvent> event = ( (OnTouchEvent) msg ).getEvent();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_TOUCHTYPE, type);
        assertEquals(Test.MATCH, Test.GENERAL_TOUCHEVENT_LIST.size(), event.size());
        for(int i=0; i< Test.GENERAL_TOUCHEVENT_LIST.size(); i++){
            TouchEvent referenceEvent = Test.GENERAL_TOUCHEVENT_LIST.get(i);
            TouchEvent dataEvent = event.get(i);
            assertTrue(Test.TRUE, Validator.validateTouchEvent(referenceEvent, dataEvent));
        }
        
        // Invalid/Null Tests
        OnTouchEvent msg = new OnTouchEvent();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getType());
        assertNull(Test.NULL, msg.getEvent());
    }
}