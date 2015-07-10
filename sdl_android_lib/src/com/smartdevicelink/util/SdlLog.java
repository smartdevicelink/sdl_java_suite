package com.smartdevicelink.util;

import android.util.Log;

public class SdlLog {
	private static final String TAG = "SDL Logger";
	private static final String SDL_TAG = "<SDL> ";
	private static final String SDL_TRACE_TAG = "<SDL> Trace: ";

	public static final int SDL_LOG_LEVEL_OFF 		= 0;
	public static final int SDL_LOG_LEVEL_TRACE 	= 10;
	public static final int SDL_LOG_LEVEL_VERBOSE 	= 20;
	public static final int SDL_LOG_LEVEL_DEBUG 	= 30;
	public static final int SDL_LOG_LEVEL_INFO 		= 40;
	public static final int SDL_LOG_LEVEL_WARN 		= 50;
	public static final int SDL_LOG_LEVEL_ERROR 	= 60;
	public static final int SDL_LOG_LEVEL_WTF 		= 100;

	private static int logLevel = SDL_LOG_LEVEL_OFF;

	/**
	 * Sets the level of logging this application would like to receive. 
	 * Since logging can force an app to take a performance hit, make sure to 
	 * turn off before pushing to production.
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

	public static void trace(String tag, String message){
		internalLog(SDL_LOG_LEVEL_TRACE, tag, message);
	}
	
	/**
	 * Send a VERBOSE SDL log message. <br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static void v(String tag, String message){
		internalLog(SDL_LOG_LEVEL_TRACE, tag, message);
	}
	/**
	 * Send a DEBUG SDL log message. <br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static void d(String tag, String message){
		internalLog(SDL_LOG_LEVEL_DEBUG, tag, message);
	}
	/**
	 * Send a INFO SDL log message. <br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static void i(String tag, String message){
		internalLog(SDL_LOG_LEVEL_INFO, tag, message);
	}
	/**
	 * Send a WARNING SDL log message. <br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static void w(String tag, String message){
		internalLog(SDL_LOG_LEVEL_WARN, tag, message);
	}
	/**
	 * Send a ERROR SDL log message. <br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static void e(String tag, String message){
		internalLog(SDL_LOG_LEVEL_ERROR, tag, message);
	}
	/**
	 * What a Terrible Failure: Report a condition that should never happen.  <br>It will have the string "<b>&lt;SDL&gt;</b>" attached to the tag for easy filtering
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static void wtf(String tag, String message){
		internalLog(SDL_LOG_LEVEL_WTF, tag, message);
	}

	private static void internalLog(int level, String tag, String message){
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
				}
			}
			else{
				logError();
			}
		}//else we don't log it
	}

	private static void logError(){
		Log.e(TAG, SDL_TAG + "ERROR LOGGING");

	}
}
