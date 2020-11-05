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
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.Language;

import java.util.Hashtable;

/**
 * Provides information to what language the Sdl HMI language was changed
 * <p>
 * </p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * AudioStreamingState:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * SystemContext:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * </ul>
 * <p>
 * <b>Parameter List:</b>
 * <table border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>Req</th>
 * <th>Notes</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>language</td>
 * <td>{@linkplain Language}</td>
 * <td>Current SDL voice engine (VR+TTS) language</td>
 * <td>Y</td>
 * <td></td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * <tr>
 * <td>hmiDisplayLanguage</td>
 * <td>{@linkplain Language}</td>
 * <td>Current display language</td>
 * <td>Y</td>
 * <td></td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * </table>
 * </p>
 */
public class OnLanguageChange extends RPCNotification {
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_HMI_DISPLAY_LANGUAGE = "hmiDisplayLanguage";

    /**
     * Constructs a newly allocated OnCommand object
     */
    public OnLanguageChange() {
        super(FunctionID.ON_LANGUAGE_CHANGE.toString());
    }

    /**
     * <p>Constructs a newly allocated OnLanguageChange object indicated by the Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public OnLanguageChange(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated OnCommand object
     *
     * @param language           language that current SDL voice engine(VR+TTS) use
     * @param hmiDisplayLanguage language that current SDL voice engine(VR+TTS) use
     */
    public OnLanguageChange(@NonNull Language language, @NonNull Language hmiDisplayLanguage) {
        this();
        setLanguage(language);
        setHmiDisplayLanguage(hmiDisplayLanguage);
    }

    /**
     * <p>Sets language that current SDL voice engine(VR+TTS) use</p>
     *
     * @param language language that current SDL voice engine(VR+TTS) use
     */
    public OnLanguageChange setLanguage(@NonNull Language language) {
        setParameters(KEY_LANGUAGE, language);
        return this;
    }

    /**
     * <p>Returns language that current SDL voice engine(VR+TTS) use</p>
     *
     * @return {@linkplain Language} language that current SDL voice engine(VR+TTS) use
     */
    public Language getLanguage() {
        return (Language) getObject(Language.class, KEY_LANGUAGE);
    }

    /**
     * <p>Sets language that current display use</p>
     *
     * @param hmiDisplayLanguage language that current SDL voice engine(VR+TTS) use
     */
    public OnLanguageChange setHmiDisplayLanguage(@NonNull Language hmiDisplayLanguage) {
        setParameters(KEY_HMI_DISPLAY_LANGUAGE, hmiDisplayLanguage);
        return this;
    }

    /**
     * <p>Returns language that current  display use</p>
     *
     * @return {@linkplain Language} language that current display use
     */
    public Language getHmiDisplayLanguage() {
        return (Language) getObject(Language.class, KEY_HMI_DISPLAY_LANGUAGE);
    }
}
