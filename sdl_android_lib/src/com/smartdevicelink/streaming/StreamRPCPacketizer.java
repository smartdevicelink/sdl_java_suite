package com.smartdevicelink.streaming;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import com.smartdevicelink.SdlConnection.SdlSession;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SdlProxyBase;
import com.smartdevicelink.proxy.interfaces.IProxyListenerBase;
import com.smartdevicelink.proxy.interfaces.IPutFileResponseListener;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnPutFileUpdateListener;

public class StreamRPCPacketizer extends AbstractPacketizer implements IPutFileResponseListener, Runnable{

	private Integer iInitialCorrID = 0;
	private final static int BUFF_READ_SIZE = 1000000;
	private Hashtable<Integer, OnStreamRPC> notificationList = new Hashtable<Integer, OnStreamRPC>();
	private Thread thread = null;
	private long lFileSize = 0;
	private String sFileName;
	private SdlProxyBase<IProxyListenerBase> _proxy;
	private IProxyListenerBase _proxyListener;

    private Object mPauseLock;
    private boolean mPaused;
    private boolean isRPCProtected = false;
	private OnPutFileUpdateListener callBack; 

	public StreamRPCPacketizer(SdlProxyBase<IProxyListenerBase> proxy, IStreamListener streamListener, InputStream is, RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion, long lLength, SdlSession session) throws IOException {
		super(streamListener, is, request, sType, rpcSessionID, wiproVersion, session);
		lFileSize = lLength;
		iInitialCorrID = request.getCorrelationID();
        mPauseLock = new Object();
        mPaused = false;
        isRPCProtected = request.isPayloadProtected();
		if (proxy != null)
		{
			_proxy = proxy;
			_proxyListener = _proxy.getProxyListener();
			_proxy.addPutFileResponseListener(this);
		}
		if(_request.getFunctionName().equalsIgnoreCase(FunctionID.PUT_FILE.toString())){
			callBack = ((PutFile)_request).getOnPutFileUpdateListener();
		}
	}

	@Override
	public void start() throws IOException {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void stop() {
		try {
			is.close();
		} catch (IOException ignore) {}
		if (thread != null)
		{
			thread.interrupt();
			thread = null;
		}
	}

	private void handleStreamSuccess(RPCResponse rpc, Long iSize)
	{
		StreamRPCResponse result = new StreamRPCResponse();
		result.setSuccess(rpc.getSuccess());
		result.setResultCode(rpc.getResultCode());
		result.setInfo(rpc.getInfo());
		result.setFileName(sFileName);
		result.setFileSize(iSize);
		result.setCorrelationID(iInitialCorrID);
		if (_proxyListener != null)
			_proxyListener.onStreamRPCResponse(result);
		stop();
		_proxy.remPutFileResponseListener(this);
		return;	
	}
	
	private void handleStreamException(RPCResponse rpc, Exception e, String error)
	{
		StreamRPCResponse result = new StreamRPCResponse();
		result.setFileName(sFileName);
		result.setCorrelationID(iInitialCorrID);
		if (rpc != null)
		{
			result.setSuccess(rpc.getSuccess());
			result.setResultCode(rpc.getResultCode());
			result.setInfo(rpc.getInfo());
		}
		else
		{
			result.setSuccess(false);
			result.setResultCode(Result.GENERIC_ERROR);
			String sException = "";
			
			if (e != null)
				sException = sException + " " + e.toString();
			
			sException = sException + " " + error;
			result.setInfo(sException);
		}
		if (_proxyListener != null)
			_proxyListener.onStreamRPCResponse(result);		
		if (e != null)
			e.printStackTrace();
		stop();
		_proxy.remPutFileResponseListener(this);
		return;
	}

    @Override
	public void pause() {
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    @Override
    public void resume() {
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }

    public void run() {
		int length;
		byte[] msgBytes;
		ProtocolMessage pm;
		OnStreamRPC notification;
		
		// Moves the current Thread into the background
		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

		try {
			
			int iCorrID = 0;
			PutFile msg = (PutFile) _request;
			sFileName = msg.getSdlFileName();
			long iOffsetCounter = msg.getOffset();
			
			int priorityCoefficient = 1;
			
			if (lFileSize != 0)
			{
				Long iFileSize = (long) lFileSize;
				//TODO: PutFile RPC needs to be updated to accept Long as we might run into overflows since a Long can store a wider range than an Integer
				msg.setLength(iFileSize);
			}
			Long iFileLength = msg.getLength();
			
			notificationList.clear();			
			
			//start reading from the stream at the given offset
			long iSkipBytes = is.skip(iOffsetCounter);

			if (iOffsetCounter != iSkipBytes)
			{
				handleStreamException(null,null," Error, PutFile offset invalid for file: " + sFileName);
			}
			if(callBack!=null){
				callBack.onStart(_request.getCorrelationID(), lFileSize);
			}
			while (!Thread.interrupted()) {				
			
				synchronized (mPauseLock)
				{
					while (mPaused)
                    {
						try
                        {
							mPauseLock.wait();
                        }
                        catch (InterruptedException e) {}
                    }
                }

				length = is.read(buffer, 0, BUFF_READ_SIZE);				
				
				if (length == -1)
					stop();
				
				if (length >= 0) {
			        
					if (msg.getOffset() != 0)
			        	msg.setLength((Long)null); //only need to send length when offset 0

					msgBytes = JsonRPCMarshaller.marshall(msg, _wiproVersion);					
					pm = new ProtocolMessage();
					pm.setData(msgBytes);

					pm.setSessionID(_rpcSessionID);
					pm.setMessageType(MessageType.RPC);
					pm.setSessionType(_serviceType);
					pm.setFunctionID(FunctionID.getFunctionId(msg.getFunctionName()));
					
					if (buffer.length != length)
						pm.setBulkData(buffer, length);
					else
						pm.setBulkDataNoCopy(buffer);

					pm.setCorrID(msg.getCorrelationID());
					pm.setPayloadProtected(isRPCProtected);
					priorityCoefficient++;
					pm.setPriorityCoefficient(priorityCoefficient);
						
					notification = new OnStreamRPC();
					notification.setFileName(msg.getSdlFileName());
					notification.setFileSize(iFileLength);										
			        iOffsetCounter = iOffsetCounter + length;
			        notification.setBytesComplete(iOffsetCounter);
			        notificationList.put(msg.getCorrelationID(),notification);
			        
			        msg.setOffset(iOffsetCounter);
					iCorrID = msg.getCorrelationID() + 1;
					msg.setCorrelationID(iCorrID);
					
			        _streamListener.sendStreamPacket(pm);
				}
			}
		} catch (Exception e) {
			handleStreamException(null, e, "");
		}
	}

	@Override
	public void onPutFileResponse(PutFileResponse response) 
	{	
		
		OnStreamRPC streamNote = notificationList.get(response.getCorrelationID());
		if (streamNote == null) return;
		
		if (response.getSuccess())
		{
			if(callBack!=null){
				callBack.onUpdate(response.getCorrelationID(), streamNote.getBytesComplete(), lFileSize);
			}
			if (_proxyListener != null){
				_proxyListener.onOnStreamRPC(streamNote);
			}
			
		}		
		else
		{
			if(callBack!=null){
				callBack.onError(response.getCorrelationID(), response.getResultCode(), response.getInfo());
			}
			handleStreamException(response, null, "");
			
		}		
		
		if (response.getSuccess() && streamNote.getBytesComplete().equals(streamNote.getFileSize()) )
		{
			if(callBack!=null){
				callBack.onResponse(iInitialCorrID, response, streamNote.getBytesComplete());
			}
			handleStreamSuccess(response, streamNote.getBytesComplete());
			
		}
	}

	@Override
	public void onPutFileStreamError(Exception e, String info) 
	{
		if (thread != null)
			handleStreamException(null, e, info);
		
	}
}
