package com.smartdevicelink.test.proxy;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.AirbagStatus;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by austinkirk on 6/8/17.
 */

public class RPCStructTests extends TestCase {
    RPCStruct testStruct = new RPCStruct();

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

        testStruct.setValue(testKey, testInt);
        assertEquals(Test.MATCH, testInt, testStruct.getValue(testKey));

        testStruct.setValue(testKey, null);
        assertNull(testStruct.getValue(testKey));
    }

    public void testCommonObjectGetters(){
        String stringKey = "String";
        String testString = Test.GENERAL_STRING;
        testStruct.setValue(stringKey, testString);

        assertEquals(Test.MATCH, testStruct.getString(stringKey), testString);

        String intKey = "Integer";
        Integer testInt = Test.GENERAL_INT;
        testStruct.setValue(intKey, testInt);

        assertEquals(Test.MATCH, testStruct.getInteger(intKey), testInt);

        String doubleKey = "Double";
        Double testDouble = Test.GENERAL_DOUBLE;
        testStruct.setValue(doubleKey, testDouble);

        assertEquals(Test.MATCH, testStruct.getDouble(doubleKey), testDouble);

        String booleanKey = "Boolean";
        Boolean testBoolean = Test.GENERAL_BOOLEAN;
        testStruct.setValue(booleanKey, testBoolean);

        assertEquals(Test.MATCH, testStruct.getBoolean(booleanKey), testBoolean);

        String longKey = "Long";
        Long testLong = Test.GENERAL_LONG;
        testStruct.setValue(longKey, testLong);

        assertEquals(Test.MATCH, testStruct.getLong(longKey), testLong);

        testStruct.setValue(longKey, testInt);

        assertEquals(Test.MATCH, testStruct.getLong(longKey), new Long(testInt.longValue()));

        testStruct.setValue(longKey, testDouble);

        assertNull(testStruct.getLong(longKey));
    }

    public void testGetObject(){
        String keyIsEmpty = "EMPTY";
        assertNull(testStruct.getObject(Integer.class, keyIsEmpty));

        String keyAirbag = AirbagStatus.KEY_DRIVER_AIRBAG_DEPLOYED;
        VehicleDataEventStatus eventStatus = VehicleDataEventStatus.FAULT;
        testStruct.setValue(keyAirbag, eventStatus);
        assertEquals(Test.MATCH, eventStatus, testStruct.getObject(VehicleDataEventStatus.class, keyAirbag));

        String eventStatusString = VehicleDataEventStatus.FAULT.toString();
        testStruct.setValue(keyAirbag, eventStatusString);
        assertEquals(Test.MATCH, eventStatus, testStruct.getObject(VehicleDataEventStatus.class, keyAirbag));

        String keyImage = Choice.KEY_IMAGE;
        Image testImage = Test.GENERAL_IMAGE;
        testStruct.setValue(keyImage, testImage.getStore());
        assertTrue(Validator.validateImage(testImage, (Image) testStruct.getObject(Image.class, keyImage)));

        String keyVrCommand = Choice.KEY_VR_COMMANDS;
        List<String> testCommands = Test.GENERAL_STRING_LIST;
        testStruct.setValue(keyVrCommand, testCommands);
        assertEquals(Test.MATCH, testCommands, testStruct.getObject(String.class, keyVrCommand));

        String keyImageFields = DisplayCapabilities.KEY_IMAGE_FIELDS;
        List<ImageField> testImageFields = Test.GENERAL_IMAGEFIELD_LIST;
        testStruct.setValue(keyImageFields, testImageFields);
        assertEquals(Test.MATCH, testImageFields, testStruct.getObject(ImageField.class, keyImageFields));

        List<Hashtable<String, Object>> testListImageFields = new ArrayList<>();
        for(ImageField imgField : testImageFields){
            testListImageFields.add(imgField.getStore());
        }
        testStruct.setValue(keyImageFields, testListImageFields);
        List<ImageField> underTest = (List<ImageField>) testStruct.getObject(ImageField.class, keyImageFields);
        int index = 0;
        for(ImageField imgField : testImageFields){
            assertTrue(Validator.validateImageFields(imgField, underTest.get(index++)));
        }

        String keyMediaClockFormats = DisplayCapabilities.KEY_MEDIA_CLOCK_FORMATS;
        List<MediaClockFormat> testMediaClockFormats = Test.GENERAL_MEDIACLOCKFORMAT_LIST;
        testStruct.setValue(keyMediaClockFormats, testMediaClockFormats);
        assertEquals(Test.MATCH, testMediaClockFormats, testStruct.getObject(MediaClockFormat.class, keyMediaClockFormats));

        List<String> testListMediaClockFormats = new ArrayList<>();
        for(MediaClockFormat mcFormat : testMediaClockFormats){
            testListMediaClockFormats.add(mcFormat.toString());
        }
        testStruct.setValue(keyMediaClockFormats, testListMediaClockFormats);
        assertEquals(Test.MATCH, testMediaClockFormats, testStruct.getObject(MediaClockFormat.class, keyMediaClockFormats));

        assertNull(testStruct.getObject(Image.class, keyAirbag)); // Test incorrect class
    }

    public void testGetObjectExceptions(){
        String invalidKey = "invalid";
        testStruct.setValue(invalidKey, new Hashtable<>());
        assertNull(testStruct.getObject(Integer.class, invalidKey));

        List<Hashtable<String, Object>> list = new ArrayList<>();
        list.add(new Hashtable<String, Object>());
        testStruct.setValue(invalidKey, list);
        assertNull(testStruct.getObject(Integer.class, invalidKey));

        testStruct.setValue(invalidKey, Test.GENERAL_STRING);
        assertNull(testStruct.getObject(Integer.class, invalidKey));
    }
}
