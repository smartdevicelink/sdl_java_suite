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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ImageType;

import java.util.Hashtable;
import java.util.List;

/**
 * Contains information of a window about supported text and image fields as well as
 * supported image types. It also contains a list of available templates, the number
 * of available preset buttons. Per currently configured template it contains
 * information about button and soft button capabilities.
 */
public class WindowCapability extends RPCStruct {
   public static final String KEY_WINDOW_ID = "windowID";
   public static final String KEY_TEXT_FIELDS = "textFields";
   public static final String KEY_IMAGE_FIELDS = "imageFields";
   public static final String KEY_IMAGE_TYPE_SUPPORTED = "imageTypeSupported";
   public static final String KEY_TEMPLATES_AVAILABLE = "templatesAvailable";
   public static final String KEY_NUM_CUSTOM_PRESETS_AVAILABLE = "numCustomPresetsAvailable";
   public static final String KEY_BUTTON_CAPABILITIES = "buttonCapabilities";
   public static final String KEY_SOFT_BUTTON_CAPABILITIES = "softButtonCapabilities";

    /**
     * Constructs a newly allocated WindowCapability object.
     */
   public WindowCapability() { }

    /**
     * Constructs a newly allocated WindowCapability object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public WindowCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Returns the specified ID of the window. Can be a predefined window,
     * or omitted for the main window on the main display.
     * @return A window ID.
     */
   public Integer getWindowID() {
       return getInteger(KEY_WINDOW_ID);
   }

    /**
     * Sets the specified ID of the window. Can be a predefined window,
     * or omitted for the main window on the main display.
     * @note This method won't modify the ID of an existing window.
     * @return A window ID.
     */
   public void setWindowID(@Nullable Integer value) {
       setValue(KEY_WINDOW_ID, value);
   }

    /**
     * Returns an array of TextField structures, each of which describes a field
     * in the window template which the application can write to using operation
     * such as <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc.
     * This array of TextField structures identify all the text fields to which
     * the application can write on the current window template.
     * @return the List of textFields
     */
    @SuppressWarnings("unchecked")
    public List<TextField> getTextFields() {
        return (List<TextField>) getObject(TextField.class, KEY_TEXT_FIELDS);
    }

    /**
     * Sets an array of TextField structures, each of which describes a field
     * in the HMI which the application can write to using operations such as
     * <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc.
     * This array of TextField structures identify all the text fields to which
     * the application can write on the current window template.
     * @param textFields the List of textFields
     */
    public void setTextFields(@Nullable List<TextField> textFields ) {
        setValue(KEY_TEXT_FIELDS, textFields);
    }

    /**
     * Returns an array of ImageField structures, each of which describes an
     * image field in the window template which the application can write to
     * using operation such as <i>{@linkplain Show}</i> etc.
     * This array of ImageField structures identify all the image fields to which
     * the application can write on the current window template.
     * @return The list of ImageFields
     */
    @SuppressWarnings("unchecked")
    public List<ImageField> getImageFields() {
        return (List<ImageField>) getObject(ImageField.class, KEY_IMAGE_FIELDS);
    }

    /**
     * Sets an array of ImageField structures, each of which describes an
     * image field in the window template which the application can write to
     * using operation such as <i>{@linkplain Show}</i> etc.
     * This array of ImageField structures identify all the image fields to which
     * the application can write on the current window template.
     * @param imageFields The list of ImageFields
     */
    public void setImageFields( List<ImageField> imageFields ) {
        setValue(KEY_IMAGE_FIELDS, imageFields);
    }

    /**
     * Returns a list of supported image types. See
     * <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ImageType}</i>.
     * This list provides the information if only static images are supported
     * of if new images can be dynamically uploaded by the application.
     * @return The list of supported image types.
     */
    @SuppressWarnings("unchecked")
    public List<ImageType> getImageTypeSupported() {
        return (List<ImageType>)getObject(ImageType.class, KEY_IMAGE_TYPE_SUPPORTED);
    }

    /**
     * Returns a list of supported image types. See
     * <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ImageType}</i>.
     * This list provides the information if only static images are supported
     * of if new images can be dynamically uploaded by the application.
     * @param imageTypes The list of supported image types.
     */
    public void setImageTypeSupported(@Nullable List<ImageType> imageTypes) {
        setValue(KEY_IMAGE_TYPE_SUPPORTED, imageTypes);
    }

    /**
     * Returns a list of template names that can be used for the window.
     * @return A list of template names.
     */
    @SuppressWarnings("unchecked")
    public List<String> getTemplatesAvailable() {
        return (List<String>) getObject(String.class, KEY_TEMPLATES_AVAILABLE);
    }

    /**
     * Sets a list of template names that can be used for the window.
     * @return A list of template names.
     */
    public void setTemplatesAvailable(List<String> templatesAvailable) {
        setValue(KEY_TEMPLATES_AVAILABLE, templatesAvailable);
    }

    public Integer getNumCustomPresetsAvailable() {
        return getInteger(KEY_NUM_CUSTOM_PRESETS_AVAILABLE);
    }

    public void setNumCustomPresetsAvailable(Integer numCustomPresetsAvailable) {
        setValue(KEY_NUM_CUSTOM_PRESETS_AVAILABLE, numCustomPresetsAvailable);
    }
}
