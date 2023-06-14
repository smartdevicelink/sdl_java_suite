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

package com.smartdevicelink.transport;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.ByteAraryMessageAssembler;
import com.smartdevicelink.transport.utl.ByteArrayMessageSpliter;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@RestrictTo(RestrictTo.Scope.LIBRARY)
public class TransportBroker {

    private static final String TAG = "SdlTransportBroker";

    /**
     * See version document included with library
     */
    private static final int MAX_MESSAGING_VERSION = 2;
    private static final int MIN_MESSAGING_VERSION = 1;

    /**
     * Version of the router service that supports the new additional transports (USB and TCP)
     */
    private static final int RS_MULTI_TRANSPORT_SUPPORT = 8;
    private static final TransportRecord LEGACY_TRANSPORT_RECORD = new TransportRecord(TransportType.BLUETOOTH, null);

    private final String WHERE_TO_REPLY_PREFIX = "com.sdl.android.";
    private final String appId;
    private final String whereToReply;
    private Context currentContext;

    private final Object INIT_LOCK = new Object();
    private final Object MESSAGE_SEND_LOCK = new Object();

    Messenger routerServiceMessenger = null;
    final Messenger clientMessenger;
    private SendToRouterServiceTaskQueue queue;
    private SendToRouterServiceTaskMaster sendToRouterServiceTaskMaster;

    boolean isBound = false, registeredWithRouterService = false;
    private String routerPackage = null, routerClassName = null;
    private ComponentName routerService;


    private SdlPacket bufferedPacket = null;
    private ByteAraryMessageAssembler bufferedPayloadAssembler = null;

    private ServiceConnection routerConnection;
    private int routerServiceVersion = 1;
    private final int messagingVersion = MAX_MESSAGING_VERSION;

    private void initRouterConnection() {
        routerConnection = new ServiceConnection() {

            public void onServiceConnected(ComponentName className, IBinder service) {
                DebugTool.logInfo(TAG, "Bound to service " + className.toString());
                queue = new SendToRouterServiceTaskQueue();
                sendToRouterServiceTaskMaster = new SendToRouterServiceTaskMaster();
                sendToRouterServiceTaskMaster.start();
                routerServiceMessenger = new Messenger(service);
                isBound = true;
                //So we just established our connection
                //Register with router service
                sendRegistrationMessage();
            }

            public void onServiceDisconnected(ComponentName className) {
                DebugTool.logInfo(TAG, "Unbound from service " + className.getClassName());
                shutDownRouterServiceMessenger();
                registeredWithRouterService = false;
                isBound = false;
                onHardwareDisconnected(null, null);
            }
        };
    }

    protected boolean sendMessageToRouterService(Message message) {
        if (queue != null) {
            queue.add(new SendToRouterServiceTask(message));
            if (sendToRouterServiceTaskMaster != null) {
                sendToRouterServiceTaskMaster.alert();
            }
        }
        // Updated to only return true as we have adding sending messages to SdlRouterService to be on a different thread.
        return true;
    }

    protected boolean sendMessageToRouterService(Message message, int retryCount) {
        synchronized (MESSAGE_SEND_LOCK) {
            if (message == null) {
                DebugTool.logWarning(TAG, "Attempted to send null message");
                return false;
            }
            //Log.i(TAG, "Attempting to send message type - " + message.what);
            if (isBound && routerServiceMessenger != null && routerServiceMessenger.getBinder() != null && routerServiceMessenger.getBinder().isBinderAlive()) {
                if (registeredWithRouterService
                        || message.what == TransportConstants.ROUTER_REGISTER_CLIENT) { //We can send a message if we are registered or are attempting to register
                    try {
                        routerServiceMessenger.send(message);
                        return true;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        //Let's check to see if we should retry
                        if (e instanceof TransactionTooLargeException || retryCount < 5 && routerServiceMessenger.getBinder().isBinderAlive() && routerServiceMessenger.getBinder().pingBinder()) { //We probably just failed on a small transaction =\
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            return sendMessageToRouterService(message, retryCount++);
                        } else {
                            //DeadObject, time to kill our connection
                            DebugTool.logInfo(TAG, "Dead object while attempting to send packet");
                            stop();
                            onHardwareDisconnected(null, null);
                            return false;
                        }
                    } catch (NullPointerException e) {
                        DebugTool.logInfo(TAG, "Null messenger while attempting to send packet"); // NPE, routerServiceMessenger is null
                        stop();
                        onHardwareDisconnected(null, null);
                        return false;
                    }
                } else {
                    DebugTool.logError(TAG, "Unable to send message to router service. Not registered.");
                    return false;
                }
            } else {
                DebugTool.logError(TAG, "Unable to send message to router service. Not bound.");
                return false;
            }
        }
    }


    /**
     * Handler of incoming messages from service.
     */
    static class ClientHandler extends Handler {
        final ClassLoader loader;
        final WeakReference<TransportBroker> provider;

        public ClientHandler(TransportBroker provider) {
            this.provider = new WeakReference<>(provider);
            loader = getClass().getClassLoader();
        }

        @Override
        public void handleMessage(Message msg) {
            TransportBroker broker = provider.get();
            if (broker == null) {
                DebugTool.logError(TAG, "Broker object null, unable to process message");
                return;
            }
            Bundle bundle = msg.getData();

            if (bundle != null) {
                bundle.setClassLoader(loader);
            }
            //Log.d(TAG, "Bundle: "  + bundle.toString());
            /* DO NOT MOVE
             * This needs to be first to make sure we already know if we are attempting to enter legacy mode or not
             */
            if (bundle != null
                    && bundle.containsKey(TransportConstants.ENABLE_LEGACY_MODE_EXTRA)) {
                boolean enableLegacy = bundle.getBoolean(TransportConstants.ENABLE_LEGACY_MODE_EXTRA, false);
                broker.enableLegacyMode(enableLegacy);
            }

            //Find out what message we have and what to do with it
            switch (msg.what) {
                case TransportConstants.ROUTER_REGISTER_CLIENT_RESPONSE:
                    switch (msg.arg1) {
                        case TransportConstants.REGISTRATION_RESPONSE_SUCESS:
                            // yay! we have been registered. Now what?
                            broker.registeredWithRouterService = true;
                            if (bundle != null) {

                                if (bundle.containsKey(TransportConstants.ROUTER_SERVICE_VERSION)) {
                                    broker.routerServiceVersion = bundle.getInt(TransportConstants.ROUTER_SERVICE_VERSION);
                                }

                                if (bundle.containsKey(TransportConstants.HARDWARE_CONNECTED) || bundle.containsKey(TransportConstants.CURRENT_HARDWARE_CONNECTED)) {
                                    //A connection is already available
                                    handleConnectionEvent(bundle, broker);
                                }

                            }
                            break;
                        case TransportConstants.REGISTRATION_RESPONSE_DENIED_LEGACY_MODE_ENABLED:
                            DebugTool.logInfo(TAG, "Denied registration because router is in legacy mode");
                            broker.registeredWithRouterService = false;
                            broker.enableLegacyMode(true);
                            //We call this so we can start the process of legacy connection
                            broker.onLegacyModeEnabled();
                            break;
                        default:
                            broker.registeredWithRouterService = false;
                            DebugTool.logWarning(TAG, "Registration denied from router service. Reason - " + msg.arg1);
                            break;
                    }


                    break;
                case TransportConstants.ROUTER_UNREGISTER_CLIENT_RESPONSE:
                    if (msg.arg1 == TransportConstants.UNREGISTRATION_RESPONSE_SUCESS) {
                        // We've been unregistered. Now what?

                    } else { //We were denied our unregister request to the router service, let's see why
                        DebugTool.logWarning(TAG, "Unregister request denied from router service. Reason - " + msg.arg1);
                        //Do we care?
                    }

                    break;
                case TransportConstants.ROUTER_RECEIVED_PACKET:
                    if (bundle == null) {
                        DebugTool.logWarning(TAG, "Received packet message from router service with no bundle");
                        return;
                    }
                    //So the intent has a packet with it. PERFECT! Let's send it through the library
                    int flags = bundle.getInt(TransportConstants.BYTES_TO_SEND_FLAGS, TransportConstants.BYTES_TO_SEND_FLAG_NONE);

                    if (bundle.containsKey(TransportConstants.FORMED_PACKET_EXTRA_NAME)) {
                        SdlPacket packet = bundle.getParcelable(TransportConstants.FORMED_PACKET_EXTRA_NAME);

                        if (flags == TransportConstants.BYTES_TO_SEND_FLAG_NONE) {
                            if (packet != null) { //Log.i(TAG, "received packet to process "+  packet.toString());

                                if (packet.getTransportRecord() == null) {
                                    // If the transport record is null, one must be added
                                    // This is likely due to an older router service being used
                                    // in which only a bluetooth transport is available
                                    packet.setTransportRecord(LEGACY_TRANSPORT_RECORD);
                                }

                                broker.onPacketReceived(packet);
                            } else {
                                DebugTool.logWarning(TAG, "Received null packet from router service, not passing along");
                            }
                        } else if (flags == TransportConstants.BYTES_TO_SEND_FLAG_SDL_PACKET_INCLUDED) {
                            broker.bufferedPacket = (SdlPacket) packet;
                            if (broker.bufferedPayloadAssembler != null) {
                                broker.bufferedPayloadAssembler.close();
                                broker.bufferedPayloadAssembler = null;
                            }

                            broker.bufferedPayloadAssembler = new ByteAraryMessageAssembler();
                            broker.bufferedPayloadAssembler.init();
                        }
                    } else if (bundle.containsKey(TransportConstants.BYTES_TO_SEND_EXTRA_NAME)) {
                        //This should contain the payload
                        if (broker.bufferedPayloadAssembler != null) {
                            byte[] chunk = bundle.getByteArray(TransportConstants.BYTES_TO_SEND_EXTRA_NAME);
                            if (!broker.bufferedPayloadAssembler.handleMessage(flags, chunk)) {
                                //If there was a problem
                                DebugTool.logError(TAG, "Error handling bytes for split packet");
                            }
                            if (broker.bufferedPayloadAssembler.isFinished()) {
                                broker.bufferedPacket.setPayload(broker.bufferedPayloadAssembler.getBytes());

                                broker.bufferedPayloadAssembler.close();
                                broker.bufferedPayloadAssembler = null;
                                broker.onPacketReceived(broker.bufferedPacket);
                                broker.bufferedPacket = null;
                            }
                        }
                    } else {
                        DebugTool.logWarning(TAG, "False positive packet reception");
                    }
                    break;
                case TransportConstants.HARDWARE_CONNECTION_EVENT:
                    if (bundle == null) {
                        DebugTool.logWarning(TAG, "Received hardware connection message from router service with no bundle");
                        return;
                    }
                    if (bundle.containsKey(TransportConstants.TRANSPORT_DISCONNECTED)
                            || bundle.containsKey(TransportConstants.HARDWARE_DISCONNECTED)) {
                        //We should shut down, so call
                        DebugTool.logInfo(TAG, "Hardware disconnected");
                        if (isLegacyModeEnabled()) {
                            broker.onLegacyModeEnabled();
                        } else {
                            if (bundle.containsKey(TransportConstants.TRANSPORT_DISCONNECTED)) {
                                TransportRecord disconnectedTransport = bundle.getParcelable(TransportConstants.TRANSPORT_DISCONNECTED);
                                List<TransportRecord> connectedTransports = bundle.getParcelableArrayList(TransportConstants.CURRENT_HARDWARE_CONNECTED);
                                broker.onHardwareDisconnected(disconnectedTransport, connectedTransports);
                            } else { //bundle contains key TransportConstants.HARDWARE_DISCONNECTED
                                // Since this is an older router service it can be assumed that the
                                // transport is bluetooth
                                broker.onHardwareDisconnected(LEGACY_TRANSPORT_RECORD, null);
                            }


                        }
                        break;
                    }

                    if (bundle.containsKey(TransportConstants.HARDWARE_CONNECTED) || bundle.containsKey(TransportConstants.CURRENT_HARDWARE_CONNECTED)) {
                        //This is a connection event
                        handleConnectionEvent(bundle, broker);
                        break;
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }

        }

        /**
         * Handle a potential connection event. This will adapt legacy router service implementations
         * into the new multiple transport scheme.
         *
         * @param bundle the received bundle from the router service
         * @param broker reference to the transport broker that this handler exists
         * @return if a connection event was triggered in the supplied broker
         */
        private boolean handleConnectionEvent(Bundle bundle, TransportBroker broker) {
            if (broker.routerServiceVersion < RS_MULTI_TRANSPORT_SUPPORT) {
                //Previous versions of the router service only supports a single
                //transport, so this will be the only extra received
                if (bundle.containsKey(TransportConstants.HARDWARE_CONNECTED)) {
                    // Only bluetooth was a supported transport on previous versions of the router
                    // service so the constant legacy bluetooth transport record will be used.
                    broker.onHardwareConnected(Collections.singletonList(LEGACY_TRANSPORT_RECORD));
                    return true;
                }
            } else {
                //Router service supports multiple transport

                if (bundle.containsKey(TransportConstants.CURRENT_HARDWARE_CONNECTED)) {
                    ArrayList<TransportRecord> transports = bundle.getParcelableArrayList(TransportConstants.CURRENT_HARDWARE_CONNECTED);
                    broker.onHardwareConnected(transports);
                    return true;
                }
            }
            return false;
        }

    }


    /***************************************************************************************************************************************
     ***********************************************  Life Cycle  **************************************************************
     ****************************************************************************************************************************************/


    @SuppressLint("SimpleDateFormat")
    public TransportBroker(@NonNull Context context, @NonNull String appId, @NonNull ComponentName service) {
        synchronized (INIT_LOCK) {
            this.appId = appId;
            currentContext = context;
            this.routerService = service;

            clientMessenger = new Messenger(new ClientHandler(this));
            initRouterConnection();

            //So the user should have set the AppId, lets define where the messages need to be sent
            SimpleDateFormat s = new SimpleDateFormat("hhmmssss"); //So we have a time stamp of the event
            String timeStamp = s.format(new Date(System.currentTimeMillis()));
            whereToReply = WHERE_TO_REPLY_PREFIX + appId + "." + timeStamp;
        }
    }

    /**
     * This beings the initial connection with the router service.
     */
    public boolean start() {
        //Log.d(TAG, "Starting up transport broker for " + whereToReply);
        synchronized (INIT_LOCK) {
            if (currentContext == null) {
                throw new IllegalStateException("This instance can't be started since it's local reference of context is null. Ensure when supplying a context to the TransportBroker that it is valid");
            }
            if (routerConnection == null) {
                initRouterConnection();
            }
            //Log.d(TAG, "Registering our reply receiver: " + whereToReply);
            if (!isBound) {
                return registerWithRouterService();
            } else {
                return false;
            }
        }
    }

    public void resetSession() {
        synchronized (INIT_LOCK) {
            unregisterWithRouterService();
            shutDownRouterServiceMessenger();
            unBindFromRouterService();
            isBound = false;
        }
    }

    /**
     * This method will end our communication with the router service.
     */
    public void stop() {
        DebugTool.logInfo(TAG, "Stopping transport broker for " + whereToReply);
        synchronized (INIT_LOCK) {
            unregisterWithRouterService();
            unBindFromRouterService();
            shutDownRouterServiceMessenger();
            currentContext = null;

        }
    }

    private void unBindFromRouterService() {
        try {
            getContext().unbindService(routerConnection);

        } catch (Exception e) {
            //This is ok
            DebugTool.logWarning(TAG, "Unable to unbind from router service. bound? " + isBound + " context? " + (getContext() != null) + " router connection?" + (routerConnection != null));
        } finally {
            isBound = false;
        }
    }

    /***************************************************************************************************************************************
     ***********************************************  Event Callbacks  **************************************************************
     ****************************************************************************************************************************************/


    public void onServiceUnregsiteredFromRouterService(int unregisterCode) {
    }


    public void onHardwareDisconnected(TransportRecord record, List<TransportRecord> connectedTransports) {

    }

    public boolean onHardwareConnected(List<TransportRecord> transports) {
        synchronized (INIT_LOCK) {
            if (routerServiceMessenger == null && transports != null && transports.size() > 0) {
                return false;
            }
            return true;
        }
    }

    public void onPacketReceived(Parcelable packet) {

    }

    public void onLegacyModeEnabled() {

    }

    protected int getRouterServiceVersion() {
        return routerServiceVersion;
    }

    public boolean sendPacketToRouterService(SdlPacket packet) { //We use ints because that is all that is supported by the output stream class
        //Log.d(TAG,whereToReply + "Sending packet to router service");

        if (routerServiceMessenger == null) {
            DebugTool.logInfo(TAG, whereToReply + " tried to send packet, but no where to send");
            return false;
        }
        if (packet == null
            //|| offset<0
            //|| count<0
        ) {//|| count>(bytes.length-offset)){
            DebugTool.logWarning(TAG, whereToReply + "incorrect params supplied");
            return false;
        }
        byte[] bytes = packet.constructPacket();
        if (bytes.length < ByteArrayMessageSpliter.MAX_BINDER_SIZE) {//Determine if this is under the packet length.
            Message message = Message.obtain(); //Do we need to always obtain new? or can we just swap bundles?
            message.what = TransportConstants.ROUTER_SEND_PACKET;
            Bundle bundle = new Bundle();
            if (routerServiceVersion < TransportConstants.RouterServiceVersions.APPID_STRING) {
                bundle.putLong(TransportConstants.APP_ID_EXTRA, convertAppId(appId));
            }
            bundle.putString(TransportConstants.APP_ID_EXTRA_STRING, appId);
            bundle.putByteArray(TransportConstants.BYTES_TO_SEND_EXTRA_NAME, bytes); //Do we just change this to the args and objs
            bundle.putInt(TransportConstants.BYTES_TO_SEND_EXTRA_OFFSET, 0);
            bundle.putInt(TransportConstants.BYTES_TO_SEND_EXTRA_COUNT, bytes.length);
            bundle.putInt(TransportConstants.BYTES_TO_SEND_FLAGS, TransportConstants.BYTES_TO_SEND_FLAG_NONE);
            bundle.putInt(TransportConstants.PACKET_PRIORITY_COEFFICIENT, packet.getPrioirtyCoefficient());
            if (packet.getTransportRecord() != null) {
                //Log.d(TAG, "Sending packet on transport " + packet.getTransportType().name());
                TransportRecord record = packet.getTransportRecord();
                bundle.putString(TransportConstants.TRANSPORT_TYPE, record.getType().name());
                bundle.putString(TransportConstants.TRANSPORT_ADDRESS, record.getAddress());
            } else {
                //Log.d(TAG, "No transport to be found");
            }
            message.setData(bundle);

            sendMessageToRouterService(message);
            return true;
        } else { //Message is too big for IPC transaction
            //Log.w(TAG, "Message too big for single IPC transaction. Breaking apart. Size - " +  bytes.length);
            ByteArrayMessageSpliter splitter = new ByteArrayMessageSpliter(appId, TransportConstants.ROUTER_SEND_PACKET, bytes, packet.getPrioirtyCoefficient());
            splitter.setRouterServiceVersion(routerServiceVersion);
            splitter.setTransportRecord(packet.getTransportRecord());
            while (splitter.isActive() && routerServiceMessenger != null) {
                sendMessageToRouterService(splitter.nextMessage());
            }
            return splitter.close();
        }

    }

    /**
     * This registers this service with the router service
     */
    private boolean registerWithRouterService() {
        if (getContext() == null) {
            DebugTool.logError(TAG, "Context set to null, failing out");
            return false;
        }

        if (routerServiceMessenger != null) {
            DebugTool.logWarning(TAG, "Already registered with router service");
            return false;
        }
        //Make sure we know where to bind to
        if (this.routerService == null) {
            DebugTool.logInfo(TAG, whereToReply + " has no router service reference; should shut down.");
            return false;
        }

        this.routerClassName = this.routerService.getClassName();
        this.routerPackage = this.routerService.getPackageName();


        if (!sendBindingIntent()) {
            DebugTool.logError(TAG, "Something went wrong while trying to bind with the router service.");
            SdlBroadcastReceiver.queryForConnectedService(currentContext);
            return false;
        }
        return true;

    }

    @SuppressLint("InlinedApi")
    private boolean sendBindingIntent() {
        if (this.isBound) {
            DebugTool.logError(TAG, "Already bound");
            return false;
        }
        if (this.routerPackage != null && this.routerClassName != null) {
            DebugTool.logInfo(TAG, "Sending bind request to " + this.routerPackage + " - " + this.routerClassName);
            Intent bindingIntent = new Intent();
            bindingIntent.setClassName(this.routerPackage, this.routerClassName);//This sets an explicit intent
            //Quickly make sure it's just up and running
            getContext().startService(bindingIntent);
            bindingIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_CLIENT);
            return getContext().bindService(bindingIntent, routerConnection, Context.BIND_AUTO_CREATE);
        } else {
            return false;
        }
    }

    private void sendRegistrationMessage() {
        Message msg = Message.obtain();
        msg.what = TransportConstants.ROUTER_REGISTER_CLIENT;
        msg.replyTo = this.clientMessenger;
        Bundle bundle = new Bundle();
        bundle.putLong(TransportConstants.APP_ID_EXTRA, convertAppId(appId)); //We send this no matter what due to us not knowing what router version we are connecting to
        bundle.putString(TransportConstants.APP_ID_EXTRA_STRING, appId);
        bundle.putInt(TransportConstants.ROUTER_MESSAGING_VERSION, messagingVersion);
        msg.setData(bundle);
        sendMessageToRouterService(msg);
    }

    private void unregisterWithRouterService() {
        DebugTool.logInfo(TAG, "Attempting to unregister with Sdl Router Service");
        if (isBound && routerServiceMessenger != null) {
            Message msg = Message.obtain();
            msg.what = TransportConstants.ROUTER_UNREGISTER_CLIENT;
            msg.replyTo = this.clientMessenger; //Including this in case this app isn't actually registered with the router service
            Bundle bundle = new Bundle();
            if (routerServiceVersion < TransportConstants.RouterServiceVersions.APPID_STRING) {
                bundle.putLong(TransportConstants.APP_ID_EXTRA, convertAppId(appId));
            }
            bundle.putString(TransportConstants.APP_ID_EXTRA_STRING, appId);
            msg.setData(bundle);
            sendMessageToRouterService(msg);
        } else {
            DebugTool.logWarning(TAG, "Unable to unregister, not bound to router service");
        }
        shutDownRouterServiceMessenger();
    }

    protected ComponentName getRouterService() {
        return this.routerService;
    }

    /**
     * Since it doesn't always make sense to add another service, use this method to get
     * the appropriate context that the rest of this class is using.
     *
     * @return The currently used context for this class
     */
    private Context getContext() {
        return currentContext;
    }


    public static Long convertAppId(String appId) {
        if (appId == null) {
            return -1L;
        }
        try {
            return Long.valueOf(appId);
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    /***************************************************************************************************************************************
     ***********************************************  LEGACY  *******************************************************************************
     ****************************************************************************************************************************************/
    /*
     * Due to old implementations of SDL/Applink, old versions can't support multiple sessions per RFCOMM channel.
     * This causes a race condition in the router service where only the first app registered will be able to communicate with the
     * head unit. With this additional code, the router service will:
     * 1) Acknowledge it's connected to an old system
     * 2) d/c its bluetooth
     * 3) Send a message to all clients connected that legacy mode is enabled
     * 4) Each client spins up their own bluetooth RFCOMM listening channel
     * 5) Head unit will need to query apps again
     * 6) HU should then connect to each app by their own RFCOMM channel bypassing the router service
     * 7) When the phone is D/C from the head unit the router service will reset and tell clients legacy mode is now off
     */

    private static boolean legacyModeEnabled = false;
    private static final Object LEGACY_LOCK = new Object();

    protected void enableLegacyMode(boolean enable) {
        synchronized (LEGACY_LOCK) {
            legacyModeEnabled = enable;
        }
    }

    protected static boolean isLegacyModeEnabled() {
        synchronized (LEGACY_LOCK) {
            return legacyModeEnabled;
        }

    }

    /***************************************************************************************************************************************
     ****************************************************  LEGACY END ***********************************************************************
     ****************************************************************************************************************************************/

    /**
     * Use this method to let the router service know that you are requesting a new session from the head unit.
     */
    public void requestNewSession(TransportRecord transportRecord) {
        Message msg = Message.obtain();
        msg.what = TransportConstants.ROUTER_REQUEST_NEW_SESSION;
        msg.replyTo = this.clientMessenger; //Including this in case this app isn't actually registered with the router service
        Bundle bundle = new Bundle();
        if (routerServiceVersion < TransportConstants.RouterServiceVersions.APPID_STRING) {
            bundle.putLong(TransportConstants.APP_ID_EXTRA, convertAppId(appId));
        }
        bundle.putString(TransportConstants.APP_ID_EXTRA_STRING, appId);
        if (transportRecord != null) {
            bundle.putString(TransportConstants.TRANSPORT_TYPE, transportRecord.getType().name());
            bundle.putString(TransportConstants.TRANSPORT_ADDRESS, transportRecord.getAddress());
        }
        msg.setData(bundle);
        this.sendMessageToRouterService(msg);
    }

    /**
     * Request secondary transport and communicate details to router service
     *
     * @param sessionId
     * @param bundle
     */
    public void requestSecondaryTransportConnection(byte sessionId, Bundle bundle) {
        Message msg = Message.obtain();
        msg.what = TransportConstants.ROUTER_REQUEST_SECONDARY_TRANSPORT_CONNECTION;
        msg.replyTo = this.clientMessenger;
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putByte(TransportConstants.SESSION_ID_EXTRA, sessionId);
        msg.setData(bundle);
        this.sendMessageToRouterService(msg);
    }


    public void removeSession(long sessionId) {
        Message msg = Message.obtain();
        msg.what = TransportConstants.ROUTER_REMOVE_SESSION;
        msg.replyTo = this.clientMessenger; //Including this in case this app isn't actually registered with the router service
        Bundle bundle = new Bundle();
        if (routerServiceVersion < TransportConstants.RouterServiceVersions.APPID_STRING) {
            bundle.putLong(TransportConstants.APP_ID_EXTRA, convertAppId(appId));
        }
        bundle.putString(TransportConstants.APP_ID_EXTRA_STRING, appId);
        bundle.putLong(TransportConstants.SESSION_ID_EXTRA, sessionId);
        msg.setData(bundle);
        this.sendMessageToRouterService(msg);
    }

    /**
     * Extends thread to consume SendToRouterServiceTask.
     */
    private class SendToRouterServiceTaskMaster extends Thread {
        protected final Object QUEUE_LOCK = new Object();
        private boolean isHalted = false, isWaiting = false;

        public SendToRouterServiceTaskMaster() {
            this.setName("PacketWriteTaskMaster");
            this.setDaemon(true);
        }

        @Override
        public void run() {
            while (!isHalted) {
                try {
                    SendToRouterServiceTask task;
                    synchronized (QUEUE_LOCK) {
                        task = getNextTask();
                        if (task != null) {
                            task.run();
                        } else {
                            isWaiting = true;
                            QUEUE_LOCK.wait();
                            isWaiting = false;
                        }
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        private void alert() {
            if (isWaiting) {
                synchronized (QUEUE_LOCK) {
                    QUEUE_LOCK.notify();
                }
            }
        }

        private void close() {
            this.isHalted = true;
        }
    }

    /**
     * A runnable task for sending messages to the SdlRouterService
     */
    public class SendToRouterServiceTask implements Runnable {
        private Message message;

        public SendToRouterServiceTask(Message message) {
            this.message = message;
        }

        @Override
        public void run() {
            sendMessageToRouterService(message, 0);
        }
    }

    /**
     * Queue that will hold SendToRouterServiceTask in the order that they are received
     */
    @SuppressWarnings("Convert2Diamond")
    private class SendToRouterServiceTaskQueue {
        final class Node<E> {
            final E item;
            SendToRouterServiceTaskQueue.Node<E> prev;
            SendToRouterServiceTaskQueue.Node<E> next;

            Node(E item, SendToRouterServiceTaskQueue.Node<E> previous, SendToRouterServiceTaskQueue.Node<E> next) {
                this.item = item;
                this.prev = previous;
                this.next = next;
            }
        }

        private SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> head;
        private SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> tail;

        /**
         * This will take the given task and insert it at the tail of the queue
         *
         * @param task the task to be inserted at the tail of the queue
         */
        private void insertAtTail(SendToRouterServiceTask task) {
            if (task == null) {
                throw new NullPointerException();
            }
            SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> oldTail = tail;
            SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> newTail = new SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask>(task, oldTail, null);
            tail = newTail;
            if (head == null) {
                head = newTail;
            } else {
                oldTail.next = newTail;
            }
        }

        /**
         * Insert the task in the queue where it belongs
         *
         * @param task the new SendToRouterServiceTask that needs to be added to the queue to be handled
         */
        public void add(SendToRouterServiceTask task) {
            synchronized (this) {
                if (task == null) {
                    throw new NullPointerException();
                }
                //If we currently don't have anything in our queue
                if (head == null || tail == null) {
                    SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> taskNode = new SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask>(task, head, tail);
                    head = taskNode;
                    tail = taskNode;
                } else {
                    insertAtTail(task);
                }
            }
        }

        /**
         * Remove the head of the queue
         *
         * @return the old head of the queue
         */
        public SendToRouterServiceTask poll() {
            synchronized (this) {
                if (head == null) {
                    return null;
                } else {
                    SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> retValNode = head;
                    SendToRouterServiceTaskQueue.Node<SendToRouterServiceTask> newHead = head.next;
                    if (newHead == null) {
                        tail = null;
                    }
                    head = newHead;

                    return retValNode.item;
                }
            }
        }

        /**
         * Currently only clears the head and the tail of the queue.
         */
        public void clear() {
            head = null;
            tail = null;
        }
    }

    protected SendToRouterServiceTask getNextTask() {
        SendToRouterServiceTaskQueue queue = this.queue;
        if (queue != null) {
            return queue.poll();
        }
        return null;
    }

    /**
     * Method to shut down RouterServiceMessenger
     */
    private void shutDownRouterServiceMessenger() {
        routerServiceMessenger = null;
        if (sendToRouterServiceTaskMaster != null) {
            sendToRouterServiceTaskMaster.close();
        }
        if (queue != null) {
            queue.clear();
        }
        queue = null;
        sendToRouterServiceTaskMaster = null;
    }
}
