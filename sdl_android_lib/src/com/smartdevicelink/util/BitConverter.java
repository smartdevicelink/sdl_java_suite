package com.smartdevicelink.util;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class BitConverter {
	
	/**
	 * Takes an array of binary (base 2) elements and converts them left to 
	 * right into a string of hexadecimal (base 16) characters.
	 * 
	 * @param bytes The array to be converted.
	 * 
	 * @return A string of hexadecimal characters.
	 * 
	 * @see {@link #bytesToHex(byte[], int, int)}
	 */
	public static String bytesToHex(byte [] bytes) {
		
		// This method assumes that the caller wants the entire array to be
		// converted into a hexadecimal string.
		return bytesToHex(bytes, 0, bytes.length);
	}
	
	/**
	 * Takes an array of binary (base 2) elements and converts them, or a subset
	 * of the array, left to right into a string of hexadecimal (base 16) 
	 * characters.
	 * 
	 * @param bytes The array to be converted.
	 * @param offset The position indicating where to begin converting. If the 
	 * offset is greater than the number of elements in the byte array then an 
	 * empty string will be returned.
	 * @param length The number of bytes to be converted into hexadecimal. If 
	 * the length given is greater than the number of bytes in the array 
	 * everything after the offset will be converted and returned.
	 * 
	 * @return A string of hexadecimal characters.
	 */
	public static String bytesToHex(byte[] bytes, int offset, int length) {
		if (bytes == null) { 
			return null; 
		}
		
		// If the offset is greater than the length of the array then there are
		// no values that need to be converted, so the method returns an empty
		// string. This avoids a potential ArrayIndexOutOfBoundsException.
		if (offset > bytes.length) {
			return new String();
		}		
		
		// An array of valid hexadecimal characters.
		final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
		
		// Will store the conversion.
		char[] hexChars = new char[2 * length];

		// Avoids an ArrayIndexOutOfBoundsException by setting an appropriate
		// limit to the number of bytes to be converted, since the conversion 
		// string can be a subset of those bytes.
		length = Math.min(bytes.length, (offset + length));
		
		for (int binaryArrayIndex = offset; binaryArrayIndex < length; binaryArrayIndex++) {
			int hexArrayIndex = 0;
			
			// Get a single byte from the array.
			byte unit = bytes[binaryArrayIndex];
			
			// Use nibbles as index into hex digit array (left nibble, then right).
			hexChars[hexArrayIndex++] = HEX_DIGITS[(unit & 0xf0) >> 4];
			hexChars[hexArrayIndex++] = HEX_DIGITS[(unit & 0x0f)];
		}
		
		return new String(hexChars);
	}

	public static byte [] hexToBytes(String hexString) {
		if (hexString == null) { return null; }
		if (hexString.length() % 2 != 0) {
			hexString = "0" + hexString;
		}
		byte [] theBytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i += 2) {
			String byteString = hexString.substring(i, i + 2);
			byte theByte = (byte)Integer.parseInt(byteString, 16);
			theBytes[i/2] = theByte;
		}
		return theBytes;
	}

	public static final byte[] intToByteArray(int value) {
		return new byte[] {
				(byte)(value >>> 24),
				(byte)(value >>> 16),
				(byte)(value >>> 8),
				(byte)value};
	}
	
	/**
	 * Takes an array of binary (base 2) elements and converts them, or a subset
	 * of the array, into an integer (base 10).
	 * 
	 * @param bytes The array to be converted.
	 * @param offset The position indicating where to begin converting. If the 
	 * offset is greater than the number of elements in the byte array then 0
	 * will be returned.
	 * 
	 * @return An integer, 0 if the array of bytes given was null or the offset
	 * was invalid.
	 */
	public static int intFromByteArray(byte[] bytes, int offset) {
		if (bytes == null) {
			return 0;
		}
		
		if (offset > bytes.length) {
			return 0;
		}
		
		return ByteBuffer.wrap(Arrays.copyOfRange(bytes, offset, bytes.length)).getInt();
    }

	public static final byte[] shortToByteArray(short value) {
		return new byte[] {
				(byte)(value >>> 8),
				(byte)value};
	}
	
	/**
	 * Takes an array of binary (base 2) elements and converts them, or a subset
	 * of the array, into a short (base 10).
	 * 
	 * @param bytes The array to be converted.
	 * @param offset The position indicating where to begin converting. If the 
	 * offset is greater than the number of elements in the byte array then 0
	 * will be returned.
	 * 
	 * @return A short, 0 if the array of bytes given was null or the offset was
	 * invalid.
	 */
	public static short shortFromByteArray(byte[] bytes, int offset) {
		if (bytes == null) {
			return 0;
		}
		
		if (offset > bytes.length) {
			return 0;
		}
		
		return ByteBuffer.wrap(Arrays.copyOfRange(bytes, offset, bytes.length)).getShort();
    }
}