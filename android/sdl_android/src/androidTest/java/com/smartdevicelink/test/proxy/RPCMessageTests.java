package com.smartdevicelink.test.proxy;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.ChangeRegistration;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by austinkirk on 6/8/17.
 */

public class RPCMessageTests extends TestCase {
    RPCMessage testMessage = new RPCMessage("TestFunction");

    private static final String TAG = "RPC Struct Tests";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGeneralGettersAndSetters(){
        String testKey = TestValues.GENERAL_STRING;
        Integer testInt = TestValues.GENERAL_INT;

        testMessage.setParameters(testKey, testInt);
        assertEquals(TestValues.MATCH, testInt, testMessage.getParameters(testKey));

        testMessage.setParameters(testKey, null);
        assertNull(testMessage.getParameters(testKey));
    }

    public void testCommonObjectGetters(){
        String stringKey = "String";
        String testString = TestValues.GENERAL_STRING;
        testMessage.setParameters(stringKey, testString);

        assertEquals(TestValues.MATCH, testMessage.getString(stringKey), testString);

        String intKey = "Integer";
        Integer testInt = TestValues.GENERAL_INT;
        testMessage.setParameters(intKey, testInt);

        assertEquals(TestValues.MATCH, testMessage.getInteger(intKey), testInt);

        String doubleKey = "Double";
        Double testDouble = TestValues.GENERAL_DOUBLE;
        testMessage.setParameters(doubleKey, testDouble);

        assertEquals(TestValues.MATCH, testMessage.getDouble(doubleKey), testDouble);

        String booleanKey = "Boolean";
        Boolean testBoolean = TestValues.GENERAL_BOOLEAN;
        testMessage.setParameters(booleanKey, testBoolean);

        assertEquals(TestValues.MATCH, testMessage.getBoolean(booleanKey), testBoolean);

        String longKey = "Long";
        Long testLong = TestValues.GENERAL_LONG;
        testMessage.setParameters(longKey, testLong);

        assertEquals(TestValues.MATCH, testMessage.getLong(longKey), testLong);

        testMessage.setParameters(longKey, testInt);

        assertEquals(TestValues.MATCH, testMessage.getLong(longKey), new Long(testInt.longValue()));

        testMessage.setParameters(longKey, testDouble);
        assertNull(testMessage.getLong(longKey));
    }

    public void testGetObject(){
        String keyIsEmpty = "EMPTY";
        assertNull(testMessage.getObject(Integer.class, keyIsEmpty));

        String keyLanguage = ChangeRegistration.KEY_LANGUAGE;
        Language language = TestValues.GENERAL_LANGUAGE;
        testMessage.setParameters(keyLanguage, language);
        assertEquals(TestValues.MATCH, language, testMessage.getObject(Language.class, keyLanguage));

        String languageString = language.toString();
        testMessage.setParameters(keyLanguage, languageString);
        assertEquals(TestValues.MATCH, language, testMessage.getObject(Language.class, keyLanguage));

        String keyImage = Choice.KEY_IMAGE;
        Image testImage = TestValues.GENERAL_IMAGE;
        testMessage.setParameters(keyImage, testImage.getStore());
        assertTrue(Validator.validateImage(testImage, (Image) testMessage.getObject(Image.class, keyImage)));

        String keyDTCs = GetDTCsResponse.KEY_DTC;
        List<String> testDTCs = TestValues.GENERAL_STRING_LIST;
        testMessage.setParameters(keyDTCs, testDTCs);
        assertEquals(TestValues.MATCH, testDTCs, testMessage.getObject(String.class, keyDTCs));

        String keyTTSchunks = Alert.KEY_TTS_CHUNKS;
        List<TTSChunk> testTTSchunks = TestValues.GENERAL_TTSCHUNK_LIST;
        testMessage.setParameters(keyTTSchunks, testTTSchunks);
        assertEquals(TestValues.MATCH, testTTSchunks, testMessage.getObject(TTSChunk.class, keyTTSchunks));

        List<Hashtable<String, Object>> testListTTSchunks = new ArrayList<>();
        for(TTSChunk ttsChunk : testTTSchunks){
            testListTTSchunks.add(ttsChunk.getStore());
        }
        testMessage.setParameters(keyTTSchunks, testListTTSchunks);
        List<TTSChunk> underTest = (List<TTSChunk>) testMessage.getObject(TTSChunk.class, keyTTSchunks);
        assertTrue(Validator.validateTtsChunks(testTTSchunks, underTest));

        String keyMediaClockFormats = DisplayCapabilities.KEY_MEDIA_CLOCK_FORMATS;
        List<MediaClockFormat> testMediaClockFormats = TestValues.GENERAL_MEDIACLOCKFORMAT_LIST;
        testMessage.setParameters(keyMediaClockFormats, testMediaClockFormats);
        assertEquals(TestValues.MATCH, testMediaClockFormats, testMessage.getObject(MediaClockFormat.class, keyMediaClockFormats));

        List<String> testListMediaClockFormats = new ArrayList<>();
        for(MediaClockFormat mcFormat : testMediaClockFormats){
            testListMediaClockFormats.add(mcFormat.toString());
        }
        testMessage.setParameters(keyMediaClockFormats, testListMediaClockFormats);
        assertEquals(TestValues.MATCH, testMediaClockFormats, testMessage.getObject(MediaClockFormat.class, keyMediaClockFormats));

        assertNull(testMessage.getObject(Image.class, keyLanguage)); // Test incorrect class
    }
}
