package com.smartdevicelink.proxy.interfaces;


import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;

public interface ISdlServiceListener {
    public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted);
    public void onServiceEnded(SdlSession session, SessionType type);
    public void onServiceError(SdlSession session, SessionType type, String reason);
}
