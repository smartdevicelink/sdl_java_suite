package com.smartdevicelink.streaming;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

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

public class StreamRPCPacketizer extends AbstractPacketizer implements IPutFileResponseListener, Runnable{

	private Integer iInitialCorrID = 0;
	private final static int BUFF_READ_SIZE = 1000000;
	private Hashtable<Integer, OnStreamRPC> notificationList = new Hashtable<Integer, OnStreamRPC>();
	private Thread thread = null;
	private long lFileSize = 0;
	private String sFileName;
	private SdlProxyBase<IProxyListenerBase> _proxy;
	private IProxyListenerBase _proxyListener;
	
	public StreamRPCPacketizer(SdlProxyBase<IProxyListenerBase> proxy, IStreamListener streamListener, InputStream is, RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion, long lLength) throws IOException {
		super(streamListener, is, request, sType, rpcSessionID, wiproVersion);
		lFileSize = lLength;
		iInitialCorrID = request.getCorrelationID();
		if (proxy != null)
		{
			_proxy = proxy;
			_proxyListener = _proxy.getProxyListener();
			_proxy.addPutFileResponseListener(this);
		}
	}

	public void start() throws IOException {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

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

	private void handleStreamSuccess(RPCResponse rpc, Integer iSize)
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
		return;	
	}
	
	private void handleStreamException(RPCResponse rpc, Exception e)
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
			
			result.setInfo(sException);
		}
		if (_proxyListener != null)
			_proxyListener.onStreamRPCResponse(result);		
		if (e != null)
			e.printStackTrace();
		stop();
		return;
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
			int iOffsetCounter = msg.getOffset();
									
			if (lFileSize != 0)
			{
				Integer iFileSize = (int) (long) lFileSize;
				//TODO: PutFile RPC needs to be updated to accept Long as we might run into overflows since a Long can store a wider range than an Integer
				msg.setLength(iFileSize);
			}
			Integer iFileLength = msg.getLength();
			
			notificationList.clear();			
			
			while (!Thread.interrupted()) {				
			
				length = is.read(buffer, 0, BUFF_READ_SIZE);				
				
				if (length == -1)
					stop();
				
				if (length >= 0) {
			        
					if (msg.getOffset() != 0)
			        	msg.setLength(null); //only need to send length when offset 0

					msgBytes = JsonRPCMarshaller.marshall(msg, _wiproVersion);					
					pm = new ProtocolMessage();
					pm.setData(msgBytes);

					pm.setSessionID(_rpcSessionID);
					pm.setMessageType(MessageType.RPC);
					pm.setSessionType(_session);
					pm.setFunctionID(FunctionID.getFunctionID(msg.getFunctionName()));
					
					if (buffer.length != length)
						pm.setBulkData(buffer, length);
					else
						pm.setBulkDataNoCopy(buffer);

					pm.setCorrID(msg.getCorrelationID());
						
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
			handleStreamException(null, e);
		}
	}

	@Override
	public void onPutFileResponse(PutFileResponse response) 
	{	
		OnStreamRPC streamNote = notificationList.get(response.getCorrelationID());
		if (streamNote == null) return;

		if (response.getSuccess())
		{
			if (_proxyListener != null)
				_proxyListener.onOnStreamRPC(streamNote);
		}		
		else
		{
			handleStreamException(response, null);
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
			handleStreamException(null, e);
	}
}
