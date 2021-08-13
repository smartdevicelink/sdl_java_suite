package com.smartdevicelink.protocol;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.protocol.enums.QueryErrorCode;
import com.smartdevicelink.protocol.enums.QueryID;
import com.smartdevicelink.protocol.enums.QueryType;
import com.smartdevicelink.util.BitConverter;
import com.smartdevicelink.util.DebugTool;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SecurityQueryPayload {
    private static final String TAG = "BinaryQueryHeader";

    private QueryType _queryType;
    private QueryID _queryID;
    private int _correlationID;
    private int _jsonSize;
    private QueryErrorCode _errorCode;

    private byte[] _jsonData = null;
    private byte[] _bulkData = null;

    public SecurityQueryPayload() {
    }

    public static SecurityQueryPayload parseBinaryQueryHeader(byte[] binHeader) {
        SecurityQueryPayload msg = new SecurityQueryPayload();

        byte QUERY_Type = (byte) (binHeader[0]);
        msg.setQueryType(QueryType.valueOf(QUERY_Type));

        byte[] _queryID = new byte[3];
        System.arraycopy(binHeader, 1, _queryID, 0, 3);
        msg.setQueryID(QueryID.valueOf(_queryID));

        int corrID = BitConverter.intFromByteArray(binHeader, 4);
        msg.setCorrelationID(corrID);

        int _jsonSize = BitConverter.intFromByteArray(binHeader, 8);
        msg.setJsonSize(_jsonSize);

        if (msg.getQueryType() == QueryType.NOTIFICATION && msg.getQueryID() == QueryID.SEND_INTERNAL_ERROR) {
            msg.setErrorCode(QueryErrorCode.valueOf(binHeader[binHeader.length - 1]));
        }

        try {
            if (_jsonSize > 0) {
                byte[] _jsonData = new byte[_jsonSize];
                System.arraycopy(binHeader, 12, _jsonData, 0, _jsonSize);
                msg.setJsonData(_jsonData);
            }

            if (binHeader.length - _jsonSize - 12 > 0) {
                byte[] _bulkData;
                if (msg.getQueryType() == QueryType.NOTIFICATION && msg.getQueryID() == QueryID.SEND_INTERNAL_ERROR) {
                    _bulkData = new byte[binHeader.length - _jsonSize - 12 - 1];
                } else {
                    _bulkData = new byte[binHeader.length - _jsonSize - 12];
                }
                System.arraycopy(binHeader, 12 + _jsonSize, _bulkData, 0, _bulkData.length);
                msg.setBulkData(_bulkData);
            }

        } catch (OutOfMemoryError | ArrayIndexOutOfBoundsException e) {
            DebugTool.logError(TAG, "Unable to process data to form header");
            return null;
        }

        return msg;
    }

    public byte[] assembleHeaderBytes() {
        byte[] ret = new byte[12];
        ret[0] = _queryType.getValue();
        System.arraycopy(_queryID.getValue(), 0, ret, 1, 3);
        System.arraycopy(BitConverter.intToByteArray(_correlationID), 0, ret, 4, 4);
        System.arraycopy(BitConverter.intToByteArray(_jsonSize), 0, ret, 8, 4);
        return ret;
    }

    public QueryType getQueryType() {
        return _queryType;
    }

    public void setQueryType(QueryType _queryType) {
        this._queryType = _queryType;
    }

    public QueryID getQueryID() {
        return _queryID;
    }

    public void setQueryID(QueryID _queryID) {
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

    public QueryErrorCode getErrorCode() {
        return _errorCode;
    }

    public void setErrorCode(QueryErrorCode _errorCode) {
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
