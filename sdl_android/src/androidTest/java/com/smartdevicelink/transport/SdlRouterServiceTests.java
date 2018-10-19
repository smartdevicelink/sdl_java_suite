package com.smartdevicelink.transport;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.BinaryFrameHeader;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlPacketFactory;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.rpc.UnregisterAppInterface;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;

import junit.framework.Assert;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class SdlRouterServiceTests extends AndroidTestCase2 {

    public static final String TAG = "SdlRouterServiceTests";
    private final int SAMPLE_RPC_CORRELATION_ID = 630;
    int version = 1;
    int sessionId = 1;
    ProtocolMessage pm = null;
    BinaryFrameHeader binFrameHeader = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        //Nothing here for now
    }

    /**
     * Test null bundle handling in AltTransportHandler when handling messages. Only test the case of
     * msg.what == TransportConstants.ROUTER_RECEIVED_PACKET
     */
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
}
