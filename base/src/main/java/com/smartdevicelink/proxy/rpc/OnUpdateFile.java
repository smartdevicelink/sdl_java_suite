/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium Inc. nor the names of
 * its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
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

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.Hashtable;

/**
 * This notification tells an app to upload and update a file with a given name.
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>fileName</td>
 *      <td>String</td>
 *      <td>File reference name.</td>
 *      <td>Y</td>
 *      <td></td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 7.0.0
 */
public class OnUpdateFile extends RPCNotification {
    public static final String KEY_FILE_NAME = "fileName";

    /**
     * Constructs a new OnUpdateFile object
     */
    public OnUpdateFile() {
        super(FunctionID.ON_UPDATE_FILE.toString());
    }

    /**
     * Constructs a new OnUpdateFile object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public OnUpdateFile(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new OnUpdateFile object
     *
     * @param fileName File reference name.
     */
    public OnUpdateFile(@NonNull String fileName) {
        this();
        setFileName(fileName);
    }

    /**
     * Sets the fileName.
     *
     * @param fileName File reference name.
     */
    public void setFileName(@NonNull String fileName) {
        setParameters(KEY_FILE_NAME, fileName);
    }

    /**
     * Gets the fileName.
     *
     * @return String File reference name.
     */
    public String getFileName() {
        return getString(KEY_FILE_NAME);
    }
}
