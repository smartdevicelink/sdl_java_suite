package com.smartdevicelink.util;

import android.util.Log;

import com.smartdevicelink.proxy.SdlProxyBase;

public class NativeLogTool {
	
	static private boolean logToSystemEnabled = true;
	private static final int ChunkSize = 4000;
	
	public enum LogTarget {
		Info
		,Warning
		,Error;
		
		public static LogTarget valueForString (String value) {
			try{
				return valueOf(value);
			} catch(Exception e) {
				return null;
			}
		}
	}

	public static void setEnableState(boolean en) {
		logToSystemEnabled = en;
	} // end-method

	public static boolean isEnabled() {
		return logToSystemEnabled;
	} // end-method
	
	public static boolean logInfo(String message) {		
		return logInfo(SdlProxyBase.TAG, message);
	}
	
	public static boolean logInfo(String tag, String message) {
		if (logToSystemEnabled) {
			return log(LogTarget.Info, tag, message);
		}
		return false;
	}
	
	public static boolean logWarning(String message) {
		return logWarning(SdlProxyBase.TAG, message);
	}
	
	public static boolean logWarning(String tag, String message) {
		if (logToSystemEnabled) {
			return log(LogTarget.Warning, tag, message);
		}
		return false;
	}
	
	public static boolean logError(String message) {
		return logError(SdlProxyBase.TAG, message);
	}
	
	public static boolean logError(String tag, String message) {
		if (logToSystemEnabled) {
			return log(LogTarget.Error, tag, message);
		}
		return false;
	}
	
	public static boolean logError(String message, Throwable t) {
		return logError(SdlProxyBase.TAG, message, t);
	}
	
	public static boolean logError(String tag, String message, Throwable t) {
		// If the call to logError is passed a throwable, write directly to the system log
		if (logToSystemEnabled) {
			Log.e(tag, message, t);
		}
		return logToSystemEnabled;
	}
	
	private static boolean log(LogTarget ltarg, String source, String logMsg) {
		// Don't log empty messages
		if (logMsg == null || logMsg.length() == 0) {
			return false;
		}

		int bytesWritten = 0;
		int substrSize = 0;
		String tag = source;
		String chunk = null;
		try {
			for (int idx=0;idx < logMsg.length();idx += substrSize) {
				substrSize = Math.min(ChunkSize, logMsg.length() - idx);
				chunk = logMsg.substring(idx, idx + substrSize);
				switch (ltarg) {
					case Info:
						bytesWritten = Log.i(tag, chunk);
						break;
					case Warning:
						bytesWritten = Log.w(tag, chunk);
						break;
					case Error:
						bytesWritten = Log.e(tag, chunk);
						break;
				}
				if (bytesWritten < chunk.length()) {
					Log.e(SdlProxyBase.TAG, "Calling Log.e: msg length=" + chunk.length() + ", bytesWritten=" + bytesWritten);
				}
			}			
		} catch (Exception ex) {
			Log.e(SdlProxyBase.TAG, "Failure writing " + ltarg.name() + " fragments to android log:" + ex.toString());
			return false;
		}		
		return true;
	} 
}
