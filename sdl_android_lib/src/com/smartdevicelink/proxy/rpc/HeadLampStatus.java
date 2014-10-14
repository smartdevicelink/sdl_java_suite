package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;
import com.smartdevicelink.util.DebugTool;

public class HeadLampStatus extends RPCStruct {
	public static final String ambientLightSensorStatus = "ambientLightSensorStatus";
	public static final String highBeamsOn = "highBeamsOn";
    public static final String lowBeamsOn = "lowBeamsOn";

    public HeadLampStatus() {}
    public HeadLampStatus(Hashtable hash) {
        super(hash);
    }
    public void setAmbientLightStatus(AmbientLightStatus ambientLightSensorStatus) {
        if (ambientLightSensorStatus != null) {
            store.put(HeadLampStatus.ambientLightSensorStatus, ambientLightSensorStatus);
        } else {
        	store.remove(HeadLampStatus.ambientLightSensorStatus);
        }
    }
    public AmbientLightStatus getAmbientLightStatus() {
        Object obj = store.get(HeadLampStatus.ambientLightSensorStatus);
        if (obj instanceof AmbientLightStatus) {
            return (AmbientLightStatus) obj;
        } else if (obj instanceof String) {
        	AmbientLightStatus theCode = null;
            try {
                theCode = AmbientLightStatus.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + HeadLampStatus.ambientLightSensorStatus, e);
            }
            return theCode;
        }
        return null;
    }
    public void setHighBeamsOn(Boolean highBeamsOn) {
        if (highBeamsOn != null) {
            store.put(HeadLampStatus.highBeamsOn, highBeamsOn);
        } else {
        	store.remove(HeadLampStatus.highBeamsOn);
        }
    }
    public Boolean getHighBeamsOn() {
    	return (Boolean) store.get(HeadLampStatus.highBeamsOn);
    }
    public void setLowBeamsOn(Boolean lowBeamsOn) {
        if (lowBeamsOn != null) {
            store.put(HeadLampStatus.lowBeamsOn, lowBeamsOn);
        } else {
        	store.remove(HeadLampStatus.lowBeamsOn);
        }
    }
    public Boolean getLowBeamsOn() {
    	return (Boolean) store.get(HeadLampStatus.lowBeamsOn);
    }
}
