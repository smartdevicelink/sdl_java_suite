package com.smartdevicelink.test.streaming;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.streaming.video.IVideoStreamListener;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.StreamPacketizer;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.transport.MultiplexTransportConfig;

import junit.framework.TestCase;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.streaming.StreamPacketizer}
 */
public class StreamPacketizerTests extends TestCase {

	private static final byte WIPRO_VERSION = 0x0B;
	private static final byte SESSION_ID = 0x0A;

	private final byte[][] SAMPLE_H264_VIDEO_STREAM = {
			// one byte array represents a frame
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x67, 0x42, (byte)0xC0, 0x0A, (byte)0xA6, 0x11, 0x11, (byte)0xE8,
					0x40, 0x00, 0x00, (byte)0xFA, 0x40, 0x00, 0x3A, (byte)0x98,
					0x23, (byte)0xC4, (byte)0x89, (byte)0x84, 0x60,
					0x00, 0x00, 0x00, 0x01,
					0x68, (byte)0xC8, 0x42, 0x0F, 0x13, 0x20,
					0x00, 0x00, 0x01,
					0x65, (byte)0x88, (byte)0x82, 0x07, 0x67, 0x39, 0x31, 0x40,
					0x00, 0x5E, 0x0A, (byte)0xFB, (byte)0xEF, (byte)0xAE, (byte)0xBA, (byte)0xEB,
					(byte)0xAE, (byte)0xBA, (byte)0xEB, (byte)0xC0},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, 0x1C, 0x0E, (byte)0xCE, 0x71, (byte)0xB0},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, 0x2A, 0x03, (byte)0xB3, (byte)0x9C, 0x6C},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, 0x3B, 0x03, (byte)0xB3, (byte)0x9C, 0x6C},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, 0x49, 0x00, (byte)0xEC, (byte)0xE7, 0x1B},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, 0x59, 0x40, (byte)0xEC, (byte)0xE7, 0x1B},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, 0x69, (byte)0x80, (byte)0xEC, (byte)0xE7, 0x1B},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, 0x79, (byte)0xC0, (byte)0xEC, (byte)0xE7, 0x1B},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, (byte)0x88, (byte)0x80, 0x3B, 0x39, (byte)0xC6, (byte)0xC0},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, (byte)0x98, (byte)0x90, 0x3B, 0x39, (byte)0xC6, (byte)0xC0},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, (byte)0xA8, (byte)0xA0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, (byte)0xB8, (byte)0xB0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, (byte)0xC8, (byte)0xC0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, (byte)0xD8, (byte)0xD0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, (byte)0xE8, (byte)0xE0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, (byte)0xF8, (byte)0xF0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, 0x00, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, 0x10, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, 0x20, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, 0x30, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, 0x40, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, 0x50, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, 0x60, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, 0x70, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, (byte)0x80, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, (byte)0x90, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, (byte)0xA0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, (byte)0xB0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, (byte)0xC0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9B, (byte)0xD0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x67, 0x42, (byte)0xC0, 0x0A, (byte)0xA6, 0x11, 0x11, (byte)0xE8,
					0x40, 0x00, 0x00, (byte)0xFA, 0x40, 0x00, 0x3A, (byte)0x98,
					0x23, (byte)0xC4, (byte)0x89, (byte)0x84, 0x60,
					0x00, 0x00, 0x00, 0x01,
					0x68, (byte)0xC8, 0x42, 0x0F, 0x13, 0x20,
					0x00, 0x00, 0x01,
					0x65, (byte)0x88, (byte)0x81, 0x00, (byte)0x8E, 0x73, (byte)0x93, 0x14,
					0x00, 0x06, (byte)0xA4, 0x2F, (byte)0xBE, (byte)0xFA, (byte)0xEB, (byte)0xAE,
					(byte)0xBA, (byte)0xEB, (byte)0xAE, (byte)0xBC},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, 0x1C, 0x0D, (byte)0xCE, 0x71, (byte)0xB0},
			new byte[]{0x00, 0x00, 0x00, 0x01,
					0x41, (byte)0x9A, 0x2A, 0x03, 0x33, (byte)0x9C, 0x6C},
	};

	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.streaming.StreamPacketizer#StreamPacketizer(IStreamListener, InputStream, SessionType, byte, SdlSession)}
	 */
	public void testConstructor () {
		
		// Test Values
		byte            testSessionId   = (byte) 0x0A;
		SessionType     testSessionType = SessionType.RPC;
		InputStream     testInputStream = null;
		byte            testWiproVersion = (byte) 0x0B;
		IStreamListener testListener    = new MockStreamListener();
		MockInterfaceBroker interfaceBroker = new MockInterfaceBroker();
		MultiplexTransportConfig transportConfig = new MultiplexTransportConfig(getInstrumentation().getTargetContext(),"19216801");
		SdlSession testSdlSession = new SdlSession(interfaceBroker, transportConfig);
		try {
			testInputStream = new BufferedInputStream(new ByteArrayInputStream("sdl streaming test".getBytes()));
			StreamPacketizer testStreamPacketizer = new StreamPacketizer(testListener, testInputStream, testSessionType, testSessionId, testSdlSession);
			assertNotNull(TestValues.NOT_NULL, testStreamPacketizer);
						
			// NOTE: Cannot test thread handling methods currently.
			
		} catch (IOException e) {
			fail("IOException was thrown.");
		}
	}

	/**
	 * Test for {@link com.smartdevicelink.streaming.StreamPacketizer#sendFrame(byte[], int, int, long)}
	 */
	public void testSendFrameInterfaceWithArray() {
		StreamReceiver mockReceiver = new StreamReceiver();
		SdlSession session = createTestSession();
		StreamPacketizer packetizer = null;
		try {
			packetizer = new StreamPacketizer(mockReceiver, null, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp mockApp = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		mockApp.inputByteStreamWithArray(SAMPLE_H264_VIDEO_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertTrue(mockReceiver.verify(SAMPLE_H264_VIDEO_STREAM));
	}

	/**
	 * Test for {@link com.smartdevicelink.streaming.StreamPacketizer#sendFrame(byte[], int, int, long)}
	 * with offset parameter
	 */
	public void testSendFrameInterfaceWithArrayOffset() {
		StreamReceiver mockReceiver = new StreamReceiver();
		SdlSession session = createTestSession();
		StreamPacketizer packetizer = null;
		try {
			packetizer = new StreamPacketizer(mockReceiver, null, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp mockApp = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		mockApp.inputByteStreamWithArrayOffset(SAMPLE_H264_VIDEO_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertTrue(mockReceiver.verify(SAMPLE_H264_VIDEO_STREAM));
	}

	/**
	 * Test for {@link com.smartdevicelink.streaming.StreamPacketizer#sendFrame(ByteBuffer, long)}
	 */
	public void testSendFrameInterfaceWithByteBuffer() {
		StreamReceiver mockReceiver = new StreamReceiver();
		SdlSession session = createTestSession();
		StreamPacketizer packetizer = null;
		try {
			packetizer = new StreamPacketizer(mockReceiver, null, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp mockApp = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		mockApp.inputByteStreamWithByteBuffer(SAMPLE_H264_VIDEO_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertTrue(mockReceiver.verify(SAMPLE_H264_VIDEO_STREAM));
	}

	/**
	 * Test for {@link com.smartdevicelink.streaming.StreamPacketizer#sendFrame(ByteBuffer, long)}
	 * with direct ByteBuffer
	 */
	public void testSendFrameInterfaceWithDirectByteBuffer() {
		StreamReceiver mockReceiver = new StreamReceiver();
		SdlSession session = createTestSession();
		StreamPacketizer packetizer = null;
		try {
			packetizer = new StreamPacketizer(mockReceiver, null, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp mockApp = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		mockApp.inputByteStreamWithDirectByteBuffer(SAMPLE_H264_VIDEO_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertTrue(mockReceiver.verify(SAMPLE_H264_VIDEO_STREAM));
	}

	/**
	 * Test for {@link com.smartdevicelink.streaming.StreamPacketizer#sendAudio(byte[], int, int, long)}
	 */
	public void testSendAudioInterfaceWithArray() {
		// assume 100 data of 16kHz / 16bits audio for 10 msecs
		int dataCount = 100;
		byte[][] sampleAudio = new byte[dataCount][];
		for (int i = 0; i < dataCount; i++) {
			sampleAudio[i] = new byte[4 * 160];
			Arrays.fill(sampleAudio[i], (byte)0);
		}

		StreamReceiver mockReceiver = new StreamReceiver();
		SdlSession session = createTestSession();
		StreamPacketizer packetizer = null;
		try {
			packetizer = new StreamPacketizer(mockReceiver, null, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp mockApp = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		mockApp.inputByteStreamWithArray(sampleAudio);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertTrue(mockReceiver.verify(sampleAudio));
	}

	/**
	 * Test for {@link com.smartdevicelink.streaming.StreamPacketizer#sendAudio(byte[], int, int, long)}
	 */
	public void testSendAudioInterfaceWithArrayOffset() {
		// assume 100 data of 16kHz / 16bits audio for 10 msecs
		int dataCount = 100;
		byte[][] sampleAudio = new byte[dataCount][];
		for (int i = 0; i < dataCount; i++) {
			sampleAudio[i] = new byte[4 * 160];
			Arrays.fill(sampleAudio[i], (byte)0);
		}

		StreamReceiver mockReceiver = new StreamReceiver();
		SdlSession session = createTestSession();
		StreamPacketizer packetizer = null;
		try {
			packetizer = new StreamPacketizer(mockReceiver, null, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp mockApp = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		mockApp.inputByteStreamWithArrayOffset(sampleAudio);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertTrue(mockReceiver.verify(sampleAudio));
	}

	/**
	 * Test for {@link com.smartdevicelink.streaming.StreamPacketizer#sendAudio(ByteBuffer, long)}
	 */
	public void testSendAudioInterfaceWithByteBuffer() {
		// assume 100 data of 16kHz / 16bits audio for 10 msecs
		int dataCount = 100;
		byte[][] sampleAudio = new byte[dataCount][];
		for (int i = 0; i < dataCount; i++) {
			sampleAudio[i] = new byte[4 * 160];
			Arrays.fill(sampleAudio[i], (byte)0);
		}

		StreamReceiver mockReceiver = new StreamReceiver();
		SdlSession session = createTestSession();
		StreamPacketizer packetizer = null;
		try {
			packetizer = new StreamPacketizer(mockReceiver, null, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp mockApp = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		mockApp.inputByteStreamWithByteBuffer(sampleAudio);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertTrue(mockReceiver.verify(sampleAudio));
	}

	/**
	 * Test for {@link com.smartdevicelink.streaming.StreamPacketizer#sendAudio(ByteBuffer, long)}
	 * with direct ByteBuffer
	 */
	public void testSendAudioInterfaceWithDirectByteBuffer() {
		// assume 100 data of 16kHz / 16bits audio for 10 msecs
		int dataCount = 100;
		byte[][] sampleAudio = new byte[dataCount][];
		for (int i = 0; i < dataCount; i++) {
			sampleAudio[i] = new byte[4 * 160];
			Arrays.fill(sampleAudio[i], (byte)0);
		}

		StreamReceiver mockReceiver = new StreamReceiver();
		SdlSession session = createTestSession();
		StreamPacketizer packetizer = null;
		try {
			packetizer = new StreamPacketizer(mockReceiver, null, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp mockApp = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		mockApp.inputByteStreamWithDirectByteBuffer(sampleAudio);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertTrue(mockReceiver.verify(sampleAudio));
	}


	private SdlSession createTestSession() {
		return new SdlSession(new MockInterfaceBroker(),  new MultiplexTransportConfig(getInstrumentation().getTargetContext(),"19216801"));
	}

	private class StreamReceiver implements IStreamListener {
		private ByteArrayOutputStream mReceiveBuffer;

		StreamReceiver() {
			mReceiveBuffer = new ByteArrayOutputStream();
		}

		@Override
		public void sendStreamPacket(ProtocolMessage pm) {
			try {
				mReceiveBuffer.write(pm.getData());
			} catch (IOException e) {
				fail();
			}
		}

		boolean verify(byte[][] expectedStream) {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			for (byte[] frame : expectedStream) {
				try {
					buffer.write(frame);
				} catch (IOException e) {
					fail();
				}
			}
			boolean result = Arrays.equals(buffer.toByteArray(), mReceiveBuffer.toByteArray());
			try {
				buffer.close();
			} catch (IOException e) {
				fail();
			}
			return result;
		}
	}

	private class MockVideoApp {
		private IVideoStreamListener mListener;

		MockVideoApp(IVideoStreamListener listener) {
			mListener = listener;
		}

		void inputByteStreamWithArray(byte[][] stream) {
			for (byte[] data : stream) {
				mListener.sendFrame(data, 0, data.length, -1);
			}
		}

		void inputByteStreamWithArrayOffset(byte[][] stream) {
			int dummyOffset = 0;
			for (byte[] data : stream) {
				// to test 'offset' param, create a buffer with a dummy offset
				byte[] buffer = new byte[dummyOffset + data.length];
				System.arraycopy(data, 0, buffer, dummyOffset, data.length);

				mListener.sendFrame(buffer, dummyOffset, data.length, -1);
				dummyOffset++;
			}
		}

		void inputByteStreamWithByteBuffer(byte[][] stream) {
			int dummyOffset = 0;
			for (byte[] data : stream) {
				// add a dummy offset inside byteBuffer for testing
				ByteBuffer byteBuffer = ByteBuffer.allocate(dummyOffset + data.length);
				byteBuffer.position(dummyOffset);

				byteBuffer.put(data);
				byteBuffer.flip();
				byteBuffer.position(dummyOffset);

				mListener.sendFrame(byteBuffer, -1);
				dummyOffset++;
			}
		}

		void inputByteStreamWithDirectByteBuffer(byte[][] stream) {
			int dummyOffset = 0;
			for (byte[] data : stream) {
				// add a dummy offset inside byteBuffer for testing
				ByteBuffer byteBuffer = ByteBuffer.allocateDirect(dummyOffset + data.length);
				byteBuffer.position(dummyOffset);

				byteBuffer.put(data);
				byteBuffer.flip();
				byteBuffer.position(dummyOffset);

				mListener.sendFrame(byteBuffer, -1);
				dummyOffset++;
			}
		}
	}
}