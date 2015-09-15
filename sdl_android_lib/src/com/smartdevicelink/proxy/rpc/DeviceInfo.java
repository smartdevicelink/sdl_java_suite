package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;

public class DeviceInfo extends RPCStruct{
    public static final String KEY_HARDWARE = "hardware";
    public static final String KEY_FIRMWARE_REV = "firmwareRev";
    public static final String KEY_OS = "os";
    public static final String KEY_OS_VERSION = "osVersion";
    public static final String KEY_CARRIER = "carrier";
    public static final String KEY_MAX_NUMBER_RFCOMM_PORTS = "maxNumberRFCOMMPorts";
	public static final String DEVICE_OS = "Android";

	public DeviceInfo() { }
 
	public DeviceInfo(Hashtable<String, Object> hash) {
        super(hash);
    }

   public void setHardware(String hardware) {
        if (hardware != null) {
        	store.put(KEY_HARDWARE, hardware);
        } else {
        	store.remove(KEY_HARDWARE);
        }
   }

   public String getHardware() {
       return (String) store.get(KEY_HARDWARE);
   }
    
   public void setFirmwareRev(String firmwareRev) {
       if (firmwareRev != null) {
       	store.put(KEY_FIRMWARE_REV, firmwareRev);
       } else {
       	store.remove(KEY_FIRMWARE_REV);
       }
  }

  public String getFirmwareRev() {
      return (String) store.get(KEY_FIRMWARE_REV);
  }

  public void setOs(String os) {
      if (os != null) {
      	store.put(KEY_OS, os);
      } else {
      	store.remove(KEY_OS);
      }
  }

 public String getOs() {
     return (String) store.get(KEY_OS);
 }  

 public void setOsVersion(String osVersion) {
     if (osVersion != null) {
     	store.put(KEY_OS_VERSION, osVersion);
     } else {
     	store.remove(KEY_OS_VERSION);
     }
}

public String getOsVersion() {
    return (String) store.get(KEY_OS_VERSION);
}  
    
public void setCarrier(String carrier) {
    if (carrier != null) {
    	store.put(KEY_CARRIER, carrier);
    } else {
    	store.remove(KEY_CARRIER);
    }
}

public String getCarrier() {
   return (String) store.get(KEY_CARRIER);
} 

public Integer getMaxNumberRFCOMMPorts() {
    return (Integer) store.get( KEY_MAX_NUMBER_RFCOMM_PORTS );
}

public void setMaxNumberRFCOMMPorts( Integer maxNumberRFCOMMPorts ) {
    if (maxNumberRFCOMMPorts != null) {
        store.put(KEY_MAX_NUMBER_RFCOMM_PORTS, maxNumberRFCOMMPorts );
    }
    else {
    	store.remove(KEY_MAX_NUMBER_RFCOMM_PORTS);
    }    
}

}
