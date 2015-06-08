package com.smartdevicelink.util;

import android.util.Log;

public class SdlLog {
	private static final String TAG = "SDL Logger";
	private static final String SDL_TAG = "<SDL> ";
	private static final String SDL_TRACE_TAG = "<SDL> Trace: ";

	private static boolean debugMode 				= false;			
	private static boolean transportTraceDebugMode 	= false;			//This bool is to log out the lower level read/write stuff
	
	public static void enableDebug(boolean enable){
		if(enable){
			debugMode = true;
			Log.i(TAG, "<SDL> DEBUG LOGGING ENABLED");
		}
		else{
			debugMode = false;
			Log.i(TAG, "<SDL> DEBUG LOGGING DISABLED");
		}
	}
	/**
	 * This is the basic level of debuging
	 * There is another for low level traces
	 * @return If the debug
	 */
	public static boolean isDebugEnabled(){
		return debugMode;
	}
	
	public static void enableTransportTraceLogging(boolean enable){
		if(enable){
			transportTraceDebugMode = true;
			Log.i(TAG, "<SDL> TRACE LOGGING ENABLED");
		}
		else{
			transportTraceDebugMode = false;
			 Log.i(TAG,"<SDL> TRACE LOGGING DISABLED");
		}
	}
	
	 public static void v(String tag, String message){
		 if(debugMode){
			 if(tag!=null && message!=null){
				 Log.v(tag, SDL_TAG + message);
			 }
			 else{
				 logError();
			 }
		 }
	 }
	 public static boolean isLoggingTrace(){
		 return transportTraceDebugMode;
	 }
	 
	 
	 public static void d(String tag, String message){
		 if(debugMode){
			 if(tag!=null && message!=null){
				 Log.d(tag, SDL_TAG + message);
			 }
			 else{
				 logError();
			 }
		 }
	 }
	 public static void i(String tag, String message){
		 if(debugMode){
			 if(tag!=null && message!=null){
				 Log.i(tag, SDL_TAG + message);
			 }
			 else{
				 logError();
			 }
		 }
	 }
	 public static void w(String tag, String message){
		 if(debugMode){
			 if(tag!=null && message!=null){
				 Log.w(tag, SDL_TAG + message);
			 }
			 else{
				 logError();
			 }
		 }
	 }
	 public static void e(String tag, String message){
		 if(debugMode){
			 if(tag!=null && message!=null){
				 Log.e(tag, SDL_TAG + message);
			 }
			 else{
				 logError();
			 }
		 }
	 }
	 
	 public static void trace(String tag, String message){
		 if(transportTraceDebugMode){
			 if(tag!=null && message!=null){
				 Log.d(tag, SDL_TRACE_TAG + message);
			 }
			 else{
				 logError();
			 }
		 }
	 }	 
	 private static void logError(){
		 Log.e(TAG, SDL_TAG + "ERROR LOGGING");

	 }
}
