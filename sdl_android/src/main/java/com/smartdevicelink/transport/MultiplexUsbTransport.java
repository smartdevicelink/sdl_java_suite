package com.smartdevicelink.transport;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.smartdevicelink.protocol.SdlPacket;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MultiplexUsbTransport extends MultiplexBaseTransport{

    private static final String TAG = "MultiplexUsbTransport";

    ReaderThread readerThread;
    WriterThread writerThread;
    final FileDescriptor fileDescriptor;

    MultiplexUsbTransport(ParcelFileDescriptor parcelFileDescriptor, Handler handler){
        super(handler);
        if(parcelFileDescriptor == null){
            Log.e(TAG, "Error with object");
            fileDescriptor = null;
            //TODO throw an exception
        }else{
            fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        }
    }

    public synchronized void start(){
        setState(STATE_CONNECTING);
        readerThread = new ReaderThread(fileDescriptor);
        writerThread = new WriterThread(fileDescriptor);

        setState(STATE_CONNECTED);
        readerThread.start();
        writerThread.start();
    }

    protected synchronized void stop(int stateToTransitionTo) {
        //Log.d(TAG, "Attempting to close the Usb transports");
        if (writerThread != null) {writerThread.cancel(); writerThread = null;}

        if (readerThread != null) {
            readerThread.cancel();
            readerThread = null;
        }

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
        Message msg = handler.obtainMessage(SdlRouterService.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "Unable to connect device");
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
        Message msg = handler.obtainMessage(SdlRouterService.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "Device connection was lost");
        msg.setData(bundle);
        handler.sendMessage(msg);
        stop();

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
            final int READ_BUFFER_SIZE = 4096;
            byte[] buffer = new byte[READ_BUFFER_SIZE];
            int bytesRead;
            boolean stateProgress;

            // read loop
            while (!isInterrupted()) {
                try {
                    bytesRead = inputStream.read(buffer);
                    if (bytesRead == -1) {
                        if (isInterrupted()) {
                            Log.e(TAG,"EOF reached, and thread is interrupted");
                        } else {
                            Log.i(TAG,"EOF reached, disconnecting!");
                            connectionLost();
                        }
                        return;
                    }
                    if (isInterrupted()) {
                        Log.w(TAG,"Read some data, but thread is interrupted");
                        return;
                    }
                    byte input;
                    for(int i=0;i<bytesRead; i++){
                        input=buffer[i];
                        stateProgress = psm.handleByte(input);
                        if(!stateProgress){//We are trying to weed through the bad packet info until we get something
                            //Log.w(TAG, "Packet State Machine did not move forward from state - "+ psm.getState()+". PSM being Reset.");
                            psm.reset();
                            buffer = new byte[READ_BUFFER_SIZE];
                        }

                        if(psm.getState() == SdlPsm.FINISHED_STATE){
                            synchronized (MultiplexUsbTransport.this) {
                                //Log.d(TAG, "Packet formed, sending off");
                                handler.obtainMessage(SdlRouterService.MESSAGE_READ, psm.getFormedPacket()).sendToTarget();
                            }
                            //We put a trace statement in the message read so we can avoid all the extra bytes
                            psm.reset();
                            buffer = new byte[READ_BUFFER_SIZE]; //FIXME just do an array copy and send off
                        }
                    }
                } catch (IOException e) {
                    if (isInterrupted()) {
                        Log.w(TAG,"Can't read data, and thread is interrupted", e);
                    } else {
                        Log.w(TAG,"Can't read data, disconnecting!", e);
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
            setName("SDL Router BT Write Thread");
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
                    Log.w(TAG, "Can't write to device, nothing to send");
                    return;
                }
                //This would be a good spot to log out all bytes received
                mmOutStream.write(buffer, offset, count);
                //Log.w(TAG, "Wrote out to device: bytes = "+ count);
            } catch (IOException|NullPointerException e) { // STRICTLY to catch mmOutStream NPE
                // Exception during write
                //OMG! WE MUST NOT BE CONNECTED ANYMORE! LET THE USER KNOW
                Log.e(TAG, "Error sending bytes to connected device!");
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
                Log.d(TAG,  "Write Thread: " + e.getMessage());
            }
        }
    }
}
