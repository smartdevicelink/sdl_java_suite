package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.SdlActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class SdlMenuManager {

    private SdlMenu mSdlMenu;
    private HashMap<SdlActivity, ArrayList<SdlMenuTransaction>> mTransactionRecords;
    private SdlGlobalPropertiesManager mPropertiesManager = new SdlGlobalPropertiesManager();

    public SdlMenuManager(){
        mSdlMenu = new SdlMenu("RootMenu", true);
        mTransactionRecords = new HashMap<>();
    }

    public SdlMenu getTopMenu(){
        return mSdlMenu;
    }

    void registerTransaction(SdlActivity sdlActivity, SdlMenuTransaction transaction){
        ArrayList<SdlMenuTransaction> transactionList = mTransactionRecords.get(sdlActivity);
        if(transactionList == null){
            transactionList = new ArrayList<>();
            mTransactionRecords.put(sdlActivity, transactionList);
        }
        transactionList.add(transaction);
    }

    public void undoTransactions(SdlActivity sdlActivity){
        ArrayList<SdlMenuTransaction> transactionRecord = mTransactionRecords.get(sdlActivity);
        if(transactionRecord != null && transactionRecord.size() > 0){
            for(int i = transactionRecord.size() - 1; i >= 0; i--){
                transactionRecord.get(i).undo();
            }
            mPropertiesManager.update(sdlActivity);
            mSdlMenu.update(sdlActivity, 0);
        }
    }

    public void redoTransactions(SdlActivity sdlActivity){
        ArrayList<SdlMenuTransaction> transactionRecord = mTransactionRecords.get(sdlActivity);
        if(transactionRecord != null && transactionRecord.size() > 0){
            for(SdlMenuTransaction transaction: transactionRecord){
                transaction.execute();
            }
            mPropertiesManager.update(sdlActivity);
            mSdlMenu.update(sdlActivity, 0);
        }
    }

    public void clearTransactionRecord(SdlActivity sdlActivity){
        mTransactionRecords.remove(sdlActivity);
    }

    SdlGlobalPropertiesManager getPropertiesManager(){return mPropertiesManager;}

}
