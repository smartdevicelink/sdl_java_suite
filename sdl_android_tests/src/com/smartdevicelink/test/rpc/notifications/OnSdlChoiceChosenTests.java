package com.smartdevicelink.test.rpc.notifications;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.OnSdlChoiceChosen;
import com.smartdevicelink.proxy.rpc.OnSdlChoiceChosen.SdlChoice;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class OnSdlChoiceChosenTests extends BaseRpcTests{

    private static final TriggerSource TRIGGER_SOURCE = TriggerSource.TS_MENU;
    private static final String        NAME           = "Choice 1";
    private static final String        SECONDARY      = "Second text";
    private static final String        TERTIARY       = "Third text";
    private static final int           ID             = 61413;
    private final List<String>  VR_COMMANDS    = Arrays.asList(new String[] { "Choice 1", "One" });

    private Choice                     choice;

    @Override
    protected RPCMessage createMessage(){
        OnSdlChoiceChosen msg = new OnSdlChoiceChosen();
        //TODO: convert into SdlChoice
        choice = new Choice();
        choice.setMenuName(NAME);
        choice.setSecondaryText(SECONDARY);
        choice.setTertiaryText(TERTIARY);
        choice.setChoiceID(ID);
        choice.setVrCommands(VR_COMMANDS);
        
        SdlChoice sdlChoice = msg.new SdlChoice(choice);
        
        msg.setTriggerSource(TRIGGER_SOURCE);
		msg.setSdlChoice(sdlChoice);
        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_SDL_CHOICE_CHOSEN;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject(), choiceJson = new JSONObject();

        try{
            choiceJson.put(Choice.KEY_CHOICE_ID, ID);
            choiceJson.put(Choice.KEY_MENU_NAME, NAME);
            choiceJson.put(Choice.KEY_SECONDARY_TEXT, SECONDARY);
            choiceJson.put(Choice.KEY_TERTIARY_TEXT, TERTIARY);
            choiceJson.put(Choice.KEY_VR_COMMANDS, JsonUtils.createJsonArray(VR_COMMANDS));

            result.put(OnSdlChoiceChosen.KEY_TRIGGER_SOURCE, TRIGGER_SOURCE);
            result.put(OnSdlChoiceChosen.KEY_SDL_CHOICE, choiceJson);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testSmartDeviceLinkChoice(){
        SdlChoice data = ( (OnSdlChoiceChosen) msg ).getSdlChoice();
        //TODO: verify this works
        assertTrue("Data didn't match input data.", Validator.validateChoice(choice, data.getChoice()));
    }

    public void testTriggerSource(){
        TriggerSource data = ( (OnSdlChoiceChosen) msg ).getTriggerSource();
        assertEquals("Data didn't match input data.", TRIGGER_SOURCE, data);
    }

    public void testNull(){
    	OnSdlChoiceChosen msg = new OnSdlChoiceChosen();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Trigger source wasn't set, but getter method returned an object.", msg.getTriggerSource());
        assertNull("Choice wasn't set, but getter method returned an object.", msg.getSdlChoice());
    }
}
