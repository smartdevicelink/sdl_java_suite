package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public class SdlPermissionManager {

    private static final String TAG = SdlPermissionManager.class.getSimpleName();

    private SdlPermissionSet mSdlPermissionSet;

    private ArrayList<ListenerWithFilter> mListeners;

    private final Object PERMISSION_LOCK = new Object();

    public SdlPermissionManager(){
        mSdlPermissionSet = SdlPermissionSet.obtain();

        mListeners = new ArrayList<>();
    }

    /**
     * This method returns true if the given single permission is available at ANY {@link HMILevel}.
     * For specific HMILevels use {@link #isPermissionAvailable(SdlPermission, HMILevel)}.
     * @param permission The {@link SdlPermission} to check.
     * @return boolean indicating if the given SdlPermission is available.
     */
    public boolean isPermissionAvailable(@NonNull SdlPermission permission){
        synchronized (PERMISSION_LOCK) {
            HMILevel[] hmiLevels = HMILevel.values();

            for (int i = 0; i < hmiLevels.length; i++) {
                if (isPermissionAvailable(permission, hmiLevels[i])) {
                    return true;
                }
            }

            return false;
        }

    }

    public OnRPCNotificationListener getPermissionChangeListener(){
        return permissionChangeListener;
    }

    /**
     * This method returns true if the given single permission is available at the given {@link HMILevel}.
     * @param permission {@link SdlPermission} to check.
     * @param hmi {@link HMILevel} to check.
     * @return boolean indication if the given SdlPermission is available at the given HMILevel
     */
    public boolean isPermissionAvailable(@NonNull SdlPermission permission, @NonNull HMILevel hmi){
        synchronized (PERMISSION_LOCK) {
            return mSdlPermissionSet.permissions.get(hmi.ordinal()).contains(permission);
        }
    }

    /**
     * Method to add a listener that will be called when the conditions specified by the provided
     * {@link SdlPermissionFilter} and {@link com.smartdevicelink.api.permission.SdlPermissionManager.ListenerMode}
     * @param listener Implementation of {@link SdlPermissionFilter} that will receive callbacks
     *                 when permissions requested by the filter change according to the supplied
     *                 ListenerMode.
     * @param filter SdlPermissionFilter that contains a set of permissions that should be reported
     *               to the listener. The listener will ONLY receive information on permissions
     *               included in the filter.
     * @param mode The {@link com.smartdevicelink.api.permission.SdlPermissionManager.ListenerMode} that
     *             defines when the listener should be called based on changes in the permissions
     *             specified by the SdlPermissionFilter.
     * @return Returns an {@link SdlPermissionEvent} containing the current state of the permissions
     * when the listener is added.
     */
    @NonNull
    public SdlPermissionEvent addListener(@NonNull SdlPermissionListener listener,
                                                       @NonNull SdlPermissionFilter filter,
                                                       @Nullable ListenerMode mode){
        synchronized (PERMISSION_LOCK) {
            mListeners.add(new ListenerWithFilter(listener, filter, mode));
            return new SdlPermissionEvent(SdlPermissionSet.copy(mSdlPermissionSet));
        }
    }

    /**
     * Convenience method that is equivalent to calling
     * {@link SdlPermissionManager#addListener(SdlPermissionListener, SdlPermissionFilter, ListenerMode)}
     * with {@link com.smartdevicelink.api.permission.SdlPermissionManager.ListenerMode}.
     * @param listener Implementation of {@link SdlPermissionFilter} that will receive callbacks
     *                 when permissions requested by the filter change according to the supplied
     *                 ListenerMode.
     * @param filter SdlPermissionFilter that contains a set of permissions that should be reported
     *               to the listener. The listener will ONLY receive information on permissions
     *               included in the filter.
     * @return Returns an {@link SdlPermissionEvent} containing the current state of the permissions
     * when the listener is added.
     */
    @NonNull
    public SdlPermissionEvent addListener(@NonNull SdlPermissionListener listener,
                                                       @NonNull SdlPermissionFilter filter){
        synchronized (PERMISSION_LOCK) {
            return addListener(listener, filter, ListenerMode.MATCH_ANY);
        }
    }

    private OnRPCNotificationListener permissionChangeListener = new OnRPCNotificationListener() {
        @Override
        public void onNotified(RPCNotification notification) {
            synchronized (PERMISSION_LOCK) {
                Log.d(TAG, "onPermissionChange");
                OnPermissionsChange onPermissionsChange = (OnPermissionsChange) notification;

                List<PermissionItem> permissionItems = onPermissionsChange.getPermissionItem();

                if(permissionItems == null){
                    return;
                }

                SdlPermissionSet newPermissions = SdlPermissionSet.obtain();

                for (PermissionItem pi : permissionItems) {
                    String rpcName = pi.getRpcName();
                    SdlPermission permission = SdlPermission.valueOf(rpcName);
                    List<HMILevel> hmiLevels = pi.getHMIPermissions().getAllowed();

                    if(hmiLevels != null) {
                        for (HMILevel level : hmiLevels) {
                            newPermissions.permissions.get(level.ordinal()).add(permission);

                            switch (permission) {
                                case GetVehicleData:
                                    addVdataRpcPermission("Get", pi, level, newPermissions);
                                    break;
                                case SubscribeVehicleData:
                                    addVdataRpcPermission("Subscribe", pi, level, newPermissions);
                                    break;
                                case OnVehicleData:
                                    addVdataRpcPermission("On", pi, level, newPermissions);
                                    break;
                                case UnsubscribeVehicleData:
                                    addVdataRpcPermission("Unsubscribe", pi, level, newPermissions);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }

                SdlPermissionSet changed = SdlPermissionSet.symmetricDifference(mSdlPermissionSet, newPermissions);

                mSdlPermissionSet.recycle();
                mSdlPermissionSet = newPermissions;

                for (ListenerWithFilter lwf : mListeners) {
                    switch (lwf.mode) {
                        case MATCH_ALL:
                            if (changed.containsAny(lwf.filter.permissionSet)
                                    && mSdlPermissionSet.containsAll(lwf.filter.permissionSet)) {
                                lwf.listener.onPermissionChanged(new SdlPermissionEvent(
                                        SdlPermissionSet.intersect(mSdlPermissionSet, lwf.filter.permissionSet)));
                            }
                            break;
                        case MATCH_EXACT:
                            if (changed.containsAny(lwf.filter.permissionSet)
                                    && mSdlPermissionSet.containsExactlyAll(lwf.filter.permissionSet)) {
                                lwf.listener.onPermissionChanged(new SdlPermissionEvent(
                                        SdlPermissionSet.intersect(mSdlPermissionSet, lwf.filter.permissionSet)));
                            }
                            break;
                        case MATCH_ANY:
                            if (changed.containsAny(lwf.filter.permissionSet)) {
                                lwf.listener.onPermissionChanged(new SdlPermissionEvent(
                                        SdlPermissionSet.intersect(mSdlPermissionSet, lwf.filter.permissionSet)));
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    };

    private void addVdataRpcPermission(String prefix, PermissionItem pi,
                                       HMILevel hmi, SdlPermissionSet permissionSet){
        List<String> allowedRpcs = pi.getParameterPermissions().getAllowed();
        for(String vdataRpc: allowedRpcs){
            char[] chars = vdataRpc.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            String permissionName = prefix + new String(chars);
            SdlPermission permission = SdlPermission.valueOf(permissionName);
            permissionSet.permissions.get(hmi.ordinal()).add(permission);
        }
    }

    private class ListenerWithFilter{
        final SdlPermissionListener listener;
        final SdlPermissionFilter filter;
        final ListenerMode mode;

        ListenerWithFilter(SdlPermissionListener listener, SdlPermissionFilter filter, ListenerMode mode){
            this.listener = listener;
            this.filter = filter;
            this.mode = mode;
        }
    }

    /**
     * EnumSet containing all permissions related to vehicle data monitoring. Not recommended if
     * only a hand full of permissions are needed.
     */
    public static final EnumSet<SdlPermission> PERMISSION_SET_VEHICLE_DATA =
            EnumSet.range(SdlPermission.GetDTCs, SdlPermission.UnsubscribeWiperStatus);

    public enum ListenerMode{
        /**
         * This mode is for use with an {@link SdlPermissionFilter} that has been populated with
         * {@link SdlPermissionFilter#addPermission(SdlPermission)} and/or
         * {@link SdlPermissionFilter#addPermissions(Collection)} as it does not enforce
         * {@link HMILevel} constraints. Will only be called when ALL permissions are available in
         * ONE OR MORE HMI levels.
         */
        MATCH_ALL,

        /**
         * This mode is for use with an {@link SdlPermissionFilter} filter that has been populated with
         * {@link SdlPermissionFilter#addPermission(SdlPermission, HMILevel)} and/or
         * {@link SdlPermissionFilter#addPermissions(Collection, HMILevel)} as it does enforce
         * {@link HMILevel} constraints. Will only be called when ALL permissions are available in
         * ALL HMI levels specified.
         */
        MATCH_EXACT,

        /**
         * This mode will cause the {@link SdlPermissionListener} to be called whenever any of the permissions specified
         * by the provided {@link SdlPermissionFilter} change.
         */
        MATCH_ANY
    }

}
