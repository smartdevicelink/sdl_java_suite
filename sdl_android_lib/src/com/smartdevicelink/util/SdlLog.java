package com.smartdevicelink.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import android.util.Log;

public class SdlLog {

	private final static String TAG = "SDL Log";
	private final static String SDL_TAG = "<SDL> ";
	private final static String SDL_TRACE_TAG = "<SDL TRACE> ";



	public static final int SDL_LOG_LEVEL_OFF 		= 0;
	/**
	 * Used to log low-level SDL specific information.
	 */
	public static final int SDL_LOG_LEVEL_TRACE 	= 10;
	/** 
	 * Used for non-critical information needed for development only.
	 */
	public static final int SDL_LOG_LEVEL_VERBOSE 	= 20;
	/** 
	 *Used for information needed for development only.
	 */
	public static final int SDL_LOG_LEVEL_DEBUG 	= 30;
	/**
	 * Used to log information reports of successful operations.
	 */
	public static final int SDL_LOG_LEVEL_INFO 		= 40;
	/**
	 * Used to log information when an unexpected but tolerable 
	 * behavior occurs.
	 */
	public static final int SDL_LOG_LEVEL_WARN 		= 50;
	/**
	 * Used to log information when an unexpected breaking behavior 
	 * occurs.
	 */
	public static final int SDL_LOG_LEVEL_ERROR 	= 60;
	/**
	 * Used to log cases that should never happen
	 */
	public static final int SDL_LOG_LEVEL_WTF 		= 100;


	private static boolean isFileLoggingEnabled = false;
	private static int logLevel = SDL_LOG_LEVEL_OFF;

	/**
	 * Sets the level of logging this application would like to receive. 
	 * Since logging can force an app to take a performance hit, make sure to 
	 * turn off before pushing to production.
	 * 	/**
	 * 
	 * <p><table border="1" rules="all">
	 * <tr><td>SDL_LOG_LEVEL_OFF</td><td>0</td><td>All Disabled</td></tr>
	 * <tr><td>SDL_LOG_LEVEL_TRACE</td><td>1</td><td>All Enabled</td></tr>
	 * <tr><td>SDL_LOG_LEVEL_VERBOSE</td><td>2</td><td>2-7</td></tr>
	 * <tr><td>SDL_LOG_LEVEL_DEBUG</td><td>3</td><td>3 - 7 Enabled</td></tr>
	 * <tr><td>SDL_LOG_LEVEL_INFO</td><td>4</td><td>4 - 7 Enabled</td></tr>
	 * <tr><td>SDL_LOG_LEVEL_WARN</td><td>5</td><td>5 - 7 Enabled</td></tr>
	 * <tr><td>SDL_LOG_LEVEL_ERROR</td><td>6</td><td>6 - 7 Enabled</td></tr>
	 * <tr><td>SDL_LOG_LEVEL_WTF</td><td>7</td><td>7 Enabled</td></tr>
	 * @param logLevel level constants contained in this class
	 */
	public static void setDebugLevel(int logLevel){
		SdlLog.logLevel = logLevel;
		if(logLevel>0){
			Log.i(TAG, "<SDL> DEBUG LOGGING ENABLED AT LEVEL " + logLevel);
		}
		else{
			Log.i(TAG, "<SDL> DEBUG LOGGING DISABLED");
		}
	}
	/**
	 * This is the basic level of debugging
	 * There is another for low level traces
	 * @return If the debug
	 */
	public static boolean isDebugEnabled(){
		return logLevel>0;
	}
	public static int getDebugLevel(){
		return logLevel;
	}

	public static boolean isTraceLoggingEnabled(){
		return logLevel == SdlLog.SDL_LOG_LEVEL_TRACE;
	}

	/**
	 * Provides whether file logging is enabled or disabled.
	 * 
	 * @return (boolean) The file logging toggle.
	 */
	public static boolean isFileLoggingEnabled() {
		return isFileLoggingEnabled;
	}

	/** Toggles the file logging functionality.
	 * 
	 * @param log Whether file logging is to be enabled or disabled.
	 */
	public static void enableWritingToFile(boolean log) {
		isFileLoggingEnabled = log;
		Log.i(TAG, "File Logging " + ((log) ? "On" : "Off"));
	}



	/**
	 * Send a VERBOSE SDL log message.Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the verbose level. <br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static boolean v(String tag, String message){
		return internalLog(SDL_LOG_LEVEL_TRACE, tag, message, null);
	}
	/**
	 * Send a DEBUG SDL log message. Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the debug level.<br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static boolean d(String tag, String message){
		return internalLog(SDL_LOG_LEVEL_DEBUG, tag, message, null);
	}
	/**
	 * Send a INFO SDL log message. Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the info level.<br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static boolean i(String tag, String message){
		return internalLog(SDL_LOG_LEVEL_INFO, tag, message, null);
	}
	/**
	 * Send a WARNING SDL log message. Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the warning level.<br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static boolean w(String tag, String message){
		return internalLog(SDL_LOG_LEVEL_WARN, tag, message, null);
	}
	/**
	 * Send a ERROR SDL log message.Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the error level. <br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static boolean e(String tag, String message){
		return internalLog(SDL_LOG_LEVEL_ERROR, tag, message, null);
	}
	/**
	 * Send a ERROR SDL log message.Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the error level. <br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 * @param throwable The details of an exception or error, may be null.
	 */
	public static boolean e(String tag, String message,Throwable throwable){
		return internalLog(SDL_LOG_LEVEL_ERROR, tag, message, throwable);
	}
	/**
	 * What a Terrible Failure: Report a condition that should never happen. Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the what a terrible failure level. <br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static boolean wtf(String tag, String message){
		return internalLog(SDL_LOG_LEVEL_WTF, tag, message, null);
	}
	/**
	 * What a Terrible Failure: Report a condition that should never happen. Logs messages to the native Android log tool if the log level allowed
	 * does not exceed the what a terrible failure level. <br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 * @param throwable The details of an exception or error, may be null.
	 */
	public static boolean wtf(String tag, String message,Throwable throwable){
		return internalLog(SDL_LOG_LEVEL_WTF, tag, message, throwable);
	}
	/**
	 * Logs messages that exceed the allowed log level to the native Android log
	 * tool.
	 * 
	 * @param level The logging level of the message.
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message to be logged, may not be null or empty.
	 * @param throwable The details of an exception or error, may be null.
	 * @return (boolean) Whether the message was logged successfully.
	 */
	private static boolean internalLog(int level, String tag, String message, Throwable throwable){
		if(logLevel>0 && logLevel<=level){
			if(tag!=null && message!=null){
				switch(level){
				case SDL_LOG_LEVEL_TRACE:
					Log.d(tag, SDL_TRACE_TAG + message);
					break;
				case SDL_LOG_LEVEL_VERBOSE:
					Log.v(tag, SDL_TAG + message);
					break;
				case SDL_LOG_LEVEL_DEBUG:
					Log.d(tag, SDL_TAG + message);
					break;
				case SDL_LOG_LEVEL_INFO:
					Log.i(tag, SDL_TAG + message);
					break;
				case SDL_LOG_LEVEL_WARN:
					Log.w(tag, SDL_TAG + message);
					break;
				case SDL_LOG_LEVEL_ERROR:
					Log.e(tag, SDL_TAG + message);
					break;
				case SDL_LOG_LEVEL_WTF:
					Log.wtf(tag, SDL_TAG + message);
					break;
					default:
						return false;
				}
			}else{
				logError();
				return false;
			}
		}//else we don't log it
		if (isFileLoggingEnabled) {
			try {
				StringBuilder build = new StringBuilder(TAG);
				if (level == SDL_LOG_LEVEL_TRACE) {
					build.append(SDL_TRACE_TAG);
				}else{
					build.append(SDL_TAG);
				}
				build.append(message);
				build.append("\n");
				if (throwable != null) {
					build.append(throwable.getMessage());
					writeToDisk(build.toString());
				}
			} catch (Throwable t) {
				// This should never happen!
				Log.wtf(TAG, "Logging failure!", t);
				return false;
			}
		}
		return true;
	}

	private static void logError(){
		Log.e(TAG, SDL_TAG + "ERROR LOGGING");

	}

	private static boolean writeToDisk(String message) {

		File logFile = new File("sdcard/sdllog.txt");

		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (Throwable t) {
				e(TAG,"Could not create log file on device.", t);
				return false;
			}
		}

		try {
			i(TAG,"Logging to file 'sdllog.txt' on external storage.");
			BufferedWriter buff = new BufferedWriter(new FileWriter(logFile, true));
			buff.append(message);
			buff.newLine();
			buff.close();
		} catch (Throwable t) {
			e(TAG,"Error writing to log file on device.", t);
			return false;
		}

		return true;
	}
}


