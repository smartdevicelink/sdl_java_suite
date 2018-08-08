package com.smartdevicelink.protocol;


import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.MultiplexTransportConfig;

public interface ISdlProtocol extends IProtocolListener {

    //TODO revisit all of these to see if they make sense
    byte getSessionId();
    void shutdown(String info);
    void onTransportDisconnected(String info, boolean altTransportAvailable, MultiplexTransportConfig transportConfig);

    SdlSecurityBase getSdlSecurity();
    VideoStreamingParameters getDesiredVideoParams();
    void setAcceptedVideoParams(VideoStreamingParameters acceptedVideoParams);

    void stopStream(SessionType serviceType);
}
