package com.smartdevicelink.proxy.rpc.listeners;

import com.smartdevicelink.proxy.RPCMessage;

public abstract class OnRPCListener {

    /**
     * Generic listener for all RPCs including Requests, response, and notificaiton
     */
    public final static int UPDATE_LISTENER_TYPE_ALL_RPCS		= -1;

    /**
     * Generic listener type that will work for most RPCs
     */
    public final static int UPDATE_LISTENER_TYPE_BASE_RPC 		= 0;
    /**
     * Listener type specific to putfile
     */
    public final static int UPDATE_LISTENER_TYPE_PUT_FILE 		= 1;
    /**
     * Listener type specific to sendRequests and sendSequentialRequests
     */
    public final static int UPDATE_LISTENER_TYPE_MULTIPLE_REQUESTS 		= 2;

    /**
     * Stores what type of listener this instance is. This prevents of from having to use reflection
     */
    int listenerType;

    /**
     * This is the base listener for all RPCs.
     */
    public OnRPCListener(){
        setListenerType(UPDATE_LISTENER_TYPE_ALL_RPCS);
    }

    protected final void setListenerType(int type){
        this.listenerType = type;
    }
    /**
     * This is used to see what type of listener this instance is. It is needed
     * because some RPCs require additional callbacks. Types are  constants located in this class
     * @return the type of listener this is
     */
    public int getListenerType(){
        return this.listenerType;
    }

    /**
     * This is the only method that must be extended.
     * @param message This will be the response message received from the core side. It should be cast into a corresponding RPC Response type. ie, if setting this
     * for a PutFile request, the message parameter should be cast to a PutFileResponse class.
     */
    public abstract void onReceived(final RPCMessage message);
}
