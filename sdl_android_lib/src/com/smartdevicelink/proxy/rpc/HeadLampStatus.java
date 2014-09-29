package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;
import com.smartdevicelink.util.DebugTool;

public class HeadLampStatus extends RPCStruct {

    public HeadLampStatus() {}
    public HeadLampStatus(Hashtable hash) {
        super(hash);
    }
    public void setAmbientLightStatus(AmbientLightStatus ambientLightSensorStatus) {
        if (ambientLightSensorStatus != null) {
            store.put(Names.ambientLightSensorStatus, ambientLightSensorStatus);
        } else {
        	store.remove(Names.ambientLightSensorStatus);
        }
    }
    public AmbientLightStatus getAmbientLightStatus() {
        Object obj = store.get(Names.ambientLightSensorStatus);
        if (obj instanceof AmbientLightStatus) {
            return (AmbientLightStatus) obj;
        } else if (obj instanceof String) {
        	AmbientLightStatus theCode = null;
            try {
                theCode = AmbientLightStatus.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.ambientLightSensorStatus, e);
            }
            return theCode;
        }
        return null;
    }
    public void setHighBeamsOn(Boolean highBeamsOn) {
        if (highBeamsOn != null) {
            store.put(Names.highBeamsOn, highBeamsOn);
        } else {
        	store.remove(Names.highBeamsOn);
        }
    }
    public Boolean getHighBeamsOn() {
    	return (Boolean) store.get(Names.highBeamsOn);
    }
    public void setLowBeamsOn(Boolean lowBeamsOn) {
        if (lowBeamsOn != null) {
            store.put(Names.lowBeamsOn, lowBeamsOn);
        } else {
        	store.remove(Names.lowBeamsOn);
        }
    }
    public Boolean getLowBeamsOn() {
    	return (Boolean) store.get(Names.lowBeamsOn);
    }
}
