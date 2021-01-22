package com.smartdevicelink.protocol;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.livio.BSON.BsonEncoder;
import com.smartdevicelink.protocol.enums.ControlFrameTags;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static com.smartdevicelink.protocol.enums.ControlFrameTags.RPC.StartServiceACK.VEHICLE_MAKE;
import static com.smartdevicelink.protocol.enums.ControlFrameTags.RPC.StartServiceACK.VEHICLE_MODEL;
import static com.smartdevicelink.protocol.enums.ControlFrameTags.RPC.StartServiceACK.VEHICLE_MODEL_YEAR;
import static com.smartdevicelink.protocol.enums.ControlFrameTags.RPC.StartServiceACK.VEHICLE_TRIM;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

@RunWith(AndroidJUnit4.class)
public class SdlPacketTests {

    // Test variables
    private final int TEST_HASH_ID = 65537;
    private final String TEST_PROTOCOL_VERSION = "5.0.0";
    private final Long TEST_MTU = (long) 131072;

    // Test payload from core representing hashId and mtu size
    private byte[] corePayload = hexStringToByteArray("39000000" +
            "10" +
            "68617368496400" +
            "01000100" +
            "126d747500" +
            "000002" + "0000000000" + "02" +
            "70726f746f636f6c56657273" +
            "696f6e0006000000352e302e300000");

    @Test
    public void testNull() {
        byte[] testPayload = hexStringToByteArray(
                "16000000" +
                        "02" +
                        "68656c6c6f" + "00" + // hello
                        "06000000" + "776f726c64" + "00" + //world
                        "00");
        String tag = "hello";

        SdlPacket sdlPacket = new SdlPacket();

        assertNull(sdlPacket.getTag(tag));
        sdlPacket.setPayload(testPayload);
        assertEquals(sdlPacket.getTag("hello"), "world");

        assertEquals(1, sdlPacket.getVersion());

        sdlPacket.setPriorityCoefficient(TestValues.GENERAL_INT);
        assertEquals(TestValues.GENERAL_INT, sdlPacket.getPrioirtyCoefficient());

        sdlPacket.setTransportRecord(new TransportRecord(TransportType.TCP, TestValues.GENERAL_STRING));
        assertEquals(TransportType.TCP, sdlPacket.getTransportRecord().getType());
        assertEquals(TestValues.GENERAL_STRING, sdlPacket.getTransportRecord().getAddress());
    }

    @Test
    public void testVehicleMake() {
        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put(VEHICLE_MAKE, "Ford");
        testMap.put(VEHICLE_MODEL, "Mustang");
        testMap.put(VEHICLE_TRIM, "GT");
        testMap.put(VEHICLE_MODEL_YEAR, "2019");

        byte[] testPayload = BsonEncoder.encodeToBytes(testMap);


        SdlPacket sdlPacket = new SdlPacket();

        assertNull(sdlPacket.getTag(VEHICLE_MAKE));
        assertNull(sdlPacket.getTag(VEHICLE_MODEL));
        assertNull(sdlPacket.getTag(VEHICLE_TRIM));
        assertNull(sdlPacket.getTag(VEHICLE_MODEL_YEAR));
        sdlPacket.setPayload(testPayload);
        assertEquals(sdlPacket.getTag(VEHICLE_MAKE), "Ford");
        assertEquals(sdlPacket.getTag(VEHICLE_MODEL), "Mustang");
        assertEquals(sdlPacket.getTag(VEHICLE_TRIM), "GT");
        assertEquals(sdlPacket.getTag(VEHICLE_MODEL_YEAR), "2019");
    }

    @Test
    public void testBsonDecoding() {
        SdlPacket sdlPacket = new SdlPacket();
        sdlPacket.setPayload(corePayload);
        assertEquals(sdlPacket.getTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID), TEST_HASH_ID);
        assertEquals(sdlPacket.getTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION), TEST_PROTOCOL_VERSION);
        assertEquals(sdlPacket.getTag(ControlFrameTags.RPC.StartServiceACK.MTU), TEST_MTU);
    }

    @Test
    public void testBsonEncoding() {
        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put(ControlFrameTags.RPC.StartServiceACK.HASH_ID, TEST_HASH_ID);
        testMap.put(ControlFrameTags.RPC.StartServiceACK.MTU, TEST_MTU);
        testMap.put(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION, TEST_PROTOCOL_VERSION);

        byte[] observed = BsonEncoder.encodeToBytes(testMap);
        assertEquals(observed.length, corePayload.length);
        for (int i = 0; i < observed.length; i++) {
            assertEquals(observed[i], corePayload[i]);
        }
    }

    // Helper method for converting String to Byte Array
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
