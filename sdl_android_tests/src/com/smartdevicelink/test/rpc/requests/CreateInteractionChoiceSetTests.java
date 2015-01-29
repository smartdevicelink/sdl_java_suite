package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.Logger;
import com.smartdevicelink.test.utils.Validator;

public class CreateInteractionChoiceSetTests extends BaseRpcTests{

    private static final String TAG = "CreateInteractionChoiceSetTests";
    
    private static final int    CHOICE_SET_ID          = 16164;
    private static final int    CHOICE1_ID             = 5163;
    private static final String CHOICE1_NAME           = "Choice #1";
    private static final String CHOICE1_SECONDARY_TEXT = "Second text";
    private static final int    CHOICE2_ID             = 8151;
    private static final String CHOICE2_NAME           = "Choice #2";
    private static final String CHOICE2_SECONDARY_TEXT = "Second text";

    private List<Choice>        choiceList;

    @Override
    protected RPCMessage createMessage(){
        CreateInteractionChoiceSet msg = new CreateInteractionChoiceSet();

        createChoiceList();

        msg.setInteractionChoiceSetID(CHOICE_SET_ID);
        msg.setChoiceSet(choiceList);

        return msg;
    }

    private void createChoiceList(){
        choiceList = new ArrayList<Choice>(2);

        Choice choice = new Choice();
        choice.setChoiceID(CHOICE1_ID);
        choice.setMenuName(CHOICE1_NAME);
        choice.setSecondaryText(CHOICE1_SECONDARY_TEXT);
        choiceList.add(choice);

        choice = new Choice();
        choice.setChoiceID(CHOICE2_ID);
        choice.setMenuName(CHOICE2_NAME);
        choice.setSecondaryText(CHOICE2_SECONDARY_TEXT);
        choiceList.add(choice);
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.CREATE_INTERACTION_CHOICE_SET;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject(), choice1 = new JSONObject(), choice2 = new JSONObject();
        JSONArray choiceArray = new JSONArray();

        try{
            choice1.put(Choice.KEY_CHOICE_ID, CHOICE1_ID);
            choice1.put(Choice.KEY_MENU_NAME, CHOICE1_NAME);
            choice1.put(Choice.KEY_SECONDARY_TEXT, CHOICE1_SECONDARY_TEXT);

            choice2.put(Choice.KEY_CHOICE_ID, CHOICE2_ID);
            choice2.put(Choice.KEY_MENU_NAME, CHOICE2_NAME);
            choice2.put(Choice.KEY_SECONDARY_TEXT, CHOICE2_SECONDARY_TEXT);

            choiceArray.put(choice1);
            choiceArray.put(choice2);

            result.put(CreateInteractionChoiceSet.KEY_INTERACTION_CHOICE_SET_ID, CHOICE_SET_ID);
            result.put(CreateInteractionChoiceSet.KEY_CHOICE_SET, choiceArray);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testChoiceSetId(){
        int cmdId = ( (CreateInteractionChoiceSet) msg ).getInteractionChoiceSetID();
        assertEquals("Command ID didn't match input command ID.", CHOICE_SET_ID, cmdId);
    }

    public void testChoiceList(){
        List<Choice> copy = ( (CreateInteractionChoiceSet) msg ).getChoiceSet();

        assertNotSame("Choice list was not defensive copied.", choiceList, copy);
        assertEquals("Choice list size didn't match expected size.", choiceList.size(), copy.size());

        for(int i = 0; i < copy.size(); i++){
            log("validating choice at index " + i + ": \"" + choiceList.get(i) + "\" vs. \"" + copy.get(i) + "\"");
            assertTrue("Choice at index " + i + " didn't match expected value.",
                    Validator.validateChoice(choiceList.get(i), copy.get(i)));
        }
    }

    public void testNull(){
        CreateInteractionChoiceSet msg = new CreateInteractionChoiceSet();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Choice list wasn't set, but getter method returned an object.", msg.getChoiceSet());
        assertNull("Choice set ID wasn't set, but getter method returned an object.", msg.getInteractionChoiceSetID());
    }
    
    private static void log(String msg){
        Logger.log(TAG, msg);
    }

}
