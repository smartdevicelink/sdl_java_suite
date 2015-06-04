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

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.enums.SdlExceptionCause;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.trace.enums.InterfaceActivityDirection;
import com.smartdevicelink.transport.interfaces.ITransportListener;
import com.smartdevicelink.util.DebugTool;

/**
 * Bluetooth Transport Implementation. This transport advertises its existence to SDL by publishing an SDP record and waiting for an incoming connection from SDL. Connection is verified by checking for the SDL UUID. For more detailed information please refer to the <a href="#bluetoothTransport">Bluetooth Transport Guide</a>.
 *
 */
public class BtTransport extends SdlTransport {	
	//936DA01F9ABD4D9D80C702AF85C822A8
	private final static UUID SDL_V4_MOBILE_APPLICATION_SVC_CLASS = new UUID(0x936DA01F9ABD4D9DL, 0x80C702AF85C822A8L);

	private static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
	
	private BluetoothAdapter adapter = null;
	private BluetoothSocket activeSocket = null;
	private InputStream input = null;
	private UUID listeningServiceUuid = SDL_V4_MOBILE_APPLICATION_SVC_CLASS;
	private BluetoothAdapterMonitor bluetoothAdapterMonitor = null;
	private TransportReaderThread transportReader = null;
	private OutputStream output = null;
	private BluetoothServerSocket serverSocket = null;
	
	private String sComment = "";
	private boolean bKeepSocketActive = true;
	
	// Boolean to monitor if the transport is in a disconnecting state
    private boolean disconnecting = false;
	
	public BtTransport(ITransportListener transportListener) {
		super(transportListener);
	} // end-ctor

	public BtTransport(ITransportListener transportListener, boolean bKeepSocket) {
		super(transportListener);
		bKeepSocketActive = bKeepSocket;		
	} // end-ctor	
	
	public BluetoothSocket getBtSocket(BluetoothServerSocket bsSocket){
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
		
		if (serverSocket != null) {
			return;
		}		
		
		// Get the device's default Bluetooth Adapter
		adapter = BluetoothAdapter.getDefaultAdapter();
		
			
		// Test if Adapter exists
		if (adapter == null) {
			throw new SdlException("No Bluetooth adapter found. Bluetooth adapter must exist to communicate with SDL.", SdlExceptionCause.BLUETOOTH_ADAPTER_NULL);
		}
		
		// Test if Bluetooth is enabled
		try {
			if (!adapter.isEnabled()) {
				throw new SdlException("Bluetooth adapter must be enabled to instantiate a SdlProxy object.", SdlExceptionCause.BLUETOOTH_DISABLED);
			}
		} catch (SecurityException e) {
			throw new SdlException("Insufficient permissions to interact with the Bluetooth Adapter.", SdlExceptionCause.PERMISSION_DENIED);
		}
		
		// Start BluetoothAdapterMonitor to ensure the Bluetooth Adapter continues to be enabled
		bluetoothAdapterMonitor = new BluetoothAdapterMonitor(adapter);
		
		try {
			serverSocket = adapter.listenUsingRfcommWithServiceRecord("SdlProxy", listeningServiceUuid);				
			BluetoothSocket mySock = getBtSocket(serverSocket);
			int iSocket = getChannel(mySock);

			sComment = "Accepting Connections on SDP Server Port Number: " + iSocket + "\r\n";
			sComment += "Keep Server Socket Open: " + bKeepSocketActive; 
			if (iSocket < 0)
			{
				throw new SdlException("Could not open connection to SDL.", SdlExceptionCause.BLUETOOTH_SOCKET_UNAVAILABLE);
			}			
		} catch (IOException e) {

			throw new SdlException("Could not open connection to SDL.", SdlExceptionCause.BLUETOOTH_SOCKET_UNAVAILABLE);

		} catch (Exception ex) {
			
			// Test to determine if the bluetooth has been disabled since last check			
			if (!adapter.isEnabled()) {
				throw new SdlException("Bluetooth adapter must be on to instantiate a SdlProxy object.", SdlExceptionCause.BLUETOOTH_DISABLED);
			}

			if(((SdlException) ex).getSdlExceptionCause() == SdlExceptionCause.BLUETOOTH_SOCKET_UNAVAILABLE) {

				throw new SdlException("Could not open connection to SDL.", SdlExceptionCause.BLUETOOTH_SOCKET_UNAVAILABLE);

			}
			throw new SdlException("Could not open connection to SDL.", ex, SdlExceptionCause.SDL_CONNECTION_FAILED);
		} 
		
		// Test to ensure serverSocket is not null
		if (serverSocket == null) {
			throw new SdlException("Could not open connection to SDL.", SdlExceptionCause.SDL_CONNECTION_FAILED);
		}
		
		SdlTrace.logTransportEvent("BTTransport: listening for incoming connect to service ID " + listeningServiceUuid, null, InterfaceActivityDirection.Receive, null, 0, SDL_LIB_TRACE_KEY);
		
		// Setup transportReader thread
		transportReader = new TransportReaderThread();
		transportReader.setName("TransportReader");
		transportReader.setDaemon(true);
		transportReader.start();
		
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
		if (disconnecting) {
			// No need to recursively call
			return;
		}		
		disconnecting = true;
		
		String disconnectMsg = (msg == null ? "" : msg);
		if (ex != null) {
			disconnectMsg += ", " + ex.toString();
		} // end-if

		SdlTrace.logTransportEvent("BTTransport.disconnect: " + disconnectMsg, null, InterfaceActivityDirection.Transmit, null, 0, SDL_LIB_TRACE_KEY);

		try {			
			if (transportReader != null) {
				transportReader.halt();
				transportReader = null;
			}
		} catch (Exception e) {
			DebugTool.logError("Failed to stop transport reader thread.", e);
		} // end-catch	
		
		try {
			if (bluetoothAdapterMonitor != null) {
				bluetoothAdapterMonitor.halt();
				bluetoothAdapterMonitor = null;
			}
		} catch (Exception e) {
			DebugTool.logError("Failed to stop adapter monitor thread.", e);
		}
		
		try {
			if (serverSocket != null) {
				serverSocket.close();
				serverSocket = null;
			} 
		} catch (Exception e) {
			DebugTool.logError("Failed to close serverSocket", e);
		} // end-catch
		
		try {
			if (activeSocket != null) {
				activeSocket.close();
				activeSocket = null;
			}
		} catch (Exception e) {
			DebugTool.logError("Failed to close activeSocket", e);
		} // end-catch
		
		try {
			if (input != null) {
				input.close();
				input = null;
			}
		} catch (Exception e) {
			DebugTool.logError("Failed to close input stream", e);
		} // end-catch
		
		try {
			if (output != null) {
				output.close();
				output = null;
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
		disconnecting = false;
	} // end-method

	
	/**
	 * Sends data over the transport.  Takes a byte array and transmits data provided starting at the
	 * offset and of the provided length to fragment transmission.
	 */
	public boolean sendBytesOverTransport(byte[] msgBytes, int offset, int length) {
		boolean sendResult = false;
		try {
			output.write(msgBytes, offset, length);
			sendResult = true;
		} catch (Exception ex) {
			DebugTool.logError("Error writing to Bluetooth socket: " + ex.toString(), ex);
			handleTransportError("Error writing to Bluetooth socket:", ex);
			sendResult = false;
		} // end-catch
		return sendResult;
	} // end-method
	
	
	
	private class TransportReaderThread extends Thread {
		
		byte[] buf = new byte[4096];
		private Boolean isHalted = false;
		
		public void halt() {
			isHalted = true;
		}
		
		private void acceptConnection() {
			SdlTrace.logTransportEvent("BTTransport: Waiting for incoming RFCOMM connect", "", InterfaceActivityDirection.Receive, null, 0, SDL_LIB_TRACE_KEY);
			
			try {
				// Blocks thread until connection established.
				activeSocket = serverSocket.accept();
				
				// If halted after serverSocket.accept(), then return immediately
				if (isHalted) {
					return;
				}
				
				// Log info of the connected device
				BluetoothDevice btDevice = activeSocket.getRemoteDevice();
				String btDeviceInfoXml = SdlTrace.getBTDeviceInfo(btDevice);
				SdlTrace.logTransportEvent("BTTransport: RFCOMM Connection Accepted", btDeviceInfoXml, InterfaceActivityDirection.Receive, null, 0, SDL_LIB_TRACE_KEY);
				
				output = activeSocket.getOutputStream();
				input = activeSocket.getInputStream();

				handleTransportConnected();
				
			} catch (Exception e) {
				
				if (!isHalted) {					
					// Only call disconnect if the thread has not been halted
					
					// Check to see if Bluetooth was disabled
					if (adapter != null && !adapter.isEnabled()) {
						disconnect("Bluetooth Adapater has been disabled.", new SdlException("Bluetooth adapter must be enabled to instantiate a SdlProxy object.", e, SdlExceptionCause.BLUETOOTH_DISABLED));
					} else {
						disconnect("Failed to accept connection", e);
					}
				} 
			}  finally {
					if (!bKeepSocketActive && serverSocket != null && !isHalted && (VERSION.SDK_INT > 0x00000010 /*VERSION_CODES.JELLY_BEAN*/) ) {
						try {
							serverSocket.close();
						} catch (IOException e) {
							//do nothing
						}
						serverSocket = null;
					}
			}
		}
		
		private void readFromTransport() {
			try {
				int bytesRead = -1;
				try {
					bytesRead = input.read(buf);
				} catch (Exception e) {
					if (!isHalted) {
						// Only call disconnect if the thread has not been halted
						
						// Check to see if Bluetooth was disabled
						if (adapter != null && !adapter.isEnabled()) {
							disconnect("Bluetooth Adapater has been disabled.", new SdlException("Bluetooth adapter must be enabled to instantiate a SdlProxy object.", e, SdlExceptionCause.BLUETOOTH_DISABLED));
						} else {
							disconnect("Failed to read from Bluetooth transport.", e);
						}
					}
					return;
				} // end-catch
				
				if (bytesRead != -1) {
					handleReceivedBytes(buf, bytesRead);
				} else {
					// When bytesRead == -1, it indicates end of stream
					if (!isHalted) {
						// Only call disconnect if the thread has not been halted
						DebugTool.logError("End of stream reached!");
						disconnect("End of stream reached.", null);
					}
				}
			} catch (Exception excp) {
				if (!isHalted) {
					// Only call disconnect if the thread has not been halted
					String errString = "Failure in BTTransport reader thread: " + excp.toString();
					DebugTool.logError(errString, excp);
					disconnect(errString, excp);
				}
				return;
			} // end-catch
		} // end-method
		
		public void run() {
			// acceptConnection blocks until the connection has been accepted
			acceptConnection();
			
			while (!isHalted) {
				readFromTransport();
			}
		}
	}
	
	private class BluetoothAdapterMonitor {
		private boolean isHalted = false;
		private BluetoothAdapter bluetoothAdapter = null;
		private final String THREAD_NAME = "BluetoothAdapterMonitor";
		private Thread bluetoothAdapterMonitorThread = null;
		
		public BluetoothAdapterMonitor(BluetoothAdapter bluetoothAdapter) {
			if (bluetoothAdapter == null) {
				throw new IllegalArgumentException("BluetoothAdapter cannot be null.");
			}
			
			// Set the bluetooth adapter
			this.bluetoothAdapter = bluetoothAdapter;
			
			bluetoothAdapterMonitorThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (!isHalted) {
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
			bluetoothAdapterMonitorThread.setName(THREAD_NAME);
			bluetoothAdapterMonitorThread.setDaemon(true);
			bluetoothAdapterMonitorThread.start();
		}
		
		private void checkIfBluetoothAdapterDisabled() {
			if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
				// Bluetooth adapter has been disabled, disconnect the transport
				disconnect("Bluetooth adapter has been disabled.", 
						   new SdlException("Bluetooth adapter must be enabled to instantiate a SdlProxy object.", SdlExceptionCause.BLUETOOTH_DISABLED));
			}
		}
		
		public void halt() {
			isHalted = true;
			bluetoothAdapterMonitorThread.interrupt();
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
	
} // end-class
