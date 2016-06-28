package com.smartdevicelink.trace;

// Borrowed from Dave Boll's infamous SdlLinkRelay.java

public class Mime {
	
	private static String m_base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	
	public static String base64Encode(String str) {
		String b64String = "";
		try {
			byte[] strBytes = str.getBytes("US-ASCII");
			b64String = base64Encode(strBytes);
		} catch (Exception ex) {
			// Don't care?
		}
		return b64String;
	}

	public static String base64Encode(byte bytesToEncode[]) {
		return base64Encode(bytesToEncode, 0, bytesToEncode.length);
	}

	public static String base64Encode(byte bytesToEncode[], int offset, int length) {
		StringBuilder sb = new StringBuilder();

		int		idxin = 0;
		int		b64idx = 0;

		for (idxin=offset;idxin < offset + length;idxin++) {
			switch ((idxin-offset) % 3) {
				case 0:
					b64idx = (bytesToEncode[idxin] >> 2) & 0x3f;
					break;
				case 1:
					b64idx = (bytesToEncode[idxin] >> 4) & 0x0f;
					b64idx |= ((bytesToEncode[idxin-1] << 4)& 0x30);
					break;
				case 2:
					b64idx = (bytesToEncode[idxin] >> 6) & 0x03;
					b64idx |= ((bytesToEncode[idxin-1] << 2)& 0x3c);
					sb.append(m_base64Chars.charAt(b64idx));
					b64idx = bytesToEncode[idxin] & 0x3f;
					break;
			}
			sb.append(m_base64Chars.charAt(b64idx));
		}

		switch ((idxin-offset) % 3) {
			case 0:
				break;
			case 1:
				b64idx = (bytesToEncode[idxin-1] << 4) & 0x30;
				sb.append(m_base64Chars.charAt(b64idx));
				sb.append("==");
				break;
			case 2:
				b64idx = ((bytesToEncode[idxin-1] << 2)& 0x3c);
				sb.append(m_base64Chars.charAt(b64idx));
				sb.append('=');
				break;
		}	
		return sb.toString();
	}
}