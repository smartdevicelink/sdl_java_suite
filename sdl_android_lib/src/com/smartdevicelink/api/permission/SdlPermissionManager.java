package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class SdlPermissionManager {

    private static final String TAG = SdlPermissionManager.class.getSimpleName();

    private SdlPermissionSet mAllowedPermissionSet;

    private SdlPermissionSet mUserDisallowSet;

    private CopyOnWriteArraySet<ListenerWithFilter> mListeners;

    private ArrayList<ListenerWithFilter> mRemovedListener = new ArrayList<>();

    private boolean isPermissionEventIter = false;

    private HMILevel mCurrentHMILevel = HMILevel.HMI_NONE;

    private final Object PERMISSION_LOCK = new Object();

    public SdlPermissionManager(){
        mAllowedPermissionSet = SdlPermissionSet.obtain();
        mUserDisallowSet = SdlPermissionSet.obtain();
        mListeners = new CopyOnWriteArraySet<>();
    }

    /**
     * This method returns true if the given single permission is available now.
     * @param permission The {@link SdlPermission} to check.
     * @return boolean indicating if the given SdlPermission is available.
     */
    public boolean isPermissionAvailable(@NonNull SdlPermission permission){
        synchronized (PERMISSION_LOCK) {
            return mAllowedPermissionSet.permissions.get(mCurrentHMILevel.ordinal()).contains(permission);
        }
    }

    /**
     * This method returns true if the given single permission is disallowed by the user.
     * @param permission The {@link SdlPermission} to check.
     * @return boolean indicating if the given SdlPermission is user disallowed.
     */
    public boolean isPermissionUserDisallowed(@NonNull SdlPermission permission){
        synchronized (PERMISSION_LOCK) {
            return mUserDisallowSet.permissions.get(mCurrentHMILevel.ordinal()).contains(permission);
        }
    }


    /**
     * Method to add a listener that will be called when the conditions specified by the provided
     * {@link SdlPermissionFilter}
     * @param listener Implementation of {@link SdlPermissionFilter} that will receive callbacks
     *                 when permissions requested by the filter change.
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
            mListeners.add(new ListenerWithFilter(listener, filter));
            return generateSdlPermissionEvent(mAllowedPermissionSet, mUserDisallowSet,
                    filter, mCurrentHMILevel);
        }
    }

    /**
     * Removes listeners that use the provided listener from the {@link SdlPermissionManager}
     * @param listener The listener reference to be removed from the {@link SdlPermissionManager}
     * @return boolean indicating if any listeners were removed based on the listener provided
     */
    public boolean removeListener(@NonNull SdlPermissionListener listener){
        synchronized (PERMISSION_LOCK){
            boolean removedListener = false;
            ArrayList<ListenerWithFilter> removeListeners = new ArrayList<>();
            for (ListenerWithFilter lwf : mListeners) {
                SdlPermissionListener permissionListener;
                if ((permissionListener = cleanNullFromListenerList(lwf, removeListeners)) == null) {
                    continue;
                }
                if (permissionListener == listener) {
                    mListeners.remove(lwf);
                    removedListener = true;
                    if(isPermissionEventIter){
                        mRemovedListener.add(lwf);
                    }
                }
            }
            mListeners.removeAll(removeListeners);
            return removedListener;
        }
    }

    /**
     * Method to change the current HMI level if it has changed and to notify added {@link SdlPermissionListener}
     * of any changes in {@link SdlPermission} with the {@link HMILevel} transition.
     * @param hmiLevel The {@link HMILevel} to change to
     */
    public void onHmi(@NonNull HMILevel hmiLevel){
        synchronized (PERMISSION_LOCK) {
            if(hmiLevel!=mCurrentHMILevel) {
                ArrayList<ListenerWithFilter> nullListeners = new ArrayList<>();
                for (ListenerWithFilter lwf : mListeners) {
                    SdlPermissionListener permissionListener;
                    if ((permissionListener = cleanNullFromListenerList(lwf, nullListeners)) == null) {
                        continue;
                    }

                    //filter out the permissions that we are not interested in
                    SdlPermissionSet intersection = SdlPermissionSet.intersect(mAllowedPermissionSet, lwf.filter.permissionSet);
                    intersection = SdlPermissionSet.union(intersection,SdlPermissionSet.intersect(mUserDisallowSet, lwf.filter.permissionSet));
                    //see if there is any change in the EnumSet between the old HMI Level and the new HMI Level
                    if (intersection.checkForChangeBetweenHMILevels(mCurrentHMILevel, hmiLevel)) {
                        permissionListener.onPermissionChanged(generateSdlPermissionEvent(
                                mAllowedPermissionSet, mUserDisallowSet, lwf.filter, hmiLevel));
                    }
                }
                mListeners.removeAll(nullListeners);
                mCurrentHMILevel = hmiLevel;
            }
        }
    }

    public void onPermissionChange(OnPermissionsChange permissionsChange){
        synchronized (PERMISSION_LOCK) {
            Log.d(TAG, "onPermissionChange");

            List<PermissionItem> permissionItems = permissionsChange.getPermissionItem();

            if(permissionItems == null){
                return;
            }

            SdlPermissionSet newAllowedPermissions = SdlPermissionSet.obtain();
            SdlPermissionSet userDisallowPermissions = SdlPermissionSet.obtain();

            for (PermissionItem pi : permissionItems) {
                String rpcName = pi.getRpcName();
                SdlPermission permission;
                try {
                    permission = SdlPermission.valueOf(rpcName);
                } catch (IllegalArgumentException e){
                    Log.w(TAG, "RPC '" + rpcName + "' not supported");
                    Log.w(TAG, e);
                    continue;
                }
                List<HMILevel> hmiAllowedLevels = pi.getHMIPermissions().getAllowed();
                List<HMILevel> hmiUserDisallowLevels = pi.getHMIPermissions().getUserDisallowed();
                List<String> allowedParams = pi.getParameterPermissions().getAllowed();
                List<String> userDisallowedParams = pi.getParameterPermissions().getUserDisallowed();

                if(hmiAllowedLevels != null) {
                    for (HMILevel level : hmiAllowedLevels) {
                        newAllowedPermissions.permissions.get(level.ordinal()).add(permission);
                        if(allowedParams != null) {
                            parsePermissionParameters(permission,
                                    allowedParams, level, newAllowedPermissions);
                        }
                        if(userDisallowedParams != null){
                            parsePermissionParameters(permission,
                                    userDisallowedParams, level, userDisallowPermissions);
                        }
                    }

                }

                if(hmiUserDisallowLevels != null){
                    for (HMILevel level : hmiUserDisallowLevels) {
                        userDisallowPermissions.permissions.get(level.ordinal()).add(permission);
                        if(userDisallowedParams != null){
                            parsePermissionParameters(permission,
                                    userDisallowedParams, level, userDisallowPermissions);
                        }
                    }
                }
            }

            SdlPermissionSet changed = SdlPermissionSet.symmetricDifference(mAllowedPermissionSet,
                    newAllowedPermissions);
            changed = SdlPermissionSet.union(changed,SdlPermissionSet.symmetricDifference(
                    mUserDisallowSet, userDisallowPermissions));

            mAllowedPermissionSet.recycle();
            mUserDisallowSet.recycle();
            mAllowedPermissionSet = newAllowedPermissions;
            mUserDisallowSet = userDisallowPermissions;

            ArrayList<ListenerWithFilter> nullListeners = new ArrayList<>();
            for (ListenerWithFilter lwf : mListeners) {
                //denote here that if any listener gets removed while we are iterating, to keep note of it
                //and make sure we do not call it
                isPermissionEventIter = true;
                SdlPermissionListener permissionListener;
                //besides checking for a garbage collected listener, we check for if
                //the listener was removed from our current list
                if (((permissionListener = cleanNullFromListenerList(lwf, nullListeners)) == null)
                        || mRemovedListener.contains(lwf)) {
                    continue;
                }
                if (changed.containsAnyForHMILevel(lwf.filter.permissionSet, mCurrentHMILevel)) {
                    permissionListener.onPermissionChanged(
                            generateSdlPermissionEvent(mAllowedPermissionSet, mUserDisallowSet,
                                    lwf.filter, mCurrentHMILevel));
                }
            }
            isPermissionEventIter = false;
            mListeners.removeAll(nullListeners);
            mRemovedListener.clear();
        }
    }

    private void addVdataRpcPermission(String prefix, List<String> parameters,
                                       HMILevel hmi, SdlPermissionSet permissionSet){
        for(String vdataRpc: parameters){
            char[] chars = vdataRpc.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            String permissionName = prefix + new String(chars);
            SdlPermission permission = SdlPermission.valueOf(permissionName);
            permissionSet.permissions.get(hmi.ordinal()).add(permission);
        }
    }

    private class ListenerWithFilter{
        final WeakReference<SdlPermissionListener> listener;
        final SdlPermissionFilter filter;

        ListenerWithFilter(SdlPermissionListener listener, SdlPermissionFilter filter){
            this.listener = new WeakReference<>(listener);
            this.filter = filter;
        }
    }

    /**
     * EnumSet containing all permissions related to vehicle data monitoring. Not recommended if
     * only a hand full of permissions are needed.
     */
    public static final EnumSet<SdlPermission> PERMISSION_SET_VEHICLE_DATA =
            EnumSet.range(SdlPermission.GetDTCs, SdlPermission.UnsubscribeWiperStatus);

    private SdlPermissionEvent generateSdlPermissionEvent(SdlPermissionSet currentAllowed, SdlPermissionSet userDisallow
            , SdlPermissionFilter filter, HMILevel hmiLevel){
        SdlPermissionSet checkPermissions= SdlPermissionSet.intersect(currentAllowed, filter.permissionSet);
        SdlPermissionSet userDisallowPermissions = SdlPermissionSet.intersect(userDisallow, filter.permissionSet);
        SdlPermissionSet disallowPermissions = SdlPermissionSet.difference(filter.permissionSet, SdlPermissionSet.union(checkPermissions,userDisallowPermissions));

        return new SdlPermissionEvent(
                checkPermissions.permissions.get(hmiLevel.ordinal()),
                userDisallowPermissions.permissions.get(hmiLevel.ordinal()),
                disallowPermissions.permissions.get(hmiLevel.ordinal()));
    }

    private SdlPermissionListener cleanNullFromListenerList(ListenerWithFilter lwf, ArrayList<ListenerWithFilter> nullListeners){
        SdlPermissionListener listener = lwf.listener.get();
        if(listener==null){
            nullListeners.add(lwf);
        }
        return listener;
    }

    private void parsePermissionParameters(SdlPermission permission, List<String> parameters, HMILevel level, SdlPermissionSet permissionSet){
        switch (permission) {
            case GetVehicleData:
                addVdataRpcPermission("Get", parameters, level, permissionSet);
                break;
            case SubscribeVehicleData:
                addVdataRpcPermission("Subscribe", parameters, level, permissionSet);
                break;
            case OnVehicleData:
                addVdataRpcPermission("On", parameters, level, permissionSet);
                break;
            case UnsubscribeVehicleData:
                addVdataRpcPermission("Unsubscribe", parameters, level, permissionSet);
                break;
            default:
                break;
        }
    }

}
