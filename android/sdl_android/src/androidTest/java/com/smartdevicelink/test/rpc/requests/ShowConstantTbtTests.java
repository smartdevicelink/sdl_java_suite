package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ShowConstantTBT;
import com.smartdevicelink.proxy.rpc.SoftButton;
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
 * {@link com.smartdevicelink.proxy.rpc.ShowConstantTBT}
 */
public class ShowConstantTbtTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        ShowConstantTBT msg = new ShowConstantTBT();

        msg.setDistanceToManeuver(TestValues.GENERAL_DOUBLE);
        msg.setDistanceToManeuverScale(TestValues.GENERAL_DOUBLE);
        msg.setEta(TestValues.GENERAL_STRING);
        msg.setManeuverComplete(true);
        msg.setNavigationText1(TestValues.GENERAL_STRING);
        msg.setNavigationText2(TestValues.GENERAL_STRING);
        msg.setNextTurnIcon(TestValues.GENERAL_IMAGE);
        msg.setSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST);
        msg.setTimeToDestination(TestValues.GENERAL_STRING);
        msg.setTotalDistance(TestValues.GENERAL_STRING);
        msg.setTurnIcon(TestValues.GENERAL_IMAGE);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.SHOW_CONSTANT_TBT.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(ShowConstantTBT.KEY_SOFT_BUTTONS, TestValues.JSON_SOFTBUTTONS);
            result.put(ShowConstantTBT.KEY_ETA, TestValues.GENERAL_STRING);
            result.put(ShowConstantTBT.KEY_MANEUVER_COMPLETE, true);
            result.put(ShowConstantTBT.KEY_DISTANCE_TO_MANEUVER, TestValues.GENERAL_DOUBLE);
            result.put(ShowConstantTBT.KEY_DISTANCE_TO_MANEUVER_SCALE, TestValues.GENERAL_DOUBLE);
            result.put(ShowConstantTBT.KEY_TEXT1, TestValues.GENERAL_STRING);
            result.put(ShowConstantTBT.KEY_TEXT2, TestValues.GENERAL_STRING);
            result.put(ShowConstantTBT.KEY_TIME_TO_DESTINATION, TestValues.GENERAL_STRING);
            result.put(ShowConstantTBT.KEY_TOTAL_DISTANCE, TestValues.GENERAL_STRING);
            result.put(ShowConstantTBT.KEY_MANEUVER_IMAGE, TestValues.GENERAL_IMAGE.serializeJSON());
            result.put(ShowConstantTBT.KEY_NEXT_MANEUVER_IMAGE, TestValues.GENERAL_IMAGE.serializeJSON());
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
        Double testScale = ((ShowConstantTBT) msg).getDistanceToManeuverScale();
        Double testDistance = ((ShowConstantTBT) msg).getDistanceToManeuver();
        String testEta = ((ShowConstantTBT) msg).getEta();
        String testTimeToDestination = ((ShowConstantTBT) msg).getTimeToDestination();
        String testTotalDistance = ((ShowConstantTBT) msg).getTotalDistance();
        String testNavText2 = ((ShowConstantTBT) msg).getNavigationText2();
        String testNavText1 = ((ShowConstantTBT) msg).getNavigationText1();
        Boolean testManeuverComplete = ((ShowConstantTBT) msg).getManeuverComplete();
        Image testTurnIcon = ((ShowConstantTBT) msg).getTurnIcon();
        Image testNextTurnIcon = ((ShowConstantTBT) msg).getNextTurnIcon();
        List<SoftButton> testSoftButtons = ((ShowConstantTBT) msg).getSoftButtons();

        // Valid Test
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testTimeToDestination);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, testScale);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testNavText1);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testNavText2);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testEta);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testTotalDistance);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, testDistance);
        assertTrue(TestValues.TRUE, testManeuverComplete);
        assertTrue(TestValues.TRUE, Validator.validateSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST, testSoftButtons));
        assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, testTurnIcon));
        assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, testNextTurnIcon));

        // Invalid/Null Tests
        ShowConstantTBT msg = new ShowConstantTBT();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getSoftButtons());
        assertNull(TestValues.NULL, msg.getNavigationText1());
        assertNull(TestValues.NULL, msg.getNavigationText2());
        assertNull(TestValues.NULL, msg.getDistanceToManeuver());
        assertNull(TestValues.NULL, msg.getDistanceToManeuverScale());
        assertNull(TestValues.NULL, msg.getEta());
        assertNull(TestValues.NULL, msg.getManeuverComplete());
        assertNull(TestValues.NULL, msg.getNextTurnIcon());
        assertNull(TestValues.NULL, msg.getTimeToDestination());
        assertNull(TestValues.NULL, msg.getTotalDistance());
        assertNull(TestValues.NULL, msg.getTurnIcon());
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
            ShowConstantTBT cmd = new ShowConstantTBT(hash);
            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTBT.KEY_ETA), cmd.getEta());
            assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, ShowConstantTBT.KEY_MANEUVER_COMPLETE), cmd.getManeuverComplete());
            assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, ShowConstantTBT.KEY_DISTANCE_TO_MANEUVER), cmd.getDistanceToManeuver());
            assertEquals(TestValues.MATCH, JsonUtils.readDoubleFromJsonObject(parameters, ShowConstantTBT.KEY_DISTANCE_TO_MANEUVER_SCALE), cmd.getDistanceToManeuverScale());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTBT.KEY_TEXT1), cmd.getNavigationText1());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTBT.KEY_TEXT2), cmd.getNavigationText2());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTBT.KEY_TIME_TO_DESTINATION), cmd.getTimeToDestination());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, ShowConstantTBT.KEY_TOTAL_DISTANCE), cmd.getTotalDistance());

            JSONObject icon1 = JsonUtils.readJsonObjectFromJsonObject(parameters, ShowConstantTBT.KEY_MANEUVER_IMAGE);
            Image refIcon1 = new Image(JsonRPCMarshaller.deserializeJSONObject(icon1));
            assertTrue(TestValues.TRUE, Validator.validateImage(refIcon1, cmd.getTurnIcon()));

            JSONObject icon2 = JsonUtils.readJsonObjectFromJsonObject(parameters, ShowConstantTBT.KEY_NEXT_MANEUVER_IMAGE);
            Image refIcon2 = new Image(JsonRPCMarshaller.deserializeJSONObject(icon2));
            assertTrue(TestValues.TRUE, Validator.validateImage(refIcon2, cmd.getNextTurnIcon()));

            JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ShowConstantTBT.KEY_SOFT_BUTTONS);
            List<SoftButton> softButtonList = new ArrayList<SoftButton>();
            for (int index = 0; index < softButtonArray.length(); index++) {
                SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject((JSONObject) softButtonArray.get(index)));
                softButtonList.add(chunk);
            }
            assertTrue(TestValues.TRUE, Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}