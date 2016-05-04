package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class SdlPermissionManager {

    private static final String TAG = SdlPermissionManager.class.getSimpleName();

    private SdlPermissionSet mSdlPermissionSet;

    private ArrayList<ListenerWithFilter> mListeners;

    private HMILevel mCurrentHMILevel = HMILevel.HMI_NONE;

    private final Object PERMISSION_LOCK = new Object();

    public SdlPermissionManager(SdlProxyALM sdlProxy){
        mSdlPermissionSet = SdlPermissionSet.obtain();

        mListeners = new ArrayList<>();
        sdlProxy.addOnRPCNotificationListener(FunctionID.ON_PERMISSIONS_CHANGE,
                mPermissionChangeListener);
        sdlProxy.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS,
                mHMIStatusListener);
    }

    /**
     * This method returns true if the given single permission is available now.
     * @param permission The {@link SdlPermission} to check.
     * @return boolean indicating if the given SdlPermission is available.
     */
    public boolean isPermissionAvailable(@NonNull SdlPermission permission){
        synchronized (PERMISSION_LOCK) {
            return mSdlPermissionSet.permissions.get(mCurrentHMILevel.ordinal()).contains(permission);
        }

    }

    /**
     * Method to change the current HMI level if it has changed and to notify added {@link SdlPermissionListener}
     * of any changes in {@link SdlPermission} with the {@link HMILevel} transition.
     * @param hmiLevel The {@link HMILevel} to change to
     */
    public void setCurrentHMILevel(@NonNull HMILevel hmiLevel){
        synchronized (PERMISSION_LOCK) {
            if(hmiLevel!=mCurrentHMILevel) {
                for (ListenerWithFilter lwf : mListeners) {
                    //filter out the permissions that we are not interested in
                    SdlPermissionSet intersection = SdlPermissionSet.intersect(mSdlPermissionSet, lwf.filter.permissionSet);
                    //see if there is any change in the EnumSet between the old HMI Level and the new HMI Level
                    if (intersection.checkForChangeBetweenHMILevels(mCurrentHMILevel, hmiLevel)) {
                        lwf.listener.onPermissionChanged(generateSdlPermmisionEvent(mSdlPermissionSet, lwf.filter, hmiLevel));
                    }
                }
                mCurrentHMILevel = hmiLevel;
            }
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
            return generateSdlPermmisionEvent(mSdlPermissionSet, filter, mCurrentHMILevel);
        }
    }

    private OnRPCNotificationListener mPermissionChangeListener = new OnRPCNotificationListener() {
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
                        //ensure there is a change in permissions first for the given HMI level and requested permissions
                        if (changed.containsAnyForHMILevel(lwf.filter.permissionSet,mCurrentHMILevel)) {
                            lwf.listener.onPermissionChanged(generateSdlPermmisionEvent(mSdlPermissionSet, lwf.filter, mCurrentHMILevel));
                        }

                    }
                }
            }

    };

    private OnRPCNotificationListener mHMIStatusListener = new OnRPCNotificationListener() {
        @Override
        public void onNotified(RPCNotification notification) {
            OnHMIStatus hmiStatus = (OnHMIStatus) notification;
            if(notification == null || hmiStatus.getHmiLevel() == null){
                Log.w(TAG, "INVALID OnHMIStatus Notification Received!");
                return;
            }
            HMILevel hmiLevel = hmiStatus.getHmiLevel();
            setCurrentHMILevel(hmiLevel);

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

        ListenerWithFilter(SdlPermissionListener listener, SdlPermissionFilter filter){
            this.listener = listener;
            this.filter = filter;
        }
    }

    /**
     * EnumSet containing all permissions related to vehicle data monitoring. Not recommended if
     * only a hand full of permissions are needed.
     */
    public static final EnumSet<SdlPermission> PERMISSION_SET_VEHICLE_DATA =
            EnumSet.range(SdlPermission.GetDTCs, SdlPermission.UnsubscribeWiperStatus);

    private SdlPermissionEvent generateSdlPermmisionEvent(SdlPermissionSet current, SdlPermissionFilter filter, HMILevel hmiLevel){
        SdlPermissionSet checkPermissions= SdlPermissionSet.intersect(current, filter.permissionSet);
        if(checkPermissions.containsAllForHMILevel(filter.permissionSet, hmiLevel)){
            //Verified that all of the filtered permissions are present with the current permissions at the current HMI Level
            return new SdlPermissionEvent(checkPermissions.permissions.get(hmiLevel.ordinal()), SdlPermissionEvent.PermissionLevel.ALL);
        }else if(checkPermissions.containsAnyForHMILevel(filter.permissionSet, hmiLevel)){
            //Verified that at least one of the filtered permissions is present at the current HMI Level
            return new SdlPermissionEvent(checkPermissions.permissions.get(hmiLevel.ordinal()), SdlPermissionEvent.PermissionLevel.SOME);
        }else {
            //None of the filtered permissions are present at the current HMI Level
            return new SdlPermissionEvent(checkPermissions.permissions.get(hmiLevel.ordinal()), SdlPermissionEvent.PermissionLevel.NONE);
        }
    }

}
