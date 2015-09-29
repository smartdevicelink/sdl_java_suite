package com.smartdevicelink.proxy;

import com.smartdevicelink.proxy.interfaces.ITLSHandshakeListener;

public class TLSManager implements ITLSHandshakeListener
{
	private Object TLS_REFERENCE_LOCK = new Object();
	private byte[] cert;
	private byte[] key;
	private boolean isHandshakeComplete = false;
	private boolean isInitSuccess = false;
	
	public int iSessionId;
	
    static 
    {
    	System.loadLibrary("crypto");
    	System.loadLibrary("TLSManager");
    }
       
    private native boolean isHandShakeComplete(int iSessionId);
    private native boolean initOpenSSL(byte[] cert, byte[] key, int iSessionId);
    private native int SSLWriteDataToServer(byte[] byteData, int iSessionId);
    private native int SSLReadDataFromServer(byte[] byteData, int iSessionId);
    private native int BIOWriteDataToServer(byte[] byteData, int iSessionId);
    private native int BIOReadDataFromServer(byte[] byteData, int iSessionId);
    private native int SSLPendingDataFromServer(int iSessionId);
    private native int BIO_pending(int iSessionId); 
    private native void close(int iSessionId);
    
    public TLSManager()
    {  
    }
    
    public boolean initializeOpenSSL(byte[] cert, byte[] key)
    {
    	synchronized(TLS_REFERENCE_LOCK)
    	{
    		this.cert = cert;
    		this.key  = key;
    		return initOpenSSL(cert,key,iSessionId);  		
    	}
    }	
    
    public boolean completeHanshake()
    {
    	if (!getInitSuccess())
    		return false;

    	synchronized(TLS_REFERENCE_LOCK)
    	{
    		return isHandShakeComplete(iSessionId);
    	}
    }    
    
    public Integer SSLWriteData(byte[] byteData)
    {
    	if (!getInitSuccess())
    		return null;

    	synchronized(TLS_REFERENCE_LOCK)
    	{
    		return SSLWriteDataToServer(byteData,iSessionId);
    	}    	
    }
    
    public Integer SSLReadData(byte[] byteData)
    {
    	if (!getInitSuccess())
    		return null;
    	    	
    	synchronized(TLS_REFERENCE_LOCK)
    	{
    		return SSLReadDataFromServer(byteData,iSessionId);
    	}    	
    }
    
    public Integer BIOWriteData(byte[] byteData)	
    {
    	if (!getInitSuccess())
    		return null;

    	synchronized(TLS_REFERENCE_LOCK)
    	{
    		return BIOWriteDataToServer(byteData, iSessionId);
    	}    	
    }
    
    public Integer BIOReadData(byte[] byteData)    
    {
    	if (!getInitSuccess())
    		return null;

    	synchronized(TLS_REFERENCE_LOCK)
    	{
    		return BIOReadDataFromServer(byteData, iSessionId);
    	}
    }

    public void closeOpenSSLSession()
    {
    	close(iSessionId);
    }
    
    private void setHandshakeComplete(boolean bVal)
    {
    	isHandshakeComplete = bVal;
    }

    public boolean getHandshakeComplete()
    {
    	return isHandshakeComplete;
    }
    
    public synchronized boolean getInitSuccess()
    {
    	return isInitSuccess;
    }
    
    private synchronized void setInitSuccess(boolean bVal)
    {
    	isInitSuccess = bVal;
    }
    
    @Override
	public void onHandShakeComplete() 
    {
    	setHandshakeComplete(true);		
	}   

    @Override
	public void onInitSuccess() 
    {
    	setInitSuccess(true);
    }

}
