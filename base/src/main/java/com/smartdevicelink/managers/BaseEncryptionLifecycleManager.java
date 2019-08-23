package com.smartdevicelink.managers;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import java.util.*;

public class BaseEncryptionLifecycleManager extends BaseSubManager {
    private ISdl internalInterface;
    private OnRPCNotificationListener onPermissionsChangeListener;
    private boolean isEncryptionReady;
    private CompletionListener securedServiceStartCallback;

    BaseEncryptionLifecycleManager(ISdl isdl) {
        super(isdl);
        internalInterface = isdl;

        onPermissionsChangeListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                List<PermissionItem> permissionItems = ((OnPermissionsChange) notification).getPermissionItem();
                Set<String> rpcSet = new HashSet<>();
                boolean appLevelEncryption = Boolean.TRUE.equals(((OnPermissionsChange) notification).getRequireEncryption());
                isAppLevelEncryptionRequired = appLevelEncryption;
                if (permissionItems != null && !permissionItems.isEmpty()) {
                    for (PermissionItem permissionItem : permissionItems) {
                        if (permissionItem != null) {
                            if (appLevelEncryption && Boolean.TRUE.equals(permissionItem.getEncryptionRequirement())) {
                                String rpcName = permissionItem.getRpcName();
                                rpcSet.add(rpcName);
                            }
                        }
                    }
                }
                if (!rpcSet.isEmpty()) {
                    if (!isEncryptionReady) {
                        //TODO: start secured service here
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
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_PERMISSIONS_CHANGE, onPermissionsChangeListener);
        internalInterface.addServiceListener(SessionType.RPC, serviceListener);
    }

    void startEncryptedRPCService(CompletionListener listener) {
        if (isEncryptionReady) {
            listener.onComplete(true);
            return;
        }
        securedServiceStartCallback = listener;
        //TODO: start secured service, callback is made in serviceListener
    }

    void stopEncryptedRPCService(byte sessionID) {
        //TODO: stop secured RPC service
    }

    boolean isEncryptionReady() {
        return isEncryptionReady;
    }

    @Override
    public void start(CompletionListener listener) {
        super.start(listener);
    }

    @Override
    public void dispose() {
        isEncryptionReady = false;
        isAppLevelEncryptionRequired = false;
        encryptionRequiredRPCs.clear();
        super.dispose();
    }

    private ISdlServiceListener serviceListener = new ISdlServiceListener() {
        @Override
        public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {
            if (SessionType.RPC.equals(type) && session != null) {
                isEncryptionReady = isEncrypted;
                if (securedServiceStartCallback != null) {
                    securedServiceStartCallback.onComplete(isEncrypted);
                }
                DebugTool.logInfo("RPC service started, secured?: " + isEncrypted);
            }
        }

        @Override
        public void onServiceEnded(SdlSession session, SessionType type) {
            if (SessionType.RPC.equals(type) && session != null) {
                isEncryptionReady = false;
            }
            DebugTool.logInfo("onServiceEnded, session id: " + (session == null ? "session null" : session.getSessionId())
                    + ", session type: " + type.getName());
        }

        @Override
        public void onServiceError(SdlSession session, SessionType type, String reason) {
            if (SessionType.RPC.equals(type) && session != null) {
                isEncryptionReady = false;
                if (securedServiceStartCallback != null) {
                    securedServiceStartCallback.onComplete(false);
                }
            }
            DebugTool.logError("onServiceError, session id: " + (session == null ? "session null" : session.getSessionId())
                    + ", session type: " + type.getName());
        }
    };
}
