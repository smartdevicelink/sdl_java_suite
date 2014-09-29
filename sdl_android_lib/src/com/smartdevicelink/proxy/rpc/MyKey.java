package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;
import com.smartdevicelink.util.DebugTool;

public class MyKey extends RPCStruct {

    public MyKey() { }
    public MyKey(Hashtable hash) {
        super(hash);
    }

    public void setE911Override(VehicleDataStatus e911Override) {
        if (e911Override != null) {
            store.put(Names.e911Override, e911Override);
        } else {
        	store.remove(Names.e911Override);
        }
    }
    public VehicleDataStatus getE911Override() {
        Object obj = store.get(Names.e911Override);
        if (obj instanceof VehicleDataStatus) {
            return (VehicleDataStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataStatus theCode = null;
            try {
                theCode = VehicleDataStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.e911Override, e);
            }
            return theCode;
        }
        return null;
    }
}
