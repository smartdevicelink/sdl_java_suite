/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smartdevicelink.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * This class does all the work for setting up and managing Bluetooth
 * connections with other devices. It has a thread that listens for
 * incoming connections, a thread for connecting with a device, and a
 * thread for performing data transmissions when connected.
 * 
 * @author Joey Grover
 * 
 */
public class MultiplexBluetoothTransport {
    //finals
	private static final String TAG = "Bluetooth Transport";
    private static final UUID SERVER_UUID= new UUID(0x936DA01F9ABD4D9DL, 0x80C702AF85C822A8L);
    // Name for the SDP record when creating server socket
    private static final String NAME_SECURE =" SdlRouterService";
    

	protected static final String SHARED_PREFS = "sdl.bluetoothprefs";


    // Constants that indicate the current connection state
    public static final int STATE_NONE 			= 0;    // we're doing nothing
    public static final int STATE_LISTEN 		= 1;    // now listening for incoming connections
    public static final int STATE_CONNECTING	= 2; 	// now initiating an outgoing connection
    public static final int STATE_CONNECTED 	= 3;  	// now connected to a remote device
    public static final int STATE_ERROR 		= 4;  	// Something bad happend, we wil not try to restart the thread

    
    // Member fields
    private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final Handler mHandler;
    private AcceptThread mSecureAcceptThread; 
    private static Object threadLock = null;
    private ConnectThread mConnectThread;
    private static ConnectedThread mConnectedThread; //I HATE ALL THIS STATIC CRAP, But it seems like the only way right now
    private static ConnectedWriteThread mConnectedWriteThread; //I HATE ALL THIS STATIC CRAP, But it seems like the only way right now

    private  static int mState;
    
    // Key names received from the BluetoothSerialServer Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    
    
    private int mBluetoothLevel = 0;
    Handler timeOutHandler;
    Runnable socketRunable;
    private static final long msTillTimeout = 2500;
    
    public static String currentlyConnectedDevice = null;
    public static String currentlyConnectedDeviceAddress = null;
    private static MultiplexBluetoothTransport serverInstance = null;
    //private BluetoothServerSocket serverSocket= null;


	static boolean listening = false, keepSocketAlive = true;
    
    
    /**
     * Constructor. Prepares a new BluetoothChat session.
     * @param context  The UI Activity Context
     * @param handler  A Handler to send messages back to the UI Activity
     */
    private MultiplexBluetoothTransport(Handler handler) {
    	//Log.w(TAG, "Creating Bluetooth Serial Adapter");
       // mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
          
        //This will keep track of which method worked last night
        mBluetoothLevel = SdlRouterService.getBluetoothPrefs(SHARED_PREFS);
        Object object = new Object();
        threadLock = object;

    }



	/*
     * Let's use this method from now on to get bluetooth service
     */
    public synchronized static MultiplexBluetoothTransport getBluetoothSerialServerInstance(Handler handler){
    	return getBluetoothSerialServerInstance(handler,true);
    }
	/*
     * Let's use this method from now on to get bluetooth service
     */
    public synchronized static MultiplexBluetoothTransport getBluetoothSerialServerInstance(Handler handler, boolean keepSocketAlive){

        if(serverInstance==null){
        	serverInstance = new MultiplexBluetoothTransport(handler);
        }
        MultiplexBluetoothTransport.keepSocketAlive = keepSocketAlive;
    	return serverInstance;
    }
    public synchronized static MultiplexBluetoothTransport getBluetoothSerialServerInstance(){
    	return serverInstance;
    }

    //These methods are used so we can have a semi-static reference to the Accept Thread (Static reference inherited by housing class)
    private synchronized AcceptThread getAcceptThread(){
    	return mSecureAcceptThread;
    }
    private synchronized void setAcceptThread(AcceptThread aThread){
    	mSecureAcceptThread = aThread;
    }
    protected synchronized void setStateManually(int state){
        //Log.d(TAG, "Setting state from: " +mState + " to: " +state);
        mState = state;
    }
    /**
     * Set the current state of the chat connection
     * @param state  An integer defining the current connection state
     */
    private synchronized void setState(int state) {
        //Log.d(TAG, "Setting state from: " +mState + " to: " +state);
    	int previousState = mState;
        mState = state;

        // Give the new state to the Handler so the UI Activity can update
        //Also sending the previous state so we know if we lost a connection
        mHandler.obtainMessage(SdlRouterService.MESSAGE_STATE_CHANGE, state, previousState).sendToTarget();
    }

    /**
     * Return the current connection state. */
    public synchronized int getState() {
        return mState;
    }

    public void setKeepSocketAlive(boolean keepSocketAlive){
    	MultiplexBluetoothTransport.keepSocketAlive = keepSocketAlive;
    }
    
    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume() */
    public synchronized void start() {
    	//Log.d(TAG, "Starting up Bluetooth Server to Listen");
        // Cancel any thread attempting to make a connection
        if (serverInstance.mConnectThread != null) {serverInstance.mConnectThread.cancel(); serverInstance.mConnectThread = null;}

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        if (mConnectedWriteThread != null) {mConnectedWriteThread.cancel(); mConnectedWriteThread = null;}

       

        // Start the thread to listen on a BluetoothServerSocket
        if (getBluetoothSerialServerInstance().getAcceptThread() == null 
        		//&& !listening
        		&& serverInstance.mAdapter != null
        		&&  serverInstance.mAdapter.isEnabled()) {
        	//Log.d(TAG, "Secure thread was null, attempting to create new");
        	getBluetoothSerialServerInstance().setAcceptThread(new AcceptThread(true));
            if(getBluetoothSerialServerInstance().getAcceptThread()!=null){
            	getBluetoothSerialServerInstance().setState(STATE_LISTEN);
            	 getBluetoothSerialServerInstance().getAcceptThread().start();
            }
        }
    }

    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     * @param device  The BluetoothDevice to connect
     */
    public synchronized void connect(BluetoothDevice device) {
        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (serverInstance.mConnectThread != null) {serverInstance.mConnectThread.cancel(); serverInstance.mConnectThread = null;}
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        if (mConnectedWriteThread != null) {mConnectedWriteThread.cancel(); mConnectedWriteThread = null;}

        
        // Cancel the accept thread because we only want to connect to one device
       if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }
        
        // Start the thread to connect with the given device
       serverInstance.mConnectThread = new ConnectThread(device);
       serverInstance.mConnectThread.start();
       serverInstance.setState(STATE_CONNECTING);
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * @param socket  The BluetoothSocket on which the connection was made
     * @param device  The BluetoothDevice that has been connected
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        // Cancel the thread that completed the connection
        if (getBluetoothSerialServerInstance().mConnectThread != null) {
        	getBluetoothSerialServerInstance().mConnectThread.cancel(); 
        	getBluetoothSerialServerInstance().mConnectThread = null;
        }
        
        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
        	mConnectedThread.cancel(); 
        	mConnectedThread = null;
        }
        if (mConnectedWriteThread != null) {
        	mConnectedWriteThread.cancel(); 
        	mConnectedWriteThread = null;
        }
        // Cancel the accept thread because we only want to connect to one device
        if (!keepSocketAlive && getBluetoothSerialServerInstance().mSecureAcceptThread != null) {
        	getBluetoothSerialServerInstance().mSecureAcceptThread.cancel();
            getBluetoothSerialServerInstance().mSecureAcceptThread = null;
        }

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
        // Start the thread to manage the connection and perform transmissions
        mConnectedWriteThread = new ConnectedWriteThread(socket);
        mConnectedWriteThread.start();

        //Store a static name of the device that is connected. We can do this since the only time
        //we will access it will be when we receive a CONNECT packet from a device
        if(device!=null && device.getName()!=null && device.getName()!=""){
        	currentlyConnectedDevice = device.getName();
        }
        
        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(SdlRouterService.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME, device.getName());
        msg.setData(bundle);
        getBluetoothSerialServerInstance().mHandler.sendMessage(msg);
        getBluetoothSerialServerInstance().setState(STATE_CONNECTED);
    }

    /**
     * Stop all threads
     */
    public synchronized void stop() {
    	getBluetoothSerialServerInstance().stop(STATE_NONE);
    }
    protected synchronized void stop(int stateToTransitionTo) {
    	//Log.d(TAG, "Attempting to close the bluetooth serial server");
        if (getBluetoothSerialServerInstance().mConnectThread != null) {
        	getBluetoothSerialServerInstance().mConnectThread.cancel(); 
        	getBluetoothSerialServerInstance().mConnectThread = null;
        }

        if (mConnectedThread != null) {
        	mConnectedThread.cancel(); 
        	mConnectedThread = null;
        }
        if (mConnectedWriteThread != null) {mConnectedWriteThread.cancel(); mConnectedWriteThread = null;}

        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }

        getBluetoothSerialServerInstance().setState(stateToTransitionTo);
    }
    

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out,  int offset, int count) {
        // Create temporary object
        ConnectedWriteThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedWriteThread;
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
        Message msg = mHandler.obtainMessage(SdlRouterService.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "Unable to connect device");
        msg.setData(bundle);
        getBluetoothSerialServerInstance().mHandler.sendMessage(msg);

        // Start the service over to restart listening mode
       // BluetoothSerialServer.this.start();
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost() {
    	listening = false;
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(SdlRouterService.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "Device connection was lost");
        msg.setData(bundle);
        getBluetoothSerialServerInstance().mHandler.sendMessage(msg);
        getBluetoothSerialServerInstance().stop();

    }
    
    private void timerDelayRemoveDialog(final BluetoothSocket sock){
    	getBluetoothSerialServerInstance().timeOutHandler = new Handler(); 
    	getBluetoothSerialServerInstance().socketRunable = new Runnable() {           
            public void run() {
            	//Log.e(TAG, "BLUETOOTH SOCKET CONNECT TIMEOUT - ATTEMPT TO CLOSE SOCKET");
            	try {
					sock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}         
            }
        };
        getBluetoothSerialServerInstance().timeOutHandler.postDelayed(socketRunable, msTillTimeout); 
    }
    
    

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class AcceptThread extends Thread {
        // The local server socket
        private String mSocketType;
       final BluetoothServerSocket mmServerSocket;
        
        @SuppressLint("NewApi")
		public AcceptThread(boolean secure) {
        	synchronized(threadLock){
            	listening = false;
            	//Log.d(TAG, "Creating an Accept Thread");
            	BluetoothServerSocket tmp = null;
            	mSocketType = secure ? "Secure":"Insecure";
            	// Create a new listening server socket
            	try {
                	if (secure) {
                		tmp = getBluetoothSerialServerInstance().mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, SERVER_UUID);
                		listening = true;
                	}
            	} catch (IOException e) {
            		listening = false;
                	//Log.e(TAG, "Socket Type: " + mSocketType + "listen() failed", e);
                	 //Let's try to shut down this thead
            	}catch(SecurityException e2){
            		//Log.e(TAG, "<LIVIO> Security Exception in Accept Thread - "+e2.toString());
            		listening = false;
            		interrupt();
            	}
            	mmServerSocket = tmp;
    			//Should only log on debug
            	//BluetoothSocket mySock = getBTSocket(mmServerSocket);
            	//Log.d(TAG, "Accepting Connections on SDP Server Port Number: " + getChannel(mySock) + "\r\n");
            }
        }
        
        public void run() {
            synchronized(threadLock){
            	Log.d(TAG, "Socket Type: " + mSocketType +
                    " BEGIN mAcceptThread" + this);
            setName("AcceptThread" + mSocketType);

            BluetoothSocket socket = null;
            int listenAttempts = 0; 
            
            // Listen to the server socket if we're not connected
            while (mState != STATE_CONNECTED) {
                try {
                	if(listenAttempts>=5){
                		Log.e(TAG, "Complete failure in attempting to listen for Bluetooth connection, erroring out.");
                		getBluetoothSerialServerInstance().stop(STATE_ERROR);
                		return;
                	}
                	listenAttempts++;
                	Log.d(TAG, "SDL Bluetooth Accept thread is running.");

                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                	if(mmServerSocket!=null){
                	
                    socket = mmServerSocket.accept();
                    
                	}
                	else{
                		Log.e(TAG, "Listening Socket was null, stopping the bluetooth serial server.");
                		getBluetoothSerialServerInstance().stop(STATE_ERROR);
                		return;
                	}
                } catch (IOException e) {
                	listening = false;
                    Log.e(TAG, "Socket Type: " + mSocketType + "accept() failed", e);
					interrupt(); 
                    return;
                }

                // If a connection was accepted
                if (socket != null) {
                    synchronized (MultiplexBluetoothTransport.this) {
                        switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                            // Situation normal. Start the connected thread.
                        	getBluetoothSerialServerInstance().connected(socket, socket.getRemoteDevice());
                            
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:
                            // Either not ready or already connected. Terminate new socket.
                            try {
                            	Log.d(TAG, "Close unwanted socket");
                                if(socket!=null){
                                	socket.close();
                                }
                            } catch (IOException e) {
                                Log.e(TAG, "Could not close unwanted socket", e);
                            }
                            break;
                        }
                    }
                }
            }
            Log.d(TAG, mState + " END mAcceptThread, socket Type: " + mSocketType);
        }
        }

        public synchronized void cancel() {
        	listening = false;
        	Log.d(TAG, mState + " Socket Type " + mSocketType + " cancel ");
            try {
            	if(mmServerSocket != null){
            		mmServerSocket.close();
            	}
            	
            } catch (IOException e) {
                Log.e(TAG, mState + " Socket Type " + mSocketType + " close() of server failed "+ e.getStackTrace());
            }
        }
    }

    
    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            //Log.d(TAG, "Attempting to conenct to " + device.getName());
            //Log.d(TAG, "UUID to conenct to " + SERVER_UUID.toString());

        }

        public void attemptCancelDiscovery(){
        	try{
        		mAdapter.cancelDiscovery();
        	}catch(SecurityException e2){
        		Log.e(TAG, "Don't have required permision to cancel discovery. Moving on");
        	}
        }
        
        public void run() {
            setName("ConnectThread");
            // Always cancel discovery because it will slow down a connection
            attemptCancelDiscovery();
            // Make a connection to the BluetoothSocket
            int attemptCount = 0;
            boolean success = false;
            Looper.prepare();

            while(attemptCount < 5)
            {
                //Looper.loop()
            	attemptCount++;
	            try {
	                // This is a blocking call and will only return on a
	                // successful connection or an exception
	            	mBluetoothLevel = SdlRouterService.getBluetoothPrefs(SHARED_PREFS);
	            	long waitTime = 3000;
	                try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//This sequence tries to use reflection first to connect with a certain number of phones that require this
					//Next is the most common methods for phones
					//Finally if both have failed an insecure connection is attempted, though this is not available on lower SDK's
	                boolean tryInsecure = false;
	                boolean trySecure = false;
	                //Log.i(TAG,mmDevice.getName() + " socket connecting...");

	                if(mBluetoothLevel<=1){
	                try {
	                	SdlRouterService.setBluetoothPrefs(2,SHARED_PREFS);
	                      Method m = mmDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
	                      //Log.i(TAG,"connecting using createRfcommSocket");
	                        mmSocket = (BluetoothSocket) m.invoke(mmDevice, Integer.valueOf(1));
	    					if(mmSocket!=null){
	    						//Looper.prepare(); 
	    						timerDelayRemoveDialog(mmSocket);
	    						//Looper.loop();
	    						mmSocket.connect();
	    						timeOutHandler.removeCallbacks(socketRunable);
	    						Looper.myLooper().quit();
	    						success=true;
	    						SdlRouterService.setBluetoothPrefs(1,SHARED_PREFS);
	    	                	break;
	    					} else{trySecure = true;}

	                } catch (Exception e) {
	                      //Log.e(TAG,"createRfcommSocket exception - " + e.toString());
	                      SdlRouterService.setBluetoothPrefs(0,SHARED_PREFS);

	                		trySecure = true;
	    	                try {
	    						Thread.sleep(500);
	    					} catch (InterruptedException e2) {
	    						e2.printStackTrace();
	    					}
	                }
	            }else{trySecure = true;}
	                if(trySecure && mBluetoothLevel<=2){
	                    try {
	                    	SdlRouterService.setBluetoothPrefs(3,SHARED_PREFS);
    	                	//Log.i(TAG, "connecting using createRfcommSocketToServiceRecord ");
	                         mmSocket = mmDevice.createRfcommSocketToServiceRecord(SERVER_UUID);                        
	     					if(mmSocket!=null){
	    						//Looper.prepare(); 
	    						timerDelayRemoveDialog(mmSocket);
	    						//Looper.loop();
	    						mmSocket.connect();
	    						timeOutHandler.removeCallbacks(socketRunable);
	    						Looper.myLooper().quit();
	    						success=true;
	    						SdlRouterService.setBluetoothPrefs(2,SHARED_PREFS);
	    	                	break;
	    					}else{tryInsecure = true;}
	                    } catch (IOException io) {
	                        tryInsecure = true;
	                         Log.e(TAG,"createRfcommSocketToServiceRecord exception - " + io.toString());
	                         SdlRouterService.setBluetoothPrefs(0,SHARED_PREFS);

	                    } catch (Exception e){
	                         Log.e(TAG,"createRfcommSocketToServiceRecord exception - " + e.toString());
	                         SdlRouterService.setBluetoothPrefs(0,SHARED_PREFS);

	                    }
	                }else{tryInsecure = true;}

	                if (tryInsecure && mBluetoothLevel<=3) {
	                    // try again using insecure comm if available
	                    try {
	                    	SdlRouterService.setBluetoothPrefs(4,SHARED_PREFS);
	                        //Log.i(TAG,"connecting using createInsecureRfcommSocketToServiceRecord");
	                        Method m = mmDevice.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] {UUID.class});
	                        mmSocket = (BluetoothSocket) m.invoke(mmDevice, new Object[] {SERVER_UUID});
    						//Looper.prepare(); 
    						timerDelayRemoveDialog(mmSocket);
    						//Looper.loop();
    						mmSocket.connect();
    						timeOutHandler.removeCallbacks(socketRunable);
    						Looper.myLooper().quit();
    						success=true;
		                	tryInsecure = false;
		                	SdlRouterService.setBluetoothPrefs(3,SHARED_PREFS);
		                	break;
	                    } catch (NoSuchMethodException ie) {
	                    	SdlRouterService.setBluetoothPrefs(0,SHARED_PREFS);
	                    } catch (IllegalAccessException ie) {
	                    	SdlRouterService.setBluetoothPrefs(0,SHARED_PREFS);
	                    } catch (InvocationTargetException ie) {
	                    	SdlRouterService.setBluetoothPrefs(0,SHARED_PREFS);
	                    }
	                }
	                if (tryInsecure && mBluetoothLevel<=4) {
	                    // try again using insecure comm if available
	                    try {
	                    	SdlRouterService.setBluetoothPrefs(0,SHARED_PREFS);
	                        //Log.i(TAG,"connecting using createInsecureRfcommSocket()");
	                        Method m = mmDevice.getClass().getMethod("createInsecureRfcommSocket()", new Class[] {UUID.class});
	                        mmSocket = (BluetoothSocket) m.invoke(mmDevice, new Object[] {SERVER_UUID});
    						//Looper.prepare(); 
    						timerDelayRemoveDialog(mmSocket);
    						//Looper.loop();
    						mmSocket.connect();
    						timeOutHandler.removeCallbacks(socketRunable);
    						Looper.myLooper().quit();
    						success=true;
    						SdlRouterService.setBluetoothPrefs(4,SHARED_PREFS);
		                	break;
	                    } catch (NoSuchMethodException ie) {
	                    	SdlRouterService.setBluetoothPrefs(0,SHARED_PREFS);
	                    } catch (IllegalAccessException ie) {
	                    	SdlRouterService.setBluetoothPrefs(0,SHARED_PREFS);
	                    } catch (InvocationTargetException ie) {
	                    	SdlRouterService.setBluetoothPrefs(0,SHARED_PREFS);
	                    }
	                }
	            }  catch (IOException e) {
              connectionFailed();
              Log.e(TAG,e.getClass().getSimpleName()
                      + " caught connecting to the bluetooth socket: "
                      + e.toString());
              try {
                  mmSocket.close();
              } catch (IOException e2) {
              	Log.e(TAG,"unable to close() socket during connection failure" + e2);
              }
              return;
          }
            }
            // Reset the ConnectThread because we're done
            if(success)
            {
	            synchronized (MultiplexBluetoothTransport.this) {
	                mConnectThread = null;
	            }
	

	            // Start the connected thread
	
	           	connected(mmSocket, mmDevice);
            }
            else
            {
            	Log.e(TAG, "There was a problem opening up RFCOMM");
            }
        }

        public void cancel() {
            try {
            	Log.d(TAG, "Calling Cancel in the connect thread");
                mmSocket.close();
            } catch (IOException e) {
                // close() of connect socket failed
            }
            catch(NullPointerException e){
            	//mSocket was pry never initialized
            }
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedWriteThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final OutputStream mmOutStream;
        

        public ConnectedWriteThread(BluetoothSocket socket) {
        	//Log.d(TAG, "Creating a Connected - Write Thread");
            mmSocket = socket;
            OutputStream tmpOut = null;
            setName("SDL Router BT Write Thread");
            // Get the BluetoothSocket input and output streams
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                // temp sockets not created
            	Log.e(TAG, "Connected Write Thread: " + e.getMessage());
            }
            mmOutStream = tmpOut;

            
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
            } catch (IOException e) {
                // Exception during write
            	//OMG! WE MUST NOT BE CONNECTED ANYMORE! LET THE USER KNOW
            	Log.e(TAG, "Error sending bytes to connected device!");
            	getBluetoothSerialServerInstance().connectionLost();
            }
        }

        public synchronized void cancel() {
            try {
            	if(mmOutStream!=null){
            		mmOutStream.flush();
            		mmOutStream.close();
                	
            	}
            	if(mmSocket!=null){
            		mmSocket.close();
            	}
            } catch (IOException e) {
                // close() of connect socket failed
            	Log.d(TAG,  "Write Thread: " + e.getMessage());
            }
        }
    }
    
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
    	SdlPsm psm;
        public ConnectedThread(BluetoothSocket socket) {
        	this.psm = new SdlPsm();
        	//Log.d(TAG, "Creating a Connected - Read Thread");
            mmSocket = socket;
            InputStream tmpIn = null;
            setName("SDL Router BT Read Thread");
            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                // temp sockets not created
            	Log.e(TAG, "Connected Read Thread: "+e.getMessage());
            }
            mmInStream = tmpIn;
            
        }
        
		@SuppressLint("NewApi")
		public void run() {
        	Log.d(TAG, "Running the Connected Thread");
            byte input = 0;
            MultiplexBluetoothTransport.currentlyConnectedDevice = mmSocket.getRemoteDevice().getName();
            MultiplexBluetoothTransport.currentlyConnectedDeviceAddress = mmSocket.getRemoteDevice().getAddress();
            // Keep listening to the InputStream while connected
            boolean stateProgress;
            
            psm.reset();
           
            while (true) {
                try {
                    input = (byte)mmInStream.read();
                    // Send the response of what we received
                    stateProgress = psm.handleByte(input); 
                    if(!stateProgress){//We are trying to weed through the bad packet info until we get something	
                    	//Log.w(TAG, "Packet State Machine did not move forward from state - "+ psm.getState()+". PSM being Reset.");
                    	psm.reset();
                    	continue;
                    }
                    
                    if(psm.getState() == SdlPsm.FINISHED_STATE){
                    	//Log.d(TAG, "Packet formed, sending off");
                    	mHandler.obtainMessage(SdlRouterService.MESSAGE_READ, psm.getFormedPacket()).sendToTarget();
                    	psm.reset(); 

                    }
                }catch (IOException e){
                	Log.e(TAG, "Lost connection in the Connected Thread");
                	e.printStackTrace();
                	connectionLost();                    
                    break;
                }
            }
        }


        public synchronized void cancel() {
            try {
            	//Log.d(TAG, "Calling Cancel in the Read thread");
            	if(mmInStream!=null){
            		mmInStream.close();
            	}
                if(mmSocket!=null){
                	mmSocket.close();
                }
                
            } catch (IOException e) {
            	//Log.trace(TAG, "Read Thread: " + e.getMessage());

            }
        }
    }
    
	public boolean isConnected() 
	{
		return !(mState == STATE_NONE);
	}

	
	public BluetoothSocket getBTSocket(BluetoothServerSocket bsSocket){
	    if(bsSocket == null){
	    	return null;
	    }
		Field[] f = bsSocket.getClass().getDeclaredFields();

	    //int channel = -1;
	    BluetoothSocket mySocket = null;
	    for (Field field : f) {
	        if(field.getName().equals("mSocket")){
	            field.setAccessible(true);
	            try {
	                
	            	mySocket = (BluetoothSocket) field.get(bsSocket);
	            	return mySocket;
	            	//channel = field.getInt(bsSocket);
	            } catch (IllegalArgumentException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }
	            field.setAccessible(false);
	        }
	    }

	    return null;
	}
	
	public int getChannel(BluetoothSocket bsSocket){

		int channel = -1;
		if (bsSocket == null){
			return channel;
		}
	    
		Field[] f = bsSocket.getClass().getDeclaredFields();
	    
	    for (Field field : f) {
	        if(field.getName().equals("mPort")){
	            field.setAccessible(true);
	            try {
	                
	            	
	            	channel = field.getInt(bsSocket);
	            } catch (IllegalArgumentException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }
	            field.setAccessible(false);
	        }
	    }

	    return channel;
	}
	
}
