package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;
import com.smartdevicelink.util.LogTool;

public class MyKey extends RPCStruct {
    public static final String KEY_E_911_OVERRIDE = "e911Override";

    public MyKey() { }
    public MyKey(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setE911Override(VehicleDataStatus e911Override) {
        if (e911Override != null) {
            store.put(KEY_E_911_OVERRIDE, e911Override);
        } else {
        	store.remove(KEY_E_911_OVERRIDE);
        }
    }
    public VehicleDataStatus getE911Override() {
        Object obj = store.get(KEY_E_911_OVERRIDE);
        if (obj instanceof VehicleDataStatus) {
            return (VehicleDataStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataStatus theCode = null;
            try {
                theCode = VehicleDataStatus.valueForString((String) obj);
            } catch (Exception e) {
                LogTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_E_911_OVERRIDE, e);
            }
            return theCode;
        }
        return null;
    }
}
