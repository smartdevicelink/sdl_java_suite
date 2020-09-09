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
package com.smartdevicelink.protocol;


import androidx.annotation.RestrictTo;

import com.smartdevicelink.util.BitConverter;
import com.smartdevicelink.util.DebugTool;
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class BinaryFrameHeader {
	private static final String TAG = "BinaryFrameHeader";

	private byte _rpcType;
	private int _functionID;
	private int _correlationID;
	private int _jsonSize;
	
	private byte[] _jsonData;
	private byte[] _bulkData;
	
	public BinaryFrameHeader() {}
	
	public static BinaryFrameHeader parseBinaryHeader(byte[] binHeader) {
		BinaryFrameHeader msg = new BinaryFrameHeader();
		
		byte RPC_Type = (byte) (binHeader[0] >>> 4);
		msg.setRPCType(RPC_Type);
		
		int _functionID = (BitConverter.intFromByteArray(binHeader, 0) & 0x0FFFFFFF);
		msg.setFunctionID(_functionID);
		
		int corrID = BitConverter.intFromByteArray(binHeader, 4);
		msg.setCorrID(corrID);
		
		int _jsonSize = BitConverter.intFromByteArray(binHeader, 8);
		msg.setJsonSize(_jsonSize);

		try {
			if (_jsonSize > 0) {
				byte[] _jsonData = new byte[_jsonSize];
				System.arraycopy(binHeader, 12, _jsonData, 0, _jsonSize);
				msg.setJsonData(_jsonData);
			}

			if (binHeader.length - _jsonSize - 12 > 0) {
				byte[] _bulkData = new byte[binHeader.length - _jsonSize - 12];
				System.arraycopy(binHeader, 12 + _jsonSize, _bulkData, 0, _bulkData.length);
				msg.setBulkData(_bulkData);
			}
		} catch (OutOfMemoryError|ArrayIndexOutOfBoundsException e){
			DebugTool.logError(TAG, "Unable to process data to form header");
			return null;
		}
		
		return msg;
	}
	
	public byte[] assembleHeaderBytes() {
		int binHeader = _functionID;
        // reset the 4 leftmost bits, for _rpcType
        binHeader &= 0xFFFFFFFF >>> 4;
		binHeader |= (_rpcType << 28);
		
		byte[] ret = new byte[12];
		System.arraycopy(BitConverter.intToByteArray(binHeader), 0, ret, 0, 4);
		System.arraycopy(BitConverter.intToByteArray(_correlationID), 0, ret, 4, 4);
		System.arraycopy(BitConverter.intToByteArray(_jsonSize), 0, ret, 8, 4);
		
		return ret;
	}
	
	public byte getRPCType() {
		return _rpcType;
	}

	public void setRPCType(byte _rpcType) {
		this._rpcType = _rpcType;
	}

	public int getFunctionID() {
		return _functionID;
	}

	public void setFunctionID(int _functionID) {
		this._functionID = _functionID;
	}

	public int getCorrID() {
		return _correlationID;
	}

	public void setCorrID(int _correlationID) {
		this._correlationID = _correlationID;
	}

	public int getJsonSize() {
		return _jsonSize;
	}

	public void setJsonSize(int _jsonSize) {
		this._jsonSize = _jsonSize;
	}
	
	public byte[] getJsonData() {
		return _jsonData;
	}
	
	public void setJsonData(byte[] _jsonData) {
		this._jsonData = new byte[this._jsonSize];
		System.arraycopy(_jsonData, 0, this._jsonData, 0, _jsonSize);
		//this._jsonData = _jsonData;
	}
	
	public byte[] getBulkData() {
		return _bulkData;
	}
	
	public void setBulkData(byte[] _bulkData) {
		this._bulkData = _bulkData;
	}
}
