package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import junit.framework.TestListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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

    public SdlPermissionManagerTest(){

    }

    @Before
    public void setUp() throws Exception {
        mSdlPermissionManager= new SdlPermissionManager();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAllPermissionsAvailableOnAdd(){
        mSdlPermissionManager.onPermissionChange(allOnPermissionChange);
        //utilizing the test listener in this case to double check the output is ok
        SdlTestListener listener = new SdlTestListener(allEnumSet, SdlPermissionEvent.PermissionLevel.ALL);
        SdlPermissionEvent checkEvent= mSdlPermissionManager.addListener(listener, getStandardSdlFilter());
        listener.checkPermissionEventAgainstSet(checkEvent);
    }

    @Test
    public void testSomePermissionsAvailableOnAdd(){
        mSdlPermissionManager.onPermissionChange(someOnPermissionChange);
        SdlTestListener listener = new SdlTestListener(someEnumSet, SdlPermissionEvent.PermissionLevel.SOME);
        SdlPermissionEvent checkEvent= mSdlPermissionManager.addListener(listener, getStandardSdlFilter());
        listener.checkPermissionEventAgainstSet(checkEvent);
    }

    @Test
    public void testNonePermissionsAvailableOnAdd(){
        mSdlPermissionManager.onPermissionChange(noneOnPermissionChange);
        SdlTestListener listener = new SdlTestListener(noneEnumSet, SdlPermissionEvent.PermissionLevel.NONE);
        SdlPermissionEvent checkEvent= mSdlPermissionManager.addListener(listener, getStandardSdlFilter());
        listener.checkPermissionEventAgainstSet(checkEvent);

    }


    @Test
    public void testNoPermissionsPresentToAll(){
        mSdlPermissionManager.onPermissionChange(noneOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlTestListener(allEnumSet, SdlPermissionEvent.PermissionLevel.ALL), getStandardSdlFilter());
        startPermissionChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), allOnPermissionChange);
    }

    @Test
    public void testNoPermissionsPresentToSome(){
        mSdlPermissionManager.onPermissionChange(noneOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlTestListener(someEnumSet, SdlPermissionEvent.PermissionLevel.SOME), getStandardSdlFilter());
        startPermissionChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), someOnPermissionChange);


    }

    @Test
    public void testSomePermissionsPresentToNone(){
        mSdlPermissionManager.onPermissionChange(someOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlTestListener(noneEnumSet, SdlPermissionEvent.PermissionLevel.NONE), getStandardSdlFilter());
        startPermissionChangeEvent(mSdlPermissionManager,getStandardSdlFilter(),noneOnPermissionChange);

    }

    @Test
    public void testSomePermissionsPresentToAll(){
        mSdlPermissionManager.onPermissionChange(someOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlTestListener(allEnumSet, SdlPermissionEvent.PermissionLevel.ALL), getStandardSdlFilter());
        startPermissionChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), allOnPermissionChange);
    }

    @Test
    public void testAllPermissionsPresentToNone(){
        mSdlPermissionManager.onPermissionChange(allOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlTestListener(noneEnumSet, SdlPermissionEvent.PermissionLevel.NONE), getStandardSdlFilter());
        startPermissionChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), noneOnPermissionChange);

    }

    @Test
    public void testNoNotificationReceivedWhenPermissionsSameBetweenHMI(){
        mSdlPermissionManager.onPermissionChange(allOnPermissionChange);

        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());

        mSdlPermissionManager.onHmi(HMILevel.HMI_BACKGROUND);
        verify(mockListener,never()).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    @Test
    public void testNoPermissionsToAllHMILevelChange(){
        mSdlPermissionManager.onPermissionChange(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlTestListener(allEnumSet, SdlPermissionEvent.PermissionLevel.ALL), getStandardSdlFilter());
        startHMIChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), HMILevel.HMI_FULL);
    }

    @Test
    public void testNoPermissionsToSomeHMILevelChange(){
        mSdlPermissionManager.onPermissionChange(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlTestListener(someEnumSet, SdlPermissionEvent.PermissionLevel.SOME), getStandardSdlFilter());
        startHMIChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), HMILevel.HMI_BACKGROUND);

    }

    @Test
    public void testSomePermissionsToNoneHMILevelChange(){
        mSdlPermissionManager.onPermissionChange(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.onHmi(HMILevel.HMI_BACKGROUND);
        mSdlPermissionManager.addListener(new SdlTestListener(noneEnumSet, SdlPermissionEvent.PermissionLevel.NONE), getStandardSdlFilter());
        startHMIChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), HMILevel.HMI_NONE);

    }

    @Test
    public void testSomePermissionsToAllHMILevelChange(){
        mSdlPermissionManager.onPermissionChange(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.onHmi(HMILevel.HMI_BACKGROUND);
        mSdlPermissionManager.addListener(new SdlTestListener(allEnumSet, SdlPermissionEvent.PermissionLevel.ALL), getStandardSdlFilter());
        startHMIChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), HMILevel.HMI_FULL);

    }

    @Test
    public void testAllPermissionsToNoneHMILevelChange(){
        mSdlPermissionManager.onPermissionChange(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.onHmi(HMILevel.HMI_FULL);
        mSdlPermissionManager.addListener(new SdlTestListener(noneEnumSet, SdlPermissionEvent.PermissionLevel.NONE), getStandardSdlFilter());
        startHMIChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), HMILevel.HMI_NONE);

    }

    @Test
    public void testAllPermissionsToSomeHMILevelChange(){
        mSdlPermissionManager.onPermissionChange(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.onHmi(HMILevel.HMI_FULL);
        mSdlPermissionManager.addListener(new SdlTestListener(someEnumSet, SdlPermissionEvent.PermissionLevel.SOME), getStandardSdlFilter());
        startHMIChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), HMILevel.HMI_BACKGROUND);
    }


    @Test
    public void testEveryPermissionsFromNoneToAll(){
        mSdlPermissionManager.addListener(new SdlTestListener(everySet, SdlPermissionEvent.PermissionLevel.ALL), getEveryPermissionFilter());
        startPermissionChangeEvent(mSdlPermissionManager, getEveryPermissionFilter(), everyOnPermissionsChange);
    }

    @Test
    public void testThatCorrectListenersReceivePermissionChangeEvent(){
        mSdlPermissionManager.addListener(new SdlTestListener(someEnumSet, SdlPermissionEvent.PermissionLevel.SOME),getStandardSdlFilter());
        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getNonMatchingSdlFilter());
        startPermissionChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), someOnPermissionChange);
        verify(mockListener,never()).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    @Test
    public void testThatCorrectListenersReceiveHMIChangeEvent(){
        mSdlPermissionManager.onPermissionChange(differenceBetweenHMIOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlTestListener(someEnumSet, SdlPermissionEvent.PermissionLevel.SOME), getStandardSdlFilter());
        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getNonMatchingSdlFilter());
        startHMIChangeEvent(mSdlPermissionManager, getStandardSdlFilter(), HMILevel.HMI_BACKGROUND);
        verify(mockListener,never()).onPermissionChanged(any(SdlPermissionEvent.class));
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
            hmiPermissions.setAllowed(new ArrayList<>(Arrays.asList(allowedHMI)));
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

    private EnumSet<SdlPermission> noneEnumSet = EnumSet.noneOf(SdlPermission.class);
    private EnumSet<SdlPermission> someEnumSet = EnumSet.of(SdlPermission.DeleteFile, new SdlPermission[]{SdlPermission.ListFiles, SdlPermission.GetBeltStatus});
    private EnumSet<SdlPermission> allEnumSet = getStandardSdlFilter().permissionSet.permissions.get(0);

    //filter created that fits the above OnPermissionChange objects
    private SdlPermissionFilter getStandardSdlFilter(){
        SdlPermissionFilter filter = new SdlPermissionFilter();
        filter.addPermission(SdlPermission.Alert);
        filter.addPermission(SdlPermission.ListFiles);
        filter.addPermission(SdlPermission.DeleteFile);
        filter.addPermission(SdlPermission.GetBeltStatus);
        return filter;
    }

    //filter used to not match any of the above OnPermissionChange objects
    private SdlPermissionFilter getNonMatchingSdlFilter(){
        SdlPermissionFilter filter = new SdlPermissionFilter();
        filter.addPermission(SdlPermission.Speak);
        filter.addPermission(SdlPermission.AlertManeuver);
        return filter;
    }

    private String[] allParameterPermissions = new String[]{"accPedalPosition",
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
                    .createPermissionItemForAllHMI("GetVehicleData", allParameterPermissions)
                    .createPermissionItemForAllHMI("SubscribeVehicleData", allParameterPermissions)
                    .createPermissionItemForAllHMI("OnVehicleData", allParameterPermissions)
                    .createPermissionItemForAllHMI("UnsubscribeVehicleData", allParameterPermissions)
                    .build();

    private EnumSet<SdlPermission> everySet= getEveryPermissionFilter().permissionSet.permissions.get(0);

    private SdlPermissionFilter getEveryPermissionFilter(){
        SdlPermissionFilter filter = new SdlPermissionFilter();
        for(SdlPermission permission:SdlPermission.values()){
            filter.addPermission(permission);
        }
        return filter;
    }

    //Ensures that the callback event matches what we initialize the listener to check
    private class SdlTestListener implements SdlPermissionListener{

        final EnumSet<SdlPermission> mPermissionSet;
        final SdlPermissionEvent.PermissionLevel mPermissionLevel;


        public SdlTestListener(EnumSet<SdlPermission> expectedResult, SdlPermissionEvent.PermissionLevel expectedLevel){
            mPermissionSet= expectedResult;
            mPermissionLevel= expectedLevel;
        }

        @Override
        public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
            checkPermissionEventAgainstSet(event);
        }

        public void checkPermissionEventAgainstSet(SdlPermissionEvent event){
            assertThat(event.getPermissions(), is(mPermissionSet));
            assertThat(event.getPermissionLevel(), is(mPermissionLevel));
        }
    }

    private void startPermissionChangeEvent(SdlPermissionManager manager, SdlPermissionFilter filter, OnPermissionsChange changeToSend){
        //mock listener to ensure that we do not accidentally pass a test where the listener callback is not called
        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        manager.addListener(mockListener, filter);
        manager.onPermissionChange(changeToSend);
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }

    private void startHMIChangeEvent(SdlPermissionManager manager, SdlPermissionFilter filter, HMILevel changeToSend){
        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        manager.addListener(mockListener, filter);
        manager.onHmi(changeToSend);
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }


}