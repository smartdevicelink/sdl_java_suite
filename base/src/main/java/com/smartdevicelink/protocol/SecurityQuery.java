package com.smartdevicelink.protocol;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.protocol.enums.QueryErrorCode;
import com.smartdevicelink.protocol.enums.QueryID;
import com.smartdevicelink.protocol.enums.QueryType;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SecurityQuery {

    private QueryType _queryType = QueryType.INVALID_QUERY_TYPE;
    private QueryID _queryID = QueryID.INVALID_QUERY_ID;
    private int _correlationId;
    private int _jsonSize;
    private QueryErrorCode _queryErrorCode = QueryErrorCode.ERROR_SUCCESS;

    private byte[] _data = null;
    private byte[] _bulkData = null;

    public SecurityQuery() {
    }

    public QueryType getQueryType() {
        return this._queryType;
    }

    public void setQueryType(QueryType queryType) {
        this._queryType = queryType;
    }

    public QueryID getQueryID() {
        return this._queryID;
    }

    public void setQueryID(QueryID queryID) {
        this._queryID = queryID;
    }

    public int getCorrelationId() {
        return this._correlationId;
    }

    public void setCorrelationId(int msgId) {
        this._correlationId = msgId;
    }

    public int getJsonSize() {
        return this._jsonSize;
    }

    public void setJsonSize(int jsonSize) {
        this._jsonSize = jsonSize;
    }

    public QueryErrorCode getQueryErrorCode() {
        return this._queryErrorCode;
    }

    public void setQueryErrorCode(QueryErrorCode queryErrorCode) {
        this._queryErrorCode = queryErrorCode;
    }

    public byte[] getData() {
        return _data;
    }

    public void setData(byte[] data) {
        this._data = data;
        this._jsonSize = data.length;
    }

    public void setData(byte[] data, int offset, int length) {
        if (this._data != null)
            this._data = null;
        this._data = new byte[length];
        System.arraycopy(data, offset, this._data, 0, length);
        this._jsonSize = 0;
    }

    public byte[] getBulkData() {
        return _bulkData;
    }

    public void setBulkDataNoCopy(byte[] bulkData) {
        this._bulkData = bulkData;
    }

    public void setBulkData(byte[] bulkData) {
        if (this._bulkData != null)
            this._bulkData = null;
        this._bulkData = new byte[bulkData.length];
        System.arraycopy(bulkData, 0, this._bulkData, 0, bulkData.length);
    }

    public void setBulkData(byte[] bulkData, int length) {
        if (this._bulkData != null)
            this._bulkData = null;
        this._bulkData = new byte[length];
        System.arraycopy(bulkData, 0, this._bulkData, 0, length);
    }
}
