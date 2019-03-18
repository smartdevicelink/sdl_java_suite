/*
 * Copyright (c) 2018 Livio, Inc.
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

package com.smartdevicelink.protocol;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.TransportManager;


@SuppressWarnings("WeakerAccess")
public class SdlProtocol extends SdlProtocolBase {
    private static final String TAG ="SdlProtocol";


    @SuppressWarnings("ConstantConditions")
    public SdlProtocol(@NonNull ISdlProtocol iSdlProtocol, @NonNull MultiplexTransportConfig config) {
        super(iSdlProtocol,config);
        this.requestedPrimaryTransports = config.getPrimaryTransports();
        this.requestedSecondaryTransports = config.getSecondaryTransports();
        this.requiresHighBandwidth = config.requiresHighBandwidth();
        this.setTransportManager(new TransportManager(config,transportEventListener));
    }


    /**
     * If there was a TransportListener attached to the supplied multiplex config, this method will
     * call the onTransportEvent method.
     */
    @Override
    void notifyDevTransportListener (){
        MultiplexTransportConfig transportConfig = (MultiplexTransportConfig)this.transportConfig;
        if(transportConfig.getTransportListener() != null && transportManager != null) {
            transportConfig.getTransportListener().onTransportEvent(transportManager.getConnectedTransports(), isTransportForServiceAvailable(SessionType.PCM),isTransportForServiceAvailable(SessionType.NAV));
        }
    }


}
