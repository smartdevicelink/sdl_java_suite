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
 * A color scheme for all display layout templates.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 *         <tr>
 *             <th>Name</th>
 *             <th>Type</th>
 *             <th>Description</th>
 *             <th>Reg.</th>
 *             <th>Version Available</th>
 *         </tr>
 *         <tr>
 *             <td>primaryColor</td>
 *             <td>RGBColor</td>
 *             <td>The primary "accent" color</td>
 *             <td>N</td>
 *             <td>SmartDeviceLink 5.0</td>
 *         </tr>
 *         <tr>
 *             <td>secondaryColor</td>
 *             <td>RGBColor</td>
 *             <td>The secondary "accent" color</td>
 *             <td>N</td>
 *             <td>SmartDeviceLink 5.0</td>
 *         </tr>
 *         <tr>
 *             <td>backgroundColor</td>
 *             <td>RGBColor</td>
 *             <td>The color of the background</td>
 *             <td>N</td>
 *             <td>SmartDeviceLink 5.0</td>
 *         </tr>
 * </table>
 *
 * @since SmartDeviceLink 5.0
 */
public class TemplateColorScheme extends RPCStruct {

    public static final String KEY_PRIMARY_COLOR = "primaryColor";
    public static final String KEY_SECONDARY_COLOR = "secondaryColor";
    public static final String KEY_BACKGROUND_COLOR = "backgroundColor";

    /**
     * Constructs a new TemplateColorScheme object
     */
    public TemplateColorScheme() {
    }

    /**
     * Constructs a new TemplateColorScheme object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public TemplateColorScheme(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the primaryColor of the scheme
     *
     * @param color an RGBColor object representing the primaryColor
     */
    public TemplateColorScheme setPrimaryColor(RGBColor color) {
        setValue(KEY_PRIMARY_COLOR, color);
        return this;
    }

    /**
     * Gets the primaryColor of the scheme
     *
     * @return an RGBColor object representing the primaryColor
     */
    public RGBColor getPrimaryColor() {
        return (RGBColor) getObject(RGBColor.class, KEY_PRIMARY_COLOR);
    }

    /**
     * Sets the secondaryColor of the scheme
     *
     * @param color an RGBColor object representing the secondaryColor
     */
    public TemplateColorScheme setSecondaryColor(RGBColor color) {
        setValue(KEY_SECONDARY_COLOR, color);
        return this;
    }

    /**
     * Gets the secondaryColor of the scheme
     *
     * @return an RGBColor object representing the secondaryColor
     */
    public RGBColor getSecondaryColor() {
        return (RGBColor) getObject(RGBColor.class, KEY_SECONDARY_COLOR);
    }

    /**
     * Sets the backgroundColor of the scheme
     *
     * @param color an RGBColor object representing the backgroundColor
     */
    public TemplateColorScheme setBackgroundColor(RGBColor color) {
        setValue(KEY_BACKGROUND_COLOR, color);
        return this;
    }

    /**
     * Gets the backgroundColor of the scheme
     *
     * @return an RGBColor object representing the backgroundColor
     */
    public RGBColor getBackgroundColor() {
        return (RGBColor) getObject(RGBColor.class, KEY_BACKGROUND_COLOR);
    }
}



