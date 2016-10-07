package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.file.SdlImage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;
import com.smartdevicelink.proxy.rpc.enums.ImageType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

public class SdlGlobalProperties {

    private ArrayList<TTSChunk> mHelpPrompt;
    private ArrayList<TTSChunk> mTimeoutPrompt;
    private String mVrHelpTitle;
    private ArrayList<VrHelpItem> mHelpItems;
    private String mMenuTitle;
    private SdlImage mMenuIcon;
    private KeyboardProperties mKeyboardProperties;

    private SdlGlobalProperties(Builder builder){
        mHelpPrompt = builder.mHelpPrompt;
        mTimeoutPrompt = builder.mTimeoutPrompt;
        mVrHelpTitle = builder.mVrHelpTitle;
        mHelpItems = builder.mHelpItems;
        mMenuTitle = builder.mMenuTitle;
        mMenuIcon = builder.mMenuIcon;
        mKeyboardProperties = builder.mKeyboardProperties;
    }

    public SetGlobalProperties constructRequest(){
        SetGlobalProperties rpcRequest = new SetGlobalProperties();
        if(mMenuTitle!=null){
            rpcRequest.setMenuTitle(mMenuTitle);
        }
        if(mHelpPrompt!=null && !mHelpPrompt.isEmpty()){
            rpcRequest.setHelpPrompt(mHelpPrompt);
        }
        if(mKeyboardProperties!=null){
            rpcRequest.setKeyboardProperties(mKeyboardProperties);
        }
        if(mMenuIcon!=null){
            Image menuIcon = new Image();
            menuIcon.setImageType(ImageType.DYNAMIC);
            menuIcon.setValue(mMenuIcon.getSdlName());
            rpcRequest.setMenuIcon(menuIcon);
        }
        if(mVrHelpTitle!=null){
            rpcRequest.setVrHelpTitle(mVrHelpTitle);
        }
        if(mHelpItems!=null && !mHelpItems.isEmpty()){
            rpcRequest.setVrHelp(mHelpItems);
        }
        if(mTimeoutPrompt!=null && !mTimeoutPrompt.isEmpty()){
            rpcRequest.setTimeoutPrompt(mTimeoutPrompt);
        }
        return rpcRequest;
    }

    public EnumSet<GlobalProperty> propertiesSet(){
        EnumSet<GlobalProperty> properties = EnumSet.noneOf(GlobalProperty.class);
        if(mMenuTitle!=null){
            properties.add(GlobalProperty.MENUNAME);
        }
        if(mHelpPrompt!=null){
            properties.add(GlobalProperty.HELPPROMPT);
        }
        if(mKeyboardProperties!=null){
            properties.add(GlobalProperty.KEYBOARDPROPERTIES);
        }
        if(mMenuIcon!=null){
            properties.add(GlobalProperty.MENUICON);
        }
        if(mVrHelpTitle!=null){
            properties.add(GlobalProperty.VRHELPTITLE);
        }
        if(mHelpItems!=null){
            properties.add(GlobalProperty.VRHELPITEMS);
        }
        if(mTimeoutPrompt!=null){
            properties.add(GlobalProperty.TIMEOUTPROMPT);
        }
        return properties;
    }

    void updateWithLaterProperties(SdlGlobalProperties newProperties){
        if(newProperties.mMenuTitle!=null){
            mMenuTitle = newProperties.mMenuTitle;
        }
        if(newProperties.mHelpPrompt!=null){
            mHelpPrompt = newProperties.mHelpPrompt;
        }
        if(newProperties.mKeyboardProperties!=null){
            mKeyboardProperties = newProperties.mKeyboardProperties;
        }
        if(newProperties.mMenuIcon!=null){
            mMenuIcon = newProperties.mMenuIcon;
        }
        if(newProperties.mVrHelpTitle!=null){
            mVrHelpTitle = newProperties.mVrHelpTitle;
        }
        if(newProperties.mHelpItems!=null){
            mHelpItems = newProperties.mHelpItems;
        }
        if(newProperties.mTimeoutPrompt!=null){
            mTimeoutPrompt = newProperties.mTimeoutPrompt;
        }
    }


    public static class Builder{
        private ArrayList<TTSChunk> mHelpPrompt;
        private ArrayList<TTSChunk> mTimeoutPrompt;
        private String mVrHelpTitle;
        private ArrayList<VrHelpItem> mHelpItems;
        private String mMenuTitle;
        private SdlImage mMenuIcon;
        private KeyboardProperties mKeyboardProperties;

        public Builder(){}

        public Builder setVisibleHelp(String helpTitle, Collection<VrHelpItem> helpItem){
            mVrHelpTitle = helpTitle;
            mHelpItems = new ArrayList<>(helpItem);
            return this;
        }

        public Builder setMenuText (String menuText){
            mMenuTitle = menuText;
            return this;
        }

        public Builder setMenuIcon (SdlImage menuIcon){
            mMenuIcon = menuIcon;
            return this;
        }

        public Builder setKeyboardProperties (KeyboardProperties properties){
            mKeyboardProperties = properties;
            return this;
        }

        public Builder setHelpPrompt(Collection<TTSChunk> helpPrompt){
            mHelpPrompt = new ArrayList<>(helpPrompt);
            return this;
        }

        public Builder setTimeoutPrompt(Collection<TTSChunk> timeoutPrompt){
            mTimeoutPrompt = new ArrayList<>(timeoutPrompt);
            return this;
        }

        public SdlGlobalProperties build(){
            return new SdlGlobalProperties(this);
        }

    }

}
