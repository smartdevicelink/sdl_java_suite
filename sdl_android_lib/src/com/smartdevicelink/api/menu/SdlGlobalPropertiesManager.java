package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.ResetGlobalProperties;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;

import java.util.ArrayList;
import java.util.EnumSet;

class SdlGlobalPropertiesManager {

    ArrayList<SdlGlobalProperties> mPropertyTransactions = new ArrayList<>();
    EnumSet<GlobalProperty> removalProperties = EnumSet.noneOf(GlobalProperty.class);

    void addSetProperty(SdlGlobalProperties properties){
        mPropertyTransactions.add(properties);
        removalProperties.removeAll(properties.propertiesSet());
    }

    void removeSetProperty(SdlGlobalProperties properties){
        mPropertyTransactions.remove(properties);
        EnumSet<GlobalProperty> setProperties = EnumSet.noneOf(GlobalProperty.class);
        for(SdlGlobalProperties currentProperties: mPropertyTransactions){
            setProperties.addAll(currentProperties.propertiesSet());
        }
        EnumSet<GlobalProperty> removingProperties = EnumSet.copyOf(properties.propertiesSet());
        removingProperties.removeAll(setProperties);
        removalProperties.addAll(removingProperties);

    }

    void update(SdlContext context){
        if(!removalProperties.isEmpty()){
            ResetGlobalProperties resetCommand = new ResetGlobalProperties();
            resetCommand.setProperties(new ArrayList<>(removalProperties));
            context.sendRpc(resetCommand);
        }

        if(!mPropertyTransactions.isEmpty()){
            context.sendRpc(squashOrderedProperties(mPropertyTransactions));
        }
    }

    SetGlobalProperties squashOrderedProperties(ArrayList<SdlGlobalProperties> properties){
        SdlGlobalProperties squashedProperties = new SdlGlobalProperties.Builder().build();
        for(SdlGlobalProperties prop:properties){
            squashedProperties.updateWithLaterProperties(prop);
        }
        return squashedProperties.constructRequest();
    }


}
