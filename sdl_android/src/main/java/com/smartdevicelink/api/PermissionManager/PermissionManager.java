package com.smartdevicelink.api.PermissionManager;

import android.support.annotation.IntDef;

import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 This manager can be accessed through SdlManager.
 It gives the developer information about what permissions are permitted in specific HMI level
 and helps developers setup listeners to be called when specific permissions become allowed.
**/

 public class PermissionManager extends BaseSubManager{

    private HMILevel currentHMILevel;
    private Map<FunctionID, PermissionItem> permissions;
    private OnRPCNotificationListener onHMIStatusListener, onPermissionsChangeListener;
    private List<PermissionFilter> filters;

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


    public PermissionManager(ISdl internalInterface){
        super(internalInterface);
        this.currentHMILevel = null;
        this.permissions = new HashMap<>();
        this.filters = new ArrayList<>();
        this.onHMIStatusListener = null;
        this.onPermissionsChangeListener = null;
        setupInternalListeners();
        transitionToState(ManagerState.READY);
    }

    // Setup the PermissionManager's internal listeners
    private void setupInternalListeners() {
        // Set PermissionManager's OnHMIStatusListener to keep currentHMILevel updated
        onHMIStatusListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                currentHMILevel = ((OnHMIStatus)notification).getHmiLevel();
            }
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);

        // Set PermissionManager's PermissionsChangeListener to keep this.permissions updated
        onPermissionsChangeListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                List<PermissionItem> permissionItems = ((OnPermissionsChange)notification).getPermissionItem();

                permissions.clear();
                for (PermissionItem permissionItem : permissionItems) {
                    permissions.put(FunctionID.getEnumForString(permissionItem.getRpcName()), permissionItem);
                }
            }
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_PERMISSIONS_CHANGE, onPermissionsChangeListener);
    }

    // Determine if an individual RPC is allowed for the current HMI level
    public boolean isRPCAllowed(FunctionID rpcName){
        if (permissions.get(rpcName) == null || permissions.get(rpcName).getHMIPermissions().getAllowed() == null || currentHMILevel == null){
            return false;
        }
        return permissions.get(rpcName).getHMIPermissions().getAllowed().contains(currentHMILevel);
    }

    public boolean isPermissionParameterAllowed(FunctionID rpcName, String parameter){
       return false;
    }

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

    // Determine if a group of permissions are allowed for the current HMI level
    public @PermissionGroupStatus int getGroupStatusOfPermissions(List<PermissionElement> permissionElements){
        return PERMISSION_GROUP_STATUS_ALLOWED;
    }

    // Retrieve a map with keys that are the passed in RPC names specifying if that RPC
    // and its parameter permissions are currently allowed for the current HMI level
    public Map <FunctionID, PermissionStatus> getStatusOfPermissions(List<PermissionElement> permissionElements){
        return null;
    }

    private void callFilterListener(PermissionFilter filter){
        int permissionGroupStatus = getGroupStatusOfPermissions(filter.getPermissionElements());
        Map <FunctionID, PermissionStatus> allowedPermissions = getStatusOfPermissions(filter.getPermissionElements());
        filter.getListener().onPermissionsChange(allowedPermissions, permissionGroupStatus);
    }

    public UUID addListener(List<PermissionElement> permissionElements, @PermissionGroupType int groupType, OnPermissionChangeListener listener){
        PermissionFilter filter = new PermissionFilter(null, permissionElements, groupType, listener);
        filters.add(filter);
        return filter.getIdentifier();
    }

    // Removes specific listener
    public void removeListener(UUID listenerId){
        for (PermissionFilter filter : filters) {
            if (filter.getIdentifier().equals(listenerId)) {
                filters.remove(filter);
                break;
            }
        }
    }

    public interface OnPermissionChangeListener {
        void onPermissionsChange(Map <FunctionID, PermissionStatus> allowedPermissions, @PermissionGroupStatus int permissionGroupStatus);
    }

}
