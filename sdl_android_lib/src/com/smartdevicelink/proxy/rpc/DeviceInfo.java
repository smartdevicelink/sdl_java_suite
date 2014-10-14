package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;

public class DeviceInfo extends RPCStruct{
    public static final String hardware = "hardware";
    public static final String firmwareRev = "firmwareRev";
    public static final String os = "os";
    public static final String osVersion = "osVersion";
    public static final String carrier = "carrier";
    public static final String maxNumberRFCOMMPorts = "maxNumberRFCOMMPorts";

	public DeviceInfo() { }
 
	public DeviceInfo(Hashtable hash) {
        super(hash);
    }
	
   public void setHardware(String hardware) {
        if (hardware != null) {
        	store.put(DeviceInfo.hardware, hardware);
        } else {
        	store.remove(DeviceInfo.hardware);
        }
   }

   public String getHardware() {
       return (String) store.get(DeviceInfo.hardware);
   }
    
   public void setFirmwareRev(String firmwareRev) {
       if (firmwareRev != null) {
       	store.put(DeviceInfo.firmwareRev, firmwareRev);
       } else {
       	store.remove(DeviceInfo.firmwareRev);
       }
  }

  public String getFirmwareRev() {
      return (String) store.get(DeviceInfo.firmwareRev);
  }

  public void setOs(String os) {
      if (os != null) {
      	store.put(DeviceInfo.os, os);
      } else {
      	store.remove(DeviceInfo.os);
      }
  }

 public String getOs() {
     return (String) store.get(DeviceInfo.os);
 }  

 public void setOsVersion(String osVersion) {
     if (osVersion != null) {
     	store.put(DeviceInfo.osVersion, osVersion);
     } else {
     	store.remove(DeviceInfo.osVersion);
     }
}

public String getOsVersion() {
    return (String) store.get(DeviceInfo.osVersion);
}  
    
public void setCarrier(String carrier) {
    if (carrier != null) {
    	store.put(DeviceInfo.carrier, carrier);
    } else {
    	store.remove(DeviceInfo.carrier);
    }
}

public String getCarrier() {
   return (String) store.get(DeviceInfo.carrier);
} 

public Integer getMaxNumberRFCOMMPorts() {
    return (Integer) store.get( DeviceInfo.maxNumberRFCOMMPorts );
}

public void setMaxNumberRFCOMMPorts( Integer maxNumberRFCOMMPorts ) {
    if (maxNumberRFCOMMPorts != null) {
        store.put(DeviceInfo.maxNumberRFCOMMPorts, maxNumberRFCOMMPorts );
    }
    else {
    	store.remove(DeviceInfo.maxNumberRFCOMMPorts);
    }    
}

}
