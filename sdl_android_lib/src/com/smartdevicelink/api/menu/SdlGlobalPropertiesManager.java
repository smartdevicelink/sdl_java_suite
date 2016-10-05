package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.ResetGlobalProperties;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

class SdlGlobalPropertiesManager {

    ArrayList<SdlGlobalProperties> mPropertyTransactions = new ArrayList<>();
    SetGlobalProperties pendingSetGlobalProperty;
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
        ResetGlobalProperties resetCommand = new ResetGlobalProperties();
        List<GlobalProperty> properties = Collections.emptyList();
        properties.addAll(removalProperties);
        resetCommand.setProperties(properties);
        context.sendRpc(resetCommand);

        if(!mPropertyTransactions.isEmpty()){
            ArrayList<SdlGlobalProperties> deepCopy = new ArrayList<>(mPropertyTransactions);
            sendPropertyRequest(deepCopy, 0, context);
        }
    }

    void squashOrderedProperties(ArrayList<SdlGlobalProperties> properties){
        SetGlobalProperties p = new SetGlobalProperties();
        for(SdlGlobalProperties prop:properties){

        }
    }

    private void sendPropertyRequest(final ArrayList<SdlGlobalProperties> properties, final int index, final SdlContext context){
        SetGlobalProperties setCommand = properties.get(index).constructRequest();
        setCommand.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(index+1<properties.size()){
                    sendPropertyRequest(properties,index+1, context);
                }
            }
        });
        context.sendRpc(setCommand);

    }


}
