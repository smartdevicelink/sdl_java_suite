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
import java.util.List;

public class LightControlCapabilities extends RPCStruct {
    public static final String KEY_MODULE_NAME = "moduleName";
    public static final String KEY_SUPPORTED_LIGHTS = "supportedLights";
    public static final String KEY_MODULE_INFO = "moduleInfo";

    /**
     * Constructs a new LightControlCapabilities object
     */
    public LightControlCapabilities() {
    }

    /**
     * <p>Constructs a new LightControlCapabilities object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash The Hashtable to use
     */
    public LightControlCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated LightControlCapabilities object
     *
     * @param moduleName      short friendly name of the light control module.
     * @param supportedLights An array of available LightCapabilities that are controllable.
     */
    public LightControlCapabilities(@NonNull String moduleName, @NonNull List<LightCapabilities> supportedLights) {
        this();
        setModuleName(moduleName);
        setSupportedLights(supportedLights);
    }

    /**
     * Sets the moduleName portion of the LightControlCapabilities class
     *
     * @param moduleName The short friendly name of the light control module. It should not be used to identify a module by mobile application.
     */
    public LightControlCapabilities setModuleName(@NonNull String moduleName) {
        setValue(KEY_MODULE_NAME, moduleName);
        return this;
    }

    /**
     * Gets the moduleName portion of the LightControlCapabilities class
     *
     * @return String - The short friendly name of the light control module. It should not be used to identify a module by mobile application.
     */
    public String getModuleName() {
        return getString(KEY_MODULE_NAME);
    }

    /**
     * Gets the supportedLights portion of the LightControlCapabilities class
     *
     * @return List<LightCapabilities> - An array of available LightCapabilities that are controllable.
     */
    @SuppressWarnings("unchecked")
    public List<LightCapabilities> getSupportedLights() {
        return (List<LightCapabilities>) getObject(LightCapabilities.class, KEY_SUPPORTED_LIGHTS);
    }

    /**
     * Sets the supportedLights portion of the LightControlCapabilities class
     *
     * @param supportedLights An array of available LightCapabilities that are controllable.
     */
    public LightControlCapabilities setSupportedLights(@NonNull List<LightCapabilities> supportedLights) {
        setValue(KEY_SUPPORTED_LIGHTS, supportedLights);
        return this;
    }

    /**
     * Sets ModuleInfo for this capability
     *
     * @param info the ModuleInfo to be set
     */
    public LightControlCapabilities setModuleInfo(ModuleInfo info) {
        setValue(KEY_MODULE_INFO, info);
        return this;
    }

    /**
     * Gets a ModuleInfo of this capability
     *
     * @return module info of this capability
     */
    public ModuleInfo getModuleInfo() {
        return (ModuleInfo) getObject(ModuleInfo.class, KEY_MODULE_INFO);
    }
}
