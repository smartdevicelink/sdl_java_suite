package com.smartdevicelink.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.ProtocolFrameHeader;
import com.smartdevicelink.proxy.RPCMessage;

public class SdlLog {
	
	/** 
	 * Should be used for non-critical information needed for development only.
	 */
	private final static int VERBOSE = 0;
	
	/** 
	 * Should be used for information needed for development only.
	 */
	private final static int DEBUG   = 1;
	
	/**
	 * Should be used to log information reports of successful operations.
	 */
	private final static int INFO    = 2;
	
	/**
	 * Should be used to log information when an unexpected but tolerable 
	 * behavior occurs.
	 */
	private final static int WARNING = 3;
	
	/**
	 * Should be used to log information when an unexpected breaking behavior 
	 * occurs.
	 */
	private final static int ERROR   = 4;
		
	private static int enabledLevel = 0;
	private static boolean isFileLoggingEnabled = false;

	private final static String TAG = "SDL Log";
	private final static String SDL = "<SDL> ";
	private final static String SDL_TRACE = "<SDL TRACE> ";
	
	public static enum Mod {
		TRANSPORT(true),
		PROTOCOL(true),
		MARSHALL(true),
		RPC(true),
		APP(true),
		PROXY(true);

		private boolean enabled = false;
		
		Mod(boolean enabled) {
			this.enabled = enabled;
		}
		
		public boolean isEnabled() {
			return enabled;
		}
		
		public void setEnabled(boolean enabledState) {
			enabled = enabledState;
		}
	};
	
	/**
	 * Sets whether all kinds of trace messages will be logged or not.
	 * 
	 * @param enabledState The state to determine if trace messages will be 
	 * logged or not.
	 */
	public static void setAllTraceLogging(boolean enabledState) {
		for (Mod value : Mod.values()) {
			value.setEnabled(enabledState);
		}
	}
	
	/**
	 * Provides whether file logging is enabled or disabled.
	 * 
	 * @return (boolean) The file logging toggle.
	 */
	public static boolean isFileLoggingEnabled() {
		return isFileLoggingEnabled;
	}
	
	/**
	 * Toggles the file logging functionality.
	 * 
	 * @param log Whether file logging is to be enabled or disabled.
	 */
	public static void enableWritingToFile(boolean log) {
		isFileLoggingEnabled = log;
		Log.i(TAG, "File Logging " + ((log) ? "On" : "Off"));
	}
	
	/**
	 * Provides the current logging level allowed.
	 * 
	 * @return (int) The level at which messages are permitted to be logged.
	 */
	public static int getLevelEnabled() {
		return enabledLevel;
	}
	
	/**
	 * Sets the level at which messages are permitted to be logged.
	 * 
	 * <p><table border="1" rules="all">
	 * <tr><td>Verbose</td><td>0</td><td>All Enabled</td></tr>
	 * <tr><td>Debug</td><td>1</td><td>1 - 4 Enabled</td></tr>
	 * <tr><td>Info</td><td>2</td><td>2 - 4 Enabled</td></tr>
	 * <tr><td>Warning</td><td>3</td><td>3 - 4 Enabled</td></tr>
	 * <tr><td>Error</td><td>4</td><td>4 Enabled</td></tr>
	 * <tr><td></td><td>&gt</td><td>All Disabled</td></tr>
	 * 
	 * <p><b>NOTE:</b> Trace logging is handled seperately.
	 * 
	 * @param level The level at which to permit log messages.
	 */
	public static void enableLoggingLevel(int level) {
		enabledLevel = level;
		Log.i(TAG, "Logging Level Enabled: " + level);
	}
		
	/**
	 * Logs messages to the native Android log tool if trace logging has been
	 * enabled.
	 * 
	 * @param mod The type of trace message to be logged.
	 * @param message The message to be logged, may not be null or empty.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	public static boolean t(Mod mod, String message) {
		return log(mod, message);
	}
	
	/**
	 * Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the verbose level.
	 * 
	 * @param message The message to be logged, may not be null or empty.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	public static boolean v(String message) {
		return log(VERBOSE, message, null);
	}
	
	/**
	 * Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the debug level.
	 * 
	 * @param message The message to be logged, may not be null or empty.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	public static boolean d(String message) {
		return log(DEBUG, message, null);
	}
		
	/**
	 * Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the info level.
	 * 
	 * @param message The message to be logged, may not be null or empty.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	public static boolean i(String message) {
		return log(INFO, message, null);
	}
		
	/**
	 * Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the warning level.
	 * 
	 * @param message The message to be logged, may not be null or empty.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	public static boolean w(String message) {
		return log(WARNING, message, null);
	}
	
	/**
	 * Logs messages to the native Android log tool if the log level allowed 
	 * does not exceed the error level.
	 * 
	 * @param message The message to be logged, may not be null or empty.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	public static boolean e(String message) {
		return log(ERROR, message, null);
	}
	
	/**
	 * Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the error level.
	 * 
	 * @param message The message to be logged, may not be null or empty.
	 * @param details The details of an exception or error, may be null.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	public static boolean e(String message, Throwable details) {
		return log(ERROR, message, null);
	}
	
	/**
	 * Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the error level.
	 * 
	 * @param exception An SDL specific exception.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	public static boolean e(SdlException exception) {
		return log(ERROR, exception.toString(), null);
	}
		
	/**
	 * Logs messages that exceed the allowed log level to the native Android log
	 * tool.
	 * 
	 * @param level The logging level of the message.
	 * @param message The message to be logged, may not be null or empty.
	 * @param details The details of an exception or error, may be null.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	private static boolean log(int level, String message, Throwable details) {
	
		// Do not log null or empty messages.
		if (message == null || message.equals("")) {
			return false;
		}
		
		try {
			switch (level) {
				case VERBOSE: if (enabledLevel < 1) Log.v(TAG, SDL + message, details); break;
				case INFO:    if (enabledLevel < 2) Log.i(TAG, SDL + message, details); break;
				case DEBUG:   if (enabledLevel < 3) Log.d(TAG, SDL + message, details); break;
				case WARNING: if (enabledLevel < 4) Log.w(TAG, SDL + message, details); break;
				case ERROR:   if (enabledLevel < 5) Log.e(TAG, SDL + message, details); break;
				default: return false;
			}
			
			if (isFileLoggingEnabled) {
				StringBuilder build = new StringBuilder(TAG);
				build.append(SDL);
				build.append(message);
				build.append("\n");
				if (details != null) build.append(details.getMessage());
				x(build.toString());
			}
		} catch (Throwable t) {
			// This should never happen!
			Log.wtf(TAG, "Logging failure!", t);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Logs trace messages that are allowed to the native Android log tool.
	 * 
	 * @param type The trace type of the message.
	 * @param message The message to be logged, may not be null or empty.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	private static boolean log(Mod type, String message) {
		
		// Do not log null or empty messages.
		if (message == null || message.equals("")) {
			return false;
		}
		
		// Do not log the message if the type of trace logging has been disabled.
		if (!type.isEnabled()) {
			return false;
		}
		
		// Prepend the type information to the trace message.
		message = type + "\n" + message;
		
		try {
			switch (type) {
				case APP:       if (enabledLevel < 3) Log.d(SDL_TRACE, message); break;
				case RPC:       if (enabledLevel < 3) Log.d(SDL_TRACE, message); break;
				case PROXY:     if (enabledLevel < 3) Log.d(SDL_TRACE, message); break;
				case PROTOCOL:  if (enabledLevel < 3) Log.d(SDL_TRACE, message); break;
				case MARSHALL:  if (enabledLevel < 3) Log.d(SDL_TRACE, message); break;
				case TRANSPORT: if (enabledLevel < 3) Log.d(SDL_TRACE, message); break;
				default: return false;
			}
			
			if (isFileLoggingEnabled) {
				StringBuilder build = new StringBuilder(TAG);
				build.append(SDL_TRACE);
				build.append(message);
				build.append("\n");
				x(build.toString());
			}
		} catch (Throwable t) {
			// This should never happen!
			Log.wtf(TAG, "Logging failure!", t);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Constructs the basic formatted trace message.
	 * 
	 * @param direction Whether the message was incoming or outgoing, if it does
	 * not have a direction the value may be null.
	 * @param body The extra information to be logged, may be null.
	 * @param data The byte data to be logged, may be null.
	 * @return (String) A formatted trace message.
	 */
	@SuppressLint("SimpleDateFormat")
	public static String buildBasicTraceMessage(String direction, String body, byte[] data) {
		StringBuilder builder = new StringBuilder();
		
		try {
			builder.append(new SimpleDateFormat("MM-dd-yyyy hh:mm:sssss").format(new Date()));
			builder.append("\n");
		} catch (Exception e) {
			Log.e(SDL, "Could not parse timestamp for trace message.");
		}
				
		if (direction != null) { 
			builder.append("Direction: ");
			builder.append(direction);
			builder.append("\n");
		}
		
		if (body != null) {
			builder.append(body);
		}
		
		if (data != null) {
			builder.append("Bytes: \n");
			builder.append(data);
			builder.append("\n");
		}
		
		return builder.toString();
	}
	
	/**
	 * Constructs the basic formatted protocol trace message.
	 * 
	 * @param direction Whether the message was incoming or outgoing, if it does
	 * not have a direction the value may be null.
	 * @param body The extra information to be logged, may be null.
	 * @param data The byte data to be logged, may be null.
	 * @param header Protocol specific trace information.
	 * @return (String) A formatted trace message.
	 */
	public static String buildProtocolTraceMessage(String direction, String body, byte[] data, ProtocolFrameHeader header) {
		String basic = buildBasicTraceMessage(direction, body, data);
		StringBuilder builder = new StringBuilder(basic);
		
		if (header != null) {		
			builder.append("Version: ");
			builder.append(header.getVersion());
			builder.append("\n");
			builder.append("Compressed State: ");
			builder.append(header.isCompressed());
			builder.append("\n");
			builder.append("Frame Type: ");
			builder.append(header.getFrameType());
			builder.append("\n");
			builder.append("Session Type: ");
			builder.append(header.getSessionType());
			builder.append("\n");
			builder.append("Session Id: ");
			builder.append(header.getSessionID());
			builder.append("\n");
		}
		
		return builder.toString();
	}
	
	/**
	 * Constructs the basic formatted transport trace message.
	 * 
	 * @param direction Whether the message was incoming or outgoing, if it does
	 * not have a direction the value may be null.
	 * @param body The extra information to be logged, may be null.
	 * @param data The byte data to be logged, may be null.
	 * @param btDevice Bluetooth transport specific trace information.
	 * @return (String) A formatted trace message.
	 */
	public static String buildTransportTraceMessage(String direction, String body, byte[] data, BluetoothDevice btDevice) {
		String basic = buildBasicTraceMessage(direction, body, data);
		StringBuilder builder = new StringBuilder(basic);
		
		if (btDevice != null) {
			builder.append("Bluetooth Data -- ");
			builder.append("\n");
			builder.append("Name: ");
			builder.append(btDevice.getName());
			builder.append("\n");
			builder.append("Address: ");
			builder.append(btDevice.getAddress());
			builder.append("\n");
			builder.append("Bond State: ");
			builder.append(btDevice.getBondState());
			builder.append("\n");
		}
		
		return builder.toString();
	}
	
	/**
	 * Constructs the basic formatted rpc trace message.
	 * 
	 * @param direction Whether the message was incoming or outgoing, if it does
	 * not have a direction the value may be null.
	 * @param body The extra information to be logged, may be null.
	 * @param data The byte data to be logged, may be null.
	 * @param rpc The specific rpc trace information.
	 * @return (String) A formatted trace message.
	 */
	public static String buildRpcTraceMessage(String direction, String body, byte[] data, RPCMessage rpc) {
		String basic = buildBasicTraceMessage(direction, body, data);
		StringBuilder builder = new StringBuilder(basic);
		
		if (rpc != null) {
			builder.append("RPC Data -- ");
			builder.append("\n");
			builder.append("Function Name: ");
			builder.append(rpc.getFunctionName());
			builder.append("\n");
			builder.append("Message Type: ");
			builder.append(rpc.getMessageType());
			builder.append("\n");
		}
		
		return builder.toString();
	}
	
	/**
	 * Logs a message to a file on the external storage (SD card) of the device.
	 * 
	 * @param message The information to be logged to the file.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	private static boolean x(String message) {
		
		File logFile = new File("sdcard/sdllog.txt");
		
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (Throwable t) {
				e("Could not create log file on device.", t);
				return false;
			}
		}
		
		try {
			i("Logging to file 'sdllog.txt' on external storage.");
			BufferedWriter buff = new BufferedWriter(new FileWriter(logFile, true));
			buff.append(message);
			buff.newLine();
			buff.close();
		} catch (Throwable t) {
			e("Error writing to log file on device.", t);
			return false;
		}
		
		return true;
	}
}