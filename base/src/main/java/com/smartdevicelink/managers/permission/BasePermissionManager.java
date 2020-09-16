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
package com.smartdevicelink.managers.permission;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 PermissionManager gives the developer information about what permissions are permitted in specific HMI level
 and helps developers setup listeners to be called when specific permissions become allowed.<br>

 This should be used through the {@link com.smartdevicelink.managers.SdlManager} and not be instantiated by itself
**/

abstract class BasePermissionManager extends BaseSubManager{

    private HMILevel currentHMILevel;
    private Map<FunctionID, PermissionItem> currentPermissionItems;
    private OnRPCNotificationListener onHMIStatusListener, onPermissionsChangeListener;
    private final List<PermissionFilter> filters;
    private final Set<String> encryptionRequiredRPCs = new HashSet<>();

    // Permission groups status constants
    @IntDef({PERMISSION_GROUP_STATUS_ALLOWED, PERMISSION_GROUP_STATUS_DISALLOWED,
            PERMISSION_GROUP_STATUS_MIXED, PERMISSION_GROUP_STATUS_UNKNOWN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionGroupStatus {}
    public static final int PERMISSION_GROUP_STATUS_ALLOWED = 0;    // Every permission in the group is currently allowed
    public static final int PERMISSION_GROUP_STATUS_DISALLOWED = 1; // Every permission in the group is currently disallowed
    public static final int PERMISSION_GROUP_STATUS_MIXED = 2;      // Some permissions in the group are allowed and some disallowed
    public static final int PERMISSION_GROUP_STATUS_UNKNOWN = 3;    // The current status of the group is unknown

    // Permission groups type constants
    @IntDef({PERMISSION_GROUP_TYPE_ALL_ALLOWED, PERMISSION_GROUP_TYPE_ANY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionGroupType {}
    public static final int PERMISSION_GROUP_TYPE_ALL_ALLOWED = 0;  // Be notified when all of the permission in the group are allowed, or, when they all stop being allowed in some sense, that is, when they were all allowed, and now they are not.
    public static final int PERMISSION_GROUP_TYPE_ANY = 1;          // Be notified when any change in availability occurs among the group

    /**
     * Creates a new instance of the PermissionManager
     * @param internalInterface an instance of the ISdl interface that can be used for common SDL operations (sendRpc, addRpcListener, etc)
     */
    BasePermissionManager(@NonNull ISdl internalInterface){
        super(internalInterface);
        this.currentPermissionItems = new HashMap<>();
        this.filters = new ArrayList<>();

        // Set PermissionManager's OnHMIStatusListener to keep currentHMILevel updated and call developer's listeners if needed
        onHMIStatusListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnHMIStatus onHMIStatus = (OnHMIStatus)notification;
                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                    return;
                }
                HMILevel previousHMILevel = currentHMILevel;
                currentHMILevel = onHMIStatus.getHmiLevel();
                checkState();
                notifyListeners(currentPermissionItems, previousHMILevel, currentPermissionItems, currentHMILevel);
            }
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);

        // Set PermissionManager's PermissionsChangeListener to keep currentPermissionItems updated and call developer's listeners if needed
        onPermissionsChangeListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                List<PermissionItem> permissionItems = ((OnPermissionsChange)notification).getPermissionItem();
                Map<FunctionID, PermissionItem> previousPermissionItems = currentPermissionItems;
                Boolean requireEncryptionAppLevel = ((OnPermissionsChange) notification).getRequireEncryption();
                encryptionRequiredRPCs.clear();
                currentPermissionItems = new HashMap<>();
                if (permissionItems != null && !permissionItems.isEmpty()) {
                    for (PermissionItem permissionItem : permissionItems) {
                        FunctionID functionID = FunctionID.getEnumForString(permissionItem.getRpcName());
                        if (functionID != null) {
                            currentPermissionItems.put(functionID, permissionItem);
                        }
                        if (Boolean.TRUE.equals(permissionItem.getRequireEncryption())) {
                            if (requireEncryptionAppLevel == null || requireEncryptionAppLevel) {
                                String rpcName = permissionItem.getRpcName();
                                if (rpcName != null) {
                                    encryptionRequiredRPCs.add(rpcName);
                                }
                            }
                        }
                    }
                }
                notifyListeners(previousPermissionItems, currentHMILevel, currentPermissionItems, currentHMILevel);
                previousPermissionItems.clear();
            }
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_PERMISSIONS_CHANGE, onPermissionsChangeListener);
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void start(CompletionListener listener) {
        checkState();
        super.start(listener);
    }

    /**
     * Checks if an RPC requires encryption
     *
     * @param rpcName the rpc name (FunctionID) to check
     * @return true if the given RPC requires encryption; false, otherwise
     */
    public boolean getRPCRequiresEncryption(@NonNull FunctionID rpcName) {
        return encryptionRequiredRPCs.contains(rpcName.toString());
    }

    /**
     * Gets the encryption requirement
     * @return true if encryption is required; false otherwise
     */
    public boolean getRequiresEncryption() {
        return !encryptionRequiredRPCs.isEmpty();
    }

    private synchronized void checkState(){
        if(this.getState() == SETTING_UP && currentHMILevel != null){
            transitionToState(READY);
        }
    }

    /**
     * Go over all developer's listeners and call them if needed because of HMI level change or permission items change
     */
    private void notifyListeners(Map<FunctionID, PermissionItem> previousPermissionItems, HMILevel previousHmiLevel, Map<FunctionID, PermissionItem> currentPermissionItems, HMILevel currentHMILevel){
        for (PermissionFilter filter : filters) {
            boolean anyChange = false;
            boolean allWereAllowed = true;
            boolean allNowAllowed = true;
            for (PermissionElement permissionElement : filter.getPermissionElements()) {
                // If at any point this condition is satisfied, then we don't need to continue
                if (anyChange && !allWereAllowed && !allNowAllowed){
                    break;
                }
                boolean rpcWasAllowed = isRPCAllowed(permissionElement.getRPCName(), previousPermissionItems, previousHmiLevel);
                boolean rpcNowAllowed = isRPCAllowed(permissionElement.getRPCName(), currentPermissionItems, currentHMILevel);
                if (rpcWasAllowed != rpcNowAllowed){
                    anyChange = true;
                }
                if (!rpcWasAllowed){
                    allWereAllowed = false;
                }
                if (!rpcNowAllowed){
                    allNowAllowed = false;
                }
                if (permissionElement.getParameters() != null && permissionElement.getParameters().size() > 0) {
                    for (String parameter : permissionElement.getParameters()) {
                        boolean parameterWasAllowed = isPermissionParameterAllowed(permissionElement.getRPCName(), parameter, previousPermissionItems, previousHmiLevel);
                        boolean parameterNowAllowed = isPermissionParameterAllowed(permissionElement.getRPCName(), parameter, currentPermissionItems, currentHMILevel);
                        if (parameterWasAllowed != parameterNowAllowed){
                            anyChange = true;
                        }
                        if (!parameterWasAllowed){
                            allWereAllowed = false;
                        }
                        if (!parameterNowAllowed){
                            allNowAllowed = false;
                        }
                    }
                }
            }
            if (filter.getGroupType() == PERMISSION_GROUP_TYPE_ALL_ALLOWED && anyChange && (allWereAllowed || allNowAllowed)){
                notifyListener(filter);
            } else if (filter.getGroupType() == PERMISSION_GROUP_TYPE_ANY && anyChange){
                notifyListener(filter);
            }
        }
    }

    /**
     * Determine if an individual RPC is allowed
     * @param rpcName FunctionID value that represents the name of the RPC
     * @param permissionItems Map containing HMI and parameter permissions for a specific RPC
     * @param hmiLevel If the RPC is allowed at that HMI Level. Ex: None or Full
     * @return boolean represents whether the RPC is allowed or not
     */
    private boolean isRPCAllowed(@NonNull FunctionID rpcName, Map<FunctionID, PermissionItem> permissionItems, HMILevel hmiLevel){
        PermissionItem permissionItem = permissionItems.get(rpcName);
        if (hmiLevel == null || permissionItem == null || permissionItem.getHMIPermissions() == null || permissionItem.getHMIPermissions().getAllowed() == null){
            return false;
        } else if (permissionItem.getHMIPermissions().getUserDisallowed() != null){
            return permissionItem.getHMIPermissions().getAllowed().contains(hmiLevel) && !permissionItem.getHMIPermissions().getUserDisallowed().contains(hmiLevel);
        } else {
            return permissionItem.getHMIPermissions().getAllowed().contains(hmiLevel);
        }
    }

    /**
     * Determine if an individual RPC is allowed for the current permission items and HMI level
     * @param rpcName rpcName FunctionID value that represents the name of the RPC
     * @return boolean represents whether the RPC is allowed or not
     */
    @SuppressWarnings("WeakerAccess")
    public boolean isRPCAllowed(@NonNull FunctionID rpcName){
        return isRPCAllowed(rpcName, currentPermissionItems, currentHMILevel);
    }

    /**
     * Determine if an individual permission parameter is allowed
     * @param rpcName FunctionID value that represents the name of the RPC
     * @param parameter String value that represents a parameter for the RPC. Ex: "rpm" or "speed" for GetVehicleData
     * @param permissionItems Map containing HMI and parameter permissions for a specific RPC
     * @param hmiLevel If the RPC is allowed at that HMI Level. Ex: None or Full
     * @return boolean represents whether the permission parameter is allowed or not
     */
    private boolean isPermissionParameterAllowed(@NonNull FunctionID rpcName, @NonNull String parameter, Map<FunctionID, PermissionItem> permissionItems, HMILevel hmiLevel){
        PermissionItem permissionItem = permissionItems.get(rpcName);
        if (permissionItem == null || !isRPCAllowed(rpcName, permissionItems, hmiLevel) || permissionItem.getParameterPermissions() == null || permissionItem.getParameterPermissions().getAllowed() == null){
            return false;
        } else if (permissionItem.getParameterPermissions().getUserDisallowed() != null){
            return permissionItem.getParameterPermissions().getAllowed().contains(parameter) && !permissionItem.getParameterPermissions().getUserDisallowed().contains(parameter);
        } else {
            return permissionItem.getParameterPermissions().getAllowed().contains(parameter);
        }
    }

    /**
     * Determine if an individual permission parameter is allowed for current permission items and current HMI level
     * @param rpcName FunctionID value that represents the name of the RPC
     * @param parameter String value that represents a parameter for the RPC
     * @return boolean represents whether the permission parameter is allowed or not
     */
    @SuppressWarnings("WeakerAccess")
    public boolean isPermissionParameterAllowed(@NonNull FunctionID rpcName, @NonNull String parameter){
        return isPermissionParameterAllowed(rpcName, parameter, currentPermissionItems, currentHMILevel);
    }

    /**
     * Clean up everything after the manager is no longer needed
     */
    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void dispose(){
        super.dispose();

        // Remove onHMIStatusListener
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);
        onHMIStatusListener = null;

        // Remove onPermissionsChangeListener
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onPermissionsChangeListener);
        onPermissionsChangeListener = null;

        // Remove developer's listeners
        filters.clear();
    }

    /**
     * Determine if a group of permissions is allowed for the current HMI level
     * @param permissionElements list of PermissionElement that represents the RPC names and their parameters
     * @return PermissionGroupStatus int value that gives an overall view whether the permissions are allowed or not
     * @see PermissionGroupStatus
     */
    @SuppressWarnings("WeakerAccess")
    public @PermissionGroupStatus int getGroupStatusOfPermissions(@NonNull List<PermissionElement> permissionElements){
        if (currentHMILevel == null){
            return PERMISSION_GROUP_STATUS_UNKNOWN;
        }

        boolean hasAllowed = false;
        boolean hasDisallowed = false;

        for (PermissionElement permissionElement : permissionElements) {
            // If at any point, we have both allowed and disallowed permissions, return the mixed result
            if (hasAllowed && hasDisallowed) {
                return PERMISSION_GROUP_STATUS_MIXED;
            }

            if (permissionElement == null){
                continue;
            } else if (!isRPCAllowed(permissionElement.getRPCName())){
                hasDisallowed = true;
            } else {
                if (permissionElement.getParameters() == null || permissionElement.getParameters().size() == 0){
                    hasAllowed = true;
                } else {
                    for (String permissionParameter : permissionElement.getParameters()) {
                        if (isPermissionParameterAllowed(permissionElement.getRPCName(), permissionParameter)) {
                            hasAllowed = true;
                        } else {
                            hasDisallowed = true;
                        }
                    }
                }
            }
        }

        if (!hasAllowed && !hasDisallowed){
            return PERMISSION_GROUP_STATUS_ALLOWED;
        } else if (hasAllowed && hasDisallowed) {
            return PERMISSION_GROUP_STATUS_MIXED;
        } else if (hasAllowed) {
            return PERMISSION_GROUP_STATUS_ALLOWED;
        } else{
            return PERMISSION_GROUP_STATUS_DISALLOWED;
        }
    }

    /**
     * Determine if a group of permissions is allowed for the current HMI level
     * This method is similar to getGroupStatusOfPermissions() but returns more detailed result about each individual permission
     * @param permissionElements list of PermissionElement that represents the RPC names and their parameters
     * @return a map with keys that are the passed in RPC names specifying if that RPC and its parameter permissions are currently allowed for the current HMI level
     */
    @SuppressWarnings("WeakerAccess")
    public Map <FunctionID, PermissionStatus> getStatusOfPermissions(@NonNull List<PermissionElement> permissionElements){
        Map<FunctionID, PermissionStatus> statusOfPermissions = new HashMap<>();
        for (PermissionElement permissionElement : permissionElements) {
            if (permissionElement == null){
                continue;
            }
            Map<String, Boolean> allowedParameters = null;
            if (permissionElement.getParameters() != null && permissionElement.getParameters().size() > 0) {
                allowedParameters = new HashMap<>();
                for (String permissionParameter : permissionElement.getParameters()) {
                    allowedParameters.put(permissionParameter, isPermissionParameterAllowed(permissionElement.getRPCName(), permissionParameter));
                }
            }
            PermissionStatus permissionStatus = new PermissionStatus(permissionElement.getRPCName(), isRPCAllowed(permissionElement.getRPCName()), allowedParameters);
            statusOfPermissions.put(permissionElement.getRPCName(), permissionStatus);
        }
        return statusOfPermissions;
    }

    /**
     * Call the listener for a specific filter
     * @param filter the permission filter to cal
     */
    private void notifyListener(@NonNull PermissionFilter filter){
        int permissionGroupStatus = getGroupStatusOfPermissions(filter.getPermissionElements());
        Map <FunctionID, PermissionStatus> allowedPermissions = getStatusOfPermissions(filter.getPermissionElements());
        filter.getListener().onPermissionsChange(allowedPermissions, permissionGroupStatus);
    }

    /**
     * Add a listener to be called when there is permissions change.
     * When the listener is added it will be called immediately with the current permission values if the values comply with with the passed groupType
     * @param permissionElements list of PermissionElement that represents the RPC names and their parameters
     * @param groupType PermissionGroupType int value represents whether we need the listener to be called when there is any permissions change or only when all permission become allowed
     * @param listener OnPermissionChangeListener interface
     * @return unique uuid number for the listener. It can be used to remove the listener later.
     */
    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
    public UUID addListener(@NonNull List<PermissionElement> permissionElements, @PermissionGroupType int groupType, @NonNull OnPermissionChangeListener listener){
        PermissionFilter filter = new PermissionFilter(null, permissionElements, groupType, listener);
        filters.add(filter);
        if (groupType == PERMISSION_GROUP_TYPE_ANY || (groupType == PERMISSION_GROUP_TYPE_ALL_ALLOWED && getGroupStatusOfPermissions(permissionElements) == PERMISSION_GROUP_STATUS_ALLOWED)) {
            notifyListener(filter);
        }
        return filter.getIdentifier();
    }

    /**
     * Removes specific listener
     * @param listenerId the id of the listener
     */
    @SuppressWarnings("WeakerAccess")
    public void removeListener(@NonNull UUID listenerId){
        for (PermissionFilter filter : filters) {
            if (filter.getIdentifier().equals(listenerId)) {
                filters.remove(filter);
                break;
            }
        }
    }

}
