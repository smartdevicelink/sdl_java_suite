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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * <Strong>If using Widgets with Core > 6.0, use {@link Show} to change widget layouts</Strong> <br>
 * Used to set an alternate display layout. If not sent, default screen for
 * given platform will be shown
 *
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
 *             <td>displayLayout</td>
 *             <td>string</td>
 *             <td>Predefined or dynamically created screen layout.<p>Currently only predefined screen layouts are defined.</p> Predefined layouts include: "ONSCREEN_PRESETS" Custom screen containing app-defined onscreen presets. </td>
 *                 <td>Y</td>
 *             <td>maxlength: 500</td>
 *             <td>SmartDeviceLink 2.0</td>
 *         </tr>
 *         <tr>
 *             <td>dayColorScheme</td>
 *             <td>TemplateColorScheme</td>
 *             <td>The color scheme that is used for day.</td>
 *                 <td>N</td>
 *                 <td></td>
 *             <td>SmartDeviceLink 5.0</td>
 *         </tr>
 *
 *         <tr>
 *             <td>nightColorScheme</td>
 *             <td>TemplateColorScheme</td>
 *             <td>The color scheme that is used for night.</td>
 *                 <td>N</td>
 *                 <td></td>
 *             <td>SmartDeviceLink 5.0</td>
 *         </tr>
 *
 *  </table>
 * <p><b>Response </b></p>
 *
 * <p><b> Non-default Result Codes: </b></p>
 * <p> SUCCESS </p>
 * <p> INVALID_DATA</p>
 * <p> OUT_OF_MEMORY</p>
 * <p>  TOO_MANY_PENDING_REQUESTS</p>
 * <p>  APPLICATION_NOT_REGISTERED</p>
 * <p>  GENERIC_ERROR</p>
 * <p>   REJECTED</p>
 *
 * @since SmartDeviceLink 2.0
 * @deprecated in SmartDeviceLink 6.0
 */
@Deprecated
public class SetDisplayLayout extends RPCRequest {
    public static final String KEY_DISPLAY_LAYOUT = "displayLayout";
    public static final String KEY_DAY_COLOR_SCHEME = "dayColorScheme";
    public static final String KEY_NIGHT_COLOR_SCHEME = "nightColorScheme";

    /**
     * Constructs a new SetDisplayLayout object
     */
    public SetDisplayLayout() {
        super(FunctionID.SET_DISPLAY_LAYOUT.toString());
    }

    /**
     * Constructs a new SetDisplayLayout object indicated by the Hashtable
     * parameter
     * <p></p>
     *
     * @param hash The Hashtable to use
     */
    public SetDisplayLayout(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new SetDisplayLayout object
     *
     * @param displayLayout a String value representing a display layout
     */
    public SetDisplayLayout(@NonNull String displayLayout) {
        this();
        setDisplayLayout(displayLayout);
    }

    /**
     * Sets a display layout. Predefined or dynamically created screen layout.
     * Currently only predefined screen layouts are defined. Predefined layouts
     * include: "ONSCREEN_PRESETS" Custom screen containing app-defined onscreen
     * presets. Currently defined for GEN2
     *
     * @param displayLayout a String value representing a display layout
     */
    public SetDisplayLayout setDisplayLayout(@NonNull String displayLayout) {
        setParameters(KEY_DISPLAY_LAYOUT, displayLayout);
        return this;
    }

    /**
     * Gets a display layout.
     */
    public String getDisplayLayout() {
        return getString(KEY_DISPLAY_LAYOUT);
    }

    /**
     * Gets the color scheme that is currently used for day
     *
     * @return TemplateColorScheme - a TemplateColorScheme object representing the colors that are used
     * for day color scheme
     * @since SmartDeviceLink 5.0
     */
    public TemplateColorScheme getDayColorScheme() {
        return (TemplateColorScheme) getObject(TemplateColorScheme.class, KEY_DAY_COLOR_SCHEME);
    }

    /**
     * Sets the color scheme that is intended to be used for day
     *
     * @param templateColorScheme a TemplateColorScheme object representing the colors that will be
     *                            used for day color scheme
     * @since SmartDeviceLink 5.0
     */
    public SetDisplayLayout setDayColorScheme(TemplateColorScheme templateColorScheme) {
        setParameters(KEY_DAY_COLOR_SCHEME, templateColorScheme);
        return this;
    }

    /**
     * Gets the color scheme that is currently used for night
     *
     * @return TemplateColorScheme - a TemplateColorScheme object representing the colors that are used
     * for night color scheme
     * @since SmartDeviceLink 5.0
     */
    public TemplateColorScheme getNightColorScheme() {
        return (TemplateColorScheme) getObject(TemplateColorScheme.class, KEY_NIGHT_COLOR_SCHEME);
    }

    /**
     * Sets the color scheme that is intended to be used for night
     *
     * @param templateColorScheme a TemplateColorScheme object representing the colors that will be
     *                            used for night color scheme
     * @since SmartDeviceLink 5.0
     */
    public SetDisplayLayout setNightColorScheme(TemplateColorScheme templateColorScheme) {
        setParameters(KEY_NIGHT_COLOR_SCHEME, templateColorScheme);
        return this;
    }
}
