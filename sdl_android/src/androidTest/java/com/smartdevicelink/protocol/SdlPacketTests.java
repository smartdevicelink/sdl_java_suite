package com.smartdevicelink.protocol;

import android.test.AndroidTestCase;

public class SdlPacketTests extends AndroidTestCase {
	//TODO: Add tests to cover other parts of SdlPacket class

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

	public void testBsonDecoding(){
		SdlPacket sdlPacket = new SdlPacket();
		// Test payload from core representing hashId and mtu size
		byte[] testPayload = hexStringToByteArray("39000000" +
				"10" +
				"68617368496400" +
				"01000100" +
				"126d747500" +
				"00000002" +
				"70726f746f636f6c56657273" +
				"696f6e0006000000352e302e300000");
		sdlPacket.setPayload(testPayload);
		assertEquals(sdlPacket.getTag("hashId"), 65537);
		assertEquals((long) sdlPacket.getTag("mtu"), (long) 33554432);
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
