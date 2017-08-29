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

package com.smartdevicelink.test.streaming;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.encoder.IEncoderListener;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.RTPH264Packetizer;
import com.smartdevicelink.transport.BTTransportConfig;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * This class includes a unit test for {@link com.smartdevicelink.streaming.RTPH264Packetizer}.
 *
 * @author Sho Amano
 */
public class RTPH264PacketizerTest extends TestCase {

	private static final int FRAME_LENGTH_LEN = 2;
	private static final int RTP_HEADER_LEN = 12;

	private static final byte WIPRO_VERSION = 0x0B;
	private static final byte SESSION_ID = 0x0A;

	private class ByteStreamNALU {
		byte[] startCode;
		byte[] nalu;
		int frameNum;

		ByteStreamNALU(byte[] startCode, byte[] nalu, int frameNum) {
			this.startCode = startCode;
			this.nalu = nalu;
			this.frameNum = frameNum;
		}

		byte[] createArray() {
			byte[] array = new byte[startCode.length + nalu.length];
			System.arraycopy(startCode, 0, array, 0, startCode.length);
			System.arraycopy(nalu, 0, array, startCode.length, nalu.length);
			return array;
		}

		public int getLength() {
			return startCode.length + nalu.length;
		}
	}

	private static final byte[] START_CODE_3 = {0x00, 0x00, 0x01};
	private static final byte[] START_CODE_4 = {0x00, 0x00, 0x00, 0x01};

	/* a sample H.264 stream, including 33 frames of 16px white square */
	private final ByteStreamNALU[] SAMPLE_STREAM = new ByteStreamNALU[] {
		// SPS
		new ByteStreamNALU(START_CODE_4, new byte[]{0x67, 0x42, (byte)0xC0, 0x0A, (byte)0xA6, 0x11, 0x11, (byte)0xE8,
		                                            0x40, 0x00, 0x00, (byte)0xFA, 0x40, 0x00, 0x3A, (byte)0x98,
		                                            0x23, (byte)0xC4, (byte)0x89, (byte)0x84, 0x60}, 0),
		// PPS
		new ByteStreamNALU(START_CODE_4, new byte[]{0x68, (byte)0xC8, 0x42, 0x0F, 0x13, 0x20}, 0),
		// I
		new ByteStreamNALU(START_CODE_3, new byte[]{0x65, (byte)0x88, (byte)0x82, 0x07, 0x67, 0x39, 0x31, 0x40,
		                                            0x00, 0x5E, 0x0A, (byte)0xFB, (byte)0xEF, (byte)0xAE, (byte)0xBA, (byte)0xEB,
		                                            (byte)0xAE, (byte)0xBA, (byte)0xEB, (byte)0xC0}, 0),
		// P
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x1C, 0x0E, (byte)0xCE, 0x71, (byte)0xB0}, 1),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x2A, 0x03, (byte)0xB3, (byte)0x9C, 0x6C}, 2),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x3B, 0x03, (byte)0xB3, (byte)0x9C, 0x6C}, 3),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x49, 0x00, (byte)0xEC, (byte)0xE7, 0x1B}, 4),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x59, 0x40, (byte)0xEC, (byte)0xE7, 0x1B}, 5),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x69, (byte)0x80, (byte)0xEC, (byte)0xE7, 0x1B}, 6),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x79, (byte)0xC0, (byte)0xEC, (byte)0xE7, 0x1B}, 7),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0x88, (byte)0x80, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 8),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0x98, (byte)0x90, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 9),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xA8, (byte)0xA0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 10),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xB8, (byte)0xB0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 11),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xC8, (byte)0xC0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 12),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xD8, (byte)0xD0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 13),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xE8, (byte)0xE0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 14),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, (byte)0xF8, (byte)0xF0, 0x3B, 0x39, (byte)0xC6, (byte)0xC0}, 15),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x00, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 16),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x10, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 17),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x20, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 18),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x30, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 19),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x40, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 20),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x50, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 21),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x60, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 22),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, 0x70, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 23),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0x80, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 24),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0x90, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 25),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0xA0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 26),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0xB0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 27),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0xC0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 28),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9B, (byte)0xD0, 0x1D, (byte)0x9C, (byte)0xE3, 0x60}, 29),
		// SPS
		new ByteStreamNALU(START_CODE_4, new byte[]{0x67, 0x42, (byte)0xC0, 0x0A, (byte)0xA6, 0x11, 0x11, (byte)0xE8,
		                                            0x40, 0x00, 0x00, (byte)0xFA, 0x40, 0x00, 0x3A, (byte)0x98,
		                                            0x23, (byte)0xC4, (byte)0x89, (byte)0x84, 0x60}, 30),
		// PPS
		new ByteStreamNALU(START_CODE_4, new byte[]{0x68, (byte)0xC8, 0x42, 0x0F, 0x13, 0x20}, 30),
		// I
		new ByteStreamNALU(START_CODE_3, new byte[]{0x65, (byte)0x88, (byte)0x81, 0x00, (byte)0x8E, 0x73, (byte)0x93, 0x14,
		                                            0x00, 0x06, (byte)0xA4, 0x2F, (byte)0xBE, (byte)0xFA, (byte)0xEB, (byte)0xAE,
		                                            (byte)0xBA, (byte)0xEB, (byte)0xAE, (byte)0xBC}, 30),
		// P
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x1C, 0x0D, (byte)0xCE, 0x71, (byte)0xB0}, 31),
		new ByteStreamNALU(START_CODE_4, new byte[]{0x41, (byte)0x9A, 0x2A, 0x03, 0x33, (byte)0x9C, 0x6C}, 32),
	};

	/**
	 * Test for creating Single Frame RTP packets from H.264 byte stream
	 */
	public void testSingleFrames() {
		StreamVerifier verifier = new StreamVerifier(SAMPLE_STREAM);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockEncoder encoder = new MockEncoder(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStream(SAMPLE_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(SAMPLE_STREAM.length, verifier.getPacketCount());
	}

	/**
	 * Test for creating Single Frame RTP packets then splitting into multiple SDL frames
	 */
	public void testSingleFramesIntoMultipleMessages() {
		StreamVerifier verifier = new StreamVerifier(SAMPLE_STREAM);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		// use small MTU and make some RTP packets split into multiple SDL frames
		packetizer.setMTU(FRAME_LENGTH_LEN + RTP_HEADER_LEN + 16);
		MockEncoder encoder = new MockEncoder(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStream(SAMPLE_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(SAMPLE_STREAM.length, verifier.getPacketCount());
	}

	/**
	 * Test for creating Fragmentation Units from H.264 byte stream
	 */
	public void testFragmentationUnits() {
		ByteStreamNALU[] stream = new ByteStreamNALU[] {
			SAMPLE_STREAM[0], SAMPLE_STREAM[1], null, null, null, SAMPLE_STREAM[5]
		};
		byte[] fakeNALU1 = new byte[65535 - RTP_HEADER_LEN];  // not fragmented
		byte[] fakeNALU2 = new byte[65536 - RTP_HEADER_LEN];  // will be fragmented
		byte[] fakeNALU3 = new byte[65537 - RTP_HEADER_LEN];  // ditto

		for (int i = 0; i < fakeNALU1.length; i++) {
			fakeNALU1[i] = (byte)(i % 256);
		}
		for (int i = 0; i < fakeNALU2.length; i++) {
			fakeNALU2[i] = (byte)(i % 256);
		}
		for (int i = 0; i < fakeNALU3.length; i++) {
			fakeNALU3[i] = (byte)(i % 256);
		}

		stream[2] = new ByteStreamNALU(START_CODE_3, fakeNALU1, 0);
		stream[3] = new ByteStreamNALU(START_CODE_4, fakeNALU2, 1);
		stream[4] = new ByteStreamNALU(START_CODE_4, fakeNALU3, 2);

		StreamVerifier verifier = new StreamVerifier(stream);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockEncoder encoder = new MockEncoder(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStream(stream);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(stream.length + 2, verifier.getPacketCount());
	}

	/**
	 * Test for RTP sequence number gets wrap-around correctly
	 */
	public void testSequenceNumWrapAround() {
		ByteStreamNALU[] stream = new ByteStreamNALU[70000];
		for (int i = 0; i < stream.length; i++) {
			stream[i] = new ByteStreamNALU(START_CODE_4, SAMPLE_STREAM[3].nalu, i);
		}

		StreamVerifier verifier = new StreamVerifier(stream);
		SdlSession session = createTestSession();
		RTPH264Packetizer packetizer = null;
		try {
			packetizer = new RTPH264Packetizer(verifier, SessionType.NAV, SESSION_ID, session);
		} catch (IOException e) {
			fail();
		}
		MockEncoder encoder = new MockEncoder(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStream(stream);
		try {
			Thread.sleep(2000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(stream.length, verifier.getPacketCount());
	}

	/**
	 * Test for {@link com.smartdevicelink.streaming.RTPH264Packetizer#setPayloadType(byte)}
	 */
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
		MockEncoder encoder = new MockEncoder(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStream(SAMPLE_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(SAMPLE_STREAM.length, verifier.getPacketCount());
	}

	/**
	 * Test for {@link com.smartdevicelink.streaming.RTPH264Packetizer#setSSRC(int)}
	 */
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
		MockEncoder encoder = new MockEncoder(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStream(SAMPLE_STREAM);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(SAMPLE_STREAM.length, verifier.getPacketCount());
	}

	/**
	 * Test for {@link com.smartdevicelink.streaming.RTPH264Packetizer#pause()} and
	 * {@link com.smartdevicelink.streaming.RTPH264Packetizer#resume()}
	 */
	public void testPauseResume() {
		int index = 0;
		// split SAMPLE_STREAM into three parts
		ByteStreamNALU[] inputStream1 = new ByteStreamNALU[8];
		ByteStreamNALU[] inputStream2 = new ByteStreamNALU[19];
		ByteStreamNALU[] inputStream3 = new ByteStreamNALU[10];
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
		ByteStreamNALU[] expectedStream = new ByteStreamNALU[inputStream1.length + 3];
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
		MockEncoder encoder = new MockEncoder(packetizer);

		try {
			packetizer.start();
		} catch (IOException e) {
			fail();
		}

		encoder.inputByteStream(inputStream1);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.pause();

		// this input stream should be disposed
		encoder.inputByteStream(inputStream2);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.resume();

		// packetizer should resume from a I frame
		encoder.inputByteStream(inputStream3);
		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e) {}

		packetizer.stop();
		assertEquals(expectedStream.length, verifier.getPacketCount());
	}

	private SdlSession createTestSession() {
		return SdlSession.createSession(WIPRO_VERSION, new MockInterfaceBroker(), new BTTransportConfig(true));
	}

	private class StreamVerifier implements IStreamListener {
		private static final int STATE_LENGTH = 0;
		private static final int STATE_PACKET = 1;

		private ByteStreamNALU[] mStream;
		private byte[] mExpectedNALU;
		private ByteBuffer mReceiveBuffer;
		private int mPacketLen;
		private int mState;
		private int mNALCount;
		private int mTotalPacketCount;
		private boolean mFragmented;
		private int mOffsetInNALU;
		private byte mPayloadType;
		private boolean mVerifySSRC;
		private int mExpectedSSRC;
		private boolean mFirstPacketReceived;
		private short mFirstSequenceNum;
		private int mFirstTimestamp;

		StreamVerifier(ByteStreamNALU[] stream) {
			this(stream, (byte)96);
		}

		StreamVerifier(ByteStreamNALU[] stream, byte payloadType) {
			mStream = stream;
			mReceiveBuffer = ByteBuffer.allocate(256 * 1024);
			mReceiveBuffer.order(ByteOrder.BIG_ENDIAN);
			mPacketLen = 0;
			mState = STATE_LENGTH;

			mNALCount = 0;
			mTotalPacketCount = 0;
			mFragmented = false;
			mOffsetInNALU = 1; // Used when verifying FUs. The first byte is skipped.

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
			mExpectedNALU = mStream[mNALCount].nalu;
			// should be same as MockEncoder's configuration (29.97 FPS)
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
			                                mExpectedNALU, 0, mExpectedNALU.length));
		}

		private boolean verifyFUTypeA(byte[] packet) {
			int firstByte = mExpectedNALU[0] & 0xFF;

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
			assertEquals(true, arrayCompare(packet, RTP_HEADER_LEN + 2, len, mExpectedNALU, mOffsetInNALU, len));
			mOffsetInNALU += len;

			if (!mFragmented) {
				// this should be the first fragmentation unit
				assertEquals(true, isFirstFU);
				assertEquals(false, isLastFU);
				mFragmented = true;
			} else {
				assertEquals(false, isFirstFU);
				if (mExpectedNALU.length == mOffsetInNALU) {
					// this is the last fragmentation unit
					assertEquals(true, isLastFU);

					mFragmented = false;
					mOffsetInNALU = 1;
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
			ByteStreamNALU current = mStream[mNALCount];
			ByteStreamNALU next = mStream[mNALCount + 1];
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

	private class MockEncoder {
		private IEncoderListener mListener;
		private int mFPSNum;
		private int mFPSDen;

		MockEncoder(IEncoderListener listener) {
			mListener = listener;
			// 29.97 fps
			mFPSNum = 30000;
			mFPSDen = 1001;
		}

		void inputByteStream(ByteStreamNALU[] stream) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			for (int i = 0; i < stream.length; i++) {
				ByteStreamNALU bs = stream[i];
				byte[] array = bs.createArray();
				os.write(array, 0, array.length);

				if (i < stream.length - 1) {
					ByteStreamNALU next = stream[i + 1];
					if (bs.frameNum == next.frameNum) {
						// enqueue it and send at once
						continue;
					}
				}

				long timestampUs = bs.frameNum * 1000L * 1000L * mFPSDen / mFPSNum;
				byte[] data = os.toByteArray();
				mListener.onEncoderOutput(VideoStreamingCodec.H264, VideoStreamingProtocol.RAW,
						data, timestampUs);
				os.reset();
			}

			try {
				os.close();
			} catch (IOException e) {
			}
		}
	}
}
