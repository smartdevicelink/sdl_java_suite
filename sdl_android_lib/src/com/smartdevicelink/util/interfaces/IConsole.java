package com.smartdevicelink.util.interfaces;


public interface IConsole {
	void logInfo(String msg);
	void logError(String msg);
	void logError(String msg, Throwable ex);
	void logRpcSend(String rpcMsg);
	void logRpcReceive(String rpcMsg);
}
