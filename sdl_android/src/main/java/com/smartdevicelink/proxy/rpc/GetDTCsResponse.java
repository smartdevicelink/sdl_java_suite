package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;
import java.util.List;

import static com.smartdevicelink.proxy.constants.Names.choiceSet;

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
        if(parameters.get(KEY_DTC) instanceof List<?>){
            List<?> list = (List<?>) parameters.get(KEY_DTC);
            if(list != null && list.size() > 0){
                Object obj = list.get(0);
                if(obj instanceof String){
                    return (List<String>) list;
                }
            }
        }
        return null;
    }

    public void setDtc(List<String> dtc){
        setParameters(KEY_DTC, dtc);
    }
    
    public Integer getEcuHeader(){
        return (Integer) parameters.get(KEY_ECU_HEADER);
    }
    
    public void setEcuHeader(Integer ecuHeader){
        setParameters(KEY_ECU_HEADER, ecuHeader);
    }
}
