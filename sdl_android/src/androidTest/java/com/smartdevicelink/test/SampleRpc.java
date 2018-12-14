package com.smartdevicelink.test;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.BinaryFrameHeader;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlPacketFactory;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.rpc.GetVehicleData;

public class SampleRpc {

	
    private final int SAMPLE_RPC_CORRELATION_ID = 630;

    int version = 1;
    int sessionId =1;
    ProtocolMessage pm = null;
    BinaryFrameHeader binFrameHeader = null;
    
    /**
     * Currently builds a GetVehicleData Request
     */
	public SampleRpc(int version){
		this.version = version;
		createBase();
		
	}
	public void createBase(){
		GetVehicleData request = new GetVehicleData();
		request.setAirbagStatus(true);
		request.setBeltStatus(true);
		request.setBeltStatus(true);
		request.setCorrelationID(SAMPLE_RPC_CORRELATION_ID);
		
		byte[] msgBytes = JsonRPCMarshaller.marshall(request, (byte)version);
		pm = new ProtocolMessage();
		pm.setData(msgBytes);
		pm.setSessionID((byte)sessionId);
		pm.setMessageType(MessageType.RPC);
		pm.setSessionType(SessionType.RPC);
		pm.setFunctionID(FunctionID.getFunctionId(request.getFunctionName()));
		pm.setCorrID(request.getCorrelationID());
		
		if (request.getBulkData() != null) {
			pm.setBulkData(request.getBulkData());
		}
		
	}
	public ProtocolMessage getProtocolMessage(){
		return pm;
	}
	public BinaryFrameHeader getBinaryFrameHeader(boolean refresh){
		if(version>1 && (refresh || binFrameHeader == null)){
			binFrameHeader = SdlPacketFactory.createBinaryFrameHeader(pm.getRPCType(), pm.getFunctionID(), pm.getCorrID(), pm.getJsonSize());
		}
		return binFrameHeader;
	}
	
	/**
	 * To manually set the bfh. Useful for trying to corrupt data
	 */
	public void setBinaryFrameHeader(BinaryFrameHeader binFrameHeader){
		this.binFrameHeader = binFrameHeader;
	}

	
	public SdlPacket toSdlPacket(){
		byte[] data = null;
		if(version > 1) { //If greater than v1 we need to include a binary frame header in the data before all the JSON starts
			data = new byte[12 + pm.getJsonSize()];
			if(binFrameHeader == null){
				getBinaryFrameHeader(false);
			}
			System.arraycopy(binFrameHeader.assembleHeaderBytes(), 0, data, 0, 12);
			System.arraycopy(pm.getData(), 0, data, 12, pm.getJsonSize());
		}else{
			data = pm.getData();
		}
		
		return  new SdlPacket(version,false,SdlPacket.FRAME_TYPE_SINGLE,SdlPacket.SERVICE_TYPE_RPC,0,sessionId,data.length,123,data);
		
	}
	
	public byte[] toByteArray(){
		return toSdlPacket().constructPacket();
	}
}
