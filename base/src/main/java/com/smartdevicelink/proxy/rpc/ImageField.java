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
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;

import java.util.Hashtable;
import java.util.List;

/** <p>The name that identifies the field.For example AppIcon,SoftButton, LocationImage, etc.</p>
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th> Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>name</td>
 * 			<td>ImageFieldName</td>
 * 			<td>The name that identifies the field.{@linkplain  ImageFieldName}</td>
 *                 <td></td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>imageTypeSupported</td>
 * 			<td>FileType</td>
 * 			<td>The image types that are supported in this field. {@linkplain FileType}</td>
 *                 <td></td>
 *                 <td>maxlength: 100</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>imageResolution</td>
 * 			<td>ImageResolution</td>
 * 			<td>The image resolution of this field.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 3.0
 * @see com.smartdevicelink.proxy.rpc.enums.ImageType
 * @see com.smartdevicelink.proxy.rpc.ImageResolution
 * @see com.smartdevicelink.proxy.rpc.enums.ImageFieldName
 *
 *
 * 
 *
 */

public class ImageField extends RPCStruct {
    public static final String KEY_IMAGE_TYPE_SUPPORTED = "imageTypeSupported";
    public static final String KEY_IMAGE_RESOLUTION = "imageResolution";
    public static final String KEY_NAME = "name";
    
    
    public ImageField() { }
   
    public ImageField(Hashtable<String, Object> hash) {
        super(hash);
    }

    public ImageField(@NonNull ImageFieldName name, @NonNull List<FileType> imageTypeSupported) {
        this();
        setName(name);
        setImageTypeSupported(imageTypeSupported);
    }

    public ImageFieldName getName() {
        return (ImageFieldName) getObject(ImageFieldName.class, KEY_NAME);
    } 
    public ImageField setName(@NonNull ImageFieldName name) {
        setValue(KEY_NAME, name);
        return this;
    }
    @SuppressWarnings("unchecked")
	public List<FileType> getImageTypeSupported() {
        return (List<FileType>) getObject(FileType.class, KEY_IMAGE_TYPE_SUPPORTED);
    }
    public ImageField setImageTypeSupported(@NonNull List<FileType> imageTypeSupported) {
        setValue(KEY_IMAGE_TYPE_SUPPORTED, imageTypeSupported);
        return this;
    }
    @SuppressWarnings("unchecked")
    public ImageResolution getImageResolution() {
        return (ImageResolution) getObject(ImageResolution.class, KEY_IMAGE_RESOLUTION);
    } 
    public ImageField setImageResolution( ImageResolution imageResolution) {
        setValue(KEY_IMAGE_RESOLUTION, imageResolution);
        return this;
    }
}
