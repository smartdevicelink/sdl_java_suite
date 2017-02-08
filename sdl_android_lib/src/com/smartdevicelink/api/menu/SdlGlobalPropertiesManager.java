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

    void addSetProperty(SdlGlobalProperties properties){
        mPropertyAdditions.add(properties);
        removalProperties.removeAll(properties.propertiesSet());
    }

    void removeSetProperty(SdlGlobalProperties properties){
        mPropertyTransactions.remove(properties);
        removalProperties.addAll(properties.propertiesSet());
    }

    void update(SdlContext context){

        SdlGlobalProperties priorProperties = null;
        if(!removalProperties.isEmpty()){

            // figuring out the previous set properties
            // if they contain all the enums we are removing then we don't need to do a reset
            priorProperties = squashOrderedProperties(mPropertyTransactions);

            // if none of the prior properties contains the removal properties, then we
            // don't need to send the prior set properties
            if(!removalProperties.removeAll(priorProperties.propertiesSet())){
                priorProperties = null;
            }

            if(!removalProperties.isEmpty()){
                ResetGlobalProperties resetCommand = new ResetGlobalProperties();
                resetCommand.setProperties(new ArrayList<>(removalProperties));
                context.sendRpc(resetCommand);
                removalProperties.clear();
            }
        }

        if(priorProperties != null || !mPropertyAdditions.isEmpty()){
            SdlGlobalProperties setProperties = new SdlGlobalProperties.Builder().build();
            if(priorProperties != null){
                setProperties.updateWithLaterProperties(priorProperties);
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
