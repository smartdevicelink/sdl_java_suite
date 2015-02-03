package com.smartdevicelink.streaming;

import java.io.IOException;
import java.io.InputStream;

import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.PutFile;

public class StreamRPCPacketizer extends AbstractPacketizer implements Runnable{

	public final static String TAG = "StreamPacketizer";
	public final static int BUFF_READ_SIZE = 1000;
	
	private Thread t = null;

	public StreamRPCPacketizer(IStreamListener streamListener, InputStream is, RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion) throws IOException {
		super(streamListener, is, request, sType, rpcSessionID, wiproVersion);
	}

	public void start() throws IOException {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	public void stop() {
		try {
			is.close();
		} catch (IOException ignore) {}
		t.interrupt();
		t = null;
	}

	public void run() {
		int length;

		try {
			
			int iCorrID = 0;
			PutFile msg = (PutFile) _request;
			int iOffsetCounter = msg.getOffset();
			
			while (!Thread.interrupted()) {				
			
				length = is.read(buffer, 0, BUFF_READ_SIZE);				
				
				if (length == -1)
					stop();
				
				if (length >= 0) {
			        
					if (msg.getOffset() != 0)
			        	msg.setLength(null); //only need to send length when offset 0

					byte[] msgBytes = msg.toJson(_wiproVersion).toString().getBytes();
					ProtocolMessage pm = new ProtocolMessage();
					pm.setData(msgBytes);

					pm.setSessionID(_rpcSessionID);
					pm.setMessageType(MessageType.RPC);
					pm.setSessionType(_session);
					pm.setFunctionID(FunctionID.getFunctionID(msg.getFunctionName()));
					
					pm.setBulkData(buffer, length);
					pm.setCorrID(msg.getCorrelationID());
						
			        _streamListener.sendStreamPacket(pm);
			        
			        iOffsetCounter = iOffsetCounter + length;
			        msg.setOffset(iOffsetCounter);
					iCorrID = msg.getCorrelationID() + 1;
					msg.setCorrelationID(iCorrID);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
