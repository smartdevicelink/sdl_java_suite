package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.ResetGlobalProperties;
import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;

import java.util.ArrayList;
import java.util.EnumSet;

class SdlGlobalPropertiesManager {

    private ArrayList<SdlGlobalProperties> mPropertyTransactions = new ArrayList<>();
    private EnumSet<GlobalProperty> removalProperties = EnumSet.noneOf(GlobalProperty.class);
    private ArrayList<SdlGlobalProperties> mPropertyAdditions = new ArrayList<>();
    private SdlGlobalProperties mPriorProperties = null;

    void addSetProperty(SdlGlobalProperties properties){
        mPropertyAdditions.add(properties);
        removalProperties.removeAll(properties.propertiesSet());
    }

    void removeSetProperty(SdlGlobalProperties properties){
        mPropertyTransactions.remove(properties);
        mPriorProperties = squashOrderedProperties(mPropertyTransactions);
        EnumSet<GlobalProperty> removingProperties = properties.propertiesSet();
        removingProperties.removeAll(mPriorProperties.propertiesSet());
        removalProperties.addAll(removingProperties);
    }

    void update(SdlContext context){
        if(!removalProperties.isEmpty()){
            ResetGlobalProperties resetCommand = new ResetGlobalProperties();
            resetCommand.setProperties(new ArrayList<>(removalProperties));
            context.sendRpc(resetCommand);
            removalProperties.clear();
        }

        if(mPriorProperties != null || !mPropertyAdditions.isEmpty()){
            SdlGlobalProperties setProperties = new SdlGlobalProperties.Builder().build();
            if(mPriorProperties != null){
                setProperties.updateWithLaterProperties(mPriorProperties);
                mPriorProperties = null;
            }
            if(!mPropertyAdditions.isEmpty()){
                setProperties.updateWithLaterProperties(squashOrderedProperties(mPropertyAdditions));
                mPropertyTransactions.addAll(mPropertyAdditions);
                mPropertyAdditions.clear();
            }
            context.sendRpc(setProperties.constructRequest());
        }

    }

    private SdlGlobalProperties squashOrderedProperties(ArrayList<SdlGlobalProperties> properties){
        SdlGlobalProperties squashedProperties = new SdlGlobalProperties.Builder().build();
        for(SdlGlobalProperties prop:properties){
            squashedProperties.updateWithLaterProperties(prop);
        }
        return squashedProperties;
    }


}
