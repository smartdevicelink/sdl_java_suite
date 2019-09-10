package com.smartdevicelink.managers.encryption;

import android.support.annotation.NonNull;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
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

abstract class BaseEncryptionManager extends BaseSubManager {
    private ISdl internalInterface;
    private OnRPCNotificationListener onPermissionsChangeListener;
    private OnRPCNotificationListener onHMIStatusListener;
    private CompletionListener startCompletionListener;
    private CompletionListener stopCompletionListener;
    private HMILevel currentHMILevel;
    private EncryptionCallback callback;
    private Set<String> encryptionRequiredRPCs = new HashSet<>();
    private int currentState;
    private boolean isAppLevelEncryptionRequired;

    BaseEncryptionManager(ISdl isdl, EncryptionCallback cb) {
        super(isdl);
        internalInterface = isdl;
        currentState = SHUTDOWN;
        callback = cb;

        onHMIStatusListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                    return;
                }
                currentHMILevel = onHMIStatus.getHmiLevel();
                if (currentHMILevel != HMILevel.HMI_NONE) {
                    checkStateAndInit();
                }
            }
        };

        onPermissionsChangeListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                List<PermissionItem> permissionItems = ((OnPermissionsChange) notification).getPermissionItem();
                Set<String> rpcSet = new HashSet<>();
                isAppLevelEncryptionRequired = Boolean.TRUE.equals(((OnPermissionsChange) notification).getRequireEncryption());
                if (isAppLevelEncryptionRequired && permissionItems != null && !permissionItems.isEmpty()) {
                    for (PermissionItem permissionItem : permissionItems) {
                        if (permissionItem != null) {
                            if (Boolean.TRUE.equals(permissionItem.getRequireEncryption())) {
                                String rpcName = permissionItem.getRpcName();
                                rpcSet.add(rpcName);
                            }
                        }
                    }
                }
                if (!rpcSet.isEmpty()) {
                    if (currentState == SHUTDOWN) {
                        checkStateAndInit();
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
     *
     * @return true if encryption is required for app level; false otherwise
     */
    public boolean getRequiresEncryption() {
        return isAppLevelEncryptionRequired;
    }

    /**
     * Checks if an RPC requires encryption
     *
     * @param rpcName the rpc name to check
     * @return true if the given RPC requires encryption; false, otherwise
     */
    public boolean getRPCRequiresEncryption(@NonNull FunctionID rpcName) {
        return encryptionRequiredRPCs.contains(rpcName.toString());
    }

    /**
     * Checks the current state and make the call back to initiate a
     * secured service flow
     */
    private void checkStateAndInit() {
        if (currentState == READY) {
            if (startCompletionListener != null) {
                startCompletionListener.onComplete(true);
            }
        } else if (currentState == SETTING_UP) {
            //do nothing a secured service is already being started
        } else if (currentState == SHUTDOWN) {
            if (currentHMILevel != HMILevel.HMI_NONE && !encryptionRequiredRPCs.isEmpty()) {
                currentState = SETTING_UP;
                callback.initSecuredSession();
            }
        }
    }

    /**
     * Sets the payload protection flag accordingly
     *
     * @param msg the RPCMessage used to prepare the protection state
     * @return true if the message payload needs to be encrypted; false, otherwise
     * @throws SdlException exception thrown when a message payload is flagged to be protected but there is not a secured service
     */
    public boolean prepareRPCPayload(RPCMessage msg) throws SdlException {
        boolean result = msg.isPayloadProtected();
        if (!result) {
            //library forces encryption on this RPC
            if (isEncryptionReady() && getRPCRequiresEncryption(msg.getFunctionID())) {
                result = true;
            }
        } else {
            if (!isEncryptionReady()) {
                throw new SdlException("Trying to encrypt message and there is not a secured service",
                        SdlExceptionCause.INVALID_RPC_PARAMETER);
            }
        }
        return result;
    }

    /**
     * Starts a secured service.
     *
     * @param listener Used to make a call back to caller on status of the secured service being started
     */
    public void startEncryptedRPCService(@NonNull CompletionListener listener) {
        startCompletionListener = listener;
        checkStateAndInit();
    }

    /**
     * Stops the secured service
     *
     * @param listener Used to make a call back to caller on status of the secured service being stopped
     */
    public void stopEncryptedRPCService(CompletionListener listener) {
        currentState = SHUTDOWN;
        stopCompletionListener = listener;
        callback.stopSecuredSession();
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
                if (startCompletionListener != null) {
                    startCompletionListener.onComplete(isEncrypted);
                }
                DebugTool.logInfo("RPC service started, secured?: " + isEncrypted);
            }
        }

        @Override
        public void onServiceEnded(SdlSession session, SessionType type) {
            if (SessionType.RPC.equals(type) && session != null) {
                currentState = SHUTDOWN;
            }
            if (stopCompletionListener != null) {
                stopCompletionListener.onComplete(true);
            }
            DebugTool.logInfo("onServiceEnded, session id: " + (session == null ? "session null" : session.getSessionId())
                    + ", session type: " + type.getName());
        }

        @Override
        public void onServiceError(SdlSession session, SessionType type, String reason) {
            if (SessionType.RPC.equals(type) && session != null) {
                currentState = SHUTDOWN;
                if (startCompletionListener != null) {
                    startCompletionListener.onComplete(false);
                }
                if (stopCompletionListener != null) {
                    stopCompletionListener.onComplete(false);
                }
            }
            DebugTool.logError("onServiceError, session id: " + (session == null ? "session null" : session.getSessionId())
                    + ", session type: " + type.getName() + ", reason: " + reason);
        }
    };
}
