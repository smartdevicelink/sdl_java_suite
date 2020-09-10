/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium Inc. nor the names of
 * its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
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
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;

import java.util.Hashtable;
import java.util.List;

/**
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>supportedDynamicImageFieldNames</td>
 *      <td>List<ImageFieldName></td>
 *      <td>An array of ImageFieldName values for which the system supports sending OnFileUpdatenotifications. If you send an Image struct for that image field with a name without havinguploaded the image data using PutFile that matches that name, the system will request thatyou upload the data with PutFile at a later point when the HMI needs it. The HMI will thendisplay the image in the appropriate field. If not sent, assume false.</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>supportsDynamicSubMenus</td>
 *      <td>Boolean</td>
 *      <td>If true, the head unit supports dynamic sub-menus by sending OnUpdateSubMenunotifications. If true, you should not send AddCommands that attach to a parentID for anAddSubMenu until OnUpdateSubMenu is received with the menuID. At that point, you shouldsend all AddCommands with a parentID that match the menuID. If not set, assume false.</td>
 *      <td>N</td>
 *      <td></td>
 *  </tr>
 * </table>
 * @since SmartDeviceLink 7.0.0
 */
public class DynamicUpdateCapabilities extends RPCStruct {
    public static final String KEY_SUPPORTED_DYNAMIC_IMAGE_FIELD_NAMES = "supportedDynamicImageFieldNames";
    public static final String KEY_SUPPORTS_DYNAMIC_SUB_MENUS = "supportsDynamicSubMenus";

    /**
     * Constructs a new DynamicUpdateCapabilities object
     */
    public DynamicUpdateCapabilities() { }

    /**
     * Constructs a new DynamicUpdateCapabilities object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public DynamicUpdateCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the supportedDynamicImageFieldNames.
     *
     * @param supportedDynamicImageFieldNames An array of ImageFieldName values for which the system supports sending OnFileUpdate
     * notifications. If you send an Image struct for that image field with a name without having
     * uploaded the image data using PutFile that matches that name, the system will request that
     * you upload the data with PutFile at a later point when the HMI needs it. The HMI will then
     * display the image in the appropriate field. If not sent, assume false.
     */
    public DynamicUpdateCapabilities setSupportedDynamicImageFieldNames( List<ImageFieldName> supportedDynamicImageFieldNames) {
        setValue(KEY_SUPPORTED_DYNAMIC_IMAGE_FIELD_NAMES, supportedDynamicImageFieldNames);
        return this;
    }

    /**
     * Gets the supportedDynamicImageFieldNames.
     *
     * @return List<ImageFieldName> An array of ImageFieldName values for which the system supports sending OnFileUpdate
     * notifications. If you send an Image struct for that image field with a name without having
     * uploaded the image data using PutFile that matches that name, the system will request that
     * you upload the data with PutFile at a later point when the HMI needs it. The HMI will then
     * display the image in the appropriate field. If not sent, assume false.
     */
    @SuppressWarnings("unchecked")
    public List<ImageFieldName> getSupportedDynamicImageFieldNames() {
        return (List<ImageFieldName>) getObject(ImageFieldName.class, KEY_SUPPORTED_DYNAMIC_IMAGE_FIELD_NAMES);
    }

    /**
     * Sets the supportsDynamicSubMenus.
     *
     * @param supportsDynamicSubMenus If true, the head unit supports dynamic sub-menus by sending OnUpdateSubMenu
     * notifications. If true, you should not send AddCommands that attach to a parentID for an
     * AddSubMenu until OnUpdateSubMenu is received with the menuID. At that point, you should
     * send all AddCommands with a parentID that match the menuID. If not set, assume false.
     */
    public DynamicUpdateCapabilities setSupportsDynamicSubMenus( Boolean supportsDynamicSubMenus) {
        setValue(KEY_SUPPORTS_DYNAMIC_SUB_MENUS, supportsDynamicSubMenus);
        return this;
    }

    /**
     * Gets the supportsDynamicSubMenus.
     *
     * @return Boolean If true, the head unit supports dynamic sub-menus by sending OnUpdateSubMenu
     * notifications. If true, you should not send AddCommands that attach to a parentID for an
     * AddSubMenu until OnUpdateSubMenu is received with the menuID. At that point, you should
     * send all AddCommands with a parentID that match the menuID. If not set, assume false.
     */
    public Boolean getSupportsDynamicSubMenus() {
        return getBoolean(KEY_SUPPORTS_DYNAMIC_SUB_MENUS);
    }
}
