package com.smartdevicelink.test.utl;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.rpc.AppServiceCapability;
import com.smartdevicelink.proxy.rpc.AppServiceManifest;
import com.smartdevicelink.proxy.rpc.AppServiceRecord;
import com.smartdevicelink.proxy.rpc.MediaServiceManifest;
import com.smartdevicelink.proxy.rpc.NavigationServiceManifest;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.WeatherServiceManifest;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;
import com.smartdevicelink.proxy.rpc.enums.ServiceUpdateReason;
import com.smartdevicelink.test.TestValues;

import java.util.ArrayList;
import java.util.List;

public class AppServiceFactory {


    public static AppServiceManifest createAppServiceManifest(AppServiceType type, String serviceName){
        AppServiceManifest manifest = new AppServiceManifest();

        manifest.setServiceName(serviceName);
        manifest.setRpcSpecVersion(new SdlMsgVersion(TestValues.MAX_RPC_VERSION_SUPPORTED));
        manifest.setAllowAppConsumers(true);
        List<FunctionID> handledRPCs = new ArrayList<>();

        switch (type){
            case MEDIA:
                handledRPCs.add(FunctionID.BUTTON_PRESS);
                manifest.setMediaServiceManifest(new MediaServiceManifest());
                break;
            case WEATHER:
                WeatherServiceManifest weatherServiceManifest = new WeatherServiceManifest();
                weatherServiceManifest.setCurrentForecastSupported(true);
                weatherServiceManifest.setMaxHourlyForecastAmount(6);
                weatherServiceManifest.setMaxMinutelyForecastAmount(30);
                weatherServiceManifest.setMaxMultidayForecastAmount(5);
                weatherServiceManifest.setWeatherForLocationSupported(true);
                manifest.setWeatherServiceManifest(weatherServiceManifest);
                break;
            case NAVIGATION:
                handledRPCs.add(FunctionID.SEND_LOCATION);
                handledRPCs.add(FunctionID.GET_WAY_POINTS);
                handledRPCs.add(FunctionID.SUBSCRIBE_VEHICLE_DATA);
                handledRPCs.add(FunctionID.UNSUBSCRIBE_VEHICLE_DATA);

                NavigationServiceManifest navigationServiceManifest = new NavigationServiceManifest();
                navigationServiceManifest.setAcceptsWayPoints(true);
                manifest.setNavigationServiceManifest(navigationServiceManifest);
                break;
        }

        manifest.setHandledRpcsUsingFunctionIDs(handledRPCs);

        return manifest;
    }

    public static AppServiceRecord createAppServiceRecord(AppServiceType type, String serviceName, String serviceID, boolean isActive){
        AppServiceRecord appServiceRecord = new AppServiceRecord();
        appServiceRecord.setServiceManifest(createAppServiceManifest(type,serviceName));
        appServiceRecord.setServiceID(serviceID);
        appServiceRecord.setServiceActive(isActive);
        appServiceRecord.setServicePublished(true);
        return appServiceRecord;
    }

    public static AppServiceCapability createAppServiceCapability(AppServiceType type, String serviceName, String serviceID, boolean isActive, ServiceUpdateReason updateReason){
        AppServiceCapability appServiceCapability = new AppServiceCapability();
        appServiceCapability.setUpdatedAppServiceRecord(createAppServiceRecord(type,serviceName,serviceID,isActive));
        appServiceCapability.setUpdateReason(updateReason);
        return appServiceCapability;
    }
}
