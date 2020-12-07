package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MetadataTags;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.TemplateConfiguration;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
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
 * {@link com.smartdevicelink.proxy.rpc.Show}
 */
public class ShowTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        Show msg = new Show();

        msg.setMainField1(TestValues.GENERAL_STRING);
        msg.setMainField2(TestValues.GENERAL_STRING);
        msg.setMainField3(TestValues.GENERAL_STRING);
        msg.setMainField4(TestValues.GENERAL_STRING);
        msg.setStatusBar(TestValues.GENERAL_STRING);
        msg.setMediaTrack(TestValues.GENERAL_STRING);
        msg.setTemplateTitle(TestValues.GENERAL_STRING);
        msg.setAlignment(TestValues.GENERAL_TEXTALIGNMENT);
        msg.setGraphic(TestValues.GENERAL_IMAGE);
        msg.setSecondaryGraphic(TestValues.GENERAL_IMAGE);
        msg.setCustomPresets(TestValues.GENERAL_STRING_LIST);
        msg.setSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST);
        msg.setMetadataTags(TestValues.GENERAL_METADATASTRUCT);
        msg.setWindowID(TestValues.GENERAL_INT);
        msg.setTemplateConfiguration(TestValues.GENERAL_TEMPLATE_CONFIGURATION);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.SHOW.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(Show.KEY_MAIN_FIELD_1, TestValues.GENERAL_STRING);
            result.put(Show.KEY_MAIN_FIELD_2, TestValues.GENERAL_STRING);
            result.put(Show.KEY_MAIN_FIELD_3, TestValues.GENERAL_STRING);
            result.put(Show.KEY_MAIN_FIELD_4, TestValues.GENERAL_STRING);
            result.put(Show.KEY_STATUS_BAR, TestValues.GENERAL_STRING);
            result.put(Show.KEY_MEDIA_TRACK, TestValues.GENERAL_STRING);
            result.put(Show.KEY_TEMPLATE_TITLE, TestValues.GENERAL_STRING);
            result.put(Show.KEY_GRAPHIC, TestValues.JSON_IMAGE);
            result.put(Show.KEY_SECONDARY_GRAPHIC, TestValues.JSON_IMAGE);
            result.put(Show.KEY_ALIGNMENT, TestValues.GENERAL_TEXTALIGNMENT);
            result.put(Show.KEY_CUSTOM_PRESETS, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
            result.put(Show.KEY_SOFT_BUTTONS, TestValues.JSON_SOFTBUTTONS);
            result.put(Show.KEY_METADATA_TAGS, TestValues.GENERAL_METADATASTRUCT.serializeJSON());
            result.put(Show.KEY_WINDOW_ID, TestValues.GENERAL_INT);
            result.put(Show.KEY_TEMPLATE_CONFIGURATION, TestValues.GENERAL_TEMPLATE_CONFIGURATION.serializeJSON());
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    @Test
    public void testSoftButtons() {
        // TestValues
        String testTrack = ((Show) msg).getMediaTrack();
        String templateTitle = ((Show) msg).getTemplateTitle();
        Image testGraphic2 = ((Show) msg).getSecondaryGraphic();
        Image testGraphic1 = ((Show) msg).getGraphic();
        String testStatusBar = ((Show) msg).getStatusBar();
        String testText1 = ((Show) msg).getMainField1();
        String testText2 = ((Show) msg).getMainField2();
        String testText3 = ((Show) msg).getMainField3();
        String testText4 = ((Show) msg).getMainField4();
        TextAlignment testAlignment = ((Show) msg).getAlignment();
        List<SoftButton> testSoftButtons = ((Show) msg).getSoftButtons();
        List<String> testCustomPresets = ((Show) msg).getCustomPresets();
        MetadataTags testMetadata = ((Show) msg).getMetadataTags();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testTrack);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, templateTitle);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TEXTALIGNMENT, testAlignment);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testStatusBar);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testText1);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testText2);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testText3);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testText4);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST.size(), testCustomPresets.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_METADATASTRUCT, testMetadata);
        assertTrue(TestValues.TRUE, Validator.validateSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST, testSoftButtons));
        assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, testGraphic2));
        assertTrue(TestValues.TRUE, Validator.validateImage(TestValues.GENERAL_IMAGE, testGraphic1));

        // Invalid/Null Tests
        Show msg = new Show();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getMainField1());
        assertNull(TestValues.NULL, msg.getMainField2());
        assertNull(TestValues.NULL, msg.getMainField3());
        assertNull(TestValues.NULL, msg.getMainField4());
        assertNull(TestValues.NULL, msg.getStatusBar());
        assertNull(TestValues.NULL, msg.getAlignment());
        assertNull(TestValues.NULL, msg.getGraphic());
        assertNull(TestValues.NULL, msg.getSecondaryGraphic());
        assertNull(TestValues.NULL, msg.getCustomPresets());
        assertNull(TestValues.NULL, msg.getMediaTrack());
        assertNull(TestValues.NULL, msg.getTemplateTitle());
        assertNull(TestValues.NULL, msg.getSoftButtons());
        assertNull(TestValues.NULL, msg.getMetadataTags());
        assertNull(TestValues.NULL, msg.getWindowID());
        assertNull(TestValues.NULL, msg.getTemplateConfiguration());
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
            Show cmd = new Show(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            JSONObject graphic = JsonUtils.readJsonObjectFromJsonObject(parameters, Show.KEY_GRAPHIC);
            Image referenceGraphic = new Image(JsonRPCMarshaller.deserializeJSONObject(graphic));
            assertTrue(TestValues.TRUE, Validator.validateImage(referenceGraphic, cmd.getGraphic()));

            List<String> customPresetsList = JsonUtils.readStringListFromJsonObject(parameters, Show.KEY_CUSTOM_PRESETS);
            List<String> testPresetsList = cmd.getCustomPresets();
            assertEquals(TestValues.MATCH, customPresetsList.size(), testPresetsList.size());
            assertTrue(TestValues.TRUE, Validator.validateStringList(customPresetsList, testPresetsList));

            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_MAIN_FIELD_1), cmd.getMainField1());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_MAIN_FIELD_2), cmd.getMainField2());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_MAIN_FIELD_3), cmd.getMainField3());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_MAIN_FIELD_4), cmd.getMainField4());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_STATUS_BAR), cmd.getStatusBar());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_ALIGNMENT), cmd.getAlignment().toString());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_MEDIA_TRACK), cmd.getMediaTrack());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_TEMPLATE_TITLE), cmd.getTemplateTitle());
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_METADATA_TAGS), cmd.getMetadataTags());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, Show.KEY_WINDOW_ID), cmd.getWindowID());

            JSONObject templateConfiguration = JsonUtils.readJsonObjectFromJsonObject(parameters, Show.KEY_TEMPLATE_CONFIGURATION);
            TemplateConfiguration refTemplateConfiguration = new TemplateConfiguration(JsonRPCMarshaller.deserializeJSONObject(templateConfiguration));
            assertTrue(TestValues.TRUE, Validator.validateTemplateConfiguration(refTemplateConfiguration, cmd.getTemplateConfiguration()));

            JSONObject secondaryGraphic = JsonUtils.readJsonObjectFromJsonObject(parameters, Show.KEY_SECONDARY_GRAPHIC);
            Image referenceSecondaryGraphic = new Image(JsonRPCMarshaller.deserializeJSONObject(secondaryGraphic));
            assertTrue(TestValues.TRUE, Validator.validateImage(referenceSecondaryGraphic, cmd.getSecondaryGraphic()));

            JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, Show.KEY_SOFT_BUTTONS);
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