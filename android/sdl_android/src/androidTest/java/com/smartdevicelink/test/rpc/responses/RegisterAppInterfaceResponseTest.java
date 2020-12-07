package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;
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
 * {@link com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse}
 */
public class RegisterAppInterfaceResponseTest extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
        RegisterAppInterfaceResponse msg = new RegisterAppInterfaceResponse();

        msg.setSdlMsgVersion(TestValues.GENERAL_SDLMSGVERSION);
        msg.setLanguage(TestValues.GENERAL_LANGUAGE);
        msg.setHmiDisplayLanguage(TestValues.GENERAL_LANGUAGE);
        msg.setDisplayCapabilities(TestValues.GENERAL_DISPLAYCAPABILITIES);
        msg.setPresetBankCapabilities(TestValues.GENERAL_PRESETBANKCAPABILITIES);
        msg.setVehicleType(TestValues.GENERAL_VEHICLETYPE);
        msg.setButtonCapabilities(TestValues.GENERAL_BUTTONCAPABILITIES_LIST);
        msg.setSoftButtonCapabilities(TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST);
        msg.setAudioPassThruCapabilities(TestValues.GENERAL_AUDIOPASSTHRUCAPABILITIES_LIST);
        msg.setPcmStreamingCapabilities(TestValues.GENERAL_AUDIOPASSTHRUCAPABILITIES);
        msg.setHmiZoneCapabilities(TestValues.GENERAL_HMIZONECAPABILITIES_LIST);
        msg.setSpeechCapabilities(TestValues.GENERAL_SPEECHCAPABILITIES_LIST);
        msg.setVrCapabilities(TestValues.GENERAL_VRCAPABILITIES_LIST);
        msg.setPrerecordedSpeech(TestValues.GENERAL_PRERECORDEDSPEECH_LIST);
        msg.setSupportedDiagModes(TestValues.GENERAL_INTEGER_LIST);
        msg.setIconResumed(TestValues.GENERAL_BOOLEAN);

        return msg;
    }

    @Override
    protected String getMessageType() {
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType() {
        return FunctionID.REGISTER_APP_INTERFACE.toString();
    }


    @Override
    protected JSONObject getExpectedParameters(int sdlVersion) {
        JSONObject result = new JSONObject();

        try {
            result.put(RegisterAppInterfaceResponse.KEY_LANGUAGE, TestValues.GENERAL_LANGUAGE);
            result.put(RegisterAppInterfaceResponse.KEY_HMI_DISPLAY_LANGUAGE, TestValues.GENERAL_LANGUAGE);
            result.put(RegisterAppInterfaceResponse.KEY_SUPPORTED_DIAG_MODES, JsonUtils.createJsonArray(TestValues.GENERAL_INTEGER_LIST));
            result.put(RegisterAppInterfaceResponse.KEY_SDL_MSG_VERSION, TestValues.JSON_SDLMSGVERSION);
            result.put(RegisterAppInterfaceResponse.KEY_VEHICLE_TYPE, TestValues.GENERAL_VEHICLETYPE.serializeJSON());
            result.put(RegisterAppInterfaceResponse.KEY_PRESET_BANK_CAPABILITIES, TestValues.JSON_PRESETBANKCAPABILITIES);
            result.put(RegisterAppInterfaceResponse.KEY_DISPLAY_CAPABILITIES, TestValues.JSON_DISPLAYCAPABILITIES);
            result.put(RegisterAppInterfaceResponse.KEY_BUTTON_CAPABILITIES, TestValues.JSON_BUTTONCAPABILITIES);
            result.put(RegisterAppInterfaceResponse.KEY_SOFT_BUTTON_CAPABILITIES, TestValues.JSON_SOFTBUTTONCAPABILITIES);
            result.put(RegisterAppInterfaceResponse.KEY_AUDIO_PASS_THRU_CAPABILITIES, TestValues.JSON_AUDIOPASSTHRUCAPABILITIES);
            result.put(RegisterAppInterfaceResponse.KEY_PCM_STREAM_CAPABILITIES, TestValues.JSON_PCMSTREAMCAPABILITIES);
            result.put(RegisterAppInterfaceResponse.KEY_SPEECH_CAPABILITIES, JsonUtils.createJsonArray(TestValues.GENERAL_SPEECHCAPABILITIES_LIST));
            result.put(RegisterAppInterfaceResponse.KEY_VR_CAPABILITIES, JsonUtils.createJsonArray(TestValues.GENERAL_VRCAPABILITIES_LIST));
            result.put(RegisterAppInterfaceResponse.KEY_HMI_ZONE_CAPABILITIES, JsonUtils.createJsonArray(TestValues.GENERAL_HMIZONECAPABILITIES_LIST));
            result.put(RegisterAppInterfaceResponse.KEY_PRERECORDED_SPEECH, JsonUtils.createJsonArray(TestValues.GENERAL_PRERECORDEDSPEECH_LIST));
            result.put(RegisterAppInterfaceResponse.KEY_ICON_RESUMED, TestValues.GENERAL_BOOLEAN);
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
        List<Integer> testSupportedDiagModes = ((RegisterAppInterfaceResponse) msg).getSupportedDiagModes();
        List<PrerecordedSpeech> testPrerecordedSpeech = ((RegisterAppInterfaceResponse) msg).getPrerecordedSpeech();
        List<VrCapabilities> testVrCapabilities = ((RegisterAppInterfaceResponse) msg).getVrCapabilities();
        List<SpeechCapabilities> testSpeechCapabilities = ((RegisterAppInterfaceResponse) msg).getSpeechCapabilities();
        List<HmiZoneCapabilities> testHmiZoneCapabilities = ((RegisterAppInterfaceResponse) msg).getHmiZoneCapabilities();
        List<SoftButtonCapabilities> testSoftButtonCapabilities = ((RegisterAppInterfaceResponse) msg).getSoftButtonCapabilities();
        List<ButtonCapabilities> testButtonCapabilities = ((RegisterAppInterfaceResponse) msg).getButtonCapabilities();
        VehicleType testVehicleType = ((RegisterAppInterfaceResponse) msg).getVehicleType();
        PresetBankCapabilities testPbc = ((RegisterAppInterfaceResponse) msg).getPresetBankCapabilities();
        DisplayCapabilities testDc = ((RegisterAppInterfaceResponse) msg).getDisplayCapabilities();
        Language testHmiLang = ((RegisterAppInterfaceResponse) msg).getHmiDisplayLanguage();
        Language testLang = ((RegisterAppInterfaceResponse) msg).getLanguage();
        SdlMsgVersion testMsgVersion = ((RegisterAppInterfaceResponse) msg).getSdlMsgVersion();
        List<AudioPassThruCapabilities> testAptc = ((RegisterAppInterfaceResponse) msg).getAudioPassThruCapabilities();
        AudioPassThruCapabilities testPcmStream = ((RegisterAppInterfaceResponse) msg).getPcmStreamingCapabilities();
        Boolean testIconResumed = ((RegisterAppInterfaceResponse) msg).getIconResumed();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER_LIST, testSupportedDiagModes);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_PRERECORDEDSPEECH_LIST, testPrerecordedSpeech);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VRCAPABILITIES_LIST, testVrCapabilities);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SPEECHCAPABILITIES_LIST, testSpeechCapabilities);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_HMIZONECAPABILITIES_LIST, testHmiZoneCapabilities);
        assertTrue(TestValues.TRUE, Validator.validateSoftButtonCapabilities(TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST, testSoftButtonCapabilities));
        assertTrue(TestValues.TRUE, Validator.validateButtonCapabilities(TestValues.GENERAL_BUTTONCAPABILITIES_LIST, testButtonCapabilities));
        assertTrue(TestValues.TRUE, Validator.validateVehicleType(TestValues.GENERAL_VEHICLETYPE, testVehicleType));
        assertTrue(TestValues.TRUE, Validator.validatePresetBankCapabilities(TestValues.GENERAL_PRESETBANKCAPABILITIES, testPbc));
        assertTrue(TestValues.TRUE, Validator.validateDisplayCapabilities(TestValues.GENERAL_DISPLAYCAPABILITIES, testDc));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LANGUAGE, testHmiLang);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LANGUAGE, testLang);
        assertTrue(TestValues.TRUE, Validator.validateSdlMsgVersion(TestValues.GENERAL_SDLMSGVERSION, testMsgVersion));
        assertTrue(TestValues.TRUE, Validator.validateAudioPassThruCapabilities(TestValues.GENERAL_AUDIOPASSTHRUCAPABILITIES_LIST, testAptc));
        assertTrue(TestValues.TRUE, Validator.validatePcmStreamCapabilities(TestValues.GENERAL_AUDIOPASSTHRUCAPABILITIES, testPcmStream));
        assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, testIconResumed);

        // Invalid/Null Tests
        RegisterAppInterfaceResponse msg = new RegisterAppInterfaceResponse();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getSdlMsgVersion());
        assertNull(TestValues.NULL, msg.getLanguage());
        assertNull(TestValues.NULL, msg.getHmiDisplayLanguage());
        assertNull(TestValues.NULL, msg.getDisplayCapabilities());
        assertNull(TestValues.NULL, msg.getPresetBankCapabilities());
        assertNull(TestValues.NULL, msg.getVehicleType());
        assertNull(TestValues.NULL, msg.getButtonCapabilities());
        assertNull(TestValues.NULL, msg.getSoftButtonCapabilities());
        assertNull(TestValues.NULL, msg.getAudioPassThruCapabilities());
        assertNull(TestValues.NULL, msg.getPcmStreamingCapabilities());
        assertNull(TestValues.NULL, msg.getHmiZoneCapabilities());
        assertNull(TestValues.NULL, msg.getSpeechCapabilities());
        assertNull(TestValues.NULL, msg.getVrCapabilities());
        assertNull(TestValues.NULL, msg.getPrerecordedSpeech());
        assertNull(TestValues.NULL, msg.getSupportedDiagModes());
        assertNull(TestValues.NULL, msg.getIconResumed());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor() {
        JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getContext(), getCommandType(), getMessageType());
        assertNotNull(TestValues.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            RegisterAppInterfaceResponse cmd = new RegisterAppInterfaceResponse(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

            JSONObject vehicleTypeObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_VEHICLE_TYPE);
            VehicleType vehicleType = new VehicleType(JsonRPCMarshaller.deserializeJSONObject(vehicleTypeObj));
            assertTrue(TestValues.TRUE, Validator.validateVehicleType(vehicleType, cmd.getVehicleType()));

            JSONObject pcmStreamObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_PCM_STREAM_CAPABILITIES);
            AudioPassThruCapabilities pcmStreamCap = new AudioPassThruCapabilities(JsonRPCMarshaller.deserializeJSONObject(pcmStreamObj));
            assertTrue(TestValues.TRUE, Validator.validatePcmStreamCapabilities(pcmStreamCap, cmd.getPcmStreamingCapabilities()));

            JSONArray speechCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_SPEECH_CAPABILITIES);
            for (int index = 0; index < speechCapabilitiesArray.length(); index++) {
                SpeechCapabilities speechCapability = SpeechCapabilities.valueForString(speechCapabilitiesArray.get(index).toString());
                assertEquals(TestValues.MATCH, speechCapability, cmd.getSpeechCapabilities().get(index));
            }

            JSONArray vrCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_VR_CAPABILITIES);
            for (int index = 0; index < vrCapabilitiesArray.length(); index++) {
                VrCapabilities vrCapability = VrCapabilities.valueForString(vrCapabilitiesArray.get(index).toString());
                assertEquals(TestValues.MATCH, vrCapability, cmd.getVrCapabilities().get(index));
            }

            JSONArray audioPassThruCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_AUDIO_PASS_THRU_CAPABILITIES);
            List<AudioPassThruCapabilities> audioPassThruCapabilitiesList = new ArrayList<AudioPassThruCapabilities>();
            for (int index = 0; index < audioPassThruCapabilitiesArray.length(); index++) {
                AudioPassThruCapabilities audioPassThruCapability =
                        new AudioPassThruCapabilities(JsonRPCMarshaller.deserializeJSONObject((JSONObject) audioPassThruCapabilitiesArray.get(index)));
                audioPassThruCapabilitiesList.add(audioPassThruCapability);
            }
            assertTrue(TestValues.TRUE, Validator.validateAudioPassThruCapabilities(audioPassThruCapabilitiesList, cmd.getAudioPassThruCapabilities()));

            JSONArray hmiZoneCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_HMI_ZONE_CAPABILITIES);
            for (int index = 0; index < hmiZoneCapabilitiesArray.length(); index++) {
                HmiZoneCapabilities hmiZoneCapability = HmiZoneCapabilities.valueForString(hmiZoneCapabilitiesArray.get(index).toString());
                assertEquals(TestValues.MATCH, hmiZoneCapability, cmd.getHmiZoneCapabilities().get(index));
            }

            JSONArray prerecordedSpeechArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_PRERECORDED_SPEECH);
            for (int index = 0; index < prerecordedSpeechArray.length(); index++) {
                PrerecordedSpeech prerecordedSpeech = PrerecordedSpeech.valueForString(prerecordedSpeechArray.get(index).toString());
                assertEquals(TestValues.MATCH, prerecordedSpeech, cmd.getPrerecordedSpeech().get(index));
            }

            List<Integer> supportedDiagnosticModesList = JsonUtils.readIntegerListFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_SUPPORTED_DIAG_MODES);
            List<Integer> testDiagnosticModesList = cmd.getSupportedDiagModes();
            assertEquals(TestValues.MATCH, supportedDiagnosticModesList.size(), testDiagnosticModesList.size());
            assertTrue(TestValues.TRUE, Validator.validateIntegerList(supportedDiagnosticModesList, testDiagnosticModesList));

            JSONObject sdlMsgVersionObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_SDL_MSG_VERSION);
            SdlMsgVersion sdlMsgVersion = new SdlMsgVersion(JsonRPCMarshaller.deserializeJSONObject(sdlMsgVersionObj));
            assertTrue(TestValues.TRUE, Validator.validateSdlMsgVersion(sdlMsgVersion, cmd.getSdlMsgVersion()));
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_LANGUAGE), cmd.getLanguage().toString());

            JSONArray buttonCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_BUTTON_CAPABILITIES);
            List<ButtonCapabilities> buttonCapabilitiesList = new ArrayList<ButtonCapabilities>();
            for (int index = 0; index < buttonCapabilitiesArray.length(); index++) {
                ButtonCapabilities buttonCapability = new ButtonCapabilities(JsonRPCMarshaller.deserializeJSONObject((JSONObject) buttonCapabilitiesArray.get(index)));
                buttonCapabilitiesList.add(buttonCapability);
            }
            assertTrue(TestValues.TRUE, Validator.validateButtonCapabilities(buttonCapabilitiesList, cmd.getButtonCapabilities()));

            JSONObject displayCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_DISPLAY_CAPABILITIES);
            DisplayCapabilities displayCapabilities = new DisplayCapabilities(JsonRPCMarshaller.deserializeJSONObject(displayCapabilitiesObj));
            assertTrue(TestValues.TRUE, Validator.validateDisplayCapabilities(displayCapabilities, cmd.getDisplayCapabilities()));
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_HMI_DISPLAY_LANGUAGE), cmd.getHmiDisplayLanguage().toString());

            JSONArray softButtonCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_SOFT_BUTTON_CAPABILITIES);
            List<SoftButtonCapabilities> softButtonCapabilitiesList = new ArrayList<SoftButtonCapabilities>();
            for (int index = 0; index < softButtonCapabilitiesArray.length(); index++) {
                SoftButtonCapabilities softButtonCapability =
                        new SoftButtonCapabilities(JsonRPCMarshaller.deserializeJSONObject((JSONObject) softButtonCapabilitiesArray.get(index)));
                softButtonCapabilitiesList.add(softButtonCapability);
            }
            assertTrue(TestValues.TRUE, Validator.validateSoftButtonCapabilities(softButtonCapabilitiesList, cmd.getSoftButtonCapabilities()));

            JSONObject presetBankCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_PRESET_BANK_CAPABILITIES);
            PresetBankCapabilities presetBankCapabilities = new PresetBankCapabilities(JsonRPCMarshaller.deserializeJSONObject(presetBankCapabilitiesObj));
            assertTrue(TestValues.TRUE, Validator.validatePresetBankCapabilities(presetBankCapabilities, cmd.getPresetBankCapabilities()));

            Boolean iconResumed = JsonUtils.readBooleanFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_ICON_RESUMED);
            assertEquals(TestValues.MATCH, iconResumed, cmd.getIconResumed());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}