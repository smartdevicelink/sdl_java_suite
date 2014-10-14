package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.util.DebugTool;

public class DIDResult extends RPCStruct {
	public static final String resultCode = "resultCode";
	public static final String data = "data";
	public static final String didLocation = "didLocation";
	
    public DIDResult() {}
    public DIDResult(Hashtable hash) {
        super(hash);
    }
    public void setResultCode(VehicleDataResultCode resultCode) {
    	if (resultCode != null) {
    		store.put(DIDResult.resultCode, resultCode);
    	} else {
    		store.remove(DIDResult.resultCode);
    	}
    }
    public VehicleDataResultCode getResultCode() {
        Object obj = store.get(DIDResult.resultCode);
        if (obj instanceof VehicleDataResultCode) {
            return (VehicleDataResultCode) obj;
        } else if (obj instanceof String) {
        	VehicleDataResultCode theCode = null;
            try {
                theCode = VehicleDataResultCode.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + DIDResult.resultCode, e);
            }
            return theCode;
        }
        return null;
    }
    public void setDidLocation(Integer didLocation) {
    	if (didLocation != null) {
    		store.put(DIDResult.didLocation, didLocation);
    	} else {
    		store.remove(DIDResult.didLocation);
    	}
    }
    public Integer getDidLocation() {
    	return (Integer) store.get(DIDResult.didLocation);
    }    
    public void setData(String data) {
    	if (data != null) {
    		store.put(DIDResult.data, data);
    	} else {
    		store.remove(DIDResult.data);
    	}
    }
    public String getData() {
    	return (String) store.get(DIDResult.data);
    }
}
