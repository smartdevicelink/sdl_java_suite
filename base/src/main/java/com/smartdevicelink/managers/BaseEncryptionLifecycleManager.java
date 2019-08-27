package com.smartdevicelink.managers;

import android.support.annotation.NonNull;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import java.util.*;

public class BaseEncryptionLifecycleManager extends BaseSubManager {
    private ISdl internalInterface;
    private OnRPCNotificationListener onPermissionsChangeListener;
    private OnRPCNotificationListener onHMIStatusListener;
    private CompletionListener securedServiceStartCallback;
    private HMILevel currentHMILevel;
    private Set<String> encryptionRequiredRPCs = new HashSet<>();
    private int currentState;
    private boolean isAppLevelEncryptionRequired;

    BaseEncryptionLifecycleManager(ISdl isdl) {
        super(isdl);
        internalInterface = isdl;
        currentState = SHUTDOWN;

        onHMIStatusListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                    return;
                }
                currentHMILevel = onHMIStatus.getHmiLevel();
                if (currentHMILevel != HMILevel.HMI_NONE) {
                    startEncryptedRPCService(new CompletionListener() {
                        @Override
                        public void onComplete(boolean success) {
                            //do nothing
                        }
                    });
                }
            }
        };

        onPermissionsChangeListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                List<PermissionItem> permissionItems = ((OnPermissionsChange) notification).getPermissionItem();
                Set<String> rpcSet = new HashSet<>();
                isAppLevelEncryptionRequired = Boolean.TRUE.equals(((OnPermissionsChange) notification).getRequireEncryption());
                if (permissionItems != null && !permissionItems.isEmpty()) {
                    for (PermissionItem permissionItem : permissionItems) {
                        if (permissionItem != null) {
                            if (isAppLevelEncryptionRequired && Boolean.TRUE.equals(permissionItem.getEncryptionRequirement())) {
                                String rpcName = permissionItem.getRpcName();
                                rpcSet.add(rpcName);
                            }
                        }
                    }
                }
                if (!rpcSet.isEmpty()) {
                    if (currentState == SHUTDOWN) {
                        startEncryptedRPCService(new CompletionListener() {
                            @Override
                            public void onComplete(boolean success) {
                                //do nothing
                            }
                        });
                    }
                    if (!encryptionRequiredRPCs.equals(rpcSet)) {
                        encryptionRequiredRPCs = rpcSet;
                    }
                }
            }
        };

        internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, onHMIStatusListener);
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_PERMISSIONS_CHANGE, onPermissionsChangeListener);
        internalInterface.addServiceListener(SessionType.RPC, serviceListener);
    }

    /**
     * Gets the app level encryption requirement
     * @return true if encryption is required for app level; false otherwise
     */
    public boolean getRequiresEncryption() {
        return isAppLevelEncryptionRequired;
    }

    /**
     * Checks if an RPC requires encryption
     * @param rpcName the rpc name to check
     * @return true if the given RPC requires encryption; false, otherwise
     */
    public boolean getRPCRequiresEncryption(@NonNull FunctionID rpcName) {
        return encryptionRequiredRPCs.contains(rpcName.toString());
    }

    /**
     * Starts a secured service.
     *
     * @param listener Used to make a call back to caller on status of the secured service being started
     */
    public void startEncryptedRPCService(@NonNull CompletionListener listener) {
        securedServiceStartCallback = listener;
        if (currentState == READY) {
            listener.onComplete(true);
            return;
        } else if (currentState == SETTING_UP) {
            //do nothing a secured service is already being  started
        } else if (currentState == SHUTDOWN) {
            if (currentHMILevel != HMILevel.HMI_NONE && !encryptionRequiredRPCs.isEmpty()) {
                currentState = SETTING_UP;
                //TODO: start secured service, callback is made in serviceListener
            }
        }
    }

    /**
     * Stops the secured service
     *
     * @param sessionID the session Id of the secured service to stop
     */
    public void stopEncryptedRPCService(byte sessionID) {
        currentState = SHUTDOWN;
        //TODO: stop secured RPC service
    }

    /**
     * Check to see if a secured service is ready to use
     *
     * @return true if there is a secured service; false otherwise
     */
    public boolean isEncryptionReady() {
        return currentState == READY;
    }

    @Override
    public void start(CompletionListener listener) {
        super.start(listener);
    }

    @Override
    public void dispose() {
        currentState = SHUTDOWN;
        isAppLevelEncryptionRequired = false;
        encryptionRequiredRPCs.clear();
        super.dispose();
    }

    private ISdlServiceListener serviceListener = new ISdlServiceListener() {
        @Override
        public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {
            if (SessionType.RPC.equals(type) && session != null) {
                if (isEncrypted) {
                    currentState = READY;
                }
                if (securedServiceStartCallback != null) {
                    securedServiceStartCallback.onComplete(isEncrypted);
                }
                DebugTool.logInfo("RPC service started, secured?: " + isEncrypted);
            }
        }

        @Override
        public void onServiceEnded(SdlSession session, SessionType type) {
            if (SessionType.RPC.equals(type) && session != null) {
                currentState = SHUTDOWN;
            }
            DebugTool.logInfo("onServiceEnded, session id: " + (session == null ? "session null" : session.getSessionId())
                    + ", session type: " + type.getName());
        }

        @Override
        public void onServiceError(SdlSession session, SessionType type, String reason) {
            if (SessionType.RPC.equals(type) && session != null) {
                currentState = SHUTDOWN;
                if (securedServiceStartCallback != null) {
                    securedServiceStartCallback.onComplete(false);
                }
            }
            DebugTool.logError("onServiceError, session id: " + (session == null ? "session null" : session.getSessionId())
                    + ", session type: " + type.getName() + ", reason: " + reason);
        }
    };
}
