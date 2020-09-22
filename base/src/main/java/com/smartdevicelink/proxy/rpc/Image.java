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
import com.smartdevicelink.proxy.rpc.enums.ImageType;

import java.util.Hashtable;

/**
 * Specifies, which image shall be used, e.g. in Alerts or on SoftButtons provided the display supports it.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>value</td>
 * 			<td>String</td>
 * 			<td>Either the static hex icon value or the binary image file name identifier (sent by PutFile).
 * 					<ul>
 * 					<li>Min: 0</li>
 * 					<li>Max: 65535</li>
 * 					</ul>
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>imageType</td>
 * 			<td>ImageType</td>
 * 			<td>Describes, whether it is a static or dynamic image.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 *
 * @since SmartDeviceLink 2.0
 */
public class Image extends RPCStruct {
    public static final String KEY_VALUE = "value";
    public static final String KEY_IMAGE_TYPE = "imageType";
    public static final String KEY_IS_TEMPLATE = "isTemplate";

    /**
     * Constructs a newly allocated Image object
     */
    public Image() {
    }

    /**
     * Constructs a newly allocated Image object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public Image(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated Image object
     *
     * @param value     either the static hex icon value or the binary image file name identifier (sent by PutFile)
     * @param imageType whether it is a static or dynamic image
     */
    public Image(@NonNull String value, @NonNull ImageType imageType) {
        this();
        setValue(value);
        setImageType(imageType);
    }

    /**
     * Set either the static hex icon value or the binary image file name identifier (sent by PutFile)
     *
     * @param value either the static hex icon value or the binary image file name identifier (sent by PutFile)
     */
    public Image setValue(@NonNull String value) {
        setValue(KEY_VALUE, value);
        return this;
    }

    /**
     * Get either the static hex icon value or the binary image file name identifier (sent by PutFile)
     *
     * @return either the static hex icon value or the binary image file name identifier (sent by PutFile)
     */
    public String getValue() {
        return getString(KEY_VALUE);
    }

    /**
     * Set the image type (static or dynamic image)
     *
     * @param imageType whether it is a static or dynamic image
     */
    public Image setImageType(@NonNull ImageType imageType) {
        setValue(KEY_IMAGE_TYPE, imageType);
        return this;
    }

    /**
     * Get image type (static or dynamic image)
     *
     * @return the image type (static or dynamic image)
     */
    public ImageType getImageType() {
        return (ImageType) getObject(ImageType.class, KEY_IMAGE_TYPE);
    }

    /**
     * Set whether this Image is a template image whose coloring should be decided by the HMI
     *
     * @param isTemplate boolean that tells whether this Image is a template image
     */
    public Image setIsTemplate(Boolean isTemplate) {
        setValue(KEY_IS_TEMPLATE, isTemplate);
        return this;
    }

    /**
     * Get whether this Image is a template image whose coloring should be decided by the HMI
     *
     * @return boolean that tells whether this Image is a template image
     */
    public Boolean getIsTemplate() {
        return getBoolean(KEY_IS_TEMPLATE);
    }
}
