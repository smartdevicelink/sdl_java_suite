package com.smartdevicelink.api.menu;

import android.util.Log;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class SdlMenu extends SdlMenuItem {

    private static final String TAG = SdlMenu.class.getSimpleName();

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

    SdlMenu(String name, int index, boolean isRootMenu){
        super(name);
        mIndex = index;
        this.isRootMenu = isCreated = isRootMenu;
        mEntryKeyList = new ArrayList<>();
        mEntryMap = new HashMap<>();
        mPendingRemovals = new LinkedList<>();
        mPendingAdditions = new HashSet<>();
    }

    SdlMenu(String name, boolean isRootMenu){
        this(name, -1, isRootMenu);
    }

    void addMenuItem(SdlMenuItem item){
        if(!mEntryMap.containsKey(item.getName())){
            String itemName = item.getName();
            Log.d(TAG, getName() + ": adding item name " + itemName);
            mEntryKeyList.add(itemName);
            mEntryMap.put(itemName, item);
            mPendingAdditions.add(itemName);
        } else {
            throw new IllegalArgumentException(String.format("Menu named '%s' already contains item " +
                    "named '%s", getName(), item.getName()));
        }
    }

    void addMenuItem(int index, SdlMenuItem item){
        if(!mEntryMap.containsKey(item.getName())){
            String itemName = item.getName();
            Log.d(TAG, getName() + ": adding item name " + itemName);
            mEntryKeyList.add(index, itemName);
            mEntryMap.put(itemName, item);
            mPendingAdditions.add(itemName);
        } else {
            throw new IllegalArgumentException(String.format("Menu named '%s' already contains item " +
                    "named '%s", getName(), item.getName()));
        }
    }

    void removeMenuItem(SdlMenuItem menuItem){
        String name = menuItem.getName();
        if(mEntryMap.containsKey(name)){
            Log.d(TAG, getName() + ": removing item name " + name);
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

    public boolean contains(SdlMenuItem sdlMenuItem){
        return sdlMenuItem != null && contains(sdlMenuItem.getName());
    }

    public boolean contains(String itemName){
        return itemName != null && mEntryMap.containsKey(itemName);
    }

    SdlMenuItem getMenuItemByName(String name){
        return mEntryMap.get(name);
    }

    int indexOf(SdlMenuItem item){
        return mEntryKeyList.indexOf(item.getName());
    }

    @Override
    void update(SdlContext sdlContext, int subMenuId) {
        if(!isCreated && !isRootMenu) {
            isCreated = true;
            sendAddSubMenu(sdlContext);
        }
        sendPendingRemovals(sdlContext);
        sendPendingAdditions(sdlContext);
    }

    private void sendPendingAdditions(SdlContext sdlContext){
        for(String key: mPendingAdditions){
            SdlMenuItem item = mEntryMap.get(key);
            if(item != null){
                item.update(sdlContext,
                        isRootMenu ? -1: getId());
            }
        }
        mPendingAdditions.clear();
    }

    private void sendPendingRemovals(SdlContext sdlContext) {
        while(!mPendingRemovals.isEmpty()){
            SdlMenuItem item = mPendingRemovals.removeFirst();
            item.remove(sdlContext);
            item.unregisterSelectListener(sdlContext);
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

        isCreated = false;
    }

    @Override
    void registerSelectListener(SdlContext sdlContext) {
        for(int i = 0; i < mEntryKeyList.size(); i++){
            SdlMenuItem item = mEntryMap.get(mEntryKeyList.get(i));
            item.registerSelectListener(sdlContext);
        }
    }

    @Override
    void unregisterSelectListener(SdlContext sdlContext) {
        for(int i = 0; i < mEntryKeyList.size(); i++){
            SdlMenuItem item = mEntryMap.get(mEntryKeyList.get(i));
            item.unregisterSelectListener(sdlContext);
        }
    }

    private void sendAddSubMenu(SdlContext sdlContext) {
        AddSubMenu asm = new AddSubMenu();
        if(mIndex > 0) asm.setPosition(mIndex);
        asm.setMenuID(getId());
        asm.setMenuName(getName());
        sdlContext.sendRpc(asm);
    }

    private void sendDeleteSubMenu(SdlContext sdlContext) {
        DeleteSubMenu dsm = new DeleteSubMenu();
        dsm.setMenuID(getId());
        sdlContext.sendRpc(dsm);
    }

    private void removeEntryKey(String key){
        for(int i = 0; i < mEntryKeyList.size(); i++){
            if(mEntryKeyList.get(i).equals(key)){
                mEntryKeyList.remove(key);
                break;
            }
        }
    }
}
