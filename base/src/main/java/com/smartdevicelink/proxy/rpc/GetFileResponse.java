/*
 * Copyright (c) 2019 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

public class GetFileResponse extends RPCResponse {

    public static final String KEY_OFFSET = "offset";
    public static final String KEY_LENGTH = "length";
    public static final String KEY_FILE_TYPE = "fileType";
    public static final String KEY_CRC = "crc";

    /**
     * Constructs a new PublishAppServiceResponse object
     */
    public GetFileResponse() {
        super(FunctionID.GET_FILE.toString());
    }

    public GetFileResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new PublishAppServiceResponse object
     *
     * @param success    whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public GetFileResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }

    // Custom Getters / Setters

    /**
     * File type that is being sent in response
     *
     * @param fileType -
     */
    public GetFileResponse setFileType(FileType fileType) {
        setParameters(KEY_FILE_TYPE, fileType);
        return this;
    }

    /**
     * File type that is being sent in response
     *
     * @return fileType
     */
    public FileType getFileType() {
        return (FileType) getObject(FileType.class, KEY_FILE_TYPE);
    }

    /**
     * Optional offset in bytes for resuming partial data chunks
     * minvalue="0" maxvalue="2000000000"
     *
     * @param offset -
     */
    public GetFileResponse setOffset(Integer offset) {
        setParameters(KEY_OFFSET, offset);
        return this;
    }

    /**
     * Optional offset in bytes for resuming partial data chunks
     * minvalue="0" maxvalue="2000000000"
     *
     * @return offset
     */
    public Integer getOffset() {
        return getInteger(KEY_OFFSET);
    }

    /**
     * Optional length in bytes for resuming partial data chunks if offset is set to 0, then length
     * is the total length of the file to be downloaded
     * minvalue="0" maxvalue="2000000000"
     *
     * @param length -
     */
    public GetFileResponse setLength(Integer length) {
        setParameters(KEY_LENGTH, length);
        return this;
    }

    /**
     * Optional length in bytes for resuming partial data chunks if offset is set to 0, then length
     * is the total length of the file to be downloaded
     * minvalue="0" maxvalue="2000000000"
     *
     * @return length
     */
    public Integer getLength() {
        return getInteger(KEY_LENGTH);
    }

    /**
     * Additional CRC32 checksum to protect data integrity up to 512 Mbits
     * minvalue="0" maxvalue="4294967295"
     *
     * @param crc -
     */
    public GetFileResponse setCRC(Integer crc) {
        setParameters(KEY_CRC, crc);
        return this;
    }

    /**
     * Additional CRC32 checksum to protect data integrity up to 512 Mbits
     * minvalue="0" maxvalue="4294967295"
     *
     * @return crc
     */
    public Integer getCRC() {
        return getInteger(KEY_CRC);
    }
}