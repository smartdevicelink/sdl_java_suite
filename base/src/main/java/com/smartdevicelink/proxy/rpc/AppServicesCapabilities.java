/*
 * Copyright (c) 2019 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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
import com.smartdevicelink.proxy.rpc.enums.ServiceUpdateReason;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Capabilities of app services including what service types are supported
 * and the current state of services.
 */
public class AppServicesCapabilities extends RPCStruct {

    public static final String KEY_APP_SERVICES = "appServices";

    // Constructors

    /**
     * Capabilities of app services including what service types are supported
     * and the current state of services.
     */
    public AppServicesCapabilities() {
    }

    /**
     * Capabilities of app services including what service types are supported
     * and the current state of services.
     *
     * @param hash of parameters
     */
    public AppServicesCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    // Setters and Getters

    /**
     * An array of currently available services. If this is an update to the
     * capability the affected services will include an update reason in that item
     *
     * @param appServices -
     */
    public AppServicesCapabilities setAppServices(List<AppServiceCapability> appServices) {
        setValue(KEY_APP_SERVICES, appServices);
        return this;
    }

    /**
     * An array of currently available services. If this is an update to the
     * capability the affected services will include an update reason in that item
     *
     * @return appServices
     */
    @SuppressWarnings("unchecked")
    public List<AppServiceCapability> getAppServices() {
        return (List<AppServiceCapability>) getObject(AppServiceCapability.class, KEY_APP_SERVICES);
    }

    /**
     * This method will update the current List<AppServiceCapability> with the updated items. If the
     * items don't exist in the original ist they will be added. If the original list is null or
     * empty, the new list will simply be set as the list.
     *
     * @param updatedAppServiceCapabilities the List<AppServiceCapability> that have been updated
     * @return if the list was updated
     */
    public boolean updateAppServices(@NonNull List<AppServiceCapability> updatedAppServiceCapabilities) {
        if (updatedAppServiceCapabilities == null) {
            return false;
        }

        List<AppServiceCapability> appServiceCapabilities = getAppServices();

        if (appServiceCapabilities == null) {
            //If there are currently no app services, create one to iterate over with no entries
            appServiceCapabilities = new ArrayList<>(0);
        }

        //Create a shallow copy for us to alter while iterating through the original list
        List<AppServiceCapability> tempList = new ArrayList<>(appServiceCapabilities);

        for (AppServiceCapability updatedAppServiceCapability : updatedAppServiceCapabilities) {
            if (updatedAppServiceCapability != null) {
                //First search if the record exists in the current list and remove it if so
                for (AppServiceCapability appServiceCapability : appServiceCapabilities) {
                    if (updatedAppServiceCapability.matchesAppService(appServiceCapability)) {
                        tempList.remove(appServiceCapability); //Remove the old entry
                        break;
                    }
                }

                if (!ServiceUpdateReason.REMOVED.equals(updatedAppServiceCapability.getUpdateReason())) {
                    //If the app service was anything but removed, we can add the updated
                    //record back into the temp list. If it was REMOVED as the update reason
                    //it will not be added back.
                    tempList.add(updatedAppServiceCapability);
                }
            }
        }

        setAppServices(tempList);
        return !tempList.equals(appServiceCapabilities); //Return if the list is not equal to the original
    }

}
