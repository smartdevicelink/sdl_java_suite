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
import com.smartdevicelink.proxy.rpc.enums.MetadataType;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class MetadataTags extends RPCStruct {

    public static final String KEY_MAIN_FIELD_1_TYPE = "mainField1";
    public static final String KEY_MAIN_FIELD_2_TYPE = "mainField2";
    public static final String KEY_MAIN_FIELD_3_TYPE = "mainField3";
    public static final String KEY_MAIN_FIELD_4_TYPE = "mainField4";

    /**
     * Constructs a newly allocated MetadataTags object
     */
    public MetadataTags() {
    }

    /**
     * Constructs a newly allocated MetadataTags object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public MetadataTags(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Set the metadata types of data contained in the "mainField1" text field
     */
    public MetadataTags setMainField1(List<MetadataType> metadataTypes) {
        setValue(KEY_MAIN_FIELD_1_TYPE, metadataTypes);
        return this;
    }

    /**
     * Set the metadata type of data contained in the "mainField1" text field
     */
    public MetadataTags setMainField1(MetadataType metadataType) {
        setValue(KEY_MAIN_FIELD_1_TYPE, Collections.singletonList(metadataType));
        return this;
    }

    /**
     * @return The type of data contained in the "mainField1" text field
     */
    @SuppressWarnings("unchecked")
    public List<MetadataType> getMainField1() {
        return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_1_TYPE);
    }

    /**
     * Set the metadata types of data contained in the "mainField2" text field
     */
    public MetadataTags setMainField2(List<MetadataType> metadataTypes) {
        setValue(KEY_MAIN_FIELD_2_TYPE, metadataTypes);
        return this;
    }

    /**
     * Set the metadata type of data contained in the "mainField2" text field
     */
    public MetadataTags setMainField2(MetadataType metadataType) {
        setValue(KEY_MAIN_FIELD_2_TYPE, Collections.singletonList(metadataType));
        return this;
    }

    /**
     * @return The type of data contained in the "mainField2" text field
     */
    @SuppressWarnings("unchecked")
    public List<MetadataType> getMainField2() {
        return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_2_TYPE);
    }

    /**
     * Set the metadata types of data contained in the "mainField3" text field
     */
    public MetadataTags setMainField3(List<MetadataType> metadataTypes) {
        setValue(KEY_MAIN_FIELD_3_TYPE, metadataTypes);
        return this;
    }

    /**
     * Set the metadata type of data contained in the "mainField3" text field
     */
    public MetadataTags setMainField3(MetadataType metadataType) {
        setValue(KEY_MAIN_FIELD_3_TYPE, Collections.singletonList(metadataType));
        return this;
    }

    /**
     * @return The type of data contained in the "mainField3" text field
     */
    @SuppressWarnings("unchecked")
    public List<MetadataType> getMainField3() {
        return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_3_TYPE);
    }

    /**
     * Set the metadata types of data contained in the "mainField4" text field
     */
    public MetadataTags setMainField4(List<MetadataType> metadataTypes) {
        setValue(KEY_MAIN_FIELD_4_TYPE, metadataTypes);
        return this;
    }

    /**
     * Set the metadata type of data contained in the "mainField4" text field
     */
    public MetadataTags setMainField4(MetadataType metadataType) {
        setValue(KEY_MAIN_FIELD_4_TYPE, Collections.singletonList(metadataType));
        return this;
    }

    /**
     * @return The type of data contained in the "mainField4" text field
     */
    @SuppressWarnings("unchecked")
    public List<MetadataType> getMainField4() {
        return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_4_TYPE);
    }

}
