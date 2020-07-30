/*
 * Copyright (c) 2019 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.SdlConnection;


import com.smartdevicelink.protocol.SdlProtocol;
import com.smartdevicelink.protocol.SdlProtocolBase;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.util.DebugTool;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SdlSession extends BaseSdlSession {

    private static final String TAG = "SdlSession";


    public SdlSession(ISdlConnectionListener listener, BaseTransportConfig config){
       super(listener,config);
       //FIXME this class needs to move to JavaSE only
    }

    @Override
    protected SdlProtocolBase getSdlProtocolImplementation() {
        return new SdlProtocol(this, transportConfig);
    }


    @Override
    public void onProtocolSessionStarted(SessionType sessionType,
                                         byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted) {

        DebugTool.logInfo(TAG, "Protocol session started");

        this.sessionId = sessionID;
        if (sessionType.eq(SessionType.RPC)){
            sessionHashId = hashID;
        }
        if (isEncrypted)
            encryptedServices.addIfAbsent(sessionType);
        this.sessionListener.onProtocolSessionStarted(sessionType, sessionID, version, correlationID, hashID, isEncrypted);
        if(serviceListeners != null && serviceListeners.containsKey(sessionType)){
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for(ISdlServiceListener listener:listeners){
                listener.onServiceStarted(this, sessionType, isEncrypted);
            }
        }

    }

    @Override
    public void onProtocolSessionEnded(SessionType sessionType, byte sessionID,
                                       String correlationID) {
        this.sessionListener.onProtocolSessionEnded(sessionType, sessionID, correlationID);
        if(serviceListeners != null && serviceListeners.containsKey(sessionType)){
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for(ISdlServiceListener listener:listeners){
                listener.onServiceEnded(this, sessionType);
            }
        }
        encryptedServices.remove(sessionType);
    }


    @Override
    public void onProtocolSessionEndedNACKed(SessionType sessionType,
                                             byte sessionID, String correlationID) {
        this.sessionListener.onProtocolSessionEndedNACKed(sessionType, sessionID, correlationID);
        if(serviceListeners != null && serviceListeners.containsKey(sessionType)){
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for(ISdlServiceListener listener:listeners){
                listener.onServiceError(this, sessionType, "End "+ sessionType.toString() +" Service NACK'ed");
            }
        }
    }





    /* ***********************************************************************************************************************************************************************
     * *****************************************************************  IProtocol Listener  ********************************************************************************
     *************************************************************************************************************************************************************************/

     public void onProtocolSessionNACKed(SessionType sessionType, byte sessionID, byte version, String correlationID, List<String> rejectedParams) {
        this.sessionListener.onProtocolSessionStartedNACKed(sessionType,
                sessionID, version, correlationID, rejectedParams);
        if(serviceListeners != null && serviceListeners.containsKey(sessionType)){
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for(ISdlServiceListener listener:listeners){
                listener.onServiceError(this, sessionType, "Start "+ sessionType.toString() +" Service NAKed");
            }
        }
    }    /* ***********************************************************************************************************************************************************************
     * *****************************************************************  Security Listener  *********************************************************************************
     *************************************************************************************************************************************************************************/

}