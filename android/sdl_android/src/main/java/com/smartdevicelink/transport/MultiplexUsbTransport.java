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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.DebugTool;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MultiplexUsbTransport extends MultiplexBaseTransport{

    private static final String TAG = "MultiplexUsbTransport";

    public static final String MANUFACTURER     = "manufacturer";
    public static final String MODEL            = "model";
    public static final String VERSION          = "version";
    public static final String URI              = "uri";
    public static final String SERIAL           = "serial";
    public static final String DESCRIPTION      = "description";

    private final Bundle deviceInfo;
    private ReaderThread readerThread;
    private WriterThread writerThread;
    private ParcelFileDescriptor parcelFileDescriptor;
    private Boolean connectionSuccessful = null;

    MultiplexUsbTransport(ParcelFileDescriptor parcelFileDescriptor, Handler handler, Bundle bundle){
        super(handler, TransportType.USB);
        if(parcelFileDescriptor == null){
            DebugTool.logError(TAG, "Error with object");
            this.parcelFileDescriptor = null;
            throw new ExceptionInInitializerError("ParcelFileDescriptor can't be null");
        }else{
            this.parcelFileDescriptor = parcelFileDescriptor;
            connectedDeviceName = "USB";
            deviceInfo = bundle;
            if(deviceInfo != null){
                //Fill in info
                connectedDeviceAddress = bundle.getString(SERIAL);
                if(connectedDeviceAddress == null){
                    connectedDeviceAddress = bundle.getString(URI);
                    if(connectedDeviceAddress == null) {
                        connectedDeviceAddress = bundle.getString(DESCRIPTION);
                        if (connectedDeviceAddress == null) {
                            connectedDeviceAddress = bundle.getString(MODEL);
                            if (connectedDeviceAddress == null) {
                                connectedDeviceAddress = bundle.getString(MANUFACTURER);
                            }
                        }
                    }
                }

            }else{
                connectedDeviceAddress = "USB";
            }
        }
    }

    public synchronized void start(){
        setState(STATE_CONNECTING);
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        if(fileDescriptor == null || !fileDescriptor.valid()){
            DebugTool.logError(TAG, "USB FD was null or not valid,");
            setState(STATE_NONE);
            return;
        }
        readerThread = new ReaderThread(fileDescriptor);
        readerThread.setDaemon(true);
        writerThread = new WriterThread(fileDescriptor);
        writerThread.setDaemon(true);

        readerThread.start();
        writerThread.start();

        // Send the name of the connected device back to the UI Activity
        Message msg = handler.obtainMessage(SdlRouterService.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME, connectedDeviceName);
        bundle.putString(DEVICE_ADDRESS, connectedDeviceAddress);
        msg.setData(bundle);
        handler.sendMessage(msg);

        setState(STATE_CONNECTED);
    }

    protected synchronized void stop(int stateToTransitionTo) {
        //Log.d(TAG, "Attempting to close the Usb transports");
        if (writerThread != null) {
            writerThread.cancel();
            writerThread.interrupt();
            writerThread = null;
        }

        if (readerThread != null) {
            readerThread.cancel();
            readerThread.interrupt();
            readerThread = null;
        }

        if( (connectionSuccessful== null || connectionSuccessful == true )      //else, the connection was bad. Not closing the PFD helps recover
                && parcelFileDescriptor != null){
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        parcelFileDescriptor = null;

        System.gc();

        setState(stateToTransitionTo);
    }


    /**
     * Write to the ConnectedThread in an unsynchronized manner
     * @param out The bytes to write
     */
    public void write(byte[] out,  int offset, int count) {
        // Create temporary object
        MultiplexUsbTransport.WriterThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = writerThread;
            //r.write(out,offset,count);
        }
        // Perform the write unsynchronized
        r.write(out,offset,count);
    }

    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
        // Send a failure message back to the Activity
        Message msg = handler.obtainMessage(SdlRouterService.MESSAGE_LOG);
        Bundle bundle = new Bundle();
        bundle.putString(LOG, "Unable to connect device");
        msg.setData(bundle);
        handler.sendMessage(msg);

        // Start the service over to restart listening mode
        // BluetoothSerialServer.this.start();
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost() {
        // Send a failure message back to the Activity
        Message msg = handler.obtainMessage(SdlRouterService.MESSAGE_LOG);
        Bundle bundle = new Bundle();
        bundle.putString(LOG, "Device connection was lost");
        msg.setData(bundle);
        handler.sendMessage(msg);
        handler.postDelayed(new Runnable() { //sends this stop back to the main thread to exit the reader thread
            @Override
            public void run() {
                stop();
            }
        }, 250);
    }

    private class ReaderThread extends Thread{
        SdlPsm psm;

        final InputStream inputStream;

        public ReaderThread(final FileDescriptor fileDescriptor){
            psm = new SdlPsm();
            inputStream = new FileInputStream(fileDescriptor);
        }

        @Override
        public void run() {             //FIXME probably check to see what the BT does
            super.run();
            final int READ_BUFFER_SIZE = 16384;
            byte[] buffer = new byte[READ_BUFFER_SIZE];
            int bytesRead;
            boolean stateProgress;

            // read loop
            while (!isInterrupted()) {
                try {
                    bytesRead = inputStream.read(buffer);
                    if (bytesRead == -1) {
                        if (isInterrupted()) {
                            DebugTool.logError(TAG,"EOF reached, and thread is interrupted");
                        } else {
                            DebugTool.logInfo(TAG,"EOF reached, disconnecting!");
                            connectionLost();
                        }
                        return;
                    }
                    if (isInterrupted()) {
                        DebugTool.logWarning(TAG,"Read some data, but thread is interrupted");
                        return;
                    }
                    if(connectionSuccessful != null && connectionSuccessful == false){
                        connectionSuccessful = true;
                    }
                    byte input;
                    for(int i=0;i<bytesRead; i++){
                        input=buffer[i];
                        stateProgress = psm.handleByte(input);
                        if(!stateProgress){//We are trying to weed through the bad packet info until we get something
                            //Log.w(TAG, "Packet State Machine did not move forward from state - "+ psm.getState()+". PSM being Reset.");
                            psm.reset();
                            continue; //Move to the next iteration of the loop
                        }

                        if(psm.getState() == SdlPsm.FINISHED_STATE){
                            synchronized (MultiplexUsbTransport.this) {
                                //Log.d(TAG, "Packet formed, sending off");
                                SdlPacket packet = psm.getFormedPacket();
                                packet.setTransportRecord(getTransportRecord());
                                handler.obtainMessage(SdlRouterService.MESSAGE_READ, packet).sendToTarget();
                            }
                            //Reset the PSM now that we have a finished packet.
                            //We will continue to loop through the data to see if any other packet
                            //is present.
                            psm.reset();
                            continue; //Move to the next iteration of the loop
                        }
                    }
                } catch (IOException e) {
                    if (isInterrupted()) {
                        DebugTool.logWarning(TAG,"Can't read data, and thread is interrupted");
                    } else {
                        DebugTool.logWarning(TAG,"Can't read data, disconnecting!");
                        connectionLost();
                    }
                    return;
                } catch (Exception e){
                    connectionLost();
                }
            }
        }


        public synchronized void cancel() {
            try {
                //Log.d(TAG, "Calling Cancel in the Read thread");
                if(inputStream!=null){
                    inputStream.close();
                }

            } catch (IOException|NullPointerException e) { // NPE is ONLY to catch error on mmInStream
                // Log.trace(TAG, "Read Thread: " + e.getMessage());
                // Socket or stream is already closed
            }
        }

    }


    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class WriterThread extends Thread {
        private final OutputStream mmOutStream;

        public WriterThread(FileDescriptor fileDescriptor) {
            //Log.d(TAG, "Creating a Connected - Write Thread");
            OutputStream tmpOut = null;
            setName("SDL USB Write Thread");
            // Get the Usb output streams
            mmOutStream = new FileOutputStream(fileDescriptor);
        }

        /**
         * Write to the connected OutStream.
         * @param buffer  The bytes to write
         */
        public void write(byte[] buffer, int offset, int count) {
            try {
                if(buffer==null){
                    DebugTool.logWarning(TAG, "Can't write to device, nothing to send");
                    return;
                }
                //This would be a good spot to log out all bytes received
                mmOutStream.write(buffer, offset, count);
                if(connectionSuccessful == null){
                    connectionSuccessful = false;
                }
                //Log.w(TAG, "Wrote out to device: bytes = "+ count);
            } catch (IOException|NullPointerException e) { // STRICTLY to catch mmOutStream NPE
                // Exception during write
                //OMG! WE MUST NOT BE CONNECTED ANYMORE! LET THE USER KNOW
                DebugTool.logError(TAG, "Error sending bytes to connected device!");
                connectionLost();
            }
        }

        public synchronized void cancel() {
            try {
                if(mmOutStream!=null){
                    mmOutStream.flush();
                    mmOutStream.close();

                }
            } catch (IOException e) {
                // close() of connect socket failed
                DebugTool.logInfo(TAG,  "Write Thread: " + e.getMessage());
            }
        }
    }
}
