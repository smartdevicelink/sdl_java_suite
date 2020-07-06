package com.smartdevicelink.protocol;

import android.support.test.runner.AndroidJUnit4;

import com.livio.BSON.BsonEncoder;
import com.smartdevicelink.protocol.enums.ControlFrameTags;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

@RunWith(AndroidJUnit4.class)
public class SdlPacketTests {
	//TODO: Add tests to cover other parts of SdlPacket class

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
	public void testNull(){
		byte[] testPayload = hexStringToByteArray(
				"16000000" +
						"02" +
						"68656c6c6f" +"00" + // hello
						"06000000" + "776f726c64" + "00" + //world
						"00");
		String tag = "hello";

		SdlPacket sdlPacket = new SdlPacket();
		assertNull(sdlPacket.getTag(tag));
		sdlPacket.setPayload(testPayload);
		assertEquals(sdlPacket.getTag("hello"),"world");
	}

	@Test
	public void testBsonDecoding(){
		SdlPacket sdlPacket = new SdlPacket();
		sdlPacket.setPayload(corePayload);
		assertEquals(sdlPacket.getTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID), TEST_HASH_ID);
		assertEquals(sdlPacket.getTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION), TEST_PROTOCOL_VERSION);
		assertEquals(sdlPacket.getTag(ControlFrameTags.RPC.StartServiceACK.MTU), TEST_MTU);
	}

	@Test
	public void testBsonEncoding(){
		HashMap<String, Object> testMap = new HashMap<>();
		testMap.put(ControlFrameTags.RPC.StartServiceACK.HASH_ID, TEST_HASH_ID);
		testMap.put(ControlFrameTags.RPC.StartServiceACK.MTU, TEST_MTU);
		testMap.put(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION, TEST_PROTOCOL_VERSION);

		byte[] observed = BsonEncoder.encodeToBytes(testMap);
		assertEquals(observed.length, corePayload.length);
		for(int i = 0; i < observed.length; i++){
			assertEquals(observed[i], corePayload[i]);
		}
	}

	// Helper method for converting String to Byte Array
	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
}
