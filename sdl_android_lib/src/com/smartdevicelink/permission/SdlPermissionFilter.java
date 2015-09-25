package com.smartdevicelink.permission;

import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import java.util.Collection;

public class SdlPermissionFilter {

    private SdlPermissionSet mPermissionSet;

    public SdlPermissionFilter(){
        mPermissionSet = SdlPermissionSet.obtain();
    }

    public void addPermission(SdlPermission permission){
        mPermissionSet.addPermission(permission);
    }

    public void addPermission(SdlPermission permission, HMILevel hmiLevel){
        mPermissionSet.addPermission(permission, hmiLevel);
    }

    public void addPermissions(Collection<SdlPermission> permissions){
        mPermissionSet.addPermissions(permissions);
    }

    public void addPermissions(Collection<SdlPermission> permissions, HMILevel hmiLevel){
        mPermissionSet.addPermissions(permissions, hmiLevel);
    }

}