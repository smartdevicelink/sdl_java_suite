package com.smartdevicelink.managers.permission;

import androidx.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.smartdevicelink.managers.permission.BasePermissionManager.PERMISSION_GROUP_STATUS_DISALLOWED;
import static com.smartdevicelink.managers.permission.PermissionManager.PERMISSION_GROUP_STATUS_ALLOWED;
import static com.smartdevicelink.managers.permission.PermissionManager.PERMISSION_GROUP_STATUS_MIXED;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class PermissionManagerTests {

    private OnRPCNotificationListener onHMIStatusListener, onPermissionsChangeListener;
    private PermissionManager permissionManager;
    private int listenerCalledCounter;


    @Before
    public void setUp() throws Exception {


        // Mock Isdl and its behaviour to use it for PermissionManager testing
        ISdl internalInterface = mock(ISdl.class);


        // When internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, OnRPCNotificationListener) is called
        // inside PermissionManager's constructor, then keep a reference to the OnRPCNotificationListener so we can trigger it later
        // to emulate what Core does when it sends OnHMIStatus notification
        Answer<Void> onHMIStatusAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                onHMIStatusListener = (OnRPCNotificationListener) args[1];
                return null;
            }
        };
        doAnswer(onHMIStatusAnswer).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCNotificationListener.class));


        // When internalInterface.addOnRPCNotificationListener(FunctionID.ON_PERMISSIONS_CHANGE, OnRPCNotificationListener) is called
        // inside PermissionManager's constructor, then keep a reference to the onPermissionsChangeListener so we can trigger it later
        // to emulate what Core does when it sends OnPermissionsChange notification
        Answer<Void> onPermissionsChangeAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                onPermissionsChangeListener = (OnRPCNotificationListener) args[1];
                return null;
            }
        };
        doAnswer(onPermissionsChangeAnswer).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_PERMISSIONS_CHANGE), any(OnRPCNotificationListener.class));


        // Initialize PermissionManager
        permissionManager = new PermissionManager(internalInterface);
    }

    // Emulate what happens when Core sends OnHMIStatus notification
    private void sendFakeCoreOnHMIStatusNotifications(HMILevel hmiLevel) {
        if (hmiLevel != null) {
            OnHMIStatus onHMIStatusFakeNotification = new OnHMIStatus();
            onHMIStatusFakeNotification.setHmiLevel(hmiLevel);
            onHMIStatusListener.onNotified(onHMIStatusFakeNotification);
        }
    }

    // Emulate what happens when Core sends OnPermissionsChange notification
    private void sendFakeCoreOnPermissionsChangeNotifications(List<PermissionItem> permissionItems) {
        if (permissionItems != null) {
            OnPermissionsChange onPermissionChangeFakeNotification = new OnPermissionsChange();
            onPermissionChangeFakeNotification.setPermissionItem(permissionItems);
            onPermissionsChangeListener.onNotified(onPermissionChangeFakeNotification);
        }
    }

    // Test adding a listener to be called when ALL of the specified permissions become allowed
    @Test
    public void testListenersAllAllowed() {
        listenerCalledCounter = 0;


        // Test how developers can add listeners through PermissionManager
        List<PermissionElement> permissionElements = new ArrayList<>();
        permissionElements.add(new PermissionElement(FunctionID.SHOW, null));
        permissionElements.add(new PermissionElement(FunctionID.GET_VEHICLE_DATA, Arrays.asList("rpm", "airbagStatus")));
        permissionManager.addListener(permissionElements, PermissionManager.PERMISSION_GROUP_TYPE_ALL_ALLOWED, new OnPermissionChangeListener() {
            @Override
            public void onPermissionsChange(@NonNull Map<FunctionID, PermissionStatus> allowedPermissions, @NonNull int permissionGroupStatus) {
            // Make sure is the actual result matches the expected one
            assertEquals(PERMISSION_GROUP_STATUS_ALLOWED, permissionGroupStatus);
            assertTrue(allowedPermissions.get(FunctionID.SHOW).getIsRPCAllowed());
            assertTrue(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getIsRPCAllowed());
            assertTrue(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getAllowedParameters().get("rpm"));
            assertTrue(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getAllowedParameters().get("airbagStatus"));
            listenerCalledCounter++;
            }
        });


        // Emulate Core's behaviour by sending OnHMIStatus notification
        sendFakeCoreOnHMIStatusNotifications(HMILevel.HMI_LIMITED);


        // Emulate Core's behaviour by sending OnPermissionsChange notification
        List<PermissionItem> permissionItems = new ArrayList<>();
        PermissionItem permissionItem1 = new PermissionItem();
        permissionItem1.setRpcName(FunctionID.SHOW.toString());
        permissionItem1.setHMIPermissions(new HMIPermissions(Arrays.asList(HMILevel.HMI_BACKGROUND, HMILevel.HMI_FULL, HMILevel.HMI_LIMITED), new ArrayList<HMILevel>()));
        permissionItem1.setParameterPermissions(new ParameterPermissions(new ArrayList<String>(), new ArrayList<String>()));
        permissionItems.add(permissionItem1);
        PermissionItem permissionItem2 = new PermissionItem();
        permissionItem2.setRpcName(FunctionID.GET_VEHICLE_DATA.toString());
        permissionItem2.setHMIPermissions(new HMIPermissions(Arrays.asList(HMILevel.HMI_BACKGROUND, HMILevel.HMI_FULL), new ArrayList<HMILevel>()));
        permissionItem2.setParameterPermissions(new ParameterPermissions(Arrays.asList("rpm", "airbagStatus"), new ArrayList<String>()));
        permissionItems.add(permissionItem2);
        sendFakeCoreOnPermissionsChangeNotifications(permissionItems);


        // Emulate Core's behaviour by sending OnHMIStatus notification
        sendFakeCoreOnHMIStatusNotifications(HMILevel.HMI_FULL);


        // Make sure the listener is called exactly once
        assertEquals("Listener was not called or called more/less frequently than expected", 1, listenerCalledCounter);
    }


    // Test adding a listener to be called when ANY of the specified permissions become allowed
    @Test
    public void testListenersAnyAllowed() {
        listenerCalledCounter = 0;


        // Emulate Core's behaviour by sending OnHMIStatus notification
        sendFakeCoreOnHMIStatusNotifications(HMILevel.HMI_NONE);


        // Test how developers can add listeners through PermissionManager
        List<PermissionElement> permissionElements = new ArrayList<>();
        permissionElements.add(new PermissionElement(FunctionID.SHOW, null));
        permissionElements.add(new PermissionElement(FunctionID.GET_VEHICLE_DATA, Arrays.asList("rpm", "airbagStatus")));
        permissionManager.addListener(permissionElements, PermissionManager.PERMISSION_GROUP_TYPE_ANY, new OnPermissionChangeListener() {
            @Override
            public void onPermissionsChange(@NonNull Map<FunctionID, PermissionStatus> allowedPermissions, @NonNull int permissionGroupStatus) {
                // Make sure is the actual result matches the expected one
                if (listenerCalledCounter == 0) { // Listener called for the first time
                    assertEquals(PERMISSION_GROUP_STATUS_DISALLOWED, permissionGroupStatus);
                    assertFalse(allowedPermissions.get(FunctionID.SHOW).getIsRPCAllowed());
                    assertFalse(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getIsRPCAllowed());
                    assertFalse(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getAllowedParameters().get("rpm"));
                    assertFalse(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getAllowedParameters().get("airbagStatus"));
                } else if (listenerCalledCounter == 1) { // Listener called for the first time
                    assertEquals(PERMISSION_GROUP_STATUS_MIXED, permissionGroupStatus);
                    assertTrue(allowedPermissions.get(FunctionID.SHOW).getIsRPCAllowed());
                    assertFalse(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getIsRPCAllowed());
                    assertFalse(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getAllowedParameters().get("rpm"));
                    assertFalse(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getAllowedParameters().get("airbagStatus"));
                } else if (listenerCalledCounter == 2) {  // Listener called for the third time
                    assertEquals(PERMISSION_GROUP_STATUS_ALLOWED, permissionGroupStatus);
                    assertTrue(allowedPermissions.get(FunctionID.SHOW).getIsRPCAllowed());
                    assertTrue(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getIsRPCAllowed());
                    assertTrue(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getAllowedParameters().get("rpm"));
                    assertTrue(allowedPermissions.get(FunctionID.GET_VEHICLE_DATA).getAllowedParameters().get("airbagStatus"));
                }
                listenerCalledCounter++;
            }
        });


        // Emulate Core's behaviour by sending OnHMIStatus notification
        sendFakeCoreOnHMIStatusNotifications(HMILevel.HMI_LIMITED);


        // Emulate Core's behaviour by sending OnPermissionsChange notification
        List<PermissionItem> permissionItems = new ArrayList<>();
        PermissionItem permissionItem1 = new PermissionItem();
        permissionItem1.setRpcName(FunctionID.SHOW.toString());
        permissionItem1.setHMIPermissions(new HMIPermissions(Arrays.asList(HMILevel.HMI_BACKGROUND, HMILevel.HMI_FULL, HMILevel.HMI_LIMITED), new ArrayList<HMILevel>()));
        permissionItem1.setParameterPermissions(new ParameterPermissions(new ArrayList<String>(), new ArrayList<String>()));
        permissionItems.add(permissionItem1);
        PermissionItem permissionItem2 = new PermissionItem();
        permissionItem2.setRpcName(FunctionID.GET_VEHICLE_DATA.toString());
        permissionItem2.setHMIPermissions(new HMIPermissions(Arrays.asList(HMILevel.HMI_BACKGROUND, HMILevel.HMI_FULL), new ArrayList<HMILevel>()));
        permissionItem2.setParameterPermissions(new ParameterPermissions(Arrays.asList("rpm", "airbagStatus"), new ArrayList<String>()));
        permissionItems.add(permissionItem2);
        sendFakeCoreOnPermissionsChangeNotifications(permissionItems);


        // Emulate Core's behaviour by sending OnHMIStatus notification
        sendFakeCoreOnHMIStatusNotifications(HMILevel.HMI_FULL);


        // Make sure the the listener is called exactly twice
        assertEquals("Listener was not called or called more/less frequently than expected", 3, listenerCalledCounter);
    }
}
