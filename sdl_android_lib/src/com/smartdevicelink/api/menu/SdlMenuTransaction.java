package com.smartdevicelink.api.menu;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.api.SdlActivity;
import com.smartdevicelink.api.interfaces.SdlContext;

import java.util.ArrayList;

public class SdlMenuTransaction{

    private static final String TAG = SdlMenuTransaction.class.getSimpleName();

    private ArrayList<SdlMenuCommand> mCommandList = new ArrayList<>();
    private SdlActivity mTopActivity;
    private SdlContext mSdlContext;
    private SdlMenuManager mMenuManager;
    private boolean isExecuted = false;

    public SdlMenuTransaction(SdlContext sdlContext, SdlActivity topActivity){
        mSdlContext = sdlContext;
        mTopActivity = topActivity;
        mMenuManager = sdlContext.getSdlMenuManager();
    }

    public SdlMenu createSubMenu(String menuName){
        SdlMenu subMenu = new SdlMenu(menuName, false);
        mCommandList.add(new AddMenuItemCommand(mMenuManager.getTopMenu(), subMenu, null));
        return subMenu;
    }

    public SdlMenu createSubMenu(String menuName, int index){
        SdlMenu subMenu = new SdlMenu(menuName, false);
        mCommandList.add(new AddMenuItemCommand(mMenuManager.getTopMenu(), subMenu, index));
        return subMenu;
    }

    public void addMenuOption(SdlMenuOption sdlMenuOption){
        Log.d(TAG, "addMenuOption called for option: " + sdlMenuOption.getName());
        mCommandList.add(new AddMenuItemCommand(mMenuManager.getTopMenu(), sdlMenuOption, null));
    }

    public void addMenuOption(int index, SdlMenuOption sdlMenuOption){
        mCommandList.add(new AddMenuItemCommand(mMenuManager.getTopMenu(), sdlMenuOption, index));
    }

    public void addMenuOption(final SdlMenu subMenu, final SdlMenuOption sdlMenuOption){
        mCommandList.add(new AddMenuItemCommand(subMenu, sdlMenuOption, null));
    }

    public void addMenuOption(final SdlMenu subMenu, int index, final SdlMenuOption sdlMenuOption){
        mCommandList.add(new AddMenuItemCommand(subMenu, sdlMenuOption, index));
    }

    public void setMenuProperties(final SdlGlobalProperties properties){
        mCommandList.add(new PropertiesItemCommand(properties));
    }

    public void removeMenuItem(String menuItemName){
        SdlMenuItem item = mMenuManager.getTopMenu().getMenuItemByName(menuItemName);
        removeMenuItem(item);
    }

    public void removeMenuItem(SdlMenuItem menuItem){
        if(menuItem == null) return;
        SdlMenu rootMenu = mMenuManager.getTopMenu();
        removeMenuItem(rootMenu, menuItem);
    }

    public void removeMenuItem(SdlMenu rootMenu, String menuItemName){
        SdlMenuItem item = mMenuManager.getTopMenu().getMenuItemByName(menuItemName);
        removeMenuItem(rootMenu, item);
    }

    public void removeMenuItem(SdlMenu rootMenu, SdlMenuItem menuItem){
        if(menuItem == null || rootMenu == null) return;
        int index = rootMenu.indexOf(menuItem);
        mCommandList.add(new RemoveMenuItemCommand(rootMenu, menuItem, index));
    }

    public void commit(){
        execute();
        if(mTopActivity != null){
            mMenuManager.registerTransaction(mTopActivity, this);
        }
        mMenuManager.getPropertiesManager().update(mSdlContext);
        mMenuManager.getTopMenu().update(mSdlContext, 0);
    }

    void undo(){
        if(!isExecuted) return;

        for(int i = mCommandList.size() - 1; i >=0; i--){
            mCommandList.get(i).undo();
        }
        isExecuted = false;
    }

    void execute(){
        if(isExecuted) return;

        Log.d(TAG, "My command list has " + mCommandList.size() + " members.");

        for(SdlMenuCommand command: mCommandList){
            command.execute();
        }
        isExecuted = true;
    }

    private interface SdlMenuCommand {

        void execute();

        void undo();

    }

    class AddMenuItemCommand implements SdlMenuCommand{

        private SdlMenu mRootMenu;
        private SdlMenuItem mSdlMenuItem;
        private Integer mIndex;

        AddMenuItemCommand(@NonNull SdlMenu rootMenu, @NonNull SdlMenuItem sdlMenuItem, Integer index){
            mRootMenu = rootMenu;
            mSdlMenuItem = sdlMenuItem;
            mIndex = index;
        }

        @Override
        public void execute() {
            Log.d(TAG, "Executing command for SdlMenuItem: " + mSdlMenuItem.getName());
            if(mIndex == null){
                mRootMenu.addMenuItem(mSdlMenuItem);
            } else {
                mRootMenu.addMenuItem(mIndex, mSdlMenuItem);
            }
        }

        @Override
        public void undo() {
            mRootMenu.removeMenuItem(mSdlMenuItem);
        }
    }

    class RemoveMenuItemCommand implements SdlMenuCommand{

        private AddMenuItemCommand mAddMenuItemCommand;

        RemoveMenuItemCommand(@NonNull SdlMenu rootMenu, @NonNull SdlMenuItem sdlMenuItem, int index){
            mAddMenuItemCommand = new AddMenuItemCommand(rootMenu, sdlMenuItem, index);
        }

        @Override
        public void execute() {
            mAddMenuItemCommand.undo();
        }

        @Override
        public void undo() {
            mAddMenuItemCommand.execute();
        }
    }

    class PropertiesItemCommand implements SdlMenuCommand{
        private SdlGlobalProperties mPropertiesItem;

        PropertiesItemCommand(@NonNull SdlGlobalProperties globalProperties){
            mPropertiesItem = globalProperties;
        }

        @Override
        public void execute() {
            mMenuManager.getPropertiesManager().addSetProperty(mPropertiesItem);
        }

        @Override
        public void undo() {
            mMenuManager.getPropertiesManager().removeSetProperty(mPropertiesItem);
        }
    }

}
