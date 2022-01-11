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

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.Version;

import java.util.Hashtable;

/**
 * Specifies the version number of the SDL V4 interface. This is used by both the application and SDL to declare what interface version each is using.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>majorVersion</td>
 * 			<td>Integer</td>
 * 			<td>
 * 					<ul>
 * 					<li>minvalue="1"</li>
 * 				    <li>maxvalue="10"</li>
 * 					</ul>
 * 			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>minorVersion</td>
 * 			<td>Integer</td>
 * 			<td>
 * 					<ul>
 * 					<li>minvalue="0"</li>
 * 				    <li>maxvalue="1000"</li>
 * 					</ul>
 * 			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * </table>
 *
 * @since SmartDeviceLink 1.0
 */
public class SdlMsgVersion extends RPCStruct {
    public static final String KEY_MAJOR_VERSION = "majorVersion";
    public static final String KEY_MINOR_VERSION = "minorVersion";
    public static final String KEY_PATCH_VERSION = "patchVersion";

    /**
     * Constructs a newly allocated SdlMsgVersion object
     */
    public SdlMsgVersion() {
    }

    /**
     * Constructs a newly allocated SdlMsgVersion object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public SdlMsgVersion(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated SdlMsgVersion object
     *
     * @param majorVersion minvalue="1" and maxvalue="10"
     * @param minorVersion min: 0; max: 1000
     */
    public SdlMsgVersion(@NonNull Integer majorVersion, @NonNull Integer minorVersion) {
        this();
        setMajorVersion(majorVersion);
        setMinorVersion(minorVersion);

    }

    /**
     * Constructs a newly allocated SdlMsgVersion object
     *
     * @param version Creates a new RPC struct SdlMsgVersion based on the utility class
     */
    public SdlMsgVersion(@NonNull Version version) {
        this();
        setMajorVersion(version.getMajor());
        setMinorVersion(version.getMinor());
        setPatchVersion(version.getPatch());

    }

    @Override
    public void format(com.smartdevicelink.util.Version rpcVersion, boolean formatParams) {
        if (getPatchVersion() == null) {
            setPatchVersion(0);
        }
        super.format(rpcVersion, formatParams);
    }

    /**
     * Get major version
     * <ul>
     * <li>minvalue="1"</li>
     *    <li>maxvalue="10"</li>
     * </ul>
     *
     * @return the major version
     */
    public Integer getMajorVersion() {
        return getInteger(KEY_MAJOR_VERSION);
    }

    /**
     * Set major version
     * <ul>
     * <li>minvalue="1"</li>
     *    <li>maxvalue="10"</li>
     * </ul>
     *
     * @param majorVersion minvalue="1" and maxvalue="10"
     */
    public SdlMsgVersion setMajorVersion(@NonNull Integer majorVersion) {
        setValue(KEY_MAJOR_VERSION, majorVersion);
        return this;
    }

    /**
     * Get minor version
     * <ul>
     * <li>minvalue="0"</li>
     *    <li>maxvalue="1000"</li>
     * </ul>
     *
     * @return the minor version
     */
    public Integer getMinorVersion() {
        return getInteger(KEY_MINOR_VERSION);
    }

    /**
     * Set minor version
     * <ul>
     * <li>minvalue="0"</li>
     *    <li>maxvalue="1000"</li>
     * </ul>
     *
     * @param minorVersion min: 0; max: 1000
     */
    public SdlMsgVersion setMinorVersion(@NonNull Integer minorVersion) {
        setValue(KEY_MINOR_VERSION, minorVersion);
        return this;
    }

    /**
     * Get patch version
     * <ul>
     * <li>minvalue="0"</li>
     *    <li>maxvalue="1000"</li>
     * </ul>
     *
     * @return the patch version
     */
    public Integer getPatchVersion() {
        return getInteger(KEY_PATCH_VERSION);
    }

    /**
     * Set patch version
     * <ul>
     * <li>minvalue="0"</li>
     *    <li>maxvalue="1000"</li>
     * </ul>
     *
     * @param patchVersion min: 0; max: 1000
     */
    public SdlMsgVersion setPatchVersion(Integer patchVersion) {
        setValue(KEY_PATCH_VERSION, patchVersion);
        return this;
    }

}
