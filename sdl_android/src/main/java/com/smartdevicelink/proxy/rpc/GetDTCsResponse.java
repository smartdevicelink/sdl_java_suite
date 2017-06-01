package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;
import java.util.List;

/**
 * Get DTCs Response is sent, when GetDTCs has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class GetDTCsResponse extends RPCResponse{

    public static final String KEY_ECU_HEADER = "ecuHeader";
    public static final String KEY_DTC = "dtc";

    public GetDTCsResponse(){
        super(FunctionID.GET_DTCS.toString());
    }

    public GetDTCsResponse(Hashtable<String, Object> hash){
        super(hash);
    }

    @SuppressWarnings("unchecked")
    public List<String> getDtc(){
        return (List<String>) getObject(String.class, KEY_DTC);
    }

    public void setDtc(List<String> dtc){
        setParameter(KEY_DTC, dtc);
    }
    
    public Integer getEcuHeader(){
        return getInteger(KEY_ECU_HEADER);
    }
    
    public void setEcuHeader(Integer ecuHeader){
        setParameter(KEY_ECU_HEADER, ecuHeader);
    }
}
