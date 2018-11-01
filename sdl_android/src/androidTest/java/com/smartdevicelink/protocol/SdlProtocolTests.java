package com.smartdevicelink.protocol;

import android.os.Bundle;
import android.util.Log;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.SdlConnection.SdlConnection;
import com.smartdevicelink.protocol.enums.ControlFrameTags;
import com.smartdevicelink.protocol.enums.FrameDataControlFrameType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.protocol.SdlProtocol.MessageFrameAssembler;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.SampleRpc;
import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.RouterServiceValidator;
import com.smartdevicelink.transport.TransportManager;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;

import junit.framework.Assert;

import org.mockito.ArgumentCaptor;
import org.mockito.internal.util.reflection.FieldReader;
import org.mockito.internal.util.reflection.FieldSetter;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SdlProtocolTests  extends AndroidTestCase2 {

    int max_int = 2147483647;
    byte[] payload;
    MultiplexTransportConfig config;
    SdlProtocol protocol;

    ISdlProtocol defaultListener = mock(ISdlProtocol.class);
    TransportManager mockTransportManager = mock(TransportManager.class);

    public static class DidReceiveListener implements ISdlProtocol{
        boolean didReceive = false;

        public void reset(){
            didReceive = false;
        }
        public boolean didReceive(){
            return didReceive;
        }
        @Override
        public void onProtocolMessageBytesToSend(SdlPacket packet) {}
        @Override
        public void onProtocolMessageReceived(ProtocolMessage msg) {
            didReceive = true;
            Log.d("DidReceiveListener", "RPC Type: " + msg.getRPCType());
            Log.d("DidReceiveListener", "Function Id: " + msg.getFunctionID());
            Log.d("DidReceiveListener", "JSON Size: " + msg.getJsonSize());
        }
        @Override
        public void onProtocolSessionStarted(SessionType sessionType,byte sessionID, byte version, String correlationID, int hashID,boolean isEncrypted){}
        @Override
        public void onProtocolSessionNACKed(SessionType sessionType,byte sessionID, byte version, String correlationID, List<String> rejectedParams) {}
        @Override
        public void onProtocolSessionEnded(SessionType sessionType,byte sessionID, String correlationID) {}
        @Override
        public void onProtocolSessionEndedNACKed(SessionType sessionType,byte sessionID, String correlationID) {}
        @Override
        public void onProtocolHeartbeat(SessionType sessionType, byte sessionID) {}
        @Override
        public void onProtocolHeartbeatACK(SessionType sessionType,byte sessionID) {}
        @Override
        public void onProtocolServiceDataACK(SessionType sessionType,int dataSize, byte sessionID) {}
        @Override
        public void onResetOutgoingHeartbeat(SessionType sessionType,byte sessionID) {}
        @Override
        public void onResetIncomingHeartbeat(SessionType sessionType,byte sessionID) {}
        @Override
        public void onProtocolError(String info, Exception e) {}
        @Override
        public byte getSessionId() {return 0;}
        @Override
        public void shutdown(String info) {}
        @Override
        public void onTransportDisconnected(String info, boolean altTransportAvailable, MultiplexTransportConfig transportConfig) {}
        @Override
        public SdlSecurityBase getSdlSecurity() {return null;}
        @Override
        public VideoStreamingParameters getDesiredVideoParams() {return null; }
        @Override
        public void setAcceptedVideoParams(VideoStreamingParameters acceptedVideoParams) {}
        @Override
        public void stopStream(SessionType serviceType) {}
    };

    DidReceiveListener onProtocolMessageReceivedListener = new DidReceiveListener();


    public void setUp(){
        config = new MultiplexTransportConfig(this.mContext, SdlUnitTestContants.TEST_APP_ID);
        protocol = new SdlProtocol(defaultListener,config);
    }

    private void substituteTransportManager() {
        try {
            FieldSetter.setField(protocol, protocol.getClass().getDeclaredField("transportManager"), mockTransportManager);
        } catch (NoSuchFieldException e) {
            fail(e.getMessage());
        }
    }

    private TransportManager.TransportEventListener retrieveTransportEventListenerField() {
        TransportManager.TransportEventListener listener = null;
        try {
            FieldReader reader = new FieldReader(protocol, protocol.getClass().getDeclaredField("transportEventListener"));
            if (!reader.isNull()) {
                listener = (TransportManager.TransportEventListener)reader.read();
            }
        } catch (NoSuchFieldException e) {
            fail(e.getMessage());
        }
        if (listener == null) {
            fail("Cannot access to 'transportEventListener' private field in SdlProtocol");
        }
        return listener;
    }

    public void testBase(){
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener,config);

    }

    public void testVersion(){
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener,config);

        sdlProtocol.setVersion((byte)0x01);
        assertEquals((byte)0x01,sdlProtocol.getProtocolVersion().getMajor());

        sdlProtocol = new SdlProtocol(defaultListener,config);
        sdlProtocol.setVersion((byte)0x02);
        assertEquals((byte)0x02,sdlProtocol.getProtocolVersion().getMajor());

        sdlProtocol = new SdlProtocol(defaultListener,config);
        sdlProtocol.setVersion((byte)0x03);
        assertEquals((byte)0x03,sdlProtocol.getProtocolVersion().getMajor());

        sdlProtocol = new SdlProtocol(defaultListener,config);
        sdlProtocol.setVersion((byte)0x04);
        assertEquals((byte)0x04,sdlProtocol.getProtocolVersion().getMajor());

        sdlProtocol = new SdlProtocol(defaultListener,config);
        sdlProtocol.setVersion((byte)0x05);
        assertEquals((byte)0x05,sdlProtocol.getProtocolVersion().getMajor());

        //If we get newer than 5, it should fall back to 5
        sdlProtocol = new SdlProtocol(defaultListener,config);
        sdlProtocol.setVersion((byte)0x06);
        assertEquals((byte)0x05,sdlProtocol.getProtocolVersion().getMajor());

        //Is this right?
        sdlProtocol = new SdlProtocol(defaultListener,config);
        sdlProtocol.setVersion((byte)0x00);
        assertEquals((byte)0x01,sdlProtocol.getProtocolVersion().getMajor());
    }

    public void testMtu(){
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener,config);

        sdlProtocol.setVersion((byte)0x01);

        try{
            assertEquals(sdlProtocol.getMtu(), 1500-8);

            //Version 2
            sdlProtocol.setVersion((byte)0x02);
            assertEquals(sdlProtocol.getMtu(), 1500-12);

            //Version 3
            sdlProtocol.setVersion((byte)0x03);
            assertEquals(sdlProtocol.getMtu(), 131072);

            //Version 4
            sdlProtocol.setVersion((byte)0x04);
            assertEquals(sdlProtocol.getMtu(), 131072);

            //Version 5
            sdlProtocol.setVersion((byte)0x05);
            assertEquals(sdlProtocol.getMtu(), 131072);

            //Version 5+
            sdlProtocol.setVersion((byte)0x06);
            assertEquals(sdlProtocol.getMtu(), 131072);

        }catch(Exception e){
            Assert.fail("Exceptin during reflection");
        }

    }

    public void testHandleFrame(){
        SampleRpc sampleRpc = new SampleRpc(4);
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener,config);
        MessageFrameAssembler assembler = sdlProtocol.new MessageFrameAssembler();
        try{
            assembler.handleFrame(sampleRpc.toSdlPacket());
        }catch(Exception e){
            Assert.fail("Exceptin during handleFrame - " + e.toString());
        }
    }
    public void testHandleFrameCorrupt(){
        SampleRpc sampleRpc = new SampleRpc(4);
        BinaryFrameHeader header = sampleRpc.getBinaryFrameHeader(true);
        header.setJsonSize(Integer.MAX_VALUE);
        sampleRpc.setBinaryFrameHeader(header);
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener,config);
        MessageFrameAssembler assembler = sdlProtocol.new MessageFrameAssembler();
        try{
            assembler.handleFrame(sampleRpc.toSdlPacket());
        }catch(Exception e){
            Assert.fail("Exceptin during handleFrame - " + e.toString());
        }
    }

    public void testHandleSingleFrameMessageFrame(){
        SampleRpc sampleRpc = new SampleRpc(4);
        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener,config);
        MessageFrameAssembler assembler = sdlProtocol.new MessageFrameAssembler();


        try{
            Method method = assembler.getClass().getDeclaredMethod ("handleSingleFrameMessageFrame", SdlPacket.class);
            method.setAccessible(true);
            method.invoke (assembler, sampleRpc.toSdlPacket());
        }catch(Exception e){
            Assert.fail("Exceptin during handleSingleFrameMessageFrame - " + e.toString());
        }
    }

    public void testHandleSingleFrameMessageFrameCorruptBfh(){
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

        SdlProtocol sdlProtocol = new SdlProtocol(defaultListener,config);


        sdlProtocol.handlePacketReceived(packet);
        assertFalse(onProtocolMessageReceivedListener.didReceive());

        onProtocolMessageReceivedListener.reset();
        SdlProtocol.MessageFrameAssembler assembler =sdlProtocol.getFrameAssemblerForFrame(packet);// sdlProtocol.new MessageFrameAssembler();
        assertNotNull(assembler);
        assembler.handleFrame(packet);
        assertFalse(onProtocolMessageReceivedListener.didReceive());

        try{
            Method  method = assembler.getClass().getDeclaredMethod("handleSingleFrameMessageFrame", SdlPacket.class);
            method.setAccessible(true);
            method.invoke (assembler, sampleRpc.toSdlPacket());
        }catch(Exception e){
            Assert.fail("Exceptin during handleSingleFrameMessageFrame - " + e.toString());
        }
    }





    public void testNormalCase(){
        setUp();
        payload = new byte[]{0x00,0x02,0x05,0x01,0x01,0x01,0x05,0x00};
        byte sessionID = 1, version = 1;
        int messageID = 1;
        boolean encrypted = false;
        SdlPacket sdlPacket = SdlPacketFactory.createMultiSendDataFirst(SessionType.RPC, sessionID, messageID, version, payload, encrypted);
        MessageFrameAssembler assembler = protocol.getFrameAssemblerForFrame(sdlPacket);

        assertNotNull(assembler);

        OutOfMemoryError oom_error = null;
        NullPointerException np_exception = null;
        try{
            assembler.handleMultiFrameMessageFrame(sdlPacket);
        }catch(OutOfMemoryError e){
            oom_error = e;
        }catch(NullPointerException z){
            np_exception = z;
        }catch(Exception e){
            e.printStackTrace();
            assertNotNull(null);
        }

        assertNull(np_exception);
        assertNull(oom_error);

        payload = new byte[23534];
        sdlPacket = SdlPacketFactory.createMultiSendDataRest(SessionType.RPC, sessionID, payload.length, (byte) 3, messageID, version, payload, 0, 1500, encrypted);
        assembler = protocol.getFrameAssemblerForFrame(sdlPacket);
        try{
            assembler.handleMultiFrameMessageFrame(sdlPacket);
        }catch(OutOfMemoryError e){
            oom_error = e;
        }catch(NullPointerException z){
            np_exception = z;
        }catch(Exception e){
            assertNotNull(null);
        }

        assertNull(np_exception);
        assertNull(oom_error);
    }

    public void testOverallocatingAccumulator(){
        setUp();
        ByteArrayOutputStream builder = new ByteArrayOutputStream();
        for(int i = 0; i < 8; i++){
            builder.write(0x0F);
        }
        payload = builder.toByteArray();
        byte sessionID = 1, version = 1;
        int messageID = 1;
        boolean encrypted = false;
        SdlPacket sdlPacket = SdlPacketFactory.createMultiSendDataFirst(SessionType.RPC, sessionID, messageID, version, payload, encrypted);
        MessageFrameAssembler assembler = protocol.getFrameAssemblerForFrame(sdlPacket);

        OutOfMemoryError oom_error = null;
        NullPointerException np_exception = null;
        try{
            assembler.handleMultiFrameMessageFrame(sdlPacket);
        }catch(OutOfMemoryError e){
            oom_error = e;
        }catch(NullPointerException z){
            np_exception = z;
        }catch(Exception e){
            assertNotNull(null);
        }

        assertNull(np_exception);
        assertNull(oom_error);

        payload = new byte[23534];
        sdlPacket = SdlPacketFactory.createMultiSendDataRest(SessionType.RPC, sessionID, payload.length, (byte) 3, messageID, version, payload, 0, 1500, encrypted);
        assembler = protocol.getFrameAssemblerForFrame(sdlPacket);

        try{
            assembler.handleMultiFrameMessageFrame(sdlPacket);
        }catch(OutOfMemoryError e){
            oom_error = e;
        }catch(NullPointerException z){
            np_exception = z;
        }catch(Exception e){
            assertNotNull(null);
        }

        assertNull(np_exception);
        assertNull(oom_error);

    }

    private void runStartRpcServiceSequence(TransportManager.TransportEventListener transportEventListener,
                                            List<TransportRecord> connectedTransports, TransportRecord transport,
                                            int version, String versionString, int sessionId, int messageId) {
        // simulate primary transport connection
        connectedTransports.add(transport);
        transportEventListener.onTransportConnected(connectedTransports);

        // SdlProtocol should have sent StartService. Reply with dummy StartServiceACK.
        final int hashId = 12345;
        final long mtu = 131072;
        final ArrayList<String> secondaryTransports = new ArrayList<>(Arrays.asList("TCP_WIFI"));
        final ArrayList<Integer> audioServiceTransports = new ArrayList<>(Arrays.asList(2));
        final ArrayList<Integer> videoServiceTransports = new ArrayList<>(Arrays.asList(2));

        SdlPacket rpcStartServiceAck = new SdlPacket(
                version, false, SdlPacket.FRAME_TYPE_CONTROL, SdlPacket.SERVICE_TYPE_RPC,
                FrameDataControlFrameType.StartSessionACK.getValue(), sessionId, 0, messageId, null);
        rpcStartServiceAck.putTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION, versionString);
        rpcStartServiceAck.putTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID, hashId);
        rpcStartServiceAck.putTag(ControlFrameTags.RPC.StartServiceACK.MTU, mtu);
        rpcStartServiceAck.putTag(ControlFrameTags.RPC.StartServiceACK.SECONDARY_TRANSPORTS, secondaryTransports);
        rpcStartServiceAck.putTag(ControlFrameTags.RPC.StartServiceACK.AUDIO_SERVICE_TRANSPORTS, audioServiceTransports);
        rpcStartServiceAck.putTag(ControlFrameTags.RPC.StartServiceACK.VIDEO_SERVICE_TRANSPORTS, videoServiceTransports);
        rpcStartServiceAck.constructPacket();
        rpcStartServiceAck.setTransportRecord(transport);

        protocol.handlePacketReceived(rpcStartServiceAck);

        // caller should verify ISdlProtocol.onProtocolMessageBytesToSend()
    }

    public void testTransportEventUpdateWithTCPConfig() {
        substituteTransportManager();
        TransportManager.TransportEventListener transportEventListener = retrieveTransportEventListenerField();

        List<TransportRecord> connectedTransports = new ArrayList<>(2);

        // Bluetooth transport for primary
        TransportRecord bluetoothRecord = new TransportRecord(TransportType.BLUETOOTH, "00:11:22:AA:BB:CC");
        // TCP transport for secondary
        final String ipAddress = "127.0.0.1";
        final int tcpPort = 12345;
        TransportRecord tcpRecord = new TransportRecord(TransportType.TCP, ipAddress + ":" + tcpPort);

        final int version = 5;
        final String versionString = "5.1.0";
        final int sessionId = 1;
        int messageId = 0;

        // simulate Bluetooth connection, Start Service and Start Service ACK sequence
        runStartRpcServiceSequence(transportEventListener, connectedTransports, bluetoothRecord,
                version, versionString, sessionId, messageId);
        messageId++;

        // simulate a TransportEventUpdate frame from Core
        SdlPacket transportEventUpdate = new SdlPacket(
                version, false, SdlPacket.FRAME_TYPE_CONTROL, SdlPacket.SERVICE_TYPE_CONTROL,
                FrameDataControlFrameType.TransportEventUpdate.getValue(), sessionId, 0, messageId, null);
        transportEventUpdate.putTag(ControlFrameTags.RPC.TransportEventUpdate.TCP_IP_ADDRESS, ipAddress);
        transportEventUpdate.putTag(ControlFrameTags.RPC.TransportEventUpdate.TCP_PORT, tcpPort);
        transportEventUpdate.constructPacket();
        messageId++;

        protocol.handlePacketReceived(transportEventUpdate);

        // verify that SdlProtocol initiates a TCP connection
        ArgumentCaptor<Bundle> bundleCaptor = ArgumentCaptor.forClass(Bundle.class);
        verify(mockTransportManager).requestSecondaryTransportConnection(eq((byte)-1), bundleCaptor.capture());
        Bundle b = bundleCaptor.getValue();
        assertNotNull(b);
        assertEquals(ipAddress, b.getString(ControlFrameTags.RPC.TransportEventUpdate.TCP_IP_ADDRESS));
        assertEquals(tcpPort, b.getInt(ControlFrameTags.RPC.TransportEventUpdate.TCP_PORT));

        // Simulate TCP connection establishment. SdlProtocol should trigger RegisterSecondaryTransport frame.
        connectedTransports.add(tcpRecord);
        transportEventListener.onTransportConnected(connectedTransports);

        // Finally, verify that SdlProtocol has sent out StartService frame (for RPC service) then
        // RegisterSecondaryTransport frame.
        ArgumentCaptor<SdlPacket> rpcStartServiceCaptor = ArgumentCaptor.forClass(SdlPacket.class);
        verify(defaultListener, times(2)).onProtocolMessageBytesToSend(rpcStartServiceCaptor.capture());

        List<SdlPacket> packetList = rpcStartServiceCaptor.getAllValues();
        assertEquals(2, packetList.size());

        // StartService frame
        SdlPacket rpcStartService = packetList.get(0);
        assertEquals(SdlPacket.FRAME_TYPE_CONTROL, rpcStartService.frameType);
        assertEquals(SdlPacket.SERVICE_TYPE_RPC, rpcStartService.serviceType);
        assertEquals(SdlPacket.FRAME_INFO_START_SERVICE, rpcStartService.frameInfo);

        // RegisterSecondaryTransport frame
        SdlPacket registerSecondaryTransport = packetList.get(1);
        assertEquals(SdlPacket.FRAME_TYPE_CONTROL, registerSecondaryTransport.frameType);
        assertEquals(SdlPacket.SERVICE_TYPE_CONTROL, registerSecondaryTransport.serviceType);
        assertEquals(SdlPacket.FRAME_INFO_REGISTER_SECONDARY_TRANSPORT, registerSecondaryTransport.frameInfo);
    }

    protected class SdlConnectionTestClass extends SdlConnection {
        protected boolean connected = false;
        public SdlConnectionTestClass(BaseTransportConfig transportConfig) {
            super(transportConfig);
        }

        protected SdlConnectionTestClass(BaseTransportConfig transportConfig,RouterServiceValidator rsvp){
            super(transportConfig,rsvp);
        }

        @Override
        public void onTransportConnected() {
            super.onTransportConnected();
            connected = true;
        }

        @Override
        public void onTransportDisconnected(String info) {
            connected = false;
            //Grab a currently running router service
            RouterServiceValidator rsvp2 = new RouterServiceValidator(mContext);
            rsvp2.setFlags(RouterServiceValidator.FLAG_DEBUG_NONE);
            assertTrue(rsvp2.validate());
            assertNotNull(rsvp2.getService());
            super.onTransportDisconnected(info);
        }

        @Override
        public void onTransportError(String info, Exception e) {
            connected = false;
            super.onTransportError(info, e);
        }
    }
}