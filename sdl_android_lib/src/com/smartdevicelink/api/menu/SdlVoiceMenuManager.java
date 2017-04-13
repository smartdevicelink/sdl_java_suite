package com.smartdevicelink.api.menu;

import android.util.Log;

import com.smartdevicelink.api.interfaces.SdlContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class SdlVoiceMenuManager {
    public static final String TAG = SdlVoiceMenuManager.class.getSimpleName();
    private final HashMap<String, SdlVoiceOption> mCommandsRegistered = new HashMap<>();
    private final LinkedList<SdlVoiceOption> mPendingRemovals = new LinkedList<>();
    private final HashSet<String> mPendingAdditions = new HashSet<>();

    void addVoiceCommand(SdlVoiceOption newOption){
        Log.v(TAG,"Adding voice command");
        for(String vrCommand: newOption.getVoiceCommands()) {
            if (vrCommand != null) {
                if (containsVRCommand(vrCommand)) {
                    throw new IllegalArgumentException(String.format("Voice command '%s' has already been registered",
                                vrCommand));
                }            }
        }
        if(!containsName(newOption.getName())){
            mCommandsRegistered.put(newOption.getName(), newOption);
            mPendingAdditions.add(newOption.getName());
        } else {
            throw new IllegalArgumentException(String.format("Voice command with '%s' name has already been registered", newOption.getName()));
        }
    }

    void removeVoiceCommand(String item){
        removeVoiceCommand(mCommandsRegistered.get(item));

    }

    void removeVoiceCommand(SdlVoiceOption item){
        if(mPendingAdditions.contains(item.getName())){
            mPendingAdditions.remove(item.getName());
        } else {
            mCommandsRegistered.remove(item.getName());
            mPendingRemovals.add(item);
        }
    }


    void update(SdlContext context){
        Log.v(TAG,"updating voice commands");
        sendPendingRemovals(context);
        sendPendingAdditions(context);
    }

    private void sendPendingAdditions(SdlContext sdlContext){
        for(String key: mPendingAdditions){
            SdlVoiceOption item = mCommandsRegistered.get(key);
            if(item != null){
                item.update(sdlContext);
            }
        }
        mPendingAdditions.clear();
    }

    private void sendPendingRemovals(SdlContext sdlContext) {
        while(!mPendingRemovals.isEmpty()){
            SdlVoiceOption item = mPendingRemovals.removeFirst();
            item.remove(sdlContext);
        }
    }

    public boolean containsName(String name){
        return name!=null && mCommandsRegistered.containsKey(name);
    }

    public boolean containsVRCommand(String vrCommand){
        if(vrCommand==null) return false;
        for(SdlVoiceOption registeredCommand: mCommandsRegistered.values()){
            if(registeredCommand.getVoiceCommands().contains(vrCommand)){
                return true;
            }
        }
        return false;
    }

    SdlVoiceOption getVoiceOption(String name){
        return mCommandsRegistered.get(name);
    }
}
