package com.smartdevicelink.api.choiceset;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

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
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by mschwerz on 5/6/16.
 */
public class SdlChoiceSetCreation {
    private final static String TAG= SdlChoiceSet.class.getSimpleName();

    private SparseArray<SdlChoice> mChoices;
    private String mChoiceSetName;
    private Integer mChoiceId = -1;
    private SdlContext mContext;

    public SdlChoiceSetCreation(@NonNull String name, @NonNull ArrayList<SdlChoice> choices, @NonNull SdlContext context){
        mContext= context;
        mChoiceId= context.getSdlChoiceSetManager().requestChoiceSetCount();
        mChoices= populateChoicesWithIds(choices,context);
        mChoiceSetName= name;
    }

    public String getSetName(){return mChoiceSetName;}

    SparseArray<SdlChoice> getChoices(){return mChoices;}

    int getChoiceId(){ return mChoiceId; }

    private SparseArray<SdlChoice> populateChoicesWithIds(Collection<SdlChoice> sdlChoices, SdlContext context){
        SparseArray<SdlChoice> newIdArray= new SparseArray<>();
        for(SdlChoice choice:sdlChoices){
            Integer newId= context.getSdlChoiceSetManager().requestChoiceCount();
            choice.addId(newId);
            newIdArray.append(newId,choice);
        }
        return newIdArray;
    }

    public boolean uploadChoiceSet(final IdReadyListener listener){
        if(mContext.getSdlChoiceSetManager().hasBeenUploaded(mChoiceSetName))
            return true;

        CreateInteractionChoiceSet newRequest = new CreateInteractionChoiceSet();

        ArrayList<Choice> proxyChoices= new ArrayList<>();
        for(int i=0; i<mChoices.size();i++) {
            int choiceID= mChoices.keyAt(i);
            SdlChoice currentChoice = mChoices.get(choiceID);
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
        newRequest.setInteractionChoiceSetID(mChoiceId);
        final SdlChoiceSetCreation finalSelf = this;
        newRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Log.d(TAG,response.getResultCode().toString());
                mContext.getSdlChoiceSetManager().registerSdlChoiceSet(createChoiceSet());
                if(listener!=null)
                    listener.onIdAdded(finalSelf);
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                super.onError(correlationId, resultCode, info);
                Log.e(TAG, resultCode.toString()+" "+info);
                if(listener!=null)
                    listener.onIdError(finalSelf);
            }
        });
        mContext.sendRpc(newRequest);
        return false;
    }

    public boolean deleteChoiceSet(final IdReadyListener listener){
        if(mChoiceId==-1){
            return true;
        }

        DeleteInteractionChoiceSet deleteSet= new DeleteInteractionChoiceSet();
        deleteSet.setInteractionChoiceSetID(mChoiceId);
        final SdlChoiceSetCreation finalSelf = this;
        deleteSet.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {

            }
            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                super.onError(correlationId, resultCode, info);
                //Log.e(TAG, resultCode.toString()+" "+info);
                if(listener!=null)
                    listener.onIdError(finalSelf);
            }
        });
        return false;
    }


    SdlChoiceSet createChoiceSet(){
        HashMap<String, Integer> choiceNames= new HashMap<>();
        for(int i=0; i<mChoices.size();i++){
            choiceNames.put(mChoices.get(mChoices.keyAt(i)).getChoiceName(), mChoices.keyAt(i));
        }
        return new SdlChoiceSet(getSetName(), getChoiceId(), choiceNames);
    }

    public SdlChoiceSet getRepresentativeChoiceSet(){
        SdlChoiceSet choiceSet = createChoiceSet();
        HashMap<String,SdlChoice.OnSelectedListener> choiceNameToListener= new HashMap<>();
        for(int i=0; i<mChoices.size();i++){
            SdlChoice currChoice = mChoices.get(mChoices.keyAt(i));
            choiceNameToListener.put(currChoice.getChoiceName(),currChoice.getListener());
        }
        choiceSet.setListenersForChoiceName(choiceNameToListener);
        return choiceSet;
    }

    public interface IdReadyListener{

        void onIdAdded(SdlChoiceSetCreation sdlIdCommand);

        void onIdError(SdlChoiceSetCreation sdlIdCommand);

    }

}
