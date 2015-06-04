package com.smartdevicelink.util;

import android.util.Log;

import com.smartdevicelink.proxy.SdlProxyBase;

public class NativeLogTool {
	
	static private boolean logToSystemEnabled = true;
	private static final int CHUNK_SIZE = 4000;
	
	private enum LogTarget {
		INFO
		,WARNING
		,ERROR
	}

	public static void setEnableState(boolean en) {
		logToSystemEnabled = en;
	} // end-method

	public static boolean isEnabled() {
		return logToSystemEnabled;
	} // end-method
	
	public static void logInfo(String message) {
		logInfo(SdlProxyBase.TAG, message);
	}
	
	public static void logInfo(String tag, String message) {
		if (logToSystemEnabled) {
			log(LogTarget.INFO, tag, message);
		}
	}
	
	public static void logWarning(String message) {
		logWarning(SdlProxyBase.TAG, message);
	}
	
	public static void logWarning(String tag, String message) {
		if (logToSystemEnabled) {
			log(LogTarget.WARNING, tag, message);
		}
	}
	
	public static void logError(String message) {
		logError(SdlProxyBase.TAG, message);
	}
	
	public static void logError(String tag, String message) {
		if (logToSystemEnabled) {
			log(LogTarget.ERROR, tag, message);
		}
	}
	
	public static void logError(String message, Throwable t) {
		logError(SdlProxyBase.TAG, message, t);
	}
	
	public static void logError(String tag, String message, Throwable t) {
		// If the call to logError is passed a throwable, write directly to the system log
		if (logToSystemEnabled) {
			Log.e(tag, message, t);
		}
	}
	
	private static void log(LogTarget ltarg, String source, String logMsg) {
		// Don't log empty messages
		if (logMsg == null || logMsg.length() == 0) {
			return;
		} // end-if

		int bytesWritten = 0;
		int substrSize = 0;
		String tag = source;
		String chunk = null;
		try {
			for (int idx=0;idx < logMsg.length();idx += substrSize) {
				substrSize = Math.min(CHUNK_SIZE, logMsg.length() - idx);
				chunk = logMsg.substring(idx, idx + substrSize);
				switch (ltarg) {
					case INFO:
						bytesWritten = Log.i(tag, chunk);
						break;
					case WARNING:
						bytesWritten = Log.w(tag, chunk);
						break;
					case ERROR:
						bytesWritten = Log.e(tag, chunk);
						break;
				} // end-switch
				if (bytesWritten < chunk.length()) {
					Log.e(SdlProxyBase.TAG, "Calling Log.e: msg length=" + chunk.length() + ", bytesWritten=" + bytesWritten);
				} // end-if
			} // end-while
		} catch (Exception ex) {
			Log.e(SdlProxyBase.TAG, "Failure writing " + ltarg.name() + " fragments to android log:" + ex.toString());
		} // end-catch
	} // end-method
}
