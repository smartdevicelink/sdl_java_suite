package com.smartdevicelink.api.choiceset;

import com.smartdevicelink.api.SdlApplication;
import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mschwerz on 5/4/16.
 */
public class SdlChoiceSetManager {
    private static final String TAG = SdlChoiceSetManager.class.getSimpleName();

    private Integer Choice_Count=0;
    private Integer Choice_Set_Count=0;

    private SdlApplication mApplication;

    public SdlChoiceSetManager(SdlApplication application){
        mApplication= application;
    }

    private HashMap<String,SdlChoiceSet> mChoiceSet = new HashMap<>();

    Integer requestChoiceCount(){
        return Choice_Count++;
    }

    Integer requestChoiceSetCount(){
        return Choice_Set_Count++;
    }

    boolean registerSdlChoiceSet(SdlChoiceSet set){
        mChoiceSet.put(set.getSetName(),set);
        return true;
    }

    public boolean uploadChoiceSetCreation(final SdlChoiceSetCreation creation, final ChoiceSetReadyListener listener){
        if(hasBeenUploaded(creation.getSetName()))
            return true;

        CreateInteractionChoiceSet newRequest = new CreateInteractionChoiceSet();

        ArrayList<Choice> proxyChoices= new ArrayList<>();
        for(int i=0; i<creation.getChoices().size();i++) {
            int choiceID= creation.getChoices().keyAt(i);
            SdlChoice currentChoice = creation.getChoices().get(choiceID);
            Choice convertToChoice= new Choice();
            convertToChoice.setMenuName(currentChoice.getMenuText());
            convertToChoice.setSecondaryText(currentChoice.getSubText());
            convertToChoice.setTertiaryText(currentChoice.getRightHandText());
            if(currentChoice.getSdlImage()!=null)
            {
                Image choiceImage= new Image();
                choiceImage.setImageType(ImageType.DYNAMIC);
                choiceImage.setValue(currentChoice.getSdlImage().getSdlName());
                convertToChoice.setImage(choiceImage);
            }
            convertToChoice.setChoiceID(choiceID);
            ArrayList<String> commands= new ArrayList<>();
            commands.addAll(currentChoice.getVoiceCommands());
            convertToChoice.setVrCommands(commands);
            proxyChoices.add(convertToChoice);
        }
        newRequest.setChoiceSet(proxyChoices);
        newRequest.setInteractionChoiceSetID(creation.getChoiceSetId());
        newRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                registerSdlChoiceSet(creation.createChoiceSet());
                if(listener!=null)
                    listener.onChoiceSetAdded(creation);
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                super.onError(correlationId, resultCode, info);
                if(listener!=null)
                    listener.onError(creation, info);
            }
        });
        mApplication.sendRpc(newRequest);
        return false;
    }


    public boolean deleteChoiceSetCreation(final String name, final ChoiceSetDeletedListener listener){

        if(!hasBeenUploaded(name))
            return true;

        DeleteInteractionChoiceSet deleteSet= new DeleteInteractionChoiceSet();
        deleteSet.setInteractionChoiceSetID(grabUploadedChoiceSet(name).getChoiceId());
        deleteSet.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                unregisterSdlChoiceSet(name);
                listener.onChoiceSetRemoved(name);
            }
            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                super.onError(correlationId, resultCode, info);
                //Log.e(TAG, resultCode.toString()+" "+info);
                listener.onError(name);
            }
        });
        return false;
    }


    boolean unregisterSdlChoiceSet(String choiceSetName){
        if(hasBeenUploaded(choiceSetName)){
            mChoiceSet.remove(choiceSetName);
            return true;
        }else
            return false;
    }

    public SdlChoiceSet grabUploadedChoiceSet(String choiceSetName){
        if(hasBeenUploaded(choiceSetName))
            return mChoiceSet.get(choiceSetName).copy();
        else
            return null;
    }

    public boolean hasBeenUploaded(String name){
        return mChoiceSet.containsKey(name);
    }


    public interface ChoiceSetReadyListener {

        void onChoiceSetAdded(SdlChoiceSetCreation sdlIdCommand);

        void onError(SdlChoiceSetCreation sdlIdCommand, String info);

    }

    public interface ChoiceSetDeletedListener{
        void onChoiceSetRemoved(String name);
        void onError(String name);
    }
}
