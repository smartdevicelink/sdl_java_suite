package com.smartdevicelink.streaming;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import com.smartdevicelink.marshall.JsonRpcMarshaller;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RpcRequest;
import com.smartdevicelink.proxy.RpcResponse;
import com.smartdevicelink.proxy.SdlProxyBase;
import com.smartdevicelink.proxy.interfaces.IProxyListenerBase;
import com.smartdevicelink.proxy.interfaces.IPutFileResponseListener;
import com.smartdevicelink.rpc.enums.Result;
import com.smartdevicelink.rpc.notifications.OnStreamRpc;
import com.smartdevicelink.rpc.requests.PutFile;
import com.smartdevicelink.rpc.responses.PutFileResponse;
import com.smartdevicelink.rpc.responses.StreamRpcResponse;
import com.smartdevicelink.streaming.interfaces.IStreamListener;

public class StreamRpcPacketizer extends AbstractPacketizer implements IPutFileResponseListener, Runnable{

	private Integer iInitialCorrId = 0;
	private final static int BUFF_READ_SIZE = 1000000;
	private Hashtable<Integer, OnStreamRpc> notificationList = new Hashtable<Integer, OnStreamRpc>();
	private Thread thread = null;
	private long lFileSize = 0;
	private String sFileName;
	private SdlProxyBase<IProxyListenerBase> proxy;
	private IProxyListenerBase proxyListener;
	
    private Object mPauseLock;
    private boolean mPaused;

	public StreamRpcPacketizer(SdlProxyBase<IProxyListenerBase> proxy, IStreamListener streamListener, InputStream is, RpcRequest request, SessionType sType, byte rpcSessionId, byte wiproVersion, long iLength) throws IOException {
		super(streamListener, is, request, sType, rpcSessionId, wiproVersion);
		lFileSize = iLength;
		iInitialCorrId = request.getCorrelationId();
        mPauseLock = new Object();
        mPaused = false;
		if (proxy != null)
		{
			this.proxy = proxy;
			proxyListener = proxy.getProxyListener();
			proxy.addPutFileResponseListener(this);
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

	private void handleStreamSuccess(RpcResponse rpc, Long iSize)
	{
		StreamRpcResponse result = new StreamRpcResponse();
		result.setSuccess(rpc.getSuccess());
		result.setResultCode(rpc.getResultCode());
		result.setInfo(rpc.getInfo());
		result.setFileName(sFileName);
		result.setFileSize(iSize);
		result.setCorrelationId(iInitialCorrId);
		if (proxyListener != null)
			proxyListener.onStreamRPCResponse(result);
		stop();
		proxy.remPutFileResponseListener(this);
		return;	
	}
	
	private void handleStreamException(RpcResponse rpc, Exception e, String error)
	{
		StreamRpcResponse result = new StreamRpcResponse();
		result.setFileName(sFileName);
		result.setCorrelationId(iInitialCorrId);
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
		if (proxyListener != null)
			proxyListener.onStreamRPCResponse(result);		
		if (e != null)
			e.printStackTrace();
		stop();
		proxy.remPutFileResponseListener(this);
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
		OnStreamRpc notification;
		// Moves the current Thread into the background
		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

		try {
			
			int iCorrId = 0;
			PutFile msg = (PutFile) request;
			long iOffsetCounter = msg.getOffset();
			sFileName = msg.getSdlFileName();
									
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

					msgBytes = JsonRpcMarshaller.marshall(msg, wiproVersion);					
					pm = new ProtocolMessage();
					pm.setData(msgBytes);

					pm.setSessionId(rpcSessionId);
					pm.setMessageType(MessageType.RPC);
					pm.setSessionType(session);
					pm.setFunctionId(FunctionId.getFunctionId(msg.getFunctionName()));
					
					if (buffer.length != length)
						pm.setBulkData(buffer, length);
					else
						pm.setBulkDataNoCopy(buffer);

					pm.setCorrId(msg.getCorrelationId());
						
					notification = new OnStreamRpc();
					notification.setFileName(msg.getSdlFileName());
					notification.setFileSize(iFileLength);										
			        iOffsetCounter = iOffsetCounter + length;
			        notification.setBytesComplete(iOffsetCounter);
			        notificationList.put(msg.getCorrelationId(),notification);
			        
			        msg.setOffset(iOffsetCounter);
					iCorrId = msg.getCorrelationId() + 1;
					msg.setCorrelationId(iCorrId);

			        streamListener.sendStreamPacket(pm);
				}
			}
		} catch (Exception e) {
			handleStreamException(null, e, "");
		}
	}

	@Override
	public void onPutFileResponse(PutFileResponse response) 
	{	
		OnStreamRpc streamNote = notificationList.get(response.getCorrelationId());
		if (streamNote == null) return;

		if (response.getSuccess())
		{
			if (proxyListener != null)
				proxyListener.onOnStreamRPC(streamNote);
		}		
		else
		{
			handleStreamException(response, null, "");
		}		
		
		if (response.getSuccess() && streamNote.getBytesComplete().equals(streamNote.getFileSize()) )
		{
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
