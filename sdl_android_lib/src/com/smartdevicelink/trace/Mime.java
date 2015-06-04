package com.smartdevicelink.trace;

// Borrowed from Dave Boll's infamous SdlLinkRelay.java

public class Mime {
	
	/**
	 * Encodes the given string using 64 alphanumeric characters, including the
	 * '+' and '/' characters and suffixed with either '=' or '=='.
	 * 
	 * @param str The string to be encoded.
	 * 
	 * @return The encoded string, null if the given string was null or 
	 * contained invalid characters and could not be encoded.
	 */
	public static String base64Encode(String str) {
		try {
			return base64Encode(str.getBytes("US-ASCII"));
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Encodes the given byte array using 64 alphanumeric characters, including
	 * the '+' and '/' characters and suffixed with either '=' or '=='.
	 * 
	 * @param bytes The byte array to be encoded.
	 * 
	 * @return The encoded string, null if the given string was null or 
	 * contained invalid characters and could not be encoded.
	 */
	public static String base64Encode(byte[] bytes) {
		return base64Encode(bytes, 0, bytes.length);
	}

	/**
	 * Encodes the given byte array using 64 alphanumeric characters, including
	 * the '+' and '/' characters and suffixed with either '=' or '=='.
	 * 
	 * @param bytes The byte array to be encoded.
	 * @param offset The position indicating where to begin converting. If the 
	 * offset is greater than the number of elements in the byte array then an 
	 * empty string will be returned.
	 * @param length The number of bytes to be encoded. If the length given is 
	 * greater than the number of bytes in the array everything after the 
	 * offset will be encoded and returned.
	 * 
	 * @return The encoded string, null if the given string was null or 
	 * contained invalid characters and could not be encoded, or if the offset
	 * was invalid.
	 */
	public static String base64Encode(byte[] bytes, int offset, int length) {
		if (bytes == null) { 
			return null; 
		}
		
		// If the offset is greater than the length of the array then there are
		// no values that need to be converted, so the method returns an empty
		// string. This avoids a potential ArrayIndexOutOfBoundsException.
		if (offset > bytes.length) {
			return new String();
		}
		
		// An array of valid base 64 characters.
		final char[] BASE64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

		// Avoids an ArrayIndexOutOfBoundsException by setting an appropriate 
		// limit to the number of bytes to be converted, since the conversion 
		// string can be a subset of those bytes.
		length = Math.min(bytes.length, (offset + length));
		
		StringBuilder builder = new StringBuilder();
		int		binaryArrayIndex = 0;
		int		base64Index = 0;
		
		try {
			for (binaryArrayIndex = offset; binaryArrayIndex < length; binaryArrayIndex++) {
				switch ((binaryArrayIndex-offset) % 3) {
					case 0:
						base64Index = (bytes[binaryArrayIndex] >> 2) & 0x3f;
						break;
					case 1:
						base64Index = (bytes[binaryArrayIndex] >> 4) & 0x0f;
						base64Index |= ((bytes[binaryArrayIndex-1] << 4)& 0x30);
						break;
					case 2:
						base64Index = (bytes[binaryArrayIndex] >> 6) & 0x03;
						base64Index |= ((bytes[binaryArrayIndex-1] << 2)& 0x3c);
						builder.append(BASE64[base64Index]);
						base64Index = bytes[binaryArrayIndex] & 0x3f;
						break;
				}
				builder.append(BASE64[base64Index]);
			}
	
			switch ((binaryArrayIndex-offset) % 3) {
				case 0:
					break;
				case 1:
					base64Index = (bytes[binaryArrayIndex-1] << 4) & 0x30;
					builder.append(BASE64[base64Index]);
					builder.append("==");
					break;
				case 2:
					base64Index = ((bytes[binaryArrayIndex-1] << 2)& 0x3c);
					builder.append(BASE64[base64Index]);
					builder.append('=');
					break;
			}			
			return builder.toString();
		} catch (Exception e) {
			return null;
		}
	} 

	@SuppressWarnings("unused")
    private byte[] base64Decode(String base64String) {
		byte[] outBytes = null;
		byte[] base64ASCIIString = null;
		final String ASCII_Encoding = "US-ASCII";
		
		// Convert b64 string to raw bytes
		try {
			base64ASCIIString = base64String.getBytes(ASCII_Encoding);
		} catch (Exception ex) {
			return null;
		}
		
		if (!m_decodeInitComplete) {
			m_decodeInitComplete = true;
			initForDecode();
		} // end-if

		int	numInChars = base64ASCIIString.length;

		if ((numInChars % 4) > 0) {
			return null;
		} // end-if

		int numOutBytes = base64ASCIIString.length / 4;
		numOutBytes *= 3;
		int	eqpos = base64String.indexOf("=");
		if (eqpos >= 0) {
			numOutBytes--;
			if (base64String.substring(eqpos).indexOf("==") >= 0) {
				numOutBytes--;
			} // end-if
		} // end-if
		outBytes = new byte[numOutBytes];

		byte	b64idxbits = 0x00;
		int		iidx = 0, oidx = 0;
		byte	writeByte = 0x00;
		byte	b64ASCIIChar = 0x00;
		for (iidx=0, oidx=0;iidx < numInChars;iidx++) {
			b64ASCIIChar = base64ASCIIString[iidx];
			if (b64ASCIIChar == 0x3d /*'='*/) {
				return outBytes;
			} // end-if

			if (!isb64char(b64ASCIIChar)) {
				return null;
			} // end-if

			switch (iidx % 4) {
				case 0:
					break;
				case 1:
					b64idxbits = b64decode[base64ASCIIString[iidx-1]];
					writeByte = (byte)((b64idxbits << 2) | ((b64decode[b64ASCIIChar] >> 4) & 0x03));
					outBytes[oidx++] = writeByte;
					break;
				case 2:
					b64idxbits = b64decode[base64ASCIIString[iidx-1]];
					writeByte = (byte)(((b64idxbits << 4) & 0xf0) | ((b64decode[b64ASCIIChar] >> 2) & 0x0f));
					outBytes[oidx++] = writeByte;
					break;
				case 3:
					b64idxbits = b64decode[base64ASCIIString[iidx-1]];
					writeByte = (byte)(((b64idxbits << 6) & 0xc0) | ((b64decode[b64ASCIIChar]) & 0x3f));
					outBytes[oidx++] = writeByte;
					break;
			} // end-switch
		} // end-for

		return outBytes;

	} // end-method


	private static	byte[]	b64decode = new byte[256];
	// A-Z is 0x41-0x5a
	// a-z is 0x61-0x7a
	// 0-9 is 0x30-0x39
	// + is 0x2b
	// / is 0x2f

	private static	boolean	m_decodeInitComplete = false;

	private void initForDecode() {
		int aidx = 0;
		int lidx = 0;
		// Set A-Z
		for (aidx=0x41, lidx=0;aidx <= 0x5a;aidx++, lidx++) {
			b64decode[aidx] = (byte)lidx;
		} // end-for
		// Set a-z
		for (aidx=0x61;aidx <= 0x7a;aidx++, lidx++) {
			b64decode[aidx] = (byte)lidx;
		} // end-for
		// Set 0-9
		for (aidx=0x30;aidx <= 0x39;aidx++, lidx++) {
			b64decode[aidx] = (byte)lidx;
		} // end-for
		// Set '+'
		b64decode[0x2b] = (byte)lidx++;
		// Set '/'
		b64decode[0x2f] = (byte)lidx++;
	} // end-method

	private boolean	isb64char(byte b) {
		// A-Z is 0x41-0x5a
		// a-z is 0x61-0x7a
		// 0-9 is 0x30-0x39
		// + is 0x2b
		// / is 0x2f
		return (   (b >= 0x41 && b <= 0x5a)
			|| (b >= 0x61 && b <= 0x7a)
			|| (b >= 0x30 && b <= 0x39)
			|| (b == 0x2b)
			|| (b == 0x2f)
			);
	} // end-method
} // end-class