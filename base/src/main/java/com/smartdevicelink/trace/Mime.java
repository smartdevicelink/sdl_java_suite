package com.smartdevicelink.trace;

// Borrowed from Dave Boll's infamous SdlLinkRelay.java

public class Mime {

	private static final String BASE_64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

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
	 * @param offset        Offset to begin at
	 * @param length        Length to read
	 * @return Base64 encoded String or a null String if input array is null or the input range is out of bounds.
	 */
	public static String base64Encode(byte bytesToEncode[], int offset, int length) {
		if (bytesToEncode == null || bytesToEncode.length < length || bytesToEncode.length < offset + length) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		int idxin = 0;
		int b64idx = 0;

		for (idxin = offset; idxin < offset + length; idxin++) {
			switch ((idxin - offset) % 3) {
				case 0:
					b64idx = (bytesToEncode[idxin] >> 2) & 0x3f;
					break;
				case 1:
					b64idx = (bytesToEncode[idxin] >> 4) & 0x0f;
					b64idx |= ((bytesToEncode[idxin - 1] << 4) & 0x30);
					break;
				case 2:
					b64idx = (bytesToEncode[idxin] >> 6) & 0x03;
					b64idx |= ((bytesToEncode[idxin - 1] << 2) & 0x3c);
					sb.append(getBase64Char(b64idx));
					b64idx = bytesToEncode[idxin] & 0x3f;
					break;
			}
			sb.append(getBase64Char(b64idx));
		}

		switch ((idxin - offset) % 3) {
			case 0:
				break;
			case 1:
				b64idx = (bytesToEncode[idxin - 1] << 4) & 0x30;
				sb.append(getBase64Char(b64idx));
				sb.append("==");
				break;
			case 2:
				b64idx = ((bytesToEncode[idxin - 1] << 2) & 0x3c);
				sb.append(getBase64Char(b64idx));
				sb.append('=');
				break;
		}

		return sb.toString();

	}

	private static char getBase64Char(int b64idx){
		if(b64idx >= 0 && b64idx < BASE_64_CHARS.length()) {
			return BASE_64_CHARS.charAt(b64idx);
		}else{
			return 0x20;
		}
	}
}