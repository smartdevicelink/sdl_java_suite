package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.PerformInteraction}
 */
public class PerformInteractionTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        PerformInteraction msg = new PerformInteraction();

        msg.setInitialPrompt(TestValues.GENERAL_TTSCHUNK_LIST);
        msg.setHelpPrompt(TestValues.GENERAL_TTSCHUNK_LIST);
        msg.setTimeoutPrompt(TestValues.GENERAL_TTSCHUNK_LIST);
        msg.setVrHelp(TestValues.GENERAL_VRHELPITEM_LIST);
        msg.setInteractionChoiceSetIDList(TestValues.GENERAL_INTEGER_LIST);
        msg.setInteractionLayout(TestValues.GENERAL_LAYOUTMODE);
        msg.setInitialText(TestValues.GENERAL_STRING);
        msg.setInteractionMode(TestValues.GENERAL_INTERACTIONMODE);
        msg.setTimeout(TestValues.GENERAL_INT);
        msg.setCancelID(TestValues.GENERAL_INTEGER);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.PERFORM_INTERACTION.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(PerformInteraction.KEY_INITIAL_PROMPT, TestValues.JSON_TTSCHUNKS);
            result.put(PerformInteraction.KEY_HELP_PROMPT, TestValues.JSON_TTSCHUNKS);
            result.put(PerformInteraction.KEY_TIMEOUT_PROMPT, TestValues.JSON_TTSCHUNKS);
            result.put(PerformInteraction.KEY_VR_HELP, TestValues.JSON_VRHELPITEMS);
            result.put(PerformInteraction.KEY_INTERACTION_CHOICE_SET_ID_LIST, JsonUtils.createJsonArray(TestValues.GENERAL_INTEGER_LIST));
            result.put(PerformInteraction.KEY_INTERACTION_LAYOUT, TestValues.GENERAL_LAYOUTMODE);
            result.put(PerformInteraction.KEY_INITIAL_TEXT, TestValues.GENERAL_STRING);
            result.put(PerformInteraction.KEY_INTERACTION_MODE, TestValues.GENERAL_INTERACTIONMODE);
            result.put(PerformInteraction.KEY_TIMEOUT, TestValues.GENERAL_INT);
            result.put(PerformInteraction.KEY_CANCEL_ID, TestValues.GENERAL_INTEGER);
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    @Test
    public void testRpcValues() {
        // Test Values
        List<TTSChunk> testInitialPrompt = ((PerformInteraction) msg).getInitialPrompt();
        List<TTSChunk> testHelpPrompt = ((PerformInteraction) msg).getHelpPrompt();
        List<TTSChunk> testTimeoutPrompt = ((PerformInteraction) msg).getTimeoutPrompt();
        List<VrHelpItem> testVrHelpItems = ((PerformInteraction) msg).getVrHelp();
        List<Integer> testChoiceSetIds = ((PerformInteraction) msg).getInteractionChoiceSetIDList();
        LayoutMode testLayout = ((PerformInteraction) msg).getInteractionLayout();
        String testInitialText = ((PerformInteraction) msg).getInitialText();
        InteractionMode testMode = ((PerformInteraction) msg).getInteractionMode();
        Integer testTimeout = ((PerformInteraction) msg).getTimeout();
        Integer testCancelID = ((PerformInteraction) msg).getCancelID();

        // Valid Tests
        assertTrue(TestValues.TRUE, Validator.validateTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST, testInitialPrompt));
        assertTrue(TestValues.TRUE, Validator.validateTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST, testHelpPrompt));
        assertTrue(TestValues.TRUE, Validator.validateTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST, testTimeoutPrompt));
        assertTrue(TestValues.TRUE, Validator.validateVrHelpItems(TestValues.GENERAL_VRHELPITEM_LIST, testVrHelpItems));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER_LIST, testChoiceSetIds);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LAYOUTMODE, testLayout);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testInitialText);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INTERACTIONMODE, testMode);
        assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, testTimeout);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, testCancelID);

        // Invald/Null Tests
        PerformInteraction msg = new PerformInteraction();
        assertNotNull(TestValues.NOT_NULL, msg);

        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getInitialPrompt());
        assertNull(TestValues.NULL, msg.getHelpPrompt());
        assertNull(TestValues.NULL, msg.getTimeoutPrompt());
        assertNull(TestValues.NULL, msg.getVrHelp());
        assertNull(TestValues.NULL, msg.getInteractionChoiceSetIDList());
        assertNull(TestValues.NULL, msg.getInteractionLayout());
        assertNull(TestValues.NULL, msg.getInitialText());
        assertNull(TestValues.NULL, msg.getInteractionMode());
        assertNull(TestValues.NULL, msg.getTimeout());
        assertNull(TestValues.NULL, msg.getCancelID());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor() {
        JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
        assertNotNull(TestValues.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            PerformInteraction cmd = new PerformInteraction(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformInteraction.KEY_INITIAL_TEXT), cmd.getInitialText());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformInteraction.KEY_INTERACTION_MODE), cmd.getInteractionMode().toString());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, PerformInteraction.KEY_CANCEL_ID), cmd.getCancelID());

            List<Integer> interactionIDList = JsonUtils.readIntegerListFromJsonObject(parameters, PerformInteraction.KEY_INTERACTION_CHOICE_SET_ID_LIST);
            List<Integer> testIDList = cmd.getInteractionChoiceSetIDList();
            assertEquals(TestValues.MATCH, interactionIDList.size(), testIDList.size());
            assertTrue(TestValues.TRUE, Validator.validateIntegerList(interactionIDList, testIDList));

            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PerformInteraction.KEY_INTERACTION_LAYOUT), cmd.getInteractionLayout().toString());

            JSONArray initalPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_INITIAL_PROMPT);
            List<TTSChunk> initalPromptList = new ArrayList<TTSChunk>();
            for (int index = 0; index < initalPromptArray.length(); index++) {
                TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject((JSONObject) initalPromptArray.get(index)));
                initalPromptList.add(chunk);
            }
            assertTrue(TestValues.TRUE, Validator.validateTtsChunks(initalPromptList, cmd.getInitialPrompt()));

            JSONArray helpPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_HELP_PROMPT);
            List<TTSChunk> helpPromptList = new ArrayList<TTSChunk>();
            for (int index = 0; index < helpPromptArray.length(); index++) {
                TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject((JSONObject) helpPromptArray.get(index)));
                helpPromptList.add(chunk);
            }
            assertTrue(TestValues.TRUE, Validator.validateTtsChunks(helpPromptList, cmd.getHelpPrompt()));

            JSONArray timeoutPromptArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_TIMEOUT_PROMPT);
            List<TTSChunk> timeoutPromptList = new ArrayList<TTSChunk>();
            for (int index = 0; index < timeoutPromptArray.length(); index++) {
                TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject((JSONObject) timeoutPromptArray.get(index)));
                timeoutPromptList.add(chunk);
            }
            assertTrue(TestValues.TRUE, Validator.validateTtsChunks(timeoutPromptList, cmd.getTimeoutPrompt()));
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, PerformInteraction.KEY_TIMEOUT), cmd.getTimeout());

            JSONArray vrHelpArray = JsonUtils.readJsonArrayFromJsonObject(parameters, PerformInteraction.KEY_VR_HELP);
            List<VrHelpItem> vrHelpList = new ArrayList<VrHelpItem>();
            for (int index = 0; index < vrHelpArray.length(); index++) {
                VrHelpItem vrHelpItem = new VrHelpItem(JsonRPCMarshaller.deserializeJSONObject((JSONObject) vrHelpArray.get(index)));
                vrHelpList.add(vrHelpItem);
            }
            assertTrue(TestValues.TRUE, Validator.validateVrHelpItems(vrHelpList, cmd.getVrHelp()));
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}