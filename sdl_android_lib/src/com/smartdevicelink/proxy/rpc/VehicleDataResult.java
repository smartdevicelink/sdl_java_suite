package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;

public class VehicleDataResult extends RPCStruct {
	public static final String KEY_DATA_TYPE = "dataType";
	public static final String KEY_RESULT_CODE = "resultCode";

    public VehicleDataResult() { }
    public VehicleDataResult(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setDataType(VehicleDataType dataType) {
    	if (dataType != null) {
    		store.put(KEY_DATA_TYPE, dataType);
    	} else {
    		store.remove(KEY_DATA_TYPE);
    	}
    }
    public VehicleDataType getDataType() {
        Object obj = store.get(KEY_DATA_TYPE);
        if (obj instanceof VehicleDataType) {
            return (VehicleDataType) obj;
        } else if (obj instanceof String) {
        	return VehicleDataType.valueForString((String) obj);
        }
        return null;
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
        	return VehicleDataResultCode.valueForString((String) obj);
        }
        return null;
    }
}
