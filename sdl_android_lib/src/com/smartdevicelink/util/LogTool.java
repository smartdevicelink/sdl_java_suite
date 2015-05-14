package com.smartdevicelink.util;

import java.util.Vector;

import android.util.Log;

import com.smartdevicelink.proxy.SdlProxyBase;
import com.smartdevicelink.proxy.Version;
import com.smartdevicelink.transport.SiphonServer;

/**
 * A utility class for logging library specific messages to the native Android
 * log output, the Siphon Server and the list of console listeners.
 * 
 * @since SmartDeviceLink v4.0
 * @see {@link android.util.Log}
 * @see {@link com.smartdevicelink.transport.SiphonServer}
 */
public class LogTool {
	
	/** 
	 * A boolean flag that indicates whether the INFO and WARNING priority
	 * constants will be logged to the native Android output.
	 */
	private static boolean isNativeLoggingEnabled = true;
	
	/**
	 * A boolean flag that indicates whether to log messages to the Siphon
	 * Server.
	 */
	private static boolean isSiphonServerLoggingEnabled = false;
	
	/**
	 * A boolean flag that indicates whether to log messages to the list of
	 * console listeners.
	 */
	private static boolean isConsoleListenerLoggingEnabled = false;
	
	/**
	 * An integer constant representing the maximum character length of an
	 * individual message logged to the native Android output.
	 */
	private static final int CHUNK_SIZE = 4000;
	
	/**
	 * An integer constant flag used to indicate that a log message is an RPC
	 * sent message for console listeners.
	 */
	private static final int LOG_RPC_SENT = 1;
	
	/**
	 * An integer constant flag used to indicate that a log message is an RPC
	 * received message for console listeners.
	 */
	private static final int LOG_RPC_RECEIVED = 2;
	
	/**
	 * An integer constant flag used to indicate that a log message is a
	 * transport message for console listeners.
	 */
	private static final int LOG_TRANSPORT = 3;
	
	/**
	 * An integer constant flag used to indicate that a log message is an
	 * error message for console listeners.
	 */
	private static final int LOG_ERROR = 4;
	
	/**
	 * The prefix to an error message displayed if an exception occured during
	 * the logging procees.
	 */
	private static final String LOG_FAILURE = "Failure writing message fragments to android log: ";
		
	/**
	 * The prefix to an error message displayed if the message logged was not
	 * equivalent to the message scheduled to be logged.
	 */
	private static final String LOG_PARTIAL = "Failure writing full message length (expected, actual) : ";
		
	/**
	 * The list of console listeners that, if enabled, will receive transport
	 * and RPC message logs.
	 */
	private static Vector<IConsole> consoleListenerList = new Vector<IConsole>();
	
	/**
	 * Enables messages with the INFO and WARNING priority constant tags to be 
	 * logged to the native Android output.
	 */
	public static void setNativeLoggingEnabled () {
		isNativeLoggingEnabled = true;
	}
	
	/**
	 * Disables messages with the INFO and WARN priority constant tags from
	 * being logged to the native Android output.
	 */
	public static void setNativeLoggingDisabled () {
		isNativeLoggingEnabled = false;
	}
	
	/**
	 * Sends the current value of the {@link #isNativeLoggingEnabled} boolean 
	 * flag.
	 * 
	 * @return {@link #isNativeLoggingEnabled} A boolean flag that indicates 
	 * whether INFO and WARNING marked messages will be logged to the native
	 * Android output.
	 */
	public static boolean isNativeLoggingEnabled () {
		return isNativeLoggingEnabled;
	}
	
	/**
	 * Enables messages to be logged to the Siphon Server output.
	 */
	public static void setSiphonServerLoggingEnabled () {
		isSiphonServerLoggingEnabled = true;
	}
	
	/**
	 * Disables messages from being logged to the Siphon Server output.
	 */
	public static void setSiphonServerLoggingDisabled () {
		isSiphonServerLoggingEnabled = false;
	}
	
	/**
	 * Sends the current value of the {@link #isSiphonServerLoggingEnabled} 
	 * boolean flag.
	 * 
	 * @return {@link #isSiphonServerLoggingEnabled} A boolean flag that 
	 * indicates whether messages will be logged to the Siphon Server output.
	 */
	public static boolean isSiphonServerLoggingEnabled () {
		return isSiphonServerLoggingEnabled;
	}
	
	/**
	 * Enables messages to be logged to the list of console listeners.
	 */
	public static void setConsoleListenerLoggingEnabled () {
		isConsoleListenerLoggingEnabled = true;
	}
	
	/**
	 * Disables messages from being logged to the list of console listeners.
	 */
	public static void setConsoleListenerLoggingDisabled () {
		isConsoleListenerLoggingEnabled = false;
	}
	
	/**
	 * Sends the current value of the {@link #isConsoleListenerLoggingEnabled}
	 * boolean flag.
	 * 
	 * @return {@link #isConsoleListenerLoggingEnabled} A boolean flag that
	 * indicates whether messages will be logged to the list of console 
	 * listeners.
	 */
	public static boolean isConsoleListenerLoggingEnabled () {
		return isConsoleListenerLoggingEnabled;
	}
	
	/**
	 * Adds a console listener to the list of listeners that will potentially
	 * be receiving transport and RPC message logs.
	 * 
	 * @param console A console listener object to be added.
	 * @return (boolean) Whether the console listener was added to the list
	 * successfully.
	 */
	public static boolean addConsole (IConsole console) {
		
		if (console == null) {
			return false;
		}

		synchronized(consoleListenerList) {
			consoleListenerList.addElement(console);
			
			if (consoleListenerList.contains(console)) {
				return true;
			}
		}		
		
		return false;
	}
	
	/**
	 * Removes a console listener from the list of listeners that will
	 * potentially be receiving transport and RPC message logs.
	 * 
	 * @param console A console listener object to be removed.
	 * @return (boolean) Whether the console listener was removed from the list
	 * successfully.
	 */
	public static boolean removeConsole (IConsole console) {
		
		if (console == null) {
			return false;
		}

		synchronized(consoleListenerList) {
			consoleListenerList.removeElement(console);
			
			if (!consoleListenerList.contains(console)) {
				return true;
			}
		}		
		
		return false;
	}
	
	/**
	 * Removes all console listeners from the list of listeners that will
	 * potentially be receiving transport and RPC message logs.
	 * 
	 * @return (boolean) Whether the console listeners were all successfully
	 * removed.
	 */
	public static boolean clearConsoles () {
		synchronized(consoleListenerList) {
			consoleListenerList.removeAllElements();
			
			if (consoleListenerList.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Logs the message provided with the INFO priority constant to the native
	 * Android and Siphon Server outputs.
	 * 
	 * @param message A string to be logged.
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled outputs.
	 */
	public static boolean logInfo (String message) {
		return logInfo(SdlProxyBase.TAG, message);
	}
	
	/**
	 * Logs the message provided with the INFO priority constant to the native
	 * Android and Siphon Server outputs.
	 * 
	 * @param message A string to be logged.
	 * @param prepend A flag to indicate if the message is to be prepended with
	 * the version info before being sent to the Siphon Server output.
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled outputs.
	 */
	public static boolean logInfo (String message, boolean prepend) {
		return logInfo(SdlProxyBase.TAG, message, prepend);
	}
	
	/**
	 * Logs the message provided with the INFO priority constant and tag to the
	 * native Android and Siphon Server outputs.
	 * 
	 * @param tag A string representing the sender of the message.
	 * @param message A string to be logged. 
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled outputs.
	 */
	public static boolean logInfo (String tag, String message) {
		
		Boolean wasLogged = true;
		
		if (isSiphonServerLoggingEnabled) {
			String siphonMessage = prependVersionInfo(message);
			wasLogged = logToSiphon(siphonMessage);
		}
		
		if (wasLogged == null || wasLogged == false) {
			return false;
		}		
		
		if (isNativeLoggingEnabled) {
			return log(Log.INFO, tag, message);
		}
		
		return wasLogged;
	}
	
	/**
	 * Logs the message provided with the INFO priority constant and tag to the
	 * native Android and Siphon Server outputs.
	 * 
	 * @param tag A string representing the sender of the message.
	 * @param message A string to be logged. 
	 * @param prepend A flag to indicate if the message is to be prepended with
	 * the version info before being sent to the Siphon Server output.
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled outputs.
	 */
	public static boolean logInfo (String tag, String message, boolean prepend) {

		Boolean wasLogged = true;
		
		if (isSiphonServerLoggingEnabled) {
			if (prepend) {
				String siphonMessage = prependVersionInfo(message);
				wasLogged = logToSiphon(siphonMessage);
			} else {
				wasLogged = logToSiphon(message);
			}			
		}
		
		if (wasLogged == null || wasLogged == false) {
			return false;
		}
		
		if (isNativeLoggingEnabled) {
			return log(Log.INFO, tag, message);
		}
		
		return wasLogged;
	}
	
	/**
	 * Logs the message provided with the WARN priority constant to the native
	 * Android and Siphon Server outputs.
	 * 
	 * @param message A string to be logged. 
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled outputs.
	 */
	public static boolean logWarning (String message) {
		return logWarning(SdlProxyBase.TAG, message);
	}
	
	/**
	 * Logs the message provided with the WARN priority constant and tag to the
	 * native Android and Siphon Server outputs.
	 * 
	 * @param tag A string representing the sender of the message.
	 * @param message A string to be logged. 
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled outputs.
	 */
	public static boolean logWarning (String tag, String message) {
		
		Boolean wasLogged = true;
		
		if (isSiphonServerLoggingEnabled) {
			String siphonMessage = prependVersionInfo(message);
			wasLogged = logToSiphon(siphonMessage);
		}
		
		if (wasLogged == null || wasLogged == false) {
			return false;
		}
		
		if (isNativeLoggingEnabled) {
			return log(Log.WARN, tag, message);
		}
		
		return wasLogged;
	}
	
	/**
	 * Logs the message provided with the ERROR priority constant to the native
	 * Android and Siphon Server outputs.
	 * 
	 * @param message A string to be logged. 
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled outputs.
	 */
	public static boolean logError (String message) {
		return logError(SdlProxyBase.TAG, message);
	}
	
	/**
	 * Logs the message provided with the ERROR priority constant and throwable
	 * to the native Android and Siphon Server outputs.
	 * 
	 * @param message A string to be logged. 
	 * @param throwable Details of what caused the error.
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled outputs.
	 */
	public static boolean logError (String message, Throwable throwable) {
		return logError(SdlProxyBase.TAG, message, throwable);
	}
	
	/**
	 * Logs the message provided with the ERROR priority constant and tag to the
	 * native Android and Siphon Server outputs.
	 * 
	 * @param tag A string representing the sender of the message.
	 * @param message A string to be logged. 
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled outputs.
	 */
	public static boolean logError (String tag, String message) {
		
		String siphonMessage = prependVersionInfo(message);
		Boolean wasLogged = logToSiphon(siphonMessage);
		
		if (wasLogged == null || wasLogged == false) {
			return false;
		}
		
		return log(Log.ERROR, tag, message);
	}
	
	/**
	 * Logs the message provided with the ERROR priority constant, tag and
	 * throwable to the native Android and Siphon Server outputs.
	 * 
	 * @param tag A string representing the sender of the message. 
	 * @param message A string to be logged. 
	 * @param throwable Details of what caused the error.
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled outputs.
	 */
	public static boolean logError (String tag, String message, Throwable throwable) {
		
		// If a throwable was provided then write directly to the system log.
		if (throwable != null) {
			
			String siphonMessage = prependVersionInfo(message);
			Boolean wasLogged = logToSiphon(siphonMessage);
			
			if (wasLogged == null || wasLogged == false) {
				return false;
			}
			
			Log.e(tag, message, throwable);
			return true;
		} else {
			return logError(tag, message);
		}
	}
	
	/**
	 * Logs the message provided with the appropriate target and tag to the
	 * native Android output.
	 * 
	 * @param target The type of information being sent (INFO/WARN/ERROR).
	 * @param tag A string representing the sender of the message.
	 * @param message A string to be logged.
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled outputs.
	 */
	private static boolean log (int target, String tag, String message) {
		
		// Do not log empty messages.
		if (message == null || message.length() == 0) {
			return false;
		}
		
		int bytesWritten  = 0;
		int substringSize = 0;
		
		try {
			
			for (int index = 0; index < message.length(); index += substringSize) {
				
				substringSize = Math.min(CHUNK_SIZE, message.length() - index);
				String chunk  = message.substring(index, index + substringSize);
				
				// Log to output with the appropriate priority constant.
				switch (target) {
					case Log.INFO:
						bytesWritten = Log.i(tag, chunk);
						break;
					case Log.WARN:
						bytesWritten = Log.w(tag, chunk);
						break;
					case Log.ERROR:
						bytesWritten = Log.e(tag, chunk);
						break;
				}
				
				// Check that the entire message was logged.
				if (bytesWritten < chunk.length()) {
					Log.e(tag, LOG_PARTIAL + chunk.length() + "," + bytesWritten);
					return false;
				}
			}
		} catch (Exception exception) {
			Log.e(tag, LOG_FAILURE + exception.toString());
			return false;
		}
		
		return true;
	}
		
	/**
	 * Sends log messages to the Siphon Server, initializing the server if it
	 * has not already been initialized.
	 * 
	 * @param message A string to be logged.
	 * @return (Boolean) Whether the string was logged successfully to the
	 * Siphon Server output.
	 */
	private static Boolean logToSiphon (String message) {
		
		// Initialize the SiphonServer; if it has already been initialized this
		// call will be ignored.
		SiphonServer.init();
		
		return SiphonServer.sendSiphonLogData(message);
	}
	
	/**
	 * Sends transport log messages to the native Android and Siphon Server 
	 * ouputs as well as the list of console listeners.
	 * 
	 * @param message A string to be logged.
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled logging options.
	 */
	public static boolean logTransport (String message) {
		if (isConsoleListenerLoggingEnabled) {			
			boolean wasLogged = logToConsole(LOG_TRANSPORT, message, null);
			
			if (wasLogged) {
				return logInfo(message);
			}
		}
		return false;
	}
	
	/**
	 * Sends RPC sent log messages to the native Android and Siphon Server 
	 * outputs as well as the list of console listeners. 
	 * 
	 * @param message A string to be logged.
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled logging options.
	 */
	public static boolean logRpcSent (String message) {
		if (isConsoleListenerLoggingEnabled) {			
			boolean wasLogged = logToConsole(LOG_RPC_SENT, message, null);
			
			if (wasLogged) {
				return logInfo("RPC message sent: " + message);
			}
		}
		return false;		
	}
	
	/**
	 * Sends RPC received log messages to the native Android and Siphon Server
	 * outputs as well as the list of console listeners.
	 * 
	 * @param message A string to be logged.
	 * @return (boolean) Whether the string was logged successfully to all
	 * enabled logging options.
	 */
	public static boolean logRpcReceived (String message) {
		if (isConsoleListenerLoggingEnabled) {			
			boolean wasLogged = logToConsole(LOG_RPC_RECEIVED, message, null);
			
			if (wasLogged) {
				return logInfo("RPC message received: " + message);
			}
		}
		return false;		
	}
	
	/**
	 * Sends log messages to the list of console listeners.
	 * 
	 * @param flag An integer indicating what kind of message was provided.
	 * @param message A string to be logged.
	 * @param throwable (optional) If provided, and the kind of message is an
	 * error, then the log will also send it to the console listeners.
	 */
	@SuppressWarnings("unchecked")
	public static boolean logToConsole (int flag, String message, Throwable throwable) {
		
		Vector<IConsole> localList = null;
		
		synchronized (consoleListenerList) {
			localList = (Vector<IConsole>) consoleListenerList.clone();
		}
		
		if (localList == null) {
			return false;
		}
		
		for (int index = 0; index < localList.size(); index++) {
			IConsole listener = (IConsole) localList.elementAt(index);
			
			try {
				
				// Determine the type of message that is to be sent to the
				// console listeners.
				switch (flag) {
					case LOG_ERROR:
						if (throwable == null) {
							listener.logError(message);
						} else {
							listener.logError(message, throwable);
						}
						break;
					case LOG_TRANSPORT:
						listener.logInfo(message);
						break;
					case LOG_RPC_SENT:
						listener.logRPCSend(message);
						break;
					case LOG_RPC_RECEIVED:
						listener.logRPCReceive(message);
						break;
					default:
						return false;
				}
				
			} catch (Exception exception) {
				Log.e(SdlProxyBase.TAG, LOG_FAILURE + exception.toString());
				return false;
			}
		}		
		return true;
	}
	
	/**
	 * Prepends the version information to the string.
	 * 
	 * @param message The string to add the version info prefix to.
	 * @return (String) The prefixed message string.
	 */
	private static String prependVersionInfo (String message) {
		
		if (message != null) {
			StringBuilder builder = new StringBuilder();
			builder.append(Version.VERSION);
			builder.append(": ");
			builder.append(message);
			message = builder.toString();
		}
		
		return message;
	}
}