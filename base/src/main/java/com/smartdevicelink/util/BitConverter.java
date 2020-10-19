/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.util;

public class BitConverter {
    /**
     * @param bytes byte array that will be converted to hex
     * @return the String containing converted hex values or null if byte array is null
     */
    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return bytesToHex(bytes, 0, bytes.length);
    } // end-method

    /**
     * @param bytes  byte array that will be converted to hex
     * @param offset int representing the offset to begin conversion at
     * @param length int representing number of bytes in array to convert
     * @return the String containing converted hex values or null if byte array is null,
     * a null value if byte array is null,
     * and an empty string if the offset is greater than the length
     */
    public static String bytesToHex(byte[] bytes, int offset, int length) {
        if (bytes == null) {
            return null;
        }

        if (offset > bytes.length) {
            return "";
        }

        final char[] HexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        byte b;
        char[] hexChars = new char[2 * length];
        //StringBuffer sb = new StringBuffer();
        int upperBound = Math.min(bytes.length, (offset + length));
        int baidx = 0;
        int sidx = 0;
        for (baidx = offset; baidx < upperBound; baidx++) {
            // Get the byte from the array
            b = bytes[baidx];
            // Use nibbles as index into hex digit array (left nibble, then right)
            hexChars[sidx++] = HexDigits[(b & 0xf0) >> 4];
            hexChars[sidx++] = HexDigits[(b & 0x0f)];
        } // end-for
        return new String(hexChars);
    } // end-method

    /**
     * @param hexString the String containing converted hex values
     * @return byte array converted from input String or null if String is null
     */
    public static byte[] hexToBytes(String hexString) {
        if (hexString == null) {
            return null;
        }
        if (hexString.length() % 2 != 0) {
            hexString = "0" + hexString;
        }
        byte[] theBytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            String byteString = hexString.substring(i, i + 2);
            byte theByte = (byte) Integer.parseInt(byteString, 16);
            theBytes[i / 2] = theByte;
        }
        return theBytes;
    } // end-method

    public static final byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

    /**
     * @param sizeBuf byte array that will be converted to int
     * @param offset The position indicating where to begin converting.
     * @return int converted from byte array,
     * -1 if byte array is null,
     * and 0 if the offset is greater than the length of the byte array
     */
    public static int intFromByteArray(byte[] sizeBuf, int offset) {
        if (sizeBuf == null) {
            return -1;
        }

        if (offset >sizeBuf.length) {
            return 0;
        }

        int ret = 0;
        for (int i = offset; i < offset + 4; i++) {
            ret <<= 8;
            ret |= 0xFF & sizeBuf[i];
        }
        return ret;
    }

    public static final byte[] shortToByteArray(short value) {
        return new byte[]{
                (byte) (value >>> 8),
                (byte) value};
    }

    /**
     * @param sizeBuf byte array that will be converted to short
     * @param offset The position indicating where to begin converting.
     * @return short converted from byte array,
     * -1 if byte array is null,
     * and 0 if the offset is greater than the length of the byte array
     */
    public static short shortFromByteArray(byte[] sizeBuf, int offset) {
        if (sizeBuf == null) {
            return -1;
        }

        if (offset > sizeBuf.length) {
            return 0;
        }

        short ret = 0;
        for (int i = offset; i < offset + 2; i++) {
            ret <<= 8;
            ret |= 0xFF & sizeBuf[i];
        }
        return ret;
    }

    /**
     * Converts the byte array into a string of hex values.
     *
     * @param bytes byte array that will be converted to hex
     * @param end   EXCLUSIVE so if it it receives 10 it will print 0-9
     * @return the String containing converted hex values or null if byte array is null
     */
    public static String bytesToHex(byte[] bytes, int end) {
        if (bytes == null) {
            return null;
        }
        if (bytes.length < end) {
            end = bytes.length;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < end; i++) {
            sb.append(" ");
            sb.append(String.format("%02X ", bytes[i]));
        }
        return sb.toString();
    }
}
