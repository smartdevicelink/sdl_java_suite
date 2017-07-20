package com.smartdevicelink.api.view;

import com.smartdevicelink.api.file.SdlFile;
import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.file.SdlImage;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class SdlChoiceSetManager {
    private static final String TAG = SdlChoiceSetManager.class.getSimpleName();

    //TODO: Make these AtomicIntegers
    private Integer Choice_Count=0;
    private Integer Choice_Set_Count=0;

    private SdlContext mApplicationContext;

    public SdlChoiceSetManager(SdlContext application){
        mApplicationContext = application;
    }

    //TODO: Convert to a SparseArray
    private HashMap<Integer,SdlChoiceSet> mChoiceSetsUploaded = new HashMap<>();

    Integer requestChoiceCount(){
        return Choice_Count++;
    }

    Integer requestChoiceSetCount(){
        return Choice_Set_Count++;
    }

    //TODO: make private and void
    boolean registerSdlChoiceSet(SdlChoiceSet set){
        mChoiceSetsUploaded.put(set.getChoiceSetId(),set);
        return true;
    }

    //TODO: Wrap the method in a runnable with a post on the Sdl thread
    public boolean uploadChoiceSetCreation(final SdlChoiceSet choiceSet, final ChoiceSetReadyListener listener){
        if(hasBeenUploaded(choiceSet))
            return true;

        final CreateInteractionChoiceSet newRequest = new CreateInteractionChoiceSet();

        ArrayList<Choice> proxyChoices = new ArrayList<>();
        final HashMap<SdlImage, ArrayList<Choice>> unsentImages = new HashMap<>();
        for(int i=0; i<choiceSet.getChoices().size();i++) {
            int choiceID= choiceSet.getChoices().keyAt(i);
            SdlChoice currentChoice = choiceSet.getChoices().get(choiceID);
            Choice convertToChoice= new Choice();
            convertToChoice.setMenuName(currentChoice.getMenuText());
            convertToChoice.setSecondaryText(currentChoice.getSubText());
            convertToChoice.setTertiaryText(currentChoice.getRightHandText());
            if(currentChoice.getSdlImage()!=null)
            {
                Image choiceImage= new Image();
                choiceImage.setImageType(ImageType.DYNAMIC);
                choiceImage.setValue(currentChoice.getSdlImage().getSdlName());
                if(!mApplicationContext.getSdlFileManager().isFileOnModule(currentChoice.getSdlImage().getSdlName())){
                    if(!unsentImages.containsKey(currentChoice.getSdlImage())){
                        ArrayList<Choice> choices = new ArrayList<>();
                        unsentImages.put(currentChoice.getSdlImage(), choices);
                        choices.add(convertToChoice);
                    } else {
                        unsentImages.get(currentChoice.getSdlImage()).add(convertToChoice);
                    }
                } else {
                    convertToChoice.setImage(choiceImage);
                }
            }
            convertToChoice.setChoiceID(choiceID);
            ArrayList<String> commands= new ArrayList<>();
            commands.addAll(currentChoice.getVoiceCommands());
            convertToChoice.setVrCommands(commands);
            proxyChoices.add(convertToChoice);
        }
        newRequest.setChoiceSet(proxyChoices);
        newRequest.setInteractionChoiceSetID(choiceSet.getChoiceSetId());
        newRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                registerSdlChoiceSet(choiceSet.deepCopy());
                if(listener!=null)
                    listener.onChoiceSetAdded(choiceSet);
            }

            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                super.onError(correlationId, resultCode, info);
                if(listener!=null)
                    listener.onError(choiceSet, info);
            }
        });
        if(!unsentImages.isEmpty()){
            HashSet<SdlImage> unsentImageIteration= new HashSet<>(unsentImages.keySet());
            for (final SdlImage unsentImage:unsentImageIteration){
                mApplicationContext.getSdlFileManager().uploadSdlImage(unsentImage, new SdlFileManager.FileReadyListener() {
                    @Override
                    public void onFileReady(SdlFile sdlFile) {
                        //it is and SdlImage
                        Image choiceImage= new Image();
                        choiceImage.setImageType(ImageType.DYNAMIC);
                        choiceImage.setValue(sdlFile.getSdlName());
                        for(Choice choices: unsentImages.get(sdlFile)){
                            choices.setImage(choiceImage);
                        }
                        unsentImages.remove(sdlFile);
                        if(unsentImages.isEmpty()){
                            mApplicationContext.sendRpc(newRequest);
                        }
                    }

                    @Override
                    public void onFileError(SdlFile sdlFile) {
                        unsentImages.remove(sdlFile);
                        if(unsentImages.isEmpty()){
                            mApplicationContext.sendRpc(newRequest);
                        }
                    }
                });
            }

        }else {
            mApplicationContext.sendRpc(newRequest);
        }

        return false;
    }

    public boolean uploadSingleChoiceCreation(final SdlChoice choice, final ChoiceSetReadyListener listener){
        SdlChoiceSet choiceSet = new SdlChoiceSet( new ArrayList<>(Collections.singletonList(choice)), mApplicationContext);
        return uploadChoiceSetCreation(choiceSet, listener);
    }

    //TODO: Wrap the method in a runnable with a post on the Sdl thread
    public boolean deleteChoiceSetCreation(final SdlChoiceSet setToDelete, final ChoiceSetDeletedListener listener){

        DeleteInteractionChoiceSet deleteSet= new DeleteInteractionChoiceSet();
        if(setToDelete!=null)
            deleteSet.setInteractionChoiceSetID(setToDelete.getChoiceSetId());
        else
            return true;
        deleteSet.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                unregisterSdlChoiceSet(setToDelete);
                if(listener!=null)
                    listener.onChoiceSetRemoved(setToDelete);
            }
            @Override
            public void onError(int correlationId, Result resultCode, String info) {
                super.onError(correlationId, resultCode, info);
                if(listener!=null)
                    listener.onError(setToDelete);
            }
        });
        mApplicationContext.sendRpc(deleteSet);
        return false;
    }

    //TODO: Make private
    boolean unregisterSdlChoiceSet(SdlChoiceSet choiceSetName){
        if(hasBeenUploaded(choiceSetName)){
            mChoiceSetsUploaded.remove(choiceSetName);
            return true;
        }else
            return false;
    }

    SdlChoiceSet grabUploadedChoiceSet(SdlChoiceSet choiceSetName) {
        if (hasBeenUploaded(choiceSetName))
            return mChoiceSetsUploaded.get(choiceSetName);
        else
            return null;
    }

    public boolean hasBeenUploaded(SdlChoiceSet choiceSet){
        return hasBeenUploaded(choiceSet.getChoiceSetId());
    }

    boolean hasBeenUploaded(Integer choiceId){
        return mChoiceSetsUploaded.containsKey(choiceId);
    }

    Collection<SdlChoiceSet> getUploadedSets(){
        return mChoiceSetsUploaded.values();
    }



    public interface ChoiceSetReadyListener {

        void onChoiceSetAdded(SdlChoiceSet sdlIdCommand);

        void onError(SdlChoiceSet sdlIdCommand, String info);

    }

    public interface ChoiceSetDeletedListener{
        void onChoiceSetRemoved(SdlChoiceSet setRemoved);
        void onError(SdlChoiceSet setErroredOn);
    }
}
