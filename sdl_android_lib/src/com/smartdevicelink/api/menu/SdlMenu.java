package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class SdlMenu extends SdlMenuItem {

    private final HashMap<String, SdlMenuItem> mEntryMap;
    // Used to keep track of indexes
    private final ArrayList<String> mEntryKeyList;
    // Used to queue up items to be removed.
    private final LinkedList<SdlMenuItem> mPendingRemovals;
    private final HashSet<String> mPendingAdditions;
    private boolean isRootMenu = false;

    // True if menu has been created on head unit
    private boolean isCreated = false;
    // True if menu on head unit needs updated
    private boolean isDirty = false;
    private int mDirtyIndex = -1;

    public SdlMenu(String name, boolean isRootMenu){
        super(name);
        this.isRootMenu = isCreated = isRootMenu;
        mEntryKeyList = new ArrayList<>();
        mEntryMap = new HashMap<>();
        mPendingRemovals = new LinkedList<>();
        mPendingAdditions = new HashSet<>();
    }

    void addMenuItem(SdlMenuItem item){
        if(!mEntryMap.containsKey(item.getName())){
            String itemName = item.getName();
            mEntryKeyList.add(itemName);
            mEntryMap.put(itemName, item);
            mPendingAdditions.add(itemName);

            makeDirty(mEntryKeyList.size() - 1);
        } else {
            throw new IllegalArgumentException(String.format("Menu named '%s' already contains item " +
                    "named '%s", mName, item.mName));
        }
    }

    void addMenuItem(int index, SdlMenuItem item){
        if(!mEntryMap.containsKey(item.getName())){
            String itemName = item.getName();
            mEntryKeyList.add(index, itemName);
            mEntryMap.put(itemName, item);
            mPendingAdditions.add(itemName);

            makeDirty(index);
        } else {
            throw new IllegalArgumentException(String.format("Menu named '%s' already contains item " +
                    "named '%s", mName, item.mName));
        }
    }

    void removeMenuItem(SdlMenuItem menuItem){
        removeMenuItem(menuItem.getName());
    }

    void removeMenuItem(String name){
        if(mEntryMap.containsKey(name)){
            SdlMenuItem item = mEntryMap.remove(name);
            removeEntryKey(name);
            if(item != null){
                if(mPendingAdditions.contains(name)){
                    mPendingAdditions.remove(name);
                } else {
                    mPendingRemovals.add(item);
                }
            }
        }
    }

    @Override
    void update(SdlContext sdlContext, int index) {
        if(!isCreated && isRootMenu){
            isCreated = true;
            sendAddSubMenu(sdlContext, index);
        }

        sendPendingRemovals(sdlContext);

        if(isDirty){
            for(int i = mDirtyIndex; i < mEntryKeyList.size(); i++){
                SdlMenuItem item = mEntryMap.get(mEntryKeyList.get(i));
                item.update(sdlContext, i);
            }
            makeClean();
        }

    }

    private void sendPendingRemovals(SdlContext sdlContext) {
        while(!mPendingRemovals.isEmpty()){
            SdlMenuItem item = mPendingRemovals.removeFirst();
            item.remove(sdlContext);
        }
    }

    @Override
    void remove(SdlContext sdlContext) {
        sendPendingRemovals(sdlContext);

        mPendingAdditions.clear();

        for(int i = 0; i < mEntryKeyList.size(); i++){
            SdlMenuItem item = mEntryMap.remove(mEntryKeyList.get(i));
            item.remove(sdlContext);
        }

        mEntryKeyList.clear();

        if(!isRootMenu){
            sendDeleteSubMenu(sdlContext);
        }

        makeClean();
        isCreated = false;
    }

    private void sendAddSubMenu(SdlContext sdlContext, int index) {
        AddSubMenu asm = new AddSubMenu();
        asm.setPosition(index);
        asm.setMenuID(mId);
        asm.setMenuName(mName);
        sdlContext.sendRpc(asm);
    }

    private void sendDeleteSubMenu(SdlContext sdlContext) {
        DeleteSubMenu dsm = new DeleteSubMenu();
        dsm.setMenuID(mId);
        sdlContext.sendRpc(dsm);
    }

    private void makeClean(){
        isDirty = false;
        mDirtyIndex = -1;
    }

    private void makeDirty(int dirtyIndex){
        isDirty = true;
        if(mDirtyIndex < 0 || mDirtyIndex > dirtyIndex){
            mDirtyIndex = dirtyIndex;
        }
    }

    private void removeEntryKey(String key){
        for(int i = 0; i < mEntryKeyList.size(); i++){
            if(mEntryKeyList.get(i).equals(key)){
                if(i != mEntryKeyList.size() - 1){
                    makeDirty(i);
                }
                mEntryKeyList.remove(key);
                break;
            }
        }
    }
}
