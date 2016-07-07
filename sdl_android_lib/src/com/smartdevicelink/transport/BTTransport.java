package com.smartdevicelink.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Build.VERSION;

import com.smartdevicelink.SdlConnection.SdlConnection;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.trace.enums.InterfaceActivityDirection;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.DebugTool;

/**
 * Bluetooth Transport Implementation. This transport advertises its existence to SDL by publishing an SDP record and waiting for an incoming connection from SDL. Connection is verified by checking for the SDL UUID. For more detailed information please refer to the <a href="#bluetoothTransport">Bluetooth Transport Guide</a>.
 *
 */
public class BTTransport extends SdlTransport {	
	//936DA01F9ABD4D9D80C702AF85C822A8
	private final static UUID SDL_V4_MOBILE_APPLICATION_SVC_CLASS = new UUID(0x936DA01F9ABD4D9DL, 0x80C702AF85C822A8L);

	private static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
	
	private BluetoothAdapter _adapter = null;
	private BluetoothSocket _activeSocket = null;
	private UUID _listeningServiceUUID = SDL_V4_MOBILE_APPLICATION_SVC_CLASS;
	private BluetoothAdapterMonitor _bluetoothAdapterMonitor = null;
	private TransportReaderThread _transportReader = null;
	private OutputStream _output = null;
	private BluetoothServerSocket _serverSocket = null;
	
	private String sComment = "";
	private boolean bKeepSocketActive = true;
	
	// Boolean to monitor if the transport is in a disconnecting state
    private boolean _disconnecting = false;
	
	public BTTransport(ITransportListener transportListener) {
		super(transportListener);
	} // end-ctor

	public BTTransport(ITransportListener transportListener, boolean bKeepSocket) {
		super(transportListener);
		bKeepSocketActive = bKeepSocket;		
	} // end-ctor	
	
	public BluetoothSocket getBTSocket(BluetoothServerSocket bsSocket){
	    Field[] f = bsSocket.getClass().getDeclaredFields();

	    @SuppressWarnings("unused")
        int channel = -1;
	   
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
		if (bsSocket == null) return channel;
	    
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
	
	
  /*  private BluetoothServerSocket getBluetoothServerSocket() throws IOException {
        BluetoothServerSocket tmp;
        
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


            try {
                // compatibility with pre SDK 10 devices
                Method listener = mBluetoothAdapter.getClass().getMethod(
                        "listenUsingRfcommWithServiceRecord", String.class, UUID.class);
                tmp = (BluetoothServerSocket) listener.invoke(mBluetoothAdapter, "SdlProxy", _listeningServiceUUID);

            } catch (NoSuchMethodException e) {

                throw new IOException(e);
            } catch (InvocationTargetException e) {
                throw new IOException(e);
            } catch (IllegalAccessException e) {
                throw new IOException(e);
            }
     
        return tmp;
    }*/
	
	
	public void openConnection () throws SdlException {
		if (_serverSocket != null) {
			return;
		}		
		
		// Get the device's default Bluetooth Adapter
		_adapter = BluetoothAdapter.getDefaultAdapter();
		
			
		// Test if Adapter exists
		if (_adapter == null) {
			SdlConnection.enableLegacyMode(false, null);
			throw new SdlException("No Bluetooth adapter found. Bluetooth adapter must exist to communicate with SDL.", SdlExceptionCause.BLUETOOTH_ADAPTER_NULL);
		}
		
		// Test if Bluetooth is enabled
		try {
			if (!_adapter.isEnabled()) {
				SdlConnection.enableLegacyMode(false, null);
				throw new SdlException("Bluetooth adapter must be enabled to instantiate a SdlProxy object.", SdlExceptionCause.BLUETOOTH_DISABLED);
			}
		} catch (SecurityException e) {
			SdlConnection.enableLegacyMode(false, null);
			throw new SdlException("Insufficient permissions to interact with the Bluetooth Adapter.", SdlExceptionCause.PERMISSION_DENIED);
		}
		
		// Start BluetoothAdapterMonitor to ensure the Bluetooth Adapter continues to be enabled
		_bluetoothAdapterMonitor = new BluetoothAdapterMonitor(_adapter);
		
		try {
			_serverSocket = _adapter.listenUsingRfcommWithServiceRecord("SdlProxy", _listeningServiceUUID);				
			BluetoothSocket mySock = getBTSocket(_serverSocket);
			int iSocket = getChannel(mySock);

			sComment = "Accepting Connections on SDP Server Port Number: " + iSocket + "\r\n";
			sComment += "Keep Server Socket Open: " + bKeepSocketActive;
			if (iSocket < 0)
			{
				SdlConnection.enableLegacyMode(false, null);
				throw new SdlException("Could not open connection to SDL.", SdlExceptionCause.BLUETOOTH_SOCKET_UNAVAILABLE);
			}			
		} catch (IOException e) {
			SdlConnection.enableLegacyMode(false, null);
			throw new SdlException("Could not open connection to SDL.", SdlExceptionCause.BLUETOOTH_SOCKET_UNAVAILABLE);

		} catch (Exception ex) {
			
			// Test to determine if the bluetooth has been disabled since last check			
			if (!_adapter.isEnabled()) {
				SdlConnection.enableLegacyMode(false, null);
				throw new SdlException("Bluetooth adapter must be on to instantiate a SdlProxy object.", SdlExceptionCause.BLUETOOTH_DISABLED);
			}

			if(ex instanceof SdlException && ((SdlException) ex).getSdlExceptionCause() == SdlExceptionCause.BLUETOOTH_SOCKET_UNAVAILABLE) {
				SdlConnection.enableLegacyMode(false, null);
				throw new SdlException("Could not open connection to SDL.", SdlExceptionCause.BLUETOOTH_SOCKET_UNAVAILABLE);

			}
			SdlConnection.enableLegacyMode(false, null);
			throw new SdlException("Could not open connection to SDL.", ex, SdlExceptionCause.SDL_CONNECTION_FAILED);
		} 
		
		// Test to ensure serverSocket is not null
		if (_serverSocket == null) {
			SdlConnection.enableLegacyMode(false, null);
			throw new SdlException("Could not open connection to SDL.", SdlExceptionCause.SDL_CONNECTION_FAILED);
		}
		
		SdlTrace.logTransportEvent("BTTransport: listening for incoming connect to service ID " + _listeningServiceUUID, null, InterfaceActivityDirection.Receive, null, 0, SDL_LIB_TRACE_KEY);
		
		// Setup transportReader thread
		_transportReader = new TransportReaderThread();
		_transportReader.setName("TransportReader");
		_transportReader.setDaemon(true);
		_transportReader.start();
		
		// Initialize the SiphonServer
		if (SiphonServer.getSiphonEnabledStatus()) {
			SiphonServer.init();
		}

	} // end-method

	public void disconnect() {
		disconnect(null, null);
	}

	/**
	 * Destroys the transport between SDL and the mobile app
	 * 
	 * @param msg
	 * @param ex
	 */
	private synchronized void disconnect(String msg, Exception ex) {		
		// If already disconnecting, return
		if (_disconnecting) {
			// No need to recursively call
			return;
		}		
		_disconnecting = true;
		
		String disconnectMsg = (msg == null ? "" : msg);
		if (ex != null) {
			disconnectMsg += ", " + ex.toString();
		} // end-if

		SdlTrace.logTransportEvent("BTTransport.disconnect: " + disconnectMsg, null, InterfaceActivityDirection.Transmit, null, 0, SDL_LIB_TRACE_KEY);

		try {			
			if (_transportReader != null) {
				_transportReader.halt();
				_transportReader = null;
			}
		} catch (Exception e) {
			DebugTool.logError("Failed to stop transport reader thread.", e);
		} // end-catch	
		
		try {
			if (_bluetoothAdapterMonitor != null) {
				_bluetoothAdapterMonitor.halt();
				_bluetoothAdapterMonitor = null;
			}
		} catch (Exception e) {
			DebugTool.logError("Failed to stop adapter monitor thread.", e);
		}
		
		try {
			if (_serverSocket != null) {
				_serverSocket.close();
				_serverSocket = null;
			} 
		} catch (Exception e) {
			DebugTool.logError("Failed to close serverSocket", e);
		} // end-catch
		
		try {
			if (_activeSocket != null) {
				_activeSocket.close();
				_activeSocket = null;
			}
		} catch (Exception e) {
			DebugTool.logError("Failed to close activeSocket", e);
		} // end-catch
		

		
		try {
			if (_output != null) {
				_output.close();
				_output = null;
			}
		} catch (Exception e) {
			DebugTool.logError("Failed to close output stream", e);
		} // end-catch
		
		if (ex == null) {
			// This disconnect was not caused by an error, notify the proxy that 
			// the trasport has been disconnected.
			handleTransportDisconnected(msg);
		} else {
			// This disconnect was caused by an error, notify the proxy
			// that there was a transport error.
			handleTransportError(msg, ex);
		}
		_disconnecting = false;
	} // end-method

	
	/**
	 * Sends data over the transport.  Takes a byte array and transmits data provided starting at the
	 * offset and of the provided length to fragment transmission.
	 */
	public boolean sendBytesOverTransport(SdlPacket packet) {
		boolean sendResult = false;
		try {
			byte[] msgBytes = packet.constructPacket();
			_output.write(msgBytes, 0, msgBytes.length);
			sendResult = true;
		} catch (Exception ex) {
			DebugTool.logError("Error writing to Bluetooth socket: " + ex.toString(), ex);
			handleTransportError("Error writing to Bluetooth socket:", ex);
			sendResult = false;
		} // end-catch
		return sendResult;
	} // end-method
	
	
	
	private class TransportReaderThread extends Thread {
		private Boolean isHalted = false;
	    SdlPsm psm;
        byte byteRead = -1;
        boolean stateProgress = false;
        
    	private InputStream _input = null;

        
	    public TransportReaderThread(){
	    	psm = new SdlPsm();
	    }
		public void halt() {
			isHalted = true;
		}
		
		private void acceptConnection() {
			SdlTrace.logTransportEvent("BTTransport: Waiting for incoming RFCOMM connect", "", InterfaceActivityDirection.Receive, null, 0, SDL_LIB_TRACE_KEY);

			try {
				// Blocks thread until connection established.
				_activeSocket = _serverSocket.accept();
				
				// If halted after serverSocket.accept(), then return immediately
				if (isHalted) {
					return;
				}
				
				// Log info of the connected device
				BluetoothDevice btDevice = _activeSocket.getRemoteDevice();
				String btDeviceInfoXml = SdlTrace.getBTDeviceInfo(btDevice);
				SdlTrace.logTransportEvent("BTTransport: RFCOMM Connection Accepted", btDeviceInfoXml, InterfaceActivityDirection.Receive, null, 0, SDL_LIB_TRACE_KEY);
				
				_output = _activeSocket.getOutputStream();
				_input = _activeSocket.getInputStream();

				handleTransportConnected();
				
			} catch (Exception e) {
				if (!isHalted) {					
					// Only call disconnect if the thread has not been halted
					clearInputStream();
					// Check to see if Bluetooth was disabled
					if (_adapter != null && !_adapter.isEnabled()) {
						disconnect("Bluetooth Adapater has been disabled.", new SdlException("Bluetooth adapter must be enabled to instantiate a SdlProxy object.", e, SdlExceptionCause.BLUETOOTH_DISABLED));
					} else {
						disconnect("Failed to accept connection", e);
					}
				} 
			}  finally {
					
					if (!bKeepSocketActive && _serverSocket != null && !isHalted && (VERSION.SDK_INT > 0x00000010 /*VERSION_CODES.JELLY_BEAN*/) ) {
						try {
							_serverSocket.close();
						} catch (IOException e) {
							//do nothing
						}
						_serverSocket = null;
					}
			}
		}
		
		private void readFromTransport() {
			try {
				try {
					byteRead = (byte)_input.read();
				} catch (Exception e) {
					if (!isHalted) {
						// Only call disconnect if the thread has not been halted
						clearInputStream();
						// Check to see if Bluetooth was disabled
						if (_adapter != null && !_adapter.isEnabled()) {
							disconnect("Bluetooth Adapater has been disabled.", new SdlException("Bluetooth adapter must be enabled to instantiate a SdlProxy object.", e, SdlExceptionCause.BLUETOOTH_DISABLED));
						} else {
							disconnect("Failed to read from Bluetooth transport.", e);
						}
					}
					return;
				} // end-catch

				stateProgress = psm.handleByte(byteRead); 
				if(!stateProgress){//We are trying to weed through the bad packet info until we get something
					//Log.w(TAG, "Packet State Machine did not move forward from state - "+ psm.getState()+". PSM being Reset.");
					psm.reset();
					if(byteRead == -1){ //If we read a -1 and the psm didn't move forward, then there is a problem
						if (!isHalted) {
							// Only call disconnect if the thread has not been halted
							DebugTool.logError("End of stream reached!");
							disconnect("End of stream reached.", null);
						}
					}
				}
				if(psm.getState() == SdlPsm.FINISHED_STATE){
					//Log.d(TAG, "Packet formed, sending off");
					handleReceivedPacket((SdlPacket)psm.getFormedPacket());
					//We put a trace statement in the message read so we can avoid all the extra bytes
					psm.reset();
				}

			} catch (Exception excp) {
			if (!isHalted) {
				// Only call disconnect if the thread has not been halted
				clearInputStream();
				String errString = "Failure in BTTransport reader thread: " + excp.toString();
				DebugTool.logError(errString, excp);
				disconnect(errString, excp);
			}
			return;
			} // end-catch
		} // end-method
		
		private void clearInputStream(){
			try {
				if (_input != null) {
					_input.close();
					_input = null;
				}
			} catch (Exception e) {
				DebugTool.logError("Failed to close input stream", e);
			} // end-catch
		}
		
		public void run() {
			// acceptConnection blocks until the connection has been accepted
			acceptConnection();
			psm.reset();
			while (!isHalted) {
				readFromTransport();
			}
		}
	}
	
	private class BluetoothAdapterMonitor {
		private boolean _isHalted = false;
		private BluetoothAdapter _bluetoothAdapter = null;
		private final String THREAD_NAME = "BluetoothAdapterMonitor";
		private Thread _bluetoothAdapterMonitorThread = null;
		
		public BluetoothAdapterMonitor(BluetoothAdapter bluetoothAdapter) {
			if (bluetoothAdapter == null) {
				throw new IllegalArgumentException("BluetoothAdapter cannot be null.");
			}
			
			// Set the bluetooth adapter
			_bluetoothAdapter = bluetoothAdapter;
			
			_bluetoothAdapterMonitorThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (!_isHalted) {
						checkIfBluetoothAdapterDisabled();
						try {
							Thread.sleep(15000);
						} catch (InterruptedException e) {
							// Break if interrupted
							break;
						}
					}
				}
			});
			_bluetoothAdapterMonitorThread.setName(THREAD_NAME);
			_bluetoothAdapterMonitorThread.setDaemon(true);
			_bluetoothAdapterMonitorThread.start();
		}
		
		private void checkIfBluetoothAdapterDisabled() {
			if (_bluetoothAdapter != null && !_bluetoothAdapter.isEnabled()) {
				// Bluetooth adapter has been disabled, disconnect the transport
				disconnect("Bluetooth adapter has been disabled.", 
						   new SdlException("Bluetooth adapter must be enabled to instantiate a SdlProxy object.", SdlExceptionCause.BLUETOOTH_DISABLED));
			}
		}
		
		public void halt() {
			_isHalted = true;
			_bluetoothAdapterMonitorThread.interrupt();
		}
	}

	/**
	 * Overridden abstract method which returns specific type of this transport.
	 * 
	 * @return Constant value - TransportType.BLUETOOTH.
	 * 
	 * @see TransportType
	 */
	public TransportType getTransportType() {
		return TransportType.BLUETOOTH;
	}

	@Override
	public String getBroadcastComment() {
		return sComment;
	}

	@Override
	protected void handleTransportDisconnected(String info) {
		SdlConnection.enableLegacyMode(false, null);
		super.handleTransportDisconnected(info);
	}

	@Override
	protected void handleTransportError(String message, Exception ex) {
		SdlConnection.enableLegacyMode(false, null);
		super.handleTransportError(message, ex);
	}
	
} // end-class
