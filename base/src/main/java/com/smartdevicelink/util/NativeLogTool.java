/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.util;

import android.util.Log;

public class NativeLogTool {
	private static String TAG = "NativeLogTool";
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
		return logInfo(TAG, message);
	}
	
	public static boolean logInfo(String tag, String message) {
		if (logToSystemEnabled) {
			return log(LogTarget.Info, tag, message);
		}
		return false;
	}
	
	public static boolean logWarning(String message) {
		return logWarning(TAG, message);
	}
	
	public static boolean logWarning(String tag, String message) {
		if (logToSystemEnabled) {
			return log(LogTarget.Warning, tag, message);
		}
		return false;
	}
	
	public static boolean logError(String message) {
		return logError(TAG, message);
	}
	
	public static boolean logError(String tag, String message) {
		if (logToSystemEnabled) {
			return log(LogTarget.Error, tag, message);
		}
		return false;
	}
	
	public static boolean logError(String message, Throwable t) {
		return logError(TAG, message, t);
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
		String chunk = null;
		try {
			for (int idx=0;idx < logMsg.length();idx += substrSize) {
				substrSize = Math.min(ChunkSize, logMsg.length() - idx);
				chunk = logMsg.substring(idx, idx + substrSize);
				switch (ltarg) {
					case Info:
						bytesWritten = Log.i(source, chunk);
						break;
					case Warning:
						bytesWritten = Log.w(source, chunk);
						break;
					case Error:
						bytesWritten = Log.e(source, chunk);
						break;
				}
				if (bytesWritten < chunk.length()) {
					Log.w(TAG, "Calling Log.e: msg length=" + chunk.length() + ", bytesWritten=" + bytesWritten);
				}
			}			
		} catch (Exception ex) {
			Log.e(TAG, "Failure writing " + ltarg.name() + " fragments to android log:" + ex.toString());
			return false;
		}		
		return true;
	} 
}
