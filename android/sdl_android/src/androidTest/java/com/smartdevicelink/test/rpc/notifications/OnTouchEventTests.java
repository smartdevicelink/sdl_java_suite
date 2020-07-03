package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnTouchEvent}
 */
public class OnTouchEventTests extends BaseRpcTests{

	@Override
    protected RPCMessage createMessage(){
        OnTouchEvent msg = new OnTouchEvent();
        msg.setType(TestValues.GENERAL_TOUCHTYPE);
        msg.setEvent(TestValues.GENERAL_TOUCHEVENT_LIST);

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
            result.put(OnTouchEvent.KEY_TYPE, TestValues.GENERAL_TOUCHTYPE);
            result.put(OnTouchEvent.KEY_EVENT,  TestValues.JSON_TOUCHEVENTS);
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
        TouchType type = ( (OnTouchEvent) msg ).getType();
        List<TouchEvent> event = ( (OnTouchEvent) msg ).getEvent();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TOUCHTYPE, type);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TOUCHEVENT_LIST.size(), event.size());
        for(int i = 0; i< TestValues.GENERAL_TOUCHEVENT_LIST.size(); i++){
            TouchEvent referenceEvent = TestValues.GENERAL_TOUCHEVENT_LIST.get(i);
            TouchEvent dataEvent = event.get(i);
            assertTrue(TestValues.TRUE, Validator.validateTouchEvent(referenceEvent, dataEvent));
        }
        
        // Invalid/Null Tests
        OnTouchEvent msg = new OnTouchEvent();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getType());
        assertNull(TestValues.NULL, msg.getEvent());
    }
}