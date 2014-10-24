package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.util.DebugTool;

public class DIDResult extends RPCStruct {
	public static final String KEY_RESULT_CODE = "resultCode";
	public static final String KEY_DATA = "data";
	public static final String KEY_DID_LOCATION = "didLocation";
	
    public DIDResult() {}
    public DIDResult(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setResultCode(VehicleDataResultCode resultCode) {
    	if (resultCode != null) {
    		store.put(KEY_RESULT_CODE, resultCode);
    	} else {
    		store.remove(KEY_RESULT_CODE);
    	}
    }
    public VehicleDataResultCode getResultCode() {
        Object obj = store.get(KEY_RESULT_CODE);
        if (obj instanceof VehicleDataResultCode) {
            return (VehicleDataResultCode) obj;
        } else if (obj instanceof String) {
        	VehicleDataResultCode theCode = null;
            try {
                theCode = VehicleDataResultCode.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_RESULT_CODE, e);
            }
            return theCode;
        }
        return null;
    }
    public void setDidLocation(Integer didLocation) {
    	if (didLocation != null) {
    		store.put(KEY_DID_LOCATION, didLocation);
    	} else {
    		store.remove(KEY_DID_LOCATION);
    	}
    }
    public Integer getDidLocation() {
    	return (Integer) store.get(KEY_DID_LOCATION);
    }    
    public void setData(String data) {
    	if (data != null) {
    		store.put(KEY_DATA, data);
    	} else {
    		store.remove(KEY_DATA);
    	}
    }
    public String getData() {
    	return (String) store.get(KEY_DATA);
    }
}
