/*
 * Copyright (c) 2017, Xevo Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.test.streaming.video;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.session.SdlSession;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.video.RTPH264Packetizer;
import com.smartdevicelink.test.streaming.MockInterfaceBroker;
import com.smartdevicelink.transport.MultiplexTransportConfig;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * This class includes a unit test for {@link RTPH264Packetizer}.
 *
 * @author Sho Amano
 */
@RunWith(AndroidJUnit4.class)
public class RTPH264PacketizerTest extends TestCase {

	private static final int FRAME_LENGTH_LEN = 2;
	private static final int RTP_HEADER_LEN = 12;

	private static final byte WIPRO_VERSION = 0x0B;
	private static final byte SESSION_ID = 0x0A;

	private class ByteStreamNALUnit {
		byte[] startCode;
		byte[] nalUnit;
		int frameNum;

		ByteStreamNALUnit(byte[] startCode, byte[] nalUnit, int frameNum) {
			this.startCode = startCode;
			this.nalUnit = nalUnit;
			this.frameNum = frameNum;
		}

		byte[] createArray() {
			byte[] array = new byte[startCode.length + nalUnit.length];
			System.arraycopy(startCode, 0, array, 0, startCode.length);
			System.arraycopy(nalUnit, 0, array, startCode.length, nalUnit.length);
			return array;
		}

		public int getLength() {
			return startCode.length + nalUnit.length;
		}
	}

	private static final byte[] START_CODE_3 = {0x00, 0x00, 0x01};
	private static final byte[] START_CODE_4 = {0x00, 0x00, 0x00, 0x01};

	/* a sample H.264 stream, including 33 frames of 16px white square */
	private final ByteStreamNALUnit[] SAMPLE_STREAM = new ByteStreamNALUnit[] {
		// SPS
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x67, 0x42, (byte)0xC0, 0x0A, (byte)0xA6, 0x11, 0x11, (byte)0xE8,
		                                               0x40, 0x00, 0x00, (byte)0xFA, 0x40, 0x00, 0x3A, (byte)0x98,
		                                               0x23, (byte)0xC4, (byte)0x89, (byte)0x84, 0x60}, 0),
		// PPS
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x68, (byte)0xC8, 0x42, 0x0F, 0x13, 0x20}, 0),
		// I
		new ByteStreamNALUnit(START_CODE_3, new byte[]{0x65, (byte)0x88, (byte)0x82, 0x07, 0x67, 0x39, 0x31, 0x40,
		                                               0x00, 0x5E, 0x0A, (byte)0xFB, (byte)0xEF, (byte)0xAE, (byte)0xBA, (byte)0xEB,
		                                               (byte)0xAE, (byte)0xBA, (byte)0xEB, (byte)0xC0}, 0),
		// P
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x1C, 0x0E, (byte)0xCE, 0x71, (byte)0xB0}, 1),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x2A, 0x03, (byte)0xB3, (byte)0x9C, 0x6C}, 2),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x3B, 0x03, (byte)0xB3, (byte)0x9C, 0x6C}, 3),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x49, 0x00, (byte)0xEC, (byte)0xE7, 0x1B}, 4),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x59, 0x40, (byte)0xEC, (byte)0xE7, 0x1B}, 5),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x69, (byte)0x80, (byte)0xEC, (byte)0xE7, 0x1B}, 6),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x79, (byte)0xC0, (byte)0xEC, (byte)0xE7, 0x1B}, 7),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0x88, (byte)0x80, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 8),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0x98, (byte)0x90, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 9),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xA8, (byte)0xA0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 10),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xB8, (byte)0xB0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 11),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xC8, (byte)0xC0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 12),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xD8, (byte)0xD0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 13),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xE8, (byte)0xE0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 14),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xF8, (byte)0xF0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 15),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x00, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 16),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x10, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 17),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x20, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 18),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x30, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 19),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x40, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 20),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x50, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 21),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x60, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 22),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x70, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 23),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0x80, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 24),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0x90, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 25),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0xA0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 26),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0xB0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 27),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0xC0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 28),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0xD0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 29),
		// SPS
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x67, 0x42, (byte)0xC0, 0x0A, (byte)0xA6, 0x11, 0x11, (byte)0xE8,
		                                               0x40, 0x00, 0x00, (byte)0xFA, 0x40, 0x00, 0x3A, (byte)0x98,
		                                               0x23, (byte)0xC4, (byte)0x89, (byte)0x84, 0x60}, 30),
		// PPS
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x68, (byte)0xC8, 0x42, 0x0F, 0x13, 0x20}, 30),
		// I
		new ByteStreamNALUnit(START_CODE_3, new byte[]{0x65, (byte)0x88, (byte)0x81, 0x00, (byte)0x8E, 0x73, (byte)0x93, 0x14,
		                                               0x00, 0x06, (byte)0xA4, 0x2F, (byte)0xBE, (byte)0xFA, (byte)0xEB, (byte)0xAE,
		                                               (byte)0xBA, (byte)0xEB, (byte)0xAE, (byte)0xBC}, 30),
		// P
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x1C, 0x0D, (byte)0xCE, 0x71, (byte)0xB0}, 31),
		new ByteStreamNALUnit(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x2A, 0x03, 0x33, (byte)0x9C, 0x6C}, 32),
	};

	/**
	 * Test for creating Single Frame RTP packets from H.264 byte stream
	 */
	@Test
	public void testSingleFrames() {
		StreamVerifier verifier = new StreamVerifier(SAMPLE_STREAM);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp encoder = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStreamWithArray(SAMPLE_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(SAMPLE_STREAM.length, verifier.getPacketCount());
	}

	/**
	 * Test for creating Single Frame RTP packets then splitting into multiple SDL frames
	 */
	@Test
	public void testSingleFramesIntoMultipleMessages() {
		StreamVerifier verifier = new StreamVerifier(SAMPLE_STREAM);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}

		// try to modify "bufferSize" variable (in AbstractPacketizer)
		Class packetizerClass = RTPH264Packetizer.class;
		String fieldName = "bufferSize";
		java.lang.reflect.Field bufferSizeField = null;
		while (packetizerClass != null) {
			try {
				bufferSizeField = packetizerClass.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
				packetizerClass = packetizerClass.getSuperclass();
			}
		}
		if (bufferSizeField != null) {
			try {
				bufferSizeField.setAccessible(true);
				// use small MTU and make some RTP packets split into multiple SDL frames
				bufferSizeField.set(packetizer, FRAME_LENGTH_LEN + RTP_HEADER_LEN + 16);
			} catch (IllegalAccessException e) {
				fail("Cannot access to private field \"" + fieldName + "\".");
			}
		} else {
			fail("Cannot find private field \"" + fieldName + "\".");
		}

		MockVideoApp encoder = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStreamWithArray(SAMPLE_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(SAMPLE_STREAM.length, verifier.getPacketCount());
	}

	/**
	 * Test for creating Fragmentation Units from H.264 byte stream
	 */
	@Test
	public void testFragmentationUnits() {
		ByteStreamNALUnit[] stream = new ByteStreamNALUnit[] {
			SAMPLE_STREAM[0], SAMPLE_STREAM[1], null, null, null, SAMPLE_STREAM[5]
		};
		byte[] fakeNALUnit1 = new byte[65535 - RTP_HEADER_LEN];  // not fragmented
		byte[] fakeNALUnit2 = new byte[65536 - RTP_HEADER_LEN];  // will be fragmented
		byte[] fakeNALUnit3 = new byte[65537 - RTP_HEADER_LEN];  // ditto

		for (int i = 0; i < fakeNALUnit1.length; i++) {
			fakeNALUnit1[i] = (byte)(i % 256);
		}
		for (int i = 0; i < fakeNALUnit2.length; i++) {
			fakeNALUnit2[i] = (byte)(i % 256);
		}
		for (int i = 0; i < fakeNALUnit3.length; i++) {
			fakeNALUnit3[i] = (byte)(i % 256);
		}

		stream[2] = new ByteStreamNALUnit(START_CODE_3, fakeNALUnit1, 0);
		stream[3] = new ByteStreamNALUnit(START_CODE_4, fakeNALUnit2, 1);
		stream[4] = new ByteStreamNALUnit(START_CODE_4, fakeNALUnit3, 2);

		StreamVerifier verifier = new StreamVerifier(stream);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp encoder = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStreamWithArray(stream);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(stream.length + 2, verifier.getPacketCount());
	}

	/**
	 * Test for RTP sequence number gets wrap-around correctly
	 */
	@Test
	public void testSequenceNumWrapAround() {
		ByteStreamNALUnit[] stream = new ByteStreamNALUnit[70000];
		for (int i = 0; i < stream.length; i++) {
			stream[i] = new ByteStreamNALUnit(START_CODE_4, SAMPLE_STREAM[3].nalUnit, i);
		}

		StreamVerifier verifier = new StreamVerifier(stream);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp encoder = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStreamWithArray(stream);
		try {
			Thread.sleep(2000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(stream.length, verifier.getPacketCount());
	}

	/**
	 * Test for {@link RTPH264Packetizer#setPayloadType(byte)}
	 */
	@Test
	public void testSetPayloadType() {
		byte pt = (byte)123;
		StreamVerifier verifier = new StreamVerifier(SAMPLE_STREAM, pt);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		packetizer.setPayloadType(pt);
		MockVideoApp encoder = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStreamWithArray(SAMPLE_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(SAMPLE_STREAM.length, verifier.getPacketCount());
	}

	/**
	 * Test for {@link RTPH264Packetizer#setSSRC(int)}
	 */
	@Test
	public void testSetSSRC() {
		int ssrc = 0xFEDCBA98;
		StreamVerifier verifier = new StreamVerifier(SAMPLE_STREAM);
		verifier.setExpectedSSRC(ssrc);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		packetizer.setSSRC(ssrc);
		MockVideoApp encoder = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStreamWithArray(SAMPLE_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(SAMPLE_STREAM.length, verifier.getPacketCount());
	}

	/**
	 * Test for {@link RTPH264Packetizer#pause()} and
	 * {@link RTPH264Packetizer#resume()}
	 */
	@Test
	public void testPauseResume() {
		int index = 0;
		// split SAMPLE_STREAM into three parts
		ByteStreamNALUnit[] inputStream1 = new ByteStreamNALUnit[8];
		ByteStreamNALUnit[] inputStream2 = new ByteStreamNALUnit[19];
		ByteStreamNALUnit[] inputStream3 = new ByteStreamNALUnit[10];
		for (int i = 0; i < inputStream1.length; i++) {
			inputStream1[i] = SAMPLE_STREAM[index++];
		}
		for (int i = 0; i < inputStream2.length; i++) {
			inputStream2[i] = SAMPLE_STREAM[index++];
		}
		for (int i = 0; i < inputStream3.length; i++) {
			inputStream3[i] = SAMPLE_STREAM[index++];
		}

		index = 0;
		// expected output is "all NAL units in inputStream1" plus "I frame and onwards in inputStream3"
		ByteStreamNALUnit[] expectedStream = new ByteStreamNALUnit[inputStream1.length + 3];
		for (int i = 0; i < inputStream1.length; i++) {
			expectedStream[index++] = inputStream1[i];
		}
		expectedStream[index++] = SAMPLE_STREAM[34];
		expectedStream[index++] = SAMPLE_STREAM[35];
		expectedStream[index] = SAMPLE_STREAM[36];

		StreamVerifier verifier = new StreamVerifier(expectedStream);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp encoder = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStreamWithArray(inputStream1);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.pause();

		// this input stream should be disposed
		encoder.inputByteStreamWithArray(inputStream2);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.resume();

		// packetizer should resume from a I frame
		encoder.inputByteStreamWithArray(inputStream3);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(expectedStream.length, verifier.getPacketCount());
	}

	/**
	 * Test for {@link RTPH264Packetizer#sendFrame(byte[], int, int, long)}
	 */
	@Test
	public void testSendFrameInterfaceWithArray() {
		StreamVerifier verifier = new StreamVerifier(SAMPLE_STREAM);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp mockApp = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		mockApp.inputByteStreamWithArrayOffset(SAMPLE_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(SAMPLE_STREAM.length, verifier.getPacketCount());
	}

	/**
	 * Test for {@link RTPH264Packetizer#sendFrame(ByteBuffer, long)}
	 */
	@Test
	public void testSendFrameInterfaceWithByteBuffer() {
		StreamVerifier verifier = new StreamVerifier(SAMPLE_STREAM);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp mockApp = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		mockApp.inputByteStreamWithByteBuffer(SAMPLE_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(SAMPLE_STREAM.length, verifier.getPacketCount());
	}

	/**
	 * Test for {@link RTPH264Packetizer#sendFrame(ByteBuffer, long)}
	 * with direct ByteBuffer
	 */
	@Test
	public void testSendFrameInterfaceWithDirectByteBuffer() {
		StreamVerifier verifier = new StreamVerifier(SAMPLE_STREAM);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockVideoApp mockApp = new MockVideoApp(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		mockApp.inputByteStreamWithDirectByteBuffer(SAMPLE_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(SAMPLE_STREAM.length, verifier.getPacketCount());
	}

	private SdlSession createTestSession() {
		return new SdlSession(new MockInterfaceBroker(), new MultiplexTransportConfig(getInstrumentation().getTargetContext(), "41146"));
	}

	private class StreamVerifier implements IStreamListener {
		private static final int STATE_LENGTH = 0;
		private static final int STATE_PACKET = 1;

		private ByteStreamNALUnit[] mStream;
		private byte[] mExpectedNALUnit;
		private ByteBuffer mReceiveBuffer;
		private int mPacketLen;
		private int mState;
		private int mNALCount;
		private int mTotalPacketCount;
		private boolean mFragmented;
		private int mOffsetInNALUnit;
		private byte mPayloadType;
		private boolean mVerifySSRC;
		private int mExpectedSSRC;
		private boolean mFirstPacketReceived;
		private short mFirstSequenceNum;
		private int mFirstTimestamp;

		StreamVerifier(ByteStreamNALUnit[] stream) {
			this(stream, (byte)96);
		}

		StreamVerifier(ByteStreamNALUnit[] stream, byte payloadType) {
			mStream = stream;
			mReceiveBuffer = ByteBuffer.allocate(256 * 1024);
			mReceiveBuffer.order(ByteOrder.BIG_ENDIAN);
			mPacketLen = 0;
			mState = STATE_LENGTH;

			mNALCount = 0;
			mTotalPacketCount = 0;
			mFragmented = false;
			mOffsetInNALUnit = 1; // Used when verifying FUs. The first byte is skipped.

			mPayloadType = payloadType;
			mVerifySSRC = false;
			mExpectedSSRC = 0;
			mFirstPacketReceived = false;
			mFirstSequenceNum = 0;
			mFirstTimestamp = 0;
		}

		void setExpectedSSRC(int ssrc) {
			mVerifySSRC = true;
			mExpectedSSRC = ssrc;
		}

		int getPacketCount() {
			return mTotalPacketCount;
		}

		@Override
		public void sendStreamPacket(ProtocolMessage pm) {
			mExpectedNALUnit = mStream[mNALCount].nalUnit;
			// should be same as MockVideoApp's configuration (29.97 FPS)
			int expectedPTSDelta = mStream[mNALCount].frameNum * 1001 * 3;
			boolean isLast = shouldBeLast();

			verifyProtocolMessage(pm, SESSION_ID);

			mReceiveBuffer.put(pm.getData());
			mReceiveBuffer.flip();

			if (mState == STATE_LENGTH) {
				if (mReceiveBuffer.remaining() >= 2) {
					mPacketLen = mReceiveBuffer.getShort() & 0xFFFF;
					mState = STATE_PACKET;
				}
			}

			if (mState == STATE_PACKET) {
				if (mReceiveBuffer.remaining() >= mPacketLen) {
					byte[] packet = new byte[mPacketLen];
					mReceiveBuffer.get(packet);

					verifyRTPPacket(packet, mPayloadType, expectedPTSDelta,
					                mVerifySSRC, mExpectedSSRC, isLast);
					mFirstPacketReceived = true;

					mState = STATE_LENGTH;
					mPacketLen = 0;
					mTotalPacketCount++;
				}
			}

			mReceiveBuffer.compact();
		}

		private void verifyProtocolMessage(ProtocolMessage pm, byte sessionId) {
			assertEquals(true, pm != null);
			assertEquals(sessionId, pm.getSessionID());
			assertEquals(SessionType.NAV, pm.getSessionType());
			assertEquals(0, pm.getFunctionID());
			assertEquals(0, pm.getCorrID());
			assertEquals(false, pm.getPayloadProtected());
		}

		private void verifyRTPPacket(byte[] packet, byte payloadType, int expectedPTSDelta,
		                             boolean verifySSRC, int expectedSSRC, boolean isLast) {
			assertTrue(packet.length > RTP_HEADER_LEN);
			verifyRTPHeader(packet, false, isLast, payloadType, (short)(mTotalPacketCount % 65536),
			                expectedPTSDelta, verifySSRC, expectedSSRC);

			byte type = (byte)(packet[RTP_HEADER_LEN] & 0x1F);
			if (type == 28) { // FU-A frame
				boolean fuEnd = verifyFUTypeA(packet);
				if (fuEnd) {
					mNALCount++;
				}
			} else if (type == 29) { // FU-B frame
				fail("Fragmentation Unit B is not supported by this test");
			} else if (type == 24 || type == 25 || type == 26 || type == 27) {
				fail("STAP and MTAP are not supported by this test");
			} else {
				// Single Frame
				verifySingleFrame(packet);
				mNALCount++;
			}
		}

		private void verifyRTPHeader(byte[] packet,
		                             boolean hasPadding, boolean isLast, byte payloadType,
		                             short seqNumDelta, int ptsDelta, boolean checkSSRC, int ssrc) {
			int byte0 = packet[0] & 0xFF;
			assertEquals((byte)2, (byte)((byte0 >> 6) & 3));                    // version
			assertEquals((byte)(hasPadding ? 1 : 0), (byte)((byte0 >> 5) & 1)); // padding
			assertEquals((byte)0, (byte)((byte0 >> 4) & 1));                    // extension
			assertEquals((byte)0, (byte)(byte0 & 0xF));                         // CSRC count

			int byte1 = packet[1] & 0xFF;
			assertEquals((byte)(isLast ? 1 : 0), (byte)((byte1 >> 7) & 1)); // marker
			assertEquals(payloadType, (byte)(byte1 & 0x7F));                // Payload Type

			short actualSeq = (short)(((packet[2] & 0xFF) << 8) | (packet[3] & 0xFF));
			if (!mFirstPacketReceived) {
				mFirstSequenceNum = actualSeq;
			} else {
				assertEquals((short)(mFirstSequenceNum + seqNumDelta), actualSeq);
			}

			int actualPTS = ((packet[4] & 0xFF) << 24) | ((packet[5] & 0xFF) << 16) |
			                 ((packet[6] & 0xFF) << 8) | (packet[7] & 0xFF);
			if (!mFirstPacketReceived) {
				mFirstTimestamp = actualPTS;
			} else {
				// accept calculation error
				assertTrue(mFirstTimestamp + ptsDelta - 1 <= actualPTS &&
				           actualPTS <= mFirstTimestamp + ptsDelta + 1);
			}

			if (checkSSRC) {
				int actualSSRC = ((packet[8] & 0xFF) << 24) | ((packet[9] & 0xFF) << 16) |
				                  ((packet[10] & 0xFF) << 8) | (packet[11] & 0xFF);
				assertEquals(ssrc, actualSSRC);
			}
		}

		private void verifySingleFrame(byte[] packet) {
			assertEquals(true, arrayCompare(packet, RTP_HEADER_LEN, packet.length - RTP_HEADER_LEN,
			                                mExpectedNALUnit, 0, mExpectedNALUnit.length));
		}

		private boolean verifyFUTypeA(byte[] packet) {
			int firstByte = mExpectedNALUnit[0] & 0xFF;

			int byte0 = packet[RTP_HEADER_LEN] & 0xFF;
			assertEquals((byte)((firstByte >> 7) & 1), (byte)((byte0 >> 7) & 1));   // F bit
			assertEquals((byte)((firstByte >> 5) & 3), (byte)((byte0 >> 5) & 3));   // NRI
			assertEquals((byte)28, (byte)(byte0 & 0x1F));                           // Type

			int byte1 = packet[RTP_HEADER_LEN+1] & 0xFF;
			boolean isFirstFU = ((byte1 >> 7) & 1) == 1;                    // Start bit
			boolean isLastFU = ((byte1 >> 6) & 1) == 1;                     // End bit
			assertEquals((byte)0, (byte)((byte1 >> 5) & 1));                // Reserved bit
			assertEquals((byte)(firstByte & 0x1F), (byte)(byte1 & 0x1F));   // Type

			int len = packet.length - (RTP_HEADER_LEN + 2);
			assertEquals(true, arrayCompare(packet, RTP_HEADER_LEN + 2, len, mExpectedNALUnit, mOffsetInNALUnit, len));
			mOffsetInNALUnit += len;

			if (!mFragmented) {
				// this should be the first fragmentation unit
				assertEquals(true, isFirstFU);
				assertEquals(false, isLastFU);
				mFragmented = true;
			} else {
				assertEquals(false, isFirstFU);
				if (mExpectedNALUnit.length == mOffsetInNALUnit) {
					// this is the last fragmentation unit
					assertEquals(true, isLastFU);

					mFragmented = false;
					mOffsetInNALUnit = 1;
					return true;
				} else {
					assertEquals(false, isLastFU);
				}
			}
			return false;
		}

		private boolean shouldBeLast() {
			if (mNALCount + 1 >= mStream.length) {
				return true;
			}
			ByteStreamNALUnit current = mStream[mNALCount];
			ByteStreamNALUnit next = mStream[mNALCount + 1];
			if (next.frameNum != current.frameNum) {
				return true;
			} else {
				return false;
			}
		}

		private boolean arrayCompare(byte[] a1, int start1, int len1, byte[] a2, int start2, int len2) {
			assertTrue(start1 + len1 <= a1.length);
			assertTrue(start2 + len2 <= a2.length);

			if (len1 != len2) {
				return false;
			}

			for (int i = 0; i < len1; i++) {
				if (a1[start1 + i] != a2[start2 + i]) {
					return false;
				}
			}
			return true;
		}
	}

	private interface IVideoFrameSender {
		void onVideoFrame(byte[] data, long timestampUs);
	}

	private class MockVideoApp {
		private IVideoStreamListener mListener;
		private int mFPSNum;
		private int mFPSDen;

		MockVideoApp(IVideoStreamListener listener) {
			mListener = listener;
			// 29.97 fps
			mFPSNum = 30000;
			mFPSDen = 1001;
		}

		void inputByteStreamWithArray(ByteStreamNALUnit[] stream) {
			generateFramesFromStream(stream, new IVideoFrameSender() {
				@Override
				public void onVideoFrame(byte[] data, long timestampUs) {
					byte[] buffer = new byte[data.length];
					System.arraycopy(data, 0, buffer, 0, data.length);
					mListener.sendFrame(buffer, 0, data.length, timestampUs);
				}
			});
		}

		void inputByteStreamWithArrayOffset(ByteStreamNALUnit[] stream) {
			generateFramesFromStream(stream, new IVideoFrameSender() {
				private int mDummyOffset = 0;

				@Override
				public void onVideoFrame(byte[] data, long timestampUs) {
					// to test 'offset' param, create a buffer with a dummy offset
					byte[] buffer = new byte[mDummyOffset + data.length];
					System.arraycopy(data, 0, buffer, mDummyOffset, data.length);

					mListener.sendFrame(buffer, mDummyOffset, data.length, timestampUs);
					mDummyOffset++;
				}
			});
		}

		void inputByteStreamWithByteBuffer(ByteStreamNALUnit[] stream) {
			generateFramesFromStream(stream, new IVideoFrameSender() {
				private int mDummyOffset = 0;

				@Override
				public void onVideoFrame(byte[] data, long timestampUs) {
					// add a dummy offset inside byteBuffer for testing
					ByteBuffer byteBuffer = ByteBuffer.allocate(mDummyOffset + data.length);
					byteBuffer.position(mDummyOffset);

					byteBuffer.put(data);
					byteBuffer.flip();
					byteBuffer.position(mDummyOffset);

					mListener.sendFrame(byteBuffer, timestampUs);
					mDummyOffset++;
				}
			});
		}

		void inputByteStreamWithDirectByteBuffer(ByteStreamNALUnit[] stream) {
			generateFramesFromStream(stream, new IVideoFrameSender() {
				private int mDummyOffset = 0;

				@Override
				public void onVideoFrame(byte[] data, long timestampUs) {
					// add a dummy offset inside byteBuffer for testing
					ByteBuffer byteBuffer = ByteBuffer.allocateDirect(mDummyOffset + data.length);
					byteBuffer.position(mDummyOffset);

					byteBuffer.put(data);
					byteBuffer.flip();
					byteBuffer.position(mDummyOffset);

					mListener.sendFrame(byteBuffer, timestampUs);
					mDummyOffset++;
				}
			});
		}

		private void generateFramesFromStream(ByteStreamNALUnit[] stream, IVideoFrameSender callback) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			for (int i = 0; i < stream.length; i++) {
				ByteStreamNALUnit bs = stream[i];
				byte[] array = bs.createArray();
				os.write(array, 0, array.length);

				if (i < stream.length - 1) {
					ByteStreamNALUnit next = stream[i + 1];
					if (bs.frameNum == next.frameNum) {
						// enqueue it and send at once
						continue;
					}
				}

				long timestampUs = bs.frameNum * 1000L * 1000L * mFPSDen / mFPSNum;
				byte[] data = os.toByteArray();
				callback.onVideoFrame(data, timestampUs);
				os.reset();
			}

			try {
				os.close();
			} catch (IOException e) {
			}
		}
	}
}
