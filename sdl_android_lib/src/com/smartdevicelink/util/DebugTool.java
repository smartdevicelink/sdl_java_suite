package com.smartdevicelink.util;

import java.util.Vector;

import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.Version;
import com.smartdevicelink.transport.SiphonServer;

public class DebugTool {
	

	public static final String TAG = "SdlProxy";

	private static boolean isErrorEnabled = false;
	private static boolean isWarningEnabled = false;
	private static boolean isInfoEnabled = false;
	
	public static void enableDebugTool() {
		isErrorEnabled = true;
		isWarningEnabled = true;
		isInfoEnabled = true;
	}

	public static void disableDebugTool() {
		isErrorEnabled = true;
		isWarningEnabled = false;
		isInfoEnabled = false;
	}
	
	public static boolean isDebugEnabled() 
	{
		if (isWarningEnabled && isInfoEnabled) return true;
		
		return false;		
	}
	
	private static String prependProxyVersionNumberToString(String string) {
		if (Version.VERSION != null && string != null) {
			string = Version.VERSION + ": " + string;
		}
		
		return string;
	}

	public static void logError(String msg) {
		
		Boolean wasWritten = false;
		
		msg = prependProxyVersionNumberToString(msg);
		
		wasWritten = logToSiphon(msg);
		
		if (isErrorEnabled && !wasWritten) {
			NativeLogTool.logError(TAG, msg);
		}
	}

	public static void logError(String msg, Throwable ex) {
		Boolean wasWritten = false;
		
		msg = prependProxyVersionNumberToString(msg);
		
		if (ex != null) {
			wasWritten = logToSiphon(msg + " Exception String: " + ex.toString());
		} else {
			wasWritten = logToSiphon(msg);
		}
		
		if (isErrorEnabled && !wasWritten) {
			NativeLogTool.logError(TAG, msg, ex);
		}
	}
	
	public static void logWarning(String msg) {
		Boolean wasWritten = false;
		
		msg = prependProxyVersionNumberToString(msg);
		
		wasWritten = logToSiphon(msg);
		
		if (isWarningEnabled && !wasWritten) {
			NativeLogTool.logWarning(TAG, msg);
		}
	}

	public static void logInfo(String msg) {
		Boolean wasWritten = false;
		
		msg = prependProxyVersionNumberToString(msg);
		
		wasWritten = logToSiphon(msg);
		
		if (isInfoEnabled && !wasWritten) {
			NativeLogTool.logInfo(TAG, msg);
		}
	}

	public static void logInfo(String msg, boolean bPrependVersion) {
		Boolean wasWritten = false;
		
		if (bPrependVersion) msg = prependProxyVersionNumberToString(msg);
		
		wasWritten = logToSiphon(msg);
		
		if (isInfoEnabled && !wasWritten) {
			NativeLogTool.logInfo(TAG, msg);
		}
	}
	
	protected static Boolean logToSiphon(String msg) {
		if (SiphonServer.getSiphonEnabledStatus()) {
			// Initialize the SiphonServer, will be ignored if already initialized
			SiphonServer.init();
		
			// Write to the SiphonServer
			return SiphonServer.sendSiphonLogData(msg);
		}
		return false;
	}

	protected static String getLine(Throwable ex) {
		if (ex == null) { return null; }
		String toPrint = ex.toString() + " :" + ex.getMessage();
		for (int i=0; i<ex.getStackTrace().length; i++) {
			StackTraceElement elem = ex.getStackTrace()[i];
			toPrint += "\n  " + elem.toString();
		}
		
		if (ex instanceof SdlException) {
			SdlException sdlEx = (SdlException) ex;
			if (sdlEx.getInnerException() != null && sdlEx != sdlEx.getInnerException()) {
				toPrint += "\n  nested:\n";
				toPrint += getLine(sdlEx.getInnerException());
			}
		}
		
		return toPrint;
	}


	protected static Vector<IConsole> consoleListenerList = new Vector<IConsole>();

	protected final static boolean isTransportEnabled = false;
	protected final static boolean isRPCEnabled = false;

	public static void addConsole(IConsole console) {
		synchronized(consoleListenerList) {
			consoleListenerList.addElement(console);
		}
	}

	public static void removeConsole(IConsole console) {
		synchronized(consoleListenerList) {
			consoleListenerList.removeElement(console);
		}
	}

	public static void clearConsoles() {
		synchronized(consoleListenerList) {
			consoleListenerList.removeAllElements();
		}
	}
	
	public static void logTransport(String msg) {
		if (isTransportEnabled) {
			Log.d(TAG, msg);
			logInfoToConsole(msg);
		}
	}

	public static void logRPCSend(String rpcMsg) {
		if (isRPCEnabled) {
			Log.d(TAG, "Sending RPC message: " + rpcMsg);
			logRPCSendToConsole(rpcMsg);
		}
	}

	public static void logRPCReceive(String rpcMsg) {
		if (isRPCEnabled) {
			Log.d(TAG, "Received RPC message: " + rpcMsg);
			logRPCSendToConsole(rpcMsg);
		}
	}

	@SuppressWarnings("unchecked")
    protected static void logInfoToConsole(String msg) {
		Vector<IConsole> localList;
		synchronized(consoleListenerList) {
			localList = (Vector<IConsole>) consoleListenerList.clone();
		}
		
		for (int i = 0; i < localList.size(); i++) {
			IConsole consoleListener = (IConsole) localList.elementAt(i);
			try {
				consoleListener.logInfo(msg);
			} catch (Exception ex) {
				Log.e(TAG, "Failure propagating logInfo: " + ex.toString(), ex);
			} // end-catch
		}
	}
	
	@SuppressWarnings("unchecked")
    protected static void logErrorToConsole(String msg) {
		Vector<IConsole> localList;
		synchronized(consoleListenerList) {
			localList = (Vector<IConsole>) consoleListenerList.clone();
		}
		for (int i = 0; i < localList.size(); i++) {
			IConsole consoleListener = (IConsole) localList.elementAt(i);
			try {
				consoleListener.logError(msg);
			} catch (Exception ex) {
				Log.e(TAG, "Failure propagating logError: " + ex.toString(), ex);
			} // end-catch
		}
	}
	
	@SuppressWarnings("unchecked")
    protected static void logErrorToConsole(String msg, Throwable e) {
		Vector<IConsole> localList;
		synchronized(consoleListenerList) {
			localList = (Vector<IConsole>) consoleListenerList.clone();
		}
		
		for (int i = 0; i < localList.size(); i++) {
			IConsole consoleListener = (IConsole) localList.elementAt(i);
			try {
				consoleListener.logError(msg, e);
			} catch (Exception ex) {
				Log.e(TAG, "Failure propagating logError: " + ex.toString(), ex);
			} // end-catch
		}
	}
	
	@SuppressWarnings("unchecked")
    protected static void logRPCSendToConsole(String msg) {
		Vector<IConsole> localList;
		synchronized(consoleListenerList) {
			localList = (Vector<IConsole>) consoleListenerList.clone();
		}
		
		for (int i = 0; i < localList.size(); i++) {
			IConsole consoleListener = (IConsole) localList.elementAt(i);
			try {
				consoleListener.logRPCSend(msg);
			} catch (Exception ex) {
				Log.e(TAG, "Failure propagating logRPCSend: " + ex.toString(), ex);
			} // end-catch
		}
	}
	
	@SuppressWarnings("unchecked")
    protected static void logRPCReceiveToConsole(String msg) {
		Vector<IConsole> localList;
		synchronized(consoleListenerList) {
			localList = (Vector<IConsole>) consoleListenerList.clone();
		}
		
		for (int i = 0; i < localList.size(); i++) {
			IConsole consoleListener = (IConsole) localList.elementAt(i);
			try {
				consoleListener.logRPCReceive(msg);
			} catch (Exception ex) {
				Log.e(TAG, "Failure propagating logRPCReceive: " + ex.toString(), ex);
			} // end-catch
		}
	}
}
