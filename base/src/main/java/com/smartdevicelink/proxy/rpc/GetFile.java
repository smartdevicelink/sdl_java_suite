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
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.FileType;

import java.util.Hashtable;

/**
 * This request is sent to the module to retrieve a file
 */
public class GetFile extends RPCRequest {

	public static final String KEY_FILE_NAME = "fileName";
	public static final String KEY_APP_SERVICE_ID = "appServiceId";
	public static final String KEY_FILE_TYPE = "fileType";
	public static final String KEY_OFFSET = "offset";
	public static final String KEY_LENGTH = "length";

	// Constructors

	public GetFile() {
		super(FunctionID.GET_FILE.toString());
	}

	public GetFile(Hashtable<String, Object> hash) {
		super(hash);
	}

	public GetFile(@NonNull String fileName) {
		this();
		setFileName(fileName);
	}

	// Setters and Getters

	/**
	 * File name that should be retrieved.
	 * maxlength="255"
	 * @param fileName -
	 */
	public GetFile setFileName(@NonNull String fileName) {
        setParameters(KEY_FILE_NAME, fileName);
        return this;
    }

	/**
	 * File name that should be retrieved.
	 * maxlength="255"
	 * @return fileName
	 */
	public String getFileName(){
		return getString(KEY_FILE_NAME);
	}

	/**
	 * ID of the service that should have uploaded the requested file
	 * @param appServiceId -
	 */
	public GetFile setAppServiceId( String appServiceId) {
        setParameters(KEY_APP_SERVICE_ID, appServiceId);
        return this;
    }

	/**
	 * ID of the service that should have uploaded the requested file
	 * @return appServiceId
	 */
	public String getAppServiceId(){
		return getString(KEY_APP_SERVICE_ID);
	}

	/**
	 * Selected file type.
	 * @param fileType -
	 */
	public GetFile setFileType( FileType fileType) {
        setParameters(KEY_FILE_TYPE, fileType);
        return this;
    }

	/**
	 * Selected file type.
	 * @return fileType
	 */
	public FileType getFileType(){
		return (FileType) getObject(FileType.class, KEY_FILE_TYPE);
	}

	/**
	 * Optional offset in bytes for resuming partial data chunks
	 * minvalue="0" maxvalue="2000000000"
	 * @param offset -
	 */
	public GetFile setOffset( Integer offset) {
        setParameters(KEY_OFFSET, offset);
        return this;
    }

	/**
	 * Optional offset in bytes for resuming partial data chunks
	 * minvalue="0" maxvalue="2000000000"
	 * @return offset
	 */
	public Integer getOffset(){
		return getInteger(KEY_OFFSET);
	}

	/**
	 * Optional length in bytes for resuming partial data chunks if offset is set to 0, then length
	 * is the total length of the file to be downloaded
	 * minvalue="0" maxvalue="2000000000"
	 * @param length -
	 */
	public GetFile setLength( Integer length) {
        setParameters(KEY_LENGTH, length);
        return this;
    }

	/**
	 * Optional length in bytes for resuming partial data chunks if offset is set to 0, then length
	 * is the total length of the file to be downloaded
	 * minvalue="0" maxvalue="2000000000"
	 * @return length
	 */
	public Integer getLength(){
		return getInteger(KEY_LENGTH);
	}

}
