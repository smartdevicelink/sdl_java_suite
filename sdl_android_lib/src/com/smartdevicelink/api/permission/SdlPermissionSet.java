package com.smartdevicelink.api.permission;

import android.support.v4.util.Pools;

import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

class SdlPermissionSet {

    private static final Pools.SynchronizedPool<SdlPermissionSet> syncPool = new Pools.SynchronizedPool<>(10);

    ArrayList<EnumSet<SdlPermission>> permissions;

    static SdlPermissionSet obtain(){
        SdlPermissionSet instance = syncPool.acquire();
        if(instance != null){
            instance.clear();
            return instance;
        } else {
            return new SdlPermissionSet();
        }
    }

    void clear(){
        for(EnumSet<SdlPermission> set: permissions){
            set.clear();
        }
    }

    void recycle(){
        syncPool.release(this);
    }

    private SdlPermissionSet(){
        permissions = newPermissionCollection();
    }

    boolean containsAny(SdlPermissionSet sdlPermissionSet){

        SdlPermissionSet copy = SdlPermissionSet.copy(this);

        try {

            for(int i = 0; i < copy.permissions.size(); i++){
                copy.permissions.get(i).retainAll(sdlPermissionSet.permissions.get(i));
                if(!copy.permissions.get(i).isEmpty()){
                    return true;
                }
            }

            return false;

        } finally {
            copy.recycle();
        }

    }

    boolean containsAll(SdlPermissionSet sdlPermissionSet){
        EnumSet<SdlPermission> condensedThis = EnumSet.noneOf(SdlPermission.class);
        EnumSet<SdlPermission> condesnedArg = EnumSet.noneOf(SdlPermission.class);

        for(int i = 0; i < permissions.size(); i++){
            condensedThis.addAll(permissions.get(i));
            condesnedArg.addAll((sdlPermissionSet.permissions.get(i)));
        }

        return condensedThis.containsAll(condesnedArg);
    }

    boolean containsExactlyAll(SdlPermissionSet sdlPermissionSet){

        for(int i = 0; i < permissions.size(); i++){
            if(!permissions.get(i).containsAll(sdlPermissionSet.permissions.get(i))){
                return false;
            }
        }

        return true;
    }

    void addPermissions(Collection<SdlPermission> permissions, HMILevel hmiLevel){
        this.permissions.get(hmiLevel.ordinal()).addAll(permissions);
    }

    void addPermission(SdlPermission permission){
        for(HMILevel level: HMILevel.values()){
            addPermission(permission, level);
        }
    }

    void addPermissions(Collection<SdlPermission> permissions){

        for(HMILevel hmiLevel: HMILevel.values()){
            addPermissions(permissions, hmiLevel);
        }

    }

    void addPermission(SdlPermission permission, HMILevel hmiLevel){
        permissions.get(hmiLevel.ordinal()).add(permission);
    }

    static SdlPermissionSet copy(SdlPermissionSet permissionSet){

        SdlPermissionSet copy = SdlPermissionSet.obtain();

        for(int i = 0; i < permissionSet.permissions.size(); i++){
            copy.permissions.get(i).addAll(permissionSet.permissions.get(i));
        }

        return copy;
    }

    static SdlPermissionSet union(SdlPermissionSet lhs, SdlPermissionSet rhs){

        SdlPermissionSet copy = SdlPermissionSet.copy(lhs);

        for(int i = 0; i < rhs.permissions.size(); i++){
            copy.permissions.get(i).addAll(rhs.permissions.get(i));
        }

        return copy;
    }

    static SdlPermissionSet intersect(SdlPermissionSet lhs, SdlPermissionSet rhs){

        SdlPermissionSet copy = SdlPermissionSet.copy(lhs);

        for(int i = 0; i < copy.permissions.size(); i++){
            copy.permissions.get(i).retainAll(rhs.permissions.get(i));
        }

        return copy;

    }

    static SdlPermissionSet difference(SdlPermissionSet lhs, SdlPermissionSet rhs){

        SdlPermissionSet copy = SdlPermissionSet.copy(lhs);

        for(int i = 0; i < copy.permissions.size(); i++){
            copy.permissions.get(i).removeAll(rhs.permissions.get(i));
        }

        return copy;
    }

    static SdlPermissionSet symmetricDifference(SdlPermissionSet lhs, SdlPermissionSet rhs){

        SdlPermissionSet differenceLhs = difference(lhs, rhs);
        SdlPermissionSet differenceRhs = difference(rhs, lhs);

        return union(differenceLhs, differenceRhs);
    }

    private ArrayList<EnumSet<SdlPermission>> newPermissionCollection() {

        int numHmiLevels = HMILevel.values().length;
        ArrayList<EnumSet<SdlPermission>> permissions = new ArrayList<>(numHmiLevels);

        for(int i = 0; i < numHmiLevels; i++){
            permissions.add(EnumSet.noneOf(SdlPermission.class));
        }

        return permissions;

    }

}