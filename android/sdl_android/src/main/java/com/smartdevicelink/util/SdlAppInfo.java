/*
 * Copyright (c) 2018 Livio, Inc.
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

package com.smartdevicelink.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;

import com.smartdevicelink.proxy.rpc.VehicleType;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Joey Grover on 2/2/18.
 */

public class SdlAppInfo {
    private static final String TAG = "SdlAppInfo";

    //FIXME we shouldn't be duplicating constants, but this currently keeps us from needing a context instance.
    private static final String SDL_ROUTER_VERSION_METADATA = "sdl_router_version";
    private static final String SDL_CUSTOM_ROUTER_METADATA = "sdl_custom_router";
    private static final String SDL_OEM_VEHICLE_TYPE_METADATA = "sdl_oem_vehicle_type";


    String packageName;
    ComponentName routerServiceComponentName;
    int routerServiceVersion = 4; //We use this as a default and assume if the number doesn't exist in meta data it is because the app hasn't updated.
    boolean isCustomRouterService = false;
    List<VehicleType> supportedVehicles = new ArrayList<>();
    long lastUpdateTime;

    @Deprecated
    public SdlAppInfo(ResolveInfo resolveInfo, PackageInfo packageInfo) {
        if (resolveInfo.serviceInfo != null) {

            this.packageName = resolveInfo.serviceInfo.packageName;
            this.routerServiceComponentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);

            Bundle metadata = resolveInfo.serviceInfo.metaData;
            if (metadata != null) {

                if (metadata.containsKey(SDL_ROUTER_VERSION_METADATA)) {
                    this.routerServiceVersion = metadata.getInt(SDL_ROUTER_VERSION_METADATA);
                }

                if (metadata.containsKey(SDL_CUSTOM_ROUTER_METADATA)) {
                    this.isCustomRouterService = metadata.getBoolean(SDL_CUSTOM_ROUTER_METADATA);
                }
            } else {
                DebugTool.logWarning(TAG, packageName + " has not supplied metadata with their router service!");
            }
        }

        if (packageInfo != null) {
            this.lastUpdateTime = packageInfo.lastUpdateTime;
            if (this.lastUpdateTime <= 0) {
                this.lastUpdateTime = packageInfo.firstInstallTime;
            }
        } else {
            this.lastUpdateTime = 0;
        }
    }

    public SdlAppInfo(ResolveInfo resolveInfo, PackageInfo packageInfo, Context context) {
        if (resolveInfo.serviceInfo != null) {

            this.packageName = resolveInfo.serviceInfo.packageName;
            this.routerServiceComponentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);

            Bundle metadata = resolveInfo.serviceInfo.metaData;
            if (metadata != null) {

                if (metadata.containsKey(SDL_ROUTER_VERSION_METADATA)) {
                    this.routerServiceVersion = metadata.getInt(SDL_ROUTER_VERSION_METADATA);
                }

                if (metadata.containsKey(SDL_CUSTOM_ROUTER_METADATA)) {
                    this.isCustomRouterService = metadata.getBoolean(SDL_CUSTOM_ROUTER_METADATA);
                }

                if (metadata.containsKey(SDL_OEM_VEHICLE_TYPE_METADATA)) {
                    if (context == null) {
                        DebugTool.logWarning(TAG, "Unable to deserialize supported vehicles: supplied Context is null");
                        return;
                    }

                    String contextPackageName = context.getPackageName();
                    if (contextPackageName == null || packageName == null) {
                        DebugTool.logWarning(TAG, String.format("Unable to deserialize supported vehicles. ContextPackageName: %1$s and PackageName: %2$s", contextPackageName, packageName));
                        return;
                    }

                    Resources resources = null;
                    if (!contextPackageName.equals(packageName)) {
                        try {
                            Context appContext = context.createPackageContext(packageName, 0);
                            if (appContext == null){
                                DebugTool.logError(TAG, "Failed to create context with the given package name");
                                return;
                            }
                            resources = appContext.getResources();
                        } catch (PackageManager.NameNotFoundException e) {
                            DebugTool.logError(TAG, "Failed to create context with the given package name: " + e.getMessage());
                        }
                    } else {
                        resources = context.getResources();
                    }

                    if (resources != null) {
                        try {
                            XmlResourceParser parser = resources.getXml(metadata.getInt(SDL_OEM_VEHICLE_TYPE_METADATA));
                            this.supportedVehicles = deserializeSupportedVehicles(parser);
                        } catch (Resources.NotFoundException ex) {
                            DebugTool.logError(TAG, "Failed to find resource: " + ex.getMessage());
                        }
                    }
                }
            } else {
                DebugTool.logWarning(TAG, packageName + " has not supplied metadata with their router service!");
            }
        }

        if (packageInfo != null) {
            this.lastUpdateTime = packageInfo.lastUpdateTime;
            if (this.lastUpdateTime <= 0) {
                this.lastUpdateTime = packageInfo.firstInstallTime;
            }
        } else {
            this.lastUpdateTime = 0;
        }
    }

    public int getRouterServiceVersion() {
        return routerServiceVersion;
    }

    public boolean isCustomRouterService() {
        return isCustomRouterService;
    }


    public ComponentName getRouterServiceComponentName() {
        return routerServiceComponentName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-------- Sdl App Info ------\n");

        builder.append("Package Name: ");
        builder.append(packageName);

        builder.append("\nRouter Service: ");
        builder.append(this.routerServiceComponentName.getClassName());

        builder.append("\nRouter Service Version: ");
        builder.append(this.routerServiceVersion);

        builder.append("\nCustom Router Service?: ");
        builder.append(this.isCustomRouterService);

        builder.append("\nLast updated: ");
        builder.append(this.lastUpdateTime);

        builder.append("\nVehicle make list: ");
        builder.append(this.supportedVehicles);

        builder.append("\n-------- Sdl App Info End------");

        return builder.toString();
    }

    /**
     * Retrieves the list of vehicle types that are set in the xml file.
     *
     * @param parser The xml parsing interface for the vehicle types xml file.
     * @return The list of vehicle types.
     */
    public static List<VehicleType> deserializeSupportedVehicles(XmlResourceParser parser) {
        List<VehicleType> vehicleMakesList = new ArrayList<>();
        if (parser == null) {
            return vehicleMakesList;
        }
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    if (tagName != null && tagName.equalsIgnoreCase("vehicle-type")) {
                        VehicleType vehicleMake = new VehicleType();
                        String make = parser.getAttributeValue(null, "make");
                        if (make != null) {
                            vehicleMake.setMake(make);
                            String model = parser.getAttributeValue(null, "model");
                            String modelYear = parser.getAttributeValue(null, "modelYear");
                            String trim = parser.getAttributeValue(null, "trim");

                            if (model == null && modelYear == null && trim == null) {
                                vehicleMakesList.add(vehicleMake);
                            } else if (model != null){
                                vehicleMake.setModel(model);
                                if (modelYear != null) {
                                    vehicleMake.setModelYear(modelYear);
                                }
                                if (trim != null) {
                                    vehicleMake.setTrim(trim);
                                }
                                vehicleMakesList.add(vehicleMake);
                            }
                        }
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            DebugTool.logError(TAG, "Failed to parse xml file", e);
        } catch (IOException e) {
            DebugTool.logError(TAG, "Failed to find next element in the xml file", e);
        }
        return vehicleMakesList;
    }

    /**
     * Check to see if a vehicle type is supported.
     *
     * @param supportedVehicleList the list of supported vehicle types.
     * @param connectedVehicle the vehicle type to check.
     * @return true if vehicle type is supported.
     */
    public static boolean checkIfVehicleSupported(List<VehicleType> supportedVehicleList, VehicleType connectedVehicle) {
        if (supportedVehicleList == null || supportedVehicleList.isEmpty() || connectedVehicle == null || connectedVehicle.getStore() == null|| connectedVehicle.getStore().isEmpty()) {
            return true;
        }
        if (supportedVehicleList.contains(connectedVehicle)) {
            return true;
        }
        for (VehicleType supportedVehicle: supportedVehicleList) {
            boolean areVehicleMakesEqual = CompareUtils.areStringsEqual(supportedVehicle.getMake(), connectedVehicle.getMake(), true, false);
            if (areVehicleMakesEqual) {
                String supportedVehicleModel = supportedVehicle.getModel();
                String connectedVehicleModel = connectedVehicle.getModel();
                if (supportedVehicleModel != null && connectedVehicleModel != null) {
                    if (connectedVehicleModel.equalsIgnoreCase(supportedVehicleModel)) {
                        boolean ret = true;
                        String supportedVehicleModelYear = supportedVehicle.getModelYear();
                        String connectedVehicleModelYear = connectedVehicle.getModelYear();
                        if (supportedVehicleModelYear != null && connectedVehicleModelYear != null) {
                            ret = connectedVehicleModelYear.equalsIgnoreCase(supportedVehicleModelYear);
                        }
                        String supportedVehicleTrim = supportedVehicle.getTrim();
                        String connectedVehicleTrim = connectedVehicle.getTrim();
                        if (supportedVehicleTrim != null && connectedVehicleTrim != null) {
                            ret &= connectedVehicleTrim.equalsIgnoreCase(supportedVehicleTrim);
                        }
                        if (ret) {
                            // We found matches and return or continue iteration otherwise
                            return true;
                        }
                    }
                } else {
                    // Return true if only make is defined and it matches
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets app's supported vehicle types.
     *
     * @return List<VehicleType> a list of supported vehicle types.
     */
    public List<VehicleType> getSupportedVehicles() {
        return supportedVehicles;
    }

    /**
     * This comparator will sort a list to find the best router service to start out of the known SDL enabled apps
     */
    public static class BestRouterComparator implements Comparator<SdlAppInfo> {

        @Override
        public int compare(SdlAppInfo one, SdlAppInfo two) {
            if (one != null) {
                if (two != null) {
                    if (one.isCustomRouterService) {
                        if (two.isCustomRouterService) {
                            return 0;
                        } else {
                            return 1;
                        }
                    } else if (two.isCustomRouterService) {
                        return -1;

                    }//else, do nothing. Move to version check

                    int versionCompare = two.routerServiceVersion - one.routerServiceVersion;

                    if (versionCompare == 0) { //Versions are equal so lets use the one that has been updated most recently
                        long updateTime = two.lastUpdateTime - one.lastUpdateTime;
                        if (updateTime == 0) {
                            //This is arbitrary, but we want to ensure all lists are sorted in the same order
                            return one.routerServiceComponentName.getPackageName().compareTo(two.routerServiceComponentName.getPackageName());
                        } else {
                            return (updateTime < 0 ? -1 : 1);
                        }
                    } else {
                        return (versionCompare < 0 ? -1 : 1);
                    }

                } else {
                    return -1;
                }
            } else {
                if (two != null) {
                    return 1;
                }
            }
            return 0;
        }


    }
}
