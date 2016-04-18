package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by mschwerz on 4/15/16.
 */
public class SdlPermissionManagerTest {

    SdlPermissionManager mSdlPermissionManager;

    @Before
    public void setUp() throws Exception {
        mSdlPermissionManager = new SdlPermissionManager(mock(SdlProxyALM.class));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAllPermissionsAvailableOnAdd(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(allOnPermissionChange);

        SdlPermissionEvent checkEvent= mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
            }
        }, getStandardSdlFilter());
        checkStandardPermissionsEvent(checkEvent, SdlPermissionEvent.PermissionLevel.ALL);
    }

    @Test
    public void testSomePermissionsAvailableOnAdd(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(someOnPermissionChange);

        SdlPermissionEvent checkEvent= mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
            }
        }, getStandardSdlFilter());
        checkStandardPermissionsEvent(checkEvent, SdlPermissionEvent.PermissionLevel.SOME);
    }

    @Test
    public void testNonePermissionsAvailableOnAdd(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(noneOnPermissionChange);

        SdlPermissionEvent checkEvent= mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
            }
        }, getStandardSdlFilter());
        checkStandardPermissionsEvent(checkEvent, SdlPermissionEvent.PermissionLevel.NONE);

    }


    @Test
    public void testNoPermissionsPresentToAll(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(noneOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                checkStandardPermissionsEvent(event, SdlPermissionEvent.PermissionLevel.ALL);
            }
        }, getStandardSdlFilter());
        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());
        mSdlPermissionManager.mPermissionChangeListener.onNotified(allOnPermissionChange);
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    @Test
    public void testNoPermissionsPresentToSome(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(noneOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                checkStandardPermissionsEvent(event, SdlPermissionEvent.PermissionLevel.SOME);
            }
        }, getStandardSdlFilter());
        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());
        mSdlPermissionManager.mPermissionChangeListener.onNotified(someOnPermissionChange);
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));


    }

    @Test
    public void testSomePermissionsPresentToNone(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(someOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                checkStandardPermissionsEvent(event, SdlPermissionEvent.PermissionLevel.NONE);
            }
        }, getStandardSdlFilter());
        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());
        mSdlPermissionManager.mPermissionChangeListener.onNotified(noneOnPermissionChange);
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));


    }

    @Test
    public void testSomePermissionsPresentToAll(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(someOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                checkStandardPermissionsEvent(event, SdlPermissionEvent.PermissionLevel.ALL);
            }
        }, getStandardSdlFilter());
        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());
        mSdlPermissionManager.mPermissionChangeListener.onNotified(allOnPermissionChange);
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    @Test
    public void testAllPermissionsPresentToNone(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(allOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                checkStandardPermissionsEvent(event, SdlPermissionEvent.PermissionLevel.NONE);
            }
        }, getStandardSdlFilter());
        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());
        mSdlPermissionManager.mPermissionChangeListener.onNotified(noneOnPermissionChange);
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    @Test
    public void testNoNotificationReceivedWhenPermissionsSameBetweenHMI(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(allOnPermissionChange);

        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());

        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_BACKGROUND));
        verify(mockListener,never()).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    @Test
    public void testNoPermissionsToAllHMILevelChange(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                checkStandardPermissionsEvent(event, SdlPermissionEvent.PermissionLevel.ALL);
            }
        }, getStandardSdlFilter());

        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());

        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_FULL));
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    @Test
    public void testNoPermissionsToSomeHMILevelChange(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                checkStandardPermissionsEvent(event, SdlPermissionEvent.PermissionLevel.SOME);
            }
        }, getStandardSdlFilter());

        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());

        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_BACKGROUND));
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    @Test
    public void testSomePermissionsToNoneHMILevelChange(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_BACKGROUND));
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                checkStandardPermissionsEvent(event, SdlPermissionEvent.PermissionLevel.NONE);
            }
        }, getStandardSdlFilter());

        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());

        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_NONE));
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    @Test
    public void testSomePermissionsToAllHMILevelChange(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_BACKGROUND));
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                checkStandardPermissionsEvent(event, SdlPermissionEvent.PermissionLevel.ALL);
            }
        }, getStandardSdlFilter());

        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());

        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_FULL));
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    @Test
    public void testAllPermissionsToNoneHMILevelChange(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_FULL));
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                checkStandardPermissionsEvent(event, SdlPermissionEvent.PermissionLevel.NONE);
            }
        }, getStandardSdlFilter());

        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());

        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_NONE));
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    @Test
    public void testAllPermissionsToSomeHMILevelChange(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_FULL));
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                checkStandardPermissionsEvent(event, SdlPermissionEvent.PermissionLevel.SOME);
            }
        }, getStandardSdlFilter());

        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());

        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_BACKGROUND));
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }


    @Test
    public void testEveryPermissionsFromNoneToAll(){
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                assertThat(event.getPermissions(), is(everySet));
                assertThat(event.mPermissionLevel, is(SdlPermissionEvent.PermissionLevel.ALL));
            }
        }, getEveryPermissionFilter());
        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getEveryPermissionFilter());
        mSdlPermissionManager.mPermissionChangeListener.onNotified(everyOnPermissionsChange);
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }



    //Test Utility helpers

    private class OnPermissionsChangeBuilder{
        private OnPermissionsChange mBuiltResponse= new OnPermissionsChange();
        private HashMap<String,PermissionItem> mPermissionItems = new HashMap<>();

        public final OnPermissionsChange build(){
            mBuiltResponse.setPermissionItem(new ArrayList<>(mPermissionItems.values()));
            return mBuiltResponse;
        }

        public OnPermissionsChangeBuilder createPermissionItem(String rpcName, HMILevel[] allowedHMI){
            return createPermissionItem(rpcName, allowedHMI, null);
        }

        public OnPermissionsChangeBuilder createPermissionItemForAllHMI(String rpcName){
            createPermissionItemForAllHMI(rpcName, null);
            return this;
        }

        public  OnPermissionsChangeBuilder createPermissionItems(String[] rpcNames,HMILevel[] allowedHMI){
            for(String rpcName: rpcNames){
                createPermissionItem(rpcName, allowedHMI);
            }
            return this;
        }

        public OnPermissionsChangeBuilder createPermissionItemsForAllHMI(String[] rpcNames){
            for(String rpcName: rpcNames){
                createPermissionItemForAllHMI(rpcName);
            }
            return this;
        }

        public OnPermissionsChangeBuilder createPermissionItem(String rpcName, HMILevel[] allowedHMI, String[] permissionNames){
            if(mPermissionItems.containsKey(rpcName)){
                fail("Check Test Data, rpcName: "+rpcName+" is being created again in OnPermission item");
                return this;
            }
            PermissionItem newPermissionItem = new PermissionItem();
            newPermissionItem.setRpcName(rpcName);
            HMIPermissions hmiPermissions = new HMIPermissions();
            hmiPermissions.setAllowed(new ArrayList<HMILevel>(Arrays.asList(allowedHMI)));
            newPermissionItem.setHMIPermissions(hmiPermissions);
            mPermissionItems.put(rpcName, newPermissionItem);
            if(permissionNames !=null){
                for(String permissionParameter: permissionNames){
                    setParameterPermissions(rpcName,permissionParameter);
                }
            }
            return this;
        }

        public OnPermissionsChangeBuilder createPermissionItemForAllHMI(String rpcName, String[] permissionNames){
            HMILevel[] hmiArray= new HMILevel[] {HMILevel.HMI_BACKGROUND,HMILevel.HMI_NONE,HMILevel.HMI_LIMITED,HMILevel.HMI_FULL};
            createPermissionItem(rpcName, hmiArray, permissionNames);
            return this;
        }

        public OnPermissionsChangeBuilder setParameterPermissions(String rpcName, String permissionName){
            if(!mPermissionItems.containsKey(rpcName)){
                fail("Check Test Data, trying to set a parameter permission for not set: "+rpcName+" with permissionName");
                return this;
            }
            PermissionItem permissionHolder= mPermissionItems.get(rpcName);
            ParameterPermissions parameterPermissions = new ParameterPermissions();
            ArrayList<String> allowedParameters = new ArrayList<>();
            if(permissionHolder.getParameterPermissions() != null ){
                parameterPermissions = permissionHolder.getParameterPermissions();
                if(parameterPermissions.getAllowed()!=null){
                    allowedParameters= (ArrayList) parameterPermissions.getAllowed();
                }
            }
            allowedParameters.add(permissionName);
            parameterPermissions.setAllowed(allowedParameters);
            permissionHolder.setParameterPermissions(parameterPermissions);
            return this;
        }

    }

    private OnHMIStatus createOnHMIStatus(HMILevel hmiLevel){
        OnHMIStatus status= new OnHMIStatus();
        status.setHmiLevel(hmiLevel);
        return status;
    }

    //Dummy test data

    private OnPermissionsChange noneOnPermissionChange =
            new OnPermissionsChangeBuilder()
                    .createPermissionItemForAllHMI("DeleteSubMenu")
                    .createPermissionItemForAllHMI("AddCommand")
                    .createPermissionItemForAllHMI("DeleteCommand")
                    .build();

    private OnPermissionsChange someOnPermissionChange =
            new OnPermissionsChangeBuilder()
                    .createPermissionItemForAllHMI("ListFiles")
                    .createPermissionItemForAllHMI("AddCommand")
                    .createPermissionItemForAllHMI("DeleteFile")
                    .createPermissionItemForAllHMI("GetVehicleData", new String[]{"beltStatus"})
                    .build();

    private OnPermissionsChange allOnPermissionChange =
            new OnPermissionsChangeBuilder()
                    .createPermissionItemForAllHMI("Alert")
                    .createPermissionItemForAllHMI("ListFiles")
                    .createPermissionItemForAllHMI("DeleteFile")
                    .createPermissionItemForAllHMI("OnTouchEvent")
                    .createPermissionItemForAllHMI("Slider")
                    .createPermissionItemForAllHMI("GetVehicleData", new String[]{"beltStatus"})
                    .build();

    private OnPermissionsChange differenceBetweenHMIOnPermissionChange =
            new OnPermissionsChangeBuilder()
                    .createPermissionItem("Alert", new HMILevel[]{HMILevel.HMI_LIMITED, HMILevel.HMI_FULL})
                    .createPermissionItem("ListFiles", new HMILevel[]{HMILevel.HMI_BACKGROUND, HMILevel.HMI_FULL})
                    .createPermissionItem("DeleteFile", new HMILevel[]{HMILevel.HMI_BACKGROUND, HMILevel.HMI_FULL})
                    .createPermissionItem("GetVehicleData", new HMILevel[]{HMILevel.HMI_BACKGROUND, HMILevel.HMI_FULL}, new String[]{"beltStatus"})
                    .build();

    private EnumSet<SdlPermission> noneSet= EnumSet.noneOf(SdlPermission.class);
    private EnumSet<SdlPermission> someSet= EnumSet.of(SdlPermission.DeleteFile, new SdlPermission[]{ SdlPermission.ListFiles, SdlPermission.GetBeltStatus});
    private EnumSet<SdlPermission> allSet= getStandardSdlFilter().permissionSet.permissions.get(0);

    private SdlPermissionFilter getStandardSdlFilter(){
        SdlPermissionFilter filter = new SdlPermissionFilter();
        filter.addPermission(SdlPermission.Alert);
        filter.addPermission(SdlPermission.ListFiles);
        filter.addPermission(SdlPermission.DeleteFile);
        filter.addPermission(SdlPermission.GetBeltStatus);
        return filter;
    }

    private String[] allPermissions= new String[]{"accPedalPosition",
            "airbagStatus","beltStatus","bodyInformation",
            "clusterModeStatus","deviceStatus","driverBraking","eCallInfo","emergencyEvent",
            "engineTorque","externalTemperature","fuelLevel","fuelLevel_State","gps","headLampStatus",
            "instantFuelConsumption","myKey","odometer","prndl","rpm","steeringWheelAngle", "speed",
            "tirePressure","vin","wiperStatus"};

    private OnPermissionsChange everyOnPermissionsChange =
            new OnPermissionsChangeBuilder()
                    .createPermissionItemsForAllHMI(new String [] {"AddCommand",
                        "AddSubMenu","Alert","AlertManeuver","ChangeRegistration",
                        "CreateInteractionChoiceSet","DeleteCommand","DeleteFile",
                        "DeleteInteractionChoiceSet","DeleteSubMenu","DiagnosticMessage",
                        "EncodedSyncPData","EndAudioPassThru","GenericResponse","ListFiles",
                        "OnAppInterfaceUnregistered","OnAudioPassThru","OnButtonEvent","OnButtonPress",
                        "OnCommand","OnDriverDistraction","OnEncodedSyncPData","OnHMIStatus",
                        "OnHashChange","OnKeyboardInput","OnLanguageChange","OnPermissionsChange",
                        "OnSystemRequest","OnTouchEvent","PerformAudioPassThru","PerformInteraction",
                        "PutFile","RegisterAppInterface","ResetGlobalProperties","ScrollableMessage",
                        "SetAppIcon","SetDisplayLayout","SetGlobalProperties","SetMediaClockTimer",
                        "Show","ShowConstantTBT","Slider","Speak","SubscribeButton","SystemRequest",
                        "UnregisterAppInterface","UnsubscribeButton","ReadDID", "GetDTCs"})
                    .createPermissionItemForAllHMI("GetVehicleData",allPermissions)
                    .createPermissionItemForAllHMI("SubscribeVehicleData", allPermissions)
                    .createPermissionItemForAllHMI("OnVehicleData",allPermissions)
                    .createPermissionItemForAllHMI("UnsubscribeVehicleData",allPermissions)
                    .build();

    private EnumSet<SdlPermission> everySet= getEveryPermissionFilter().permissionSet.permissions.get(0);

    private SdlPermissionFilter getEveryPermissionFilter(){
        SdlPermissionFilter filter = new SdlPermissionFilter();
        for(SdlPermission permission:SdlPermission.values()){
            filter.addPermission(permission);
        }
        return filter;
    }

    private void checkStandardPermissionsEvent(SdlPermissionEvent event, SdlPermissionEvent.PermissionLevel verifyLevel){
        assertThat(event.getPermissionLevel(), is(verifyLevel));
        switch (verifyLevel) {
            case ALL:
                assertThat(event.getPermissions(),is(allSet));
                break;
            case SOME:
                assertThat(event.getPermissions(),is(someSet));
                break;
            case NONE:
                assertThat(event.getPermissions(),is(noneSet));
                break;
        }

    }



}