package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.SdlActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class SdlMenuManager {

    private SdlMenu mSdlMenu;
    private HashMap<SdlActivity, ArrayList<SdlMenuTransaction>> mTransactionRecords;

    public SdlMenuManager(){
        mSdlMenu = new SdlMenu("RootMenu", true);
        mTransactionRecords = new HashMap<>();
    }

    SdlMenu getTopMenu(){
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
        if(transactionRecord != null){
            for(int i = transactionRecord.size() - 1; i >= 0; i--){
                transactionRecord.get(i).undo();
            }
        }
    }

    public void redoTransactions(SdlActivity sdlActivity){
        ArrayList<SdlMenuTransaction> transactionRecord = mTransactionRecords.get(sdlActivity);
        if(transactionRecord != null){
            for(SdlMenuTransaction transaction: transactionRecord){
                transaction.execute();
            }
        }
    }

    public void clearTransactionRecord(SdlActivity sdlActivity){
        mTransactionRecords.remove(sdlActivity);
    }

}
