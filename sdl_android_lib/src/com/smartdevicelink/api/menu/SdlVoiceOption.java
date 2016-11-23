package com.smartdevicelink.api.menu;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.AddCommand;

import java.util.ArrayList;

public class SdlVoiceOption {
    private SdlSyncCommand mSyncCommand;
    private ArrayList<String> mVrCommands;
    private SdlCommandInfo mCommandInfo;

    public SdlVoiceOption(@NonNull String name, @NonNull SelectListener listener, @NonNull ArrayList<String> vrCommands) {
        mSyncCommand = new SdlSyncCommand(listener);
        mVrCommands = vrCommands;
        mCommandInfo = new SdlCommandInfo(name);
    }

    void update(SdlContext sdlContext) {
        mSyncCommand.update(mCommandInfo.getId(), sdlContext, generateAddCommand());
    }

    void remove(SdlContext sdlContext) {
        mSyncCommand.remove(mCommandInfo.getId(), sdlContext);
    }

    private AddCommand generateAddCommand() {
        AddCommand ac = new AddCommand();
        ac.setCmdID(mCommandInfo.getId());
        ac.setVrCommands(mVrCommands);
        return ac;
    }

    ArrayList<String> getVoiceCommands(){
        return mVrCommands;
    }

    public String getName(){
        return mCommandInfo.getName();
    }

}
