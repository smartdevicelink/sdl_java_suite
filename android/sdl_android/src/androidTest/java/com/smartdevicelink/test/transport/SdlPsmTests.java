package com.smartdevicelink.test.transport;

import android.util.Log;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlPacketFactory;
import com.smartdevicelink.protocol.SdlProtocol;
import com.smartdevicelink.protocol.enums.ControlFrameTags;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.transport.SdlPsm;

import junit.framework.TestCase;

import org.junit.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.transport.SdlPsm}
 */
public class SdlPsmTests extends TestCase {
    private static final String TAG = "SdlPsmTests";
    private static final int MAX_DATA_LENGTH_V1 = SdlProtocol.V1_V2_MTU_SIZE - SdlProtocol.V1_HEADER_SIZE;
    private static final int MAX_DATA_LENGTH_V2 = SdlProtocol.V1_V2_MTU_SIZE - SdlProtocol.V2_HEADER_SIZE;
    SdlPsm sdlPsm;
    Field frameType, dataLength, version, controlFrameInfo;
    Method transitionOnInput;
    byte rawByte = (byte) 0x0;

    SdlPacket startServiceACK;

    protected void setUp() throws Exception {
        super.setUp();
        sdlPsm = new SdlPsm();
        transitionOnInput = SdlPsm.class.getDeclaredMethod("transitionOnInput", byte.class, int.class);
        transitionOnInput.setAccessible(true);

        frameType = SdlPsm.class.getDeclaredField("frameType");
        dataLength = SdlPsm.class.getDeclaredField("dataLength");
        version = SdlPsm.class.getDeclaredField("version");
        controlFrameInfo = SdlPsm.class.getDeclaredField("controlFrameInfo");
        frameType.setAccessible(true);
        dataLength.setAccessible(true);
        version.setAccessible(true);
        controlFrameInfo.setAccessible(true);

        startServiceACK = SdlPacketFactory.createStartSessionACK(SessionType.RPC, (byte) 0x01, (byte) 0x05, (byte) 0x05);
        startServiceACK.putTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID, "3bb34978fe3a");
        startServiceACK.putTag(ControlFrameTags.RPC.StartServiceACK.MTU, "150000");
        startServiceACK.putTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION, "5.1.0");
    }


    public void testHappyPath() {


        byte[] packetBytes = startServiceACK.constructPacket();

        SdlPsm sdlPsm = new SdlPsm();
        boolean didTransition = false;

        for (byte packetByte : packetBytes) {
            didTransition = sdlPsm.handleByte(packetByte);
            assertTrue(didTransition);
        }

        assertEquals(SdlPsm.FINISHED_STATE, sdlPsm.getState());
        SdlPacket parsedPacket = sdlPsm.getFormedPacket();
        assertNotNull(parsedPacket);

        assertEquals(startServiceACK.getVersion(), parsedPacket.getVersion());
        assertEquals(startServiceACK.getServiceType(), parsedPacket.getServiceType());
        assertEquals(startServiceACK.getFrameInfo(), parsedPacket.getFrameInfo());
        assertEquals(startServiceACK.getFrameType(), parsedPacket.getFrameType());
        assertEquals(startServiceACK.getDataSize(), parsedPacket.getDataSize());
        assertEquals(startServiceACK.getMessageId(), parsedPacket.getMessageId());

        assertTrue(startServiceACK.getTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID).equals(parsedPacket.getTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID)));
        assertTrue(startServiceACK.getTag(ControlFrameTags.RPC.StartServiceACK.MTU).equals(parsedPacket.getTag(ControlFrameTags.RPC.StartServiceACK.MTU)));
        assertTrue(startServiceACK.getTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION).equals(parsedPacket.getTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION)));

    }

    public void testGarbledControlFrame() {
        try {
            rawByte = 0x0;
            version.set(sdlPsm, 1);
            controlFrameInfo.set(sdlPsm, SdlPacket.FRAME_INFO_START_SERVICE);
            frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_CONTROL);

            dataLength.set(sdlPsm, MAX_DATA_LENGTH_V1 + 1);
            int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_4_STATE);

            assertEquals(TestValues.MATCH, SdlPsm.ERROR_STATE, STATE);
        } catch (Exception e) {
            Assert.fail(e.toString());
            Log.e(TAG, e.toString());
        }
    }

    public void testMaximumControlFrameForVersion1() {
        try {
            rawByte = 0x0;
            version.set(sdlPsm, 1);
            controlFrameInfo.set(sdlPsm, SdlPacket.FRAME_INFO_START_SERVICE);
            frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_CONTROL);

            dataLength.set(sdlPsm, MAX_DATA_LENGTH_V1);
            int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_4_STATE);

            assertEquals(TestValues.MATCH, SdlPsm.DATA_PUMP_STATE, STATE);
        } catch (Exception e) {
            Assert.fail(e.toString());
            Log.e(TAG, e.toString());
        }
    }

    public void testMaximumControlFrameForVersion2Plus() {
        try {
            rawByte = 0x0;
            version.set(sdlPsm, 2);
            controlFrameInfo.set(sdlPsm, SdlPacket.FRAME_INFO_START_SERVICE);
            frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_CONTROL);

            dataLength.set(sdlPsm, MAX_DATA_LENGTH_V2);
            int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_4_STATE);

            assertEquals(TestValues.MATCH, SdlPsm.MESSAGE_1_STATE, STATE);
        } catch (Exception e) {
            Assert.fail(e.toString());
            Log.e(TAG, e.toString());
        }
    }

    public void testOutOfMemoryDS4() {
        try {
            rawByte = 0x0;
            version.set(sdlPsm, 1);
            frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);

            dataLength.set(sdlPsm, 2147483647);
            int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.MESSAGE_4_STATE);

            assertEquals(TestValues.MATCH, SdlPsm.ERROR_STATE, STATE);
        } catch (Exception e) {
            Assert.fail(e.toString());
            Log.e(TAG, e.toString());
        }
    }

    public void testNegativeDataSize() {
        byte[] packetBytes = startServiceACK.constructPacket();

        SdlPsm sdlPsm = new SdlPsm();
        boolean didTransition = false;

        for (byte packetByte : packetBytes) {
            int state = sdlPsm.getState();
            switch (state) {
                case SdlPsm.MESSAGE_4_STATE:
                    didTransition = sdlPsm.handleByte(packetByte);
                    assertFalse(didTransition);
                    assertEquals(SdlPsm.ERROR_STATE, sdlPsm.getState());
                    return;
                case SdlPsm.DATA_SIZE_1_STATE:
                case SdlPsm.DATA_SIZE_2_STATE:
                case SdlPsm.DATA_SIZE_3_STATE:
                case SdlPsm.DATA_SIZE_4_STATE:
                    didTransition = sdlPsm.handleByte((byte) 0xFF);
                    assertTrue(didTransition);
                    break;
                default:
                    didTransition = sdlPsm.handleByte(packetByte);
                    assertTrue(didTransition);
            }
        }
    }

    public void testIncorrectVersion() {
        SdlPacket startServiceACK = SdlPacketFactory.createStartSessionACK(SessionType.RPC, (byte) 0x01, (byte) 0x05, (byte) 0x06);
        startServiceACK.putTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID, "3bb34978fe3a");
        startServiceACK.putTag(ControlFrameTags.RPC.StartServiceACK.MTU, "150000");
        startServiceACK.putTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION, "5.1.0");
        byte[] packetBytes = startServiceACK.constructPacket();

        SdlPsm sdlPsm = new SdlPsm();
        boolean didTransition = sdlPsm.handleByte(packetBytes[0]);
        assertFalse(didTransition);
    }

    public void testIncorrectService() {

        byte[] packetBytes = startServiceACK.constructPacket();

        SdlPsm sdlPsm = new SdlPsm();
        boolean didTransition = false;

        for (byte packetByte : packetBytes) {
            int state = sdlPsm.getState();
            switch (state) {
                case SdlPsm.SERVICE_TYPE_STATE:
                    didTransition = sdlPsm.handleByte((byte) 0xFF);
                    assertFalse(didTransition);
                    assertEquals(SdlPsm.ERROR_STATE, sdlPsm.getState());
                    return;
                default:
                    didTransition = sdlPsm.handleByte(packetByte);
                    assertTrue(didTransition);
            }
        }
    }

    public void testRecovery() {
        byte[] packetBytes = startServiceACK.constructPacket();
        byte[] processingBytes = new byte[packetBytes.length + 15];

        System.arraycopy(packetBytes, 10, processingBytes, 0, 15);
        System.arraycopy(packetBytes, 0, processingBytes, 15, packetBytes.length);


        SdlPsm sdlPsm = new SdlPsm();
        boolean didTransition = false;
        byte packetByte;
        int state = SdlPsm.START_STATE, i = 0, limit = 0;

        while (state != SdlPsm.FINISHED_STATE && limit < 10) {

            packetByte = processingBytes[i];
            didTransition = sdlPsm.handleByte(packetByte);
            state = sdlPsm.getState();
            if (!didTransition) {
                assertEquals(SdlPsm.ERROR_STATE, state);
                sdlPsm.reset();
            } else if (state == SdlPsm.FINISHED_STATE) {
                break;
            }

            if (i == processingBytes.length - 1) {
                i = 0;
                limit++;
            } else {
                i++;
            }
        }

        assertEquals(SdlPsm.FINISHED_STATE, sdlPsm.getState());
        SdlPacket parsedPacket = sdlPsm.getFormedPacket();
        assertNotNull(parsedPacket);

    }


    protected void tearDown() throws Exception {
        super.tearDown();
    }
}