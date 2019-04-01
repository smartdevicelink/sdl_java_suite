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
import com.smartdevicelink.test.Test;
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
        String testKey = Test.GENERAL_STRING;
        Integer testInt = Test.GENERAL_INT;

        testMessage.setParameters(testKey, testInt);
        assertEquals(Test.MATCH, testInt, testMessage.getParameters(testKey));

        testMessage.setParameters(testKey, null);
        assertNull(testMessage.getParameters(testKey));
    }

    public void testCommonObjectGetters(){
        String stringKey = "String";
        String testString = Test.GENERAL_STRING;
        testMessage.setParameters(stringKey, testString);

        assertEquals(Test.MATCH, testMessage.getString(stringKey), testString);

        String intKey = "Integer";
        Integer testInt = Test.GENERAL_INT;
        testMessage.setParameters(intKey, testInt);

        assertEquals(Test.MATCH, testMessage.getInteger(intKey), testInt);

        String doubleKey = "Double";
        Double testDouble = Test.GENERAL_DOUBLE;
        testMessage.setParameters(doubleKey, testDouble);

        assertEquals(Test.MATCH, testMessage.getDouble(doubleKey), testDouble);

        String booleanKey = "Boolean";
        Boolean testBoolean = Test.GENERAL_BOOLEAN;
        testMessage.setParameters(booleanKey, testBoolean);

        assertEquals(Test.MATCH, testMessage.getBoolean(booleanKey), testBoolean);

        String longKey = "Long";
        Long testLong = Test.GENERAL_LONG;
        testMessage.setParameters(longKey, testLong);

        assertEquals(Test.MATCH, testMessage.getLong(longKey), testLong);

        testMessage.setParameters(longKey, testInt);

        assertEquals(Test.MATCH, testMessage.getLong(longKey), new Long(testInt.longValue()));

        testMessage.setParameters(longKey, testDouble);
        assertNull(testMessage.getLong(longKey));
    }

    public void testGetObject(){
        String keyIsEmpty = "EMPTY";
        assertNull(testMessage.getObject(Integer.class, keyIsEmpty));

        String keyLanguage = ChangeRegistration.KEY_LANGUAGE;
        Language language = Test.GENERAL_LANGUAGE;
        testMessage.setParameters(keyLanguage, language);
        assertEquals(Test.MATCH, language, testMessage.getObject(Language.class, keyLanguage));

        String languageString = language.toString();
        testMessage.setParameters(keyLanguage, languageString);
        assertEquals(Test.MATCH, language, testMessage.getObject(Language.class, keyLanguage));

        String keyImage = Choice.KEY_IMAGE;
        Image testImage = Test.GENERAL_IMAGE;
        testMessage.setParameters(keyImage, testImage.getStore());
        assertTrue(Validator.validateImage(testImage, (Image) testMessage.getObject(Image.class, keyImage)));

        String keyDTCs = GetDTCsResponse.KEY_DTC;
        List<String> testDTCs = Test.GENERAL_STRING_LIST;
        testMessage.setParameters(keyDTCs, testDTCs);
        assertEquals(Test.MATCH, testDTCs, testMessage.getObject(String.class, keyDTCs));

        String keyTTSchunks = Alert.KEY_TTS_CHUNKS;
        List<TTSChunk> testTTSchunks = Test.GENERAL_TTSCHUNK_LIST;
        testMessage.setParameters(keyTTSchunks, testTTSchunks);
        assertEquals(Test.MATCH, testTTSchunks, testMessage.getObject(TTSChunk.class, keyTTSchunks));

        List<Hashtable<String, Object>> testListTTSchunks = new ArrayList<>();
        for(TTSChunk ttsChunk : testTTSchunks){
            testListTTSchunks.add(ttsChunk.getStore());
        }
        testMessage.setParameters(keyTTSchunks, testListTTSchunks);
        List<TTSChunk> underTest = (List<TTSChunk>) testMessage.getObject(TTSChunk.class, keyTTSchunks);
        assertTrue(Validator.validateTtsChunks(testTTSchunks, underTest));

        String keyMediaClockFormats = DisplayCapabilities.KEY_MEDIA_CLOCK_FORMATS;
        List<MediaClockFormat> testMediaClockFormats = Test.GENERAL_MEDIACLOCKFORMAT_LIST;
        testMessage.setParameters(keyMediaClockFormats, testMediaClockFormats);
        assertEquals(Test.MATCH, testMediaClockFormats, testMessage.getObject(MediaClockFormat.class, keyMediaClockFormats));

        List<String> testListMediaClockFormats = new ArrayList<>();
        for(MediaClockFormat mcFormat : testMediaClockFormats){
            testListMediaClockFormats.add(mcFormat.toString());
        }
        testMessage.setParameters(keyMediaClockFormats, testListMediaClockFormats);
        assertEquals(Test.MATCH, testMediaClockFormats, testMessage.getObject(MediaClockFormat.class, keyMediaClockFormats));

        assertNull(testMessage.getObject(Image.class, keyLanguage)); // Test incorrect class
    }
}
