package com.smartdevicelink.api.menu;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.SdlActivity;

import java.util.ArrayList;

public class SdlMenuTransaction{

    private ArrayList<SdlMenuCommand> mCommandList = new ArrayList<>();
    private SdlActivity mTopActivity;
    private SdlMenuManager mMenuManager;
    private boolean isExecuted = false;

    public SdlMenuTransaction(SdlMenuManager sdlMenuManager, SdlActivity topActivity){
        mTopActivity = topActivity;
        mMenuManager = sdlMenuManager;
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
        mMenuManager.getTopMenu().update(mTopActivity, 0, 0);
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
        private SdlMenuItem mSdlMenuOption;
        private Integer mIndex;

        AddMenuItemCommand(@NonNull SdlMenu rootMenu, @NonNull SdlMenuItem sdlMenuItem, Integer index){
            mRootMenu = rootMenu;
            mSdlMenuOption = sdlMenuItem;
            mIndex = index;
        }

        @Override
        public void execute() {
            if(mIndex == null){
                mRootMenu.addMenuItem(mSdlMenuOption);
            } else {
                mRootMenu.addMenuItem(mIndex, mSdlMenuOption);
            }
        }

        @Override
        public void undo() {
            mRootMenu.removeMenuItem(mSdlMenuOption);
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

}
