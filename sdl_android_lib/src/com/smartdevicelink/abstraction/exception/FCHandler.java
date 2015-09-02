package com.smartdevicelink.abstraction.exception;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;

import com.smartdevicelink.abstraction.log.Log;

public class FCHandler implements UncaughtExceptionHandler {
	private static UncaughtExceptionHandler defaultHandler;
	private static UncaughtExceptionHandler logHandler;
	
	public FCHandler() {
		if(defaultHandler == null){
			defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
			logHandler = Log.getUncaughtExceptionHandler();
		}
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		logHandler.uncaughtException(thread, ex);
		defaultHandler.uncaughtException(thread, ex);
		
	}

}
