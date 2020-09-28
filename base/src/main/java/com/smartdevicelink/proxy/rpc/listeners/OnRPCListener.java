/*
 * Copyright (c) 2019, Livio, Inc.
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
package com.smartdevicelink.proxy.rpc.listeners;

import com.smartdevicelink.proxy.RPCMessage;

public abstract class OnRPCListener {

    /**
     * Generic listener for all RPCs including Requests, response, and notification
     */
    public final static int UPDATE_LISTENER_TYPE_ALL_RPCS = -1;

    /**
     * Generic listener type that will work for most RPCs
     */
    public final static int UPDATE_LISTENER_TYPE_BASE_RPC = 0;

    /**
     * Listener type specific to sendRequests and sendSequentialRequests
     */
    public final static int UPDATE_LISTENER_TYPE_MULTIPLE_REQUESTS = 2;

    /**
     * Stores what type of listener this instance is. This prevents of from having to use reflection
     */
    int listenerType;

    /**
     * This is the base listener for all RPCs.
     */
    public OnRPCListener() {
        setListenerType(UPDATE_LISTENER_TYPE_ALL_RPCS);
    }

    protected final void setListenerType(int type) {
        this.listenerType = type;
    }

    /**
     * This is used to see what type of listener this instance is. It is needed
     * because some RPCs require additional callbacks. Types are  constants located in this class
     *
     * @return the type of listener this is
     */
    public int getListenerType() {
        return this.listenerType;
    }

    /**
     * This is the only method that must be extended.
     *
     * @param message This will be the response message received from the core side. It should be cast into a corresponding RPC Response type. ie, if setting this
     *                for a PutFile request, the message parameter should be cast to a PutFileResponse class.
     */
    public abstract void onReceived(final RPCMessage message);
}
