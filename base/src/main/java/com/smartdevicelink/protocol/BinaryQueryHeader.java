package com.smartdevicelink.protocol;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.util.BitConverter;
import com.smartdevicelink.util.DebugTool;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class BinaryQueryHeader {
    private static final String TAG = "BinaryQueryHeader";

    private byte _queryType;
    private int _queryID;
    private int _correlationID;
    private int _jsonSize;
    private int _errorCode;

    private byte[] _jsonData;
    private byte[] _bulkData;

    public BinaryQueryHeader() {
    }

    public static BinaryQueryHeader parseBinaryQueryHeader(byte[] binHeader) {
        BinaryQueryHeader msg = new BinaryQueryHeader();

        byte QUERY_Type = (byte) (binHeader[0] >>> 4);
        msg.setQueryType(QUERY_Type);

        int _queryID = (BitConverter.intFromByteArray(binHeader, 0) & 0x0FFFFFFF);
        msg.setQueryID(_queryID);

        int corrID = BitConverter.intFromByteArray(binHeader, 4);
        msg.setCorrelationID(corrID);

        int _jsonSize = BitConverter.intFromByteArray(binHeader, 8);
        msg.setJsonSize(_jsonSize);

        if (msg.getQueryType() == 0x20 && msg.getQueryID() == 0x02) {
            int _errorCode = BitConverter.intFromByteArray(binHeader, binHeader.length-1);
            msg.setErrorCode(_errorCode);
        }

        try {
            if (_jsonSize > 0) {
                byte[] _jsonData = new byte[_jsonSize];
                System.arraycopy(binHeader, 12, _jsonData, 0, _jsonSize);
                msg.setJsonData(_jsonData);
            }

            if (binHeader.length - _jsonSize - 12 > 0) {
                byte[] _bulkData;
                if (msg.getQueryType() == 0x20 && msg.getQueryID() == 0x02) {
                    _bulkData = new byte[binHeader.length - _jsonSize - 12 - 1];
                    System.arraycopy(binHeader, 12 + _jsonSize, _bulkData, 0, _bulkData.length - 1);
                } else {
                    _bulkData = new byte[binHeader.length - _jsonSize - 12];
                    System.arraycopy(binHeader, 12 + _jsonSize, _bulkData, 0, _bulkData.length);
                }
                msg.setBulkData(_bulkData);
            }

        } catch (OutOfMemoryError | ArrayIndexOutOfBoundsException e) {
            DebugTool.logError(TAG, "Unable to process data to form header");
            return null;
        }

        return msg;
    }

    public byte[] assembleHeaderBytes() {
        int binHeader = _queryID;
        binHeader &= 0xFFFFFFFF >>> 4;
        binHeader |= (_queryType << 28);

        byte[] ret = new byte[12];
        System.arraycopy(BitConverter.intToByteArray(binHeader), 0, ret, 0, 4);
        System.arraycopy(BitConverter.intToByteArray(_correlationID), 0, ret, 4, 4);
        System.arraycopy(BitConverter.intToByteArray(_jsonSize), 0, ret, 8, 4);
        return ret;
    }

    public byte getQueryType() {
        return _queryType;
    }

    public void setQueryType(byte _queryType) {
        this._queryType = _queryType;
    }

    public int getQueryID() {
        return _queryID;
    }

    public void setQueryID(int _queryID) {
        this._queryID = _queryID;
    }

    public int getCorrelationID() {
        return _correlationID;
    }

    public void setCorrelationID(int _correlationID) {
        this._correlationID = _correlationID;
    }

    public int getJsonSize() {
        return _jsonSize;
    }

    public void setJsonSize(int _jsonSize) {
        this._jsonSize = _jsonSize;
    }

    public int getErrorCode() {
        return _errorCode;
    }

    public void setErrorCode(int _errorCode) {
        this._errorCode = _errorCode;
    }

    public byte[] getJsonData() {
        return _jsonData;
    }

    public void setJsonData(byte[] _jsonData) {
        this._jsonData = new byte[this._jsonSize];
        System.arraycopy(_jsonData, 0, this._jsonData, 0, _jsonSize);
    }

    public byte[] getBulkData() {
        return _bulkData;
    }

    public void setBulkData(byte[] _bulkData) {
        this._bulkData = _bulkData;
    }
}
