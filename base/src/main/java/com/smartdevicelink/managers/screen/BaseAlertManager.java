package com.smartdevicelink.managers.screen;

import android.util.Log;

import androidx.annotation.NonNull;

import com.livio.taskmaster.Queue;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.managers.permission.OnPermissionChangeListener;
import com.smartdevicelink.managers.permission.PermissionElement;
import com.smartdevicelink.managers.permission.PermissionManager;
import com.smartdevicelink.managers.permission.PermissionStatus;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class BaseAlertManager extends BaseSubManager {

    private static final String TAG = "BaseAlertManager";
    Queue transactionQueue;
    WindowCapability defaultMainWindowCapability;
    private OnSystemCapabilityListener onDisplaysCapabilityListener;
    private UUID permissionListener;
    private boolean currentAlertPermissionStatus = false;
    private final WeakReference<FileManager> fileManager;
    private final WeakReference<PermissionManager> permissionManager;
    private int nextCancelId;
    final int alertCancelIdMin = 1;

    public BaseAlertManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager, @NonNull PermissionManager permissionManager) {
        super(internalInterface);
        this.transactionQueue = newTransactionQueue();
        this.fileManager = new WeakReference<>(fileManager);
        this.permissionManager = new WeakReference<>(permissionManager);
        nextCancelId = alertCancelIdMin;
        addListeners();
    }

    @Override
    public void start(CompletionListener listener) {
        transitionToState(READY);
        super.start(listener);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void presentAlert(AlertView alert, CompletionListener listener) {
        if (getState() == ERROR) {
            DebugTool.logWarning(TAG, "Alert Manager In Error State");
            return;
        }

        if(!BaseScreenManager.checkAndAssignButtonIds(alert.getSoftButtons(), BaseScreenManager.SoftButtonLocation.ALERT_MANAGER)){
            DebugTool.logError(TAG, "Attempted to set soft button objects for Alert, but multiple buttons had the same id.");
            return;
        }

        PresentAlertOperation operation = new PresentAlertOperation(internalInterface, alert, defaultMainWindowCapability, fileManager.get(), nextCancelId++, listener);
        transactionQueue.add(operation, false);

    }

    private Queue newTransactionQueue() {
        Queue queue = internalInterface.getTaskmaster().createQueue("AlertManager", 4, false);
        queue.pause();
        return queue;
    }

    // Suspend the queue if the WindowCapabilities are null
    // OR if the HMI level is NONE since we want to delay sending RPCs until we're in non-NONE
    private void updateTransactionQueueSuspended() {
        if (!currentAlertPermissionStatus || defaultMainWindowCapability == null) {
            DebugTool.logInfo(TAG, String.format("Suspending the transaction queue. Current permission status is false: %b, window capabilities are null: %b", currentAlertPermissionStatus, defaultMainWindowCapability == null));
            transactionQueue.pause();
        } else {
            DebugTool.logInfo(TAG, "Starting the transaction queue");
            transactionQueue.resume();
        }
    }


    private void addListeners() {
        onDisplaysCapabilityListener = new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                // instead of using the parameter it's more safe to use the convenience method
                List<DisplayCapability> capabilities = SystemCapabilityManager.convertToList(capability, DisplayCapability.class);
                if (capabilities == null || capabilities.size() == 0) {
                    defaultMainWindowCapability = null;
                } else {
                    DisplayCapability display = capabilities.get(0);
                    for (WindowCapability windowCapability : display.getWindowCapabilities()) {
                        int currentWindowID = windowCapability.getWindowID() != null ? windowCapability.getWindowID() : PredefinedWindows.DEFAULT_WINDOW.getValue();
                        if (currentWindowID == PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                            defaultMainWindowCapability = windowCapability;
                            continue;
                        }
                    }
                }
                // Update the queue's suspend state
                updateTransactionQueueSuspended();
            }

            @Override
            public void onError(String info) {
                DebugTool.logError(TAG, "Display Capability cannot be retrieved");
                defaultMainWindowCapability = null;
                updateTransactionQueueSuspended();
            }
        };
        if (internalInterface.getSystemCapabilityManager() != null) {
            this.internalInterface.getSystemCapabilityManager().addOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplaysCapabilityListener);
        }

        PermissionElement alertPermissionElement = new PermissionElement(FunctionID.ALERT, null);
        permissionListener = permissionManager.get().addListener(Collections.singletonList(alertPermissionElement), permissionManager.get().PERMISSION_GROUP_TYPE_ANY, new OnPermissionChangeListener() {
            @Override
            public void onPermissionsChange(@NonNull Map<FunctionID, PermissionStatus> allowedPermissions, int permissionGroupStatus) {
                currentAlertPermissionStatus = allowedPermissions.get(FunctionID.ALERT).getIsRPCAllowed();
                updateTransactionQueueSuspended();
            }
        });
    }
}
