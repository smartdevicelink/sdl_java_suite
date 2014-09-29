package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.util.DebugTool;

public class DIDResult extends RPCStruct {
	
    public DIDResult() {}
    public DIDResult(Hashtable hash) {
        super(hash);
    }
    public void setResultCode(VehicleDataResultCode resultCode) {
    	if (resultCode != null) {
    		store.put(Names.resultCode, resultCode);
    	} else {
    		store.remove(Names.resultCode);
    	}
    }
    public VehicleDataResultCode getResultCode() {
        Object obj = store.get(Names.resultCode);
        if (obj instanceof VehicleDataResultCode) {
            return (VehicleDataResultCode) obj;
        } else if (obj instanceof String) {
        	VehicleDataResultCode theCode = null;
            try {
                theCode = VehicleDataResultCode.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.resultCode, e);
            }
            return theCode;
        }
        return null;
    }
    public void setDidLocation(Integer didLocation) {
    	if (didLocation != null) {
    		store.put(Names.didLocation, didLocation);
    	} else {
    		store.remove(Names.didLocation);
    	}
    }
    public Integer getDidLocation() {
    	return (Integer) store.get(Names.didLocation);
    }    
    public void setData(String data) {
    	if (data != null) {
    		store.put(Names.data, data);
    	} else {
    		store.remove(Names.data);
    	}
    }
    public String getData() {
    	return (String) store.get(Names.data);
    }
}