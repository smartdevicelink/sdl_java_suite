package com.smartdevicelink.util;

import android.util.Log;

import com.smartdevicelink.exception.SdlException;

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
	
	/**
	 * Should be used to log SDL specific information.
	 */
	private final static int TRACE   = 5; // Logs handled seperately.
	
	private static int enabledLevel = 0;
	private static boolean isTraceEnabled = true;

	private final static String TAG = "SDL Log";
	private final static String SDL = "<SDL> ";
	private final static String SDL_TRACE = "<SDL TRACE> ";
	
	/**
	 * Provides whether trace logging is enabled or disabled.
	 *  
	 * @return (boolean) The trace logging toggle.
	 */
	public static boolean isTraceEnabled() {
		return isTraceEnabled;
	}
	
	/**
	 * Toggles the trace logging functionality.
	 * 
	 * @param trace Whether trace logging is to be enabled or disabled.
	 */
	public static void setTraceEnabled(boolean trace) {
		isTraceEnabled = trace;
		Log.i(TAG, "Trace Logging " + ((trace) ? "On" : "Off"));
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
	 * @param message The message to be logged, may not be null or empty.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	public static boolean t(String message) {
		return log(TRACE, message, null);
	}
	
	/**
	 * Logs messages to the native Android log tool if trace logging has been
	 * enabled.
	 * 
	 * @param message The message to be logged, may not be null or empty.
	 * @param details The details of an exception or error, may be null.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	public static boolean t(String message, Throwable details) {
		return log(TRACE, message, details);
	}
	
	/**
	 * Logs messages to the native Android log tool if trace logging has been
	 * enabled.
	 * 
	 * @param exception An SDL specific exception.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	public static boolean t(SdlException exception) {
		return log(TRACE, exception.toString(), null);
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
				case TRACE:   if (isTraceEnabled)   Log.d(TAG, SDL_TRACE + message, details); break;
				case VERBOSE: if (enabledLevel < 1) Log.v(TAG, SDL + message, details); break;
				case INFO:    if (enabledLevel < 2) Log.i(TAG, SDL + message, details); break;
				case DEBUG:   if (enabledLevel < 3) Log.d(TAG, SDL + message, details); break;
				case WARNING: if (enabledLevel < 4) Log.w(TAG, SDL + message, details); break;
				case ERROR:   if (enabledLevel < 5) Log.e(TAG, SDL + message, details); break;
				default: return false;
			}			
		} catch (Throwable t) {
			// This should never happen!
			Log.wtf(TAG, "Logging failure!", t);
			return false;
		}
		
		return true;
	}
}