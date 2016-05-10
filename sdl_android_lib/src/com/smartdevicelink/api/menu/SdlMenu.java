package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;

import java.util.ArrayList;
import java.util.List;

public class SdlMenu extends SdlMenuEntry {

    private SdlMenu mRootMenu = null;

    private final ArrayList<SdlMenuEntry> mMenuEntries;

    public SdlMenu(SdlContext sdlContext, String name){
        super(sdlContext, name);
        mMenuEntries = new ArrayList<>();
    }

    public void addMenuItem(SdlMenuItem menuItem){
        mMenuEntries.add(menuItem);
        menuItem.setRootMenu(this);
        if(isDisplayed){
            menuItem.update();
        } else {
            isChanged = true;
        }
    }

    public void addMenuItem(int position, SdlMenuItem menuItem){
        mMenuEntries.add(position, menuItem);
        menuItem.setRootMenu(this);
        if(isDisplayed){
            menuItem.update();
        } else {
            isChanged = true;
        }
    }

    public void removeMenuItem(SdlMenuItem menuItem){
        if(mMenuEntries.remove(menuItem) && menuItem.isDisplayed) {
            menuItem.remove();
        }
    }

    public void removeMenuItem(int position){
        SdlMenuEntry entry = mMenuEntries.remove(position);
        if(entry != null){
            entry.remove();
        }
    }

    public boolean addSubMenu(SdlMenu menu){
        if(mRootMenu == null){
            menu.setRootMenu(this);
            mMenuEntries.add(menu);
            if(isDisplayed) {
                menu.update();
            } else {
                isChanged = true;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean addSubMenu(int position, SdlMenu menu){
        if(mRootMenu == null){
            menu.setRootMenu(this);
            mMenuEntries.add(position, menu);
            if(isDisplayed) {
                menu.update();
            } else {
                isChanged = true;
            }
            return true;
        } else {
            return false;
        }
    }

    public void removeSubMenu(SdlMenu menu){
        if(mMenuEntries.remove(menu) && menu.isDisplayed) {
            menu.remove();
        }
    }

    public void removeSubMenu(int position){
        SdlMenuEntry entry = mMenuEntries.remove(position);
        if(entry != null){
            entry.remove();
        }
    }

    public List<SdlMenuEntry> getEntries(){
        return mMenuEntries;
    }

    int getEntryPosition(SdlMenuEntry entry){
        return mMenuEntries.indexOf(entry);
    }

    @Override
    public void update() {
        if(isChanged){
            if(isDisplayed && mRootMenu != null){
                sendDeleteSubMenu();
            }
            if(mRootMenu != null){
                sendAddSubMenu();
            }
            isChanged = false;
        }
        for(SdlMenuEntry entry: mMenuEntries){
            entry.update();
        }
    }

    @Override
    void remove() {
        if(mRootMenu != null){
            sendDeleteSubMenu();
        } else {
            for(SdlMenuEntry entry: mMenuEntries){
                entry.remove();
            }
        }
    }

    private void sendAddSubMenu() {
        AddSubMenu asm = new AddSubMenu();
        asm.setMenuID(mId);
        asm.setMenuName(mName);
        asm.setPosition(mRootMenu.getEntryPosition(this));
        mSdlContext.sendRpc(asm);
    }

    private void sendDeleteSubMenu() {
        DeleteSubMenu dsm = new DeleteSubMenu();
        dsm.setMenuID(mId);
        mSdlContext.sendRpc(dsm);
    }
}
