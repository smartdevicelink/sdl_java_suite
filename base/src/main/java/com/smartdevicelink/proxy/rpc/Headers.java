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
package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * @since SmartDeviceLink 3.0
 */

public class Headers extends RPCStruct {
    public static final String KEY_CONTENT_TYPE = "ContentType";
    public static final String KEY_CONNECT_TIMEOUT = "ConnectTimeout";
    public static final String KEY_DO_OUTPUT = "DoOutput";
    public static final String KEY_DO_INPUT = "DoInput";
    public static final String KEY_USE_CACHES = "UseCaches";
    public static final String KEY_REQUEST_METHOD = "RequestMethod";
    public static final String KEY_READ_TIMEOUT = "ReadTimeout";
    public static final String KEY_INSTANCE_FOLLOW_REDIRECTS = "InstanceFollowRedirects";
    public static final String KEY_CHARSET = "charset";
    public static final String KEY_CONTENT_LENGTH = "Content-Length";

    public Headers() {
    }

    /**
     * <p>
     * Constructs a new Headers object indicated by the Hashtable
     * parameter
     * </p>
     *
     * @param hash The Hashtable to use
     */

    public Headers(Hashtable<String, Object> hash) {
        super(hash);
    }

    public Headers setContentType(String contentType) {
        setValue(KEY_CONTENT_TYPE, contentType);
        return this;
    }

    public String getContentType() {
        return getString(KEY_CONTENT_TYPE);
    }

    public Headers setConnectTimeout(Integer connectionTimeout) {
        setValue(KEY_CONNECT_TIMEOUT, connectionTimeout);
        return this;
    }

    public Integer getConnectTimeout() {
        return getInteger(KEY_CONNECT_TIMEOUT);
    }

    public Headers setDoOutput(Boolean doOutput) {
        setValue(KEY_DO_OUTPUT, doOutput);
        return this;
    }

    public Boolean getDoOutput() {
        return getBoolean(KEY_DO_OUTPUT);
    }

    public Headers setDoInput(Boolean doInput) {
        setValue(KEY_DO_INPUT, doInput);
        return this;
    }

    public Boolean getDoInput() {
        return getBoolean(KEY_DO_INPUT);
    }

    public Headers setUseCaches(Boolean usesCaches) {
        setValue(KEY_USE_CACHES, usesCaches);
        return this;
    }

    public Boolean getUseCaches() {
        return getBoolean(KEY_USE_CACHES);
    }

    public Headers setRequestMethod(String requestMethod) {
        setValue(KEY_REQUEST_METHOD, requestMethod);
        return this;
    }

    public String getRequestMethod() {
        return getString(KEY_REQUEST_METHOD);
    }


    public Headers setReadTimeout(Integer readTimeout) {
        setValue(KEY_READ_TIMEOUT, readTimeout);
        return this;
    }

    public Integer getReadTimeout() {
        return getInteger(KEY_READ_TIMEOUT);
    }

    public Headers setInstanceFollowRedirects(Boolean instanceFollowRedirects) {
        setValue(KEY_INSTANCE_FOLLOW_REDIRECTS, instanceFollowRedirects);
        return this;
    }

    public Boolean getInstanceFollowRedirects() {
        return getBoolean(KEY_INSTANCE_FOLLOW_REDIRECTS);
    }

    public Headers setCharset(String charset) {
        setValue(KEY_CHARSET, charset);
        return this;
    }

    public String getCharset() {
        return getString(KEY_CHARSET);
    }

    public Headers setContentLength(Integer contentLength) {
        setValue(KEY_CONTENT_LENGTH, contentLength);
        return this;
    }

    public Integer getContentLength() {
        return getInteger(KEY_CONTENT_LENGTH);
    }
}
