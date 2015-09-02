package com.smartdevicelink.abstraction.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

public class LogService extends Service {

	private static final int MAX_LINE_BUFFER = 10;
	static final int MAX_LOG_PARTS = 5;
	private static final int MAX_LOG_PART_SIZE = 1000*1024;
	
	private String fileName;
	private String filePath;
	private boolean forcePartBreak = false;
	
	private ArrayList<String> inBuffer;
	private ArrayList<String> outBuffer;
	
	private final LogServiceBinder mBinder = new LogServiceBinder();
	
	@Override
	public void onCreate() {
		fileName = "log";
		filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/";
		inBuffer = new ArrayList<String>();
		outBuffer = new ArrayList<String>();
		super.onCreate();
	}	

	@Override
	public void onDestroy() {
		outputLogToFile();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	void setFilePath(String filePath) {
		synchronized (outBuffer) {			
			if(!this.filePath.equals(filePath)){
				clearLogCache(false);
				this.filePath = filePath;
			}
		}
	}
	
	void setFileName(String fileName){
		if(fileName != null){			
			if(!this.fileName.equals(fileName)){
				clearLogCache(false);
				synchronized (outBuffer) {
					this.fileName = fileName;
				}				
			}
		}
	}
	
	void writeToLog(String[] messages){
		synchronized (inBuffer) {
			for(int i = 0; i < messages.length; i++){
				inBuffer.add(messages[i]);
			}
			if (inBuffer.size() >= MAX_LINE_BUFFER) {
				outputLogToFile();
			}
		}
	}
	
	void clearLogCache(boolean saveToFile){
		if(saveToFile) {
			outputLogToFile();
		} else {
			synchronized (outBuffer) {
				inBuffer = new ArrayList<String>();
				outBuffer = new ArrayList<String>();
			}
		}
	}
	
	void forceLogPartBreak(){
		clearLogCache(true);
		synchronized(outBuffer){
			forcePartBreak = true;
		}
		outputLogToFile();
	}
	
	private void outputLogToFile(){
		synchronized (outBuffer) {
			outBuffer = inBuffer;
			inBuffer = new ArrayList<String>();
		}
		Thread fileWriterThread = new Thread(mFileWriterRunnable);
		fileWriterThread.run();
	}
	
	private Runnable mFileWriterRunnable = new Runnable(){

		@Override
		public void run() {
			synchronized (outBuffer) {
				File logFile = new File(filePath + fileName + Log.LOG_FILE_EXT);
				if(!logFile.exists()){
					File dir = new File(filePath);
					dir.mkdirs();
					try {
						logFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else if(forcePartBreak || logFile.length() > MAX_LOG_PART_SIZE){
					// Increment file names and overwrite last log part
					for(int i = MAX_LOG_PARTS - 2; i > 0 ; i--){
						File logPart = new File(filePath + fileName + "_" + i + Log.LOG_FILE_EXT);
						if(logPart.exists()){
							logPart.renameTo(new File(filePath + fileName + "_" + (i+1) + Log.LOG_FILE_EXT));
						}
					}
					// Move root log to partial number 1
					logFile.renameTo(new File(filePath + fileName + "_" + 1 + Log.LOG_FILE_EXT));
					// Delete root log and create a new file
					logFile = new File(filePath + fileName + Log.LOG_FILE_EXT);
					logFile.delete();
					try {
						logFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					forcePartBreak = false;
				}
				
				FileOutputStream fs = null;
				try {
					fs = new FileOutputStream(logFile, true);
					for (int i = 0; i < outBuffer.size(); i++) {
						fs.write(outBuffer.get(i).getBytes());
					}
					fs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			
		}
		
	};
	
	class LogServiceBinder extends Binder{
		LogService getLogger(){
			return LogService.this;
		}
	}

}
