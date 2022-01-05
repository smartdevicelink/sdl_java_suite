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

import java.util.Hashtable;

/**
 * VR help items  i.e. the text strings to be displayed, and when pronounced by the user the recognition of any of which must trigger the corresponding VR command.
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 *         <tr>
 *             <th>Param Name</th>
 *             <th>Type</th>
 *             <th>Description</th>
 *                 <th> Req.</th>
 *             <th>Notes</th>
 *             <th>Version Available</th>
 *         </tr>
 *         <tr>
 *             <td>text</td>
 *             <td>String</td>
 *             <td>Text to display for VR Help item</td>
 *                 <td>Y</td>
 *             <td>maxlength: 500</td>
 *             <td>SmartDeviceLink 2.3.2</td>
 *         </tr>
 *         <tr>
 *             <td>image</td>
 *             <td>Image</td>
 *             <td>Image struct for VR Help item</td>
 *                 <td>N</td>
 *             <td></td>
 *             <td>SmartDeviceLink 2.3.2</td>
 *         </tr>
 *         <tr>
 *             <td>position</td>
 *             <td>Integer</td>
 *             <td>Position to display item in VR Help list</td>
 *                 <td>N</td>
 *             <td> minvalue=1; maxvalue=100</td>
 *             <td>SmartDeviceLink 2.3.2</td>
 *         </tr>
 *  </table>
 */
public class VrHelpItem extends RPCStruct {
    public static final String KEY_POSITION = "position";
    public static final String KEY_TEXT = "text";
    public static final String KEY_IMAGE = "image";

    public VrHelpItem() {
    }

    /**
     * <p>
     * Constructs a new VrHelpItem object indicated by the Hashtable
     * parameter
     * </p>
     *
     * @param hash -The Hashtable to use
     */
    public VrHelpItem(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * VR help items  i.e. the text strings to be displayed, and when pronounced
     * by the user the recognition of any of which must trigger the corresponding VR command.
     *
     * @param text     Text to display for VR Help item
     * @param position Position to display item in VR Help list
     */
    public VrHelpItem(@NonNull String text, @NonNull Integer position) {
        this();
        setText(text);
        setPosition(position);
    }

    public VrHelpItem setText(@NonNull String text) {
        setValue(KEY_TEXT, text);
        return this;
    }

    public String getText() {
        return getString(KEY_TEXT);
    }

    public VrHelpItem setImage(Image image) {
        setValue(KEY_IMAGE, image);
        return this;
    }

    public Image getImage() {
        return (Image) getObject(Image.class, KEY_IMAGE);
    }

    public VrHelpItem setPosition(@NonNull Integer position) {
        setValue(KEY_POSITION, position);
        return this;
    }

    public Integer getPosition() {
        return getInteger(KEY_POSITION);
    }
}
