package com.smartdevicelink.trace;

// Borrowed from Dave Boll's infamous SdlLinkRelay.java

public class Mime {
	
	private static String m_base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	/**
	 * @param str A String to encode into base64 String.
	 * @return Base64 encoded String or a null String if input is null.
	 */
	public static String base64Encode(String str) {
		if(str == null){
			return null;
		}

		String b64String = "";
		try {
			byte[] strBytes = str.getBytes("US-ASCII");
			b64String = base64Encode(strBytes);
		} catch (Exception ex) {
			// Don't care?
		}
		return b64String;
	}

	/**
	 * @param bytesToEncode A byte array to encode into base64 String.
	 * @return Base64 encoded String or a null String if input array is null.
	 */
	public static String base64Encode(byte bytesToEncode[]) {
		if(bytesToEncode != null){
			return base64Encode(bytesToEncode, 0, bytesToEncode.length);
		}
		return null;
	}

	/**
	 * @param bytesToEncode A byte array to encode into base64 String.
	 * @param offset Offset to begin at
	 * @param length Length to read
	 * @return Base64 encoded String or a null String if input array is null.
	 */
	public static String base64Encode(byte bytesToEncode[], int offset, int length) {
		if(bytesToEncode == null){
			return null;
		}

		StringBuilder sb = new StringBuilder();

		int		idxin = 0;
		int		b64idx = 0;

		try {

			for (idxin = offset; idxin < offset + length; idxin++) {
				switch ((idxin - offset) % 3) {
					case 0:
						if(idxin >= 0 && idxin < bytesToEncode.length){
							b64idx = (bytesToEncode[idxin] >> 2) & 0x3f;
						}
						break;
					case 1:
						if(idxin >= 0 && idxin < bytesToEncode.length) {
							b64idx = (bytesToEncode[idxin] >> 4) & 0x0f;
						}
						if(idxin - 1 >= 0 && idxin - 1 < bytesToEncode.length) {
							b64idx |= ((bytesToEncode[idxin - 1] << 4) & 0x30);
						}
						break;
					case 2:
						if(idxin >= 0 && idxin < bytesToEncode.length) {
							b64idx = (bytesToEncode[idxin] >> 6) & 0x03;
						}
						if(idxin - 1 >= 0 && idxin - 1 < bytesToEncode.length) {
							b64idx |= ((bytesToEncode[idxin - 1] << 2) & 0x3c);
						}
						if(b64idx >= 0 && b64idx < m_base64Chars.length()) {
							sb.append(m_base64Chars.charAt(b64idx));
						}
						if(idxin >= 0 && idxin < bytesToEncode.length) {
							b64idx = bytesToEncode[idxin] & 0x3f;
						}
						break;
				}
				if(b64idx >= 0 && b64idx < m_base64Chars.length()) {
					sb.append(m_base64Chars.charAt(b64idx));
				}
			}

			switch ((idxin - offset) % 3) {
				case 0:
					break;
				case 1:
					if(idxin - 1 >= 0 && idxin - 1 < m_base64Chars.length()) {
						b64idx = (bytesToEncode[idxin - 1] << 4) & 0x30;
					}
					if(b64idx >= 0 && b64idx < m_base64Chars.length()) {
						sb.append(m_base64Chars.charAt(b64idx));
					}
					sb.append("==");
					break;
				case 2:
					if(idxin - 1 >= 0 && idxin - 1 < m_base64Chars.length()) {
						b64idx = ((bytesToEncode[idxin - 1] << 2) & 0x3c);
					}
					if(b64idx >= 0 && b64idx < m_base64Chars.length()) {
						sb.append(m_base64Chars.charAt(b64idx));
					}
					sb.append('=');
					break;
			}

			return sb.toString();
		}catch (ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
}