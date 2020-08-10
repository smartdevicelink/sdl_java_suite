package com.smartdevicelink.transport;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.test.runner.AndroidJUnit4;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.BinaryFrameHeader;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlPacketFactory;
import com.smartdevicelink.protocol.enums.ControlFrameTags;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.rpc.UnregisterAppInterface;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import static androidx.test.InstrumentationRegistry.getContext;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class SdlRouterServiceTests {

    public static final String TAG = "SdlRouterServiceTests";
    private final int SAMPLE_RPC_CORRELATION_ID = 630;
    int version = 1;
    int sessionId = 1;
    ProtocolMessage pm = null;
    BinaryFrameHeader binFrameHeader = null;

	/**
	 * Ensure that the router service hardcoded number is the same as the integer value in
	 * the resources.
	 */
	@Test
	public void testVersionCorrectness(){
    	int resourceVersion = getContext().getResources().getInteger(com.smartdevicelink.test.R.integer.sdl_router_service_version_value);
		assertEquals(resourceVersion, SdlRouterService.ROUTER_SERVICE_VERSION_NUMBER);
	}

    /**
     * Test null bundle handling in AltTransportHandler when handling messages. Only test the case of
     * msg.what == TransportConstants.ROUTER_RECEIVED_PACKET
     */
    @Test
    public void testAlTransportHandlerHandleNullBundle() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        class AltTransportHandler extends Handler {
            ClassLoader loader;
            final WeakReference<SdlRouterService> provider;

            public AltTransportHandler(SdlRouterService provider) {
                this.provider = new WeakReference<SdlRouterService>(provider);
                loader = getClass().getClassLoader();
            }

            @Override
            public void handleMessage(Message msg) {
                SdlRouterService service = this.provider.get();
                Bundle receivedBundle = msg.getData();
                switch (msg.what) {
                    case TransportConstants.ROUTER_RECEIVED_PACKET:
                        if (receivedBundle != null) {
                            receivedBundle.setClassLoader(loader);//We do this because loading a custom parceable object isn't possible without it
                            if (receivedBundle.containsKey(TransportConstants.FORMED_PACKET_EXTRA_NAME)) {
                                SdlPacket packet = receivedBundle.getParcelable(TransportConstants.FORMED_PACKET_EXTRA_NAME);
                                if (packet != null && service != null) {
                                    service.onPacketRead(packet);
                                } else {
                                    Log.w(TAG, "Received null packet from alt transport service");
                                }
                            } else {
                                Log.w(TAG, "Flase positive packet reception");
                            }
                        } else {
                            Log.e(TAG, "Bundle was null while sending packet to router service from alt transport");
                        }
                        break;
                    default:
                        super.handleMessage(msg);
                }

            }
        }
        AltTransportHandler testHandler = new AltTransportHandler(null);
        Message msg = Message.obtain(null, TransportConstants.ROUTER_RECEIVED_PACKET);
        //Send a null bundle
        msg.setData(null);
        try {
            testHandler.handleMessage(msg);
        } catch (Exception e) {
            Assert.fail("Exception in testAlTransportHandlerHandleNullBundle, " + e);
        }
    }

    /**
     * Test writeBytesToTransport method for handling null byte array in bundle
     *
     * @see SdlRouterService#writeBytesToTransport(Bundle)
     */
    @Test
    public void testWriteBytesToTransport() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        Method method;
        Field field = null;
        Object sdlRouterService = null;
        try {
            sdlRouterService = Class.forName("com.smartdevicelink.transport.SdlRouterService").newInstance();
            //Send a null bundle
            method = SdlRouterService.class.getDeclaredMethod("writeBytesToTransport", Bundle.class);
            Bundle bundle = null;
            method.invoke(sdlRouterService, bundle);

            //Send a non-null bundle with a null bytes array
            //First, set mSerialService to the correct state so we get to test packet being null
            MultiplexBluetoothTransport transport = new MultiplexBluetoothTransport(null);
            transport.setStateManually(MultiplexBluetoothTransport.STATE_CONNECTED);
            field = SdlRouterService.class.getDeclaredField("bluetoothTransport");
            field.setAccessible(true);
            field.set(sdlRouterService, transport);
            bundle = new Bundle();
            bundle.putByteArray(TransportConstants.BYTES_TO_SEND_EXTRA_NAME, null);
            method.invoke(sdlRouterService, bundle);
        } catch (Exception e) {
            Assert.fail("Exception in testWriteBytesToTransport, " + e);
        }

        //Return mSerialService to previous state
        if (field != null && sdlRouterService != null) {
            try {
                field.set(sdlRouterService, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

	/**
	 * Test sending UAI to an app whose session id is the same as a removed app
	 * but is indeed a different app
	 *
	 * @see SdlRouterService#sendPacketToRegisteredApp(SdlPacket)
	 */
	@Test
	public void testRegisterAppExistingSessionIDDifferentApp() {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		Method method;
		try {
			// create instance of router service
			SdlRouterService sdlRouterService = new SdlRouterService();

			// We need a registered app for this to work
			Message message = Message.obtain();
			SdlRouterService.RegisteredApp app1 = sdlRouterService.new RegisteredApp("12345",message.replyTo);
			SdlRouterService.RegisteredApp app2 = sdlRouterService.new RegisteredApp("12344",message.replyTo);
			HashMap<String,SdlRouterService.RegisteredApp> registeredApps = new HashMap<>();
			registeredApps.put(app1.getAppId(),app1);
			registeredApps.put(app2.getAppId(),app2);

			// set registered apps array
			Field raf = sdlRouterService.getClass().getDeclaredField("registeredApps");
			raf.setAccessible(true);
			raf.set(sdlRouterService, registeredApps);

			// need a session map too
			SparseArray<String> sessionMap = new SparseArray<String>();
			sessionMap.put(1, "12345");
			Field sessionMapField = sdlRouterService.getClass().getDeclaredField("bluetoothSessionMap");
			sessionMapField.setAccessible(true);
			sessionMapField.set(sdlRouterService, sessionMap);

			// set cleaned session map
			SparseIntArray testCleanedMap = new SparseIntArray();
			testCleanedMap.put(1, 12345);
			Field f = sdlRouterService.getClass().getDeclaredField("cleanedSessionMap");
			f.setAccessible(true);
			f.set(sdlRouterService, testCleanedMap);

			// set session hash id map
			SparseIntArray testHashIdMap = new SparseIntArray();
			testHashIdMap.put(1, 12344);
			Field f2 = sdlRouterService.getClass().getDeclaredField("sessionHashIdMap");
			f2.setAccessible(true);
			f2.set(sdlRouterService, testHashIdMap);

			// make sure maps are set and NOT the same
			Assert.assertNotNull(raf.get(sdlRouterService));
			Assert.assertNotNull(sessionMapField.get(sdlRouterService));
			Assert.assertNotNull(f.get(sdlRouterService));
			Assert.assertNotNull(f2.get(sdlRouterService));

			// make da RPC
			UnregisterAppInterface request = new UnregisterAppInterface();
			request.setCorrelationID(SAMPLE_RPC_CORRELATION_ID);

			// build protocol message
			byte[] msgBytes = JsonRPCMarshaller.marshall(request, (byte) version);
			pm = new ProtocolMessage();
			pm.setData(msgBytes);
			pm.setSessionID((byte) sessionId);
			pm.setMessageType(MessageType.RPC);
			pm.setSessionType(SessionType.RPC);
			pm.setFunctionID(FunctionID.getFunctionId(request.getFunctionName()));
			pm.setCorrID(request.getCorrelationID());

			if (request.getBulkData() != null) {
				pm.setBulkData(request.getBulkData());
			}

			// binary frame header
			byte[] data = new byte[12 + pm.getJsonSize()];
			binFrameHeader = SdlPacketFactory.createBinaryFrameHeader(pm.getRPCType(), pm.getFunctionID(), pm.getCorrID(), pm.getJsonSize());
			System.arraycopy(binFrameHeader.assembleHeaderBytes(), 0, data, 0, 12);
			System.arraycopy(pm.getData(), 0, data, 12, pm.getJsonSize());

			// create packet and invoke sendPacketToRegisteredApp
			SdlPacket packet = new SdlPacket(4, false, SdlPacket.FRAME_TYPE_SINGLE, SdlPacket.SERVICE_TYPE_RPC, 0, sessionId, data.length, 123, data);
			packet.setTransportRecord(new TransportRecord(TransportType.BLUETOOTH,null));
			method = sdlRouterService.getClass().getDeclaredMethod("sendPacketToRegisteredApp", SdlPacket.class);
			Boolean success = (Boolean) method.invoke(sdlRouterService, packet);

			// we do not want the UAI packet to be sent. make sure it is dropped
			Assert.assertFalse(success);

		} catch (Exception e) {
			Assert.fail("Exception in sendPacketToRegisteredApp, " + e);
		}
	}
	/**
	 * Test sending UAI to an app whose session id is the same as a removed app
	 * but is indeed the SAME app
	 *
	 * @see SdlRouterService#sendPacketToRegisteredApp(SdlPacket)
	 */
	@Test
	public void testRegisterAppExistingSessionIDSameApp() {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		Method method;
		try {
			// create instance of router service
			SdlRouterService sdlRouterService = new SdlRouterService();

			// We need a registered app for this to work
			Message message = Message.obtain();
			SdlRouterService.RegisteredApp app1 = sdlRouterService.new RegisteredApp("12345",message.replyTo);
			SdlRouterService.RegisteredApp app2 = sdlRouterService.new RegisteredApp("12344",message.replyTo);
			HashMap<String,SdlRouterService.RegisteredApp> registeredApps = new HashMap<>();
			registeredApps.put(app1.getAppId(),app1);
			registeredApps.put(app2.getAppId(),app2);

			// set registered apps array
			Field raf = sdlRouterService.getClass().getDeclaredField("registeredApps");
			raf.setAccessible(true);
			raf.set(sdlRouterService, registeredApps);

			// need a session map too
			SparseArray<String> sessionMap = new SparseArray<String>();
			sessionMap.put(1, "12345");
			Field sessionMapField = sdlRouterService.getClass().getDeclaredField("bluetoothSessionMap");
			sessionMapField.setAccessible(true);
			sessionMapField.set(sdlRouterService, sessionMap);

			// set cleaned session map
			SparseIntArray testCleanedMap = new SparseIntArray();
			testCleanedMap.put(1, 12345);
			Field f = sdlRouterService.getClass().getDeclaredField("cleanedSessionMap");
			f.setAccessible(true);
			f.set(sdlRouterService, testCleanedMap);

			// set session hash id map
			SparseIntArray testHashIdMap = new SparseIntArray();
			testHashIdMap.put(1, 12345);
			Field f2 = sdlRouterService.getClass().getDeclaredField("sessionHashIdMap");
			f2.setAccessible(true);
			f2.set(sdlRouterService, testHashIdMap);

			// make sure maps are set and NOT the same
			Assert.assertNotNull(raf.get(sdlRouterService));
			Assert.assertNotNull(sessionMapField.get(sdlRouterService));
			Assert.assertNotNull(f.get(sdlRouterService));
			Assert.assertNotNull(f2.get(sdlRouterService));

			// make da RPC
			UnregisterAppInterface request = new UnregisterAppInterface();
			request.setCorrelationID(SAMPLE_RPC_CORRELATION_ID);

			// build protocol message
			byte[] msgBytes = JsonRPCMarshaller.marshall(request, (byte) version);
			pm = new ProtocolMessage();
			pm.setData(msgBytes);
			pm.setSessionID((byte) sessionId);
			pm.setMessageType(MessageType.RPC);
			pm.setSessionType(SessionType.RPC);
			pm.setFunctionID(FunctionID.getFunctionId(request.getFunctionName()));
			pm.setCorrID(request.getCorrelationID());

			if (request.getBulkData() != null) {
				pm.setBulkData(request.getBulkData());
			}

			// binary frame header
			byte[] data = new byte[12 + pm.getJsonSize()];
			binFrameHeader = SdlPacketFactory.createBinaryFrameHeader(pm.getRPCType(), pm.getFunctionID(), pm.getCorrID(), pm.getJsonSize());
			System.arraycopy(binFrameHeader.assembleHeaderBytes(), 0, data, 0, 12);
			System.arraycopy(pm.getData(), 0, data, 12, pm.getJsonSize());

			// create packet and invoke sendPacketToRegisteredApp
			SdlPacket packet = new SdlPacket(4, false, SdlPacket.FRAME_TYPE_SINGLE, SdlPacket.SERVICE_TYPE_RPC, 0, sessionId, data.length, 123, data);
			packet.setTransportRecord(new TransportRecord(TransportType.BLUETOOTH,null));
			method = sdlRouterService.getClass().getDeclaredMethod("sendPacketToRegisteredApp", SdlPacket.class);
			Boolean success = (Boolean) method.invoke(sdlRouterService, packet);

			// Since it is the same app, allow the packet to be sent
			Assert.assertTrue(success);

		} catch (Exception e) {
			Assert.fail("Exception in sendPacketToRegisteredApp, " + e);
		}
	}

	/**
	 * Test router service correctly picks up Hash ID from start service ACK (prior to V5)
	 */
	@Test
	public void testStartSessionAckHashId() {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		Method method;
		try {
			SdlRouterService sdlRouterService = new SdlRouterService();

			initFields(sdlRouterService);
			addDummyRegisteredApp(sdlRouterService, "12345", sessionId);

			// create packet and invoke sendPacketToRegisteredApp
			int hashId = 0x123456;
			byte[] payload = new byte[]{0x00, 0x12, 0x34, 0x56};
			SdlPacket packet = new SdlPacket(4, false, SdlPacket.FRAME_TYPE_CONTROL, SdlPacket.SERVICE_TYPE_RPC, SdlPacket.FRAME_INFO_START_SERVICE_ACK, sessionId, payload.length, 2, payload);
			packet.setTransportRecord(new TransportRecord(TransportType.BLUETOOTH,null));

			method = sdlRouterService.getClass().getDeclaredMethod("sendPacketToRegisteredApp", SdlPacket.class);
			method.setAccessible(true);
			Boolean success = (Boolean) method.invoke(sdlRouterService, packet);
			Assert.assertTrue(success);

			// verify hash id map contains the correct ID
			Field field = sdlRouterService.getClass().getDeclaredField("sessionHashIdMap");
			field.setAccessible(true);
			SparseIntArray sessionHashIdMap = (SparseIntArray)field.get(sdlRouterService);

			Assert.assertTrue(sessionHashIdMap.indexOfKey(sessionId) >= 0);
			int value = sessionHashIdMap.get(sessionId, -1);
			Assert.assertEquals(hashId, value);
		} catch (Exception e) {
			Assert.fail("Exception in sendPacketToRegisteredApp, " + e);
		}
	}

	/**
	 * Test router service correctly acquires Hash ID from V5 start service ACK
	 */
	@Test
	public void testStartSessionAckV5HashId() {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		Method method;
		try {
			SdlRouterService sdlRouterService = new SdlRouterService();

			initFields(sdlRouterService);
			addDummyRegisteredApp(sdlRouterService, "12345", sessionId);

			// create packet and invoke sendPacketToRegisteredApp
			int hashId = 0x123456;
			SdlPacket packet = new SdlPacket(5, false, SdlPacket.FRAME_TYPE_CONTROL, SdlPacket.SERVICE_TYPE_RPC, SdlPacket.FRAME_INFO_START_SERVICE_ACK, sessionId, 0, 2, null);
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION, "5.0.0");
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID, hashId);
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.MTU, 1024);
			packet.setTransportRecord(new TransportRecord(TransportType.BLUETOOTH,null));
			packet.constructPacket(); // update 'payload' field in the packet instance

			method = sdlRouterService.getClass().getDeclaredMethod("sendPacketToRegisteredApp", SdlPacket.class);
			method.setAccessible(true);
			Boolean success = (Boolean) method.invoke(sdlRouterService, packet);
			Assert.assertTrue(success);

			// verify hash id map contains the correct ID
			Field field = sdlRouterService.getClass().getDeclaredField("sessionHashIdMap");
			field.setAccessible(true);
			SparseIntArray sessionHashIdMap = (SparseIntArray)field.get(sdlRouterService);

			Assert.assertTrue(sessionHashIdMap.indexOfKey(sessionId) >= 0);
			int value = sessionHashIdMap.get(sessionId, -1);
			Assert.assertEquals(hashId, value);
		} catch (Exception e) {
			Assert.fail("Exception in sendPacketToRegisteredApp, " + e);
		}
	}

	/**
	 * Test router service sends a valid end service request from attemptToCleanupModule()
	 */
	@Test
	public void testEndSessionV5FromAttemptToCleanupModule() {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		Method method;
		try {
			SdlRouterService sdlRouterService = new SdlRouterService();

			initFields(sdlRouterService);
			addDummyRegisteredApp(sdlRouterService, "12345", sessionId);

			MultiplexBluetoothTransport mockTransport = mock(MultiplexBluetoothTransport.class);
			Field btf = sdlRouterService.getClass().getDeclaredField("bluetoothTransport");
			btf.setAccessible(true);
			btf.set(sdlRouterService, mockTransport);

			// create packet and invoke sendPacketToRegisteredApp
			int hashId = 0x123456;
			SdlPacket packet = new SdlPacket(5, false, SdlPacket.FRAME_TYPE_CONTROL, SdlPacket.SERVICE_TYPE_RPC, SdlPacket.FRAME_INFO_START_SERVICE_ACK, sessionId, 0, 2, null);
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION, "5.0.0");
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID, hashId);
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.MTU, 1024);
			packet.setTransportRecord(new TransportRecord(TransportType.BLUETOOTH,null));
			packet.constructPacket(); // update 'payload' field in the packet instance

			method = sdlRouterService.getClass().getDeclaredMethod("sendPacketToRegisteredApp", SdlPacket.class);
			method.setAccessible(true);
			Boolean success = (Boolean) method.invoke(sdlRouterService, packet);
			Assert.assertTrue(success);

			when(mockTransport.getState()).thenReturn(MultiplexBluetoothTransport.STATE_CONNECTED);

			// now call attemptToCleanUpModule()
			method = sdlRouterService.getClass().getDeclaredMethod("attemptToCleanUpModule", int.class, int.class, TransportType.class);
			method.setAccessible(true);
			method.invoke(sdlRouterService, sessionId, 5, TransportType.BLUETOOTH);

			ArgumentCaptor<byte[]> argument = ArgumentCaptor.forClass(byte[].class);
			verify(mockTransport, times(2)).write(argument.capture(), eq(0), anyInt());

			List<byte[]> frames = argument.getAllValues();

			// First, we receive UnregisterAppInterface. Verifying the message is out of scope of this test.
			Assert.assertEquals(2, frames.size());

			// then we should receive a end service frame
			byte[] expectedBsonPayload = new byte[] {
					0x11, 0x00, 0x00, 0x00, /* total bytes */
					0x10, 'h', 'a', 's', 'h', 'I', 'd', 0x00, 0x56, 0x34, 0x12, 0x00, /* int32: "hashId": 0x00123456 */
					0x00, /* end of document */
			};
			byte[] actualFrame = frames.get(1);

			Assert.assertEquals(12 + expectedBsonPayload.length, actualFrame.length);
			Assert.assertEquals(actualFrame[2], 0x04 /* end service */);
			Assert.assertEquals(actualFrame[3], (byte)sessionId);
			byte[] actualPayload = Arrays.copyOfRange(actualFrame, 12, actualFrame.length);
			Assert.assertTrue(Arrays.equals(expectedBsonPayload, actualPayload));
		} catch (Exception e) {
			Assert.fail("Exception in sendPacketToRegisteredApp, " + e);
		}
	}

	/**
	 * Test router service sends a valid end service request when it receives a packet from Core
	 * and the app has been unregistered
	 */
	@Test
	public void testEndSessionV5WhenPacketForUnregisteredAppReceived() {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		Method method;
		try {
			SdlRouterService sdlRouterService = new SdlRouterService();

			initFields(sdlRouterService);
			addDummyRegisteredApp(sdlRouterService, "12345", sessionId);
			// The end service frame will not be sent out if there is no app in the map. Is this expected?
			addDummyRegisteredApp(sdlRouterService, "12346", 2);

			MultiplexBluetoothTransport mockTransport = mock(MultiplexBluetoothTransport.class);
			Field btf = sdlRouterService.getClass().getDeclaredField("bluetoothTransport");
			btf.setAccessible(true);
			btf.set(sdlRouterService, mockTransport);

			// create packet and invoke sendPacketToRegisteredApp
			int hashId = 0x123456;
			SdlPacket packet = new SdlPacket(5, false, SdlPacket.FRAME_TYPE_CONTROL, SdlPacket.SERVICE_TYPE_RPC, SdlPacket.FRAME_INFO_START_SERVICE_ACK, sessionId, 0, 2, null);
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION, "5.0.0");
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID, hashId);
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.MTU, 1024);
			packet.setTransportRecord(new TransportRecord(TransportType.BLUETOOTH,null));
			packet.constructPacket(); // update 'payload' field in the packet instance

			method = sdlRouterService.getClass().getDeclaredMethod("sendPacketToRegisteredApp", SdlPacket.class);
			method.setAccessible(true);
			Boolean ret = (Boolean) method.invoke(sdlRouterService, packet);
			Assert.assertTrue(ret);

			// remove the app from "registeredApps" map
			Field field = sdlRouterService.getClass().getDeclaredField("registeredApps");
			field.setAccessible(true);
			HashMap<String, SdlRouterService.RegisteredApp> registeredApps = (HashMap<String,SdlRouterService.RegisteredApp>)field.get(sdlRouterService);
			registeredApps.remove("12345");

			when(mockTransport.getState()).thenReturn(MultiplexBluetoothTransport.STATE_CONNECTED);

			// call sendPacketToRegisteredApp once again with a dummy packet
			byte[] dummyRegisterAppInterface = new byte[]{0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02, '{', '}'};
			SdlPacket dummyPacket = new SdlPacket(5, false, SdlPacket.FRAME_TYPE_SINGLE, SdlPacket.SERVICE_TYPE_RPC, 0x00, sessionId, dummyRegisterAppInterface.length, 3, dummyRegisterAppInterface);
			dummyPacket.setTransportRecord(new TransportRecord(TransportType.BLUETOOTH,null));

			ret = (Boolean) method.invoke(sdlRouterService, dummyPacket);
			Assert.assertFalse(ret);

			ArgumentCaptor<byte[]> argument = ArgumentCaptor.forClass(byte[].class);
			verify(mockTransport, times(2)).write(argument.capture(), eq(0), anyInt());

			List<byte[]> frames = argument.getAllValues();

			// First, we receive UnregisterAppInterface. Verifying the message is out of scope of this test.
			Assert.assertEquals(2, frames.size());

			// then we should receive a end service frame
			byte[] expectedBsonPayload = new byte[] {
					0x11, 0x00, 0x00, 0x00, /* total bytes */
					0x10, 'h', 'a', 's', 'h', 'I', 'd', 0x00, 0x56, 0x34, 0x12, 0x00, /* int32: "hashId": 0x00123456 */
					0x00, /* end of document */
			};
			byte[] actualFrame = frames.get(1);

			Assert.assertEquals(12 + expectedBsonPayload.length, actualFrame.length);
			Assert.assertEquals(actualFrame[2], 0x04 /* end service */);
			Assert.assertEquals(actualFrame[3], (byte)sessionId);
			byte[] actualPayload = Arrays.copyOfRange(actualFrame, 12, actualFrame.length);
			Assert.assertTrue(Arrays.equals(expectedBsonPayload, actualPayload));
		} catch (Exception e) {
			Assert.fail("Exception in sendPacketToRegisteredApp, " + e);
		}
	}

	/**
	 * Test router service sends a valid end service request when it fails to deliver a message
	 * to an app
	 */
	@Test
	public void testEndSessionV5WhenSendMessageToClientFailed() {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		Method method;
		try {
			SdlRouterService sdlRouterService = new SdlRouterService();
			initFields(sdlRouterService);
			addDummyRegisteredApp(sdlRouterService, "12345", sessionId);

			// create packet and invoke sendPacketToRegisteredApp
			int hashId = 0x123456;
			SdlPacket packet = new SdlPacket(5, false, SdlPacket.FRAME_TYPE_CONTROL, SdlPacket.SERVICE_TYPE_RPC, SdlPacket.FRAME_INFO_START_SERVICE_ACK, sessionId, 0, 2, null);
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION, "5.0.0");
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID, hashId);
			packet.putTag(ControlFrameTags.RPC.StartServiceACK.MTU, 1024);
			packet.setTransportRecord(new TransportRecord(TransportType.BLUETOOTH,null));
			packet.constructPacket(); // update 'payload' field in the packet instance

			method = sdlRouterService.getClass().getDeclaredMethod("sendPacketToRegisteredApp", SdlPacket.class);
			method.setAccessible(true);
			Boolean ret = (Boolean) method.invoke(sdlRouterService, packet);
			Assert.assertTrue(ret);

			SdlRouterService.RegisteredApp mockApp = mock(SdlRouterService.RegisteredApp.class);
			when(mockApp.sendMessage(any(Message.class))).thenReturn(SdlRouterService.RegisteredApp.SEND_MESSAGE_ERROR_MESSENGER_DEAD_OBJECT);
			Vector<Long> dummySessionIds = new Vector<>();
			dummySessionIds.add((long)sessionId);
			when(mockApp.getSessionIds()).thenReturn(dummySessionIds);
			List<TransportType> dummyTransportTypes = new ArrayList<>();
			dummyTransportTypes.add(TransportType.BLUETOOTH);
			when(mockApp.getTransportsForSession(sessionId)).thenReturn(dummyTransportTypes);

			MultiplexBluetoothTransport mockTransport = mock(MultiplexBluetoothTransport.class);
			Field btf = sdlRouterService.getClass().getDeclaredField("bluetoothTransport");
			btf.setAccessible(true);
			btf.set(sdlRouterService, mockTransport);

			when(mockTransport.getState()).thenReturn(MultiplexBluetoothTransport.STATE_CONNECTED);

			// invoke sendPacketMessageToClient
			method = sdlRouterService.getClass().getDeclaredMethod("sendPacketMessageToClient", SdlRouterService.RegisteredApp.class, Message.class, byte.class);
			method.setAccessible(true);
			Message dummyMessage = Message.obtain();
			ret = (Boolean) method.invoke(sdlRouterService, mockApp, dummyMessage, (byte)5);
			Assert.assertFalse(ret);

			ArgumentCaptor<byte[]> argument = ArgumentCaptor.forClass(byte[].class);
			verify(mockTransport, times(2)).write(argument.capture(), eq(0), anyInt());

			List<byte[]> frames = argument.getAllValues();

			// First, we receive UnregisterAppInterface. Verifying the message is out of scope of this test.
			Assert.assertEquals(2, frames.size());

			// then we should receive a end service frame
			byte[] expectedBsonPayload = new byte[] {
					0x11, 0x00, 0x00, 0x00, /* total bytes */
					0x10, 'h', 'a', 's', 'h', 'I', 'd', 0x00, 0x56, 0x34, 0x12, 0x00, /* int32: "hashId": 0x00123456 */
					0x00, /* end of document */
			};
			byte[] actualFrame = frames.get(1);

			Assert.assertEquals(12 + expectedBsonPayload.length, actualFrame.length);
			Assert.assertEquals(actualFrame[2], 0x04 /* end service */);
			Assert.assertEquals(actualFrame[3], (byte)sessionId);
			byte[] actualPayload = Arrays.copyOfRange(actualFrame, 12, actualFrame.length);
			Assert.assertTrue(Arrays.equals(expectedBsonPayload, actualPayload));
		} catch (Exception e) {
			Assert.fail("Exception in sendPacketToRegisteredApp, " + e);
		}
	}

	private void initFields(SdlRouterService routerService) throws IllegalAccessException, NoSuchFieldException {
		// set registered apps array
		HashMap<String,SdlRouterService.RegisteredApp> registeredApps = new HashMap<>();
		Field raf = routerService.getClass().getDeclaredField("registeredApps");
		raf.setAccessible(true);
		raf.set(routerService, registeredApps);

		// need a session map too
		SparseArray<String> sessionMap = new SparseArray<String>();
		Field bsmf = routerService.getClass().getDeclaredField("bluetoothSessionMap");
		bsmf.setAccessible(true);
		bsmf.set(routerService, sessionMap);

		SparseIntArray emptyCleanedSessionMap = new SparseIntArray();
		Field csmf = routerService.getClass().getDeclaredField("cleanedSessionMap");
		csmf.setAccessible(true);
		csmf.set(routerService, emptyCleanedSessionMap);

		SparseIntArray emptyHashIdMap = new SparseIntArray();
		Field shmf = routerService.getClass().getDeclaredField("sessionHashIdMap");
		shmf.setAccessible(true);
		shmf.set(routerService, emptyHashIdMap);
	}

	private void addDummyRegisteredApp(SdlRouterService routerService, String appId, int sessionId)
			throws IllegalAccessException, NoSuchFieldException {
		Message message = Message.obtain();
		SdlRouterService.RegisteredApp app = routerService.new RegisteredApp(appId, message.replyTo);

		Field raf = routerService.getClass().getDeclaredField("registeredApps");
		raf.setAccessible(true);
		HashMap<String, SdlRouterService.RegisteredApp> registeredApps = (HashMap<String,SdlRouterService.RegisteredApp>)raf.get(routerService);
		registeredApps.put(app.getAppId(), app);

		Field bsmf = routerService.getClass().getDeclaredField("bluetoothSessionMap");
		bsmf.setAccessible(true);
		SparseArray<String> sessionMap = (SparseArray<String>)bsmf.get(routerService);
		sessionMap.put(sessionId, appId);
	}
}
