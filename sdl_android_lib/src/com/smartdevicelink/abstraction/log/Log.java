package com.smartdevicelink.abstraction.log;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;

import com.smartdevicelink.abstraction.log.LogService.LogServiceBinder;

public class Log {
	
	private static final String TAG = Log.class.getSimpleName();

	public static final String LOG_FILE_EXT = ".log";
	
	private static String fileName = "log";
	private static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	private static boolean enableFileLog = false;
	private static boolean mWaitingToBind = false;
	private static LogFilter filterLevel = LogFilter.I;
	private static LogService logService = null;
	private static Object logLock = new Object();
	private static ArrayList<String> mQueuedWaitingForBind = new ArrayList<String>();
	
	private static LogListener logListener = null;

	public enum LogFilter {
		V, D, I, W, E
	}
	
	enum LogExtras{
		FILE_PATH,
		FILE_NAME
	}
	
	public static boolean setLogListener(LogListener listner){
		if(logListener == null){
			logListener = listner;
			return true;
		} else {
			return false;
		}
	}
	
	public static void clearLogListener(){
		logListener = null;
	}

	public static void v(String tag, String msg) {
		// Log to logcat v
		android.util.Log.v(tag, msg);
		
		if(!enableFileLog) return;
		
		if (filterLevel == LogFilter.V) {
			// Log to file
			String[] logMsg = makeLogString(tag, msg, LogFilter.V);
			writeToLog(logMsg);
		}

	}
	
	public static void v(String tag, String msg, Throwable t) {
		// Log to logcat v
		android.util.Log.v(tag, msg, t);
		
		if(!enableFileLog) return;
		
		if (filterLevel == LogFilter.V) {
			// Log to file
			String[] logMsg = makeLogString(tag, msg, LogFilter.V);
			writeToLog(logMsg);
		}

	}

	public static void d(String tag, String msg) {
		// Log to logcat d
		android.util.Log.d(tag, msg);
	
		if(!enableFileLog) return;
		
		switch (filterLevel) {
		case E:
		case W:
		case I:
			break;
		case D:
		case V:
			// Log to file
			String[] logMsg = makeLogString(tag, msg, LogFilter.D);
			writeToLog(logMsg);
			break;
		default:
			break;
		}

	}

	public static void d(String tag, String msg, Throwable t) {
		// Log to logcat d
		android.util.Log.d(tag, msg, t);
	
		if(!enableFileLog) return;
		
		switch (filterLevel) {
		case E:
		case W:
		case I:
			break;
		case D:
		case V:
			// Log to file
			String[] logMsg = makeLogString(tag, msg, t, LogFilter.D);
			writeToLog(logMsg);
			break;
		default:
			break;
		}

	}

	public static void i(String tag, String msg) {
		// Log to logcat i
		android.util.Log.i(tag, msg);
	
		if(!enableFileLog) return;
		
		switch (filterLevel) {
		case E:
		case W:
			break;
		case I:
		case D:
		case V:
			// Log to file
			String[] logMsg = makeLogString(tag, msg, LogFilter.I);
			writeToLog(logMsg);
			break;
		default:
			break;
		}

	}

	public static void i(String tag, String msg, Throwable t) {
		// Log to logcat i
		android.util.Log.i(tag, msg, t);
	
		if(!enableFileLog) return;
		
		switch (filterLevel) {
		case E:
		case W:
			break;
		case I:
		case D:
		case V:
			// Log to file
			String[] logMsg = makeLogString(tag, msg, t, LogFilter.I);
			writeToLog(logMsg);
			break;
		default:
			break;
		}

	}

	public static void w(String tag, String msg) {
		// Log to logcat w
		android.util.Log.w(tag, msg);
	
		if(!enableFileLog) return;
		
		switch (filterLevel) {
		case E:
			break;
		case W:
		case I:
		case D:
		case V:
			// Log to file
			String[] logMsg = makeLogString(tag, msg, LogFilter.W);
			writeToLog(logMsg);
			break;
		default:
			break;
		}

	}

	public static void w(String tag, String msg, Throwable t) {
		// Log to logcat w
		android.util.Log.w(tag, msg, t);
	
		if(!enableFileLog) return;
		
		switch (filterLevel) {
		case E:
			break;
		case W:
		case I:
		case D:
		case V:
			// Log to file
			String[] logMsg = makeLogString(tag, msg, t, LogFilter.W);
			writeToLog(logMsg);
			break;
		default:
			break;
		}

	}

	public static void e(String tag, String msg) {
		// Log to logcat e
		android.util.Log.e(tag, msg);
	
		if(!enableFileLog) return;
		
		switch (filterLevel) {
		case E:
		case W:
		case I:
		case D:
		case V:
			// Log to file
			String[] logMsg = makeLogString(tag, msg, LogFilter.E);
			writeToLog(logMsg);
			break;
		default:
			break;
		}

	}
	
	public static void e(String tag, String msg, Throwable t) {
		// Log to logcat e
		android.util.Log.e(tag, msg, t);
	
		if(!enableFileLog) return;
		
		switch (filterLevel) {
		case E:
		case W:
		case I:
		case D:
		case V:
			// Log to file
			String[] logMsg = makeLogString(tag, msg, t, LogFilter.E);
			writeToLog(logMsg);
			break;
		default:
			break;
		}

	}
	
	private static String stackTraceString(Throwable t){
		if(t == null){
			return "";
		}
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}
	
	private static String[] makeLogString(String tag, String msg, LogFilter lf){
		return makeLogString(tag, msg, null, lf);
	}

	private static String[] makeLogString(String tag, String msg, Throwable tr, LogFilter lf) {
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
		String[] lines = null;
		if(msg != null){
			lines = msg.split("\n");
		} else {
			lines = new String[]{""};
		}
		String stackTrace = stackTraceString(tr);
		String[] stackLines = null;
		int totalLines;
		if (!stackTrace.equalsIgnoreCase("")) {
			stackLines = stackTrace.split("\n");
			totalLines = lines.length + stackLines.length;
		} else {
			totalLines = lines.length;
		}
		String[] returnLines = new String[totalLines];
		
		for (int i = 0; i < totalLines; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(sdf.format(new Date(time)));
			sb.append(": ");
			sb.append(lf.name());
			sb.append("/");
			sb.append(tag);
			sb.append("(");
			sb.append(android.os.Process.myPid());
			sb.append("[");
			sb.append(android.os.Process.myTid());
			sb.append("]): ");
			
			if(i > 0 && i < lines.length) sb.append("\t");
			
			if(i<lines.length){
				sb.append(lines[i]);
			}else{
				sb.append(stackLines[i-lines.length]);
			}
			
			sb.append("\n");
			returnLines[i] = sb.toString();
		}
		
		return returnLines;
	}

	private static void writeToLog(String[] logStrings){
		synchronized (logLock) {
			if (logService != null) {
				logService.writeToLog(logStrings);
			} else if(mWaitingToBind){
				for(int i = 0; i < logStrings.length; i++){
					mQueuedWaitingForBind.add(logStrings[i]);
				}
			} else {
				android.util.Log.d(TAG, "logService not available");
			}			

			if(logListener != null){
				logListener.onLogWrite(logStrings);
			}
		}
	}

	public static void setLogFilter(LogFilter f) {
		filterLevel = f;
	}

	public static LogFilter getFilterLevel() {
		return filterLevel;
	}

	public static boolean isEnableFileLog() {
		return enableFileLog;
	}
	
	public static void setLogFileName(String fileName){
		if(fileName != null){
			String iFileName;
			if(fileName.startsWith("/")){
				iFileName = fileName.substring(1);
			} else {
				iFileName = fileName;
			}
			
			String[] tokens = iFileName.split("\\.");
			if(tokens.length > 0){
				iFileName = tokens[0];
			} else {
				iFileName = fileName;
			}
			
			if(!Log.fileName.equals(iFileName)){
				Log.fileName = iFileName;	
			}
		}
	}
	
	public static String getFileName(){
		return Log.fileName + LOG_FILE_EXT;
	}
	
	public static void setFilePath(String filePath){
		String iFilePath;
		if(filePath == null) {
			iFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
		} else if (filePath.endsWith("/")){
			iFilePath = filePath;
		} else {
			iFilePath = filePath.substring(0, filePath.length()-1);
		}
		
		if(iFilePath.startsWith("/")){
			iFilePath = iFilePath.substring(1);
		}
		
		if(!Log.filePath.equals(iFilePath)){
			Log.filePath = iFilePath;
		}
	}
	
	public static String getFilePath(String filePath){
		return Log.filePath;
	}
	
	public static void flushCache(boolean saveToFile){
		if(logService != null){
			logService.clearLogCache(saveToFile);
		}
	}
	
	public static void forceLogPartialBreak(){
		if(logService != null){
			logService.forceLogPartBreak();
		}
	}
	
	public static void deleteLogs(String filePath, String fileName){
		setLogFileName(fileName);
		setFilePath(filePath);
		
		deleteLogs();		
	}

	public static void deleteLogs() {
		deleteLogHistory();
		
		File log = new File(Log.filePath + Log.fileName);
		if(log.exists()){
			log.delete();
		}
	}
	
	public static void deleteLogHistory(){
		for(int i = LogService.MAX_LOG_PARTS - 1; i > 0 ; i--){
			File logPart = new File(Log.filePath + Log.fileName + "_" + i);
			if(logPart.exists()){
				logPart.delete();
			}
		}
	}

	public static void enableFileLog(boolean enableFileLog, Context context) {
		if(enableFileLog == Log.enableFileLog) return;
		Log.enableFileLog = enableFileLog;
		if(enableFileLog){
			android.util.Log.d(TAG, "attempting to bind service");
			Intent intent = new Intent(context, LogService.class);
			mWaitingToBind = context.bindService(intent, 
					mLogConnection, Context.BIND_AUTO_CREATE);
		} else {
			context.unbindService(mLogConnection);
			logService = null;
		}
	}
	
	public static UncaughtExceptionHandler getUncaughtExceptionHandler(){
		return new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				e("UncaughtException", "UncaughtException", ex);
				Log.flushCache(true);
			}
		};
	}
	
	private static ServiceConnection mLogConnection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			synchronized (logLock) {
				logService = ((LogServiceBinder) service).getLogger();
				logService.setFileName(fileName);
				logService.setFilePath(filePath);				
				mWaitingToBind = false;
				String[] queuedArray = new String[mQueuedWaitingForBind.size()];
				writeToLog(mQueuedWaitingForBind.toArray(queuedArray));
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			logService = null;
		}
	};

}
