package com.smartdevicelink.protocol;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.protocol.enums.SecurityQueryErrorCode;
import com.smartdevicelink.protocol.enums.SecurityQueryID;
import com.smartdevicelink.protocol.enums.SecurityQueryType;
import com.smartdevicelink.util.BitConverter;
import com.smartdevicelink.util.DebugTool;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SecurityQueryPayload {
    private static final String TAG = "BinaryQueryHeader";

    private SecurityQueryType _securityQueryType;
    private SecurityQueryID _securityQueryID;
    private int _correlationID;
    private int _jsonSize;
    private SecurityQueryErrorCode _errorCode;

    private byte[] _jsonData = null;
    private byte[] _bulkData = null;

    private static final int SECURITY_QUERY_HEADER_SIZE = 12;

    public SecurityQueryPayload() {
    }

    public static SecurityQueryPayload parseBinaryQueryHeader(byte[] binHeader) {
        if (binHeader == null || binHeader.length < SECURITY_QUERY_HEADER_SIZE) {
            DebugTool.logError(TAG, "Security Payload error: not enough data to form a Security Query Header. Data length: " + (binHeader != null ? binHeader.length : "null"));
            return null;
        }

        SecurityQueryPayload msg = new SecurityQueryPayload();

        //Set QueryType from the first 8 bits
        byte QUERY_Type = (byte) (binHeader[0]);
        msg.setQueryType(SecurityQueryType.valueOf(QUERY_Type));

        //Set queryID from the last 24 bits of the first 32 bits
        byte[] _queryID = new byte[3];
        System.arraycopy(binHeader, 1, _queryID, 0, 3);
        msg.setQueryID(SecurityQueryID.valueOf(_queryID));

        //set correlationID from the 32 bits after the first 32 bits
        int corrID = BitConverter.intFromByteArray(binHeader, 4);
        msg.setCorrelationID(corrID);

        //set jsonSize from the last 32 bits after the first 64 bits
        int _jsonSize = BitConverter.intFromByteArray(binHeader, 8);
        msg.setJsonSize(_jsonSize);

        //If we get an error message we want the error code from the last 8 bits
        if (msg.getQueryType() == SecurityQueryType.NOTIFICATION && msg.getQueryID() == SecurityQueryID.SEND_INTERNAL_ERROR) {
            msg.setErrorCode(SecurityQueryErrorCode.valueOf(binHeader[binHeader.length - 1]));
        }

        try {
            //Get the JsonData after the header (after 96 bits) based on the jsonData size
            if (_jsonSize > 0 && _jsonSize <= (binHeader.length - SECURITY_QUERY_HEADER_SIZE)) {
                byte[] _jsonData = new byte[_jsonSize];
                System.arraycopy(binHeader, SECURITY_QUERY_HEADER_SIZE, _jsonData, 0, _jsonSize);
                msg.setJsonData(_jsonData);
            }

            //Get the binaryData after the header (after 96 bits) and the jsonData size
            if (binHeader.length - _jsonSize - SECURITY_QUERY_HEADER_SIZE > 0) {
                byte[] _bulkData;
                if (msg.getQueryType() == SecurityQueryType.NOTIFICATION && msg.getQueryID() == SecurityQueryID.SEND_INTERNAL_ERROR) {
                    _bulkData = new byte[binHeader.length - _jsonSize - SECURITY_QUERY_HEADER_SIZE - 1];
                } else {
                    _bulkData = new byte[binHeader.length - _jsonSize - SECURITY_QUERY_HEADER_SIZE];
                }
                System.arraycopy(binHeader, SECURITY_QUERY_HEADER_SIZE + _jsonSize, _bulkData, 0, _bulkData.length);
                msg.setBulkData(_bulkData);
            }

        } catch (OutOfMemoryError | ArrayIndexOutOfBoundsException e) {
            DebugTool.logError(TAG, "Unable to process data to form header");
            return null;
        }

        return msg;
    }

    public byte[] assembleSecurityQueryPayload(int payloadSize) {
        byte[] payLoad = new byte[SECURITY_QUERY_HEADER_SIZE];
        if (_securityQueryID == SecurityQueryID.SEND_INTERNAL_ERROR && _securityQueryType == SecurityQueryType.NOTIFICATION) {
            payLoad = new byte[SECURITY_QUERY_HEADER_SIZE + payloadSize + 1];
            System.arraycopy(_jsonData, 0, payLoad, SECURITY_QUERY_HEADER_SIZE, _jsonSize);
            byte[] errorCode = new byte[1];
            if (this._errorCode != null) {
                errorCode[0] = _errorCode.getValue();
            } else {
                errorCode[0] = SecurityQueryErrorCode.ERROR_UNKNOWN_INTERNAL_ERROR.getValue();
            }
            System.arraycopy(errorCode, 0, payLoad, payLoad.length - 1, 1);
        } else if (_securityQueryID == SecurityQueryID.SEND_HANDSHAKE_DATA && _securityQueryType == SecurityQueryType.RESPONSE) {
            payLoad = new byte[SECURITY_QUERY_HEADER_SIZE + payloadSize];
            System.arraycopy(_bulkData, 0, payLoad, SECURITY_QUERY_HEADER_SIZE, payloadSize);
        }

        System.arraycopy(assembleHeaderBytes(), 0, payLoad, 0, SECURITY_QUERY_HEADER_SIZE);

        return payLoad;
    }

    private byte[] assembleHeaderBytes() {
        // From the properties, create a data buffer
        // Query Type - first 8 bits
        // Query ID - next 24 bits
        // Sequence Number - next 32 bits
        // JSON size - next 32 bits
        byte[] ret = new byte[SECURITY_QUERY_HEADER_SIZE];
        ret[0] = _securityQueryType.getValue();
        System.arraycopy(_securityQueryID.getValue(), 0, ret, 1, 3);
        System.arraycopy(BitConverter.intToByteArray(_correlationID), 0, ret, 4, 4);
        System.arraycopy(BitConverter.intToByteArray(_jsonSize), 0, ret, 8, 4);
        return ret;
    }

    public SecurityQueryType getQueryType() {
        return _securityQueryType;
    }

    public void setQueryType(SecurityQueryType _securityQueryType) {
        this._securityQueryType = _securityQueryType;
    }

    public SecurityQueryID getQueryID() {
        return _securityQueryID;
    }

    public void setQueryID(SecurityQueryID _securityQueryID) {
        this._securityQueryID = _securityQueryID;
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

    private void setJsonSize(int _jsonSize) {
        this._jsonSize = _jsonSize;
    }

    public SecurityQueryErrorCode getErrorCode() {
        return _errorCode;
    }

    public void setErrorCode(SecurityQueryErrorCode _errorCode) {
        this._errorCode = _errorCode;
    }

    public byte[] getJsonData() {
        return _jsonData;
    }

    public void setJsonData(byte[] _jsonData) {
        if (_jsonData == null) {
            this._jsonSize = 0;
            this._jsonData = null;
            return;
        }
        this._jsonSize = _jsonData.length;
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
