package com.smartdevicelink.SdlConnection;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.ControlFrameTags;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.streaming.StreamPacketizer;
import com.smartdevicelink.streaming.video.RTPH264Packetizer;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.SdlConnection.SdlSession}
 */
@RunWith(AndroidJUnit4.class)
public class SdlSessionTests extends AndroidTestCase {
	private static final byte WIPRO_VER = (byte) 5;
	private static final byte SESSION_ID = (byte) 1;

	private ISdlConnectionListener testListener = new TestSdlConnectionListener();

	@Test
	public void testOnTransportEventUpdate() {
		Context testContext = getContext();
		SdlSession session =  SdlSession.createSession(WIPRO_VER, testListener, new MultiplexTransportConfig(testContext, "testapp"));
		session.onProtocolSessionStarted(SessionType.RPC, SESSION_ID, WIPRO_VER, "0", 1, false, TransportType.MULTIPLEX);
		ArrayList<String> secondaryTransports = new ArrayList<>();
		secondaryTransports.add("TCP_WIFI");
		ArrayList<Integer> audioTransports = new ArrayList<>();
		audioTransports.add(2);
		ArrayList<Integer> videoTransports = new ArrayList<>();
		videoTransports.add(2);
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, audioTransports, videoTransports, TransportType.MULTIPLEX);

		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertTrue("Secondary connection enabled", field.getBoolean(session));

			field = SdlSession.class.getDeclaredField("secondaryTransportTypes");
			assertNotNull("secondaryTransportTypes field not null", field);
			field.setAccessible(true);
			ArrayList<TransportType> list = (ArrayList<TransportType>) field.get(session);
			assertNotNull("secondaryTransportTypes field value not null", list);
			assertEquals("list size matches", 1, list.size());
			assertTrue("list contains TCP transport type", list.contains(TransportType.TCP));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}

		HashMap<String, Object> map = new HashMap<>();
		map.put(ControlFrameTags.RPC.TransportEventUpdate.TCP_IP_ADDRESS, "127.0.0.1");
		map.put(ControlFrameTags.RPC.TransportEventUpdate.TCP_PORT, Integer.valueOf(0));
		session.onTransportEventUpdate(SESSION_ID, map);
		try {
			Field field = SdlSession.class.getDeclaredField("secondarySdlConnection");
			assertNotNull("secondarySdlConnection field not null", field);
			field.setAccessible(true);
			assertNotNull("Secondary connection not null", field.get(session));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}

		map.clear();
		session.onTransportEventUpdate(SESSION_ID, map);
		try {
			Field field = SdlSession.class.getDeclaredField("secondarySdlConnection");
			assertNotNull("secondarySdlConnection field not null", field);
			field.setAccessible(true);
			assertNull("Secondary connection is null", field.get(session));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}
	}

	@Test
	public void testOnTransportEventUpdateUSB() {
		Context testContext = getContext();
		SdlSession session =  SdlSession.createSession(WIPRO_VER, testListener, new MultiplexTransportConfig(testContext, "testapp"));
		session.onProtocolSessionStarted(SessionType.RPC, SESSION_ID, WIPRO_VER, "0", 1, false, TransportType.MULTIPLEX);
		ArrayList<String> secondaryTransports = new ArrayList<>();
		secondaryTransports.add("AOA_USB");
		ArrayList<Integer> audioTransports = new ArrayList<>();
		audioTransports.add(2);
		ArrayList<Integer> videoTransports = new ArrayList<>();
		videoTransports.add(2);
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, audioTransports, videoTransports, TransportType.MULTIPLEX);

		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertTrue("Secondary connection enabled", field.getBoolean(session));

			field = SdlSession.class.getDeclaredField("secondaryTransportTypes");
			assertNotNull("secondaryTransportTypes field not null", field);
			field.setAccessible(true);
			ArrayList<TransportType> list = (ArrayList<TransportType>) field.get(session);
			assertNotNull("secondaryTransportTypes field value not null", list);
			assertEquals("list size matches", 1, list.size());
			assertTrue("list contains TCP transport type", list.contains(TransportType.USB));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}

		// TODO: USB parameters have not been established so we can't test enabling/disabling from this message yet
//		HashMap<String, Object> map = new HashMap<>();
//		map.put(ControlFrameTags.RPC.TransportEventUpdate.TCP_IP_ADDRESS, "127.0.0.1");
//		map.put(ControlFrameTags.RPC.TransportEventUpdate.TCP_PORT, Integer.valueOf(0));
//		session.onTransportEventUpdate(SESSION_ID, map);
//		map.clear();
//		session.onTransportEventUpdate(SESSION_ID, map);
	}

	@Test
	public void testOnEnableSecondaryTransportNonBT() {
		SdlSession session =  SdlSession.createSession(WIPRO_VER, testListener, new TCPTransportConfig(8080,"",false));
		session.onProtocolSessionStarted(SessionType.RPC, SESSION_ID, WIPRO_VER, "0", 1, false, TransportType.MULTIPLEX);
		ArrayList<String> secondaryTransports = new ArrayList<>();
		ArrayList<Integer> audioTransports = new ArrayList<>();
		ArrayList<Integer> videoTransports = new ArrayList<>();
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, audioTransports, videoTransports, TransportType.TCP);

		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertFalse("Secondary connection not enabled", field.getBoolean(session));

			field = SdlSession.class.getDeclaredField("secondaryTransportTypes");
			assertNotNull("secondaryTransportTypes field not null", field);
			field.setAccessible(true);
			ArrayList<TransportType> list = (ArrayList<TransportType>) field.get(session);
			assertNull("secondaryTransportTypes field value null", list);
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}
	}

	@Test
	public void testOnEnableSecondaryTransportMBT() {
		Context testContext = getContext();
		SdlSession session =  SdlSession.createSession(WIPRO_VER, testListener, new MultiplexTransportConfig(testContext, "testapp"));
		session.onProtocolSessionStarted(SessionType.RPC, SESSION_ID, WIPRO_VER, "0", 1, false, TransportType.MULTIPLEX);
		ArrayList<String> secondaryTransports = new ArrayList<>();
		secondaryTransports.add("TCP_WIFI");
		ArrayList<Integer> audioTransports = new ArrayList<>();
		audioTransports.add(2);
		ArrayList<Integer> videoTransports = new ArrayList<>();
		videoTransports.add(2);
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, audioTransports, videoTransports, TransportType.MULTIPLEX);

		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertTrue("Secondary connection enabled", field.getBoolean(session));

			field = SdlSession.class.getDeclaredField("secondaryTransportTypes");
			assertNotNull("secondaryTransportTypes field not null", field);
			field.setAccessible(true);
			ArrayList<TransportType> list = (ArrayList<TransportType>) field.get(session);
			assertNotNull("secondaryTransportTypes field value not null", list);
			assertEquals("list size matches", 1, list.size());
			assertTrue("list contains TCP transport type", list.contains(TransportType.TCP));

			field = SdlSession.class.getDeclaredField("audioTransports");
			assertNotNull("audioTransports field not null", field);
			field.setAccessible(true);
			ArrayList audio = (ArrayList) field.get(session);
			assertNotNull("audioTransports field value not null", audio);
			assertEquals("list size matches", 1, audio.size());
			assertEquals("list contains SECONDARY transport", "SECONDARY", audio.get(0).toString());

			field = SdlSession.class.getDeclaredField("videoTransports");
			assertNotNull("videoTransports field not null", field);
			field.setAccessible(true);
			ArrayList video = (ArrayList) field.get(session);
			assertNotNull("videoTransports field value not null", video);
			assertEquals("list size matches", 1, video.size());
			assertEquals("list contains SECONDARY transport", "SECONDARY", video.get(0).toString());
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}
	}

	@Test
	public void testOnEnableSecondaryTransportDiff() {
		Context testContext = getContext();
		SdlSession session =  SdlSession.createSession(WIPRO_VER, testListener, new MultiplexTransportConfig(testContext, "testapp"));
		session.onProtocolSessionStarted(SessionType.RPC, SESSION_ID, WIPRO_VER, "0", 1, false, TransportType.MULTIPLEX);
		ArrayList<String> secondaryTransports = new ArrayList<>();
		secondaryTransports.add("TCP_WIFI");
		ArrayList<Integer> audioTransports = new ArrayList<>();
		audioTransports.add(2);
		ArrayList<Integer> videoTransports = new ArrayList<>();
		videoTransports.add(2);
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, audioTransports, videoTransports, TransportType.BLUETOOTH);

		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertFalse("Secondary connection not enabled", field.getBoolean(session));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}
	}

	@Test
	public void testOnEnableSecondaryTransportNull() {
		Context testContext = getContext();
		SdlSession session =  SdlSession.createSession(WIPRO_VER, testListener, new MultiplexTransportConfig(testContext, "testapp"));
		session.onProtocolSessionStarted(SessionType.RPC, SESSION_ID, WIPRO_VER, "0", 1, false, TransportType.MULTIPLEX);
		session.onEnableSecondaryTransport(SESSION_ID, null, null, null, TransportType.MULTIPLEX);
		ArrayList<String> secondaryTransports = new ArrayList<>();
		ArrayList<Integer> audioTransports = new ArrayList<>();
		ArrayList<Integer> videoTransports = new ArrayList<>();
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, null, null, TransportType.MULTIPLEX);
		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertFalse("Secondary connection not enabled", field.getBoolean(session));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, audioTransports, null, TransportType.MULTIPLEX);
		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertFalse("Secondary connection not enabled", field.getBoolean(session));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, null, videoTransports, TransportType.MULTIPLEX);
		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertFalse("Secondary connection not enabled", field.getBoolean(session));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}
		session.onEnableSecondaryTransport(SESSION_ID, null, audioTransports, null, TransportType.MULTIPLEX);
		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertFalse("Secondary connection not enabled", field.getBoolean(session));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}
		session.onEnableSecondaryTransport(SESSION_ID, null, audioTransports, videoTransports, TransportType.MULTIPLEX);
		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertFalse("Secondary connection not enabled", field.getBoolean(session));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}
		session.onEnableSecondaryTransport(SESSION_ID, null, null, videoTransports, TransportType.MULTIPLEX);
		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertFalse("Secondary connection not enabled", field.getBoolean(session));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}
	}

	@Test
	public void testOnEnableSecondaryTransportUSB() {
		Context testContext = getContext();
		SdlSession session =  SdlSession.createSession(WIPRO_VER, testListener, new MultiplexTransportConfig(testContext, "testapp"));
		session.onProtocolSessionStarted(SessionType.RPC, SESSION_ID, WIPRO_VER, "0", 1, false, TransportType.MULTIPLEX);
		ArrayList<String> secondaryTransports = new ArrayList<>();
		ArrayList<Integer> audioTransports = new ArrayList<>();
		ArrayList<Integer> videoTransports = new ArrayList<>();
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, audioTransports, videoTransports, TransportType.MULTIPLEX);
		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertFalse("Secondary connection not enabled", field.getBoolean(session));
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}

		secondaryTransports.add("AOA_USB");
		videoTransports.add(2);
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, audioTransports, videoTransports, TransportType.MULTIPLEX);
		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertTrue("Secondary connection enabled", field.getBoolean(session));

			field = SdlSession.class.getDeclaredField("secondaryTransportTypes");
			assertNotNull("secondaryTransportTypes field not null", field);
			field.setAccessible(true);
			ArrayList<TransportType> list = (ArrayList<TransportType>) field.get(session);
			assertNotNull("secondaryTransportTypes field value not null", list);
			assertEquals("list size matches", 1, list.size());
			assertTrue("list contains USB transport type", list.contains(TransportType.USB));

			field = SdlSession.class.getDeclaredField("audioTransports");
			assertNotNull("audioTransports field not null", field);
			field.setAccessible(true);
			ArrayList audio = (ArrayList) field.get(session);
			assertNotNull("audioTransports field value not null", audio);
			assertEquals("audio list size matches", 0, audio.size());

			field = SdlSession.class.getDeclaredField("videoTransports");
			assertNotNull("videoTransports field not null", field);
			field.setAccessible(true);
			ArrayList video = (ArrayList) field.get(session);
			assertNotNull("videoTransports field value not null", video);
			assertEquals("video list size matches", 1, video.size());
			assertEquals("video list contains SECONDARY transport", "SECONDARY", video.get(0).toString());
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}

		secondaryTransports.add("TCP_WIFI");
		videoTransports.clear();
		audioTransports.add(2);
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, audioTransports, videoTransports, TransportType.MULTIPLEX);
		try {
			Field field = SdlSession.class.getDeclaredField("secondaryConnectionEnabled");
			assertNotNull("secondaryConnectionEnabled field not null", field);
			field.setAccessible(true);
			assertTrue("Secondary connection enabled", field.getBoolean(session));

			field = SdlSession.class.getDeclaredField("secondaryTransportTypes");
			assertNotNull("secondaryTransportTypes field not null", field);
			field.setAccessible(true);
			ArrayList<TransportType> list = (ArrayList<TransportType>) field.get(session);
			assertNotNull("secondaryTransportTypes field value not null", list);
			assertEquals("list size matches", 2, list.size());
			assertTrue("list contains TCP transport type", list.contains(TransportType.TCP));
			assertTrue("list contains USB transport type", list.contains(TransportType.USB));
			assertEquals("TCP index matches", 1, list.indexOf(TransportType.TCP));

			field = SdlSession.class.getDeclaredField("audioTransports");
			assertNotNull("audioTransports field not null", field);
			field.setAccessible(true);
			ArrayList audio = (ArrayList) field.get(session);
			assertNotNull("audioTransports field value not null", audio);
			assertEquals("audio list size matches", 1, audio.size());
			assertEquals("audio list contains SECONDARY transport", "SECONDARY", audio.get(0).toString());

			field = SdlSession.class.getDeclaredField("videoTransports");
			assertNotNull("videoTransports field not null", field);
			field.setAccessible(true);
			ArrayList video = (ArrayList) field.get(session);
			assertNotNull("video videoTransports field value not null", video);
			assertEquals("video list size matches", 0, video.size());
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			fail("test failed because " + ex.getMessage());
		}
	}

	@Test
	public void testStartStreamingService() {
		Context testContext = getContext();
		SdlSession session =  SdlSession.createSession(WIPRO_VER, testListener, new MultiplexTransportConfig(testContext, "testapp"));
		session.onProtocolSessionStarted(SessionType.RPC, SESSION_ID, WIPRO_VER, "0", 1, false, TransportType.MULTIPLEX);
		ArrayList<String> secondaryTransports = new ArrayList<>();
		secondaryTransports.add("TCP_WIFI");
		ArrayList<Integer> audioTransports = new ArrayList<>();
		audioTransports.add(2);
		ArrayList<Integer> videoTransports = new ArrayList<>();
		videoTransports.add(2);
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, audioTransports, videoTransports, TransportType.MULTIPLEX);
		IVideoStreamListener l = session.startVideoStream();
		assertNotNull("VideoStreamListener not null", l);
	}

	@Test
	public void testOnRegisterSecondaryTransportACK() {
		Context testContext = getContext();
		SdlSession session =  SdlSession.createSession(WIPRO_VER, testListener, new MultiplexTransportConfig(testContext, "testapp"));
		session.onProtocolSessionStarted(SessionType.RPC, SESSION_ID, WIPRO_VER, "0", 1, false, TransportType.MULTIPLEX);

		ArrayList<String> secondaryTransports = new ArrayList<>();
		secondaryTransports.add("TCP_WIFI");
		ArrayList<Integer> audioTransports = new ArrayList<>();
		audioTransports.add(2);
		ArrayList<Integer> videoTransports = new ArrayList<>();
		videoTransports.add(2);
		session.onEnableSecondaryTransport(SESSION_ID, secondaryTransports, audioTransports, videoTransports, TransportType.MULTIPLEX);

		IVideoStreamListener l = session.startVideoStream();
		assertNotNull("VideoStreamListener not null", l);
		if (l instanceof RTPH264Packetizer) {
			assertNull("SDLConnection is null", ((RTPH264Packetizer) l).sdlConnection);
		} else if (l instanceof StreamPacketizer) {
			assertNull("SDLConnection is null", ((StreamPacketizer) l).sdlConnection);
		} else {
			fail("packetizer type is " + l.getClass().getSimpleName());
		}

		HashMap<String, Object> map = new HashMap<>();
		map.put(ControlFrameTags.RPC.TransportEventUpdate.TCP_IP_ADDRESS, "127.0.0.1");
		map.put(ControlFrameTags.RPC.TransportEventUpdate.TCP_PORT, Integer.valueOf(0));
		session.onTransportEventUpdate(SESSION_ID, map);

		session.onRegisterSecondaryTransportACK(SESSION_ID);
		if (l instanceof RTPH264Packetizer) {
			assertNotNull("SDLConnection is not null", ((RTPH264Packetizer) l).sdlConnection);
		} else if (l instanceof StreamPacketizer) {
			assertNotNull("SDLConnection is not null", ((StreamPacketizer) l).sdlConnection);
		}
	}

	private class TestSdlConnectionListener implements ISdlConnectionListener {
		@Override
		public void onTransportDisconnected(String info) {}

		@Override
		public void onTransportDisconnected(String info, TransportType transportType) {}

		@Override
		public void onTransportError(String info, Exception e) {}

		@Override
		public void onTransportError(String info, TransportType transportType, Exception e) {}

		@Override
		public void onProtocolMessageReceived(ProtocolMessage msg) {}

		@Override
		public void onProtocolSessionStartedNACKed(SessionType sessionType, byte sessionID, byte version, String correlationID, List<String> rejectedParams) {}

		@Override
		public void onProtocolSessionStarted(SessionType sessionType, byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted) {}

		@Override
		public void onProtocolSessionStarted(SessionType sessionType, byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted, TransportType transportType) {}

		@Override
		public void onProtocolSessionEnded(SessionType sessionType, byte sessionID, String correlationID) {}

		@Override
		public void onProtocolSessionEnded(SessionType sessionType, byte sessionID, String correlationID, TransportType transportType) {}

		@Override
		public void onProtocolSessionEndedNACKed(SessionType sessionType, byte sessionID, String correlationID) {}

		@Override
		public void onProtocolError(String info, Exception e) {}

		@Override
		public void onHeartbeatTimedOut(byte sessionID) {}

		@Override
		public void onProtocolServiceDataACK(SessionType sessionType, int dataSize, byte sessionID) {}

		@Override
		public void onEnableSecondaryTransport(byte sessionID, ArrayList<String> secondaryTransports, ArrayList<Integer> audioTransports, ArrayList<Integer> videoTransports, TransportType type) {}

		@Override
		public void onTransportEventUpdate(byte sessionID, Map<String, Object> params) {}

		@Override
		public void onRegisterSecondaryTransportACK(byte sessionID) {}

		@Override
		public void onRegisterSecondaryTransportNACKed(byte sessionID, String reason) {}
	}
}
