package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
        assertThat(checkEvent.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.ALL));
    }

    @Test
    public void testSomePermissionsAvailableOnAdd(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(someOnPermissionChange);

        SdlPermissionEvent checkEvent= mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
            }
        }, getStandardSdlFilter());
        assertThat(checkEvent.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.SOME));
    }

    @Test
    public void testNonePermissionsAvailableOnAdd(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(noneOnPermissionChange);

        SdlPermissionEvent checkEvent= mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
            }
        }, getStandardSdlFilter());
        assertThat(checkEvent.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.NONE));
    }


    @Test
    public void testNoPermissionsPresentToAll(){
        mSdlPermissionManager.mPermissionChangeListener.onNotified(noneOnPermissionChange);
        mSdlPermissionManager.addListener(new SdlPermissionListener() {
            @Override
            public void onPermissionChanged(@NonNull SdlPermissionEvent event) {
                assertThat(event.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.ALL));
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
                assertThat(event.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.SOME));
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
                assertThat(event.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.NONE));
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
                assertThat(event.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.ALL));
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
                assertThat(event.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.NONE));
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
                assertThat(event.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.ALL));
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
                assertThat(event.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.SOME));
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
                assertThat(event.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.NONE));
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
                assertThat(event.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.ALL));
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
                assertThat(event.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.NONE));
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
                assertThat(event.getPermissionLevel(), is(SdlPermissionEvent.PermissionLevel.SOME));
            }
        }, getStandardSdlFilter());

        SdlPermissionListener mockListener= mock(SdlPermissionListener.class);
        mSdlPermissionManager.addListener(mockListener, getStandardSdlFilter());

        mSdlPermissionManager.mHMIStatusListener.onNotified(createOnHMIStatus(HMILevel.HMI_BACKGROUND));
        verify(mockListener,times(1)).onPermissionChanged(any(SdlPermissionEvent.class));
    }



    //Test Utility helpers

    private class OnPermissionsChangeBuilder{
        private OnPermissionsChange mBuiltResponse= new OnPermissionsChange();

        public final OnPermissionsChange build(){
            return mBuiltResponse;
        }

        public OnPermissionsChangeBuilder createPermissionItem(String rpcName, HMILevel[] allowedHMI){
            PermissionItem newPermissionItem = new PermissionItem();
            newPermissionItem.setRpcName(rpcName);
            HMIPermissions hmiPermissions= new HMIPermissions();
            hmiPermissions.setAllowed(new ArrayList<HMILevel>(Arrays.asList(allowedHMI)));
            newPermissionItem.setHMIPermissions(hmiPermissions);
            ArrayList<PermissionItem> tempList = (ArrayList) mBuiltResponse.getPermissionItem();
            if(tempList ==null){
                tempList= new ArrayList<>();
            }
            tempList.add(newPermissionItem);
            mBuiltResponse.setPermissionItem(tempList);
            return this;
        }

        public  OnPermissionsChangeBuilder createPermissionItems(Collection<String> rpcNames,HMILevel[] allowedHMI){
            for(String rpcName: rpcNames){
                createPermissionItem(rpcName,allowedHMI);
            }
            return this;
        }

        public OnPermissionsChangeBuilder createPermissionItemForAllHMI(String rpcName){
            HMILevel[] hmiArray= new HMILevel[] {HMILevel.HMI_BACKGROUND,HMILevel.HMI_NONE,HMILevel.HMI_LIMITED,HMILevel.HMI_FULL};
            createPermissionItem(rpcName,hmiArray);
            return this;
        }

        public OnPermissionsChangeBuilder createPermissionItemsForAllHMI(Collection<String> rpcNames){
            for(String rpcName: rpcNames){
                createPermissionItemForAllHMI(rpcName);
            }
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
                    .createPermissionItemForAllHMI("Alert")
                    .createPermissionItemForAllHMI("ListFiles")
                    .createPermissionItemForAllHMI("AddCommand")
                    .createPermissionItemForAllHMI("DeleteCommand")
                    .build();

    private OnPermissionsChange allOnPermissionChange =
            new OnPermissionsChangeBuilder()
                    .createPermissionItemForAllHMI("Alert")
                    .createPermissionItemForAllHMI("ListFiles")
                    .createPermissionItemForAllHMI("DeleteFile")
                    .createPermissionItemForAllHMI("OnTouchEvent")
                    .createPermissionItemForAllHMI("Slider")
                    .build();

    private OnPermissionsChange differenceBetweenHMIOnPermissionChange =
            new OnPermissionsChangeBuilder()
                    .createPermissionItem("Alert", new HMILevel[]{HMILevel.HMI_LIMITED, HMILevel.HMI_FULL})
                    .createPermissionItem("ListFiles", new HMILevel[]{HMILevel.HMI_BACKGROUND, HMILevel.HMI_FULL})
                    .createPermissionItem("DeleteFile", new HMILevel[]{HMILevel.HMI_BACKGROUND, HMILevel.HMI_FULL})
                    .build();

    private SdlPermissionFilter getStandardSdlFilter(){
        SdlPermissionFilter filter = new SdlPermissionFilter();
        filter.addPermission(SdlPermission.Alert);
        filter.addPermission(SdlPermission.ListFiles);
        filter.addPermission(SdlPermission.DeleteFile);
        return filter;
    }



}