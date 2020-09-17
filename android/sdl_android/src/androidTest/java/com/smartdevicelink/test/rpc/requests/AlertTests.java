package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.TTSChunk;
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
 * {@link com.smartdevicelink.proxy.rpc.Alert}
 */
public class AlertTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        Alert msg = new Alert();

        msg.setDuration(TestValues.GENERAL_INT);
        msg.setAlertText1(TestValues.GENERAL_STRING);
        msg.setAlertText2(TestValues.GENERAL_STRING);
        msg.setAlertText3(TestValues.GENERAL_STRING);
        msg.setPlayTone(TestValues.GENERAL_BOOLEAN);
        msg.setProgressIndicator(TestValues.GENERAL_BOOLEAN);
        msg.setTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST);
        msg.setSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST);
        msg.setCancelID(TestValues.GENERAL_INTEGER);
        msg.setAlertIcon(TestValues.GENERAL_IMAGE);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.ALERT.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(Alert.KEY_DURATION, TestValues.GENERAL_INT);
            result.put(Alert.KEY_ALERT_TEXT_1, TestValues.GENERAL_STRING);
            result.put(Alert.KEY_ALERT_TEXT_2, TestValues.GENERAL_STRING);
            result.put(Alert.KEY_ALERT_TEXT_3, TestValues.GENERAL_STRING);
            result.put(Alert.KEY_PLAY_TONE, TestValues.GENERAL_BOOLEAN);
            result.put(Alert.KEY_PROGRESS_INDICATOR, TestValues.GENERAL_BOOLEAN);
            result.put(Alert.KEY_TTS_CHUNKS, TestValues.JSON_TTSCHUNKS);
            result.put(Alert.KEY_SOFT_BUTTONS, TestValues.JSON_SOFTBUTTONS);
            result.put(Alert.KEY_CANCEL_ID, TestValues.GENERAL_INTEGER);
            result.put(Alert.KEY_ALERT_ICON, TestValues.JSON_IMAGE);
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
        int testDuration = ((Alert) msg).getDuration();
        String testAlertText1 = ((Alert) msg).getAlertText1();
        String testAlertText2 = ((Alert) msg).getAlertText2();
        String testAlertText3 = ((Alert) msg).getAlertText3();
        boolean testPlayTone = ((Alert) msg).getPlayTone();
        boolean testProgressIndicator = ((Alert) msg).getProgressIndicator();
        List<TTSChunk> testTtsChunks = ((Alert) msg).getTtsChunks();
        List<SoftButton> testSoftButtons = ((Alert) msg).getSoftButtons();
        Integer testCancelID = ((Alert) msg).getCancelID();
        Image alertIcon = ((Alert) msg).getAlertIcon();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, testDuration);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testAlertText1);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testAlertText2);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testAlertText3);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, testPlayTone);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, testProgressIndicator);
        assertTrue(TestValues.TRUE, Validator.validateSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST, testSoftButtons));
        assertTrue(TestValues.TRUE, Validator.validateTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST, testTtsChunks));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, testCancelID);
        assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, alertIcon));

        // Invalid/Null Tests
        Alert msg = new Alert();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getAlertText1());
        assertNull(TestValues.NULL, msg.getAlertText2());
        assertNull(TestValues.NULL, msg.getAlertText3());
        assertNull(TestValues.NULL, msg.getDuration());
        assertNull(TestValues.NULL, msg.getPlayTone());
        assertNull(TestValues.NULL, msg.getProgressIndicator());
        assertNull(TestValues.NULL, msg.getTtsChunks());
        assertNull(TestValues.NULL, msg.getSoftButtons());
        assertNull(TestValues.NULL, msg.getCancelID());
        assertNull(TestValues.NULL, msg.getAlertIcon());
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
            Alert cmd = new Alert(hash);
            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, Alert.KEY_PLAY_TONE), cmd.getPlayTone());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, Alert.KEY_DURATION), cmd.getDuration());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Alert.KEY_ALERT_TEXT_1), cmd.getAlertText1());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Alert.KEY_ALERT_TEXT_2), cmd.getAlertText2());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Alert.KEY_ALERT_TEXT_3), cmd.getAlertText3());
            assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, Alert.KEY_PROGRESS_INDICATOR), cmd.getProgressIndicator());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, Alert.KEY_CANCEL_ID), cmd.getCancelID());

            JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, Alert.KEY_TTS_CHUNKS);
            List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
            for (int index = 0; index < ttsChunkArray.length(); index++) {
                TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject((JSONObject) ttsChunkArray.get(index)));
                ttsChunkList.add(chunk);
            }
            assertTrue(TestValues.TRUE, Validator.validateTtsChunks(ttsChunkList, cmd.getTtsChunks()));

            JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, Alert.KEY_SOFT_BUTTONS);
            List<SoftButton> softButtonList = new ArrayList<SoftButton>();
            for (int index = 0; index < softButtonArray.length(); index++) {
                SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject((JSONObject) softButtonArray.get(index)));
                softButtonList.add(chunk);
            }
            assertTrue(TestValues.TRUE, Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));

            JSONObject alertIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, Alert.KEY_ALERT_ICON);
            Image referenceAlertIcon = new Image(JsonRPCMarshaller.deserializeJSONObject(alertIcon));
            assertTrue(TestValues.TRUE, Validator.validateImage(referenceAlertIcon, cmd.getAlertIcon()));
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}