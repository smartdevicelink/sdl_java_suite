package com.smartdevicelink.protocol;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.SampleRpc;
import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.util.Version;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class SdlProtocolTests {

    int max_int = 2147483647;
    byte[] payload;
    MultiplexTransportConfig config;
    SdlProtocol protocol;

    ISdlProtocol defaultListener = mock(ISdlProtocol.class);

    public static class DidReceiveListener implements ISdlProtocol {
        boolean didReceive = false;

        public void reset() {
            didReceive = false;
        }

        public boolean didReceive() {
            return didReceive;
        }

        @Override
        public void onProtocolMessageReceived(ProtocolMessage msg) {
            didReceive = true;
            Log.d("DidReceiveListener", "RPC Type: " + msg.getRPCType());
            Log.d("DidReceiveListener", "Function Id: " + msg.getFunctionID());
            Log.d("DidReceiveListener", "JSON Size: " + msg.getJsonSize());
        }

        @Override
        public void onServiceStarted(SdlPacket packet, SessionType serviceType, int sessionID, Version version, boolean isEncrypted) {

        }

        @Override
        public void onServiceEnded(SdlPacket packet, SessionType serviceType, int sessionID) {

        }

        @Override
        public void onServiceError(SdlPacket packet, SessionType serviceType, int sessionID, String error) {

        }

        @Override
        public void onProtocolError(String info, Exception e) {

        }

        @Override
        public int getSessionId() {
            return 0;
        }

        @Override
        public void shutdown(String info) {

        }

        @Override
        public void onTransportDisconnected(String info, boolean altTransportAvailable, BaseTransportConfig transportConfig) {

        }

        @Override
        public SdlSecurityBase getSdlSecurity() {
            return null;
        }

        @Override
        public VideoStreamingParameters getDesiredVideoParams() {
            return null;
        }

        @Override
        public void setAcceptedVideoParams(VideoStreamingParameters acceptedVideoParams) {

        }

        @Override
        public void onAuthTokenReceived(String authToken) {

        }

    }

    DidReceiveListener onProtocolMessageReceivedListener = new DidReceiveListener();


    @Before
    public void setUp() {
        config = new MultiplexTransportConfig(getInstrumentation().getContext(), SdlUnitTestContants.TEST_APP_ID);
        protocol = new SdlProtocol(defaultListener, config);
    }


    @Test
    public void testBase() {
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener, config);

    }

    @Test
    public void testVersion() {
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener, config);

        sdlProtocol.setVersion((byte) 0x01);
        assertEquals((byte) 0x01, sdlProtocol.getProtocolVersion().getMajor());

        sdlProtocol = new SdlProtocol(defaultListener, config);
        sdlProtocol.setVersion((byte) 0x02);
        assertEquals((byte) 0x02, sdlProtocol.getProtocolVersion().getMajor());

        sdlProtocol = new SdlProtocol(defaultListener, config);
        sdlProtocol.setVersion((byte) 0x03);
        assertEquals((byte) 0x03, sdlProtocol.getProtocolVersion().getMajor());

        sdlProtocol = new SdlProtocol(defaultListener, config);
        sdlProtocol.setVersion((byte) 0x04);
        assertEquals((byte) 0x04, sdlProtocol.getProtocolVersion().getMajor());

        sdlProtocol = new SdlProtocol(defaultListener, config);
        sdlProtocol.setVersion((byte) 0x05);
        assertEquals((byte) 0x05, sdlProtocol.getProtocolVersion().getMajor());

        //If we get newer than 5, it should fall back to 5
        sdlProtocol = new SdlProtocol(defaultListener, config);
        sdlProtocol.setVersion((byte) 0x06);
        assertEquals((byte) 0x05, sdlProtocol.getProtocolVersion().getMajor());

        //Is this right?
        sdlProtocol = new SdlProtocol(defaultListener, config);
        sdlProtocol.setVersion((byte) 0x00);
        assertEquals((byte) 0x01, sdlProtocol.getProtocolVersion().getMajor());
    }

    @Test
    public void testMtu() {
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener, config);

        sdlProtocol.setVersion((byte) 0x01);

        try {
            assertEquals(sdlProtocol.getMtu(), 1500 - 8);

            //Version 2
            sdlProtocol.setVersion((byte) 0x02);
            assertEquals(sdlProtocol.getMtu(), 1500 - 12);

            //Version 3
            sdlProtocol.setVersion((byte) 0x03);
            assertEquals(sdlProtocol.getMtu(), 131072);

            //Version 4
            sdlProtocol.setVersion((byte) 0x04);
            assertEquals(sdlProtocol.getMtu(), 131072);

            //Version 5
            sdlProtocol.setVersion((byte) 0x05);
            assertEquals(sdlProtocol.getMtu(), 131072);

            //Version 5+
            sdlProtocol.setVersion((byte) 0x06);
            assertEquals(sdlProtocol.getMtu(), 131072);

        } catch (Exception e) {
            Assert.fail("Exceptin during reflection");
        }

    }

    @Test
    public void testHandleFrame() {
        SampleRpc sampleRpc = new SampleRpc(4);
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener, config);
        SdlProtocolBase.MessageFrameAssembler assembler = sdlProtocol.new MessageFrameAssembler();
        try {
            assembler.handleFrame(sampleRpc.toSdlPacket());
        } catch (Exception e) {
            Assert.fail("Exceptin during handleFrame - " + e.toString());
        }
    }

    @Test
    public void testHandleFrameCorrupt() {
        SampleRpc sampleRpc = new SampleRpc(4);
        BinaryFrameHeader header = sampleRpc.getBinaryFrameHeader(true);
        header.setJsonSize(Integer.MAX_VALUE);
        sampleRpc.setBinaryFrameHeader(header);
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener, config);
        SdlProtocolBase.MessageFrameAssembler assembler = sdlProtocol.new MessageFrameAssembler();
        try {
            assembler.handleFrame(sampleRpc.toSdlPacket());
        } catch (Exception e) {
            Assert.fail("Exceptin during handleFrame - " + e.toString());
        }
    }

    @Test
    public void testHandleSingleFrameMessageFrame() {
        SampleRpc sampleRpc = new SampleRpc(4);
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener, config);
        SdlProtocolBase.MessageFrameAssembler assembler = sdlProtocol.new MessageFrameAssembler();


        try {
            Method method = assembler.getClass().getDeclaredMethod("handleSingleFrameMessageFrame", SdlPacket.class);
            method.setAccessible(true);
            method.invoke(assembler, sampleRpc.toSdlPacket());
        } catch (Exception e) {
            Assert.fail("Exceptin during handleSingleFrameMessageFrame - " + e.toString());
        }
    }

    @Test
    public void testHandleSingleFrameMessageFrameCorruptBfh() {
        SampleRpc sampleRpc = new SampleRpc(4);

        //Create a corrupted header
        BinaryFrameHeader header = sampleRpc.getBinaryFrameHeader(true);
        header.setJsonSize(5);
        header.setJsonData(new byte[5]);
        header.setJsonSize(Integer.MAX_VALUE);
        sampleRpc.setBinaryFrameHeader(header);

        SdlPacket packet = sampleRpc.toSdlPacket();

        BinaryFrameHeader binFrameHeader = BinaryFrameHeader.parseBinaryHeader(packet.payload);
        assertNull(binFrameHeader);

        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener, config);


        sdlProtocol.handlePacketReceived(packet);
        assertFalse(onProtocolMessageReceivedListener.didReceive());

        onProtocolMessageReceivedListener.reset();
        SdlProtocol.MessageFrameAssembler assembler = sdlProtocol.getFrameAssemblerForFrame(packet);// sdlProtocol.new MessageFrameAssembler();
        assertNotNull(assembler);
        assembler.handleFrame(packet);
        assertFalse(onProtocolMessageReceivedListener.didReceive());

        try {
            Method method = assembler.getClass().getDeclaredMethod("handleSingleFrameMessageFrame", SdlPacket.class);
            method.setAccessible(true);
            method.invoke(assembler, sampleRpc.toSdlPacket());
        } catch (Exception e) {
            Assert.fail("Exceptin during handleSingleFrameMessageFrame - " + e.toString());
        }
    }


    @Test
    public void testNormalCase() {
        setUp();
        payload = new byte[]{0x00, 0x02, 0x05, 0x01, 0x01, 0x01, 0x05, 0x00};
        byte sessionID = 1, version = 1;
        int messageID = 1;
        boolean encrypted = false;
        SdlPacket sdlPacket = SdlPacketFactory.createMultiSendDataFirst(SessionType.RPC, sessionID, messageID, version, payload, encrypted);
        SdlProtocolBase.MessageFrameAssembler assembler = protocol.getFrameAssemblerForFrame(sdlPacket);

        assertNotNull(assembler);

        OutOfMemoryError oom_error = null;
        NullPointerException np_exception = null;
        try {
            assembler.handleMultiFrameMessageFrame(sdlPacket);
        } catch (OutOfMemoryError e) {
            oom_error = e;
        } catch (NullPointerException z) {
            np_exception = z;
        } catch (Exception e) {
            e.printStackTrace();
            assertNotNull(null);
        }

        assertNull(np_exception);
        assertNull(oom_error);

        payload = new byte[23534];
        sdlPacket = SdlPacketFactory.createMultiSendDataRest(SessionType.RPC, sessionID, payload.length, (byte) 3, messageID, version, payload, 0, 1500, encrypted);
        assembler = protocol.getFrameAssemblerForFrame(sdlPacket);
        try {
            assembler.handleMultiFrameMessageFrame(sdlPacket);
        } catch (OutOfMemoryError e) {
            oom_error = e;
        } catch (NullPointerException z) {
            np_exception = z;
        } catch (Exception e) {
            assertNotNull(null);
        }

        assertNull(np_exception);
        assertNull(oom_error);
    }

    @Test
    public void testOverallocatingAccumulator() {
        setUp();
        ByteArrayOutputStream builder = new ByteArrayOutputStream();
        for (int i = 0; i < 8; i++) {
            builder.write(0x0F);
        }
        payload = builder.toByteArray();
        byte sessionID = 1, version = 1;
        int messageID = 1;
        boolean encrypted = false;
        SdlPacket sdlPacket = SdlPacketFactory.createMultiSendDataFirst(SessionType.RPC, sessionID, messageID, version, payload, encrypted);
        SdlProtocolBase.MessageFrameAssembler assembler = protocol.getFrameAssemblerForFrame(sdlPacket);

        OutOfMemoryError oom_error = null;
        NullPointerException np_exception = null;
        try {
            assembler.handleMultiFrameMessageFrame(sdlPacket);
        } catch (OutOfMemoryError e) {
            oom_error = e;
        } catch (NullPointerException z) {
            np_exception = z;
        } catch (Exception e) {
            assertNotNull(null);
        }

        assertNull(np_exception);
        assertNull(oom_error);

        payload = new byte[23534];
        sdlPacket = SdlPacketFactory.createMultiSendDataRest(SessionType.RPC, sessionID, payload.length, (byte) 3, messageID, version, payload, 0, 1500, encrypted);
        assembler = protocol.getFrameAssemblerForFrame(sdlPacket);

        try {
            assembler.handleMultiFrameMessageFrame(sdlPacket);
        } catch (OutOfMemoryError e) {
            oom_error = e;
        } catch (NullPointerException z) {
            np_exception = z;
        } catch (Exception e) {
            assertNotNull(null);
        }

        assertNull(np_exception);
        assertNull(oom_error);

    }

}