package com.smartdevicelink.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SdlPermissionManager {

    private static SdlPermissionManager mInstance;

    private SdlPermissionSet mSdlPermissionSet;

    private ArrayList<ListenerWithFilter> mListeners;

    private SdlPermissionManager(){

        mSdlPermissionSet = SdlPermissionSet.obtain();

        mListeners = new ArrayList<>();
    }

    public synchronized static SdlPermissionManager getInstance(){
        if(mInstance == null){
            mInstance = new SdlPermissionManager();
        }
        return mInstance;
    }

    /**
     * This method returns true if the given single permission is available at ANY {@link HMILevel}.
     * For specific HMILevels use {@link #isPermissionAvailable(SdlPermission, HMILevel)}.
     * @param permission The {@link SdlPermission} to check.
     * @return boolean indicating if the given SdlPermission is available.
     */
    public synchronized boolean isPermissionAvailable(@NonNull SdlPermission permission){

        HMILevel[] hmiLevels = HMILevel.values();

        for(int i = 0; i < hmiLevels.length; i++){
            if(isPermissionAvailable(permission, hmiLevels[i])){
                return true;
            }
        }

        return false;

    }

    /**
     * This method returns true if the given single permission is available at the given {@link HMILevel}.
     * @param permission {@link SdlPermission} to check.
     * @param hmi {@link HMILevel} to check.
     * @return boolean indication if the given SdlPermission is available at the given HMILevel
     */
    public synchronized boolean isPermissionAvailable(@NonNull SdlPermission permission, @NonNull HMILevel hmi){
        return mSdlPermissionSet.permissions.get(hmi.ordinal()).contains(permission);
    }

    public synchronized SdlPermissionEvent addListener(SdlPermissionListener listener, SdlPermissionSet filter){
        mListeners.add(new ListenerWithFilter(listener, filter, mode));
        return null;
    }

    public synchronized void onPermissionChange(OnPermissionsChange onPermissionsChange){
        List<PermissionItem> permissionItems = onPermissionsChange.getPermissionItem();

        SdlPermissionSet newPermissions = SdlPermissionSet.obtain();

        for(PermissionItem pi: permissionItems){
            String rpcName = pi.getRpcName();
            SdlPermission permission = SdlPermission.valueOf(rpcName);
            List<HMILevel> hmiLevels = pi.getHMIPermissions().getAllowed();

            for(HMILevel level: hmiLevels){
                newPermissions.permissions.get(level.ordinal()).add(permission);

                switch(permission){
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

        SdlPermissionSet changed = SdlPermissionSet.symetricDifference(mSdlPermissionSet, newPermissions);

        mSdlPermissionSet.recycle();
        mSdlPermissionSet = newPermissions;

        for(ListenerWithFilter lwf: mListeners){
            switch(lwf.mode){
                case MATCH_ALL:
                    if(changed.containsAny(lwf.filter) && mSdlPermissionSet.containsAll(lwf.filter)){
                        lwf.listener.onPermissionChanged();
                    }
                    break;
                case MATCH_EXACT:
                    break;
                case MATCH_ANY:
                    break;
            }
        }

    }

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
        final SdlPermissionSet filter;
        final ListenerMode mode;

        ListenerWithFilter(SdlPermissionListener listener, SdlPermissionSet filter, ListenerMode mode){
            this.listener = listener;
            this.filter = filter;
            this.mode = mode;
        }
    }

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
    };

}
