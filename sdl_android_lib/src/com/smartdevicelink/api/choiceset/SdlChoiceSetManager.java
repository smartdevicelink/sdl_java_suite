package com.smartdevicelink.api.choiceset;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.api.SdlApplication;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoiceSetManager {
    private static final String TAG = SdlChoiceSetManager.class.getSimpleName();

    private Integer Choice_Count=0;
    private Integer Choice_Set_Count=0;

    private HashMap<String,SdlChoiceSet> mChoiceSet = new HashMap<>();

    Integer requestChoiceCount(){
        return Choice_Count++;
    }

    Integer requestChoiceSetCount(){
        return Choice_Set_Count++;
    }

    public boolean registerSdlChoiceSet(SdlChoiceSet set){
        mChoiceSet.put(set.getSetName(),set);
        return true;
    }

    public SdlChoiceSet grabUploadedChoiceSet(String name){
        if(hasBeenUploaded(name))
            return mChoiceSet.get(name).copy();
        else
            return null;
    }

    public boolean hasBeenUploaded(String name){
        return mChoiceSet.containsKey(name);
    }

}
