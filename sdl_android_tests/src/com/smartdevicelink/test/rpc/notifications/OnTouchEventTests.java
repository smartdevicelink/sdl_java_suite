package com.smartdevicelink.test.rpc.notifications;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.Validator;


public class OnTouchEventTests extends BaseRpcTests{

    private static final TouchType TOUCH_TYPE = TouchType.BEGIN;
    
    private static final int ID_1 = 5815;
    private static final int TIMESTAMP_1 = 185439051;
    private static final int X_1 = 28;
    private static final int Y_1 = 2;
    
    private static final int ID_2 = 48916;
    private static final int TIMESTAMP_2 = 185439063;
    private static final int X_2 = 30;
    private static final int Y_2 = 16;
    
    private List<TouchEvent> touchEvents;
    
    @Override
    protected RPCMessage createMessage(){
        OnTouchEvent msg = new OnTouchEvent();

        createTouchEvents();
        
        msg.setType(TOUCH_TYPE);
        msg.setEvent(touchEvents);

        return msg;
    }
    
    private void createTouchEvents(){
        touchEvents = new ArrayList<TouchEvent>(2);
        
        TouchCoord touchCoordinate = new TouchCoord();
        touchCoordinate.setX(X_1);
        touchCoordinate.setY(Y_1);
        
        TouchEvent event = new TouchEvent();
        event.setId(ID_1);
        
        List<Integer> tsList = new ArrayList<Integer>();
        tsList.add(TIMESTAMP_1);
        event.setTs(tsList);
        
        List<TouchCoord> cList = new ArrayList<TouchCoord>();
        cList.add(touchCoordinate);
        event.setC(cList);
        
        touchEvents.add(event);
        
        touchCoordinate = new TouchCoord();
        touchCoordinate.setX(X_2);
        touchCoordinate.setY(Y_2);
        
        event = new TouchEvent();
        event.setId(ID_2);
        
        List<Integer> tsList2 = new ArrayList<Integer>();
        tsList2.add(TIMESTAMP_2);
        event.setTs(tsList2);
        
        List<TouchCoord> cList2 = new ArrayList<TouchCoord>();
        cList2.add(touchCoordinate);
        event.setC(cList2);
        
        touchEvents.add(event);
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_TOUCH_EVENT;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();
        JSONObject coordJsonObject, eventJsonObject;
        JSONArray eventJsonArray = new JSONArray();

        try{
            coordJsonObject = new JSONObject();
            eventJsonObject = new JSONObject();
            
            coordJsonObject.put(TouchCoord.KEY_X, X_1);
            coordJsonObject.put(TouchCoord.KEY_Y, Y_1);
            
            eventJsonObject.put(TouchEvent.KEY_C, coordJsonObject);
            eventJsonObject.put(TouchEvent.KEY_ID, ID_1);
            eventJsonObject.put(TouchEvent.KEY_TS, TIMESTAMP_1);
            
            eventJsonArray.put(eventJsonObject);

            coordJsonObject = new JSONObject();
            eventJsonObject = new JSONObject();
            
            coordJsonObject.put(TouchCoord.KEY_X, X_2);
            coordJsonObject.put(TouchCoord.KEY_Y, Y_2);
            
            eventJsonObject.put(TouchEvent.KEY_C, coordJsonObject);
            eventJsonObject.put(TouchEvent.KEY_ID, ID_2);
            eventJsonObject.put(TouchEvent.KEY_TS, TIMESTAMP_2);

            eventJsonArray.put(eventJsonObject);
            
            result.put(OnTouchEvent.KEY_TYPE, TOUCH_TYPE);
            result.put(OnTouchEvent.KEY_EVENT, eventJsonArray);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testTouchType(){
        TouchType data = ( (OnTouchEvent) msg ).getType();
        assertEquals("Data didn't match input data.", TOUCH_TYPE, data);
    }
    
    public void testEvent(){
        List<TouchEvent> data = ( (OnTouchEvent) msg ).getEvent();
        assertNotSame("List object wasn't defensive copied.", touchEvents, data);
        assertEquals("List size didn't match expected size.", touchEvents.size(), data.size());
        for(int i=0; i<touchEvents.size(); i++){
            TouchEvent referenceEvent = touchEvents.get(i);
            TouchEvent dataEvent = data.get(i);
            assertNotSame("Data object wasn't defensive copied.", referenceEvent, dataEvent);
            assertTrue("", Validator.validateTouchEvent(referenceEvent, dataEvent));
        }
    }

    public void testNull(){
        OnTouchEvent msg = new OnTouchEvent();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Touch type wasn't set, but getter method returned an object.", msg.getType());
        assertNull("Touch event wasn't set, but getter method returned an object.", msg.getEvent());
    }
}
