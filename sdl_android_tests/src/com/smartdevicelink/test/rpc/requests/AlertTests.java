package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.test.BaseRpcTests;

public class AlertTests extends BaseRpcTests{

    private static final int     DURATION           = 500;
    private static final String  ALERT_TEXT_1       = "Line #1";
    private static final String  ALERT_TEXT_2       = "Line #2";
    private static final String  ALERT_TEXT_3       = "Line #3";
    private static final boolean PLAY_TONE          = true;
    private static final boolean PROGRESS_INDICATOR = true;

    @Override
    protected RPCMessage createMessage(){
        Alert msg = new Alert();

        msg.setDuration(DURATION);
        msg.setAlertText1(ALERT_TEXT_1);
        msg.setAlertText2(ALERT_TEXT_2);
        msg.setAlertText3(ALERT_TEXT_3);
        msg.setPlayTone(PLAY_TONE);
        msg.setProgressIndicator(PROGRESS_INDICATOR);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ALERT;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(Alert.KEY_DURATION, DURATION);
            result.put(Alert.KEY_ALERT_TEXT_1, ALERT_TEXT_1);
            result.put(Alert.KEY_ALERT_TEXT_2, ALERT_TEXT_2);
            result.put(Alert.KEY_ALERT_TEXT_3, ALERT_TEXT_3);
            result.put(Alert.KEY_PLAY_TONE, PLAY_TONE);
            result.put(Alert.KEY_PROGRESS_INDICATOR, PROGRESS_INDICATOR);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testDuration(){
        int duration = ( (Alert) msg ).getDuration();
        assertEquals("Duration didn't match input duration.", DURATION, duration);
    }

    public void testAlertText1(){
        String alertText1 = ( (Alert) msg ).getAlertText1();
        assertEquals("Alert text 1 didn't match input text.", ALERT_TEXT_1, alertText1);
    }

    public void testAlertText2(){
        String alertText2 = ( (Alert) msg ).getAlertText2();
        assertEquals("Alert text 2 didn't match input text.", ALERT_TEXT_2, alertText2);
    }

    public void testAlertText3(){
        String alertText3 = ( (Alert) msg ).getAlertText3();
        assertEquals("Alert text 3 didn't match input text.", ALERT_TEXT_3, alertText3);
    }

    public void testPlayTone(){
        boolean playTone = ( (Alert) msg ).getPlayTone();
        assertEquals("Play tone didn't match input play tone.", PLAY_TONE, playTone);
    }

    public void testProgressIndicator(){
        boolean progressIndicator = ( (Alert) msg ).getProgressIndicator();
        assertEquals("Progress indicator didn't match input progress indicator.", PROGRESS_INDICATOR, progressIndicator);
    }

    public void testNull(){
        Alert msg = new Alert();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Alert text 1 wasn't set, but getter method returned an object.", msg.getAlertText1());
        assertNull("Alert text 2 wasn't set, but getter method returned an object.", msg.getAlertText2());
        assertNull("Alert text 3 wasn't set, but getter method returned an object.", msg.getAlertText3());
        assertNull("Duration wasn't set, but getter method returned an object.", msg.getDuration());
        assertNull("Play tone wasn't set, but getter method returned an object.", msg.getPlayTone());
        assertNull("Progress indicator wasn't set, but getter method returned an object.", msg.getProgressIndicator());
    }

}
