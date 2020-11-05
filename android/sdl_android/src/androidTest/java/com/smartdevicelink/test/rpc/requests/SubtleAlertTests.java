/*
 * Copyright (c)  2019 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Created by Nicole Yarroch on 7/17/19 10:06 AM
 */

package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SubtleAlert;
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
 * {@link com.smartdevicelink.proxy.rpc.SubtleAlert}
 */
public class SubtleAlertTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        SubtleAlert msg = new SubtleAlert();
        msg.setAlertText1(TestValues.GENERAL_STRING);
        msg.setAlertText2(TestValues.GENERAL_STRING);
        msg.setAlertIcon(TestValues.GENERAL_IMAGE);
        msg.setTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST);
        msg.setDuration(TestValues.GENERAL_INTEGER);
        msg.setSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST);
        msg.setCancelID(TestValues.GENERAL_INTEGER);
        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.SUBTLE_ALERT.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(SubtleAlert.KEY_ALERT_TEXT_1, TestValues.GENERAL_STRING);
            result.put(SubtleAlert.KEY_ALERT_TEXT_2, TestValues.GENERAL_STRING);
            result.put(SubtleAlert.KEY_ALERT_ICON, TestValues.JSON_IMAGE);
            result.put(SubtleAlert.KEY_TTS_CHUNKS, TestValues.JSON_TTSCHUNKS);
            result.put(SubtleAlert.KEY_DURATION, TestValues.GENERAL_INTEGER);
            result.put(SubtleAlert.KEY_SOFT_BUTTONS, TestValues.JSON_SOFTBUTTONS);
            result.put(SubtleAlert.KEY_CANCEL_ID, TestValues.GENERAL_INTEGER);
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
        String testAlertText1 = ((SubtleAlert) msg).getAlertText1();
        String testAlertText2 = ((SubtleAlert) msg).getAlertText2();
        Image testAlertIcon = ((SubtleAlert) msg).getAlertIcon();
        List<TTSChunk> testTtsChunks = ((SubtleAlert) msg).getTtsChunks();
        Integer testDuration = ((SubtleAlert) msg).getDuration();
        List<SoftButton> testSoftButtons = ((SubtleAlert) msg).getSoftButtons();
        Integer testCancelID = ((SubtleAlert) msg).getCancelID();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testAlertText1);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testAlertText2);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_IMAGE, testAlertIcon);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TTSCHUNK_LIST, testTtsChunks);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, testDuration);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SOFTBUTTON_LIST, testSoftButtons);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, testCancelID);

        // Invalid/Null Tests
        SubtleAlert msg = new SubtleAlert();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getAlertText1());
        assertNull(TestValues.NULL, msg.getAlertText2());
        assertNull(TestValues.NULL, msg.getAlertIcon());
        assertNull(TestValues.NULL, msg.getTtsChunks());
        assertNull(TestValues.NULL, msg.getDuration());
        assertNull(TestValues.NULL, msg.getSoftButtons());
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
            SubtleAlert cmd = new SubtleAlert(hash);
            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SubtleAlert.KEY_ALERT_TEXT_1), cmd.getAlertText1());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SubtleAlert.KEY_ALERT_TEXT_2), cmd.getAlertText2());

            JSONObject alertIcon = JsonUtils.readJsonObjectFromJsonObject(parameters, SubtleAlert.KEY_ALERT_ICON);
            Image referenceAlertIcon = new Image(JsonRPCMarshaller.deserializeJSONObject(alertIcon));
            assertTrue(TestValues.TRUE, Validator.validateImage(referenceAlertIcon, cmd.getAlertIcon()));

            JSONArray ttsChunkArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SubtleAlert.KEY_TTS_CHUNKS);
            List<TTSChunk> ttsChunkList = new ArrayList<TTSChunk>();
            for (int index = 0; index < ttsChunkArray.length(); index++) {
                TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject((JSONObject) ttsChunkArray.get(index)));
                ttsChunkList.add(chunk);
            }
            assertTrue(TestValues.TRUE, Validator.validateTtsChunks(ttsChunkList, cmd.getTtsChunks()));

            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, SubtleAlert.KEY_DURATION), cmd.getDuration());

            JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SubtleAlert.KEY_SOFT_BUTTONS);
            List<SoftButton> softButtonList = new ArrayList<SoftButton>();
            for (int index = 0; index < softButtonArray.length(); index++) {
                SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject((JSONObject) softButtonArray.get(index)));
                softButtonList.add(chunk);
            }
            assertTrue(TestValues.TRUE, Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));

            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, SubtleAlert.KEY_CANCEL_ID), cmd.getCancelID());

        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
